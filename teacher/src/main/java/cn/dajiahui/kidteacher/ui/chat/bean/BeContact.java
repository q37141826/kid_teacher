package cn.dajiahui.kidteacher.ui.chat.bean;

import java.util.List;

import cn.dajiahui.kidteacher.util.BeanObj;

/**
 * Created by wdj on 2016/4/6.
 */
public class BeContact extends BeanObj {
    private String name;
    private List<BeGroupListUsers> groupList;
    private List<BeGroupListUsers> classList;
    private String phone;

    public String getPhone() {
        return phone;
    }

    public String getName() {
        return name;
    }

    public List<BeGroupListUsers> getGroupList() {
        return groupList;
    }

    public List<BeGroupListUsers> getClassList() {
        return classList;
    }

}
