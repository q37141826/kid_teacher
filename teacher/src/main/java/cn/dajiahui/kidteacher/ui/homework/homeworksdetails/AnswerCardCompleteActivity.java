package cn.dajiahui.kidteacher.ui.homework.homeworksdetails;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.fxtx.framework.http.callback.ResultCallback;
import com.fxtx.framework.json.HeadJson;
import com.fxtx.framework.log.Logger;
import com.fxtx.framework.log.ToastUtil;
import com.fxtx.framework.ui.FxActivity;
import com.squareup.okhttp.Request;

import java.util.ArrayList;
import java.util.List;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.http.RequestUtill;
import cn.dajiahui.kidteacher.ui.homework.adapter.ApHomeWorkDetail;
import cn.dajiahui.kidteacher.ui.homework.bean.BeAnswerSheet;
import cn.dajiahui.kidteacher.ui.homework.bean.BeHomeWorkDetails;
import cn.dajiahui.kidteacher.util.DjhJumpUtil;

public class AnswerCardCompleteActivity extends FxActivity {


    private GridView grildview;

    private List<BeAnswerSheet> mBeAnswerSheetList = new ArrayList<>();
    private ApHomeWorkDetail apHomeWorkDetail;

    private String homeworkId, userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeworkId = getIntent().getExtras().getString("homeworkId");
        userId = getIntent().getExtras().getString("userId");
        setfxTtitle("答题卡");
        onBackText();
        httpData();
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_answer_card_complete);
        initialize();

        apHomeWorkDetail = new ApHomeWorkDetail(AnswerCardCompleteActivity.this, mBeAnswerSheetList);
        grildview.setAdapter(apHomeWorkDetail);


        /*item条目点击事件*/
        grildview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent();
                intent.putExtra("current_num", position);

                setResult(DjhJumpUtil.getInstance().activity_todohomework, intent);
                finishActivity();
            }
        });

    }

    /*初始化*/
    private void initialize() {

        grildview = (GridView) findViewById(R.id.grildview);

    }

    @Override
    public void httpData() {
        super.httpData();
        showfxDialog();

        RequestUtill.getInstance().httpRequestHomeworkDetail(AnswerCardCompleteActivity.this, callRequestHomeworkDetail, homeworkId, userId);

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
            Logger.d("答题卡response:" + response);
            dismissfxDialog();
            HeadJson json = new HeadJson(response);

            if (json.getstatus() == 0) {
                BeHomeWorkDetails beHomeWorkDetails = json.parsingObject(BeHomeWorkDetails.class);
                if (beHomeWorkDetails != null) {
                    mBeAnswerSheetList.addAll(beHomeWorkDetails.getAnswer_sheet());
                    apHomeWorkDetail.notifyDataSetChanged();
                }
            } else {
                ToastUtil.showToast(context, json.getMsg());
            }
        }
    };
}

