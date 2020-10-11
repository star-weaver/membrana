package com.mycompany.myapp81;

import android.app.*;
import android.content.*;
import android.hardware.*;
import android.os.*;
import android.view.*;
import org.opencv.android.*;
import org.opencv.core.*;
import org.opencv.imgproc.*;
import yjkim.mjpegviewer.*;

//https://www.techrepublic.com/article/pro-tip-create-your-own-magnetic-compass-using-androids-internal-sensors/
//https://github.com/sidberg/aruco-android/tree/master/Aruco/src
//https://github.com/nacgarg/android-aruco
//https://github.com/sanyaade-augmented-reality/aruco-android/tree/master/Aruco/src/es/ava/aruco

public class Implement extends Activity implements CameraBridgeViewBase.CvCameraViewListener2, SensorEventListener
{
CustomCameraView customCameraView;
//TitleString titleString;
Mat rgba;
private boolean mIsJavaCamera = true;
private MenuItem mItemSwitchCamera = null;
//NEW SENSORS START
private SensorManager mSensorManager;
private Sensor mAccelerometer;
private Sensor mMagnetometer;
private float[] mLastAccelerometer = new float[3];
private float[] mLastMagnetometer = new float[3];
private boolean mLastAccelerometerSet = false;
private boolean mLastMagnetometerSet = false;
//private boolean astralStarted = false;
private float[] mR = new float[9];
private float[] mOrientation = new float[3];
private float mCurrentDegree = 0f;
private float azimuthInRadians;
private float pitchInRadians;
private float rollInRadians;
private float azimuthInDegress;
private float pitchInDegress;
private float rollInDegress;
private float azimuthFloat;
private float rollFloat;
private float pitchFloat;
private static boolean destinationCalibrated = false;//CALIBRATE
private static float calibratedSensorFloat;//CALIBRATE
private static final float ROLL_MAX_VALUE = 9.5f;//1.5<=>358.5
private static final float ROLL_MIN_VALUE = 350.5f;
String testText = "test";//TEST
int positionInt = 0;
int oldPositionInt = positionInt;
int pitchInt = 0;
int oldPitchInt = pitchInt;
int oldPositionIntCounter = 0;

//private static int rollIndicator = 0;//temporary roll indicator
private static String rollIndicatorLever= "begin";//temporary roll indicator
private boolean startPositionWasNotImplemented = true;
private float startImplementPosition;
private float verifiedRange;
private float[] rangePositionLow = new float[5];
private float[] rangePositionHigh = new float[5];
private boolean[] rangeUpward = new boolean[5];
//new sensors end
//intent start
//static final int MSG_SET_STRING_VALUE = 1;
//Messenger mService = null;
//intent end
//mjpeg start
MjpegView mjpegView;
//mjpeg end

//42 start
Messenger mService = null;
boolean mIsBound;
final Messenger mMessenger = new Messenger(new IncomingHandler());

class IncomingHandler extends Handler 
{
@Override
public void handleMessage(Message msg) 
{
switch (msg.what) 
{
case MqttConnectionManagerService.MSG_SET_INT_VALUE:
/*
 =
 */
break;
case MqttConnectionManagerService.MSG_SET_STRING_VALUE:
/*
 =
 */
break;
}
}
}

private ServiceConnection mConnection = new ServiceConnection() 
{
public void onServiceConnected(ComponentName className, IBinder service) 
{
mService = new Messenger(service);
try 
{
Message msg = Message.obtain(null, MqttConnectionManagerService.MSG_REGISTER_CLIENT);
msg.replyTo = mMessenger;
mService.send(msg);
}
catch (RemoteException e) 
{
// In this case the service has crashed before we could even do anything with it
}
}
public void onServiceDisconnected(ComponentName className) 
{
// This is called when the connection with the service has been unexpectedly disconnected - process crashed.
mService = null;
//textStatus.setText("Disconnected.");
}
};
//42 end

private BaseLoaderCallback baseLoaderCallback = new BaseLoaderCallback(this) 
{
@Override
public void onManagerConnected(int status) 
{
switch (status) 
{
case LoaderCallbackInterface.SUCCESS: 
{
customCameraView.enableView();
//rgba=new Mat();//experimental
}
break;
default: 
{
super.onManagerConnected(status);
}
break;
}
}
};

@Override
protected void onCreate(Bundle savedInstanceState) 
{
super.onCreate(savedInstanceState);
getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
setContentView(R.layout.implement);
customCameraView = (CustomCameraView) findViewById(R.id.camera_view);
customCameraView.setVisibility(SurfaceView.VISIBLE);
customCameraView.setCvCameraViewListener(this);
customCameraView.setFocusMode(this, 3);
customCameraView.setMaxFrameSize(640, 360);//also set this at com.mycompany.myapp35.aruco.android.ViewBase
//new sensors start
mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
mMagnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
//new sensors end
//42 start
Intent intent = getIntent();//IS NEED THIS???
CheckIfServiceIsRunning();
doBindService();
//42 end
//sendMessageStringToService("third_","eye","Astral_intrO");
//mjpeg start
mjpegView = (MjpegView) findViewById(R.id.videwView);
//mjpegView.Start("http://217.197.157.7:7070/axis-cgi/mjpg/video.cgi?resolution=320x240", MjpegViewHandler);
//SEARCH -ANDROID REFRESH VIEW
mjpegView.Start("http://192.168.0.108:8080/?action=stream", MjpegViewHandler);
mjpegView.invalidate();
//mjpeg end
}

@Override
public void onPause()
{
super.onPause();
//new sensors start
mSensorManager.unregisterListener(this, mAccelerometer);
mSensorManager.unregisterListener(this, mMagnetometer);
//new.sensors end
if (customCameraView != null) customCameraView.disableView();
}

@Override
public void onResume()
{
super.onResume();
//new sensors start
mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
mSensorManager.registerListener(this, mMagnetometer, SensorManager.SENSOR_DELAY_GAME);
//new sensors end
if (!OpenCVLoader.initDebug()) 
{
OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, baseLoaderCallback);
} 
else 
{
baseLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
} 
}

public void onDestroy() 
{
super.onDestroy();
if (customCameraView != null) customCameraView.disableView();
mjpegView.Stop();
mSensorManager.unregisterListener(this, mAccelerometer);
mSensorManager.unregisterListener(this, mMagnetometer);
sendMessageStringToService("third_","eye","Astral_exiT");
//42 start
try 
{
doUnbindService();
}
catch (Throwable t) 
{
//Log.e("MainActivity", "Failed to unbind from the service", t);
}
//42 end
}

public void onCameraViewStarted(int width, int height) 
{
rgba = new Mat();
}

public void onCameraViewStopped() 
{
}

public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) 
{
rgba = inputFrame.rgba();
//Imgproc.cvtColor(rgba, rgba, Imgproc.COLOR_RGB2GRAY);//convert to greyscale
//Imgproc.cvtColor(rgba, rgba, Imgproc.COLOR_BGR2HSV, 3);
//think about fine_location permission
Imgproc.cvtColor(rgba, rgba, Imgproc.COLOR_BGR2HSV);
//Imgproc.putText(rgba , sensorValue , new Point( rgba.cols()/4 , rgba.rows()/4 ), Core.FONT_HERSHEY_COMPLEX , 1, new Scalar(255, 0, 0)) ;
Imgproc.rectangle(rgba, new Point(rgba.cols() * 0.7 , rgba.rows() * 0.9), new Point(rgba.cols() * 0.3, rgba.rows() * 0.7), new Scalar(0, 0, 255));
Imgproc.rectangle(rgba, new Point(rgba.cols() * 0.7 , rgba.rows() * 0.6), new Point(rgba.cols() * 0.3, rgba.rows() * 0.4), new Scalar(255, 0, 0));
//~= <115[0] .-. 315[200]>/6=33.33per each
//[115-148][148-181][181-214]
//[214-247][247-280][280-313]
//<125-138><158-171><191-204>
//<224-237><257-270><290-303>
//{0-33}{33-66}{66-99}
//{99-132}{132-165}{165-198}
//(10-23)(43-56)(76-89)
//(109-122)(142-155)(175-188)

for (int i=0; i<5; i++)
{
if (rangeUpward[i] == true)
{
if ((azimuthFloat > rangePositionHigh[i] && azimuthFloat < rangePositionLow[i])&&(rollFloat > ROLL_MIN_VALUE || rollFloat < ROLL_MAX_VALUE))
{
positionInt = i;
      if (positionInt != oldPositionInt)
      {
      oldPositionInt = positionInt;
	  oldPositionIntCounter++;
	  sendMessageStringToService("third_","eye",""+positionInt);
      }
}
else if (azimuthFloat < rangePositionLow[0] && azimuthFloat > rangePositionHigh[4])
{
positionInt = 8;
}
}
else
{
if ((azimuthFloat < rangePositionHigh[i] && azimuthFloat > rangePositionLow[i])&&(rollFloat > ROLL_MIN_VALUE || rollFloat < ROLL_MAX_VALUE))
{
positionInt = i;
       if (positionInt != oldPositionInt)
       {
       oldPositionInt = positionInt;
	   oldPositionIntCounter++;
	   sendMessageStringToService("third_","eye",""+positionInt);
       }
}
else if (azimuthFloat < rangePositionLow[0] && azimuthFloat > rangePositionHigh[4])
{
positionInt = 8;
}
}

}
//temporary roll indicator start
//1.5<=>358.5
if (rollFloat > ROLL_MAX_VALUE && rollFloat < 180f)
{
rollIndicatorLever = "-->";//12
}
else if (rollFloat < ROLL_MIN_VALUE && rollFloat > 180f)
{
rollIndicatorLever = "<--";//328
}
else{rollIndicatorLever = "-=000=-";}
//temporary roll indicator end
//pitch seeker start
if (pitchFloat < 280f && pitchFloat > 270f)
{
pitchInt = 1;
      if (pitchInt != oldPitchInt)
      {
      oldPitchInt = pitchInt;
      sendMessageStringToService("third_","eye",""+801);
      }
}
else if (pitchFloat > 300f)
{
pitchInt = 2;
      if (pitchInt != oldPitchInt)
      {
      oldPitchInt = pitchInt;
      sendMessageStringToService("third_","eye",""+802);
      }
}
else if (pitchFloat < 250f)
{
pitchInt = 0;
      if (pitchInt != oldPitchInt)
      {
      oldPitchInt = pitchInt;
      sendMessageStringToService("third_","eye",""+800);
      }
}
//pitch seeker end

Imgproc.putText(rgba , "" + positionInt , new Point(rgba.cols() * 0.42 , rgba.rows() * 0.86), Core.FONT_HERSHEY_COMPLEX , 2, new Scalar(0, 0, 255));
Imgproc.putText(rgba , "" + azimuthFloat , new Point(rgba.cols() * 0.38 , rgba.rows() * 0.56), Core.FONT_HERSHEY_COMPLEX , 2, new Scalar(255, 0, 0));
//Imgproc.putText(rgba , "<" + range1PositionLow + "/" + range1PositionHigh+ "><" + range2PositionLow + "/" +range2PositionHigh + "><" + range3PositionLow + "/" +range3PositionHigh + "><" + range4PositionLow + "/" +range4PositionHigh + "><" + range5PositionLow + "/" +range5PositionHigh + ">", new Point(rgba.cols() * 0.01 , rgba.rows() * 0.26), Core.FONT_HERSHEY_COMPLEX , 0.4, new Scalar(0, 0, 0));
Imgproc.putText(rgba , ""+ oldPositionIntCounter, new Point(rgba.cols() * 0.1 , rgba.rows() * 0.1), Core.FONT_HERSHEY_COMPLEX , 0.4, new Scalar(0, 0, 0));
Imgproc.putText(rgba , ""+ pitchFloat, new Point(rgba.cols() * 0.1 , rgba.rows() * 0.5), Core.FONT_HERSHEY_COMPLEX , 1, new Scalar(0, 0, 0));
//temporary roll indicator start
Imgproc.putText(rgba , rollIndicatorLever, new Point(rgba.cols() * 0.7 , rgba.rows() * 0.5), Core.FONT_HERSHEY_COMPLEX , 1, new Scalar(0, 0, 0));
//temporary roll indicator end
Imgproc.putText(rgba , "<" + rangePositionLow[0] + "/" + rangePositionHigh[0] + "><" + rangePositionLow[1] + "/" +rangePositionHigh[1] + "><" + rangePositionLow[2] + "/" +rangePositionHigh[2] + "><" + rangePositionLow[3] + "/" +rangePositionHigh[3] + "><" + rangePositionLow[4] + "/" +rangePositionHigh[4] + ">", new Point(rgba.cols() * 0.01 , rgba.rows() * 0.26), Core.FONT_HERSHEY_COMPLEX , 0.4, new Scalar(0, 0, 0));
//Imgproc.putText(rgba , "" + startImplementPosition , new Point(rgba.cols() * 0.42 , rgba.rows() * 0.26), Core.FONT_HERSHEY_COMPLEX , 2, new Scalar(255, 255, 0)) ;
//Core.inRange(rgba, new Scalar(10,100,20), new Scalar(25,255,255), rgba);
//Core.inRange(rgba, new Scalar(65, 50, 50), new Scalar(135,255,255), rgba);
//Core.flip(rgba, rgba, 1);//mirror
return rgba;
//return inputFrame.rgba();
}

//new sensors start
@Override
public void onSensorChanged(SensorEvent event) 
{
if (event.sensor == mAccelerometer) 
{
System.arraycopy(event.values, 0, mLastAccelerometer, 0, event.values.length);
mLastAccelerometerSet = true;
} 
else if (event.sensor == mMagnetometer) 
{
System.arraycopy(event.values, 0, mLastMagnetometer, 0, event.values.length);
mLastMagnetometerSet = true;
}
if (mLastAccelerometerSet && mLastMagnetometerSet) 
{
SensorManager.getRotationMatrix(mR, null, mLastAccelerometer, mLastMagnetometer);
SensorManager.getOrientation(mR, mOrientation);
azimuthInRadians = mOrientation[0];
rollInRadians = mOrientation[1];//1.5<=>358.5
pitchInRadians = mOrientation[2];//260<=>280//
azimuthInDegress = (float)(Math.toDegrees(azimuthInRadians) + 360) % 360;
rollInDegress = (float)(Math.toDegrees(rollInRadians) + 360) % 360;
pitchInDegress = (float)(Math.toDegrees(pitchInRadians) + 360) % 360;
if (!destinationCalibrated) calibratedSensorFloat = azimuthFloat;
azimuthFloat = (float)Math.round(azimuthInDegress * 10) / 10;
rollFloat = (float)Math.round(rollInDegress * 10) / 10;
pitchFloat = (float)Math.round(pitchInDegress * 10) / 10;
//testText = (""+sensorFloat);
if (startPositionWasNotImplemented)
{
startImplementPosition = azimuthFloat;
startPositionWasNotImplemented = false;
//floats inRi array:
rangePositionLow[0] = verifyDegrees((startImplementPosition-66)-7);
rangePositionHigh[0] = verifyDegrees((startImplementPosition-66)+7);
rangePositionLow[1] = verifyDegrees((startImplementPosition-33)-7);
rangePositionHigh[1] = verifyDegrees((startImplementPosition-33)+7);
rangePositionLow[2] = verifyDegrees(startImplementPosition - 7);
rangePositionHigh[2] = verifyDegrees(startImplementPosition + 7);
rangePositionLow[3] = verifyDegrees((startImplementPosition+33)-7);
rangePositionHigh[3] = verifyDegrees((startImplementPosition+33)+7);
rangePositionLow[4] = verifyDegrees((startImplementPosition+66)-7);
rangePositionHigh[4] = verifyDegrees((startImplementPosition+66)+7);
verifyRange();
//IF LOW>HIGH
}
mCurrentDegree = -azimuthInDegress;
}
}

@Override
public void onAccuracyChanged(Sensor sensor, int accuracy) 
{
// TODO Auto-generated method stub
}
//new sensors end
//mjpeg start
final Handler MjpegViewHandler = new Handler()
{
@Override
public void handleMessage(Message msg)
{
switch (msg.obj.toString())
{
case "DISCONNECTED" :
// TODO : When video stream disconnected
break;
case "CONNECTION_PROGRESS" :
// TODO : When connection progress
break;
case "CONNECTED" :
// TODO : When video streaming connected
break;
case "CONNECTION_ERROR" :
// TODO : When connection error
break;
case "STOPPING_PROGRESS" :
// TODO : When MjpegViewer is in stopping progress
break;
}
}
};
//mjpeg end

private float verifyDegrees(float f)
{
if (f > 360)
{verifiedRange = f - 360;}
else if (f < 0)
{verifiedRange = 360+f;}
else
{verifiedRange = f;}
return verifiedRange;
}

private void verifyRange()
{
for (int i=0;i<5;i++)
{
if (rangePositionHigh[i]<rangePositionLow[i])
{rangeUpward[i]=true;}
else
{rangeUpward[i]=false;}
}
}

//42 start
private void CheckIfServiceIsRunning() 
{
//If the service is running when the activity starts, we want to automatically bind to it.
if (MqttConnectionManagerService.isRunning()) 
{
doBindService();
testText = "service is-running";
}
else
{
testText = "service no running";
}
}

void doBindService() 
{
bindService(new Intent(this, MqttConnectionManagerService.class), mConnection, Context.BIND_AUTO_CREATE);
mIsBound = true;
//textStatus.setText("Binded");
}

void doUnbindService() 
{
if (mIsBound) 
{
// If we have received the service, and hence registered with it, then now is the time to unregister.
if (mService != null) 
{
try 
{
Message msg = Message.obtain(null, MqttConnectionManagerService.MSG_UNREGISTER_CLIENT);
msg.replyTo = mMessenger;
mService.send(msg);
}
catch (RemoteException e) 
{
// There is nothing special we need to do if the service has crashed.
}
}
// Detach our existing connection.
unbindService(mConnection);
mIsBound = false;
//textStatus.setText("Unbinded");
}
}


public void sendMessageStringToService(String messageString0, String messageString1, String messageString2)
{
if (mIsBound) 
{
if (mService != null) 
{
try 
{
Bundle b = new Bundle();
b.putString("str1", messageString0);
b.putString("str2", messageString1);
b.putString("str3", messageString2);
Message msg = Message.obtain(null, MqttConnectionManagerService.MSG_SET_STRING_VALUE);
msg.setData(b);
mService.send(msg);
}
catch (RemoteException e) 
{
// 
}
}
}
}
//42 end

}
