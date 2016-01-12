# Android动画学习笔记

## 对AVLoadingIndicatorView库的学习
* [AVLoadingIndicatorView GitHub地址](https://github.com/81813780/AVLoadingIndicatorView)
* [详细注释版本](https://github.com/basti-shi031/AnimationLoadingView/tree/master#animationloadingview)

### 代码部分的学习
#### 项目结构
![](http://7xpvut.com1.z0.glb.clouddn.com/animator1.png)

项目结构很清晰，AVLoadingIndicatorView类继承自View，是控件的具体实现类，这个控件有两个自定义属性：
![](http://7xpvut.com1.z0.glb.clouddn.com/animator2.png)

1. 自定义属性 indicator_color 为AVLoadingIndicatorView的颜色，这个没什么好说的，
2. 自定义属性 indicator 可以理解为 AVLoadingIndicatorView的具体样式，
在AVLoadingIndicatorView中根据不同的indicator值实现不同的样式，所有实现样式的类都包含在indicator包中，
其中BaseIndicatorController为所有实现类的基类。

#### AVLoadingIndicatorView类
* 这是一个继承自View的类，和一般的自定义View一样，重写了部分方法。
* 在构造函数中，调用了init方法，该方法中对自定义属性进行读取和赋值，并新建了一个画笔Paint实例，最后调用了
applyIndicator()方法。
* onLayout方法在布局时调用，最后调用了applyAnimation()方法
* onDraw方法调用了drawIndicator()方法
* 其他方法都比较简单，不再说明

##### applyIndicator()
上面说到AVLoadingIndicatorView中根据不同的indicator值实现不同的样式，applyIndicator()即完成这一步骤。
##### applyAnimation()
不同的样式有不同的动画，applyAnimation()方法实现了根据不同indicator值实现动画
##### drawIndicator()
每个样式都有自己的绘制方法，drawIndicator()方法实现绘制过程

#### BaseIndicatorController类
这个是所有样式类的父类，在这个类中实现一些公用方法和抽象方法供子类去掉用和实现

#### 具体实现
##### BallPulseIndicator类
BallPulseIndicator是效果图中最左上角的view，他实现了父类的两个方法，draw()和createAnimation()

1. createAnimation()方法
  这个方法用于初始化三个圆点的动画效果，对这三个圆点的动画进行分解，其实就是每个圆点大小缩放的动画，且三个动画之间有一定的时间间隔。
  ```java
  ValueAnimator scaleAnim=ValueAnimator.ofFloat(1,0.3f,1);
  scaleAnim.setDuration(750);
  scaleAnim.setRepeatCount(-1);
  scaleAnim.setStartDelay(delays[i]);
  ```
    * ValueAnimator.ofFloat()方法允许传入多个float参数，这是一个值平滑过渡的过程，在这里即表示值从1平滑过渡到0.3，在过渡到1.
    * scaleAnim.setDuration(750);表示该动画的时间。
    * scaleAnim.setRepeatCount(-1);设置为无限循环，这里的-1实际上是常量ValueAnimator.INFINITE。
    * scaleAnim.setStartDelay(delays[i]);设置每个动画的开始时间，因为从效果图中可以看出来每个圆点的动画开始时间
    是不同的

    ```java
    mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            scale[index] = (float) animation.getAnimatedValue();
            postInvalidate();
        }
    });

    mValueAnimator.start();
    ```
    并且给每一个动画设置监听，监听其在播放时的值，将这个值传给成员变量float[] scale,并根据scale的值对view进行重绘。

2. draw()方法
  分别调用 canvas.drawCircle(x,y,radius,paint);方法绘制三个圆点，每个圆点的radius相同，所以可以在循环外直接
  算出，x、y的值需要在循环内动态计算

  ![](http://7xpvut.com1.z0.glb.clouddn.com/animator3.png)

  从图中可以看出，半径radius = (控件宽度-2*圆点间间隔)/6，即
  ```java
  float radius = (getWidth()-2*mCircleSpacing)/6;
  ```
  对于第i个圆，y的值永远为
  ```java
  float y=getHeight() / 2;
  ```
  第i个圆，x的值为i个圆间间隔+(i*2+1)个半径
  ```java
  float x = mCircleSpacing*i+(1+i*2)*radius;
  ```
  算完了圆心坐标和半径就可以开始绘制圆了
  ```java
  canvas.save();
  float x = mCircleSpacing*i+(1+i*2)*radius;
  float y = getHeight()/2;
  canvas.translate(x,y);
  canvas.scale(scale[i],scale[i]);
  canvas.drawCircle(0,0,radius,paint);
  canvas.restore();
  ```
    * canvas.translate(x,y)将圆心的原点设置为第i个圆的圆心处
    * canvas.scale(scale[i],scale[i]);将画布按照valueAnimator的值进行缩放
    * canvas.save()可以理解为保存当前画布，将当前画布上绘制的图像保存起来，后续的操作就相当于在一张新的画布上绘制，绘制完毕后调用canvas.restore()，可以理解为将两张画布合并为一张画布。
    需要注意的是，程序中save方法的调用次数一定是大于等于restore方法的，即save后才可能有restore操作。

到此，BallPulseIndicator的绘制就已经全部完成了。
