package ws.com.login_ws_team.util;

import static java.net.HttpURLConnection.HTTP_OK;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpUtil {
    private static String BASE_URL = "http://119.96.82.181:8081/WS_Administration";
    private static String TAG = "HttpUtil";

    public static void http(FormBody formBody,Handler handler, String api) {
        new Thread(new Runnable() {
            //http://119.96.82.181:8081/WS_Administration/login?phone=1&password=1
            @Override
            public void run() {
                String url = BASE_URL+api;
                Log.d(TAG, "run: "+url);
                OkHttpClient okHttpClient = new OkHttpClient();
                Request build = new Request.Builder().url(url).post(formBody).build();
                Call call = okHttpClient.newCall(build);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        Log.d(TAG, "onFailure: "+e.toString());
                    }
                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        if(response.code()==HTTP_OK){
                            try {
//                        Log.d(TAG, "json: "+response.body().string());
                                String result = response.body().string();
                                Log.d(TAG, "login: "+result);
                                Message msg = new Message();
                                Bundle bundle = new Bundle();
                                bundle.putString("result",result);
                                msg.setData(bundle);
                                handler.sendMessage(msg);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        }).start();
    }
}
