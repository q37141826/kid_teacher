package cn.dajiahui.kidteacher.util;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.fxtx.framework.image.SelectPhotoActivity;
import com.fxtx.framework.ui.base.WebActivity;
import com.fxtx.framework.util.JumpUtil;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.dajiahui.kidteacher.controller.Constant;
import cn.dajiahui.kidteacher.ui.album.PhotoActivity;
import cn.dajiahui.kidteacher.ui.album.PhotoDetailsActivity;
import cn.dajiahui.kidteacher.ui.album.PhotoPageActivity;
import cn.dajiahui.kidteacher.ui.album.bean.BePhoto;
import cn.dajiahui.kidteacher.ui.chat.ChatActivity;
import cn.dajiahui.kidteacher.ui.chat.ContactListDetailActivity;
import cn.dajiahui.kidteacher.ui.chat.bean.BeContactUser;
import cn.dajiahui.kidteacher.ui.file.FileFrActivity;
import cn.dajiahui.kidteacher.ui.login.ScanActivity;
import cn.dajiahui.kidteacher.ui.mine.personalinformation.UserSetActivity;


/**
 * @author djh-zy
 * @version :1
 * @CreateDate 2015年8月3日 下午4:49:49
 * @description :跳转工具类
 */
public class DjhJumpUtil extends JumpUtil {
    private static DjhJumpUtil nUtil;
    public final int activity_PhotoPage = 1002;
    public final int activity_auditDetails = 2002;
    public final int activtiy_SelectPhoto = 3001;
    public final int activtiy_DataFr = 4001;
    public final int activtiy_UserSet = 5001;
    public final int activtiy_TeFile = 6001;
    public final int activtiy_TeSelect = 6002;
    public final int activity_notice_class = 7001; //添加班级请求吗
    public final int activity_mp3 = 8001; //录音回调

    public final int activtiy_SendHomework = 1002; // 发布作业
    public final int activtiy_ChooseSupplementary = 1003; //发布作业.教辅选择
    public final int activtiy_ChooseClass = 1004; //发布课本作业（选择班级，截止时间)

    public final int activtiy_ChoiceHomework = 1005;
    public final int activtiy_ChoiceHomeworkResult = 1006;//检查作业结果请求码

    public final int activtiy_myclassDetail = 1007;//我的班级详情请求码
    public final int activtiy_Notice = 1008;//我的通知
    public final int activtiy_SendHomeWorkDetail = 1009; //选择作业发送
    public final int activity_sendhomeworkPreviewrequest = 1010;//发送作业
    public final int activity_sendhomeworkPreviewresultCode = 1011;//发送作业


    public final int activity_todohomework = 7100;
    public final int activity_answerCardComplete = 7200;


    private DjhJumpUtil() {
    }

    /**
     * 单一实例
     */
    public static DjhJumpUtil getInstance() {
        if (nUtil == null) {
            synchronized (DjhJumpUtil.class) {
                if (nUtil == null) {
                    nUtil = new DjhJumpUtil();
                }
            }
        }
        return nUtil;
    }


    /**
     * 访问网页
     *
     * @param context
     * @param title
     * @param Url
     */
    public void startWebActivity(Context context, String title, String Url, Boolean type) {
        startBaseWebActivity(context, title, Url, type, WebActivity.class);
    }

    /**
     * 访问网页
     *
     * @param context
     * @param title
     * @param Url
     */
    public void startBaseWebActivity(Context context, String title, String Url, Boolean type, Class classs) {
        Bundle bundle = new Bundle();
        bundle.putString(Constant.bundle_title, title);
        bundle.putString(Constant.bundle_obj, Url);
        bundle.putBoolean(Constant.bundle_type, type);
        startBaseActivity(context, classs, bundle, 0);
    }


    /**
     * 启动根据班级id 打开的界面
     *
     * @param context
     * @param classId
     * @param type:参考Constant.all
     */
    public void startClassSelectActivity(Context context, String classId, int isMyClass, int type) {
        Bundle bundle = new Bundle();
        bundle.putString(Constant.bundle_id, classId);
        bundle.putInt(Constant.bundle_obj, isMyClass);
        Class classs = null;
        switch (type) {
            case Constant.all_class:
//                classs = ClassDetailsActivity.class;
                break;
            case Constant.all_site:
//                classs = SiteCollectionActivity.class;
                break;
            case Constant.all_opinion:
//                classs = OpStuListActivity.class;
                break;
            case Constant.all_call:
//                classs = CallRollActivity.class;
                break;
        }
        startBaseActivity(context, classs, bundle, 0);
    }


    // 班级码
    public void startClassCodeActivity(Context context, String code, String title) {
        Bundle bundle = new Bundle();
        bundle.putString(Constant.bundle_title, title);
        bundle.putString(Constant.bundle_obj, code);
//        startBaseActivity(context, ClassCodeActivity.class, bundle, 0);
    }

    //点名 还是 评价
    public void startLessonActivity(Context context, int type) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.bundle_type, type);
//        startBaseActivity(context, LessonActivity.class, bundle, 0);
    }

    //我的相册
    public void startPhotoActivity(Context context, String albumId, String title, int isMyClass) {
        Bundle bundle = new Bundle();
        bundle.putString(Constant.bundle_title, title);
        bundle.putString(Constant.bundle_id, albumId);
        bundle.putInt(Constant.bundle_obj, isMyClass);
        startBaseActivity(context, PhotoActivity.class, bundle, 0);
    }

    public void startPhotoPageActivity(Activity activity, ArrayList<BePhoto> lists, int index, boolean isMo) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.bundle_obj, lists);
        bundle.putInt(Constant.bundle_id, index);
        bundle.putBoolean(Constant.bundle_type, isMo);
        startBaseActivityForResult(activity, PhotoPageActivity.class, bundle, activity_PhotoPage);
    }

    public void startPhotoDetails(Context context, String photoId) {
        Bundle bundle = new Bundle();
        bundle.putString(Constant.bundle_id, photoId);
        startBaseActivity(context, PhotoDetailsActivity.class, bundle, 0);
    }

    public void startCourseActivity(Context context, String classId, String title) {
        Bundle bundle = new Bundle();
        bundle.putString(Constant.bundle_id, classId);
        bundle.putString(Constant.bundle_title, title);
//        startBaseActivity(context, CourseActivity.class, bundle, 0);
    }

    public void startAuditDetailsActivity(Activity activity, String studentId) {
        Bundle bundle = new Bundle();
        bundle.putString(Constant.bundle_id, studentId);
//        startBaseActivityForResult(activity, AuditDetailsActivity.class, bundle, activity_auditDetails);
    }

    //启动相册
    public void startSelectPhotoActivity(Activity activity, int photoMax) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.bundle_obj, photoMax);
        startBaseActivityForResult(activity, SelectPhotoActivity.class, bundle, activtiy_SelectPhoto);
    }

    //启动文件选择界面
    public void startSelectMediaActivity(Activity activity, int max, long size, ArrayList<File> mediaFiles, ArrayList<File> imageFiles, ArrayList<File> documentFiles) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.bundle_id, max);
        bundle.putLong(Constant.bundle_type, size);
        bundle.putSerializable(Constant.bundle_media, mediaFiles);
        bundle.putSerializable(Constant.bundle_img, imageFiles);
        bundle.putSerializable(Constant.bundle_document, documentFiles);
        startBaseActivityForResult(activity, FileFrActivity.class, bundle, activtiy_SelectPhoto);
    }

    // 通知详情
    public void startNoticeDetails(Context context, String noticeId) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.bundle_id, noticeId);
//        startBaseActivity(context, NoticeDetailsActivity.class, bundle, 0);
    }


    public void startUserSetActivity(Activity activity, int type) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.bundle_type, type);
        startBaseActivityForResult(activity, UserSetActivity.class, bundle, activtiy_UserSet);
    }


    public void startChatActivity(Context context, String imId, String phone) {
        Bundle bundle = new Bundle();
        bundle.putString("userId", imId);
        bundle.putString("phone", phone);
        startBaseActivity(context, ChatActivity.class, bundle, 0);
    }

    public void startContactListDetailActivity(Context context, List<BeContactUser> data, String title) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.bundle_obj, (Serializable) data);
        bundle.putString(Constant.bundle_title, title);
        startBaseActivity(context, ContactListDetailActivity.class, bundle, 0);

    }


    //扫描结果
    public void startScanActivity(Context activity, String result) {
        Bundle bundle = new Bundle();
        bundle.putString(Constant.bundle_obj, result);
        startBaseActivity(activity, ScanActivity.class, bundle, 0);
    }


    /**
     * 资料查看详情界面
     *
     * @param activity
     * @param id
     */
    public void startRDataLookInfoActivity(Context activity, String id) {
        Bundle bundle = new Bundle();
        bundle.putString(Constant.bundle_id, id);
//        startBaseActivity(activity, DataReadDetailsActivity.class, bundle, 0);
    }
}