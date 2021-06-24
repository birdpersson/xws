package xws.auth.dto;

import xws.auth.domain.User;

import javax.persistence.*;

public class ReportDTO {

    private String username;

    private String repUsername;

    private String reason;

    public  ReportDTO(){}
    public ReportDTO( String username,String repUsername, String reason) {

        this.reason = reason;
        this.username = username;
        this.repUsername = repUsername;
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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
