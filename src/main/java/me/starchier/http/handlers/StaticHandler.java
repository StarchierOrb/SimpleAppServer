package me.starchier.http.handlers;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.StatusCodes;
import me.starchier.ServerMain;
import me.starchier.http.WebPagesManager;
import me.starchier.http.callback.FileTransferCall;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;

public class StaticHandler implements HttpHandler {
    private final Logger LOGGER = LogManager.getLogger(StaticHandler.class.getName());
    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        try {
            String filePath = exchange.getRequestURI().replaceFirst("/", "").replace("/", File.separator);
            FileInputStream fileInputStream = null;
            try {
                fileInputStream = new FileInputStream(ServerMain.ROOT_PATH + "webroot" + File.separator + filePath);
            } catch (Exception e) {
                LOGGER.trace("试图传送资源时出错(0)，客户端IP： " + exchange.getHostAndPort());
                LOGGER.trace("   客户端请求路径：" + exchange.getRequestURI());
                LOGGER.trace("   错误信息： " , e);
                exchange.setStatusCode(StatusCodes.NOT_FOUND);
                exchange.getResponseSender().send(WebPagesManager.getErrorPage(StatusCodes.NOT_FOUND).getHtml());
                return;
            }
            exchange.getResponseSender().transferFrom(fileInputStream.getChannel(), new FileTransferCall());
        } catch (Exception e) {
            LOGGER.trace("试图传送资源时出错(1)，客户端IP： " + exchange.getConnection().getPeerAddress());
            LOGGER.trace("   客户端请求路径：" + exchange.getRequestURI());
            LOGGER.trace("   错误信息： " , e);
            exchange.setStatusCode(StatusCodes.FORBIDDEN);
            exchange.getResponseSender().send(WebPagesManager.getErrorPage(StatusCodes.NOT_FOUND).getHtml());
        }
    }
}
