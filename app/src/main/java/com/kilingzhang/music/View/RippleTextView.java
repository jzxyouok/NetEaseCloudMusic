package com.kilingzhang.music.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Slight on 2017/4/8.
 */

public class RippleTextView extends TextView {

    private RippleDrawable mRippleDrawable;

    public RippleTextView(Context context) {
        this(context, null);
    }

    public RippleTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RippleTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        mRippleDrawable = new RippleDrawable(
//                BitmapFactory.decodeResource(getResources(),R.mipmap.meinv));
        mRippleDrawable = new RippleDrawable();

        //设置刷新接口 在View中已经实现
        mRippleDrawable.setCallback(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mRippleDrawable.draw(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        //设置Drawable绘制和刷新的区域
        super.onSizeChanged(w, h, oldw, oldh);
        mRippleDrawable.setBounds(0, 0, getWidth(), getHeight());
    }

    @Override
    protected boolean verifyDrawable(Drawable who) {
        //验证Drawable 是否 ok
        return who == mRippleDrawable || super.verifyDrawable(who);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.setBackgroundColor(0000000);
        mRippleDrawable.onTouch(event);
        //不掉用invalidate方法更新onDraw()
//        invalidate();
        this.setBackgroundColor(0);
        super.onTouchEvent(event);
        return true;
    }
}
