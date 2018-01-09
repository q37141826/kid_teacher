package com.fxtx.framework.ui.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.fxtx.framework.R;
import com.fxtx.framework.ui.FxFragment;


/**
 * 欢迎引导页面显示Activity
 *
 * @author Administrator
 */

public class WelcomeFr extends FxFragment {

    @Override
    protected View initinitLayout(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fr_welcome, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((ImageView) rootView).setImageResource(bundle.getInt("id"));
    }
}
