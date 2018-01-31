package cn.dajiahui.kidteacher.ui.mine.waiteaddclass;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.fxtx.framework.http.callback.ResultCallback;
import com.fxtx.framework.json.HeadJson;
import com.fxtx.framework.log.ToastUtil;
import com.fxtx.framework.ui.FxActivity;
import com.fxtx.framework.widgets.dialog.FxDialog;
import com.fxtx.framework.widgets.refresh.MaterialRefreshLayout;
import com.hyphenate.easeui.domain.EaseUser;
import com.squareup.okhttp.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.http.RequestUtill;
import cn.dajiahui.kidteacher.ui.mine.adapter.ApWaiteAddclass;
import cn.dajiahui.kidteacher.ui.mine.bean.BeWaiteAddClass;
import cn.dajiahui.kidteacher.ui.mine.bean.BeWaiteAddStudent;

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
    private int itemNumber = 0; // 总的数据数

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
        apWaiteAddclass = new ApWaiteAddclass(this, applyStudentInfoList, handler);
        mListview.setAdapter(apWaiteAddclass);

        mListview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {


                FxDialog dialog = new FxDialog(WaiteAddClassActivity.this) {
                    @Override
                    public void onRightBtn(int flag) {
                        Toast.makeText(WaiteAddClassActivity.this, "删除", Toast.LENGTH_SHORT).show();
                        applyStudentInfoList.remove(position);
                        apWaiteAddclass.notifyDataSetChanged();
                    }

                    @Override
                    public void onLeftBtn(int flag) {

                    }
                };
                dialog.setTitle(R.string.prompt);
                dialog.setMessage(R.string.prompt_delete);
                dialog.show();

                return false;
            }
        });

    }

    @Override
    public void httpData() {
        //网络请求
        RequestUtill.getInstance().httpGetApplyStudents(context, callGetApplyStudents); // 取得申请入班学生列表

//        JSONObject jsonObject = new JSONObject();
//        JSONObject json = new JSONObject();
//        JSONArray jsonArray = new JSONArray();
//        try {
//            jsonObject.put("class_id", "001");
//            jsonObject.put("user_id", "000a");
//            jsonArray.put(jsonObject);
//
//            jsonObject = new JSONObject();
//            jsonObject.put("class_id", "002");
//            jsonObject.put("user_id", "000b");
//            jsonArray.put(jsonObject);
//
//            jsonObject = new JSONObject();
//            jsonObject.put("class_id", "003");
//            jsonObject.put("user_id", "000c");
//            jsonArray.put(jsonObject);
//
//
////            jsonArray.put(list);
//            json.put("list",jsonArray);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//
//        RequestUtill.getInstance().httpTestJson(context, callGetApplyStudents, json.toString()); // 测试json
    }

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
}
