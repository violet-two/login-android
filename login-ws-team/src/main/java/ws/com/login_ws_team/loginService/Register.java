package ws.com.login_ws_team.loginService;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

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
                    result = new LoginUtil();
                    result.setFlag("success");
                    List<LinkedTreeMap<String,Object>> dataBeans = new ArrayList<>();
                    LinkedTreeMap<String,Object> linkedHashMap= new LinkedTreeMap<>();
                    linkedHashMap.put("id",1);
                    linkedHashMap.put("regname",params.get("regname"));
                    linkedHashMap.put("phone",params.get("phone"));
                    linkedHashMap.put("deptId",1);
                    linkedHashMap.put("department",params.get("department"));
                    linkedHashMap.put("status",1);
                    dataBeans.add(linkedHashMap);
                    result.setData(dataBeans);
                    intent.putExtra("data",result);
                    ToastUtil.show(context,"注册成功");
                    context.startActivity(intent);
                } else {
                    ToastUtil.show(context, result.getData().toString());
                }
            }
        };
        //md5加密
//        String md5Password = MD5Util.md5s( password+ MD5Util.SALT);
        String md5Password = MD5Util.md5s( password);
        api = HttpUtil.getRetrofit().create(API.class);
        params.put("password", md5Password);
        Call<LoginUtil> task = api.Regist(params);
        HttpUtil.loginTask(handler, task);
    }
}
