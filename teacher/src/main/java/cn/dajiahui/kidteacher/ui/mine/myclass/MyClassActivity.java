package cn.dajiahui.kidteacher.ui.mine.myclass;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fxtx.framework.http.callback.ResultCallback;
import com.fxtx.framework.json.HeadJson;
import com.fxtx.framework.log.ToastUtil;
import com.fxtx.framework.ui.FxActivity;
import com.fxtx.framework.widgets.refresh.MaterialRefreshLayout;
import com.squareup.okhttp.Request;

import java.util.ArrayList;
import java.util.List;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.http.RequestUtill;
import cn.dajiahui.kidteacher.ui.mine.adapter.ApMyClass;
import cn.dajiahui.kidteacher.ui.mine.bean.BeClass;
import cn.dajiahui.kidteacher.ui.mine.bean.BeMyclass;
import cn.dajiahui.kidteacher.util.DjhJumpUtil;

/*
* 我的班级
* */
public class MyClassActivity extends FxActivity {

    private ListView mListView;
    private MaterialRefreshLayout refresh;
    private ApMyClass apMyClass;   // 班级列表的Adapter
    private List<BeClass> classInfoList = new ArrayList<BeClass>();
    private int itemNumber = 0; // 总的数据数


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setfxTtitle(R.string.mine_my_class);
        onBackText();
        showfxDialog();
        httpData();
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_my_class);

        refresh = getView(R.id.refresh);
//        mPageSize = 2;
        initRefresh(refresh);

        mListView = getView(R.id.listview);
        apMyClass = new ApMyClass(MyClassActivity.this, classInfoList);

        mListView.setAdapter(apMyClass);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String classId = classInfoList.get(position).getId();
                Bundle bundle = new Bundle();
                bundle.putString("classId", classId);
                bundle.putString("className", classInfoList.get(position).getClass_name());
                DjhJumpUtil.getInstance().startBaseActivity(MyClassActivity.this, ClassInfoActivity.class, bundle, 0);
            }
        });

    }

    @Override
    public void httpData() {
        //网络请求
        ResultCallback call = new ResultCallback() {
            @Override
            public void onError(Request request, Exception e) {
                dismissfxDialog();
                finishRefreshAndLoadMoer(refresh, 0);
            }

            @Override
            public void onResponse(String response) {
                dismissfxDialog();
                HeadJson json = new HeadJson(response);
                if (json.getstatus() == 0) {
                    /* 解析班级列表信息 */
                    if (mPageNum == 1) {
                        classInfoList.clear();
                    }

                    BeMyclass temp = json.parsingObject(BeMyclass.class);
                    itemNumber = temp.getTotalRows();
                    if (temp != null && temp.getLists().size() > 0) {
                        mPageNum++;
                        classInfoList.addAll(temp.getLists());
                    }

                    apMyClass.notifyDataSetChanged();

                } else {
                    ToastUtil.showToast(context, json.getMsg());
                }
                finishRefreshAndLoadMoer(refresh, isLastPage()); // 要自己判断是否为最后一页
            }
        };

        RequestUtill.getInstance().httpLessonClass(context, call, mPageSize, mPageNum );
    }

    @Override
    protected void dismissfxDialog(int flag) {
        super.dismissfxDialog(flag);
    }

    /**
     * 判断是否为最后一页
     * @return 0 不是最后一页 1 是最后一页
     */
    private int isLastPage() {
        int result = 0;

        if ((mPageNum - 1) * mPageSize >= itemNumber) {
            result = 1;
        }

        return result;
    }
}
