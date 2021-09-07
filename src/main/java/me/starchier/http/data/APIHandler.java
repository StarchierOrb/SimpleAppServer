package me.starchier.http.data;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import io.undertow.io.Receiver;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import io.undertow.util.StatusCodes;
import me.starchier.api.ResponseCode;
import me.starchier.http.callback.RequestReceiver;
import me.starchier.json.objects.ActionObject;
import me.starchier.json.objects.SimpleBackObj;
import me.starchier.json.objects.LoginRequest;
import me.starchier.storage.LoginRequestHandler;
import me.starchier.util.TokenUtil;

import java.io.InputStream;

public class APIHandler implements HttpHandler {
    public static final Gson gson = new Gson();
    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "application/json");
        String[] urlArgs = exchange.getRequestURI().replaceFirst("/services/", "").split("/");
        if (urlArgs.length == 0) {
            exchange.getResponseSender().send(gson.toJson(new SimpleBackObj(ResponseCode.ARGUMENT_ERROR, "错误的请求目录")));
            return;
        }
        if (urlArgs[0].equals("login")) {
            if (exchange.getRequestHeaders().get(Headers.CONTENT_TYPE) == null) {
                exchange.getResponseSender().send(gson.toJson(new SimpleBackObj(ResponseCode.TYPE_ERROR, "请求头类型不正确")));
                return;
            }
            if (exchange.getRequestHeaders().get(Headers.CONTENT_TYPE).toString().contains("application/json")) {
                exchange.getRequestReceiver().receiveFullString(new RequestReceiver());
            }
        } else {
            exchange.setStatusCode(StatusCodes.NOT_IMPLEMENTED);
            exchange.getResponseSender().send(gson.toJson(new SimpleBackObj(ResponseCode.NOT_IMPLEMENTED, "尚未实现的功能")));
        }
    }
}
