package ws.com.login_ws_team;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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

    private static final String TAG = "SignInActivity";
    private int year;
    private int month;
    private GridView gvDate;
    private int[][] days = null;
    private TextView monthNum;
    private String radioStatus = "false";
    private double pingMuSize;
    private SignInTextView signInTextView;
    private TextView tvText;
    private Button signInButton;
    private SignInModelImpl signInModel;
    private DateAdapter dateAdapter;
    private int[] signInDays;
    private List<SignInBean.JpdetailBean> jpdetail;
    private GridView gvWeek;
    private HashMap<String, String> hashMap;
    private int today;
    private boolean dataError = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //设置主题，同时去掉加载应用时的主题
        setTheme(R.style.Theme_Login);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        //初始化时间数据
        initDate();
        //初始化视图
        initView();
        gvWeek = findViewById(R.id.gvWeek);
        //配置week适配器
        WeekAdapter weekAdapter = new WeekAdapter(this);
        gvWeek.setAdapter(weekAdapter);

        //初始化账号
        hashMap = new HashMap<>();
        hashMap.put("type", "sign");
        hashMap.put("phone", "15337117134");
        //初始化signInModel实现类
        signInModel = new SignInModelImpl();
        //获取签到的天数并初始化适配器
        getSignInDays();
        //设置系统属性
        setSystemStyle();
    }

    private void setSystemStyle() {
        pingMuSize = GetPingMuSizeUtil.getPingMuSize(this);
        if (pingMuSize < 5.5) {
            //设置属性,获取属性要获取到他的父级容器标签或者布局
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) tvText.getLayoutParams();
            lp.setMargins(0, 0, 0, 20);
            tvText.setLayoutParams(lp);
            signInTextView.setLayoutParams(lp);
            gvDate.setVerticalSpacing(0);
        }else{
            gvDate.setVerticalSpacing(40);
        }
    }

    private void getSignInDays() {
        hashMap.put("type", "selectSign");
        signInModel.signIn(hashMap, new IBaseRetCallback<SignInBean>() {

            @Override
            public synchronized void onSucceed(Response<SignInBean> response) {
                SignInBean body = response.body();
                try {
                    //判断时间是否被篡改
                    Date date = new Date();
                    Date parse = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(body.getNowTime());
                    if((Math.abs((date.getTime() - parse.getTime()))  / 1000)>1){
                        ToastUtil.show(SignInActivity.this,"日期不对");
                        signInDays = new int[0];
                        initAdapter();
                        dataError = false;
                        return;
                    }
                    //如果请求失败，则只进行日历的显示
                    if(body==null){
                        signInDays = new int[0];
                        initAdapter();
                        return;
                    }
                    if (body.getQiandaoTx()) {
                        findViewById(R.id.leftRadio).setVisibility(View.GONE);
                        findViewById(R.id.rightRadio).setVisibility(View.VISIBLE);
                        radioStatus = "true";
                    }
                    if ("success".equals(body.getFlag()) && body.getNowFlag()) {
                        tvText.setText("今天已签到，获取奖励");
                        signInTextView.setText(" ×" + body.getPoints().toString());
                        signInButton.setText("已签到");
                    }
                    if ("success".equals(body.getFlag())) {
                        if (body.getSignDate() == null) {
                            signInDays = new int[0];
                            initAdapter();
                            return;
                        }
                        //获取签到的总天数
                        int signInNum = body.getSignDate().size();
                        signInDays = new int[signInNum];
                        jpdetail = body.getJpdetail();
                        for (int i = 0; i < signInNum; i++) {
                            if (month == body.getSignDate().get(i).getMonth()) {
                                int day = body.getSignDate().get(i).getDay();
                                signInDays[i] = day;
                                if (signInDays == null) continue;
                            }
                        }
                        //初始化适配器
                        initAdapter();
                    }
                } catch (Exception e) {
                }
            }

            @Override
            public void onFailed(Throwable t) {

            }
        });
    }

    private void initAdapter() {
        do {
            //如果是首次签到，初始化礼物为7天后
            if (jpdetail == null) {
                List<SignInBean.JpdetailBean> list = new ArrayList<>();
                SignInBean.JpdetailBean jpdetailBeans = new SignInBean.JpdetailBean();
                jpdetailBeans.setDay(today + 6);
                jpdetailBeans.setMonth(month);
                jpdetailBeans.setYear(year);
                jpdetailBeans.setNum(0);
                jpdetailBeans.setContinuityNum(0);
                list.add(jpdetailBeans);
                dateAdapter = DateAdapter.getInstance(SignInActivity.this, days, year, month, signInDays, list);
                break;
            }
            dateAdapter = DateAdapter.getInstance(SignInActivity.this, days, year, month, signInDays, jpdetail);
        } while (days == null);
        if(jpdetail==null){
            dateAdapter.changeSetGift(today+6,month,year,0,0);
        }else{
            if(jpdetail.get(0).getDay()<DateUtils.getCurrentDayOfMonth()){
                dateAdapter.changeSetGift(today+6,month,year,0,0);
            }
        }
        gvDate.setAdapter(dateAdapter);
    }

    private void initView() {
        gvDate = findViewById(R.id.gvDate);
        signInTextView = findViewById(R.id.signInTextView);
        monthNum = findViewById(R.id.monthNum);
        monthNum.setText(String.valueOf(month));
        gvDate = findViewById(R.id.gvDate);
        tvText = findViewById(R.id.tv_text);
        signInButton = findViewById(R.id.signInButton);
        signInButton.setOnClickListener(this);
    }


    private void initDate() {
        year = DateUtils.getYear();
        month = DateUtils.getMonth();
        today = DateUtils.getToday();
        days = DateUtils.getDayOfMonthFormat(year, month);
    }

    public void reBack(View view) {
    }

    public void radioClick(View view) {
        HashMap<String, String> hashMap1 = new HashMap<>();
        hashMap1.put("type", "signTx");
        hashMap1.put("phone", hashMap.get("phone"));
        radioStatus = radioStatus == "false" ? "true" : "false";
        hashMap1.put("qiandaoTx", "" + radioStatus + "");
        signInModel.signIn(hashMap1, new IBaseRetCallback<SignInBean>() {
            @Override
            public void onSucceed(Response<SignInBean> response) {
            }

            @Override
            public void onFailed(Throwable t) {
            }
        });
        hashMap.put("type", "selectSign");
        signInModel.signIn(hashMap, new IBaseRetCallback<SignInBean>() {
            @Override
            public void onSucceed(Response<SignInBean> response) {
                SignInBean result = response.body();
                if (result.getQiandaoTx()) {
                    findViewById(R.id.leftRadio).setVisibility(View.GONE);
                    findViewById(R.id.rightRadio).setVisibility(View.VISIBLE);
                } else {
                    findViewById(R.id.leftRadio).setVisibility(View.VISIBLE);
                    findViewById(R.id.rightRadio).setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailed(Throwable t) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signInButton:
                if(!dataError){
                    ToastUtil.show(SignInActivity.this,"日期不对，请调整日期");
                    return ;
                }
                hashMap.put("type", "sign");
                signInModel.signIn(hashMap, new IBaseRetCallback<SignInBean>() {
                    @Override
                    public void onSucceed(Response<SignInBean> response) {
                        SignInBean result = response.body();
                        if ("success".equals(result.getFlag())) {
                            signInButton.setText("已签到");
                            hashMap.put("type", "selectSign");
                            tvText.setText("今天已签到，获取奖励");
                            ToastUtil.show(SignInActivity.this, result.getMsg());
                            signInModel.signIn(hashMap, new IBaseRetCallback<SignInBean>() {
                                @Override
                                public void onSucceed(Response<SignInBean> response) {
                                    signInTextView.setText(" ×" + response.body().getPoints().toString());
                                    dateAdapter.changeMJdetail(response.body().getJpdetail().get(0));
                                    dateAdapter.changeToday(today);
                                    if(response.body().getJpdetail().get(0).getContinuityNum()==7){
                                        dateAdapter.changeSetGift(today+6,month,year,0,0);
                                    }
                                    gvDate.setAdapter(dateAdapter);
                                }

                                @Override
                                public void onFailed(Throwable t) {
                                }
                            });
                        } else {
                            ToastUtil.show(SignInActivity.this, result.getMsg());
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