package cn.dajiahui.kidteacher.ui.mine.myclass;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
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
    private ImageView mImgAdd;
    private EditText mInput;
    private List<BeclassSpace> listphoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setfxTtitle(R.string.mine_dynamic);
        onBackText();
        onRightBtn(R.drawable.ic_launcher, R.string.mine_send);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_send_dynamic);
        mInput = getView(R.id.ed_input);
        mImgAdd = getView(R.id.img_add);
        grildview = getView(R.id.grildview);
        listphoto = new ArrayList<>();
        mImgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DjhJumpUtil.getInstance().startSelectPhotoActivity(SendDynamicActivity.this, Constant.Alum_send_dynamic);

            }
        });


    }

    @Override
    public void onRightBtnClick(View view) {
        Toast.makeText(context, "发布", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 3001) {
            mImgAdd.setVisibility(View.GONE);
            grildview.setVisibility(View.VISIBLE);
            ArrayList<String> list = data.getStringArrayListExtra("_object");

            for (int i = 0; i < list.size(); i++) {

                listphoto.add(new BeclassSpace(BitmapFactory.decodeFile(list.get(i).toString())));
            }
            ApClassSpacePhoneDynamic apClassSpacePhoneDynamic = new ApClassSpacePhoneDynamic(SendDynamicActivity.this, listphoto);
            grildview.setAdapter(apClassSpacePhoneDynamic);


        }
    }


}
