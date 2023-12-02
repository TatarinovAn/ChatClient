package ru.netology;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class Logger {

    private static Logger logger;
    String dateTime = DateTimeFormatter.ofPattern("dd.mm.yyyy hh:mm:ss")
            .format(LocalDateTime.now());


    private Logger() {
    }

    public void log(String msg) {
        System.out.println("[" + dateTime + "] " + msg);
    }

    public static Logger getInstance() {
        if (logger == null) {
            return new Logger();
        }
        return logger;
    }
}
