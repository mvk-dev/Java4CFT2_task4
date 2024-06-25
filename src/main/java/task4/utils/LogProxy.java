package task4.utils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

public class LogProxy implements InvocationHandler {
    private final Object target;
    private boolean isLogTransformation = false;
    private String logFileName;

    public LogProxy(Object target) {
        this.target = target;

        if (target.getClass().isAnnotationPresent(LogTransformation.class)) {
            isLogTransformation = true;
            logFileName = target.getClass().getAnnotation(LogTransformation.class).logFileName();
            if (logFileName.isEmpty())
                logFileName = "logfile.log";
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String input = "";
        if (isLogTransformation) {
            input = "Input: " + Arrays.toString(args) + System.lineSeparator();
        }

        Object result = method.invoke(target, args);

        if (isLogTransformation) {
            String logStr = target.getClass().getName() + System.lineSeparator()
                    + input
                    + "Output: " + Arrays.toString(args);
            LogUtils.writeToLog(logFileName, logStr, true);
        }

        return result;
    }
}
