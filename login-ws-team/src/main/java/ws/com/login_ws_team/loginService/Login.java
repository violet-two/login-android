package ws.com.login_ws_team.loginService;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import java.util.HashMap;

import retrofit2.Call;
import ws.com.login_ws_team.InformationDepartmentActivity;
import ws.com.login_ws_team.api.API;
import ws.com.login_ws_team.util.HttpUtil;
import ws.com.login_ws_team.util.LoginUtil;
import ws.com.login_ws_team.util.MD5Util;
import ws.com.login_ws_team.util.ToastUtil;

public class Login {
    private static String TAG = "HttpUtil";
    private static Handler handler;


    @SuppressLint("HandlerLeak")
    public static void login(Context context,HashMap<String, String> params) {
        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                Bundle bundle = msg.getData();
                LoginUtil result = (LoginUtil) bundle.getSerializable("result");
                if ("success".equals(result.getFlag())) {
                    Intent intent = new Intent(context, InformationDepartmentActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("data", result);
                    context.startActivity(intent);
                } else {
                    ToastUtil.show(context, result.getData().toString());
                }
            }
        };
        //md5加密
        String md5Password = MD5Util.md5s(params.get("password"));
//        String md5Password = MD5Util.md5s(params.get("password") + MD5Util.SALT);
        //访问网络请求
//        FormBody formBody = new FormBody.Builder()
//                .add("phone", params.get("user"))
//                .add("password", md5Password)
//                .build();
//        Log.d(TAG, "password: "+md5Password);
//        String api = "/login";
//        HttpUtil.http(formBody, handler, api);
//        String md5Password = MD5Util.md5s(params.get("password"));
        API api = HttpUtil.getRetrofit().create(API.class);
        params.put("password", md5Password);
        Call<LoginUtil> task = api.Login(params);
        HttpUtil.loginTask(handler, task);
    }

    @SuppressLint("HandlerLeak")
    public static void checkPassword(Context context,HashMap<String, String> params) {
        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                Bundle bundle = msg.getData();
                LoginUtil result = (LoginUtil) bundle.getSerializable("result");
                if ("fail".equals(result.getFlag())){
                    ToastUtil.show(context,"密码与原密码不一致");
                }
            }
        };
        //md5加密
        String md5Password = MD5Util.md5s(params.get("password"));
        API api = HttpUtil.getRetrofit().create(API.class);
        params.put("password", md5Password);
        Call<LoginUtil> task = api.Login(params);
        HttpUtil.loginTask(handler, task);
    }
}
