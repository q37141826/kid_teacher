package cn.dajiahui.kidteacher.ui.homework.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.fxtx.framework.adapter.CommonAdapter;
import com.fxtx.framework.adapter.ViewHolder;

import java.util.List;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.ui.homework.bean.ChooseSupplementary;


/**
 * 选择教辅适配器
 */
public class ApChooseSupplementary extends CommonAdapter<ChooseSupplementary> {

    private ImageView mImgSupplementary;
    private TextView mTvSupplementary;
    private ImageView id_item_select;
    private int selectorPosition = -1;


    public ApChooseSupplementary(Context context, List<ChooseSupplementary> mDatas) {
        super(context, mDatas, R.layout.item_choosesupplementary);
    }

    @Override
    public void convert(ViewHolder viewHolder, int position, ChooseSupplementary item) {

        mImgSupplementary = viewHolder.getView(R.id.img_supplementary);
        mTvSupplementary = viewHolder.getView(R.id.tv_supplementary);
        id_item_select = viewHolder.getView(R.id.id_item_select);

        mImgSupplementary.setImageResource(R.drawable.ic_launcher);
        mTvSupplementary.setText(item.getBookname());
        //如果当前的position等于传过来点击的position,就去改变他的状态
        if (selectorPosition == position) {
            id_item_select.setImageResource(R.drawable.ico_im_ok);
        } else {
            //其他的恢复原来的状态
            id_item_select.setImageResource(R.drawable.ico_im_not);
        }
    }

    public void changeState(int pos) {
        selectorPosition = pos;
        notifyDataSetChanged();
    }

}
