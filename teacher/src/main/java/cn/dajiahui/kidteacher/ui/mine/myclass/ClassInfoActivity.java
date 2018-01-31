package cn.dajiahui.kidteacher.ui.mine.myclass;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fxtx.framework.http.callback.ResultCallback;
import com.fxtx.framework.json.HeadJson;
import com.fxtx.framework.log.ToastUtil;
import com.fxtx.framework.ui.FxActivity;
import com.squareup.okhttp.Request;

import java.util.ArrayList;
import java.util.List;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.http.RequestUtill;
import cn.dajiahui.kidteacher.ui.mine.adapter.ApMyClassInfo;
import cn.dajiahui.kidteacher.ui.mine.bean.BeMyclassInfo;
import cn.dajiahui.kidteacher.ui.mine.bean.BeStudents;
import cn.dajiahui.kidteacher.util.DjhJumpUtil;

/*
* 班级信息
* */
public class ClassInfoActivity extends FxActivity {

    private final static int MSG_SHOW_FXDIALOG = 1;     // 显示dialog
    private final static int MSG_DISMISS_FXDIALOG = 2;  // 关闭dialog
    private final static int MSG_REFRESH_LIST = 3;  // 刷新列表

    private String classId;
    private ListView mListView;
    private ApMyClassInfo apMyClassInfo;   // 班级详情的Adapter
    private List<BeStudents> studentInfoList = new ArrayList<BeStudents>();

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SHOW_FXDIALOG: // 显示dialog
                    showfxDialog();
                    break;

                case MSG_DISMISS_FXDIALOG: // 关闭dialog
                    dismissfxDialog();
                    break;

                case MSG_REFRESH_LIST: // 刷新列表S
                    apMyClassInfo.notifyDataSetChanged();
                    break;

                default:
                    dismissfxDialog();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setfxTtitle(R.string.mine_class_info);
        onBackText();

        classId = getIntent().getStringExtra("classId");

        apMyClassInfo = new ApMyClassInfo(this, studentInfoList, handler, classId);
        mListView.setAdapter(apMyClassInfo);

        showfxDialog();
        httpData();
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_class_info);

//        TextView mClass = getView(R.id.tv_class);
//        TextView mCode = getView(R.id.tv_code);
//        TextView mCount = getView(R.id.tv_count);
//        ImageView mHead = getView(R.id.img_head);
        TextView mClassspace = getView(R.id.tv_classspace);
        Button mInvitation = getView(R.id.btn_invitation);
        mClassspace.setOnClickListener(onClick);
        mInvitation.setOnClickListener(onClick);
        mListView = getView(R.id.listview);

    }


    private View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
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

    @Override
    public void httpData() {
        //网络请求
        ResultCallback call = new ResultCallback() {
            @Override
            public void onError(Request request, Exception e) {
                dismissfxDialog();
            }

            @Override
            public void onResponse(String response) {
                dismissfxDialog();
                HeadJson json = new HeadJson(response);
                if (json.getstatus() == 0) {
                    /* 解析班级列表信息 */
                    BeMyclassInfo temp = json.parsingObject(BeMyclassInfo.class);

                    TextView clssCode = getView(R.id.tv_code);
                    clssCode.setText(temp.getCode());

                    TextView className = getView(R.id.tv_class);
                    className.setText(temp.getClass_name());

                    TextView studentNumber = getView(R.id.tv_count);
                    studentNumber.setText(temp.getStudents_num() + "/" + temp.getMax_students_num());

                    studentInfoList.addAll(temp.getStudent_list());

                    apMyClassInfo.notifyDataSetChanged();


                } else {
                    ToastUtil.showToast(context, json.getMsg());
                }
            }
        };

        RequestUtill.getInstance().httpGetClassInfo(context, call, classId);
    }
}
