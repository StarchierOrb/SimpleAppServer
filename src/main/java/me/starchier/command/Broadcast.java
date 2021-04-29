package me.starchier.command;

import me.starchier.ServerMain;
import me.starchier.api.command.CommandExecutor;

public class Broadcast extends CommandExecutor {
    public Broadcast(String cmd) {
        super(cmd);
    }
    @Override
    public boolean execute(String[] args) {
        StringBuilder sb = new StringBuilder();
        for(int i = 1; i < args.length; i++) {
            sb.append(args[i]);
            sb.append(" ");
        }
        String str = sb.toString();
        ServerMain.socketServer.broadcast(str);
        getLogger().info("已广播信息： " + str);
        return true;
    }

    @Override
    public String description() {
        return "向所有客户端广播消息";
    }
}
