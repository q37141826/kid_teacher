package cn.dajiahui.kidteacher.ui.homework.homeworksdetails;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.ui.homework.adapter.Dir;
import cn.dajiahui.kidteacher.ui.homework.bean.DrawPath;
import cn.dajiahui.kidteacher.ui.homework.bean.LineQuestionModle;
import cn.dajiahui.kidteacher.ui.homework.bean.Point;
import cn.dajiahui.kidteacher.ui.homework.myinterface.CheckHomework;
import cn.dajiahui.kidteacher.ui.homework.view.DrawView;
import cn.dajiahui.kidteacher.ui.homework.view.LineImagePointView;
import cn.dajiahui.kidteacher.ui.homework.view.Sublineinfo;
import cn.dajiahui.kidteacher.util.Logger;

import static cn.dajiahui.kidteacher.controller.Constant.lineView_margin;
import static cn.dajiahui.kidteacher.controller.Constant.pointViewDiameter_margin;
import static cn.dajiahui.kidteacher.ui.homework.homeworksdetails.DoHomeworkActivity.screenWidth;


/**
 * 连线题
 */
/*显示我的答案和正确答案之后要禁止连线*/
public class LineFragment extends BaseHomeworkFragment implements
        View.OnClickListener {// CheckHomework, Sublineinfo,

    private LineQuestionModle inbasebean;
    private SubmitLineFragment submit;
    private ImageView img_play;
    private TextView mLeft, mRight, tv_line, tv_schedule;
    private RelativeLayout selectview_root, draw_root;

    private DrawView drawView;
    private LineImagePointView currentSelectedView;//当前选中的view
    // 左邊視圖
    private List<LineImagePointView> leftViews = new ArrayList<>();
    // 右边视图
    private List<LineImagePointView> rightViews = new ArrayList<>();
    int mLeftTop = 0;
    int mRightTop = 0;
    final List<Point> listPoint = new ArrayList<>();
    private int LEFT = 1;
    private int RIGHT = 2;


    private Map<Integer, Point> ponitViewXY = new HashMap();//通过val获取point点的map（提供显示正确答案 和 自己的答案 用）
    private boolean calculation = false;//false 监听  测量连线题图片的左右第一个 坐标

    private String mediaUrl;

    private Map<Integer, LineImagePointView> showT_RMap = new HashMap<>();//用于显示判断划线的颜色
    private String title;
    private LinearLayout mLinroot;//显示正确答案我的答案的父布局


    private List<ImageView> mMaskImageviewL = new ArrayList<>();//遮罩view大集合
    private List<ImageView> mMaskImageviewR = new ArrayList<>();//遮罩view大集合


    private Boolean mOnclickAnswer = false;//false可以点击正确答案
    private Map mMineAnswerMap;//我的答案
    private Map mStandardMap;//参考答案

    @Override
    protected View initinitLayout(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fr_line, null);
    }

    Bundle bundle;

    @Override
    public void setArguments(Bundle bundle) {
        this.bundle = bundle;
        inbasebean = (LineQuestionModle) bundle.get("LineQuestionModle");
        mediaUrl = inbasebean.getMedia();
        title = inbasebean.getTitle();
    }

    /*添加左右侧图片*/
    private void addGroupImage(int size, RelativeLayout lin, Dir direction) {

        for (int i = 0; i < size; i++) {
            LineImagePointView mView = new LineImagePointView(getActivity(), i, inbasebean, direction);//this,
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            if (direction == Dir.left) {
                lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
                lp.topMargin = mLeftTop;
                mLeftTop += screenWidth / 4;
                lp.leftMargin = lineView_margin;
                leftViews.add(mView);
                showT_RMap.put(i + 1, mView);

            } else {
                lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
                lp.topMargin = mRightTop;
                lp.rightMargin = lineView_margin;
                mRightTop += screenWidth / 4;
                rightViews.add(mView);
                showT_RMap.put(i + 1 + size, mView);
            }
            mView.setLayoutParams(lp);
            lin.addView(mView); //动态添加图片
        }

    }


    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == LEFT) {
                Point pLeft = (Point) msg.obj;
                listPoint.add(pLeft);
                /*第一个左边第一个点的X Y*/
                int pLeftX = pLeft.getX();
                int pLeftY = pLeft.getY();
                for (int i = 0; i < leftViews.size(); i++) {
                    ponitViewXY.put(i + 1, new Point(pLeftX, pLeftY, "" + (i + 1)));
                    pLeftY = pLeftY += screenWidth / 4;//左边所有点的y坐标

                }

            } else if (msg.what == RIGHT) {
                Point pRight = (Point) msg.obj;
                listPoint.add(pRight);
                /*第一个左边第一个点的X Y*/
                int pRightX = pRight.getX();
                int pRightY = pRight.getY();

                for (int i = 0; i < rightViews.size(); i++) {
                    ponitViewXY.put((i + 1 + leftViews.size()), new Point(pRightX, pRightY, "" + ((i + 1) + leftViews.size())));
                    pRightY = pRightY += screenWidth / 4;//左边所有点的y坐标
                    if (ponitViewXY.size() == (leftViews.size() * 2)) {
                        /*首先判断是否作答*/
                        switch (inbasebean.getIs_complete()) {
                            /*未开始*/
                            case "-1":

                                break;
                            /*进行中*/
                            case "0":

                                break;
                            /*已完成*/
                            case "1":

                                getAnswer();
                                mLinroot.setVisibility(View.VISIBLE);
                                mRight.setText("我的答案");
                                mLeft.setText("正确答案");
                                showRightAnswer();


                                break;
                            default:
                                break;

                        }

                    }
                }

            }
        }
    };


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initialize();

        tv_line.setText(title);

        tv_schedule.setText(bundle.getString("currntQuestion"));
        /*非空校验*/
        if (inbasebean.getOptions().getRight() != null) {
            //添加左侧图片
            addGroupImage(inbasebean.getOptions().getRight().size(), selectview_root, Dir.left);
            //添加右侧图片
            addGroupImage(inbasebean.getOptions().getRight().size(), selectview_root, Dir.right);

            ViewTreeObserver observer = selectview_root.getViewTreeObserver();
            observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    if (!calculation) {
                        /*左边*/
                        if (leftViews.get(0).getChildAt(0) != null) {
                            int leftPointViewX = (leftViews.get(0).getLeft()) + (leftViews.get(0).getWidth());
                            /*获取第一个控件的距离左边的距离*/
                            int leftpointViewY = leftViews.get(0).getChildAt(0).getTop() + leftViews.get(0).getChildAt(0).getHeight() / 2;
                            Message msg = Message.obtain();
                            msg.obj = new Point(leftPointViewX, leftpointViewY);
                            msg.what = LEFT;
                            handler.sendMessage(msg);
                            calculation = !calculation;
                        }
                        /*右边*/
                        if (rightViews.get(0).getChildAt(0) != null) {
                            int rightPointViewX = rightViews.get(0).getLeft() + pointViewDiameter_margin;
                            int rightPointViewY = rightViews.get(0).getChildAt(0).getTop() + rightViews.get(0).getChildAt(0).getHeight() / 2;
                            Message msg = Message.obtain();
                            msg.what = RIGHT;
                            msg.obj = new Point(rightPointViewX, rightPointViewY);
                            handler.sendMessage(msg);
                        }
                    }
                }
            });
        }

    }

    /*初始化*/
    @SuppressLint("ResourceType")
    private void initialize() {
        draw_root = getView(R.id.draw_root);
        selectview_root = getView(R.id.selectview_root);
        img_play = getView(R.id.img_play);
        mLinroot = getView(R.id.linroot);

        mLeft = getView(R.id.mLeft);
        mRight = getView(R.id.mRight);
        tv_line = getView(R.id.tv_line);
        tv_schedule = getView(R.id.tv_schedule);

        img_play.setOnClickListener(this);
        img_play.setBackground(animationDrawable);

        if (inbasebean.getIs_complete().equals("1")) {
            mLeft.setOnClickListener(this);
            mRight.setOnClickListener(this);
        }


    }

    /*删除线作业模块*/
    private void removeLineHomework(LineImagePointView firstView, LineImagePointView secondView) {
        /* 查找firstView */
        for (int i = 0; i < inbasebean.getDrawPathList().size(); i++) {
            DrawPath drawPath = inbasebean.getDrawPathList().get(i);
            if (firstView.getDirection() == Dir.left) {
                if (drawPath.getLeftPoint().getY() == firstView.getPoint().getY()) {
                    draw_root.removeViewAt(i);
                    // 当前这点是有线的，要删除
                    inbasebean.getDrawPathList().remove(i);
                    break;
                }
            } else {
                if (drawPath.getRightPoint().getY() == firstView.getPoint().getY()) {
                    // 当前这点是有线的，要删除
                    draw_root.removeViewAt(i);
                    // 当前这点是有线的，要删除
                    inbasebean.getDrawPathList().remove(i);
                    break;
                }
            }
        }
        /* 查找secondView */
        for (int i = 0; i < inbasebean.getDrawPathList().size(); i++) {
            DrawPath drawPath = inbasebean.getDrawPathList().get(i);
            if (secondView.getDirection() == Dir.left) {
                if (drawPath.getLeftPoint().getY() == secondView.getPoint().getY()) {
                    // 当前这点是有线的，要删除
                    draw_root.removeViewAt(i);
                    // 当前这点是有线的，要删除
                    inbasebean.getDrawPathList().remove(i);
                    break;
                }
            } else {
                if (drawPath.getRightPoint().getY() == secondView.getPoint().getY()) {
                    // 当前这点是有线的，要删除
                    draw_root.removeViewAt(i);
                    // 当前这点是有线的，要删除
                    inbasebean.getDrawPathList().remove(i);
                    break;
                }
            }
        }
    }

    /*添加线作业模块*/
    private void addLineHomework(LineImagePointView firstView, LineImagePointView secondView) {
        DrawPath path = null;
        if (firstView.getDirection() == Dir.left) {
            inbasebean.getInitLineMyanswerMap().put(firstView.value, secondView.value);
            path = new DrawPath(firstView.getPoint(), secondView.getPoint());
        } else {
            inbasebean.getInitLineMyanswerMap().put(secondView.value, firstView.value);
            path = new DrawPath(secondView.getPoint(), firstView.getPoint());
        }
        inbasebean.getDrawPathList().add(path);
    }


//    /*要连线的图片的点击事件*/
//    @Override
//    public void submitlininfo(LineImagePointView lineImagePointView) {
//
//        /*首先判断是否作答*/
//        switch (inbasebean.getIs_complete()) {
//            /*未开始*/
//            case "-1":
//                ShowDrawLineUI(lineImagePointView);
//                break;
//            /*进行中*/
//            case "0":
//                ShowDrawLineUI(lineImagePointView);
//                break;
//            /*已完成*/
//            case "1":
//
//                break;
//            default:
//                break;
//
//        }
//
//    }

    /*画线*/
    private void ShowDrawLineUI(LineImagePointView lineImagePointView) {
        //判断当前选择的view是否为空 若等于空把点击的view赋值给当前选择的view
        if (currentSelectedView == null) {
            lineImagePointView.selected(true);//设置为选中
            currentSelectedView = lineImagePointView;
        } else {//当前选择的view不为空  把点击的
            /*判断再次点击的是不是自己  是自己 就把 点击的view付给选择的currentSelectedView*/
            if (lineImagePointView == currentSelectedView) {
                lineImagePointView.selected(false);//设置非选中
                currentSelectedView = null;
            } else {
                /*再次点击的不是自己*/
                /* 判断是否同侧点 */
                if (currentSelectedView.getDirection() == lineImagePointView.getDirection()) {
                    currentSelectedView.selected(false); // 把上一个view设置成非选中
                    lineImagePointView.selected(true); // 把当前的view设置成选中
                    currentSelectedView = lineImagePointView;
                } else {
                    /* 非同侧view */
                    /*先删除*/
                    removeLineHomework(currentSelectedView, lineImagePointView);
                    /*必须每次都要new出来 要不不显示划线*/
                    drawView = new DrawView(getActivity(), currentSelectedView, lineImagePointView, getResources().getColor(R.color.black));
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, selectview_root.getMeasuredHeight());
                    draw_root.addView(drawView, params);
                    addLineHomework(currentSelectedView, lineImagePointView);
                    currentSelectedView.selected(false);
                    currentSelectedView = null;
                    lineImagePointView.selected(false);

                    inbasebean.setAnswerflag("true");//答题标志

                    submit.submitLineFragment(inbasebean);//告诉活动每次连线的数据

                }

            }
        }
    }
//
//    /*翻页回来掉的接口*/
//    @Override
//    public void submitHomework(Object questionModle) {
//        if (questionModle != null) {
//            inbasebean = (LineQuestionModle) questionModle;
//
//            if (currentSelectedView != null) {
//                currentSelectedView.selected(true);
//            }
//            /*作业模式没有答过题*/
//            if (inbasebean.getIs_answered().equals("0")) {
//                for (int i = 0; i < inbasebean.getDrawPathList().size(); i++) {
//                    DrawPath drawPath = inbasebean.getDrawPathList().get(i);
//                    drawPath.setPathColor(getResources().getColor(R.color.btn_green_noraml));
//                    DrawView drawView = new DrawView(getActivity());
//                    drawView.DrawViewOnback(drawPath);
//                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, selectview_root.getMeasuredHeight());
//                    draw_root.addView(drawView, params);
//
//                }
//            }
//        }
//    }


    /*我的答案  ，正确答案的点击事件*/
    @Override
    public void onClick(View v) {


        switch (v.getId()) {

            case R.id.img_play:
                if (!mediaUrl.equals("")) {
                    playMp3(mediaUrl);
                } else {
                    audioDialog.show();
                }
                break;
            case R.id.mLeft:
                if (!mOnclickAnswer) {
                    /*显示正确答案*/
                    showRightAnswer();
                }
                break;
            case R.id.mRight:
                if (mOnclickAnswer) {
                    /*显示我的答案*/
                    showMineAnswer();
                }
                break;

            default:
                break;
        }
    }


    /*与activity通信*/
    public interface SubmitLineFragment {
        public void submitLineFragment(LineQuestionModle questionModle);

    }


    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
//        submit = (SubmitLineFragment) activity;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        System.gc();
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onStop() {
        super.onStop();
        mediaPlayer.stop();

    }

    @Override
    public void onPause() {
        super.onPause();
        mediaPlayer.stop();
    }


    /*我的答案*/
    public void showMineAnswer() {
        if (inbasebean != null) {
            mLeft.setTextColor(getResources().getColor(R.color.gray_9f938f));
            mRight.setTextColor(getResources().getColor(R.color.yellow_FEBF12));
            mOnclickAnswer = !mOnclickAnswer;

            /*便利参考答案*/
            inbasebean.getDrawPathList().clear();
            /*划线父布局清空view*/
            draw_root.removeAllViews();

            if (mStandardMap != null) {
                for (int n = 1; n <= mStandardMap.size(); n++) {

                    for (View v : mMaskImageviewL) {

                        leftViews.get(n - 1).mContentView.removeView(v);

                        if (mMineAnswerMap != null) {
                            for (int i = 1; i <= mMineAnswerMap.size(); i++) {

                                showT_RMap.get(mMineAnswerMap.get(i)).mContentView.removeView(v);
                                showT_RMap.get(i).mContentView.removeView(v);
                            }
                        }
                    }
                    for (View v : mMaskImageviewR) {

                        rightViews.get(n - 1).mContentView.removeView(v);

                        if (mMineAnswerMap != null) {
                            for (int i = 1; i <= mMineAnswerMap.size(); i++) {

                                showT_RMap.get(mMineAnswerMap.get(i)).mContentView.removeView(v);
                                showT_RMap.get(i).mContentView.removeView(v);
                            }
                        }
                    }


                }
            }


            mMaskImageviewL.clear();
            mMaskImageviewR.clear();

            if (mMineAnswerMap != null)
                for (int i = 1; i <= mMineAnswerMap.size(); i++) {

                    DrawPath drawPath = new DrawPath(ponitViewXY.get(i), ponitViewXY.get(mMineAnswerMap.get(i)));
                    DrawView drawView = null;
                    /*回答正确*/
                    if (mStandardMap.get(i) == mMineAnswerMap.get(i)) {
                        /*改变划线颜色 green*/
                        drawView = new DrawView(getActivity(), getResources().getColor(R.color.green_9DEAA6));
                        /*添加左边view的遮罩*/
                        RelativeLayout.LayoutParams paramsL = new RelativeLayout.LayoutParams(screenWidth / 5, screenWidth / 5);
                        paramsL.addRule(RelativeLayout.CENTER_IN_PARENT);
                        ImageView imageViewL = new ImageView(getActivity());
                        imageViewL.setLayoutParams(paramsL);
                        imageViewL.setBackgroundResource(R.drawable.answer_true_bg);

                        showT_RMap.get(i).mContentView.addView(imageViewL);
                        mMaskImageviewL.add(imageViewL);

                        /*改变小点颜色 绿色*/
                        showT_RMap.get(i).pointview.setcolor(getResources().getColor(R.color.green_9DEAA6));
                        showT_RMap.get(i).pointview.refreshPonitColor();

                        /*添加右边view的遮罩*/
                        RelativeLayout.LayoutParams paramsR = new RelativeLayout.LayoutParams(screenWidth / 5, screenWidth / 5);
                        paramsR.addRule(RelativeLayout.CENTER_IN_PARENT);
                        ImageView imageViewR = new ImageView(getActivity());
                        imageViewR.setLayoutParams(paramsR);
                        imageViewR.setBackgroundResource(R.drawable.answer_true_bg);


                        showT_RMap.get(mMineAnswerMap.get(i)).mContentView.addView(imageViewR);
                        mMaskImageviewR.add(imageViewR);


                        showT_RMap.get(mMineAnswerMap.get(i)).pointview.setcolor(getResources().getColor(R.color.green_9DEAA6));
                        showT_RMap.get(mMineAnswerMap.get(i)).pointview.refreshPonitColor();


                    } else {//回答错误

                        /*改变划线颜色 red*/
                        drawView = new DrawView(getActivity(), getResources().getColor(R.color.red));

                        /*添加遮罩*/
                        RelativeLayout.LayoutParams paramsL = new RelativeLayout.LayoutParams(screenWidth / 5, screenWidth / 5);
                        paramsL.addRule(RelativeLayout.CENTER_IN_PARENT);
                        ImageView imageViewL = new ImageView(getActivity());
                        imageViewL.setLayoutParams(paramsL);
                        imageViewL.setBackgroundResource(R.drawable.answer_false_bg);

                        showT_RMap.get(i).mContentView.addView(imageViewL);
                        mMaskImageviewL.add(imageViewL);
                        /*改变小点颜色 红色*/
                        showT_RMap.get(i).pointview.setcolor(getResources().getColor(R.color.red));
                        showT_RMap.get(i).pointview.refreshPonitColor();


                        /*添加右边view的遮罩*/
                        RelativeLayout.LayoutParams paramsR = new RelativeLayout.LayoutParams(screenWidth / 5, screenWidth / 5);
                        paramsR.addRule(RelativeLayout.CENTER_IN_PARENT);
                        ImageView imageViewR = new ImageView(getActivity());
                        imageViewR.setLayoutParams(paramsR);
                        imageViewR.setBackgroundResource(R.drawable.answer_false_bg);

                        showT_RMap.get(mMineAnswerMap.get(i)).mContentView.addView(imageViewR);
                        mMaskImageviewR.add(imageViewR);


                        showT_RMap.get(mMineAnswerMap.get(i)).pointview.refreshPonitColor();
                        showT_RMap.get(mMineAnswerMap.get(i)).pointview.setcolor(getResources().getColor(R.color.red));

                    }

                    drawView.DrawViewOnback(drawPath);
                    inbasebean.getDrawPathList().add(drawPath);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, selectview_root.getMeasuredHeight());
                    draw_root.addView(drawView, params);


                }
        }
    }


    /*显示正确答案*/

    private void showRightAnswer() {
        if (this.isAdded()) {
            mRight.setTextColor(getResources().getColor(R.color.gray_9f938f));
            mLeft.setTextColor(getResources().getColor(R.color.yellow_FEBF12));
            inbasebean.getDrawPathList().clear();
            /*划线父布局清空view*/
            draw_root.removeAllViews();
            if (mStandardMap != null) {
                for (int n = 1; n <= mStandardMap.size(); n++) {

                    for (View v : mMaskImageviewL) {

                        leftViews.get(n - 1).mContentView.removeView(v);

                        if (mMineAnswerMap != null) {
                            for (int i = 1; i <= mMineAnswerMap.size(); i++) {

                                showT_RMap.get(mMineAnswerMap.get(i)).mContentView.removeView(v);
                                showT_RMap.get(i).mContentView.removeView(v);
                            }
                        }
                    }
                    for (View v : mMaskImageviewR) {

                        rightViews.get(n - 1).mContentView.removeView(v);

                        if (mMineAnswerMap != null) {
                            for (int i = 1; i <= mMineAnswerMap.size(); i++) {

                                showT_RMap.get(mMineAnswerMap.get(i)).mContentView.removeView(v);
                                showT_RMap.get(i).mContentView.removeView(v);
                            }
                        }
                    }


                }
            }
            mMaskImageviewL.clear();
            mMaskImageviewR.clear();


            if (mStandardMap != null)
                for (int n = 1; n <= mStandardMap.size(); n++) {
                    DrawPath drawPath = new DrawPath(ponitViewXY.get(n), ponitViewXY.get(mStandardMap.get(n)));
                    DrawView drawView = new DrawView(getActivity(), getResources().getColor(R.color.green_9DEAA6));
                    drawView.DrawViewOnback(drawPath);
                    inbasebean.getDrawPathList().add(drawPath);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, selectview_root.getMeasuredHeight());
                    draw_root.addView(drawView, params);

                    leftViews.get(n - 1).pointview.setcolor(getResources().getColor(R.color.green_9DEAA6));
                    rightViews.get(n - 1).pointview.setcolor(getResources().getColor(R.color.green_9DEAA6));
                    leftViews.get(n - 1).pointview.refreshPonitColor();
                    rightViews.get(n - 1).pointview.refreshPonitColor();
                    RelativeLayout.LayoutParams paramsL = new RelativeLayout.LayoutParams(screenWidth / 5, screenWidth / 5);
                    paramsL.addRule(RelativeLayout.CENTER_IN_PARENT);


                    ImageView imageViewL = new ImageView(getActivity());
                    imageViewL.setLayoutParams(paramsL);
                    imageViewL.setBackgroundResource(R.drawable.answer_true_bg);
                    /*添加遮罩到正确答案集合左边*/


                    leftViews.get(n - 1).mContentView.addView(imageViewL);
                    mMaskImageviewL.add(imageViewL);


                    RelativeLayout.LayoutParams paramsR = new RelativeLayout.LayoutParams(screenWidth / 5, screenWidth / 5);
                    paramsR.addRule(RelativeLayout.CENTER_IN_PARENT);


                    ImageView imageViewR = new ImageView(getActivity());
                    imageViewR.setLayoutParams(paramsR);
                    imageViewR.setBackgroundResource(R.drawable.answer_true_bg);


                    rightViews.get(n - 1).mContentView.addView(imageViewR);
                    mMaskImageviewR.add(imageViewR);


                }
        }
        mOnclickAnswer = !mOnclickAnswer;
        Logger.d("------------------1");
    }

    /*json转map*/
    public Map jsonToObject(String jsonStr) {
        JSONObject jsonObj = null;
        try {
            jsonObj = new JSONObject(jsonStr);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Iterator<String> nameItr = jsonObj.keys();
        String name;
        Map<Integer, Integer> outMap = new HashMap<Integer, Integer>();
        while (nameItr.hasNext()) {
            name = nameItr.next();
            try {
                if (!jsonObj.getString(name).equals(""))
                    outMap.put(Integer.parseInt(name), Integer.parseInt(jsonObj.getString(name)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return outMap;
    }

    /*获取答案*/
    private void getAnswer() {
        /*我的答案start */
        String my_answer = inbasebean.getMy_answer();
        if (!my_answer.equals("")) {
            //我的答案map
            mMineAnswerMap = jsonToObject(my_answer);
        }
        String standard_answer = inbasebean.getStandard_answer();
        //参考答案map
        mStandardMap = jsonToObject(standard_answer);
    }
}
