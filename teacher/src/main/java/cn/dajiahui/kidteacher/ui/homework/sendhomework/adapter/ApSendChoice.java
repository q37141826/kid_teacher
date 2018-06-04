package cn.dajiahui.kidteacher.ui.homework.sendhomework.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.ui.homework.bean.BeChoiceOptions;
import cn.dajiahui.kidteacher.ui.homework.bean.ChoiceQuestionModle;


/**
 * 选择题
 */
public class ApSendChoice extends BaseAdapter {
    private int selectorPosition = -1;

    private List<BeChoiceOptions> mPptions;
    private Context context;
    private LayoutInflater mInflater;
    private ChoiceQuestionModle inbasebean;
    private Map<Integer, ShowAnswer> posttionMap = new HashMap<>();


    /*is_answer=0*/
    public ApSendChoice(Context context, List<BeChoiceOptions> mPptions, ChoiceQuestionModle inbasebean) {
        this.mPptions = mPptions;
        this.context = context;
        this.inbasebean = inbasebean;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mPptions.size();
    }

    @Override
    public Object getItem(int position) {
        return mPptions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();

            /*区别答案的类型*/
            if (mPptions.get(position).getType().equals("2")) {//文字答案
                convertView = mInflater.inflate(R.layout.item_choicetext, null);
                holder.img_rightchoice = (ImageView) convertView.findViewById(R.id.img_rightchoice);
                holder.tv_answer = (TextView) convertView.findViewById(R.id.tv_answer);
                holder.tv_choice_text = (TextView) convertView.findViewById(R.id.tv_choice_text);
                holder.choice_root = (RelativeLayout) convertView.findViewById(R.id.choice_root);
                holder.masked_root = (RelativeLayout) convertView.findViewById(R.id.masked_root);

            } else {//图片答案
                convertView = mInflater.inflate(R.layout.item_choicepic, null);
                holder.img_rightchoice = (ImageView) convertView.findViewById(R.id.img_rightchoice);
                holder.img_answer = (ImageView) convertView.findViewById(R.id.img_answer);
                holder.choice_root = (RelativeLayout) convertView.findViewById(R.id.choice_root);
                holder.tv_choice_text = (TextView) convertView.findViewById(R.id.tv_choice_text);
                holder.masked_root = (RelativeLayout) convertView.findViewById(R.id.masked_root);
            }

            if (mPptions.get(position).getType().equals("2")) {//文字答案
                holder.tv_answer.setText(mPptions.get(position).getContent());

            } else {
                Glide.with(context).load(mPptions.get(position).getContent()).asBitmap()
                        .diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.img_answer);

            }
            holder.tv_choice_text.setText(mPptions.get(position).getLabel());
            posttionMap.put(position, new ShowAnswer(position, holder.img_rightchoice));
            convertView.setTag(holder);
        } else {
            //通过调用缓冲视图convertView，然后就可以调用到viewHolder,viewHolder中已经绑定了各个控件，省去了findViewById的步骤
            holder = (ViewHolder) convertView.getTag();
        }

        //如果当前的position等于传过来点击的position,就去改变他的状态
        if (selectorPosition == position) {
            holder.choice_root.setBackgroundResource(R.drawable.select_judge_image);
        } else {
            //其他的恢复原来的状态
            holder.choice_root.setBackgroundResource(R.drawable.noselect_judge_image);
        }

        return convertView;
    }



    class ViewHolder {
        public ImageView img_answer;
        public TextView tv_answer, tv_choice_text ;
        public ImageView img_rightchoice;
        public RelativeLayout choice_root,masked_root;
    }

    class ShowAnswer {
        int position;
        ImageView img_rightchoice;

        public ShowAnswer(int position, ImageView img_rightchoice) {
            this.position = position;
            this.img_rightchoice = img_rightchoice;
        }

        public int getPosition() {
            return position;
        }

        public ImageView getImg_rightchoice() {
            return img_rightchoice;
        }


    }

}