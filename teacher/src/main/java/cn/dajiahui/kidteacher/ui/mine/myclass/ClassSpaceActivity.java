package cn.dajiahui.kidteacher.ui.mine.myclass;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.fxtx.framework.ui.FxActivity;

import java.util.ArrayList;
import java.util.List;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.ui.mine.adapter.ApClassSpace;
import cn.dajiahui.kidteacher.ui.mine.bean.BeclassSpace;
import cn.dajiahui.kidteacher.util.DjhJumpUtil;

/*
* 班级空间
* */
public class ClassSpaceActivity extends FxActivity {
    private ListView mListview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setfxTtitle(R.string.mine_class_space);
        onBackText();
        onRightBtn(R.drawable.ic_launcher, R.string.mine_send_dynamic);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_class_space);
        mListview = getView(R.id.listview);

        List<BeclassSpace> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(new BeclassSpace("二年" + i + "班", "201" + i, "真好" + i));

        }
        ApClassSpace apClassSpace = new ApClassSpace(this, list);

        mListview.setAdapter(apClassSpace);
    }

    @Override
    public void onRightBtnClick(View view) {
        Bundle bundle = new Bundle();
        DjhJumpUtil.getInstance().startBaseActivityForResult(ClassSpaceActivity.this, SendDynamicActivity.class, bundle, 0);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
