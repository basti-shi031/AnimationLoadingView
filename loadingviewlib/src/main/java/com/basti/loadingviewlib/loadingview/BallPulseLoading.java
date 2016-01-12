package com.basti.loadingviewlib.loadingview;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.basti.loadingviewlib.base.BaseLoadingView;

import java.util.ArrayList;
import java.util.List;

/**
 * 坐标[0,0]
 * Created by SHIBW-PC on 2016/1/11.
 */
public class BallPulseLoading extends BaseLoadingView {

    //三个圆点的放大倍数
    private float[] scale = {1f,1f,1f};
    //圆点间的间距
    private static final float mCircleSpacing = 4;
    //演示播放时间
    int delay[] = {120,240,360};

    //画圆点
    @Override
    public void draw(Canvas canvas, Paint paint) {

        //计算半径radius
        float radius = (getWidth()-2*mCircleSpacing)/6;

        for (int i = 0;i < 3;i++){
            //保存画布状态
            canvas.save();
            //圆心的x坐标
            float x = mCircleSpacing*i+(1+i*2)*radius;
            //圆心的y坐标
            float y = getHeight()/2;
            //把canvas的圆点移到圆点的圆心
            canvas.translate(x,y);
            //根据ValueAnimator的值缩小画布
            canvas.scale(scale[i],scale[i]);
            //绘制圆点
            canvas.drawCircle(0,0,radius,paint);
            //恢复画布状态
            //关于canvas.save() canvas.restore()的详细用法可以参考 《Android群英传》P117
            canvas.restore();
        }
    }

    //初始化动画
    @Override
    public List<Animator> InitAnimator() {

        List<Animator> mAnimators = new ArrayList<>();

        for (int i = 0;i< 3;i++){
            //new一个ValueAnimator的实例，范围是1-0.3，表示scale的倍数范围
            ValueAnimator mValueAnimator = ValueAnimator.ofFloat(1f,0.3f,1.0f);

            //无限循环
            mValueAnimator.setRepeatCount(-1);
            //持续时间
            mValueAnimator.setDuration(750);
            //延时播放
            mValueAnimator.setStartDelay(delay[i]);

            final int index = i;
            mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    scale[index] = (float) animation.getAnimatedValue();
                    postInvalidate();
                }
            });
            mValueAnimator.start();
            mAnimators.add(mValueAnimator);
        }
        return mAnimators;
    }
}
