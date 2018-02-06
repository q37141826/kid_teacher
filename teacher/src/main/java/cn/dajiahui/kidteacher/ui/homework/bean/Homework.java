package cn.dajiahui.kidteacher.ui.homework.bean;

import java.util.List;

import cn.dajiahui.kidteacher.util.BeanObj;

/**
 * 作业（首页）
 */

public class Homework extends BeanObj {

    private int totalRows;
    private List<BeHomeworkList> lists;

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    public List<BeHomeworkList> getLists() {
        return lists;
    }

    public void setLists(List<BeHomeworkList> lists) {
        this.lists = lists;
    }

/*作业列表中是待检查和未检查 start*/

    private String task_check;//是否检查
    public Homework(String task_check) {
        this.task_check = task_check;
    }

    public String getTask_check() {
        return task_check;
    }

    public void setTask_check(String task_check) {
        this.task_check = task_check;
    }
    /*作业列表中是待检查和未检查 end*/

    private String test_first_name;

    private String task_second_class_name;//班级名称
    private String task_second_time;//截止时间
    private String task_second_content;//内容
    private String test_second_status;//状态
    private String task_second_complete;//是否完成

    public Homework(String test_first_name, String task_second_class_name, String task_second_time, String task_second_content, String test_second_status, String task_second_complete) {
        this.test_first_name = test_first_name;
        this.task_second_class_name = task_second_class_name;
        this.task_second_time = task_second_time;
        this.task_second_content = task_second_content;
        this.test_second_status = test_second_status;
        this.task_second_complete = task_second_complete;
    }


    public String getTest_first_name() {
        return test_first_name;
    }

    public void setTest_first_name(String test_first_name) {
        this.test_first_name = test_first_name;
    }

    public String getTask_second_class_name() {
        return task_second_class_name;
    }

    public void setTask_second_class_name(String task_second_class_name) {
        this.task_second_class_name = task_second_class_name;
    }

    public String getTask_second_time() {
        return task_second_time;
    }

    public void setTask_second_time(String task_second_time) {
        this.task_second_time = task_second_time;
    }

    public String getTask_second_content() {
        return task_second_content;
    }

    public void setTask_second_content(String task_second_content) {
        this.task_second_content = task_second_content;
    }

    public String getTest_second_status() {
        return test_second_status;
    }

    public void setTest_second_status(String test_second_status) {
        this.test_second_status = test_second_status;
    }

    public String getTask_second_complete() {
        return task_second_complete;
    }

    public void setTask_second_complete(String task_second_complete) {
        this.task_second_complete = task_second_complete;
    }


}
