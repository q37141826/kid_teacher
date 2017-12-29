package cn.dajiahui.kidteacher.ui.album.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.fxtx.framework.adapter.CommonAdapter;
import com.fxtx.framework.adapter.ViewHolder;
import com.fxtx.framework.image.util.GlideUtil;

import java.util.List;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.ui.album.bean.BePhoto;

/**
 * Created by z on 2016/3/10.
 */
public class ApPhoto extends CommonAdapter<BePhoto> {

    private int isMyClass;

    public ApPhoto(Context context, List<BePhoto> mDatas,int isMyClass) {
        super(context, mDatas, R.layout.album_photo_item);
        this.isMyClass = isMyClass;
    }

    @Override
    public void convert(ViewHolder viewHolder, int position, BePhoto item) {
        ImageView view = viewHolder.getConvertView();
        if (position == 0 && 1 == isMyClass) {
            view.setScaleType(ImageView.ScaleType.FIT_XY);
            view.setImageResource(R.drawable.ico_c_album);
        } else {
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            GlideUtil.showNoneImage(mContext, item.getThumbUrl(), view, R.drawable.ico_default, false);
        }
    }
}
