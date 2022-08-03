package ws.com.login_ws_team.util;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LoginUtil implements Serializable {
    @SerializedName("data")
    private String data;
    @SerializedName("flag")
    private String flag;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "LoginUtil{" +
                "data='" + data + '\'' +
                ", flag='" + flag + '\'' +
                '}';
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
