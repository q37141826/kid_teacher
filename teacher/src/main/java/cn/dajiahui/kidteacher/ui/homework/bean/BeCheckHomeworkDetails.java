package cn.dajiahui.kidteacher.ui.homework.bean;

import java.util.List;

import cn.dajiahui.kidteacher.util.BeanObj;

/**
 * Created by wangzhi on 2018/2/7.
 */

public class BeCheckHomeworkDetails extends BeanObj {
    private String homework_id;
    private String totalQues;
    private String totalUsers;
    private List<BeCheckHomeworkQuestionDetails> list;

    public String getHomework_id() {
        return homework_id;
    }

    public void setHomework_id(String homework_id) {
        this.homework_id = homework_id;
    }

    public String getTotalQues() {
        return totalQues;
    }

    public void setTotalQues(String totalQues) {
        this.totalQues = totalQues;
    }

    public String getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(String totalUsers) {
        this.totalUsers = totalUsers;
    }

    public List<BeCheckHomeworkQuestionDetails> getList() {
        return list;
    }

    public void setList(List<BeCheckHomeworkQuestionDetails> list) {
        this.list = list;
    }
}
