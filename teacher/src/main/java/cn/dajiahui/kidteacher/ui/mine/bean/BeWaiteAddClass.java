package cn.dajiahui.kidteacher.ui.mine.bean;

import java.util.List;

import cn.dajiahui.kidteacher.util.BeanObj;

/**
 * 待加入班级学生列表
 * Created by wangzhi on 2018/1/31.
 */

public class BeWaiteAddClass extends BeanObj {
    int totalRows;
    List<BeWaiteAddStudent> lists;

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    public List<BeWaiteAddStudent> getLists() {
        return lists;
    }

    public void setLists(List<BeWaiteAddStudent> lists) {
        this.lists = lists;
    }
}
