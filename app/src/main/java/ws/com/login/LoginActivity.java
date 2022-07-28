package ws.com.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import java.util.ArrayList;
import java.util.HashMap;

import ws.com.login.event.Login;
import ws.com.login.fragment.LoginByPhoneAndPasswordFragment;
import ws.com.login.fragment.LoginByPhoneAndSmsFragment;
import ws.com.login.util.ScreenUtil;
import ws.com.login.util.ToastUtil;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Login login;
    private EditText et_user_sms;
    private EditText et_password;
    private RadioButton rb_isRead;
    private EditText et_user_password;
    private int loginId = 0;//0为密码登录 1为短息登录
    private Button btn_login_method;


    private int currentIndex = 0;//控制当前需要显示第几个Fragment
    private ArrayList<Fragment> fragmentArrayList = new ArrayList<Fragment>() {
    };//用List来存储Fragment,List的初始化没有写
    private Fragment mCurrentFragment;//显示当前Fragment
    private EditText et_verification;
    private LoginByPhoneAndPasswordFragment loginByPhoneAndPassword = new LoginByPhoneAndPasswordFragment();
    private LoginByPhoneAndSmsFragment loginByPhoneAndSms = new LoginByPhoneAndSmsFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //登录事件
        findViewById(R.id.iBtn_login).setOnClickListener(this);
        findViewById(R.id.btn_login_method).setOnClickListener(this);
        fragmentArrayList.add(loginByPhoneAndPassword);
        fragmentArrayList.add(loginByPhoneAndSms);
        loginMethodChange(1);
        loginMethodChange(0);
        initView();
    }

    //改变登录框 短信登录和密码登录切换
    private void loginMethodChange(int index) {
        currentIndex = index;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        //判断当前的Fragment是否为空，不为空则隐藏
        if (null != mCurrentFragment) {
            ft.hide(mCurrentFragment);
        }
        //先根据Tag从FragmentTransaction事物获取之前添加的Fragment
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(fragmentArrayList.get(currentIndex).getClass().getName());

        if (null == fragment) {
            //如fragment为空，则之前未添加此Fragment。便从集合中取出
            fragment = fragmentArrayList.get(index);
        }
        mCurrentFragment = fragment;

        //判断此Fragment是否已经添加到FragmentTransaction事物中
        if (!fragment.isAdded()) {
            ft.add(R.id.fragment, fragment, fragment.getClass().getName());
        } else {
            ft.show(fragment);
        }
        ft.commit();
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
        rb_isRead = findViewById(R.id.rb_isRead);
        btn_login_method = findViewById(R.id.btn_login_method);
    }

    public void loginByPhone(View view) {

    }

    public void register(View view) {

    }

    public void other(View view) {
    }

    @Override
    public void onClick(View view) {
        initFragment();
        switch (view.getId()) {
            case R.id.iBtn_login:
                if (loginId == 0) {
                    if (et_user_password.length() < 11 || et_user_password == null) {
                        ToastUtil.show(this, "请输入正确的手机号");
                        return;
                    }
                    if (!rb_isRead.isChecked()) {
                        ToastUtil.show(this, "请勾选协议");
                        return;
                    }
                    HashMap<String, String> params = new HashMap<>();
                    params.put("user", et_user_password.getText().toString());
                    params.put("password", et_password.getText().toString());
                    Login.login(this, params);
                } else {
                    if (et_user_password.length() < 11 || et_user_password == null) {
                        ToastUtil.show(this, "请输入正确的手机号");
                        return;
                    }
                    if (!rb_isRead.isChecked()) {
                        ToastUtil.show(this, "请勾选协议");
                        return;
                    }
                    HashMap<String, String> params = new HashMap<>();
                    params.put("user", et_user_sms.getText().toString());
                    params.put("password", et_verification.getText().toString());
                    Login.login(this, params);
                }
                break;
            case R.id.btn_login_method:
                loginId = loginId == 0 ? 1 : 0;
                if (loginId == 0) {
                    btn_login_method.setText("短信登录");
                    loginMethodChange(0);
                } else {
                    btn_login_method.setText("密码登录");
                    loginMethodChange(1);
                }
                break;
        }
    }

    private void initFragment() {
        View loginByPhoneAndPasswordView = getSupportFragmentManager().findFragmentByTag(fragmentArrayList.get(0).getClass().getName()).getView();
        View loginByPhoneAndSmsView = getSupportFragmentManager().findFragmentByTag(fragmentArrayList.get(1).getClass().getName()).getView();
        et_user_sms = loginByPhoneAndSmsView.findViewById(R.id.et_user_sms);
        et_user_password = loginByPhoneAndPasswordView.findViewById(R.id.et_user_password);
        et_password = loginByPhoneAndPasswordView.findViewById(R.id.et_password);
        et_verification = loginByPhoneAndSmsView.findViewById(R.id.et_verification);
    }

    public void closeBy_et_user(View view) {
        initFragment();
        et_user_password.setText("");
        et_password.setText("");
        et_verification.setText("");
        et_user_sms.setText("");
    }

    public void closeBy_et_password(View view) {
        initFragment();
        et_password.setText("");
    }

    public void closeBy_et_verification(View view) {
        initFragment();
        et_verification.setText("");
    }

    public void closeBy_et_user_verification(View view) {
        initFragment();
        et_user_password.setText("");
        et_password.setText("");
        et_verification.setText("");
        et_user_sms.setText("");
    }

}