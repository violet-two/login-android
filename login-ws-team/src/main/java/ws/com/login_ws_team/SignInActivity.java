package ws.com.login_ws_team;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

import ws.com.login_ws_team.adapter.DateAdapter;
import ws.com.login_ws_team.adapter.WeekAdapter;
import ws.com.login_ws_team.util.DateUtils;

public class SignInActivity extends AppCompatActivity {

    private int year;
    private int month;
    private GridView gvDate;
    private int[][] days = new int[5][7];

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
    }

    private void initView() {
        gvDate = findViewById(R.id.gvDate);
        days = DateUtils.getDayOfMonthFormat(year, month);
        DateAdapter dateAdapter = new DateAdapter(this, days, year, month);
        gvDate.setAdapter(dateAdapter);

    }

    private void initDate() {
        year = DateUtils.getYear();
        month = DateUtils.getMonth();
    }

    public void reBack(View view) {
    }
}