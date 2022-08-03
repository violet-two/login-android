package ws.com.login_ws_team;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import java.util.HashMap;

import ws.com.login_ws_team.loginService.Register;
import ws.com.login_ws_team.util.ScreenUtil;
import ws.com.login_ws_team.util.ToastUtil;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    //    public static final String PW_PATTERN = "/^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#$%^&*?]).{8,16}$/";
    public static final String PW_PATTERN = "/^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z]).{8,16}$/";
    private EditText mPhone;
    private EditText mPassword;
    private EditText mConfirmPassword;
    private EditText mName;
    private Button mRegister;
    private RadioButton mCheck;
    private Boolean isCheck = false;//true代表选中，false代表没选择

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_activity);
        mPhone = findViewById(R.id.et_phone);
        mPassword = findViewById(R.id.et_password);
        mConfirmPassword = findViewById(R.id.et_confirm_password);
        mName = findViewById(R.id.et_name);
        mRegister = findViewById(R.id.btn_register);
        mRegister.setOnClickListener(this);
        mCheck = findViewById(R.id.check);
        mCheck.setOnClickListener(this);
        mCheck.setChecked(false);
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
                if(phone==null||phone==""||phone.equals("")||
                        password==null||password==""||password.equals("")||
                        confirmPassword==null||confirmPassword==""||confirmPassword.equals("")||
                        name==null||name==""||name.equals("")){
                    ToastUtil.show(this,"输入不能为空");
                    return ;
                }
                if(!mCheck.isChecked()){
                    ToastUtil.show(this,"请勾选协议");
                    return ;
                }
                if(password.matches(PW_PATTERN)||password.length()<8||password.length()>16){
                    ToastUtil.show(this,"密码长度必须为8-16位并且包含大小写字母、数字");
                    return ;
                }
                if(!password.equals(confirmPassword)){
                    ToastUtil.show(this,"密码不一致请重新输入");
                    return ;
                }
                HashMap<String,String> params = new HashMap<>();
                params.put("phone",phone);
                params.put("regname",name);
                params.put("deptId","2");
                params.put("sex","2");
                params.put("age","2");
                params.put("department","2");
                params.put("wsid","2");
                params.put("password",password);
                params.put("address","2");
                params.put("upload","2");
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