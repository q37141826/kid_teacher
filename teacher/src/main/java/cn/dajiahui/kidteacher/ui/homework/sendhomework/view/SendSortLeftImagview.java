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
public class SendSortLeftImagview extends RelativeLayout {
    private Context context;
    private LayoutParams params;
    private int position;
    private SortQuestionModle inbasebean;


    public SendSortLeftImagview(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @SuppressLint("ResourceAsColor")
    public SendSortLeftImagview(Context context, int position, SortQuestionModle inbasebean) {
        super(context);
        this.context = context;
        this.position = position;
        this.inbasebean = inbasebean;

        this.setBackgroundResource(R.drawable.sortview_default_bg);
        this.setPadding(SortAnswerView_margin, SortAnswerView_margin, SortAnswerView_margin, SortAnswerView_margin);
        params = new LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        ShowCompleteUI();

    }

    /*显示完成视图*/
    private void ShowCompleteUI() {


            String content = inbasebean.getOptions().get(position).getContent();
            /*答案是图片*/
            if (content.startsWith("h", 0) && content.startsWith("t", 1)) {
                ShowImageViewUI();
            } else {
                /*答案是文字*/
                ShowTextViewUI(inbasebean.getOptions().get(position).getContent());
            }

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

    /*显示图片*/
    private void ShowImageViewUI() {
        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(params);
        Glide.with(context).load(inbasebean.getOptions().get(position).getContent()).asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
        this.addView(imageView);
    }


}
