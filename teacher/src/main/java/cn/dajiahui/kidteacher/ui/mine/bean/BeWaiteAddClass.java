package cn.dajiahui.kidteacher.ui.mine.bean;

import cn.dajiahui.kidteacher.util.BeanObj;

/**
 * 待加入班级的学生
 */

public class BeWaiteAddClass extends BeanObj {

    private String classname;
    private String studentname;
    private String studentpic;

    public BeWaiteAddClass(String classname, String studentname) {
        this.classname = classname;
        this.studentname = studentname;
    }




    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getStudentname() {
        return studentname;
    }

    public void setStudentname(String studentname) {
        this.studentname = studentname;
    }

    public String getStudentpic() {
        return studentpic;
    }

    public void setStudentpic(String studentpic) {
        this.studentpic = studentpic;
    }

}
