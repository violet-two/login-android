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
import ws.com.login_ws_team.LoginSuccess;
import ws.com.login_ws_team.MainActivity;
import ws.com.login_ws_team.api.API;
import ws.com.login_ws_team.util.HttpUtil;
import ws.com.login_ws_team.util.LoginUtil;
import ws.com.login_ws_team.util.MD5Util;
import ws.com.login_ws_team.util.ToastUtil;

public class ModifyPassword {
    private static String TAG = "HttpUtil";
    private static Handler handler;


    @SuppressLint("HandlerLeak")
    public static void modifyPassword(Context context, HashMap<String, String> params) {
        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                Bundle bundle = msg.getData();
                LoginUtil result = (LoginUtil) bundle.getSerializable("result");
                if (result.getFlag().equals("success")) {
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("state", "修改成功,请重新登录");
                    context.startActivity(intent);
                } else {
                    ToastUtil.show(context, result.getData().toString());
                }
            }
        };
        //md5加密

        API api = HttpUtil.getRetrofit().create(API.class);
//        String md5Password = MD5Util.md5s(params.get("password") + MD5Util.SALT);
        String md5Password = MD5Util.md5s(params.get("password") );
        params.put("password", md5Password);
        Call<LoginUtil> task = api.Modify(params);
        HttpUtil.loginTask(handler, task);
    }
}
