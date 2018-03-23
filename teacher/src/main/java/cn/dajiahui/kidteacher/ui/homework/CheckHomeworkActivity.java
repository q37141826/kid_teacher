package cn.dajiahui.kidteacher.ui.homework;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.fxtx.framework.http.callback.ResultCallback;
import com.fxtx.framework.json.HeadJson;
import com.fxtx.framework.log.ToastUtil;
import com.fxtx.framework.time.TimeUtil;
import com.fxtx.framework.ui.FxActivity;
import com.fxtx.framework.widgets.StatusBarCompat;
import com.squareup.okhttp.Request;

import java.io.Serializable;
import java.math.RoundingMode;
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
import cn.dajiahui.kidteacher.util.Logger;

/**
 * 检查作业
 */
public class CheckHomeworkActivity extends FxActivity {

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
        StatusBarCompat.compat(this, getResources().getColor(com.fxtx.framework.R.color.app_bg_b));
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_check_homework);
        init();
        /*已完成*/
        apFinish = new ApHomeworkReport(this, listFinish, true);
        mListviewFinish.setAdapter(apFinish);
       /*进行中*/
        apDoing = new ApHomeworkReport(this, listDoing, false);
        mListviewDoing.setAdapter(apDoing);
        /*未开始*/
        apNotStart = new ApHomeworkReport(this, listNotStart, false);
        mListviewNotStart.setAdapter(apNotStart);

        mListviewFinish.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                ToastUtil.showToast(context, "跳转到个人作业详情页面");
//                String classId = listFinish.get(position).getId();
//                Bundle bundle = new Bundle();
//                bundle.putString("classId", classId);
//                DjhJumpUtil.getInstance().startBaseActivity(MyClassActivity.this, ClassInfoActivity.class, bundle, 0); // 跳转到个人作业详情页面
            }
        });
    }

    @Override
    public void onRightBtnClick(View view) {
        Bundle bundle = new Bundle();
        bundle.putString("homeworkId", homeworkId);
        DjhJumpUtil.getInstance().startBaseActivity(CheckHomeworkActivity.this, CheckHomeworkDetailsActivity.class, bundle, 0);  // 作业详情
    }

    private View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_completed:

                    /* 已完成 */
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
                    /* 进行中 */
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
                    /* 未开始 */
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
                    mBtnCheck.setText("已检查");
                    /* 检查作业 */
                    Bundle checkBundle = new Bundle();
                    checkBundle.putString("homeworkId", homeworkId);
                    checkBundle.putSerializable("listFinish", (Serializable) listFinish);
                    checkBundle.putSerializable("listDoing", (Serializable) listDoing);
                    checkBundle.putSerializable("listNotStart", (Serializable) listNotStart);

                    DjhJumpUtil.getInstance().startBaseActivityForResult(CheckHomeworkActivity.this, CheckHomeworkResultActivity.class, checkBundle, DjhJumpUtil.getInstance().activtiy_ChoiceHomeworkResult);

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

        tv_null = getView(R.id.tv_null);

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

        @SuppressLint("ResourceType")
        @Override
        public void onResponse(String response) {
            Logger.d("response:" + response);
            dismissfxDialog();
            HeadJson json = new HeadJson(response);

            if (json.getstatus() == 0) {
                /* 解析列表信息 */
                HomeworkReport temp = json.parsingObject(HomeworkReport.class);
                if (temp != null) {
                    mUnit = getView(R.id.tv_unit);
                    mUnit.setText(temp.getName()); // 单元名

                    mEndTime = getView(R.id.tv_time);
                    mEndTime.setText("截止至：" + TimeUtil.stampToString(temp.getEnd_time())); // 截止时间

                    mExecution = getView(R.id.tv_execution);
                    mExecution.setText("完成情况：" + temp.getComplete_students() + "/" + temp.getAll_students()); // 完成情况

                    mAccuracy = getView(R.id.tv_accuracy);
                    Float correctRateF = Float.parseFloat(temp.getCorrect_rate());
                    DecimalFormat df = new DecimalFormat("0.00%");
                    df.setRoundingMode(RoundingMode.HALF_UP); // 四舍五入
                    String correctRateStr = df.format(correctRateF);
                    mAccuracy.setText("正确率：" + correctRateStr);

                    if (temp.getStudent_list() != null && temp.getStudent_list().size() > 0) {
                        for (BeHomewrokReportInfo item : temp.getStudent_list()) {
                            if (item.getStatus_key() != null && item.getStatus_key().equals("finish")) {
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
                                mDoing.setText("进行中：" + item.getTotal());

                                listDoing.clear();
                                listDoing.addAll(item.getLists());
                                apDoing.notifyDataSetChanged();
                            } else if (item.getStatus_key() != null && item.getStatus_key().equals("not_start")) {
                                mNocompleted.setText("未开始：" + item.getTotal());
                                listNotStart.clear();
                                listNotStart.addAll(item.getLists());
                                apNotStart.notifyDataSetChanged();
                            }
                        }
                    }

                    if (temp.getIs_checked().equals("0")) {
                        mBtnCheck.setText("检查作业");
                    } else {
                        mBtnCheck.setText("已检查");
                    }

                }

            } else {
                ToastUtil.showToast(context, json.getMsg());
            }
        }
    };



    /*监听返回键*/
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) { //监控/拦截/屏蔽返回键
            setResult(RESULT_OK);
            finishActivity();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        finishActivity();
    }

    @Override
    protected void onDestroy() {
        StatusBarCompat.compat(this, getResources().getColor(com.fxtx.framework.R.color.app_bg));
        super.onDestroy();
    }
}