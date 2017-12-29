package cn.dajiahui.kidteacher.ui.homework.bean;

import cn.dajiahui.kidteacher.util.BeanObj;

/**
 * 选择班级
 */

public class ChooseClass extends BeanObj {

    private String classname;

    public ChooseClass(String classname) {
        this.classname = classname;
    }


    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }


}
