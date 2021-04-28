package me.starchier.command;

import me.starchier.ServerMain;
import me.starchier.api.command.CommandExecutor;
import me.starchier.util.VersionCheck;

public class Version extends CommandExecutor {
    public Version(String cmd) {
        super(cmd);
    }
    @Override
    public boolean execute(String[] args) {
        getLogger().info("当前正在运行服务端 " + ServerMain.NAME + " 版本： " + ServerMain.VERSION + " (" + VersionCheck.check() + ")" +
                "  构建版本： " + VersionCheck.getBuildNumber());
        return true;
    }
    @Override
    public String description() {
        return "查询服务端版本信息";
    }
}
