package ws.com.login_ws_team.model.impl;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ws.com.login_ws_team.api.API;
import ws.com.login_ws_team.model.IBaseRetCallback;
import ws.com.login_ws_team.model.LoginModel;
import ws.com.login_ws_team.entity.LoginBean;
import ws.com.login_ws_team.util.RetrofitUtil;


public class LoginModelImpl implements LoginModel<IBaseRetCallback<LoginBean>> {

    private final API api;

    public LoginModelImpl() {
        api = RetrofitUtil.getAPI();
    }

    @Override
    public void login(HashMap<String, String> hashMap, IBaseRetCallback<LoginBean> loginCallback) {
        Call<LoginBean> login = api.Login(hashMap);
        login.enqueue(new Callback<LoginBean>() {
            @Override
            public void onResponse(Call<LoginBean> call, Response<LoginBean> response) {
                loginCallback.onSucceed(response);
            }

            @Override
            public void onFailure(Call<LoginBean> call, Throwable t) {
                // 失败时做处理
                loginCallback.onFailed(t);
            }
        });
    }
}
