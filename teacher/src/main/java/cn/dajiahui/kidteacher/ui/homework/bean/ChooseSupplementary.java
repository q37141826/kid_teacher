package cn.dajiahui.kidteacher.ui.homework.bean;

import java.util.List;

import cn.dajiahui.kidteacher.util.BeanObj;

/**
 * 教辅Bean
 */

public class ChooseSupplementary extends BeanObj {
    int totalRows;
    List<BeWorkBook> lists;

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    public List<BeWorkBook> getLists() {
        return lists;
    }

    public void setLists(List<BeWorkBook> lists) {
        this.lists = lists;
    }
}
