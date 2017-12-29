package cn.dajiahui.kidteacher.ui.homework;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.fxtx.framework.ui.FxActivity;

import cn.dajiahui.kidteacher.R;

/**
 * 检查作业结果
 */
public class CheckHomeworkResultActivity extends FxActivity {
    private TextView mTitle;
    private TextView mLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_check_homework_result);
        mTitle = getView(R.id.tool_title);
        mLeft = getView(R.id.tool_left);
        mLeft.setOnClickListener(onClick);

        //        task_checkhomeworkresult

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
