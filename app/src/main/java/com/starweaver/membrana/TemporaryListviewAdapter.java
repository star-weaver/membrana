package com.starweaver.membrana;

import android.app.*;
import android.content.*;
import android.graphics.*;
import android.view.*;
import android.widget.*;
import android.widget.RelativeLayout.*;
import java.util.*;

//REARRANGE DATASTRING MQTTSTRING

public class TemporaryListviewAdapter extends BaseAdapter 
{
private final Context mContext;
TextView textView;
TextView textMultisensorLumen;
TextView textMultisensorTemperature;
TextView textMultisensorHumidity;
TextView textMultisensorPressure;
TextView textMultisensorCo2;
ImageView imageView;
CircleImageView circleView;
/*ImageView imageMultisensorLumen;
ImageView imageMultisensorTemperature;
ImageView imageMultisensorHumidity;
ImageView imageMultisensorPressure;
ImageView imageMultisensorCo2;*/
String[] dataString = new String[5];
String[] nominals = new String[]{"Lm","°C"," %","Pa", "ppm"};
Integer[] icons = {R.drawable.icons33,R.drawable.icons13,R.drawable.icons23,R.drawable.icons33};//!!!!_THIS!!!!!!
Integer[] multisensorIcons = {R.drawable.icons33,R.drawable.icons13,R.drawable.icons23,R.drawable.icons33,R.drawable.icons13};
ArrayList<Integer> arrayListPattern1 = new ArrayList<Integer>(Arrays.asList(0,1,0,1,1));

private ArrayList<String> arrayListString;
private ArrayList<Integer> arrayListImage;
private ArrayList<Integer> arrayListPattern;

MqttConnectionManagerService mqttConnectionManagerService;

String[] mqttString = new String[5];

//IS NEED?
public TemporaryListviewAdapter (Fragment fragment, String[] s, ArrayList<Integer> pattern) 
{
mContext = fragment.getActivity();
dataString = s;
arrayListPattern.clear();
arrayListPattern = pattern;
}

public TemporaryListviewAdapter(Context context, ArrayList<String> data1, ArrayList<Integer> img_data1, ArrayList<Integer> pattern1) 
{
arrayListString = data1;
arrayListImage = img_data1;
arrayListPattern = pattern1;
mContext = context;
}

public TemporaryListviewAdapter(Context context, ArrayList<String> data1, ArrayList<Integer> img_data1, ArrayList<Integer> pattern1, String[] s) 
{
arrayListString = data1;
arrayListImage = img_data1;
arrayListPattern = pattern1;
mqttString = s;
mContext = context;
}

public int getCount() 
{
return arrayListString.size();
}

public Object getItem(int position) 
{
return null;
}

public long getItemId(int position)
{
return 0;
}

public View getView(int position, View convertView, ViewGroup parent) 
{
ImageView image;
TextView title;
final ToggleButton tbutton;
int buttonHeight = 170;
int buttonWidth = 170;
////////********
if (convertView == null) 
{
//42 removed start
//mqttConnectionManagerService = new MqttConnectionManagerService(mContext);
//42 removed end
switch (arrayListPattern.get(position))
{
case 0:
RelativeLayout layout = new RelativeLayout(mContext);
layout.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
image = new ImageView(mContext);
title = new TextView(mContext);
tbutton = new ToggleButton(mContext);
image.setId(1);
title.setId(2);
tbutton.setId(3);
RelativeLayout.LayoutParams imageParams = new RelativeLayout.LayoutParams(buttonWidth, buttonHeight);
imageParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
imageParams.setMargins(15, 15, 15, 15);
RelativeLayout.LayoutParams textParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
textParams.addRule(RelativeLayout.RIGHT_OF, image.getId());
textParams.addRule(RelativeLayout.CENTER_VERTICAL);
textParams.setMargins(10, 20, 5, 5);
RelativeLayout.LayoutParams tbuttonParams = new RelativeLayout.LayoutParams(buttonWidth, buttonHeight);
tbuttonParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
tbuttonParams.setMargins(15, 15, 15, 15);
layout.addView(image, imageParams);
layout.addView(title, textParams);
layout.addView(tbutton, tbuttonParams);

image.setImageResource(arrayListImage.get(position));

title.setText(arrayListString.get(position));
title.setTextSize(19);
title.setTypeface(Typeface.MONOSPACE);

tbutton.setBackgroundResource(R.drawable.toggle_selector);
tbutton.setChecked(true);
tbutton.setText(null);
tbutton.setTextOn(null);
tbutton.setTextOff(null);
//42 removed start
/*tbutton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() 
{
public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) 
{
if (isChecked) 
{
mqttConnectionManagerService.Publish("green","8","1");
}
else 
{
mqttConnectionManagerService.Publish("green","8","0");
}
}
});*/
//42 removed end
break;
//
//mqttHelper = new MqttHelper(mContext);
/*convertView = layoutInflater.inflate(R.layout.temporary_adapter_device, null);


ToggleButton toggleButton0 = (ToggleButton)convertView.findViewById(R.id.tb);

toggleButton0.setOnClickListener(new View.OnClickListener() 
{
@Override
public void onClick(View v) 
{
//--
}
});

TextView textView0 = (TextView)convertView.findViewById(R.id.deviceTextView);
ImageView imageView0 = (ImageView)convertView.findViewById(R.id.deviceImageView);*/

//final ToggleButton toggleButton1 = new ToggleButton(mContext);
//imageView.setImageBitmap(icon);

/*imageView0.setImageResource(arrayListImage.get(position));
textView0.setText(arrayListString.get(position));*/

//toggleButton1.setBackgroundResource(R.drawable.toggle_selector);
/*toggleButton1.setChecked(true);
toggleButton1.setText(null);
toggleButton1.setTextOn(null);
toggleButton1.setTextOff(null);
toggleButton1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() 
{
public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) 
{
if (isChecked) 
{
mqttHelper.Publish("1");
}
else 
{
mqttHelper.Publish("0");
}
}
});*/
//toggleButton0.setText("ASF");
//textView0.setText("xxx");
//imageView.setImageResource(arrayListImage.get(position));
/*toggleButton0.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() 
{
public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) 
{
if (isChecked) 
{
mqttHelper.Publish("1");
}
else 
{
mqttHelper.Publish("0");
}
}
});*/

//break;
case 1:
LayoutInflater layoutInflater = LayoutInflater.from(mContext);
convertView = layoutInflater.inflate(R.layout.temporary_adapter_multisensor, null);
textMultisensorLumen = (TextView)convertView.findViewById(R.id.lumentTextView);
textMultisensorTemperature = (TextView)convertView.findViewById(R.id.temperatureTextView);
textMultisensorHumidity = (TextView)convertView.findViewById(R.id.humidityTextView);
textMultisensorPressure = (TextView)convertView.findViewById(R.id.pressureTextView);
textMultisensorCo2 = (TextView)convertView.findViewById(R.id.co2TextView);
textMultisensorLumen.setText(mqttString[0]+"Lm");
textMultisensorTemperature.setText(mqttString[1]+"°C");
textMultisensorHumidity.setText(mqttString[2]+"%");
textMultisensorPressure.setText(mqttString[3]+"Pa");
textMultisensorCo2.setText(mqttString[4]+"ppm");
break;
}
} 
else 
{
//
}
////////********
/*if (convertView == null) 
{
switch (arrayListPattern1.get(position))
{
case 0:
convertView = layoutInflater.inflate(R.layout.temporary_adapter_listview, null);
textView = (TextView)convertView.findViewById(R.id.textView1);
imageView = (ImageView)convertView.findViewById(R.id.imageView1);
//textView.setText(dataString[0]+nominals[0]);
//textView.setText(arrayListPattern.get(position).toString());
//textView.setText(String.valueOf(arrayListPattern.size()));
textView.setText(String.valueOf(position));
imageView.setImageResource(icons[0]);
break;
case 1:
convertView = layoutInflater.inflate(R.layout.temporary_adapter_multisensor, null);
//imageMultisensorLumen = (ImageView)convertView.findViewById(R.id.lumenImageView);
//imageMultisensorTemperature = (ImageView)convertView.findViewById(R.id.temperatureImageView);
//imageMultisensorHumidity = (ImageView)convertView.findViewById(R.id.humidityImageView);
//imageMultisensorPressure = (ImageView)convertView.findViewById(R.id.pressureImageView);
//imageMultisensorCo2 = (ImageView)convertView.findViewById(R.id.co2ImageView);
textMultisensorLumen = (TextView)convertView.findViewById(R.id.lumentTextView);
textMultisensorTemperature = (TextView)convertView.findViewById(R.id.temperatureTextView);
textMultisensorHumidity = (TextView)convertView.findViewById(R.id.humidityTextView);
textMultisensorPressure = (TextView)convertView.findViewById(R.id.pressureTextView);
textMultisensorCo2 = (TextView)convertView.findViewById(R.id.co2TextView);
//imageMultisensorLumen.setImageResource(multisensorIcons[0]);
//imageMultisensorTemperature.setImageResource(multisensorIcons[1]);
//imageMultisensorHumidity.setImageResource(multisensorIcons[2]);
//imageMultisensorPressure.setImageResource(multisensorIcons[3]);
//imageMultisensorCo2.setImageResource(multisensorIcons[4]);
textMultisensorLumen.setText(dataString[0]+"Lm");
textMultisensorTemperature.setText(dataString[1]+"°C");
textMultisensorHumidity.setText(dataString[2]+"%");
textMultisensorPressure.setText(dataString[3]+"Pa");
textMultisensorCo2.setText(dataString[4]+"ppm");
break;
} 
}
else
{
//
}
//////!!!!!!!!!!!!!!!!!///////
*/

/*if (arrayListPattern1.get(position) == 0)
{
//TextView 
textView = (TextView)convertView.findViewById(R.id.textView1);
//imageView = (ImageView)convertView.findViewById(R.id.mytri);
circleView = (MyCircle)convertView.findViewById(R.id.mytri);
//Bitmap icon = BitmapFactory.decodeResource(convertView.getResources(), arrayListImage.get(position));
//imageView.setImageBitmap(icon);
//textView.setText(arrayListString.get(position));
//textView.setText("xxx");
//imageView.setImageResource(arrayListImage.get(position));
circleView.setImageResource(R.drawable.gan);
}*/
/*if (arrayListPattern1.get(position) == 1)
{
textMultisensorLumen = (TextView)convertView.findViewById(R.id.lumentTextView);
textMultisensorTemperature = (TextView)convertView.findViewById(R.id.temperatureTextView);
textMultisensorHumidity = (TextView)convertView.findViewById(R.id.humidityTextView);
textMultisensorPressure = (TextView)convertView.findViewById(R.id.pressureTextView);
textMultisensorCo2 = (TextView)convertView.findViewById(R.id.co2TextView);*/
//textMultisensorLumen.setText(mqttString[0]+"Lm");
//textMultisensorTemperature.setText(mqttString[1]+"°C");
//textMultisensorHumidity.setText(mqttString[2]+"%");
//textMultisensorPressure.setText(mqttString[3]+"Pa");
//textMultisensorCo2.setText(mqttString[4]+"ppm");
//}
//else
//{
//
//}



/*@Override
public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) 
{
if (isChecked)
{}
//
else
{}
}*/





return convertView;
}


/*@Override
public void onCheckedChanged(CompoundButton p1, boolean p2)
{
if (p2) 
{
mqttHelper.Publish("1");
}
else 
{
mqttHelper.Publish("0");
}
}*/

}
