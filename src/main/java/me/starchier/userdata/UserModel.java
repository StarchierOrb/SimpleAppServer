package me.starchier.userdata;

import me.starchier.util.MD5Util;

import java.util.UUID;

public class UserModel {
    private final int type;
    private final UUID uuid;
    private final UserInformation userInformation;
    private final String userName;
    private final String password;

    public UserModel(int type, UserInformation userInformation, String userName, String password) {
        this.type = type;
        this.userInformation = userInformation;
        this.uuid = UUID.randomUUID();
        this.userName = userName;
        this.password = MD5Util.md5(password);
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public int getType() {
        return type;
    }

    public UUID getUuid() {
        return uuid;
    }

    public UserInformation getUserInformation() {
        return userInformation;
    }
}
