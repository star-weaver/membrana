package com.mycompany.myapp81;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.*;
import com.google.gson.*;

public class TreeNode<T> implements Iterable<TreeNode<T>> 
{
public T ierarchicalName;
public TreeNode<T> parent;
public List<TreeNode<T>> children;
private List<TreeNode<T>> elementsIndex;

//MAY BE SET FRAGMENTDEVICES DATA DIRECTLY WITHOUT LOCAL VARIABLES

FragmentDevices nodeFragmentDevices;
private ImplementInRobot implementInRobotListener;

private String environmentName;
private static String environmentDevicesArrayListJsonString;
private String[] mqttString = new String[5];
private int nodeType; //0-primelistview(7root_dark;8root_light);1-devices with mult;2-devices w/o mult;3-timer;
private int environmentPicture;
private int buttonStateInt;
private int multisensorArrayPosition;
private int devicesCounter = 0;
private boolean multisensorAvaiable;

private ArrayList<String> childrenNamesArrayList = new ArrayList<String>();
private ArrayList<String> environmentDevicesNamesArrayList = new ArrayList<String>();//(Arrays.asList("3f", "5f"));
private ArrayList<Integer> environmentDevicesPicturesArrayList = new ArrayList<Integer>();//(Arrays.asList(R.drawable.house2, R.drawable.flat4));
private ArrayList<Integer> environmentDevicesPatternsArrayList = new ArrayList<Integer>();//(Arrays.asList(8, 8));
//--?--//
private String environmentDevicesNamesArray[] = {"TEST_A","TEST_B","TEST_C"};
private int environmentDevicesPicturesArray[] = {R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher};
private int environmentDevicesPatternsArray[] = {7,8,7};

public TreeNode(T ierarchicalName, String environmentName, int environmentPicture, ImplementInRobot implementInRobotListener, int buttonStateInt, int multisensorArrayPosition, int nodeType) 
{
this.ierarchicalName = ierarchicalName;
this.children = new LinkedList<TreeNode<T>>();
this.elementsIndex = new LinkedList<TreeNode<T>>();
this.elementsIndex.add(this);
this.environmentName = environmentName;
this.environmentPicture = environmentPicture;
this.nodeType = nodeType;
this.implementInRobotListener = implementInRobotListener;
this.buttonStateInt = buttonStateInt;
this.multisensorArrayPosition = multisensorArrayPosition;
multisensorAvaiable = (nodeType == 1) ? true : false;
nodeFragmentDevices = new FragmentDevices(environmentDevicesNamesArrayList, environmentDevicesPicturesArrayList, environmentDevicesPatternsArrayList, implementInRobotListener, environmentName, multisensorAvaiable, buttonStateInt, multisensorArrayPosition);
}

public boolean isRoot() 
{
return parent == null;
}

public boolean isLeaf() 
{
return children.size() == 0;
}

public String getIerarchicalName()
{
return  ierarchicalName.toString();
}

public int getButtonStateInt()
{
return buttonStateInt;
}

public int getMultiSensorArrayPosition()
{
return multisensorArrayPosition;
}

public int getNodeType()
{
return nodeType;
}

public void setNodeType(int nodeType)
{
this.nodeType = nodeType;
}

/*public String getEnvironmentDevicesArrayListJsonString(int dataType)
{
Gson gson = new Gson();
switch(dataType)
{
case 0:
if (environmentDevicesNamesArrayList !=null) environmentDevicesArrayListJsonString = gson.toJson(environmentDevicesNamesArrayList);
else return "n/a";
break;
case 1:
if (environmentDevicesPicturesArrayList !=null) environmentDevicesArrayListJsonString = gson.toJson(environmentDevicesPicturesArrayList);
else return "n/a";
break;
case 2:
if (environmentDevicesPatternsArrayList !=null) environmentDevicesArrayListJsonString = gson.toJson(environmentDevicesPatternsArrayList);
else return "n/a";
break;
}
return environmentDevicesArrayListJsonString;
}*/
//--?--//
/*public String[] getEnvironmentDevicesNamesArray()
{
return environmentDevicesNamesArray;
}*/
//--?--//

public String getEnvironmentDevicesArrayListString (int dataType)
{
//Gson gson = new Gson();
switch(dataType)
{
case 0:
//if (environmentDevicesNamesArrayList !=null) environmentDevicesArrayListJsonString = gson.toJson(environmentDevicesNamesArrayList);
if (environmentDevicesNamesArrayList !=null) environmentDevicesArrayListJsonString = android.text.TextUtils.join(",",environmentDevicesNamesArrayList);
else return "n/a";
break;
case 1:
//if (environmentDevicesPicturesArrayList !=null) environmentDevicesArrayListJsonString = gson.toJson(environmentDevicesPicturesArrayList);
if (environmentDevicesPicturesArrayList !=null) environmentDevicesArrayListJsonString = android.text.TextUtils.join(",",environmentDevicesPicturesArrayList);
else return "n/a";
break;
case 2:
//if (environmentDevicesPatternsArrayList !=null) environmentDevicesArrayListJsonString = gson.toJson(environmentDevicesPatternsArrayList);
if (environmentDevicesPatternsArrayList !=null) environmentDevicesArrayListJsonString = android.text.TextUtils.join(",",environmentDevicesPatternsArrayList);
else return "n/a";
break;
case 3:
childrenNamesArrayList.clear();
for (int i=0;i<children.size();i++)
{
childrenNamesArrayList.add(children.get(i).getEnvironmentName());
}
environmentDevicesArrayListJsonString = android.text.TextUtils.join(",",childrenNamesArrayList);
break;
}
return environmentDevicesArrayListJsonString;
}

public TreeNode<T> addChild(T childNodeIerarchicalName, String childNodeEnvironmentName, int childNodeEnvironmentPicture, ImplementInRobot implementInRobotListener, int buttonStateInt, int multisensorArrayPosition, int nodeType) 
{
TreeNode<T> childNode = new TreeNode<T>(childNodeIerarchicalName, childNodeEnvironmentName, childNodeEnvironmentPicture, implementInRobotListener, buttonStateInt, multisensorArrayPosition, nodeType);
childNode.parent = this;
this.children.add(childNode);
if (this.nodeType==0 || this.nodeType==7 || this.nodeType==8) //type - primelistview (theme color variations)
{
//for (TreeNode<T> childNodeItem : this.children) {}
this.environmentDevicesNamesArrayList.add(childNode.getEnvironmentName());
this.environmentDevicesPicturesArrayList.add(childNode.getEnvironmentPicture());
this.environmentDevicesPatternsArrayList.add(8);
}
if (this.nodeType==3) //type - timer
{
this.environmentDevicesNamesArrayList.add(childNode.getEnvironmentName());
this.environmentDevicesPicturesArrayList.add(childNode.getEnvironmentPicture());
this.environmentDevicesPatternsArrayList.add(7);
this.environmentDevicesPatternsArrayList.add(7);
this.environmentDevicesPatternsArrayList.add(7);
}
this.registerChildForSearch(childNode);
return childNode;
}
//--?--//
/*
public TreeNode<T> addChild(T childNodeIerarchicalName, String childNodeEnvironmentName, int childNodeEnvironmentPicture, ImplementInRobot implementInRobotListener, int buttonStateInt, int multisensorArrayPosition, int nodeType) 
{
TreeNode<T> childNode = new TreeNode<T>(childNodeIerarchicalName, childNodeEnvironmentName, childNodeEnvironmentPicture, implementInRobotListener, buttonStateInt, multisensorArrayPosition, nodeType);
childNode.parent = this;
this.children.add(childNode);
if (this.nodeType==0 || this.nodeType==7 || this.nodeType==8) //type - primelistview (theme color variations)
{
//for (TreeNode<T> childNodeItem : this.children) {}
this.environmentDevicesNamesArray[devicesCounter] = childNode.getEnvironmentName();
this.environmentDevicesPicturesArray[devicesCounter] = childNode.getEnvironmentPicture();
this.environmentDevicesPatternsArray[devicesCounter] =8;
}
if (this.nodeType==3) //type - timer
{
this.environmentDevicesNamesArray[devicesCounter] = childNode.getEnvironmentName();
this.environmentDevicesPicturesArray[devicesCounter] = childNode.getEnvironmentPicture();
this.environmentDevicesPatternsArray[devicesCounter] = 7;
this.environmentDevicesPatternsArrayList.add(7);
this.environmentDevicesPatternsArrayList.add(7);
}
this.registerChildForSearch(childNode);
return childNode;
}*/

public void addChildNode(TreeNode childNode0) 
{
childNode0.parent = this;
this.children.add(childNode0);
if (this.nodeType==0 || this.nodeType==7 || this.nodeType==8) //type - primelistview (theme color variations)
{
this.environmentDevicesNamesArray[devicesCounter] = childNode0.getEnvironmentName();
this.environmentDevicesPicturesArray[devicesCounter] = childNode0.getEnvironmentPicture();
this.environmentDevicesPatternsArray[devicesCounter] =8;
}
if (this.nodeType==3) //type - timer
{
this.environmentDevicesNamesArray[devicesCounter] = childNode0.getEnvironmentName();
this.environmentDevicesPicturesArray[devicesCounter] = childNode0.getEnvironmentPicture();
this.environmentDevicesPatternsArray[devicesCounter] = 7;
this.environmentDevicesPatternsArrayList.add(7);
this.environmentDevicesPatternsArrayList.add(7);
}
this.registerChildForSearch(childNode0);
}

public TreeNode<T> getChild(int i)
{
return this.children.get(i);
}

public TreeNode<T> getParent()
{
return this.parent;
}

public int getLevel() 
{
if (this.isRoot()) return 0;
else return parent.getLevel() + 1;
}

public void setEnvironmentName(String environmentName)
{
this.environmentName = environmentName;
}

public void setEnvironmentPicture(int environmentPicture)
{
this.environmentPicture = environmentPicture;
}

public String getEnvironmentName()
{
return environmentName;
}

public int getEnvironmentPicture()
{
return environmentPicture;
}

private void registerChildForSearch(TreeNode<T> node) 
{
elementsIndex.add(node);
if (parent != null) parent.registerChildForSearch(node);
}

public TreeNode<T> findTreeNode(Comparable<T> comparable) 
{
for (TreeNode<T> element : this.elementsIndex) 
{
T elementIerarchicalName = element.ierarchicalName;
if (comparable.compareTo(elementIerarchicalName) == 0) return element;
}
return null;
}

@Override
public Iterator<TreeNode<T>> iterator() 
{
TreeNodeIter<T> iter = new TreeNodeIter<T>(this);
return iter;
}

public void addDevice(int devicePattern, String deviceName, int devicePicture)
{
environmentDevicesPatternsArrayList.add(devicePattern);
environmentDevicesNamesArrayList.add(deviceName);
environmentDevicesPicturesArrayList.add(devicePicture);
}
//--?--//
/*public void addDevice(int devicePattern, String deviceName, int devicePicture)
{
environmentDevicesPatternsArray[devicesCounter] = devicePattern;
environmentDevicesNamesArray[devicesCounter] = deviceName;
environmentDevicesPicturesArray[devicesCounter] = devicePicture;
devicesCounter++;
}*/

public FragmentDevices getNodeFragmentDevices()
{
return nodeFragmentDevices;
}

public void setMqttString(String[] mqttString)
{
this.mqttString = mqttString;
}

public String[] getMqttString()
{
return mqttString;
}

public ArrayList<String> getChildrenNamesArrayList()
{
return childrenNamesArrayList;
}

public void setChildrenNamesArrayList(ArrayList<String> arrayList)
{
this.childrenNamesArrayList=arrayList;
}

}
