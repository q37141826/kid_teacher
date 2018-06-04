package cn.dajiahui.kidteacher.ui.homework.sendhomework.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.TextView;

import com.fxtx.framework.adapter.CommonAdapter;
import com.fxtx.framework.adapter.ViewHolder;

import java.util.List;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.ui.homework.sendhomework.bean.BeHomeworkPreView;


/**
 * 留作业详情
 */
public class ApHomeWorkPreview extends CommonAdapter<BeHomeworkPreView> {

    public ApHomeWorkPreview(Context context, List<BeHomeworkPreView> mDatas) {
        super(context, mDatas, R.layout.item_answercard);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void convert(ViewHolder viewHolder, int position, BeHomeworkPreView item) {
        TextView tv_circle = viewHolder.getView(R.id.tv_circle);
        tv_circle.setText((position + 1) + "");

        tv_circle.setBackgroundResource(R.drawable.homewor_true_bg_green);
    }
}