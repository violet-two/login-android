package ws.com.login_ws_team.loginService;

import static java.net.HttpURLConnection.HTTP_OK;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.HashMap;

import okhttp3.FormBody;
import okhttp3.internal.concurrent.Task;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ws.com.login_ws_team.api.API;
import ws.com.login_ws_team.util.HttpUtil;
import ws.com.login_ws_team.LoginSuccess;
import ws.com.login_ws_team.util.LoginUtil;
import ws.com.login_ws_team.util.MD5Util;
import ws.com.login_ws_team.util.ToastUtil;

public class Login {
    private static String TAG = "HttpUtil";
    private static Handler handler;


    @SuppressLint("HandlerLeak")
    public static void login(Context context, API api, HashMap<String, String> params) {
        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                Bundle bundle = msg.getData();
                LoginUtil result = (LoginUtil) bundle.getSerializable("result");
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
        String md5Password = MD5Util.md5s(params.get("password") + MD5Util.SALT);
        //访问网络请求
//        FormBody formBody = new FormBody.Builder()
//                .add("phone", params.get("user"))
//                .add("password", md5Password)
//                .build();
//        Log.d(TAG, "password: "+md5Password);
//        String api = "/login";
//        HttpUtil.http(formBody, handler, api);
//        String md5Password = MD5Util.md5s(params.get("password"));
        params.put("password", md5Password);
        Call<LoginUtil> task = api.Login(params);
        HttpUtil.loginTask(handler, task);
    }
}
