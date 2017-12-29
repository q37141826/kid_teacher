package cn.dajiahui.kidteacher.ui.file.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;

import com.fxtx.framework.adapter.CommonAdapter;
import com.fxtx.framework.adapter.ViewHolder;
import com.fxtx.framework.image.util.GlideUtil;
import com.fxtx.framework.log.ToastUtil;

import java.io.File;
import java.util.ArrayList;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.ui.file.FileFrActivity;

/**
 * Created by wdj on 2016/6/16.
 */
public class ApImage extends CommonAdapter<File> {
    public ArrayList<File> mSelected;
    private FileFrActivity activity;
    public ApImage(Activity context, ArrayList<File> mDatas, ArrayList<File> mSelected) {
        super(context, mDatas, R.layout.grid_item);
        this.activity = (FileFrActivity) context;
        if (mSelected == null) this.mSelected = new ArrayList<File>();
        else this.mSelected = mSelected;
    }

    @Override
    public void convert(ViewHolder viewHolder, int position, File item) {
        ImageView img = viewHolder.getView(R.id.id_item_image);
        ImageView imb = viewHolder.getView(R.id.id_item_select);
        imb.setBackgroundResource(R.drawable.ico_im_not);
        GlideUtil.showImageFile(mContext, item.getAbsolutePath(), img, com.fxtx.framework.R.drawable.pictures_no);
        img.setColorFilter(null);
        img.setOnClickListener(new MyOnClickListener(img,imb,position));
        if (mSelected.contains(item)) {
            imb.setImageResource(com.fxtx.framework.R.drawable.ico_im_ok);
            img.setColorFilter(Color.parseColor("#77000000"));
        } else {
            imb.setImageResource(com.fxtx.framework.R.drawable.ico_im_not);
            img.setColorFilter(Color.parseColor("#00000000"));
        }
    }
    class MyOnClickListener implements View.OnClickListener {
      ImageView img,imb;
        int position;

        MyOnClickListener(ImageView img,ImageView imb, int position) {
            this.img = img;
            this.imb= imb;
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            // 已经选择过该图片
            if (mSelected.contains(mDatas.get(position))) {
                mSelected.remove( mDatas.get(position ));
                imb.setImageResource(com.fxtx.framework.R.drawable.ico_im_not);
               img.setColorFilter(null);
                activity.numFiles--;
            } else {// 未选择该图片
                if (activity.numFiles>=FileFrActivity.maxFiles) {
                    ToastUtil.showToast(mContext, mContext.getString(com.fxtx.framework.R.string.photos_max, FileFrActivity.maxFiles));
                    return;
                }
                mSelected.add(mDatas.get(position));
                activity.numFiles++;
               imb.setImageResource(com.fxtx.framework.R.drawable.ico_im_ok);
                img.setColorFilter(Color.parseColor("#77000000"));
            }
            onPhotoSelectedListener.photoClick(mSelected);
        }

    }

    public OnPhotoSelectedListener onPhotoSelectedListener;

    public void setOnPhotoSelectedListener(OnPhotoSelectedListener onPhotoSelectedListener) {
        this.onPhotoSelectedListener = onPhotoSelectedListener;
    }

    public interface OnPhotoSelectedListener {
        void photoClick(ArrayList<File> number);
    }
}
