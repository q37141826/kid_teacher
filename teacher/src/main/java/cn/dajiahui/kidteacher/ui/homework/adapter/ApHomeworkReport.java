package cn.dajiahui.kidteacher.ui.homework.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.fxtx.framework.adapter.CommonAdapter;
import com.fxtx.framework.adapter.ViewHolder;

import java.util.List;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.ui.homework.bean.HomeworkReport;
import cn.dajiahui.kidteacher.ui.homework.view.CricleTextView;

/**
 * 发布课本作业的Utils适配器
 */
public class ApHomeworkReport extends CommonAdapter<HomeworkReport> {


    private CricleTextView number;

    public ApHomeworkReport(Context context, List<HomeworkReport> mDatas) {
        super(context, mDatas, R.layout.item_homeworkreport);
    }

    @Override
    public void convert(ViewHolder viewHolder, int position, HomeworkReport item) {
        cn.dajiahui.kidteacher.ui.homework.view.CricleTextView number = viewHolder.getView(R.id.number);
        ImageView imgpic = viewHolder.getView(R.id.img_pic);
        TextView tv_studentname = viewHolder.getView(R.id.tv_studentname);
        TextView tv_submittime = viewHolder.getView(R.id.tv_submittime);
        TextView tv_accuracy = viewHolder.getView(R.id.tv_accuracy);

        tv_studentname.setText(item.getStudentname());
        tv_submittime.setText(item.getSubmittime());
        tv_accuracy.setText(item.getCorrectrate());
    }


}
