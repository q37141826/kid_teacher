package cn.dajiahui.kidteacher.http.file;

import android.content.Context;

import com.fxtx.framework.http.callback.ResultCallback;
import com.squareup.okhttp.Request;

import java.io.File;
import java.util.ArrayList;

import cn.dajiahui.kidteacher.controller.UserController;
import cn.dajiahui.kidteacher.http.RequestUtill;


/**
 * Created by z on 2016/3/17.
 * 附件文档上传
 */
public class AttachmentFileUpdate {
    private ArrayList<File> filePath = new ArrayList<File>();//总数据
//    private ArrayList<BeTeFile> teFiles = new ArrayList<BeTeFile>();//上传成功后的数据
    private Context context;
    private int updateIndex;//上传数量
    private OnAttachmentUpdate onFileUpdate;
    private String userId;
    private boolean isCancel;//是否取消
    private int error;//失败数
    private int successful;//成功数

    public AttachmentFileUpdate(ArrayList<File> filePath, Context context, OnAttachmentUpdate onFileUpdate) {
        this.filePath = filePath;
        this.context = context;
        this.onFileUpdate = onFileUpdate;
        userId = UserController.getInstance().getUserId();
    }

    //开始上传
    public void startUpdate() {
        if (filePath == null || filePath.size() == 0) {
//            onFileUpdate.saveFile(teFiles);
        } else {
            httpAttachmentUpdate(filePath.get(updateIndex), userId);
        }
    }

    public void httpAttachmentUpdate(final File file, String userId) {
        RequestUtill.getInstance().uploadAttachment(context, new ResultCallback() {
            @Override
            public void onError(Request request, Exception e) {
                setUpdateIndex();
                error++;
            }

            @Override
            public void onResponse(String response) {
//                HeadJson json = new HeadJson(response);
//                if (json.getstatus() == 0) {
//                    //成功
//                    successful++;
//                    BeTeFile beTeFile = json.parsingObject(BeTeFile.class);
//                    if (beTeFile != null)
//                        teFiles.add(beTeFile);
//                } else {
//                    error++;
//                }
//                setUpdateIndex();
            }

            @Override
            public void inProgress(float progress) {
                super.inProgress(progress);
            }
        }, file, userId);
    }

    private void setUpdateIndex() {
        if (isCancel) {
            updateIndex = filePath.size();
        } else {
            updateIndex++;
        }
        if (updateIndex == filePath.size()) {
            //上传结束
//            onFileUpdate.saveFile(teFiles);
        } else {
            httpAttachmentUpdate(filePath.get(updateIndex), userId);
        }
    }
}
