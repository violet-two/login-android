package ws.com.login_ws_team.view;

import retrofit2.Response;
import ws.com.login_ws_team.entity.InformationDPBean;

public interface IInformationView extends IBaseView{
    void queryInformation(Response<InformationDPBean> response);
}
