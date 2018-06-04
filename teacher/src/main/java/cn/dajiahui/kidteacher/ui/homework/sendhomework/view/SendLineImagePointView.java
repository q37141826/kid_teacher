package cn.dajiahui.kidteacher.ui.homework.sendhomework.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.fxtx.framework.util.BaseUtil;


import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.ui.homework.adapter.Dir;
import cn.dajiahui.kidteacher.ui.homework.bean.LineQuestionModle;
import cn.dajiahui.kidteacher.ui.homework.bean.Point;
import cn.dajiahui.kidteacher.ui.homework.view.CricleTextView;

import static cn.dajiahui.kidteacher.controller.Constant.pointViewDiameter;
import static cn.dajiahui.kidteacher.controller.Constant.pointViewDiameter_margin;


/**
 * 连线题视图模型
 */


public class SendLineImagePointView extends RelativeLayout {//implements View.OnClickListener
    private Context context;
    private ImageView imageViewL;
    private ImageView imageViewR;
    private TextView textViewL;
    private TextView textViewR;
    public CricleTextView pointview;
    private int cLeftposiion = 0;
    private LineQuestionModle inbasebean;
    private Dir direction;
    public String value;

    private final LinearLayout linRoot;//imageview或者textview 和小点的父亲布局
    public RelativeLayout mContentView;//左边的view添加遮罩的父view
    private final int screenWidth;


    private Point point;

    public Point getPoint() {
        Point point = new Point();
        if (direction == Dir.left) {
            point.setX(this.getLeft() + pointview.getLeft() + pointViewDiameter / 2); // 小圆点直径的一半
        } else {
            point.setX(getLeft() + pointViewDiameter / 2);
        }
        point.setY(this.getTop() + pointview.getTop() + pointViewDiameter / 2);
        return point;
    }

    @SuppressLint("ResourceType")
    public SendLineImagePointView(Context context, int cLeftposiion, LineQuestionModle inbasebean, Dir direction) {//Sublineinfo sublineinfo,
        super(context);
        this.context = context;
        //获取屏幕宽度
        screenWidth = BaseUtil.getWidthPixels((Activity) context);
        this.cLeftposiion = cLeftposiion;
        this.direction = direction;
        this.inbasebean = inbasebean;


        /*imageview和textview的大小*/
        LayoutParams lp = new LayoutParams(screenWidth / 5, screenWidth / 5);

        linRoot = new LinearLayout(context);


        if (direction == Dir.left) {//左边

            String content = inbasebean.getOptions().getLeft().get(cLeftposiion).getContent();

            if (content.startsWith("h", 0) && content.startsWith("t", 1)) {

                imageViewL = addLImageView();
                imageViewL.setLayoutParams(lp);
                mContentView = new RelativeLayout(context);
                mContentView.addView(imageViewL);
                linRoot.addView(mContentView);

            } else {

                mContentView = new RelativeLayout(context);
                textViewL = addLTextView();
                lp.addRule(CENTER_IN_PARENT);
                textViewL.setLayoutParams(lp);
                mContentView.addView(textViewL);

                linRoot.addView(mContentView);

            }
            mContentView.setPadding(5, 5, 5, 5);
            mContentView.setBackgroundResource(R.drawable.line_bg);
            this.value = inbasebean.getOptions().getLeft().get(cLeftposiion).getVal();
            addPointLeft();//添加左边小黑点

        } else {//右边

            this.value = inbasebean.getOptions().getRight().get(cLeftposiion).getVal();
            addPointRight();//添加右边小黑点
            String content = inbasebean.getOptions().getRight().get(cLeftposiion).getContent();

            if (content.startsWith("h", 0) && content.startsWith("t", 1)) {
                mContentView = new RelativeLayout(context);

                imageViewR = addRImageView();
                imageViewR.setLayoutParams(lp);
                mContentView.addView(imageViewR);
                linRoot.addView(mContentView);

            } else {
                mContentView = new RelativeLayout(context);
                textViewR = addRTextView();
                lp.addRule(CENTER_IN_PARENT);
                mContentView.addView(textViewR);
                textViewR.setLayoutParams(lp);
                linRoot.addView(mContentView);
            }
            mContentView.setPadding(5, 5, 5, 5);
            mContentView.setBackgroundResource(R.drawable.line_bg);
        }


        this.addView(linRoot);
    }


    /*添加左侧图片*/
    private ImageView addLImageView() {
        ImageView imageView = new ImageView(context);
        Glide.with(context)
                .load(inbasebean.getOptions().getLeft().get(cLeftposiion).getContent())
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);

        return imageView;
    }

    /*添加左侧文字*/
    private TextView addLTextView() {
        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        TextView textView = new TextView(context);
        textView.setText(inbasebean.getOptions().getLeft().get(cLeftposiion).getContent());
        textView.setLayoutParams(lp);
        return textView;
    }


    /*添加右侧图片*/
    private ImageView addRImageView() {
        ImageView imageView = new ImageView(context);
        Glide.with(context)
                .load(inbasebean.getOptions().getRight().get(cLeftposiion).getContent())
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);

        return imageView;
    }

    /*添加右侧文字*/
    private TextView addRTextView() {
        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        TextView textView = new TextView(context);
        textView.setText(inbasebean.getOptions().getRight().get(cLeftposiion).getContent());
        textView.setLayoutParams(lp);
        return textView;
    }


    /*添加小黑点 右*/
    @SuppressLint("ResourceType")
    public void addPointRight() {
        pointview = new CricleTextView(context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(pointViewDiameter, pointViewDiameter);
        lp.rightMargin = pointViewDiameter_margin;
        //设置居中显示：
        lp.gravity = Gravity.CENTER;
        pointview.setLayoutParams(lp);
        linRoot.addView(pointview);


    }

    /*添加小黑点 左*/
    public void addPointLeft() {

        pointview = new CricleTextView(context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(pointViewDiameter, pointViewDiameter);
        lp.leftMargin = pointViewDiameter_margin;
        //设置居中显示：
        lp.gravity = Gravity.CENTER;
        pointview.setLayoutParams(lp);
        linRoot.addView(pointview);

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);

    }

    /*测量子视图*/
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }


}
