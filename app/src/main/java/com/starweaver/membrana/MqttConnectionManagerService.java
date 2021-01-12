package com.starweaver.membrana;

import android.*;
import android.app.*;
import android.content.*;
import android.net.*;
import android.os.*;
import android.util.*;
import java.util.*;
import org.eclipse.paho.android.service.*;
import org.eclipse.paho.client.mqttv3.*;

public class MqttConnectionManagerService extends Service 
{
NotificationManager notifycationManager;
Notification notification;
private static boolean isRunning = false;
private MqttAndroidClient client;
private MqttConnectOptions options;
final String publishGreenTopic = "green";//IS NEED???
final String subscriptionTopicMultisensor = "multisensor";
//final String subscriptionTopicMultisensor = "Multisensor/#";
//final String subscriptionTopicMultisensor1 = "Hallway";
//final String subscriptionTopicMultisensor2 = "Greenhouse";
final String username = "androidUser";
final String password = "888888";
final String serverUri = "---";
final String clientId = "ExampleAndroidClient";
Context context;
String[] mqttString = new String[5];
byte[] mqttBytePayload = new byte[12];
char[] mqttCharPayload = new char[12];

ArrayList<Messenger> mClients = new ArrayList<Messenger>(); // Keeps track of all current registered clients.
//int mValue = 0; // Holds last value set by a client.
static final int MSG_REGISTER_CLIENT = 1;
static final int MSG_UNREGISTER_CLIENT = 2;
static final int MSG_SET_INT_VALUE = 3;
static final int MSG_SET_STRING_VALUE = 4;
static final int MSG_SET_ASTRAL_VALUE = 5;
final Messenger mMessenger = new Messenger(new IncomingHandler()); // Target we publish for clients to send messages to IncomingHandler.


@Override
public IBinder onBind(Intent intent) 
{
//notificationMessage("get binder");
return mMessenger.getBinder();
}

// Handler of incoming messages from clients.
class IncomingHandler extends Handler 
{ 
@Override
public void handleMessage(Message msg) 
{
switch (msg.what) 
{
case MSG_REGISTER_CLIENT:
mClients.add(msg.replyTo);
//notificationMessage("client registered!");
break;
case MSG_UNREGISTER_CLIENT:
mClients.remove(msg.replyTo);
break;
case MSG_SET_INT_VALUE:
//notificationMessage("" + msg.arg1);
mqttPublishLight(msg.arg1);
break;
case MSG_SET_STRING_VALUE:
String str1 = msg.getData().getString("str1");
String str2 = msg.getData().getString("str2");
String str3 = msg.getData().getString("str3");
//notificationMessage(str1 + str2 + str3);
mqttPublish(str1,str2,str3);
break;
case MSG_SET_ASTRAL_VALUE://NOWORK NOW STILL!!
String astralString = msg.getData().getString("str1");
//notificationMessage("astralString");
astralPublish(astralString);
break;
default:
super.handleMessage(msg);
}
}
}

private void sendMessageStringToUI(String messageString0, String messageString1)
{
for (int i=mClients.size() - 1; i >= 0; i--) 
{
try 
{
Bundle b = new Bundle();
b.putString("str1", messageString0);
b.putString("str2", messageString1);
Message msg = Message.obtain(null, MSG_SET_STRING_VALUE);
msg.setData(b);
mClients.get(i).send(msg);
}
catch (RemoteException e) 
{
// The client is dead. Remove it from the list; we are going through the list from back to front so this is safe to do inside the loop.
mClients.remove(i);
}
}
}

private void sendMessageIntToUI(int intvaluetosend) 
{
for (int i=mClients.size() - 1; i >= 0; i--) 
{
try 
{
// Send data as an Integer
mClients.get(i).send(Message.obtain(null, MSG_SET_INT_VALUE, intvaluetosend, 0));
}
catch (RemoteException e) 
{
// The client is dead. Remove it from the list; we are going through the list from back to front so this is safe to do inside the loop.
mClients.remove(i);
}
}
}

@Override
public void onCreate() 
{
super.onCreate();
//mqtt start
options = createMqttConnectOptions();
client = createMqttAndroidClient();
//mqtt end
isRunning = true;
notifycationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//notificationMessage("onCreate");
}

@Override
public int onStartCommand(Intent intent, int flags, int startId) 
{
super.onStartCommand(intent, flags, startId);
//mqtt start
this.connect(client, options);
//mqtt end
Log.i("MyService", "Received start id " + startId + ": " + intent);
//notificationMessage("Service Restarted");
return START_STICKY; // run until explicitly stopped.
}

public static boolean isRunning()
{
return isRunning;
}

@Override
public void onDestroy() 
{
super.onDestroy();
//notifycationManager.cancel(R.string.service_started); // Cancel the persistent notification.
Log.d("MyService", "onDestroy");
isRunning = false;
//notificationMessage("onDestroy");
}

public void notificationMessage(String s)
{
Notification.Builder notifycationBuilder = new Notification.Builder(this);
notifycationBuilder.setAutoCancel(false);
notifycationBuilder.setTicker("Notification");
notifycationBuilder.setContentTitle("Membrana");//title of the message             
notifycationBuilder.setContentText(s);
notifycationBuilder.setSmallIcon(R.drawable.ic_launcher, 0);
//notifycationBuilder.setContentIntent(pendingIntent);
//notifycationBuilder.setVibrate(new long[] { 500, 500, 500, 500, 500 });//works only if vibration mode is set
//notifycationBuilder.setSubText("This is subtext...");   //API level 16
//Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
//notifycationBuilder.setNumber(100);
notifycationBuilder.setOngoing(false);//if true => never closes
//Uri alarmSound = Uri.parse("file:///system/media/audio/notifications/23_Jump.ogg");
Uri alarmSound = Uri.parse("file:///system/media/audio/notifications/11_Link.ogg");
notifycationBuilder.setSound(alarmSound);
notifycationBuilder.build();
//Create pending intent, mention the Activity which needs to be 
//triggered when user clicks on notification(StopScript.class in this case)
PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
notifycationBuilder.setContentIntent(pendingIntent);
notification = notifycationBuilder.getNotification();
notifycationManager.notify(11, notification);
}

//mqtt start
private MqttConnectOptions createMqttConnectOptions() 
{
//create and return options
options = new MqttConnectOptions();
options.setAutomaticReconnect(true);
options.setCleanSession(false);
options.setUserName(username);
options.setPassword(password.toCharArray());
return options;
}

private MqttAndroidClient createMqttAndroidClient() 
{
//create and return client
client = new MqttAndroidClient(this, serverUri, clientId);
client.setCallback(new MqttCallbackExtended()
{

@Override
public void connectComplete(boolean b, String s)
{
}

@Override
public void connectionLost(Throwable throwable)
{
}

@Override
public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception
{
sendMessageStringToUI(topic, mqttMessage.toString());
//notificationMessage("message!");
}

@Override
public void deliveryComplete(IMqttDeliveryToken imqttDeliveryToken)
{
}
});
return client;
}

public void connect(final MqttAndroidClient client, MqttConnectOptions options) 
{
try
{
client.connect(options, null, new IMqttActionListener()
{
@Override
public void onSuccess(IMqttToken asyncActionToken)
{
DisconnectedBufferOptions disconnectedBuferOptions = new DisconnectedBufferOptions();
disconnectedBuferOptions.setBufferEnabled(true);
disconnectedBuferOptions.setBufferSize(100);
disconnectedBuferOptions.setPersistBuffer(false);
disconnectedBuferOptions.setDeleteOldestMessages(false);
client.setBufferOpts(disconnectedBuferOptions);
subscribeToTopic();
subscribeToTopic1();
}

@Override
public void onFailure(IMqttToken asyncActionToken, Throwable exception)
{
}

});
}
catch (MqttException ex)
{
ex.printStackTrace();
}
}

private void subscribeToTopic()
{
try
{
client.subscribe(subscriptionTopicMultisensor, 0, null, new IMqttActionListener()
{
@Override
public void onSuccess(IMqttToken asyncActionToken)
{
}
@Override 
public void onFailure(IMqttToken asyncActionToken, Throwable exception)
{
}
});
}
catch (MqttException ex)
{
System.err.println("Exception whilst subscribing");
ex.printStackTrace();
}
}

private void subscribeToTopic1()
{
try
{
client.subscribe(subscriptionTopicMultisensor, 0, null, new IMqttActionListener()
{
@Override
public void onSuccess(IMqttToken asyncActionToken)
{
}
@Override 
public void onFailure(IMqttToken asyncActionToken, Throwable exception)
{
}
});
}
catch (MqttException ex)
{
System.err.println("Exception whilst subscribing");
ex.printStackTrace();
}
}

/*public void mqttPublish(String m)
 {
 try
 {
 MqttMessage message = new MqttMessage();//(content.getBytes());
 message.setPayload(m.getBytes());
 client.publish(publishGreenTopic, message);
 }
 catch(MqttException me)
 {
 me.printStackTrace();
 }
 }*/

public void mqttPublish(String topic, String position, String message)
{
try
{
MqttMessage mqttMessage = new MqttMessage();//(content.getBytes());
mqttMessage.setPayload(message.getBytes());
client.publish(topic + position, mqttMessage);
}
catch (MqttException me)
{
me.printStackTrace();
} 
}

public void astralPublish(String message)
{
try
{
MqttMessage mqttMessage = new MqttMessage();//(content.getBytes());
mqttMessage.setPayload(message.getBytes());
client.publish("third_eye", mqttMessage);
}
catch (MqttException me)
{
me.printStackTrace();
} 
}

public void mqttPublishLight(int i)
{
try
{
MqttMessage message = new MqttMessage();//(content.getBytes());
//message.setPayload("{"status":"OFF","brightness":255,"bulb_mode":"white","color":{"r":255,"g":255,"b":255},"color_temp":255}".getBytes());
switch (i)
{
// https://www.rapidtables.com/web/color/index.html 
case 0:
message.setPayload("{status:ON,brightness:255,bulb_mode:color,color:{r:255,g:0,b:0}}".getBytes());
break;
case 1:
message.setPayload("{status:ON,brightness:255,bulb_mode:color,color:{r:255,g:165,b:0}}".getBytes());
break;
case 2:
message.setPayload("{status:ON,brightness:255,bulb_mode:color,color:{r:255,g:255,b:0}}".getBytes());
break;
case 3:
message.setPayload("{status:ON,brightness:255,bulb_mode:color,color:{r:0,g:255,b:0}}".getBytes());
break;
case 4:
message.setPayload("{status:ON,brightness:255,bulb_mode:color,color:{r:0,g:0,b:255}}".getBytes());
break;
case 5:
message.setPayload("{status:ON,brightness:255,bulb_mode:color,color:{r:255,g:0,b:255}}".getBytes());
break;
case 6:
message.setPayload("{status:ON,brightness:255,bulb_mode:white,color:{r:255,g:255,b:255}}".getBytes());
break;
case 7:
message.setPayload("{status:ON,brightness:255,bulb_mode:white,color_temp:150}".getBytes());
break;
case 8:
message.setPayload("{status:ON,brightness:255,bulb_mode:white,color_temp:300}".getBytes());
break;
case 10:
message.setPayload("{status:OFF,brightness:255,bulb_mode:color,color:{r:255,g:0,b:0}}".getBytes());
break;
case 11:
message.setPayload("{status:OFF,brightness:255,bulb_mode:color,color:{r:255,g:165,b:0}}".getBytes());
break;
case 12:
message.setPayload("{status:OFF,brightness:255,bulb_mode:color,color:{r:255,g:255,b:0}}".getBytes());
break;
case 13:
message.setPayload("{status:OFF,brightness:255,bulb_mode:color,color:{r:0,g:255,b:0}}".getBytes());
break;
case 14:
message.setPayload("{status:OFF,brightness:255,bulb_mode:color,color:{r:0,g:0,b:255}}".getBytes());
break;
case 15:
message.setPayload("{status:OFF,brightness:255,bulb_mode:color,color:{r:255,g:0,b:255}}".getBytes());
break;
case 16:
message.setPayload("{status:OFF,brightness:255,bulb_mode:white,color:{r:255,g:255,b:255}}".getBytes());
break;
case 17:
message.setPayload("{status:OFF,brightness:255,bulb_mode:white,color_temp:150}".getBytes());
break;
case 18:
message.setPayload("{status:ON,brightness:255,bulb_mode:white,color_temp:300}".getBytes());
break;
}
client.publish("milight/0x1E7F/rgb_cct/1", message);
}
catch (MqttException me)
{
me.printStackTrace();
}
}
//mqtt end

}
