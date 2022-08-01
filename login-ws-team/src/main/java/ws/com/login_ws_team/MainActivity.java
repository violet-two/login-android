package ws.com.login_ws_team;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;

import ws.com.login_ws_team.LoginService.Login;
import ws.com.login_ws_team.util.ScreenUtil;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText password;

    int inputType = 0;//0为密文，1为明文
    private EditText user;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.changePasswordImage).setBackgroundResource(R.drawable.no_eye);

        password = findViewById(R.id.et_password);
        user = findViewById(R.id.et_user);
        login = findViewById(R.id.btn_login);
        Button resign = findViewById(R.id.btn_resign);
        login.setOnClickListener(this);
        resign.setOnClickListener(this);
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

    public void changePasswordImage(View view) {
        if(inputType==0){
            view.setBackgroundResource(R.drawable.eye);
            password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            inputType=1;
            return ;
        }
        if(inputType==1){
            view.setBackgroundResource(R.drawable.no_eye);
            password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            inputType=0;
            return ;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_login:
                HashMap<String, String> params = new HashMap<>();
                params.put("user", user.getText().toString());
                params.put("password", password.getText().toString());
                Login.login(this,params);
                break;
            case R.id.btn_resign:
                Intent intent = new Intent(this, RegisterActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;

        }
    }
}