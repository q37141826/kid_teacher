package cn.dajiahui.kidteacher.ui.homework.view;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

import cn.dajiahui.kidteacher.ui.homework.adapter.Dir;
import cn.dajiahui.kidteacher.ui.homework.bean.DrawPath;

import static cn.dajiahui.kidteacher.controller.Constant.lineWidth;


/**
 * Created by lenovo on 2018/1/13.
 * 画线的view
 */

public class DrawView extends View {

    Context context;
    DrawPath path;
    LineImagePointView currentSelectedView;
    LineImagePointView lineImagePointView;
    private Paint mPaint;

    public DrawView(Context context, int color) {
        super(context);
        this.context = context;
        this.mPaint = new Paint();
        mPaint.setColor(color);

    }

    public DrawView(Context context) {
        super(context);
        this.context = context;
        this.mPaint = new Paint();
        mPaint.setStrokeWidth(lineWidth);

    }

    /*翻页后回来后要绘制的坐标点*/
    public void DrawViewOnback(DrawPath drawPath) {
        this.path = drawPath;
    }

    /*点击画线调取*/
    @SuppressLint("ResourceAsColor")
    public DrawView(Context context, LineImagePointView currentSelectedView, LineImagePointView lineImagePointView,int color) {
        super(context);
        this.context = context;
        this.currentSelectedView = currentSelectedView;
        this.lineImagePointView = lineImagePointView;
        this.path = getPath(currentSelectedView, lineImagePointView);
        this.mPaint = new Paint();
        mPaint.setColor(color );
    }

    /*获取起始坐标点的路径*/
    private DrawPath getPath(LineImagePointView currentSelectedView, LineImagePointView lineImagePointView) {

        Map<String, String> answerMap = new HashMap<String, String>();
        /*获取起始坐标的path路径*/
        if (currentSelectedView.getDirection() == Dir.left) {
            path = new DrawPath(currentSelectedView.getPoint(), lineImagePointView.getPoint());
            answerMap.put(currentSelectedView.value, lineImagePointView.value);
        } else {
            path = new DrawPath(lineImagePointView.getPoint(), currentSelectedView.getPoint());
            answerMap.put(lineImagePointView.value, currentSelectedView.value);
        }

        return path;
    }

    public DrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setAntiAlias(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(lineWidth);

        if (path != null && mPaint != null) {
            canvas.drawLine(path.getLeftPoint().x, path.getLeftPoint().y, path.getRightPoint().x, path.getRightPoint().y, mPaint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(widthMeasureSpec);

        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(200, 600);
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(200, heightSpecSize);
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSpecSize, 600);
        }
    }
}
