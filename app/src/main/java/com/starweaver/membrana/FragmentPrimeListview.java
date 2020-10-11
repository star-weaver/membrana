package com.mycompany.myapp81;

import android.*;
import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import java.util.*;

public class FragmentPrimeListview extends Fragment 
{
TitleString titleString;
TitleIcon titleIcon;
ListView lv;
FragmentSecondListview fragsecond;
FragmentVideo fragmentVideo;
FragmentDevices fragmentDevices;
FragmentTransaction fTrans;
ArrayList<Integer> draws0 = new ArrayList<Integer>(Arrays.asList(R.drawable.black, R.drawable.sand));
ArrayList<String> textlist0 = new ArrayList<String>(Arrays.asList("Pool", "Yard"));
int stringArrayNumber;
int iconArrayNumber;
Activity mainActivity;

public FragmentPrimeListview(int is, int ii)
{
stringArrayNumber = is;
iconArrayNumber = ii;
}

@Override 
public void onCreate(Bundle savedInstanceState) 
{
super.onCreate(savedInstanceState);
setRetainInstance(true);
}

@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
{
titleString = new TitleString();
titleIcon = new TitleIcon();

View view_lv=inflater.inflate(R.layout.fragment_lv, container, false);
lv = (ListView)view_lv.findViewById(R.id.mobile_list);
lv.setAdapter(new ListViewAdapter(this, titleString.getListviewArraylist(stringArrayNumber), titleIcon.getListviewArraylist(iconArrayNumber)));

lv.setOnItemClickListener(new AdapterView.OnItemClickListener() 
{
@Override 
public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
{
titleString.setSecondListViewPosition(position);
((MainActivity)getActivity()).switchToChildNode(position);
if (position == 0)
{
titleString.Up();
//because node nav//((MainActivity)getActivity()).mTitleTextView.setText(titleString.getTitleList(0));
titleIcon.Up();
//because node nav//((MainActivity)getActivity()).fragmentMain.setIcon(titleIcon.getIconsList(0), R.drawable.sand);
//setMqttString(0);
//((MainActivity)getActivity()).setBottomNavigationView(0);
fragsecond = new FragmentSecondListview(1, 1, 0);
fTrans = getFragmentManager().beginTransaction();
fTrans.replace(R.id.frgmCont2, fragsecond);
fTrans.addToBackStack(null);
fTrans.commit();
}
else if (position == 1)
{
titleString.Up();
//because node nav//((MainActivity)getActivity()).mTitleTextView.setText(titleString.getTitleList(1));
titleIcon.Up();
//because node nav//((MainActivity)getActivity()).fragmentMain.setIcon(titleIcon.getIconsList(1), R.drawable.sand);
//setMqttString(1);
//((MainActivity)getActivity()).setBottomNavigationView(0);
fragsecond = new FragmentSecondListview(2, 2, 1);
fTrans = getFragmentManager().beginTransaction();
fTrans.replace(R.id.frgmCont2, fragsecond);
fTrans.addToBackStack(null);
fTrans.commit();
}
else if (position == 2)
{
titleString.Up();
//because node nav//((MainActivity)getActivity()).mTitleTextView.setText(titleString.getTitleList(2));
titleString.setGlobalTitleFirst(position);
titleIcon.Up();
//because node nav//((MainActivity)getActivity()).fragmentMain.setIcon(titleIcon.getIconsList(2), R.drawable.sand);
FragmentMjpgStream fragmentMjpgStream = new FragmentMjpgStream();
fTrans = getFragmentManager().beginTransaction();
fTrans.add(R.id.frgmCont, fragmentMjpgStream);
fTrans.addToBackStack(null);
fTrans.replace(R.id.frgmCont2, ((MainActivity)getActivity()).fragmentList.get(0));
fTrans.addToBackStack(null);
fTrans.commit();
}
}
});
return view_lv;
}

public ListView getlv()//is need?
{
return lv;
}

}
