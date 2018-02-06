package cn.dajiahui.kidteacher.ui.homework.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.fxtx.framework.adapter.CommonAdapter;
import com.fxtx.framework.adapter.ViewHolder;

import java.util.List;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.ui.homework.bean.BeUnit;

/**
 * 发布课本作业的Unit适配器
 */
public class ApChooseUnit extends CommonAdapter<BeUnit> {

    private int selectorPosition = -1;
    private Context context;

    public ApChooseUnit(Context context, List<BeUnit> mDatas) {
        super(context, mDatas, R.layout.item_chooseutils);
        this.context = context;
    }

    @Override
    public void convert(ViewHolder viewHolder, int position, BeUnit item) {
        TextView tvUtilsname = viewHolder.getView(R.id.tv_utilName);
        ImageView isSureImg = viewHolder.getView(R.id.id_item_select);

        tvUtilsname.setText(item.getName());
        //如果当前的position等于传过来点击的position,就去改变他的状态
        if (selectorPosition == position) {
            isSureImg.setImageResource(R.drawable.ico_im_ok);
            tvUtilsname.setTextColor(context.getResources().getColor(R.color.blue_dark));
        } else {
            //其他的恢复原来的状态
            isSureImg.setImageResource(R.drawable.ico_im_not);
            tvUtilsname.setTextColor(context.getResources().getColor(R.color.black_tv_6));
        }
    }

    public void changeState(int pos) {
        selectorPosition = pos;
        notifyDataSetChanged();
    }
}
