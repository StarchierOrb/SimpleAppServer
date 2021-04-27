package me.starchier;

import org.jline.reader.EndOfFileException;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.UserInterruptException;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;

public class ServerMain {
    public static final String VERSION = "1.0.b2-SNAPSHOT";
    private static final String prompt = ">";
    public static void main(String[] args) {
        Terminal terminal;
        LineReader lineReader = null;
        try {
            terminal = TerminalBuilder.builder().system(true).build();
            lineReader = LineReaderBuilder.builder().terminal(terminal).build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            String line;
            try {
                assert lineReader != null;
                line = lineReader.readLine(prompt);
            } catch (UserInterruptException e) {

            } catch(EndOfFileException e) {

            }
        }
    }
}
