package me.starchier.http;

import io.undertow.Undertow;
import me.starchier.configuration.YamlConfiguration;
import me.starchier.http.handlers.InitHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.simpleyaml.configuration.file.YamlFile;

public class UndertowServer extends Thread {
    private final Logger LOGGER = LogManager.getLogger(this.getName());
    public static boolean STATE = false;
    public static Undertow server;
    @Override
    public void run() {
        YamlFile config = YamlConfiguration.getConfig();
        if(config.getBoolean("manage-panel.enabled", true)) {
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
        }
    }
}
