package ws.com.login_ws_m.model;

import retrofit2.Response;

public interface IBaseRetCallback<T>{
    void onSucceed(Response<T> response);

    void onFailed(Throwable t);
}
