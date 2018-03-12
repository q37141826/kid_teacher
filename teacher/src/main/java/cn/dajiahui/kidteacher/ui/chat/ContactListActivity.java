package cn.dajiahui.kidteacher.ui.chat;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.fxtx.framework.http.ErrorCode;
import com.fxtx.framework.http.callback.ResultCallback;
import com.fxtx.framework.json.GsonType;
import com.fxtx.framework.json.HeadJson;
import com.fxtx.framework.log.ToastUtil;
import com.fxtx.framework.text.StringUtil;
import com.fxtx.framework.ui.FxActivity;
import com.fxtx.framework.widgets.listview.PinnedHeaderListView;
import com.hyphenate.easeui.domain.EaseUser;
import com.squareup.okhttp.Request;

import java.util.ArrayList;
import java.util.List;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.controller.UserController;
import cn.dajiahui.kidteacher.http.RequestUtill;
import cn.dajiahui.kidteacher.ui.chat.adapter.ApContact;
import cn.dajiahui.kidteacher.ui.chat.bean.BeContactUser;
import cn.dajiahui.kidteacher.ui.chat.bean.BeGroupListUsers;
import cn.dajiahui.kidteacher.ui.chat.db.DemoDBManager;
import cn.dajiahui.kidteacher.util.DjhJumpUtil;

/**
 * Created by Administrator on 2016/3/17.
 * 通讯录
 */
public class ContactListActivity extends FxActivity {

    private PinnedHeaderListView listviewConcactList;
    private TextView textNullConcactList;
    private ApContact apContact;
    private List<BeGroupListUsers> userList = new ArrayList<BeGroupListUsers>(); // 通讯录列表

    @Override
    protected void initView() {
        setContentView(R.layout.activity_contact_list);
        listviewConcactList = getView(R.id.pinnedListView_concact_list);
        textNullConcactList = getView(R.id.tv_null);
        listviewConcactList.setEmptyView(textNullConcactList);
        listviewConcactList.setPinHeaders(false);

        apContact = new ApContact(this, userList);

        listviewConcactList.setAdapter(apContact);
        listviewConcactList.setOnItemClickListener(new PinnedHeaderListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int section, int position, long id) {
                //子Item点击进入下一界面
                BeContactUser contactUser = userList.get(section).getStudent_list().get(position);
                if (StringUtil.isEmpty(contactUser.getEasemob_username())) {
//                    ToastUtil.showToast(ContactListActivity.this, "数据错误，无法聊天");
                    return;
                }
                if (contactUser.getId().equals(UserController.getInstance().getUserId())) {
                    ToastUtil.showToast(ContactListActivity.this, R.string.Cant_chat_with_yourself);
                    return;
                }
                EaseUser user = new EaseUser(contactUser.getEasemob_username());
                user.setAvatar(contactUser.getAvatar());
                user.setNick(contactUser.getNickname());
                DemoDBManager.getInstance().saveContact(user);
                DjhJumpUtil.getInstance().startChatActivity(ContactListActivity.this, contactUser.getEasemob_username(), contactUser.getPhone());
            }

            @Override
            public void onSectionClick(AdapterView<?> adapterView, View view, int section, long id) {
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setfxTtitle(R.string.chat_list);
        onBackText();
        showfxDialog();
        httpData();
    }

    @Override
    public void dismissfxDialog() {
        super.dismissfxDialog();
        textNullConcactList.setText(R.string.chat_concact_no);
    }

    @Override
    public void httpData() {
        ResultCallback callback = new ResultCallback() {
            @Override
            public void onError(Request request, Exception e) {
                dismissfxDialog();
                ToastUtil.showToast(ContactListActivity.this, ErrorCode.error(e));
            }

            @Override
            public void onResponse(String response) {
                dismissfxDialog();
                HeadJson json = new HeadJson(response);
                if (json.getstatus() == 0) {
                    userList.clear();

                    /* 解析通讯录信息 */
                    List<BeGroupListUsers> temp = json.parsingListArray("data", new GsonType<List<BeGroupListUsers>>() {
                    });

                    userList.addAll(temp);

                    apContact.notifyDataSetChanged();

                } else {
                    ToastUtil.showToast(ContactListActivity.this, json.getMsg());
                }
            }
        };
        RequestUtill.getInstance().httpGetContactList(ContactListActivity.this, callback);
    }
}
