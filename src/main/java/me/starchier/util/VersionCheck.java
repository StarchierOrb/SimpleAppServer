package me.starchier.util;

import me.starchier.ServerMain;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VersionCheck {
    private static final Logger logger = LogManager.getLogger(VersionCheck.class);
    public static String check() {
        String[] version = ServerMain.VERSION.split("-");
        switch (version[1]) {
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
            default: {
                return "未知版本";
            }
        }
    }
}
