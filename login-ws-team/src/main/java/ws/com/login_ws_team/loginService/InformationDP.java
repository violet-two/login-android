package ws.com.login_ws_team.loginService;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import ws.com.login_ws_team.R;
import ws.com.login_ws_team.adapter.InformationAdapter;
import ws.com.login_ws_team.api.API;
import ws.com.login_ws_team.util.HttpUtil;
import ws.com.login_ws_team.util.InformationDPUtil;
import ws.com.login_ws_team.util.ToastUtil;

public class InformationDP extends AppCompatActivity {
    private static List<InformationDPUtil.DataBean> info;
    public static   List<InformationDPUtil.DataBean> getInfo(){
        return info;
    }

    private static Handler handler;

    @SuppressLint("HandlerLeak")
    public static void informationDP(Activity activity, View view, HashMap<String, String> params){
        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                Bundle bundle = msg.getData();
                InformationDPUtil result = (InformationDPUtil) bundle.getSerializable("result");
                if ("success".equals(result.getFlag())) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<ArrayList<InformationDPUtil.DataBean>>(){}.getType();
                    info = gson.fromJson(result.getData().toString(),type);
                    //初始化人员数据
                    initData(view,activity);
                } else {
                    ToastUtil.show(activity, result.getData().toString());
                }
            }
        };
        API api = HttpUtil.getRetrofit().create(API.class);
        Call<InformationDPUtil> task = api.queryDPAll(params);
        HttpUtil.queryTask(handler, task);
    }

    private static void initData(View view,Activity activity) {
        RecyclerView informationListRV = view.findViewById(R.id.informationListRV);
        SearchView searchView = view.findViewById(R.id.searchView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        informationListRV.setLayoutManager(linearLayoutManager);
        InformationAdapter informationAdapter = new InformationAdapter(info);
        informationListRV.setAdapter(informationAdapter);
        informationListRV.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            //触摸拦截事件 返回false不执行后面函数
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                //取消search框的焦点
                searchView.clearFocus();
                return false;
            }
            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
            }
            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
    }
}
