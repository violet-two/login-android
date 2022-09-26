package ws.com.login_ws_team.presenter;

import java.util.HashMap;

import retrofit2.Response;
import ws.com.login_ws_team.entity.InformationDPBean;
import ws.com.login_ws_team.model.IBaseRetCallback;
import ws.com.login_ws_team.model.InformationDepartmentModel;
import ws.com.login_ws_team.model.impl.InformationDepartmentModelImpl;
import ws.com.login_ws_team.view.IInformationView;

/**
 * author su
 * Create by on 2022/9/26 10:22
 */
public class InformationPresenter extends IBasePresenter{
    InformationDepartmentModel informationDepartmentModel = new InformationDepartmentModelImpl();
    public void information(HashMap<String,Object> hashMap){
        if(informationDepartmentModel!=null&&mView.get()!=null){
            informationDepartmentModel.queryInformation(hashMap, new IBaseRetCallback<InformationDPBean>() {
                @Override
                public void onSucceed(Response response) {
                    ((IInformationView)mView.get()).queryInformation(response);
                }

                @Override
                public void onFailed(Throwable t) {

                }
            });
        }
    }
}
