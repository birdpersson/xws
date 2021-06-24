package xws.auth.domain;

import javax.persistence.*;

@Entity
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String username;

    @Column
    private String repUsername;

    @Column
    private String reason;

    public Report(String username,String repUsername,String reason) {

        this.reason = reason;
        this.username = username;
        this.repUsername = repUsername;

    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRepUsername() {
        return repUsername;
    }

    public void setRepUsername(String repUsername) {
        this.repUsername = repUsername;
    }
}
