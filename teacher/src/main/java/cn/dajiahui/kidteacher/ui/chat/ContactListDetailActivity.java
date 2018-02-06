package cn.dajiahui.kidteacher.ui.chat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.fxtx.framework.log.ToastUtil;
import com.fxtx.framework.text.StringUtil;
import com.fxtx.framework.ui.FxActivity;
import com.fxtx.framework.widgets.BasicListView;
import com.fxtx.framework.widgets.refresh.MaterialRefreshLayout;
import com.hyphenate.easeui.domain.EaseUser;

import java.util.ArrayList;
import java.util.List;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.controller.Constant;
import cn.dajiahui.kidteacher.controller.UserController;
import cn.dajiahui.kidteacher.ui.chat.adapter.ApContactDetail;
import cn.dajiahui.kidteacher.ui.chat.bean.BeContactUser;
import cn.dajiahui.kidteacher.ui.chat.db.DemoDBManager;
import cn.dajiahui.kidteacher.util.DjhJumpUtil;

/**
 * 通讯录二级列表点击详情页
 */
public class ContactListDetailActivity extends FxActivity {
    private BasicListView listview;
    private MaterialRefreshLayout refreshConcactListDetail;
    private TextView textNullConcactListDetail;
    private ApContactDetail adapter;
    private List<BeContactUser> data = new ArrayList<BeContactUser>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setfxTtitle(intent.getStringExtra(Constant.bundle_title));
        onBackText();

    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_contact_list_detail);
        listview = getView(R.id.listview);
        refreshConcactListDetail = getView(R.id.refresh_concact_list_detail);
        textNullConcactListDetail = getView(R.id.tv_null);
        listview.setEmptyView(textNullConcactListDetail);
        initRefresh(refreshConcactListDetail);
        initdata();
        adapter = new ApContactDetail(this, data);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(onItemClickListener);

    }

    private void initdata() {
        Intent intent = getIntent();
        data = (List<BeContactUser>) intent.getSerializableExtra(Constant.bundle_obj);
    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            BeContactUser beContactUser = data.get(position);
//            if (StringUtil.isEmpty(beContactUser.getHxId())) {
//                ToastUtil.showToast(ContactListDetailActivity.this, "数据错误，无法聊天");
//                return;
//            }
            if (beContactUser.getObjectId().equals(UserController.getInstance().getUserId())) {
                ToastUtil.showToast(ContactListDetailActivity.this, R.string.Cant_chat_with_yourself);
                return;
            }
//            EaseUser user = new EaseUser(beContactUser.getHxId());
//            user.setAvatar(beContactUser.getAvator());
//            user.setNick(beContactUser.getRealName());
//            DemoDBManager.getInstance().saveContact(user);
//            DjhJumpUtil.getInstance().startChatActivity(ContactListDetailActivity.this, data.get(position).getHxId(), data.get(position).getPhone());

        }
    };

    @Override
    public void httpData() {
        finishRefreshAndLoadMoer(refreshConcactListDetail, 1);
    }
}
