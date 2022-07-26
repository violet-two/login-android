package ws.com.login_ws_team;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.HashMap;

import retrofit2.Response;
import ws.com.login_ws_team.entity.LoginBean;
import ws.com.login_ws_team.entity.ModifyPasswordBean;
import ws.com.login_ws_team.model.IBaseRetCallback;
import ws.com.login_ws_team.model.impl.LoginModelImpl;
import ws.com.login_ws_team.model.impl.ModifyPasswordModelImpl;
import ws.com.login_ws_team.presenter.IBasePresenter;
import ws.com.login_ws_team.util.MD5Util;
import ws.com.login_ws_team.util.StatusBarUtil;
import ws.com.login_ws_team.util.ToastUtil;

public class ModifyPasswordActivity extends BaseActivity implements View.OnClickListener {

    private EditText mOldPassword;
    private EditText mNewPassword;
    private EditText mAgainPassword;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_password);
        //获取RelativeLayout
        RelativeLayout topBox = findViewById(R.id.topBox);
        //设置属性,获取属性要获取到他的父级容器标签或者布局
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) topBox.getLayoutParams();
        lp.setMargins(0, statusBarHeight, 0, 0);
        topBox.setLayoutParams(lp);

        //获取人员信息页面的参数
        Bundle bundle = getIntent().getExtras();
        phone = bundle.getString("phone");

        mOldPassword = findViewById(R.id.old_password);
        mNewPassword = findViewById(R.id.new_password);
        mAgainPassword = findViewById(R.id.again_password);
        mNewPassword.setOnFocusChangeListener((view, b) -> {
            if (b) {
                // 此处为得到焦点时的处理内容
                if ("".equals(mOldPassword.getText().toString())) {
                    ToastUtil.show(this, "旧密码不能为空");
                    return;
                }
//                checkPassword();
            }else{
                if((!"".equals(mAgainPassword.getText().toString()))&&!mNewPassword.getText().toString().equals(mAgainPassword.getText().toString())){
                    ToastUtil.show(this, "2次输入密码不一致");
                    return ;
                }
            }
        });
        mAgainPassword.setOnFocusChangeListener((view, b) -> {
            if (b) {
                // 此处为得到焦点时的处理内容
                if ("".equals(mOldPassword.getText().toString())) {
                    ToastUtil.show(this, "旧密码不能为空");
                    return;
                }
                if ("".equals(mNewPassword.getText().toString())) {
                    ToastUtil.show(this, "新密码不能为空");
                    return;
                }
//                checkPassword();
            }else{
                if((!"".equals(mAgainPassword.getText().toString()))&&!mNewPassword.getText().toString().equals(mAgainPassword.getText().toString())){
                    ToastUtil.show(this, "2次输入密码不一致");
                    return ;
                }
            }
        });
        findViewById(R.id.modify_password).setOnClickListener(this);
    }


    boolean b;

    //检查密码是否正确
    @SuppressLint("HandlerLeak")
    private boolean checkPassword() {
        if ("".equals(mOldPassword.getText().toString())) {
            mOldPassword.requestFocus();
            ToastUtil.show(this, "输入密码不能为空");
            return false;
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("phone", phone);
        params.put("password", mOldPassword.getText().toString());
        LoginModelImpl loginModel = new LoginModelImpl();
        loginModel.login(params, new IBaseRetCallback<LoginBean>() {
            @Override
            public void onSucceed(Response<LoginBean> response) {
                LoginBean result = response.body();
                if ("success".equals(result.getFlag())) {
                } else {
                    mOldPassword.requestFocus();
                    ToastUtil.show(ModifyPasswordActivity.this, "旧密码错误");
                }
            }

            @Override
            public void onFailed(Throwable t) {

            }
        });
        return b;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        closeKeyBoard();
        return onTouchEvent(event);
    }


    public void reBack(View view) {
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.modify_password:
                String beforePassword = mOldPassword.getText().toString();
                String newPassword = mNewPassword.getText().toString();
                String againPassword = mAgainPassword.getText().toString();
                if ("".equals(beforePassword)) {
                    ToastUtil.show(this, "旧密码不能为空");
                    return;
                }
                if ("".equals(newPassword)) {
                    ToastUtil.show(this, "新密码不能为空");
                    return;
                }
                if ("".equals(againPassword)) {
                    ToastUtil.show(this, "重复密码不能为空");
                    return;
                }
                if (!newPassword.equals(againPassword)) {
                    ToastUtil.show(this, "2次输入密码不一致");
                    return;
                }
                if(newPassword.matches(PW_PATTERN)||newPassword.length()<8||newPassword.length()>16){
                    ToastUtil.show(this,"密码长度必须为8-16位并且包含大小写字母、数字、特殊符号");
                    return ;
                }
                HashMap<String, String> params = new HashMap<>();
                params.put("phone", phone);
                params.put("beforePassword", MD5Util.md5s(beforePassword));
                params.put("password", MD5Util.md5s(newPassword));
                ModifyPasswordModelImpl modifyPasswordModel = new ModifyPasswordModelImpl();
                modifyPasswordModel.modifyPassword(params, new IBaseRetCallback<ModifyPasswordBean>() {
                    @Override
                    public void onSucceed(Response<ModifyPasswordBean> response) {
                        ModifyPasswordBean result = response.body();
                        if (result.getFlag().equals("success")) {
                            Intent intent = new Intent(ModifyPasswordActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.putExtra("phone",phone);
                            intent.putExtra("password",newPassword);
                            startActivity(intent);
                        } else {
                            ToastUtil.show(ModifyPasswordActivity.this, result.getData());
                        }
                    }

                    @Override
                    public void onFailed(Throwable t) {

                    }
                });
                break;
        }
    }
}
