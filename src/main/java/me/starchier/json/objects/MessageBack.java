package me.starchier.json.objects;

public class MessageBack {
    private int type;
    private String message;
    public MessageBack(int type, String message) {
        this.message = message;
        this.type = type;
    }
}
