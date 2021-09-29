package me.starchier.storage;

import me.starchier.ServerMain;
import me.starchier.http.data.APIHandler;
import me.starchier.userdata.UserInformation;
import me.starchier.userdata.UserModel;
import me.starchier.userdata.UserType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class UserDataManager {
    private static final Logger logger = LogManager.getLogger(UserDataManager.class.getName());
    private static List<UserModel> users = new ArrayList<>();
    private static final File userDir = new File(ServerMain.ROOT_PATH + "users");

    public static void initData() {
        logger.info("正在载入用户数据...");
        if (!userDir.exists()) {
            userDir.mkdirs();
        }
        String[] fileList = userDir.list();
        if (fileList == null) {
            logger.info("没有可以载入的用户数据。");
            return;
        }
        int i = 0;
        for (String s : fileList) {
            try {
                File file = new File(userDir + File.separator + s);
                InputStream inputStream = new FileInputStream(file);
                BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                StringBuilder sb = new StringBuilder();
                String line = in.readLine();
                while (line != null) {
                    sb.append(line);
                    line = in.readLine();
                }
                in.close();
                users.add(APIHandler.gson.fromJson(sb.toString(), UserModel.class));
                i++;
            } catch (IOException e) {
                logger.warn("加载用户数据 " + s + " 出错。", e);
            }
        }
        logger.info("已加载 " + i + " 条用户数据，共有 " + fileList.length + " 条数据。");
    }

    public static void saveUser(UserModel user) {
        try {
            File file = new File(ServerMain.ROOT_PATH + "users" + File.separator + user.getUuid() + ".json");
            file.createNewFile();
            try (
                    FileWriter writer = new FileWriter(file);
                    BufferedWriter out = new BufferedWriter(writer)
            ) {
                out.write(APIHandler.gson.toJson(user));
            }
        } catch (IOException e) {
            logger.warn("保存用户数据失败 ", e);
        }
    }

    public static void generateTestUser() {
        UserModel user = new UserModel(UserType.ADMIN, new UserInformation(), "lzj", "123");
        UserModel user2 = new UserModel(UserType.ADMIN, new UserInformation(), "sjh", "666");
        saveUser(user);
        saveUser(user2);
    }

    public static List<UserModel> getUsers() {
        return users;
    }
}
