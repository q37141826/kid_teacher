package cn.dajiahui.kidteacher.ui.homework.bean;

import cn.dajiahui.kidteacher.util.BeanObj;

/**
 * 作业报告
 */

public class HomeworkReport extends BeanObj {

    private String imgpic;
    private String studentname;
    private String submittime;
    private String correctrate;


    public HomeworkReport(String studentname, String submittime, String correctrate) {
        this.studentname = studentname;
        this.submittime = submittime;
        this.correctrate = correctrate;
    }


    public String getImgpic() {
        return imgpic;
    }

    public void setImgpic(String imgpic) {
        this.imgpic = imgpic;
    }

    public String getStudentname() {
        return studentname;
    }

    public void setStudentname(String studentname) {
        this.studentname = studentname;
    }

    public String getSubmittime() {
        return submittime;
    }

    public void setSubmittime(String submittime) {
        this.submittime = submittime;
    }

    public String getCorrectrate() {
        return correctrate;
    }

    public void setCorrectrate(String correctrate) {
        this.correctrate = correctrate;
    }


}
