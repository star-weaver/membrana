package com.mycompany.myapp81;
//IS NEED THIS CLASS??????
import android.app.*;
import android.content.*;
import org.eclipse.paho.android.service.*;
import org.eclipse.paho.client.mqttv3.*;
import android.os.*;

public class BootService extends Service
{
public MqttAndroidClient mqttAndroidClient;
final String serverUri = "tcp://192.168.0.88:1883";
//final String serverUri = "tcp://176.36.227.181:1883";
final String clientId = "ExampleAndroidClient";
final String subscriptionTopicMultisensor = "multisensor";
final String publishGreenTopic = "green";
final String username = "androidUser";
final String password = "888888";

@Override
public IBinder onBind(Intent intent) 
{
return null;
}

public BootService(Context context)
{
mqttAndroidClient = new MqttAndroidClient(context, serverUri, clientId);
mqttAndroidClient.setCallback(new MqttCallbackExtended()
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
}

@Override
public void deliveryComplete(IMqttDeliveryToken imqttDeliveryToken)
{
}
});

connect();
}

public void setCallback(MqttCallbackExtended callback)
{
mqttAndroidClient.setCallback(callback);
}

private void connect()
{
MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
mqttConnectOptions.setAutomaticReconnect(true);
mqttConnectOptions.setCleanSession(false);
mqttConnectOptions.setUserName(username);
mqttConnectOptions.setPassword(password.toCharArray());
try
{
mqttAndroidClient.connect(mqttConnectOptions, null, new IMqttActionListener()
{
@Override
public void onSuccess(IMqttToken asyncActionToken)
{
DisconnectedBufferOptions disconnectedBuferOptions = new DisconnectedBufferOptions();
disconnectedBuferOptions.setBufferEnabled(true);
disconnectedBuferOptions.setBufferSize(100);
disconnectedBuferOptions.setPersistBuffer(false);
disconnectedBuferOptions.setDeleteOldestMessages(false);
mqttAndroidClient.setBufferOpts(disconnectedBuferOptions);
subscribeToTopic();
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
mqttAndroidClient.subscribe(subscriptionTopicMultisensor, 0, null, new IMqttActionListener()
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

public void Publish(String m)
{
try
{
MqttMessage message = new MqttMessage();//(content.getBytes());
message.setPayload(m.getBytes());
mqttAndroidClient.publish(publishGreenTopic, message);
}
catch(MqttException me)
{
me.printStackTrace();
}
}

@Override
public void onCreate() 
//instantiate when service starts firstly
//onCreate() will only ever be called once 
//per instantiated object.
//You only need to implement onCreate() 
//if you actually want/need to initialize 
//something only once.
{
}

@Override
//is called every time a client starts 
//the service
//This means that onStartCommand() 
//can get called multiple times. You 
//should do the things in this method 
//that are needed each time a client 
//requests something from your service. 
public int onStartCommand(Intent intent, int flags, int startId) 
{
connect();
return START_STICKY;
}

} 
