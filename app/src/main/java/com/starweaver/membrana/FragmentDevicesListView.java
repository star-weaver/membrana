package com.starweaver.membrana;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import java.util.*;

public class FragmentDevicesListView extends Fragment
{
//TextView tv;
MqttConnectionManagerService mqttConnectionManagerService;//TEMPORARY SERGEY
ListView lv;
String[] arrayx_string = new String[5];//is need

ArrayList<String> arrayListDevices = new ArrayList<String>();
ArrayList<Integer> arrayListIcons = new ArrayList<Integer>();
ArrayList<Integer> arrayListPattern = new ArrayList<Integer>();
String[] mqttString = new String[5];//is need
private String topicString;
boolean result;

//IS NEED?
public FragmentDevicesListView(String[] s, ArrayList<Integer> pattern)
{
arrayx_string = s;
//arrayListPattern.clear();
arrayListPattern = pattern;
}

public FragmentDevicesListView(ArrayList<String> arrs, ArrayList<Integer> arri, ArrayList<Integer> arrp, String t)
{
arrayListDevices.clear();
arrayListIcons.clear();
arrayListPattern.clear();
arrayListDevices = arrs;
arrayListIcons = arri;
arrayListPattern = arrp;
topicString = t;
}

public FragmentDevicesListView(ArrayList<String> arrs, ArrayList<Integer> arri, ArrayList<Integer> arrp, String t, String[] s)
{
arrayListDevices.clear();
arrayListIcons.clear();
arrayListPattern.clear();
arrayListDevices = arrs;
arrayListIcons = arri;
arrayListPattern = arrp;
topicString = t;
mqttString = s;
}

@Override public void onCreate(Bundle savedInstanceState) 
{
super.onCreate(savedInstanceState);
setRetainInstance(true);
}

@Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
{
//View view4=inflater.inflate(R.layout.fragment_gridview, container, false);
View view4=inflater.inflate(R.layout.temporary_fragment_listview, container, false);
//Get the widgets reference from XML layout
//tv = (TextView)view4.findViewById(R.id.tv);
//lv = (ListView)view4.findViewById(R.id.gridview);
lv = (ListView)view4.findViewById(R.id.listview);
//lv.setAdapter(new TemporaryListviewAdapter(this, arrayx_string, arrayListPattern));
//lv.setAdapter(new DeviceListViewAdapter(getActivity(), arrayListDevices, arrayListIcons, arrayListPattern, topicString, mqttString));
//lv.setAdapter(new TemporaryListviewAdapter(getActivity(), arrayListDevices, arrayListIcons, arrayListPattern, mqttString));
//tv.setText("clicked");
//Toast.makeText(((MainActivity)getActivity()), "TRUE", Toast.LENGTH_LONG).show();
((MainActivity)getActivity()).setMultisensorAvaiable(true);
lv.setOnItemClickListener(new AdapterView.OnItemClickListener() 
{
@Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
{
if (position == 0)
{
/* tv.setText("GridView item clicked");
 }
 else if (position == 1)
 {
 tv.setText("111 888 111");
 }
 else
 {*/
}
else if (position == 1)//TEMPORARY SERGEY
{
//mqttHelper.Publish("1");
}
else if (position == 2)//TEMPORARY SERGEY
{
//mqttHelper.Publish("0");
}
}
});//END OF CLICKLISTENER..
//return inflater.inflate(R.layout.fragment4, null);
return view4;
}
/*public TextView gettv0() 
{
return tv;
}*/
public ListView getlv0()
{
return lv;
}

@Override
public void onDestroyView()
{
super.onDestroyView();
//Toast.makeText(((MainActivity)getActivity()), "FALSE", Toast.LENGTH_LONG).show();
((MainActivity)getActivity()).setMultisensorAvaiable(false);
}

//IS NEED?
boolean isMultisensorNeed()
{
for ( int i=0; i<= arrayListPattern.size(); i++ )
{
//
result = (arrayListPattern.get(i) == 1) ? true : false;
}
return result;
}

//https://stackoverflow.com/questions/20702333/refresh-fragment-at-reload
}
