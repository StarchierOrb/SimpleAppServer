package me.starchier.http;

import me.starchier.json.JsonTextReader;

import java.util.ArrayList;
import java.util.List;

public class WebPagesManager {
    private static List<PageHandler> pages = new ArrayList<>();
    public static void registerPage(String filename) {
        pages.add(new PageHandler());
    }
}
