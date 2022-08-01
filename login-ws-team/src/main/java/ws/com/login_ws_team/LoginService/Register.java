package ws.com.login_ws_team.LoginService;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

import java.util.HashMap;

import okhttp3.FormBody;
import ws.com.login_ws_team.LoginSuccess;
import ws.com.login_ws_team.RegisterActivity;
import ws.com.login_ws_team.util.HttpUtil;
import ws.com.login_ws_team.util.LoginUtil;
import ws.com.login_ws_team.util.MD5Util;
import ws.com.login_ws_team.util.ToastUtil;

public class Register {
    private static String TAG = "HttpUtil";
    private static Handler handler;
    @SuppressLint("HandlerLeak")
    public static void register(RegisterActivity context, HashMap<String, String> params) {
        handler = new Handler() {
            @SuppressLint("HandlerLeak")
            @Override
            public void handleMessage(@NonNull Message msg) {
                Log.d("login", "handleMessage: " + msg.getData());
                Bundle bundle = msg.getData();
                String bundleString = bundle.getString("result");
                Log.d("login", "bundleString: " + bundleString);
                Gson gson = new Gson();
                LoginUtil result = gson.fromJson(bundleString, LoginUtil.class);
                Log.d("login", "gson: " + result);
                Log.d("login", "flag: " + result.getFlag());
                if (result.getFlag().equals("success")) {
                    Intent intent = new Intent(context, LoginSuccess.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("state", "注册成功");
                    context.startActivity(intent);
                } else {
                    ToastUtil.show(context, result.getData());
                }
            }
        };
        //md5加密
//        String md5Password = MD5Util.md5s(params.get("password") + MD5Util.SALT);
        String md5Password = MD5Util.md5s((String) params.get("password"));
        //访问网络请求
        FormBody formBody = new FormBody.Builder()
                .add("phone", params.get("phone"))
                .add("regname",  params.get("regname"))
                .add("deptId", params.get("deptId"))
                .add("sex", params.get("sex"))
                .add("age",  params.get("age"))
                .add("department", params.get("department"))
                .add("wsid",  params.get("wsid"))
                .add("password", md5Password)
                .add("address",  params.get("address"))
                .add("upload", params.get("upload"))
                .build();
        Log.d(TAG, "password: "+md5Password);
        String api = "/regist";
        HttpUtil.http(formBody, handler, api);
    }
}
