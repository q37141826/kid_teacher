package cn.dajiahui.kidteacher.ui.homework.bean;

import java.util.List;

import cn.dajiahui.kidteacher.util.BeanObj;

/**
 * 选择班级
 */

public class ChooseClass extends BeanObj {

    int totalRows;
    List<BeHomewrokClass> lists;


    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    public List<BeHomewrokClass> getLists() {
        return lists;
    }

    public void setLists(List<BeHomewrokClass> lists) {
        this.lists = lists;
    }
}
