package ws.com.login_ws_team.util;

import java.io.Serializable;

public class InformationDPUtil implements Serializable {
    private String name;
    private String phone;
    private String department;
    private String role;

    public InformationDPUtil(String name, String phone, String department, String role) {
        this.name = name;
        this.phone = phone;
        this.department = department;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
