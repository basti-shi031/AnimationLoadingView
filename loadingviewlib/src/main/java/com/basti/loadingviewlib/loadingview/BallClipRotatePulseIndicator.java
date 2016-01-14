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
 * 第二个
 * Created by SHIBW-PC on 2016/1/14.
 */
public class BallClipRotatePulseIndicator extends BaseLoadingView {

    private float scalePoint,scaleCircle,rotate;
    float circleSpacing=12;


    @Override
    public void draw(Canvas canvas, Paint paint) {
        float x=getWidth()/2;
        float y=getHeight()/2;

        //绘制圆心
        //因为圆心和圆弧的动画效果不同，所以必须先保存画布状态
        canvas.save();
        canvas.translate(x, y);
        canvas.scale(scalePoint, scalePoint);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(0, 0, x / 2.5f, paint);

        canvas.restore();

        canvas.translate(x, y);
        canvas.scale(scaleCircle, scaleCircle);
        canvas.rotate(rotate);

        paint.setStrokeWidth(3);
        paint.setStyle(Paint.Style.STROKE);

        //绘制两个圆弧
        float[] startAngles=new float[]{225,45};
        for (int i = 0; i < 2; i++) {
            RectF rectF=new RectF(-x+circleSpacing,-y+circleSpacing,x-circleSpacing,y-circleSpacing);
            canvas.drawArc(rectF, startAngles[i], 90, false, paint);
        }


    }

    @Override
    public List<Animator> initAnimator() {
        ValueAnimator scaleAnim=ValueAnimator.ofFloat(1,0.3f,1);
        scaleAnim.setDuration(1000);
        scaleAnim.setRepeatCount(-1);
        scaleAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                scalePoint = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        scaleAnim.start();

        ValueAnimator scaleAnim2=ValueAnimator.ofFloat(1,0.6f,1);
        scaleAnim2.setDuration(1000);
        scaleAnim2.setRepeatCount(-1);
        scaleAnim2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                scaleCircle = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        scaleAnim2.start();

        ValueAnimator rotateAnim=ValueAnimator.ofFloat(0, 180,360);
        rotateAnim.setDuration(1000);
        rotateAnim.setRepeatCount(-1);
        rotateAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                rotate = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        rotateAnim.start();
        List<Animator> animators=new ArrayList<>();
        animators.add(scaleAnim);
        animators.add(scaleAnim2);
        animators.add(rotateAnim);
        return animators;
    }
}
