package ws.com.login_ws_team.entity;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

public class UserManage{
    private static UserManage instance;

    public UserManage() {
    }

    public static UserManage getInstance() {
        if(instance==null){
            instance = new UserManage();
        }
        return instance;
    }

    /*保存用户信息*/
    public void saveUserInfo(Context context, LoginBean data){
        //Context.MODE_PRIVATE表示SharePrefences的数据只有自己应用程序能访问
        SharedPreferences sp = context.getSharedPreferences("userInfo",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if(data==null){
            editor.putString("result", "");
        }else{
            editor.putString("result", data.getData().toString());
        }
        editor.commit();
    }

    /*获取用户信息*/
    public LoginBean getUserInfo(Context context){
        SharedPreferences sp = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        LoginBean loginBean = new LoginBean();
        loginBean.setData(sp.getString("result",""));
        return loginBean;
    }

    /*判断用户是否为空*/
    public boolean hasUserInfo(Context context){
        LoginBean userInfo = getUserInfo(context);
        if(userInfo!=null){
            if (!TextUtils.isEmpty(userInfo.getData().toString())) {//有数据
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
}
