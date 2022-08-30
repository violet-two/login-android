package ws.com.login_ws_team.model.impl;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ws.com.login_ws_team.api.API;
import ws.com.login_ws_team.entity.InformationDPBean;
import ws.com.login_ws_team.model.IBaseRetCallback;
import ws.com.login_ws_team.model.InformationDepartmentModel;
import ws.com.login_ws_team.util.RetrofitUtil;

public class InformationDepartmentModelImpl implements InformationDepartmentModel<IBaseRetCallback<InformationDPBean>> {
    private final API api;

    public InformationDepartmentModelImpl() {
        api = RetrofitUtil.getRetrofit();
    }
    @Override
    public void queryInformation(HashMap<String, String> hashMap, IBaseRetCallback<InformationDPBean> informationCallBack) {
        Call<InformationDPBean> informationDPBeanCall = api.queryDPAll(hashMap);
        informationDPBeanCall.enqueue(new Callback<InformationDPBean>() {
            @Override
            public void onResponse(Call<InformationDPBean> call, Response<InformationDPBean> response) {
                informationCallBack.onSucceed(response);
            }

            @Override
            public void onFailure(Call<InformationDPBean> call, Throwable t) {
                informationCallBack.onFailed(t);
            }
        });
    }
}
