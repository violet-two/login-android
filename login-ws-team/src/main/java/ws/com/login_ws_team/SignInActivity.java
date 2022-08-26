package ws.com.login_ws_team;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;

import retrofit2.Response;
import ws.com.login_ws_team.adapter.DateAdapter;
import ws.com.login_ws_team.adapter.WeekAdapter;
import ws.com.login_ws_team.customView.SignInTextView;
import ws.com.login_ws_team.entity.SignInBean;
import ws.com.login_ws_team.model.IBaseRetCallback;
import ws.com.login_ws_team.model.impl.SignInModelImpl;
import ws.com.login_ws_team.util.DateUtils;
import ws.com.login_ws_team.util.GetPingMuSizeUtil;
import ws.com.login_ws_team.util.ToastUtil;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    private static double sqrt;
    private int year;
    private int month;
    private GridView gvDate;
    private int[][] days = null;
    private TextView monthNum;
    private int radioStatus = 1;
    private double pingMuSize;
    private SignInTextView signInTextView;
    private TextView tvText;
    private Button signInButton;
    private SignInModelImpl signInModel;
    private DateAdapter dateAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        GridView gvWeek = findViewById(R.id.gvWeek);
        WeekAdapter weekAdapter = new WeekAdapter(this);
        gvWeek.setAdapter(weekAdapter);
        //初始化时间
        initDate();
        //初始化组件
        initView();
        pingMuSize = GetPingMuSizeUtil.getPingMuSize(this);
        if(pingMuSize<4.5){
            //设置属性,获取属性要获取到他的父级容器标签或者布局
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) tvText.getLayoutParams();
            lp.setMargins(0,0,0,10);
            tvText.setLayoutParams(lp);
            signInTextView.setLayoutParams(lp);
        }
    }

    private void initView() {
        monthNum = findViewById(R.id.monthNum);
        monthNum.setText(String.valueOf(month));
        gvDate = findViewById(R.id.gvDate);
        days = DateUtils.getDayOfMonthFormat(year, month);
        dateAdapter = DateAdapter.getInstance(this, days, year, month);
        gvDate.setAdapter(dateAdapter);
        gvDate.setVerticalSpacing(60);
        //有问题
//        dateAdapter.isSign(new int[]{1,23});
//        gvDate.setEnabled(false);
        signInTextView = findViewById(R.id.signInTextView);
        tvText = findViewById(R.id.tv_text);
        signInButton = findViewById(R.id.signInButton);
        signInButton.setOnClickListener(this);
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("type","selectSign");
        hashMap.put("phone","13464849855");
        signInModel = new SignInModelImpl();
        signInModel.sign(hashMap, new IBaseRetCallback<SignInBean>() {
            @Override
            public void onSucceed(Response<SignInBean> response) {
                SignInBean result = response.body();
                if("success".equals(result.getFlag())){
                    tvText.setText("今天已签到，获取奖励");
                    signInTextView.setText("×"+result.getPoints().toString());
                    signInButton.setText("已签到");
                }
            }

            @Override
            public void onFailed(Throwable t) {

            }
        });
    }

    private void initDate() {
        year = DateUtils.getYear();
        month = DateUtils.getMonth();
    }

    public void reBack(View view) {
    }

    public void radioClick(View view) {
        if(radioStatus==0){
            findViewById(R.id.leftRadio).setVisibility(View.VISIBLE);
            findViewById(R.id.rightRadio).setVisibility(View.GONE);
            radioStatus = 1;
            return ;
        }
        if(radioStatus==1){
            findViewById(R.id.leftRadio).setVisibility(View.GONE);
            findViewById(R.id.rightRadio).setVisibility(View.VISIBLE);
            radioStatus = 0;
            return ;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.signInButton:
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("type","sign");
                hashMap.put("phone","13464849855");
                signInModel.sign(hashMap, new IBaseRetCallback<SignInBean>() {
                    @Override
                    public void onSucceed(Response<SignInBean> response) {
                        SignInBean result = response.body();
                        if("success".equals(result.getFlag())){
                            tvText.setText("今天已签到，获取奖励");
                            signInButton.setText("已签到");
                            hashMap.put("type","selectSign");
                            signInModel.sign(hashMap, new IBaseRetCallback<SignInBean>() {
                                @Override
                                public void onSucceed(Response<SignInBean> response) {
                                    signInTextView.setText("×"+response.body().getPoints().toString());
                                }

                                @Override
                                public void onFailed(Throwable t) {

                                }
                            });
                        }else{
                            ToastUtil.show(SignInActivity.this,result.getMsg());
                        }
                    }

                    @Override
                    public void onFailed(Throwable t) {

                    }
                });
                break;
        }
    }
}