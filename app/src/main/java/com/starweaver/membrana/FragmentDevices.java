package com.starweaver.membrana;

import android.app.*;
import android.os.*;
import android.util.*;
import android.view.*;
import android.widget.*;
import java.util.*;
import org.eclipse.paho.client.mqttv3.*;

public class FragmentDevices extends Fragment
{
TitleString titleString;
TitleIcon titleIcon;
FragmentRGBcolor fragmentRGBColor;
FragmentEnter0 fragmentEnter0;
FragmentEnter1 fragmentEnter1;
FragmentTimePicker fragmentTimePicker;
DeviceListViewAdapter deviceListViewAdapter;
private ImplementInRobot listener;
ListView lvfmp;
String[] arrayx_string = new String[3];
ArrayList<String> arrayListDevices = new ArrayList<String>();
ArrayList<Integer> arrayListIcons = new ArrayList<Integer>();
ArrayList<Integer> arrayListPattern = new ArrayList<Integer>();
String topicString;
static String settingsTitleString;
static String timerTitleString;
boolean result;
boolean multisensorAvaiable;
int buttonStateInt;
int multisensorArrayPosition;

public FragmentDevices(ArrayList<String> arrs, ArrayList<Integer> arri, ArrayList<Integer> arrp, String t, int buttonStateInt)
{
arrayListDevices.clear();
arrayListIcons.clear();
arrayListPattern.clear();
arrayListDevices = arrs;
arrayListIcons = arri;
arrayListPattern = arrp;
topicString = t;
multisensorAvaiable = false;
this.buttonStateInt = buttonStateInt;
}

public FragmentDevices(ArrayList<String> arrs, ArrayList<Integer> arri, ArrayList<Integer> arrp, ImplementInRobot listener, String t, boolean multisensorAvaiable, int buttonStateInt, int multisensorArrayPosition)
//FragmentDevices(environmentDevicesNamesArrayList, environmentDevicesPicturesArrayList, environmentDevicesPatternsArrayList, implementInRobotListener, environmentName, buttonStateInt, multisensorArrayPosition)
{
arrayListDevices.clear();
arrayListIcons.clear();
arrayListPattern.clear();
arrayListDevices = arrs;
arrayListIcons = arri;
arrayListPattern = arrp;
this.listener = listener;
topicString = t;
this.multisensorAvaiable = multisensorAvaiable;
this.buttonStateInt = buttonStateInt;
this.multisensorArrayPosition = multisensorArrayPosition;
}

@Override public void onCreate(Bundle savedInstanceState) 
{
super.onCreate(savedInstanceState);
setRetainInstance(true);
fragmentRGBColor = new FragmentRGBcolor(buttonStateInt);
fragmentEnter0 = new FragmentEnter0();//TEMPORARY!!!???
fragmentEnter1 = new FragmentEnter1(listener);//TEMPORARY!!!???

if (multisensorAvaiable) ((MainActivity)getActivity()).setBottomNavigationView(true);
titleString = new TitleString();
if (topicString != "Settings" && topicString != "Timer") titleString.setGlobalButtonStateInt(buttonStateInt);
}

@Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
{
if (multisensorAvaiable) 
{
((MainActivity)getActivity()).seTMultisensorTopic(topicString);
titleString.setMultisensorBoolean(true);
}
titleString = new TitleString();
titleIcon = new TitleIcon();
View view_lvfmp=inflater.inflate(R.layout.fragment_lv, container, false);
deviceListViewAdapter = new DeviceListViewAdapter(getActivity(), arrayListDevices, arrayListIcons, arrayListPattern, listener, topicString, multisensorAvaiable, buttonStateInt, FragmentDevices.this);
lvfmp = (ListView)view_lvfmp.findViewById(R.id.mobile_list);
lvfmp.setAdapter(deviceListViewAdapter);
titleString.setMultisensorStateArray(multisensorArrayPosition, multisensorAvaiable);
((MainActivity)getActivity()).setMultisensorArrayPosition(multisensorArrayPosition);
return view_lvfmp;
}

boolean isMultisensorNeed()
{
for ( int i=0; i<= arrayListPattern.size(); i++ )
{
result = (arrayListPattern.get(i) == 1) ? true : false;
}
return result;
}

public ListView getFragmentDevicesListview()
{
return lvfmp;
}

@Override
public void onResume()
{
super.onResume();
if (multisensorAvaiable) 
{
((MainActivity)getActivity()).seTMultisensorTopic(topicString);
((MainActivity)getActivity()).setBottomNavigationView(true);
}
}

@Override
public void onDestroyView()
{
super.onDestroyView();
titleString.setMultisensorStateArray(multisensorArrayPosition, false);
if (multisensorAvaiable) 
{
((MainActivity)getActivity()).setBottomNavigationView(false);
titleString.setMultisensorBoolean(false);
}
}

public void showFragmentRGBcolor(int position)
{
fragmentRGBColor.setToggleButtonPosition(position);
titleString.Up();
((MainActivity)getActivity()).mTitleTextView.setText(titleString.getTitleList(2));
titleIcon.Up();
((MainActivity)getActivity()).fragmentMain.setIcon(titleIcon.getIconsList(2), R.drawable.sand);
FragmentTransaction fTrans = getFragmentManager().beginTransaction();
fTrans.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right, R.animator.slide_in_right, R.animator.slide_out_left);
fTrans.replace(R.id.frgmCont2, fragmentRGBColor);
fTrans.addToBackStack(null);
fTrans.commit();
}

public void showFragmentEnterAstral()
{
titleString.Up();
((MainActivity)getActivity()).mTitleTextView.setText(titleString.getTitleList(5));
titleIcon.Up();
((MainActivity)getActivity()).fragmentMain.setIcon(titleIcon.getIconsList(2), R.drawable.sand);
FragmentTransaction fTrans = getFragmentManager().beginTransaction();
fTrans.add(R.id.frgmCont, fragmentEnter0);
fTrans.addToBackStack(null);
fTrans.replace(R.id.frgmCont2, fragmentEnter1);
fTrans.addToBackStack(null);
fTrans.commit();
}

public void showFragmentSettings(int position, String topicString1, String topicString2)
{
titleString.Up();
settingsTitleString = "Settings "+titleString.getGlobalTitle();
((MainActivity)getActivity()).mTitleTextView.setText(settingsTitleString);
titleString.setSettingsTitle(settingsTitleString);
titleString.setFragmentSettinsBoolean(true);
titleString.setSettingsTopicStrings(topicString1, topicString2);
titleIcon.Up();
titleString.setGlobalButtonStateInt(buttonStateInt);
if (multisensorAvaiable)
{
titleString.setGlobalPositionInt(position-1);
}
else
{
titleString.setGlobalPositionInt(position);
}
FragmentTransaction fTrans = getFragmentManager().beginTransaction();
fTrans.replace(R.id.frgmCont2, ((MainActivity)getActivity()).fragmentList.get(5));
fTrans.addToBackStack(null);
fTrans.commit();
}

public void showFragmentTimerPeriods(int position)
{
titleString.Up();
timerTitleString = "Timer "+titleString.getGlobalTitle();
((MainActivity)getActivity()).mTitleTextView.setText(timerTitleString);
titleString.setTimerTitle(timerTitleString);
titleString.setFragmentTimerPeriodsBoolean(true);
titleIcon.Up();
FragmentTransaction fTrans = getFragmentManager().beginTransaction();
fTrans.replace(R.id.frgmCont2, ((MainActivity)getActivity()).fragmentList.get(6));
fTrans.addToBackStack(null);
fTrans.commit();
}

public void showFragmentTimePicker(int globalPosition, int globalButtonState, int position)
{
fragmentTimePicker = new FragmentTimePicker(globalPosition, globalButtonState, position);
titleString.Up();
((MainActivity)getActivity()).mTitleTextView.setText("Set Timer "+titleString.getGlobalTitle());
titleIcon.Up();
FragmentTransaction fTrans = getFragmentManager().beginTransaction();
fTrans.replace(R.id.frgmCont2, fragmentTimePicker);
fTrans.addToBackStack(null);
fTrans.commit();
}

public void sendMessageToMqttConnectionManagerService(String topic, String position, String message)
{
((MainActivity)getActivity()).sendMessageStringToService(topic, position, message);
}

public void sendPublishLightMessageToMqttConnectionManagerService(int i)
{
((MainActivity)getActivity()).sendMessageStringPublishLightToService(i);
}

public void sendMessageToToast(String toast)
{
((MainActivity)getActivity()).toastMessage(toast);
}

public int getCurrentLevelTreeNodeChildEnvironmentPicture(int position)
{
return ((MainActivity)getActivity()).currentLevelTreeNode.getChild(position).getEnvironmentPicture();
}

public String getCurrentLevelTreeNodeChildEnvironmentName(int position)
{
return ((MainActivity)getActivity()).currentLevelTreeNode.getChild(position).getEnvironmentName();
}

public void changeCurrentTreeNode(int nodeNumber)
{
((MainActivity)getActivity()).switchToChildNode(nodeNumber);
}

public String[] getCurrentMqttString()
{
return ((MainActivity)getActivity()).currentLevelTreeNode.getMqttString();
}

}
