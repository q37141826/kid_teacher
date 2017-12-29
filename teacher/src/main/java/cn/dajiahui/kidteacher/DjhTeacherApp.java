package cn.dajiahui.kidteacher;

import android.app.Application;
import android.support.multidex.MultiDex;

import com.fxtx.framework.http.OkHttpClientManager;
import com.fxtx.framework.platforms.umeng.UmengShare;
import com.umeng.socialize.utils.ContextUtil;

import java.util.concurrent.TimeUnit;

import cn.dajiahui.kidteacher.ui.chat.constant.ImHelper;

/**
 * Created by z on 2016/3/11.
 */
public class DjhTeacherApp extends Application {
    private static DjhTeacherApp instance;

    @Override
    public void onCreate() {
        ContextUtil.setContext(this);
        UmengShare.initSharePlat();
        ImHelper.getInstance().init(this);
        OkHttpClientManager.getInstance().getOkHttpClient().setReadTimeout(100000, TimeUnit.MILLISECONDS);//读的时间
        OkHttpClientManager.getInstance().getOkHttpClient().setWriteTimeout(100000, TimeUnit.MILLISECONDS);//写的时间
        OkHttpClientManager.getInstance().getOkHttpClient().setConnectTimeout(100000, TimeUnit.MILLISECONDS); //设置超时时间
        instance = this;
        MultiDex.install(this) ;
        super.onCreate();
    }

    public static DjhTeacherApp getInstance() {
        if (instance == null) {
            synchronized (DjhTeacherApp.class) {
                if (instance == null) {
                    instance = new DjhTeacherApp();
                }
            }
        }
        return instance;
    }
}
