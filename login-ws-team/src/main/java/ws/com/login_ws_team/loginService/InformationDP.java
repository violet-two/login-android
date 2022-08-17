package ws.com.login_ws_team.loginService;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import java.util.HashMap;

import retrofit2.Call;
import ws.com.login_ws_team.api.API;
import ws.com.login_ws_team.util.HttpUtil;
import ws.com.login_ws_team.util.InformationDPUtil;

public class InformationDP {

    private Handler handler;

    @SuppressLint("HandlerLeak")
    public void informationDP(Context context, API api, HashMap<String, String> params) {
        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                Bundle bundle = msg.getData();
                InformationDP result = (InformationDP) bundle.getSerializable("result");

            }
        };
        Call<InformationDPUtil> task = api.queryDP(params);
        HttpUtil.queryTask(handler, task);
    }
}
