package ws.com.login_ws_team;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import ws.com.login_ws_team.presenter.IBasePresenter;
import ws.com.login_ws_team.util.ScreenUtil;
import ws.com.login_ws_team.util.StatusBarUtil;
import ws.com.login_ws_team.util.ToastUtil;
import ws.com.login_ws_team.view.IBaseView;

public abstract class BaseActivity<P extends IBasePresenter,V extends IBaseView> extends AppCompatActivity {
    //判断手机号码是否正确
    protected static final String isTruePhoneNum = "^1(3[0-9]|5[012356789]|7[1235678]|8[0-9])\\d{8}$";
    //正则表达式，判断是否为含有大小写、8-16位、特殊符号
    protected static final String PW_PATTERN = "/^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#$%^&*?]).{8,16}$/";
    //底部状态栏高度
    protected static int statusBarHeight;
    protected P presenter;
    protected ProgressDialog progressDialog;
    //    public static final String PW_PATTERN = "/^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z]).{8,16}$/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置状态栏背景为透明
        StatusBarUtil.getStatusAToTransparent(this);
        //获取状态栏高度
        statusBarHeight = StatusBarUtil.getStatusBarHeight(this);
        presenter = createPresenter();
        presenter.attachView((V)this);
        //弹出等待框
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("请稍等.....");

    }

    protected abstract P createPresenter();

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
    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
        ToastUtil.cancelToast();
    }

}