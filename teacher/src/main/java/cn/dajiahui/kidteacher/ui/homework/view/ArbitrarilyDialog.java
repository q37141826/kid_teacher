package cn.dajiahui.kidteacher.ui.homework.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.fxtx.framework.R;

/**
 * 任意位置的dialog
 */
public abstract class ArbitrarilyDialog extends Dialog {
    protected Context mContext;
    protected View rootView;
    int mDialogHeight;

    public ArbitrarilyDialog(Context c, int resId, int mDialogHeight) {
        super(c, R.style.transparentDialogChoiseClass);//
        this.mContext = c;
        this.mDialogHeight = mDialogHeight;
        rootView = LayoutInflater.from(mContext).inflate(resId, null);
        LinearLayout rootView = (LinearLayout) this.rootView;
       /*设置透明度 全透明*/
        rootView.getBackground().setAlpha(130);
        initView();
        this.setContentView(this.rootView);
        initPop(resId);
        /*关闭弹框*/
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*透明部分点击事件*/
                onTouchOutside();
                dismiss();
            }
        });
    }

    /**
     * 初始化
     */
    private void initPop(int resId) {
        Window window = this.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM;
        lp.x = 0;//x作标
        lp.height = mDialogHeight;//设置dialog的高度

        // 设置显示位置
        this.onWindowAttributesChanged(lp);
        this.setCanceledOnTouchOutside(true);// 设置点击Dialog外部任意区域关闭Dialog
        window.setAttributes(lp);
        /*設置窗口透明*/
        window.setDimAmount(0f);


    }

    public abstract void initView();

    protected abstract void onTouchOutside();

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        /* 触摸外部弹窗 */
        if (isOutOfBounds(getContext(), event)) {
            onTouchOutside();
        }
        return super.onTouchEvent(event);
    }

    private boolean isOutOfBounds(Context context, MotionEvent event) {
        final int x = (int) event.getX();
        final int y = (int) event.getY();
        final int slop = ViewConfiguration.get(context).getScaledWindowTouchSlop();
        final View decorView = getWindow().getDecorView();
        return (x < -slop) || (y < -slop) || (x > (decorView.getWidth() + slop))
                || (y > (decorView.getHeight() + slop));
    }

}
