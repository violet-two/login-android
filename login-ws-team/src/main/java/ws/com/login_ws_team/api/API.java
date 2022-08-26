package ws.com.login_ws_team.api;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import ws.com.login_ws_team.entity.InformationDPBean;
import ws.com.login_ws_team.entity.LoginBean;
import ws.com.login_ws_team.entity.ModifyPasswordBean;
import ws.com.login_ws_team.entity.RegisterBean;
import ws.com.login_ws_team.entity.SignInBean;

public interface API {

    @POST("/WS_Administration/login")
    Call<LoginBean> Login(@QueryMap HashMap<String, String> params);

    @POST("/WS_Administration/registUser")
    Call<RegisterBean> Regist(@QueryMap HashMap<String, String> params);

    @POST("/WS_Administration/updatePasswordByPhone")
    Call<ModifyPasswordBean> Modify(@QueryMap HashMap<String, String> params);

    @POST("/WS_Administration/findUser")
    Call<InformationDPBean> queryDPAll(@QueryMap HashMap<String, String> params);

    @POST("/WS_Administration/sign")
    Call<SignInBean> sign(@QueryMap HashMap<String, String> params);
}
