package cn.dajiahui.kidteacher.ui.homework.bean;

import android.os.Parcelable;

import java.io.Serializable;

import cn.dajiahui.kidteacher.util.BeanObj;

/**
 * 作业报告首页中学生信息bean
 * Created by wangzhi on 2018/2/6.
 */

public class BeHomewrokStudent extends BeanObj implements Serializable {
    private String id;
    private String nickname;
    private String avatar;
    private String correct_rate;
    private String updated_at;

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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCorrect_rate() {
        return correct_rate;
    }

    public void setCorrect_rate(String correct_rate) {
        this.correct_rate = correct_rate;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
