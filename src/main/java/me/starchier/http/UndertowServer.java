package me.starchier.http;

import io.undertow.Undertow;
import me.starchier.ServerMain;
import me.starchier.configuration.YamlConfiguration;
import me.starchier.http.handlers.InitHandler;
import me.starchier.util.ResourceManager;
import me.starchier.util.UnTarUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.simpleyaml.configuration.file.YamlFile;

import java.io.File;
import java.io.IOException;

public class UndertowServer extends Thread {
    private final Logger LOGGER = LogManager.getLogger(this.getName());
    public static boolean STATE = false;
    public static Undertow server;
    private final String srcPath = System.getProperty("user.dir") + File.separator + "panel" + File.separator + "sdk.tar.gz";
    private final String destPath = System.getProperty("user.dir") + File.separator + "panel";
    @Override
    public void run() {
        YamlFile config = YamlConfiguration.getConfig();
        if(ServerMain.isHttpEnabled) {
            //下载文件
            try {
                ResourceManager.dropResources();
                if(new File(destPath + File.separator + "sdk").listFiles().length==0) {
                    File src = new File(srcPath);
                    if(src.exists()) {
                        src.delete();
                    }
                    LOGGER.info("正在下载百度Amis SDK前端框架 (下载源：Gitee)...");
                    ResourceManager.downloadFile("https://gitee.com/baidu/amis/attach_files/687137/download/sdk.tar.gz",
                            new File(srcPath));
                    LOGGER.info("Amis SDK框架下载成功，正在尝试解压...");
                    UnTarUtil.deCompressGZipFile(srcPath, destPath);
                }
            } catch (IOException e) {
                LOGGER.error("文件下载失败： " , e);
            } catch (Exception e) {
                LOGGER.fatal("处理资源文件出错： ", e);
                System.exit(21);
            }
            LOGGER.info("正在启动后台管理系统HTTP服务...");
            server = Undertow.builder()
                    .addHttpListener(config.getInt("manage-panel.port"), config.getString("manage-panel.host"))
                    .setHandler(new InitHandler())
                    .build();
            server.start();
            STATE = true;
            LOGGER.info("后台管理系统已启动，可用以下链接访问： http://" + config.getString("manage-panel.host") + ":" + config.getInt("manage-panel.port"));
        } else {
            LOGGER.info("未开启用户后台管理系统，服务端将不会启动HTTP服务。");
            STATE = true;
            this.interrupt();
        }
    }
}
