package cn.dajiahui.kidteacher.ui.chat.bean;

import java.util.List;

import cn.dajiahui.kidteacher.util.BeanObj;

/**
 * Created by wdj on 2016/4/6.
 */
public class BeGroupListUsers extends BeanObj {
    private String class_name;
    private List<BeContactUser>student_list;

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public List<BeContactUser> getStudent_list() {
        return student_list;
    }

    public void setStudent_list(List<BeContactUser> student_list) {
        this.student_list = student_list;
    }
}
