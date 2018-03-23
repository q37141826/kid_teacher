package cn.dajiahui.kidteacher.ui.homework;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fxtx.framework.http.callback.ResultCallback;
import com.fxtx.framework.json.HeadJson;
import com.fxtx.framework.log.Logger;
import com.fxtx.framework.log.ToastUtil;
import com.fxtx.framework.ui.FxActivity;
import com.fxtx.framework.widgets.refresh.MaterialRefreshLayout;
import com.squareup.okhttp.Request;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.http.RequestUtill;
import cn.dajiahui.kidteacher.ui.homework.adapter.ApChooseClass;
import cn.dajiahui.kidteacher.ui.homework.bean.BeHomewrokClass;
import cn.dajiahui.kidteacher.ui.homework.bean.ChooseClass;
import cn.dajiahui.kidteacher.ui.homework.view.CustomDatePicker;

/**
 * 发布课本作业（选择班级，截止时间)
 */
public class ChooseClassActivity extends FxActivity {

    private final static int HTTP_TYPE_GET_CLASSES = 1;  // 取得班级
    private final static int HTTP_TYPE_PUBLISH = 2;  // 确认布置

    private int httpType = HTTP_TYPE_GET_CLASSES;
    private ListView mListview; // 班级列表
    private TextView mChoosetime;
    private CustomDatePicker customDatePicker;
    private ApChooseClass apchooseClass; // 选择班级适配器
    private List<BeHomewrokClass> classInfoList = new ArrayList<BeHomewrokClass>();
    private MaterialRefreshLayout refresh;
    private String bookId;
    private String unitId;
    private int itemNumber = 0; // 总的数据数
    private BeHomewrokClass selectClass;
    private Button mConfirm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setfxTtitle(R.string.task_textbook_assignment);
        onBackText();
        bookId = getIntent().getStringExtra("bookId");
        unitId = getIntent().getStringExtra("unitId");
        showfxDialog();
        httpType = HTTP_TYPE_GET_CLASSES;
        httpData(); // 请求取得班级列表
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_choose_class);

        TextView mCleartime = getView(R.id.tv_cleartime);
        mChoosetime = getView(R.id.tv_choosetime);
        // 确认布置按钮
        mConfirm = getView(R.id.btn_confirm);
        mListview = getView(R.id.listview);
        mConfirm.setOnClickListener(onClick);
        mCleartime.setOnClickListener(onClick);
        /*时间选择器*/
        initDatePicker();

        refresh = getView(R.id.refresh);
        initRefresh(refresh);

        apchooseClass = new ApChooseClass(this, classInfoList);
        mListview.setAdapter(apchooseClass);
        mChoosetime.setOnClickListener(onClick);
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 布置成功，返回首页作业页面 测试用
//                setResult(1);
//                finishActivity();
                //把点击的position传递到adapter里面去
                if (classInfoList.get(position).getIs_pubed().equals("1")) {
                    ToastUtil.showToast(context, "已经给这个班布置过了");
                } else {
                    selectClass = classInfoList.get(position);
                    apchooseClass.changeState(position);
                    /*设置底部按钮颜色*/
                    if (!mChoosetime.getText().equals("")) {
                        mConfirm.setBackgroundColor(getResources().getColor(R.color.blue_dark));
                    }
                }
            }
        });
    }

    private View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_confirm:
                    if (selectClass == null) {
                        ToastUtil.showToast(context, "请选择班级");
                    } else if (mChoosetime.getText().toString().equals("")) {
                        ToastUtil.showToast(context, "请选择截止时间");
                    } else {
                        httpType = HTTP_TYPE_PUBLISH;
                        httpData();
//                        finishActivity();
                    }

                    break;

                case R.id.tv_choosetime:
//                    Toast.makeText(ChooseClassActivity.this, "时间选择器", Toast.LENGTH_SHORT).show();
                    // 日期格式为yyyy-MM-dd HH:mm
                    if (mChoosetime.getText().toString().equals("")) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
                        String now = sdf.format(new Date());
                        customDatePicker.show(now);
                    } else {
                        customDatePicker.show(mChoosetime.getText().toString());
                    }
                    break;

                case R.id.tv_cleartime:
                    Toast.makeText(ChooseClassActivity.this, "清空", Toast.LENGTH_SHORT).show();
                    initDatePicker();
//                    mChoosetime.setTextColor(Color.WHITE);
                    break;

                default:
                    break;
            }
        }
    };

    private void initDatePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());

        mChoosetime.setText("");
//        mChoosetime.setTextColor(Color.WHITE);

        customDatePicker = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                mChoosetime.setText(time);
                mChoosetime.setTextColor((context.getResources().getColor(R.color.black_tv_6)));
            }
        }, now, "2200-12-31 00:00"); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker.showSpecificTime(true); // 显示时和分
        customDatePicker.setIsLoop(false); // 不允许循环滚动
    }

    @Override
    public void httpData() {
        //网络请求
        switch (httpType) {
            case HTTP_TYPE_GET_CLASSES:
                RequestUtill.getInstance().httpGetHomeWorkClassList(context, callGetClassList, bookId, unitId, mPageSize, mPageNum); // 取得班级列表
                break;

            case HTTP_TYPE_PUBLISH:
                RequestUtill.getInstance().httpPublishHomework(context, callGetUnitList, selectClass.getId(), bookId, unitId, mChoosetime.getText().toString()); // 确认布置
                break;
        }
    }

    /**
     * 取得班级列表的callback
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
            HeadJson json = new HeadJson(response);
            if (json.getstatus() == 0) {
                /* 解析班级列表信息 */
                if (mPageNum == 1) {
                    classInfoList.clear();
                }

                ChooseClass temp = json.parsingObject(ChooseClass.class);
                itemNumber = temp.getTotalRows();
                if (temp != null && temp.getLists().size() > 0) {
                    mPageNum++;
                    classInfoList.addAll(temp.getLists());
                }

                apchooseClass.notifyDataSetChanged();

            } else {
                ToastUtil.showToast(context, json.getMsg());
            }
            finishRefreshAndLoadMoer(refresh, isLastPage()); // 要自己判断是否为最后一页
        }
    };

    /**
     * 确认布置的callback
     */
    ResultCallback callGetUnitList = new ResultCallback() {
        @Override
        public void onError(Request request, Exception e) {
            Logger.d("布置作业失败：" + e.toString());
            dismissfxDialog();
        }

        @Override
        public void onResponse(String response) {
            dismissfxDialog();
            Logger.d("布置作业response" + response);
            HeadJson json = new HeadJson(response);
            if (json.getstatus() == 0) {
                // 布置成功，返回首页作业页面
                setResult(RESULT_OK);
                finishActivity();
            } else {
                ToastUtil.showToast(context, json.getMsg());
                Logger.d("布置作业失败 json.getMsg()：" + json.getMsg());
            }
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
