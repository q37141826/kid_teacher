package cn.dajiahui.kidteacher.ui.homework.bean;

import cn.dajiahui.kidteacher.util.BeanObj;

/**
 * Created by wangzhi on 2018/2/5.
 */

public class BeHomeworkStatus extends BeanObj {
    private String value;
    private String label;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
