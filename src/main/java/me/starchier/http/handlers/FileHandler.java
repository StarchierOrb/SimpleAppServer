package me.starchier.http.handlers;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import me.starchier.ServerMain;
import me.starchier.http.callback.FileTransferCall;

import java.io.FileInputStream;

public class FileHandler implements HttpHandler {
    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        String[] url = exchange.getRequestURI().split("/");
        String filename = url[url.length - 1];
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = (FileInputStream) ServerMain.class.getResourceAsStream("/panel/sdk/" + filename);
        } catch (Exception e) {
            exchange.getResponseSender().send(e.getMessage());
            return;
        }
        exchange.getResponseSender().transferFrom(fileInputStream.getChannel(), new FileTransferCall());
    }
}
