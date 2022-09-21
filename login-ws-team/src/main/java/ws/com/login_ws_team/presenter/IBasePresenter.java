package ws.com.login_ws_team.presenter;

import java.lang.ref.WeakReference;

import ws.com.login_ws_team.view.IBaseView;

public class IBasePresenter<T extends IBaseView>{
    //持有view接口
   protected WeakReference<T> mView;
    /**
     * 绑定与解绑
     * @param
     */
    public void attachView(T view){
        mView = new WeakReference<>(view);
    }
    public void detachView(){
        if(mView!=null){
            mView.clear();
            mView = null;
        }
    }
}
