package cn.dajiahui.kidteacher.ui.homework.sendhomework;

import android.app.Activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;


import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.ui.homework.bean.JudjeQuestionModle;
import cn.dajiahui.kidteacher.ui.homework.homeworksdetails.BaseHomeworkFragment;
import cn.dajiahui.kidteacher.ui.homework.sendhomework.view.SendJudgeAnswerView;
import cn.dajiahui.kidteacher.util.Logger;


/**
 * 判断题
 */
public class SendJudgeFragment extends BaseHomeworkFragment {


    private JudjeQuestionModle inbasebean;//数据模型
    private TextView tv_judge, tv_schedule;
    private ImageView imgconment, img_play;
    private SubmitSendJudgeFragment submit;
    private RelativeLayout answerRoot;
    private String mediaUrl;
    private Bundle bundle;
    private CheckBox mSendCheck;
    private ScrollView mScrollview;


    @Override
    protected View initinitLayout(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fr_judge_sendhomework, null);
    }

    @Override
    public void setArguments(Bundle bundle) {
        this.bundle = bundle;
        inbasebean = (JudjeQuestionModle) bundle.get("JudgeQuestionModle");
        mediaUrl = inbasebean.getMedia();
    }

    /*动态添加选择视图*/
    private void addGroupImage(int size) {
        for (int i = 0; i < size; i++) {
            SendJudgeAnswerView judgeImagview = new SendJudgeAnswerView(getActivity(), inbasebean, i);
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(SendHomeworkDetailsActivity.screenWidth / 5, SendHomeworkDetailsActivity.screenWidth / 5);
            if (i == 0) {
                lp.addRule(RelativeLayout.LEFT_OF, R.id.centerline);
                lp.rightMargin = 100;
            } else {
                lp.addRule(RelativeLayout.RIGHT_OF, R.id.centerline);
                lp.leftMargin = 100;
            }
            judgeImagview.setLayoutParams(lp);
            answerRoot.addView(judgeImagview);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize();

        tv_judge.setText(inbasebean.getTitle());
        tv_schedule.setText(bundle.getString("currntQuestion"));
        /*加载选择视图*/
        addGroupImage(inbasebean.getOptions().size());

        /*加载内容图片*/
        Glide.with(getActivity()).load(inbasebean.getQuestion_stem()).asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgconment);

    }


    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        submit = (SubmitSendJudgeFragment) activity;
    }


    /*初始化数据*/
    private void initialize() {
        mScrollview = getView(R.id.scrollview);
        answerRoot = getView(R.id.judge_root);
        tv_judge = getView(R.id.tv_judge);
        tv_schedule = getView(R.id.tv_schedule);
        imgconment = getView(R.id.img_conment);
        img_play = getView(R.id.img_play);
        mSendCheck = getView(R.id.send_check);
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
                    }else {
                        audioDialog.show();
                    }

                    break;

                case R.id.send_check:
                    if (mSendCheck.isChecked() == true) {
                        inbasebean.setSendItemFlag(true);

                    } else {
                        inbasebean.setSendItemFlag(false);

                    }
                    submit.sendJudgeFragment(inbasebean);

                    break;
                default:
                    break;
            }
        }
    };

    /*翻页回来*/
    @Override
    public void submitHomework(Object questionModle) {

        Logger.d("翻页回来判断题：" + ((JudjeQuestionModle) questionModle).getSendItemFlag());

    }

    /*保存选择题接口*/
    public interface SubmitSendJudgeFragment {
        public void sendJudgeFragment(JudjeQuestionModle questionModle);

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


}

