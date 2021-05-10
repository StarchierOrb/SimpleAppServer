package me.starchier.http;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class PageHandler {
    private final Logger LOGGER = LogManager.getLogger(this.getClass().getName());
    private final String htmlText;
    public PageHandler(String title, String text) {
        InputStream inputStream = this.getClass().getResourceAsStream("/panel/model.html");
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        } catch (NullPointerException e) {
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
        htmlText = sb.toString().replace("%%TITLE_TO_BE_REPLACED%%", title).replace("%%JSON_TO_BE_REPLACE%%", text);
    }
    public String getHtml() {
        return htmlText;
    }
}
