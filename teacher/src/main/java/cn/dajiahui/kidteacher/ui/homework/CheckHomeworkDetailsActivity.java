package cn.dajiahui.kidteacher.ui.homework;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.fxtx.framework.ui.FxActivity;

import java.util.ArrayList;
import java.util.List;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.ui.homework.adapter.ApCheckHomeworkDetails;
import cn.dajiahui.kidteacher.ui.homework.bean.CheckHomeworkDetails;

/**
 * 检查作业详情
 */
public class CheckHomeworkDetailsActivity extends FxActivity {

    private TextView mTitle;
    private TextView mLeft;
    private ListView mListview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_check_homework_details);
        mTitle = getView(R.id.tool_title);
        mLeft = getView(R.id.tool_left);
        mTitle.setText(R.string.task_homeworkdetails);

        mLeft.setText(R.string.sign_out);
        mLeft.setOnClickListener(onClick);
        mListview = getView(R.id.listview);

        List<CheckHomeworkDetails> list = new ArrayList<>();
        for (int i = 1; i < 20; i++) {
            list.add(new CheckHomeworkDetails(i + "", i + "%", i + "/20"));
        }
        ApCheckHomeworkDetails ApCheckHomeworkDetails = new ApCheckHomeworkDetails(this, list);

        mListview.setAdapter(ApCheckHomeworkDetails);


    }


    private View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.tool_left:
                    finishActivity();

                    break;

                default:
                    break;
            }
        }
    };
}
