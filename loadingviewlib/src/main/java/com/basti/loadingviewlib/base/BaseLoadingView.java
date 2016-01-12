package com.basti.loadingviewlib.base;

import android.animation.Animator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import java.util.List;

/**
 * Loadingview的基类，子类需要实现 绘制 和 初始化动画 两个方法
 * Created by SHIBW-PC on 2016/1/12.
 */
public abstract class BaseLoadingView {

    //动画集合
    private List<Animator> mAnimators;

    //对应的view
    private View v;

    public abstract void draw(Canvas canvas,Paint paint);

    public abstract List<Animator> InitAnimator();

    public float getWidth(){
        if (v == null){
            return -1;
        }
        return v.getWidth();
    }

    public float getHeight(){
        if (v == null){
            return -1;
        }
        return v.getHeight();
    }

    public void setView(View view){
        v = view;
    }

    public void postInvalidate(){
        v.postInvalidate();
    }

}
