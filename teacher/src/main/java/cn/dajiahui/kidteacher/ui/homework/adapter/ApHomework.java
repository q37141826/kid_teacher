package cn.dajiahui.kidteacher.ui.homework.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fxtx.framework.adapter.ViewHolder;
import com.fxtx.framework.time.TimeUtil;
import com.fxtx.framework.widgets.listview.SectionedBaseAdapter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.ui.homework.bean.BeHomeworkList;

import static com.fxtx.framework.time.TimeUtil.dayOfWeek;
import static com.fxtx.framework.time.TimeUtil.isSameDay;


/**
 * 作业适配器
 */
public class ApHomework extends SectionedBaseAdapter {

    private String Day[] = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};


    private Context context;
    private List<BeHomeworkList> data;

    public ApHomework(Context context, List<BeHomeworkList> data) {
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
        if (data.get(section).getHome_list() != null && data.get(section).getHome_list().size() > 0)
            return data.get(section).getHome_list().get(position);
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
        int count = 0;
        if (data.get(section).getHome_list()!=null&&data.get(section).getHome_list().size()>0) {
            count = data.get(section).getHome_list().size();
        }

        return count;
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
        TextView tv_classname = holder.getView(R.id.task_second_class_name);
        TextView tv_end_time = holder.getView(R.id.task_end_time);
        TextView tv_content = holder.getView(R.id.task_second_content); // 作业名
        TextView tv_status = holder.getView(R.id.test_second_status);
        TextView tv_complete = holder.getView(R.id.task_second_complete);
        TextView task_second_completeTotal = holder.getView(R.id.task_second_completeTotal);
        TextView bottomLine = holder.getView(R.id.task_bottom_line);

        tv_classname.setText(data.get(section).getHome_list().get(position).getClass_name()); // 班名

        String stampStr = data.get(section).getHome_list().get(position).getEnd_time();
        String endTime = TimeUtil.stampToString(stampStr);
        tv_end_time.setText(endTime);  // 截止时间

        tv_content.setText(data.get(section).getHome_list().get(position).getName()); // 作业名

        tv_complete.setText(data.get(section).getHome_list().get(position).getComplete_students());  // 完成多少人
        task_second_completeTotal.setText(("/" + data.get(section).getHome_list().get(position).getAll_student()));  // 总共多少人


        if (data.get(section).getHome_list().get(position).getIs_checked().equals("0")) {
            tv_status.setText(R.string.task_unexamined); // 待检查
            tv_status.setTextColor(context.getResources().getColor(R.color.red_F1311D));

        } else {
            tv_status.setText(R.string.task_examined);  // 已审查
            tv_status.setTextColor(context.getResources().getColor(R.color.black_tv_3));
        }

        if (position == (getCountForSection(section) - 1)) {
            /* 一个section中最后一个item */
            bottomLine.setVisibility(View.INVISIBLE);
        } else {
            bottomLine.setVisibility(View.VISIBLE);
        }
        return holder.getConvertView();
    }

    @Override
    public View getSectionHeaderView(int section, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.getHolder(context, convertView, parent, R.layout.task_item_first, 0);
        TextView tv_first_name = holder.getView(R.id.task_first_name);
        try {
            String pubDataStr = data.get(section).getPubdate();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date pubData = df.parse(pubDataStr);
            String day = Day[dayOfWeek(pubData)-1];
            Date today = new Date();
            if (isSameDay(today, pubData)) {
                tv_first_name.setText("今天 " + day);
            } else {
                tv_first_name.setText(data.get(section).getPubdate() + " " + day);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return holder.getConvertView();
    }
}
