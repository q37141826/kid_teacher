package cn.dajiahui.kidteacher.ui.homework;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fxtx.framework.http.callback.ResultCallback;
import com.fxtx.framework.json.GsonType;
import com.fxtx.framework.json.HeadJson;
import com.fxtx.framework.log.ToastUtil;
import com.fxtx.framework.ui.FxActivity;
import com.squareup.okhttp.Request;

import java.util.ArrayList;
import java.util.List;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.http.RequestUtill;
import cn.dajiahui.kidteacher.ui.homework.adapter.ApChooseUnit;
import cn.dajiahui.kidteacher.ui.homework.bean.BeUnit;
import cn.dajiahui.kidteacher.ui.homework.bean.BeWorkBook;
import cn.dajiahui.kidteacher.util.DjhJumpUtil;

/**
 * 发布作业
 */
public class SendHomeworkActivity extends FxActivity {

    private final static int HTTP_TYPE_GET_WORKBOOK = 1;  // 取得教辅
    private final static int HTTP_TYPE_GET_UNIT = 2;  // 取得单元

    private ImageView mImgSupplementary;
    private TextView mTvSupplementary;
    private ListView mListview; // 单元列表
    private TextView mNext; // 下一步按钮

    private ApChooseUnit apChooseUnit; // 单元列表的Adapter
    private BeWorkBook selectWorkBook; // 教辅
    private BeUnit selectUnit;  // 选择的单元
    private List<BeUnit> unitInfoList = new ArrayList<BeUnit>();

    private LinearLayout dataLayout;  // 显示教辅、单元的layout
    private LinearLayout emptyLayout; // 空页面

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setfxTtitle(R.string.task_textbook_assignment);
        onBackText();
        onRightText(R.string.task_supplementary);
        showfxDialog();
        httpData(HTTP_TYPE_GET_WORKBOOK); // 请求取得教辅
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_sendhomework);
        dataLayout = getView(R.id.data_layout);
        emptyLayout = getView(R.id.empty_layout);
        mListview = getView(R.id.listview);
        final TextView mNext = getView(R.id.next);
        mNext.setOnClickListener(onClick);
        mImgSupplementary = getView(R.id.img_supplementary);
        mTvSupplementary = getView(R.id.tv_supplementary);


        mNext.setOnClickListener(onClick);

        apChooseUnit = new ApChooseUnit(this, unitInfoList);
        mListview.setAdapter(apChooseUnit);
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //把点击的position传递到adapter里面去
                apChooseUnit.changeState(position);
                selectUnit = unitInfoList.get(position);

                mNext.setBackgroundResource(R.color.blue_dark); // 修改下一步按钮背景色

                mNext.setBackgroundColor(getResources().getColor(R.color.blue_dark));

//                Toast.makeText(SendHomeworkActivity.this, "" + position, Toast.LENGTH_SHORT).show();
            }
        });






    }

    @Override
    public void onRightBtnClick(View view) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("selectWorkBook", selectWorkBook);
        DjhJumpUtil.getInstance().startBaseActivityForResult(SendHomeworkActivity.this, ChooseSupplementaryActivity.class, bundle, DjhJumpUtil.getInstance().activtiy_ChooseSupplementary);
    }

    private View.OnClickListener onClick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.next:
                    if (selectUnit != null) {
                        Bundle bundle = new Bundle();
                        bundle.putString("bookId", selectWorkBook.getId());
                        bundle.putString("unitId", selectUnit.getId());
                        DjhJumpUtil.getInstance().startBaseActivityForResult(SendHomeworkActivity.this, ChooseClassActivity.class, bundle, DjhJumpUtil.getInstance().activtiy_ChooseClass); // 跳转到发布课本作业页面
                    } else {
//                        Toast.makeText(SendHomeworkActivity.this, "请选择内容", Toast.LENGTH_SHORT).show();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 从别的页面返回
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == DjhJumpUtil.getInstance().activtiy_ChooseSupplementary && resultCode == RESULT_OK) {
            // 从选择教辅页面设置教辅返回
            if (data != null) {
                selectUnit = null;
                apChooseUnit.changeState(-1);
                selectWorkBook = (BeWorkBook) data.getSerializableExtra("workBook");
                mTvSupplementary.setText(selectWorkBook.getName());
                Glide.with(this).load(selectWorkBook.getLogo()).fitCenter().into(mImgSupplementary);
                httpData(HTTP_TYPE_GET_UNIT); // 请求取得单元

                dataLayout.setVisibility(View.VISIBLE);
                emptyLayout.setVisibility(View.GONE);

                mNext.setBackgroundResource(R.color.gray_DBDBDB);  // 修改下一步按钮背景色
            }
        }

        if (requestCode == DjhJumpUtil.getInstance().activtiy_ChooseClass && resultCode == RESULT_OK) { // 布置作业成功返回
            setResult(RESULT_OK);
            finishActivity();
        }

    }

    public void httpData(int httpType) {
        //网络请求
        switch (httpType) {
            case HTTP_TYPE_GET_WORKBOOK: // 取得教辅
                RequestUtill.getInstance().httpGetWorkBook(context, callGetWorkBook); // 请求取得教辅
                break;

            case HTTP_TYPE_GET_UNIT: // 取得单元
                RequestUtill.getInstance().httpGetUnitList(context, callGetUnitList, selectWorkBook.getId()); // 请求取得单元列表
                break;
        }
    }

    /**
     * 取得教辅的callback
     */
    ResultCallback callGetWorkBook = new ResultCallback() {
        @Override
        public void onError(Request request, Exception e) {
            dismissfxDialog();
        }

        @Override
        public void onResponse(String response) {
            dismissfxDialog();

            HeadJson json = new HeadJson(response);
            if (json.getstatus() == 0) {
                /* 解析教辅信息 */
                List<BeWorkBook> temp = json.parsingListArray("data", new GsonType<List<BeWorkBook>>() {
                });
                if (temp != null && temp.size() > 0) {
                    selectWorkBook = temp.get(0);
                    mTvSupplementary.setText(selectWorkBook.getName());
                    Glide.with(context).load(selectWorkBook.getLogo()).into(mImgSupplementary);
                    apChooseUnit.notifyDataSetChanged();
                    httpData(HTTP_TYPE_GET_UNIT);  // 请求取得单元

                    dataLayout.setVisibility(View.VISIBLE);
                    emptyLayout.setVisibility(View.GONE);
                }


            } else {
                ToastUtil.showToast(context, json.getMsg());
            }
        }
    };

    /**
     * 取得单元列表的callback
     */
    ResultCallback callGetUnitList = new ResultCallback() {
        @Override
        public void onError(Request request, Exception e) {
            dismissfxDialog();
        }

        @Override
        public void onResponse(String response) {
            dismissfxDialog();

            HeadJson json = new HeadJson(response);
            if (json.getstatus() == 0) {
                /* 解析Unit列表信息 */
                List<BeUnit> temp = json.parsingListArray("data", new GsonType<List<BeUnit>>() {
                });
                unitInfoList.clear();
                unitInfoList.addAll(temp);
                apChooseUnit.notifyDataSetChanged();

            } else {
                ToastUtil.showToast(context, json.getMsg());
            }
        }
    };

}
