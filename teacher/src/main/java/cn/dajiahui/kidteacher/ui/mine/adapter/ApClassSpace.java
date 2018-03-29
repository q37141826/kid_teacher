package cn.dajiahui.kidteacher.ui.mine.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fxtx.framework.adapter.CommonAdapter;
import com.fxtx.framework.adapter.ViewHolder;

import java.util.List;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.ui.mine.bean.BeClassSpaceList;
import cn.dajiahui.kidteacher.ui.mine.myclass.ShowPictureActivity;
import cn.dajiahui.kidteacher.util.DateUtils;
import cn.dajiahui.kidteacher.util.DjhJumpUtil;

/**
 * 我的班级
 */
public class ApClassSpace extends CommonAdapter<BeClassSpaceList> {
    private Context context;
    private Activity activity;

    public ApClassSpace(Context context, List<BeClassSpaceList> mDatas) {
        super(context, mDatas, R.layout.item_classspace);
        this.context = context;
        this.activity = (Activity) context;
    }


    @Override
    public void convert(ViewHolder viewHolder, final int position, final BeClassSpaceList item) {

        TextView tv_classname = viewHolder.getView(R.id.tv_classname);
        TextView tv_endtime = viewHolder.getView(R.id.tv_endtime);
        TextView tv_content = viewHolder.getView(R.id.tv_content);
        RelativeLayout grildview_root = viewHolder.getView(R.id.grildview_root);

        if (item.getImg_url().size()==0) {
            grildview_root.setVisibility(View.GONE);
        } else {
            cn.dajiahui.kidteacher.view.NoSlideGrildView grildview = viewHolder.getView(R.id.grildview);

            ApClassSpacepicture apClassSpacepicture = new ApClassSpacepicture(this.context, item.getThumbnail());
            grildview.setAdapter(apClassSpacepicture);
            /*图片item的点击事件*/
            grildview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Bundle bundle = new Bundle();
                    bundle.putString("IMG_URL", item.getImg_url().get(position));
                    int location[] = new int[2];
                    view.getLocationOnScreen(location);
                    bundle.putInt("locationX", location[0]);
                    bundle.putInt("locationY", location[1]);
                    bundle.putInt("width", view.getWidth());
                    bundle.putInt("height", view.getHeight());


                /*先跳转 在网络请请求获取数据*/
                    DjhJumpUtil.getInstance().startBaseActivity(context, ShowPictureActivity.class, bundle, 0);
                    activity.overridePendingTransition(0, 0);
                }
            });
        }

        tv_classname.setText(item.getClass_name() + "班动态");
        tv_endtime.setText("发表于：" + DateUtils.timeHour(item.getCreated_at()));
        tv_content.setText(item.getContent());
    }
}
