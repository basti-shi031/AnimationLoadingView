package com.basti.loadingviewlib.loadingview;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.animation.LinearInterpolator;

import com.basti.loadingviewlib.base.BaseLoadingView;

import java.util.ArrayList;
import java.util.List;

/**
 * 第三个
 * Created by SHIBW-PC on 2016/1/14.
 */
public class SquareSpinIndicator extends BaseLoadingView {
    @Override
    public void draw(Canvas canvas, Paint paint) {
        canvas.drawRect(new RectF(getWidth() / 5, getHeight() / 5, getWidth() * 4 / 5, getHeight() * 4 / 5), paint);
    }

    @Override
    public List<Animator> initAnimator() {

        List<Animator> animators = new ArrayList<>();

        //围绕x轴旋转
        PropertyValuesHolder xProperty = PropertyValuesHolder.ofFloat("rotationX",0,180,180,0,0);
        //围绕y轴旋转
        PropertyValuesHolder yProperty = PropertyValuesHolder.ofFloat("rotationY",0,0,180,180,0);

        //使用PropertyValuesHolder实现一个动画更改多个效果
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(getView(),xProperty,yProperty);
        objectAnimator.setDuration(2500);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        //设置一个线性插值器
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.start();

        animators.add(objectAnimator);
        return animators;
    }
}
