package com.mycompany.myapp81;

import android.app.*;
import android.graphics.*;
import android.os.*;
import android.support.v7.widget.*;
import android.view.*;
import android.widget.*;
import java.util.*;

public class FragmentRGBcolor extends Fragment implements CustomRecyclerViewAdapter.ItemClickListener
{
TitleString titleString;
//MqttConnectionManagerService mqttConnectionManagerService;
private CustomRecyclerViewAdapter customRecyclerViewAdapter;
static ArrayList<Integer> arrayListImage = new ArrayList<Integer>(Arrays.asList(R.drawable.color_red, R.drawable.color_orange, R.drawable.color_yellow, R.drawable.color_green, R.drawable.color_blue, R.drawable.color_violet, R.drawable.color_warm_white, R.drawable.color_middle_white, R.drawable.color_cold_white));
static ArrayList<String> arrayListString = new ArrayList<String>(Arrays.asList("Red","Orange","Yellow","Green","Blue","Violet","Warm","Balanced","Cold"));
private int buttonStateInt;
private int toggleButtonPosition;

public FragmentRGBcolor(int buttonStateInt)
{
this.buttonStateInt = buttonStateInt;
}

@Override public void onCreate(Bundle savedInstanceState) 
{
super.onCreate(savedInstanceState);
titleString = new TitleString();
}

@Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
{
View fragmentRgbColorView = inflater.inflate(R.layout.fragment_rgb_color, container, false);
//mqttConnectionManagerService = new MqttConnectionManagerService();
// set up the RecyclerView
RecyclerView recyclerView = fragmentRgbColorView.findViewById(R.id.rvAnimals);
GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false);
recyclerView.setLayoutManager(gridLayoutManager);
customRecyclerViewAdapter = new CustomRecyclerViewAdapter(getActivity(), arrayListImage, arrayListString);
customRecyclerViewAdapter.setClickListener(this);
recyclerView.setAdapter(customRecyclerViewAdapter);
return fragmentRgbColorView;
}

@Override
public void onDestroyView()
{
super.onDestroyView();
}

@Override
public void onItemClick(View view, int position) 
{
//Toast.makeText(((MainActivity)getActivity()), "Color Number: " + customRecyclerViewAdapter.getItem(position) + " on item position " + position, Toast.LENGTH_SHORT).show();
//Toast.makeText(((MainActivity)getActivity()), "Color Number: " + position, Toast.LENGTH_SHORT).show();
titleString.setBulbInt(position);
((MainActivity)getActivity()).sendMessageStringPublishLightToService(titleString.getBulbInt());
//if (multisensorAvaiable)
//{
//((MainActivity)getActivity()).setTestTextView(String.valueOf(position));
titleString.setToggleButtonPattern(buttonStateInt, toggleButtonPosition, true);
//titleString.setToggleButtonPattern(0, position - 1, true);
//}
//else
///{
//titleString.setToggleButtonPattern(buttonStateInt, position, true);
//}
((MainActivity)getActivity()).onBackPressed();
}

public void setToggleButtonPosition(int position)
{
toggleButtonPosition = position;
}

}
