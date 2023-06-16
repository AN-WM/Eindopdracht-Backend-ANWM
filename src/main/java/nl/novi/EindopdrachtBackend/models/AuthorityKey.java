package nl.novi.EindopdrachtBackend.models;

import java.io.Serializable;

public class AuthorityKey implements Serializable {
    private Long employeeId;
    private String authority;

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
