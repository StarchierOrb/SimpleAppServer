package me.starchier.http.handlers;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import me.starchier.ServerMain;

import java.io.InputStream;

public class MainHandler implements HttpHandler {
    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");
        InputStream inputStream = ServerMain.class.getResourceAsStream("/panel/model.html");
        byte[] byteArr = new byte[inputStream.available()];
        inputStream.read(byteArr);
        exchange.getResponseSender().send(new String(byteArr));
    }
}
