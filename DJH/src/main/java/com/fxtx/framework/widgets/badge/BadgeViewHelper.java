
package com.fxtx.framework.widgets.badge;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.fxtx.framework.R;

public class BadgeViewHelper {
    private Bitmap mBitmap;
    private Badgeable mBadgeable;
    private Paint mBadgePaint;
    /**
     * 角标背景色
     */
    private int mBadgeBgColor;
    /**
     * 角标文本的颜色
     */
    private int mBadgeTextColor;
    /**
     * 角标文本字体大小
     */
    private int mBadgeTextSize;
    /**
     * 角标背景与宿主控件上下边缘间距离
     */
    private int mBadgeVerticalMargin;
    /**
     * 角标背景与宿主控件左右边缘间距离
     */
    private int mBadgeHorizontalMargin;
    /***
     * 角标文本边缘与徽章背景边缘间的距离
     */
    private int mBadgePadding;
    /**
     * 角标文本
     */
    private String mBadgeText;
    /**
     * 角标文本所占区域大小
     */
    private Rect mBadgeNumberRect;
    /**
     * 是否显示
     */
    private boolean mIsShowBadge;
    /**
     * 角标在宿主控件中的位置
     */
    private BadgeGravity mBadgeGravity;
    /**
     * 角标所占区域
     */
    private RectF mBadgeRectF;
    /**
     * 是否可拖动
     */
    private boolean mDragable;
    /**
     * 拖动时的角标控件
     */
    private DragBadgeView mDropBadgeView;
    /**
     * 是否正在拖动
     */
    private boolean mIsDraging;
    private DragDismissDelegate mDelegage;
    private boolean mIsShowDrawable = false;

    public BadgeViewHelper(Badgeable badgeable, Context context, AttributeSet attrs, BadgeGravity defaultBadgeGravity) {
        mBadgeable = badgeable;
        initDefaultAttrs(context, defaultBadgeGravity);
        initCustomAttrs(context, attrs);
        afterInitDefaultAndCustomAttrs();
        mDropBadgeView = new DragBadgeView(context, this);
    }

    public void setmDragable(boolean mDragable) {
        this.mDragable = mDragable;
    }

    private void initDefaultAttrs(Context context, BadgeGravity defaultBadgeGravity) {
        mBadgeNumberRect = new Rect();
        mBadgeRectF = new RectF();
        mBadgeBgColor = Color.RED;
        mBadgeTextColor = Color.WHITE;
        mBadgeTextSize = BadgeViewUtil.sp2px(context, 10);

        mBadgePaint = new Paint();
        mBadgePaint.setAntiAlias(true);
        mBadgePaint.setStyle(Paint.Style.FILL);
        // 设置mBadgeText居中，保证mBadgeText长度为1时，文本也能居中
        mBadgePaint.setTextAlign(Paint.Align.CENTER);

        mBadgePadding = BadgeViewUtil.dp2px(context, 4);
        mBadgeVerticalMargin = BadgeViewUtil.dp2px(context, 4);
        mBadgeHorizontalMargin = BadgeViewUtil.dp2px(context, 4);

        mBadgeGravity = defaultBadgeGravity;
        mIsShowBadge = false;

        mBadgeText = null;

        mBitmap = null;

        mIsDraging = false;

        mDragable = false;
    }

    private void initCustomAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BGABadgeView);
        final int N = typedArray.getIndexCount();
        for (int i = 0; i < N; i++) {
            initCustomAttr(typedArray.getIndex(i), typedArray);
        }
        typedArray.recycle();
    }

    private void initCustomAttr(int attr, TypedArray typedArray) {
        if (attr == R.styleable.BGABadgeView_badge_bgColor) {
            mBadgeBgColor = typedArray.getColor(attr, mBadgeBgColor);
        } else if (attr == R.styleable.BGABadgeView_badge_textColor) {
            mBadgeTextColor = typedArray.getColor(attr, mBadgeTextColor);
        } else if (attr == R.styleable.BGABadgeView_badge_textSize) {
            mBadgeTextSize = typedArray.getDimensionPixelSize(attr, mBadgeTextSize);
        } else if (attr == R.styleable.BGABadgeView_badge_verticalMargin) {
            mBadgeVerticalMargin = typedArray.getDimensionPixelSize(attr, mBadgeVerticalMargin);
        } else if (attr == R.styleable.BGABadgeView_badge_horizontalMargin) {
            mBadgeHorizontalMargin = typedArray.getDimensionPixelSize(attr, mBadgeHorizontalMargin);
        } else if (attr == R.styleable.BGABadgeView_badge_padding) {
            mBadgePadding = typedArray.getDimensionPixelSize(attr, mBadgePadding);
        } else if (attr == R.styleable.BGABadgeView_badge_gravity) {
            int ordinal = typedArray.getInt(attr, mBadgeGravity.ordinal());
            mBadgeGravity = BadgeGravity.values()[ordinal];
        } else if (attr == R.styleable.BGABadgeView_badge_dragable) {
            mDragable = typedArray.getBoolean(attr, mDragable);
        }
    }

    private void afterInitDefaultAndCustomAttrs() {
        mBadgePaint.setTextSize(mBadgeTextSize);
    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mDragable && mIsShowBadge && mBadgeRectF.contains(event.getX(), event.getY())) {
                    mIsDraging = true;
                    mBadgeable.getParent().requestDisallowInterceptTouchEvent(true);

                    Rect badgeableRect = new Rect();
                    mBadgeable.getGlobalVisibleRect(badgeableRect);
                    mDropBadgeView.setStickCenter(badgeableRect.left + mBadgeRectF.left + mBadgeRectF.width() / 2, badgeableRect.top + mBadgeRectF.top + mBadgeRectF.height() / 2);

                    mDropBadgeView.onTouchEvent(event);
                    mBadgeable.postInvalidate();
                    return true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (mIsDraging) {
                    mDropBadgeView.onTouchEvent(event);
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (mIsDraging) {
                    mDropBadgeView.onTouchEvent(event);
                    mIsDraging = false;
                    return true;
                }
                break;
            default:
                break;
        }
        return mBadgeable.callSuperOnTouchEvent(event);
    }

    public void endDragWithDismiss() {
        hiddenBadge();
        if (mDelegage != null) {
            mDelegage.onDismiss(mBadgeable);
        }
    }

    public void endDragWithoutDismiss() {
        mBadgeable.postInvalidate();
    }

    public void drawBadge(Canvas canvas) {
        if (mIsShowBadge && !mIsDraging) {
            if (mIsShowDrawable) {
                drawDrawableBadge(canvas);
            } else {
                drawTextBadge(canvas);
            }
        }
    }

    /**
     * 绘制图像角标
     *
     * @param canvas
     */
    private void drawDrawableBadge(Canvas canvas) {
        mBadgeRectF.left = mBadgeable.getWidth() - mBadgeHorizontalMargin - mBitmap.getWidth();
        mBadgeRectF.top = mBadgeVerticalMargin;
        switch (mBadgeGravity) {
            case RightTop:
                mBadgeRectF.top = mBadgeVerticalMargin;
                break;
            case RightCenter:
                mBadgeRectF.top = (mBadgeable.getHeight() - mBitmap.getHeight()) / 2;
                break;
            case RightBottom:
                mBadgeRectF.top = mBadgeable.getHeight() - mBitmap.getHeight() - mBadgeVerticalMargin;
                break;
            default:
                break;
        }
        canvas.drawBitmap(mBitmap, mBadgeRectF.left, mBadgeRectF.top, mBadgePaint);
        mBadgeRectF.right = mBadgeRectF.left + mBitmap.getWidth();
        mBadgeRectF.bottom = mBadgeRectF.top + mBitmap.getHeight();
    }

    /**
     * 绘制文字角标
     *
     * @param canvas
     */
    private void drawTextBadge(Canvas canvas) {
        String badgeText = "";
        if (!TextUtils.isEmpty(mBadgeText)) {
            badgeText = mBadgeText;
        }
        // 获取文本宽所占宽高
        mBadgePaint.getTextBounds(badgeText, 0, badgeText.length(), mBadgeNumberRect);
        // 计算角标背景的宽高
//        int badgeHeight = mBadgeNumberRect.height() + mBadgePadding * 2;
        int badgeHeight = 0;
        if (mBadgeNumberRect.height() > 0) {
            badgeHeight = mBadgeNumberRect.height() + mBadgePadding * 2;
        } else {
            badgeHeight = mBadgeNumberRect.height() + mBadgePadding * 3;
        }
        int badgeWidth;
        // 当mBadgeText的长度为1或0时，计算出来的高度会比宽度大，此时设置宽度等于高度
        if (badgeText.length() == 1 || badgeText.length() == 0) {
            badgeWidth = badgeHeight;
        } else {
            badgeWidth = mBadgeNumberRect.width() + mBadgePadding * 2;
        }

        // 计算角标背景上下的值
        mBadgeRectF.top = 0;//mBadgeVerticalMargin;
        mBadgeRectF.bottom = mBadgeable.getHeight() - mBadgeVerticalMargin;
        switch (mBadgeGravity) {
            case RightTop:
                mBadgeRectF.bottom = mBadgeRectF.top + badgeHeight;
                break;
            case RightCenter:
                mBadgeRectF.top = (mBadgeable.getHeight() - badgeHeight) / 2;
                mBadgeRectF.bottom = mBadgeRectF.top + badgeHeight;
                break;
            case RightBottom:
                mBadgeRectF.top = mBadgeRectF.bottom - badgeHeight;
                break;
            default:
                break;
        }

        // 计算角标背景左右的值
        mBadgeRectF.right = mBadgeable.getWidth() * 4 / 6 ;
        mBadgeRectF.left = mBadgeRectF.right - badgeWidth;

        // 设置角标背景色
        mBadgePaint.setColor(mBadgeBgColor);
        // 绘制角标背景
        canvas.drawRoundRect(mBadgeRectF, badgeHeight / 2, badgeHeight / 2, mBadgePaint);

        if (!TextUtils.isEmpty(mBadgeText)) {
            // 设置角标文本颜色
            mBadgePaint.setColor(mBadgeTextColor);
            // initDefaultAttrs方法中设置了mBadgeText居中，此处的x为角标背景的中心点y
            float x = mBadgeRectF.left + badgeWidth / 2;
            // 注意：绘制文本时的y是指文本底部，而不是文本的中间
            float y = mBadgeRectF.bottom - mBadgePadding;
            // 绘制角标文本
            canvas.drawText(badgeText, x, y, mBadgePaint);
        }
    }

    public void showCirclePointBadge() {
        showTextBadge(null);
    }

    public void showTextBadge(String badgeText) {
        mIsShowDrawable = false;
        mBadgeText = badgeText;
        mIsShowBadge = true;
        mBadgeable.postInvalidate();
    }

    public void hiddenBadge() {
        mIsShowBadge = false;
        mBadgeable.postInvalidate();
    }

    public void showDrawable(Bitmap bitmap) {
        mBitmap = bitmap;
        mIsShowDrawable = true;
        mIsShowBadge = true;
        mBadgeable.postInvalidate();
    }

    public boolean isShowDrawable() {
        return mIsShowDrawable;
    }

    public RectF getBadgeRectF() {
        return mBadgeRectF;
    }

    public int getBadgePadding() {
        return mBadgePadding;
    }

    public String getBadgeText() {
        return mBadgeText;
    }

    public int getBadgeBgColor() {
        return mBadgeBgColor;
    }

    public int getBadgeTextColor() {
        return mBadgeTextColor;
    }

    public int getBadgeTextSize() {
        return mBadgeTextSize;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public void setDragDismissDelegage(DragDismissDelegate delegage) {
        mDelegage = delegage;
    }

    public enum BadgeGravity {
        RightTop,
        RightCenter,
        RightBottom
    }
}