package cn.dajiahui.kidteacher.controller;


import cn.dajiahui.kidteacher.ui.homework.bean.Point;

/**
 * Created by z on 2016/2/25.
 */
public class Constant {

    public static final int PG_QUEST_CODE = 10001; //批改请求吗
    public static final String broad_badge_count_action = "broad_badeg_count_action_ata_teacher";//角标广播发送
    public static final String broad_notice_action = "broad_notice_student";//通知

    public static final String socket_scan = "/scanner/server/login/scan/";//扫码
    public static final String socket_submit = "/scanner/server/login/submit/";//登陆
    public static final String socket_reset = "/scanner/server/login/reset/";//取消
    public static final String socket_result = "/server/scanner/login/submit/result/";//回调

    /**
     * 系统常量 不允许修改
     **/
    public static String bundle_id = "_id";
    public static String bundle_obj = "_object";
    public static String bundle_title = "_title";
    public static String bundle_type = "_type";
    public static String bundle_media = "_media";
    public static String bundle_img = "_img";
    public static String bundle_document = "_document";
    /**
     * 文件格式
     */
    public static final int file_img = 0;
    public static final int file_word = 1;
    public static final int file_ppt = 2;
    public static final int file_excel = 3;
    public static final int file_pdf = 4;//pdf
    public static final int file_mp3 = 5;//录音
    public static final int file_video = 6;//视频
    public static final int file_paper = 7; //试卷
    public static final int file_wps = 8;//格式


    /**
     * 通过我的班级列表进入的
     */
    public static final int all_class = 0;//我的班级
    public static final int all_call = 1;// 点名
    public static final int all_site = 2;// 网址
    public static final int all_opinion = 3;//评价
    public static final int all_course = 4;//微客


    public static final int Album_Photo_Max = 20;//相册可选择上传图片最大值
    public static final int Alum_phone_UserIcon = 1;//相册选择用户头像时只能选一张
    public static final int Alum_send_dynamic = 10;//相册选择动态图时只能选9张
    public static final int classNotice = 3;//班级 通知 也叫 教师通知
    public static final int schoolNotice = 2;
    public static final int systemNotice = 1;

    //data remark  我的资料中的状态
    public static final int Data_rm_MOVE = -2;//移动的move
    public static final int Data_rm_MI = 1;//我的
    public static final int Data_rm_Ishare = 2;//我分享的资料
    public static final int Data_rm_ShareMe = 3;//分享给我的资料
    public static final int Data_rm_outfit = 4;//机构资料
    public static final int Data_rm_other = 5;//第三方资料

    public static final int user_edit_pwd = 1;
    public static final int user_edit_user = 2;
    public static final int user_edit_phone = 3;
    public static final int user_edit_sign = 4;
    public static final int user_edit_age = 5;
    public static final int user_edit_email = 6;
    public static final int user_edit_name = 7;
    public static final int user_edit_sex = 8;
    //题目答案类型

    public static final String type_wdbj = "wdbj";//我的班级

    //待办
    public static final String type_bjsq = "bjsq";//班级申请
    public static final String type_jxyj = "jxyj";//教学研究
    public static final String type_jybg = "jybg";//教学报告
    public static final String type_jxzj = "jyzj";//教学总结

    public static final String type_cptj = "cptj";//测评提交
    public static final String type_zytj = "zytj";//作业提交
    public static final String type_dm = "dm";//点名
    public static final String type_pjxy = "pjxy";//评价学生
    public static final String type_wdpj = "wdpj";//我的评价

    //事务  所有待办也属于事务
    public static final String type_zl = "zl";//资料
    public static final String type_wk = "wk";//微课
    public static final String type_cp = "cp";//测评发布
    public static final String type_zybz = "zybz";//作业布置
    public static final String type_xc = "xc";//相册
    public static final String type_xcpl = "xcpl";//相册评论
    public static final String type_xchf = "xchf";//相册回复
    public static final String type_bjdt = "bjdt";//班级动态

    public static final String type_tz = "tz";//通知
    public static final String type_tzpl = "tzpl";//通知评论
    public static final String type_tzhf = "tzhf";//通知回复

    public static final String type_xygl = "xygl";//学生审核

    public static final String type_sktx = "sktx";//上课提醒
    public static final String type_zb = "zb";//转班
    public static final String type_jk = "jk";//结课

    public static final String type_jrbj = "jrbj";//同意加入班级
    public static final String type_mrtz = "mrtz";//摩尔通知

    //权限
    public static final String ac_class = "class";
    public static final String ac_notice = "notice";
    public static final String ac_msn = "msn";
    public static final String ac_job = "job";
    public static final String ac_attend = "attend";
    public static final String ac_stuEval = "stuEval";
    public static final String ac_album = "album";
    public static final String ac_research = "research";
    public static final String ac_microClass = "microClass";
    public static final String ac_evaluation = "evaluation";
    public static final String ac_myFile = "myFile";
    public static final String ac_stuVerify = "stuVerify";
    public static final String ac_urlFavorites = "urlFavorites";
    public static final String ac_Lesson = "lesson";

    /*版本更新*/
    public static final String device = "3";//公共参数
    public static final String os_version = "Android" + android.os.Build.VERSION.RELEASE;
    public static final String signKey = "dajiahuiv5@#$%123";
    public static  final  String Code="dajiahui-mover";//验证码秘钥

    /* 魔耳 检查作业*/
    public static final String Judje = "1";
    public static final String Choice = "2";
    public static final String Sort = "3";
    public static final String Line = "4";
    public static final String Completion = "5";

    /*画线路径*/
    public static final cn.dajiahui.kidteacher.ui.homework.bean.Point PointZero = new Point(0, 0);

    public static final int pointViewDiameter = 20;//作业，练习连线题小圆点的直径
    public static final int pointViewDiameter_margin = 15;//作业，练习连线题小圆点与框框的距离


    public static final int lineWidth = 5;//作业，练习连线题线的宽度
    public static final int JudgeAnswerView_margin = 10;// 作业，练习选择JudgeAnswerView 的panding
    public static final int lineView_margin = 50;// 作业，练习连线题的答题的view的距离

    public static final int SortAnswerView_margin = 10;// 作业，练习排序的view 的panding


}
