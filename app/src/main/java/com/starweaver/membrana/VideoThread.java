package com.starweaver.membrana;

import android.media.*;
import android.view.*;
import java.io.*;
import java.util.*;

public class VideoThread extends Thread
{
private boolean runFlag = false;
private SurfaceHolder surfaceHolder;
MediaPlayer mp;
boolean isPaused;//is need??
String cameraURL;

public VideoThread(SurfaceHolder surfaceHolder, String c)
{
this.surfaceHolder = surfaceHolder;
mp = new MediaPlayer();
isPaused = false;//is need??
cameraURL = c;
}

public void run()
{
try 
{
mp.setDisplay(surfaceHolder);
if (!isPaused)
{
//https://developer.android.com/guide/topics/media/mediaplayer#java
//mp.setDataSource(cameraURL);
//mp.setDataSource(titleString.getVideoLink(cameraNumber));
//mp.setDataSource("rtsp://184.72.239.149/vod/mp4:BigBuckBunny_175k.mov");
//mp.setDataSource("rtsp://888888:888888@192.168.0.108:554/cam/realmonitor?channel=1&subtype=1");
//mp.setDataSource("rtsp://192.168.0.108:8554/");
mp.setDataSource("rtsp://888888:888888@37.57.199.109:554/cam/realmonitor?channel=1&subtype=1"); 
//mp.setDataSource("rtsp://3gp-tv2. unwire.dk/livestreaming/tv2/tv2news/tv2news_108k.sdp");
mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() 
{
@Override public void onPrepared(MediaPlayer mp) 
{
mp.start();
}
});
mp.prepareAsync();
}
else if (isPaused)
{
mp.start();
}
else
{
//
}
isPaused = false;//is need??
} //end try
catch (IOException io) 
{        
} 
catch (IllegalStateException is)
{
}
}//end run

public void setRunning(boolean run) 
{
runFlag = run;
}

public void mpstop()
{
mp.stop();
}

}//end of class
