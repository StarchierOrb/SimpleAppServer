package me.starchier.http.callback;

import com.google.gson.JsonSyntaxException;
import io.undertow.io.Receiver;
import io.undertow.server.HttpServerExchange;
import me.starchier.api.ResponseCode;
import me.starchier.http.data.APIHandler;
import me.starchier.json.objects.ActionObject;
import me.starchier.json.objects.LoginRequest;
import me.starchier.json.objects.SimpleBackObj;
import me.starchier.storage.LoginRequestHandler;
import me.starchier.util.TokenUtil;

public class RequestReceiver implements Receiver.FullStringCallback {
    @Override
    public void handle(HttpServerExchange exchange, String message) {
        try {
            LoginRequest login = APIHandler.gson.fromJson(message, LoginRequest.class);
            if (login.getUsername() == null || login.getPassword() == null) {
                exchange.getResponseSender().send(APIHandler.gson.toJson(new SimpleBackObj(ResponseCode.JSON_SYNTAX_ERROR, "登录失败：JSON结构无法解析")));
                return;
            }
            if (LoginRequestHandler.verifyLogin(login)) {
                exchange.getResponseSender().send(APIHandler.gson.toJson(new ActionObject(ResponseCode.AUTH_SUCCESS, "登录成功！", TokenUtil.generateToken(login.getUsername(), login.getPassword()))));
            } else {
                exchange.getResponseSender().send(APIHandler.gson.toJson(new SimpleBackObj(ResponseCode.AUTH_ERROR, "登录失败！ 帐号或密码认证失败")));

            }
        } catch (JsonSyntaxException e) {
            exchange.getResponseSender().send(APIHandler.gson.toJson(new SimpleBackObj(ResponseCode.JSON_SYNTAX_ERROR, "登录失败：JSON结构无法解析")));
        }
    }
}
