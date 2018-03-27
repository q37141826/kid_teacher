package cn.dajiahui.kidteacher.ui.homework;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import cn.dajiahui.kidteacher.ui.homework.adapter.ApClassStatus;
import cn.dajiahui.kidteacher.ui.homework.adapter.ApClasssify;
import cn.dajiahui.kidteacher.ui.homework.adapter.ApHomework;
import cn.dajiahui.kidteacher.ui.homework.bean.BeClassAndStatus;
import cn.dajiahui.kidteacher.ui.homework.bean.BeHomeworkList;
import cn.dajiahui.kidteacher.ui.homework.bean.BeHomeworkStatus;
import cn.dajiahui.kidteacher.ui.homework.bean.Homework;
import cn.dajiahui.kidteacher.ui.homework.view.ArbitrarilyDialog;
import cn.dajiahui.kidteacher.ui.mine.bean.BeClass;
import cn.dajiahui.kidteacher.util.DjhJumpUtil;

import static android.app.Activity.RESULT_OK;

/**
 * 作业(首页面)
 */
public class FrHomework extends FxFragment {
    private final static int HTTP_TYPE_GET_HOMEWORK_LIST = 1;  // 获取作业列表
    private final static int HTTP_TYPE_GET_CLASS_STATUS = 2;  // 获取班级和状态列表

    private int httpType = HTTP_TYPE_GET_CLASS_STATUS;

    private TextView mTitle;
    private TextView mTvnull;

    private PinnedHeaderListView listview; // 作业列表
    private ApHomework adapterHomework; // 作业列表的Adapter
    private List<BeHomeworkList> homeworkList;//作业列表数据源
    private MaterialRefreshLayout refresh;
    private int itemNumber = 0; // 作业总的数据数

    private PopupWindow popupWindow;
    private ApClasssify apClass;//选择班级列表的adapter
    private ApClassStatus apCheckState;//选择状态列表的adapter
    private List<BeClass> classList = new ArrayList<BeClass>();  // 班级列表
    private List<BeHomeworkStatus> statusList = new ArrayList<BeHomeworkStatus>();// 作业状态的列表
    private int choosetag = 0;//choosetag=1 选择班级 choosetag=2 选择状态
    private String mClassId;
    private String mStatusId;
    private TextView mChooseClass;//选择班级
    private TextView mChooseStatus;//选择状态

    private Fragment mFragement;
    private LinearLayout line_task, fr_root;
    private ArbitrarilyDialog arbitrarilyDialog;
    private ListView mChoiceClassListView;
    private int selectClassPosition = 0;//选中班级的position

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
        mChooseStatus = getView(R.id.tv_state);
        fr_root = getView(R.id.fr_root);
        line_task = getView(R.id.line_task);

        refresh = getView(R.id.refresh);
        initRefresh(refresh);

        tv.setVisibility(View.VISIBLE);
        mTitle.setText(R.string.tab_task);
        tv.setText(R.string.send_homework);
        tv.setOnClickListener(onClick);
        mChooseClass.setOnClickListener(onClick);
        mChooseStatus.setOnClickListener(onClick);

        homeworkList = new ArrayList<>();
        adapterHomework = new ApHomework(getActivity(), homeworkList);
        listview.setAdapter(adapterHomework);
        listview.setOnItemClickListener(onItemClick);

        //初始化班级列表
        BeClass classifno = new BeClass();
        classifno.setClass_name(getResources().getString(R.string.all_options));
        classList.add(classifno);
        apClass = new ApClasssify(getActivity(), classList);

        // 初始化状态列表
        BeHomeworkStatus statusInfo = new BeHomeworkStatus();
        statusInfo.setLabel(getResources().getString(R.string.all_options));
        statusList.add(statusInfo);
        apCheckState = new ApClassStatus(getActivity(), statusList);

        listview.setEmptyView(mTvnull);
        mTvnull.setText(R.string.e_homework);
        showfxDialog();
        httpType = HTTP_TYPE_GET_CLASS_STATUS;
        httpData(); // 获取班级和状态列表
        mFragement = this;

    }


    /*作业、班级、发布作业点击事件*/
    private View.OnClickListener onClick = new View.OnClickListener() {


        @Override
        public void onClick(View v) {
            /*选择状态栏*/
            int line_taskHeight = (int) line_task.getY();
            /*手机高度*/
            int phoneHeight = BaseUtil.getPhoneHeight(getActivity());
            boolean navigationBarShow = BaseUtil.isNavigationBarShow(getActivity());

            switch (v.getId()) {
                case R.id.tool_right:
                    DjhJumpUtil.getInstance().fragmentStartBaseActivityForResult(getActivity(), SendHomeworkActivity.class, mFragement, null, DjhJumpUtil.getInstance().activtiy_SendHomework);
                    break;

                case R.id.tv_class:
                    /*动态设置textview右边 向上 1.向上 2.向下*/
                    setPictureDirection(R.drawable.up, 1);

                    setPictureDirection(R.drawable.down, 2);
                    choosetag = 1;
                    int mClassDialogHeight = 0;

                    /*显示 虚拟按键*/
                    if (navigationBarShow) {
                        /*显示虚拟按键*/
                        BaseUtil.getDaoHangHeight(getActivity());
                        mClassDialogHeight = phoneHeight - line_taskHeight - BaseUtil.getStatusBarHeight(getActivity());
                    } else {
                        /*不显示虚拟按键*/
                        mClassDialogHeight = phoneHeight - line_taskHeight - BaseUtil.getDaoHangHeight(getActivity()) - BaseUtil.getStatusBarHeight(getActivity());
                    }

                    /*弹出Dialog*/
                    arbitrarilyDialog = new ArbitrarilyDialog(getActivity(), R.layout.view_arbitrarily_dialog_layout, mClassDialogHeight) {


                        @Override
                        public void initView() {

                            mChoiceClassListView = (ListView) rootView.findViewById(R.id.listview);
                            mChoiceClassListView.setAdapter(apClass);
                            apClass.reFreshItem(selectClassPosition);
                            mChoiceClassListView.setOnItemClickListener(onitemclick);
                        }

                        @Override
                        protected void onTouchOutside() {
//                            Logger.d("外部关闭弹框1：");
                               /*动态设置textview右边 向上 1.向上 2.向下*/
                            setPictureDirection(R.drawable.down, 1);

                            setPictureDirection(R.drawable.down, 2);
                        }
                    };
                    arbitrarilyDialog.show();

                    break;
                case R.id.tv_state:
                     /*动态设置textview右边 向上 1.向上 2.向下*/
                    setPictureDirection(R.drawable.up, 2);

                    setPictureDirection(R.drawable.down, 1);
                    choosetag = 2;

                    int mStateDialogHeight = 0;


                    /*显示 虚拟按键*/
                    if (navigationBarShow) {
                        /*显示虚拟按键*/
                        BaseUtil.getDaoHangHeight(getActivity());
                        mStateDialogHeight = phoneHeight - line_taskHeight - BaseUtil.getStatusBarHeight(getActivity());
                    } else {
                        /*不显示虚拟按键*/
                        mStateDialogHeight = phoneHeight - line_taskHeight - BaseUtil.getDaoHangHeight(getActivity()) - BaseUtil.getStatusBarHeight(getActivity());
                    }

                    /*弹出Dialog*/
                    arbitrarilyDialog = new ArbitrarilyDialog(getActivity(), R.layout.view_arbitrarily_dialog_layout, mStateDialogHeight) {


                        @Override
                        public void initView() {

                            mChoiceClassListView = (ListView) rootView.findViewById(R.id.listview);
                            mChoiceClassListView.setAdapter(apCheckState);
                            apCheckState.reFreshItem(selectClassPosition);
                            mChoiceClassListView.setOnItemClickListener(onitemclick);
                        }

                        @Override
                        protected void onTouchOutside() {
                            setPictureDirection(R.drawable.down, 1);
                            setPictureDirection(R.drawable.down, 2);
                        }
                    };
                    arbitrarilyDialog.show();
                    break;
                default:
                    break;
            }
        }
    };

    /*上设置图片方向 1向上 2.向下*/
    private void setPictureDirection(int picture, int dir) {
        switch (dir) {
            case 1:
                Drawable drawableUp = getActivity().getResources().getDrawable(picture);
                drawableUp.setBounds(0, 0, drawableUp.getMinimumWidth(), drawableUp.getMinimumHeight());
                mChooseClass.setCompoundDrawables(null, null, drawableUp, null);
                break;
            case 2:
                Drawable drawableDown = getActivity().getResources().getDrawable(picture);
                drawableDown.setBounds(0, 0, drawableDown.getMinimumWidth(), drawableDown.getMinimumHeight());
                mChooseStatus.setCompoundDrawables(null, null, drawableDown, null);
                break;
            default:
                break;

        }

    }

    /*item点击事件*/
    private PinnedHeaderListView.OnItemClickListener onItemClick = new PinnedHeaderListView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int section, int position, long id) {

            Bundle b = new Bundle();
            b.putString("className", homeworkList.get(section).getHome_list().get(position).getClass_name());
            b.putString("homeworkId", homeworkList.get(section).getHome_list().get(position).getId());

            DjhJumpUtil.getInstance().fragmentStartBaseActivityForResult(getActivity(), CheckHomeworkActivity.class, mFragement, b, DjhJumpUtil.getInstance().activtiy_ChoiceHomework);

//            DjhJumpUtil.getInstance().fragmentStartBaseActivityForResult(getActivity(), CheckHomeworkActivity.class, b, 0);
        }

        @Override
        public void onSectionClick(AdapterView<?> adapterView, View view, int section, long id) {

        }
    };


    public void httpData() {
        //网络请求
        switch (httpType) {
            case HTTP_TYPE_GET_HOMEWORK_LIST:
                RequestUtill.getInstance().httpGetHomeworkList(getActivity(), callGetClassList, mClassId, mStatusId, mPageSize, mPageNum); // 获取作业列表
                break;

            case HTTP_TYPE_GET_CLASS_STATUS:
                RequestUtill.getInstance().httpGetClassAndStatus(getActivity(), callGetClassAndStatus); // 获取班级和状态
                break;
        }

    }

    /**
     * 获取班级和状态列表的callback函数
     */
    ResultCallback callGetClassAndStatus = new ResultCallback() {
        @Override
        public void onError(Request request, Exception e) {
            dismissfxDialog();
        }

        @Override
        public void onResponse(String response) {
            Logger.d("作业返回数据：" + response);
            dismissfxDialog();
            httpType = HTTP_TYPE_GET_HOMEWORK_LIST;
            httpData(); // 获取班级和状态列表
            HeadJson json = new HeadJson(response);
            if (json.getstatus() == 0) {
                /* 解析班级和状态列表信息 */
                BeClassAndStatus temp = json.parsingObject(BeClassAndStatus.class);
                if (temp != null) {
                    if (temp.getClass_list().size() > 0) {
                        classList.addAll(temp.getClass_list());
                    }

                    if (temp.getHomework_status().size() > 0) {
                        statusList.addAll(temp.getHomework_status());
                    }
                }

                apClass.notifyDataSetChanged();
                apCheckState.notifyDataSetChanged();

            } else {
                ToastUtil.showToast(getActivity(), json.getMsg());
            }
        }
    };

    /**
     * 获取作业列表的callback函数
     */
    ResultCallback callGetClassList = new ResultCallback() {
        @Override
        public void onError(Request request, Exception e) {
            dismissfxDialog();
            finishRefreshAndLoadMoer(refresh, 0);
        }

        @Override
        public void onResponse(String response) {
            dismissfxDialog();
            Logger.d("callGetClassList    response ------"+response);
            HeadJson json = new HeadJson(response);
            if (json.getstatus() == 0) {
                    /* 解析作业列表信息 */
                if (mPageNum == 1) {
                    homeworkList.clear();
                }
                Homework temp = json.parsingObject(Homework.class);
                itemNumber = temp.getTotalRows();
                if (temp != null && temp.getLists().size() > 0) {
                    mPageNum++;
                    addList(temp.getLists());

                }

                adapterHomework.notifyDataSetChanged();
            } else {
                ToastUtil.showToast(getActivity(), json.getMsg());
            }
            finishRefreshAndLoadMoer(refresh, isLastPage()); // 要自己判断是否为最后一页
        }
    };


    /*选择列表事件监听*/
    private AdapterView.OnItemClickListener onitemclick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            if (choosetag == 1) {
                selectClassPosition = position;
                apClass.reFreshItem(selectClassPosition);
                mChooseClass.setText(classList.get(position).getClass_name());
                mChooseClass.setTextColor(getResources().getColor(R.color.blue_1F6DED));
                mClassId = classList.get(position).getId();
                httpType = HTTP_TYPE_GET_HOMEWORK_LIST;
                mPageNum = 1;
                httpData();
            } else {
                selectClassPosition = position;
                apCheckState.reFreshItem(selectClassPosition);
                mChooseStatus.setText(statusList.get(position).getLabel());
                mChooseStatus.setTextColor(getResources().getColor(R.color.blue_1F6DED));
                mStatusId = statusList.get(position).getValue();
                httpType = HTTP_TYPE_GET_HOMEWORK_LIST;
                mPageNum = 1;
                httpData();
            }
            if (popupWindow != null) popupWindow.dismiss();


            if (arbitrarilyDialog != null) {
                arbitrarilyDialog.dismiss();
                /*动态设置textview右边 向上 1.向上 2.向下*/
                setPictureDirection(R.drawable.down, 1);
                setPictureDirection(R.drawable.down, 2);
            }

        }
    };


    @Override
    public void onResume() {
        super.onResume();
//        Logger.d("FrHomework    onResume ");

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DjhJumpUtil.getInstance().activtiy_SendHomework && resultCode == RESULT_OK) { // 布置作业成功返回
            httpType = HTTP_TYPE_GET_HOMEWORK_LIST;
            mPageNum = 1;
            httpData();
            // TODO 刷新列表
        }
        if (requestCode == DjhJumpUtil.getInstance().activtiy_ChoiceHomework && resultCode == RESULT_OK) {//检查作业成功

            httpType = HTTP_TYPE_GET_HOMEWORK_LIST;
            mPageNum = 1;
            httpData();
            // TODO 刷新列表
        }
    }

    /**
     * 把发布时间同样的作业放到同一个Section中去
     *
     * @param list
     */
    private void addList(List<BeHomeworkList> list) {
        if (homeworkList.size() > 0) {
            if (homeworkList.get(homeworkList.size() - 1).getPubdate().equals(list.get(0).getPubdate())) {
                homeworkList.get(homeworkList.size() - 1).getHome_list().addAll(list.get(0).getHome_list());
                list.remove(0);
            }
            homeworkList.addAll(list);
        } else {
            homeworkList.addAll(list);
        }
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
}
