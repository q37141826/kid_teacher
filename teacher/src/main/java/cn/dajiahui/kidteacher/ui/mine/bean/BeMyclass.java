package cn.dajiahui.kidteacher.ui.mine.bean;

import java.util.List;

import cn.dajiahui.kidteacher.util.BeanObj;

/**
 * Created by wangzhi on 2018/1/30.
 */

public class BeMyclass extends BeanObj {
    int totalRows;
    List<BeClass> lists;

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    public List<BeClass> getLists() {
        return lists;
    }

    public void setLists(List<BeClass> lists) {
        this.lists = lists;
    }
}
