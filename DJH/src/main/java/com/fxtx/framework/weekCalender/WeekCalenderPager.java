package com.fxtx.framework.weekCalender;

import android.content.Context;
import android.util.AttributeSet;

import com.fxtx.framework.widgets.ViewPagerFixed;

import java.util.List;

/**
 * Created by wdj on 2016/3/30.
 */
public class WeekCalenderPager extends ViewPagerFixed {
    public static int oneIndex = 27;

    public WeekCalenderPager(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);
    }

    public WeekCalenderPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WeekCalenderPager(Context context) {
        super(context);
    }

    public void currentItem(boolean gTo) {
        int index = this.getCurrentItem();
        if (gTo) {
            //左侧滑动
            if (index > 0)
                this.setCurrentItem(index - 1, true);
        } else {
            //右侧滑动
            if (index < Integer.MAX_VALUE - 1) {
                this.setCurrentItem(index + 1, true);
            }
        }
    }

    //刷新当前的布局
    public void refreshItem(List<BeWeekReck> beans) {
        if (beans == null) {
            return;
        }
        WeekCard card = (WeekCard) this.findViewWithTag(getCurrentItem());
        if (card != null){
            card.getMyGroup().setGroupData(beans);
        }

    }

}
