package cn.dajiahui.kidteacher.ui.myclass;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.fxtx.framework.http.callback.ResultCallback;
import com.fxtx.framework.json.HeadJson;
import com.fxtx.framework.log.Logger;
import com.fxtx.framework.log.ToastUtil;
import com.fxtx.framework.ui.FxFragment;
import com.fxtx.framework.util.BaseUtil;
import com.fxtx.framework.widgets.listview.PinnedHeaderListView;
import com.fxtx.framework.widgets.refresh.MaterialRefreshLayout;
import com.squareup.okhttp.Request;

import java.util.ArrayList;
import java.util.List;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.http.RequestUtill;
import cn.dajiahui.kidteacher.ui.homework.CheckHomeworkActivity;
import cn.dajiahui.kidteacher.ui.homework.SendHomeworkActivity;
import cn.dajiahui.kidteacher.ui.homework.adapter.ApClassStatus;
import cn.dajiahui.kidteacher.ui.homework.adapter.ApClasssify;
import cn.dajiahui.kidteacher.ui.homework.adapter.ApHomework;
import cn.dajiahui.kidteacher.ui.homework.bean.BeClassAndStatus;
import cn.dajiahui.kidteacher.ui.homework.bean.BeHomeworkList;
import cn.dajiahui.kidteacher.ui.homework.bean.BeHomeworkStatus;
import cn.dajiahui.kidteacher.ui.homework.bean.Homework;
import cn.dajiahui.kidteacher.ui.homework.view.ArbitrarilyDialog;
import cn.dajiahui.kidteacher.ui.mine.adapter.ApMyClass;
import cn.dajiahui.kidteacher.ui.mine.bean.BeClass;
import cn.dajiahui.kidteacher.ui.mine.bean.BeMyclass;
import cn.dajiahui.kidteacher.ui.mine.myclass.ClassInfoActivity;
import cn.dajiahui.kidteacher.ui.mine.myclass.MyClassActivity;
import cn.dajiahui.kidteacher.util.DjhJumpUtil;

import static android.app.Activity.RESULT_OK;

/**
 * 班级
 */
public class FrClass extends FxFragment {

    protected Toolbar toolbar;
    private ListView mListView;
    private MaterialRefreshLayout refresh;
    private ApMyClass apMyClass;   // 班级列表的Adapter
    private List<BeClass> classInfoList = new ArrayList<BeClass>();
    private int itemNumber = 0; // 总的数据数
    private TextView tvNUll;


    @Override
    protected View initinitLayout(LayoutInflater inflater) {
        return inflater.inflate(R.layout.activity_my_class, null);
    }
    @SuppressLint("ResourceAsColor")
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView title = getView(R.id.tool_title);
        title.setText("我的班级");
        showfxDialog();
        httpData();
        refresh = getView(R.id.refresh);
        initRefresh(refresh);
        mListView = getView(R.id.listview);
        tvNUll = getView(R.id.tv_null);
        tvNUll.setText("暂无班级");
        apMyClass = new ApMyClass(getActivity(), classInfoList);
        mListView.setEmptyView(tvNUll);
        mListView.setAdapter(apMyClass);


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String classId = classInfoList.get(position).getId();
                Bundle bundle = new Bundle();
                bundle.putString("classId", classId);
                bundle.putString("className", classInfoList.get(position).getClass_name());
                DjhJumpUtil.getInstance().startBaseActivityForResult(getActivity(), ClassInfoActivity.class, bundle, DjhJumpUtil.getInstance().activtiy_myclassDetail);
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
                Logger.d("教师端我的班级：" + response);
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
                    ToastUtil.showToast(getActivity(), json.getMsg());
                }
                finishRefreshAndLoadMoer(refresh, isLastPage()); // 要自己判断是否为最后一页
            }
        };

        RequestUtill.getInstance().httpLessonClass(getActivity(), call, mPageSize, mPageNum);
    }

    @Override
    protected void dismissfxDialog(int flag) {
        super.dismissfxDialog(flag);
    }

    /**
     * 判断是否为最后一页
     *
     * @return 0 不是最后一页 1 是最后一页
     */
    private int isLastPage() {
        int result = 0;

        if ((mPageNum - 1) * mPageSize >= itemNumber) {
            result = 1;
        }

        return result;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*移除学生有返回本页面刷新页面*/
        if (requestCode == DjhJumpUtil.getInstance().activtiy_myclassDetail && resultCode == RESULT_OK) {
            mPageNum = 1;
            httpData();
        }

    }
}
