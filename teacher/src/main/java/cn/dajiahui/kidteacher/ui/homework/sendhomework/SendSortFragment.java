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

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.ui.homework.bean.SortQuestionModle;
import cn.dajiahui.kidteacher.ui.homework.homeworksdetails.BaseHomeworkFragment;
import cn.dajiahui.kidteacher.ui.homework.sendhomework.view.SendSortLeftImagview;
import cn.dajiahui.kidteacher.ui.homework.sendhomework.view.SendSortRightImagview;

import static cn.dajiahui.kidteacher.ui.homework.sendhomework.SendHomeworkDetailsActivity.screenWidth;


/**
 * 排序题
 */


public class SendSortFragment extends BaseHomeworkFragment {


    private SortQuestionModle inbasebean;
    private RelativeLayout relaroot, answerroot;
    int mLeftTop = 0;
    int mRightTop = 0;
    private TextView tv_sort, tv_schedule;
    private ImageView img_play;//播放器按钮
    private String mediaUrl;
    private String title;
    private Bundle bundle;
    private ScrollView mScrollview;
    private CheckBox mSendCheck;
    private SendSubmitSortFragment submit;

    @Override
    protected View initinitLayout(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fr_sort_sendhomework, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize();

        tv_sort.setText(title);
        tv_schedule.setText(bundle.getString("currntQuestion"));

        /*添加右侧视图*/
        addGroupImage(inbasebean.getOptions().size(), relaroot);
        /*添加左侧图片*/
        addGroupMoviewImage(inbasebean.getOptions().size(), relaroot);

    }

    /*添加右侧图片*/
    private void addGroupImage(int size, RelativeLayout lin) {

        for (int i = 0; i < size; i++) {
            SendSortRightImagview fixedImagview = new SendSortRightImagview(getActivity(), i);
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(screenWidth / 5, screenWidth / 5);
            lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
            lp.topMargin = mRightTop;
            mRightTop += screenWidth / 4;
            lp.rightMargin = screenWidth / 6;
            fixedImagview.setLayoutParams(lp);

            lin.addView(fixedImagview); //动态添加图片

        }
    }

    /*添加左侧图片*/

    private void addGroupMoviewImage(int size, RelativeLayout lin) {

        for (int i = 0; i < size; i++) {
            SendSortLeftImagview mMoveView = new SendSortLeftImagview(getActivity(), i, inbasebean);
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(screenWidth / 5, screenWidth / 5);
            lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
            lp.topMargin = mLeftTop;
            mLeftTop += screenWidth / 4;
            lp.leftMargin = screenWidth / 6;
            mMoveView.setLayoutParams(lp);
            lin.addView(mMoveView); //动态添加图片
        }
    }


    @Override
    public void setArguments(Bundle bundle) {
        this.bundle = bundle;
        inbasebean = (SortQuestionModle) bundle.get("SortQuestionModle");
        mediaUrl = inbasebean.getMedia();
        title = inbasebean.getTitle();
    }

    /*初始化*/
    private void initialize() {
        mScrollview = getView(R.id.scrollview);
        img_play = getView(R.id.img_play);
        tv_schedule = getView(R.id.tv_schedule);
        tv_sort = getView(R.id.tv_sort);
        answerroot = getView(R.id.answerroot);
        relaroot = getView(R.id.relaroot);
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
                    submit.sendSortFragment(inbasebean);

                    break;
                default:
                    break;
            }
        }
    };


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

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        submit = (SendSubmitSortFragment) activity;

    }


    /*保存排序数据接口*/
    public interface SendSubmitSortFragment {
        public void sendSortFragment(SortQuestionModle questionModle);
    }

}

