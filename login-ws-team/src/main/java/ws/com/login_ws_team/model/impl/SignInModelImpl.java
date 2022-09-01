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
import ws.com.login_ws_team.util.RetrofitUtil;

public class SignInModelImpl implements SignInModel<IBaseRetCallback<SignInBean>> {
    private final API api;

    public SignInModelImpl() {
        api = RetrofitUtil.getInstance().getAPI();
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
