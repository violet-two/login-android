package ws.com.login_ws_team;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.HashMap;

import ws.com.login_ws_team.api.API;
import ws.com.login_ws_team.loginService.Login;
import ws.com.login_ws_team.util.HttpUtil;
import ws.com.login_ws_team.util.ScreenUtil;
import ws.com.login_ws_team.util.StatusBarUtil;
import ws.com.login_ws_team.util.ToastUtil;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText password;

    int inputType = 0;//0为密文，1为明文
    private EditText user;
    private Button login;
    private static API api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //设置状态栏背景为透明
        StatusBarUtil.getStatusAToTransparent(this);

        api = HttpUtil.getRetrofit().create(API.class);

        password = findViewById(R.id.et_password);
        user = findViewById(R.id.et_user);
        login = findViewById(R.id.btn_login);
        Button resign = findViewById(R.id.btn_resign);
        login.setOnClickListener(this);
        resign.setOnClickListener(this);

        findViewById(R.id.changePasswordImage).setBackgroundResource(R.drawable.ic_visibility_off_24);
        password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
//        findViewById(R.id.btn_modifyPassword).setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
            Bundle bundle = getIntent().getExtras();
            if(bundle!=null){
                String status = bundle.getString("status");
                if("success".equals(status)){
                    user.setText(bundle.getString("user"));
                    password.setText(bundle.getString("password"));
                    Intent intent = new Intent(this, InformationDepartmentActivity.class);
                    startActivity(intent);
                }
            }else{
                password.setText("");
            }
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
            view.setBackgroundResource(R.drawable.ic_visibility_24);
            password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            inputType=1;
            return ;
        }
        if(inputType==1){
            view.setBackgroundResource(R.drawable.ic_visibility_off_24);
            password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            inputType=0;
            return ;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_login:
                if("".equals(user.getText().toString())){
                    ToastUtil.show(this,"用户名不能为空");
                    return ;
                }
                if("".equals(password.getText().toString())){
                    ToastUtil.show(this,"密码不能为空");
                    return ;
                }
                HashMap<String, String> params = new HashMap<>();
                params.put("phone", user.getText().toString());
                params.put("password", password.getText().toString());
                Login.login(this,api,params);
                break;
            case R.id.btn_resign:
                Intent intent = new Intent(this, RegisterActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
                break;
//            case R.id.btn_modifyPassword:
//                Intent intentModifyPassword = new Intent(this, ModifyPasswordActivity.class);
//                intentModifyPassword.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                startActivity(intentModifyPassword);
//                break;
        }
    }
}