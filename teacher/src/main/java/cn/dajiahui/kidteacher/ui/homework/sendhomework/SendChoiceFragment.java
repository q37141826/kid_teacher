package cn.dajiahui.kidteacher.ui.homework.sendhomework;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.ui.homework.bean.BeChoiceOptions;
import cn.dajiahui.kidteacher.ui.homework.bean.ChoiceQuestionModle;
import cn.dajiahui.kidteacher.ui.homework.homeworksdetails.BaseHomeworkFragment;
import cn.dajiahui.kidteacher.ui.homework.sendhomework.adapter.ApSendChoice;


/**
 * 选择
 */

public class SendChoiceFragment extends BaseHomeworkFragment {


    private ListView mListview;
    private ChoiceQuestionModle inbasebean;
    private TextView tv_choice, tv_schedule;
    private ImageView img_play, img_conment;
    private SubmitSendChoiseFragment submit;
    private ApSendChoice apChoice;
    private String mediaUrl;
    private CheckBox mSendCheck;
    private ScrollView mScrollview;


    @Override
    protected View initinitLayout(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fr_choice_sendhomework, null);
    }

    Bundle bundle;

    @Override
    public void setArguments(Bundle bundle) {
        this.bundle = bundle;
        inbasebean = (ChoiceQuestionModle) bundle.get("ChoiceQuestionModle");
        mediaUrl = inbasebean.getMedia();

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize();

        tv_choice.setText(inbasebean.getTitle());
        tv_schedule.setText(bundle.getString("currntQuestion"));
        /*加载内容图片*/
        Glide.with(getActivity()).load(inbasebean.getQuestion_stem()).asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL).into(img_conment);

        List<BeChoiceOptions> options = inbasebean.getOptions();
        apChoice = new ApSendChoice(getActivity(), options, inbasebean);


        /*设置适配器*/
        mListview.setAdapter(apChoice);
        /*设置listview的高度*/
        setHeight();
    }

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        submit = (SubmitSendChoiseFragment) activity;

    }

    /*设置高度*/
    public void setHeight() {
        int height = 0;
        int count = apChoice.getCount();
        for (int i = 0; i < count; i++) {
            View temp = apChoice.getView(i, null, mListview);
            temp.measure(0, 0);
            height += temp.getMeasuredHeight() + 30;
        }
        ViewGroup.LayoutParams params = this.mListview.getLayoutParams();
        mListview.setDividerHeight(30);
        params.height = height;
        mListview.setLayoutParams(params);
    }


    /*初始化*/
    private void initialize() {
        mScrollview = getView(R.id.srcllview);
        mListview = getView(R.id.listview);
        tv_choice = getView(R.id.tv_choice);
        img_play = getView(R.id.img_play);
        tv_schedule = getView(R.id.tv_schedule);
        img_conment = getView(R.id.img_conment);
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
                    submit.sendChoiceFragment(inbasebean);

                    break;
                default:
                    break;
            }

        }
    };

    /*翻页回来*/
    @Override
    public void submitHomework(Object questionModle) {
        if (questionModle != null) {

        }
    }

    public interface SubmitSendChoiseFragment {
        public void sendChoiceFragment(ChoiceQuestionModle questionModle);
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

