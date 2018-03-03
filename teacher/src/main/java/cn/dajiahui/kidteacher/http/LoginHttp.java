package cn.dajiahui.kidteacher.http;

import android.text.TextUtils;

import com.fxtx.framework.http.ErrorCode;
import com.fxtx.framework.http.callback.ResultCallback;
import com.fxtx.framework.json.HeadJson;
import com.fxtx.framework.log.ToastUtil;
import com.fxtx.framework.platforms.jpush.JpushUtil;
import com.fxtx.framework.ui.FxActivity;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.squareup.okhttp.Request;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.controller.UserController;
import cn.dajiahui.kidteacher.ui.MainActivity;
import cn.dajiahui.kidteacher.ui.chat.constant.ImHelper;
import cn.dajiahui.kidteacher.ui.chat.constant.PreferenceManager;
import cn.dajiahui.kidteacher.ui.login.bean.BeNum;
import cn.dajiahui.kidteacher.ui.login.bean.BeUser;
import cn.dajiahui.kidteacher.util.DjhJumpUtil;
import cn.dajiahui.kidteacher.util.Logger;
import cn.dajiahui.kidteacher.util.SpUtil;

/**
 * 登录
 */
public class LoginHttp {
    private OnLogin onLogin;
    private FxActivity context;

    public LoginHttp(OnLogin onLogin, FxActivity context) {
        this.onLogin = onLogin;
        this.context = context;
    }

    public void setStartActivity() {
        DjhJumpUtil.getInstance().startBaseActivity(context, MainActivity.class);
    }

    public void httpData(final String user, final String pwd) {
        RequestUtill.getInstance().httpLogin(context, new ResultCallback() {
            @Override
            public void onError(Request request, Exception e) {
                onLogin.error();
                ToastUtil.showToast(context, ErrorCode.error(e));
                Logger.d("登錄失敗" + e);
            }

            @Override
            public void onResponse(String response) {
                Logger.d("teacher登录成功" + response);
                HeadJson json = new HeadJson(response);
                if (json.getstatus() == 0) {
                    JpushUtil.infoJpush(context);
                    BeUser temp = json.parsingObject(BeUser.class);

                    BeNum num = json.parsingObject("teacherSumInfo", BeNum.class);
                    if (temp == null) {
                        ToastUtil.showToast(context, "用户信息丢失");
                        onLogin.error();
                        return;
                    }
                    Logger.d("teacher登录token" + temp.getToken());
                    UserController.getInstance().savaUser(temp);
                    UserController.getInstance().saveNum(num);
                    UserController.getInstance().getUser().setPwd(pwd);
                    SpUtil spUtil = new SpUtil(context);
                    spUtil.setLogin(user, pwd);
                    spUtil.setUser(temp);

                    if (UserController.getInstance().getAccess().isMsn) {
                        if (ImHelper.getInstance().isLoggedIn()) {
                            setStartActivity();
                            onLogin.successful();
                        } else {
                            huanxLogin(temp.getThird().getEasemob_username(), temp.getThird().getEasemob_passwd());
                        }
                    } else {
                        if (ImHelper.getInstance().isLoggedIn()) {
                            ImHelper.getInstance().logout(true);
                        }
                        setStartActivity();
                        onLogin.successful();
                    }
                    JpushUtil.statJpushAlias(context, temp.getThird().getJpush_alias());

                } else {
                    ToastUtil.showToast(context, json.getMsg());
                    onLogin.error();
                }
            }
        }, user, pwd);
    }


    public void huanxLogin(final String user, final String pwd) {
        if (!EaseCommonUtils.isNetWorkConnected(context)) {
            ToastUtil.showToast(context, R.string.network_isnot_available);
            return;
        }

        if (TextUtils.isEmpty(user) || TextUtils.isEmpty(pwd)) {
            setStartActivity();
            onLogin.successful();
            return;
        }
//        user, pwd,
        EMClient.getInstance().login(user, pwd, new EMCallBack() {

            @Override
            public void onSuccess() {
                // 登陆成功，保存用户名
                ImHelper.getInstance().setCurrentUserName(user);
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                // 更新当前用户的nickname 此方法的作用是在ios离线推送时能够显示用户nick
                String nickName = UserController.getInstance().getUser().getNickname();
                //异步获取当前用户的昵称和头像(从自己服务器获取，demo使用的一个第三方服务)
                PreferenceManager.getInstance().setCurrentUserAvatar(UserController.getInstance().getUser().getAvatar());
                PreferenceManager.getInstance().setCurrentUserNick(nickName);
                PreferenceManager.getInstance().setCurrentUserName(user);
                setStartActivity();
                onLogin.successful();

            }

            @Override
            public void onProgress(int progress, String status) {
            }

            @Override
            public void onError(final int code, final String message) {
                context.runOnUiThread(new Runnable() {
                    public void run() {
                        setStartActivity();
                        onLogin.successful();
                    }
                });
            }
        });
    }

    public interface OnLogin {
        void error();

        void successful();
    }
}
