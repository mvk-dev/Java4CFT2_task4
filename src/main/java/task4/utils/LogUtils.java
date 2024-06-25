package task4.utils;

import java.io.IOException;
import java.lang.reflect.Proxy;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogUtils {
    public static final String WORKING_DIR = Path.of("").toAbsolutePath() + "\\";
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @SuppressWarnings(value = "unchecked")
    public static <T> T get(T object) {
        Class<T> classObj = (Class<T>) object.getClass();

        return (T) Proxy.newProxyInstance(classObj.getClassLoader(),
                classObj.getInterfaces(),
                new LogProxy(object));
    }

    public static void writeToLog(String logFileName, String logString, boolean markTime) {
        Path fullPath = Path.of(WORKING_DIR + logFileName);
        String logStr = logString + System.lineSeparator();
        if (markTime)
            logStr = LocalDateTime.now().format(TIME_FORMATTER) + "; " + logStr;

        try {
            Files.writeString(fullPath, logStr, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
