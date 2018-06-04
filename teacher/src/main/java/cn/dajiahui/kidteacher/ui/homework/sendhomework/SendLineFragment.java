package cn.dajiahui.kidteacher.ui.homework.sendhomework;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.fxtx.framework.file.FileUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.ui.homework.adapter.Dir;

import cn.dajiahui.kidteacher.ui.homework.bean.LineQuestionModle;

import cn.dajiahui.kidteacher.ui.homework.homeworksdetails.BaseHomeworkFragment;
import cn.dajiahui.kidteacher.ui.homework.sendhomework.view.SendLineImagePointView;



import static cn.dajiahui.kidteacher.controller.Constant.lineView_margin;

import static cn.dajiahui.kidteacher.ui.homework.sendhomework.SendHomeworkDetailsActivity.screenWidth;


/**
 * 连线题
 */
/*显示我的答案和正确答案之后要禁止连线*/
public class SendLineFragment extends BaseHomeworkFragment {

    private LineQuestionModle inbasebean;
    private SendSubmitLineFragment submit;
    private ImageView img_play;
    private TextView tv_line, tv_schedule;
    private RelativeLayout selectview_root, draw_root;


    int mLeftTop = 0;
    int mRightTop = 0;

    private String mediaUrl;


    private String title;
    private CheckBox mSendCheck;
    private ScrollView mScrollview;

    @Override
    protected View initinitLayout(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fr_line_sendhomework, null);
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
            SendLineImagePointView mView = new SendLineImagePointView(getActivity(), i, inbasebean, direction);//this,
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            if (direction == Dir.left) {
                lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
                lp.topMargin = mLeftTop;
                mLeftTop += screenWidth / 4;
                lp.leftMargin = lineView_margin;

            } else {
                lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
                lp.topMargin = mRightTop;
                lp.rightMargin = lineView_margin;
                mRightTop += screenWidth / 4;
            }
            mView.setLayoutParams(lp);
            lin.addView(mView); //动态添加图片
        }

    }


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


        }

    }

    /*初始化*/
    @SuppressLint("ResourceType")
    private void initialize() {
        mScrollview = getView(R.id.scrollview);
        draw_root = getView(R.id.draw_root);
        selectview_root = getView(R.id.selectview_root);
        img_play = getView(R.id.img_play);
        tv_line = getView(R.id.tv_line);
        tv_schedule = getView(R.id.tv_schedule);
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
                    submit.sendLineFragment(inbasebean);

                    break;
                default:
                    break;
            }
        }
    };

    /*与activity通信*/
    public interface SendSubmitLineFragment {
        public void sendLineFragment(LineQuestionModle questionModle);

    }


    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        submit = (SendSubmitLineFragment) activity;

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
