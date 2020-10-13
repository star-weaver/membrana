package com.starweaver.membrana;

import android.animation.*;
import android.content.*;
import android.graphics.*;
import android.util.*;
import android.view.*;
import android.view.animation.*;
import android.widget.*;
import java.util.logging.*;

public class VerticalSeekBar extends SeekBar 
{
private OnSeekBarChangeListener onSeekBarChangeListener;
private ValueAnimator mAnimator;
private float yCoordinate;
private boolean isNeedCallListener = true;

public VerticalSeekBar(Context context) 
{
super(context);
}

public VerticalSeekBar(Context context, AttributeSet attrs, int defStyle) 
{
super(context, attrs, defStyle);
}

public VerticalSeekBar(Context context, AttributeSet attrs) 
{
super(context, attrs);
}

protected void onSizeChanged(int w, int h, int oldw, int oldh) 
{
super.onSizeChanged(h, w, oldh, oldw);
}

@Override
protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) 
{
super.onMeasure(heightMeasureSpec, widthMeasureSpec);
setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
}

@Override
public void setOnSeekBarChangeListener(OnSeekBarChangeListener onSeekBarChangeListener)
{
this.onSeekBarChangeListener = onSeekBarChangeListener;
}

protected void onDraw(Canvas c) 
{
c.rotate(-90);
c.translate(-getHeight(), 0);
super.onDraw(c);
}

@Override
public boolean onTouchEvent(MotionEvent event) 
{
if (!isEnabled()) 
{
return false;
}
switch (event.getAction()) 
{
case MotionEvent.ACTION_DOWN:
case MotionEvent.ACTION_MOVE:
case MotionEvent.ACTION_UP:
yCoordinate = event.getY();
setProgress(getMax() - (int) (getMax() * event.getY() / getHeight()));
onSizeChanged(getWidth(), getHeight(), 0, 0);
break;
case MotionEvent.ACTION_CANCEL:
break;
}
return true;
}

public float getYCoordinate()
{
return yCoordinate;
}

public void updateThumb()
{
onSizeChanged(getWidth(), getHeight(), 0, 0);
}

public void setSmoothProgress(final int progress) 
{
final int currentProgress = getProgress();
if (mAnimator != null) 
{
mAnimator.cancel();
mAnimator.removeAllUpdateListeners();
mAnimator.removeAllListeners();
mAnimator = null;
isNeedCallListener = true;
}
mAnimator = ValueAnimator.ofInt(currentProgress, progress);
mAnimator.setDuration(getResources().getInteger(android.R.integer.config_longAnimTime));
mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() 
{
@Override
public void onAnimationUpdate(ValueAnimator animation) 
{
int value = (int) animation.getAnimatedValue();
if (value == progress) 
{
isNeedCallListener = true;
} 
else 
{
isNeedCallListener = false;
}
VerticalSeekBar.super.setProgress(value);
updateThumb();
}
});
mAnimator.start();
}

}

