package me.starchier.http;

import io.undertow.util.StatusCodes;
import me.starchier.ServerMain;
import me.starchier.json.JsonTextReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class WebPagesManager {
    private static List<PageHandler> pages = new ArrayList<>();
    private static List<PageHandler> errorPages = new ArrayList<>();
    private static final Logger LOGGER = LogManager.getLogger(WebPagesManager.class.getName());
    private static final JsonTextReader[] list = {
            new JsonTextReader("index.json", "欢迎")
    };
    private static final JsonTextReader[] errorList = {
            new JsonTextReader("error/404.json", "页面不存在"),
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
    public static void registerErrorPage(JsonTextReader page) {
        errorPages.add(new PageHandler(page));
        LOGGER.debug("已注册并加载后台管理系统错误页面 " + page.getResourceName());
    }

    //This should be invoked on server start.
    public static void registerInitPages() {
        if (ServerMain.isPanelEnabled) {
            for(JsonTextReader jsonFile : list) {
                registerPage(jsonFile);
            }
        }
        for(JsonTextReader jsonFile : errorList) {
            registerErrorPage(jsonFile);
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
        String path = "error/" + code + ".json";
        for(PageHandler page : errorPages) {
            if(path.equalsIgnoreCase(page.getResourceName())) {
                return page;
            }
        }
        return null;
    }
}
