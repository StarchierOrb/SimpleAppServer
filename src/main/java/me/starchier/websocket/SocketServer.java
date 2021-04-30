package me.starchier.websocket;

import me.starchier.json.JsonStatic;
import me.starchier.json.objects.MessageBack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.java_websocket.WebSocket;
import org.java_websocket.framing.CloseFrame;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;

public class SocketServer extends WebSocketServer {
    private final Logger logger = LogManager.getLogger(this.getClass());
    public SocketServer(int port) {
        super(new InetSocketAddress("0.0.0.0", port));
    }
    public SocketServer(String host, int port) {
        super(new InetSocketAddress(host, port));
    }
    @Override
    public void onStart() {
        logger.info("WebSocket服务端现已启动。");
        SocketThread.STATE = true;
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        logger.info("用户 " + " 已断开连接： " + reason + " (代码 " + code + ")");
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        logger.trace("接收到客户端 " + getAddressWithPort(conn) + " 发送数据。");
        logger.trace("数据内容：" + message);
        if(message.contains("\"actionType\": ")) {

        } else {
            MessageBack msg = new MessageBack(0, "数据包格式错误，即将中断连接。");
            conn.send(JsonStatic.gson.toJson(msg));
            conn.close(CloseFrame.UNEXPECTED_CONDITION, "数据包格式错误");
        }
        conn.send("received.");
        //TODO Handling Message
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        logger.warn("在处理客户端连接时发生错误： ", ex);
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        logger.info("用户 " + getAddressWithPort(conn) + " 已建立连接。");
    }
    public String getAddressWithPort(WebSocket conn) {
        if(conn == null) {
            return null;
        }
        InetSocketAddress info = conn.getRemoteSocketAddress();
        return info.getAddress().getHostName() + ":" + info.getPort();
    }
}
