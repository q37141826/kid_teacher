package cn.dajiahui.kidteacher.ui.mine.bean;

import cn.dajiahui.kidteacher.util.BeanObj;

/**
 * 学生详情
 * Created by wangzhi on 2018/1/31.
 */

public class BeStudents extends BeanObj {
    String id;
    String nickname;
    String username;
    String avatar;
    String org_id;
    String user_type;
    String easemob_username;
    String easemob_passwd;
    String easemob_nikname;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getOrg_id() {
        return org_id;
    }

    public void setOrg_id(String org_id) {
        this.org_id = org_id;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getEasemob_username() {
        return easemob_username;
    }

    public void setEasemob_username(String easemob_username) {
        this.easemob_username = easemob_username;
    }

    public String getEasemob_passwd() {
        return easemob_passwd;
    }

    public void setEasemob_passwd(String easemob_passwd) {
        this.easemob_passwd = easemob_passwd;
    }

    public String getEasemob_nikname() {
        return easemob_nikname;
    }

    public void setEasemob_nikname(String easemob_nikname) {
        this.easemob_nikname = easemob_nikname;
    }
}
