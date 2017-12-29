package cn.dajiahui.kidteacher.view;

import android.content.Context;
import android.graphics.Color;
import android.text.Layout;
import android.text.Selection;
import android.util.AttributeSet;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.EditText;




public class TextPage extends EditText {
    private int off; //字符串的偏移值
    private Context context;

    public TextPage(Context context) {
        super(context);
        this.context = context;
        initialize();
    }

    public TextPage(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initialize();
    }

    private void initialize() {
        setGravity(Gravity.TOP);
        setBackgroundColor(Color.WHITE);
    }

//    private PgTextActivity obj;

    public boolean isEdit = false;//

    /**
     * 注册监听
     *
     * @param pgInterface
     */
//    public void regisInterface(PgTextActivity obj, PgInterface pgInterface) {
//        this.pgInterface = pgInterface;
//        this.obj = obj;
//    }

    @Override
    protected void onCreateContextMenu(ContextMenu menu) {
        //不做任何处理，为了阻止长按的时候弹出上下文菜单    
    }

    @Override
    public boolean getDefaultEditable() {
        return false;
    }

    int curOff = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        Layout layout = getLayout();
        int line = 0;
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                line = layout.getLineForVertical(getScrollY() + (int) event.getY());
                off = layout.getOffsetForHorizontal(line, (int) event.getX());
                Selection.setSelection(getEditableText(), off);
                curOff = off;
                break;
            case MotionEvent.ACTION_MOVE:
                if (!isEdit) {
                    line = layout.getLineForVertical(getScrollY() + (int) event.getY());
                    curOff = layout.getOffsetForHorizontal(line, (int) event.getX());
                    break;
                }
                line = layout.getLineForVertical(getScrollY() + (int) event.getY());
                curOff = layout.getOffsetForHorizontal(line, (int) event.getX());
                Selection.setSelection(getEditableText(), off, curOff);
                break;
            case MotionEvent.ACTION_UP:
                boolean flag = true;
                if (curOff != off) {
                    if (!isEdit) {
                        return isEdit;
                    }
                    int startIndex = 0;
                    int endIndex = 0;
                    if (off > curOff) {
                        startIndex = curOff;
                        endIndex = off;
                    } else {
                        startIndex = off;
                        endIndex = curOff;
                    }
//                    for (BePgContent beContent :
//                            obj.rectViewList) {
//                        int a = (int) beContent.getNoteX();
//                        int b = (int) (beContent.getNoteX() + beContent.getNoteY());
//                        if (!(startIndex < a && endIndex <= a || startIndex >= b && endIndex > b)) {
//                            ToastUtil.showToast(context, "此区域已经批注过请选择其他区域");
//                            flag = false;
//                            initStatus();
                            break;//此处已经批注过了
                        }
                    }
//                    if (flag) {
//                        String substring = getText().toString().substring(startIndex, endIndex);
//                        pgInterface.selectContent(substring, startIndex, endIndex);
//                    }
//                } else {
//                    pgInterface.downContent(off);//
//                }
//                break;
//        }
        return true;
    }

    public void initStatus() {
        Selection.setSelection(getEditableText(), off);
    }

    private PgInterface pgInterface;

    public interface PgInterface {
        void selectContent(String selectContent, int startIndex, int endIndex);

        void downContent(int clickContent);

        void moveContent(int clickContent);
    }

} 