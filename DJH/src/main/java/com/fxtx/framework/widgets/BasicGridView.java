package com.fxtx.framework.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 禁止滚动的GridView  可以在ScorlView中显示
 */
public class BasicGridView extends GridView {

        public BasicGridView(Context context, AttributeSet attrs) {
                super(context, attrs);  
        }  
          
        /** 
         * 设置不滚动 
         */  
        public void onMeasure(int widthMeasureSpec, int heightMeasureSpec)  
        {  
                int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,  
                                MeasureSpec.AT_MOST);  
                super.onMeasure(widthMeasureSpec, expandSpec);
        }
}  