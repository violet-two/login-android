package ws.com.login_ws_team.view;

import retrofit2.Response;
import ws.com.login_ws_team.entity.ModifyPasswordBean;
import ws.com.login_ws_team.entity.RegisterBean;

public interface IModifyPasswordView extends IBaseView{
    //修改密码
    void modifyPassword(Response<ModifyPasswordBean> modifyPasswordBeanResponse);
    //检查密码是否正确
    void checkPasswordIsTrue(Response<ModifyPasswordBean> modifyPasswordBeanResponse);
}
