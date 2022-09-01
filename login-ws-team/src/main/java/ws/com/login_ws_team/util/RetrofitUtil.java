package ws.com.login_ws_team.util;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ws.com.login_ws_team.api.API;

public class RetrofitUtil {
    private static Retrofit retrofit;
    private static volatile RetrofitUtil mInstance;
    private static API api;

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
    public synchronized static Retrofit getRetrofit(){
        if(retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://119.96.82.181:8081")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static API getAPI(){
        api = getRetrofit().create(API.class);
        return api;
    }
}
