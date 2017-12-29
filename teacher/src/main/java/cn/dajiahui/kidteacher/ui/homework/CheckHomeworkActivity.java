package cn.dajiahui.kidteacher.ui.homework;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.fxtx.framework.ui.FxActivity;

import java.util.ArrayList;
import java.util.List;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.ui.homework.adapter.ApHomeworkReport;
import cn.dajiahui.kidteacher.ui.homework.bean.HomeworkReport;
import cn.dajiahui.kidteacher.util.DjhJumpUtil;

/**
 * 检查作业
 */
public class CheckHomeworkActivity extends FxActivity {


    private TextView mTitle;
    private TextView mLeft;
    private TextView mUnit;
    private TextView mTime;
    private TextView mCheck;
    private TextView mExecution;
    private TextView mAccuracy;
    private TextView mCompleted;
    private TextView mGoing;
    private TextView mNocompleted;
    private ListView mListview;
    private Button mBtnCheck;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_check_homework);
        //从Intent 中获取数据
        Bundle myBundelForGetName = this.getIntent().getExtras();
        init();

        mTitle.setText(myBundelForGetName.getString("unit"));
        mLeft.setText("返回");


        List<HomeworkReport> list = new ArrayList();
        for (int i = 0; i < 20; i++) {
            list.add(new HomeworkReport("李四" + i, "201" + i, "正确" + i));

        }
        ApHomeworkReport apHomeworkReport = new ApHomeworkReport(this, list);

        mListview.setAdapter(apHomeworkReport);

    }


    private View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.tool_left:
                    finishActivity();

                    break;
                case R.id.tv_unit:


                    break;
                case R.id.tv_time:

                    break;
                case R.id.tv_check:
                    DjhJumpUtil.getInstance().startBaseActivity(CheckHomeworkActivity.this, CheckHomeworkDetailsActivity.class);
                    break;
                case R.id.tv_execution:

                    break;
                case R.id.tv_accuracy:

                    break;
                case R.id.tv_completed:

                    break;
                case R.id.btn_confirm:

                    break;
                case R.id.tv_choosetime:

                    break;
                case R.id.tv_cleartime:


                    break;
                case R.id.tv_going:


                    break;
                case R.id.tv_nocompleted:


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
        mTitle = getView(R.id.tool_title);
        mLeft = getView(R.id.tool_left);
        mUnit = getView(R.id.tv_unit);
        mTime = getView(R.id.tv_time);
        mCheck = getView(R.id.tv_check);
        mExecution = getView(R.id.tv_execution);
        mAccuracy = getView(R.id.tv_accuracy);
        mCompleted = getView(R.id.tv_completed);
        mGoing = getView(R.id.tv_going);
        mNocompleted = getView(R.id.tv_nocompleted);
        mBtnCheck = getView(R.id.btn_check);
        mListview = getView(R.id.listview);
        registrationevent();
    }

    /*注册点击事件*/
    private void registrationevent() {
        mLeft.setOnClickListener(onClick);
        mUnit.setOnClickListener(onClick);
        mTime.setOnClickListener(onClick);
        mCheck.setOnClickListener(onClick);
        mExecution.setOnClickListener(onClick);
        mAccuracy.setOnClickListener(onClick);
        mCompleted.setOnClickListener(onClick);
        mGoing.setOnClickListener(onClick);
        mNocompleted.setOnClickListener(onClick);
        mBtnCheck.setOnClickListener(onClick);
    }
}