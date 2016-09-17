package com.hunter.lib;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;

public class DotImageView extends ImageView implements IDot{

    private DotView mDotView;

    public DotImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mDotView = new DotView(this, context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int width = getDrawable().getIntrinsicWidth() / 2 + w;
        mDotView.onSizeChanged(width, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mDotView.draw(canvas);
    }
    @Override
    public void setTipsCount(int tipsCount) {
        mDotView.setTipsCount(tipsCount);
    }

    @Override
    public int getTipsCount() {
        return mDotView.getTipsCount();
    }

    @Override
    public void setIsShow(boolean isShowDot) {
        mDotView.setIsShow(isShowDot);
    }

    @Override
    public void setColor(int color) {
        mDotView.setColor(color);
    }
}