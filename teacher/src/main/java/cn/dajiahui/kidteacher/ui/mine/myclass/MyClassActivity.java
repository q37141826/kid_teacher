package cn.dajiahui.kidteacher.ui.mine.myclass;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fxtx.framework.ui.FxActivity;

import java.util.ArrayList;
import java.util.List;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.ui.mine.adapter.ApMyClass;
import cn.dajiahui.kidteacher.ui.mine.bean.BeMyclass;
import cn.dajiahui.kidteacher.util.DjhJumpUtil;

/*
* 我的班级
* */
public class MyClassActivity extends FxActivity {

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setfxTtitle(R.string.mine_my_class);
        onBackText();
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_my_class);

        List<BeMyclass> list = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            list.add(new BeMyclass("二年" + i + "班" , "101" + i, i + "1人"));
        }
        mListView = getView(R.id.listview);
        ApMyClass apMyClass = new ApMyClass(MyClassActivity.this, list);

        mListView.setAdapter(apMyClass);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DjhJumpUtil.getInstance().startBaseActivity(MyClassActivity.this, ClassInfoActivity.class);
            }
        });


    }
}
