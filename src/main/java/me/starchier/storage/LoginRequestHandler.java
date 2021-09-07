package me.starchier.storage;

import me.starchier.ServerMain;
import me.starchier.json.objects.LoginRequest;
import me.starchier.userdata.UserModel;
import me.starchier.util.MD5Util;

public class LoginRequestHandler {
    public static boolean verifyLogin(LoginRequest req) {
        if (ServerMain.storageType.equalsIgnoreCase("flat")) {
            for (UserModel user : UserDataManager.getUsers()) {
                if (user.getUserName().equals(req.getUsername())) {
                    if (user.getPassword().equals(MD5Util.md5(req.getPassword()))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
