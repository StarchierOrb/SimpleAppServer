package me.starchier;

import me.starchier.command.CommandRegistry;
import me.starchier.command.core.CommandBase;
import me.starchier.command.core.CommandRegister;
import me.starchier.configuration.ConfigObject;
import me.starchier.configuration.YamlConfiguration;
import me.starchier.util.VersionCheck;
import me.starchier.websocket.SocketServer;
import me.starchier.websocket.SocketThread;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.java_websocket.WebSocket;
import org.java_websocket.framing.CloseFrame;
import org.jline.reader.EndOfFileException;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.UserInterruptException;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.*;
import java.math.BigDecimal;

public class ServerMain {
    public static final String VERSION = "1.0.93-DEV-SNAPSHOT";
    public static final String NAME = "SmartApp-Server";
    public static ConfigObject configObject;
    private static final Logger getLogger = LogManager.getLogger("ServerMain");
    private static final String prompt = ">";
    public static final File config = new File(System.getProperty("user.dir") + File.separator + "config.yml");
    public static SocketServer socketServer;
    public static YamlConfiguration configFile;
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
        //初始化并加载配置文件
        if(!config.exists()) {
            getLogger.warn("根目录下不存在服务端配置文件，正在尝试创建。");
            InputStream inputStream = ServerMain.class.getResourceAsStream("/config.yml");
            try {
                config.createNewFile();
                OutputStream outputStream = new FileOutputStream(config);
                int index = 0;
                byte[] bytes = new byte[1024];
                while ((index = inputStream.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, index);
                }
                outputStream.flush();
                outputStream.close();
                inputStream.close();
                getLogger.info("配置文件已成功创建。");
            } catch (IOException e) {
                getLogger.fatal("创建配置文件出错： ", e);
            }
        }
        getLogger.info("正在加载配置文件...");
        try {
            configFile = new YamlConfiguration();
            configFile.loadConfig();
            configObject = configFile.getConfig();
        } catch (Exception e) {
            getLogger.fatal("加载配置文件时发生错误： ", e);
            getLogger.fatal("服务端将会关闭，请检查配置文件。");
            return;
        }
        //启动WebSocket
        getLogger.info("正在启动WebSocket服务端...");
        socketServer = new SocketServer(configObject.getIP(), configObject.getPort());
        socketServer.setConnectionLostTimeout(15);
        new SocketThread().start();
        while (!SocketThread.STATE) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                getLogger.error("启动服务端时发生错误: ", e);
            }
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
                        getLogger.info("正在停止服务器...");
                        stop();
                        getLogger.info("感谢使用.");
                        return;
                    }
                    default: {
                        try {
                            if(!CommandBase.execute(line)) {
                                getLogger.info("未知的指令，输入 help 来查看指令帮助。");
                            }
                        } catch (ArrayIndexOutOfBoundsException e) {
                        } catch (Exception e) {
                            getLogger.error("发生错误：", e);
                        }
                    }
                }
            } catch (UserInterruptException e) {
                getLogger.warn("用户强制关闭服务器，正在关闭...");
                stop();
            } catch(EndOfFileException e) {
                getLogger.info("正在关闭服务器...");
                stop();
            }
        }
    }
    public static void stop() {
        getLogger.info("正在断开所有与客户端的连接...");
        for(WebSocket client : socketServer.getConnections()) {
            getLogger.info("正在断开客户端 " + client.getRemoteSocketAddress().getAddress());
            client.closeConnection(CloseFrame.SERVICE_RESTART, "服务器已关闭。");
        }
        getLogger.info("正在结束WebSocket服务端...");
        try {
            socketServer.stop(3000);
        } catch (Exception interruptedException) {
            getLogger.error("结束WS服务端时发生错误：", interruptedException);
        }
    }
}
