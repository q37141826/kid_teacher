package cn.dajiahui.kidteacher.ui.homework.bean;

import cn.dajiahui.kidteacher.util.BeanObj;

/**
 * Created by wangzhi on 2018/2/1.
 */

public class BeUnit extends BeanObj {
    private String id;
    private String name;
    private String seq;
    private String count;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
