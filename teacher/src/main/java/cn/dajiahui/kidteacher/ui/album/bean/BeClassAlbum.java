package cn.dajiahui.kidteacher.ui.album.bean;

import com.fxtx.framework.text.ParseUtil;

import java.util.ArrayList;
import java.util.List;

import cn.dajiahui.kidteacher.util.BeanObj;

/**
 * Created by z on 2016/3/11.
 */
public class BeClassAlbum extends BeanObj {
    private String isMyClass;
    private String className;
    private List<BeAlbum> list;

    public int getIsMyClass() {
        return ParseUtil.parseInt(isMyClass);
    }

    public String getClassName() {
        return className;
    }

    public List<BeAlbum> getList() {
        if (list == null) {
            list = new ArrayList<BeAlbum>();
        }
        return list;
    }
}
