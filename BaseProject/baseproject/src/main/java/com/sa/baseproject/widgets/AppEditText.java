package com.sa.baseproject.widgets;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

import com.sa.baseproject.utils.FontUtils;


public class AppEditText extends AppCompatEditText {


    public AppEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        parseAttributes(context, attrs);
    }

    public AppEditText(Context context) {
        super(context);
    }

    public AppEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parseAttributes(context, attrs);
    }

    private void parseAttributes(Context context, AttributeSet attrs) {
        if (isInEditMode())
            return;

        setTypeface(FontUtils.getTypeface(context, attrs));
    }


}
