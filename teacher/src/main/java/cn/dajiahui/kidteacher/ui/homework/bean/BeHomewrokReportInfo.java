package cn.dajiahui.kidteacher.ui.homework.bean;

import java.util.List;

import cn.dajiahui.kidteacher.util.BeanObj;

/**
 * 作业报告首页中详细信息bean
 * Created by wangzhi on 2018/2/6.
 */
public class BeHomewrokReportInfo extends BeanObj {
    private String status;
    private String status_key;
    private String total;
    private List<BeHomewrokStudent> lists;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus_key() {
        return status_key;
    }

    public void setStatus_key(String status_key) {
        this.status_key = status_key;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<BeHomewrokStudent> getLists() {
        return lists;
    }

    public void setLists(List<BeHomewrokStudent> lists) {
        this.lists = lists;
    }
}
