package cn.dajiahui.kidteacher.ui.homework;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.fxtx.framework.ui.FxActivity;

import java.util.ArrayList;
import java.util.List;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.ui.homework.adapter.ApChooseSupplementary;
import cn.dajiahui.kidteacher.ui.homework.bean.ChooseSupplementary;
import cn.dajiahui.kidteacher.util.Logger;

/**
 * 选择教辅
 */
public class ChooseSupplementaryActivity extends FxActivity {

    private ChooseSupplementary chooseSupplementary;
    private GridView mGrildview;
    private String imgbook = "";
    private String bookname = "";

    @Override
    protected void initView() {
        setContentView(R.layout.activity_choose_supplementary);
        mGrildview = getView(R.id.grildview);
        TextView mTitle = getView(R.id.tool_title);
        TextView mRight = getView(R.id.tool_right);
        TextView mLeft = getView(R.id.tool_left);
        mTitle.setText(R.string.task_choose_supplementary);
        mRight.setText(R.string.task_choose_ok);
        mLeft.setText(R.string.sign_out);

        final List<ChooseSupplementary> list = new ArrayList<>();
        for (int a = 0; a < 15; a++) {
            chooseSupplementary = new ChooseSupplementary("", "move up " + a, R.drawable.ico_im_ok);
            list.add(chooseSupplementary);
        }

        final ApChooseSupplementary apChooseSupplementary = new ApChooseSupplementary(ChooseSupplementaryActivity.this, list);
        mGrildview.setAdapter(apChooseSupplementary);


        mGrildview.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //把点击的position传递到adapter里面去
                apChooseSupplementary.changeState(position);
                bookname = list.get(position).getBookname();
                imgbook = list.get(position).getImgbook();
                Logger.d("majin", "bookname:" + bookname + "imgbook:" + imgbook + " position:" + position);
            }
        });

        mRight.setOnClickListener(onClick);
        mLeft.setOnClickListener(onClick);

    }

    /*教辅设置点击事件*/
    private View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tool_right:
                    Toast.makeText(ChooseSupplementaryActivity.this, "完成", Toast.LENGTH_SHORT).show();
                    if (!bookname.equals("")) {
                        Intent intent = new Intent();
                        intent.putExtra("supplementaryname", new ChooseSupplementary("", bookname));
                        setResult(0, intent);
                        finishActivity();
                    } else {
                        Toast.makeText(ChooseSupplementaryActivity.this, "请选择教辅", Toast.LENGTH_SHORT).show();
                    }

                    break;
                case R.id.tool_left:
                    Toast.makeText(ChooseSupplementaryActivity.this, "退出", Toast.LENGTH_SHORT).show();
                    finishActivity();
                    break;
                default:
                    break;
            }
        }
    };
}
