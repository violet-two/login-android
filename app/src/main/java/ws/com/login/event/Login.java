package ws.com.login.event;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.logging.LogRecord;

import okhttp3.FormBody;
import okhttp3.internal.http2.Http2Reader;
import ws.com.login.LoginActivity;
import ws.com.login.ManageMenuActivity;
import ws.com.login.util.HttpUtil;
import ws.com.login.util.LoginUtil;
import ws.com.login.util.ParamsUtil;
import ws.com.login.util.ToastUtil;

public class Login extends Thread {

    private static Handler handler;

    @SuppressLint("HandlerLeak")
    public static void login(Context context,HashMap<String, String> params) {
        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                Log.d("login", "handleMessage: "+msg.getData());
                Bundle bundle = msg.getData();
                String bundleString = bundle.getString("result");
                Log.d("login", "bundleString: "+bundleString);
                Gson gson = new Gson();
                LoginUtil result = gson.fromJson(bundleString,LoginUtil.class);
                Log.d("login", "gson: "+result);
                Log.d("login", "flag: "+result.getFlag());
                if(result.getFlag().equals("success")){
                    Intent intent = new Intent(context,ManageMenuActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }else{
                    ToastUtil.show(context,"密码错误");
                }
            }
        };
        //字符串处理
//        String param = ParamsUtil.stringBuilder(params);
        //访问网络请求
        FormBody formBody = new FormBody.Builder()
                .add("phone", params.get("user"))
                .add("password", params.get("password"))
                .build();
        String api = "/login";
        HttpUtil.http(formBody, handler, api);
    }
}
