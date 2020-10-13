package com.starweaver.membrana;

import android.app.*;
import android.graphics.*;
import android.os.*;
import android.view.*;
import android.view.ViewTreeObserver.*;
import android.widget.*;
import com.plattysoft.leonids.*;
import com.plattysoft.leonids.modifiers.*;

public class FragmentMain extends Fragment
{
TriangleImageView triangleImageviewTriangle;
TriangleImageView triangleImageViewShadow;
TitleIcon titleIcon;
int width;
int height;

public FragmentMain()
{
}

@Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
{
View view_fragment_main= inflater.inflate(R.layout.fragment_main1, container, false);
triangleImageviewTriangle = (TriangleImageView)view_fragment_main.findViewById(R.id.mytri);
triangleImageViewShadow = (TriangleImageView)view_fragment_main.findViewById(R.id.myshad);
titleIcon = new TitleIcon();
setIcon(titleIcon.getIconsList(0), R.drawable.sand);
final RelativeLayout layout = (RelativeLayout)view_fragment_main.findViewById(R.id.fr_m);
ViewTreeObserver vto = layout.getViewTreeObserver(); 
vto.addOnGlobalLayoutListener (new OnGlobalLayoutListener() 
{ 
@Override public void onGlobalLayout() 
{
layout.getViewTreeObserver().removeOnGlobalLayoutListener(this); 
width  = layout.getMeasuredWidth();
height = layout.getMeasuredHeight();
} 
});
return view_fragment_main;
}

public void doAnimation1()
{
int rand = (int) (Math.random() * 700);
int rand1 = (int) (Math.random() * 3);
ParticleSystem ps = new ParticleSystem(this.getActivity(), 1, R.drawable.fog2, 65000, R.id.background_hook);//how much emit for one shot/time of live of one shot
ps.setSpeedModuleAndAngleRange(0.0025f, 0.027f, 00, 00);
ps.emit(-400, rand, 1, 65000);//speed of emitting per second/time of live whole emit system
ps.addModifier(new ScaleModifier(rand1, rand1, -400, 0));
}

/*void setIcon(int i, int s)
{
Bitmap icon = BitmapFactory.decodeResource(getResources(), i);
triangleImageviewTriangle.setImageBitmap(icon);
Bitmap shadow = BitmapFactory.decodeResource(getResources(), s);
triangleImageViewShadow.setImageBitmap(shadow);
}*/

void setIcon(int i, int s)
{
float[] colorMatrixData = new float[]
{
0.3f, 0.59f, 0.11f, 0, 0, 
0.3f, 0.59f, 0.11f, 0, 0, 
0.3f, 0.59f, 0.11f, 0, 0, 
0, 0, 0, 1, 0,
};

ColorMatrix colorMatrix = new ColorMatrix(colorMatrixData);
ColorMatrixColorFilter colorMatrixColorFilter = new ColorMatrixColorFilter(colorMatrix);

Bitmap icon = BitmapFactory.decodeResource(getResources(), i);

triangleImageviewTriangle.setImageBitmap(icon);
Bitmap shadow = BitmapFactory.decodeResource(getResources(), s);
triangleImageViewShadow.setImageBitmap(shadow);
}

}
