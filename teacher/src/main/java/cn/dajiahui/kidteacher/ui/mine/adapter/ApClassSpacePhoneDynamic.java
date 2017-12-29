package cn.dajiahui.kidteacher.ui.mine.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.fxtx.framework.adapter.CommonAdapter;
import com.fxtx.framework.adapter.ViewHolder;

import java.util.List;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.ui.mine.bean.BeclassSpace;

/**
 * 我的班级动态
 */
public class ApClassSpacePhoneDynamic extends CommonAdapter<BeclassSpace> {

    public ApClassSpacePhoneDynamic(Context context, List<BeclassSpace> mDatas) {
        super(context, mDatas, R.layout.item_classspace_photodynamic);
    }


    @Override
    public void convert(ViewHolder viewHolder, final int position, BeclassSpace item) {

        ImageView img_1 = viewHolder.getView(R.id.img_classphotodynamic);

        img_1.setImageBitmap(item.getBitmap());
    }
}
