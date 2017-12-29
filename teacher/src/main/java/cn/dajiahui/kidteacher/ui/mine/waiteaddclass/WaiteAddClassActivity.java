package cn.dajiahui.kidteacher.ui.mine.waiteaddclass;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.fxtx.framework.ui.FxActivity;
import com.fxtx.framework.widgets.dialog.FxDialog;

import java.util.ArrayList;
import java.util.List;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.ui.mine.adapter.ApWaiteAddclass;
import cn.dajiahui.kidteacher.ui.mine.bean.BeWaiteAddClass;

/*
* 待加入班级的学生
* */
public class WaiteAddClassActivity extends FxActivity {

    private ListView mListview;
    private ApWaiteAddclass apWaiteAddclass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setfxTtitle(R.string.mine_wait_add_class);
        onBackText();
    }


    @Override
    protected void initView() {
        setContentView(R.layout.activity_waite_add_class);

        final List<BeWaiteAddClass> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(new BeWaiteAddClass("二年" + i + "班", "李四"));

        }

        mListview = getView(R.id.listview);
        apWaiteAddclass = new ApWaiteAddclass(this, list);
        mListview.setAdapter(apWaiteAddclass);

        mListview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {


                FxDialog dialog = new FxDialog(WaiteAddClassActivity.this) {
                    @Override
                    public void onRightBtn(int flag) {
                        Toast.makeText(WaiteAddClassActivity.this, "删除", Toast.LENGTH_SHORT).show();
                        list.remove(list.get(position));
                        apWaiteAddclass.notifyDataSetChanged();
                    }

                    @Override
                    public void onLeftBtn(int flag) {

                    }
                };
                dialog.setTitle(R.string.prompt);
                dialog.setMessage(R.string.prompt_delete);
                dialog.show();

                return false;
            }
        });

    }


}
