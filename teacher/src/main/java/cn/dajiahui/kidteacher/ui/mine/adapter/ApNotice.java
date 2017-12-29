package cn.dajiahui.kidteacher.ui.mine.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fxtx.framework.adapter.CommonAdapter;
import com.fxtx.framework.adapter.ViewHolder;

import java.util.List;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.ui.mine.bean.BeNotice;

/**
 * 等待加入班级
 */
public class ApNotice extends CommonAdapter<BeNotice> {


    public ApNotice(Context context, List<BeNotice> mDatas) {
        super(context, mDatas, R.layout.item_notice);
    }


    @Override
    public void convert(ViewHolder viewHolder, int position, BeNotice item) {

        TextView tv_updatecontent = viewHolder.getView(R.id.tv_updatecontent);
        TextView tv_deadline = viewHolder.getView(R.id.tv_deadline);

        tv_updatecontent.setText(item.getUpdateContent());
        tv_deadline.setText(item.getDeadline());


    }


}
