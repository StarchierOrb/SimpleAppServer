package me.starchier.util;

import me.starchier.ServerMain;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VersionCheck {
    private static final Logger logger = LogManager.getLogger(VersionCheck.class);
    public static String check() {
        String[] version = ServerMain.VERSION.split("-");
        try {
            switch (version[2]) {
                case "SNAPSHOT": {
                    if(version[0].contains("1.0")) {
                        logger.warn("当前服务端处于初期开发阶段，仅供测试。");
                        return "早期版本";
                    } else {
                        logger.warn("当前服务端版本处于开发版本，可能存在问题，请谨慎使用。");
                        return "开发版";
                    }
                }
                case "release": {
                    return "稳定版";
                }
                case "alpha": {
                    logger.warn("Alpha版本是新功能的初期测试版本，可能存在严重问题。");
                    return "Alpha版";
                }
                case "beta": {
                    return "测试版";
                }
                default: {
                    logger.warn("未知的版本（非官方版本），当前版本可能是修改版？");
                    return "未知版本";
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            logger.warn("获取版本号时发生错误." + e);
            return "版本获取出错";
        }
    }
    public static String getBuildNumber() {
        String build = ServerMain.VERSION.split("-")[1];
        return build.replace("build", "");
    }
}
