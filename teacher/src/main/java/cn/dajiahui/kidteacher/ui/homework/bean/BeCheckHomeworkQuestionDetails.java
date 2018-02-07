package cn.dajiahui.kidteacher.ui.homework.bean;

import cn.dajiahui.kidteacher.util.BeanObj;

/**
 * Created by wangzhi on 2018/2/7.
 */

public class BeCheckHomeworkQuestionDetails extends BeanObj {
    private String qId;
    private String correctRate;
    private String completeCnt;
    private String totalUsers;

    public String getqId() {
        return qId;
    }

    public void setqId(String qId) {
        this.qId = qId;
    }

    public String getCorrectRate() {
        return correctRate;
    }

    public void setCorrectRate(String correctRate) {
        this.correctRate = correctRate;
    }

    public String getCompleteCnt() {
        return completeCnt;
    }

    public void setCompleteCnt(String completeCnt) {
        this.completeCnt = completeCnt;
    }

    public String getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(String totalUsers) {
        this.totalUsers = totalUsers;
    }
}
