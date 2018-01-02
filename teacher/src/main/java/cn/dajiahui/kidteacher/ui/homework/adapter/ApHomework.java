package cn.dajiahui.kidteacher.ui.homework.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fxtx.framework.adapter.ViewHolder;
import com.fxtx.framework.widgets.listview.SectionedBaseAdapter;

import java.util.List;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.ui.homework.bean.Homework;


/**
 * 作业适配器
 */
public class ApHomework extends SectionedBaseAdapter {
    private Context context;
    private List<Homework> data;

    public ApHomework(Context context, List<Homework> data) {
        this.context = context;
        this.data = data;
    }

    //刷新适配器
    public void onRefreshListData(Context context) {
        this.context = context;
        this.data = data;
        this.notifyDataSetChanged();
    }


    @Override
    public Object getItem(int section, int position) {
//        if (data.get(section).getList() != null && data.get(section).getList().size() > 0)
//            return data.get(section).getList().get(position);
        return null;
    }

    @Override
    public long getItemId(int section, int position) {
        return position;
    }

    @Override
    public int getSectionCount() {
        return data != null ? data.size() : 0;
    }

    @Override
    public int getCountForSection(int section) {
//        if (data.get(section).getList()!=null&&data.get(section).getList().size()>0)
//            return datas.get(section).getList().size();
        return 3;
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
        ViewHolder holder = ViewHolder.getHolder(context, convertView, parent, R.layout.task_item_second, 1);
        LinearLayout tv_task = holder.getView(R.id.re_task_second);
        TextView tv_classname = holder.getView(R.id.task_second_class_name);
        TextView tv_time = holder.getView(R.id.task_second_time);
        TextView tv_content = holder.getView(R.id.task_second_content);
        TextView tv_status = holder.getView(R.id.test_second_status);
        TextView tv_complete = holder.getView(R.id.task_second_complete);
        TextView task_second_completeTotal = holder.getView(R.id.task_second_completeTotal);

        tv_classname.setText(data.get(section).getTask_second_class_name());
        tv_time.setText(data.get(section).getTask_second_time());
        tv_content.setText(data.get(section).getTask_second_content());
        tv_complete.setText(data.get(section).getTask_second_complete());

        tv_status.setText(data.get(section).getTest_second_status());
        if ("待检查".equals(data.get(position).getTask_check())) {
            tv_status.setTextColor(context.getResources().getColor(R.color.red));

        } else {
            tv_status.setTextColor(context.getResources().getColor(R.color.black));
        }

        return holder.getConvertView();
    }

    @Override
    public View getSectionHeaderView(int section, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.getHolder(context, convertView, parent, R.layout.task_item_first, 0);
        TextView tv_first_name = holder.getView(R.id.task_first_name);
        tv_first_name.setText(data.get(section).getTest_first_name());


        return holder.getConvertView();
    }
}
