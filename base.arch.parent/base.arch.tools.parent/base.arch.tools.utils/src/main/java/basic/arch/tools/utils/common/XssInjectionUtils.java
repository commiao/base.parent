package basic.arch.tools.utils.common;

import org.apache.commons.lang3.StringEscapeUtils;

public class XssInjectionUtils {

   // private static Logger logger = LoggerFactory.getLogger(XssInjectionUtils.class);

    private static final String XSS_EVENT = "((?i) onload|onunload|onchange|onsubmit|onreset" + "|onselect|onblur|onfocus|onkeydown|onkeypress|onkeyup|onerror|script|alert"
            + "|onclick|ondblclick|onmousedown|onmousemove|onmouseout|onfScripTocus|alscRiPtert|onmouseover|onmouseup|<|>|%3C|%3E)";
    //private static final String XSS_SQL = "('.+--)|(--)|(\\|)|(%7C)|(%22)";

    private static final String HTTP_EVENTS = "((?i)http|https|href|location|www|com|cn|src)";

    private static final String SQL_REGEX = "('.+--)|(--)|(\\|)|(%7C)";

    // private static final String SQL_EVENT =
    // "((?i)where|from|delete|update|insert)";

    public static String filterXssStr(String param) {
        return filterXssStr(param, false, true, false, true);
    }

    public static String filterXssStr(String param, boolean html, boolean event, boolean httpevent, boolean sqlevent) {

        if (html) {
            param = StringEscapeUtils.escapeHtml4(param);
        }
        if (event) {
            param = param.replaceAll(XSS_EVENT, "#");
        }
        if (httpevent) {
            param = param.replaceAll(HTTP_EVENTS, param);
        }
        if (sqlevent) {
            param = param.replaceAll(SQL_REGEX, param);
            // param = param.replaceAll(SQL_EVENT, param);
        }
        return param;
    }

}
