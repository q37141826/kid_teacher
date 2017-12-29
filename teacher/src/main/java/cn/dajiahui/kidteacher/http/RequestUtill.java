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


    public final String homeworkUrl = getUrl() + "workAta/myTest?";//作业试卷
    public final String lessonkUrl = getUrl() + "workAta/paper?";//微课试卷连接
    public final String accessoryUrl = getUrl() + "material/fileShow?httpFileUrl=";//附件连接


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
        getHttpBuilder(context, "version/update.json").params(params).post(callback);
    }

    /**
     * 登录
     */
    public void httpLogin(Context context, ResultCallback callback, String userName, String passowrd) {
        IdentityHashMap params = new IdentityHashMap<>();
        params.put("username", userName);
        params.put("password", passowrd);
        params.put("plat", "android");
        getHttpBuilder(context, "login/do.json").params(params).post(callback);
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


    public void getTask(Context context, ResultCallback callback) {


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

    /**
     * 评价列表
     */
    public void httpEvaluatClass(Context context, ResultCallback callback, String userid, int num, String size) {
        IdentityHashMap params = new IdentityHashMap<>();
        params.put("userId", userid);
        params.put("pageSize", size);
        params.put("pageNum", num + "");
        getHttpBuilder(context, "attence/getMyClassWithAttence.json").params(params).post(callback);
    }

    /**
     * 评价列表
     */
    public void httpEvaluatStudent(Context context, ResultCallback callback, String lessonId) {
        IdentityHashMap params = new IdentityHashMap<>();
        params.put("lessonId", lessonId);
        getHttpBuilder(context, "orgClass/normalStudents.json").params(params).post(callback);
    }

    public void httpEvaluatInfo(Context context, ResultCallback callback, String userid, String lessonId) {
        IdentityHashMap params = new IdentityHashMap<>();
        params.put("userId", userid);
        params.put("lessonId", lessonId);
        getHttpBuilder(context, "studentEvaluate/findStudentEvaluate.json").params(params).post(callback);
    }


    //获取通知列表
    public void httpNoticeList(Context context, ResultCallback callback, String userId, int num, String size) {
        IdentityHashMap params = new IdentityHashMap<>();
        params.put("userId", userId);
        params.put("pageSize", size);
        params.put("pageNum", num + "");
        getHttpBuilder(context, "notice/getMyNoticeList.json").params(params).post(callback);
    }

    //通知详情
    public void httpNoticeDetail(Context context, ResultCallback callback, String userId, String noticeId) {
        IdentityHashMap params = new IdentityHashMap<>();
        params.put("userId", userId);
        params.put("noticeId", noticeId);
        getHttpBuilder(context, "notice/findNotice.json").params(params).post(callback);
    }

    //通知评论
    public void httpNoticeComment(Context context, ResultCallback callback, String userId, String noticeId, String content, String parentId, String replyUserId) {
        IdentityHashMap params = new IdentityHashMap<>();
        params.put("userId", userId);
        params.put("noticeId", noticeId);
        params.put("parentId", parentId);
        params.put("content", content);
        params.put("replyUserId", replyUserId);
        getHttpBuilder(context, "notice/addComment.json").params(params).post(callback);
    }

    public void httpNoticeClass(Context context, ResultCallback callback, String userId) {
        IdentityHashMap params = new IdentityHashMap<>();
        params.put("userId", userId);
        getHttpBuilder(context, "orgClass/getMyClassMin.json").params(params).post(callback);
    }

    public void httpAddNotice(Context context, ResultCallback callback, String userId, String classIds, String title, String content, String pictureIds) {
        IdentityHashMap params = new IdentityHashMap<>();
        params.put("userId", userId);
        params.put("classIds", classIds);
        params.put("title", title);
        params.put("content", content);
        params.put("pictureIds", pictureIds);
        getHttpBuilder(context, "notice/addNotices.json").params(params).post(callback);
    }

    // 校园通知已读接口
    public void httpReadNotice(Context context, ResultCallback callback, String userId, String noticeId) {
        IdentityHashMap params = new IdentityHashMap<>();
        params.put("userId", userId);
        params.put("noticeId", noticeId);
        getHttpBuilder(context, "notice/readNotice.json").params(params).post(callback);
    }

//    public void httpMyData(Context context, ResultCallback callback, String userId, BeDataType type, String orderType, String pageSize, int pageNum) {
//        IdentityHashMap params = new IdentityHashMap<>();
//        String str;
//        if (type.getRemark() < 1) {
//            str = "material/myMaterialDir.json";
//        } else {
//            str = "material/myMaterial.json";
//            params.put("codeId", type.getCodeId());
//        }
//        params.put("userId", userId);
//        params.put("materialId", type.getObjectId());
//        params.put("parentId", type.getParentId());
//
//        params.put("orderType", orderType);
//        params.put("remark", type.getRemark() + "");
//        params.put("name", type.search);
//        params.put("materialId", type.getMaterialId());
//
//        params.put("branchId", type.getBranch_id());//学科
//        params.put("byOrgId", type.getByOrgId());//来源
//        params.put("sourceType", type.getSource_type());//类型
//        params.put("gradeIds", type.getGradeId());// 年级Id
//
//        params.put("pageSize", pageSize);
//        params.put("pageNum", pageNum + "");//不能传int变量 只支持String
//        getHttpBuilder(context, str).params(params).post(callback);
//    }

    public void httpCancelShare(Context context, ResultCallback callback, String shareId) {
        IdentityHashMap params = new IdentityHashMap<>();
        params.put("shareId", shareId);
        getHttpBuilder(context, "material/cancelShare.json").params(params).post(callback);
    }

    public void httpCancelShareMe(Context context, ResultCallback callback, String materialId, String userId) {
        IdentityHashMap params = new IdentityHashMap<>();
        params.put("materialId", materialId);
        params.put("userId", userId);
        getHttpBuilder(context, "material/cancelShareMe.json").params(params).post(callback);
    }

    public void httpDeleteData(Context context, ResultCallback callback, String materialId) {
        IdentityHashMap params = new IdentityHashMap<>();
        params.put("materialId", materialId);
        getHttpBuilder(context, "material/deleteMaterial.json").params(params).post(callback);
    }

    public void httpMoveData(Context context, ResultCallback callback, String materialId, String parentId) {
        IdentityHashMap params = new IdentityHashMap<>();
        params.put("materialId", materialId);
        params.put("parentId", parentId);
        getHttpBuilder(context, "material/moveMaterial.json").params(params).post(callback);
    }

    public void httpDataSearch(Context context, ResultCallback callback, String userId, String remark) {
        IdentityHashMap params = new IdentityHashMap<>();
        params.put("userId", userId);
        params.put("remark", remark);
        getHttpBuilder(context, "material/myMaterialSearch.json").params(params).post(callback);
    }

    public void httpDataShare(Context context, ResultCallback callback, String userIds, String materialId) {
        IdentityHashMap params = new IdentityHashMap<>();
        params.put("userIds", userIds);//用户id
        params.put("materialId", materialId);//资料id
        getHttpBuilder(context, "material/share.json").params(params).post(callback);
    }

    public void httpAuditList(Context context, ResultCallback callback, String userid) {
        IdentityHashMap params = new IdentityHashMap<>();
        params.put("userId", userid);
        getHttpBuilder(context, "classApply/getClassApplyList.json").params(params).post(callback);
    }

    public void httpAuditDetails(Context context, ResultCallback callback, String classApplyId) {
        IdentityHashMap params = new IdentityHashMap<>();
        params.put("classApplyId", classApplyId);
        getHttpBuilder(context, "classApply/getClassApply.json").params(params).post(callback);
    }


    public void httpAudit(Context context, ResultCallback callback, String studentId, String type, String auditUserId) {
        IdentityHashMap params = new IdentityHashMap<>();
        params.put("classApplyId", studentId);
        params.put("status", type);//1tongyi 2butongyi
        params.put("auditUserId", auditUserId);
        getHttpBuilder(context, "classApply/auditClassApply.json").params(params).post(callback);
    }

    //获取待办事项
    public void httpCommission(Context context, ResultCallback callback, String userId) {
        IdentityHashMap params = new IdentityHashMap<>();
        params.put("userId", userId);
        getHttpBuilder(context, "caseMsg/findList.json").params(params).post(callback);
    }

    //修改绑定手机  获取验证码
    public void httpSendCode(Context context, ResultCallback callback, String access_token, String username, String telnum, String password) {
        IdentityHashMap params = new IdentityHashMap<>();
        params.put("userId", access_token);
        params.put("username", username);
        params.put("telnum", telnum);
        params.put("password", password);
        getHttpBuilder(context, "user/modifyTelnumSendCode.json").params(params).post(callback);
    }

    public void httpUserSendCode(Context context, ResultCallback callback, String access_token, String telnum) {
        IdentityHashMap params = new IdentityHashMap<>();
        params.put("userId", access_token);
        params.put("telnum", telnum);
        getHttpBuilder(context, "user/sendCode.json").params(params).post(callback);
    }

    public void httpModifyAccount(Context context, ResultCallback callback, String telnum, String xinUsername, String code, String userId, String password) {
        IdentityHashMap params = new IdentityHashMap<>();
        params.put("telnum", telnum);
        params.put("xinUsername", xinUsername);
        params.put("code", code);
        params.put("userId", userId);
        params.put("password", password);
        getHttpBuilder(context, "user/modifyAccount.json").params(params).post(callback);
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

    public void httpboundPhone(Context context, ResultCallback callback, String userId, String telnum, String captcha) {
        IdentityHashMap params = new IdentityHashMap<>();
        params.put("userId", userId);
        params.put("telnum", telnum);
        params.put("captcha", captcha);
        getHttpBuilder(context, "user/boundPhone.json").params(params).post(callback);
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

    /**
     * 获取消息类型
     *
     * @param context
     * @param type     1教师端消息类型 2学生端消息类型
     * @param callback
     */
    public void httpMessageType(Context context, String type, String userId, ResultCallback callback) {
        IdentityHashMap params = new IdentityHashMap<>();
        params.put("type", type);
        params.put("userId", userId);
        getHttpBuilder(context, "caseMsg/getCaseMsgType.json").params(params).post(callback);

    }

    public void httpMessageByType(Context context, String userId, String type, ResultCallback callback, String pageSize, String pageNum) {
        IdentityHashMap params = new IdentityHashMap<>();
        params.put("userId", userId);
        params.put("pageSize", pageSize);
        params.put("pageNum", pageNum);
        params.put("type", type);
        getHttpBuilder(context, "caseMsg/findMsgs.json").params(params).post(callback);
    }


    /**
     * 将个人日志中的未读状态的消息置为已读
     *
     * @param context
     * @param callback
     * @param caseMsgId
     */
    public void httpReadMsg(Context context, ResultCallback callback, String caseMsgId) {
        IdentityHashMap params = new IdentityHashMap<>();
        params.put("caseMsgId", caseMsgId);
        getHttpBuilder(context, "caseMsg/readMsg.json").params(params).post(callback);
    }


    public void httpUserSex(Context context, ResultCallback callback, String userId, String sex) {
        IdentityHashMap params = new IdentityHashMap<>();
        params.put("userId", userId);
        params.put("sex", sex);
        getHttpBuilder(context, "user/updateUserInfo.json").params(params).post(callback);
    }

    /**
     * 获取试卷详情
     *
     * @param context
     * @param callback
     * @param classPaperId
     * @param userId
     */
    public void httppaperStatus(Context context, ResultCallback callback, String classPaperId, String userId) {
        IdentityHashMap params = new IdentityHashMap<>();
        params.put("classPaperId", classPaperId);
        params.put("userId", userId);
        getHttpBuilder(context, "classPaper/getPaperStatus.json").params(params).post(callback);
    }

    /**
     * 获取资料 微课详情
     *
     * @param context
     * @param callback
     * @param materialId
     */
    public void httpDataMessage(Context context, ResultCallback callback, String materialId) {
        IdentityHashMap params = new IdentityHashMap<>();
        params.put("materialId", materialId);
        getHttpBuilder(context, "material/getMaterialInfo.json").params(params).post(callback);
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

    public void getTitle(Context context, ResultCallback callback, String testId) {
        IdentityHashMap params = new IdentityHashMap();
        params.put("myTestId", testId);
        getHttpBuilder(context, "classPaper/getPaperStatusByMyTest.json").params(params).post(callback);
    }

    //添加班级网址收藏
    public void httpAddFavorate(Context context, ResultCallback callback, String userId, String classId, String httpUrl, String httpUrlName) {
        IdentityHashMap params = new IdentityHashMap();
        params.put("httpUrl", httpUrl);
        params.put("userId", userId);
        params.put("classId", classId);
        params.put("httpUrlName", httpUrlName);
        getHttpBuilder(context, "myFavorateHttp/addMyFavorateHttp.json").params(params).post(callback);
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

    // 学生对教师的评价
    public void httpEvaluatTeacherInfo(Context context, ResultCallback callback, String userid, String lessonId, String teachingId) {
        IdentityHashMap params = new IdentityHashMap<>();
        params.put("userId", userid);
        params.put("lessonId", lessonId);
        params.put("teachingId", teachingId);
        getHttpBuilder(context, "teacherEvaluate/findTeacherEvaluate.json").params(params).post(callback);
    }

    //获取学生数 课件数，班级数
    public void httpTeacherSum(Context context, ResultCallback callback, String userId) {
        IdentityHashMap params = new IdentityHashMap<>();
        params.put("userId", userId);
        getHttpBuilder(context, "teacherAta/sum.json").params(params).post(callback);
    }

    //获取测评报告 或 结课报告
    public void httpReportDate(Context context, ResultCallback callback, String userId, int type, int pageNum) {
        IdentityHashMap params = new IdentityHashMap<>();
        String url = "report/paperClass/getList.json";
        if (type == R.drawable.ico_re_session) {
            url = "report/classOver/getList.json";
        }
        params.put("uid", userId);
        params.put("pageNum", pageNum + "");
        getHttpBuilder(context, url).params(params).post(callback);
    }

    public void httpDlPaper(Context context, ResultCallback callback, String paperId, String studentId) {
        IdentityHashMap params = new IdentityHashMap<>();
        params.put("cpid", paperId);
        params.put("studentId", studentId);
        getHttpBuilder(context, "doQuestion/getDlPaper.json").params(params).post(callback);
    }

    public void httpDlPaperSave(Context context, ResultCallback callback, String cpid, String studentId, String scoreParams) {
        IdentityHashMap params = new IdentityHashMap<>();
        params.put("cpid", cpid);
        params.put("studentId", studentId);
        params.put("scoreParams", scoreParams);
        getHttpBuilder(context, "doQuestion/setScore.json").params(params).post(callback);
    }

    /**
     * 微课观看记录
     *
     * @param context
     * @param callback
     * @param microKwngMapId
     */
    public void httpLookinfo(Context context, ResultCallback callback, String microKwngMapId) {
        IdentityHashMap params = new IdentityHashMap<>();
        params.put("microKwngMapId", microKwngMapId);
        getHttpBuilder(context, "microKwng/getMyClassMicroKwngLookInfo.json").params(params).post(callback);
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
}

