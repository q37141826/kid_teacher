package cn.dajiahui.kidteacher.ui.mine.about;

import android.os.Bundle;

import java.util.ArrayList;

import cn.dajiahui.kidteacher.controller.Constant;
import cn.dajiahui.kidteacher.ui.mine.bean.BeHelp;

/**
 * Created by z on 2016/2/25.
 */
public class AllFounctionActivity extends AboutActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String title = getIntent().getStringExtra(Constant.bundle_title);
        setfxTtitle(title);
        onBackText();
    }

    @Override
    public void httpData() {
        ArrayList<BeHelp> BeHelp = (ArrayList) getIntent().getSerializableExtra(Constant.bundle_obj);
        if (BeHelp != null)
            helpList.addAll(BeHelp);
        adapter.notifyDataSetChanged();
//        tvNull.setText(R.string.not_data);
    }
}
