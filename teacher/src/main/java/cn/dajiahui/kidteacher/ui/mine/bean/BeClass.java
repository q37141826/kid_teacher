package cn.dajiahui.kidteacher.ui.mine.bean;

import cn.dajiahui.kidteacher.util.BeanObj;

/**
 * Created by lenovo on 2017/12/27.
 */

public class BeClass extends BeanObj {
    private String id;              // 班ID
    private String class_name;      // 班名
    private String students_num;    // 学生数
    private String grade;           // 年级
    private String teacher_name;    // 老师姓名
    private String code;            // 班级码
    private String grade_name;      // 年级

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public String getStudents_num() {
        return students_num;
    }

    public void setStudents_num(String students_num) {
        this.students_num = students_num;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getTeacher_name() {
        return teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getGrade_name() {
        return grade_name;
    }

    public void setGrade_name(String grade_name) {
        this.grade_name = grade_name;
    }
}
