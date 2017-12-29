package cn.dajiahui.kidteacher.ui.homework;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.fxtx.framework.ui.FxFragment;
import com.fxtx.framework.widgets.listview.PinnedHeaderListView;

import java.util.ArrayList;
import java.util.List;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.ui.homework.adapter.ApTask;
import cn.dajiahui.kidteacher.ui.homework.bean.Homework;
import cn.dajiahui.kidteacher.util.DjhJumpUtil;

/**
 * 作业
 */
public class FrHomework extends FxFragment {


    private TextView mTitle;
    private TextView mTvnull;
    private PinnedHeaderListView listview;
    private ApTask adapter;
    private List<Homework> list;

    @Override
    protected View initinitLayout(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fr_task, null);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTitle = getView(R.id.tool_title);
        TextView tv = getView(R.id.tool_right);
        mTvnull = getView(R.id.tv_null);
        listview = getView(R.id.listview);

        TextView mClass = getView(R.id.tv_class);
        TextView mState = getView(R.id.tv_state);

        tv.setVisibility(View.VISIBLE);
        mTitle.setText(R.string.tab_task);
        mTitle.setTextColor(R.color.black);
        tv.setTextColor(R.color.black);
        tv.setText(R.string.send_task);
        tv.setOnClickListener(onClick);
        mClass.setOnClickListener(onClick);
        mState.setOnClickListener(onClick);

//        multiplexing_listview_root.setEmptyView(mTvnull);


        list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {

            list.add(new Homework("周一", "二年" + i + "班", "12月20号 14：00", "Until" + i + " frist to school", "待检查", "0/3"));

        }


        adapter = new ApTask(getActivity(), list);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(onItemClick);
//        showfxDialog();
        httpData();

    }

    /*作业、班级、发布作业点击事件*/
    private View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tool_right:
                    Toast.makeText(activity, R.string.send_task, Toast.LENGTH_SHORT).show();
                    DjhJumpUtil.getInstance().startBaseActivity(getContext(), ChooseContentActivity.class);
                    break;

                case R.id.tv_class:
                    Toast.makeText(activity, R.string.task_class, Toast.LENGTH_SHORT).show();
                    break;

                case R.id.tv_state:
                    Toast.makeText(activity, R.string.task_state, Toast.LENGTH_SHORT).show();
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
            b.putString("unit", list.get(position).getTask_second_class_name());

            DjhJumpUtil.getInstance().startBaseActivity(getActivity(), CheckHomeworkActivity.class, b, 0);
        }

        @Override
        public void onSectionClick(AdapterView<?> adapterView, View view, int section, long id) {

        }
    };


    @Override
    protected void dismissfxDialog(int flag) {
        super.dismissfxDialog(flag);
        mTvnull.setText(R.string.e_class_null);
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


//        RequestUtill.getInstance().getTask(getActivity(), new ResultCallback() {
//
//            @Override
//            public void onError(Request request, Exception e) {
//                dismissfxDialog();
//                ToastUtil.showToast(getActivity(), ErrorCode.error(e));
//
//            }
//
//            @Override
//            public void onResponse(String response) {
//                dismissfxDialog();
//                HeadJson json = new HeadJson(response);
//                if (json.getFlag() == 1) {
////                    data.clear();
////                    List< > temp = json.parsingListArray(new GsonType<List< >>() {
////                    });
////                    if (temp != null)
////                        data.addAll(temp);
////                    adapter.notifyDataSetChanged();
//                } else {
//                    ToastUtil.showToast(getActivity(), json.getMsg());
//                }
//
//            }
//        });
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {

        }
    }

    @Override
    public void onResume() {
        super.onResume();


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // ity
        if (resultCode == -1) {
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString("result");
            DjhJumpUtil.getInstance().startScanActivity(getContext(), scanResult);
        }
    }


}
