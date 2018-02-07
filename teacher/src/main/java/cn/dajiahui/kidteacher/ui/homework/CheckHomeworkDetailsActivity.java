package cn.dajiahui.kidteacher.ui.homework;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.fxtx.framework.http.callback.ResultCallback;
import com.fxtx.framework.json.HeadJson;
import com.fxtx.framework.log.ToastUtil;
import com.fxtx.framework.ui.FxActivity;
import com.squareup.okhttp.Request;

import java.util.ArrayList;
import java.util.List;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.http.RequestUtill;
import cn.dajiahui.kidteacher.ui.homework.adapter.ApCheckHomeworkDetails;
import cn.dajiahui.kidteacher.ui.homework.bean.BeCheckHomeworkDetails;
import cn.dajiahui.kidteacher.ui.homework.bean.BeCheckHomeworkQuestionDetails;

/**
 * 检查作业详情
 */
public class CheckHomeworkDetailsActivity extends FxActivity {

//    private TextView mTitle;
//    private TextView mLeft;
    private String homeworkId;
    private ListView mListview;
    private TextView tvNull;
    private ApCheckHomeworkDetails apCheckHomeworkDetails;
    private List<BeCheckHomeworkQuestionDetails>  questionDetailsList = new ArrayList<BeCheckHomeworkQuestionDetails>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setfxTtitle(R.string.task_homeworkdetails);
        onBackText();

        homeworkId = getIntent().getExtras().getString("homeworkId");

        httpData(); // 作业详情
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_check_homework_details);
        mListview = getView(R.id.listview);

        apCheckHomeworkDetails = new ApCheckHomeworkDetails(this, questionDetailsList);

        mListview.setAdapter(apCheckHomeworkDetails);

        tvNull = getView(R.id.tv_null);
    }

    @Override
    public void httpData() {
        //网络请求
        RequestUtill.getInstance().httpGetCheckHomeworkDetails(context, callCheckHomework, homeworkId); // 作业详情

    }

    /**
     * 通知后台检查作业了的callback
     */
    ResultCallback callCheckHomework = new ResultCallback() {
        @Override
        public void onError(Request request, Exception e) {
            dismissfxDialog();
        }

        @Override
        public void onResponse(String response) {
            dismissfxDialog();
            HeadJson json = new HeadJson(response);
            if (json.getstatus() == 0) {
                /* 解析信息 */
                BeCheckHomeworkDetails temp = json.parsingObject(BeCheckHomeworkDetails.class);
                if (temp.getList() != null && temp.getList().size() > 0) {
                    questionDetailsList.clear();

//                    BeCheckHomeworkQuestionDetails ss = new BeCheckHomeworkQuestionDetails();  // 测试用
//                    ss.setCompleteCnt("10");
//                    ss.setCorrectRate("0.00156");
//                    ss.setTotalUsers("20");
//                questionDetailsList.add(ss);
                    questionDetailsList.addAll(temp.getList());
                apCheckHomeworkDetails.notifyDataSetChanged();
                } else {
                    tvNull.setText(R.string.not_data);
                    mListview.setEmptyView(tvNull);
                }
            } else {
                ToastUtil.showToast(context, json.getMsg());
            }
        }
    };

    private View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                default:
                    break;
            }
        }
    };
}
