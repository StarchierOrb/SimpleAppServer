package me.starchier.command;

import me.starchier.ServerMain;
import me.starchier.api.command.CommandExecutor;
import org.java_websocket.WebSocket;

public class DataSender extends CommandExecutor {
    public DataSender(String cmd) {
        super(cmd);
    }

    @Override
    public boolean execute(String[] args) {
        //TODO With user data.
        for(WebSocket client : ServerMain.socketServer.getConnections()) {
            getLogger().info(client.getResourceDescriptor());
        }
        return true;
    }

    @Override
    public String description() {
        return "向指定客户端发送信息";
    }
}
