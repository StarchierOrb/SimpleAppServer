package me.starchier.http.handlers;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.StatusCodes;
import me.starchier.http.callback.FileTransferCall;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;

public class FileHandler implements HttpHandler {
    private final String path = System.getProperty("user.dir") + File.separator + "panel" + File.separator + "sdk" + File.separator;
    private final Logger LOGGER = LogManager.getLogger(this.getClass().getName());
    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        if(exchange.getRequestURI().contains("/static/panel/sdk/")) {
            try {
                String filePath = exchange.getRequestURI().replace("/static/panel/sdk/", "").replace("/", File.separator);
                FileInputStream fileInputStream = null;
                try {
                    fileInputStream = new FileInputStream(path + filePath);
                } catch (Exception e) {
                    LOGGER.trace("试图传送文件时出错(0)，客户端IP： " + exchange.getHostAndPort());
                    LOGGER.trace("   客户端请求路径：" + exchange.getRequestURI());
                    LOGGER.trace("   错误信息： " , e);
                    exchange.setStatusCode(StatusCodes.NOT_FOUND);
                    exchange.getResponseSender().send("<html><head><title>啊吧</title></head><body>404 NOT FOUND</body></html>");
                    return;
                }
                exchange.getResponseSender().transferFrom(fileInputStream.getChannel(), new FileTransferCall());
            } catch (Exception e) {
                LOGGER.trace("试图传送文件时出错(1)，客户端IP： " + exchange.getConnection().getPeerAddress());
                LOGGER.trace("   客户端请求路径：" + exchange.getRequestURI());
                LOGGER.trace("   错误信息： " , e);
                exchange.setStatusCode(StatusCodes.INTERNAL_SERVER_ERROR);
                exchange.getResponseSender().send("<html><head><title>啊吧</title></head><body>500 INTERNAL SERVER ERROR<br>See debug log pls.</body></html>");
            }
        } else {
            exchange.setStatusCode(StatusCodes.FORBIDDEN);
            exchange.getResponseSender().send("<html><body>403 FORBIDDEN</body></html>");
        }
    }
}
