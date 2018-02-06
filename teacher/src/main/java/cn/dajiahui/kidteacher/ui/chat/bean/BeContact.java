package cn.dajiahui.kidteacher.ui.chat.bean;

import java.util.List;

import cn.dajiahui.kidteacher.util.BeanObj;

/**
 * Created by wdj on 2016/4/6.
 */
public class BeContact extends BeanObj {
    private String name;
    private String classNmae; // 班名
    private List<BeGroupListUsers> groupList;
    private List<BeGroupListUsers> classList;

    private String phone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassNmae() {
        return classNmae;
    }

    public void setClassNmae(String classNmae) {
        this.classNmae = classNmae;
    }

    public List<BeGroupListUsers> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<BeGroupListUsers> groupList) {
        this.groupList = groupList;
    }

    public List<BeGroupListUsers> getClassList() {
        return classList;
    }

    public void setClassList(List<BeGroupListUsers> classList) {
        this.classList = classList;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
