package ws.com.login_ws_team;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Response;
import ws.com.login_ws_team.entity.LoginBean;
import ws.com.login_ws_team.entity.RegisterBean;
import ws.com.login_ws_team.model.IBaseRetCallback;
import ws.com.login_ws_team.model.impl.RegisterModelImpl;
import ws.com.login_ws_team.presenter.RegisterPresenter;
import ws.com.login_ws_team.util.MD5Util;
import ws.com.login_ws_team.util.StatusBarUtil;
import ws.com.login_ws_team.util.ToastUtil;
import ws.com.login_ws_team.view.IRegisterView;

public class RegisterActivity extends BaseActivity<RegisterPresenter, IRegisterView> implements View.OnClickListener, IRegisterView {

    private EditText mPhone;
    private EditText mPassword;
    private EditText mConfirmPassword;


    private EditText mName;
    private Button mRegister;
    private RadioButton mCheck;
    private Boolean isCheck = false;//true代表选中，false代表没选择
    private EditText mDepartment;
    private HashMap<String, String> params;
    private RegisterPresenter<IRegisterView> iRegisterViewRegisterPresenter;

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
        lp.setMargins(0, statusBarHeight, 0, 0);
        topBox.setLayoutParams(lp);

        //初始化视图
        initView();
        //初始化点击事件
        initEvent();
        iRegisterViewRegisterPresenter = new RegisterPresenter<>();
//        mDepartment.setSelection(0,false);
//        mDepartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                if ("".equals(mPhone.getText().toString())) {
//                    ToastUtil.show(RegisterActivity.this, "手机号不能为空");
//                    return;
//                }
//
//                if ("".equals(mPassword.getText().toString())) {
//                    ToastUtil.show(RegisterActivity.this, "密码不能为空");
//                    return;
//                }
//                if ("".equals(mConfirmPassword.getText().toString())) {
//                    ToastUtil.show(RegisterActivity.this, "重复密码不能为空");
//                    return;
//                }
//                if ("".equals(mName.getText().toString())) {
//                    ToastUtil.show(RegisterActivity.this, "名称不能为空");
//                    return;
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//                return;
//            }
//        });
    }


    private void initEvent() {
        mPhone.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) return;
            else {
                if (!(mPhone.getText().toString().matches(isTruePhoneNum))) {
                    ToastUtil.show(this, "手机号格式不正确");
                }
            }
        });
        mPassword.setOnFocusChangeListener((view, b) -> {
            if ("".equals(mPhone.getText().toString())) {
                ToastUtil.show(this, "手机号不能为空");
                return;
            }
            if (!(mPhone.getText().toString().matches(isTruePhoneNum))) {
                ToastUtil.show(this, "手机号格式不正确");
                return;
            }
            if (!b) {
                if ((!"".equals((mConfirmPassword.getText().toString()))) && !(mConfirmPassword.getText().toString()).equals(mPassword.getText().toString())) {
                    ToastUtil.show(this, "密码不一致");
                    return;
                }
            }
        });
        mConfirmPassword.setOnFocusChangeListener((view, b) -> {
            if ("".equals(mPhone.getText().toString())) {
                ToastUtil.show(this, "手机号不能为空");
                return;
            }
            if (!(mPhone.getText().toString().matches(isTruePhoneNum))) {
                ToastUtil.show(this, "手机号格式不正确");
                return;
            }
            if ("".equals(mPassword.getText().toString())) {
                ToastUtil.show(this, "密码不能为空");
                return;
            }
            if (!b) {
                if ((!"".equals((mConfirmPassword.getText().toString()))) && !(mConfirmPassword.getText().toString()).equals(mPassword.getText().toString())) {
                    ToastUtil.show(this, "密码不一致");
                    return;
                }
            }
        });
        mName.setOnFocusChangeListener((view, b) -> {
            if (!(mPhone.getText().toString().matches(isTruePhoneNum))) {
                ToastUtil.show(this, "手机号格式不正确");
                return;
            }
            if ("".equals(mPhone.getText().toString())) {
                ToastUtil.show(this, "手机号不能为空");
                return;
            }
            if ("".equals(mPassword.getText().toString())) {
                ToastUtil.show(this, "密码不能为空");
                return;
            }
            if ("".equals(mConfirmPassword.getText().toString())) {
                ToastUtil.show(this, "重复密码不能为空");
                return;
            }
        });
    }

    private void initView() {
        mPhone = findViewById(R.id.et_phone);
        mPhone.setFocusable(true);
        mPhone.requestFocus();
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
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter();
    }

    public void reBack(View view) {
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                String password = mPassword.getText().toString();
                String confirmPassword = mConfirmPassword.getText().toString();
                String phone = mPhone.getText().toString();
                String name = mName.getText().toString();
                String department = mDepartment.getText().toString();
                if ("".equals(phone)) {
                    ToastUtil.show(this, "手机号不能为空");
                    return;
                }
                if ("".equals(password)) {
                    ToastUtil.show(this, "密码不能为空");
                    return;
                }
                if ("".equals(confirmPassword)) {
                    ToastUtil.show(this, "重复密码不能为空");
                    return;
                }
                if ("".equals(name)) {
                    ToastUtil.show(this, "昵称不能为空");
                    return;
                }
                if (!mCheck.isChecked()) {
                    ToastUtil.show(this, "请勾选协议");
                    return;
                }
                if (password.matches(PW_PATTERN) || password.length() < 8 || password.length() > 16) {
                    ToastUtil.show(this, "密码长度必须为8-16位并且包含大小写字母、数字、特殊符号");
                    return;
                }
                if (!password.equals(confirmPassword)) {
                    ToastUtil.show(this, "密码不一致请重新输入");
                    return;
                }
                progressDialog.show();
                params = new HashMap<>();
                params.put("phone", phone);
                params.put("regname", name);
                params.put("department", department);
                params.put("password", MD5Util.md5s(password));
                iRegisterViewRegisterPresenter.register(params);
                break;
            case R.id.check:
                if (isCheck) {
                    mCheck.setChecked(false);
                    isCheck = false;
                } else {
                    mCheck.setChecked(true);
                    isCheck = true;
                }
                break;
        }
    }

    //点击注册后回调方法
    @Override
    public void register(Response<RegisterBean> response) {
        RegisterBean body = response.body();
        progressDialog.dismiss();
        if (body.getFlag().equals("success")) {
            Intent intent = new Intent(RegisterActivity.this, InformationDepartmentActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            LoginBean result = new LoginBean();
            result.setFlag("success");
            List<LinkedTreeMap<String, Object>> dataBeans = new ArrayList<>();
            LinkedTreeMap<String, Object> linkedHashMap = new LinkedTreeMap<>();
            linkedHashMap.put("id", 1);
            linkedHashMap.put("regname", params.get("regname"));
            linkedHashMap.put("phone", params.get("phone"));
            linkedHashMap.put("deptId", 1);
            linkedHashMap.put("department", params.get("department"));
            linkedHashMap.put("status", 1);
            dataBeans.add(linkedHashMap);
            result.setData(dataBeans);
            intent.putExtra("data", result);
            ToastUtil.show(RegisterActivity.this, "注册成功");
            startActivity(intent);
        } else {
            ToastUtil.show(RegisterActivity.this, body.getData().toString());
        }
    }
}