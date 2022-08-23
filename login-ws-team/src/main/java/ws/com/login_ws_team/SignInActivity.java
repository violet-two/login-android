package ws.com.login_ws_team;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import ws.com.login_ws_team.adapter.DateAdapter;
import ws.com.login_ws_team.adapter.WeekAdapter;
import ws.com.login_ws_team.customView.SignInTextView;
import ws.com.login_ws_team.util.DateUtils;
import ws.com.login_ws_team.util.GetPingMuSizeUtil;

public class SignInActivity extends AppCompatActivity {

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
        DateAdapter dateAdapter = new DateAdapter(this, days, 2027, 2);
        gvDate.setAdapter(dateAdapter);
        gvDate.setVerticalSpacing(60);
//        gvDate.setEnabled(false);

        signInTextView = findViewById(R.id.signInTextView);
        tvText = findViewById(R.id.tv_text);
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
}