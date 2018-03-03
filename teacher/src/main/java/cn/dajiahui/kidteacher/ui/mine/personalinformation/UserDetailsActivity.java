package cn.dajiahui.kidteacher.ui.mine.personalinformation;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.fxtx.framework.http.ErrorCode;
import com.fxtx.framework.http.callback.ResultCallback;
import com.fxtx.framework.image.util.GlideUtil;
import com.fxtx.framework.image.util.ImageUtil;
import com.fxtx.framework.json.HeadJson;
import com.fxtx.framework.log.ToastUtil;
import com.fxtx.framework.ui.FxActivity;
import com.fxtx.framework.util.BaseUtil;
import com.fxtx.framework.widgets.dialog.BottomDialog;
import com.squareup.okhttp.Request;

import java.io.File;
import java.util.ArrayList;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.controller.Constant;
import cn.dajiahui.kidteacher.controller.UserController;
import cn.dajiahui.kidteacher.http.RequestUtill;
import cn.dajiahui.kidteacher.ui.chat.constant.PreferenceManager;
import cn.dajiahui.kidteacher.ui.homework.view.CustomDatePickerFixbirthday;
import cn.dajiahui.kidteacher.ui.login.bean.BeUser;
import cn.dajiahui.kidteacher.ui.mine.bean.BeShowUserProfileInfo;
import cn.dajiahui.kidteacher.ui.mine.bean.BeUpUserIcon;
import cn.dajiahui.kidteacher.util.DjhJumpUtil;
import cn.dajiahui.kidteacher.util.Logger;

/*
 个人信息
* */
public class UserDetailsActivity extends FxActivity {
    public static final int PICSETSULT = 55;
    private ImageView userIcon;
    private TextView tvSchool, tvSex, tvBirthay;
    private EditText ed_name;
    private BottomDialog dialog;
    private CustomDatePickerFixbirthday customDatePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setfxTtitle(R.string.user_message);
        onBackText();
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_user_details);
        getView(R.id.re_user_icon).setOnClickListener(onclick);
        getView(R.id.re_user_name).setOnClickListener(onclick);
        getView(R.id.tv_user_birthday_right).setOnClickListener(onclick);
        getView(R.id.re_user_sex).setOnClickListener(onclick);

        // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker = new CustomDatePickerFixbirthday(this, new CustomDatePickerFixbirthday.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                tvBirthay.setText(time);
                /*修改生日*/
                changeUserIcon(3, time);
            }
        }, "1970-01-01 00:00", "2200-12-31 00:00");
        customDatePicker.showSpecificTime(false); // 显示时和分
        customDatePicker.setIsLoop(false); // 不允许循环滚动

        userIcon = getView(R.id.img_user_icon);
        ed_name = getView(R.id.tv_user_name_right);
        tvSchool = getView(R.id.tv_user_school);
        tvBirthay = getView(R.id.tv_user_birthday_right);
        tvSex = getView(R.id.tv_user_sex_right);

      /*监听姓名输入框*/
        ed_name.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    /*修改姓名*/
                    changeUserIcon(1, ed_name.getText().toString());
                    /*隐藏软键盘*/
                    BaseUtil.hideSoftInput(UserDetailsActivity.this);
                    return true;
                }
                return false;
            }
        });
           /*展示用户信息网络请求*/
        showUserProfileInfo();
        dialog = new BottomDialog(context, R.layout.dialog_choosepic) {
            @Override
            public void initView() {
                rootView.findViewById(R.id.btn_cancle).setOnClickListener(onClick);
                rootView.findViewById(R.id.btn_takephoto).setOnClickListener(onClick);
                rootView.findViewById(R.id.btn_choose).setOnClickListener(onClick);
            }
        };

    }

    /*展示用户信息*/
    private void showUserProfileInfo() {
        RequestUtill.getInstance().httpShowUserProfileInfo(context, new ResultCallback() {
            @Override
            public void onError(Request request, Exception e) {
                Logger.d("展示用户信息失败：" + e);
                ToastUtil.showToast(context, ErrorCode.error(e));
            }

            @Override
            public void onResponse(String response) {
                HeadJson headJson = new HeadJson(response);
                if (headJson.getstatus() == 0) {
                    BeShowUserProfileInfo beShowUserProfileInfo = headJson.parsingObject(BeShowUserProfileInfo.class);
                    if (beShowUserProfileInfo != null) {
                        initData(beShowUserProfileInfo);
                    }
                }
            }
        });
    }

    /*展示用户信息*/
    private void initData(BeShowUserProfileInfo profileInfo) {
        GlideUtil.showRoundImage(UserDetailsActivity.this, profileInfo.getAvatar(), userIcon, R.drawable.ico_default_user, true);
        ed_name.setText(profileInfo.getNickname());
        tvBirthay.setText(profileInfo.getBirthday());//TimeUtil.timeFormat(user.getBirthday(), TimeUtil.yyMD)
        BeUser user = UserController.getInstance().getUser();
        String gender = profileInfo.getGender();
        if (gender.equals("1")) {
            tvSex.setText("女");
        } else {
            tvSex.setText("男");
        }
    }


    private View.OnClickListener onclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.re_user_icon:
                    //点击用户头像行
                    if (dialog != null)
                        dialog.show();
                    break;

                case R.id.re_user_name:
                    //点击用户姓名行
//                    DjhJumpUtil.getInstance().startUserSetActivity(context, Constant.user_edit_name);
                    break;


                case R.id.re_user_sex:
                    BaseUtil.hideSoftInput(UserDetailsActivity.this);
                    final FixSexDialog fixSexDialog = new FixSexDialog(context);
                    fixSexDialog.show();
                    /*选择男*/
                    fixSexDialog.Boy().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            tvSex.setText("男");
                            changeUserIcon(2, "0");
                            fixSexDialog.dismiss();

                        }
                    });
                       /*选择女*/
                    fixSexDialog.Girl().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            tvSex.setText("女");
                            changeUserIcon(2, "1");
                            fixSexDialog.dismiss();
                        }
                    });
                    //点击用户性别
//                    DjhJumpUtil.getInstance().startUserSetActivity(context, Constant.user_edit_sex);
                    break;
                case R.id.tv_user_birthday_right:
                    BaseUtil.hideSoftInput(UserDetailsActivity.this);
                       /*设置默认显示时间*/
                    customDatePicker.show("1970-01-01 00:00");
                    //点击用户性别
//                    DjhJumpUtil.getInstance().startUserSetActivity(context, Constant.user_edit_age);
                    break;

                default:
                    break;
            }
        }
    };
    public static int TAKE_CAMERA_PICTURE = 1000;
    private String path;
    private String imagename;
    private View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dialog.dismiss();
            switch (v.getId()) {
                case R.id.btn_cancle:
                    //取消
                    if (dialog != null && dialog.isShowing())
                        dialog.dismiss();
                    break;
                case R.id.btn_takephoto:
                    //拍照
                    path = UserController.getInstance().getUserImageAnswer(UserDetailsActivity.this);//拍照存放路径
                    imagename = getImgName();
                    ImageUtil.takePhone(UserDetailsActivity.this, path, imagename, TAKE_CAMERA_PICTURE);
                    break;
                case R.id.btn_choose:
                    //相册
                    DjhJumpUtil.getInstance().startSelectPhotoActivity(context, Constant.Alum_phone_UserIcon);
                    break;

                case R.id.re_user_sex:
                    BaseUtil.hideSoftInput(UserDetailsActivity.this);
                    final FixSexDialog fixSexDialog = new FixSexDialog(context);
                    fixSexDialog.show();
                    /*选择男*/
                    fixSexDialog.Boy().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            tvSex.setText("男");
                            changeUserIcon(2, "0");
                            fixSexDialog.dismiss();

                        }
                    });
                       /*选择女*/
                    fixSexDialog.Girl().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            tvSex.setText("女");
                            changeUserIcon(2, "1");
                            fixSexDialog.dismiss();
                        }
                    });

//                    DjhJumpUtil.getInstance().startUserSetActivity(context, Constant.user_edit_sex);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            //用户信息修改
            if (requestCode == DjhJumpUtil.getInstance().activtiy_SelectPhoto) {
                //上传选择的照片
                ArrayList<String> strings = data.getStringArrayListExtra(Constant.bundle_obj);
                File file = new File(strings.get(0));
                httpUserIcon(file);
            }
            if (requestCode == TAKE_CAMERA_PICTURE) {
                Bitmap map = ImageUtil.uriToBitmap(Uri.fromFile(new File(path + imagename)), context);
                int degree = ImageUtil.readPictureDegree(path + imagename);
                if (degree != 0) {
                    map = ImageUtil.rotaingImageView(degree, map);
                }
                ImageUtil.bitmapToFile(map, path + imagename, 4096);
                File file = new File(path + imagename);
                httpUserIcon(file);
            }
        }
    }

    private String getImgName() {
        String imageName = "";
        imageName = System.currentTimeMillis() + ".jpg";
        return imageName;
    }

    /*修改头像*/
    public void httpUserIcon(File file) {
        showfxDialog(R.string.submiting);
        RequestUtill.getInstance().uploadUserIcon(context, new ResultCallback() {
            @Override
            public void onError(Request request, Exception e) {
                Logger.d("头像上传失败！");
                dialog.dismiss();
                dismissfxDialog();
                ToastUtil.showToast(context, ErrorCode.error(e));
            }

            @Override
            public void onResponse(String response) {
                Logger.d("头像上传成功！");
                dismissfxDialog();
                dialog.dismiss();
                HeadJson headJson = new HeadJson(response);
                if (headJson.getstatus() == 0) {
                    UserController.getInstance().getUser().setAvatar(headJson.parsingObject(BeUpUserIcon.class).getUrl());
                    PreferenceManager.getInstance().setCurrentUserAvatar(UserController.getInstance().getUser().getAvatar());
                    changeUserIcon(0, headJson.parsingObject(BeUpUserIcon.class).getUrl());
                    GlideUtil.showRoundImage(UserDetailsActivity.this, UserController.getInstance().getUser().getAvatar(), userIcon, R.drawable.ico_default_user, true);
                    ToastUtil.showToast(context, R.string.save_ok);
                    setResult(PICSETSULT);
                } else {
                    ToastUtil.showToast(context, headJson.getMsg());
                }
            }
        }, file);
    }

    private void changeUserIcon(int type, String info) {
        RequestUtill.getInstance().httpChangeUserInfo(context, new ResultCallback() {
            @Override
            public void onError(Request request, Exception e) {
                Logger.d("修改失败：" + e);
                ToastUtil.showToast(context, ErrorCode.error(e));
            }

            @Override
            public void onResponse(String response) {
                Logger.d("response:" + response);
                HeadJson headJson = new HeadJson(response);


            }
        }, type, info);
    }
}
