package cn.dajiahui.kidteacher.ui.homework.myinterface;


import cn.dajiahui.kidteacher.ui.homework.bean.BeLocation;
import cn.dajiahui.kidteacher.ui.homework.view.MoveImagview;

/**
 * Created by lenovo on 2018/1/17.
 */

public interface MoveLocation {
    /*可拖动视图的中心点x y*/
    public BeLocation submitCenterPoint(MoveImagview moveImagview, int position, float X, float Y);
}
