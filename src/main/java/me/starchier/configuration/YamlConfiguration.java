package me.starchier.configuration;

import me.starchier.ServerMain;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class YamlConfiguration {
    private String configPath;
    private ConfigObject configObject = null;
    private final Yaml yaml = new Yaml();
    public YamlConfiguration() {

    }
    public YamlConfiguration(String configPath) {
        this.configPath = configPath;
    }
    public void loadConfig() {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(ServerMain.config);
        } catch (FileNotFoundException ignored) {
        }
        configObject = yaml.load(fileInputStream);
    }
    public ConfigObject getConfig() {
        //TODO Need to fix
        return configObject;
    }
}
