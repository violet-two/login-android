package ws.com.login_ws_team.model;

import java.util.HashMap;

public interface RegisterModel<T> {
    void register(HashMap<String,String> hashMap,T registerCallback);
}
