package cn.dajiahui.kidteacher.ui.homework.sendhomework;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.LinkedHashMap;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.ui.homework.bean.CompletionQuestionModle;
import cn.dajiahui.kidteacher.ui.homework.bean.CompletionQuestionadapterItemModle;
import cn.dajiahui.kidteacher.ui.homework.homeworksdetails.BaseHomeworkFragment;
import cn.dajiahui.kidteacher.ui.homework.sendhomework.adapter.SendCompleteAdapter;
import cn.dajiahui.kidteacher.view.NoSlideGrildView;


/**
 * 填空题
 */
public class SendCompletionFragment extends BaseHomeworkFragment {//CheckHomework, SubmitEditext,

    private CompletionQuestionModle inbasebean;
    private SendSubmitCompletionFragment submit;
    private TextView tvcompletion, tv_schedule;
    private ImageView img_play;
    private ImageView imgconment;
    private RelativeLayout stemroot;
    private LinearLayout horlistviewroot;
    /////////////////


    private int mTop = 0;//初始距离上端
    private int mTvTop = 0;//初始距离上端
    private String mediaUrl;//音频地址
    private Bundle bundle;
    private ScrollView mScrollview;
    private CheckBox mSendCheck;

    @Override
    protected View initinitLayout(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fr_completion_sendhomework, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initialize();

        tvcompletion.setText(inbasebean.getTitle());
        tv_schedule.setText(bundle.getString("currntQuestion"));
        /*加载内容图片*/
        Glide.with(getActivity()).load(inbasebean.getQuestion_stem()).asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgconment);


        /*解析正确答案（后台获取的正确答案）۞    分隔单词  然后自己拆分一个单词几个字母*/
        String standard_answer = inbasebean.getStandard_answer();

        /*多个空*/
        if (standard_answer.contains("۞")) {
            String[] strsTrue = standard_answer.split("۞");
            for (int i = 0, len = strsTrue.length; i < len; i++) {
                LinkedHashMap<Integer, CompletionQuestionadapterItemModle> mItemMap = new LinkedHashMap<>();//每个横滑dadpter的数据
                for (int b = 0; b < strsTrue[i].length(); b++) {
                    mItemMap.put(b, new CompletionQuestionadapterItemModle());
                }
                inbasebean.getmCompletionAllMap().put(i, mItemMap);
            }


        } else {//单个空

//                LinkedHashMap<Integer, String> mItemMap = new LinkedHashMap<>();//每个横滑dadpter的数据
//                for (int a = 0; a < my_answer.length(); a++) {
//
//                }
//                inbasebean.getmCompletionAllMap().put(0, mItemMap);
        }


//        /*添加题干*/
        addQuestionStem();
//        /*添加布局*/
        addHorizontalListView(inbasebean.getmCompletionAllMap().size());

    }

    /*添加填空题题干*/
    private void addQuestionStem() {
        TextView textView = new TextView(getActivity());
        textView.setTextSize(15);
        textView.setText(Html.fromHtml(inbasebean.getOptions()));
        stemroot.addView(textView);
    }


    /*添加布局*/
    @SuppressLint("ResourceType")
    private void addHorizontalListView(int size) {

        for (int i = 0; i < size; i++) {
            RelativeLayout relativeLayout = new RelativeLayout(getActivity());
            RelativeLayout.LayoutParams tvparams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            TextView textView = new TextView(getActivity());
            textView.setText((i + 1) + ".");
            textView.setTextSize(20);
            tvparams.topMargin = mTvTop;
            textView.setLayoutParams(tvparams);

            NoSlideGrildView grildView = new NoSlideGrildView(getActivity());

            grildView.setNumColumns(10);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.leftMargin = 80;


            grildView.setLayoutParams(params);

            final SendCompleteAdapter apCompleteGrildViewAdapter = new SendCompleteAdapter(getActivity(), i, inbasebean);

            grildView.setAdapter(apCompleteGrildViewAdapter);

            relativeLayout.addView(textView);
            relativeLayout.addView(grildView);

            horlistviewroot.addView(relativeLayout);

        }

    }


    /*先走setArguments 在走onPageSelected中的函数 最后走 submitHomework*/
    @Override
    public void setArguments(Bundle bundle) {

        this.bundle = bundle;
        inbasebean = (CompletionQuestionModle) bundle.get("CompletionQuestionModle");
        mediaUrl = inbasebean.getMedia();
    }


    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        submit = (SendSubmitCompletionFragment) activity;

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

    /*初始化*/
    private void initialize() {
        mScrollview = getView(R.id.srcllview);
        tvcompletion = getView(R.id.tv_completion);
        img_play = getView(R.id.img_play);
        imgconment = getView(R.id.img_conment);
        tv_schedule = getView(R.id.tv_schedule);
        horlistviewroot = getView(R.id.horlistviewroot);
        mSendCheck = getView(R.id.send_check);
        stemroot = getView(R.id.stemroot);
        mSendCheck.setOnClickListener(onClick);
        img_play.setOnClickListener(onClick);
        img_play.setBackground(animationDrawable);

    }

    private View.OnClickListener onClick = new View.OnClickListener() {


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

                case R.id.send_check:
                    if (mSendCheck.isChecked() == true) {

                        inbasebean.setSendItemFlag(true);
                    } else {
                        inbasebean.setSendItemFlag(false);
                    }
                    submit.sendCompletionFragment(inbasebean);

                    break;
                default:
                    break;
            }

        }
    };

    /**/
    public interface SendSubmitCompletionFragment {
        public void sendCompletionFragment(CompletionQuestionModle questionModle);
    }
}