package me.starchier.api.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommandExecutor {
    private final String[] cmds;
    private final int length;
    private final Logger logger = LogManager.getLogger(this.getClass());
    public CommandExecutor(String cmd) {
        cmds = cmd.split(" ");
        length = cmds.length;
    }
    public boolean execute(String[] args) {
        IllegalStateException ex = new IllegalStateException("未定义的指令");
        logger.warn("执行指令 " + cmds[0] + " 时发生错误：", ex);
        return false;
    }
    public String getCommand() {
        return cmds[0];
    }
    public Logger getLogger() {
        return logger;
    }
    public String description() {
        return "该指令没有提供相关帮助信息";
    }
    public void onInit() {}
}
