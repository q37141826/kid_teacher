package cn.dajiahui.kidteacher.ui.homework.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fxtx.framework.adapter.CommonAdapter;
import com.fxtx.framework.adapter.ViewHolder;

import java.util.List;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.ui.homework.bean.ChooseClass;


/**
 * 选择班级适配器
 */
public class ApChooseClass extends CommonAdapter<ChooseClass> {


    private ImageView id_item_select;
    private TextView tv_class;
    private int selectorPosition = -1;
    private RelativeLayout mImgSupplementary;


    public ApChooseClass(Context context, List<ChooseClass> mDatas) {
        super(context, mDatas, R.layout.item_chooseclass);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void convert(ViewHolder viewHolder, int position, ChooseClass item) {

        mImgSupplementary = viewHolder.getView(R.id.id_item_select_root);
        id_item_select = viewHolder.getView(R.id.id_item_select);
        tv_class = viewHolder.getView(R.id.tv_class);
        tv_class.setText(item.getClassname());

        //如果当前的position等于传过来点击的position,就去改变他的状态
        if (selectorPosition == position) {
            id_item_select.setImageResource(R.drawable.ico_im_ok);
//            mImgSupplementary.setBackgroundColor(R.color.yellow);
        } else {
            //其他的恢复原来的状态
            id_item_select.setImageResource(R.drawable.ico_im_not);
//            mImgSupplementary.setBackgroundColor(R.color.transparent);

        }
    }

    public void changeState(int pos) {
        selectorPosition = pos;
        notifyDataSetChanged();
    }

}
