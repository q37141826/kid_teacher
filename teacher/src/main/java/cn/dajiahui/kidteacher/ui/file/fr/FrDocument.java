package cn.dajiahui.kidteacher.ui.file.fr;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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
import cn.dajiahui.kidteacher.ui.file.FileFrActivity;
import cn.dajiahui.kidteacher.ui.file.OnFragment;
import cn.dajiahui.kidteacher.ui.file.adapter.ApFileAndDocument;

/**
 * Created by z on 2016/6/16.
 * 文档文件列表*(.pdf)"*(.doc|.docx)(.ppt|.pptx|.dps)*(.xls|.xlsx)*(.wps)*(.txt)
 */
public class FrDocument extends FxFragment {
    private MaterialRefreshLayout refreshLayout;
    private TextView tvNull;
    private ListView listView;
    public ArrayList<File> choiseFiles = new ArrayList<File>();
    private ArrayList<File> allFiles = new ArrayList<File>();
    private ArrayList<File> allFilestemp = new ArrayList<File>();
    private ApFileAndDocument adapter;
    private final long maxLength = 104857600;//100M
    private OnFragment onFragment;
    @Override
    protected View initinitLayout(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fr_document, null);
    }
    public void setOnFragment(OnFragment onFragment) {
        this.onFragment = onFragment;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        refreshLayout = getView(R.id.refresh);
        tvNull = getView(R.id.tv_null);
        tvNull.setText("暂无文件资料");
        tvNull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showfxDialog();
                httpData();
            }
        });
        listView = getView(R.id.listview);
        initRefresh(refreshLayout);
        listView.setEmptyView(tvNull);
        String data = new FileUtil().getData(UserController.getInstance().getNoticeFileLocalList(getContext()));

        allFiles = new GsonUtil().getJsonList(data, new GsonType<ArrayList<File>>() {
        });
        if (allFiles == null) allFiles = new ArrayList<File>();
        adapter = new ApFileAndDocument(getActivity(), allFiles, choiseFiles);
        listView.setAdapter(adapter);
        if (allFiles.size() > 0) {
            finishRefreshAndLoadMoer(refreshLayout, 1);
            adapter.notifyDataSetChanged();
        } else {
            showfxDialog();
            httpData();
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                File file = adapter.getItem(position);
                if (file.length() > FileFrActivity.maxFilesSize) {
                    ToastUtil.showToast(getContext(), Html.fromHtml(getString(R.string.file_max_lengths, FileFrActivity.maxFilesSize / 1024 / 1024)).toString());

                } else {
                    View v = getViewByPosition(position,listView);
                    TextView tvName = (TextView) v.findViewById(R.id.tvName);
                    adapter.addFile(file,tvName);
                }
                if (choiseFiles==null)choiseFiles = new ArrayList<File>();
                choiseFiles = adapter.selectFile;
                if (onFragment!=null)onFragment.onToMessage(3,choiseFiles);
            }
        });
    }
    public View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }
    @Override
    public void httpData() {
        allFilestemp.clear();
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
                    allFiles.clear();
                    allFiles .addAll(allFilestemp);
                    new FileUtil().saveData(UserController.getInstance().getNoticeFileLocalList(getContext()), new GsonUtil().getJsonElement(allFiles).toString());
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

    /**
     * 读取手机中文件
     *
     * @param dir
     */
    private void readerAllFiles(File dir) {
        File[] fs = dir.listFiles();
        if (fs!=null)
        for (File f : fs) {
            String path = f.getAbsolutePath();
            if ((f.isFile()&&f.length()>0)&&(StringUtil.isFileType(path, StringUtil.wpsType) || StringUtil.isFileType(path, StringUtil.pptType) ||
                    StringUtil.isFileType(path, StringUtil.pdfType) || StringUtil.isFileType(path, StringUtil.wordType)
                    || StringUtil.isFileType(path, StringUtil.excleType))) {
                allFilestemp.add(f);
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
