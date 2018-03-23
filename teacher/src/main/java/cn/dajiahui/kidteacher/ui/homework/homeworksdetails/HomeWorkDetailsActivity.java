package cn.dajiahui.kidteacher.ui.homework.homeworksdetails;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.fxtx.framework.http.callback.ResultCallback;
import com.fxtx.framework.json.HeadJson;
import com.fxtx.framework.log.ToastUtil;
import com.fxtx.framework.text.ParseUtil;
import com.fxtx.framework.ui.FxActivity;
import com.squareup.okhttp.Request;

import java.util.ArrayList;
import java.util.List;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.http.RequestUtill;
import cn.dajiahui.kidteacher.ui.homework.adapter.ApHomeWorkDetail;
import cn.dajiahui.kidteacher.ui.homework.bean.BeAnswerSheet;
import cn.dajiahui.kidteacher.ui.homework.bean.BeHomeWorkDetails;
import cn.dajiahui.kidteacher.util.DateUtils;
import cn.dajiahui.kidteacher.util.DjhJumpUtil;
import cn.dajiahui.kidteacher.util.Logger;

/*
* 作业详情初级页面
* */
public class HomeWorkDetailsActivity extends FxActivity {
    private String homeworkId;
    private String userId;
    private TextView mHomeworkname;
    private TextView mcompletetime;
    private TextView mCorrectRate;
    private RatingBar mRbScore;
    private GridView mGrildview;
    private List<BeAnswerSheet> mBeAnswerSheetList = new ArrayList<>();
    private ApHomeWorkDetail apHomeWorkDetail;//作业详情适配器
    private String nickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeworkId = getIntent().getExtras().getString("homeworkId");
        userId = getIntent().getExtras().getString("userId");
        nickname = getIntent().getExtras().getString("nickname");
        setfxTtitle(nickname + "的作业");
        onBackText();
        httpData();
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_home_work_details);

        mHomeworkname = getView(R.id.tv_homeworkname);
        mcompletetime = getView(R.id.tv_completetime);
        mCorrectRate = getView(R.id.tv_correct_rate);
        mRbScore = getView(R.id.rb_score);
        mGrildview = getView(R.id.grildview);
        Button mBtnHomework = getView(R.id.btn_homework);

        apHomeWorkDetail = new ApHomeWorkDetail(HomeWorkDetailsActivity.this, mBeAnswerSheetList);

        mGrildview.setAdapter(apHomeWorkDetail);

        /*浏览作业*/
        mBtnHomework.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("userId", userId);
                bundle.putString("homeworkId", homeworkId);
                bundle.putString("nickname", nickname);
                DjhJumpUtil.getInstance().startBaseActivity(HomeWorkDetailsActivity.this, DoHomeworkActivity.class, bundle, 0); // 跳转到个人作业详情页面


            }
        });
    }

    @Override
    public void httpData() {
        //网络请求
        RequestUtill.getInstance().httpRequestHomeworkDetail(context, callRequestHomeworkDetail, homeworkId, userId); // 取得作业详情

    }

    /**
     * 取得班级列表的callback
     */
    ResultCallback callRequestHomeworkDetail = new ResultCallback() {
        @Override
        public void onError(Request request, Exception e) {
            dismissfxDialog();
        }

        @SuppressLint("ResourceType")
        @Override
        public void onResponse(String response) {
            Logger.d("response:" + response);
            dismissfxDialog();
            HeadJson json = new HeadJson(response);

            if (json.getstatus() == 0) {
                BeHomeWorkDetails beHomeWorkDetails = json.parsingObject(BeHomeWorkDetails.class);
                if (beHomeWorkDetails != null) {
                    mHomeworkname.setText(beHomeWorkDetails.getName());
                    mcompletetime.setText(DateUtils.completeHomeWorktime(beHomeWorkDetails.getComplete_time()));
                    String correct_rate = beHomeWorkDetails.getCorrect_rate();
                    double v = Double.parseDouble(correct_rate) * 100;
                    mCorrectRate.setText("正确率：" + (int) v + "%");//正确率

                    mRbScore.setMax(100);
                    /*打分的分数 */
                    mRbScore.setProgress(getScore((int) (ParseUtil.parseFloat(beHomeWorkDetails.getCorrect_rate()) * 100)));
                    mBeAnswerSheetList.addAll(beHomeWorkDetails.getAnswer_sheet());
                    apHomeWorkDetail.notifyDataSetChanged();
                }
            } else {
                ToastUtil.showToast(context, json.getMsg());
            }
        }
    };

    /*评分算法 20分为一颗星*/
    private int getScore(int score) {
        if (0 == score) {
            return 0;
        } else if (0 < score && score <= 20) {
            return 20;
        } else if (20 < score && score <= 40) {
            return 40;
        } else if (40 < score && score <= 60) {
            return 60;
        } else if (60 < score && score <= 80) {
            return 80;
        } else if (80 < score && score <= 100) {
            return 100;
        }
        return 0;
    }
}
