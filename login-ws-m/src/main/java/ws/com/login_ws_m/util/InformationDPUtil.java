package ws.com.login_ws_m.util;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class InformationDPUtil<T> implements Serializable {

    @SerializedName("data")
    private Object data;
    @SerializedName("flag")
    private String flag;
    @SerializedName("page")
    private PageBean page;

    public Object getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public static class PageBean implements Serializable{
        @SerializedName("pageSize")
        private Integer pageSize;
        @SerializedName("startPage")
        private Integer startPage;
        @SerializedName("total")
        private Integer total;

        public Integer getPageSize() {
            return pageSize;
        }

        public void setPageSize(Integer pageSize) {
            this.pageSize = pageSize;
        }

        public Integer getStartPage() {
            return startPage;
        }

        public void setStartPage(Integer startPage) {
            this.startPage = startPage;
        }

        public Integer getTotal() {
            return total;
        }

        public void setTotal(Integer total) {
            this.total = total;
        }
    }

    public static class DataBean implements Serializable{

        public DataBean(String regname, String userStatus, String phone, String department) {
            this.regname = regname;
            this.userStatus = userStatus;
            this.phone = phone;
            this.department = department;
        }

        @SerializedName("regname")
        private String regname;
        @SerializedName("userStatus")
        private String userStatus;
        @SerializedName("phone")
        private String phone;
        @SerializedName("department")
        private String department;

        public String getRegname() {
            return regname;
        }

        public void setRegname(String regname) {
            this.regname = regname;
        }

        public String getUserStatus() {
            return userStatus;
        }

        public void setUserStatus(String userStatus) {
            this.userStatus = userStatus;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }
    }
}
