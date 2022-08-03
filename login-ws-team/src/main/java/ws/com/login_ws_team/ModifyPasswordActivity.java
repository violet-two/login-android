package ws.com.login_ws_team;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

import ws.com.login_ws_team.loginService.ModifyPassword;
import ws.com.login_ws_team.loginService.Register;
import ws.com.login_ws_team.util.ScreenUtil;
import ws.com.login_ws_team.util.ToastUtil;

public class ModifyPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mPhone;
    private EditText mPassword;
//    public static final String PW_PATTERN = "/^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#$%^&*?]).{8,16}$/";
    public static final String PW_PATTERN = "/^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z]).{8,16}$/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_password);
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
                if(phone==null||phone==""||phone.equals("")||
                        password==null||password==""||password.equals("")){
                    ToastUtil.show(this,"输入不能为空");
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
