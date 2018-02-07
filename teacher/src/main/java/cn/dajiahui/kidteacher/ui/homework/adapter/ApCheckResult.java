package cn.dajiahui.kidteacher.ui.homework.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fxtx.framework.adapter.CommonAdapter;
import com.fxtx.framework.adapter.ViewHolder;
import com.fxtx.framework.image.util.GlideUtil;
import com.fxtx.framework.time.TimeUtil;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.ui.homework.bean.BeHomeworkStatus;
import cn.dajiahui.kidteacher.ui.homework.bean.BeHomewrokStudent;

/**
 * 检查作业结果
 * Created by wangzhi on 2018/2/7.
 */

public class ApCheckResult extends CommonAdapter<List<BeHomewrokStudent>> {

    public ApCheckResult(Context context, List<List<BeHomewrokStudent>> mDatas) {
        super(context, mDatas, R.layout.check_homework_result_item);
    }

    @Override
    public void convert(ViewHolder viewHolder, int position, List<BeHomewrokStudent> item) {
        TextView title = viewHolder.getView(R.id.title);
        TextView content = viewHolder.getView(R.id.content);
        if (position == 0) {
            /* 已完成学生 */
            title.setText("已经完成：" + item.size() + "个");
//            StringBuilder stringBuilder = new StringBuilder();
//            for (BeHomewrokStudent studentItem:item) {
//                stringBuilder.append(studentItem.getNickname());
//                stringBuilder.append("  ");
//            }
//            content.setText(stringBuilder.toString());
        } else {
            /* 未完成学生 */
            title.setText("未完成：" + item.size() + "个");
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (BeHomewrokStudent studentItem:item) {
            stringBuilder.append(studentItem.getNickname());
            stringBuilder.append("  ");
        }
        content.setText(stringBuilder.toString());

//        TextView tv_number = viewHolder.getView(R.id.number);
//        ImageView userAvtar = viewHolder.getView(R.id.img_pic);
//        TextView tv_studentname = viewHolder.getView(R.id.tv_studentname);
//        TextView tv_submittime = viewHolder.getView(R.id.tv_submittime);
//        TextView tv_accuracy = viewHolder.getView(R.id.tv_accuracy);

//        tv_studentname.setText(item.getNickname());  // 名字
//
//        if (item.getAvatar() != null && !item.getAvatar().equals("")) {
//            GlideUtil.showRoundImage(mContext, item.getAvatar(), userAvtar, R.drawable.ico_default_user, true); // 头像
//        }

//        if(isShowAccuracy) {
//            tv_submittime.setText(TimeUtil.stampToString(item.getUpdated_at())); // 截止时间
//            tv_submittime.setVisibility(View.VISIBLE);
//
//            Float correctRateF = Float.parseFloat(item.getCorrect_rate());
//            DecimalFormat df = new DecimalFormat("0.00%");
//            df.setRoundingMode(RoundingMode.HALF_UP); // 四舍五入
//            String correctRateStr = df.format(correctRateF);
//            tv_accuracy.setText("正确率：" + correctRateStr + " ");   // 正确率
//            tv_accuracy.setVisibility(View.VISIBLE);
//        }

//        tv_number.setText(String.valueOf(position + 1));
    }
}
