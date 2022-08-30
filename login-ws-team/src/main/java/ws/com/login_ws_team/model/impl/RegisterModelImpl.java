package ws.com.login_ws_team.model.impl;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ws.com.login_ws_team.api.API;
import ws.com.login_ws_team.entity.RegisterBean;
import ws.com.login_ws_team.model.IBaseRetCallback;
import ws.com.login_ws_team.model.RegisterModel;
import ws.com.login_ws_team.util.RetrofitUtil;

public class RegisterModelImpl implements RegisterModel<IBaseRetCallback<RegisterBean>> {
    private final API api;

    public RegisterModelImpl() {
        api = RetrofitUtil.getRetrofit();
    }
    @Override
    public void register(HashMap<String, String> hashMap, IBaseRetCallback<RegisterBean> loginCallback) {
        Call<RegisterBean> login = api.Regist(hashMap);
        login.enqueue(new Callback<RegisterBean>() {
            @Override
            public void onResponse(Call<RegisterBean> call, Response<RegisterBean> response) {
                loginCallback.onSucceed(response);
            }
            @Override
            public void onFailure(Call<RegisterBean> call, Throwable t) {
                // 失败时做处理
                loginCallback.onFailed(t);
            }
        });
    }
}
