package com.starweaver.membrana;

import android.*;
import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import java.util.*;

public class FragmentSecondListview extends Fragment
{
FragmentDevices fragmentDevices;
FragmentVideo fragmentVideo;
FragmentTransaction fTrans;
TitleIcon titleIcon;
TitleString titleString;
ListView lv;
int stringArrayNumber;
int iconArrayNumber;
int patternArrayNumber;

public FragmentSecondListview(int is, int ii, int ip)
{
stringArrayNumber = is;
iconArrayNumber = ii;
patternArrayNumber = ip;
}

@Override 
public void onCreate(Bundle savedInstanceState) 
{
super.onCreate(savedInstanceState);
setRetainInstance(true);
}

@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
{
titleString = new TitleString();
titleIcon = new TitleIcon();

View view_lv=inflater.inflate(R.layout.fragment_lv, container, false);
lv = (ListView)view_lv.findViewById(R.id.mobile_list);
lv.setAdapter(new ListViewAdapter(this, titleString.getListviewArraylist(stringArrayNumber), titleIcon.getListviewArraylist(iconArrayNumber)));
lv.setOnItemClickListener(new AdapterView.OnItemClickListener() 
{
@Override 
public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
{
if (position == 0)
{
titleString.Up();
titleIcon.Up();
fragmentVideo = new FragmentVideo(0);
fTrans = getFragmentManager().beginTransaction();
fTrans.add(R.id.frgmCont, fragmentVideo);
fTrans.addToBackStack(null);

switch(titleString.getSecondListviewPosition())
{
case 0:
((MainActivity)getActivity()).mTitleTextView.setText(titleString.getTitleList(0));
titleString.setGlobalTitleFirst(0);
((MainActivity)getActivity()).fragmentMain.setIcon(titleIcon.getIconsList(0), R.drawable.sand);
fTrans.replace(R.id.frgmCont2, ((MainActivity)getActivity()).fragmentList.get(1));
break;
case 1:
((MainActivity)getActivity()).mTitleTextView.setText(titleString.getTitleList(1));
titleString.setGlobalTitleFirst(1);
((MainActivity)getActivity()).fragmentMain.setIcon(titleIcon.getIconsList(1), R.drawable.sand);
fTrans.replace(R.id.frgmCont2, ((MainActivity)getActivity()).fragmentList.get(3));
break;
}//end switch
fTrans.addToBackStack(null);
fTrans.commit();
}
if (position == 1)
{
titleString.Up();
titleIcon.Up();
fragmentVideo = new FragmentVideo(0);
fTrans = getFragmentManager().beginTransaction();
fTrans.add(R.id.frgmCont, fragmentVideo);
fTrans.addToBackStack(null);

switch(titleString.getSecondListviewPosition())
{
case 0:
((MainActivity)getActivity()).mTitleTextView.setText(titleString.getTitleList(3));
titleString.setGlobalTitleFirst(3);
((MainActivity)getActivity()).fragmentMain.setIcon(titleIcon.getIconsList(0), R.drawable.sand);
fTrans.replace(R.id.frgmCont2, ((MainActivity)getActivity()).fragmentList.get(2));
break;
case 1:
((MainActivity)getActivity()).mTitleTextView.setText(titleString.getTitleList(4));
titleString.setGlobalTitleFirst(4);
((MainActivity)getActivity()).fragmentMain.setIcon(titleIcon.getIconsList(1), R.drawable.sand);
//titleString.setMqttStringInt(0);//temporary mqttstring
fTrans.replace(R.id.frgmCont2, ((MainActivity)getActivity()).fragmentList.get(4));
break;
}//end switch

fTrans.addToBackStack(null);
fTrans.commit();
}
}
});
return view_lv;
}

public ListView getlv()
{
return lv;
}
}

