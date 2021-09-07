package me.starchier.command;

import me.starchier.api.command.CommandExecutor;
import me.starchier.storage.UserDataManager;

public class TestUsers extends CommandExecutor {
    public TestUsers(String cmd) {
        super(cmd);
    }

    @Override
    public boolean execute(String[] args) {
        UserDataManager.generateTestUser();
        return true;
    }

    @Override
    public String description() {
        return "生成测试用户";
    }
}
