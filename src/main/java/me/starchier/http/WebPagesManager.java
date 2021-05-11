package me.starchier.http;

import io.undertow.util.StatusCodes;
import me.starchier.json.JsonTextReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class WebPagesManager {
    private static List<PageHandler> pages = new ArrayList<>();
    private static final Logger LOGGER = LogManager.getLogger(WebPagesManager.class.getName());
    private static final JsonTextReader[] list = {
            new JsonTextReader("login.json", "登录"),
            new JsonTextReader("error/404.json", "不存在的页面"),
            new JsonTextReader("error/500.json", "发生内部错误")
    };
    public static void registerPage(String filename, String title) {
        pages.add(new PageHandler(new JsonTextReader(filename, title)));
        LOGGER.debug("已注册并加载后台管理系统页面 " + filename);
    }
    public static void registerPage(JsonTextReader page) {
        pages.add(new PageHandler(page));
        LOGGER.debug("已注册并加载后台管理系统页面 " + page.getResourceName());
    }
    //This should be invoked on server start.
    public static void registerInitPages() {
        for(JsonTextReader jsonFile : list) {
            registerPage(jsonFile);
        }
    }
    public static PageHandler getPage(String resourceName) {
        for(PageHandler page : pages) {
            if(resourceName.equalsIgnoreCase(page.getResourceName())) {
                return page;
            }
        }
        return getErrorPage(StatusCodes.NOT_FOUND);
    }
    public static PageHandler getErrorPage(int code) {
        return getPage("error/" + code + ".json");
    }
}
