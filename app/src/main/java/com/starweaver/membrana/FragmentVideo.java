package com.mycompany.myapp81;

import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import java.util.*;

public class FragmentVideo extends Fragment 
{
TitleString titleString; 
int cameraNumber;

//https://stackoverflow.com/questions/9632387/arraylist-add-throws-arrayindexoutofboundsexception
ArrayList<String> videoLink = new ArrayList<String>(Arrays.asList(
"rtsp://184.72.239.149/vod/mp4:BigBuckBunny_175k.mov", 
"rtsp://184.72.239.149/vod/mp4:BigBuckBunny_175k.mov"
//"rtsp://admin:HuaWei123@192.168.0.101/LiveMedia/ch1/Media1",
//"rtsp://admin:HuaWei123@192.168.0.101/LiveMedia/ch1/Media1"
));

public FragmentVideo(int c) 
{
cameraNumber = c;
}

@Override 
public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
{
View viewVideo= inflater.inflate(R.layout.fragment_video, container, false);
return new VideoSurfaceView(getActivity());
}

public class VideoSurfaceView extends SurfaceView implements SurfaceHolder.Callback
{
private VideoThread videoThread;

public VideoSurfaceView(Context context) 
{
super(context);
getHolder().addCallback(this);
}
@Override 
public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) 
{ 
}
@Override 
public void surfaceCreated(SurfaceHolder surfaceHolder) 
{
videoThread = new VideoThread(getHolder(), videoLink.get(cameraNumber));
//videoThread.setRunning(true);
//((MainActivity)getActivity()).testTextView.setText("SurfaceChanged");
//videoThread.setInt(2);//is need??
videoThread.start();
}

@Override 
public void surfaceDestroyed(SurfaceHolder surfaceHolder) 
{
boolean retry = true;
// завершаем работу потока
//videoThread.setRunning(false);
//videoThread.stop();
while (retry) 
{
try 
{
//((MainActivity)getActivity()).testTextView.setText("SurfaceDestroyed");
videoThread.join();
videoThread.mpstop();
retry = false;
} 
catch (InterruptedException e) 
{
// если не получилось, то будем пытаться еще и еще
}
}
}
}//end of class

}
