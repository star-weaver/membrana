package com.starweaver.membrana;

import android.content.*;
import android.graphics.*;
import android.graphics.Bitmap.*;
import android.graphics.PorterDuff.*;
import android.graphics.drawable.*;
import android.util.*;
import android.widget.*;

import android.graphics.Bitmap.Config;

public class CircleImageView extends ImageView
{
boolean monochrome;
//color matrix modify
float[] cmData = new float[]
{
0.3f, 0.59f, 0.11f, 0, 0, 
0.3f, 0.59f, 0.11f, 0, 0, 
0.3f, 0.59f, 0.11f, 0, 0, 
0, 0, 0, 1, 0,
};
/*float[] cmData = new float[]
{
0, 0, 1, 0, 0, 
0, 0.59f, 0, 0, 0, 
0, 0, 1, 0, 0, 
0, 0, 0, 1, 0,
};*/
//WHOLE RED
/*float[] cmData = new float[]
 {
 0, 0, 0, 0, 255, 
 0, 0, 0, 0, 0, 
 0, 0, 0, 0, 0, 
 0, 0, 0, 1, 0
 };*/

ColorMatrix cm = new ColorMatrix(cmData);
ColorFilter filter = new ColorMatrixColorFilter(cm);
//color matrix modify

public CircleImageView (Context ctx, AttributeSet attrs) 
{
super(ctx, attrs);
init(attrs);
}

public CircleImageView (Context ctx) 
{
super(ctx);
monochrome = true;
}


private void init(final AttributeSet attrs) 
{
if (attrs != null) 
{
String packageName = "http://schemas.android.com/apk/res-auto";
monochrome = attrs.getAttributeBooleanValue(packageName, "monochrome", true);
}
}

@Override
protected void onDraw(Canvas canvas) 
{
Drawable drawable = getDrawable();
if (drawable == null) 
{
return;
}

if (getWidth() == 0 || getHeight() == 0) 
{
return;
}

Bitmap b = ((BitmapDrawable) drawable).getBitmap();
Bitmap bitmap = b.copy(Bitmap.Config.ARGB_8888, true);
int w = getWidth(), h = getHeight();
Bitmap roundBitmap = getRoundedCroppedBitmap(bitmap, w);
canvas.drawBitmap(roundBitmap, 0, 0, null);
}

//was: public static Bitmap
public Bitmap getRoundedCroppedBitmap(Bitmap bitmap, int radius) 
{
Bitmap finalBitmap;
if (bitmap.getWidth() != radius || bitmap.getHeight() != radius)
finalBitmap = Bitmap.createScaledBitmap(bitmap, radius, radius, false);
else
finalBitmap = bitmap;
Bitmap output = Bitmap.createBitmap(finalBitmap.getWidth(), finalBitmap.getHeight(), Config.ARGB_8888);
Canvas canvas = new Canvas(output);
final Paint paint = new Paint();
final Rect rect = new Rect(0, 0, finalBitmap.getWidth(), finalBitmap.getHeight());
paint.setAntiAlias(true);
paint.setFilterBitmap(true);
paint.setDither(true);
//color matrix modify
if(monochrome){paint.setColorFilter(filter);}
//color matrix modify
canvas.drawARGB(0, 0, 0, 0);
paint.setColor(Color.parseColor("#BAB399"));
canvas.drawCircle(finalBitmap.getWidth() / 2 + 0.7f,
				  finalBitmap.getHeight() / 2 + 0.7f,
				  finalBitmap.getWidth() / 2 + 0.1f, paint);
paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
canvas.drawBitmap(finalBitmap, rect, rect, paint);
return output;
}
}
