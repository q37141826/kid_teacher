package cn.dajiahui.kidteacher.ui.mine.myclass;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.fxtx.framework.http.ErrorCode;
import com.fxtx.framework.http.callback.ResultCallback;
import com.fxtx.framework.json.HeadJson;
import com.fxtx.framework.log.ToastUtil;
import com.fxtx.framework.ui.FxActivity;
import com.squareup.okhttp.Request;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.controller.Constant;
import cn.dajiahui.kidteacher.http.RequestUtill;
import cn.dajiahui.kidteacher.ui.mine.adapter.ApClassSpacePhoneDynamic;
import cn.dajiahui.kidteacher.ui.mine.bean.BeClassSpace;
import cn.dajiahui.kidteacher.ui.mine.bean.BeUpUserIcon;
import cn.dajiahui.kidteacher.util.DjhJumpUtil;
import cn.dajiahui.kidteacher.util.Logger;

/*
* 发布动态
* */
public class SendDynamicActivity extends FxActivity {

    private GridView grildview;
    private EditText mInput;
    private List<BeClassSpace> listphoto;
    private TextView tvNUll;
    private ApClassSpacePhoneDynamic apClassSpacePhoneDynamic;
    private Boolean showDefaultImg = true;
    private Boolean haveText = false;
    private Boolean havePhoto = false;
    private TextView publishBtn;
    private ArrayList<String> mPhotolist;//图片集合
    private ArrayList<String> mPhotoUrl = new ArrayList<>();
    private String classId;
    private String mInpuText;

    private List<File> upList = new ArrayList<>();//上传图片的集合

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setfxTtitle(R.string.mine_dynamic);
        onBack();
        if (toolbar != null) {
            publishBtn = getView(com.fxtx.framework.R.id.tool_right);
            publishBtn.setText(R.string.mine_send);
            publishBtn.setVisibility(View.VISIBLE);
//            publishBtn.setTextColor(getResources().getColor(R.color.white));
            publishBtn.setTextColor(Color.argb(40, 255, 255, 255));   //文字透明度
            publishBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRightBtnClick(v);
                }
            });
        }
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_send_dynamic);
        classId = getIntent().getStringExtra("classId");
        mInput = getView(R.id.ed_input);
        mInput.addTextChangedListener(textWatcher);
        grildview = getView(R.id.grildview);
        listphoto = new ArrayList<BeClassSpace>();
        BeClassSpace addBitmap = new BeClassSpace(BitmapFactory.decodeResource(getResources(), R.drawable.ico_c_album));
        listphoto.add(addBitmap);
        apClassSpacePhoneDynamic = new ApClassSpacePhoneDynamic(SendDynamicActivity.this, listphoto);
        grildview.setAdapter(apClassSpacePhoneDynamic);
        grildview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0 && showDefaultImg) {
                    DjhJumpUtil.getInstance().startSelectPhotoActivity(SendDynamicActivity.this, Constant.Alum_send_dynamic - listphoto.size());
                }
            }
        });
        grildview.setEmptyView(tvNUll);

    }

    @Override
    public void onRightBtnClick(View view) {

        mInpuText = mInput.getText().toString();

        Logger.d("mPhotoUrl.toArray().toString():" + mPhotoUrl.toArray().toString());

            /*图片和文字*/
        if (upList.size() > 0) {
            for (int i = 0; i < upList.size(); i++) {
                httpUserIcon(upList.get(i));
            }
        } else {
            if(mInpuText.length()>0){
                /*单发文字*/
                httpSendDynamic();
            }
        }
    }

    /*发布动态*/
    private void httpSendDynamic() {

        RequestUtill.getInstance().httpSendDynamic(SendDynamicActivity.this, callSendDynamic, classId, mInpuText, mPhotoUrl);


    }

    /*班级空间*/
    ResultCallback callSendDynamic = new ResultCallback() {
        @Override
        public void onError(Request request, Exception e) {
            dismissfxDialog();
            Logger.d("发布动态失败返回 ：" + e.toString());
        }

        @Override
        public void onResponse(String response) {

            Logger.d("发布动态成功返回 ：" + response);

            dismissfxDialog();
            HeadJson json = new HeadJson(response);
            if (json.getstatus() == 0) {
                setResult(1);
                finishActivity();
                sendNum = 0;
            } else {
                sendNum = 0;
                ToastUtil.showToast(SendDynamicActivity.this, json.getMsg());
            }

        }

    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == 3001) {
                mPhotolist = data.getStringArrayListExtra("_object");
                if (mPhotolist.size() > 0) {
                    for (int i = 0; i < mPhotolist.size(); i++) {
                        String path = mPhotolist.get(i).toString();
                        Bitmap bmp = getImageThumbnail(path, 100, 100);
                        listphoto.add(new BeClassSpace(bmp));
                        File file = new File(path);
                        upList.add(file);
//                        httpUserIcon(file);
                    }
                    if (listphoto.size() >= Constant.Alum_send_dynamic) {
                        listphoto.remove(0);
                        showDefaultImg = false;
                        apClassSpacePhoneDynamic.showDefaultImg = false;
                    }
                    apClassSpacePhoneDynamic.notifyDataSetChanged();
                }
            }

            if (showDefaultImg && listphoto.size() < 2) {
                havePhoto = false;
            } else {
                havePhoto = true;
            }
            setPublishBtn();
        }
    }

    private TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() > 0) {
                haveText = true;
            } else {
                haveText = false;
            }
            setPublishBtn();
        }
    };

    public void setPublishBtn() {
        if (havePhoto || haveText) {
            publishBtn.setTextColor(getResources().getColor(R.color.white)); // 置白，可发布
        } else {
//            publishBtn.setTextColor(getResources().getColor(R.color.white));  // 置白，不能发布+30%透明度
            publishBtn.setTextColor(Color.argb(40, 255, 255, 255));   //文字透明度
        }
    }

    /**
     * 获取缩略图
     *
     * @param imagePath:文件路径
     * @param width:缩略图宽度
     * @param height:缩略图高度
     * @return
     */
    public static Bitmap getImageThumbnail(String imagePath, int width, int height) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; //关于inJustDecodeBounds的作用将在下文叙述
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);
        int h = options.outHeight;//获取图片高度
        int w = options.outWidth;//获取图片宽度
        int scaleWidth = w / width; //计算宽度缩放比
        int scaleHeight = h / height; //计算高度缩放比
        int scale = 1;//初始缩放比
        if (scaleWidth < scaleHeight) {//选择合适的缩放比
            scale = scaleWidth;
        } else {
            scale = scaleHeight;
        }
        if (scale <= 0) {//判断缩放比是否符合条件
            scale = 1;
        }
        options.inSampleSize = scale;
        // 重新读入图片，读取缩放后的bitmap，注意这次要把inJustDecodeBounds 设为 false
        options.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(imagePath, options);
        // 利用ThumbnailUtils来创建缩略图，这里要指定要缩放哪个Bitmap对象
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return bitmap;
    }


    private int sendNum = 0;//发送服务器返回成功的标志

    public void httpUserIcon(File file) {
        showfxDialog(R.string.submiting);
        RequestUtill.getInstance().uploadUserIcon(context, new ResultCallback() {
            @Override
            public void onError(Request request, Exception e) {
                Logger.d("图片上传失败！");

                dismissfxDialog();
                ToastUtil.showToast(context, ErrorCode.error(e));
            }

            @Override
            public void onResponse(String response) {
                Logger.d("图片上传成功！");
//                dismissfxDialog();

                HeadJson headJson = new HeadJson(response);
                if (headJson.getstatus() == 0) {
                    String url = headJson.parsingObject(BeUpUserIcon.class).getUrl();
                    mPhotoUrl.add(url);
                    sendNum++;
                    if (sendNum == upList.size()) {
                        httpSendDynamic();
                        Logger.d("发布的成功的计数：" + sendNum);
                    }

                } else {
//                    ToastUtil.showToast(context, headJson.getMsg());
                }
            }
        }, file);
    }
}
