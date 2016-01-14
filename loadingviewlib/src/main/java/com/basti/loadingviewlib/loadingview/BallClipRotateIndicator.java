package com.basti.loadingviewlib.loadingview;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.basti.loadingviewlib.base.BaseLoadingView;

import java.util.ArrayList;
import java.util.List;

/**
 * 第一个
 * Created by SHIBW-PC on 2016/1/14.
 */
public class BallClipRotateIndicator extends BaseLoadingView {

    private float scale, degree;
    private float x, y;
    private float spacing=12;

    @Override
    public void draw(Canvas canvas, Paint paint) {

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);

        x = getWidth() / 2;
        y = getHeight() / 2;

        canvas.translate(x,y);
        canvas.scale(scale, scale);
        //围绕canvas的（0,0）旋转
        canvas.rotate(degree);

        RectF rectF = new RectF(-x+spacing,-y+spacing,x-spacing,y-spacing);
        canvas.drawArc(rectF,-90,225,false,paint);
    }

    @Override
    public List<Animator> initAnimator() {

        List<Animator> animatorList = new ArrayList<>();

        //缩放动画
        ValueAnimator scaleAnimator = ValueAnimator.ofFloat(1, 0.5f, 1);

        scaleAnimator.setRepeatCount(ValueAnimator.INFINITE);
        scaleAnimator.setDuration(750);
        scaleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                scale = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        scaleAnimator.start();

        //旋转动画
        ValueAnimator rotateAnimator = ValueAnimator.ofFloat(0, 360);

        rotateAnimator.setRepeatCount(ValueAnimator.INFINITE);
        rotateAnimator.setDuration(750);
        rotateAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                degree = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        rotateAnimator.start();

        animatorList.add(scaleAnimator);
        animatorList.add(rotateAnimator);

        return animatorList;
    }
}
