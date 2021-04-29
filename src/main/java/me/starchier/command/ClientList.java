package me.starchier.command;

import me.starchier.ServerMain;
import me.starchier.api.command.CommandExecutor;
import org.java_websocket.WebSocket;

import java.util.Collection;

public class ClientList extends CommandExecutor {
    public ClientList(String cmd) {
        super(cmd);
    }

    @Override
    public boolean execute(String[] args) {
        Collection<WebSocket> webSockets = ServerMain.socketServer.getConnections();
        getLogger().info("在线客户端总数： " + webSockets.size());
        getLogger().info("当前在线客户端信息：");
        int i = 0;
        for(WebSocket webSocket : webSockets) {
            StringBuilder sb = new StringBuilder();
            sb.append(++i);
            sb.append("\tIP: ");
            sb.append(webSocket.getRemoteSocketAddress().getAddress());
            sb.append("(ID: ");
            sb.append(webSocket.hashCode());
            sb.append(")");
            getLogger().info(sb.toString());
        }
        return true;
    }

    @Override
    public String description() {
        return "获取所有在线的客户端信息";
    }
}
