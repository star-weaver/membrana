package com.mycompany.myapp81;

//https://androidexample365.com/a-collection-of-nice-loading-animations-for-android/amp/
//https://www.tutorialspoint.com/android/android_loading_spinner.htm

import android.app.*;
import android.os.*;
import android.support.v7.widget.*;
import android.view.*;
import com.mycompany.myapp81.*;

public class FragmentLoading extends Fragment
{

@Override public void onCreate(Bundle savedInstanceState) 
{
super.onCreate(savedInstanceState);
}

@Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
{
View fragmentLoading = inflater.inflate(R.layout.fragment_loading, container, false);
//getActivity().getWindow().setBackgroundDrawableResource(R.color.magic_magenta);
return fragmentLoading;
}

@Override
public void onDestroyView()
{
super.onDestroyView();
//getActivity().getWindow().setBackgroundDrawableResource(R.color.bottom_navigation_item);
}

}
