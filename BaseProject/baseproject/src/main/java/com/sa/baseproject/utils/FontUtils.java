package com.sa.baseproject.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;

import com.sa.baseproject.R;
import com.sa.baseproject.widgets.AppTypefaceSpan;


public class FontUtils {

    static final String AVENIR_REGULAR = "fonts/AvenirNextLTPro-MediumCn.otf";
    private static final String AVENIR_BOLD = "fonts/AvenirNextLTPro-Bold.otf";


    /**
     * Get TypeFace with new fonts
     *
     * @param context Application/Activity context
     * @param attrs type of font like Bold/Regular/Italic.
     */
    public static Typeface getTypeface(Context context, AttributeSet attrs) {

        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.fontStyle);
        final int typeFace = typedArray.getInt(R.styleable.fontStyle_typeface, 0);

        typedArray.recycle();

        switch (typeFace) {
            case 1:
                return Typeface.createFromAsset(context.getAssets(), AVENIR_REGULAR);
            case 2:
                return Typeface.createFromAsset(context.getAssets(), AVENIR_BOLD);
            default:
                return Typeface.createFromAsset(context.getAssets(), AVENIR_REGULAR);
        }

    }


    /**
     * Set custom fonts in menu
     *
     * @param context Application/Activity context
     * @param menu Menu object from Activity/Fragment.
     */
    public static void setCustomFontInMenu(Context context, Menu menu) {
        for (int i = 0; i < menu.size(); i++) {
            final MenuItem mi = menu.getItem(i);

            //for aapplying a font to subMenu ...
            final SubMenu subMenu = mi.getSubMenu();
            if (subMenu != null && subMenu.size() > 0) {
                for (int j = 0; j < subMenu.size(); j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    applyFontToMenuItem(context, subMenuItem);
                }
            }

            //the method we have create in activity
            applyFontToMenuItem(context, mi);
        }
    }
    /**
     * Set custom fonts in menuItem
     *
     * @param context Application/Activity context
     * @param menuItem MenuItem object of Menu.
     */
    private static void applyFontToMenuItem(Context context, MenuItem menuItem) {
        final Typeface font = Typeface.createFromAsset(context.getAssets(), AVENIR_REGULAR);
        final SpannableString mNewTitle = new SpannableString(menuItem.getTitle());
        mNewTitle.setSpan(new AppTypefaceSpan("", font), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        menuItem.setTitle(mNewTitle);
    }

}
