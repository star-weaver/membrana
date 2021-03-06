package com.starweaver.membrana;

import android.*;
import android.app.*;
import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.os.*;
import android.support.design.internal.*;
import android.support.design.widget.*;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.widget.RelativeLayout.*;
import java.util.*;
import org.eclipse.paho.android.service.*;

import android.view.View.OnClickListener;

//if temp <10 =>mqttstring dont work
//BLOCHS:
//https://stackoverflow.com/questions/51584562/calculating-the-distance-and-yaw-between-aruco-marker-and-camera
//Set style programmatically:
//https://stackoverflow.com/questions/11723881/android-set-view-style-programmatically
//Style - styles.xml attrs.xml:
//https://stackoverflow.com/questions/3241729/android-dynamically-change-style-at-runtime
//https://www.hidroh.com/2015/02/25/support-multiple-themes-android-app-part-2/
//http://mrbool.com/how-to-change-the-layout-theme-of-an-android-application/25837
//https://www.chrisblunt.com/android-toggling-your-apps-theme/
//https://mobile.developer.com/ws/android/changing-your-android-apps-theme-dynamically.html
//https://www.javarticles.com/2015/04/android-set-theme-dynamically.html
//remove titlestring titleicon up down in all classes

public class MainActivity extends Activity implements ImplementInRobot
{
ActionBar actionBar;
NotificationManager notifycationManager;
Notification notification;
LayoutInflater mInflater;
View mCustomView;
BottomNavigationView bottomNavigationView;
TextView mTitleTextView;
//TextView debugTextView; //DEBUG
//Button debugButton0; //DEBUG
//Button debugButton1; ///DEBUG
//Button debugButton2; //DEBUG
LayoutParams lp;

FragmentMain fragmentMain;
FragmentPrimeListview fragmentPrimeListview;
FragmentLineChart fragmentLineChart;
FragmentPieChart fragmentPieChart;
FragmentTransaction fTrans;
FragmentLoading fragmentLoading;

Resources.Theme resourceTheme;//is need???

String[] temporString = new String[5];
ThreadMist threadMist;
TitleString titleString;
TitleIcon titleIcon;
DataBaseHandler dataBaseHandler;

ArrayList<Integer> startingSettingsDrawableArrayList;
ArrayList<Integer> startingSettingsPatternArrayList;
ArrayList<Float> lineChartArrayList = new ArrayList<Float>();
private List<TreeNode> treeNodes = new ArrayList<TreeNode>();
private List<TreeNode> temporaryTestTreeNodes = new ArrayList<TreeNode>();

FragmentDevices fragmentDevicesListView;
FragmentDevices fragmentDevicesListView0;
FragmentDevices fragmentDevicesListView1;
FragmentDevices fragmentDevicesListView2;
FragmentDevices fragmentDevicesListView3;
FragmentDevices fragmentDevicesListView4;
FragmentDevices fragmentDevicesListView5;
ArrayList<Fragment> fragmentList; 

private static TreeNode<String> rootNode;
private static TreeNode<String> node1_0;
private static TreeNode<String> node1_1;
private static TreeNode<String> node1_2;
private static TreeNode<String> node2_0;
private static TreeNode<String> node2_1;
private static TreeNode<String> node2_2;
private static TreeNode<String> node2_3;
private static TreeNode<String> formDataBaseTemporaryTreeNode0;
private static TreeNode<String> formDataBaseTemporaryTreeNode1;
public static TreeNode<String> currentLevelTreeNode;

private static boolean multisensorAvaiable = false;
private static int multisensorArrayPosition = 99;
private static String multisensorTopic;

static String testString2;
static String mqttRequestAnswerData;

Messenger mService = null;
boolean mIsBound;
boolean useAlternativeTheme = false;
boolean isDataRequest = false;
boolean mqttRequestAnswerDataWasReceived = false;
final Messenger mMessenger = new Messenger(new IncomingHandler());

Menu globalMenu;
static int bottomNavigationViewCoefficient = 0;//WORK ON IT NOW..(if not work on it now, think about deleting it..)
static int bottomNavigationViewCoefficientOld = bottomNavigationViewCoefficient;
static int bottomNavigationViewCoefficientOnBack = bottomNavigationViewCoefficientOld;
static int dataRequestCounter;

static final Handler chartResposeHandler = new Handler();

class IncomingHandler extends Handler 
{//0
@Override
public void handleMessage( Message msg ) 
{//1
switch (msg.what) 
{//2
case MqttConnectionManagerService.MSG_SET_INT_VALUE:
break;
case MqttConnectionManagerService.MSG_SET_STRING_VALUE:
String str1 = msg.getData().getString("str1");
String str2 = msg.getData().getString("str2");
if (str1.equals("chart"))
{//3
char[] separated = str2.toCharArray();
lineChartArrayList.clear();
for (int i=0; i < separated.length + 1; i++)
{//4
lineChartArrayList.add(i, Float.valueOf(separated[i]));
}//4
titleString.setLineChartArrayList(lineChartArrayList);
}//3 end of if (str1 == "chart")
else if (str1.equals("mqtt_request_answer_data"))
{//5
mqttRequestAnswerDataWasReceived = true;
//titleString.setChartDataWasRecieved(true);
}//5
else
{//6
char[] separated = str2.toCharArray();
temporString[0] = (separated[0] + "") + (separated[1] + "");
temporString[1] = (separated[2] + "") + (separated[3] + "");
temporString[2] = (separated[4] + "") + (separated[5] + "");
temporString[3] = (separated[6] + "") + (separated[7] + "") + (separated[8] + "");
temporString[4] = "000";//(separated[9] + "") + (separated[10] + "") + (separated[11] + "");
//toastMessage(str1+"< "+str2+" >"+currentLevelTreeNode.getEnvironmentName());
//toastMessage(str1+"<>"+str2);

if (str1.equals("multisensor" + "/" + currentLevelTreeNode.getEnvironmentName()))
{//7
currentLevelTreeNode.setMqttString(temporString);
refreshFragmentDevices();
}//7
if (str1.equals("multisensor") && currentLevelTreeNode.getEnvironmentName().equals("Office"))
{//8
currentLevelTreeNode.setMqttString(temporString);
//toastMessage("catch!");
//toastMessage(str2);
refreshFragmentDevices();
}//8
/*switch (str1)
 {//9
 //case "Office/Multisensor":
 case "multisensor":
 //NEED FIND CONCRETE TREENODE TO SAVE CONCRETE MQTTSTRING
 currentLevelTreeNode.setMqttString(temporString);
 if (titleString.getMultisensorStateArray(0) == true) current=environmentname currentLevelTreeNode.getEnvironmentName()
 {//10
 refreshFragmentDevices();
 }//10
 break;
 case "Hallway/Multisensor":
 setMqttString(1);
 if (titleString.getMultisensorStateArray(1) == true)
 {//11
 refreshFragmentDevices();
 }//11
 break;
 case "Greenhouse/Multisensor":
 setMqttString(0);
 if (titleString.getMultisensorStateArray(2) == true)
 {//12
 refreshFragmentDevices();
 }//12
 break;
 }*///9
}//end of else -- if (str1 == "chart")
//}//end - if multisensor avaiable
break;
//case Implement.MSG_SET_STRING_VALUE:
////String str3 = msg.getData().getString("str1");
//sendMessageStringToService("Test", "Implement", "str3");
//break;
default:
super.handleMessage(msg);
break;
}
}
}

private ServiceConnection mConnection = new ServiceConnection() 
{
public void onServiceConnected( ComponentName className, IBinder service ) 
{
mService = new Messenger(service);
try 
{
Message msg = Message.obtain(null, MqttConnectionManagerService.MSG_REGISTER_CLIENT);
msg.replyTo = mMessenger;
mService.send(msg);
}
catch (RemoteException e) 
{
// In this case the service has crashed before we could even do anything with it
}
}
public void onServiceDisconnected( ComponentName className ) 
{
// This is called when the connection with the service has been unexpectedly disconnected - process crashed.
mService = null;
}
};

void changeToTheme( Activity activity )
{
activity.finish();
startActivity(getIntent());
}

@Override 
public void onCreate( Bundle savedInstanceState ) 
{
Log.i("MainActivity", "program start");
dataBaseHandler = new DataBaseHandler(this, this);
//dataBaseHandler.deleteAll();
formNodeTreeFromDatabase();//restores database nodes to "treeNodes"
//formNodeTree(true);//TEMPORARY!!!create and save no-database NODES to database
//formNodeTree(false);//TEMPORARY!!!create and add no-database NODES to "treeNodes" arraylist
/*if (dataBaseHandler.dataBaseExists())
 {
 toastMessage("DATABASE EXISTS-items quantity in database: "+dataBaseHandler.getDataBaseItemsQuantity());
 }
 else
 {
 Log.i("MainActivity", " dataBase_Absent");
 }*/
if (treeNodes.get(0).getNodeType() == 8) setTheme(R.style.AlternativeTheme);//https://stackoverflow.com/questions/11562051/change-activitys-theme-programmatically
super.onCreate(savedInstanceState);
setContentView(R.layout.main);
titleString = new TitleString();//IS NEED???
titleIcon = new TitleIcon();//IS NEED???

actionBar = getActionBar();//menu back button
actionBar.setDisplayHomeAsUpEnabled(true);//menu back button
actionBar.setHomeAsUpIndicator(R.drawable.mybuttonicon);
actionBar.setDisplayShowHomeEnabled(false);
actionBar.setDisplayShowTitleEnabled(false);
actionBar.setDisplayShowCustomEnabled(true);
mInflater = LayoutInflater.from(this);
mCustomView = mInflater.inflate(R.layout.custom_actionbar, null);
mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_text);
mTitleTextView.setText(currentLevelTreeNode.getEnvironmentName());
lp = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
lp.setMargins(0, 0, 50, 0);
mTitleTextView.setLayoutParams(lp);
//mTitleTextView.setGravity(Gravity.CENTER);
actionBar.setCustomView(mCustomView);

/*ImageView homeIconImageView = (ImageView) findViewById(android.R.id.home);
 FrameLayout.LayoutParams homeIconImageViewLayoutParams = (FrameLayout.LayoutParams) homeIconImageView.getLayoutParams();
 homeIconImageViewLayoutParams.topMargin = homeIconImageViewLayoutParams.bottomMargin = 100;
 homeIconImageViewLayoutParams.leftMargin = homeIconImageViewLayoutParams.rightMargin = 500;
 homeIconImageView.setLayoutParams(homeIconImageViewLayoutParams);*/

//debug str
//debugTextView = (TextView) findViewById(R.id.tv);
//debugButton0 = (Button) findViewById(R.id.debug_button_0);
//debugButton1 = (Button) findViewById(R.id.debug_button_1);
//debugButton2 = (Button) findViewById(R.id.debug_button_2);
//debugTextView.setText("test..");
//debug end

bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
final Menu menu = bottomNavigationView.getMenu();
setGlobalMenu(menu);
globalMenu.add(Menu.NONE, 1, Menu.NONE, null).setIcon(R.drawable.energy_icon_bottom_menu_00);
globalMenu.add(Menu.NONE, 2, Menu.NONE, null).setIcon(R.drawable.app_icon_bottom_menu);
globalMenu.add(Menu.NONE, 3, Menu.NONE, null).setIcon(R.drawable.notify_icon_bottom_menu_00);
//is this work??:
globalMenu.getItem(0).setChecked(false);
globalMenu.getItem(1).setChecked(false);
globalMenu.getItem(2).setChecked(false);
//set bottomnavigationview icons size
for (int i = 0; i < menuView.getChildCount(); i++) 
{
final View iconView = menuView.getChildAt(i).findViewById(android.support.design.R.id.icon);
final ViewGroup.LayoutParams layoutParams = iconView.getLayoutParams();
final DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
layoutParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 26, displayMetrics);
layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 26, displayMetrics);
iconView.setLayoutParams(layoutParams);
}
//set bottomnavigationview clicklistener
bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() 
{
@Override
public boolean onNavigationItemSelected( MenuItem item )
{
switch (item.getItemId())
{
case 1:
titleString.Up();
mTitleTextView.setText("Chart");
titleIcon.Up();
fragmentMain.setIcon(R.drawable.black, R.drawable.sand);
fragmentPieChart = new FragmentPieChart();
fTrans = getFragmentManager().beginTransaction();
fTrans.replace(R.id.frgmCont2, fragmentPieChart);
fTrans.addToBackStack(null);
fTrans.commit();
break;
case 2:
if (!titleString.getMultisensorBoolean())
{
toastMessage("membrana");
}                                                                                                                                                                                       
else
{
dataRequestCounter = 0;
//->>>-here we will switch to loader fragment
//titleString.Up();//IS NEED IN OUR SITUATION????
//mTitleTextView.setText("Loading chart data..");
//titleIcon.Up();//IS NEED IN OUR SITUATION????
fragmentMain.setIcon(R.drawable.black, R.drawable.sand);
fragmentLoading = new FragmentLoading();
fTrans = getFragmentManager().beginTransaction();
//cause error onBack: fTrans.replace(R.id.frgmCont2, fragmentLoading);
fTrans.add(R.id.frgmCont2, fragmentLoading);
fTrans.addToBackStack(null);
fTrans.commit();
//sendMessageStringToService("greenhouse", "", "1");
new DataRequestTask().execute();
}//-1
break;
case 3:
toastMessage("3");
break;
}//-3
//---
if (titleString.getChartTransactionIsApproved())
{
titleString.setChartTransactionIsApproved(false);
titleString.Up();//IS NEED IN OUR SITUATION????
mTitleTextView.setText("Chart");
titleIcon.Up();//IS NEED IN OUR SITUATION????
fragmentMain.setIcon(R.drawable.black, R.drawable.sand);
fragmentLineChart = new FragmentLineChart(titleString.getLineChartArrayList());
fTrans = getFragmentManager().beginTransaction();
fTrans.replace(R.id.frgmCont2, fragmentLineChart);
fTrans.addToBackStack(null);
fTrans.commit();
}
else
{
}
return false;
}
});

threadMist = new ThreadMist();
threadMist.start();

createFragment();//need to comment later

Intent intent = getIntent();
//restoreMe(savedInstanceState);//<-NO NEED???
startService(new Intent(MainActivity.this, MqttConnectionManagerService.class));
CheckIfServiceIsRunning();
doBindService();

//DEBUG BUTTON START!!
OnClickListener listener = new OnClickListener() 
{
@Override
public void onClick( View v )
{
/*if (v == debugButton0)
 {
 //[   ENVIRONMENT NAME  & UPDATE  ]
 ////debugTextView.setText(""+dataBaseHandler.formTreeNodeFromDataBaseRestoredData("1").getNodeType());
 //debugTextView.setText(""
 //+dataBaseHandler.formTreeNodeFromDataBaseRestoredData("2").getEnvironmentDevicesArrayListString(0)
 //+dataBaseHandler.formTreeNodeFromDataBaseRestoredData("2").getEnvironmentDevicesArrayListString(3) //8355835

 //+" - "+temporaryTestTreeNodes.get(0).getChildrenNamesArrayList().size()
 //+"//-//"+dataBaseHandler.formTreeNodeFromDataBaseRestoredDataString("1")
 //+"//-//"+dataBaseHandler.formTreeNodeFromDataBaseRestoredDataString("2")
 //+"//-//"+dataBaseHandler.formTreeNodeFromDataBaseRestoredDataString("3")
 //+"//-//"+dataBaseHandler.formTreeNodeFromDataBaseRestoredDataString("4")
 //+"//-//"+dataBaseHandler.formTreeNodeFromDataBaseRestoredDataString("5")
 //+"//-//"+dataBaseHandler.formTreeNodeFromDataBaseRestoredDataString("6")
 //+"//-//"+dataBaseHandler.formTreeNodeFromDataBaseRestoredDataString("7")
 //+"//TEMPORARYTEST//"+treeNodes.get(0).getEnvironmentDevicesArrayListString(0)
 //+"+"+treeNodes.get(1).getEnvironmentDevicesArrayListString(0)
 //+"+"+treeNodes.get(2).getEnvironmentDevicesArrayListString(0)
 //+"+"+treeNodes.get(3).getEnvironmentDevicesArrayListString(0)
 //+"+"+treeNodes.get(4).getEnvironmentDevicesArrayListString(0)
 //+"+"+treeNodes.get(5).getEnvironmentDevicesArrayListString(0)
 //+"+"+treeNodes.get(6).getEnvironmentDevicesArrayListString(0)
 //+"+"+treeNodes.get(7).getEnvironmentDevicesArrayListString(0)
 //);
 }*/
/*else if (v == debugButton1)
 {
 if (treeNodes.get(0).getNodeType()==8)
 {
 treeNodes.get(0).setNodeType(7);
 toastMessage("switched to day-theme");
 }
 else
 {
 treeNodes.get(0).setNodeType(8);
 toastMessage("switched to night-theme");
 }
 dataBaseHandler.updateTreeNodeAttributesInDataBase("1",treeNodes.get(0));
 changeToTheme(MainActivity.this);
 }*/
/*else if (v == debugButton2)
 {
 dataBaseHandler.deleteAll();
 formNodeTree(true);
 }*/
}
};
//debugButton0.setOnClickListener(listener);
//debugButton1.setOnClickListener(listener);
//debugButton2.setOnClickListener(listener);
//DEBUG BUTTON END!!

if (savedInstanceState == null) 
{
fragmentMain = new FragmentMain();
getFragmentManager().beginTransaction().add(R.id.frgmCont, fragmentMain).commit();
//fragmentPrimeListview = new FragmentPrimeListview(0, 0);
//getFragmentManager().beginTransaction().add(R.id.frgmCont2, fragmentPrimeListview).commit();
getFragmentManager().beginTransaction().add(R.id.frgmCont2, currentLevelTreeNode.getNodeFragmentDevices()).commit();
}
}

@Override
protected void onSaveInstanceState( Bundle outState ) 
{
super.onSaveInstanceState(outState);
}

//IS NEED???
/*private void restoreMe(Bundle state) 
 {
 if (state != null) 
 {
 //textStatus.setText(state.getString("textStatus"));
 }
 }*/

private void CheckIfServiceIsRunning( ) 
{
//If the service is running when the activity starts, we want to automatically bind to it.
if (MqttConnectionManagerService.isRunning()) 
{
doBindService();
}
}

public void sendMessageStringToService( String messageString0, String messageString1, String messageString2 )
{
if (mIsBound) 
{
if (mService != null) 
{
try 
{
Bundle b = new Bundle();
b.putString("str1", messageString0);
b.putString("str2", messageString1);
b.putString("str3", messageString2);
Message msg = Message.obtain(null, MqttConnectionManagerService.MSG_SET_STRING_VALUE);
msg.setData(b);
mService.send(msg);
}
catch (RemoteException e) 
{
// 
}
}
}
}

public void  sendMessageStringPublishLightToService( int intvaluetosend )
{
if (mIsBound) 
{
if (mService != null) 
{
try 
{
Message msg = Message.obtain(null, MqttConnectionManagerService.MSG_SET_INT_VALUE, intvaluetosend, 0);
msg.replyTo = mMessenger;
mService.send(msg);
}
catch (RemoteException e) 
{
//
}
}
} 
}

void doBindService( ) 
{
bindService(new Intent(this, MqttConnectionManagerService.class), mConnection, Context.BIND_AUTO_CREATE);
mIsBound = true;
}

void doUnbindService( ) 
{
if (mIsBound) 
{
// If we have received the service, and hence registered with it, then now is the time to unregister.
if (mService != null) 
{
try 
{
Message msg = Message.obtain(null, MqttConnectionManagerService.MSG_UNREGISTER_CLIENT);
msg.replyTo = mMessenger;
mService.send(msg);
}
catch (RemoteException e) 
{
// There is nothing special we need to do if the service has crashed.
}
}
// Detach our existing connection.
unbindService(mConnection);
mIsBound = false;
}
}

@Override
protected void onDestroy( ) 
{
super.onDestroy();
threadMist.interrupt();
try 
{
doUnbindService();
}
catch (Throwable t) 
{
//Log.e("MainActivity", "Failed to unbind from the service", t);
}
}

@Override
protected void onResume( )
{
//https://stackoverflow.com/questions/40926546/implement-eclipse-mqtt-android-client-using-single-connection-instance/41060618#41060618
startService(new Intent(this, MqttService.class));//????…
super.onResume();
}

@Override//create menu
public boolean onCreateOptionsMenu( Menu menu ) 
{
getMenuInflater().inflate(R.menu.mymenu, menu);
if (treeNodes.get(0).getNodeType() == 8)
{
menu.getItem(0).setIcon(R.drawable.smart_day_night_n0);
}
else
{
menu.getItem(0).setIcon(R.drawable.smart_day_night_d0);
}
return super.onCreateOptionsMenu(menu);
}
// handle button activities
@Override//menu & back button
public boolean onOptionsItemSelected( MenuItem item ) 
{
int id = item.getItemId();
if (id == R.id.item) 
{
// do something here
//Toast.makeText(this, "Here will be Settings", Toast.LENGTH_LONG).show();
if (treeNodes.get(0).getNodeType() == 8)
{
treeNodes.get(0).setNodeType(7);
}
else
{
treeNodes.get(0).setNodeType(8);
}
dataBaseHandler.updateTreeNodeAttributesInDataBase("1", treeNodes.get(0));
changeToTheme(MainActivity.this);
}
/*
 else if (id == R.id.item0)  //for menu "vipadalka"
 {
 // do something here
 Toast.makeText(this, "000", Toast.LENGTH_LONG).show();
 }*/
else if (id == android.R.id.home)
{
//finish();//this command ends program
onBackPressed();
}
return super.onOptionsItemSelected(item);
}

@Override
public void onBackPressed( ) 
{
super.onBackPressed();
//titleString.Down();//NO NEED???
//titleIcon.Down();//NO NEED??
if (isDataRequest) 
{
isDataRequest = false;
}
else
{
switchToParentNode();
}
if (titleString.fragmentSettings() && titleString.fragmentTimerPeriods())
{
mTitleTextView.setText(titleString.getTimerTitle());
titleString.setFragmentTimerPeriodsBoolean(false);
}
else if (titleString.fragmentSettings() && !titleString.fragmentTimerPeriods())
{
mTitleTextView.setText(titleString.getSettingsTitle());
titleString.setFragmentSettinsBoolean(false);
}
else
{
//
}
multisensorArrayPosition = 99;//IS NEED
}

// begin of ThreadMist
public class ThreadMist extends Thread 
{
@Override
public void run( ) 
{
while (true) 
{
runOnUiThread(new Runnable() 
{
@Override
public void run( ) 
{
fragmentMain.doAnimation1();
}
});// end of runOnUiThread
try 
{
Thread.sleep(6000);
} 
catch (InterruptedException e) 
{
return;
}
}// end while(true)
}// end run()
}// end of ThreadMist

//setStatusBar for Meizu
//https://stackoverflow.com/questions/40481700/statusbar-color-doesnt-set-properly-on-meizu-pro-6
@Override
public void onWindowFocusChanged( boolean hasFocus ) 
{
super.onWindowFocusChanged(hasFocus);
if (hasFocus) 
{
Window window = this.getWindow();
//window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
window.setStatusBarColor(Color.GRAY);
}
}

private void formNodeTree( boolean isNeedSaveToDatabase )
{
rootNode = new TreeNode<String>("0", "MEMBRANA", R.drawable.test, this, 2, 0, 7);
node1_0 = rootNode.addChild("1_0", "House", R.drawable.house2, this, 2, 0, 0);			
node1_1 = rootNode.addChild("1_1", "Flat", R.drawable.flat4, this, 2, 0, 0);			
node1_2 = rootNode.addChild("1_2", "Office", R.drawable.office1, this, 2, 0, 1, "");
node1_2.addDevice(1, "Multisensor", R.drawable.icons13);
node1_2.addDevice(0, "Lights", R.drawable.smart_lamp_new);
node1_2.addDevice(0, "Plug", R.drawable.smart_plug);
node1_2.addDevice(2, "Avatar", R.drawable.smart_robot);
node2_0 = node1_0.addChild("2_0", "Yard", R.drawable.yard_winter, this, 2, 0, 1);
node2_0.addDevice(1, "Multisensor", R.drawable.icons13);
node2_0.addDevice(0, "Lights", R.drawable.smart_lamp_new);
node2_1 = node1_0.addChild("2_1", "Pool", R.drawable.pool_winter, this, 2, 0, 1, "");
node2_1.addDevice(1, "Multisensor", R.drawable.icons13);
node2_1.addDevice(0, "Gate", R.drawable.smart_gate_new);
node2_2 = node1_1.addChild("2_2", "Hallway", R.drawable.hallway0, this, 2, 0, 1, "");//rtsp://wowzaec2demo.streamlock.net/vod/mp4:BigBuckBunny_115k.mov
node2_2.addDevice(1, "Multisensor", R.drawable.icons13);
node2_2.addDevice(0, "Lights", R.drawable.smart_lamp_new);
node2_3 = node1_1.addChild("2_3", "Greenhouse", R.drawable.gan, this, 2, 0, 1);
node2_3.addDevice(1, "Multisensor", R.drawable.icons13);
node2_3.addDevice(0, "Lights", R.drawable.smart_lamp_new);
node2_3.addDevice(4, "Drip", R.drawable.smart_humidity_new);
if (isNeedSaveToDatabase)
{
dataBaseHandler.saveTreeNodeAttributesToDataBase(rootNode);
dataBaseHandler.saveTreeNodeAttributesToDataBase(node1_0);
dataBaseHandler.saveTreeNodeAttributesToDataBase(node1_1);
dataBaseHandler.saveTreeNodeAttributesToDataBase(node1_2);
dataBaseHandler.saveTreeNodeAttributesToDataBase(node2_0);
dataBaseHandler.saveTreeNodeAttributesToDataBase(node2_1);
dataBaseHandler.saveTreeNodeAttributesToDataBase(node2_2);
dataBaseHandler.saveTreeNodeAttributesToDataBase(node2_3);
}
else
{
currentLevelTreeNode = rootNode;
treeNodes.add(rootNode);
treeNodes.add(node1_0);
treeNodes.add(node1_1);
treeNodes.add(node1_2);
treeNodes.add(node2_0);
treeNodes.add(node2_1);
treeNodes.add(node2_2);
treeNodes.add(node2_3);
}
}

private void formNodeTreeFromDatabase( )
{
for (int i=1;i <= dataBaseHandler.getDataBaseItemsQuantity();i++)
{
treeNodes.add(dataBaseHandler.formTreeNodeFromDataBaseRestoredData("" + i));
}

for (int i=0;i < treeNodes.size();i++)
{
formDataBaseTemporaryTreeNode0 = treeNodes.get(i);
//Log.i("MainActivity-testtree0", formDataBaseTemporaryTreeNode0.getEnvironmentName());
for (int j=0;j < formDataBaseTemporaryTreeNode0.getChildrenNamesArrayList().size();j++)
{
//Log.i("MainActivity-testtree>>", ">>"+formDataBaseTemporaryTreeNode0.getChildrenNamesArrayList().get(j));
for (int k=0;k < treeNodes.size();k++)
{
formDataBaseTemporaryTreeNode1 = treeNodes.get(k);
//Log.i("- -","- -");
//Log.i("treenode0 child env name:", formDataBaseTemporaryTreeNode0.getChildrenNamesArrayList().get(j));
//Log.i("treenode1 env name:", formDataBaseTemporaryTreeNode1.getEnvironmentName());
if (formDataBaseTemporaryTreeNode0.getChildrenNamesArrayList().get(j).equals(formDataBaseTemporaryTreeNode1.getEnvironmentName()))
{
//Log.i("- -","BINGO!!!");
//node1_0 = rootNode.addChild("1_0", "House", R.drawable.house2, this, 2, 0, 0);
formDataBaseTemporaryTreeNode0.addChildNode(treeNodes.get(k));
}
//Log.i("- -","- -");
}
}
}
currentLevelTreeNode = treeNodes.get(0);
}

public void createFragment( )
{
titleString.setSettingsButtonStateArray();
titleString.setTimerPeriodsStateArray();
titleString.setTimerDataArrayList();
fragmentList = new ArrayList<Fragment>();
fragmentDevicesListView = new FragmentDevices(titleString.getDeviceArraylist(2), titleIcon.getDeviceArraylist(2), titleString.getListViewPatternArraylist(2), this, "Office", true, 2, 0);
fragmentDevicesListView0 = new FragmentDevices(titleString.getDeviceArraylist(0), titleIcon.getDeviceArraylist(0), titleString.getListViewPatternArraylist(0), "Pool", 0);
fragmentDevicesListView1 = new FragmentDevices(titleString.getDeviceArraylist(1), titleIcon.getDeviceArraylist(1), titleString.getListViewPatternArraylist(1), "Yard", 1);
fragmentDevicesListView2 = new FragmentDevices(titleString.getDeviceArraylist(3), titleIcon.getDeviceArraylist(3), titleString.getListViewPatternArraylist(3), this, "Hallway", true, 3, 1);
fragmentDevicesListView3 = new FragmentDevices(titleString.getDeviceArraylist(4), titleIcon.getDeviceArraylist(4), titleString.getListViewPatternArraylist(4), this, "Greenhouse", true,  4, 2);
fragmentDevicesListView4 = new FragmentDevices(titleString.getDeviceArraylist(5),  titleIcon.getSettingsDrawableArrayList(), titleString.getSettingsPatternArrayList(), "Settings", 0);
fragmentDevicesListView5 = new FragmentDevices(titleString.getDeviceArraylist(6),  titleIcon.getTimePeriodsDrawableArrayList(), titleString.getTimePeriodsPatternArrayList(), "Timer", 0);
fragmentList.add(0, fragmentDevicesListView);
fragmentList.add(1, fragmentDevicesListView0);
fragmentList.add(2, fragmentDevicesListView1);
fragmentList.add(3, fragmentDevicesListView2);
fragmentList.add(4, fragmentDevicesListView3);
fragmentList.add(5, fragmentDevicesListView4);
fragmentList.add(6, fragmentDevicesListView5);
}

public void refreshFragmentDevices( )
{
fTrans = getFragmentManager().beginTransaction();
fTrans.detach(currentLevelTreeNode.getNodeFragmentDevices());
fTrans.attach(currentLevelTreeNode.getNodeFragmentDevices());
fTrans.commitAllowingStateLoss();
}

/*if (!fragmentOne.isAdded()){
 transaction = manager.beginTransaction();
 transaction.add(R.id.group,fragmentOne,"Fragment_One");
 transaction.commit();
 }*/

public void switchToChildNode( int numberOfFragment )
{
currentLevelTreeNode = currentLevelTreeNode.getChild(numberOfFragment);
mTitleTextView.setText(currentLevelTreeNode.getEnvironmentName());
fragmentMain.setIcon(currentLevelTreeNode.getEnvironmentPicture(), R.drawable.sand);
//titleString.Up();
titleString.setGlobalTitleFirst(numberOfFragment);// was 2//IS NEED????
//titleIcon.Up();
fTrans = getFragmentManager().beginTransaction();
if (currentLevelTreeNode.getNodeType() == 1 || currentLevelTreeNode.getNodeType() == 2)
{
/*maybe here we must firstly send
 request for a envir. devices states?*/
//FragmentMjpgStream fragmentMjpgStream = new FragmentMjpgStream();
//fTrans.add(R.id.frgmCont, fragmentMjpgStream);
FragmentVideo fragmentVideo = new FragmentVideo(0, currentLevelTreeNode.getVideoSourceUrl());
fTrans.add(R.id.frgmCont, fragmentVideo);
//fTrans.addToBackStack(null);
}
fTrans.replace(R.id.frgmCont2, currentLevelTreeNode.getNodeFragmentDevices());
//fTrans.replace(R.id.frgmCont2, fragmentMain);
fTrans.addToBackStack(null);
fTrans.commit();
}

public void switchToParentNode( )
{
currentLevelTreeNode = currentLevelTreeNode.getParent();
mTitleTextView.setText(currentLevelTreeNode.getEnvironmentName());
fragmentMain.setIcon(currentLevelTreeNode.getEnvironmentPicture(), R.drawable.sand);
}

public void setMultisensorAvaiable( boolean b )
{
this.multisensorAvaiable = b;
}

public boolean multisensorAvaiable( )
{
return multisensorAvaiable;
}

public void setMultisensorArrayPosition( int arrayPosition )
{
this.multisensorArrayPosition = arrayPosition;
}

public void seTMultisensorTopic( String s )
{
multisensorTopic = s;
}

public String getMultisensorTopic( )
{
return multisensorTopic;
}

public void startImplement( )
{
//Intent i = new Intent(MainActivity.this, Implement.class);
Intent i = new Intent(MainActivity.this, Remote.class);
startActivity(i);
}

private void setMqttString( int i )
{
titleString.setMqttStringPosition(i, 0, temporString[0]);
titleString.setMqttStringPosition(i, 1, temporString[1]);
titleString.setMqttStringPosition(i, 2, temporString[2]);
titleString.setMqttStringPosition(i, 3, temporString[3]);
titleString.setMqttStringPosition(i, 4, temporString[4]);
}

public void setBottomNavigationView( boolean deviceAdapterShown )
{
if (deviceAdapterShown) 
{
globalMenu.getItem(1).setIcon(R.drawable.chart_icon_bottom_menu);
}
else
{
globalMenu.getItem(1).setIcon(R.drawable.app_icon_bottom_menu);
}
}

public void setGlobalMenu( Menu menu )
{
globalMenu = menu;
}

//this temporary for testing bottomnavigationview clicklistener
public void toastMessage( String s )
{
Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
}


class DataRequestTask extends AsyncTask<Void, Void, Void> 
{
@Override
protected void onPreExecute( ) 
{
super.onPreExecute();
sendMessageStringToService("Hallway1", "", "2");
}

@Override
protected Void doInBackground( Void... params ) 
{
try 
{
for (int i = 0; i <= 5; i++) 
{
runOnUiThread(new Runnable() 
{
@Override
public void run( ) 
{
// code to call your service
if (mqttRequestAnswerDataWasReceived)
{
toastMessage("!Chart data was received!");
onBackPressed();
cancel(true);
}
else if (dataRequestCounter == 5)
{
toastMessage("No connection...");
onBackPressed();
}
else
{
dataRequestCounter++;
}
}
});
Thread.sleep(3000);
}
} 
catch (InterruptedException e) 
{
e.printStackTrace();
}
return null;
}

@Override
protected void onPostExecute( Void result ) 
{
super.onPostExecute(result);
}
}


/*class DataRequestTask extends AsyncTask<Void, Void, Void> 
 {
 @Override
 protected void onPreExecute( ) 
 {
 super.onPreExecute();
 }
 @Override
 protected String doInBackground( String... params ) 
 {
 try 
 {
 for (int i = 0; i <= 5; i++) 
 {
 runOnUiThread(new Runnable() 
 {
 @Override
 public void run( ) 
 {
 // code to call your service
 if (mqttRequestAnswerDataWasReceived)
 {
 mqttRequestAnswerData = "!mqtt data was received!";
 isDataRequest = true;
 onBackPressed();
 }
 else if (dataRequestCounter == 5)
 {
 mqttRequestAnswerData = "No connection...";
 isDataRequest = true;
 debugTextView.setText("trey");
 onBackPressed();
 }
 else
 {
 dataRequestCounter++;
 }
 }
 });
 Thread.sleep(3000);
 }
 } 
 catch (InterruptedException e) 
 {
 e.printStackTrace();
 }
 }
 @Override
 protected void onPostExecute( String result ) 
 {
 super.onPostExecute(result);
 }
 }*/

/*
 private class GetContacts extends AsyncTask<Void, Void, String>
 {
 @Override
 protected void onPreExecute()
 {
 super.onPreExecute();
 // Showing progress dialog
 pDialog = new ProgressDialog(MyActivity.this);
 pDialog.setMessage("Please wait...");
 pDialog.setCancelable(false);
 pDialog.show();
 }
 @Override
 protected Void doInBackground(String... arg0)
 {
 // Creating service handler class instance
 ServiceHandler sh = new ServiceHandler();
 // Making a request to url and getting response
 String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);
 Log.d("Response: ", "> " + jsonStr);
 if (jsonStr != null)
 {
 try
 {
 JSONObject jsonObj = new JSONObject(jsonStr);
 String auth2 = jsonObj.getString("auth");
 return auth2;
 }
 catch (JSONException e)
 {
 e.printStackTrace();
 }
 }
 else
 {
 Log.e("ServiceHandler", "Couldn't get any data from the url");
 }
 return null;
 }
 @Override
 protected void onPostExecute(String auth2)
 {
 super.onPostExecute(auth2);
 // Dismiss the progress dialog
 if (pDialog.isShowing())
 pDialog.dismiss();
 Toast.makeText(getApplicationContext(), "String retrived:" + auth2, Toast.LENGTH_SHORT).show();
 }
 }
 */

//IS NEED????
public void saveTreeNodesArrayListToJson( View view )
{
boolean result = JSONHelper.exportToJSON(this, treeNodes);
if (result)
{
Toast.makeText(this, "Nodes data saved to JSON", Toast.LENGTH_LONG).show();
}
else
{
Toast.makeText(this, "Can not save nodes to JSON", Toast.LENGTH_LONG).show();
}
}

//IS NEED???
public void getTreeNodesArrayListFromJson( View view )
{
treeNodes = JSONHelper.importFromJSON(this);
if (treeNodes != null)
{
Toast.makeText(this, "Nodes data restored from JSON", Toast.LENGTH_LONG).show();
}
else
{
Toast.makeText(this, "Can not restore node data from JSON", Toast.LENGTH_LONG).show();
}
}

}
