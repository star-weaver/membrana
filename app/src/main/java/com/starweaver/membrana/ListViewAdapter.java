package com.starweaver.membrana;

import android.app.*;
import android.content.*;
import android.graphics.*;
import android.view.*;
import android.widget.*;
import java.util.*;

public class ListViewAdapter extends BaseAdapter
{
private final Context mContext;
static ArrayList<String> arrayListString = new ArrayList<String>();
static ArrayList<Integer> arrayListImage = new ArrayList<Integer>();

public ListViewAdapter(Fragment fragment, ArrayList<String> arrs, ArrayList<Integer> arri)
{
mContext = fragment.getActivity();
arrayListString.clear();
arrayListImage.clear();
arrayListString = arrs;
arrayListImage = arri;
}

public int getCount() 
{
return arrayListImage.size();
}

public Object getItem(int position) 
{
return null;
}

public long getItemId(int position)
{
return 0;
}

// create a new ImageView for each item referenced by the Adapter
public View getView(int position, View convertView, ViewGroup parent) 
{
if (convertView == null) 
{
final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
convertView = layoutInflater.inflate(R.layout.programlist2, null);
} 
else 
{
//
}
TextView textView = (TextView)convertView.findViewById(R.id.textView1);
CircleImageView imageView = (CircleImageView)convertView.findViewById(R.id.mytri);
Bitmap icon = BitmapFactory.decodeResource(convertView.getResources(), arrayListImage.get(position));
imageView.setImageBitmap(icon);
textView.setText(arrayListString.get(position));
imageView.setImageResource(arrayListImage.get(position));
return convertView;
}
}
