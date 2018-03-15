package cn.dajiahui.kidteacher.ui.login;

import com.fxtx.framework.text.StringUtil;
import com.fxtx.framework.ui.base.FxFirstActivity;

import cn.dajiahui.kidteacher.controller.AppSet;
import cn.dajiahui.kidteacher.http.LoginHttp;
import cn.dajiahui.kidteacher.http.UpdateApp;
import cn.dajiahui.kidteacher.ui.chat.constant.ImHelper;
import cn.dajiahui.kidteacher.util.DjhJumpUtil;
import cn.dajiahui.kidteacher.util.SpUtil;

/**
 * 首次登录
 */
public class FirstActivity extends FxFirstActivity {
    @Override
    protected void handlerUpdate() {

        UpdateApp manager = new UpdateApp(this, onUpdate);
        manager.checkUpdateOrNotAuto();
    }

    @Override
    protected void handlerWelcome() {
        SpUtil util = new SpUtil(this);
        if (util.getWelcomeNum() < AppSet.welcome) {
            util.setWelcomeNum(AppSet.welcome);
//            DjhJumpUtil.getInstance().startBaseActivity(context, WelComnActivity.class);
            /*暂无引导页 直接跳转登录页*/
            DjhJumpUtil.getInstance().startBaseActivity(context, LoginActivity.class);
            finishActivity();
        } else {
            String u = util.getKeyLogU();
            String p = util.getKeyLogP();
            if (!StringUtil.isEmpty(u) && !StringUtil.isEmpty(p)) {
                showfxDialog();
                new LoginHttp(new LoginHttp.OnLogin() {
                    @Override
                    public void error() {
                        dismissfxDialog();
                        if (ImHelper.getInstance().isLoggedIn())
                            ImHelper.getInstance().logout(true);
                        DjhJumpUtil.getInstance().startBaseActivity(context, LoginActivity.class);
                        finishActivity();
                    }

                    @Override
                    public void successful() {
                        dismissfxDialog();
                        finishActivity();
                    }
                }, FirstActivity.this).httpData(u, p);
            } else {
                if (ImHelper.getInstance().isLoggedIn()) {
                    ImHelper.getInstance().logout(true);
                }
                DjhJumpUtil.getInstance().startBaseActivity(context, LoginActivity.class);
                finishActivity();
            }
        }

    }

    @Override
    protected void initView() {
    }
}
