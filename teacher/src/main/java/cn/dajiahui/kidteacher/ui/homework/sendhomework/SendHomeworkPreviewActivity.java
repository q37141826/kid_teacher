package cn.dajiahui.kidteacher.ui.homework.sendhomework;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.fxtx.framework.ui.FxActivity;
import com.fxtx.framework.util.ActivityUtil;

import java.util.ArrayList;
import java.util.List;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.controller.Constant;
import cn.dajiahui.kidteacher.ui.homework.ChooseClassActivity;
import cn.dajiahui.kidteacher.ui.homework.SendHomeworkActivity;
import cn.dajiahui.kidteacher.ui.homework.bean.ChoiceQuestionModle;
import cn.dajiahui.kidteacher.ui.homework.bean.CompletionQuestionModle;
import cn.dajiahui.kidteacher.ui.homework.bean.JudjeQuestionModle;
import cn.dajiahui.kidteacher.ui.homework.bean.LineQuestionModle;
import cn.dajiahui.kidteacher.ui.homework.bean.QuestionModle;
import cn.dajiahui.kidteacher.ui.homework.bean.SortQuestionModle;
import cn.dajiahui.kidteacher.ui.homework.sendhomework.adapter.ApHomeWorkPreview;
import cn.dajiahui.kidteacher.ui.homework.sendhomework.bean.BeHomeworkPreView;
import cn.dajiahui.kidteacher.ui.homework.sendhomework.bean.BeSendHomeWorkPreview;
import cn.dajiahui.kidteacher.util.DjhJumpUtil;
import cn.dajiahui.kidteacher.util.Logger;


/*
 *
 * 发送作业预览界面
 *
 * */
public class SendHomeworkPreviewActivity extends FxActivity {

    private BeSendHomeWorkPreview beSendHomeWorkPreview;
    private String book_id;
    private String unit_id;
    private GridView grildview;
    private Button mBtnNext;

    private List<BeHomeworkPreView> bePreViewList = new ArrayList<>();
    private TextView tvChooseOk;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setfxTtitle(R.string.Look);
        onBackText();
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_send_homework_preview);
        initialize();

        Bundle bundle = getIntent().getExtras();

        beSendHomeWorkPreview = (BeSendHomeWorkPreview) bundle.getSerializable("SENDHOMEWORKPREVIEW");

        book_id = beSendHomeWorkPreview.getBook_id();
        unit_id = beSendHomeWorkPreview.getUnit_id();

        bePreViewList  = beSendHomeWorkPreview.getBePreViewList();
        ApHomeWorkPreview apHomeWorkDetail = new ApHomeWorkPreview(SendHomeworkPreviewActivity.this, bePreViewList);

        grildview.setAdapter(apHomeWorkDetail);



        /*item条目点击事件*/
        grildview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent();
                intent.putExtra("current_num", bePreViewList.get(position).getItem_position());

                setResult(DjhJumpUtil.getInstance().activity_sendhomeworkPreviewresultCode, intent);

                finishActivity();
            }
        });

    }


    /*初始化*/
    private void initialize() {
        grildview = (GridView) findViewById(R.id.grildview);
        tvChooseOk = getView(R.id.choose_ok);
        mBtnNext = (Button) findViewById(R.id.btn_next);
        mBtnNext.setOnClickListener(onClick);
    }

    private View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {

                case R.id.btn_next:
                    if (bePreViewList.size() > 0) {
                        Bundle bundle = new Bundle();
                        bundle.putString("bookId", book_id);
                        bundle.putString("unitId", unit_id);
                        bundle.putSerializable("SENDHOMEWORKQUESTION", new BeSendHomeWorkPreview(bePreViewList));
                        DjhJumpUtil.getInstance().startBaseActivityForResult(SendHomeworkPreviewActivity.this, ChooseClassActivity.class, bundle, DjhJumpUtil.getInstance().activtiy_ChooseClass); // 跳转到发布课本作业页面


                    } else {
                        Toast.makeText(context, "请选择要布置的作业", Toast.LENGTH_SHORT).show();
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
        /*选择预览界面回传*/
        if (requestCode == DjhJumpUtil.getInstance().activtiy_ChooseClass && resultCode == RESULT_OK) {

            setResult(RESULT_OK);
            finishActivity();
        }
    }
}
