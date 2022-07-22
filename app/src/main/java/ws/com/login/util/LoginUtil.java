package ws.com.login.util;

import com.google.gson.annotations.SerializedName;

public class LoginUtil {
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

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
