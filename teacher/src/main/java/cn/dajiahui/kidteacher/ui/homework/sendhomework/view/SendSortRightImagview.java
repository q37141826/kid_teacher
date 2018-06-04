package cn.dajiahui.kidteacher.ui.homework.sendhomework.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.ui.homework.bean.SortQuestionModle;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static cn.dajiahui.kidteacher.controller.Constant.SortAnswerView_margin;


/**
 * Created by lenovo on 2018/1/16.
 * 不可滑動的imageview
 */

@SuppressLint("AppCompatCustomView")
public class SendSortRightImagview extends RelativeLayout {
    private Context context;
    private LayoutParams params;
    private int position;

    public SendSortRightImagview(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @SuppressLint("ResourceAsColor")
    public SendSortRightImagview(Context context, int position) {
        super(context);
        this.context = context;
        this.position = position;

        this.setBackgroundResource(R.drawable.sortview_default_bg);
        this.setPadding(SortAnswerView_margin, SortAnswerView_margin, SortAnswerView_margin, SortAnswerView_margin);
        params = new LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        ShowNoCompleteUI();

    }

    /*显示未完成视图*/
    private void ShowNoCompleteUI() {
        ShowTextViewUI((position + 1) + "");
    }


    /*显示文本*/
    private void ShowTextViewUI(String textComtent) {
        TextView textview = new TextView(context);
        textview.setText(textComtent);
        textview.setLayoutParams(params);
        params.addRule(RelativeLayout.CENTER_IN_PARENT, TRUE);
        textview.setTextColor(context.getResources().getColor(R.color.gray_9c9c9c));
        this.addView(textview);
    }
}
