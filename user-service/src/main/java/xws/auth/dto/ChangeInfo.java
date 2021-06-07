package xws.auth.dto;

import java.util.Date;

public class ChangeInfo {
    String name;
    String username;
    String email;
    String website;
    String phone;
    Date date;
    String gender;
    String bio;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public ChangeInfo(){}
    public ChangeInfo(String name, String username, String email, String website, String phone, Date date, String gender, String bio){
        this.name = name;
        this.username = username;
        this.email = email;
        this.date = date;
        this.bio = bio;
        this.phone = phone;
        this.gender = gender;
    }
}
