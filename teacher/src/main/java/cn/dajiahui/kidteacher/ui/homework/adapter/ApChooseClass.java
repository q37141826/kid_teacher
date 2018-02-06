package cn.dajiahui.kidteacher.ui.homework.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fxtx.framework.adapter.CommonAdapter;
import com.fxtx.framework.adapter.ViewHolder;

import java.util.List;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.ui.homework.bean.BeHomewrokClass;
import cn.dajiahui.kidteacher.ui.homework.bean.ChooseClass;


/**
 * 选择班级适配器
 */
public class ApChooseClass extends CommonAdapter<BeHomewrokClass> {


    private ImageView id_item_select;
    private TextView tv_class;
    private int selectorPosition = -1;
    private Context context;


    public ApChooseClass(Context context, List<BeHomewrokClass> mDatas) {
        super(context, mDatas, R.layout.item_chooseclass);
        this.context = context;
    }

    @Override
    public void convert(ViewHolder viewHolder, int position, BeHomewrokClass item) {

        id_item_select = viewHolder.getView(R.id.id_item_select);
        tv_class = viewHolder.getView(R.id.tv_class);
        tv_class.setText(item.getClass_name());

        if (item.getIs_pubed().equals("1")) {
            viewHolder.getView(R.id.tv_published).setVisibility(View.VISIBLE); // 显示已布置
        }

        //如果当前的position等于传过来点击的position,就去改变他的状态
        if (selectorPosition == position) {
            id_item_select.setImageResource(R.drawable.ico_im_ok);
            tv_class.setTextColor(context.getResources().getColor(R.color.blue_dark));
        } else {
            //其他的恢复原来的状态
            id_item_select.setImageResource(R.drawable.ico_im_not);
            tv_class.setTextColor(context.getResources().getColor(R.color.black_tv_6));
        }
    }

    public void changeState(int pos) {
        selectorPosition = pos;
        notifyDataSetChanged();
    }

}
