package cn.dajiahui.kidteacher.ui.homework.adapter;

import android.content.Context;
import android.widget.TextView;

import com.fxtx.framework.adapter.CommonAdapter;
import com.fxtx.framework.adapter.ViewHolder;

import java.util.List;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.ui.homework.bean.CheckHomeworkDetails;


/**
 * 检查作业详情
 */
public class ApCheckHomeworkDetails extends CommonAdapter<CheckHomeworkDetails> {


    private TextView tv_subject;
    private TextView tv_accuracy;
    private TextView tv_complete;


    public ApCheckHomeworkDetails(Context context, List<CheckHomeworkDetails> mDatas) {
        super(context, mDatas, R.layout.item_checkhomeworkdatails);
    }


    @Override
    public void convert(ViewHolder viewHolder, int position, CheckHomeworkDetails item) {

        tv_subject = viewHolder.getView(R.id.subject);
        tv_accuracy = viewHolder.getView(R.id.accuracy);
        tv_complete = viewHolder.getView(R.id.complete);

        tv_subject.setText(item.getSubject());
        tv_accuracy.setText(item.getAccuracy());
        tv_complete.setText(item.getComplete());

    }


}
