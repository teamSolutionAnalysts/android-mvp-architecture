package com.sa.baseproject.widgets;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.AppCompatRadioButton;
import android.util.AttributeSet;

import com.sa.baseproject.utils.FontUtils;


public class AppRadioButton extends AppCompatRadioButton {


    public AppRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        parseAttributes(context, attrs);
    }

    public AppRadioButton(Context context) {
        super(context);
    }

    public AppRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parseAttributes(context, attrs);
    }

    private void parseAttributes(Context context, AttributeSet attrs) {
        if (isInEditMode())
            return;

        setTypeface(FontUtils.getTypeface(context, attrs));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setStateListAnimator(null);
        }

    }


}
