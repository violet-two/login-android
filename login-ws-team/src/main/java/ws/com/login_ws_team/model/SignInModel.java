package ws.com.login_ws_team.model;

import java.util.HashMap;

public interface SignInModel<T> {
    void signIn(HashMap<String,String> hashMap,T signInCallBack);
}
