package cn.dajiahui.kidteacher.ui.homework.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.fxtx.framework.log.Logger;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.ui.homework.bean.CompletionQuestionModle;
import cn.dajiahui.kidteacher.ui.homework.bean.CompletionQuestionadapterItemModle;
import cn.dajiahui.kidteacher.ui.homework.myinterface.SubmitEditext;


/*填空题 横划listview适配器*/
public class HorizontallListViewAdapter extends BaseAdapter {
    private Context mContext;
//    private final SubmitEditext submitEditext;
    private MyFoucus myFoucus;
//    private EditChangedListener editChangedListener;//editext监听器
    public LinkedHashMap<Integer, CompletionQuestionadapterItemModle> inputContainer = new LinkedHashMap();//存editext的集合
    private int selfposition;//HorizontallList在碎片中的索引（用于取出当前的HorizontallList）
    private String haveFocus = "";//用于网络请求后清空editext所有焦点
    public String IsShowRightAnswer = "";//是否显示editext
    private CompletionQuestionModle inbasebean;


    public void setInputContainer(LinkedHashMap<Integer, CompletionQuestionadapterItemModle> inputContainer) {
        if (inputContainer != null) {
            if (this.inputContainer == null) {
                this.inputContainer = new LinkedHashMap();
            }
            for (int a = 0; a < inputContainer.size(); a++) {
                this.inputContainer.put(a, inputContainer.get(a));
            }
        }
        notifyDataSetChanged();
    }

    public HorizontallListViewAdapter(Context context,  int selfposition, CompletionQuestionModle inbasebean) {//SubmitEditext submitEditext,
        this.mContext = context;
//        this.submitEditext = submitEditext;
        this.selfposition = selfposition;
        this.haveFocus = inbasebean.getIsFocusable();
        this.IsShowRightAnswer = inbasebean.getIsShowRightAnswer();
        this.inbasebean = inbasebean;
        myFoucus = new MyFoucus();
//        editChangedListener = new EditChangedListener();

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
        HolderView holderView = null;

        if (convertView == null) {

            holderView = new HolderView();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.match_league_round_item, parent, false);
            holderView.editext = (EditText) convertView.findViewById(R.id.editext);
            holderView.tv_rightanswer = (TextView) convertView.findViewById(R.id.tv_rightanswer);
            // 注册上自己写的焦点监听
            holderView.editext.setOnFocusChangeListener(myFoucus);
            holderView.editext.setLongClickable(false);
            convertView.setTag(holderView);
        } else {
            holderView = (HolderView) convertView.getTag();

        }

        // setTag是个好东西呀，把position放上去，一会用
        holderView.editext.setTag(position);


        View currentFocus = ((Activity) mContext).getCurrentFocus();
        if (currentFocus != null) {
            currentFocus.clearFocus();
        }


//        holderView.editext.removeTextChangedListener(editChangedListener);

        if (this.inputContainer.containsKey(position)) {
            if (this.inputContainer.get(position) != null) {
                if (this.inputContainer.get(position).getShowItemMy().toString().equals("㊒")) {
                    holderView.editext.setText("");
                } else {
                    holderView.editext.setText(this.inputContainer.get(position).toString());
                }
            }
        } else {
            holderView.editext.setText("");
        }

        if (IsShowRightAnswer.equals("no")) {
            /*不显示正确答案*/
            holderView.tv_rightanswer.setText("");
        }
        /*显示正确答案*/
        else if (IsShowRightAnswer.equals("yes")) {

            if (inbasebean.getIs_complete().equals("1")) {

                LinkedHashMap<Integer, CompletionQuestionadapterItemModle> integerStringMap = inbasebean.getmCompletionAllMap().get(selfposition);

                if (integerStringMap != null)
                    for (int i = 0; i < integerStringMap.size(); i++) {
                        /*显示我的答案*/
                        if (!integerStringMap.get(position).getShowItemMy().equals("㊒")) {
                            holderView.editext.setText(integerStringMap.get(position).getShowItemMy());
                        } else {
                            holderView.editext.setText("");
                        }
                        /*字母显示绿色 框显示绿色*/
                        if (integerStringMap.get(position).getShowItemRightColor() == 0) {
                            holderView.editext.setBackgroundResource(R.drawable.select_completion_editext_bg_green);
                            holderView.editext.setTextColor(mContext.getResources().getColor(R.color.green));
                        } else {
                            holderView.editext.setBackgroundResource(R.drawable.select_completion_editext_bg_red);
                            /*显示正确答案*/
                            holderView.tv_rightanswer.setText(integerStringMap.get(position).getShowItemright());
                            holderView.editext.setTextColor(mContext.getResources().getColor(R.color.red));
                            holderView.tv_rightanswer.setVisibility(View.VISIBLE);
                        }
                    }
            }

        }


        /*清空焦点*/
        if (haveFocus.equals("false")) {
            holderView.editext.setClickable(false);
            holderView.editext.setFocusable(false);
        }
//        holderView.editext.addTextChangedListener(editChangedListener);

        return convertView;
    }

    class HolderView {
        EditText editext;
        TextView tv_rightanswer;
//        TextView tv_num;
    }

//    class EditChangedListener implements TextWatcher {
//        private CharSequence temp;//监听前的文本
//        private int editStart;//光标开始位置
//        private int editEnd;//光标结束位置
//        private final int charMaxNum = 1;
//        private EditText editText;
//        private int position;
//
//
//        @Override
//        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            temp = s;
//        }
//
//        @Override
//        public void onTextChanged(CharSequence text, int start, int before, int count) {
//        }
//
//        @Override
//        public void afterTextChanged(Editable s) {
//            inputContainer.put(position, new CompletionQuestionadapterItemModle(s.toString()));
////            /*得到光标开始和结束位置 ,超过最大数后记录刚超出的数字索引进行控制 */
//            editStart = this.editText.getSelectionStart();
//            editEnd = this.editText.getSelectionEnd();
//
//            if (temp.length() > charMaxNum) {
////                Toast.makeText(mContext, "你输入的字数已经超过了限制！", Toast.LENGTH_LONG).show();
//                s.delete(editStart - 1, editEnd);
//                int tempSelection = editStart;
//                if (!s.equals("")) {
//                    this.editText.setText(s);
//
//                }
//                this.editText.setSelection(tempSelection);
//            }
////            submitEditext.submitEditextInfo(selfposition, inputContainer,position,s.toString());
//        }
//    }

    class MyFoucus implements View.OnFocusChangeListener {
        // 当获取焦点时修正myWatch中的position值,这是最重要的一步!
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                int position = (int) v.getTag();

//                editChangedListener.position = position;
//                editChangedListener.editText = (EditText) v;
            }
        }
    }
}
