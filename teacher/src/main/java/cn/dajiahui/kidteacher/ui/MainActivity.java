package cn.dajiahui.kidteacher.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.view.KeyEvent;
import android.widget.RadioGroup;

import com.fxtx.framework.http.callback.ResultCallback;
import com.fxtx.framework.log.ToastUtil;
import com.fxtx.framework.text.StringUtil;
import com.fxtx.framework.ui.FxFragment;
import com.fxtx.framework.ui.base.FxTabActivity;
import com.fxtx.framework.ui.bean.BeTab;
import com.fxtx.framework.util.ActivityUtil;
import com.fxtx.framework.widgets.badge.BadgeRadioButton;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.domain.EaseUser;
import com.squareup.okhttp.Request;

import java.util.ArrayList;
import java.util.List;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.controller.Constant;
import cn.dajiahui.kidteacher.controller.UserController;
import cn.dajiahui.kidteacher.http.RequestUtill;
import cn.dajiahui.kidteacher.ui.chat.FrChat;
import cn.dajiahui.kidteacher.ui.chat.constant.ImHelper;
import cn.dajiahui.kidteacher.ui.chat.db.DemoDBManager;
import cn.dajiahui.kidteacher.ui.chat.db.UserDao;
import cn.dajiahui.kidteacher.ui.homework.FrHomework;
import cn.dajiahui.kidteacher.ui.mine.FrMine;
import cn.dajiahui.kidteacher.ui.mine.bean.BeAccess;
import cn.dajiahui.kidteacher.ui.mine.personalinformation.UserDetailsActivity;

public class MainActivity extends FxTabActivity {
    private RadioGroup radioGroup;
    public FrChat frChat;

    public FrHomework frHomework;
    private FrMine frMine;

    private int rediobtnId; // 当前选择的模块
    private BadgeRadioButton functionRb, chatRb, noticeRb;
    private Boolean isExit = false;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    isExit = false;
                    break;
                default:
                    break;
            }
        }
    };


    private LocalBroadcastManager broadcastManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        synchronizeData();
        registerBroadcastReceiver();
        initFragment(savedInstanceState);
//        BadgeController.getInstance().httpCommission();
    }

    //异步同步数据
    public void synchronizeData() {
        RequestUtill.getInstance().synchronizeData(context, new ResultCallback() {
            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onResponse(String response) {

            }
        }, UserController.getInstance().getUserId());
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_main);
        radioGroup = getView(R.id.tab_group);
        BeTab tab = new BeTab(R.id.rediobtn_task, "", getString(R.string.tab_task), R.drawable.radio_index, true);
        rediobtnId = R.id.rediobtn_task;
        addRadioView(tab, radioGroup);
        BeAccess access = UserController.getInstance().getAccess();

//        if (access.isMsn) {
        BeTab tab3 = new BeTab(R.id.rediobtn_chat, "", getString(R.string.tab_chat), R.drawable.radio_chat, false);
        chatRb = addRadioView(tab3, radioGroup);
//        }
        BeTab tab4 = new BeTab(R.id.rediobtn_mine, "", getString(R.string.tab_mine), R.drawable.radio_mine, false);
        addRadioView(tab4, radioGroup);
        radioGroup.setOnCheckedChangeListener(this);
    }

    @Override
    protected FxFragment initIndexFragment() {
        if (frHomework == null)
            frHomework = new FrHomework();
        return frHomework;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        rediobtnId = checkedId;
        switch (checkedId) {
            case R.id.rediobtn_task:
                if (frHomework == null)
                    frHomework = new FrHomework();//作业
                switchContent(isFragment, frHomework);
                break;

            case R.id.rediobtn_chat:
                if (frChat == null)
                    frChat = new FrChat();
                switchContent(isFragment, frChat);
                break;
            case R.id.rediobtn_mine:
                if (frMine == null)
                    frMine = new FrMine();
                switchContent(isFragment, frMine);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode) {
            if (requestCode == 3001) {
                ArrayList<String> strings = data.getStringArrayListExtra(Constant.bundle_obj);
                if (strings != null && strings.size() != 0) {
                    if (frMine != null) {
//                        httpUserIcon(new File(strings.get(0)));
                    }
                }
            }
        } else if (resultCode == UserDetailsActivity.PICSETSULT) {
            //进入用户详情修改图片后返回来的值
            if (frHomework != null) {
//             GlideUtil.showRoundImage(MainActivity.this, UserController.getInstance().getUser().getAvator(), frHomework.imUer, R.drawable.ico_default_user, true);
            }
            if (frMine != null) {
//                GlideUtil.showRoundImage(MainActivity.this, UserController.getInstance().getUser().getAvator(), frMine.imUser, R.drawable.ico_default_user, true);
            }
        }
    }


    EMMessageListener messageListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            // 提示新消息
            for (EMMessage message : messages) {
                String avatar = message.getStringAttribute(UserDao.COLUMN_NAME_AVATAR, "");
                String nickname = message.getStringAttribute(UserDao.COLUMN_NAME_NICK, "");
                if (!StringUtil.isEmpty(nickname)) {
                    EaseUser user = new EaseUser(message.getFrom());
                    user.setAvatar(avatar);
                    user.setNick(nickname);
                    DemoDBManager.getInstance().saveContact(user);
                }
                ImHelper.getInstance().getNotifier().onNewMsg(message);
            }
            refreshUIWithMessage();
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
        }

        @Override
        public void onMessageRead(List<EMMessage> list) {

        }

        @Override
        public void onMessageDelivered(List<EMMessage> list) {

        }

        @Override
        public void onMessageRecalled(List<EMMessage> list) {

        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {
        }
    };

    private void refreshUIWithMessage() {
        runOnUiThread(new Runnable() {
            public void run() {
                // 刷新bottom bar消息未读数
                updateUnreadLabel();
                if (rediobtnId == R.id.rediobtn_chat) {
                    // 当前页面如果为聊天历史页面，刷新此页面
                    if (frChat != null) {
                        frChat.refresh();
                    }
                }
            }
        });
    }

    private void registerBroadcastReceiver() {

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constant.broad_notice_action); // 收到通知（魔耳通知）
        registerReceiver(broadcastReceiver, intentFilter);
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(Constant.broad_notice_action)) { // 收到通知 （魔耳通知）
//                if (mineRb != null) {
////                    mineRb.setTextColor(Color.RED);
////                    mineRb.showTextBadge("2");
////                    StudentUtil.setBadge(mineRb, 22);
//                    mineRb.showCirclePointBadge();
//                }

            }

        }
    };

    private void unregisterBroadcastReceiver() {
        unregisterReceiver(broadcastReceiver);
//        broadcastManager.unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onDestroy() {
        unregisterBroadcastReceiver();
        super.onDestroy();
    }

    /**
     * 刷新未读消息数
     */
    public void updateUnreadLabel() {
        int count = getUnreadMsgCountTotal();
        if (count > 0) {
            chatRb.showTextBadge("" + count);
        } else {
            chatRb.hiddenBadge();
        }
    }

    /**
     * 获取未读消息数
     *
     * @return
     */
    public int getUnreadMsgCountTotal() {
        return EMClient.getInstance().chatManager().getUnreadMsgsCount();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (UserController.getInstance().getAccess().isMsn) {
            updateUnreadLabel();
            ImHelper sdkHelper = ImHelper.getInstance();
            sdkHelper.pushActivity(this);
            EMClient.getInstance().chatManager().addMessageListener(messageListener);
        }
    }

    @Override
    protected void onStop() {
        if (UserController.getInstance().getAccess().isMsn) {
            EMClient.getInstance().chatManager().removeMessageListener(messageListener);
            ImHelper sdkHelper = ImHelper.getInstance();
            sdkHelper.popActivity(this);
        }
        super.onStop();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!isExit) {
                isExit = true;
                ToastUtil.showToast(context, R.string.angin_back);
                handler.sendEmptyMessageDelayed(0, 2000);
                return false;
            } else {
                UserController.getInstance().initBean();
                moveTaskToBack(false);
                ActivityUtil.getInstance().finishAllActivity();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}
