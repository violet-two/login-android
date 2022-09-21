package ws.com.login_ws_team.presenter;

import android.util.Log;

import java.util.HashMap;

import retrofit2.Response;
import ws.com.login_ws_team.entity.LoginBean;
import ws.com.login_ws_team.model.IBaseRetCallback;
import ws.com.login_ws_team.model.impl.LoginModelImpl;
import ws.com.login_ws_team.view.ILoginView;

public class LoginPresenter<T extends ILoginView> extends IBasePresenter{
    //持有model接口
    LoginModelImpl loginModel = new LoginModelImpl();

    public void login(HashMap<String,String> hashMap){
        if(loginModel!=null&&mView.get()!=null){
            loginModel.login(hashMap, new IBaseRetCallback<LoginBean>() {
                @Override
                public void onSucceed(Response<LoginBean> response) {
                    ((ILoginView)mView.get()).login(response);
                }
                @Override
                public void onFailed(Throwable t) {
                    Log.d("TAG", "onFailed: "+t);
                }
            });
        }
    }
}
