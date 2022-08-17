package ws.com.login_ws_team;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ws.com.login_ws_team.adapter.InformationAdapter;
import ws.com.login_ws_team.util.InformationDPUtil;
import ws.com.login_ws_team.util.ScreenUtil;

public class InformationDepartmentActivity extends AppCompatActivity {

    private SearchView searchView;
    private List<InformationDPUtil> list = new ArrayList<>();
    private TextView search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_department);
        searchView = findViewById(R.id.searchView);
        searchView.setSubmitButtonEnabled(false);
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                System.out.println("aaaaa");
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                System.out.println("aaaaa");
                return false;
            }
        });

        search = findViewById(R.id.tv_search);
        initData();
        RecyclerView informationListRV = findViewById(R.id.informationListRV);
        //配置布局管理器和适配器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        informationListRV.setLayoutManager(linearLayoutManager);
        InformationAdapter informationAdapter = new InformationAdapter(list);
        informationListRV.setAdapter(informationAdapter);
        informationListRV.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            //触摸拦截事件 返回false不执行后面函数
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                return true;
            }
            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                //关闭软键盘
                closeKeyBoard();
                //取消search框的焦点
                searchView.clearFocus();
            }
            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
    }

    private void initData() {
        for (int i = 0; i < 40; i++) {
            InformationDPUtil informationDPUtil = new InformationDPUtil("" + i, "1", "部门","111");
            list.add(informationDPUtil);
        }
    }

    public void quit(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        this.startActivity(intent);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //关闭软键盘
        closeKeyBoard();
        //取消search框的焦点
        searchView.clearFocus();
        return super.onTouchEvent(event);
    }

    //关闭软键盘
    public void closeKeyBoard() {
        if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
            View v = getCurrentFocus();
            ScreenUtil.closeSoftInput(this, v);
        }
    }

    public void reBack(View view) {
        finish();
    }

    //修改密码
    public void modifyPassword(View view) {
        Intent intent = new Intent(this, ModifyPasswordActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        this.startActivity(intent);
    }
}