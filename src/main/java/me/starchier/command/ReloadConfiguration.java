package me.starchier.command;

import me.starchier.ServerMain;
import me.starchier.api.command.CommandExecutor;
import me.starchier.configuration.YamlConfiguration;
import me.starchier.http.UndertowServer;
import me.starchier.storage.UserDataManager;

public class ReloadConfiguration extends CommandExecutor {
    public ReloadConfiguration(String cmd) {
        super(cmd);
    }

    @Override
    public boolean execute(String[] args) {
        getLogger().info("正在重新加载配置文件...");
        YamlConfiguration.loadConfig();
        ServerMain.storageType = YamlConfiguration.getConfig().getString("storage-type", "flat");
        UserDataManager.initData();
        UndertowServer.webTitle = " - " + YamlConfiguration.getConfig().getString("manage-panel.web-title", "后台管理系统");
        getLogger().info("配置文件重新加载完毕。");
        getLogger().info("注意：部分设置需要重新启动服务端才能生效。");
        return true;
    }

    @Override
    public String description() {
        return "重新加载配置文件";
    }
}
