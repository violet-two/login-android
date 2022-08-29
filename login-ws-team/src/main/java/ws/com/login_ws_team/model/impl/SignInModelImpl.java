package ws.com.login_ws_team.model.impl;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ws.com.login_ws_team.api.API;
import ws.com.login_ws_team.entity.LoginBean;
import ws.com.login_ws_team.entity.SignInBean;
import ws.com.login_ws_team.model.IBaseRetCallback;
import ws.com.login_ws_team.model.SignInModel;

public class SignInModelImpl implements SignInModel<IBaseRetCallback<SignInBean>> {
    private final Retrofit retrofit;
    private final API api;

    public SignInModelImpl() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://119.96.82.181:8081")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(API.class);
    }

    @Override
    public void signIn(HashMap<String, String> hashMap, IBaseRetCallback<SignInBean> signInCallBack) {
        Call<SignInBean> login = api.sign(hashMap);
        login.enqueue(new Callback<SignInBean>() {
            @Override
            public void onResponse(Call<SignInBean> call, Response<SignInBean> response) {
                signInCallBack.onSucceed(response);
            }

            @Override
            public void onFailure(Call<SignInBean> call, Throwable t) {
                // 失败时做处理
                signInCallBack.onFailed(t);
            }
        });
    }
}
