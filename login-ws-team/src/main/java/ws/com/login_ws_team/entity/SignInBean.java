package ws.com.login_ws_team.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SignInBean {


    @SerializedName("msg")
    private String msg;
    @SerializedName("flag")
    private String flag;
    @SerializedName("jpdetail")
    private List<JpdetailBean> jpdetail;
    @SerializedName("nowFlag")
    private Boolean nowFlag;
    @SerializedName("signDate")
    private List<SignDateBean> signDate;
    @SerializedName("points")
    private Integer points;
    @SerializedName("nowTime")
    private String nowTime;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public List<JpdetailBean> getJpdetail() {
        return jpdetail;
    }

    public void setJpdetail(List<JpdetailBean> jpdetail) {
        this.jpdetail = jpdetail;
    }

    public Boolean getNowFlag() {
        return nowFlag;
    }

    public void setNowFlag(Boolean nowFlag) {
        this.nowFlag = nowFlag;
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

    public String getNowTime() {
        return nowTime;
    }

    public void setNowTime(String nowTime) {
        this.nowTime = nowTime;
    }

    public static class JpdetailBean {
        @SerializedName("month")
        private Integer month;
        @SerializedName("year")
        private Integer year;
        @SerializedName("continuityNum")
        private Integer continuityNum;
        @SerializedName("num")
        private Integer num;
        @SerializedName("day")
        private Integer day;

        public Integer getMonth() {
            return month;
        }

        public void setMonth(Integer month) {
            this.month = month;
        }

        public Integer getYear() {
            return year;
        }

        public void setYear(Integer year) {
            this.year = year;
        }

        public Integer getContinuityNum() {
            return continuityNum;
        }

        public void setContinuityNum(Integer continuityNum) {
            this.continuityNum = continuityNum;
        }

        public Integer getNum() {
            return num;
        }

        public void setNum(Integer num) {
            this.num = num;
        }

        public Integer getDay() {
            return day;
        }

        public void setDay(Integer day) {
            this.day = day;
        }
    }

    public static class SignDateBean {
        @SerializedName("month")
        private Integer month;
        @SerializedName("year")
        private Integer year;
        @SerializedName("day")
        private Integer day;

        public Integer getMonth() {
            return month;
        }

        public void setMonth(Integer month) {
            this.month = month;
        }

        public Integer getYear() {
            return year;
        }

        public void setYear(Integer year) {
            this.year = year;
        }

        public Integer getDay() {
            return day;
        }

        public void setDay(Integer day) {
            this.day = day;
        }
    }
}
