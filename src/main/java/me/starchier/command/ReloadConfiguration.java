package me.starchier.command;

import me.starchier.api.command.CommandExecutor;
import me.starchier.configuration.YamlConfiguration;

public class ReloadConfiguration extends CommandExecutor {
    public ReloadConfiguration(String cmd) {
        super(cmd);
    }

    @Override
    public boolean execute(String[] args) {
        getLogger().info("正在重新加载配置文件...");
        YamlConfiguration.loadConfig();
        getLogger().info("配置文件重新加载完毕。");
        getLogger().info("注意：部分设置需要重新启动服务端才能生效。");
        return true;
    }

    @Override
    public String description() {
        return "重新加载配置文件";
    }
}
