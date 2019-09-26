package nist.module.mhxt.util;

import com.google.gson.Gson;

/**
 * 公用输出类
 */
public class ResponseUtil {
    public ResponseUtil() {
    }

    public static String writer(boolean code, String message) {
        return writer(code ? "1" : "0", message);
    }

    public static String writer(String code, String message) {
        return writer(code, message, (String)null);
    }

    public static String writer(boolean code, String message, String response) {
        return writer(code ? "1" : "0", message, response, new Long(0L));
    }

    public static String writer(String code, String message, String response) {
        return writer(code, message, response, new Long(0L));
    }

    public static String writer(boolean code, String message, String response, long count) {
        return writer(code ? "1" : "0", message, response, count, true);
    }

    public static String writer(String code, String message, String response, long count) {
        return writer(code, message, response, count, true);
    }

    public static String writer(boolean code, String message, Object object) {
        return writer(code ? "1" : "0", message, object);
    }

    public static String writer(String code, String message, Object object) {
        return writer(code, message, object, new Long(0L));
    }

    public static String writer(String code, String message, Object object, long count) {
        Gson gson = new Gson();
        String response = gson.toJson(object);
        return writer(code, message, response, count, true);
    }

    public static String writer(String code, String message, String response, long count, boolean flag) {
        StringBuilder reponseResultStr = new StringBuilder();
        reponseResultStr.append("{");
        reponseResultStr.append("\"code\":" + code);
        reponseResultStr.append(",\"msg\":\"" + message + "\"");
        reponseResultStr.append(",\"count\":" + count);
        if (flag) {
            reponseResultStr.append(",\"data\":" + response);
        } else {
            reponseResultStr.append(",\"data\":\"" + response + "\"");
        }
        reponseResultStr.append("}");
        return reponseResultStr.toString();
    }
}
