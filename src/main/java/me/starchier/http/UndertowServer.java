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
    public static String webTitle;
    public static Undertow server;
    private final String srcPath = System.getProperty("user.dir") + File.separator + "panel" + File.separator + "sdk.tar.gz";
    private final String destPath = System.getProperty("user.dir") + File.separator + "panel";
    @Override
    public void run() {
        YamlFile config = YamlConfiguration.getConfig();
        if(ServerMain.isPanelEnabled) {
            webTitle = " - " + config.getString("manage-panel.web-title", "后台管理系统");
            //加载网页资源
            LOGGER.info("正在注册并加载后台管理服务资源至内存...");
            WebPagesManager.registerInitPages();
            //下载文件
            try {
                ResourceManager.dropResources();
                if(new File(destPath + File.separator + "sdk").listFiles().length == 0) {
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
        }
        LOGGER.info("正在启动HTTP服务...");
        server = Undertow.builder()
                .addHttpListener(config.getInt("http-services.port"), config.getString("http-services.host"))
                .setHandler(new InitHandler())
                .build();
        try {
            server.start();
        } catch (Exception e) {
            LOGGER.fatal("启动HTTP服务失败： ", e);
            System.exit(39);
        }
        STATE = true;
        LOGGER.info("Http服务已启动，API链接： http://" + config.getString("http-services.host") + ":" + config.getInt("http-services.port") + "/services/");
        if (ServerMain.isPanelEnabled) {
            LOGGER.info("后台管理服务已启动，可用以下链接访问： http://" + config.getString("http-services.host") + ":" + config.getInt("http-services.port"));
        }
    }
}
