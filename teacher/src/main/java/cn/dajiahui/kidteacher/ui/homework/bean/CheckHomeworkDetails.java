package cn.dajiahui.kidteacher.ui.homework.bean;

import cn.dajiahui.kidteacher.util.BeanObj;

/**
 * 检查作业详情
 */

public class CheckHomeworkDetails extends BeanObj {


    private String subject;
    private String accuracy;
    private String complete;


    public CheckHomeworkDetails(String subject, String accuracy, String complete) {
        this.subject = subject;
        this.accuracy = accuracy;
        this.complete = complete;
    }


    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(String accuracy) {
        this.accuracy = accuracy;
    }

    public String getComplete() {
        return complete;
    }

    public void setComplete(String complete) {
        this.complete = complete;
    }


}
