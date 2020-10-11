package com.mycompany.myapp81;

import android.media.*;
import android.view.*;
import java.io.*;
import java.util.*;

public class AudioThread extends Thread
{
private boolean runFlag = false;
MediaPlayer mediaPlayer;
boolean isPaused;//is need??
String audioSourceURL;

public AudioThread(String audioSourceString)
{
mediaPlayer = new MediaPlayer();
mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
isPaused = false;//is need??
audioSourceURL = audioSourceString;
}

public void run()
{
try 
{
if (!isPaused)
{
//mp.setDataSource(audioSourceURL);
mediaPlayer.setDataSource("https://archive.org/download/testmp3testfile/mpthreetest.mp3");
mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() 
{
@Override public void onPrepared(MediaPlayer mediaPlayer) 
{
mediaPlayer.start();
}
});
mediaPlayer.prepareAsync();
}
else if (isPaused)
{
mediaPlayer.start();
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
mediaPlayer.stop();
}

}//end of class
