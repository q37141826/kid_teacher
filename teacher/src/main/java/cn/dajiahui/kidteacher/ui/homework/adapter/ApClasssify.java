package cn.dajiahui.kidteacher.ui.homework.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fxtx.framework.adapter.ViewHolder;

import java.util.List;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.ui.mine.bean.BeClass;


/**
 * 选择班级列表
 */
public class ApClasssify extends BaseAdapter {
    private Context context;
    private List<BeClass> datas;
    private int selectPosition = -1;


    public ApClasssify(Context context, List<BeClass> datas) {
        this.context = context;
        this.datas = datas;

    }

    /*改变UI*/
    public void reFreshItem(int selectPosition) {
        this.selectPosition = selectPosition;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return datas != null ? datas.size() : 0;
    }

    @Override
    public BeClass getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.getHolder(context, convertView, parent, R.layout.simple_spinner_item, 0);
        TextView tv = holder.getView(R.id.tv_classify_item);

        tv.setText(datas.get(position).getClass_name());

        if (selectPosition == position) {
            tv.setTextColor(context.getResources().getColor(R.color.blue_1F6DED));
            Drawable drawableUp = context.getResources().getDrawable(R.drawable.checked);
            drawableUp.setBounds(0, 0, drawableUp.getMinimumWidth(), drawableUp.getMinimumHeight());
            tv.setCompoundDrawables(null, null, drawableUp, null);
        } else {
            tv.setTextColor(context.getResources().getColor(R.color.gray_666666));
            tv.setCompoundDrawables(null, null, null, null);
        }

        return holder.getConvertView();
    }


}
