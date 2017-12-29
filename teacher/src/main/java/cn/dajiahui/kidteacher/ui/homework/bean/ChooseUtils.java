package cn.dajiahui.kidteacher.ui.homework.bean;

import cn.dajiahui.kidteacher.util.BeanObj;

/**
 * 选择单元
 */

public class ChooseUtils extends BeanObj {
    public ChooseUtils(String utlisname, String comnent) {
        this.utlisname = utlisname;
        this.comnent = comnent;
    }

    private String utlisname;
    private String comnent;

    public String getUtlisname() {
        return utlisname;
    }

    public void setUtlisname(String utlisname) {
        this.utlisname = utlisname;
    }

    public String getComnent() {
        return comnent;
    }

    public void setComnent(String comnent) {
        this.comnent = comnent;
    }


}
