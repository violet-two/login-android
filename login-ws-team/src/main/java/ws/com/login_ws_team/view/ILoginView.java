package ws.com.login_ws_team.view;

import android.view.View;

import retrofit2.Response;
import ws.com.login_ws_team.entity.LoginBean;

public interface ILoginView extends IBaseView{
    //点击登录按钮事件
    void login(Response<LoginBean> loginBeanResponse);
    //点击注册按钮事件
    void register();
    //改变密码框输入的密码是否显示和图标显示
    void changePasswordImage(View view);
}
