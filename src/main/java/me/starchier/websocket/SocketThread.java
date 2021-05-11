package me.starchier.websocket;

import me.starchier.ServerMain;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SocketThread extends Thread{
    private final Logger LOGGER = LogManager.getLogger(this.getName());
    public static boolean STATE = false;
    @Override
    public void run() {
        try {
            ServerMain.socketServer.start();
        } catch (Exception e) {
            LOGGER.fatal("启动WebSocket服务端失败： ", e);
            System.exit(40);
        }
        LOGGER.info("WebSocket服务端初始化完成。");
        LOGGER.info("服务器现正监听端口： " + ServerMain.socketServer.getPort() + "  (" + ServerMain.socketServer.getAddress() + ")");
    }
}
