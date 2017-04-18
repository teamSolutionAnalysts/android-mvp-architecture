package com.sa.baseproject.widgets;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.sa.baseproject.utils.FontUtils;


public class AppTextView extends AppCompatTextView {


    public AppTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        parseAttributes(context, attrs);
    }

    public AppTextView(Context context) {
        super(context);
    }

    public AppTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parseAttributes(context, attrs);
    }

    private void parseAttributes(Context context, AttributeSet attrs) {
        if (isInEditMode())
            return;

        setTypeface(FontUtils.getTypeface(context, attrs));
    }


}
