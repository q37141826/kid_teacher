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
import cn.dajiahui.kidteacher.ui.mine.bean.BeWaiteAddClass;

/**
 * 等待加入班级
 */
public class ApWaiteAddclass extends CommonAdapter<BeWaiteAddClass> {

    private RelativeLayout re_choose_root;
    private TextView tv_already;

    public ApWaiteAddclass(Context context, List<BeWaiteAddClass> mDatas) {
        super(context, mDatas, R.layout.item_waiteaddclass);
    }


    @Override
    public void convert(ViewHolder viewHolder, int position, BeWaiteAddClass item) {
        re_choose_root = viewHolder.getView(R.id.re_choose_root);
        tv_already = viewHolder.getView(R.id.tv_already);
        ImageView img_pic = viewHolder.getView(R.id.img_pic);
        TextView tv_studentname = viewHolder.getView(R.id.tv_studentname);
        TextView tv_applyaddclass = viewHolder.getView(R.id.tv_applyaddclass);
        TextView tv_agree = viewHolder.getView(R.id.tv_agree);
        TextView tv_disagree = viewHolder.getView(R.id.tv_disagree);

        tv_studentname.setText(item.getStudentname());
        tv_applyaddclass.setText(item.getClassname());

        tv_agree.setOnClickListener(onClick);
        tv_applyaddclass.setOnClickListener(onClick);
    }

    // 刷新有问题
    private View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.tv_agree:

                    Toast.makeText(mContext, "同意", Toast.LENGTH_SHORT).show();
                    re_choose_root.setVisibility(View.GONE);
                    tv_already.setVisibility(View.VISIBLE);
                    tv_already.setText("已同意");
                    notifyDataSetChanged();
                    break;
                case R.id.tv_disagree:
                    re_choose_root.setVisibility(View.GONE);
                    tv_already.setVisibility(View.VISIBLE);
                    tv_already.setText("已拒绝");
                    Toast.makeText(mContext, "拒绝", Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();
                    break;

                default:
                    break;

            }
        }
    };
}
