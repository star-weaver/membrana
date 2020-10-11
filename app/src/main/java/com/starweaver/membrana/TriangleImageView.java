package com.mycompany.myapp81;

import android.content.*;
import android.graphics.*;
import android.graphics.Bitmap.*;
import android.graphics.PorterDuff.*;
import android.graphics.drawable.*;
import android.util.*;
import android.widget.*;

import android.graphics.Bitmap.Config;

public class TriangleImageView extends ImageView 
{
Bitmap shadow;
boolean shad;
public TriangleImageView (Context ctx, AttributeSet attrs) 
{
super(ctx, attrs);
init(attrs);
}
@Override protected void onDraw(Canvas canvas) 
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

private void init(final AttributeSet attrs) 
{
if (attrs != null) 
{
String packageName = "http://schemas.android.com/apk/res-auto";
shad = attrs.getAttributeBooleanValue(packageName, "shadow", false);
}
}

private Paint createShadow() 
{
Paint mShadow = new Paint();
float radius = 50.0f;
float xOffset = 0.0f;
float yOffset = 20.0f;
// color=black
int color = 0xFF000000;
mShadow.setShadowLayer(radius, xOffset, yOffset, color);
return mShadow;
}

public Bitmap getRoundedCroppedBitmap(Bitmap bitmap, int radius) 
{
Bitmap finalBitmap;
Paint paint;
if (bitmap.getWidth() != radius || bitmap.getHeight() != radius)
finalBitmap = Bitmap.createScaledBitmap(bitmap, radius, radius, false);
else
finalBitmap = bitmap;
Bitmap output = Bitmap.createBitmap(finalBitmap.getWidth(), finalBitmap.getHeight(), Config.ARGB_8888);
Canvas canvas = new Canvas(output);
if (shad == true)
{
paint = new Paint();
}
else
{
paint = createShadow();
}
final Rect rect = new Rect(0, 0, finalBitmap.getWidth(), finalBitmap.getHeight());
Point point1_draw = new Point(150, 0);//half of 360
Point point2_draw = new Point(0, 300);
Point point3_draw = new Point(300, 300);
Path path = new Path();
path.moveTo(point1_draw.x, point1_draw.y);
path.lineTo(point2_draw.x, point2_draw.y);
path.lineTo(point3_draw.x, point3_draw.y);
path.lineTo(point1_draw.x, point1_draw.y);
path.close();
canvas.drawARGB(0, 0, 0, 0);
paint.setColor(Color.parseColor("#BAB399"));
canvas.drawPath(path, paint);
paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
canvas.drawBitmap(finalBitmap, rect, rect, paint);
return output;
}

}
