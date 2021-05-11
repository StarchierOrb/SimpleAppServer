package me.starchier.http;

import me.starchier.json.JsonTextReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class PageHandler {
    private final Logger LOGGER = LogManager.getLogger(this.getClass().getName());
    private final String htmlText;
    private final JsonTextReader jsonTextReader;
    private final String resourceName;
    public PageHandler(JsonTextReader jsonTextReader) {
        this.resourceName = jsonTextReader.getResourceName();
        this.jsonTextReader = jsonTextReader;
        InputStream inputStream = this.getClass().getResourceAsStream("/panel/model.html");
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        } catch (Exception e) {
            LOGGER.error("无法读取资源文件： ", e);
        }
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            while((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            LOGGER.error("读取资源文件时发生错误：", e);
        }
        htmlText = sb.toString().replace("%%JSON_TO_BE_REPLACE%%", jsonTextReader.getJsonText());
    }
    public String getHtml() {
        return htmlText.replace("%%TITLE_TO_BE_REPLACED%%", jsonTextReader.getTitle() + UndertowServer.webTitle);
    }

    public String getResourceName() {
        return resourceName;
    }
}
