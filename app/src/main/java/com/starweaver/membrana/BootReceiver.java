package com.starweaver.membrana;

import android.content.*;
import org.eclipse.paho.android.service.*;

public class BootReceiver extends BroadcastReceiver 
{   
@Override
public void onReceive(Context context, Intent intent) 
{
Intent mqttServiceIntent = new Intent(context, MqttService.class);
context.startService(mqttServiceIntent);
Intent mqttConnectionManagerServiceIntent = new Intent(context, MqttConnectionManagerService.class);
context.startService(mqttConnectionManagerServiceIntent);
}
}
