package ws.com.login_ws_team.util;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ws.com.login_ws_team.api.API;

public class RetrofitUtil {
    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://119.96.82.181:8081")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private static volatile RetrofitUtil mInstance;

    public static RetrofitUtil getInstance(){
        if(mInstance==null){
            synchronized(Retrofit.class){
                if(mInstance==null){
                    mInstance = new RetrofitUtil();
                }
            }
        }
        return mInstance;
    }
    public static API getRetrofit(){
        API api = retrofit.create(API.class);
        return api;
    }
}
