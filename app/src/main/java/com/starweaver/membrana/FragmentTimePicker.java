package com.mycompany.myapp81;

import android.app.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import java.util.*;

public class FragmentTimePicker extends Fragment
{
private TitleString titleString;
private TimePicker timePicker;
private Calendar calendar;
private TextView testTextView;
private Button button;

int position;
private int globalPosition;
private int globalButtonStateInt;

String testString0;
String testString1;

private boolean timePeriodState = false;

FragmentTimePicker(int globalPosition, int globalButtonState, int position)
{
this.globalPosition = globalPosition;
this.globalButtonStateInt = globalButtonState;
this.position = position;
}

@Override public void onCreate(Bundle savedInstanceState) 
{
super.onCreate(savedInstanceState);
}

@Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
{
View fragmentTimePicker = inflater.inflate(R.layout.fragment_time_picker, container, false);
timePicker = (TimePicker)fragmentTimePicker.findViewById(R.id.time_picker);
testTextView = (TextView)fragmentTimePicker.findViewById(R.id.text_view);
button = (Button)fragmentTimePicker.findViewById(R.id.button);
calendar = Calendar.getInstance();
timePicker.setIs24HourView(true);
//timePicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
//timePicker.setCurrentMinute(calendar.get(Calendar.MINUTE));
timePicker.setCurrentHour(titleString.timerDataArrayList.get(globalButtonStateInt).get(globalPosition).get(position).get(0));
timePicker.setCurrentMinute(titleString.timerDataArrayList.get(globalButtonStateInt).get(globalPosition).get(position).get(1));
testTextView.setText("SET START TIME");
button.setText("SET");

OnClickListener listener = new OnClickListener() 
{
@Override
public void onClick(View v) 
{
if (!timePeriodState )
{
//titleString.setTimerData(globalButtonStateInt, globalPosition, 0, timePicker.getCurrentHour());
//titleString.setTimerData(globalButtonStateInt, globalPosition, 1, timePicker.getCurrentMinute());
//testTextView.setText("start:"+timePicker.getHour()+" : "+timePicker.getCurrentMinute());
//timePeriodState = true;
//testTextView.setText("globalButtonStateInt: "+globalButtonStateInt+" globalPosition: "+globalPosition+" getCurrentHour: "+timePicker.getCurrentHour());
//testTextView.setText("globalButtonStateInt: "+globalButtonStateInt+" globalPosition: "+globalPosition+" TimerData at position 0: "+titleString.getTimerData(globalButtonStateInt,globalPosition,0,timePicker.getCurrentHour()));
//testTextView.setText("TimerData at position 0: "+titleString.getTimerData(0,0,0));
/*for (int i=0;i<=titleString.timerDataArrayList.size()-1;i++)
{
for (int j=0;j<=titleString.timerDataArrayList.get(i).size()-1;j++)
{
//testString0=("el"+i+":"+"size:"+titleString.timerDataArrayList.get(i).size()+"xx="+titleString.timerDataArrayList.get(i).get(j).get(0));
testString0=("gBSI: "+globalButtonStateInt+" gPn: "+globalPosition+" i="+i+" j="+j+" [0]="+titleString.timerDataArrayList.get(i).get(j).get(0));
testString1=testString1+testString0;
}
}*/
//testTextView.setText(testString1);
//testString0=("gBSI: "+globalButtonStateInt+" gPn: "+globalPosition+" [0]="+titleString.timerDataArrayList.get(globalButtonStateInt).get(globalPosition).get(0));
//testTextView.setText(testString0);
//testTextView.setText("gBSI: "+globalButtonStateInt+" gPn: "+globalPosition+" [0]="+titleString.timerDataArrayList.get(globalButtonStateInt).get(globalPosition).get(0));
//testTextView.setText(""+titleString.getTimerData(globalButtonStateInt,globalPosition,0));
titleString.timerDataArrayList.get(globalButtonStateInt).get(globalPosition).get(position).set(0,timePicker.getCurrentHour());
titleString.timerDataArrayList.get(globalButtonStateInt).get(globalPosition).get(position).set(1,timePicker.getCurrentMinute());
testTextView.setText("SET END TIME");
timePeriodState = true;
}
else
{
//titleString.setTimerData(globalButtonStateInt, globalPosition, 2, timePicker.getCurrentHour());
//titleString.setTimerData(globalButtonStateInt, globalPosition, 3, timePicker.getCurrentMinute());
titleString.timerDataArrayList.get(globalButtonStateInt).get(globalPosition).get(position).set(2,timePicker.getCurrentHour());
titleString.timerDataArrayList.get(globalButtonStateInt).get(globalPosition).get(position).set(3,timePicker.getCurrentMinute());
((MainActivity)getActivity()).onBackPressed();
//testTextView.setText("end:"+timePicker.getHour()+" : "+timePicker.getCurrentMinute());
}
}
};
button.setOnClickListener(listener);

return fragmentTimePicker;
}

@Override
public void onDestroyView()
{
super.onDestroyView();
timePeriodState = false;
}

}
