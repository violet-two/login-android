package ws.com.login_ws_team;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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

    private static final String TAG = "SignInActivity";
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
    private int[] signInDays = null;
    private GridView gvWeek;
    private HashMap<String, String> hashMap;
    private int today;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
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
        //初始化时间数据
        initDate();
        //获取签到的天数并初始化适配器
        getSignInDays();
        //初始化视图
        initView();
        //设置系统属性
        setSystemStyle();
    }

    private void setSystemStyle() {
        pingMuSize = GetPingMuSizeUtil.getPingMuSize(this);
        if (pingMuSize < 4.5) {
            //设置属性,获取属性要获取到他的父级容器标签或者布局
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) tvText.getLayoutParams();
            lp.setMargins(0, 0, 0, 10);
            tvText.setLayoutParams(lp);
            signInTextView.setLayoutParams(lp);
        }
    }

    private void getSignInDays() {
        hashMap.put("type", "selectSign");
        signInModel.signIn(hashMap, new IBaseRetCallback<SignInBean>() {
            @Override
            public synchronized void onSucceed(Response<SignInBean> response) {
                SignInBean body = response.body();
                try {
                    if ("success".equals(body.getFlag())) {
                        //获取签到的总天数
                        int signInNum = body.getSignDate().size();
                        signInDays = new int[signInNum];
                        for (int i = 0; i < signInNum; i++) {
                            if (month == Integer.parseInt(body.getSignDate().get(i).getMonth())) {
                                int day = Integer.parseInt(body.getSignDate().get(i).getDay());
                                Log.d(TAG, "onSucceed: " + day);
                                signInDays[i] = day;
                            }
                        }
                    }
                } catch (Exception e) {
                }
                //初始化适配器
                initAdapter();
            }

            @Override
            public void onFailed(Throwable t) {

            }
        });
    }

    private void initAdapter() {
        dateAdapter = DateAdapter.getInstance(SignInActivity.this, days, year, month, signInDays);
        gvDate.setAdapter(dateAdapter);
        gvDate.setVerticalSpacing(60);
//        gvDate.setEnabled(false);
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

        hashMap.put("type", "selectSign");
        signInModel.signIn(hashMap, new IBaseRetCallback<SignInBean>() {
            @Override
            public void onSucceed(Response<SignInBean> response) {
                SignInBean result = response.body();
                try {
                    if ("success".equals(result.getFlag())&&result.getSignDate()!=null) {
                        tvText.setText("今天已签到，获取奖励");
                        signInTextView.setText("×" + result.getPoints().toString());
                        signInButton.setText("已签到");
                    }
                } catch (Exception e) {
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
        today = DateUtils.getToday();
        Log.d(TAG, "today: " + today);
        days = DateUtils.getDayOfMonthFormat(year, month);
    }

    public void reBack(View view) {
    }

    public void radioClick(View view) {
        if (radioStatus == 0) {
            findViewById(R.id.leftRadio).setVisibility(View.VISIBLE);
            findViewById(R.id.rightRadio).setVisibility(View.GONE);
            radioStatus = 1;
            return;
        }
        if (radioStatus == 1) {
            findViewById(R.id.leftRadio).setVisibility(View.GONE);
            findViewById(R.id.rightRadio).setVisibility(View.VISIBLE);
            radioStatus = 0;
            return;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signInButton:
                hashMap.put("type", "sign");
                signInModel.signIn(hashMap, new IBaseRetCallback<SignInBean>() {
                    @Override
                    public void onSucceed(Response<SignInBean> response) {
                        SignInBean result = response.body();
                        if ("success".equals(result.getFlag())) {
                            tvText.setText("今天已签到，获取奖励");
                            signInButton.setText("已签到");
                            hashMap.put("type", "selectSign");
                            signInModel.signIn(hashMap, new IBaseRetCallback<SignInBean>() {
                                @Override
                                public void onSucceed(Response<SignInBean> response) {
                                    signInTextView.setText("×" + response.body().getPoints().toString());
                                    dateAdapter.changeToday(today);
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