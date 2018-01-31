package cn.dajiahui.kidteacher.ui.mine.bean;

import java.util.List;

import cn.dajiahui.kidteacher.util.BeanObj;

/**
 * 班级详情
 * Created by lenovo on 2017/12/27.
 */

public class BeMyclassInfo extends BeanObj {
    String id;
    String class_name;
    String max_students_num;
    String students_num;
    String teacher_id;
    String teacher_name;
    String code;
    List<BeStudents> student_list;

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

    public String getMax_students_num() {
        return max_students_num;
    }

    public void setMax_students_num(String max_students_num) {
        this.max_students_num = max_students_num;
    }

    public String getStudents_num() {
        return students_num;
    }

    public void setStudents_num(String students_num) {
        this.students_num = students_num;
    }

    public String getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(String teacher_id) {
        this.teacher_id = teacher_id;
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

    public List<BeStudents> getStudent_list() {
        return student_list;
    }

    public void setStudent_list(List<BeStudents> student_list) {
        this.student_list = student_list;
    }
}
