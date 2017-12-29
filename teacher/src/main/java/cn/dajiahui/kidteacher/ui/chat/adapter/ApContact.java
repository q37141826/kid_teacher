package cn.dajiahui.kidteacher.ui.chat.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fxtx.framework.adapter.ViewHolder;
import com.fxtx.framework.widgets.listview.SectionedBaseAdapter;

import java.util.List;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.ui.chat.bean.BeContact;

/**
 * Created by Administrator on 2016/3/17.
 */
public class ApContact extends SectionedBaseAdapter {
    private Context context;
    private List<BeContact> datas;

    public ApContact(Context context, List<BeContact> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public Object getItem(int section, int position) {
        if (datas.get(section).getGroupList() != null && datas.get(section).getGroupList().size() > 0)
            return datas.get(section).getGroupList().get(position);
        if (datas.get(section).getClassList() != null && datas.get(section).getClassList().size() > 0)
            return datas.get(section).getClassList().get(position);
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
        if (datas.get(section).getGroupList() != null && datas.get(section).getGroupList().size() > 0)
            return datas.get(section).getGroupList().size();
        if (datas.get(section).getClassList() != null && datas.get(section).getClassList().size() > 0)
            return datas.get(section).getClassList().size();
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
        ViewHolder holder = ViewHolder.getHolder(context, convertView, parent, R.layout.contact_item_second, 1);
        TextView tvName = holder.getView(R.id.contact_item_second_name);
        TextView tvNum = holder.getView(R.id.contact_item_second_num);
        TextView tvLine = holder.getView(R.id.contact_item_line);
       if (datas.get(section).getGroupList()!=null&&datas.get(section).getGroupList().size()>0)
       {
           tvName.setText(datas.get(section).getGroupList().get(position).getName());
           tvNum.setText(datas.get(section).getGroupList().get(position).getUserCount()+"人");
       }
        if (datas.get(section).getClassList()!=null&&datas.get(section).getClassList().size()>0){
            tvName.setText(datas.get(section).getClassList().get(position).getClassName());
            tvNum.setText(datas.get(section).getClassList().get(position).getUserCount()+"人");
        }
        return holder.getConvertView();
    }

    @Override
    public View getSectionHeaderView(int section, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.getHolder(context, convertView, parent, R.layout.contact_item_first, 0);
        TextView tvName = holder.getView(R.id.contact_item_first);
        tvName.setText(datas.get(section).getName());
        return holder.getConvertView();
    }
}
