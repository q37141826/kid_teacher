package cn.dajiahui.kidteacher.ui.file;

import android.content.res.Configuration;
import android.view.View;

import com.fxtx.framework.ui.base.WebActivity;

import cn.dajiahui.kidteacher.util.DjhJumpUtil;

/**
 * Created by z on 2016/9/13.
 * 浏览附件的webView 。就因为要给WebView 添加上横竖屏切换。所以新写的一个类
 */
public class FileWebActivity extends WebActivity {
    /**
     * 屏幕旋转时调用此方法
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //newConfig.orientation获得当前屏幕状态是横向或者竖向
        //Configuration.ORIENTATION_PORTRAIT 表示竖向
        //Configuration.ORIENTATION_LANDSCAPE 表示横屏
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            toolbar.setVisibility(View.VISIBLE);
        }
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            toolbar.setVisibility(View.GONE);
        }
    }
    
    @Override
    public void onRightBtnClick(View view) {
        DjhJumpUtil.getInstance().startRDataLookInfoActivity(context, id);
    }
}
