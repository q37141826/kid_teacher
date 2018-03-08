package cn.dajiahui.kidteacher.ui.mine.waiteaddclass;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.fxtx.framework.http.callback.ResultCallback;
import com.fxtx.framework.json.GsonUtil;
import com.fxtx.framework.json.HeadJson;
import com.fxtx.framework.log.ToastUtil;
import com.fxtx.framework.ui.FxActivity;
import com.fxtx.framework.widgets.dialog.FxDialog;
import com.fxtx.framework.widgets.refresh.MaterialRefreshLayout;
import com.squareup.okhttp.Request;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.http.RequestUtill;
import cn.dajiahui.kidteacher.ui.mine.adapter.ApWaiteAddclass;
import cn.dajiahui.kidteacher.ui.mine.bean.BeDelateStudent;
import cn.dajiahui.kidteacher.ui.mine.bean.BeToDeleStudentJson;
import cn.dajiahui.kidteacher.ui.mine.bean.BeWaiteAddClass;
import cn.dajiahui.kidteacher.ui.mine.bean.BeWaiteAddStudent;
import cn.dajiahui.kidteacher.util.Logger;

/*
* 待加入班级的学生
* */
public class WaiteAddClassActivity extends FxActivity {

    private final static int HTTP_TYPE_GET_APPLY_STUDENTS = 0;  // 取得申请学生列表

    private final static int MSG_SHOW_FXDIALOG = 1;     // 显示dialog
    private final static int MSG_DISMISS_FXDIALOG = 2;  // 关闭dialog

    private ListView mListview;
    protected MaterialRefreshLayout refresh;
    private ApWaiteAddclass apWaiteAddclass; // 申请学生列表的Adapter
    private List<BeWaiteAddStudent> applyStudentInfoList = new ArrayList<BeWaiteAddStudent>();
    private RelativeLayout delete_view;
    private int itemNumber = 0; // 总的数据数
    private List<BeDelateStudent> mIdList = new ArrayList<>();//删除学生Id的集合
    private CheckBox allCheck;//全选按钮
    private Button btn_delete;


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SHOW_FXDIALOG: // 显示dialog
                    showfxDialog();
                    break;

                case MSG_DISMISS_FXDIALOG: // 关闭dialog
                    dismissfxDialog();
                    break;

                default:
                    dismissfxDialog();
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setfxTtitle(R.string.mine_wait_add_class);
        onRightBtn(R.string.edit);
        onBackText();
        showfxDialog();
        httpData();
    }


    @Override
    protected void initView() {
        setContentView(R.layout.activity_waite_add_class);

        refresh = getView(R.id.refresh);
        initRefresh(refresh);

        mListview = getView(R.id.listview);
        delete_view = getView(R.id.delete_view);
        allCheck = getView(R.id.allCheck);
        btn_delete = getView(R.id.btn_delete);
        apWaiteAddclass = new ApWaiteAddclass(this, applyStudentInfoList, handler);
        mListview.setAdapter(apWaiteAddclass);

        /*全选*/
        allCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (allCheck.isChecked() == false) {
                    allCheck.setChecked(false);
                    btn_delete.setBackgroundResource(R.color.gray);
                    for (int i = 0; i < applyStudentInfoList.size(); i++) {
                        applyStudentInfoList.get(i).setBo(false);

                    }
                    /*全部非选择状态就清空集合*/
                    mIdList.clear();
                } else {
                    allCheck.setChecked(true);
                    btn_delete.setBackgroundResource(R.color.red);
                    for (int i = 0; i < applyStudentInfoList.size(); i++) {
                        applyStudentInfoList.get(i).setBo(true);
                    }
                }
                // 刷新
                apWaiteAddclass.notifyDataSetChanged();
            }
        });

        /*单个选项*/
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (delete_view.getVisibility() == View.VISIBLE) {

                    if (applyStudentInfoList.get(position).getBo() == true) {
                        applyStudentInfoList.get(position).setBo(false);
                    } else {
                        applyStudentInfoList.get(position).setBo(true);
                    }
                    apWaiteAddclass.notifyDataSetChanged();
                }
            }
        });



        /*执行删除*/
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 /*遍历 模型集合 找出true 就是选中的    false 就是未选中的*/
                for (int i = 0; i < applyStudentInfoList.size(); i++) {
                    if (applyStudentInfoList.get(i).getBo() == true) {
                        mIdList.add(new BeDelateStudent(applyStudentInfoList.get(i).getUser_id(), applyStudentInfoList.get(i).getClass_id()));
                    }
                }

                if (mIdList.size() > 0) {
                    FxDialog dialog = new FxDialog(WaiteAddClassActivity.this) {
                        @Override
                        public void onRightBtn(int flag) {
                            Iterator it = applyStudentInfoList.iterator();
                            while (it.hasNext()) {
                                // 得到对应集合元素
                                BeWaiteAddStudent g = (BeWaiteAddStudent) it.next();
                                // 判断
                                if (g.getBo()) {
                                    // 从集合中删除上一次next方法返回的元素
                                    it.remove();
                                }
                            }

                            isShowCheckbox = false;
                            String s = new GsonUtil().getJsonElement(new BeToDeleStudentJson(mIdList)).toString();
                            delete_view.setVisibility(View.GONE);
                            apWaiteAddclass.changeState(-2);

                            /*删除网络请求*/
                            deleteStudents(s);

                            mIdList.clear();
                        }

                        @Override
                        public void onLeftBtn(int flag) {


                        }
                    };
                    dialog.setTitle(R.string.prompt);
                    dialog.setMessage(R.string.prompt_delete);
                    dialog.show();

                }
            }
        });
    }

    @Override
    public void httpData() {
        //网络请求
        RequestUtill.getInstance().httpGetApplyStudents(context, callGetApplyStudents); // 取得申请入班学生列表
    }

    /*删除我的作品*/
    private void deleteStudents(String deleteJson) {
        RequestUtill.getInstance().httpDeleteStudents(WaiteAddClassActivity.this, callDeleteStudents, deleteJson);
    }

    /*删除待加入班级记录*/
    ResultCallback callDeleteStudents = new ResultCallback() {
        @Override
        public void onError(Request request, Exception e) {
            dismissfxDialog();
        }

        @Override
        public void onResponse(String response) {
            dismissfxDialog();
            Logger.d("删除学生" + response);
            HeadJson json = new HeadJson(response);
            if (json.getstatus() == 0) {


            } else {
                ToastUtil.showToast(WaiteAddClassActivity.this, json.getMsg());
            }

        }
    };
    /**
     * 取得申请入班学生列表的callback函数
     */
    ResultCallback callGetApplyStudents = new ResultCallback() {
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
                /* 解析学生信息 */
                if (mPageNum == 1) {
                    applyStudentInfoList.clear();
                }
                BeWaiteAddClass temp = json.parsingObject(BeWaiteAddClass.class);
                itemNumber = temp.getTotalRows();
                if (temp != null && temp.getLists().size() > 0) {
                    mPageNum++;
                    applyStudentInfoList.addAll(temp.getLists());
                }
                apWaiteAddclass.notifyDataSetChanged();

            } else {
                ToastUtil.showToast(context, json.getMsg());
            }
            finishRefreshAndLoadMoer(refresh, isLastPage()); // 要自己判断是否为最后一页
        }
    };

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
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    public boolean isShowCheckbox = false;//mfalse 不显示 mtrue 显示

    @Override
    public void onRightBtnClick(View view) {

        if (!isShowCheckbox) {
//            mIdMap.clear();
            mIdList.clear();
            delete_view.setVisibility(View.VISIBLE);

              /*刷新整个Addapter*/
            for (int i = 0; i < applyStudentInfoList.size(); i++) {
                // 改变boolean
                applyStudentInfoList.get(i).setBo(false);

            }
            /*设置非选择状态*/
            allCheck.setChecked(false);
            apWaiteAddclass.changeState(-1);
            isShowCheckbox = !isShowCheckbox;
               /*设置删除按钮颜色*/
            btn_delete.setBackgroundResource(R.color.gray);
        } else {
            delete_view.setVisibility(View.GONE);
            apWaiteAddclass.changeState(-2);
            isShowCheckbox = !isShowCheckbox;
        }
    }
}
