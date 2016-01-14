package com.basti.loadingviewlib.loadingview;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.animation.LinearInterpolator;

import com.basti.loadingviewlib.base.BaseLoadingView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SHIBW-PC on 2016/1/14.
 */
public class BallPulseRiseIndicator extends BaseLoadingView{

    @Override
    public void draw(Canvas canvas, Paint paint) {
        float width = getWidth();
        float height = getHeight();
        float radius = width/10;
        paint.setStyle(Paint.Style.FILL);

        //绘制五个圆点
        canvas.drawCircle(getWidth()/4,radius*2,radius,paint);
        canvas.drawCircle(getWidth()*3/4,radius*2,radius,paint);

        canvas.drawCircle(radius,getHeight()-2*radius,radius,paint);
        canvas.drawCircle(getWidth()/2,getHeight()-2*radius,radius,paint);
        canvas.drawCircle(getWidth()-radius,getHeight()-2*radius,radius,paint);
    }

    @Override
    public List<Animator> initAnimator() {

        List<Animator> animators = new ArrayList<>();

        PropertyValuesHolder xProperty = PropertyValuesHolder.ofFloat("rotationX",0,360);

        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(getView(), xProperty);
        objectAnimator.setDuration(1000);
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.start();

        animators.add(objectAnimator);
        return animators;
    }
}
