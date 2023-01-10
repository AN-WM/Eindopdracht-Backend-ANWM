package nl.novi.EindopdrachtBackend.models;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name="users")
public class User {
    @Id
    private String username;
    private String password;


    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Authority> authorityCollection;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<Authority> getAuthorityCollection() {
        return authorityCollection;
    }

    public void setAuthorityCollection(Collection<Authority> authorityCollection) {
        this.authorityCollection = authorityCollection;
    }
}
