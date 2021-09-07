package me.starchier.json.objects;

public class ActionObject extends SimpleBackObj {
    private final String token;
    public ActionObject(int code, String message, String token) {
        super(code, message);
        this.token = token;
    }

}
