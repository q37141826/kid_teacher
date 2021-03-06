package cn.dajiahui.kidteacher.ui.mine.personalinformation;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.fxtx.framework.http.ErrorCode;
import com.fxtx.framework.http.callback.ResultCallback;
import com.fxtx.framework.json.HeadJson;
import com.fxtx.framework.log.ToastUtil;
import com.fxtx.framework.text.StringUtil;
import com.fxtx.framework.ui.FxFragment;
import com.fxtx.framework.widgets.dialog.FxDialog;
import com.squareup.okhttp.Request;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.controller.UserController;
import cn.dajiahui.kidteacher.http.RequestUtill;
import cn.dajiahui.kidteacher.ui.chat.constant.PreferenceManager;

/**
 * 修改名字
 */
public class FrSetName extends FxFragment {
    private EditText tvName;

    @Override
    protected View initinitLayout(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fr_set_name, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvName = getView(R.id.tv_user_name);
        tvName.setText(UserController.getInstance().getUser().getNickname());
        tvName.setSelection(tvName.getText().length());
    }

    @Override
    public void httpData() {
        if (StringUtil.isEmpty(tvName.getText().toString().trim())) {
            ToastUtil.showToast(getContext(), R.string.ed_user_name);
            return;
        }
        if (UserController.getInstance().getUser().getNickname().equals(tvName.getText().toString().trim())) {
            ToastUtil.showToast(getContext(), R.string.nameequel);
            return;
        }
        FxDialog dialg = new FxDialog(getContext()) {
            @Override
            public void onRightBtn(int flag) {
                httpName(tvName.getText().toString().trim());
            }

            @Override
            public void onLeftBtn(int flag) {

            }
        };
        dialg.setTitle(R.string.prompt);
        dialg.setMessage("是否要修改姓名？");
        dialg.show();
    }

    private void httpName(final String realName) {
        showfxDialog(R.string.submiting);
        RequestUtill.getInstance().httpUserMessage(getContext(), new ResultCallback() {
            @Override
            public void onError(Request request, Exception e) {
                dismissfxDialog();
                ToastUtil.showToast(getContext(), ErrorCode.error(e));
            }

            @Override
            public void onResponse(String response) {
                dismissfxDialog();
                HeadJson headJson = new HeadJson(response);
                if (headJson.getstatus() == 0) {
                    ToastUtil.showToast(getContext(), R.string.save_ok);
                    UserController.getInstance().getUser().setNickname(realName);
                    PreferenceManager.getInstance().setCurrentUserNick(realName);
                    getActivity().setResult(Activity.RESULT_OK);
                    finishActivity();
                } else {
                    ToastUtil.showToast(getContext(), headJson.getMsg());
                }
            }
        }, UserController.getInstance().getUserId(), realName, null, null, null);
    }
}
