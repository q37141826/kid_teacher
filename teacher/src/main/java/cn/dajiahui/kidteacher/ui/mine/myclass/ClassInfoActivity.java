package cn.dajiahui.kidteacher.ui.mine.myclass;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fxtx.framework.ui.FxActivity;

import java.util.ArrayList;
import java.util.List;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.ui.mine.adapter.ApMyClassInfo;
import cn.dajiahui.kidteacher.ui.mine.bean.BeMyclassInfo;
import cn.dajiahui.kidteacher.util.DjhJumpUtil;

/*
* 班级信息
* */
public class ClassInfoActivity extends FxActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setfxTtitle(R.string.mine_class_info);
        onBackText();

    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_class_info);

        TextView mClass = getView(R.id.tv_class);
        TextView mCode = getView(R.id.tv_code);
        TextView mCount = getView(R.id.tv_count);
        ImageView mHead = getView(R.id.img_head);
        TextView mClassspace = getView(R.id.tv_classspace);
        Button mInvitation = getView(R.id.btn_invitation);
        ListView mListview = getView(R.id.listview);
        mClassspace.setOnClickListener(onClick);
        mInvitation.setOnClickListener(onClick);


        List<BeMyclassInfo> list = new ArrayList<>();

        for (int i = 0; i < 20; i++) {

            list.add(new BeMyclassInfo("", "张三" + i));

        }
        ApMyClassInfo apMyClassInfo = new ApMyClassInfo(this, list);

        mListview.setAdapter(apMyClassInfo);

    }


    private View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
//                case R.id.tool_right:
//
//                    break;
                case R.id.tv_classspace:
                    DjhJumpUtil.getInstance().startBaseActivity(ClassInfoActivity.this, ClassSpaceActivity.class);

                    break;
                case R.id.btn_invitation:
                    Toast.makeText(context, "邀请学员", Toast.LENGTH_SHORT).show();
                    break;


                default:
                    break;
            }
        }
    };
}
