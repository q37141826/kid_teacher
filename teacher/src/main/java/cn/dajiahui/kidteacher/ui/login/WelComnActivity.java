package cn.dajiahui.kidteacher.ui.login;

import com.fxtx.framework.text.StringUtil;
import com.fxtx.framework.ui.base.FxWelcomeAvtivity;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.http.LoginHttp;
import cn.dajiahui.kidteacher.util.DjhJumpUtil;
import cn.dajiahui.kidteacher.util.SpUtil;

/**
 * Created by z on 2016/3/16.
 */
public class WelComnActivity extends FxWelcomeAvtivity {

    @Override
    public void welcomeClick() {
        SpUtil util = new SpUtil(this);
        String u = util.getKeyLogU();
        String p = util.getKeyLogP();
        if (!StringUtil.isEmpty(u) && !StringUtil.isEmpty(p)) {
            showfxDialog();
            new LoginHttp(new LoginHttp.OnLogin() {
                @Override
                public void error() {
                    dismissfxDialog();
                    DjhJumpUtil.getInstance().startBaseActivity(context, LoginActivity.class);
                }

                @Override
                public void successful() {
                    dismissfxDialog();
                }
            }, WelComnActivity.this).httpData(u, p);
        } else {
            DjhJumpUtil.getInstance().startBaseActivity(context, LoginActivity.class);
        }
        finishActivity();
    }

    @Override
    protected Integer[] initWelcome() {
        return new Integer[]{R.drawable.wel1, R.drawable.wel2, R.drawable.wel3,R.drawable.wel4};
    }

    @Override
    public boolean hineIndicator() {
        return false;
    }
}
