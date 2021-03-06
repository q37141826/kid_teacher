package cn.dajiahui.kidteacher.ui.mine.personalinformation;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.fxtx.framework.http.ErrorCode;
import com.fxtx.framework.http.callback.ResultCallback;
import com.fxtx.framework.json.HeadJson;
import com.fxtx.framework.log.ToastUtil;
import com.fxtx.framework.ui.FxFragment;
import com.fxtx.framework.widgets.dialog.FxDialog;
import com.squareup.okhttp.Request;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.controller.UserController;
import cn.dajiahui.kidteacher.http.RequestUtill;

/**
 * 修改性别
 */
public class FrSetSex extends FxFragment {
    private RadioGroup radio_sex;
    private RadioButton radio_boy, radio_girl;

    @Override
    protected View initinitLayout(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fr_set_sex, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        radio_sex = getView(R.id.radio_sex);
        radio_boy = getView(R.id.sex_boy);
        radio_girl = getView(R.id.sex_girl);
        if ("M".equals(UserController.getInstance().getUser().getGender())) {
            radio_boy.setChecked(true);
            radio_girl.setChecked(false);
            radio_boy.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ico_im_ok, 0, 0, 0);
            radio_girl.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ico_sex_unchecked, 0, 0, 0);
        } else {
            radio_girl.setChecked(true);
            radio_boy.setChecked(false);
            radio_girl.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ico_im_ok, 0, 0, 0);
            radio_boy.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ico_sex_unchecked, 0, 0, 0);
        }
        radio_sex.setOnCheckedChangeListener(oncheckchanged);
    }

    private RadioGroup.OnCheckedChangeListener oncheckchanged = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.sex_boy:
                    radio_boy.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ico_im_ok, 0, 0, 0);
                    radio_girl.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ico_sex_unchecked, 0, 0, 0);
                    break;
                case R.id.sex_girl:
                    radio_girl.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ico_im_ok, 0, 0, 0);
                    radio_boy.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ico_sex_unchecked, 0, 0, 0);
                    break;
            }
        }
    };

    @Override
    public void httpData() {
        if (UserController.getInstance().getUser().getGender().equals(radio_boy.isChecked() ? "M" : "W")) {
            ToastUtil.showToast(getContext(), "请求错误");
            return;
        }
        FxDialog dialg = new FxDialog(getContext()) {
            @Override
            public void onRightBtn(int flag) {
                httpSex(radio_boy.isChecked() ? "M" : "W");
            }

            @Override
            public void onLeftBtn(int flag) {

            }
        };
        dialg.setTitle(R.string.prompt);
        dialg.setMessage("是否要修改性别？");
        dialg.show();

    }

    private void httpSex(final String sex) {
        showfxDialog();
        ResultCallback callback = new ResultCallback() {
            @Override
            public void onError(Request request, Exception e) {
                dismissfxDialog();
                ToastUtil.showToast(getContext(), ErrorCode.error(e));
            }

            @Override
            public void onResponse(String response) {
                dismissfxDialog();
                HeadJson json = new HeadJson(response);
                if (json.getstatus() == 0) {
                    UserController.getInstance().getUser().setGender(sex);
                    getActivity().setResult(Activity.RESULT_OK);
                    finishActivity();
                    ToastUtil.showToast(getContext(), R.string.save_ok);
                } else {
                    ToastUtil.showToast(getContext(), json.getMsg());
                }
            }
        };
        RequestUtill.getInstance().httpUserSex(getContext(), callback, UserController.getInstance().getUserId(), sex);
    }
}