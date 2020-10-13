package com.starweaver.membrana;
/*
this is another FragmentMjpgStream
than in the "unused" folder: two
different fragments with same name
*/
//IS WE NEED THIS CLASS?(OR WE HAVE ALL THIS IN IMPLEMENT CLASS?)
import android.app.*;
import android.os.*;
import android.util.*;
import android.view.*;
import yjkim.mjpegviewer.*;

public class FragmentMjpgStream extends Fragment 
{
MjpegView mv;

@Override
public void onAttach(Activity activity) 
{
super.onAttach(activity);
}

public void onCreate(Bundle savedInstanceState) 
{
super.onCreate(savedInstanceState);
}

public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
{
View fragmentMjpgStreamView = inflater.inflate(R.layout.fragment_mjpg_stream, container, false);
mv = (MjpegView)fragmentMjpgStreamView.findViewById(R.id.mjpgView);
//mv.Start("http://webcam.st-malo.com/axis-cgi/mjpg/video.cgi?resolution=352x288", MjpegViewHandler);
mv.Start("http://192.168.0.108:8080/?action=stream", MjpegViewHandler);
//mv.Start("http://217.197.157.7:7070/axis-cgi/mjpg/video.cgi?resolution=320x240", MjpegViewHandler);
return fragmentMjpgStreamView;
//return inflater.inflate(R.layout.fragment_mjpg_stream, null) ;
}

public void onActivityCreated(Bundle savedInstanceState) 
{
super.onActivityCreated(savedInstanceState);
}

public void onStart() 
{
super.onStart();
}

public void onResume() 
{
super.onResume();
}

public void onPause() 
{
super.onPause();
}

public void onStop() 
{
super.onStop();
}

public void onDestroyView() 
{
super.onDestroyView();
mv.Stop();
}

public void onDestroy() 
{
super.onDestroy();
}

public void onDetach() 
{
super.onDetach();
}

final Handler MjpegViewHandler = new Handler()
{
@Override
public void handleMessage(Message msg)
{
Log.d("State : ", msg.obj.toString());
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

}
