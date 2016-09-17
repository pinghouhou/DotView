package com.hunter.lib;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

public class DotView {

    /**
     * 默认圆点宽高
     */
    public static final int DOT_CIRCLE_SPEC     = 15;
    public static final int DOT_OVAL_WIDTH      = 20;
    public static final int DOT_NONE_COUNT_SPEC = 8;

    public static final int DEF_COLOR_RED = Color.parseColor("#FD3737");

    public static final int DOT_GRAVITY_TOP = 0;

    protected Context mContext;

    protected Paint mPaint;
    protected Paint mTextPaint;

    /**
     * 自定义属性
     */
    protected int     mTipsCount;
    protected int     mMarginLeft;
    protected int     mMarginTop;
    protected int     mDotHorizontalPadding;
    protected int     mDotGravity;
    protected boolean mIsShowDot;
    protected int     mDotColor;

    protected int   mDotWidth;
    protected int   mDotHeight;
    protected int   mDotRadius;
    protected RectF mDotOvalRectF;

    protected View mView;
    protected int  mViewWidth;
    protected int  mViewHeight;

    protected int mDotCircleSpec;
    protected int mDotOvalWidth;
    protected int mDotNoneCountSpec;

    /**
     * 计算 View 额外的 Padding 值
     */
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

        initPaint();
        initDot();
        resetCount();
    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(mDotColor);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setFakeBoldText(true);
    }

    private void initDot() {
        mDotCircleSpec = DotUtils.dp2px(mContext, DOT_CIRCLE_SPEC);
        mDotOvalWidth = DotUtils.dp2px(mContext, DOT_OVAL_WIDTH);
        mDotNoneCountSpec = DotUtils.dp2px(mContext, DOT_NONE_COUNT_SPEC);
    }

    private void resetCount() {
        if (mTipsCount >= 10) {
            mDotWidth = mDotOvalWidth;
            mDotHeight = mDotCircleSpec;
        } else if (mTipsCount > 0) {
            mDotHeight = mDotWidth = mDotCircleSpec;
        } else {
            mDotHeight = mDotWidth = mDotNoneCountSpec;
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
        canvas.drawRoundRect(mDotOvalRectF, (int) (mDotWidth * 0.3), (int) (mDotWidth * 0.3), mPaint);
    }

    private void drawText(Canvas canvas) {
        String text = mTipsCount > 99 ? "99+" : (mTipsCount + "");
        FontMetricsInt fontMetrics = mTextPaint.getFontMetricsInt();

        if (mDotGravity == DOT_GRAVITY_TOP) {
            int baseline = mDotHeight - (fontMetrics.descent + fontMetrics.ascent) / 2;
            canvas.drawText(text, mViewWidth / 2 + mExtraX, baseline + mExtraY, mTextPaint);
        } else {
            int baseline = (mViewHeight - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
            canvas.drawText(text, mViewWidth / 2 + mExtraX, baseline + mExtraY, mTextPaint);
        }
    }

    protected void onSizeChanged(int w, int h) {
        if (mView instanceof TextView) {
            mViewWidth = w;
            mExtraX = mMarginLeft + mDotHorizontalPadding - mView.getPaddingRight();
        } else {
            mViewWidth = w + mView.getPaddingLeft() - mView.getPaddingRight();
            mExtraX = mMarginLeft + mDotHorizontalPadding;
        }
        mViewHeight = h + mView.getPaddingTop() - mView.getPaddingBottom();
        mExtraY = mMarginTop;

        float left = mViewWidth / 2 + mExtraX - mDotOvalWidth / 2;
        if (mDotGravity == DOT_GRAVITY_TOP) {
            float top = mDotCircleSpec / 2 + mExtraY;
            mDotOvalRectF = new RectF(left, top, left + mDotOvalWidth, top + mDotCircleSpec);
        } else {
            float top = mViewHeight / 2 - mDotCircleSpec / 2 + mExtraY;
            mDotOvalRectF = new RectF(left, top, left + mDotOvalWidth, top + mDotCircleSpec);
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