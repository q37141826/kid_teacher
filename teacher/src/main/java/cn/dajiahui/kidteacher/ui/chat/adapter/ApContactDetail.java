package cn.dajiahui.kidteacher.ui.chat.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.fxtx.framework.adapter.CommonAdapter;
import com.fxtx.framework.adapter.ViewHolder;
import com.fxtx.framework.image.util.GlideUtil;

import java.util.List;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.ui.chat.bean.BeContactUser;

/**
 * Created by wdj on 2016/3/17.
 */
public class ApContactDetail extends CommonAdapter<BeContactUser> {

    public ApContactDetail(Context context, List<BeContactUser> mDatas) {
        super(context, mDatas, R.layout.contact_item_detail_child);
    }

    @Override
    public void convert(ViewHolder viewHolder, int position, BeContactUser item) {
        ImageView userAvtor = viewHolder.getView(R.id.round_imge_child);
        TextView userName =viewHolder.getView(R.id.text_child_name);
//        if (item.getAvator()!=null)
//            GlideUtil.showRoundImage(mContext, item.getAvator(), userAvtor, R.drawable.ico_default_user, true);
//        userName.setText(item.getRealName());
    }
}
