package me.starchier.http.handlers;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import io.undertow.util.HttpString;
import io.undertow.util.Methods;
import me.starchier.http.data.APIHandler;

public class InitHandler implements HttpHandler {
    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        if (exchange.getRequestMethod().equals(Methods.GET)) {
            exchange.getResponseHeaders().put(Headers.ACCEPT_ENCODING, "utf-8");
            exchange.getResponseHeaders().put(new HttpString("Access-Control-Allow-Origin"), "*");
            String[] urlArgs;
            String url = exchange.getRequestURI();
            if (url.equals("/")) {
                new CMSHandler().handleRequest(exchange);
            } else {
                urlArgs = exchange.getRequestURI().replaceFirst("/", "").split("/");
                switch (urlArgs[0]) {
                    case "cms": {
                        new CMSHandler().handleRequest(exchange);
                        return;
                    }
                    case "login": {
                        new LoginHandler().handleRequest(exchange);
                        return;
                    }
                    case "services": {
                        new APIHandler().handleRequest(exchange);
                        return;
                    }
                    case "static": {
                        new FileHandler().handleRequest(exchange);
                        return;
                    }
                    default: {
                        new StaticHandler().handleRequest(exchange);
                    }
                }
            }
        } else if(exchange.getRequestMethod().equals(Methods.POST)) {
            exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/json");
            exchange.getResponseSender().send(exchange.getQueryString());
        }
    }
}
