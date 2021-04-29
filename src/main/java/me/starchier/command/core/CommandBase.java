package me.starchier.command.core;

import me.starchier.api.command.CommandExecutor;

import java.util.List;

public class CommandBase {
    //Search for a command to execute.
    public static boolean execute(String cmd) {
        List<CommandExecutor> commandExecutors = CommandRegister.getCommands();
        for(CommandExecutor commandExecutor : commandExecutors) {
            if(cmd.split(" ")[0].equalsIgnoreCase(commandExecutor.getCommand())) {
                commandExecutor.execute(cmd.split(" "));
                return true;
            }
        }
        return false;
    }
}
