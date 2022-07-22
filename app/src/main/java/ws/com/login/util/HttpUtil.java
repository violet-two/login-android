package ws.com.login.util;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil {
    private static String BASE_URL = "HTTP://10.0.2.2:9102";

    public static void http(String params, String method, String api) {

        new Thread(new Runnable() {
            private BufferedReader bufferedReader;

            @Override
            public void run() {
                try {
                    URL url = null;
                    if (params != null) {
                        url = new URL(BASE_URL + api + params);
                    } else {
                        url = new URL(BASE_URL + api);
                    }
                    Log.d("HttpUtil", "url: " + url.toString());
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod(method);
                    httpURLConnection.setConnectTimeout(10000);
                    httpURLConnection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
                    httpURLConnection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9");
                    httpURLConnection.setRequestProperty("Accept", "application/json,text/plain,*/*");
                    //拿结果
                    int responseCode = httpURLConnection.getResponseCode();
                    if (responseCode == httpURLConnection.HTTP_OK) {
                        InputStream inputStream = httpURLConnection.getInputStream();
                        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                        Log.d("shen", "run: " + bufferedReader.readLine());
                        inputStream.close();
                        Message message = new Message();
                        Handler handler = new Handler();
                        message.obj = bufferedReader;
                        message.what = 0x123;
                        handler.sendMessage(message);
                    }
                } catch (Exception e) {

                }
            }
        }).start();
    }
}
