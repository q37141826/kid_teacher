package cn.dajiahui.kidteacher.ui.homework.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fxtx.framework.adapter.CommonAdapter;
import com.fxtx.framework.adapter.ViewHolder;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.ui.homework.bean.BeWorkBook;


/**
 * 选择教辅适配器
 */
public class ApChooseSupplementary extends CommonAdapter<BeWorkBook> {

    private ImageView mImgSupplementary;
    private TextView mTvSupplementary;
//    private ImageView id_item_select;
    private RelativeLayout mask;
    private int selectorPosition = -1;
    private BeWorkBook selectBook;

    public ApChooseSupplementary(Context context, List<BeWorkBook> mDatas, BeWorkBook selectBook) {
        super(context, mDatas, R.layout.item_choosesupplementary);
        this.selectBook = selectBook;
    }

    @Override
    public void convert(ViewHolder viewHolder, int position, BeWorkBook item) {

        mImgSupplementary = viewHolder.getView(R.id.img_supplementary);
        mTvSupplementary = viewHolder.getView(R.id.tv_supplementary);
        mask = viewHolder.getView(R.id.img_mask);
//        id_item_select = viewHolder.getView(R.id.id_item_select);

        if (item.getLogo() != null && !item.getLogo().equals("")) {
            Glide.with(mContext).load(item.getLogo()).into(mImgSupplementary);
        } else {
            mImgSupplementary.setImageResource(R.drawable.ic_launcher);
        }
        mTvSupplementary.setText(item.getName());

        //如果当前的position等于传过来点击的position,就去改变他的状态
        if (selectBook != null && selectBook.getId().equals(item.getId())) {
            mask.setVisibility(View.VISIBLE);
//            id_item_select.setImageResource(R.drawable.ico_im_ok);
        } else {
            //其他的恢复原来的状态
            mask.setVisibility(View.INVISIBLE);
//            id_item_select.setImageResource(R.drawable.ico_im_not);
        }
    }

    public void changeState(BeWorkBook selectBook) {
        this.selectBook = selectBook;
        notifyDataSetChanged();
    }

}
