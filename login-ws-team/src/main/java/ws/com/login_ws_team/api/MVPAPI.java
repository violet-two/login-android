package ws.com.login_ws_team.api;

import java.util.HashMap;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import ws.com.login_ws_team.entity.InformationDPBean;
import ws.com.login_ws_team.entity.LoginBean;
import ws.com.login_ws_team.entity.ModifyPasswordBean;
import ws.com.login_ws_team.entity.RegisterBean;
import ws.com.login_ws_team.entity.SignInBean;

public interface MVPAPI {
    @POST("/WS_Administration/login")
    Observable<LoginBean> Login(@QueryMap HashMap<String, String> params);

    @POST("/WS_Administration/registUser")
    Observable<RegisterBean> Regist(@QueryMap HashMap<String, String> params);

    @POST("/WS_Administration/updatePasswordByPhone")
    Observable<ModifyPasswordBean> Modify(@QueryMap HashMap<String, String> params);

    @POST("/WS_Administration/findUser")
    Observable<InformationDPBean> queryDPAll(@QueryMap HashMap<String, String> params);

    @POST("/WS_Administration/sign")
    Observable<SignInBean> sign(@QueryMap HashMap<String, String> params);
}
