package me.starchier.command;

import me.starchier.api.command.CommandExecutor;
import org.java_websocket.server.WebSocketServer;

public class Status extends CommandExecutor {
    public Status(String cmd) {
        super(cmd);
    }

    @Override
    public boolean execute(String[] args) {
        getLogger().info("当前激活的WebSocketWorker: " + WebSocketServer.WebSocketWorker.activeCount());
        getLogger().info("线程是否中断:" + WebSocketServer.WebSocketWorker.interrupted());
        return true;
    }

    @Override
    public String description() {
        return "查看当前WebSocket服务状态";
    }
}
