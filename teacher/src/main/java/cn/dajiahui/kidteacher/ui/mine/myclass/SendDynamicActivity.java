package cn.dajiahui.kidteacher.ui.mine.myclass;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fxtx.framework.ui.FxActivity;

import java.util.ArrayList;
import java.util.List;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.controller.Constant;
import cn.dajiahui.kidteacher.ui.mine.adapter.ApClassSpacePhoneDynamic;
import cn.dajiahui.kidteacher.ui.mine.bean.BeclassSpace;
import cn.dajiahui.kidteacher.util.DjhJumpUtil;

/*
* 发布动态
* */
public class SendDynamicActivity extends FxActivity {

    private GridView grildview;
    private EditText mInput;
    private List<BeclassSpace> listphoto;
    private TextView tvNUll;
    private ApClassSpacePhoneDynamic apClassSpacePhoneDynamic;
    private Boolean showDefaultImg = true;
    private Boolean haveText = false;
    private Boolean havePhoto = false;
    TextView publishBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setfxTtitle(R.string.mine_dynamic);
        onBack();
//        onRightBtn(R.drawable.ic_launcher, R.string.mine_send);
        if (toolbar != null) {
            publishBtn = getView(com.fxtx.framework.R.id.tool_right);
            publishBtn.setText(R.string.mine_send);
            publishBtn.setVisibility(View.VISIBLE);
            publishBtn.setTextColor(getResources().getColor(R.color.text_gray_df));
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
        mInput = getView(R.id.ed_input);
        mInput.addTextChangedListener(textWatcher);
//        mInput.requestFocus();
//        mInput.setCursorVisible(true);
        grildview = getView(R.id.grildview);
        listphoto = new ArrayList<BeclassSpace>();
        BeclassSpace addBitmap = new BeclassSpace("0", BitmapFactory.decodeResource(getResources(),R.drawable.ico_c_album));
        listphoto.add(addBitmap);
        apClassSpacePhoneDynamic = new ApClassSpacePhoneDynamic(SendDynamicActivity.this, listphoto);
        grildview.setAdapter(apClassSpacePhoneDynamic);
        grildview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position==0 && showDefaultImg){
                    DjhJumpUtil.getInstance().startSelectPhotoActivity(SendDynamicActivity.this, Constant.Alum_send_dynamic - listphoto.size());
                }
            }
        });
        grildview.setEmptyView(tvNUll);

    }

    @Override
    public void onRightBtnClick(View view) {
        Toast.makeText(context, "发布", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == 3001) {
                ArrayList<String> list = data.getStringArrayListExtra("_object");
                if (list.size() > 0) {
                    for (int i = 0; i < list.size(); i++) {
                        String path = list.get(i).toString();
                        Bitmap bmp = getImageThumbnail(path, 100, 100);
                        listphoto.add(new BeclassSpace(bmp));
                    }
                    if (listphoto.size() >= Constant.Alum_send_dynamic) {
                        listphoto.remove(0);
                        showDefaultImg = false;
                        apClassSpacePhoneDynamic.showDefaultImg = false;
                    }
                    apClassSpacePhoneDynamic.notifyDataSetChanged();
                }
            }

            if(showDefaultImg && listphoto.size() < 2) {
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
            publishBtn.setTextColor(getResources().getColor(R.color.text_deepgray)); // 置黑，可发布
        } else {
            publishBtn.setTextColor(getResources().getColor(R.color.text_gray_df));  // 置灰，不能发布
        }
    }

    /**
     * 获取缩略图
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
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return bitmap;
    }

}
