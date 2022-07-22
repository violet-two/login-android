package ws.com.login.event;

import java.util.HashMap;

import ws.com.login.util.HttpUtil;
import ws.com.login.util.ParamsUtil;

public class Login {
    public static Object login(HashMap<String, String> params) {
        //字符串处理
        String param = ParamsUtil.stringBuilder(params);
        //访问网络请求
        String api = "/get/param";
        HttpUtil.http(param,"POST",api);
        return null;
    }
}
