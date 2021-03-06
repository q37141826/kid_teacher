package cn.dajiahui.kidteacher.ui.homework.homeworksdetails;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;

import com.fxtx.framework.http.callback.ResultCallback;
import com.fxtx.framework.json.HeadJson;
import com.fxtx.framework.log.Logger;
import com.fxtx.framework.log.ToastUtil;
import com.fxtx.framework.ui.FxActivity;
import com.fxtx.framework.util.BaseUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Request;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.controller.Constant;
import cn.dajiahui.kidteacher.http.RequestUtill;
import cn.dajiahui.kidteacher.ui.homework.bean.ChoiceQuestionModle;
import cn.dajiahui.kidteacher.ui.homework.bean.CompletionQuestionModle;
import cn.dajiahui.kidteacher.ui.homework.bean.JudjeQuestionModle;
import cn.dajiahui.kidteacher.ui.homework.bean.LineQuestionModle;
import cn.dajiahui.kidteacher.ui.homework.bean.QuestionModle;
import cn.dajiahui.kidteacher.ui.homework.bean.SortQuestionModle;
import cn.dajiahui.kidteacher.util.DjhJumpUtil;

/*
 * 查看作业具体信息
 * */
public class DoHomeworkActivity extends FxActivity implements BaseHomeworkFragment.GetBaseHomeworkFragment {//implements JudgeFragment.SubmitJudgeFragment,ChoiceFragment.SubmitChoiseFragment,JudgeFragment.GetMediaPlayer, SortFragment.SubmitSortFragment, LineFragment.SubmitLineFragment,CompletionFragment.SubmitCompletionFragment

    private cn.dajiahui.kidteacher.ui.homework.view.NoScrollViewPager mViewpager;
    private String subjectype = "";//当前题型
    private MediaPlayer mediaPlayer;
    private int currentposition = 0;//当前页面的索引
    private Map<Integer, BaseHomeworkFragment> frMap = new HashMap();//保存每个不同类型的Fragment

    private ArrayList<Object> mDatalist;//所有数据的模型集合
    private String homeworkId;
    private String userId;
    private int mItemPosition;
    public static int screenWidth;//屏幕宽度
    private BaseHomeworkFragment baseHomeworkFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewpager.setNoScroll(false);//作业可以滑动
        Intent intent = getIntent();
        mItemPosition = intent.getIntExtra("position", -1);
        homeworkId = getIntent().getExtras().getString("homeworkId");
        userId = getIntent().getExtras().getString("userId");

        setfxTtitle(intent.getStringExtra("nickname") + "的作业");
        onRightBtn(R.string.AnswerCard);
        httpData();
        onBack();
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_do_homework);
        initialize();
        mDatalist = new ArrayList<>();

    }


    /*初始化*/
    private void initialize() {
        mViewpager = getView(R.id.viewpager);
        //获取屏幕宽度
        screenWidth = BaseUtil.getWidthPixels(this);
    }

    @Override
    public void httpData() {
        super.httpData();
        /*作业请求*/
        RequestUtill.getInstance().httprequestHomeworkQuestions(DoHomeworkActivity.this, callHomeWorkContinue, homeworkId, userId);
    }

    /**
     * 学生作业所有题callback函数
     */
    ResultCallback callHomeWorkContinue = new ResultCallback() {

        @Override
        public void onError(Request request, Exception e) {
            Logger.d("作业返回失败json：" + e.toString());
            dismissfxDialog();
        }

        @Override
        public void onResponse(String response) {
//            Logger.d("作业返回json：" + response);
            dismissfxDialog();
            HeadJson headJson = new HeadJson(response);
            if (headJson.getstatus() == 0) {

                try {
                    JSONArray jsonArray = headJson.getObject().getJSONObject("data").getJSONArray("question_list");
                    List<QuestionModle> mdata = new Gson().fromJson(jsonArray.toString(), new TypeToken<List<QuestionModle>>() {
                    }.getType());

                    /*解析json数据*/
                    for (int i = 0; i < mdata.size(); i++) {

                        switch (mdata.get(i).getQuestion_cate_id()) {
                            case Constant.Judje:
                                Logger.d("判断：" + jsonArray.get(i).toString());
                                JudjeQuestionModle judjeQuestionModle = new Gson().fromJson(jsonArray.get(i).toString(), JudjeQuestionModle.class);
                                mDatalist.add(judjeQuestionModle);
                                break;
                            case Constant.Choice:
                                ChoiceQuestionModle choiceQuestionModle = new Gson().fromJson(jsonArray.get(i).toString(), ChoiceQuestionModle.class);
                                mDatalist.add(choiceQuestionModle);
                                Logger.d("选择：" + jsonArray.get(i).toString());
                                break;
                            case Constant.Sort:
                                Logger.d("排序：" + jsonArray.get(i).toString());
                                SortQuestionModle sortQuestionModle = new Gson().fromJson(jsonArray.get(i).toString(), SortQuestionModle.class);
                                mDatalist.add(sortQuestionModle);
                                break;
                            case Constant.Line:
                                Logger.d("连线：" + jsonArray.get(i).toString());
                                LineQuestionModle lineQuestionModle = new Gson().fromJson(jsonArray.get(i).toString(), LineQuestionModle.class);
                                mDatalist.add(lineQuestionModle);
                                break;
                            case Constant.Completion:
                                Logger.d("填空：" + jsonArray.get(i).toString());
                                CompletionQuestionModle completionQuestionModle = new Gson().fromJson(jsonArray.get(i).toString(), CompletionQuestionModle.class);
                                mDatalist.add(completionQuestionModle);
                                break;
                            default:
                                break;

                        }
                    }
                    Logger.d("mDatalist.size()" + mDatalist.size());
                    /*作业适配器*/
                    HomeWorkAdapter homeWorkAdapter = new HomeWorkAdapter(getSupportFragmentManager(), mdata);
                    mViewpager.setAdapter(homeWorkAdapter);
                    mViewpager.setOnPageChangeListener(onPageChangeListener);
                    /*跳转单个题型的题*/
                    if (mItemPosition != -1) {
                        mViewpager.setCurrentItem(mItemPosition);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                ToastUtil.showToast(DoHomeworkActivity.this, headJson.getMsg());
            }
        }
    };


    /*答题卡*/
    @Override
    public void onRightBtnClick(View view) {

        /*答题状态*/
        Bundle bundle = new Bundle();
        bundle.putString("userId", userId);
        bundle.putString("homeworkId", homeworkId);
        /*跳转答题卡*/
        DjhJumpUtil.getInstance().startBaseActivityForResult(DoHomeworkActivity.this, AnswerCardCompleteActivity.class, bundle, DjhJumpUtil.getInstance().activity_answerCardComplete);


    }


    /*适配器*/
    private class HomeWorkAdapter extends FragmentStatePagerAdapter {

        private List<QuestionModle> data;

        private HomeWorkAdapter(FragmentManager fragmentManager, List<QuestionModle> data) {
            super(fragmentManager);

            this.data = data;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Fragment getItem(int position) {
            //获取题型
            subjectype = data.get(position).getQuestion_cate_id();
            if (subjectype.equals(Constant.Judje)) { /*判断*/

                JudgeFragment fr1 = new JudgeFragment();

                Bundle judgeBundle = new Bundle();
                judgeBundle.putSerializable("JudgeQuestionModle", (Serializable) mDatalist.get(position));
                judgeBundle.putString("currntQuestion", (position + 1) + "/" + data.size());
                fr1.setArguments(judgeBundle);
                frMap.put(position, fr1);

                return fr1;
            } else if (subjectype.equals(Constant.Choice)) { /*选择*/

                ChoiceFragment fr2 = new ChoiceFragment();
                Bundle choiceBundle = new Bundle();
                choiceBundle.putSerializable("ChoiceQuestionModle", (Serializable) mDatalist.get(position));
                choiceBundle.putString("currntQuestion", (position + 1) + "/" + data.size());
                fr2.setArguments(choiceBundle);
                frMap.put(position, fr2);
                return fr2;

            } else if (subjectype.equals(Constant.Sort)) {/*排序*/

                SortFragment fr3 = new SortFragment();

                Bundle sortBundle = new Bundle();
                sortBundle.putSerializable("SortQuestionModle", (Serializable) mDatalist.get(position));
                sortBundle.putString("currntQuestion", (position + 1) + "/" + data.size());
                frMap.put(position, fr3);
                fr3.setArguments(sortBundle);
                return fr3;

            } else if (subjectype.equals(Constant.Line)) {   /*连线*/

                LineFragment fr4 = new LineFragment();

                Bundle linebBundle = new Bundle();
                linebBundle.putSerializable("LineQuestionModle", (Serializable) mDatalist.get(position));
                linebBundle.putString("currntQuestion", (position + 1) + "/" + data.size());
                frMap.put(position, fr4);
                fr4.setArguments(linebBundle);
                return fr4;

            } else if (subjectype.equals(Constant.Completion)) {  /*填空*/

                CompletionFragment fr5 = new CompletionFragment();

                Bundle bundle = new Bundle();
                bundle.putSerializable("CompletionQuestionModle", (Serializable) mDatalist.get(position));
                bundle.putString("currntQuestion", (position + 1) + "/" + data.size());
                frMap.put(position, fr5);

                fr5.setArguments(bundle);
                return fr5;


            }
            return null;
        }


        @Override/*销毁的是销毁当前的页数*/
        public void destroyItem(ViewGroup container, int position, Object object) {
            //如果注释这行，那么不管怎么切换，page都不会被销毁
            super.destroyItem(container, position, object);
            frMap.remove(position);

            //希望做一次垃圾回收
            System.gc();
        }


    }

    @Override
    public void getBaseHomeworkFragment(BaseHomeworkFragment baseHomeworkFragment) {

        this.baseHomeworkFragment = baseHomeworkFragment;
    }

    /*viewpager滑动监听*/
    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
//            currentposition = position;//当前题的页数
            if (baseHomeworkFragment != null) {
                baseHomeworkFragment.stopAutio();
                baseHomeworkFragment.stopAnimation();
            }

//            /*滑动停止音频*/
//            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
//                mediaPlayer.stop();
//            }

//            QuestionModle questionModle = (QuestionModle) mDatalist.get(position);
//
//            switch (questionModle.getQuestion_cate_id()) {
//                case Constant.Judje:/*判断题*/
//
////                    JudjeQuestionModle jude = (JudjeQuestionModle) mDatalist.get(position);
////                    if (frMap.get(position) != null) {
//                    JudgeFragment judgeFragment = (JudgeFragment) frMap.get(position);
//                    judgeFragment.mediaPlayer.stop();
////                        judgeFragment.submitHomework(jude);
////                    }
//                    break;
//                case Constant.Choice:/*选择题*/
//
////                    ChoiceQuestionModle choice = (ChoiceQuestionModle) mDatalist.get(position);
////                    if (frMap.get(position) != null) {
//                    ChoiceFragment choiceFragment = (ChoiceFragment) frMap.get(position);
//                    choiceFragment.mediaPlayer.stop();
////                        choiceFragment.submitHomework(choice);
////                    }
//                    break;
//                case Constant.Sort:/*排序题*/
//
////                    SortQuestionModle sort = (SortQuestionModle) mDatalist.get(position);
////                    if (frMap.get(position) != null) {
//                    SortFragment sortFragment = (SortFragment) frMap.get((currentposition));
//                    sortFragment.mediaPlayer.stop();
////                        sortFragment.submitHomework(sort);
////                    }
//                    break;
//                case Constant.Line:/*连线题*/
//
////                    LineQuestionModle line = (LineQuestionModle) mDatalist.get(position);
////                    if (frMap.get(position) != null) {
//                    LineFragment linFragment = (LineFragment) frMap.get((position));
//                    linFragment.mediaPlayer.stop();
////                        linFragment.submitHomework(line);
////                    }
//                    break;
//                case Constant.Completion:/*填空*/
//
////                    CompletionQuestionModle complete = (CompletionQuestionModle) mDatalist.get(position);
////                    if (frMap.get(position) != null) {
//                    CompletionFragment completionFragment = (CompletionFragment) frMap.get((position));
//                    completionFragment.mediaPlayer.stop();
////                        completionFragment.submitHomework(complete);
////                    }
//                    break;
//
//                default:
//                    break;
//            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
//
//    /*判断题回调接口*/
//    @Override
//    public void submitJudgeFragment(JudjeQuestionModle questionModle) {
////        int eachposition = questionModle.getEachposition();
//        mDatalist.set(currentposition, questionModle);
//
//    }
//
//    /*选择题回调接口*/
//    @Override
//    public void submitChoiceFragment(ChoiceQuestionModle questionModle) {
//
//        mDatalist.set(currentposition, questionModle);
//    }
//
//
//    /*排序题回调接口*/
//    @Override
//    public void submitSoreFragment(SortQuestionModle questionModle) {
//
//        mDatalist.set(currentposition, questionModle);
//    }
//
//    /*连线题回调接口*/
//    @Override
//    public void submitLineFragment(LineQuestionModle questionModle) {
//
//        mDatalist.set(currentposition, questionModle);
//    }
//
//    /*填空题回掉接口*/
//    @Override
//    public void submitCompletionFragment(CompletionQuestionModle questionModle) {
//
//        mDatalist.set(currentposition, questionModle);
//    }


    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == DjhJumpUtil.getInstance().activity_answerCardComplete && resultCode == DjhJumpUtil.getInstance().activity_todohomework) {
            int current_num = data.getIntExtra("current_num", -1);
            mViewpager.setCurrentItem(current_num);
        }
    }

    /*监听虚拟按键*/
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) { //监控/拦截/屏蔽返回键

            finishActivity();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

}
