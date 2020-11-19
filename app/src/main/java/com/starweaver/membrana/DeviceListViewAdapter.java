package com.starweaver.membrana;

import android.*;
import android.content.*;
import android.graphics.*;
import android.view.*;
import android.widget.*;
import android.widget.RelativeLayout.*;
import java.util.*;

public class DeviceListViewAdapter extends BaseAdapter 
{
FragmentDevices fragmentDevices;
TitleString titleString;
TitleIcon titleIcon;
private ImplementInRobot listener;
private LayoutInflater   inflater;
private ArrayList<String>  arrayListString;
private ArrayList<Integer> arrayListInteger;
private ArrayList<Integer> arrayListPattern;

Context con;
//MqttConnectionManagerService mqttConnectionManagerService;
//CardView cardview;

private boolean multisensorAvaiable;
private int buttonStateInt;
private int globalButtonStateInt;
private int globalPosition;
private String topicString;
String[] mqttString = new String[]{"00","00","00","00","00"};

//public DeviceListViewAdapter(Context context, ArrayList<String> data1, ArrayList<Integer> img_data1, ArrayList<Integer> pattern1, ImplementInRobot listener, String t, String[] s, boolean b, int buttonStateInt, FragmentDevices fragmentDevices)
public DeviceListViewAdapter(Context context, ArrayList<String> data1, ArrayList<Integer> img_data1, ArrayList<Integer> pattern1, ImplementInRobot listener, String t, boolean b, int buttonStateInt, FragmentDevices fragmentDevices)
{
inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
arrayListString = data1;
arrayListInteger = img_data1;
arrayListPattern = pattern1;
con = context;
this.listener = listener;
topicString = t;
//mqttString = s;//temporary unset
titleString = new TitleString();
titleIcon = new TitleIcon();
multisensorAvaiable = b;
this.buttonStateInt = buttonStateInt;
this.fragmentDevices = fragmentDevices;
//fragmentDevices.setBottomNavigationView(true);
}

@Override
public int getCount() 
{
return arrayListString.size();
}

@Override
public Object getItem(int position) 
{
return null;
}

@Override
public long getItemId(int position) 
{
return 1;
}

// create a new ImageView for each item referenced by the Adapter
public View getView(final int position, View view, ViewGroup viewgroup) 
{
ImageView image;
ImageView imageMultisensorLumen;
ImageView imageMultisensorTemperature;
ImageView imageMultisensorHumidity;
ImageView imageMultisensorPressure;
ImageView imageMultisensorCo2;
CircleImageView circleImageView;
final TextView title;
TextView textMultisensorLumen;
TextView textMultisensorTemperature;
TextView textMultisensorHumidity;
TextView textMultisensorPressure;
TextView textMultisensorCo2;
TextView textCircleImageView;

Button button;
Button rgbSettingsButton;
Button globalButton;
Button iconButton;
final Button irrigationButton;
final ToggleButton tbutton; 
int buttonHeight = 170;
int buttonWidth = 170;
final String str = "" + position;

//experimental mqttstring start
/*int i = titleString.getMqttStringInt();
mqttString[0] = titleString.getMqttStringPosition(i,0);
mqttString[1] = titleString.getMqttStringPosition(i,1);
mqttString[2] = titleString.getMqttStringPosition(i,2);
mqttString[3] = titleString.getMqttStringPosition(i,3);
mqttString[4] = titleString.getMqttStringPosition(i,4);*/
//experimental mqttstring end
mqttString = fragmentDevices.getCurrentMqttString();

if (view == null) 
{//0
//mqttConnectionManagerService = new MqttConnectionManagerService(con);
RelativeLayout layout = new RelativeLayout(con);
layout.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
//start creating strings of elements
image = new ImageView(con);
imageMultisensorLumen = new ImageView(con);
imageMultisensorTemperature = new ImageView(con);
imageMultisensorHumidity = new ImageView(con);
imageMultisensorPressure = new ImageView(con);
imageMultisensorCo2 = new ImageView(con);
circleImageView = new CircleImageView(con);
title = new TextView(con);
textMultisensorLumen = new TextView(con);
textMultisensorTemperature = new TextView(con);
textMultisensorHumidity = new TextView(con);
textMultisensorPressure = new TextView(con);
textMultisensorCo2 = new TextView(con);
textCircleImageView = new TextView(con);
button = new Button(con);
irrigationButton = new Button(con);
rgbSettingsButton = new Button(con);
iconButton = new Button(con);
tbutton = new ToggleButton(con);
globalButton = new Button(con);
//cardview = new CardView(con);

image.setId(1);
imageMultisensorLumen.setId(2);
imageMultisensorTemperature.setId(3);
imageMultisensorHumidity.setId(4);
imageMultisensorPressure.setId(5);
imageMultisensorCo2.setId(6);
title.setId(7);
textMultisensorLumen.setId(8);
textMultisensorTemperature.setId(9);
textMultisensorHumidity.setId(10);
textMultisensorPressure.setId(11);
textMultisensorCo2.setId(12);
button.setId(13);
rgbSettingsButton.setId(14);
tbutton.setId(15);
globalButton.setId(21);
//cardview.setId(16);
irrigationButton.setId(17);
iconButton.setId(18);
circleImageView.setId(19);
textCircleImageView.setId(20);

RelativeLayout.LayoutParams imageParams = new RelativeLayout.LayoutParams(dpToPx(30, con), dpToPx(30, con));
imageParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
imageParams.addRule(RelativeLayout.CENTER_VERTICAL);
imageParams.setMargins(15, 15, 15, 15);

RelativeLayout.LayoutParams imageMultisensorHumidityParams = new RelativeLayout.LayoutParams(dpToPx(20, con), dpToPx(20, con));
imageMultisensorHumidityParams.addRule(RelativeLayout.RIGHT_OF, textMultisensorTemperature.getId());
imageMultisensorHumidityParams.setMargins(dpToPx(5, con), dpToPx(5, con), dpToPx(3, con), dpToPx(5, con));

RelativeLayout.LayoutParams imageMultisensorPressureParams = new RelativeLayout.LayoutParams(dpToPx(20, con), dpToPx(20, con)); 
imageMultisensorPressureParams.addRule(RelativeLayout.RIGHT_OF, textMultisensorHumidity.getId());
imageMultisensorPressureParams.setMargins(dpToPx(5, con), dpToPx(5, con), dpToPx(3, con), dpToPx(5, con));

RelativeLayout.LayoutParams imageMultisensorCo2Params = new RelativeLayout.LayoutParams(dpToPx(20, con), dpToPx(20, con));
imageMultisensorCo2Params.addRule(RelativeLayout.LEFT_OF, textMultisensorCo2.getId());
imageMultisensorCo2Params.setMargins(dpToPx(5, con), dpToPx(5, con), dpToPx(3, con), dpToPx(5, con));

RelativeLayout.LayoutParams imageMultisensorTemperatureParams = new RelativeLayout.LayoutParams(dpToPx(20, con), dpToPx(20, con));
imageMultisensorTemperatureParams.addRule(RelativeLayout.RIGHT_OF, textMultisensorLumen.getId());
imageMultisensorTemperatureParams.setMargins(dpToPx(5, con), dpToPx(5, con), dpToPx(3, con), dpToPx(5, con));

RelativeLayout.LayoutParams imageMultisensorLumenParams = new RelativeLayout.LayoutParams(dpToPx(20, con), dpToPx(20, con));
imageMultisensorLumenParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
imageMultisensorLumenParams.setMargins(dpToPx(5, con), dpToPx(5, con), dpToPx(3, con), dpToPx(5, con));

RelativeLayout.LayoutParams imageAutoModeParams = new RelativeLayout.LayoutParams(dpToPx(30, con), dpToPx(30, con)); 
imageAutoModeParams.addRule(RelativeLayout.RIGHT_OF, title.getId());
imageAutoModeParams.setMargins(dpToPx(5, con), dpToPx(5, con), dpToPx(3, con), dpToPx(5, con));

RelativeLayout.LayoutParams circleImageParams = new RelativeLayout.LayoutParams(buttonWidth, buttonHeight);
circleImageParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
circleImageParams.addRule(RelativeLayout.CENTER_VERTICAL);
circleImageParams.setMargins(15, 15, 15, 15);

RelativeLayout.LayoutParams textParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
textParams.addRule(RelativeLayout.RIGHT_OF, image.getId());
//textParams.addRule(RelativeLayout.RIGHT_OF, iconButton.getId());
textParams.addRule(RelativeLayout.CENTER_VERTICAL);
textParams.setMargins(10, 20, 5, 5);

RelativeLayout.LayoutParams textIconButtonParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//textParams.addRule(RelativeLayout.RIGHT_OF, image.getId());
textIconButtonParams.addRule(RelativeLayout.RIGHT_OF, iconButton.getId());
textIconButtonParams.addRule(RelativeLayout.CENTER_VERTICAL);
textIconButtonParams.setMargins(10, 20, 5, 5);

RelativeLayout.LayoutParams textMultisensorLumenParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
textMultisensorLumenParams.addRule(RelativeLayout.RIGHT_OF, imageMultisensorLumen.getId());
textMultisensorLumenParams.addRule(RelativeLayout.CENTER_VERTICAL);
textMultisensorLumenParams.setMargins(0, dpToPx(5, con), dpToPx(5, con), dpToPx(5, con));

RelativeLayout.LayoutParams textMultisensorTemperatureParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
textMultisensorTemperatureParams.addRule(RelativeLayout.RIGHT_OF, imageMultisensorTemperature.getId());
textMultisensorTemperatureParams.addRule(RelativeLayout.CENTER_VERTICAL);
textMultisensorTemperatureParams.setMargins(0, dpToPx(5, con), dpToPx(5, con), dpToPx(5, con));

RelativeLayout.LayoutParams textMultisensorHumidityParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
textMultisensorHumidityParams.addRule(RelativeLayout.RIGHT_OF, imageMultisensorHumidity.getId());
textMultisensorHumidityParams.addRule(RelativeLayout.CENTER_VERTICAL);
textMultisensorHumidityParams.setMargins(0, dpToPx(5, con), dpToPx(5, con), dpToPx(5, con));

RelativeLayout.LayoutParams textMultisensorPressureParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
textMultisensorPressureParams.addRule(RelativeLayout.RIGHT_OF, imageMultisensorPressure.getId());
textMultisensorPressureParams.addRule(RelativeLayout.CENTER_VERTICAL);
textMultisensorPressureParams.setMargins(0, dpToPx(5, con), dpToPx(5, con), dpToPx(5, con));

RelativeLayout.LayoutParams textMultisensorCo2Params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
textMultisensorCo2Params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
textMultisensorCo2Params.addRule(RelativeLayout.CENTER_VERTICAL);
textMultisensorCo2Params.setMargins(0, dpToPx(5, con), dpToPx(5, con), dpToPx(5, con));

RelativeLayout.LayoutParams textCircleImageViewParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
textCircleImageViewParams.addRule(RelativeLayout.RIGHT_OF, circleImageView.getId());
textCircleImageViewParams.addRule(RelativeLayout.CENTER_VERTICAL);
textCircleImageViewParams.setMargins(10, 20, 5, 5);

RelativeLayout.LayoutParams buttonParams = new RelativeLayout.LayoutParams(buttonWidth, buttonHeight);
buttonParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
buttonParams.setMargins(15, 15, 15, 15);

RelativeLayout.LayoutParams rgbSettingsButtonParams = new RelativeLayout.LayoutParams(buttonWidth, buttonHeight);
rgbSettingsButtonParams.addRule(RelativeLayout.LEFT_OF, tbutton.getId());
rgbSettingsButtonParams.setMargins(15, 15, 15, 15);

RelativeLayout.LayoutParams globalButtonParams = new RelativeLayout.LayoutParams(dpToPx(35, con), dpToPx(35, con));
globalButtonParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
globalButtonParams.setMargins(15, 40, 15, 15);

RelativeLayout.LayoutParams iconButtonParams = new RelativeLayout.LayoutParams(dpToPx(30, con), dpToPx(30, con));
iconButtonParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
iconButtonParams.addRule(RelativeLayout.CENTER_VERTICAL);
iconButtonParams.setMargins(15, 15, 15, 15);

RelativeLayout.LayoutParams tbuttonParams = new RelativeLayout.LayoutParams(buttonWidth, buttonHeight);
tbuttonParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
tbuttonParams.setMargins(15, 15, 15, 15);

final RelativeLayout.LayoutParams cardViewParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
cardViewParams.setMargins(15, 15, 15, 15);

switch (arrayListPattern.get(position))
{
case 0: //device
//layout.addView(image, imageParams);
layout.addView(iconButton, iconButtonParams);
//layout.addView(title, textParams);
layout.addView(title, textIconButtonParams);
layout.addView(tbutton, tbuttonParams);

//image.setImageResource(arrayListInteger.get(position));
iconButton.setBackgroundResource(arrayListInteger.get(position));
iconButton.setText(null);
iconButton.setOnClickListener(new View.OnClickListener()
{
@Override public void onClick(View v)
{
titleIcon.setSettingsDrawableArrayList(arrayListInteger.get(position));
titleString.setGlobalTitleSecond(arrayListString.get(position));
fragmentDevices.showFragmentSettings(position,topicString,str);
}
});
//titleIcon.setSettingsDrawableArrayList(position, arrayListInteger.get(position));
title.setText(arrayListString.get(position));
title.setTextSize(19);
title.setTypeface(Typeface.MONOSPACE);

if (arrayListString.get(position) == "Gate")
{
tbutton.setBackgroundResource(R.drawable.toggle_selector_open);
}
else
{
tbutton.setBackgroundResource(R.drawable.toggle_selector);
}

if (multisensorAvaiable)
{
tbutton.setChecked(titleString.getToggleButtonPattern(buttonStateInt, position - 1));
}
else
{
tbutton.setChecked(titleString.getToggleButtonPattern(buttonStateInt, position));
}
tbutton.setText(null);
tbutton.setTextOn(null);
tbutton.setTextOff(null);
tbutton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() 
{
public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) 
{
//final String str = "" + position;
if (isChecked) 
{
// *** send message *** //
fragmentDevices.sendMessageToMqttConnectionManagerService(topicString, str, "1");
// ***  -  *** //
if (multisensorAvaiable)
{
titleString.setToggleButtonPattern(buttonStateInt, position - 1, true);
}
else
{
titleString.setToggleButtonPattern(buttonStateInt, position, true);
}

}
else 
{
//*** send message ***//
fragmentDevices.sendMessageToMqttConnectionManagerService(topicString, str, "0");
//***      -      ***//

if (multisensorAvaiable)
{
titleString.setToggleButtonPattern(buttonStateInt, position - 1, false);
}
else
{
titleString.setToggleButtonPattern(buttonStateInt, position, false);
}
}
}
});
break;
case 1: //multisensor
/*LayoutInflater layoutInflater = LayoutInflater.from(con);
 view = layoutInflater.inflate(R.layout.temporary_adapter_multisensor, null);
 textMultisensorLumen = (TextView)view.findViewById(R.id.lumentTextView);
 textMultisensorTemperature = (TextView)view.findViewById(R.id.temperatureTextView);
 textMultisensorHumidity = (TextView)view.findViewById(R.id.humidityTextView);
 textMultisensorPressure = (TextView)view.findViewById(R.id.pressureTextView);
 textMultisensorCo2 = (TextView)view.findViewById(R.id.co2TextView);
 textMultisensorLumen.setText(mqttString[0]+"Lm");
 textMultisensorTemperature.setText(mqttString[1]+"°C");
 textMultisensorHumidity.setText(mqttString[2]+"%");
 textMultisensorPressure.setText(mqttString[3]+"Pa");
 textMultisensorCo2.setText(mqttString[4]+"ppm");*/
layout.addView(imageMultisensorLumen, imageMultisensorLumenParams);
layout.addView(imageMultisensorTemperature, imageMultisensorTemperatureParams);
layout.addView(imageMultisensorHumidity, imageMultisensorHumidityParams);
layout.addView(imageMultisensorPressure, imageMultisensorPressureParams);
layout.addView(imageMultisensorCo2, imageMultisensorCo2Params);
layout.addView(textMultisensorLumen, textMultisensorLumenParams);
layout.addView(textMultisensorTemperature, textMultisensorTemperatureParams);
layout.addView(textMultisensorHumidity, textMultisensorHumidityParams);
layout.addView(textMultisensorPressure, textMultisensorPressureParams);
layout.addView(textMultisensorCo2, textMultisensorCo2Params);

imageMultisensorLumen.setImageResource(R.drawable.smart_lumen);
imageMultisensorTemperature.setImageResource(R.drawable.smart_temperature);
imageMultisensorHumidity.setImageResource(R.drawable.smart_humidity);
imageMultisensorPressure.setImageResource(R.drawable.smart_pressure);
imageMultisensorCo2.setImageResource(R.drawable.smart_co2);

textMultisensorLumen.setText((mqttString[0] == null) ? "00Lm": mqttString[0] + "Lm");
textMultisensorLumen.setTextSize(14);
textMultisensorLumen.setTypeface(Typeface.MONOSPACE);
//textMultisensorLumen.setTextColor(Color.parseColor("#509C4E"));
//textMultisensorLumen.setTextColor(R.color.chart_color);
//
textMultisensorTemperature.setText((mqttString[1] == null) ? "00°C": mqttString[1] + "°C");
textMultisensorTemperature.setTextSize(14);
textMultisensorTemperature.setTypeface(Typeface.MONOSPACE);
//textMultisensorTemperature.setTextColor(R.color.sensor_color);
//
textMultisensorHumidity.setText((mqttString[2] == null) ? "00%": mqttString[2] + "%");
textMultisensorHumidity.setTextSize(14);
textMultisensorHumidity.setTypeface(Typeface.MONOSPACE);
//textMultisensorHumidity.setTextColor(R.color.chart_color);
//
textMultisensorPressure.setText((mqttString[3] == null) ? "000mmHg": mqttString[3] + "mmHg");
textMultisensorPressure.setTextSize(14);
textMultisensorPressure.setTypeface(Typeface.MONOSPACE);
//textMultisensorPressure.setTextColor(R.color.chart_color);
//
textMultisensorCo2.setText((mqttString[4] == null) ? "00Ppc": mqttString[4] + "Ppc");
textMultisensorCo2.setTextSize(14);
textMultisensorCo2.setTypeface(Typeface.MONOSPACE);
//textMultisensorCo2.setTextColor(R.color.material_blue_grey_800);
//
break;
case 2://avatar
layout.addView(image, imageParams);
layout.addView(title, textParams);
layout.addView(button, buttonParams);

image.setImageResource(arrayListInteger.get(position));

title.setText(arrayListString.get(position));
title.setTextSize(19);
title.setTypeface(Typeface.MONOSPACE);

button.setBackgroundResource(R.drawable.smart_cardboard);
button.setText(null);
button.setOnClickListener(new View.OnClickListener()
{
@Override public void onClick(View v)
{
//listener.startImplement();
fragmentDevices.showFragmentEnterAstral();
}
});
break;

case 3://rgb lights
//https://stackoverflow.com/questions/24502394/call-a-fragment-method-from-an-adapter
//layout.addView(image, imageParams);
layout.addView(iconButton, iconButtonParams);
//layout.addView(title, textParams);
layout.addView(title, textIconButtonParams);
layout.addView(tbutton, tbuttonParams);
layout.addView(rgbSettingsButton, rgbSettingsButtonParams);

//image.setImageResource(arrayListInteger.get(position));
iconButton.setBackgroundResource(arrayListInteger.get(position));
iconButton.setText(null);
iconButton.setOnClickListener(new View.OnClickListener()
{
@Override public void onClick(View v)
{
//fragmentDevices.sendMessageToMqttConnectionManagerService("8","8","8");
titleIcon.setSettingsDrawableArrayList(arrayListInteger.get(position));
titleString.setGlobalTitleSecond(arrayListString.get(position));
fragmentDevices.showFragmentSettings(position,topicString,str);
}
});

title.setText(arrayListString.get(position));
title.setTextSize(19);
title.setTypeface(Typeface.MONOSPACE);

rgbSettingsButton.setBackgroundResource(R.drawable.smart_settings);
rgbSettingsButton.setText(null);
rgbSettingsButton.setOnClickListener(new View.OnClickListener()
{
@Override public void onClick(View v)
{
//fragmentDevices.sendMessageToMqttConnectionManagerService("third_","eye","Astral_intrO");
if (multisensorAvaiable)
{
fragmentDevices.showFragmentRGBcolor(position-1);
}
else
{
fragmentDevices.showFragmentRGBcolor(position);
}
//
/*cardview.setLayoutParams(cardViewParams);
cardview.setRadius(15);
cardview.setPadding(25, 25, 25, 25);
cardview.setCardBackgroundColor(Color.MAGENTA);
cardview.setMaxCardElevation(30);
cardview.setMaxCardElevation(6);
temporaryTextView = new TextView(con);
temporaryTextView.setLayoutParams(temporaryTextViewParams);
temporaryTextView.setText("CardView Programmatically");
temporaryTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
temporaryTextView.setTextColor(Color.WHITE);
temporaryTextView.setPadding(25, 25, 25, 25);
temporaryTextView.setGravity(Gravity.CENTER);
cardview.addView(temporaryTextView);
layout.addView(cardview, cardViewParams);*/
//
}
});

tbutton.setBackgroundResource(R.drawable.toggle_selector);
if (multisensorAvaiable)
{
tbutton.setChecked(titleString.getToggleButtonPattern(buttonStateInt, position - 1));
}
else
{
tbutton.setChecked(titleString.getToggleButtonPattern(buttonStateInt, position));
}
tbutton.setText(null);
tbutton.setTextOn(null);
tbutton.setTextOff(null);
tbutton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() 
{
public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) 
{
if (isChecked) 
{
// *** sendMessage *** //
//mqttConnectionManagerService.PublishLight(titleString.getBulbInt());
fragmentDevices.sendPublishLightMessageToMqttConnectionManagerService(titleString.getBulbInt());
// *** - *** //
if (multisensorAvaiable)
{
titleString.setToggleButtonPattern(buttonStateInt, position - 1, true);
}
else
{
titleString.setToggleButtonPattern(buttonStateInt, position, true);
}
}
else 
{
//mqttConnectionManagerService.PublishLight(titleString.getBulbInt()+10);
fragmentDevices.sendPublishLightMessageToMqttConnectionManagerService(titleString.getBulbInt()+10);
//<--fragmentDevices.sendMessageToMqttConnectionManagerService(String topic, String position, String message)
if (multisensorAvaiable)
{
titleString.setToggleButtonPattern(buttonStateInt, position - 1, false);
}
else
{
titleString.setToggleButtonPattern(buttonStateInt, position, false);
}
}
}
});
break;

case 4://irrigation
//layout.addView(image, imageParams);
layout.addView(iconButton, iconButtonParams);
//layout.addView(title, textParams);
layout.addView(title, textIconButtonParams);
layout.addView(irrigationButton, buttonParams);

//image.setImageResource(arrayListInteger.get(position));
iconButton.setBackgroundResource(arrayListInteger.get(position));
iconButton.setText(null);
iconButton.setOnClickListener(new View.OnClickListener()
{
@Override public void onClick(View v)
{
//fragmentDevices.sendMessageToMqttConnectionManagerService("8","8","8");
titleIcon.setSettingsDrawableArrayList(arrayListInteger.get(position));
titleString.setGlobalTitleSecond(arrayListString.get(position));
fragmentDevices.showFragmentSettings(position,topicString,str);
}
});

title.setText(arrayListString.get(position));
title.setTextSize(19);
title.setTypeface(Typeface.MONOSPACE);

irrigationButton.setBackgroundResource(R.drawable.smart_off);
irrigationButton.setText(null);
irrigationButton.setOnClickListener(new View.OnClickListener()
{
@Override public void onClick(View v)
{
irrigationButton.setBackgroundResource(R.drawable.smart_on);
fragmentDevices.sendMessageToMqttConnectionManagerService(topicString, str, "1");
irrigationButton.postDelayed(new Runnable() {
@Override
public void run() 
{
irrigationButton.setBackgroundResource(R.drawable.smart_off);
//send mqtt
//2.array mqttstring
}
}, 3000);
}
});
break;

case 5://settings sensor mode(or global settings mode???)
globalPosition = titleString.getGlobalPositionInt();
globalButtonStateInt = titleString.getGlobalButtonStateInt();
layout.addView(image, imageParams);
layout.addView(title, textParams);
layout.addView(tbutton, tbuttonParams);
layout.addView(rgbSettingsButton, rgbSettingsButtonParams);
image.setImageResource(arrayListInteger.get(position));
title.setText(arrayListString.get(position));
title.setTextSize(19);
title.setTypeface(Typeface.MONOSPACE);
rgbSettingsButton.setBackgroundResource(R.drawable.smart_settings);
rgbSettingsButton.setText(null);
rgbSettingsButton.setOnClickListener(new View.OnClickListener()
{
@Override public void onClick(View v)
{
fragmentDevices.showFragmentTimerPeriods(position);
}
});
tbutton.setBackgroundResource(R.drawable.toggle_selector);
tbutton.setChecked(titleString.getSettingsButtonPattern(globalButtonStateInt, globalPosition, position));
tbutton.setText(null);
tbutton.setTextOn(null);
tbutton.setTextOff(null);
tbutton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() 
{
public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) 
{
if (isChecked) 
{
fragmentDevices.sendMessageToMqttConnectionManagerService(titleString.getSettingTopicString(1), titleString.getSettingTopicString(2), "a_m_1");
titleString.setSettingsButtonPattern(globalButtonStateInt, globalPosition, 0, true);
}
else 
{
fragmentDevices.sendMessageToMqttConnectionManagerService(titleString.getSettingTopicString(1), titleString.getSettingTopicString(2), "a_m_0");
titleString.setSettingsButtonPattern(globalButtonStateInt, globalPosition, 0, false);
}
}
});
break;

case 6://settings timer mode
globalPosition = titleString.getGlobalPositionInt();
globalButtonStateInt = titleString.getGlobalButtonStateInt();
layout.addView(image, imageParams);
layout.addView(title, textParams);
layout.addView(tbutton, tbuttonParams);
layout.addView(rgbSettingsButton, rgbSettingsButtonParams);
image.setImageResource(arrayListInteger.get(position));
title.setText(arrayListString.get(position));
title.setTextSize(19);
title.setTypeface(Typeface.MONOSPACE);
rgbSettingsButton.setBackgroundResource(R.drawable.smart_settings);
rgbSettingsButton.setText(null);
rgbSettingsButton.setOnClickListener(new View.OnClickListener()
{
@Override public void onClick(View v)
{
fragmentDevices.showFragmentTimerPeriods(position);
}
});
tbutton.setBackgroundResource(R.drawable.toggle_selector);
tbutton.setChecked(titleString.getSettingsButtonPattern(globalButtonStateInt, globalPosition, position));
tbutton.setText(null);
tbutton.setTextOn(null);
tbutton.setTextOff(null);
tbutton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() 
{
public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) 
{
if (isChecked) 
{
fragmentDevices.sendMessageToMqttConnectionManagerService(titleString.getSettingTopicString(1), titleString.getSettingTopicString(2), "t_m_1");
titleString.setSettingsButtonPattern(globalButtonStateInt, globalPosition, 1, true);
}
else 
{
fragmentDevices.sendMessageToMqttConnectionManagerService(titleString.getSettingTopicString(1), titleString.getSettingTopicString(2), "t_m_0");
titleString.setSettingsButtonPattern(globalButtonStateInt, globalPosition, 1, false);
}
}
});
break;

case 7://timer periods
globalPosition = titleString.getGlobalPositionInt();
globalButtonStateInt = titleString.getGlobalButtonStateInt();
layout.addView(image, imageParams);
layout.addView(title, textParams);
layout.addView(tbutton, tbuttonParams);
layout.addView(rgbSettingsButton, rgbSettingsButtonParams);
image.setImageResource(arrayListInteger.get(position));
image.setImageResource(arrayListInteger.get(titleString.getGlobalPositionInt()));
title.setText(arrayListString.get(position));
title.setTextSize(19);
title.setTypeface(Typeface.MONOSPACE);
rgbSettingsButton.setBackgroundResource(R.drawable.smart_time_period);
rgbSettingsButton.setText(titleString.timerDataArrayList.get(globalButtonStateInt).get(globalPosition).get(position).get(0)+":"+titleString.timerDataArrayList.get(globalButtonStateInt).get(globalPosition).get(position).get(1)+"                "+titleString.timerDataArrayList.get(globalButtonStateInt).get(globalPosition).get(position).get(2)+":"+titleString.timerDataArrayList.get(globalButtonStateInt).get(globalPosition).get(position).get(3));
rgbSettingsButton.setOnClickListener(new View.OnClickListener()
{
@Override public void onClick(View v)
{
fragmentDevices.showFragmentTimePicker(globalPosition, globalButtonStateInt, position);
}
});
tbutton.setBackgroundResource(R.drawable.toggle_selector);
tbutton.setChecked(titleString.getTimerPeriodsButtonPattern(globalButtonStateInt, globalPosition, position));
tbutton.setText(null);
tbutton.setTextOn(null);
tbutton.setTextOff(null);
tbutton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() 
{
public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) 
{
if (isChecked) 
{
fragmentDevices.sendMessageToToast(""+globalButtonStateInt+" "+globalPosition+" "+position);
titleString.setTimerPeriodsButtonPattern(globalButtonStateInt, globalPosition, position, true);
}
else 
{
titleString.setTimerPeriodsButtonPattern(globalButtonStateInt, globalPosition, position, false);
}
}
});
break;

case 8: //717 питання - чи зберігаються чайлдноди при внeсeнні у датабeйс
layout.addView(circleImageView, circleImageParams);
layout.addView(textCircleImageView, textCircleImageViewParams);
layout.addView(globalButton, globalButtonParams);
circleImageView.setImageResource(fragmentDevices.getCurrentLevelTreeNodeChildEnvironmentPicture(position));
textCircleImageView.setText(fragmentDevices.getCurrentLevelTreeNodeChildEnvironmentName(position));
textCircleImageView.setTextSize(19);
textCircleImageView.setTypeface(Typeface.MONOSPACE);
globalButton.setBackgroundResource(R.drawable.mybuttonicon2);
globalButton.setText(null);
//globalButton.setBackgroundColor(Color.parseColor("#FF0000"));
globalButton.setOnClickListener(new View.OnClickListener()
{
@Override public void onClick(View v)
{
//listener.startImplement();
//fragmentDevices.showFragmentEnterAstral();
fragmentDevices.changeCurrentTreeNode(position);
}
});
break;

}//--end SWITCH

view = layout;
}//--end of IF VIEW = NULL
return view;
}//--end of GETVIEW

public static int dpToPx(int dp, Context context) 
{
float density = context.getResources().getDisplayMetrics().density;
return Math.round((float) dp * density);
}
}
