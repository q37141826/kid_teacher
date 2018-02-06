package cn.dajiahui.kidteacher.ui.homework.bean;

import java.util.List;

import cn.dajiahui.kidteacher.util.BeanObj;

/**
 * 作业报告
 */

public class HomeworkReport extends BeanObj {
    private String id;
    private String name;
    private String start_time;
    private String end_time;
    private String class_name;
    private String correct_rate;
    private String all_students;
    private String complete_students;
    private List<BeHomewrokReportInfo> student_list;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public String getCorrect_rate() {
        return correct_rate;
    }

    public void setCorrect_rate(String correct_rate) {
        this.correct_rate = correct_rate;
    }

    public String getAll_students() {
        return all_students;
    }

    public void setAll_students(String all_students) {
        this.all_students = all_students;
    }

    public String getComplete_students() {
        return complete_students;
    }

    public void setComplete_students(String complete_students) {
        this.complete_students = complete_students;
    }

    public List<BeHomewrokReportInfo> getStudent_list() {
        return student_list;
    }

    public void setStudent_list(List<BeHomewrokReportInfo> student_list) {
        this.student_list = student_list;
    }
}
