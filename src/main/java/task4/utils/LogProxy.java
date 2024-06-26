package task4.utils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

public class LogProxy implements InvocationHandler {
    private final Object target;
    private String logFileName;

    public LogProxy(Object target) {
        this.target = target;

        logFileName = target.getClass().getAnnotation(LogTransformation.class).logFileName();
        if (logFileName.isEmpty())
            logFileName = "logfile.log";
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String className = target.getClass().getSimpleName() + ". ";
        String input = className + "Input: " + System.lineSeparator() + Arrays.toString(args);
        Utils.writeToLog(logFileName, input, true);

        Object result = method.invoke(target, args);

        String output = className + "Output: " + System.lineSeparator() + Arrays.toString(args);
        Utils.writeToLog(logFileName, output, true);

        return result;
    }
}
