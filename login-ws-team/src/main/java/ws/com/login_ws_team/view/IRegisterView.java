package ws.com.login_ws_team.view;

import retrofit2.Response;
import ws.com.login_ws_team.entity.RegisterBean;

public interface IRegisterView extends IBaseView{
    //注册按钮接口
    void register(Response<RegisterBean> loginBeanResponse);

}
