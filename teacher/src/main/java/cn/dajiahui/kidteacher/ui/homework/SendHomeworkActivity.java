package cn.dajiahui.kidteacher.ui.homework;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fxtx.framework.ui.FxActivity;

import java.util.ArrayList;
import java.util.List;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.ui.homework.adapter.ApChooseUtils;
import cn.dajiahui.kidteacher.ui.homework.bean.ChooseSupplementary;
import cn.dajiahui.kidteacher.ui.homework.bean.ChooseUtils;
import cn.dajiahui.kidteacher.util.DjhJumpUtil;

/**
 * 发布作业
 */
public class SendHomeworkActivity extends FxActivity {


    private ImageView mImgSupplementary;
    private TextView mTvSupplementary;
    private ListView mListview;
    private String utlisname = "";

    @Override
    protected void initView() {
        setContentView(R.layout.activity_sendhomework);
        mListview = getView(R.id.listview);
        mImgSupplementary = getView(R.id.img_supplementary);
        mTvSupplementary = getView(R.id.tv_supplementary);


        TextView mTitle = getView(R.id.tool_title);
        TextView mRight = getView(R.id.tool_right);
        TextView mLeft = getView(R.id.tool_left);
        TextView mNext = getView(R.id.next);
        mTitle.setText(R.string.task_textbook_assignment);
        mRight.setText(R.string.task_supplementary);
        mLeft.setText(R.string.sign_out);
        final List<ChooseUtils> list = new ArrayList<>();

        for (int a = 0; a < 20; a++) {
            list.add(new ChooseUtils("unit  " + a, ""));
        }

        final ApChooseUtils apChooseUtils = new ApChooseUtils(this, list);
        mListview.setAdapter(apChooseUtils);
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //把点击的position传递到adapter里面去
                apChooseUtils.changeState(position);
                utlisname = list.get(position).getUtlisname();
                Toast.makeText(SendHomeworkActivity.this, "" + position, Toast.LENGTH_SHORT).show();
            }
        });

        mRight.setOnClickListener(onClick);
        mLeft.setOnClickListener(onClick);
        mNext.setOnClickListener(onClick);

    }

    private View.OnClickListener onClick = new View.OnClickListener() {


        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tool_right:
//                    Toast.makeText(getContext(), "教辅设置", Toast.LENGTH_SHORT).show();
                    Bundle bundle = new Bundle();
                    DjhJumpUtil.getInstance().startBaseActivityForResult(SendHomeworkActivity.this, ChooseSupplementaryActivity.class, bundle, 0);


                    break;
                case R.id.tool_left:
                    Toast.makeText(SendHomeworkActivity.this, "退出", Toast.LENGTH_SHORT).show();
                    finishActivity();

                    break;


                case R.id.next:
                    if (!utlisname.equals("")) {
                        DjhJumpUtil.getInstance().startBaseActivity(SendHomeworkActivity.this, ChooseClassActivity.class);
                        finishActivity();
                    } else {
                        Toast.makeText(SendHomeworkActivity.this, "请选择内容", Toast.LENGTH_SHORT).show();
                    }

                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0 && requestCode == 0) {
            Toast.makeText(SendHomeworkActivity.this, "教辅设置完成", Toast.LENGTH_SHORT).show();
            if (data != null) {
                ChooseSupplementary supplementaryname = (ChooseSupplementary) data.getSerializableExtra("supplementaryname");
                mTvSupplementary.setText(supplementaryname.getBookname());
//                GlideUtil.
//                mImgSupplementary.setImageResource(supplementaryname.getImgbook());
                mImgSupplementary.setImageResource(R.drawable.ic_launcher);
            }
        }

    }


}
