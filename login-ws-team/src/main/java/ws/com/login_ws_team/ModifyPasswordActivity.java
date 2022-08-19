package ws.com.login_ws_team;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

import ws.com.login_ws_team.loginService.ModifyPassword;
import ws.com.login_ws_team.util.ScreenUtil;
import ws.com.login_ws_team.util.StatusBarUtil;
import ws.com.login_ws_team.util.ToastUtil;

public class ModifyPasswordActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText mOldPassword;
    private EditText mNewPassword;
    private EditText mAgainPassword;
    //正则表达式，判断是否为含有大小写、8-16位、特殊符号
    public static final String PW_PATTERN = "/^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#$%^&*?]).{8,16}$/";
//    public static final String PW_PATTERN = "/^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z]).{8,16}$/";
    private String phone;

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

        //获取人员信息页面的参数
        Bundle bundle = getIntent().getExtras();
        phone = bundle.getString("phone");

        mOldPassword = findViewById(R.id.old_password);
        mNewPassword = findViewById(R.id.new_password);
        mAgainPassword = findViewById(R.id.again_password);
//        mOldPassword.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//            @Override
//            public void afterTextChanged(Editable editable) {
//                HashMap<String,String> hashMap = new HashMap<>();
//                hashMap.put("phone",phone);
//                hashMap.put("password",editable.toString());
//                Login.checkPassword(ModifyPasswordActivity.this,hashMap);
//            }
//        });
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
                String beforePassword = mOldPassword.getText().toString();
                String newPassword = mNewPassword.getText().toString();
                String againPassword = mAgainPassword.getText().toString();
                if("".equals(beforePassword)){
                    ToastUtil.show(this,"旧密码不能为空");
                    return ;
                }
                if("".equals(newPassword)){
                    ToastUtil.show(this,"新密码不能为空");
                    return ;
                }
                if("".equals(againPassword)){
                    ToastUtil.show(this,"重复密码不能为空");
                    return ;
                }
                if(!newPassword.equals(againPassword)){
                    ToastUtil.show(this,"2次输入密码不一致");
                    return ;
                }
//                if(newPassword.matches(PW_PATTERN)||newPassword.length()<8||newPassword.length()>16){
//                    ToastUtil.show(this,"密码长度必须为8-16位并且包含大小写字母、数字、特殊符号");
//                    return ;
//                }
                HashMap<String,String> params = new HashMap<>();
                params.put("phone",phone);
                params.put("beforePassword",beforePassword);
                params.put("password",newPassword);
                ModifyPassword.modifyPassword(this,params);
                break;
        }
    }
}
