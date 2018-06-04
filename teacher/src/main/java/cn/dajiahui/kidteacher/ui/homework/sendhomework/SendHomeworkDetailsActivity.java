package cn.dajiahui.kidteacher.ui.homework.sendhomework;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import cn.dajiahui.kidteacher.ui.homework.homeworksdetails.AnswerCardCompleteActivity;
import cn.dajiahui.kidteacher.ui.homework.homeworksdetails.BaseHomeworkFragment;
import cn.dajiahui.kidteacher.ui.homework.homeworksdetails.DoHomeworkActivity;
import cn.dajiahui.kidteacher.ui.homework.sendhomework.bean.BeHomeworkPreView;
import cn.dajiahui.kidteacher.ui.homework.sendhomework.bean.BeSendHomeWorkPreview;
import cn.dajiahui.kidteacher.util.DjhJumpUtil;


/*
 * 选择作业详情（可以勾选具体的一道题）
 *
 * */
public class SendHomeworkDetailsActivity extends FxActivity implements BaseHomeworkFragment.GetBaseHomeworkFragment,
        SendJudgeFragment.SubmitSendJudgeFragment, SendChoiceFragment.SubmitSendChoiseFragment, SendSortFragment.SendSubmitSortFragment, SendLineFragment.SendSubmitLineFragment, SendCompletionFragment.SendSubmitCompletionFragment {
    private cn.dajiahui.kidteacher.ui.homework.view.NoScrollViewPager mViewpager;
    public static int screenWidth;//屏幕宽度
    private ArrayList<Object> mDatalist;//所有数据的模型集合

    private String subjectype = "";//当前题型
    private Map<Integer, BaseHomeworkFragment> frMap = new HashMap();//保存每个不同类型的Fragment
    private BaseHomeworkFragment baseHomeworkFragment;//每个题型碎片的实例
    private String bookId;
    private String unitId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewpager.setNoScroll(false);//作业可以滑动
        setfxTtitle("布置详情");
        onRightBtn(R.string.Look);
        bookId = getIntent().getStringExtra("bookId");
        unitId = getIntent().getStringExtra("unitId");
        httpData();
        onBack();
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_choice_details);
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

        RequestUtill.getInstance().httpSelectQuestion(SendHomeworkDetailsActivity.this, callSelectQuestion, bookId, unitId);
    }

    /**
     * 学生作业所有题callback函数
     */
    ResultCallback callSelectQuestion = new ResultCallback() {

        @Override
        public void onError(Request request, Exception e) {

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

                    /*作业适配器*/
                    HomeWorkAdapter homeWorkAdapter = new HomeWorkAdapter(getSupportFragmentManager(), mdata);
                    mViewpager.setAdapter(homeWorkAdapter);
                    mViewpager.setOnPageChangeListener(onPageChangeListener);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                ToastUtil.showToast(SendHomeworkDetailsActivity.this, headJson.getMsg());
            }
        }
    };


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

                SendJudgeFragment fr1 = new SendJudgeFragment();

                Bundle judgeBundle = new Bundle();
                judgeBundle.putSerializable("JudgeQuestionModle", (Serializable) mDatalist.get(position));
                judgeBundle.putString("currntQuestion", (position + 1) + "/" + data.size());
                fr1.setArguments(judgeBundle);
                frMap.put(position, fr1);

                return fr1;
            } else if (subjectype.equals(Constant.Choice)) { /*选择*/

                SendChoiceFragment fr2 = new SendChoiceFragment();
                Bundle choiceBundle = new Bundle();
                choiceBundle.putSerializable("ChoiceQuestionModle", (Serializable) mDatalist.get(position));
                choiceBundle.putString("currntQuestion", (position + 1) + "/" + data.size());
                fr2.setArguments(choiceBundle);
                frMap.put(position, fr2);
                return fr2;

            } else if (subjectype.equals(Constant.Sort)) {/*排序*/

                SendSortFragment fr3 = new SendSortFragment();

                Bundle sortBundle = new Bundle();
                sortBundle.putSerializable("SortQuestionModle", (Serializable) mDatalist.get(position));
                sortBundle.putString("currntQuestion", (position + 1) + "/" + data.size());
                frMap.put(position, fr3);
                fr3.setArguments(sortBundle);
                return fr3;

            } else if (subjectype.equals(Constant.Line)) {   /*连线*/

                SendLineFragment fr4 = new SendLineFragment();

                Bundle linebBundle = new Bundle();
                linebBundle.putSerializable("LineQuestionModle", (Serializable) mDatalist.get(position));
                linebBundle.putString("currntQuestion", (position + 1) + "/" + data.size());
                frMap.put(position, fr4);
                fr4.setArguments(linebBundle);
                return fr4;

            } else if (subjectype.equals(Constant.Completion)) {  /*填空*/

                SendCompletionFragment fr5 = new SendCompletionFragment();

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

    /*判断题接口回调*/
    @Override
    public void sendJudgeFragment(JudjeQuestionModle questionModle) {

        Logger.d("questionModle：" + questionModle.getSendItemFlag());
    }

    /*选择题回调接口*/
    @Override
    public void sendChoiceFragment(ChoiceQuestionModle questionModle) {

    }

    /*排序题回调接口*/
    @Override
    public void sendSortFragment(SortQuestionModle questionModle) {

    }

    /*连线题回调接口*/
    @Override
    public void sendLineFragment(LineQuestionModle questionModle) {

    }

    /*填空题接口回调*/
    @Override
    public void sendCompletionFragment(CompletionQuestionModle questionModle) {

    }

    /*viewpager滑动监听*/
    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            /*滑动停止音频*/
            if (baseHomeworkFragment != null) {
                baseHomeworkFragment.stopAutio();
                baseHomeworkFragment.stopAnimation();
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };


    private List<BeHomeworkPreView> bePreViewList = new ArrayList<>();

    @Override
    public void onRightBtnClick(View view) {
        bePreViewList.clear();
        for (int i = 0; i < mDatalist.size(); i++) {
            /*得到基本的数据模型 取出题型的标记*/
            QuestionModle qm = (QuestionModle) mDatalist.get(i);
            /*判断题型*/
            switch (qm.getQuestion_cate_id()) {

                case Constant.Judje:
                    JudjeQuestionModle jqm = (JudjeQuestionModle) mDatalist.get(i);
                    if (jqm.getSendItemFlag() == true)
                        bePreViewList.add(new BeHomeworkPreView(i, jqm.getId()));
                    break;
                case Constant.Choice:
                    ChoiceQuestionModle cqm = (ChoiceQuestionModle) mDatalist.get(i);
                    if (cqm.getSendItemFlag() == true)
                        bePreViewList.add(new BeHomeworkPreView(i, cqm.getId()));
                    break;
                case Constant.Sort:
                    SortQuestionModle sqm = (SortQuestionModle) mDatalist.get(i);
                    if (sqm.getSendItemFlag() == true)
                        bePreViewList.add(new BeHomeworkPreView(i, sqm.getId()));
                    break;
                case Constant.Line:
                    LineQuestionModle lqm = (LineQuestionModle) mDatalist.get(i);
                    if (lqm.getSendItemFlag() == true)
                        bePreViewList.add(new BeHomeworkPreView(i, lqm.getId()));

                    break;
                case Constant.Completion:
                    CompletionQuestionModle coqm = (CompletionQuestionModle) mDatalist.get(i);
                    if (coqm.getSendItemFlag() == true)
                        bePreViewList.add(new BeHomeworkPreView(i, coqm.getId()));
                    break;
                default:
                    break;
            }

        }

        if (bePreViewList.size() == 0) {
            ToastUtil.showToast(context,"请选择布置的题");
        } else {
            Bundle bundle = new Bundle();
            bundle.putSerializable("SENDHOMEWORKPREVIEW", new BeSendHomeWorkPreview(bePreViewList, bookId, unitId));

            DjhJumpUtil.getInstance().startBaseActivityForResult(SendHomeworkDetailsActivity.this, SendHomeworkPreviewActivity.class, bundle, DjhJumpUtil.getInstance().activity_sendhomeworkPreviewrequest);

        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*选择预览界面回传*/
        if (requestCode == DjhJumpUtil.getInstance().activity_sendhomeworkPreviewrequest && resultCode == DjhJumpUtil.getInstance().activity_sendhomeworkPreviewresultCode) {
            int current_num = data.getIntExtra("current_num", -1);
            mViewpager.setCurrentItem(current_num);
        } else if (requestCode == DjhJumpUtil.getInstance().activity_sendhomeworkPreviewrequest && resultCode == RESULT_OK) {
            setResult(RESULT_OK);
            finishActivity();
        }

    }

}
