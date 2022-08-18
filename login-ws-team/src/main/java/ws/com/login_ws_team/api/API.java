package ws.com.login_ws_team.api;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import ws.com.login_ws_team.util.InformationDPUtil;
import ws.com.login_ws_team.util.LoginUtil;

public interface API {

    @POST("/WS_Administration/login")
    Call<LoginUtil> Login(@QueryMap HashMap<String, String> params);

    @POST("/WS_Administration/regist")
    Call<LoginUtil> Regist(@QueryMap HashMap<String, String> params);

    @POST("/WS_Administration/updatePasswordByPhone")
    Call<LoginUtil> Modify(@QueryMap HashMap<String, String> params);

    @POST("/WS_Administration/findUser")
    Call<InformationDPUtil> queryDPAll(@QueryMap HashMap<String, String> params);
}
