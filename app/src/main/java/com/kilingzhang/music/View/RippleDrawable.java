package com.kilingzhang.music.View;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import static android.content.ContentValues.TAG;

/**
 * Created by Slight on 2017/4/8.
 */

public class RippleDrawable extends Drawable {

    private int mAlpha = 255;
    private int mRippleColor = 0;

    //圆心坐标
    private float mRipplePointX;
    private float mRipplePointY;
    //半径
    private float mRippleRadius = 0;
    //进入动画进度值
    private float mProgress = 0;
    private float mEnterIncrement = 16f / 300;


    private float mStartRadius, mEndRadius;
    //点击之后的坐标
    private float mDonePointX, mDonePointY;
    //点击之后的中心点
    private float mCenterPointX, mCenterPointY;


    private boolean isDrawBitmio = false;

    //抗锯齿画笔
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private Bitmap mBitmap;

    //动画的差值器
    private DecelerateInterpolator mEnterInterpolator = new DecelerateInterpolator();

    private boolean mEnterDone;


    public RippleDrawable(Bitmap mBitmap) {

        this.mBitmap = mBitmap;
        this.isDrawBitmio = true;
        //抗锯齿
        mPaint.setAntiAlias(true);
        //防抖动
        mPaint.setDither(true);
        //设置画笔为填充
        mPaint.setStyle(Paint.Style.FILL);

        setRippleColor(0x90000000);

    }

    public RippleDrawable() {

        //获取屏幕宽度


        //抗锯齿
        mPaint.setAntiAlias(true);
        //防抖动
        mPaint.setDither(true);

        setRippleColor(0x90CCCCCC);

    }

    //设置画笔颜色
    public void setRippleColor(int color) {
        //不建议直接设置颜色  mPaint.setColor(color);
        mRippleColor = color;
        onColorOrAlphaChange();
    }


    public void onColorOrAlphaChange() {
        mPaint.setColor(mRippleColor);
        /**
         * pAlpha = Color.alpha(mRippleColor)/mPaint.getAlpha() 先去颜色中去解析颜色
         * 得到颜色本身透明度
         */
        Log.e(TAG, "onColorOrAlphaChange: oldColor=" + mPaint.getColor());
        if (mAlpha != 255) {
            int pAlpha = mPaint.getAlpha();
            int realAlpha = (int) (pAlpha * (mAlpha / 255f));
            mPaint.setAlpha(realAlpha);
            int realColor = mPaint.getColor();
            Log.e(TAG, "onColorOrAlphaChange: newColor=" + realColor);

        }

    }


    //更改颜色透明度
    public int changeColorAlpha(int color, int alpha) {
        int a = (color >> 24) & 0xFF;
        a = (int) (a * (alpha / 255f));
        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = (color) & 0xFF;
        return (a << 24) | (r << 16) | (g << 8) | b;
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        mCenterPointX = bounds.centerX();
        mCenterPointY = bounds.centerY();
        //得到圆的最大半径
        float maxRadius = Math.max(mCenterPointX, mCenterPointY);
        mStartRadius = maxRadius * 0f;
        mEndRadius = maxRadius * 0.7f;
    }

    private int mBgAlpha;
    private int mCircleAlpha;

    private int getCircleAlpha(int preAlpha, int bgAlpha) {
        int dAlpha = preAlpha - bgAlpha;
        return (int) ((dAlpha * 255f) / (255f - bgAlpha));
    }

    @Override
    public void draw(Canvas canvas) {

        int preAlpha = mPaint.getAlpha();
        int bgAlpha = (int) (preAlpha * (mBgAlpha / 255f));
        int maxCircleAlpha = getCircleAlpha(preAlpha, bgAlpha);
        int circleAlpha = (int) (maxCircleAlpha * (mCircleAlpha / 255f));

        mPaint.setAlpha(bgAlpha);
        canvas.drawColor(mPaint.getColor());
        //如果实现了设置背景  则invalidateSelf()的所有验证均可跳过, 因为绘制背景时已经实现
        if (isDrawBitmio) {
            canvas.drawBitmap(mBitmap, 0, 0, mPaint);
        }
        mPaint.setAlpha(circleAlpha);
        canvas.drawCircle(mRipplePointX, mRipplePointY, mRippleRadius, mPaint);
        mPaint.setAlpha(preAlpha);
    }

    @Override
    public void setAlpha(int alpha) {
        //设置透明度
        mAlpha = alpha;
        invalidateSelf();
    }

    @Override
    public int getAlpha() {
        return mAlpha;
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        //设置滤镜
        if (colorFilter != mPaint.getColorFilter()) {
            mPaint.setColorFilter(colorFilter);
        }
        invalidateSelf();
    }

    private void startEnterRunnable() {
        mProgress = 0;
        mCircleAlpha = 255;
        mEnterDone = false;
        unscheduleSelf(mExitRunnable);
        unscheduleSelf(mEnterRunnable);
        scheduleSelf(mEnterRunnable, SystemClock.uptimeMillis());
    }

    private Runnable mEnterRunnable = new Runnable() {
        @Override
        public void run() {
            mProgress = mProgress + mEnterIncrement;
            if (mProgress > 1) {
                onEnterProgress(1);
                onEnterDone();
                return;
            }
            float realProgress = mEnterInterpolator.getInterpolation(mProgress);
            onEnterProgress(realProgress);
            //延迟16毫秒 保证界面刷新接近60fps
            scheduleSelf(this, SystemClock.uptimeMillis() + 16);
        }
    };

    //进入动画刷新方法
    private void onEnterProgress(float progress) {
        mRippleRadius = getProgressValue(mStartRadius, mEndRadius, progress);
        mBgAlpha = (int) getProgressValue(0, 182, progress);
        invalidateSelf();
    }

    //进入完成的方法
    private void onEnterDone() {
        mEnterDone = true;
        if (mTouchRelease) {
            startExitRunnable();
        }
        Log.e(TAG, "onEnterDone: ");
    }

    private float getProgressValue(float start, float end, float progress) {
        return start + (end - start) * progress;
    }

    //退出动画

    //退出动画的进度值
    private float mExitProgress = 0;
    //退出动画递增进度值
    private float mExitIncreement = 16f / 300;

    //差值器  从慢到快
    private AccelerateInterpolator mExitInterpolator = new AccelerateInterpolator(2);
    private Runnable mExitRunnable = new Runnable() {
        @Override
        public void run() {
            if (!mEnterDone) {
                return;
            }
            mExitProgress = mExitProgress + mExitIncreement;
            Log.e(TAG, "run: " + mExitProgress);
            if (mExitProgress > 1) {
                onExitProgress(1);
                onExitDone();
                return;
            }
            float realProgress = mExitInterpolator.getInterpolation(mExitProgress);
            onExitProgress(realProgress);
            //延迟16毫秒 保证界面刷新接近60fps
            scheduleSelf(this, SystemClock.uptimeMillis() + 16);
        }
    };

    //退出动画刷新方法
    private void onExitProgress(float progress) {

        //背景减淡
        mBgAlpha = (int) getProgressValue(182, 0, progress);
        //圆形减淡
        mCircleAlpha = (int) getProgressValue(255, 0, progress);
        invalidateSelf();
    }

    //启动退出的方法
    private void startExitRunnable() {
        mExitProgress = 0;
        unscheduleSelf(mEnterRunnable);
        unscheduleSelf(mExitRunnable);
        scheduleSelf(mExitRunnable, SystemClock.uptimeMillis());
    }


    //退出动画完成的方法
    private void onExitDone() {
        Log.e(TAG, "onExitDone: ");
    }


    @Override
    public int getOpacity() {

        int alpha = mPaint.getAlpha();
        if (alpha == 255) {
            //全透明
            return PixelFormat.OPAQUE;
        } else if (alpha == 0) {
            //不透明
            return PixelFormat.TRANSPARENT;
        } else {
            //半透明
            return PixelFormat.TRANSLUCENT;
        }
    }

    //标识手是否抬起
    private boolean mTouchRelease;

    public void onTouch(MotionEvent event) {

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                onTouchDown(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                onTouchMove(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_UP:
                onTouchUp(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_CANCEL:
                onTouchCancel(event.getX(), event.getY());
                break;
            default:
                break;
        }
    }

    public void onTouchDown(float x, float y) {
        mDonePointX = x;
        mDonePointY = y;
        mRipplePointX = x;
        mRipplePointY = y;
        mRippleRadius = 0;
        mTouchRelease = false;
        startEnterRunnable();
    }

    public void onTouchMove(float x, float y) {

    }

    public void onTouchUp(float x, float y) {
        mTouchRelease = true;
        if (mEnterDone)
            startExitRunnable();
    }


    public void onTouchCancel(float x, float y) {
        mTouchRelease = true;
        if (mEnterDone)
            startExitRunnable();
    }

}

