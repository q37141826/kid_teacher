package cn.dajiahui.kidteacher.http;

import android.app.Activity;

import com.fxtx.framework.updata.OnUpdateListener;
import com.fxtx.framework.updata.UpdateManager;

/**
 * Created by z on 2016/1/26.
 * 版本更新回调
 */
public class UpdateApp extends UpdateManager {

    public UpdateApp(Activity context, OnUpdateListener onUpdate) {
        super(context, onUpdate);
    }

    @Override
    public void checkUpdateOrNotAuto() {
//        RequestUtill.getInstance().httpUpdateApp(mContext, new ResultCallback() {
//            @Override
//            public void onError(Request request, Exception e) {
//                onUpdate.onUpdateCancel(0);
//            }
//
//            @Override
//            public void onResponse(String response) {
//                HeadJson json = new HeadJson(response);
//                if (json.getstatus() == 0) {
//                    BeUpdate update = json.parsingObject(BeUpdate.class);
//                    if (update.getCodeNumber() > BaseUtil.getVersionCode(mContext)) {
//                        message = update.getContent();
//                        if (StringUtil.sameStr(update.isForceUpdateFlag(), "1")) {
//                            isMustUpdate = true;
//                        } else {
//                            isMustUpdate = false;
//                        }
////                        doUpdate(update.getDownloadUrl());
//                        onUpdate.onUpdateCancel(0);
//                    } else {
//                        onUpdate.onUpdateCancel(0);
//                    }
//                } else {
//                    onUpdate.onUpdateCancel(0);
//                }
//            }
//        });
        onUpdate.onUpdateCancel(0);
    }

    @Override
    public void checkUpdateOrNot() {
//        progressDialog = new ProgressDialog(mContext);
//        progressDialog.setMessage("检测更新");
//        progressDialog.show();
//        RequestUtill.getInstance().httpUpdateApp(mContext, new ResultCallback() {
//            @Override
//            public void onError(Request request, Exception e) {
//                onUpdate.onUpdateError("请求更新失败");
//                progressDialog.dismiss();
//                updateHandler.sendEmptyMessage(DO_NOTHING);
//            }
//
//            @Override
//            public void onResponse(String response) {
//                progressDialog.dismiss();
//                HeadJson json = new HeadJson(response);
//                if (json.getstatus() == 0) {
//                    BeUpdate update = json.parsingObject(BeUpdate.class);
//                    if (update.getCodeNumber() > BaseUtil.getVersionCode(mContext)) {
//                        message = update.getContent();
//                        if (StringUtil.sameStr(update.isForceUpdateFlag(), "1")) {
//                            isMustUpdate = true;
//                        } else {
//                            isMustUpdate = false;
//                        }
////                        doUpdate(update.getDownloadUrl());
//                        onUpdate.onUpdateCancel(0);
//                    } else {
//                        onUpdate.onUpdateCancel(0);
//                        updateHandler.sendEmptyMessage(DO_NOTHING);
//                    }
//                } else {
//                    onUpdate.onUpdateCancel(0);
//                    updateHandler.sendEmptyMessage(DO_NOTHING);
//                }
//            }
//        });
        onUpdate.onUpdateCancel(0);
    }
}