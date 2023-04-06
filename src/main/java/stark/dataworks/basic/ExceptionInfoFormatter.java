package stark.dataworks.basic;

import java.util.Arrays;

public class ExceptionInfoFormatter
{
    private ExceptionInfoFormatter()
    {
    }

    public static String formatMessageAndStackTrace(Throwable e)
    {
        String errorStackTrace = Arrays.toString(e.getStackTrace());
        errorStackTrace = errorStackTrace.substring(1, errorStackTrace.length() - 1);
        errorStackTrace = errorStackTrace.replace(", ", System.lineSeparator());
        return e.getMessage() + System.lineSeparator() + "Stack trace:" + System.lineSeparator() + errorStackTrace;
    }
}
