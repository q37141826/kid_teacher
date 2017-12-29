package cn.dajiahui.kidteacher.ui.mine.personalinformation;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.fxtx.framework.ui.FxActivity;
import com.fxtx.framework.ui.FxFragment;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.controller.Constant;

/**
 * Created by z on 2016/3/28.
 * 修改信息接口
 */
public class UserSetActivity extends FxActivity {
    private FxFragment fragment;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_user_set);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int type = getIntent().getIntExtra(Constant.bundle_type, Constant.user_edit_user);
        //修改用户信息
        switch (type) {

            case Constant.user_edit_age:
                setfxTtitle(R.string.edit_age);
                fragment = new FrSetAge();
                break;
            case Constant.user_edit_name:
                setfxTtitle(R.string.edit_name);
                fragment = new FrSetName();
                break;
            case Constant.user_edit_sex:
                setfxTtitle("设置性别");
                fragment = new FrSetSex();
                break;
            default:
                break;
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(com.fxtx.framework.R.id.tab_fragment, fragment).commit();
        onBackText();
        onRightBtn(R.drawable.ico_updata, R.string.tv_submit);
    }

    @Override
    public void onRightBtnClick(View view) {
        super.onRightBtnClick(view);
        fragment.httpData();
    }

}
