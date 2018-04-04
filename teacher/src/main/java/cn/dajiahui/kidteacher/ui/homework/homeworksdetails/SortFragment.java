package cn.dajiahui.kidteacher.ui.homework.homeworksdetails;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.ui.homework.bean.BeLocation;
import cn.dajiahui.kidteacher.ui.homework.bean.SortQuestionModle;
import cn.dajiahui.kidteacher.ui.homework.myinterface.CheckHomework;
import cn.dajiahui.kidteacher.ui.homework.myinterface.MoveLocation;
import cn.dajiahui.kidteacher.ui.homework.view.MoveImagview;
import cn.dajiahui.kidteacher.view.FixedImagview;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static cn.dajiahui.kidteacher.ui.homework.homeworksdetails.DoHomeworkActivity.screenWidth;


/**
 * 排序题
 */


public class SortFragment extends BaseHomeworkFragment implements View.OnClickListener {


    private SortQuestionModle inbasebean;
    private RelativeLayout relaroot, answerroot;

    int mLeftTop = 0;
    int mTop = 0;
    int mRightTop = 0;

    // 左邊視圖
    private List<MoveImagview> leftViews = new ArrayList<>();
    // 右边视图
    private List<FixedImagview> rightViews = new ArrayList<>();


    private boolean calculation = false;//false 监听  测量连线题图片的左右第一个 坐标
    private final int RIGHT = 1;
    private final int LEFT = 2;
    private final int PREPARERIGHT = 3;//准备数据
    private final int PREPMINEARERIGHT = 4;//准备数据我的答案

    private List<BeLocation> pointRightList = new ArrayList<>(); //右视图坐标点的集合
    private List<BeLocation> pointLeftList = new ArrayList<>();//左视图位置的集合

    private List<String> substringRightList = new ArrayList<>();//截取字符串的集合（正确答案）
    private List<String> substringMineList = new ArrayList<>();//截取字符串的集合（我的答案）


    private Map<Integer, BeLocation> sortMineAnswerMap = new HashMap<>();//我的答案（ isanswer=1）
    private Map<Integer, BeLocation> sortRightAnswerMap = new HashMap<>();//正确答案（ isanswer=1）

    private TextView tv_sort, tv_schedule;
    private ImageView sort_img_play;//播放器按钮
    private String media;
    private List<String> mRightContentList;//正确答案的内容
    private List<String> mMineContentList;//我的答案的内容
    private String title;


    private Map<Integer, BeLocation> mMineAnswerMap = new HashMap<>();//（isanswer=0）
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {

                case LEFT:
                    /*算法  计算每个view的位置*/
                    Point pLeft = (Point) msg.obj;
                    /*第一个左边第一个点的X Y*/
                    int pLeftX = pLeft.x;
                    int pLeftY = pLeft.y;
                    for (int i = 0; i < (leftViews.size()); i++) {
                        BeLocation beLocation = new BeLocation(pLeftX, pLeftY, leftViews.get(0).getRight(), leftViews.get(i).getBottom(), leftViews.get(0).getWidth(), leftViews.get(0).getHeight());
                        sortRightAnswerMap.put((i + 1), beLocation);
                        pointLeftList.add(beLocation);
                        pLeftY = (pLeftY += screenWidth / 4);//左边所有点的y坐标
                    }
                    Message msg1 = Message.obtain();
                    msg1.what = PREPARERIGHT;
                    handler.sendMessage(msg1);

                    break;
                case RIGHT:
                    /*算法  计算每个view的位置*/
                    Point pRight = (Point) msg.obj;
                    /*第一个右边第一个点的X Y*/
                    int pRightX = pRight.x;
                    int pRightY = pRight.y;
                    for (int i = 0; i < (rightViews.size()); i++) {
                        BeLocation beLocation = new BeLocation(pRightX, pRightY, rightViews.get(0).getRight(), rightViews.get(i).getBottom(), rightViews.get(0).getWidth(), rightViews.get(0).getHeight());
                        sortMineAnswerMap.put((i + 1), beLocation);
                        pointRightList.add(beLocation);
                        pRightY = (pRightY += screenWidth / 4);//左边所有点的y坐标

                    }
                    Message msg2 = Message.obtain();
                    msg2.what = PREPMINEARERIGHT;
                    handler.sendMessage(msg2);
                    break;
                case PREPARERIGHT: /*为点击正确答案准备数据*/
                    if (inbasebean.getStandard_answer() != null && inbasebean.getOptions() != null) {
                        String standard_answer = inbasebean.getStandard_answer();
                        String[] strs = standard_answer.split(",");
                        /*截取字符串*/
                        for (int i = 0, len = strs.length; i < len; i++) {
                            String split = strs[i].toString();
                            substringRightList.add(split);
                        }
                    }
                    break;
                case PREPMINEARERIGHT:  /*我的答案准备数据*/
                    if (inbasebean.getMy_answer() != null && inbasebean.getOptions() != null) {
                        String getMy_answer = inbasebean.getMy_answer();
                        String[] strs = getMy_answer.split(",");
                        /*截取字符串*/
                        for (int i = 0, len = strs.length; i < len; i++) {
                            String split = strs[i].toString();
                            substringMineList.add(split);
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected View initinitLayout(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fr_sort, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize();
        tv_sort.setText(title);
        tv_schedule.setText(bundle.getString("currntQuestion"));
        /*判断已经完成*/
        if (inbasebean.getIs_complete().equals("1")) {
            showAnswer();
            getAnswerList();
        }
        /*添加右侧视图*/
        addGroupImage(inbasebean.getOptions().size(), relaroot);
        /*添加左侧图片*/
        addGroupMoviewImage(inbasebean.getOptions().size(), relaroot);

        /*监听 relaroot 上的子视图绘制完成*/
        ViewTreeObserver observer = relaroot.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (!calculation) {
                    /*左边*/
                    if (leftViews.get(0) != null) {
                        int leftPointViewX = leftViews.get(0).getLeft();
                        int leftPointViewY = leftViews.get(0).getTop();
                        Point point = new Point(leftPointViewX, leftPointViewY);
                        Message msg = Message.obtain();
                        msg.what = LEFT;
                        msg.obj = point;
                        handler.sendMessage(msg);
                    }
                    /*右边*/
                    if (rightViews.get(0).getChildAt(0) != null) {
                        int rightPointViewX = rightViews.get(0).getLeft();
                        int rightPointViewY = rightViews.get(0).getTop();
                        Point point = new Point(rightPointViewX, rightPointViewY);
                        Message msg = Message.obtain();
                        msg.what = RIGHT;
                        msg.obj = point;
                        handler.sendMessage(msg);
                    }

                    calculation = true;
                }
            }
        });
    }

    /*显示正确，我的答案文案*/
    private void showAnswer() {
        TextView mLeft = new TextView(getActivity());
        mLeft.setText("正确答案");
        mLeft.setTextColor(getResources().getColor(R.color.gray_9f938f));
        RelativeLayout.LayoutParams lpLeft = new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        lpLeft.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        lpLeft.leftMargin = screenWidth / 5;
        answerroot.addView(mLeft);
        mLeft.setLayoutParams(lpLeft);

        TextView mRight = new TextView(getActivity());
        mRight.setText("我的答案");
        mRight.setTextColor(getResources().getColor(R.color.gray_9f938f));
        RelativeLayout.LayoutParams lpRight = new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        lpRight.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        lpRight.rightMargin = screenWidth / 5;
        answerroot.addView(mRight);
        mRight.setLayoutParams(lpRight);
    }

    /*添加右侧图片*/
    private void addGroupImage(int size, RelativeLayout lin) {

        for (int i = 0; i < size; i++) {
            FixedImagview fixedImagview = new FixedImagview(getActivity(), i, mMineContentList, mRightContentList, inbasebean);
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(screenWidth / 5, screenWidth / 5);
            lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
            lp.topMargin = mRightTop;
            mRightTop += screenWidth / 4;
            lp.rightMargin = screenWidth / 6;
            fixedImagview.setLayoutParams(lp);
            rightViews.add(fixedImagview);
            lin.addView(fixedImagview); //动态添加图片

        }
    }

    /*添加左可以动的侧图片*/

    private void addGroupMoviewImage(int size, RelativeLayout lin) {

        for (int i = 0; i < size; i++) {
            MoveImagview mMoveView = new MoveImagview(getActivity(), i, mRightContentList, inbasebean);//this,
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(screenWidth / 5, screenWidth / 5);
            lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
            lp.topMargin = mLeftTop;
            mLeftTop += screenWidth / 4;
            lp.leftMargin = screenWidth / 6;
            leftViews.add(mMoveView);
            mMoveView.setLayoutParams(lp);
            lin.addView(mMoveView); //动态添加图片
        }
    }
    private Bundle bundle;

    @Override
    public void setArguments(Bundle bundle) {
        this.bundle = bundle;
        inbasebean = (SortQuestionModle) bundle.get("SortQuestionModle");
        media = inbasebean.getMedia();
        title = inbasebean.getTitle();
    }

    /*初始化*/
    private void initialize() {
        sort_img_play = getView(R.id.sort_img_play);
        tv_schedule = getView(R.id.tv_schedule);
        tv_sort = getView(R.id.tv_sort);
        answerroot = getView(R.id.answerroot);
        relaroot = getView(R.id.relaroot);
        sort_img_play.setOnClickListener(this);
        sort_img_play.setBackground(animationDrawable);

    }


    /*我的答案  正确答案的点击事件*/
    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.sort_img_play:
                playMp3(media);
                break;
            default:
                break;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);

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
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    /*获取答案集合*/
    private void getAnswerList() {
        /*我的答案 start*/
        String my_answer = inbasebean.getMy_answer();
        List<String> mMineList = new ArrayList<>();//截取字符串的集合（我的答案）
        //我的答案的顺序集合
        mMineContentList = new ArrayList<>();
        if (!my_answer.equals("")) {
            String[] strs = my_answer.split(",");
            /*截取字符串*/
            for (int i = 0, len = strs.length; i < len; i++) {
                String split = strs[i].toString();
                mMineList.add(split);
            }

            /*遍历我的答案的集合*/
            for (int i = 0; i < mMineList.size(); i++) {
                /*遍历解析的集合找到 我的答案所对应的val*/
                for (int t = 0; t < inbasebean.getOptions().size(); t++) {
                    /*如果val值相等*/
                    if (mMineList.get(i).equals(inbasebean.getOptions().get(t).getVal())) {
                        mMineContentList.add(inbasebean.getOptions().get(t).getContent());
                    }
                }
            }
        }
        /*我的答案 end*/

        /*正确答案 start*/
        String standard_answer = inbasebean.getStandard_answer();
        List<String> mSandardAnswerList = new ArrayList<>();//截取字符串的集合参考答案）
        //参考答案的顺序集合
        mRightContentList = new ArrayList<>();
        String[] strs = standard_answer.split(",");
        /*截取字符串*/
        for (int i = 0, len = strs.length; i < len; i++) {
            String split = strs[i].toString();
            mSandardAnswerList.add(split);
        }

        /*遍历正确答案的集合*/
        for (int i = 0; i < mSandardAnswerList.size(); i++) {
            /*遍历解析的集合找到 我的答案所对应的val*/
            for (int t = 0; t < inbasebean.getOptions().size(); t++) {
                /*如果val值相等*/
                if (mSandardAnswerList.get(i).equals(inbasebean.getOptions().get(t).getVal())) {
                    mRightContentList.add(inbasebean.getOptions().get(t).getContent());
                }
            }
        }
        /*正确答案 end*/
    }
}

