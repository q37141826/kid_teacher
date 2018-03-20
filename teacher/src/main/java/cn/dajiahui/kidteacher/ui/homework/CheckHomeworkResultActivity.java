package cn.dajiahui.kidteacher.ui.homework;

import android.os.Bundle;
import android.widget.ListView;

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
import cn.dajiahui.kidteacher.ui.homework.adapter.ApCheckResult;
import cn.dajiahui.kidteacher.ui.homework.bean.BeHomewrokStudent;

/**
 * 检查作业结果
 */
public class CheckHomeworkResultActivity extends FxActivity {

    private String homeworkId;
    private ListView resultListView;

    List<List<BeHomewrokStudent>> resultList = new ArrayList<List<BeHomewrokStudent>>();
    List<BeHomewrokStudent> listFinish = new ArrayList<BeHomewrokStudent>(); // 已完成的学生列表
    List<BeHomewrokStudent> listDoing = new ArrayList<BeHomewrokStudent>(); // 进行中的学生列表
    List<BeHomewrokStudent> listNotStart = new ArrayList<BeHomewrokStudent>(); // 未开始的学生列表
    List<BeHomewrokStudent> listNotFinish = new ArrayList<BeHomewrokStudent>(); // 未完成的学生列表(进行中和未开始的合集)

    ApCheckResult apCheckResult; // 作业结果Adapter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setfxTtitle(R.string.task_checkhomeworkresult);
        onBackText();

        homeworkId = getIntent().getExtras().getString("homeworkId");

        listFinish = (List<BeHomewrokStudent>) getIntent().getSerializableExtra("listFinish");
        listDoing = (List<BeHomewrokStudent>) getIntent().getSerializableExtra("listDoing");
        listNotStart = (List<BeHomewrokStudent>) getIntent().getSerializableExtra("listNotStart");

        resultList.add(listFinish);
        listNotFinish.addAll(listDoing);
        listNotFinish.addAll(listNotStart);
        resultList.add(listNotFinish);

        apCheckResult = new ApCheckResult(this, resultList);
        resultListView.setAdapter(apCheckResult);

        httpData(); // 通知后台检查作业了
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_check_homework_result);
        resultListView = getView(R.id.listview);
    }

    @Override
    public void httpData() {
        //网络请求
        RequestUtill.getInstance().httpCheckHomework(context, callCheckHomework, homeworkId, 1); // 通知后台检查作业了

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
            Logger.d("檢查作业完成：" + response);

            dismissfxDialog();
            HeadJson json = new HeadJson(response);
            if (json.getstatus() == 0) {
                /* 解析信息 */

            } else {
                ToastUtil.showToast(context, json.getMsg());
            }
        }
    };

}
