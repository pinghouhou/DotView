package com.hunter.lib;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.RadioButton;

public class DotRadioButton extends RadioButton {

    private DotView mDotView;

    public DotRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mDotView = new DotView(this, context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Drawable[] drawables = getCompoundDrawables();
        int width = drawables[1].getIntrinsicWidth() / 2 + w;
        mDotView.onSizeChanged(width, h, true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mDotView.draw(canvas);
    }

}