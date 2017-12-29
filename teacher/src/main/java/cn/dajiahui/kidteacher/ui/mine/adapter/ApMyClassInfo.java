package cn.dajiahui.kidteacher.ui.mine.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.fxtx.framework.adapter.CommonAdapter;
import com.fxtx.framework.adapter.ViewHolder;

import java.util.List;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.ui.mine.bean.BeMyclassInfo;

/**
 * 我的班级
 */
public class ApMyClassInfo extends CommonAdapter<BeMyclassInfo> {

    public ApMyClassInfo(Context context, List<BeMyclassInfo> mDatas) {
        super(context, mDatas, R.layout.item_myclassinfo);
    }


    @Override
    public void convert(ViewHolder viewHolder, final int position, BeMyclassInfo item) {
        ImageView img_head = viewHolder.getView(R.id.img_head);
        TextView tv_studentname = viewHolder.getView(R.id.tv_studentname);
        TextView tv_delete = viewHolder.getView(R.id.tv_delete);

        tv_studentname.setText(item.getStudentname());

//        tv_delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mDatas.get(position).
//
//            }
//        });

    }
}
