package cn.dajiahui.kidteacher.ui.mine.bean;

import cn.dajiahui.kidteacher.util.BeanObj;

/**
 * Created by lenovo on 2017/12/27.
 */

public class BeMyclass extends BeanObj {

    private String myclass;
    private String classum;
    private String studentnum;


    public BeMyclass(String myclass, String classum, String studentnum) {
        this.myclass = myclass;
        this.classum = classum;
        this.studentnum = studentnum;
    }

    public String getMyclass() {
        return myclass;
    }

    public void setMyclass(String myclass) {
        this.myclass = myclass;
    }

    public String getClassum() {
        return classum;
    }

    public void setClassum(String classum) {
        this.classum = classum;
    }

    public String getStudentnum() {
        return studentnum;
    }

    public void setStudentnum(String studentnum) {
        this.studentnum = studentnum;
    }

}
