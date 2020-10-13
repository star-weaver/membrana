package com.starweaver.membrana;

import android.animation.*;
import android.app.*;
import android.hardware.*;
import android.os.*;
import android.support.graphics.drawable.animated.*;
import android.view.*;
import android.view.animation.*;
import android.widget.*;
import com.plattysoft.leonids.*;
import com.plattysoft.leonids.modifiers.*;

import android.support.graphics.drawable.animated.R;

public class FragmentEnter1 extends Fragment implements SensorEventListener, View.OnClickListener
{
private ImplementInRobot listener;
private SensorManager sensorManager;
private Sensor accelerometerSensor;
private Sensor magnetometerSensor;
private TitleString titleString;
private float[] lastAccelerometerSensor = new float[3];
private float[] lastMagnetometerSensor = new float[3];
private boolean lastAccelerometerSensorSet = false;
private boolean lastMagnetometerSensorSet = false;
private boolean astralTextNotSet = true;
private boolean zzzTextNotSet = true;
private boolean astralGateIsOpen = false;
private boolean startPositionWasNotImplemented = true;
private float[] mR = new float[9];
private float[] mOrientation = new float[3];
private float rollInRadians;
private float rollInDegress;
private float rollFloat;

//TextView textView;
Button testButton;

public FragmentEnter1(ImplementInRobot listener)
{
this.listener = listener; 
}

@Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
{
titleString = new TitleString();
View view= inflater.inflate(R.layout.fragment_enter_1, container, false);
sensorManager = (SensorManager)getActivity().getSystemService(getActivity().SENSOR_SERVICE);
accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
magnetometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
//textView = (TextView)view.findViewById(R.id.text_view);
//textView.setText("ROLL"+ rollFloat + startPositionWasNotImplemented);
testButton = (Button)view.findViewById(R.id.button_test_fragment_1);
//testButton.setText("test");
testButton.setBackgroundResource(R.drawable.mandala_button_02);
testButton.setOnClickListener(this);
sendMessageToMqttConnectionManagerService("third_", "eye", "Astral_intrO");
ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(testButton,"rotation",0f,360f);
objectAnimator.setRepeatCount(objectAnimator.INFINITE);
objectAnimator.setRepeatMode(objectAnimator.RESTART);
objectAnimator.setDuration(3000);
objectAnimator.setInterpolator(new LinearInterpolator());
objectAnimator.start();
return view;
}

@Override
public void onClick(View v) 
{
switch (v.getId()) 
{
case R.id.button_test_fragment_1:
if (astralGateIsOpen)
{
sensorManager.unregisterListener(this, accelerometerSensor);
sensorManager.unregisterListener(this, magnetometerSensor);
startPositionWasNotImplemented = false;
//emittingGravity();
emittingTonnel();
testButton.postDelayed(new Runnable() 
{
@Override
public void run() 
{
listener.startImplement();
}
}, 4000);
}
break;
}
}

@Override
public void onPause()
{
super.onPause();
sensorManager.unregisterListener(this, accelerometerSensor);
sensorManager.unregisterListener(this, magnetometerSensor);
}

@Override
public void onResume()
{
super.onResume();
sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_GAME);
sensorManager.registerListener(this, magnetometerSensor, SensorManager.SENSOR_DELAY_GAME);
//textView.setText("ROLL" + rollFloat + startPositionWasNotImplemented);
}

@Override
public void onDestroyView()
{
super.onDestroyView();
sensorManager.unregisterListener(this, accelerometerSensor);
sensorManager.unregisterListener(this, magnetometerSensor);
rollFloat = 88;
startPositionWasNotImplemented = true;
}

@Override
public void onSensorChanged(SensorEvent event) 
{
if (event.sensor == accelerometerSensor) 
{
System.arraycopy(event.values, 0, lastAccelerometerSensor, 0, event.values.length);
lastAccelerometerSensorSet = true;
} 
else if (event.sensor == magnetometerSensor) 
{
System.arraycopy(event.values, 0, lastMagnetometerSensor, 0, event.values.length);
lastMagnetometerSensorSet = true;
}
if (lastAccelerometerSensorSet && lastMagnetometerSensorSet) 
{
SensorManager.getRotationMatrix(mR, null, lastAccelerometerSensor, lastMagnetometerSensor);
SensorManager.getOrientation(mR, mOrientation);
rollInRadians = mOrientation[1];//1.5<=>358.5
rollInDegress = (float)(Math.toDegrees(rollInRadians) + 360) % 360;
rollFloat = (float)Math.round(rollInDegress * 10) / 10;
if (startPositionWasNotImplemented)
{
if (rollFloat < 10 || rollFloat > 350)
{
if (astralTextNotSet) 
{
//testButton.setText("astral");
testButton.setBackgroundResource(R.drawable.mandala_button_01);
astralTextNotSet = false;
zzzTextNotSet = true;
}
astralGateIsOpen = true;
}
else
{
if (zzzTextNotSet)
{
//testButton.setText("zzz");
testButton.setBackgroundResource(R.drawable.mandala_button_02);
zzzTextNotSet = false;
astralTextNotSet = true;
}
astralGateIsOpen = false;
//textView.setText("" + rollFloat + startPositionWasNotImplemented);
}
}
else
{
((MainActivity)getActivity()).onBackPressed();
}
}
}

@Override
public void onAccuracyChanged(Sensor sensor, int accuracy) 
{
// TODO Auto-generated method stub
}

public void sendMessageToMqttConnectionManagerService(String topic, String position, String message)
{
((MainActivity)getActivity()).sendMessageStringToService(topic, position, message);
}

public void emittingTonnel()
{
ParticleSystem ps = new ParticleSystem(this.getActivity(), 100, R.drawable.flare_01, 950);
ps.setScaleRange(0.2f, 0.5f);
ps.setSpeedModuleAndAngleRange(0.3f, 0.3f, 0, 360);
ps.setRotationSpeedRange(40, 70);
ps.setAcceleration(0.00013f, 90);
ps.setFadeOut(200, new AccelerateInterpolator());
ps.emit(getView().findViewById(R.id.emitter), 100, 4000);
}

public void emittingGravity()
{
new ParticleSystem(this.getActivity(), 80, R.drawable.ic_launcher, 3000)
.setSpeedModuleAndAngleRange(0.1f, 0.3f, 180, 270)
.setRotationSpeed(144)
.setAcceleration(0.00017f, 90)
.emitWithGravity(getView().findViewById(R.id.emitter), Gravity.CENTER, 8, 3000);
}

}



