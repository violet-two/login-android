package ws.com.login_ws_team.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SignInBean {

    @SerializedName("flag")
    private String flag;
    @SerializedName("msg")
    private String msg;
    @SerializedName("num")
    private Integer num;
    @SerializedName("signDate")
    private List<SignDateBean> signDate;
    @SerializedName("points")
    private Integer points;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public List<SignDateBean> getSignDate() {
        return signDate;
    }

    public void setSignDate(List<SignDateBean> signDate) {
        this.signDate = signDate;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public static class SignDateBean {
        @SerializedName("month")
        private String month;
        @SerializedName("year")
        private String year;
        @SerializedName("day")
        private String day;

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }
    }
}
