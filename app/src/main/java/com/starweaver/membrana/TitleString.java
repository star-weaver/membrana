package com.mycompany.myapp81;

import java.util.*;

public class TitleString
{
static String titleString = "Main";
static String globalTitleFirst = " ";
static String globalTitleSecond = " ";
static String settingsTitle;
static String settingsTopicString1;
static String settingsTopicString2;
static String settingsTopicReturnString;
static String timerTitle;
String[] temporaryMqttStringArray;
static int titleInt = 0;
static int integer = 0;
static int bulbInt = 0;
static int secondListViewPosition;
static int mqttStringInt = 0;
static int globalButtonStateInt = 0;
static int globalPositionInt = 0;
static boolean multisensor = false;
static boolean implementAvatar = true;
static boolean fragmentSettings = false;
static boolean fragmentTimerPeriods = false;
private static boolean chartDataWasReceived = false;
private static boolean chartTransactionIsApproved = false;

static Integer temporaryTimeData;
//test fragment refreshing
static Integer[] stint = {0,0};
//temporary mqttstring start
static String[] mqttArray0 = new String[]{"00","00","00","00","00"};
static String[] mqttArray1 = new String[]{"11","11","11","11","11"};
static String[] mqttArray2 = new String[]{"22","22","22","22","22"};
String mqttArrayString;
String temporaryStboolString;
String[] mqttArrayStringArray;
//temporary mqttstring end
static Boolean[][] stbool = {{false,false},{false},{false,false},{false},{false,false}};
//static Boolean[][] settingsModeButtonState = {{false,false},{false,false},{false,false},{false,false},{false,false}};
//static List<ArrayList<Boolean>> settingsButtonStateArray = new ArrayList<ArrayList<Boolean>>();
static ArrayList<ArrayList<ArrayList<Boolean>>> settingsButtonStateArray = new ArrayList<>();
static ArrayList<ArrayList<ArrayList<Boolean>>> timerPeriodsStateArray = new ArrayList<>();
public static ArrayList<ArrayList<ArrayList<ArrayList<Integer>>>> timerDataArrayList = new ArrayList<>();
static ArrayList<Boolean> multisensorStateArray = new ArrayList<Boolean>();
static ArrayList<Float> lineChartArrayList = new ArrayList<Float>(Arrays.asList(3f, 5f, 9f, 2f, 5f, 6f, 10f, 5f));

//test fragment refreshing
//IS NEED???
//ArrayList<String> titlelist = new ArrayList<String>(Arrays.asList("Main", "Home", "Pool", "Yard"));
ArrayList<String> videoLink = new ArrayList<String>(Arrays.asList(
													"rtsp://184.72.239.149/vod/mp4:BigBuckBunny_175k.mov", 
													"rtsp://184.72.239.149/vod/mp4:BigBuckBunny_175k.mov"
													));

//need deprecate?
List<ArrayList<String>> titleStringArray = new ArrayList<ArrayList<String>>();
List<ArrayList<String>> deviceStringArray = new ArrayList<ArrayList<String>>();
List<ArrayList<String>> listviewStringArray = new ArrayList<ArrayList<String>>();
List<ArrayList<String>> videolinkStringArray = new ArrayList<ArrayList<String>>();
List<String[]> mqttStringArray = new ArrayList<String[]>();
List<ArrayList<Integer>> listviewPatternArray = new ArrayList<ArrayList<Integer>>();
static ArrayList<Integer> settingsPatternArrayList = new ArrayList<Integer>(Arrays.asList(5,6));
static ArrayList<Integer> timePeriodsPatternArrayList = new ArrayList<Integer>(Arrays.asList(7,7,7));

public TitleString()
{
setPrimeArray();
}

//temporary start
public int getStboolLength()
{
return stbool.length;
}
//temporary end

//temporary start
public String getStboolString()
{
for (int i = 0; i < stbool.length; i++) 
{
temporaryStboolString=temporaryStboolString+i+":"+" ";
for (int j = 0; j < stbool[i].length; j++) 
{
temporaryStboolString = temporaryStboolString+stbool[i][j].toString();
}
temporaryStboolString=temporaryStboolString+"|";
}
return temporaryStboolString;
}
//temporary end

public int getTitleInt()
{
return titleInt;
}

public int getI()
{
return integer;
}

public String getTitleList(int i)
{
String s = titleStringArray.get(i).get(titleInt);
//String s = "Test";
integer = i;
return s;
}

public ArrayList<String> getDeviceArraylist(int i)
{
return deviceStringArray.get(i);
}

public ArrayList<Integer> getListViewPatternArraylist(int i)
{
return listviewPatternArray.get(i);
}

public Boolean getToggleButtonPattern(int i0, int i1)
{
return stbool[i0][i1];
}

public void setToggleButtonPattern(int i0, int i1, Boolean b)
{
stbool[i0][i1] = b;
}

/*public Boolean getSettingsModeButtonState(int i0, int i1)
{
return settingsModeButtonState[i0][i1];
}*/

/*public void setSettingsModeButtonState(int i0, int i1, Boolean b)
{
settingsModeButtonState[i0][i1] = b;
}*/

public ArrayList<String> getListviewArraylist(int i)
{
return listviewStringArray.get(i);
}

public void setTitleString(String s)
{
this.titleString = s;
}

public void setTitleInt(int i)
{
this.titleInt = i;
}

public int getBulbInt()
{
return bulbInt;
}

public void setBulbInt(int i)
{
bulbInt = i;
}

public int getSecondListviewPosition()
{
return secondListViewPosition;
}

public void setSecondListViewPosition(int i)
{
secondListViewPosition = i;
}

public String getVideoLink(int x)
{
return videoLink.get(x);
}

public void setMqttStringPosition(int arrayPosition, int positionNumber, String string)
{
switch (arrayPosition)
{
case 0:
mqttArray0[positionNumber] = string;
break;
case 1:
mqttArray1[positionNumber] = string;
break;
case 2:
mqttArray2[positionNumber] = string;
break;
}
}

public String getMqttStringPosition(int arrayPosition, int stringPosition)
{
/*temporaryMqttStringArray = mqttStringArray.get(arrayPosition);
 return temporaryMqttStringArray[stringPosition];*/
//temporary mqttstring start
switch (arrayPosition)
{
case 0:
mqttArrayString = mqttArray0[stringPosition];
break;
case 1:
mqttArrayString = mqttArray1[stringPosition];
break;
case 2:
mqttArrayString = mqttArray2[stringPosition];
break;
}
return mqttArrayString;
//temporary mqttstring end
}

public void setMultisensorStateArray(int arrayPosition, boolean b)
{
multisensorStateArray.add(arrayPosition, b);
}

public boolean getMultisensorStateArray(int arrayPosition)
{
return multisensorStateArray.get(arrayPosition);
}

public void setMqttStringInt(int i)
{
mqttStringInt = i;
}

public int getMqttStringInt()
{
return mqttStringInt;
}

public void setMultisensorBoolean(boolean b)
{
multisensor = b;
}

public boolean getMultisensorBoolean()
{
return multisensor;
}

public ArrayList<Integer> getSettingsPatternArrayList()
{
return settingsPatternArrayList;
}

public ArrayList<Integer> getTimePeriodsPatternArrayList()
{
return timePeriodsPatternArrayList;
}

/*public void setSettingsPatternArrayList(ArrayList<Integer> arrayList)
{
settingsPatternArrayList.clear();
settingsPatternArrayList = arrayList;
}*/

public boolean getImplementAvatarState()
{
return implementAvatar;
}

public void setImplementAvatarState(boolean b)
{
implementAvatar = b;
}

public int getGlobalButtonStateInt()
{
return globalButtonStateInt;
}

public void setGlobalButtonStateInt(int i)
{
globalButtonStateInt = i;
}

public int getGlobalPositionInt()
{
return globalPositionInt;
}

public void setGlobalPositionInt(int i)
{
globalPositionInt = i;
}

public ArrayList<Float> getLineChartArrayList()
{
//if (chartDataWasReceived)
//{
//
//--show loading
//--send request
//--waiting some time for answer
//---if answer-> return ok and
//--post delayed remove loading
//
//chartDataWasReceived = false;
return lineChartArrayList;
}

public void setLineChartArrayList(ArrayList<Float> inputArrayList)
{
lineChartArrayList.clear();
lineChartArrayList = inputArrayList;
}

public void setChartDataWasRecieved(boolean b)
{
chartDataWasReceived= b;
}

public boolean getChartDataWasRecieved()
{
return chartDataWasReceived;
}

public boolean getChartTransactionIsApproved()
{
return chartTransactionIsApproved;
}

public void setChartTransactionIsApproved(boolean b)
{
chartTransactionIsApproved =b;
}

public String getGlobalTitle()
{
return globalTitleFirst+" "+globalTitleSecond;
}

public void setGlobalTitleFirst(int i)
{
globalTitleFirst = titleStringArray.get(i).get(titleInt);
}

public void setGlobalTitleSecond(String string)
{
globalTitleSecond = string;
}

public void setSettingsTitle(String string)
{
settingsTitle = string;
}

public void setSettingsTopicStrings(String str1, String str2)
{
settingsTopicString1 = str1;
settingsTopicString2 = str2;
}

public void setTimerTitle(String string)
{
timerTitle = string;
}

public String getSettingsTitle()
{
return settingsTitle;
}

public String getSettingTopicString(int i)
{
switch (i)
{
case 1:
settingsTopicReturnString = settingsTopicString1;
break;
case 2:
settingsTopicReturnString = settingsTopicString2;
break;
default:
settingsTopicReturnString = "null";
break;
}
return settingsTopicReturnString;
}

public String getTimerTitle()
{
return timerTitle;
}

public void setFragmentSettinsBoolean(boolean b)
{
fragmentSettings = b;
}

public void setFragmentTimerPeriodsBoolean(boolean b)
{
fragmentTimerPeriods = b;
}

public boolean fragmentSettings()
{
return fragmentSettings;
}

public boolean fragmentTimerPeriods()
{
return fragmentTimerPeriods;
}

private void setPrimeArray()
{
titleStringArray.add(0, new ArrayList<String>(Arrays.asList("MEMBRANA", "House", "Pool", "null")));
titleStringArray.add(1, new ArrayList<String>(Arrays.asList("MEMBRANA", "Flat", "Hallway", "null")));
titleStringArray.add(2, new ArrayList<String>(Arrays.asList("MEMBRANA", "Office", "Set RGB Lamp Color", "null")));
titleStringArray.add(3, new ArrayList<String>(Arrays.asList("MEMBRANA", "House", "Yard", "null")));
titleStringArray.add(4, new ArrayList<String>(Arrays.asList("MEMBRANA", "Flat", "Greenhouse", "null")));
titleStringArray.add(5, new ArrayList<String>(Arrays.asList("MEMBRANA", "Office", "Enter Astral")));

deviceStringArray.add(0, new ArrayList<String>(Arrays.asList("Gate", "Lights")));
deviceStringArray.add(1, new ArrayList<String>(Arrays.asList("Lights")));
deviceStringArray.add(2, new ArrayList<String>(Arrays.asList("Multisensor", "Lights", "Plug", "Avatar")));
deviceStringArray.add(3, new ArrayList<String>(Arrays.asList("Multisensor", "Lights")));
deviceStringArray.add(4, new ArrayList<String>(Arrays.asList("Multisensor", "Lights", "Drip")));//greenhouse
deviceStringArray.add(5, new ArrayList<String>(Arrays.asList("Sensor Mode", "Timer Mode")));
deviceStringArray.add(6, new ArrayList<String>(Arrays.asList("Time period 1", "Time period 2", "Time period 3")));

listviewStringArray.add(0, new ArrayList<String>(Arrays.asList("House", "Flat", "Office")));
listviewStringArray.add(1, new ArrayList<String>(Arrays.asList("Pool", "Yard")));
listviewStringArray.add(2, new ArrayList<String>(Arrays.asList("Hallway", "Greenhouse")));

listviewPatternArray.add(0, new ArrayList<Integer>(Arrays.asList(0, 0)));
listviewPatternArray.add(1, new ArrayList<Integer>(Arrays.asList(0)));
listviewPatternArray.add(2, new ArrayList<Integer>(Arrays.asList(1, 3, 0, 2)));
listviewPatternArray.add(3, new ArrayList<Integer>(Arrays.asList(1, 0)));
listviewPatternArray.add(4, new ArrayList<Integer>(Arrays.asList(1, 0, 4)));//greenhouse

mqttStringArray.add(0, new String[]{"00", "00", "00", "000", "000"});
mqttStringArray.add(1, new String[]{"11", "11", "11", "111", "111"});
mqttStringArray.add(2, new String[]{"22", "22", "22", "222", "222"});

multisensorStateArray.add(0, false);
multisensorStateArray.add(1, false);
multisensorStateArray.add(2, false);
}

public void setSettingsButtonStateArray()
{
for (int i = 0; i < stbool.length; i++) 
{
settingsButtonStateArray.add(new ArrayList<ArrayList<Boolean>>());
for (int j = 0; j < stbool[i].length; j++) 
{
settingsButtonStateArray.get(i).add(new ArrayList<Boolean>());
settingsButtonStateArray.get(i).get(j).add(0,false);
settingsButtonStateArray.get(i).get(j).add(1,false);
}
}
}

public void setTimerPeriodsStateArray()
{
for (int i = 0; i < stbool.length; i++) 
{
timerPeriodsStateArray.add(new ArrayList<ArrayList<Boolean>>());
for (int j = 0; j < stbool[i].length; j++) 
{
timerPeriodsStateArray.get(i).add(new ArrayList<Boolean>());
timerPeriodsStateArray.get(i).get(j).add(0,false);
timerPeriodsStateArray.get(i).get(j).add(1,false);
timerPeriodsStateArray.get(i).get(j).add(2,false);
}
}
}

//public static ArrayList<Array(i)List<Array(j)List<Arra(b)yList<Integer>>>> timerDataArrayList = new ArrayList<>();
//
public void setTimerDataArrayList()
{
for (int i = 0; i < stbool.length; i++) 
{
timerDataArrayList.add(new ArrayList<ArrayList<ArrayList<Integer>>>());
for (int j = 0; j < stbool[i].length; j++) 
{
timerDataArrayList.get(i).add(new ArrayList<ArrayList<Integer>>());
timerDataArrayList.get(i).get(j).add(new ArrayList<Integer>());
timerDataArrayList.get(i).get(j).add(0,new ArrayList<Integer>());
timerDataArrayList.get(i).get(j).add(1,new ArrayList<Integer>());
timerDataArrayList.get(i).get(j).add(2,new ArrayList<Integer>());
for (int k = 0; k < 3; k++)
{
timerDataArrayList.get(i).get(j).get(k).add(0,0);
timerDataArrayList.get(i).get(j).get(k).add(1,0);
timerDataArrayList.get(i).get(j).get(k).add(2,0);
timerDataArrayList.get(i).get(j).get(k).add(3,0);
}
}
}
}

public boolean getSettingsButtonPattern(int buttonStateInt, int position, int type)
{
return settingsButtonStateArray.get(buttonStateInt).get(position).get(type);
}

public void setSettingsButtonPattern(int buttonStateInt, int position, int type, boolean b)
{
settingsButtonStateArray.get(buttonStateInt).get(position).set(type,b);
}

public boolean getTimerPeriodsButtonPattern(int buttonStateInt, int position, int type)
{
return timerPeriodsStateArray.get(buttonStateInt).get(position).get(type);
}

public void setTimerPeriodsButtonPattern(int buttonStateInt, int position, int type, boolean b)
{
timerPeriodsStateArray.get(buttonStateInt).get(position).set(type,b);
}

//BUG!!! This getter dont work!!!
public Integer getTimerData(int globalButtonStateInt, int globalPosition, int position, int type)
{
return timerDataArrayList.get(globalButtonStateInt).get(globalPosition).get(position).get(type);
}

public void setTimerData(int buttonStateInt, int globalPosition, int position, int type, int i)
{
timerDataArrayList.get(buttonStateInt).get(globalPosition).get(position).set(type,i);
}

public void Up()
{
titleInt++;
}

public void Down()
{
titleInt--;
}

}
