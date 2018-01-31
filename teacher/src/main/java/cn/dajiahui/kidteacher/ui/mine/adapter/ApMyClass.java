package cn.dajiahui.kidteacher.ui.mine.adapter;

import android.content.Context;
import android.widget.TextView;

import com.fxtx.framework.adapter.CommonAdapter;
import com.fxtx.framework.adapter.ViewHolder;

import java.util.List;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.ui.mine.bean.BeClass;

/**
 * 我的班级
 */
public class ApMyClass extends CommonAdapter<BeClass> {

    public ApMyClass(Context context, List<BeClass> mDatas) {
        super(context, mDatas, R.layout.item_myclass);
    }


    @Override
    public void convert(ViewHolder viewHolder, int position, BeClass item) {
        TextView className = viewHolder.getView(R.id.myclass_tv_class_name);
        TextView studentNumber = viewHolder.getView(R.id.myclass_tv_class_code);
        TextView classCode = viewHolder.getView(R.id.myclass_tv_student_number);

        className.setText(item.getClass_name());
        studentNumber.setText(this.mContext.getString(R.string.class_code) + item.getCode());
        classCode.setText(item.getStudents_num() + "人");
    }
}
