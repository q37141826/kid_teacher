package cn.dajiahui.kidteacher.ui.homework.bean;

import cn.dajiahui.kidteacher.util.BeanObj;

/**
 * Created by wangzhi on 2018/2/2.
 */

public class BeHomewrokClass extends BeanObj {
    private String id;
    private String class_name;
    private String is_pubed;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public String getIs_pubed() {
        return is_pubed;
    }

    public void setIs_pubed(String is_pubed) {
        this.is_pubed = is_pubed;
    }
}
