package me.starchier.command;

import me.starchier.ServerMain;
import me.starchier.api.command.CommandExecutor;
import me.starchier.command.core.CommandRegister;

public class HelpCommand extends CommandExecutor {
    final String help = ServerMain.NAME + "  指令帮助菜单";
    public HelpCommand(String cmd) {
        super(cmd);
    }
    @Override
    public boolean execute(String[] args) {
        getLogger().info(help);
        getLogger().info("stop  -  关闭服务端");
        for(CommandExecutor executor : CommandRegister.getCommands()) {
            getLogger().info(executor.getCommand() + "  -  " + executor.description());
        }
        return true;
    }

    @Override
    public String description() {
        return "查询服务端指令帮助";
    }
}
