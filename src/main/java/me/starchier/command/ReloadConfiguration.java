package me.starchier.command;

import me.starchier.ServerMain;
import me.starchier.api.command.CommandExecutor;

public class ReloadConfiguration extends CommandExecutor {
    public ReloadConfiguration(String cmd) {
        super(cmd);
    }

    @Override
    public boolean execute(String[] args) {
        getLogger().info("正在重新加载配置文件...");
        ServerMain.configFile.loadConfig();
        getLogger().info("新设置：" + ServerMain.configObject.getIP() + ":" + ServerMain.configObject.getPort());
        getLogger().info("部分设置需要重新启动服务端才能生效。");
        return true;
    }

    @Override
    public String description() {
        return "重新加载配置文件";
    }
}
