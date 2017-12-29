package cn.dajiahui.kidteacher.ui.chat.bean;

import java.util.List;

import cn.dajiahui.kidteacher.util.BeanObj;

/**
 * Created by wdj on 2016/4/6.
 */
public class BeGroupListUsers extends BeanObj {
    private String className;
    private String teacherIds;
    private int userCount;
    private List<BeContactUser>list;
    private String name;

    public String getClassName() {
        return className;
    }

    public String getTeacherIds() {
        return teacherIds;
    }

    public int getUserCount() {
        return userCount;
    }

    public List<BeContactUser> getList() {
        return list;
    }

    public String getName() {
        return name;
    }

}
