package me.starchier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jline.reader.EndOfFileException;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.UserInterruptException;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;

public class ServerMain {
    public static final String VERSION = "1.0.b21-SNAPSHOT";
    private static final Logger getLogger = LogManager.getLogger("Main");
    private static final String prompt = ">";
    public static void main(String[] args) {
        Terminal terminal;
        LineReader lineReader = null;
        getLogger.info("服务端已启动。");
        try {
            terminal = TerminalBuilder.builder().system(true).dumb(true).build();
            lineReader = LineReaderBuilder.builder().terminal(terminal).build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            String line;
            try {
                assert lineReader != null;
                line = lineReader.readLine(prompt);
                switch (line) {
                    case "version": {
                        getLogger.info(VERSION);
                        continue;
                    }
                    case "stop": {
                        getLogger.info("接收到停止命令。");
                        getLogger.info("正在停止服务器...");
                        return;
                    }
                    default: {
                        getLogger.info("未知的指令。");
                    }
                }
            } catch (UserInterruptException e) {
            } catch(EndOfFileException e) {
                getLogger.info("正在关闭服务器...");
            }
        }
    }
}
