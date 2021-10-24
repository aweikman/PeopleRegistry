package springboot.model;

import javax.persistence.*;

@Entity
@Table(name = "session")

public class Sessions {
    @Id
    @Column(name = "token", nullable = false)
    private String token;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false, length = 100)
    private int userId;

    public Sessions() {}

    @Override
    public String toString() {
        return getToken();
    }

    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }
    public void setToken(String token){
        this.token = token;
    }

}
