package me.starchier;

import me.starchier.command.CommandRegistry;
import me.starchier.command.core.CommandBase;
import me.starchier.command.core.CommandRegister;
import me.starchier.util.VersionCheck;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jline.reader.EndOfFileException;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.UserInterruptException;
import org.jline.reader.impl.completer.StringsCompleter;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;
import java.math.BigDecimal;

public class ServerMain {
    public static final String VERSION = "1.0.48-DEV-SNAPSHOT";
    public static final String NAME = "SmartApp-Server-TEST";
    private static final Logger getLogger = LogManager.getLogger("ServerMain");
    private static final String prompt = ">";
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        getLogger.info("正在启动服务端，请稍等...");
        getLogger.info("当前正在运行 " + NAME + " 版本： " + VERSION + " (" + VersionCheck.check() + ")" +
                "  构建版本： " + VersionCheck.getBuildNumber());
        Terminal terminal;
        LineReader lineReader = null;
        //注册指令
        getLogger.info("正在注册指令...");
        new CommandRegistry().register();
        try {
            terminal = TerminalBuilder.builder().system(true).dumb(true).build();
            //TODO 命令自动补全有毛病，以后有空再修
            lineReader = LineReaderBuilder.builder().terminal(terminal).completer(CommandRegister.initCompleter())
                    .completer(CommandRegister.getCompleters()).build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        long finishTime = System.currentTimeMillis();
        getLogger.info("服务端启动完毕， 用时 " + new BigDecimal(finishTime - startTime).divide(new BigDecimal(1000)).doubleValue() + " 秒。");
        getLogger.info("要获取指令帮助，请输入 help 查看。");
        while (true) {
            String line;
            try {
                assert lineReader != null;
                line = lineReader.readLine(prompt);
                switch (line) {
                    case "stop": {
                        getLogger.info("接收到停止命令。");
                        getLogger.info("正在停止服务器...");
                        return;
                    }
                    default: {
                        if(!CommandBase.execute(line)) {
                            getLogger.info("未知的指令，输入 help 来查看指令帮助。");
                        }
                    }
                }
            } catch (UserInterruptException e) {
            } catch(EndOfFileException e) {
                getLogger.info("正在关闭服务器...");
            }
        }
    }
}
