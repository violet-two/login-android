package ws.com.login_ws_team.model;

import java.util.HashMap;

public interface LoginModel<T>{
    void login(HashMap<String,String> hashMap,T loginCallback);
}
