package ws.com.login_ws_team.presenter;

import android.util.Log;

import java.util.HashMap;

import retrofit2.Response;
import ws.com.login_ws_team.entity.RegisterBean;
import ws.com.login_ws_team.model.IBaseRetCallback;
import ws.com.login_ws_team.model.impl.RegisterModelImpl;
import ws.com.login_ws_team.view.IRegisterView;

/**
 * author su
 * Create by on 2022/9/22 10:25
 */
public class RegisterPresenter<T extends IRegisterView> extends IBasePresenter{
    RegisterModelImpl registerModel = new RegisterModelImpl();
    public void register(HashMap<String,String> hashMap){
        if(registerModel!=null&&mView.get()!=null){
            registerModel.register(hashMap, new IBaseRetCallback<RegisterBean>() {
                @Override
                public void onSucceed(Response<RegisterBean> response) {
                    ((IRegisterView)mView.get()).register(response);
                }

                @Override
                public void onFailed(Throwable t) {
                    Log.d("TAG", "onFailed: "+t);
                }
            });
        }
    }
}
