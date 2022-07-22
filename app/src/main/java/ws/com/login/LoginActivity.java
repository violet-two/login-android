package ws.com.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import java.util.HashMap;

import ws.com.login.event.Login;
import ws.com.login.util.ScreenUtil;
import ws.com.login.util.ToastUtil;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Login login;
    private EditText et_user;
    private EditText et_password;
    private RadioButton rb_isRead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();

        //登录事件
        findViewById(R.id.iBtn_login).setOnClickListener(this);
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

    //初始化视图
    private void initView() {
        et_user = findViewById(R.id.et_user);
        et_password = findViewById(R.id.et_password);
        rb_isRead = findViewById(R.id.rb_isRead);
    }

    public void loginByPhone(View view) {

    }

    public void register(View view) {

    }

    public void other(View view) {
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iBtn_login:
                if(et_user.length()<11||et_user==null){
                    ToastUtil.show(this,"请输入正确的手机号");
                    return;
                }
                if(!rb_isRead.isChecked()){
                    ToastUtil.show(this,"请勾选协议");
                    return;
                }
                HashMap<String,String> params = new HashMap<>();
                params.put("user",et_user.getText().toString());
                params.put("password",et_password.getText().toString());
                Login.login(this,params);
                break;
        }
    }

    public void closeBy_et_user(View view) {
        et_user.setText("");
        et_password.setText("");
    }

    public void closeBy_et_password(View view) {
        et_password.setText("");
    }
}