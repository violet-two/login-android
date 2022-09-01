//package ws.com.login_ws_team.util;
//
//import static java.net.HttpURLConnection.HTTP_OK;
//
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.util.Log;
//
//import java.util.Date;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//import ws.com.login_ws_team.entity.InformationDPBean;
//import ws.com.login_ws_team.entity.LoginBean;
//
//public class HttpUtil {
//    private static String BASE_URL = "http://119.96.82.181:8081/WS_Administration";
//    private static String TAG = "HttpUtil";
//
//    private static Retrofit retrofit = new Retrofit.Builder()
//            .baseUrl("http://119.96.82.181:8081")
////            //一般使用这个
//            .addConverterFactory(GsonConverterFactory.create())
//            //自定义工厂解决数据返回类型不一致问题
////            .addConverterFactory(GsonDConverterFactory.create())
//            .build();
//
//    public static Retrofit getRetrofit(){
//        return retrofit;
//    }
//
//    public static void loginTask(Handler handler,Call<LoginBean> task){
//        task.enqueue(new Callback<LoginBean>() {
//            @Override
//            public void onResponse(Call<LoginBean> call, Response<LoginBean> response) {
//                if (response.code() == HTTP_OK) {
//                    try {
//                        LoginBean result = response.body();
//                        Log.d(TAG, "onResponse: "+result);
//                        Log.d(TAG, "onResponse: "+new Date());
//                        Message msg = new Message();
//                        Bundle bundle = new Bundle();
//                        bundle.putSerializable("result",result);
//                        msg.setData(bundle);
//                        handler.sendMessage(msg);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<LoginBean> call, Throwable t) {
//                Log.d(TAG, "onResponse: " + t);
//            }
//        });
//    }
//    public static void queryTask(Handler handler,Call<InformationDPBean> task){
//        task.enqueue(new Callback<InformationDPBean>() {
//            @Override
//            public void onResponse(Call<InformationDPBean> call, Response<InformationDPBean> response) {
//                if (response.code() == HTTP_OK) {
//                    try {
//                        InformationDPBean result = response.body();
//                        Log.d(TAG, "onResponse: "+result);
//                        Message msg = new Message();
//                        Bundle bundle = new Bundle();
//                        bundle.putSerializable("result", result);
//                        Log.d(TAG, "onResponse:queryTask "+new Date());
//                        msg.setData(bundle);
//                        handler.sendMessage(msg);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//            @Override
//            public void onFailure(Call<InformationDPBean> call, Throwable t) {
//                Log.d(TAG, "onResponse: " + t);
//            }
//        });
//    }
//
////    public static void http(FormBody formBody,Handler handler, String api) {
////        new Thread(new Runnable() {
////            //http://119.96.82.181:8081/WS_Administration/login?phone=1&password=1
////            @Override
////            public void run() {
////                String url = BASE_URL+api;
////                OkHttpClient okHttpClient = new OkHttpClient();
////                Request build = new Request.Builder().url(url).post(formBody).build();
////                Call call = okHttpClient.newCall(build);
////                call.enqueue(new Callback() {
////                    @Override
////                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
////                        Log.d(TAG, "onFailure: "+e.toString());
////                    }
////                    @Override
////                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
////                        if(response.code()==HTTP_OK){
////                            try {
//////                        Log.d(TAG, "json: "+response.body().string());
////                                String result = response.body().string();
////                                Log.d(TAG, "login: "+result);
////                                Message msg = new Message();
////                                Bundle bundle = new Bundle();
////                                bundle.putString("result",result);
////                                msg.setData(bundle);
////                                handler.sendMessage(msg);
////                            } catch (Exception e) {
////                                e.printStackTrace();
////                            }
////                        }
////                    }
////                });
////            }
////        }).start();
////    }
//}
