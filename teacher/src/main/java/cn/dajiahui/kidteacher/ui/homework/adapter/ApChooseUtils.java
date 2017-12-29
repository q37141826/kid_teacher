package cn.dajiahui.kidteacher.ui.homework.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.fxtx.framework.adapter.CommonAdapter;
import com.fxtx.framework.adapter.ViewHolder;

import java.util.List;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.ui.homework.bean.ChooseUtils;

/**
 * 发布课本作业的Utils适配器
 */
public class ApChooseUtils extends CommonAdapter<ChooseUtils> {

    int selectorPosition = -1;

    public ApChooseUtils(Context context, List<ChooseUtils> mDatas) {
        super(context, mDatas, R.layout.item_chooseutils);
    }

    @Override
    public void convert(ViewHolder viewHolder, int position, ChooseUtils item) {
        TextView tvUtilsname = viewHolder.getView(R.id.tv_utilName);
        TextView tvUtilscontent = viewHolder.getView(R.id.tv_utilcomtent);
        ImageView isSureImg = viewHolder.getView(R.id.id_item_select);

        tvUtilsname.setText(item.getUtlisname());
        tvUtilscontent.setText(item.getComnent());

        //如果当前的position等于传过来点击的position,就去改变他的状态
        if (selectorPosition == position) {
            isSureImg.setImageResource(R.drawable.ico_im_ok);
        } else {
            //其他的恢复原来的状态
            isSureImg.setImageResource(R.drawable.ico_im_not);
        }
    }

    public void changeState(int pos) {
        selectorPosition = pos;
        notifyDataSetChanged();
    }
}
