package cn.dajiahui.kidteacher.ui.mine.bean;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.List;

import cn.dajiahui.kidteacher.util.BeanObj;

/**
 * Created by mj on 2017/12/28.
 */

public class BeClassSpace extends BeanObj implements Serializable {

    private List<BeClassSpaceList> list;
    private String totalRows;
    private Bitmap bitmap;

    public BeClassSpace(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public List<BeClassSpaceList> getList() {
        return list;
    }

    public void setList(List<BeClassSpaceList> list) {
        this.list = list;
    }

    public String getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(String totalRows) {
        this.totalRows = totalRows;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }


}
