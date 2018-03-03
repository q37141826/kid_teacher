package cn.dajiahui.kidteacher.ui.mine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fxtx.framework.http.ErrorCode;
import com.fxtx.framework.http.callback.ResultCallback;
import com.fxtx.framework.image.util.GlideUtil;
import com.fxtx.framework.json.HeadJson;
import com.fxtx.framework.log.ToastUtil;
import com.fxtx.framework.ui.FxFragment;
import com.squareup.okhttp.Request;

import org.json.JSONObject;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.controller.UserController;
import cn.dajiahui.kidteacher.http.RequestUtill;
import cn.dajiahui.kidteacher.ui.mine.about.AboutActivity;
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
    public TextView tv_userName, tv_campusName;
    private TextView mWaitaddclassconut;
    private TextView mNoticecount;


    @Override
    protected View initinitLayout(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fr_mine, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize();
        /*获取网络数据*/
        mineHttp();
        initData();

    }

    private void mineHttp() {
        httpData();
    }

    @Override
    public void httpData() {
        super.httpData();
        RequestUtill.getInstance().httpMine(getActivity(), callMine);
    }

    ResultCallback callMine = new ResultCallback() {


        @Override
        public void onError(Request request, Exception e) {
            dismissfxDialog();

        }

        @Override
        public void onResponse(String response) {
            dismissfxDialog();
            HeadJson json = new HeadJson(response);
            if (json.getstatus() == 0) {


            } else {
                ToastUtil.showToast(getContext(), json.getMsg());
            }

        }

    };

    private void initData() {

        tv_userName.setText(UserController.getInstance().getUser().getNickname());
        tv_campusName.setText("北京");

        GlideUtil.showRoundImage(getActivity(), UserController.getInstance().getUser().getAvatar(), imUser, R.drawable.ico_default_user, true);

    }


    @Override
    public void onResume() {
        super.onResume();
    }

    private View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_edit:
                case R.id.topView:
                    //设置
                    DjhJumpUtil.getInstance().startBaseActivityForResult(getActivity(), UserDetailsActivity.class, null, PICFPRRESULT);
                    break;
                case R.id.iv_user:

//                    Toast.makeText(activity, "头像", Toast.LENGTH_SHORT).show();

                    break;

                case R.id.tvMyclass:

//                    Toast.makeText(activity, "我的班级", Toast.LENGTH_SHORT).show();
                    DjhJumpUtil.getInstance().startBaseActivity(getContext(), MyClassActivity.class);
                    break;
                case R.id.tvWaitaddclass:
//                    Toast.makeText(activity, "等待加入班级", Toast.LENGTH_SHORT).show();
                    DjhJumpUtil.getInstance().startBaseActivity(getContext(), WaiteAddClassActivity.class);
                    break;
                case R.id.tvNotice:
//                    Toast.makeText(activity, "通知", Toast.LENGTH_SHORT).show();

                    DjhJumpUtil.getInstance().startBaseActivity(getContext(), NoticeActivity.class);

                    break;
                case R.id.tvAbout:
                    DjhJumpUtil.getInstance().startBaseActivity(getContext(), AboutActivity.class);
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
                if (json.getstatus() == 0) {
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
    private void initialize() {
        imUser = getView(R.id.iv_user);
        imSet = getView(R.id.iv_edit);
        imUser.setOnClickListener(onClick);
        imSet.setOnClickListener(onClick);
        getView(R.id.topView).setOnClickListener(onClick);
        imUser = getView(R.id.iv_user);
        tv_userName = getView(R.id.tv_user_name);
        tv_campusName = getView(R.id.tv_campus_name);
        getView(R.id.tvMyclass).setOnClickListener(onClick);
        getView(R.id.tvWaitaddclass).setOnClickListener(onClick);
        mWaitaddclassconut = getView(R.id.tv_waitaddclassconut);
        getView(R.id.tvNotice).setOnClickListener(onClick);
        mNoticecount = getView(R.id.tv_noticecount);
        getView(R.id.tvAbout).setOnClickListener(onClick);
        getView(R.id.tvSetting).setOnClickListener(onClick);
    }
}
