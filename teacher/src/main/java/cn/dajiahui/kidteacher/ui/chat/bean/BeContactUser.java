package cn.dajiahui.kidteacher.ui.chat.bean;

import cn.dajiahui.kidteacher.util.BeanObj;

/**
 * Created by wdj on 2016/4/6.
 */
public class BeContactUser extends BeanObj {
    private String avator;
    private String hxId;
    private String hxPwd;
    private String realName;
    private String sex;
    private String userName;
    private String phone;

    public String getPhone() {
        return phone;
    }

    public String getAvator() {
        return avator;
    }

    public String getHxId() {
        return hxId;
    }

    public String getHxPwd() {
        return hxPwd;
    }

    public String getRealName() {
        return realName;
    }

    public String getSex() {
        return sex;
    }

    public String getUserName() {
        return userName;
    }

}
