package cn.dajiahui.kidteacher.ui.mine.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fxtx.framework.adapter.CommonAdapter;
import com.fxtx.framework.adapter.ViewHolder;
import com.fxtx.framework.http.callback.ResultCallback;
import com.fxtx.framework.image.util.GlideUtil;
import com.fxtx.framework.json.HeadJson;
import com.fxtx.framework.log.Logger;
import com.fxtx.framework.log.ToastUtil;
import com.squareup.okhttp.Request;

import java.util.List;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.http.RequestUtill;
import cn.dajiahui.kidteacher.ui.mine.bean.BeWaiteAddStudent;

/**
 * 等待加入班级
 */
public class ApWaiteAddclass extends CommonAdapter<BeWaiteAddStudent> {

    private final static int HTTP_TYPE_APPLY_ACCEPT = 1;  // 批准申请
    private final static int HTTP_TYPE_APPLY_DENY = 2;  // 拒绝申请
    private final static int MSG_SHOW_FXDIALOG = 1;     // 显示dialog
    private final static int MSG_DISMISS_FXDIALOG = 2;  // 关闭dialog
    private Handler activityHandler;
    private ItemInfo selectItem;
    int selectorPosition = -3;

    public ApWaiteAddclass(Context context, List<BeWaiteAddStudent> mDatas, Handler handler) {
        super(context, mDatas, R.layout.item_waiteaddclass);
        this.activityHandler = handler;
    }

    private class ItemInfo {
        ViewHolder viewHolder;
        int position;
        BeWaiteAddStudent item;
    }

    @Override
    public void convert(ViewHolder viewHolder, int position, BeWaiteAddStudent item) {
        RelativeLayout re_choose_root = viewHolder.getView(R.id.re_choose_root);
        ImageView img_pic = viewHolder.getView(R.id.img_pic);
        TextView tv_studentname = viewHolder.getView(R.id.tv_studentname);
        TextView tv_applyaddclass = viewHolder.getView(R.id.tv_applyaddclass);
        TextView tv_agree = viewHolder.getView(R.id.tv_agree);
        TextView tv_disagree = viewHolder.getView(R.id.tv_disagree);
        TextView tv_already = viewHolder.getView(R.id.tv_already);
        CheckBox checkBox = viewHolder.getView(R.id.checkbox);
        checkBox.setChecked(mDatas.get(position).getBo());


        //如果当前的position等于传过来点击的position,就去改变他的状态
        if (selectorPosition == -1) {
            checkBox.setVisibility(View.VISIBLE);
        } else if (selectorPosition == -2) {
            checkBox.setVisibility(View.GONE);
        }
        ItemInfo itemInfo = new ItemInfo();
        itemInfo.item = item;
        itemInfo.position = position;
        itemInfo.viewHolder = viewHolder;
        tv_agree.setTag(itemInfo);
        tv_agree.setOnClickListener(onClick);

        tv_disagree.setTag(itemInfo);
        tv_disagree.setOnClickListener(onClick);

        tv_studentname.setText(item.getNickname());

        Logger.d("item.getClass_name().length():" + item.getClass_name().length());

        if (item.getClass_name().length() > 15) {
            String substring = item.getClass_name().substring(0, 10);
            tv_applyaddclass.setText("申请加入" + substring+"...");
        } else {

            tv_applyaddclass.setText("申请加入" + item.getClass_name());
        }

        if (item.getStatus().equals("0")) {
            // 已同意
            re_choose_root.setVisibility(View.GONE);
            tv_already.setVisibility(View.VISIBLE);
            tv_already.setText("已同意");
            tv_already.setBackgroundResource(R.drawable.round_bg_gray_addclass);
            tv_already.setTextColor(mContext.getResources().getColor(R.color.gray_DCDCDC));

        } else if (item.getStatus().equals("3")) {
            // 已拒绝
            re_choose_root.setVisibility(View.GONE);
            tv_already.setVisibility(View.VISIBLE);
            tv_already.setText("已拒绝");
            tv_already.setBackgroundResource(R.drawable.round_bg_gray_addclass);
            tv_already.setTextColor(mContext.getResources().getColor(R.color.gray_DCDCDC));

        } else {
            re_choose_root.setVisibility(View.VISIBLE);
            tv_already.setVisibility(View.GONE);
        }

        if (item.getAvatar() != null && !item.getAvatar().equals("")) {
            GlideUtil.showRoundImage(mContext, item.getAvatar(), img_pic, R.drawable.ico_default_user, true); // 头像
        }

    }

    private View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ItemInfo itemInfo = (ItemInfo) v.getTag();
            selectItem = itemInfo;
            switch (v.getId()) {

                case R.id.tv_agree:
                    activityHandler.sendEmptyMessage(MSG_SHOW_FXDIALOG);
                    httpData(HTTP_TYPE_APPLY_ACCEPT, itemInfo);
                    break;

                case R.id.tv_disagree:
                    activityHandler.sendEmptyMessage(MSG_SHOW_FXDIALOG);
                    httpData(HTTP_TYPE_APPLY_DENY, itemInfo);
                    break;

                default:
                    break;
            }
        }
    };

    public void httpData(int httpType, ItemInfo itemInfo) {
        //网络请求
        switch (httpType) {
            case HTTP_TYPE_APPLY_ACCEPT:
                RequestUtill.getInstance().httpAcceptApply(mContext, callAcceptApply, itemInfo.item.getClass_id(), itemInfo.item.getUser_id()); // 批准学生申请
                break;

            case HTTP_TYPE_APPLY_DENY:
                RequestUtill.getInstance().httpDenyApply(mContext, callDenyApply, itemInfo.item.getClass_id(), itemInfo.item.getUser_id()); // 拒绝学生申请
                break;
        }

    }

    /**
     * 批准学生申请的callback函数
     */
    ResultCallback callAcceptApply = new ResultCallback() {
        @Override
        public void onError(Request request, Exception e) {
            activityHandler.sendEmptyMessage(MSG_DISMISS_FXDIALOG);
        }

        @Override
        public void onResponse(String response) {
            activityHandler.sendEmptyMessage(MSG_DISMISS_FXDIALOG);

            HeadJson json = new HeadJson(response);
            if (json.getstatus() == 0) {
                if (selectItem != null) {
                    ViewHolder viewHolder = selectItem.viewHolder;
                    RelativeLayout re_choose_root = viewHolder.getView(R.id.re_choose_root);
                    TextView tv_already = viewHolder.getView(R.id.tv_already);
                    re_choose_root.setVisibility(View.GONE);
                    tv_already.setVisibility(View.VISIBLE);
                    tv_already.setText("已同意");
                    tv_already.setBackgroundResource(R.drawable.round_bg_gray_addclass);
                    tv_already.setTextColor(mContext.getResources().getColor(R.color.gray_DCDCDC));
//                    Toast.makeText(mContext, "同意", Toast.LENGTH_SHORT).show();
                }

            } else {
                ToastUtil.showToast(mContext, json.getMsg());
            }
        }
    };

    /**
     * 拒绝学生申请的callback函数
     */
    ResultCallback callDenyApply = new ResultCallback() {
        @Override
        public void onError(Request request, Exception e) {
            activityHandler.sendEmptyMessage(MSG_DISMISS_FXDIALOG);
        }

        @Override
        public void onResponse(String response) {
            activityHandler.sendEmptyMessage(MSG_DISMISS_FXDIALOG);

            HeadJson json = new HeadJson(response);
            if (json.getstatus() == 0) {
                ViewHolder viewHolder = selectItem.viewHolder;
                RelativeLayout re_choose_root = viewHolder.getView(R.id.re_choose_root);
                TextView tv_already = viewHolder.getView(R.id.tv_already);
                re_choose_root.setVisibility(View.GONE);
                tv_already.setVisibility(View.VISIBLE);
                tv_already.setText("已拒绝");
                tv_already.setBackgroundResource(R.drawable.round_bg_gray_addclass);
                tv_already.setTextColor(mContext.getResources().getColor(R.color.gray_DCDCDC));
//                Toast.makeText(mContext, "拒绝", Toast.LENGTH_SHORT).show();

            } else {
                ToastUtil.showToast(mContext, json.getMsg());
            }
        }
    };


    public void changeState(int pos) {
        selectorPosition = pos;
        notifyDataSetChanged();
    }

}
