package cn.dajiahui.kidteacher.ui.homework;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.fxtx.framework.http.ErrorCode;
import com.fxtx.framework.http.callback.ResultCallback;
import com.fxtx.framework.json.GsonType;
import com.fxtx.framework.json.HeadJson;
import com.fxtx.framework.log.ToastUtil;
import com.fxtx.framework.ui.FxFragment;
import com.fxtx.framework.widgets.listview.PinnedHeaderListView;
import com.squareup.okhttp.Request;

import java.util.ArrayList;
import java.util.List;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.http.RequestUtill;
import cn.dajiahui.kidteacher.ui.homework.adapter.ApClassState;
import cn.dajiahui.kidteacher.ui.homework.adapter.ApClasssify;
import cn.dajiahui.kidteacher.ui.homework.adapter.ApHomework;
import cn.dajiahui.kidteacher.ui.homework.bean.Homework;
import cn.dajiahui.kidteacher.util.DjhJumpUtil;

/**
 * 作业
 */
public class FrHomework extends FxFragment {
    private TextView mTitle;
    private TextView mTvnull;
    private PinnedHeaderListView listview;
    private ApHomework adapter_homework;
    private List<Homework> data;//作业列表数据源
    private PopupWindow popupWindow;
    private ApClasssify adapter_apclass;//选择班级列表
    private ApClassState adapter_apcheckState;//选择状态列表
    private TextView mChooseClass;//选择班级
    private TextView mChooseState;//选择状态
    private int position = 0;
    private String type = "";
    private int choosetag = 0;//choosetag=1 选择班级 choosetag=2 选择状态
    private List<Homework> datasstate;//选择状态的数据源

    /*
    * 作业
    * */
    @Override
    protected View initinitLayout(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fr_homework, null);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTitle = getView(R.id.tool_title);
        TextView tv = getView(R.id.tool_right);
        mTvnull = getView(R.id.tv_null);
        listview = getView(R.id.listview);
        mChooseClass = getView(R.id.tv_class);
        mChooseState = getView(R.id.tv_state);

        tv.setVisibility(View.VISIBLE);
        mTitle.setText(R.string.tab_task);
        tv.setText(R.string.send_homework);
        tv.setOnClickListener(onClick);
        mChooseClass.setOnClickListener(onClick);
        mChooseState.setOnClickListener(onClick);

        data = new ArrayList<>();
        adapter_homework = new ApHomework(getActivity(), data);
        listview.setAdapter(adapter_homework);
        listview.setOnItemClickListener(onItemClick);

        listview.setEmptyView(mTvnull);
        mTvnull.setText(R.string.e_homework);
//        showfxDialog();

        httpData();

    }

    /*作业、班级、发布作业点击事件*/
    private View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tool_right:
                    Toast.makeText(activity, R.string.send_homework, Toast.LENGTH_SHORT).show();
                    DjhJumpUtil.getInstance().startBaseActivity(getContext(), SendHomeworkActivity.class);
                    break;

                case R.id.tv_class:
                    Toast.makeText(activity, "选择班级", Toast.LENGTH_SHORT).show();
                    choosetag = 1;
                    if (popupWindow != null && popupWindow.isShowing()) {
                        popupWindow.dismiss();
                        return;
                    }
                    popupWindow = null;
                    if (popupWindow == null) {
                        initPopup();
                    }
                    popupWindow.showAsDropDown(mChooseClass, 20, -10);

                    break;
                case R.id.tv_state:
                    if (mChooseClass.getText().equals("班级")) {
                        Toast toast = Toast.makeText(getActivity(), "请先选择班级", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        return;
                    }
                    Toast.makeText(activity, "选择检查状态", Toast.LENGTH_SHORT).show();
                    choosetag = 2;
                    //点击选项列表
                    if (popupWindow != null && popupWindow.isShowing()) {
                        popupWindow.dismiss();

                        return;
                    }
                    if (popupWindow != null) {
                        initPopup();
                    }
                    popupWindow.showAsDropDown(mChooseState, 20, -10);


                    break;
                default:
                    break;
            }
        }
    };

    /*item点击事件*/
    private PinnedHeaderListView.OnItemClickListener onItemClick = new PinnedHeaderListView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int section, int position, long id) {

            Toast.makeText(activity, "跳作业报告", Toast.LENGTH_SHORT).show();
            Bundle b = new Bundle();
            b.putString("unit", data.get(position).getTask_second_class_name());

            DjhJumpUtil.getInstance().startBaseActivity(getActivity(), CheckHomeworkActivity.class, b, 0);
        }

        @Override
        public void onSectionClick(AdapterView<?> adapterView, View view, int section, long id) {

        }
    };


    @Override
    protected void dismissfxDialog(int flag) {
        super.dismissfxDialog(flag);
        mTvnull.setText(R.string.e_homework);
        mTvnull.setVisibility(View.VISIBLE);
        mTvnull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showfxDialog();
                httpData();
            }
        });
    }

    /*请求数据*/
    @Override
    public void httpData() {
        super.httpData();

        for (int i = 0; i < 10; i++) {
            data.add(new Homework("周一", "二年" + i + "班",
                    "2018年01月02日 13:57:0" + i,
                    "Until" + i + " frist to school",
                    "待检查", "0/3"));
        }
        adapter_apclass = new ApClasssify(getActivity(), data);
        //模拟数据
        datasstate = new ArrayList<>();
        datasstate.add(new Homework("待检查"));
        datasstate.add(new Homework("已检查"));
        adapter_apcheckState = new ApClassState(getActivity(), datasstate);

        adapter_homework.notifyDataSetChanged();

        RequestUtill.getInstance().getHomework(getActivity(), new ResultCallback() {

            @Override
            public void onError(Request request, Exception e) {
                dismissfxDialog();
                ToastUtil.showToast(getActivity(), ErrorCode.error(e));

            }

            @Override
            public void onResponse(String response) {
                dismissfxDialog();
                HeadJson json = new HeadJson(response);
                if (json.getstatus() == 0) {
                    data.clear();
                    List<Homework> temp = json.parsingListArray(new GsonType<List<Homework>>() {
                    });
                    if (temp != null)
                        data.addAll(temp);
                    adapter_homework.notifyDataSetChanged();
                } else {
                    ToastUtil.showToast(getActivity(), json.getMsg());
                }

            }
        });
    }

    /*选择班级和状态*/
    private void initPopup() {

        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        View contentView = View.inflate(getActivity(), R.layout.view_popup_layout, null);
        if (choosetag == 1) {
            popupWindow = new PopupWindow(contentView, metrics.widthPixels * 3 / 10, metrics.heightPixels / 2);
        } else {
            popupWindow = new PopupWindow(contentView, metrics.widthPixels * 3 / 10, mChooseState.getHeight() * 2);
        }
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.update();
        popupWindow.setFocusable(true);
        ListView listView = (ListView) contentView.findViewById(R.id.listview);

        if (choosetag == 1) {
            listView.setAdapter(adapter_apclass);
//            adapter_apclass.reFreshItem(position);

        } else {
            listView.setAdapter(adapter_apcheckState);
        }
        listView.setOnItemClickListener(onitemclick);
    }

    /*选择列表事件监听*/
    private AdapterView.OnItemClickListener onitemclick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            mPageNum = 1;

            if (choosetag == 1) {
                mChooseClass.setText(data.get(position).getTask_second_class_name());
                // mChooseClass.setTag(data.get(position).getCode());
                // type = data.get(position).getCode();
                httpData();
            } else {
                mChooseState.setText(datasstate.get(position).getTask_check());
                if (datasstate.get(position).getTask_check().equals("待检查")) {
                    mChooseState.setTextColor(getResources().getColor(R.color.red));

                } else {
                    mChooseState.setTextColor(getResources().getColor(R.color.black));
                }
            }
            if (popupWindow != null) popupWindow.dismiss();
        }
    };


    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // ity
        if (resultCode == -1) {
        }
    }
}
