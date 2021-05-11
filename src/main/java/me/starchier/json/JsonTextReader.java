package me.starchier.json;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;

public class JsonTextReader {
    private final String jsonText;
    private final String title;
    private final String resourceName;
    private final Logger LOGGER = LogManager.getLogger(this.getClass().getName());
    public JsonTextReader(String resourceName, String title) {
        this.title = title;
        this.resourceName = resourceName;
        InputStream inputStream = this.getClass().getResourceAsStream("/webpages/" + resourceName);
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        } catch (NullPointerException e) {
            LOGGER.error("无法读取JSON资源文件： ", e);
            System.exit(20);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("无效编码： ", e);
            System.exit(21);
        }
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            while((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            LOGGER.error("读取JSON文件时发生错误：", e);
        }
        jsonText = sb.toString();
    }
    public String getJsonText() {
        return jsonText;
    }

    public String getTitle() {
        return title;
    }

    public String getResourceName() {
        return resourceName;
    }
}
