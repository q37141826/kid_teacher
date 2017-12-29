package cn.dajiahui.kidteacher.http.file;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.fxtx.framework.http.OkHttpClientManager;
import com.fxtx.framework.http.callback.ResultCallback;
import com.fxtx.framework.json.HeadJson;
import com.fxtx.framework.widgets.dialog.FxDialog;
import com.squareup.okhttp.Request;

import java.io.File;
import java.util.ArrayList;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.controller.UserController;
import cn.dajiahui.kidteacher.http.RequestUtill;

/**
 * Created by z on 2016/3/17.
 * 图片上传
 */
public class AlbumFileUpdate {
    private ArrayList<String> filePath;
    private ArrayList<String> successful = new ArrayList<>();//上传成功的图片
    private Context context;
    private String albumId;
    private int updateIndex;//上传数量
    private OnFileUpdate onFileUpdate;
    private String userId;
    private FxDialog dialog;
    private boolean isCancel;//是否取消
    private ProgressBar progressBar;
    private int error;


    public AlbumFileUpdate(ArrayList<String> filePath, Context context, String albumId, OnFileUpdate onFileUpdate) {
        this.filePath = filePath;
        this.context = context;
        this.albumId = albumId;
        this.onFileUpdate = onFileUpdate;
        userId = UserController.getInstance().getUserId();
        dialog = new FxDialog(context) {
            @Override
            public void onRightBtn(int flag) {
                //确定

            }

            @Override
            public void onLeftBtn(int flag) {
                //上传时点击取消
                isCancel = true;
                OkHttpClientManager.getInstance().cancelTag(AlbumFileUpdate.this.context);
            }

            @Override
            public boolean isOnClickDismiss() {
                return false;
            }
        };
        dialog.setCancelable(false);
        View contextView = LayoutInflater.from(context).inflate(
                com.fxtx.framework.R.layout.dialog_update, null);
        progressBar = (ProgressBar) contextView.findViewById(com.fxtx.framework.R.id.dialog_pb);
        progressBar.setMax(100);
        dialog.addView(contextView);
        dialog.setRightBtnHide(View.GONE);
        dialog.setCanceledOnTouchOutside(false);
    }


    //开始上传
    public void startUpdate() {
        httpImgUpdate(filePath.get(updateIndex), userId, albumId);
        dialog.setTitle("图片上传");
        dialog.setMessage(context.getString(R.string.file_img_update, filePath.size(), successful.size(), error));
        progressBar.setVisibility(View.VISIBLE);
        dialog.setFloag(0);
        dialog.show();
    }

    public void httpImgUpdate(final String path, String userId, String albumId) {
        progressBar.setProgress(0);
        RequestUtill.getInstance().uploadImageFile(context, new ResultCallback() {
            @Override
            public void onError(Request request, Exception e) {
                setUpdateIndex();
                error++;
            }

            @Override
            public void onResponse(String response) {
                HeadJson json = new HeadJson(response);
                if (json.getFlag() == 1) {
                    //成功
                    successful.add(path);
                } else {
                    error++;
                }
                setUpdateIndex();
            }

            @Override
            public void inProgress(float progress) {
                super.inProgress(progress);
                progressBar.setProgress((int) (progress * 100.f));

            }
        }, new File(path), albumId, userId);
    }

    private void setUpdateIndex() {
        if (isCancel) {
            updateIndex = filePath.size();
        } else {
            updateIndex++;
        }
        if (updateIndex == filePath.size()) {
            //上传结束 清除已经失败的图片
            filePath.removeAll(successful);
            onFileUpdate.successful();
            dialog.dismiss();
        } else {
            httpImgUpdate(filePath.get(updateIndex), userId, albumId);
        }
        dialog.setMessage(context.getString(R.string.file_img_update, filePath.size(), successful.size(), error));
    }


}
