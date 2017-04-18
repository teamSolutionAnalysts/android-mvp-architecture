package com.sa.baseproject.imageview;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.squareup.picasso.Transformation;

/**
 * This class for circle image view.
 */
public class CircleTransform implements Transformation {

    private int borderwidth = 4;
    private String color = "#7f7f7f";

    public CircleTransform() {
    }

    public CircleTransform(String color) {
        this.color = color;
    }

    @Override
    public Bitmap transform(Bitmap source) {
        int size = Math.min(source.getWidth(), source.getHeight());

        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;

//        final int width = source.getWidth() + borderwidth;
//        final int height = source.getHeight() + borderwidth;

        Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
        if (squaredBitmap != source) {
            source.recycle();
        }

        Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        BitmapShader shader = new BitmapShader(squaredBitmap,
                BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setAntiAlias(true);

        float r = size / 2f;
        canvas.drawCircle(r, r, r, paint);

//        paint.setShader(null);
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setColor(Color.parseColor(color));
//        paint.setStrokeWidth(borderwidth);
//        canvas.drawCircle(width / 2, height / 2, r - borderwidth / 2, paint);

        squaredBitmap.recycle();
        return bitmap;
    }

    @Override
    public String key() {
        return "circle";
    }
}