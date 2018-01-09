package cn.dajiahui.kidteacher.ui.album;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.fxtx.framework.http.ErrorCode;
import com.fxtx.framework.http.callback.ResultCallback;
import com.fxtx.framework.json.GsonType;
import com.fxtx.framework.json.HeadJson;
import com.fxtx.framework.log.ToastUtil;
import com.fxtx.framework.text.MaxLenghtWatcher;
import com.fxtx.framework.text.StringUtil;
import com.fxtx.framework.ui.FxActivity;
import com.fxtx.framework.widgets.dialog.BottomDialog;
import com.fxtx.framework.widgets.dialog.FxDialog;
import com.fxtx.framework.widgets.refresh.MaterialRefreshLayout;
import com.squareup.okhttp.Request;

import java.util.ArrayList;
import java.util.List;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.controller.Constant;
import cn.dajiahui.kidteacher.controller.UserController;
import cn.dajiahui.kidteacher.http.RequestUtill;
import cn.dajiahui.kidteacher.http.file.AlbumFileUpdate;
import cn.dajiahui.kidteacher.http.file.OnFileUpdate;
import cn.dajiahui.kidteacher.ui.album.adapter.ApAlbumRecyclerView;
import cn.dajiahui.kidteacher.ui.album.adapter.ApAlbumTitle;
import cn.dajiahui.kidteacher.ui.album.bean.BeAlbum;
import cn.dajiahui.kidteacher.ui.album.bean.BeClassAlbum;
import cn.dajiahui.kidteacher.util.DjhJumpUtil;

/**
 * Created by z on 2016/3/10.
 */
public class AlbumActivity extends FxActivity {
    private List<BeClassAlbum> albumList = new ArrayList<BeClassAlbum>();
    private MaterialRefreshLayout refresh;
    private TextView tvNUll;
    public ArrayList<ApAlbumTitle> ablunList = new ArrayList<ApAlbumTitle>();
    public int itemIndex;
    public EditText edAlbum;
    public int albumIndex;
    private RecyclerView recyclerView;
    private ApAlbumRecyclerView adapter;
    private String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userId = getIntent().getStringExtra(Constant.bundle_id);
        onBackText();
        String title = getIntent().getStringExtra(Constant.bundle_title);
        setfxTtitle(title);
        showfxDialog();
        httpData();
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_album);
        refresh = getView(R.id.refresh);
        tvNUll = getView(R.id.tv_null);
        tvNUll.setVisibility(View.GONE);
        recyclerView = getView(R.id.rv_recyclerview);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ApAlbumRecyclerView(this, albumList);
        recyclerView.setAdapter(adapter);

        initRefresh(refresh);
        dialog = new BottomDialog(context, R.layout.dialog_album) {
            @Override
            public void initView() {
                rootView.findViewById(R.id.btn_cancle).setOnClickListener(onClick);
                rootView.findViewById(R.id.btn_updata).setOnClickListener(onClick);
                rootView.findViewById(R.id.btn_create).setOnClickListener(onClick);
            }
        };
        createAlbum = new FxDialog(context) {
            @Override
            public void onRightBtn(int flag) {
                //创建方法 调用创建接口
                BeClassAlbum classAlbum = albumList.get(itemIndex);
                String albumId = "";
                if (flag == 1) {
                    //修复
                    albumId = classAlbum.getList().get(albumIndex).getObjectId();
                }
                String name = edAlbum.getText().toString().trim();
                if (StringUtil.isEmpty(name)) {
                    ToastUtil.showToast(context, R.string.hint_album_name);
                } else {
                    dismiss();
                    httpCreateAlbum(classAlbum.getObjectId(), name, albumId);
                }
            }

            @Override
            public void onLeftBtn(int flag) {
                dismiss();
            }

            @Override
            public boolean isOnClickDismiss() {
                return false;
            }
        };
        edAlbum = (EditText) getLayoutInflater().inflate(R.layout.dialog_album_create, null);
        edAlbum.addTextChangedListener(new MaxLenghtWatcher(10, edAlbum, context));
        createAlbum.addView(edAlbum);
        createAlbum.setTitle(R.string.create_album);
    }

    @Override
    protected void dismissfxDialog(int flag) {
        super.dismissfxDialog(flag);
        tvNUll.setText(R.string.e_class_null);
        if (albumList.size() == 0)
            tvNUll.setVisibility(View.VISIBLE);
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
        RequestUtill.getInstance().httpClassAlbumList(context, new ResultCallback() {
            @Override
            public void onError(Request request, Exception e) {
                dismissfxDialog();
                finishRefreshAndLoadMoer(refresh, 0);
                ToastUtil.showToast(context, ErrorCode.error(e));
            }

            @Override
            public void onResponse(String response) {
                HeadJson json = new HeadJson(response);
                if (json.getstatus() == 0) {
                    List<BeClassAlbum> temp = json.parsingListArray(new GsonType<List<BeClassAlbum>>() {
                    });
                    if (temp != null && temp.size() > 0) {
                        albumList.clear();
                        ablunList.clear();
                        albumList.addAll(temp);
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    ToastUtil.showToast(context, json.getMsg());
                }
                dismissfxDialog();
                finishRefreshAndLoadMoer(refresh, json.getIsLastPage());
            }
        }, userId, UserController.getInstance().getUserId());
    }

    public BottomDialog dialog;
    public FxDialog createAlbum;//创建相册
    private View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dialog.dismiss();
            switch (v.getId()) {
                case R.id.btn_cancle:
                    //取消
                    break;
                case R.id.btn_updata:
                    //上传照片
                    DjhJumpUtil.getInstance().startSelectPhotoActivity(AlbumActivity.this, Constant.Album_Photo_Max);
                    break;
                case R.id.btn_create:
                    //创建相册
                    createAlbum.setTitle(R.string.create_album);
                    createAlbum.show();
                    createAlbum.setFloag(0);
                    edAlbum.setText("");
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == DjhJumpUtil.getInstance().activtiy_SelectPhoto) {
            //获取选择的图片信息
            ArrayList<String> strings = data.getStringArrayListExtra(Constant.bundle_obj);
            BeClassAlbum classAlbum = albumList.get(itemIndex);
            new AlbumFileUpdate(strings, context, classAlbum.getList().get(1).getObjectId(), new OnFileUpdate() {
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


    public void httpCreateAlbum(String classId, String albumName, final String albumId) {
        showfxDialog();
        RequestUtill.getInstance().httpsaveAlbunm(context, new ResultCallback() {
            @Override
            public void onError(Request request, Exception e) {
                dismissfxDialog();
                ToastUtil.showToast(context, ErrorCode.error(e));
            }

            @Override
            public void onResponse(String response) {
                dismissfxDialog();
                HeadJson json = new HeadJson(response);
                if (json.getstatus() == 0) {
                    //创建成功
                    BeAlbum album = json.parsingObject(BeAlbum.class);
                    if (StringUtil.isEmpty(albumId)) {
                        ablunList.get(itemIndex).addAlbum(album);
                    } else {
                        ablunList.get(itemIndex).setAlbum(albumIndex, album);
                    }
                } else {
                    ToastUtil.showToast(context, json.getMsg());
                }
            }
        }, UserController.getInstance().getUserId(), classId, albumName, albumId);
    }
}
