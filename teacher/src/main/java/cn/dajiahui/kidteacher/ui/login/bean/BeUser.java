package cn.dajiahui.kidteacher.ui.login.bean;

import com.fxtx.framework.text.StringUtil;

import java.util.List;

import cn.dajiahui.kidteacher.util.BeanObj;

/**
 * 用户对象
 */
public class BeUser extends BeanObj {
    private String token;
    private String user_id;
    private String user_type;//用户类型


    private String avator;
    private String email;

    private String phone;
    private String realName;
    private String receiveMsgTel;
    private String sex;
    private String userName;

    private String hxId;
    private String hxPwd;
    private String birthday;
    private String signature;
    private String pwd;
    private String schoolId;//学校id
    private List<String> authList;
    private String loadUrl;
    private String logoUrl;
    private String cname;

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getSignature() {
        return "";//将签名值设置为空，这样永远都不会显示出签名了
    }

    public String getHxId() {
        return hxId;
    }

    public String getHxPwd() {
        return hxPwd;
    }

    public String getAvator() {
        return avator;
    }

    public void setAvator(String avator) {
        this.avator = avator;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getuser_id() {
        return user_id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRealName() {
        if (StringUtil.isEmpty(realName))
            realName = getUserName();
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getReceiveMsgTel() {
        return receiveMsgTel;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getUserName() {
        if (StringUtil.isEmpty(userName))
            userName = "";
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getuser_type() {
        return user_type;
    }

    public String gettoken() {
        return token;
    }

    public void setId(String objectId) {
        setObjectId(objectId);
    }

    public String getSchoolId() {
        return schoolId;
    }

    public List<String> getAuthList() {
        return authList;
    }

    public String getLoadUrl() {
        return loadUrl;
    }

    public String getLogoUrl() {
//        return "http://img03.sogoucdn.com/app/a/100520024/6cabb1c8dc0ef86dbff3e87e2269d6a5";
        return logoUrl;
    }

    public String getOrglName() {
//        return  "dksdka";
        return cname;
    }
}
