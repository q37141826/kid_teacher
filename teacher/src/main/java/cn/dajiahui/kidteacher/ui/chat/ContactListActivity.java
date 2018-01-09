package cn.dajiahui.kidteacher.ui.chat;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.fxtx.framework.http.ErrorCode;
import com.fxtx.framework.http.callback.ResultCallback;
import com.fxtx.framework.json.HeadJson;
import com.fxtx.framework.log.ToastUtil;
import com.fxtx.framework.ui.FxActivity;
import com.fxtx.framework.widgets.listview.PinnedHeaderListView;
import com.fxtx.framework.widgets.refresh.MaterialRefreshLayout;
import com.squareup.okhttp.Request;

import java.util.ArrayList;
import java.util.List;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.controller.UserController;
import cn.dajiahui.kidteacher.http.RequestUtill;
import cn.dajiahui.kidteacher.ui.chat.adapter.ApContact;
import cn.dajiahui.kidteacher.ui.chat.bean.BeContact;
import cn.dajiahui.kidteacher.ui.chat.bean.BeContactUser;
import cn.dajiahui.kidteacher.util.DjhJumpUtil;

/**
 * Created by Administrator on 2016/3/17.
 * 通讯录
 */
public class ContactListActivity extends FxActivity {

    private PinnedHeaderListView listviewConcactList;
    private MaterialRefreshLayout refreshConcactList;
    private TextView textNullConcactList;
    private ApContact adapter;
    private List<BeContact> datas = new ArrayList<BeContact>();

    @Override
    protected void initView() {
        setContentView(R.layout.activity_contact_list);
        listviewConcactList = getView(R.id.pinnedListView_concact_list);
        refreshConcactList = getView(R.id.refresh_concact_list);
        textNullConcactList = getView(R.id.tv_null);
        listviewConcactList.setEmptyView(textNullConcactList);
        initRefresh(refreshConcactList);
        listviewConcactList.setPinHeaders(false);
        adapter = new ApContact(this, datas);
        listviewConcactList.setAdapter(adapter);
        listviewConcactList.setOnItemClickListener(new PinnedHeaderListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int section, int position, long id) {
                //子Item点击进入下一界面
                BeContact text = datas.get(section) ;
                List<BeContactUser> data = null;
                if (text.getGroupList() != null && text.getGroupList().size() > 0 && text.getGroupList().get(position).getUserCount() > 0) {
                    data = datas.get(section).getGroupList().get(position).getList();
                    DjhJumpUtil.getInstance().startContactListDetailActivity(ContactListActivity.this, data,datas.get(section).getGroupList().get(position).getName());
                } else if (text.getClassList() != null && text.getClassList().size() > 0 && text.getClassList().get(position).getUserCount() >0 ) {
                    data = datas.get(section).getClassList().get(position).getList();
                    DjhJumpUtil.getInstance().startContactListDetailActivity(ContactListActivity.this, data,datas.get(section).getClassList().get(position).getClassName());
                }
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
                finishRefreshAndLoadMoer(refreshConcactList, 1);
            }

            @Override
            public void onResponse(String response) {
                dismissfxDialog();
                HeadJson headJson = new HeadJson(response);
                if (headJson.getstatus() == 0) {
                    datas.clear();
                    BeContact beContact1 = headJson.parsingObject("info1", BeContact.class);
                    BeContact beContact2 = headJson.parsingObject("info2", BeContact.class);
                    if (beContact1 != null && beContact1.getGroupList() != null && beContact1.getGroupList().size() > 0)
                        datas.add(beContact1);
                    if (beContact2 != null && beContact2.getClassList() != null && beContact2.getClassList().size() > 0)
                        datas.add(beContact2);
                    if (datas != null && datas.size() > 0)
                        adapter.notifyDataSetChanged();

                } else {
                    ToastUtil.showToast(ContactListActivity.this, headJson.getMsg());
                }
                finishRefreshAndLoadMoer(refreshConcactList, 1);
            }
        };
        RequestUtill.getInstance().httpContactList(ContactListActivity.this, callback, UserController.getInstance().getUserId());
    }
}
