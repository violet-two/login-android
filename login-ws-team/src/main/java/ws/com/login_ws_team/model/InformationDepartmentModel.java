package ws.com.login_ws_team.model;

import java.util.HashMap;

public interface InformationDepartmentModel <T> {
    void queryInformation(HashMap<String,String> hashMap,T informationCallBack);
}
