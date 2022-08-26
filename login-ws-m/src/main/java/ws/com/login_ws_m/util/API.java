package ws.com.login_ws_m.util;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface API {

    @POST("/WS_Administration/login")
    Call<LoginUtil> Login(@QueryMap HashMap<String, String> params);

    @POST("/WS_Administration/registUser")
    Call<LoginUtil> Regist(@QueryMap HashMap<String, String> params);

    @POST("/WS_Administration/updatePasswordByPhone")
    Call<LoginUtil> Modify(@QueryMap HashMap<String, String> params);

    @POST("/WS_Administration/findUser")
    Call<InformationDPUtil> queryDPAll(@QueryMap HashMap<String, String> params);
}
