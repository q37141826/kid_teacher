package cn.dajiahui.kidteacher.ui.homework;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

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
import cn.dajiahui.kidteacher.ui.homework.adapter.ApChooseSupplementary;
import cn.dajiahui.kidteacher.ui.homework.bean.BeWorkBook;
import cn.dajiahui.kidteacher.ui.homework.bean.ChooseSupplementary;

/**
 * 选择教辅
 */
public class ChooseSupplementaryActivity extends FxActivity {

    private final static int HTTP_TYPE_GET_WORKBOOKLIST = 1;  // 取得教辅
    private final static int HTTP_TYPE_SELECT_WORKBOOK = 2;  // 确认教辅
    private int httpType = HTTP_TYPE_GET_WORKBOOKLIST;
    private GridView mGrildview;
    private List<BeWorkBook> bookInfoList = new ArrayList<BeWorkBook>();
    private MaterialRefreshLayout refresh;
    private ApChooseSupplementary apChooseSupplementary;    // 教辅列表的Adapter
    private int itemNumber = 0; // 总的数据数
    private BeWorkBook selectBook;  // 选择的教辅

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setfxTtitle(R.string.task_choose_supplementary);
        onBackText();
        onRightText(R.string.task_choose_ok);
        selectBook = (BeWorkBook)getIntent().getSerializableExtra("selectWorkBook");
        apChooseSupplementary = new ApChooseSupplementary(ChooseSupplementaryActivity.this, bookInfoList, selectBook);
        mGrildview.setAdapter(apChooseSupplementary);

        mGrildview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //把点击的position传递到adapter里面去
                selectBook = bookInfoList.get(position);
                apChooseSupplementary.changeState(selectBook);
            }
        });
        showfxDialog();
        httpData(); // 取教辅列表
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_choose_supplementary);
        mGrildview = getView(R.id.grildview);

        refresh = getView(R.id.refresh);
        initRefresh(refresh);
    }


    @Override
    public void onRightBtnClick(View view) {
        if (selectBook != null) {
            httpType = HTTP_TYPE_SELECT_WORKBOOK;
            httpData(); //确认教辅
        } else {
            Toast.makeText(ChooseSupplementaryActivity.this, "请选择教辅", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void httpData() {
        //网络请求
        if (httpType == HTTP_TYPE_SELECT_WORKBOOK && selectBook != null ) {
            RequestUtill.getInstance().httpSelectWorkBook(context, callSelectWorkBook, selectBook.getId()); // 选择教辅
        } else {
            RequestUtill.getInstance().httpGetWorkBookList(context, callGetWorkBookList, mPageSize, mPageNum); // 请求取得教辅列表
        }
    }

    /**
     * 取得教辅列表的callback
     */
    ResultCallback callGetWorkBookList = new ResultCallback() {
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
                    bookInfoList.clear();
                }

                ChooseSupplementary temp = json.parsingObject(ChooseSupplementary.class);
                itemNumber = temp.getTotalRows();
                if (temp != null && temp.getLists().size() > 0) {
                    mPageNum++;
                    bookInfoList.addAll(temp.getLists());
                }

                apChooseSupplementary.notifyDataSetChanged();

            } else {
                ToastUtil.showToast(context, json.getMsg());
            }
            finishRefreshAndLoadMoer(refresh, isLastPage()); // 要自己判断是否为最后一页
        }
    };

    /**
     * 设置教辅的callback
     */
    ResultCallback callSelectWorkBook = new ResultCallback() {
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
                Intent intent = new Intent();
                intent.putExtra("workBook", selectBook);
                setResult(RESULT_OK, intent);
                finishActivity();
            }
        }
    };


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
