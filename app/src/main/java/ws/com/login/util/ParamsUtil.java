package ws.com.login.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ParamsUtil {
    public static String stringBuilder(HashMap<String, String> params) {
        StringBuilder sb = new StringBuilder();
        //组装参数
        if (params != null && params.size() > 0) {
            sb.append("?");
            Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> next = iterator.next();
                sb.append(next.getKey());
                sb.append("=");
                sb.append(next.getValue());
                if (iterator.hasNext()) {
                    sb.append("&");
                }
            }
        }
        return sb.toString();
    }
}
