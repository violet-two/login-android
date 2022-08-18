package ws.com.login_ws_team.loginService;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import java.util.HashMap;

import retrofit2.Call;
import ws.com.login_ws_team.InformationDepartmentActivity;
import ws.com.login_ws_team.MainActivity;
import ws.com.login_ws_team.RegisterActivity;
import ws.com.login_ws_team.api.API;
import ws.com.login_ws_team.util.HttpUtil;
import ws.com.login_ws_team.util.LoginUtil;
import ws.com.login_ws_team.util.MD5Util;
import ws.com.login_ws_team.util.ToastUtil;

public class Register {
    private static String TAG = "HttpUtil";
    private static Handler handler;
    private static API api;

    @SuppressLint("HandlerLeak")
    public static void register(RegisterActivity context, HashMap<String, String> params) {
        String password = params.get("password");
        handler = new Handler() {
            @SuppressLint("HandlerLeak")
            @Override
            public void handleMessage(@NonNull Message msg) {
                Bundle bundle = msg.getData();
                LoginUtil result = (LoginUtil) bundle.getSerializable("result");
                if (result.getFlag().equals("success")) {
//                    Intent intent = new Intent(context, MainActivity.class);
                    Intent intent = new Intent(context, InformationDepartmentActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("status", "success");
                    intent.putExtra("user", params.get("phone"));
                    intent.putExtra("password",  password);
                    ToastUtil.show(context,"注册成功");
                    context.startActivity(intent);
                } else {
                    ToastUtil.show(context, result.getData().toString());
                }
            }
        };
        //md5加密
        String md5Password = MD5Util.md5s( password+ MD5Util.SALT);
        api = HttpUtil.getRetrofit().create(API.class);
        params.put("password", md5Password);
        Call<LoginUtil> task = api.Regist(params);
        HttpUtil.loginTask(handler, task);
    }
}
