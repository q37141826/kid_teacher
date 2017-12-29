package cn.dajiahui.kidteacher.ui.mine.adapter;

import android.content.Context;
import android.widget.TextView;

import com.fxtx.framework.adapter.CommonAdapter;
import com.fxtx.framework.adapter.ViewHolder;

import java.util.List;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.ui.mine.bean.BeMyclass;

/**
 * 我的班级
 */
public class ApMyClass extends CommonAdapter<BeMyclass> {

    public ApMyClass(Context context, List<BeMyclass> mDatas) {
        super(context, mDatas, R.layout.item_myclass);
    }


    @Override
    public void convert(ViewHolder viewHolder, int position, BeMyclass item) {
        TextView tv_class = viewHolder.getView(R.id.tv_class);
        TextView tv_classnum = viewHolder.getView(R.id.tv_classnum);
        TextView class_code = viewHolder.getView(R.id.class_code);

        tv_class.setText(item.getMyclass());
        tv_classnum.setText(item.getStudentnum());
        class_code.setText(item.getClassum());
    }
}
