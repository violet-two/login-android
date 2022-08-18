package ws.com.login_ws_team;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

import ws.com.login_ws_team.adapter.InformationAdapter;
import ws.com.login_ws_team.api.API;
import ws.com.login_ws_team.loginService.InformationDP;
import ws.com.login_ws_team.util.InformationDPUtil;
import ws.com.login_ws_team.util.LoginUtil;
import ws.com.login_ws_team.util.ScreenUtil;
import ws.com.login_ws_team.util.StatusBarUtil;

public class InformationDepartmentActivity extends AppCompatActivity {

    private SearchView searchView;
    private List<InformationDPUtil.DataBean> list = new ArrayList<>();
    private TextView search;
    private RecyclerView informationListRV;
    private String selfPhone;
    private Integer selfStatus;
    private String department;
    private String regname;
    private TextView dpAndName;
    private static API api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_department);
        //设置状态栏背景为透明---------------
        StatusBarUtil.getStatusAToTransparent(this);
        //获取状态栏高度
        int statusBarHeight = StatusBarUtil.getStatusBarHeight(this);
        //获取RelativeLayout
        RelativeLayout topBox = findViewById(R.id.topBox);
        //设置属性,获取属性要获取到他的父级容器标签或者布局
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) topBox.getLayoutParams();
        lp.setMargins(0,statusBarHeight,0,0);
        topBox.setLayoutParams(lp);
        //-----------------------------

        dpAndName = findViewById(R.id.dpAndName);
        informationListRV = findViewById(R.id.informationListRV);
        getLoginData();
        //获取所有人员信息
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("managerPhone",selfPhone);
        hashMap.put("phone","1");
        View allView = findViewById(R.id.allView);
        InformationDP.informationDP(this,allView,hashMap);

        searchView = findViewById(R.id.searchView);
        //设置输入框属性
        searchViewStyle();
        search = findViewById(R.id.tv_search);
        search.setOnClickListener(view->{
        });
    }

    private void getLoginData() {
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            LoginUtil result = (LoginUtil) bundle.getSerializable("data");
            Object data = result.getData().toString();
            System.out.println(data);
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<LoginUtil.DataBean>>(){}.getType();
            List<LoginUtil.DataBean> info = gson.fromJson(result.getData().toString(),type);
            System.out.println(info);
            regname = info.get(0).getRegname();
            department = info.get(0).getDepartment();
            dpAndName.setText(department+"+"+regname);
            selfStatus = info.get(0).getStatus();
            selfPhone = info.get(0).getPhone();
        }
    }


    public void searchViewStyle() {
        //设置输入框的图标不可见
        searchView.setSubmitButtonEnabled(false);
        //设置搜索图标在输入框外面
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }


    public void quit(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        //新建一个activity并清除以前的全部activity
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
        //如果修改密码成功则修改密码activity不进入栈
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        this.startActivity(intent);
    }
}