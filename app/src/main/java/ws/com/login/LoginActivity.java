package ws.com.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.HashMap;

import ws.com.login.event.Login;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Login login;
    private EditText et_user;
    private EditText et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();

        //登录事件
        findViewById(R.id.iBtn_login).setOnClickListener(this);
    }
    //初始化视图
    private void initView() {
        et_user = findViewById(R.id.et_user);
        et_password = findViewById(R.id.et_password);
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
                HashMap<String,String> params = new HashMap<>();
                params.put("user",et_user.getText().toString());
                params.put("password",et_password.getText().toString());
                Login.login(params);
                break;
        }
    }
}