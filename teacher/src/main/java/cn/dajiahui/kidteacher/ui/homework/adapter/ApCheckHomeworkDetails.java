package cn.dajiahui.kidteacher.ui.homework.adapter;

import android.content.Context;
import android.widget.TextView;

import com.fxtx.framework.adapter.CommonAdapter;
import com.fxtx.framework.adapter.ViewHolder;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.ui.homework.bean.BeCheckHomeworkQuestionDetails;
import cn.dajiahui.kidteacher.ui.homework.bean.CheckHomeworkDetails;


/**
 * 检查作业详情
 */
public class ApCheckHomeworkDetails extends CommonAdapter<BeCheckHomeworkQuestionDetails> {


    private TextView tv_subject;
    private TextView tv_accuracy;
    private TextView tv_complete;


    public ApCheckHomeworkDetails(Context context, List<BeCheckHomeworkQuestionDetails> mDatas) {
        super(context, mDatas, R.layout.item_checkhomeworkdatails);
    }


    @Override
    public void convert(ViewHolder viewHolder, int position, BeCheckHomeworkQuestionDetails item) {
        TextView tv_number = viewHolder.getView(R.id.number);
        tv_number.setText(String.valueOf(position + 1));

        TextView tv_accuracy = viewHolder.getView(R.id.accuracy); // 正确率
        Float correctRateF = Float.parseFloat(item.getCorrectRate());
        DecimalFormat df = new DecimalFormat("0.00%");
        df.setRoundingMode(RoundingMode.HALF_UP); // 四舍五入
        String correctRateStr = df.format(correctRateF);
        tv_accuracy.setText(correctRateStr + " ");   // 正确率

        TextView tv_complete = viewHolder.getView(R.id.complete); // 完成情况
        tv_complete.setText(item.getCompleteCnt() + "/" + item.getTotalUsers());

    }


}
