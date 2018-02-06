package cn.dajiahui.kidteacher.ui.homework.bean;

import java.util.List;

import cn.dajiahui.kidteacher.ui.mine.bean.BeClass;

/**
 * Created by wangzhi on 2018/2/5.
 */

public class BeClassAndStatus {
    List<BeClass> class_list;
    List<BeHomeworkStatus> homework_status;

    public List<BeClass> getClass_list() {
        return class_list;
    }

    public void setClass_list(List<BeClass> class_list) {
        this.class_list = class_list;
    }

    public List<BeHomeworkStatus> getHomework_status() {
        return homework_status;
    }

    public void setHomework_status(List<BeHomeworkStatus> homework_status) {
        this.homework_status = homework_status;
    }
}
