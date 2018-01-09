package cn.dajiahui.kidteacher.ui.album;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.fxtx.framework.http.ErrorCode;
import com.fxtx.framework.http.callback.ResultCallback;
import com.fxtx.framework.json.GsonType;
import com.fxtx.framework.json.HeadJson;
import com.fxtx.framework.log.ToastUtil;
import com.fxtx.framework.ui.FxActivity;
import com.fxtx.framework.widgets.refresh.MaterialRefreshLayout;
import com.squareup.okhttp.Request;

import java.util.ArrayList;
import java.util.List;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.controller.Constant;
import cn.dajiahui.kidteacher.controller.UserController;
import cn.dajiahui.kidteacher.http.file.AlbumFileUpdate;
import cn.dajiahui.kidteacher.http.file.OnFileUpdate;
import cn.dajiahui.kidteacher.http.RequestUtill;
import cn.dajiahui.kidteacher.ui.album.adapter.ApPhoto;
import cn.dajiahui.kidteacher.ui.album.bean.BePhoto;
import cn.dajiahui.kidteacher.util.DjhJumpUtil;

/**
 * Created by z on 2016/3/10.
 * 图片列表界面
 */
public class PhotoActivity extends FxActivity {
    private GridView gridview;
    private MaterialRefreshLayout refresh;
    private TextView tvNUll;
    private ArrayList<BePhoto> photos = new ArrayList<BePhoto>();//相册列表对象
    private ApPhoto adapter;
    private String albumId;//相册id
    private String albumTitle;//相册标题
    private int isMyClass;

    @Override
    protected void initView() {
        albumId = getIntent().getStringExtra(Constant.bundle_id);
        albumTitle = getIntent().getStringExtra(Constant.bundle_title);
        isMyClass = getIntent().getIntExtra(Constant.bundle_obj, -1);
        setContentView(R.layout.activity_photo_list);
        refresh = getView(R.id.refresh);
        tvNUll = getView(R.id.tv_null);
        gridview = getView(R.id.gridview);
        initRefresh(refresh);
        adapter = new ApPhoto(context, photos, isMyClass);
        gridview.setAdapter(adapter);
        gridview.setEmptyView(tvNUll);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position==0&&1==isMyClass){
                    DjhJumpUtil.getInstance().startSelectPhotoActivity(PhotoActivity.this, Constant.Album_Photo_Max);
                }else if (1==isMyClass){
                    DjhJumpUtil.getInstance().startPhotoPageActivity(context, (ArrayList<BePhoto>) photos, position - 1, true);
                }else {
                    DjhJumpUtil.getInstance().startPhotoPageActivity(context, (ArrayList<BePhoto>) photos, position , true);
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == DjhJumpUtil.getInstance().activity_PhotoPage) {
                mPageNum = 1;
                showfxDialog();
                httpData();
            }
            if (requestCode == DjhJumpUtil.getInstance().activtiy_SelectPhoto) {
                //选择成功后
                ArrayList<String> strings = data.getStringArrayListExtra(Constant.bundle_obj);
                new AlbumFileUpdate(strings, context, albumId, new OnFileUpdate() {
                    @Override
                    public void successful() {
                        //结束了上传操作
                        mPageNum = 1;
                        showfxDialog();
                        httpData();
                    }
                }).startUpdate();
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setfxTtitle(albumTitle);
        onBackText();
        showfxDialog();
        httpData();
    }

    @Override
    protected void dismissfxDialog(int flag) {
        super.dismissfxDialog(flag);
        tvNUll.setText(R.string.e_photo_null);
        tvNUll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPageNum = 1;
                showfxDialog();
                httpData();
            }
        });
    }

    @Override
    public void httpData() {
        RequestUtill.getInstance().httpPictureList(context, new ResultCallback() {
            @Override
            public void onError(Request request, Exception e) {
                dismissfxDialog();
                finishRefreshAndLoadMoer(refresh, 0);
                ToastUtil.showToast(context, ErrorCode.error(e));
            }

            @Override
            public void onResponse(String response) {
                dismissfxDialog();
                HeadJson json = new HeadJson(response);
                if (json.getstatus() == 0) {
                    if (mPageNum == 1) {
                        photos.clear();
                        if (isMyClass == 1)
                            photos.add(new BePhoto());//添加第一个对象
                    }
                    List<BePhoto> temp = json.parsingListArray(new GsonType<List<BePhoto>>() {
                    });
                    if (temp != null && temp.size() > 0) {
                        mPageNum++;
                        photos.addAll(temp);
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    ToastUtil.showToast(context, json.getMsg());
                }
                finishRefreshAndLoadMoer(refresh, json.getIsLastPage());
            }
        }, albumId, UserController.getInstance().getUserId(), mPageNum, "30");
    }

}
