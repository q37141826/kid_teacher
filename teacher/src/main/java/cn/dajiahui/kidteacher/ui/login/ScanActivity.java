package cn.dajiahui.kidteacher.ui.login;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.TextView;

import com.fxtx.framework.text.StringUtil;
import com.fxtx.framework.ui.FxActivity;
import com.fxtx.framework.ui.FxFragment;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.controller.Constant;
import cn.dajiahui.kidteacher.ui.login.fr.FrAutoLogin;
import cn.dajiahui.kidteacher.ui.login.fr.FrSocket;

/**
 * Created by z on 2016/4/27.
 */
public class ScanActivity extends FxActivity {
    private TextView tvNull;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_scan);
        tvNull = getView(R.id.tv_null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String scan = getIntent().getStringExtra(Constant.bundle_obj);
        onBackText();
        setfxTtitle(R.string.scan_valuer);
        if (StringUtil.isEmpty(scan)) {
            tvNull.setText("二维码编码不正确");
            tvNull.setVisibility(TextView.VISIBLE);
        } else if (scan.startsWith("zmr")) {
            //socket登录
            setScanFragment(new FrSocket());
        } else if(scan.startsWith("PCT")){
            //普通登录
            setScanFragment(new FrAutoLogin());
        }else{
            tvNull.setText("二维码编码不正确");
            tvNull.setVisibility(TextView.VISIBLE);
        }
    }

    private void setScanFragment(FxFragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        fragment.setArguments(getIntent().getExtras());
        ft.replace(R.id.frScan, fragment);
        ft.commit();
    }
}
