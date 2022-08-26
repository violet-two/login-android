package ws.com.login_ws_m.model.impl;

import static java.net.HttpURLConnection.HTTP_OK;

import java.io.IOException;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ws.com.login_ws_m.model.IBaseRetCallback;
import ws.com.login_ws_m.model.LoginModel;
import ws.com.login_ws_m.util.API;
import ws.com.login_ws_m.util.LoginUtil;

public class LoginModelImpl implements LoginModel<IBaseRetCallback<LoginUtil>> {

    private final Retrofit retrofit;
    private final API api;

    public LoginModelImpl() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://119.96.82.181:8081")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(API.class);
    }

    @Override
    public void login(HashMap<String, String> hashMap, IBaseRetCallback<LoginUtil> loginCallback) {
        Call<LoginUtil> login = api.Login(hashMap);
        login.enqueue(new Callback<LoginUtil>() {
            @Override
            public void onResponse(retrofit2.Call<LoginUtil> call, Response<LoginUtil> response) {
                loginCallback.onSucceed(response);
            }

            @Override
            public void onFailure(retrofit2.Call<LoginUtil> call, Throwable t) {
                // 失败时做处理
                loginCallback.onFailed(t);
            }
        });
    }
}
