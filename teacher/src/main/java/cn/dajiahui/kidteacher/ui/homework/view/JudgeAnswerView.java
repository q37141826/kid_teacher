package cn.dajiahui.kidteacher.ui.homework.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.ui.homework.bean.JudjeQuestionModle;
import cn.dajiahui.kidteacher.ui.homework.homeworksdetails.JudgeFragment;

import static cn.dajiahui.kidteacher.controller.Constant.JudgeAnswerView_margin;


/**
 * Created by lenovo on 2018/2/7.
 */

@SuppressLint("AppCompatCustomView")
public class JudgeAnswerView extends RelativeLayout {
    private Context context;
    private JudjeQuestionModle inbasebean;
    private int position;
    private JudgeFragment.SubmitJudgeFragment submit;
    private List<JudgeAnswerView> AnswerViewList;
    public String val;

    public JudgeAnswerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    public JudgeAnswerView(Context context, JudjeQuestionModle inbasebean, int position, JudgeFragment.SubmitJudgeFragment submit, List<JudgeAnswerView> AnswerViewList) {
        super(context);
        this.context = context;
        this.inbasebean = inbasebean;
        this.position = position;
        this.submit = submit;
        this.AnswerViewList = AnswerViewList;
        this.val = inbasebean.getOptions().get(position).getVal();
        this.setPadding(JudgeAnswerView_margin, JudgeAnswerView_margin, JudgeAnswerView_margin, JudgeAnswerView_margin);
        this.setBackgroundResource(R.drawable.noselect_judge_image);

        ShowCompleteUI();


    }



    /*已完成*/
    private void ShowCompleteUI() {
        String content = inbasebean.getOptions().get(position).getContent();

        if (content.startsWith("h", 0) && content.startsWith("t", 1)) {
            /*添加图片*/
            addView(ShowImageViewUI(inbasebean.getOptions().get(position).getContent()));
        } else {
            /*添加文字*/
            addView(ShowTextViewUI(inbasebean.getOptions().get(position).getContent()));
        }

    }

    /*未开始*/
    private void ShowNoCompleteUI() {
        String content = inbasebean.getOptions().get(position).getContent();

        if (content.startsWith("h", 0) && content.startsWith("t", 1)) {
            /*添加图片*/
            addView(ShowImageViewUI(inbasebean.getOptions().get(position).getContent()));
        } else {
            /*添加文字*/
            addView(ShowTextViewUI(inbasebean.getOptions().get(position).getContent()));
        }

    }

    /*显示文本*/
    private TextView ShowTextViewUI(String textComtent) {
        ShowYellowShapFrame();
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        TextView textView = new TextView(context);
        textView.setLayoutParams(params);
        textView.setTextColor(getResources().getColor(R.color.gray_333333));
        textView.setText(textComtent);
        return textView;
    }

    /*显示图片*/
    private ImageView ShowImageViewUI(String imgUrl) {
        ShowYellowShapFrame();
        ImageView imageView = new ImageView(context);
        /*加载正确答案按钮图片*/
        Glide.with(context)
                .load(imgUrl)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
        return imageView;
    }

    /*我的答案添加黄色边框*/
    private void ShowYellowShapFrame() {
        /*我的答案添加黄色边框 */
        if (val.equals(inbasebean.getMy_answer())) {
            this.setBackgroundResource(R.drawable.select_judge_image);
        } else {
            /*白色边框表示非选的答案*/
            this.setBackgroundResource(R.drawable.noselect_judge_image);
        }
    }


}
