package cn.dajiahui.kidteacher.ui.homework;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fxtx.framework.ui.FxActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.ui.homework.adapter.ApChooseClass;
import cn.dajiahui.kidteacher.ui.homework.bean.ChooseClass;
import cn.dajiahui.kidteacher.ui.homework.view.CustomDatePicker;

/**
 * 选择班级
 */
public class ChooseClassActivity extends FxActivity {

    private ListView mListview;
    private TextView mChoosetime;
    CustomDatePicker customDatePicker;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_choose_class);

        TextView mTitle = getView(R.id.tool_title);
        TextView mLeft = getView(R.id.tool_left);
        TextView mCleartime = getView(R.id.tv_cleartime);
        mChoosetime = getView(R.id.tv_choosetime);
        Button mConfirm = getView(R.id.btn_confirm);
        mListview = getView(R.id.listview);
        mTitle.setText(R.string.task_textbook_assignment);
        mLeft.setText(R.string.sign_out);

        mLeft.setOnClickListener(onClick);
        mConfirm.setOnClickListener(onClick);
        mCleartime.setOnClickListener(onClick);
        /*时间选择器*/
        initDatePicker();

        List<ChooseClass> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(new ChooseClass("二年" + i + "班"));
        }

        final ApChooseClass apchooseClass = new ApChooseClass(this, list);
        mListview.setAdapter(apchooseClass);
        mChoosetime.setOnClickListener(onClick);
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //把点击的position传递到adapter里面去
                apchooseClass.changeState(position);
            }
        });
    }

    private View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.tool_left:
                    Toast.makeText(ChooseClassActivity.this, "退出", Toast.LENGTH_SHORT).show();
                    finishActivity();

                    break;
                case R.id.btn_confirm:
                    Toast.makeText(ChooseClassActivity.this, "确认布置", Toast.LENGTH_SHORT).show();
                    finishActivity();
                    break;
                case R.id.tv_choosetime:
                    Toast.makeText(ChooseClassActivity.this, "时间选择器", Toast.LENGTH_SHORT).show();
                    // 日期格式为yyyy-MM-dd HH:mm
                    customDatePicker.show(mChoosetime.getText().toString());

                    break;
                case R.id.tv_cleartime:
                    Toast.makeText(ChooseClassActivity.this, "清空", Toast.LENGTH_SHORT).show();
                    initDatePicker();

                    break;
                default:
                    break;
            }
        }
    };

    private void initDatePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());

        mChoosetime.setText(now);

        customDatePicker = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                mChoosetime.setText(time);
            }
        }, "2010-01-01 00:00", now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker.showSpecificTime(true); // 显示时和分
        customDatePicker.setIsLoop(true); // 允许循环滚动
    }

}
