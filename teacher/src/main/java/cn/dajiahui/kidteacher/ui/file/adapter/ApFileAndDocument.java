package cn.dajiahui.kidteacher.ui.file.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.TextView;

import com.fxtx.framework.adapter.CommonAdapter;
import com.fxtx.framework.adapter.ViewHolder;
import com.fxtx.framework.file.FileUtil;
import com.fxtx.framework.log.ToastUtil;
import com.fxtx.framework.text.StringUtil;
import com.fxtx.framework.time.TimeUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.ui.file.FileFrActivity;
import cn.dajiahui.kidteacher.util.TeacherUtil;

/**
 * Created by z on 2016/3/31.
 */
public class ApFileAndDocument extends CommonAdapter<File> {
    public ArrayList<File> selectFile;
    private FileUtil fileUtil;
    private FileFrActivity activity;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    };

    public ApFileAndDocument(Activity context, ArrayList<File> mDatas, ArrayList<File> selectFile) {
        super(context, mDatas, R.layout.te_item_file);
        this.activity = (FileFrActivity) context;
        fileUtil = new FileUtil();
        if (selectFile == null)
            this.selectFile = new ArrayList<File>();
        else
            this.selectFile = selectFile;
    }

    private boolean isSelectr(File item) {
        return selectFile.contains(item);
    }

    public void addFile(File file,TextView tvName) {
        if (isSelectr(file)) {
            selectFile.remove(file);
            tvName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ico_im_not, 0);
            activity.numFiles--;
        } else {
            if (activity.numFiles == FileFrActivity.maxFiles) {
                ToastUtil.showToast(mContext, mContext.getString(com.fxtx.framework.R.string.photos_max, FileFrActivity.maxFiles));
            } else {
                selectFile.add(file);
                activity.numFiles++;
                tvName.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ico_select_ok,0);
            }
        }

    }


    @Override
    public void convert(ViewHolder viewHolder, int position, final File item) {
        TextView tvName = viewHolder.getView(R.id.tvName);
        TextView tvTime = viewHolder.getView(R.id.tvTime);
        final ImageView im = viewHolder.getView(R.id.im_user);
        im.setTag(position);
        tvName.setText(item.getName());
        if (isSelectr(item)) {
            tvName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ico_select_ok, 0);
        } else {
            tvName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ico_im_not, 0);
        }
        if (StringUtil.isFileType(item.getAbsolutePath(), StringUtil.videoType)) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Bitmap bitmap = getVideoThumbnail(item, 50, 50, MediaStore.Images.Thumbnails.MICRO_KIND);
                    if (bitmap != null)
                        im.setImageBitmap(bitmap);
                    else
                        im.setImageResource(TeacherUtil.setFileType(item.getName()));
                }
            });
        } else
            im.setImageResource(TeacherUtil.setFileType(item.getName()));
        tvTime.setText(TimeUtil.timeFormat(item.lastModified() + "", TimeUtil.yyMD) + " " + fileUtil.FormetFileSize(item.length()));
    }

    /**
     * 获取视频的缩略图
     * 先通过ThumbnailUtils来创建一个视频的缩略图，然后再利用ThumbnailUtils来生成指定大小的缩略图。
     * 如果想要的缩略图的宽和高都小于MICRO_KIND，则类型要使用MICRO_KIND作为kind的值，这样会节省内存。
     *
     * @param file   视频wenjian
     * @param width  指定输出视频缩略图的宽度
     * @param height 指定输出视频缩略图的高度度
     * @param kind   参照MediaStore.Images.Thumbnails类中的常量MINI_KIND和MICRO_KIND。
     *               其中，MINI_KIND: 512 x 384，MICRO_KIND: 96 x 96
     * @return 指定大小的视频缩略图
     */
    private Bitmap getVideoThumbnail(File file, int width, int height,
                                     int kind) {
        Bitmap bitmap = null;
        // 获取视频的缩略图
        bitmap = getBitmap(file.getAbsolutePath());
        if (bitmap != null) return bitmap;
        try {
            bitmap = ThumbnailUtils.createVideoThumbnail(file.getAbsolutePath(), kind);
            bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                    ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
            saveBitmap(bitmap, file);
            return bitmap;
        } catch (Exception e) {
            return null;
        }
    }

    public void saveBitmap(Bitmap bm, File file) {
        File f = new File(file.getAbsolutePath(), file.getName().substring(0, file.getName().lastIndexOf(".")));
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public Bitmap getBitmap(String path) {
        Bitmap bitmap = null;
        bitmap = BitmapFactory.decodeFile(path);
        return bitmap;
    }
}
