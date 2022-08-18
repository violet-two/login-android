package ws.com.login_ws_team.util;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class LoginUtil implements Serializable {

    @SerializedName("data")
    private List<DataBean> data;
    @SerializedName("flag")
    private String flag;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public static class DataBean {
        @SerializedName("regname")
        private String regname;
        @SerializedName("phone")
        private String phone;
        @SerializedName("deptId")
        private Integer deptId;
        @SerializedName("id")
        private Integer id;
        @SerializedName("department")
        private String department;
        @SerializedName("status")
        private Integer status;

        public String getRegname() {
            return regname;
        }

        public void setRegname(String regname) {
            this.regname = regname;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public Integer getDeptId() {
            return deptId;
        }

        public void setDeptId(Integer deptId) {
            this.deptId = deptId;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }
    }
}
