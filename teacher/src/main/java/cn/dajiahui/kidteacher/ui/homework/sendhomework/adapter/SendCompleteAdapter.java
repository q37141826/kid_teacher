package cn.dajiahui.kidteacher.ui.homework.sendhomework.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.LinkedHashMap;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.ui.homework.bean.CompletionQuestionModle;
import cn.dajiahui.kidteacher.ui.homework.bean.CompletionQuestionadapterItemModle;
import cn.dajiahui.kidteacher.util.Logger;


/*填空题 横划listview适配器*/
public class SendCompleteAdapter extends BaseAdapter {
    private Context mContext;
    private int selfposition;//HorizontallList在碎片中的索引（用于取出当前的HorizontallList）
    private CompletionQuestionModle inbasebean;


    public SendCompleteAdapter(Context context, int selfposition, CompletionQuestionModle inbasebean) {//SubmitEditext submitEditext,
        this.mContext = context;
        this.selfposition = selfposition;
        this.inbasebean = inbasebean;
    }

    @Override
    public int getCount() {
        return inbasebean.getmCompletionAllMap().get(selfposition).size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    /*不用优化 要获取每个editext的实例*/
    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

            convertView = LayoutInflater.from(mContext).inflate(R.layout.send_match_league_round_item, parent, false);
        return convertView;
    }

}
