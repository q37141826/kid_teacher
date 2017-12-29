package cn.dajiahui.kidteacher.util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.fxtx.framework.json.GsonUtil;
import com.fxtx.framework.json.HeadJson;
import com.fxtx.framework.log.ToastUtil;
import com.fxtx.framework.platforms.jpush.JpushReceiver;
import com.fxtx.framework.text.StringUtil;

import cn.dajiahui.kidteacher.controller.Constant;


/**
 * Created by z on 2016/5/4.
 */
public class TeacherReceiver extends JpushReceiver {

    private BeJpush jpush;

    @Override
    protected void openActiviy(HeadJson json) {
        jpush = new GsonUtil().getJsonObject(json.getObject().toString(), BeJpush.class);
        if (jpush != null) {
            //处理逻辑
            String str = jpush.getType();
            if (!StringUtil.isEmpty(str)) {
                jpushJumpAct(str);
            }
        } else {
            ToastUtil.showToast(context, "数据非法");
        }
    }

    private void jpushJumpAct(String string) {
        switch (string) {
            case Constant.type_bjsq:
            case Constant.type_xygl:
                // 班级申请
//                startAct(context, AuditDetailsActivity.class, jpush.getForeignId());
                break;
            case Constant.type_cptj:
            case Constant.type_zytj:
                // 测评提交,作业提交
//                startAct(context, HttpPaperDeTailsActivity.class, jpush.getForeignId());
                break;
            case Constant.type_jxyj:
            case Constant.type_jxzj:
            case Constant.type_jybg:
                // 教学研究 教研总结 教研报告
//                startAct(context, TeDetailsActivity.class, jpush.getForeignId());
                break;
            case Constant.type_tz:
            case Constant.type_tzpl:
            case Constant.type_tzhf:
                // 通知 通知评论 通知回复
//                startAct(context, NoticeDetailsActivity.class, jpush.getForeignId());
                break;
            case Constant.type_xc:
            case Constant.type_xcpl:
            case Constant.type_xchf:
                // 相册 相册评论 相册回复
//                startAct(context, PhotoDetailsActivity.class, jpush.getForeignId()); // getForeignId
                break;
            case Constant.type_sktx:
                // 上课提醒         无操作
                break;
            case Constant.type_pjxy:
                // 评价学生
//                startOpStuAct(context, OpStuListActivity.class, jpush.getForeignId());
                break;
            case Constant.type_dm:
                // 点名
//                startOpStuAct(context, CallRollActivity.class, jpush.getForeignId());
                break;
            case Constant.type_zb:
            case Constant.type_jk:
                // 转班   结课
//                startClassAct(context, ClassActivity.class);
                break;
        }
    }

    private void startAct(Context context, Class classs, String string) {
        Intent intent = new Intent(context, classs);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Bundle bundle = new Bundle();
        bundle.putString(Constant.bundle_id, string);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    private void startOpStuAct(Context context, Class classs, String objectId) {
        Intent intent = new Intent(context, classs);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Bundle bundle = new Bundle();
        bundle.putString(Constant.bundle_id, objectId);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    private void startClassAct(Context context, Class classs) {
        Intent intent = new Intent(context, classs);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.bundle_type, 0);
        bundle.putInt(Constant.bundle_obj, 0);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
