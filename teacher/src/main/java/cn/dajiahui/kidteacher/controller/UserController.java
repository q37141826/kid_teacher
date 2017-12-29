package cn.dajiahui.kidteacher.controller;

import android.content.Context;

import com.fxtx.framework.FxtxConstant;
import com.fxtx.framework.file.FileUtil;
import com.fxtx.framework.platforms.jpush.JpushUtil;

import java.io.File;

import cn.dajiahui.kidteacher.DjhTeacherApp;
import cn.dajiahui.kidteacher.ui.login.bean.BeNum;
import cn.dajiahui.kidteacher.ui.login.bean.BeUser;
import cn.dajiahui.kidteacher.ui.mine.bean.BeAccess;
import cn.dajiahui.kidteacher.util.SpUtil;

/**
 * Created by z on 2016/2/25.
 * 用户信息控制器  控制用户基本信息数据
 */
public class UserController {

    private static UserController controller;
    private BeUser user;//用户信息
    private BeNum num = new BeNum();
    private BeAccess access;//用户权限

    private UserController() {
    }

    public void savaUser(BeUser user) {
        this.user = user;
        access = new BeAccess();
        if (user.getAuthList() != null)
            for (String auth : user.getAuthList()) {
                saveAccess(auth);
            }
    }

    public void saveNum(BeNum num) {
        this.num = num;
    }

    public BeNum getNum() {
        if (num == null)
            num = new BeNum();
        return num;
    }

    /**
     * 单一实例
     */
    public static UserController getInstance() {
        if (controller == null) {
            synchronized (UserController.class) {
                if (controller == null) {
                    controller = new UserController();
                }
            }
        }
        return controller;
    }

    //返回用户id
    public String getUserId() {
        return getUser().getObjectId();
    }

    //获取用户的图片存储地址
    public String getUserImageFile(Context context) {
        String path = new FileUtil().dirFile(context) +
                File.separator + "ata" +
                File.separator + getUserId() +
                File.separator + "image" +
                File.separator;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return path;
    }

    /**
     * 获取用户资料的路径
     *
     * @param context
     * @return
     */
    public String getUserMaterial(Context context) {
        String path = new FileUtil().dirFile(context) +
                File.separator + "ata" +
                File.separator + getUserId() +
                File.separator + "material" +
                File.separator;
        File file = new File(path);
        if (!file.exists()) file.mkdirs();
        return path;
    }

    /**
     * 教研附件本地的文件路径
     *
     * @param context
     * @return
     */
    public String getTeachingLocalList(Context context) {
        String path = new FileUtil().dirFile(context) +
                File.separator + "ata" +
                File.separator + getUserId() +
                File.separator + "image" +
                File.separator;
        File file = new File(path);
        if (!file.exists()) file.mkdirs();
        return path + "teachingdata.txt";
    }

    /**
     * 通知文件附件本地的文件路径
     *
     * @param context
     * @return
     */
    public String getNoticeFileLocalList(Context context) {
        String path = new FileUtil().dirFile(context) +
                File.separator + "ata" +
                File.separator + getUserId() +
                File.separator + "image" +
                File.separator;
        File file = new File(path);
        if (!file.exists()) file.mkdirs();
        return path + "noticefile.txt";
    }

    /**
     * 通知音视频附件本地的文件路径
     *
     * @param context
     * @return
     */
    public String getNoticeVideoLocalList(Context context) {
        String path = new FileUtil().dirFile(context) +
                File.separator + "ata" +
                File.separator + getUserId() +
                File.separator + "image" +
                File.separator;
        File file = new File(path);
        if (!file.exists())
            file.mkdirs();
        return path + "noticevideo.txt";
    }

    /**
     * 通知图片频附件本地的文件路径
     *
     * @param context
     * @return
     */
    public String getNoticeImgLocalList(Context context) {
        String path = new FileUtil().dirFile(context) +
                File.separator + "ata" +
                File.separator + getUserId() +
                File.separator + "image" +
                File.separator;
        File file = new File(path);
        if (!file.exists()) file.mkdirs();
        return path + "noticeimage.txt";
    }

    /**
     * 拍照目录
     *
     * @param context
     * @return
     */
    public String getUserImageAnswer(Context context) {
        String path = new FileUtil().dirFile(context) +
                File.separator + FxtxConstant.ImageFile;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return path;
    }

    //用户调用退出登录后的处理
    public void exitLogin(Context context) {
        initBean();
        JpushUtil.stopJpushAlias(context);
        JpushUtil.clearAllNotifications(context);
        SpUtil spUtil = new SpUtil(context);
        spUtil.cleanUser();
    }

    public void initBean() {
        controller = null;
        access = null;
        BadgeController.getInstance().initNum(true);
    }

    public BeUser getUser() {
        if (user == null)
            user = new SpUtil(DjhTeacherApp.getInstance()).getUser();
        return user;
    }

    public BeAccess getAccess() {
        if (access == null) {
            access = new BeAccess();
            if (getUser().getAuthList() != null)
                for (String auth : getUser().getAuthList()) {
                    saveAccess(auth);
                }
        }
        return access;
    }

    public void saveAccess(String string) {
        switch (string) {
            case Constant.ac_class:
                access.isClass = true;
                break;
            case Constant.ac_notice:
                access.isNotice = true;
                break;
            case Constant.ac_msn:
                access.isMsn = true;
                break;
            case Constant.ac_job:
                access.isJob = true;
                break;
            case Constant.ac_attend:
                access.isAttend = true;
                break;
            case Constant.ac_research:
                access.isResearch = true;
                break;
            case Constant.ac_microClass:
                access.isMicroClass = true;
                break;
            case Constant.ac_evaluation:
                access.isEvaluation = true;
                break;
            case Constant.ac_myFile:
                access.isMyFile = true;
                break;
            case Constant.ac_album:
                access.isAlbum = true;
                break;
            case Constant.ac_stuEval:
                access.isStuEval = true;
                break;
            case Constant.ac_stuVerify:
                access.isStuVerify = true;
                break;
            case Constant.ac_urlFavorites:
                access.isUrlFavorites = true;
                break;
            case Constant.ac_Lesson:
                access.isLesson = true;
                break;
        }

    }

}
