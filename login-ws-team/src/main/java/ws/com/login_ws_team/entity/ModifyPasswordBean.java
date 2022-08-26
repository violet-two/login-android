package ws.com.login_ws_team.entity;

import com.google.gson.annotations.SerializedName;

public class ModifyPasswordBean {
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
