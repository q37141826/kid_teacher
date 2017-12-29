package cn.dajiahui.kidteacher.ui.album.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.fxtx.framework.adapter.CommonAdapter;
import com.fxtx.framework.adapter.ViewHolder;
import com.fxtx.framework.image.util.GlideUtil;
import com.fxtx.framework.text.StringUtil;

import java.util.List;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.ui.album.bean.BeAlbum;

/**
 * Created by z on 2016/3/11.
 * 带标题的 相册展示
 */
public class ApAlbumTitle extends CommonAdapter<BeAlbum> {
    private int index;
    private int isMyClass;

    public ApAlbumTitle(Context context, List<BeAlbum> mDatas, int index, int isMyClass) {
        super(context, mDatas, R.layout.album_item_title);
        this.index = index;
        this.isMyClass = isMyClass;
        if (mDatas != null)
            if (isMyClass == 1 && !StringUtil.isEmpty(mDatas.get(0).getName())) {
                mDatas.add(0, new BeAlbum());
            }
    }

    @Override
    public long getItemId(int position) {
        return index;
    }

    @Override
    public BeAlbum getItem(int position) {
        return super.getItem(position);
    }

    public void addAlbum(BeAlbum album) {
        if (album != null) {
            mDatas.add(album);
            notifyDataSetChanged();
        }
    }

    public void setAlbum(int index, BeAlbum album) {
        if (album != null) {
            mDatas.set(index, album);
            notifyDataSetChanged();
        }
    }


    @Override
    public void convert(ViewHolder viewHolder, int position, BeAlbum item) {
        TextView tvTitle = viewHolder.getView(R.id.tvTitle);
        ImageView image = viewHolder.getView(R.id.imAlbum);
        if (position == 0 && 1 == isMyClass) {
            tvTitle.setText(R.string.create);
            image.setBackgroundResource(R.drawable.ico_c_album);
        } else {
            image.setBackgroundResource(R.color.white);
            tvTitle.setText(item.getName());
            GlideUtil.showNoneImage(mContext, item.getCoverUrl(), image, R.drawable.ico_default, true);
        }
    }
}
