package cn.dajiahui.kidteacher.ui.homework.bean;

import cn.dajiahui.kidteacher.util.BeanObj;

/**
 * 作业（首页）中作业列表中的作业bean
 * Created by wangzhi on 2018/2/5.
 */

public class BeHomewrok extends BeanObj {
    private String id;  // 作业ID
    private String class_id;  // 班级ID
    private String book_id;  // 教辅ID
    private String unit_id;  // 单元ID
    private String name;  // 作业名
    private String is_checked;  // 老师是否检查了
    private String start_time;  // 开始时间
    private String end_time;  // 截止时间
    private String class_name;  // 班级名
    private String all_student;  // 班级学生
    private String complete_students;  // 完成学生

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    public String getBook_id() {
        return book_id;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }

    public String getUnit_id() {
        return unit_id;
    }

    public void setUnit_id(String unit_id) {
        this.unit_id = unit_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIs_checked() {
        return is_checked;
    }

    public void setIs_checked(String is_checked) {
        this.is_checked = is_checked;
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

    public String getAll_student() {
        return all_student;
    }

    public void setAll_student(String all_student) {
        this.all_student = all_student;
    }

    public String getComplete_students() {
        return complete_students;
    }

    public void setComplete_students(String complete_students) {
        this.complete_students = complete_students;
    }
}
