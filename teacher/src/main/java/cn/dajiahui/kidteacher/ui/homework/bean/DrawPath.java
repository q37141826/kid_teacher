package cn.dajiahui.kidteacher.ui.homework.bean;



import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import cn.dajiahui.kidteacher.controller.Constant;


/**
 * Created by lenovo on 2018/1/13.
 */

public class DrawPath implements Serializable {
    int pathColor;
    Point leftPoint = Constant.PointZero;
    Point rightPoint = Constant.PointZero;

    private Map<String, String> myanswerMap = new HashMap<>();


    /*记录坐标点*/
    public DrawPath(Point leftPoint, Point rightPoint) {
        this.leftPoint = leftPoint;
        this.rightPoint = rightPoint;

    }

    public Map<String, String> getMyanswerMap() {
        return myanswerMap;
    }

    public void setMyanswerMap(Map<String, String> myanswerMap) {
        this.myanswerMap = myanswerMap;
    }

    public int getPathColor() {
        return pathColor;
    }

    public void setPathColor(int pathColor) {
        this.pathColor = pathColor;
    }

    public Point getLeftPoint() {
        return leftPoint;
    }

    public void setLeftPoint(Point leftPoint) {
        this.leftPoint = leftPoint;
    }

    public Point getRightPoint() {
        return rightPoint;
    }

    public void setRightPoint(Point point) {
        this.rightPoint = point;
    }

    @Override
    public String toString() {
        return "DrawPath{" +
                "pathColor=" + pathColor +
                ", leftPoint=" + leftPoint +
                ", rightPoint=" + rightPoint +
                ", myanswerMap=" + myanswerMap +
                '}';
    }
}
