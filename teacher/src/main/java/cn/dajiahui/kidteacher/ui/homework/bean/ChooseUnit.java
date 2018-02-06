package cn.dajiahui.kidteacher.ui.homework.bean;

import java.util.List;

import cn.dajiahui.kidteacher.util.BeanObj;

/**
 * 选择单元
 */

public class ChooseUnit extends BeanObj {
    List<BeUnit> data;

    public List<BeUnit> getData() {
        return data;
    }

    public void setData(List<BeUnit> data) {
        this.data = data;
    }
}
