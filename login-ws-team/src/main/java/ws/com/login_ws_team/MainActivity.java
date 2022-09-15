package ws.com.login_ws_team;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

import retrofit2.Response;
import ws.com.login_ws_team.entity.LoginBean;
import ws.com.login_ws_team.entity.UserManage;
import ws.com.login_ws_team.model.IBaseRetCallback;
import ws.com.login_ws_team.model.impl.LoginModelImpl;
import ws.com.login_ws_team.util.MD5Util;
import ws.com.login_ws_team.util.ScreenUtil;
import ws.com.login_ws_team.util.StatusBarUtil;
import ws.com.login_ws_team.util.ToastUtil;


public class MainActivity extends BaseActivity implements View.OnClickListener {

    private EditText password;

    int inputType = 0;//0为密文，1为明文
    private EditText user;
    private Button login;
    private Button resign;
    private LoginModelImpl loginModel;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //设置主题，同时去掉加载应用时的主题
        setTheme(R.style.Theme_Login);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //设置状态栏背景为透明
        StatusBarUtil.getStatusAToTransparent(this);
        user = findViewById(R.id.et_user);
        user.setFocusable(true);
        user.requestFocus();
        password = findViewById(R.id.et_password);
        login = findViewById(R.id.btn_login);
        resign = findViewById(R.id.btn_resign);
        user.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    // 此处为得到焦点时的处理内容
                    System.out.println("获取焦点");
                } else {
                    System.out.println("失去焦点");
                }
            }
        });
        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    // 此处为得到焦点时的处理内容
                    System.out.println("获取焦点");
                    String isTruePhoneNum = "^1(3[0-9]|5[012356789]|7[1235678]|8[0-9])\\d{8}$";
                    String inputPhone = user.getText().toString();
                    if (!inputPhone.matches(isTruePhoneNum)) {
                        ToastUtil.show(MainActivity.this, "手机号格式不正确");
                        user.requestFocus();
                    }
                } else {
                    System.out.println("失去焦点");
                }
            }
        });
        login.setOnClickListener(this);
        resign.setOnClickListener(this);
        //设置密码框默认属性
        findViewById(R.id.changePasswordImage).setBackgroundResource(R.drawable.ic_visibility_off_24);
        password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        //弹出等待框
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("请稍等.....");

        loginModel = new LoginModelImpl();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String status = bundle.getString("status");
            if ("success".equals(status)) {
                user.setText(bundle.getString("user"));
                password.setText(bundle.getString("password"));
                Intent intent = new Intent(this, InformationDepartmentActivity.class);
                startActivity(intent);
            }
        } else {
            password.setText("");
        }
    }


    //改变密码框输入的密码是否显示和图标显示
    public void changePasswordImage(View view) {
        if (inputType == 0) {
            view.setBackgroundResource(R.drawable.ic_visibility_24);
            password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            inputType = 1;
            return;
        }
        if (inputType == 1) {
            view.setBackgroundResource(R.drawable.ic_visibility_off_24);
            password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            inputType = 0;
            return;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                String phone = user.getText().toString();
                String mPassword = password.getText().toString();
                if ("".equals(phone)) {
                    ToastUtil.show(this, "用户名不能为空");
                    return;
                }
                if ("".equals(mPassword)) {
                    ToastUtil.show(this, "密码不能为空");
                    return;
                }
                progressDialog.show();
                HashMap<String, String> params = new HashMap<>();
                params.put("phone", phone);
                params.put("password", MD5Util.md5s(mPassword));
                loginModel.login(params, new IBaseRetCallback<LoginBean>() {
                    @Override
                    public void onSucceed(Response<LoginBean> response) {
                        LoginBean result = response.body();
                        Log.d("mainActivity", "onSucceed: " + result);
                        if ("success".equals(result.getFlag())) {
                            progressDialog.dismiss();
                            Intent intent = new Intent(MainActivity.this, InformationDepartmentActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("data", result);
                            startActivity(intent);
                            //登录成功就数据存储到SharedPreferences里面去
                            UserManage.getInstance().saveUserInfo(MainActivity.this,result);
                            finish();
                        } else {
                            progressDialog.dismiss();
                            ToastUtil.show(MainActivity.this, result.getData().toString());
                        }
                    }

                    @Override
                    public void onFailed(Throwable t) {
                    }
                });
                break;
            case R.id.btn_resign:
                Intent intent = new Intent(this, RegisterActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
                break;
        }
    }
}