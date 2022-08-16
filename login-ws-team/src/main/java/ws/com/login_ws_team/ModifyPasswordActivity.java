package ws.com.login_ws_team;

import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

import ws.com.login_ws_team.loginService.ModifyPassword;
import ws.com.login_ws_team.loginService.Register;
import ws.com.login_ws_team.util.ScreenUtil;
import ws.com.login_ws_team.util.StatusBarUtil;
import ws.com.login_ws_team.util.ToastUtil;

public class ModifyPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mPhone;
    private EditText mPassword;
    //正则表达式，判断是否为含有大小写、8-16位
//    public static final String PW_PATTERN = "/^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#$%^&*?]).{8,16}$/";
    public static final String PW_PATTERN = "/^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z]).{8,16}$/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_password);

        //设置状态栏背景为透明
        StatusBarUtil.getStatusAToTransparent(this);
        //获取状态栏高度
        int statusBarHeight = StatusBarUtil.getStatusBarHeight(this);
        //获取RelativeLayout
        RelativeLayout topBox = findViewById(R.id.topBox);
        //设置属性,获取属性要获取到他的父级容器标签或者布局
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) topBox.getLayoutParams();
        lp.setMargins(0,statusBarHeight,0,0);
        topBox.setLayoutParams(lp);


        mPhone = findViewById(R.id.et_phone);
        mPassword = findViewById(R.id.et_password);
        findViewById(R.id.modify_password).setOnClickListener(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        closeKeyBoard();
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
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.modify_password:
                String password = mPassword.getText().toString();
                String phone = mPhone.getText().toString();
                if("".equals(phone)){
                    ToastUtil.show(this,"手机号不能为空");
                    return ;
                }
                if("".equals(password)){
                    ToastUtil.show(this,"密码不能为空");
                    return ;
                }
                if(password.matches(PW_PATTERN)||password.length()<8||password.length()>16){
                    ToastUtil.show(this,"密码长度必须为8-16位并且包含大小写字母、数字");
                    return ;
                }
                HashMap<String,String> params = new HashMap<>();
                params.put("phone",phone);
                params.put("password",password);
                ModifyPassword.modifyPassword(this,params);
                break;
        }
    }
}
