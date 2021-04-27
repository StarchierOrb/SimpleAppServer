package me.starchier.command;

import me.starchier.api.command.CommandExecutor;
import me.starchier.command.core.CommandRegister;
import org.jline.reader.impl.completer.ArgumentCompleter;
import org.jline.reader.impl.completer.StringsCompleter;

import java.util.Arrays;

public class CommandRegistry {
    public void register() {
        final CommandExecutor[] executors = {
                new Version("version"),
                new HelpCommand("help"),
                new CommandExecutor("print") {
                    @Override
                    public boolean execute(String[] args) {
                        StringBuilder sb = new StringBuilder();
                        for(String s : args) {
                            sb.append(s);
                            sb.append(" ");
                        }
                        getLogger().info(sb.toString());
                        return true;
                    }
                }
        };
        CommandRegister.register(Arrays.asList(executors));
    }
}
