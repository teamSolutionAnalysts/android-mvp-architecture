
package com.sa.baseproject.imageview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ProgressBar;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 *  We can load image with progressbar using this class.
 *
 */
public class AppImageView extends FrameLayout implements Target {

    private ImageView imageView;
    private ImageView placeHolderImageView;
    private ProgressBar progressBar;
    private Context context;
    private LayoutParams imageParams;
    private OnLoadBitmap onLoadBitmap;
    private boolean isCenterCrop = false;
    private boolean isCircleTransformation = false;


    /**
     * For circle imageview after initialisation just call this method and pass true.
     *
     * @param circleTransformation
     */
    public void setCircleTransformation(boolean circleTransformation) {
        isCircleTransformation = circleTransformation;
    }

    public void setOnLoadBitmap(OnLoadBitmap onLoadBitmap) {
        this.onLoadBitmap = onLoadBitmap;
    }

    public AppImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public AppImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AppImageView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context c) {
        this.context = c;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        imageView = new ImageView(context);
        imageView.setAdjustViewBounds(true);
        placeHolderImageView = new ImageView(context);

        progressBar = new ProgressBar(context, null, android.R.attr.progressBarStyleSmall);
        progressBar.setIndeterminate(true);
        final LayoutParams progressParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        progressParams.gravity = Gravity.CENTER;

        imageParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        imageParams.gravity = Gravity.CENTER;

        final LayoutParams placeHolderParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        placeHolderParams.gravity = Gravity.CENTER;

        addView(placeHolderImageView, placeHolderParams);
        addView(imageView, imageParams);
        addView(progressBar, progressParams);

    }

    /**
     * Load url with progress bar.
     * @param url image url
     */
    public void loadImage(String url) {
        if (!TextUtils.isEmpty(url)) {
            if (isCircleTransformation) {
                PicassoBigCache.INSTANCE.getPicassoBigCache(context).load(url)
                        .priority(Picasso.Priority.NORMAL)
                        .transform(new CircleTransform())
                        .config(Bitmap.Config.ARGB_8888)
                        .into(this);
            } else {
                PicassoBigCache.INSTANCE.getPicassoBigCache(context).load(url)
                        .priority(Picasso.Priority.NORMAL)
                        .config(Bitmap.Config.ARGB_8888)
                        .into(this);
            }
        } else {
            removeView(progressBar);
            removeView(imageView);
        }
    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        removeView(progressBar);
        removeView(placeHolderImageView);

        if (isCenterCrop)
            imageView.setScaleType(ScaleType.CENTER_CROP);

        imageView.setImageBitmap(bitmap);

        if (onLoadBitmap != null)
            onLoadBitmap.loadBitmap(bitmap);
    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {
        removeView(progressBar);
    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void setImageScaleType(ScaleType scaleType) {
        imageView.setScaleType(scaleType);
    }

    public interface OnLoadBitmap {
        void loadBitmap(Bitmap bitmap);
    }

    public void setCenterCrop(boolean centerCrop) {
        isCenterCrop = centerCrop;
    }
}
