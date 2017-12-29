package cn.dajiahui.kidteacher.ui.login;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.fxtx.framework.log.ToastUtil;
import com.fxtx.framework.text.MaxLenghtWatcher;
import com.fxtx.framework.text.StringUtil;
import com.fxtx.framework.ui.FxActivity;
import com.fxtx.framework.util.ActivityUtil;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.controller.AppSet;
import cn.dajiahui.kidteacher.http.LoginHttp;
import cn.dajiahui.kidteacher.util.DjhJumpUtil;
import cn.dajiahui.kidteacher.util.SpUtil;

/**
 * Created by z on 2016/3/17.
 */
public class LoginActivity extends FxActivity {
    private EditText euser, edPwd;
    private TextView mTitle;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_login);
        mTitle = getView(R.id.tool_title);
        mTitle.setText(R.string.account_login);
        euser = getView(R.id.edUser);
        euser.addTextChangedListener(new MaxLenghtWatcher(AppSet.login_maxlenght, euser, context));
        edPwd = getView(R.id.edPwd);
        edPwd.addTextChangedListener(new MaxLenghtWatcher(AppSet.login_maxlenght, edPwd, context));

        getView(R.id.tv_forget).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DjhJumpUtil.getInstance().startBaseActivity(context, ForgetPwdActivity.class);
            }
        });
        getView(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                httpData();
            }
        });
        SpUtil util = new SpUtil(this);
        euser.setText(util.getKeyLogU());
        euser.setSelection(euser.getText().length()); // 设置光标在文本末尾
    }

    @Override
    public void httpData() {
        super.httpData();
        String user = euser.getText().toString().trim();
        String pwd = edPwd.getText().toString().trim();
        if (StringUtil.isEmpty(user)) {
            ToastUtil.showToast(context, R.string.input_user_phone);
            return;
        }

//        if (TeacherUtil.isMobileNO(user)) {
//            ToastUtil.showToast(context, R.string.check_user_phone);
//            return;
//        }


        if (StringUtil.isEmpty(pwd)) {
            ToastUtil.showToast(context, R.string.input_user_pwd);
            return;
        }
        showfxDialog(R.string.login);
        new LoginHttp(new LoginHttp.OnLogin() {
            @Override
            public void error() {
                dismissfxDialog();
            }

            @Override
            public void successful() {
                dismissfxDialog();
                finishActivity();
            }
        }, LoginActivity.this).httpData(user, pwd);
    }

    @Override
    public void onBackPressed() {
        ActivityUtil.getInstance().finishAllActivity();
    }
}
