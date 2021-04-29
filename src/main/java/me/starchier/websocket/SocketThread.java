package me.starchier.websocket;

import me.starchier.ServerMain;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SocketThread extends Thread{
    private Logger logger = LogManager.getLogger(this.getName());
    public static boolean STATE = false;
    @Override
    public void run() {
        ServerMain.socketServer.start();
        logger.info("WebSocket服务端初始化完成。");
        logger.info("服务器现正监听端口： " + ServerMain.socketServer.getPort() + "  (" + ServerMain.socketServer.getAddress() + ")");
    }
}
