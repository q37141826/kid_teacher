package cn.dajiahui.kidteacher.ui.mine.bean;

/**
 * Created by mj on 2018/3/7.
 * 删除学员
 */

public class BeDelateStudent {
    private String user_id;
    private String class_id;

    public BeDelateStudent(String user_id, String class_id) {
        this.user_id = user_id;
        this.class_id = class_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    @Override
    public String toString() {
        return "BeDelateStudent{" +
                "user_id='" + user_id + '\'' +
                ", class_id='" + class_id + '\'' +
                '}';
    }
}
