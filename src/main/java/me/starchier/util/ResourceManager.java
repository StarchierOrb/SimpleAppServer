package me.starchier.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class ResourceManager {
    private static final Logger LOGGER = LogManager.getLogger(ResourceManager.class.getName());
    private static final File dirSDK = new File(System.getProperty("user.dir") + File.separator + "panel" + File.separator + "sdk");
    public static void downloadFile(String downloadUrl, File saveFile) throws IOException {
        URL url = new URL(downloadUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setConnectTimeout(8000);
        connection.setRequestMethod("GET");
        ReadableByteChannel readableByteChannel = Channels.newChannel(connection.getInputStream());
        FileOutputStream fileOutputStream = new FileOutputStream(saveFile);
        fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
        readableByteChannel.close();
        fileOutputStream.close();
        connection.disconnect();
    }
    public static void dropResources() {
        if(!dirSDK.exists() && !dirSDK.isDirectory()) {
            if(dirSDK.mkdirs()) {
                LOGGER.trace("成功创建目录： " + dirSDK.getAbsolutePath());
            } else {
                LOGGER.trace("未能创建目录： " + dirSDK.getAbsolutePath() + " 是否已存在： " + dirSDK.exists());
            }
        }
    }
}
