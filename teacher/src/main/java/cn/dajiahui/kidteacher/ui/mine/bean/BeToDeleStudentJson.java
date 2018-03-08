package cn.dajiahui.kidteacher.ui.mine.bean;

import java.util.List;

/**
 * Created by mj on 2018/3/7.
 */

public class BeToDeleStudentJson {
    private List<BeDelateStudent> list;

    public BeToDeleStudentJson(List<BeDelateStudent> list) {
        this.list = list;
    }

    public List<BeDelateStudent> getlist() {
        return list;
    }

    public void setlist(List<BeDelateStudent> list) {
        this.list = list;
    }
}
