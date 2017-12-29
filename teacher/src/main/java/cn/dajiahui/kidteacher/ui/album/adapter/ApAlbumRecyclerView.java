package cn.dajiahui.kidteacher.ui.album.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.ui.album.AlbumActivity;
import cn.dajiahui.kidteacher.ui.album.bean.BeAlbum;
import cn.dajiahui.kidteacher.ui.album.bean.BeClassAlbum;
import cn.dajiahui.kidteacher.util.DjhJumpUtil;

/**
 * Created by Mjj on 2016/5/31.
 */
public class ApAlbumRecyclerView extends RecyclerView.Adapter<ApAlbumRecyclerView.MyViewHolder> {

    private Context context;
    private List<BeClassAlbum> list;
    private AlbumActivity albumActivity;
    private int isMyClass;

    public ApAlbumRecyclerView(Context context, List<BeClassAlbum> list) {
        this.context = context;
        this.list = list;
        albumActivity = (AlbumActivity) context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.album_item_class, parent, false);
        MyViewHolder holder = new MyViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        BeClassAlbum item = list.get(position);
        holder.tvTitle.setText(item.getClassName());
        isMyClass = item.getIsMyClass();

        ApAlbumTitle adapter = new ApAlbumTitle(context, item.getList(), position, isMyClass);
        albumActivity.ablunList.add(adapter);
        holder.gridView.setAdapter(adapter);
        holder.gridView.setOnItemClickListener(onItemClickListener);

        if (1 == isMyClass) {
            holder.gridView.setOnItemLongClickListener(onItemLongClickListener);
            holder.ivClassIco.setImageResource(R.drawable.ico_class_my);
        } else {
            holder.ivClassIco.setImageResource(R.drawable.ico_class_child);
        }
    }

    @Override
    public int getItemCount() {
        return list.size() == 0 ? 0 : list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        ImageView ivClassIco;
        GridView gridView;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            ivClassIco = (ImageView) itemView.findViewById(R.id.iv_Class_ico);
            gridView = (GridView) itemView.findViewById(R.id.gridview);
        }
    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            BeClassAlbum classs = list.get((int) id);
            BeAlbum album = classs.getList().get(position);
            if (position == 0 && classs.getIsMyClass() == 1) {
                albumActivity.itemIndex = (int) id;
                albumActivity.dialog.show();
            } else {
                DjhJumpUtil.getInstance().startPhotoActivity(context, album.getObjectId(), album.getName(), classs.getIsMyClass());
            }
        }
    };

    private AdapterView.OnItemLongClickListener onItemLongClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            BeClassAlbum classAlbum = list.get((int) id);
            if (position < 2 ||classAlbum.getIsMyClass() != 1) {
                return false;
            }
            albumActivity.albumIndex = position;
            albumActivity.itemIndex = (int) id;
            albumActivity.createAlbum.setTitle(R.string.set_album);
            albumActivity.createAlbum.setFloag(1);
            albumActivity.edAlbum.setText(classAlbum.getList().get(position).getName());
            albumActivity.edAlbum.setSelection(albumActivity.edAlbum.getText().length());
            albumActivity.createAlbum.show();
            return true;
        }
    };
}
