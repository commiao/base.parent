package basic.arch.tools.utils.common;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * 异常堆栈处理帮助
 * @author jannal
 *
 */
public abstract class StackTraceUtils {

    /**
     * 将异常堆栈转换为字符串
     * @param aThrowable 异常
     * @return String
     */
    public static String getStackTrace(Throwable aThrowable) {
        final Writer result = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(result);
        aThrowable.printStackTrace(printWriter);
        return result.toString();
    }
}