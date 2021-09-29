package me.starchier.http.handlers;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import io.undertow.util.StatusCodes;
import me.starchier.http.WebPagesManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CMSHandler implements HttpHandler {
    private final Logger LOGGER = LogManager.getLogger(this.getClass().getName());
    @Override
    public void handleRequest(HttpServerExchange exchange) {
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");
        /*
        InputStream inputStream = ServerMain.class.getResourceAsStream("/panel/model.html");
        byte[] byteArr = new byte[inputStream.available()];
        inputStream.read(byteArr);
        exchange.getResponseSender().send(new String(byteArr));
         */
        String pageText = "null";
        String url = exchange.getRequestURI().replaceFirst("/cms/", "");
        try {
            if(url.equalsIgnoreCase("") || url.replace("/", "").split("\\.")[0].equalsIgnoreCase("index")) {
                if(!url.replace("index", "").replace("/", "").equalsIgnoreCase("")) {
                    exchange.setRequestURI("/");
                }
                pageText = WebPagesManager.getPage("index.json").getHtml();
            } else {
                pageText = WebPagesManager.getPage(url).getHtml();
            }
        } catch (Exception e) {
            LOGGER.error("读取JSON资源数据失败(3)： ", e);
            LOGGER.error("即将重定向用户请求至内部错误500页面。");
            pageText = WebPagesManager.getErrorPage(StatusCodes.INTERNAL_SERVER_ERROR).getHtml();
        }
        exchange.getResponseSender().send(pageText);
    }
}
