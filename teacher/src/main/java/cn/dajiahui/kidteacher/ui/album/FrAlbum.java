package cn.dajiahui.kidteacher.ui.album;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.fxtx.framework.http.ErrorCode;
import com.fxtx.framework.http.callback.ResultCallback;
import com.fxtx.framework.json.GsonType;
import com.fxtx.framework.json.HeadJson;
import com.fxtx.framework.log.ToastUtil;
import com.fxtx.framework.ui.FxFragment;
import com.fxtx.framework.widgets.refresh.MaterialRefreshLayout;
import com.squareup.okhttp.Request;

import java.util.ArrayList;
import java.util.List;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.controller.Constant;
import cn.dajiahui.kidteacher.http.RequestUtill;
import cn.dajiahui.kidteacher.ui.album.adapter.ApAlbum;
import cn.dajiahui.kidteacher.ui.album.bean.BeAlbum;
import cn.dajiahui.kidteacher.util.DjhJumpUtil;

/**
 * Created by z on 2016/3/7.
 * 相册信息界面
 */
public class FrAlbum extends FxFragment {
    private GridView gridview;
    private MaterialRefreshLayout refresh;
    private TextView tvNUll;
    private List<BeAlbum> ablums = new ArrayList<BeAlbum>();
    private ApAlbum adapter;
    private String classId;
    private int isMyClass;

    @Override
    protected View initinitLayout(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fr_class_photo, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        classId = bundle.getString(Constant.bundle_id);
        isMyClass = bundle.getInt(Constant.bundle_obj);
        refresh = getView(R.id.refresh);
        tvNUll = getView(R.id.tv_null);
        gridview = getView(R.id.gridview);
        initRefresh(refresh);
        adapter = new ApAlbum(getContext(), ablums);
        gridview.setAdapter(adapter);
        gridview.setEmptyView(tvNUll);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BeAlbum album = adapter.getItem(position);
                DjhJumpUtil.getInstance().startPhotoActivity(getContext(), album.getObjectId(), album.getName(),isMyClass);
            }
        });
        showfxDialog();
        httpData();
    }


    @Override
    protected void dismissfxDialog(int flag) {
        super.dismissfxDialog(flag);
        tvNUll.setText(R.string.e_class_null);
        tvNUll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showfxDialog();
                httpData();
            }
        });
    }

    @Override
    public void httpData() {
        RequestUtill.getInstance().httpMyClassAlbumList(getContext(), new ResultCallback() {
            @Override
            public void onError(Request request, Exception e) {
                dismissfxDialog();
                finishRefreshAndLoadMoer(refresh, 1);
                ToastUtil.showToast(getContext(), ErrorCode.error(e));
            }

            @Override
            public void onResponse(String response) {
                dismissfxDialog();
                HeadJson json = new HeadJson(response);
                if (json.getstatus() == 0) {
                    if (mPageNum == 1) {
                        ablums.clear();
                    }
                    List<BeAlbum> temp = json.parsingListArray(new GsonType<List<BeAlbum>>() {
                    });
                    if (temp != null && temp.size() > 0) {
                        mPageNum++;
                        ablums.addAll(temp);
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    ToastUtil.showToast(getContext(), json.getMsg());
                }
                finishRefreshAndLoadMoer(refresh,1);
            }
        }, classId, mPageNum, "30");
    }
}
