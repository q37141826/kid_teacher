package com.fxtx.framework.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.fxtx.framework.text.StringUtil;


/**
 * @author djh-zy
 * @version :1
 * @CreateDate 2015年8月3日 上午11:49:47
 * @description :
 */

public class BaseUtil {
    /**
     * 收起键盘
     */
    public static void hideSoftInput(Activity context) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(context.getWindow().getDecorView()
                    .getWindowToken(), 0);
        }
    }
    
    /**
     * 弹出键盘
     *
     * @param editText 输入框
     */
    public static void showSoftInput(EditText editText) {
        editText.requestFocus();
        InputMethodManager manager = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }
    
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
    
    public static float limitValue(float a, float b) {
        float valve = 0;
        final float min = Math.min(a, b);
        final float max = Math.max(a, b);
        valve = valve > min ? valve : min;
        valve = valve < max ? valve : max;
        return valve;
    }
    
    /**
     * 获取软件版本名称
     *
     * @return
     */
    public static String getVersionName(Context context) {
        String versionName = "v 1.0.0";
        
        try {
            versionName = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }
    
    /**
     * 获取软件版本号
     *
     * @return
     */
    public static int getVersionCode(Context context) {
        int versionCode = 0;
        try {
            versionCode = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionCode;
            return versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }
    
    /**
     * 拨打电话
     *
     * @param context
     * @param phoneNum
     */
    public static void callPhone(Context context, String phoneNum) {
        if (!StringUtil.isEmpty(phoneNum)) {
            Uri uri = Uri.parse("tel:" + phoneNum);
            Intent intent = new Intent(Intent.ACTION_DIAL, uri);
            context.startActivity(intent);
        }
    }
    
    /**
     * dp转成像素px
     */
    public static int dpToPx(Context context, float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
    }
    
    
    /**
     * 判断sd卡是否挂载
     */
    public static boolean isSDCardMount() {
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * 获取手机宽度
     *
     * @param context
     * @return
     */
    @SuppressWarnings("deprecation")
    public static int getPhoneWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }
    
    /**
     * 获取屏幕宽度
     */
    public static int getWidthPixels(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }
    
    /**
     * 获取状态栏的高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
    
    public static int getHeightPixels(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }
    
    public static int getAppbarSize(Activity context) {
        int height = 0;
        TypedValue tv = new TypedValue();
        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            height =TypedValue.complexToDimensionPixelOffset(tv.data,context.getResources().getDisplayMetrics());
        }

        return height;
    }
    
    /**
     * 获取手机高度
     *
     * @param context
     */
    @SuppressWarnings("deprecation")
    public static int getPhoneHeight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getHeight();
    }
    
    
    public static void clipboard(Context context, String text) {
//        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
//        cmb.setText(text);
    }
    
}
