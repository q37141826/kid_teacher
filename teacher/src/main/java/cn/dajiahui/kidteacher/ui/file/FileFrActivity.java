package cn.dajiahui.kidteacher.ui.file;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fxtx.framework.adapter.FgPagerAdapter;
import com.fxtx.framework.file.FileUtil;
import com.fxtx.framework.ui.FxActivity;
import com.fxtx.framework.ui.FxFragment;
import com.fxtx.framework.widgets.ViewPagerFixed;
import com.fxtx.framework.widgets.scrolltitle.BeScorllTitle;
import com.fxtx.framework.widgets.scrolltitle.ScrollTitleBar;
import com.fxtx.framework.widgets.scrolltitle.its.OnScrollTitleListener;
import com.fxtx.framework.widgets.scrolltitle.its.OnTitleClickListener;
import com.fxtx.framework.widgets.scrolltitle.its.OnViewPagerChangeListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.controller.Constant;
import cn.dajiahui.kidteacher.ui.file.fr.FrDocument;
import cn.dajiahui.kidteacher.ui.file.fr.FrImage;
import cn.dajiahui.kidteacher.ui.file.fr.FrMedia;


/**
 * Created by z on 2016/6/16.
 * 选择附件功能
 * 要求：
 * 1可以定义选择的文件大小。2可以限制选择文件数量。
 * 2 可以还原选择的数据。
 */
public class FileFrActivity extends FxActivity {
    private ScrollTitleBar mTitleBar;
    private ViewPagerFixed contentPager;
    private ArrayList<FxFragment> fragmentList = new ArrayList<FxFragment>();
    public static int maxFiles = 5;
    public static long maxFilesSize = 104857600 * 2;//200M
    public int numFiles = 0;
    private FrMedia frMedia;
    private FrImage frImage;
    private FrDocument frDocument;
    private ArrayList<File> mediaFiles = new ArrayList<File>();
    private ArrayList<File> imageFiles = new ArrayList<File>();
    private ArrayList<File> documentFiles = new ArrayList<File>();
    private TextView tvSize;
    private Button butNum;
    private FileUtil fileUtil;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_fr_file);
        mTitleBar = getView(R.id.scrollTitleBar);
        contentPager = getView(R.id.content_pager);
        tvSize = getView(R.id.text_size);
        butNum = getView(R.id.button_send);
        fileUtil = new FileUtil();
        butNum.setOnClickListener(onClick);
        contentPager.setOffscreenPageLimit(3);
        mTitleBar.setTitleClickListener(new OnTitleClickListener() {
            @Override
            public void titleOnClickListener(int position) {
                contentPager.setCurrentItem(position);
            }
        });
        mTitleBar.setTitleView(initCategory(), new OnScrollTitleListener() {
            @Override
            public void lastViewSelect(View view) {
                ((TextView) view).setTextColor(getResources().getColor(
                        R.color.gray));
            }

            @Override
            public void currentViewSelect(View view) {
                ((TextView) view).setTextColor(getResources().getColor(
                        R.color.app_bg));
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onBackText();
        setfxTtitle("本地文件");
        frMedia = new FrMedia();
        frImage = new FrImage();
        frDocument = new FrDocument();
        frMedia.setOnFragment(onFragment);
        frImage.setOnFragment(onFragment);
        frDocument.setOnFragment(onFragment);
        mediaFiles = (ArrayList<File>) getIntent().getSerializableExtra(Constant.bundle_media);
        imageFiles = (ArrayList<File>) getIntent().getSerializableExtra(Constant.bundle_img);
        documentFiles = (ArrayList<File>) getIntent().getSerializableExtra(Constant.bundle_document);
        maxFiles = getIntent().getIntExtra(Constant.bundle_id, maxFiles);
        maxFilesSize = getIntent().getLongExtra(Constant.bundle_type, maxFilesSize);
        if (mediaFiles == null)
            mediaFiles = new ArrayList<File>();
        if (imageFiles == null)
            imageFiles = new ArrayList<File>();
        if (documentFiles == null)
            documentFiles = new ArrayList<File>();
        numFiles = mediaFiles.size() + imageFiles.size() + documentFiles.size();
        dealWithSelectData();
        frMedia.choiseFiles = mediaFiles;
        frImage.selectedImg = imageFiles;
        frDocument.choiseFiles = documentFiles;
        initFragment();
    }

    /**
     * 分类数据生成
     */
    private List<BeScorllTitle> initCategory() {
        List<BeScorllTitle> categories = new ArrayList<BeScorllTitle>();
        categories.add(new BeScorllTitle("影音"));
        categories.add(new BeScorllTitle("照片"));
        categories.add(new BeScorllTitle("文档"));
        return categories;
    }

    //创建Fragment
    private void initFragment() {
        fragmentList.add(frMedia);
        fragmentList.add(frImage);
        fragmentList.add(frDocument);
        FgPagerAdapter adapter = new FgPagerAdapter(
                getSupportFragmentManager(), fragmentList);
        contentPager.setAdapter(adapter);
        contentPager.addOnPageChangeListener(new OnViewPagerChangeListener(
                contentPager, mTitleBar.getItemWidth(), mTitleBar, mTitleBar.getTitleBar()) {
            @Override
            public void moveNextTrue() {
                super.moveNextTrue();
            }
        });
        contentPager.setCurrentItem(0);
    }

    private View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button_send:
//                    Intent intent = new Intent(FileFrActivity.this, CreateNoticeActivity.class);
//                    if (mediaFiles == null) mediaFiles = new ArrayList<File>();
//                    intent.putExtra(Constant.bundle_media, mediaFiles);
//                    if (imageFiles == null) imageFiles = new ArrayList<File>();
//                    intent.putExtra(Constant.bundle_img, imageFiles);
//                    if (documentFiles == null) documentFiles = new ArrayList<File>();
//                    intent.putExtra(Constant.bundle_document, documentFiles);
//                    setResult(RESULT_OK, intent);
//                    finishActivity();
                    break;
            }
        }
    };
    private OnFragment onFragment = new OnFragment() {
        @Override
        public void onToMessage(int state, Object t) {
            switch (state) {
                case 1:
                    //影音
                    mediaFiles = (ArrayList<File>) t;
                    break;
                case 2:
                    //图片
                    imageFiles = (ArrayList<File>) t;
                    break;
                case 3:
                    //文件
                    documentFiles = (ArrayList<File>) t;
                    break;
            }
            dealWithSelectData();
        }
    };

    public void dealWithSelectData() {
        butNum.setText("确定(" + numFiles + ")");
        long size = 0l;
        for (File file : mediaFiles) {
            size += file.length();
        }
        for (File file : imageFiles) {
            size += file.length();
        }
        for (File file : documentFiles) {
            size += file.length();
        }
        tvSize.setText("已选" + fileUtil.FormetFileSize(size));
    }


    @Override
    public void onBackPressed() {
        numFiles = mediaFiles.size() + imageFiles.size() + documentFiles.size();
        super.onBackPressed();
    }
}
