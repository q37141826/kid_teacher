package cn.dajiahui.kidteacher.ui.mine.bean;

import cn.dajiahui.kidteacher.util.BeanObj;

/**
 * Created by lenovo on 2017/12/27.
 */

public class BeMyclassInfo extends BeanObj {


    private String imghead;
    private String studentname;

    public BeMyclassInfo(String imghead, String studentname) {


        this.imghead = imghead;
        this.studentname = studentname;
    }

    public String getImghead() {
        return imghead;
    }

    public void setImghead(String imghead) {
        this.imghead = imghead;
    }

    public String getStudentname() {
        return studentname;
    }

    public void setStudentname(String studentname) {
        this.studentname = studentname;
    }


}
