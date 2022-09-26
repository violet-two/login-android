package ws.com.login_ws_team.presenter;

import android.util.Log;

import java.util.HashMap;

import retrofit2.Response;
import ws.com.login_ws_team.entity.ModifyPasswordBean;
import ws.com.login_ws_team.model.IBaseRetCallback;
import ws.com.login_ws_team.model.ModifyPasswordModel;
import ws.com.login_ws_team.model.impl.ModifyPasswordModelImpl;
import ws.com.login_ws_team.view.IModifyPasswordView;

/**
 * author su
 * Create by on 2022/9/26 9:35
 */
public class ModifyPasswordPresenter<T extends IModifyPasswordView> extends IBasePresenter{
    ModifyPasswordModel modifyPasswordModel = new ModifyPasswordModelImpl();
    public void modifyPassword(HashMap<String,String> hashMap){
        if(modifyPasswordModel!=null&&mView.get()!=null){
            modifyPasswordModel.modifyPassword(hashMap, new IBaseRetCallback<ModifyPasswordBean>() {
                @Override
                public void onSucceed(Response response) {
                    ((IModifyPasswordView)mView.get()).modifyPassword(response);
                }

                @Override
                public void onFailed(Throwable t) {
                    Log.d("TAG", "onFailed: "+t);
                }
            });
        }
    }


}
