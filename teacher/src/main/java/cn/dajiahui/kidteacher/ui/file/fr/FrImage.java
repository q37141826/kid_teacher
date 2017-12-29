package cn.dajiahui.kidteacher.ui.file.fr;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.fxtx.framework.file.FileUtil;
import com.fxtx.framework.json.GsonType;
import com.fxtx.framework.json.GsonUtil;
import com.fxtx.framework.log.ToastUtil;
import com.fxtx.framework.text.StringUtil;
import com.fxtx.framework.ui.FxFragment;
import com.fxtx.framework.widgets.refresh.MaterialRefreshLayout;

import java.io.File;
import java.util.ArrayList;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.controller.UserController;
import cn.dajiahui.kidteacher.ui.file.OnFragment;
import cn.dajiahui.kidteacher.ui.file.adapter.ApImage;

/**
 * Created by z on 2016/6/16.
 * 图片文件列表
 * .jpg|.jpeg|.png|.gif|.bmp
 */
public class FrImage extends FxFragment {
    private MaterialRefreshLayout refreshLayout;
    private TextView tvNull;
    private GridView gridView;
    private ApImage adapter;
    private ArrayList<File> allFileImg = new ArrayList<>();
    private ArrayList<File> allFileImgTemp = new ArrayList<>();
    public ArrayList<File> selectedImg = new ArrayList<>();
    private final long maxLength = 104857600*2;//200M
    private OnFragment onFragment;
    @Override
    protected View initinitLayout(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fr_image, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        refreshLayout = getView(R.id.refresh);
        tvNull = getView(R.id.tv_null);
        tvNull.setText("暂无图片资料");
        tvNull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showfxDialog();
                httpData();
            }
        });
        gridView = getView(R.id.gridview);
        initRefresh(refreshLayout);
        gridView.setEmptyView(tvNull);
        String data = new FileUtil().getData(UserController.getInstance().getNoticeImgLocalList(getContext()));

        allFileImg = new GsonUtil().getJsonList(data, new GsonType<ArrayList<File>>() {
        });
        if (allFileImg == null) allFileImg = new ArrayList<File>();
        adapter = new ApImage(getActivity(), allFileImg, selectedImg);
        gridView.setAdapter(adapter);
        if (allFileImg.size() > 0) {
            finishRefreshAndLoadMoer(refreshLayout, 1);
            adapter.notifyDataSetChanged();
        } else {
            showfxDialog();
            httpData();
        }
        adapter.setOnPhotoSelectedListener(new ApImage.OnPhotoSelectedListener() {
            @Override
            public void photoClick(ArrayList<File> number) {
                if (selectedImg == null) selectedImg = new ArrayList<File>();
                selectedImg = number;
                if (onFragment!=null)onFragment.onToMessage(2,selectedImg);
            }
        });
    }

    public void setOnFragment(OnFragment onFragment) {
        this.onFragment = onFragment;
    }

    @Override
    public void httpData() {
        super.httpData();
        allFileImgTemp.clear();
        readFile();
    }

    private void readFile() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            new AsyncTask<Void, Void, Void>() {

                @Override
                protected Void doInBackground(Void... params) {
                    readerAllFiles(Environment.getExternalStorageDirectory());
                    return null;
                }

                @Override
                protected void onPostExecute(Void result) {
                    super.onPostExecute(result);
                    dismissfxDialog();
                    allFileImg.clear();
                    allFileImg.addAll(allFileImgTemp);
                    new FileUtil().saveData(UserController.getInstance().getNoticeImgLocalList(getContext()), new GsonUtil().getJsonElement(allFileImg).toString());
                    finishRefreshAndLoadMoer(refreshLayout, 1);
                    if (adapter != null)
                        adapter.notifyDataSetChanged();

                }
            }.execute();
        } else {
            dismissfxDialog();
            ToastUtil.showToast(getContext(), "读取文件失败");
            finishActivity();
        }
    }

    private void readerAllFiles(File dir) {
        File[] fs = dir.listFiles();
        if (fs!=null)
        for (File f : fs) {
            String path = f.getAbsolutePath();
            if ((f.isFile()&&f.length()>0)&&StringUtil.isFileType(path, StringUtil.imageType)) {
                allFileImgTemp.add(f);
            }
            if (!f.getAbsolutePath().startsWith(".") && !f.isHidden() && f.isDirectory()) {
                try {
                    readerAllFiles(f);
                } catch (Exception e) {
                    ToastUtil.showToast(getContext(), "读取文件失败");
                    e.printStackTrace();
                }
            }
        }
    }
}
