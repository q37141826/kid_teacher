package cn.dajiahui.kidteacher.ui.mine.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fxtx.framework.adapter.CommonAdapter;
import com.fxtx.framework.adapter.ViewHolder;
import com.fxtx.framework.http.callback.ResultCallback;
import com.fxtx.framework.image.util.GlideUtil;
import com.fxtx.framework.json.HeadJson;
import com.fxtx.framework.log.ToastUtil;
import com.fxtx.framework.widgets.dialog.FxDialog;
import com.squareup.okhttp.Request;

import java.util.List;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.http.RequestUtill;
import cn.dajiahui.kidteacher.ui.mine.bean.BeStudents;
import cn.dajiahui.kidteacher.ui.mine.setting.SettingActivity;

/**
 * 我的班级
 */
public class ApMyClassInfo extends CommonAdapter<BeStudents> {

    private final static int MSG_SHOW_FXDIALOG = 1;     // 显示dialog
    private final static int MSG_DISMISS_FXDIALOG = 2;  // 关闭dialog
    private final static int MSG_REFRESH_LIST = 3;  // 刷新列表

    private Handler activityHandler;
    private ItemInfo selectItem;
    private String classId;
    private List<BeStudents> studentInfoList;
    private Context context;

    private class ItemInfo {
        ViewHolder viewHolder;
        int position;
        BeStudents item;
    }

    public ApMyClassInfo(Context context, List<BeStudents> mDatas, Handler handler, String classId) {
        super(context, mDatas, R.layout.item_myclassinfo);
        this.context = context;
        this.activityHandler = handler;
        this.classId = classId;
        this.studentInfoList = mDatas;
    }


    @Override
    public void convert(ViewHolder viewHolder, final int position, BeStudents item) {
        ItemInfo itemInfo = new ItemInfo();
        itemInfo.item = item;
        itemInfo.position = position;
        itemInfo.viewHolder = viewHolder;

        ImageView img_head = viewHolder.getView(R.id.img_head);

        if (item.getAvatar() != null && !item.getAvatar().equals("")) {
            GlideUtil.showRoundImage(mContext, item.getAvatar(), img_head, R.drawable.ico_default_user, true); // 头像
        }

        TextView tv_studentname = viewHolder.getView(R.id.tv_studentname);
        TextView tv_delete = viewHolder.getView(R.id.tv_delete);
        tv_delete.setTag(itemInfo);
        tv_delete.setOnClickListener(onClick);

        tv_studentname.setText(item.getNickname());
    }


    private View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final ItemInfo itemInfo = (ItemInfo) v.getTag();
            FxDialog dialog = new FxDialog(context) {
                @Override
                public void onRightBtn(int flag) {
                    selectItem = itemInfo;
                    activityHandler.sendEmptyMessage(MSG_SHOW_FXDIALOG);
                    httpData(itemInfo.item.getId());
                }

                @Override
                public void onLeftBtn(int flag) {

                }
            };
            dialog.setTitle(R.string.prompt);
            dialog.setMessage(R.string.remove_student);
            dialog.show();


        }
    };

    public void httpData(String userId) {
        //网络请求
        ResultCallback call = new ResultCallback() {
            @Override
            public void onError(Request request, Exception e) {
                activityHandler.sendEmptyMessage(MSG_DISMISS_FXDIALOG);
            }

            @Override
            public void onResponse(String response) {
                activityHandler.sendEmptyMessage(MSG_DISMISS_FXDIALOG);
                HeadJson json = new HeadJson(response);
                if (json.getstatus() == 0) {
                    /* 解析班级列表信息 */
                    studentInfoList.remove(selectItem.position);
                    activityHandler.sendEmptyMessage(MSG_REFRESH_LIST);

                    Toast.makeText(mContext, "移除成功!", Toast.LENGTH_SHORT).show();

                } else {
                    ToastUtil.showToast(mContext, json.getMsg());
                }
            }
        };

        RequestUtill.getInstance().httpRemoveStudent(mContext, call, classId, userId); // 移除学生
    }
}
