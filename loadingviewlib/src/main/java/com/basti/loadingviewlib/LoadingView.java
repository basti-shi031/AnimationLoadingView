package com.basti.loadingviewlib;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.basti.loadingviewlib.base.BaseLoadingView;
import com.basti.loadingviewlib.loadingview.BallClipRotateIndicator;
import com.basti.loadingviewlib.loadingview.BallClipRotatePulseIndicator;
import com.basti.loadingviewlib.loadingview.BallPulseLoading;
import com.basti.loadingviewlib.loadingview.BallPulseRiseIndicator;
import com.basti.loadingviewlib.loadingview.SquareSpinIndicator;
import com.basti.loadingviewlib.utils.DimensionUtils;

/**
 * Created by SHIBW-PC on 2016/1/11.
 */
public class LoadingView extends View {

    private Paint mPaint;
    private int mColor;
    private int mIndicatorId;
    private BaseLoadingView mLoadingView;
    private static final int defaultSize = 45;

    public LoadingView(Context context) {
        this(context,null);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs);

        //根据不同的id新建不同的LoadingView
        initLoadingView();

        //根据不同的id新建不同的动画
        initAnimators();
    }

    private void initAnimators() {
        mLoadingView.initAnimator();
    }

    private void initLoadingView() {

        switch (mIndicatorId){
            case 0:mLoadingView = new BallPulseLoading();
                break;
            case 1:mLoadingView = new BallClipRotateIndicator();
                break;
            case 2:mLoadingView = new BallClipRotatePulseIndicator();
                break;
            case 3:mLoadingView = new SquareSpinIndicator();
                break;
            case 4:mLoadingView = new BallPulseRiseIndicator();
                break;
        }

        mLoadingView.setView(this);


    }

    //初始化一些自定义属性和变量
    private void init(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs,R.styleable.LoadingView);

        mIndicatorId = ta.getInt(R.styleable.LoadingView_indicator, 0);
        mColor = ta.getColor(R.styleable.LoadingView_indicator_color, Color.WHITE);

        ta.recycle();

        mPaint = new Paint();
        mPaint.setColor(mColor);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = measuredDimension(widthMeasureSpec);
        int height = measuredDimension(heightMeasureSpec);

        setMeasuredDimension(width, height);
    }

    private int measuredDimension(int measureSpec) {

        //将默认值转化为px
        int result = DimensionUtils.dp2px(defaultSize,getContext());//px

        int size = MeasureSpec.getSize(measureSpec);
        int mode = MeasureSpec.getMode(measureSpec);

        if (mode == MeasureSpec.EXACTLY){
            //EXACTLY:Match_Parent,xxx dp
            result = size;
        }else if (mode == MeasureSpec.AT_MOST){
            //AT_MOST:wrap content
            result = Math.min(DimensionUtils.dp2px(defaultSize,getContext()), size);
        }

        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawLoadingView(canvas);
    }

    private void drawLoadingView(Canvas canvas) {
        mLoadingView.draw(canvas,mPaint);
    }
}
