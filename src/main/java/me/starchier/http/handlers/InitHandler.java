package me.starchier.http.handlers;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import io.undertow.util.Methods;

public class InitHandler implements HttpHandler {
    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        if (exchange.getRequestMethod().equals(Methods.GET)) {
            String[] urlArgs;
            String url = exchange.getRequestURI();
            if (url.equals("/")) {
                new MainHandler().handleRequest(exchange);
            } else {
                urlArgs = exchange.getRequestURI().replaceFirst("/", "").split("/");
                switch (urlArgs[0]) {
                    default: {
                        new MainHandler().handleRequest(exchange);
                        return;
                    }
                    case "login": {
                        new LoginHandler().handleRequest(exchange);
                        return;
                    }
                    case "static": {
                        new FileHandler().handleRequest(exchange);
                    }
                }
            }
        } else if(exchange.getRequestMethod().equals(Methods.POST)) {
            exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/json");
            exchange.getResponseSender().send(exchange.getQueryString());
        }
    }
}
