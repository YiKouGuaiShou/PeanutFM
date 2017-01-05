package com.yikouguaishou.peanutfm.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.yikouguaishou.peanutfm.R;

/**
 * Created by snowflake on 2017/1/5.
 */
public class CircleProgressView extends View {
    /**
     * 直径
     */
    private int mDiameter;

    /**
     * 绘制时控制绘制的范围
     */
    private Paint mPaint;
    private int progressValue=0;
    RectF rect;

    //颜色（渐变色的4个点）
    private int[] mColors = { Color.RED, Color.YELLOW, Color.GREEN,  Color.BLUE};


    public CircleProgressView(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public CircleProgressView(Context context)
    {
        this(context, null);
    }

    /**
     * 获得我自定义的样式属性
     *
     * @param context
     * @param attrs
     * @param defStyle
     */
    public CircleProgressView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        /**
         * 获得我们所定义的自定义样式属性
         */
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CircleProgressView, defStyle, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++)
        {
            int attr = a.getIndex(i);
            switch (attr)
            {
                case R.styleable.CircleProgressView_diameter:
                    // 默认设置为40dp
                    mDiameter = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_SP, 40, getResources().getDisplayMetrics()));
                    break;
            }
        }
        a.recycle();

        mPaint = new Paint();
        rect = new RectF();
        progressValue=0;
    }

    public void setProgressValue(int progressValue){

        this.progressValue = progressValue;
        Log.i("customView","log: progressValue="+progressValue);

    }

    public void setColors(int[] colors){

        mColors = colors;
        Log.i("customView","log: progressValue="+progressValue);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int width = 0;
        int height = 0;

        //设置直径的最小值
        if(mDiameter<=40){
            mDiameter=40;
        }
        height=mDiameter;
        width=mDiameter;

        Log.i("customView","log: w="+width+" h="+height);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int mWidth = getMeasuredWidth();
        int mHeight = getMeasuredHeight();

        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth((float) mWidth/10 );
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setColor(Color.TRANSPARENT);

        rect.set(20, 20, mWidth - 20, mHeight - 20);
        canvas.drawArc(rect, 0, 360, false, mPaint);
        mPaint.setColor(Color.BLACK);

        float section = ((float)progressValue) / 100;
        int count = mColors.length;
        int[] colors = new int[count];
        System.arraycopy(mColors, 0, colors, 0, count);

        LinearGradient shader = new LinearGradient(3, 3, mWidth - 3 , mHeight - 3, colors, null,
                Shader.TileMode.CLAMP);
        mPaint.setShader(shader);

        canvas.drawArc(rect, 0, section * 360, false, mPaint);
    }
}
