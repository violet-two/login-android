package ws.com.login_ws_team;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import java.util.HashMap;

import ws.com.login_ws_team.loginService.Register;
import ws.com.login_ws_team.util.ScreenUtil;
import ws.com.login_ws_team.util.StatusBarUtil;
import ws.com.login_ws_team.util.ToastUtil;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

        public static final String PW_PATTERN = "/^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#$%^&*?]).{8,16}$/";
//    public static final String PW_PATTERN = "/^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z]).{8,16}$/";
    private EditText mPhone;
    private EditText mPassword;
    private EditText mConfirmPassword;
    private EditText mName;
    private Button mRegister;
    private RadioButton mCheck;
    private Boolean isCheck = false;//true代表选中，false代表没选择
    private Spinner mDepartment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_activity);
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
        mConfirmPassword = findViewById(R.id.et_confirm_password);
        mName = findViewById(R.id.et_name);
        mRegister = findViewById(R.id.btn_register);
        mRegister.setOnClickListener(this);
        mCheck = findViewById(R.id.check);
        mCheck.setOnClickListener(this);
        mCheck.setChecked(false);
        mDepartment = findViewById(R.id.department);

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
            case R.id.btn_register:
                String password = mPassword.getText().toString();
                String confirmPassword = mConfirmPassword.getText().toString();
                String phone = mPhone.getText().toString();
                String name = mName.getText().toString();
                String department = mDepartment.getSelectedItem().toString();
                if("".equals(phone)){
                    ToastUtil.show(this,"手机号不能为空");
                    return ;
                }
                if("".equals(password)){
                    ToastUtil.show(this,"密码不能为空");
                    return ;
                }
                if("".equals(confirmPassword)){
                    ToastUtil.show(this,"重复密码不能为空");
                    return ;
                }
                if("".equals(name)){
                    ToastUtil.show(this,"昵称不能为空");
                    return ;
                }
                if(!mCheck.isChecked()){
                    ToastUtil.show(this,"请勾选协议");
                    return ;
                }
//                if(password.matches(PW_PATTERN)||password.length()<8||password.length()>16){
//                    ToastUtil.show(this,"密码长度必须为8-16位并且包含大小写字母、数字、特殊符号");
//                    return ;
//                }
                if(!password.equals(confirmPassword)){
                    ToastUtil.show(this,"密码不一致请重新输入");
                    return ;
                }
                HashMap<String,String> params = new HashMap<>();
                params.put("phone",phone);
                params.put("regname",name);
                params.put("deptId","");
                params.put("sex","");
                params.put("age","");
                params.put("department",department);
                params.put("wsid","");
                params.put("password",password);
                params.put("address","");
                params.put("upload","");
                Register.register(this,params);
                break;
            case R.id.check:
                if(isCheck){
                    mCheck.setChecked(false);
                    isCheck = false;
                }else{
                    mCheck.setChecked(true);
                    isCheck = true;
                }
                break;
        }
    }

}