package cn.dajiahui.kidteacher.ui.mine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.fxtx.framework.http.ErrorCode;
import com.fxtx.framework.http.callback.ResultCallback;
import com.fxtx.framework.json.HeadJson;
import com.fxtx.framework.log.ToastUtil;
import com.fxtx.framework.text.MaxLenghtWatcher;
import com.fxtx.framework.text.StringUtil;
import com.fxtx.framework.ui.FxFragment;
import com.fxtx.framework.util.ActivityUtil;
import com.fxtx.framework.widgets.dialog.FxDialog;
import com.hyphenate.EMCallBack;
import com.squareup.okhttp.Request;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.controller.AppSet;
import cn.dajiahui.kidteacher.controller.UserController;
import cn.dajiahui.kidteacher.http.RequestUtill;
import cn.dajiahui.kidteacher.ui.MainActivity;
import cn.dajiahui.kidteacher.ui.chat.constant.ImHelper;
import cn.dajiahui.kidteacher.ui.login.LoginActivity;
import cn.dajiahui.kidteacher.ui.login.bean.BeUser;
import cn.dajiahui.kidteacher.util.DjhJumpUtil;

/**
 * Created by z on 2016/3/28.
 * 修改密码
 */
public class FrSetPwd extends FxFragment {
    private EditText edOldPWd, edNewPwd, edTooPwd;


    @Override
    protected View initinitLayout(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fr_set_pwd, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edOldPWd = getView(R.id.edOldPwd);
        edTooPwd = getView(R.id.edToopwd);
        edNewPwd = getView(R.id.ednewPwd);
        edNewPwd.addTextChangedListener(new MaxLenghtWatcher(AppSet.login_maxlenght, edNewPwd, getContext()));
        edTooPwd.addTextChangedListener(new MaxLenghtWatcher(AppSet.login_maxlenght, edTooPwd, getContext()));

    }

    @Override
    public void httpData() {
        //弹出对话框
        final String oldPwd = edOldPWd.getText().toString().trim();
        if (StringUtil.isEmpty(oldPwd)) {
            ToastUtil.showToast(getContext(), R.string.inputoldpwd);
            return;
        }
        if (StringUtil.hasChinese(oldPwd)) {
            ToastUtil.showToast(getContext(), R.string.no_chinese);
            return;
        }
        if (oldPwd.length() < 6 || oldPwd.length() > 16) {
            ToastUtil.showToast(getContext(), R.string.correct_pwd);
            return;
        }
        final String newPwd = edNewPwd.getText().toString().trim();
        if (StringUtil.isEmpty(newPwd)) {
            ToastUtil.showToast(getContext(), R.string.inputnewpwd);
            return;
        }
        if (newPwd.length() < 6 || newPwd.length() > 16) {
            ToastUtil.showToast(getContext(), R.string.correct_pwd);
            return;
        }
        if (StringUtil.hasChinese(newPwd)) {
            ToastUtil.showToast(getContext(), R.string.no_chinese);
            return;
        }
        final String tooPwd = edTooPwd.getText().toString().trim();
        if (StringUtil.isEmpty(tooPwd)) {
            ToastUtil.showToast(getContext(), R.string.inputpwdto);
            return;
        }
        if (StringUtil.hasChinese(tooPwd)) {
            ToastUtil.showToast(getContext(), R.string.no_chinese);
            return;
        }
        if (!StringUtil.sameStr(newPwd, tooPwd)) {
            ToastUtil.showToast(getContext(), R.string.text_oldnewpwd1);
            return;
        }
        if (!StringUtil.sameStr(UserController.getInstance().getUser().getPwd(), oldPwd)) {
            ToastUtil.showToast(getContext(), R.string.pwderror);
            return;
        }
        FxDialog dialg = new FxDialog(getContext()) {
            @Override
            public void onRightBtn(int flag) {
                httpPwd(oldPwd, newPwd, tooPwd);
            }

            @Override
            public void onLeftBtn(int flag) {

            }
        };
        dialg.setTitle(R.string.prompt);
        dialg.setMessage("是否要修改密码？");
        dialg.show();
        //执行保存操作
    }

    /**
     * 修改mima
     */
    private void httpPwd(final String oldPwd, final String newPwd, String tooPwd) {
        showfxDialog(R.string.submiting);
        BeUser user = UserController.getInstance().getUser();
        RequestUtill.getInstance().httpModifyPwd(getContext(), new ResultCallback() {
            @Override
            public void onError(Request request, Exception e) {
                dismissfxDialog();
                ToastUtil.showToast(getContext(), ErrorCode.error(e));
            }

            @Override
            public void onResponse(String response) {
                dismissfxDialog();
                HeadJson json = new HeadJson(response);
                if (json.getFlag() == 1) {
                    UserController.getInstance().getUser().setPwd(newPwd);
                    ToastUtil.showToast(getContext(), R.string.save_ok);
                    loginOut();
                } else {
                    ToastUtil.showToast(getContext(), json.getMsg());
                }
            }
        }, UserController.getInstance().getUserId(), user.getUserName(), oldPwd, newPwd, tooPwd);
    }

    public void loginOut() {
        showfxDialog(R.string.logout);
        ImHelper.getInstance().logout(true, new EMCallBack() {
            @Override
            public void onSuccess() {
                //退出成功
                dismissfxDialog();
                DjhJumpUtil.getInstance().startBaseActivity(getContext(), LoginActivity.class);
                ActivityUtil.getInstance().finishActivity(MainActivity.class);
                UserController.getInstance().exitLogin(getContext());
                finishActivity();
            }

            @Override
            public void onError(int i, String s) {
                dismissfxDialog();
                ToastUtil.showToast(getContext(), s);
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }
}