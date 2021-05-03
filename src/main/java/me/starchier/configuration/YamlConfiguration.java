package me.starchier.configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.simpleyaml.configuration.file.YamlFile;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import java.io.File;
import java.io.IOException;

public class YamlConfiguration {
    private static YamlFile config;
    private static final Logger logger = LogManager.getLogger(YamlConfiguration.class.getName());
    public static void loadConfig() {
        config = new YamlFile(System.getProperty("user.dir") + File.separator + "config.yml");
        try {
            config.loadWithComments();
        } catch (InvalidConfigurationException e) {
            logger.warn("加载配置文件时出错（无效配置文件）: ", e);
        } catch (IOException e) {
            logger.warn("加载配置文件时出错: ", e);
        }
    }
    public static YamlFile getConfig() {
        if(config == null) {
            loadConfig();
        }
        return config;
    }
}
