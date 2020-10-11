package com.mycompany.myapp81;

import android.content.*;
import android.hardware.*;
import android.util.*;
import android.widget.*;
import java.util.*;
import org.opencv.android.*;

//https://www.thecodecity.com/2016/08/focus-modes-opencv-javacameraview-android.html?m=0

public class CustomCameraView extends JavaCameraView
{
public CustomCameraView(Context context, AttributeSet attrs) 
{
super(context, attrs);
}

public void setFocusMode(Context item, int type) 
{
Camera camera = mCamera;
if (camera != null) 
{
Camera.Parameters params = camera.getParameters();
camera.cancelAutoFocus();

camera.autoFocus(new Camera.AutoFocusCallback() 
{
@Override
public void onAutoFocus(boolean b, Camera camera) 
{
}
});

List<String> FocusModes = params.getSupportedFocusModes();

switch (type) 
{
case 0:
if (FocusModes.contains(Camera.Parameters.FOCUS_MODE_AUTO))
params.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
else
Toast.makeText(item, "Auto Mode is not supported", Toast.LENGTH_SHORT).show();
break;
case 1:
if (FocusModes.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO))
params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
else
Toast.makeText(item, "Continuous Mode is not supported", Toast.LENGTH_SHORT).show();
break;
case 2:
if (FocusModes.contains(Camera.Parameters.FOCUS_MODE_EDOF))
params.setFocusMode(Camera.Parameters.FOCUS_MODE_EDOF);
else
Toast.makeText(item, "EDOF Mode is not supported", Toast.LENGTH_SHORT).show();
break;
case 3:
if (FocusModes.contains(Camera.Parameters.FOCUS_MODE_FIXED))
{
params.setFocusMode(Camera.Parameters.FOCUS_MODE_FIXED);
Log.d("CustomCameraView", "FOCUS_MODE_FIXED");
}
else
Toast.makeText(item, "Fixed Mode is not supported", Toast.LENGTH_SHORT).show();
break;
case 4:
if (FocusModes.contains(Camera.Parameters.FOCUS_MODE_INFINITY))
params.setFocusMode(Camera.Parameters.FOCUS_MODE_INFINITY);
else
Toast.makeText(item, "Infinity Mode is not supported", Toast.LENGTH_SHORT).show();
break;
case 5:
if (FocusModes.contains(Camera.Parameters.FOCUS_MODE_MACRO))
params.setFocusMode(Camera.Parameters.FOCUS_MODE_MACRO);
else
Toast.makeText(item, "Macro Mode is not supported", Toast.LENGTH_SHORT).show();
break;
}
camera.setParameters(params);
}
}
}
