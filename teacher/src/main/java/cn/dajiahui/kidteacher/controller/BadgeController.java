package cn.dajiahui.kidteacher.controller;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.fxtx.framework.http.callback.ResultCallback;
import com.fxtx.framework.json.HeadJson;
import com.squareup.okhttp.Request;
import com.umeng.socialize.utils.ContextUtil;

import cn.dajiahui.kidteacher.http.RequestUtill;

/**
 * Created by z on 2016/2/3.
 * 角标控制器  用来控制模块角标显示结果
 */
public class BadgeController {
    //通知，全部功能，角标控制器
    public int noticeBadge;
    //模块角标控制器 作业,点名,审核学生，测评，教研,评价学生
    public int paperBadge, callBadge, auditBadge, testBadge, researchBadge, evelBadge;

    public static BadgeController controller;

    private BadgeController() {
    }

    public void initNum(boolean isTrue) {
        if (isTrue) {
            callBadge = 0;
            evelBadge = 0;
            paperBadge = 0;
            auditBadge = 0;
            testBadge = 0;
        }
        noticeBadge = 0;
        researchBadge = 0;
    }

    /**
     * 单一实例
     */
    public static BadgeController getInstance() {
        if (controller == null) {
            synchronized (BadgeController.class) {
                if (controller == null) {
                    controller = new BadgeController();
                }
            }
        }
        return controller;
    }

    /**
     * 获取工作台角标数
     *
     * @return
     */
    public int getBadgeFunction() {
        return researchBadge + testBadge + paperBadge + callBadge + evelBadge + auditBadge;
    }


    //发布角标更新通知
    public void sendBadgeMessage(Context context) {
        LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(Constant.broad_badge_count_action));
    }

    //初始化 角标数量 List<BeUnReadCount> lists
    public void initBeDge() {
        initNum(false);
//        for (BeUnReadCount c : lists) {
//            switch (c.type) {
//                case Constant.type_tz:
//                    noticeBadge = c.count;
//                    break;
//                case Constant.type_jxyj:
//                    //教学研究
//                    researchBadge = c.count;
//                    break;
//            }
//        }
    }

    public int getTypeBadge(String type) {
        int count;
        switch (type) {
            case Constant.type_tz://通知
                count = noticeBadge;
                break;
            case Constant.type_bjsq:
                //班级申请
                count = auditBadge;
                break;
            case Constant.type_jxyj:
                //教学研究
                count = researchBadge;
                break;
            case Constant.type_cptj:
                //测评提交
                count = testBadge;
                break;
            case Constant.type_zytj:
                //作业提交
                count = paperBadge;
                break;
            case Constant.type_dm:
                // 点名
                count = callBadge;
                break;
            case Constant.type_pjxy:
                // "评价学生"
                count = evelBadge;
                break;
            default:
                count = 0;
                break;
        }
        return count;
    }


    /**
     * 获取角标数查询
     */
    public void httpCommission() {
        RequestUtill.getInstance().httpCountInfo(ContextUtil.getContext(), new ResultCallback() {
            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onResponse(String response) {
                HeadJson json = new HeadJson(response);
                if (json.getFlag() == 1) {
                    BadgeController.getInstance().evelBadge = json.parsingInt("pjxscount");
                    BadgeController.getInstance().callBadge = json.parsingInt("skdmcount");
                    BadgeController.getInstance().paperBadge = json.parsingInt("jczycount");
                    BadgeController.getInstance().testBadge = json.parsingInt("jccpcount");
                    BadgeController.getInstance().auditBadge = json.parsingInt("shxscount");
                } else {
                    BadgeController.getInstance().evelBadge = 0;
                    BadgeController.getInstance().callBadge = 0;
                    BadgeController.getInstance().paperBadge = 0;
                    BadgeController.getInstance().auditBadge = 0;
                    BadgeController.getInstance().testBadge = 0;
                }
                BadgeController.getInstance().sendBadgeMessage(ContextUtil.getContext());
            }
        }, UserController.getInstance().getUserId());
    }

}
