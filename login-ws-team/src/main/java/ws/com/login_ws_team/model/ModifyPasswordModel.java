package ws.com.login_ws_team.model;

import java.util.HashMap;

public interface ModifyPasswordModel<T> {
    void modifyPassword(HashMap<String,String> hashMap,T modifyCallBack);
}
