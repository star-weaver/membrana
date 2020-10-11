package com.mycompany.myapp81;

import java.util.*;

public class TitleIcon
{
static int iconInt = 0;
static int titleIcon = R.drawable.tost;//is need?
static int integer = 0;
static ArrayList<Integer> settingsDrawableArrayList = new ArrayList<Integer>(Arrays.asList( R.drawable.smart_color_green, R.drawable.smart_color_green));
static ArrayList<Integer> timePeriodsDrawableArrayList = new ArrayList<Integer>(Arrays.asList( R.drawable.smart_color_green, R.drawable.smart_color_green, R.drawable.smart_color_green));
//static List<ArrayList<Integer>> settingsDrawableArrayList = new ArrayList<ArrayList<Integer>>();
/*
 public TitleString()
 {
 setSettingsDrawableArrayListArray();
 }
 =====
 private void setSettingsDrawableArrayListArray()
 {
 titleStringArray.add(0, new ArrayList<String>(Arrays.asList("MEMBRANA", "House", "Pool", "null")));
 titleStringArray.add(1, new ArrayList<String>(Arrays.asList("MEMBRANA", "Flat", "Hallway")));
 titleStringArray.add(2, new ArrayList<String>(Arrays.asList("MEMBRANA", "Office", "Set RGB Lamp Color", "null")));
 titleStringArray.add(3, new ArrayList<String>(Arrays.asList("MEMBRANA", "House", "Yard", "null")));
 titleStringArray.add(4, new ArrayList<String>(Arrays.asList("MEMBRANA", "Flat", "Greenhouse")));
 titleStringArray.add(5, new ArrayList<String>(Arrays.asList("MEMBRANA", "Office", "Enter Astral")));
 }
*/

List<ArrayList<Integer>> titleIconArray = new ArrayList<ArrayList<Integer>>();
List<ArrayList<Integer>> deviceIconArray = new ArrayList<ArrayList<Integer>>();
List<ArrayList<Integer>> listviewIconArray = new ArrayList<ArrayList<Integer>>();

public TitleIcon()
{
setPrimeArray();
//setSettingsDrawableArrayListArray();
}

public void Up()
{
iconInt++;
}

public void Down()
{
iconInt--;
}

public int getI()
{
return integer;
}

public int getIconsList(int x)
{
int i = titleIconArray.get(x).get(iconInt);
//int i = R.drawable.ic_launcher;
integer = x;
return i;
}

public ArrayList<Integer> getDeviceArraylist(int i)
{
return deviceIconArray.get(i);
}

public ArrayList<Integer> getListviewArraylist(int i)
{
return listviewIconArray.get(i);
}

//public ArrayList<Integer> getSettingsDrawableArrayList(int i)
public ArrayList<Integer> getSettingsDrawableArrayList()
{
//return settingsDrawableArrayList.get(i);
return settingsDrawableArrayList;
}

public ArrayList<Integer> getTimePeriodsDrawableArrayList()
{ 
return timePeriodsDrawableArrayList;
}

/*public void setSettingsDrawableArrayList(int i, Integer drawable)
{
if (settingsDrawableArrayList.size()-1 < i)
{
settingsDrawableArrayList.get(i).add(0, drawable);
settingsDrawableArrayList.get(i).add(1, drawable);
}
else
{
//settingsDrawableArrayList.clear();
settingsDrawableArrayList.get(i).set(0, drawable);
settingsDrawableArrayList.get(i).set(1, drawable);
}
}*/

public void setSettingsDrawableArrayList(Integer drawable)
{
settingsDrawableArrayList.set(0, drawable);
settingsDrawableArrayList.set(1, drawable);
//
timePeriodsDrawableArrayList.set(0, drawable);
timePeriodsDrawableArrayList.set(1, drawable);
timePeriodsDrawableArrayList.set(2, drawable);
}

private void setPrimeArray()
{
titleIconArray.add(0, new ArrayList<Integer>(Arrays.asList(R.drawable.test, R.drawable.house2, R.drawable.pool_summer, R.drawable.black, R.drawable.black)));
titleIconArray.add(1, new ArrayList<Integer>(Arrays.asList(R.drawable.test, R.drawable.flat4, R.drawable.black, R.drawable.black, R.drawable.black)));
titleIconArray.add(2, new ArrayList<Integer>(Arrays.asList(R.drawable.test, R.drawable.office1, R.drawable.black, R.drawable.black, R.drawable.black)));

deviceIconArray.add(0, new ArrayList<Integer>(Arrays.asList(R.drawable.smart_gate_new, R.drawable.smart_lamp_new)));
deviceIconArray.add(1, new ArrayList<Integer>(Arrays.asList(R.drawable.smart_lamp_new)));
deviceIconArray.add(2, new ArrayList<Integer>(Arrays.asList(R.drawable.icons13, R.drawable.smart_lamp_new, R.drawable.smart_plug,R.drawable.smart_robot)));
deviceIconArray.add(3, new ArrayList<Integer>(Arrays.asList(R.drawable.icons13, R.drawable.smart_lamp_new)));
deviceIconArray.add(4, new ArrayList<Integer>(Arrays.asList(R.drawable.icons13, R.drawable.smart_lamp_new, R.drawable.smart_humidity_new)));//greenhouse

listviewIconArray.add(0, new ArrayList<Integer>(Arrays.asList(R.drawable.house2, R.drawable.flat4, R.drawable.office1)));
listviewIconArray.add(1, new ArrayList<Integer>(Arrays.asList(R.drawable.pool_summer, R.drawable.yard_summer)));
listviewIconArray.add(2, new ArrayList<Integer>(Arrays.asList(R.drawable.hallway0, R.drawable.gan)));
}

//temporary
public int getIconInt()
{return iconInt;}
//tempirary

}
