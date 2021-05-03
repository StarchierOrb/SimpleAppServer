package me.starchier.util;

import me.starchier.ServerMain;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;

public class FileDrop {
    private static Logger logger = LogManager.getLogger(FileDrop.class.getName());
    public static void dropConfig(String resourceName) {
        File config = new File(System.getProperty("user.dir") + File.separator + resourceName);
        InputStream inputStream = ServerMain.class.getResourceAsStream("/" + resourceName);
        try {
            config.createNewFile();
            OutputStream outputStream = new FileOutputStream(config);
            int index = 0;
            byte[] bytes = new byte[1024];
            while ((index = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, index);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();
            logger.info("已成功创建文件： " + resourceName);
        } catch (IOException e) {
            logger.fatal("创建文件出错： ", e);
            logger.fatal("由于严重错误，服务器将会关闭。");
            System.exit(20);
        }
    }
    public static void dropPanelFiles(InputStream inputStream, String resourceName) {
        File config = new File(System.getProperty("user.dir") + File.separator + "panel" + File.separator + resourceName);
        try {
            config.createNewFile();
            OutputStream outputStream = new FileOutputStream(config);
            int index = 0;
            byte[] bytes = new byte[1024];
            while ((index = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, index);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();
            logger.trace("已成功创建资源文件： " + resourceName);
        } catch (IOException e) {
            logger.fatal("创建资源文件出错： ", e);
            logger.fatal("由于严重错误，服务器将会关闭。");
            System.exit(20);
        }
    }
}
