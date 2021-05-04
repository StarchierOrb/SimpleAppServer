package me.starchier.http.callback;

import io.undertow.io.IoCallback;
import io.undertow.io.Sender;
import io.undertow.server.HttpServerExchange;

import java.io.IOException;

public class FileTransferCall implements IoCallback {
    @Override
    public void onComplete(HttpServerExchange exchange, Sender sender) {
        exchange.endExchange();
    }

    @Override
    public void onException(HttpServerExchange exchange, Sender sender, IOException exception) {

    }
}
