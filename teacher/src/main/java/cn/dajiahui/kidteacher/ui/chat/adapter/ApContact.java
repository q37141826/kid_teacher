package cn.dajiahui.kidteacher.ui.chat.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fxtx.framework.adapter.ViewHolder;
import com.fxtx.framework.image.util.GlideUtil;
import com.fxtx.framework.widgets.listview.SectionedBaseAdapter;

import java.util.List;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.ui.chat.bean.BeContact;
import cn.dajiahui.kidteacher.ui.chat.bean.BeGroupListUsers;

/**
 * Created by Administrator on 2016/3/17.
 */
public class ApContact extends SectionedBaseAdapter {
    private Context context;
    private List<BeGroupListUsers> datas;

    public ApContact(Context context, List<BeGroupListUsers> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public Object getItem(int section, int position) {
        if (datas.get(section).getStudent_list()!= null && datas.get(section).getStudent_list().size() > 0)
            return datas.get(section).getStudent_list().get(position);
        return null;
    }

    @Override
    public long getItemId(int section, int position) {
        return position;
    }

    @Override
    public int getSectionCount() {
        return datas != null ? datas.size() : 0;
    }

    @Override
    public int getCountForSection(int section) {
        if (datas.get(section).getStudent_list() != null && datas.get(section).getStudent_list().size() > 0)
            return datas.get(section).getStudent_list().size();
        return 0;
    }

    /**
     * 二级列表的展示信息（子Item）
     *
     * @param section
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getItemView(int section, int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.getHolder(context, convertView, parent, R.layout.contact_item_detail_child, 1);
//        TextView tvName = holder.getView(R.id.contact_item_second_name);
//        TextView tvNum = holder.getView(R.id.contact_item_second_num);
//        TextView tvLine = holder.getView(R.id.contact_item_line);
//       if (datas.get(section).getStudent_list()!=null&&datas.get(section).getStudent_list().size()>0)
//       {
//           tvName.setText(datas.get(section).getStudent_list().get(position).getNickname());
//       }

        ImageView userAvtor = holder.getView(R.id.round_imge_child);
        TextView userName =holder.getView(R.id.text_child_name);

        TextView fillText = holder.getView(R.id.fill);
        if(position == getCountForSection(section) -1 ) {
            fillText.setVisibility(View.GONE);
        } else {
            fillText.setVisibility(View.VISIBLE);
        }

        if (datas.get(section).getStudent_list().get(position).getAvatar()!=null) {
            GlideUtil.showRoundImage(context, datas.get(section).getStudent_list().get(position).getAvatar(), userAvtor, R.drawable.ico_default_user, true);
        }

        userName.setText(datas.get(section).getStudent_list().get(position).getNickname());

        return holder.getConvertView();
    }

    @Override
    public View getSectionHeaderView(int section, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.getHolder(context, convertView, parent, R.layout.contact_item_first, 0);
        TextView tvName = holder.getView(R.id.contact_item_first);
        tvName.setText(datas.get(section).getClass_name());
        return holder.getConvertView();
    }
}
