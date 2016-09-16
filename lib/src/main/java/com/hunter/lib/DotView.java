package com.hunter.lib;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class DotView {

    public static final int DOT_GRAVITY_TOP             = 0;
    public static final int DOT_GRAVITY_CENTER_VERTICAL = 1;

    public static final int DEF_COLOR_RED = Color.parseColor("#FD3737");

    protected Paint mPaint;
    protected Paint mTextPaint;

    protected int mTipsCount;

    protected int mMarginLeft;
    protected int mMarginTop;

    protected int mDotHorizontalPadding;

    protected boolean mIsShowDot;
    protected int     mDotColor;
    protected int     mDotGravity;

    protected int mDotWidth;
    protected int mDotHeight;

    protected int mDotRadius;

    protected RectF mDotRectF;
    protected Rect  mTextRect;

    protected int mViewWidth;
    protected int mViewHeight;

    protected View    mView;
    protected Context mContext;

    protected int mExtraX;
    protected int mExtraY;

    public DotView(View view, Context context, AttributeSet attrs) {
        mView = view;
        mContext = context;

        TypedArray array = mContext.obtainStyledAttributes(attrs, R.styleable.DotView);
        mTipsCount = array.getInt(R.styleable.DotView_dot_tips_count, 0);
        mMarginLeft = array.getDimensionPixelOffset(R.styleable.DotView_dot_margin_left, 0);
        mMarginTop = array.getDimensionPixelOffset(R.styleable.DotView_dot_margin_top, 0);
        mDotHorizontalPadding = array.getDimensionPixelOffset(R.styleable.DotView_dot_horizontal_padding, 0);
        mIsShowDot = array.getBoolean(R.styleable.DotView_dot_is_show, false);
        mDotColor = array.getColor(R.styleable.DotView_dot_color, DEF_COLOR_RED);
        mDotGravity = array.getInt(R.styleable.DotView_dot_gravity, DOT_GRAVITY_TOP);

        if (mTipsCount > 0) mIsShowDot = true;
        array.recycle();

        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(mDotColor);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setFakeBoldText(true);
        mTextRect = new Rect();

        resetCount();
    }

    private void resetCount() {
        if (mTipsCount >= 10) {
            mDotWidth = DotUtils.getRectWidthDp(mContext);
            mDotHeight = DotUtils.getCircleDp(mContext);
        } else if (mTipsCount > 0) {
            mDotWidth = DotUtils.getCircleDp(mContext);
            mDotHeight = DotUtils.getCircleDp(mContext);
        } else {
            mDotHeight = mDotWidth = DotUtils.getNoneDp(mContext);
        }
        mDotRadius = mDotWidth / 2;

        mTextPaint.setTextSize(mDotHeight * 0.6f);

        mView.invalidate();
    }

    protected void draw(Canvas canvas) {
        if (mIsShowDot) {
            if (mTipsCount < 10) drawCircleDot(canvas);
            else drawOvalDot(canvas);
            if (mTipsCount > 0) drawText(canvas);
        }
    }

    private void drawCircleDot(Canvas canvas) {
        if (mDotGravity == DOT_GRAVITY_TOP) {
            canvas.drawCircle(mViewWidth / 2 + mExtraX, mDotHeight + mExtraY, mDotRadius, mPaint);
        } else {
            canvas.drawCircle(mViewWidth / 2 + mExtraX, mViewHeight / 2f + mExtraY, mDotRadius, mPaint);
        }
    }

    private void drawOvalDot(Canvas canvas) {
        canvas.drawRoundRect(mDotRectF, (int) (mDotWidth * 0.3), (int) (mDotWidth * 0.3), mPaint);
    }

    private void drawText(Canvas canvas) {
        String text = mTipsCount > 99 ? "99+" : (mTipsCount + "");
        mTextPaint.getTextBounds(text, 0, text.length(), mTextRect);
        FontMetricsInt fontMetrics = mTextPaint.getFontMetricsInt();

        if (mDotGravity == DOT_GRAVITY_TOP) {
            int baseline = mDotHeight - (fontMetrics.descent + fontMetrics.ascent) / 2;
            canvas.drawText(text, mViewWidth / 2 + mExtraX, baseline + mExtraY, mTextPaint);
        } else {
            int baseline = (mViewHeight - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
            canvas.drawText(text, mViewWidth / 2 + mExtraX, baseline + mExtraY, mTextPaint);
        }
    }

    protected void onSizeChanged(int w, int h, boolean isText) {
        if (isText) {
            mViewWidth = w;
            mViewHeight = h + mView.getPaddingTop() - mView.getPaddingBottom();
            mExtraX = mMarginLeft + mDotHorizontalPadding - mView.getPaddingRight();
        } else {
            mViewWidth = w + mView.getPaddingLeft() - mView.getPaddingRight();
            mViewHeight = h;
            mExtraX = mMarginLeft + mDotHorizontalPadding;
            mExtraY = mMarginTop + mView.getPaddingTop();
        }

        if (mDotGravity == DOT_GRAVITY_TOP) {
            mDotRectF = new RectF(mViewWidth / 2 + mExtraX - mDotWidth / 2,
                                  mDotHeight / 2 + mExtraY,
                                  mViewWidth / 2 + mDotWidth + mExtraX - mDotWidth / 2,
                                  mDotHeight / 2 + mDotHeight + mExtraY);
        } else {
            mDotRectF = new RectF(mViewWidth / 2 + mExtraX - mDotWidth / 2,
                                  mViewHeight / 2 - mDotHeight / 2 + mExtraY,
                                  mViewWidth / 2 + mDotWidth + mExtraX - mDotWidth / 2,
                                  mViewHeight / 2 + mDotHeight / 2 + mExtraY);
        }
    }

    public void setTipsCount(int tipsCount) {
        mTipsCount = tipsCount;
        resetCount();
    }

    public int getTipsCount() {
        return mTipsCount;
    }

    public void setIsShow(boolean isShowDot) {
        mIsShowDot = isShowDot;
        mView.invalidate();
    }

    public void setColor(int color) {
        mPaint.setColor(color);
        mView.invalidate();
    }

}