package entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {
    @Id
    private String email;
    private String username, password, avatar = "default.png";
    private Integer acNum = 0, waNum = 0, tleNum = 0, mleNum = 0, reNum = 0, ceNum = 0;

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }

    public Integer getAcNum() {
        return acNum;
    }

    public Integer getCeNum() {
        return ceNum;
    }

    public Integer getMleNum() {
        return mleNum;
    }

    public Integer getReNum() {
        return reNum;
    }

    public Integer getTleNum() {
        return tleNum;
    }

    public Integer getWaNum() {
        return waNum;
    }

    public void setAcNum(Integer acNum) {
        this.acNum = acNum;
    }

    public void setCeNum(Integer ceNum) {
        this.ceNum = ceNum;
    }

    public void setMleNum(Integer mleNum) {
        this.mleNum = mleNum;
    }

    public void setReNum(Integer reNum) {
        this.reNum = reNum;
    }

    public void setTleNum(Integer tleNum) {
        this.tleNum = tleNum;
    }

    public void setWaNum(Integer waNum) {
        this.waNum = waNum;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
