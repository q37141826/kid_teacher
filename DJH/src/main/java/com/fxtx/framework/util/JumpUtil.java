package com.fxtx.framework.util;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by z on 2016/1/18.
 */
public class JumpUtil {
    public void startBaseActivity(Context context, Class classs) {
        startBaseActivity(context, classs, null, 0);
    }

    public void startBaseActivity(Context context, Class classs,
                                     Bundle extras, int intentflag) {
        Intent intent = new Intent(context, classs);
        if (intentflag != 0)
            intent.setFlags(intentflag);
        if (extras != null) {
            intent.putExtras(extras);
        }
        context.startActivity(intent);

    }

    public void startBaseActivityForResult(Activity activity, Class classs,
                                                Bundle extras, int requestCode) {
        Intent intent = new Intent(activity.getApplicationContext(), classs);
        if (extras != null) {
            intent.putExtras(extras);
        }
        activity.startActivityForResult(intent, requestCode);
    }

    public void fragmentStartBaseActivityForResult(Activity activity, Class classs, Fragment fragment,
                                                   Bundle extras, int requestCode) {
        Intent intent = new Intent(activity.getApplicationContext(), classs);
        if (extras != null) {
            intent.putExtras(extras);
        }
        fragment.startActivityForResult(intent, requestCode);
    }
}
