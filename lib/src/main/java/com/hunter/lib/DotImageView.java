package com.hunter.lib;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;

public class DotImageView extends ImageView {

    private DotView mDotView;

    public DotImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mDotView = new DotView(this, context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int width = getDrawable().getIntrinsicWidth() / 2 + w;
        width = width + getPaddingLeft() - getPaddingRight();
        int height = h + getPaddingTop() - getPaddingBottom();
        mDotView.onSizeChanged(width, height, false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mDotView.draw(canvas);
    }

}