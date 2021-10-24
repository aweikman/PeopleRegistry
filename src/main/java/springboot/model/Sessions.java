package springboot.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "session")

public class Sessions {
    @Id
    @Column(name = "token", nullable = false)
    private String token;

    @Column(name = "user_id", nullable = false, length = 100)
    private String username;

    public Sessions() {}

    @Override
    public String toString() {
        return getToken();
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }
    public void setToken(String token){
        this.token = token;
    }

}