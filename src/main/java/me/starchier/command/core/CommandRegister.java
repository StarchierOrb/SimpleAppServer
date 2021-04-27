package me.starchier.command.core;

import me.starchier.api.command.CommandExecutor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jline.reader.Completer;
import org.jline.reader.impl.completer.AggregateCompleter;
import org.jline.reader.impl.completer.StringsCompleter;

import java.util.ArrayList;
import java.util.List;

public class CommandRegister {
    private static Logger logger = LogManager.getLogger(CommandRegister.class);
    private static List<CommandExecutor> commandExecutors = new ArrayList<>();
    private static List<Completer> completers = new ArrayList<>();
    public static void register(CommandExecutor commandExecutor) {
        commandExecutors.add(commandExecutor);
        commandExecutor.onInit();
        logger.debug("成功注册指令 " + commandExecutor.getCommand());
    }
    public static List<CommandExecutor> getCommands() {
        return commandExecutors;
    }
    public static void register(List<CommandExecutor> commandExecutors) {
        for(CommandExecutor commandExecutor : commandExecutors) {
            CommandRegister.register(commandExecutor);
        }
    }
    //设置命令补全
    public static void setCompleter(Completer completer) {
        completers.add(completer);
    }
    public static AggregateCompleter getCompleters() {
        Completer[] completers = new Completer[CommandRegister.completers.size()];
        return new AggregateCompleter(CommandRegister.completers.toArray(completers));
    }
    public static StringsCompleter initCompleter() {
        int size = CommandRegister.getCommands().size();
        String[] cmds = new String[size];
        for(int i = 0; i < size; i++) {
            cmds[i] = CommandRegister.getCommands().get(i).getCommand();
        }
        return new StringsCompleter(cmds);
    }
}
