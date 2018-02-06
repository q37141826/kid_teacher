package cn.dajiahui.kidteacher.ui.homework;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.fxtx.framework.http.callback.ResultCallback;
import com.fxtx.framework.json.HeadJson;
import com.fxtx.framework.log.ToastUtil;
import com.fxtx.framework.time.TimeUtil;
import com.fxtx.framework.ui.FxActivity;
import com.squareup.okhttp.Request;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.http.RequestUtill;
import cn.dajiahui.kidteacher.ui.homework.adapter.ApHomeworkReport;
import cn.dajiahui.kidteacher.ui.homework.bean.BeHomewrokReportInfo;
import cn.dajiahui.kidteacher.ui.homework.bean.BeHomewrokStudent;
import cn.dajiahui.kidteacher.ui.homework.bean.HomeworkReport;
import cn.dajiahui.kidteacher.util.DjhJumpUtil;

/**
 * 检查作业
 */
public class CheckHomeworkActivity extends FxActivity {

    String testString = "{\n" +
            "    \"status\": 0,\n" +
            "    \"msg\": \"操作成功\",\n" +
            "    \"data\": {\n" +
            "        \"id\": \"18\",\n" +
            "        \"name\": \"程度上的\",\n" +
            "        \"start_time\": \"1517553301\",\n" +
            "        \"end_time\": \"1519111800\",\n" +
            "        \"class_name\": \"曹操的班级\",\n" +
            "        \"correct_rate\": 0.62,\n" +
            "        \"all_students\": 15,\n" +
            "        \"complete_students\": 7,\n" +
            "        \"student_list\": [\n" +
            "            {\n" +
            "                \"status\": \"已完成\",\n" +
            "                \"status_key\": \"finish\",\n" +
            "                \"total\": 7,\n" +
            "                \"lists\": [\n" +
            "                    {\n" +
            "                        \"id\": \"16\",\n" +
            "                        \"nickname\": \"李时珍\",\n" +
            "                        \"avatar\": \"\",\n" +
            "                        \"correct_rate\": \"0.9646860779865515\",\n" +
            "                        \"updated_at\": \"1517556701\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"id\": \"17\",\n" +
            "                        \"nickname\": \"万试问\",\n" +
            "                        \"avatar\": \"\",\n" +
            "                        \"correct_rate\": \"0.8916432707492665\",\n" +
            "                        \"updated_at\": \"1517556701\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"id\": \"18\",\n" +
            "                        \"nickname\": \"路飞\",\n" +
            "                        \"avatar\": \"\",\n" +
            "                        \"correct_rate\": \"0.7950762694655696\",\n" +
            "                        \"updated_at\": \"1517556701\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"id\": \"21\",\n" +
            "                        \"nickname\": \"佩恩\",\n" +
            "                        \"avatar\": \"\",\n" +
            "                        \"correct_rate\": \"0.7302982311046666\",\n" +
            "                        \"updated_at\": \"1517556701\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"id\": \"20\",\n" +
            "                        \"nickname\": \"任我行\",\n" +
            "                        \"avatar\": \"\",\n" +
            "                        \"correct_rate\": \"0.7148383499261349\",\n" +
            "                        \"updated_at\": \"1517556701\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"id\": \"22\",\n" +
            "                        \"nickname\": \"阿斯蒂芬\",\n" +
            "                        \"avatar\": \"\",\n" +
            "                        \"correct_rate\": \"0.20683840588372052\",\n" +
            "                        \"updated_at\": \"1517556701\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"id\": \"19\",\n" +
            "                        \"nickname\": \"答学大\",\n" +
            "                        \"avatar\": \"\",\n" +
            "                        \"correct_rate\": \"0.02551969329409273\",\n" +
            "                        \"updated_at\": \"1517556701\"\n" +
            "                    }\n" +
            "                ]\n" +
            "            },\n" +
            "            {\n" +
            "                \"status\": \"进行中\",\n" +
            "                \"status_key\": \"starting\",\n" +
            "                \"total\": 4,\n" +
            "                \"lists\": [\n" +
            "                    {\n" +
            "                        \"id\": \"1\",\n" +
            "                        \"nickname\": \"\",\n" +
            "                        \"avatar\": \"\",\n" +
            "                        \"correct_rate\": \"0.5766365160948006\",\n" +
            "                        \"updated_at\": \"1517553501\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"id\": \"3\",\n" +
            "                        \"nickname\": \"\",\n" +
            "                        \"avatar\": \"\",\n" +
            "                        \"correct_rate\": \"0.3648200392395445\",\n" +
            "                        \"updated_at\": \"1517553501\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"correct_rate\": \"0.17694048134325116\",\n" +
            "                        \"updated_at\": \"1517553501\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"id\": \"2\",\n" +
            "                        \"nickname\": \"\",\n" +
            "                        \"avatar\": \"\",\n" +
            "                        \"correct_rate\": \"0.029828533558015277\",\n" +
            "                        \"updated_at\": \"1517553501\"\n" +
            "                    }\n" +
            "                ]\n" +
            "            },\n" +
            "            {\n" +
            "                \"status\": \"未开始\",\n" +
            "                \"status_key\": \"not_start\",\n" +
            "                \"total\": 4,\n" +
            "                \"lists\": [\n" +
            "                    {\n" +
            "                        \"id\": \"12\",\n" +
            "                        \"nickname\": \"刘老师2\",\n" +
            "                        \"avatar\": \"\",\n" +
            "                        \"correct_rate\": \"0.46\",\n" +
            "                        \"updated_at\": \"1517553301\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"id\": \"10\",\n" +
            "                        \"nickname\": \"刘小勇\",\n" +
            "                        \"avatar\": \"\",\n" +
            "                        \"correct_rate\": \"0.45\",\n" +
            "                        \"updated_at\": \"1517553301\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"id\": \"13\",\n" +
            "                        \"nickname\": \"刘老师3\",\n" +
            "                        \"avatar\": \"\",\n" +
            "                        \"correct_rate\": \"0.31\",\n" +
            "                        \"updated_at\": \"1517553301\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"id\": \"11\",\n" +
            "                        \"nickname\": \"刘学生\",\n" +
            "                        \"avatar\": \"\",\n" +
            "                        \"correct_rate\": \"0.22\",\n" +
            "                        \"updated_at\": \"1517553301\"\n" +
            "                    }\n" +
            "                ]\n" +
            "            }\n" +
            "        ]\n" +
            "    }\n" +
            "}";

    private TextView mUnit;         // 单元名称
    private TextView mEndTime;      // 截止时间
    private TextView mExecution;    // 完成情况
    private TextView mAccuracy;     // 正确率
    private TextView mCompleted;    // 已完成
    private TextView mDoing;        // 进行中
    private TextView mNocompleted;  // 未开始

    private ListView mListviewFinish; // 已完成的Listview
    List<BeHomewrokStudent> listFinish = new ArrayList<BeHomewrokStudent>(); // 已完成的学生列表
    ApHomeworkReport apFinish; // 已完成的学生的adapter

    private ListView mListviewDoing; // 进行中的Listview
    List<BeHomewrokStudent> listDoing = new ArrayList<BeHomewrokStudent>(); // 进行中的学生列表
    ApHomeworkReport apDoing; // 进行中的学生的adapter

    private ListView mListviewNotStart; // 未开始的Listview
    List<BeHomewrokStudent> listNotStart = new ArrayList<BeHomewrokStudent>(); // 未开始的学生列表
    ApHomeworkReport apNotStart; // 未开始的学生的adapter

    private Button mBtnCheck;
    private String homeworkId;

    private TextView tv_null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getView(R.id.toolbar).setBackgroundColor(Color.TRANSPARENT);
        homeworkId = getIntent().getExtras().getString("homeworkId");
        setfxTtitle(getIntent().getExtras().getString("className"), Color.WHITE);
        onBackText(R.drawable.ico_back_white);
        onRightText(R.string.task_homeworkdetails, Color.WHITE);
        showfxDialog();
        httpData(); // 取得作业报告
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_check_homework);
        init();

        apFinish = new ApHomeworkReport(this, listFinish, true);
        mListviewFinish.setAdapter(apFinish);

        apDoing = new ApHomeworkReport(this, listDoing, false);
        mListviewDoing.setAdapter(apDoing);

        apNotStart = new ApHomeworkReport(this, listNotStart, false);
        mListviewNotStart.setAdapter(apNotStart);

    }

    @Override
    public void onRightBtnClick(View view) {
        DjhJumpUtil.getInstance().startBaseActivity(CheckHomeworkActivity.this, CheckHomeworkDetailsActivity.class);
    }

    private View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
//                case R.id.tv_check:
//                    DjhJumpUtil.getInstance().startBaseActivity(CheckHomeworkActivity.this, CheckHomeworkDetailsActivity.class);
//                    break;
                case R.id.tv_completed:
                    mListviewFinish.setVisibility(View.VISIBLE);
                    mCompleted.setTextColor(getResources().getColor(R.color.blue));

                    tv_null.setVisibility(View.GONE);
                    if (listFinish != null && listFinish.size() <= 0) {
                        tv_null.setText(R.string.not_data);
                        mListviewFinish.setEmptyView(tv_null);
                    }

                    mListviewDoing.setVisibility(View.INVISIBLE);
                    mDoing.setTextColor(getResources().getColor(R.color.black_tv_6));

                    mListviewNotStart.setVisibility(View.INVISIBLE);
                    mNocompleted.setTextColor(getResources().getColor(R.color.black_tv_6));

                    break;

                case R.id.tv_doing:
                    mListviewFinish.setVisibility(View.INVISIBLE);
                    mCompleted.setTextColor(getResources().getColor(R.color.black_tv_6));

                    mListviewDoing.setVisibility(View.VISIBLE);
                    mDoing.setTextColor(getResources().getColor(R.color.blue));

                    tv_null.setVisibility(View.GONE);
                    if (listDoing != null && listDoing.size() <= 0) {
                        tv_null.setText(R.string.not_data);
                        mListviewDoing.setEmptyView(tv_null);
                    }

                    mListviewNotStart.setVisibility(View.INVISIBLE);
                    mNocompleted.setTextColor(getResources().getColor(R.color.black_tv_6));

                    break;

                case R.id.tv_nocompleted:
                    mListviewFinish.setVisibility(View.INVISIBLE);
                    mCompleted.setTextColor(getResources().getColor(R.color.black_tv_6));

                    mListviewDoing.setVisibility(View.INVISIBLE);
                    mDoing.setTextColor(getResources().getColor(R.color.black_tv_6));

                    mListviewNotStart.setVisibility(View.VISIBLE);
                    mNocompleted.setTextColor(getResources().getColor(R.color.blue));

                    tv_null.setVisibility(View.GONE);
                    if (listNotStart != null && listNotStart.size() <= 0) {
                        tv_null.setText(R.string.not_data);
                        mListviewNotStart.setEmptyView(tv_null);
                    }

                    break;

                case R.id.btn_check:
                    DjhJumpUtil.getInstance().startBaseActivity(CheckHomeworkActivity.this, CheckHomeworkResultActivity.class);
                    break;

                default:
                    break;
            }
        }
    };

    /*初始化*/
    private void init() {
        mBtnCheck = getView(R.id.btn_check);
        mListviewFinish = getView(R.id.listview_finish);
        mListviewDoing = getView(R.id.listview_doing);
        mListviewNotStart = getView(R.id.listview_not_start);

        mCompleted = getView(R.id.tv_completed);
        mDoing = getView(R.id.tv_doing);
        mNocompleted = getView(R.id.tv_nocompleted);

        tv_null = getView(com.hyphenate.easeui.R.id.tv_null);

        registrationevent();
    }

    /*注册点击事件*/
    private void registrationevent() {
        mBtnCheck.setOnClickListener(onClick);
        mCompleted.setOnClickListener(onClick);
        mDoing.setOnClickListener(onClick);
        mNocompleted.setOnClickListener(onClick);
    }

    @Override
    public void httpData() {
        //网络请求
        RequestUtill.getInstance().httpHomeworkReport(context, callGetClassList, homeworkId); // 取得作业报告

    }

    /**
     * 取得班级列表的callback
     */
    ResultCallback callGetClassList = new ResultCallback() {
        @Override
        public void onError(Request request, Exception e) {
            dismissfxDialog();
        }

        @Override
        public void onResponse(String response) {
            dismissfxDialog();
            HeadJson json = new HeadJson(response);
//            HeadJson json = new HeadJson(testString);
            if (json.getstatus() == 0) {
                /* 解析班级列表信息 */
                HomeworkReport temp = json.parsingObject(HomeworkReport.class);
                if (temp != null) {
                    mUnit = getView(R.id.tv_unit);
                    mUnit.setText(temp.getName()); // 单元名

                    mEndTime = getView(R.id.tv_time);
                    mEndTime.setText("截止至：" + TimeUtil.stampToString(temp.getEnd_time())); // 截止时间

                    mExecution = getView(R.id.tv_execution);
                    mExecution.setText("完成情况：" + temp.getComplete_students() + "/" + temp.getAll_students()); // 完成情况

                    mAccuracy = getView(R.id.tv_accuracy);
                    Float correctRateF = Float.parseFloat(temp.getCorrect_rate()) * 100;
                    DecimalFormat df = new DecimalFormat("#.00");
                    String correctRateStr = df.format(correctRateF);
                    if (correctRateStr.equals(".00")) {
                        correctRateStr = "0.00";
                    }
                    mAccuracy.setText("正确率：" + correctRateStr + "%");

                    if (temp.getStudent_list() != null && temp.getStudent_list().size() > 0) {
                        for (BeHomewrokReportInfo item : temp.getStudent_list()) {
                            if (item.getStatus_key() != null && item.getStatus_key().equals("finish")) {
//                                mCompleted = getView(R.id.tv_completed);
                                mCompleted.setText("已完成：" + item.getTotal());

                                listFinish.clear();
                                listFinish.addAll(item.getLists());
                                apFinish.notifyDataSetChanged();

                                tv_null.setVisibility(View.GONE);
                                if (listFinish.size() <= 0) {
                                    tv_null.setText(R.string.not_data);
                                    mListviewFinish.setEmptyView(tv_null);
                                }

                            } else if (item.getStatus_key() != null && item.getStatus_key().equals("starting")) {
//                                mDoing = getView(R.id.tv_doing);
                                mDoing.setText("进行中：" + item.getTotal());

                                listDoing.clear();
                                listDoing.addAll(item.getLists());
                                apDoing.notifyDataSetChanged();
                            } else if (item.getStatus_key() != null && item.getStatus_key().equals("not_start")) {
//                                mNocompleted = getView(R.id.tv_nocompleted);
                                mNocompleted.setText("未完成：" + item.getTotal());

                                listNotStart.clear();
                                listNotStart.addAll(item.getLists());
                                apNotStart.notifyDataSetChanged();
                            }
                        }
                    }

                }

            } else {
                ToastUtil.showToast(context, json.getMsg());
            }
        }
    };
}