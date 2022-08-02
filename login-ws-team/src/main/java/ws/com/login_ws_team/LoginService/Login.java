package ws.com.login_ws_team.LoginService;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

import java.util.HashMap;

import okhttp3.FormBody;
import ws.com.login_ws_team.util.HttpUtil;
import ws.com.login_ws_team.LoginSuccess;
import ws.com.login_ws_team.util.LoginUtil;
import ws.com.login_ws_team.util.MD5Util;
import ws.com.login_ws_team.util.ToastUtil;

public class Login {
    private static String TAG = "HttpUtil";
    private static Handler handler;

    @SuppressLint("HandlerLeak")
    public static void login(Context context, HashMap<String, String> params) {
        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                Bundle bundle = msg.getData();
                String bundleString = bundle.getString("result");
                Gson gson = new Gson();
                LoginUtil result = gson.fromJson(bundleString, LoginUtil.class);
                if (result.getFlag().equals("success")) {
                    Intent intent = new Intent(context, LoginSuccess.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("state", "登录成功");
                    context.startActivity(intent);
                } else {
                    ToastUtil.show(context, result.getData());
                }
            }
        };
        //md5加密
//        String md5Password = MD5Util.md5s(params.get("password") + MD5Util.SALT);
        String md5Password = MD5Util.md5s(params.get("password"));
        //访问网络请求
        FormBody formBody = new FormBody.Builder()
                .add("phone", params.get("user"))
                .add("password", md5Password)
                .build();
        Log.d(TAG, "password: "+md5Password);
        String api = "/login";
        HttpUtil.http(formBody, handler, api);

    }
}
