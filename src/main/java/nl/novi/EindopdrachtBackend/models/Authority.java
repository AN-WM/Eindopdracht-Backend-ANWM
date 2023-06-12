package nl.novi.EindopdrachtBackend.models;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@IdClass(AuthorityKey.class)
@Table(name = "authorities")
public class Authority implements Serializable {

    @Id
    @Column(nullable = false)
    private Long employeeId;

    @Id
    @Column(nullable = false)
    private String authority;

    public Authority() {}
    public Authority(Long employeeId, String authority) {
        this.employeeId = employeeId;
        this.authority = authority;
    }

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