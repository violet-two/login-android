package ws.com.login_ws_m.model;

import java.util.HashMap;

import ws.com.login_ws_m.util.LoginUtil;

public interface LoginModel<T>{
    void login(HashMap<String,String> hashMap,T loginCallback);
}
