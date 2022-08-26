package ws.com.login_ws_team.model.impl;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ws.com.login_ws_team.api.API;
import ws.com.login_ws_team.entity.LoginBean;
import ws.com.login_ws_team.entity.ModifyPasswordBean;
import ws.com.login_ws_team.model.IBaseRetCallback;
import ws.com.login_ws_team.model.ModifyPasswordModel;

public class ModifyPasswordModelImpl implements ModifyPasswordModel<IBaseRetCallback<ModifyPasswordBean>> {
    private final Retrofit retrofit;
    private final API api;

    public ModifyPasswordModelImpl() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://119.96.82.181:8081")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(API.class);
    }
    @Override
    public void modifyPassword(HashMap<String, String> hashMap, IBaseRetCallback<ModifyPasswordBean> modifyCallBack) {
        Call<ModifyPasswordBean> login = api.Modify(hashMap);
        login.enqueue(new Callback<ModifyPasswordBean>() {
            @Override
            public void onResponse(Call<ModifyPasswordBean> call, Response<ModifyPasswordBean> response) {
                modifyCallBack.onSucceed(response);
            }

            @Override
            public void onFailure(Call<ModifyPasswordBean> call, Throwable t) {
                // 失败时做处理
                modifyCallBack.onFailed(t);
            }
        });
    }
}
