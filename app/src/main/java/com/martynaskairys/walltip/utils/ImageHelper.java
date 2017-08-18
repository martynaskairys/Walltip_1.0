package com.martynaskairys.walltip.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

// TODO: 17/08/2017 give a better name
public class ImageHelper extends ImageView {

    private float radius = 14.0f;
    private Path path;
    private RectF rect;

    public ImageHelper(Context context) {
        super(context);
        init();
    }

    public ImageHelper(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ImageHelper(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        path = new Path();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        rect = new RectF(0, 0, this.getWidth(), this.getHeight());
        path.addRoundRect(rect, radius, radius, Path.Direction.CW);
        canvas.clipPath(path);
        super.onDraw(canvas);
    }
}