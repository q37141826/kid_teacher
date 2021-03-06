package cn.dajiahui.kidteacher.ui.login.bean;

import cn.dajiahui.kidteacher.util.BeanObj;

/**
 * 用户对象
 */
public class BeUser extends BeanObj {
    private String token;
    private String user_id;
    private String user_type;//用户类型
    private String avatar;
    private String telnum;
    private String nickname;
    private String gender;
    private String birthday;
    private String pwd;
    private String school_name;

    public String getSchool_name() {
        return school_name;
    }

    public void setSchool_name(String school_name) {
        this.school_name = school_name;
    }

    private BeUserThrid third;//解析第三方注册登录信息

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getTelnum() {
        return telnum;
    }

    public void setTelnum(String telnum) {
        this.telnum = telnum;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public BeUserThrid getThird() {
        return third;
    }

    public void setThird(BeUserThrid third) {
        this.third = third;
    }
}
