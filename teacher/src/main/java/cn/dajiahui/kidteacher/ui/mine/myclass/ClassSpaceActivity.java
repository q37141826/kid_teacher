package cn.dajiahui.kidteacher.ui.mine.myclass;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.fxtx.framework.http.callback.ResultCallback;
import com.fxtx.framework.json.HeadJson;
import com.fxtx.framework.log.Logger;
import com.fxtx.framework.log.ToastUtil;
import com.fxtx.framework.ui.FxActivity;
import com.fxtx.framework.widgets.refresh.MaterialRefreshLayout;
import com.squareup.okhttp.Request;

import java.util.ArrayList;
import java.util.List;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.http.RequestUtill;
import cn.dajiahui.kidteacher.ui.mine.adapter.ApClassSpace;
import cn.dajiahui.kidteacher.ui.mine.bean.BeClassSpace;
import cn.dajiahui.kidteacher.ui.mine.bean.BeClassSpaceList;
import cn.dajiahui.kidteacher.util.DjhJumpUtil;

/*
* 班级空间
* */
public class ClassSpaceActivity extends FxActivity {
    private ListView mListview;
    private ApClassSpace apClassSpace;
    private MaterialRefreshLayout refresh;
    private List<BeClassSpaceList> classSpacesList = new ArrayList<>();
    private String classId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setfxTtitle(R.string.mine_class_space);
        onBackText();
        onRightBtn(R.string.mine_send_dynamic);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_class_space);
        classId = getIntent().getStringExtra("classId");

        mListview = getView(R.id.listview);
        refresh = getView(R.id.refresh);
        TextView tvNUll = getView(R.id.tv_null);
        tvNUll.setOnClickListener(onClick);
        tvNUll.setText("暂无班级动态");
        mListview.setEmptyView(tvNUll);
        initRefresh(refresh);

        httpClassSpace();

        apClassSpace = new ApClassSpace(this, classSpacesList);

        mListview.setAdapter(apClassSpace);
    }

    /*发布动态*/
    @Override
    public void onRightBtnClick(View view) {
        Bundle bundle = new Bundle();
        bundle.putString("classId", classId);
        DjhJumpUtil.getInstance().startBaseActivityForResult(ClassSpaceActivity.this, SendDynamicActivity.class, bundle, 0);

    }

    /*请求班级空间数据*/
    private void httpClassSpace() {
        httpData();
    }

    @Override
    public void httpData() {
        super.httpData();
        RequestUtill.getInstance().httpClassSpace(ClassSpaceActivity.this, callClassSpace, classId, mPageSize + "", mPageNum + "");
    }

    /*班级空间*/
    ResultCallback callClassSpace = new ResultCallback() {
        @Override
        public void onError(Request request, Exception e) {
            dismissfxDialog();
        }

        @Override
        public void onResponse(String response) {

            Logger.d("班级空间 ：" + response);

            dismissfxDialog();
            HeadJson json = new HeadJson(response);
            if (json.getstatus() == 0) {
                BeClassSpace beClassSpace = json.parsingObject(BeClassSpace.class);
                List<BeClassSpaceList> list = beClassSpace.getList();
                classSpacesList.clear();
                classSpacesList.addAll(list);
                apClassSpace.notifyDataSetChanged();
            } else {
                ToastUtil.showToast(ClassSpaceActivity.this, json.getMsg());
            }
            finishRefreshAndLoadMoer(refresh, 1);
        }

    };

    private View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_null:
                    showfxDialog();
                    httpClassSpace();
                    break;
                default:
                    break;
            }

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0 && resultCode == 1) {
            httpData();
        }


    }
}
