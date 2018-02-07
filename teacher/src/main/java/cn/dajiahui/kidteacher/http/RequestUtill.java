package cn.dajiahui.kidteacher.http;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Pair;

import com.fxtx.framework.http.callback.ResultCallback;
import com.fxtx.framework.http.request.OkHttpDownloadRequest;
import com.fxtx.framework.http.request.OkHttpRequest;
import com.fxtx.framework.image.util.ImageUtil;
import com.fxtx.framework.log.ToastUtil;

import java.io.File;
import java.util.IdentityHashMap;

import cn.dajiahui.kidteacher.BuildConfig;
import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.controller.UserController;


/**
 * Created by z on 2016/1/20.
 * 网络请求处理工具
 */
public class RequestUtill {
    private static RequestUtill util;

    public String getUrl() {
        return BuildConfig.httpUrl;
    }

    public String getFileUrl() {
        return BuildConfig.httpFileUrl;
    }

    /**
     * 单一实例
     */
    public static RequestUtill getInstance() {
        if (util == null) {
            synchronized (RequestUtill.class) {
                if (util == null) {
                    util = new RequestUtill();
                }
            }
        }
        return util;
    }


    /**
     * 获取网络请求
     */
    public OkHttpRequest.Builder getHttpBuilder(Context context, String httpAction) {
        return new OkHttpRequest.Builder().tag(context).url(getUrl() + httpAction);
    }

    public void httpDownFile(Context context, String url, ResultCallback callback, String file, String dir) {
        new OkHttpDownloadRequest.Builder().tag(context).url(url).destFileName(file).destFileDir(dir).download(callback);
    }

    //下载
    public void downImageFile(Context context, String url, String fileName, ResultCallback callback) {
        //文件名称 和文件地址
        httpDownFile(context, url, callback, fileName, UserController.getInstance().getUserImageFile(context));
    }

    //下载资料文件
    public void downMaterialFile(Context context, String url, String fileName, ResultCallback callback) {
        //文件名称 和文件地址
        httpDownFile(context, url, callback, fileName, UserController.getInstance().getUserMaterial(context));
    }

    public void addDownCount(Context context, String id, ResultCallback callback) {
        new OkHttpRequest.Builder().tag(context).url(getUrl() + "material/addCount.json").addParams("materialId", id).get(callback);
    }

    //相册图片上传
    public void uploadImageFile(Context context, ResultCallback callback, File files, String albunmId, String userId) {
        IdentityHashMap params = new IdentityHashMap<>();
        params.put("classAlbumId", albunmId);
        params.put("userId", userId);
        new OkHttpRequest.Builder().tag(context).url(getFileUrl() + "picture/uploadImg.json").files(new Pair<String, File>("file", files)).params(params).upload(callback);
    }

    //通知图片上传
    public void uploadNoticeFile(Context context, ResultCallback callback, File files, String userId) {
        IdentityHashMap params = new IdentityHashMap<>();
        params.put("userId", userId);
        new OkHttpRequest.Builder().tag(context).url(getFileUrl() + "picture/uploadNoticeImg.json").files(new Pair<String, File>("file", files)).params(params).upload(callback);
    }

    //附件上传
    public void uploadAttachment(Context context, ResultCallback callback, File files, String userId) {
        IdentityHashMap params = new IdentityHashMap<>();
        params.put("userId", userId);
        new OkHttpRequest.Builder().tag(context).url(getFileUrl() + "attachment/uploadAttachment.json").files(new Pair<String, File>("file", files)).params(params).upload(callback);
    }

    /**
     * 版本更新
     *
     * @return key:entity
     */
    public void httpUpdateApp(Context context, ResultCallback callback) {
        IdentityHashMap params = new IdentityHashMap<>();
        params.put("versionType", "0");
        params.put("type", "1");
//        getHttpBuilder(context, "version/update.json").params(params).post(callback);
    }


    //忘记密码  提交信息接口
    public void changePwd(Context context, ResultCallback callback, String phone, String code,
                          String toChangePwd, String pwdAgain) {
        IdentityHashMap params = new IdentityHashMap<>();
//        params.put("phone", phone);
//        params.put("code", code);
//        params.put("toChangePwd", toChangePwd);
//        params.put("pwdAgain", pwdAgain);
        getHttpBuilder(context, "login/changePwdCode.json").params(params).post(callback);
    }

    //忘记密码  获取验证码
    public void sendPhoneCode(Context context, ResultCallback callback, String phone, String userName) {
        IdentityHashMap params = new IdentityHashMap<>();
        params.put("phone", phone);
        params.put("userName", userName);
        getHttpBuilder(context, "login/sendChangePwdCode.json").params(params).post(callback);
    }

    //获取作业列表
    public void getHomework(Context context, ResultCallback callback) {


    }


    /**
     * 获取班级相册
     *
     * @param num
     * @param size
     */
    public void httpMyClassAlbumList(Context context, ResultCallback callback, String classId, int num, String size) {
        IdentityHashMap params = new IdentityHashMap<>();
        params.put("classId", classId);
        params.put("pageSize", size);
        params.put("pageNum", num + "");
        getHttpBuilder(context, "classAlbum/getClassAlbumList.json").params(params).post(callback);
    }

    /**
     * 获取班级相册
     */
    public void httpClassAlbumList(Context context, ResultCallback callback, String userId, String loginUserId) {
        IdentityHashMap params = new IdentityHashMap<>();
        params.put("userId", userId);
        params.put("loginUserId", loginUserId);
        getHttpBuilder(context, "classAlbum/getClassAndClassAlbumList.json").params(params).post(callback);
    }


    public void httpPictureDetails(Context context, ResultCallback callback, String userId, String pictureId) {
        IdentityHashMap params = new IdentityHashMap<>();
        params.put("userId", userId);
        params.put("pictureId", pictureId);
        getHttpBuilder(context, "picture/getById.json").params(params).post(callback);
    }

    public void httpsaveAlbunm(Context context, ResultCallback callback, String userId, String classId, String name, String albumId) {
        IdentityHashMap params = new IdentityHashMap<>();
        params.put("userId", userId);
        params.put("classId", classId);
        params.put("name", name);
        params.put("classAlbumId", albumId);
        getHttpBuilder(context, "classAlbum/saveOrUpdate.json").params(params).post(callback);
    }


    public void httpAddCommnet(Context context, ResultCallback callback, String userId, String pictureId, String content, String parentId, String replyUserId) {
        IdentityHashMap params = new IdentityHashMap<>();
        params.put("userId", userId);
        params.put("pictureId", pictureId);
        params.put("content", content);
        params.put("parentId", parentId);
        params.put("replyUserId", replyUserId);
        getHttpBuilder(context, "picture/addComment.json").params(params).post(callback);
    }


    public void httpPictureList(Context context, ResultCallback callback, String albumid, String userId, int num, String size) {
        IdentityHashMap params = new IdentityHashMap<>();
        params.put("classAlbumId", albumid);
        params.put("userId", userId);
        params.put("pageSize", size);
        params.put("pageNum", num + "");
        getHttpBuilder(context, "picture/getPictureList.json").params(params).post(callback);
    }

    public void httpPictureLike(Context context, ResultCallback callback, String urserId, String pictureId, int type) {
        IdentityHashMap params = new IdentityHashMap<>();
        params.put("userId", urserId);
        params.put("pictureId", pictureId);
        params.put("type", type + "");//1赞，0取消
        getHttpBuilder(context, "picture/like.json").params(params).post(callback);
    }

    public void httpPictureDelete(Context context, ResultCallback callback, String pictureId) {
        IdentityHashMap params = new IdentityHashMap<>();
        params.put("pictureId", pictureId);
        getHttpBuilder(context, "picture/delete.json").params(params).post(callback);
    }



    public void httpModifyPwd(Context context, ResultCallback callback, String access_token, String username, String oldPassword, String xinPassword, String confirmPassword) {
        IdentityHashMap params = new IdentityHashMap<>();
        params.put("userId", access_token);
        params.put("username", username);
        params.put("oldPassword", oldPassword);
        params.put("xinPassword", xinPassword);
        params.put("confirmPassword", confirmPassword);
        getHttpBuilder(context, "user/modifyPassword.json").params(params).post(callback);
    }

    /**
     * 使用帮助
     */
    public void httpHelp(Context context, String type, ResultCallback callback) {
        IdentityHashMap params = new IdentityHashMap<>();
        params.put("type", type);
        getHttpBuilder(context, "cms/findCms.json").params(params).post(callback);
    }

    /**
     * 关于我们
     */
    public void httpAboutUs(Context context, ResultCallback callback) {
        getHttpBuilder(context, "index/aboutUs.json").get(callback);
    }


    /**
     * 教师端通讯录
     *
     * @param context
     * @param callback
     * @param userId
     */
    public void httpContactList(Context context, ResultCallback callback, String userId) {
        IdentityHashMap params = new IdentityHashMap<>();
        params.put("userId", userId);
        getHttpBuilder(context, "user/teacherContacts.json").params(params).post(callback);
    }

    public void httpUserMessage(Context context, ResultCallback callback, String userId, String realName, String email, String birthday, String signature) {
        IdentityHashMap params = new IdentityHashMap<>();
        params.put("userId", userId);
        params.put("realName", realName);
        params.put("email", email);
        params.put("birthday", birthday);
        params.put("signature", signature);
        getHttpBuilder(context, "user/updateUserInfo.json").params(params).post(callback);
    }

    /**
     * 上传头像
     *
     * @param context
     * @param callback
     * @param files
     * @param userId
     */
    public void uploadUserIcon(Context context, ResultCallback callback, File files, String userId) {
        Bitmap map = ImageUtil.uriToBitmap(Uri.fromFile(files), context);
        map = ImageUtil.centerSquareScaleBitmap(map, 400);
        File file = ImageUtil.bitmapToFile(map, UserController.getInstance().getUserImageFile(context) + System.currentTimeMillis() + ".jpg", -1);
        if (file == null) {
            ToastUtil.showToast(context, "文件错误无法提交");
            callback.onError(null, null);
        } else {
            IdentityHashMap params = new IdentityHashMap<>();
            params.put("userId", userId);
            new OkHttpRequest.Builder().tag(context).url(getFileUrl() + "user/uploadAvator.json").files(new Pair<String, File>("file", file)).params(params).upload(callback);
        }
    }

       public void httpUserSex(Context context, ResultCallback callback, String userId, String sex) {
        IdentityHashMap params = new IdentityHashMap<>();
        params.put("userId", userId);
        params.put("sex", sex);
        getHttpBuilder(context, "user/updateUserInfo.json").params(params).post(callback);
    }

    /**
     * 扫码登录
     *
     * @param context
     * @param callback
     * @param qrCode
     * @param password
     */
    public void httpAutoLogin(Context context, ResultCallback callback, String qrCode, String userName, String password) {
        IdentityHashMap params = new IdentityHashMap();
        params.put("qrCode", qrCode);
        params.put("userName", userName);
        params.put("password", password);
        getHttpBuilder(context, "login/setLoginInfo.json").params(params).post(callback);
    }


    //获取分享数据
    public void shareMsg(Context context, ResultCallback callback, String pictureId) {
        IdentityHashMap params = new IdentityHashMap<>();
        params.put("pictureId", pictureId);
        getHttpBuilder(context, "picture/shareMsg.json").params(params).post(callback);
    }

    //同步数据
    public void synchronizeData(Context context, ResultCallback callback, String userId) {
        IdentityHashMap params = new IdentityHashMap<>();
        params.put("userId", userId);
        getHttpBuilder(context, "api/synchronizeData/all").params(params).post(callback);
    }


    /**
     * 获取工作台 角标显示
     *
     * @param context
     * @param resultCallback
     * @param userId
     */
    public void httpCountInfo(Context context, ResultCallback resultCallback, String userId) {
        IdentityHashMap params = new IdentityHashMap<>();
        params.put("userId", userId);
        getHttpBuilder(context, "/caseMsg/findListforWTEC.json").params(params).post(resultCallback);
    }


    /*888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888*/

    /**
     * 登录
     */
    public void httpLogin(Context context, ResultCallback callback, String userName, String passowrd) {
        IdentityHashMap params = new IdentityHashMap<>();
        params.put("username", userName);
        params.put("password", passowrd);
        getHttpBuilder(context, "site/login").params(params).post(callback);
    }

    /**
     * 获取手机获取验证码
     * @param context
     * @param callback
     * @param phone
     */
    public void sendPhoneCode(Context context, ResultCallback callback, String phone) {
        IdentityHashMap params = new IdentityHashMap<>();
        params.put("telnum", phone);
        getHttpBuilder(context, "site/send-code").params(params).post(callback);
    }

    /**
     * 获取教师班级列表
     */
    public void httpLessonClass(Context context, ResultCallback callback, int pageSize, int pageNum) {
        IdentityHashMap params = new IdentityHashMap<>();
        params.put("token", UserController.getInstance().getUser().getToken());
        params.put("pageSize", String.valueOf(pageSize));
        params.put("page", String.valueOf(pageNum));
        getHttpBuilder(context, "teacher/classroom/list").params(params).post(callback);
    }

    /**
     * 取得申请入班学生列表
     * @param context
     * @param callback
     */
    public void httpGetApplyStudents(Context context, ResultCallback callback) {
        IdentityHashMap params = new IdentityHashMap<>();
        params.put("token", UserController.getInstance().getUser().getToken());
        getHttpBuilder(context, "teacher/classroom/get-apply-students").params(params).post(callback);
    }

    /**
     * 批准学生入班
     * @param context
     * @param callback
     * @param classId
     * @param userId
     */
    public void httpAcceptApply(Context context, ResultCallback callback, String classId, String userId) {
        IdentityHashMap params = new IdentityHashMap<>();
        params.put("token", UserController.getInstance().getUser().getToken());
        params.put("class_id", classId);
        params.put("user_id", userId);
        getHttpBuilder(context, "teacher/classroom/accept").params(params).post(callback);
    }

    /**
     * 拒绝学生入班
     * @param context
     * @param callback
     * @param classId
     * @param userId
     */
    public void httpDenyApply(Context context, ResultCallback callback, String classId, String userId) {
        IdentityHashMap params = new IdentityHashMap<>();
        params.put("token", UserController.getInstance().getUser().getToken());
        params.put("class_id", classId);
        params.put("user_id", userId);
        getHttpBuilder(context, "teacher/classroom/deny").params(params).post(callback);
    }

    /**
     * 取得班级详情
     * @param context
     * @param callback
     * @param classId
     */
    public void httpGetClassInfo(Context context, ResultCallback callback, String classId) {
        IdentityHashMap params = new IdentityHashMap<>();
        params.put("token", UserController.getInstance().getUser().getToken());
        params.put("class_id", classId);
        getHttpBuilder(context, "teacher/classroom/detail").params(params).post(callback);
    }

    /**
     * 移除学生
     * @param context
     * @param callback
     * @param classId
     * @param userId
     */
    public void httpRemoveStudent(Context context, ResultCallback callback, String classId, String userId) {
        IdentityHashMap params = new IdentityHashMap<>();
        params.put("token", UserController.getInstance().getUser().getToken());
        params.put("class_id", classId);
        params.put("user_id", userId);
        getHttpBuilder(context, "teacher/classroom/remove").params(params).post(callback);
    }

    /**
     * 取得教辅列表
     * @param context
     * @param callback
     * @param pageSize
     * @param pageNum
     */
    public void httpGetWorkBookList(Context context, ResultCallback callback, int pageSize, int pageNum) {
        IdentityHashMap params = new IdentityHashMap<>();
        params.put("token", UserController.getInstance().getUser().getToken());
        params.put("pageSize", String.valueOf(pageSize));
        params.put("page", String.valueOf(pageNum));
        getHttpBuilder(context, "teacher/book/index").params(params).post(callback);
    }

    /**
     * 选择教辅
     * @param context
     * @param callback
     * @param bookId
     */
    public void httpSelectWorkBook(Context context, ResultCallback callback, String bookId) {
        IdentityHashMap params = new IdentityHashMap<>();
        params.put("token", UserController.getInstance().getUser().getToken());
        params.put("book_id", bookId);
        getHttpBuilder(context, "teacher/book/select").params(params).post(callback);
    }

    /**
     * 取得常用教辅
     * @param context
     * @param callback
     */
    public void httpGetWorkBook(Context context, ResultCallback callback) {
        IdentityHashMap params = new IdentityHashMap<>();
        params.put("token", UserController.getInstance().getUser().getToken());
        getHttpBuilder(context, "teacher/book/history").params(params).post(callback);
    }


    /**
     * 取得单元列表
     */
    public void httpGetUnitList(Context context, ResultCallback callback, String bookId) {
        IdentityHashMap params = new IdentityHashMap<>();
        params.put("token", UserController.getInstance().getUser().getToken());
        params.put("book_id", bookId);
        getHttpBuilder(context, "teacher/book/choose").params(params).post(callback);
    }

    /**
     * 发布作业时取得的班级列表
     * @param context
     * @param callback
     * @param bookId
     * @param unitId
     * @param pageSize
     * @param pageNum
     */
    public void httpGetHomeWorkClassList(Context context, ResultCallback callback, String bookId, String unitId, int pageSize, int pageNum) {
        IdentityHashMap params = new IdentityHashMap<>();
        params.put("token", UserController.getInstance().getUser().getToken());
        params.put("book_id", bookId);
        params.put("unit_id", unitId);
        params.put("pageSize", String.valueOf(pageSize));
        params.put("page", String.valueOf(pageNum));
        getHttpBuilder(context, "teacher/homework/next").params(params).post(callback);
    }

    /**
     * 发布作业
     * @param context
     * @param callback
     * @param classId
     * @param bookId
     * @param unitId
     * @param endTime
     */
    public void httpPublishHomework(Context context, ResultCallback callback, String classId, String bookId, String unitId, String endTime) {
        IdentityHashMap params = new IdentityHashMap<>();
        params.put("token", UserController.getInstance().getUser().getToken());
        params.put("class_id", classId);
        params.put("book_id", bookId);
        params.put("unit_id", unitId);
        params.put("end_time", endTime);
        getHttpBuilder(context, "teacher/homework/pub").params(params).post(callback);

    }

    /**
     * 获取作业列表
     * @param context
     * @param callback
     * @param classId
     * @param isChecked
     * @param pageSize
     * @param pageNum
     */
    public void httpGetHomeworkList(Context context, ResultCallback callback, String classId, String isChecked, int pageSize, int pageNum ) {
        IdentityHashMap params = new IdentityHashMap<>();
        params.put("token", UserController.getInstance().getUser().getToken());
        params.put("class_id", classId);
        params.put("is_checked", isChecked);
        params.put("pageSize", String.valueOf(pageSize));
        params.put("page", String.valueOf(pageNum));
        getHttpBuilder(context, "teacher/homework/list").params(params).post(callback);
    }

    /**
     * 获取班级和状态列表（作业（首页）页面）
     * @param context
     * @param callback
     */
    public void httpGetClassAndStatus(Context context, ResultCallback callback) {
        IdentityHashMap params = new IdentityHashMap<>();
        params.put("token", UserController.getInstance().getUser().getToken());
        getHttpBuilder(context, "teacher/homework/basic").params(params).post(callback);
    }

    /**
     * 教师端通讯录
     *
     * @param context
     * @param callback
     */
    public void httpGetContactList(Context context, ResultCallback callback) {
        IdentityHashMap params = new IdentityHashMap<>();
        params.put("token", UserController.getInstance().getUser().getToken());
        getHttpBuilder(context, "teacher/classroom/contact").params(params).post(callback);
    }

    /**
     * 取得作业报告
     * @param context
     * @param callback
     * @param homeworkId
     */
    public void httpHomeworkReport(Context context, ResultCallback callback, String homeworkId) {
        IdentityHashMap params = new IdentityHashMap<>();
        params.put("token", UserController.getInstance().getUser().getToken());
        params.put("homework_id", homeworkId);
        getHttpBuilder(context, "teacher/homework/detail").params(params).post(callback);
    }

    /**
     * 通知后台检查作业
     * @param context
     * @param callback
     * @param homeworkId
     * @param flag 0：数据不从后台取 1：数据从后台取
     */
    public void httpCheckHomework(Context context, ResultCallback callback, String homeworkId, int flag) {
        IdentityHashMap params = new IdentityHashMap<>();
        params.put("token", UserController.getInstance().getUser().getToken());
        params.put("homework_id", homeworkId);
        params.put("flag", String.valueOf(flag));
        getHttpBuilder(context, "teacher/homework/check").params(params).post(callback);
    }

    /**
     * 作业详情
     * @param context
     * @param callback
     * @param homeworkId
     */
    public void httpGetCheckHomeworkDetails(Context context, ResultCallback callback, String homeworkId) {
        IdentityHashMap params = new IdentityHashMap<>();
        params.put("token", UserController.getInstance().getUser().getToken());
        params.put("homework_id", homeworkId);
        getHttpBuilder(context, "teacher/homework/detail-next").params(params).post(callback);
    }


    /**
     * 测试json
     * @param context
     * @param callback
     * @param json
     */
    public void httpTestJson(Context context, ResultCallback callback, String json) {
        IdentityHashMap params = new IdentityHashMap<>();
        params.put("token", UserController.getInstance().getUser().getToken());
        params.put("json", json);
        getHttpBuilder(context, "teacher/setting/test").params(params).post(callback);
    }
}

