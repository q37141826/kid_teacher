package cn.dajiahui.kidteacher.ui.mine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fxtx.framework.http.ErrorCode;
import com.fxtx.framework.http.callback.ResultCallback;
import com.fxtx.framework.json.HeadJson;
import com.fxtx.framework.log.ToastUtil;
import com.fxtx.framework.ui.FxFragment;
import com.squareup.okhttp.Request;

import org.json.JSONObject;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.http.RequestUtill;
import cn.dajiahui.kidteacher.ui.mine.myclass.MyClassActivity;
import cn.dajiahui.kidteacher.ui.mine.notice.NoticeActivity;
import cn.dajiahui.kidteacher.ui.mine.personalinformation.UserDetailsActivity;
import cn.dajiahui.kidteacher.ui.mine.setting.SettingActivity;
import cn.dajiahui.kidteacher.ui.mine.waiteaddclass.WaiteAddClassActivity;
import cn.dajiahui.kidteacher.util.DjhJumpUtil;

/**
 * 我的
 */
public class FrMine extends FxFragment {
    public static final int PICFPRRESULT = 9;
    private ImageView imBg, imSet;
    public ImageView imUser;
    private TextView tvVersion;
    private Button btnEdit;

    private String url = "";
    public TextView tv_userName, tv_campusName; // 用户名和个性签名

    @Override
    protected View initinitLayout(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fr_mine, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        tv_userName.setText("张三");
        tv_campusName.setText("北京");
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    private View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.img_edit:
                    Toast.makeText(activity, "编辑", Toast.LENGTH_SHORT).show();
                    //设置
                    DjhJumpUtil.getInstance().startBaseActivityForResult(getActivity(), UserDetailsActivity.class, null, PICFPRRESULT);
                    break;
                case R.id.iv_user:

                    Toast.makeText(activity, "头像", Toast.LENGTH_SHORT).show();

                    break;

                case R.id.tvMyclass:

                    Toast.makeText(activity, "我的班级", Toast.LENGTH_SHORT).show();
                    DjhJumpUtil.getInstance().startBaseActivity(getContext(), MyClassActivity.class);
                    break;
                case R.id.tvWaitaddclass:
                    Toast.makeText(activity, "等待加入班级", Toast.LENGTH_SHORT).show();
                    DjhJumpUtil.getInstance().startBaseActivity(getContext(), WaiteAddClassActivity.class);
                    break;
                case R.id.tvNotice:
                    Toast.makeText(activity, "通知", Toast.LENGTH_SHORT).show();

                    DjhJumpUtil.getInstance().startBaseActivity(getContext(),  NoticeActivity.class);

                    break;
                case R.id.tvSetting:
                    DjhJumpUtil.getInstance().startBaseActivity(getContext(), SettingActivity.class);
                    break;


                default:
                    break;
            }
        }
    };

    // 关于我们
    private void httpAboutUs() {
        showfxDialog();
        RequestUtill.getInstance().httpAboutUs(getActivity(), new ResultCallback() {
            @Override
            public void onError(Request request, Exception e) {
                dismissfxDialog();
                ToastUtil.showToast(getActivity(), ErrorCode.error(e));
            }

            @Override
            public void onResponse(String response) {
                dismissfxDialog();
                HeadJson json = new HeadJson(response);
                if (json.getFlag() == 1) {
                    try {
                        JSONObject obj = new JSONObject(response);
                        JSONObject obj2 = obj.getJSONObject("info");
                        url = obj2.getString("aboutUrl");
//                        DjhJumpUtil.getInstance().startWebActivity(getContext(), getString(R.string.text_about), url, false);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    ToastUtil.showToast(getActivity(), json.getMsg());
                }
            }
        });
    }

    /*初始化*/
    private void init() {
        //        imBg = getView(R.id.ib_bg);
        imUser = getView(R.id.iv_user);
        imSet = getView(R.id.img_edit);
        imUser.setOnClickListener(onClick);
        imSet.setOnClickListener(onClick);

        tv_userName = getView(R.id.tv_user_name);
        tv_campusName = getView(R.id.tv_campus_name);


        getView(R.id.tvMyclass).setOnClickListener(onClick);
        getView(R.id.tvWaitaddclass).setOnClickListener(onClick);
        TextView mWaitaddclassconut = getView(R.id.tv_waitaddclassconut);
        getView(R.id.tvNotice).setOnClickListener(onClick);
        TextView mNoticecount = getView(R.id.tv_noticecount);
        getView(R.id.tvAbout).setOnClickListener(onClick);
        getView(R.id.tvSetting).setOnClickListener(onClick);
    }
}
