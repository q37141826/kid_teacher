package cn.dajiahui.kidteacher.util;

import java.io.Serializable;

/**
 * Created by z on 2016/3/10.
 * bena对象基类
 */
public class BeanObj implements Serializable {
    private String objectId;

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

}
