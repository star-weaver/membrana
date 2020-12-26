package com.starweaver.membrana;

import android.content.*;
import android.graphics.*;
import android.support.annotation.*;
import android.support.v7.recyclerview.*;
import android.support.v7.widget.*;
import android.view.*;
import android.widget.*;
import java.util.*;
import android.support.v7.recyclerview.R;

public class CustomRecyclerViewAdapter extends RecyclerView.Adapter<CustomRecyclerViewAdapter.ViewHolder>
{
private List<Integer> colorList;
private List<String> textList;
private LayoutInflater mInflater;
private ItemClickListener mClickListener;

// data is passed into the constructor
CustomRecyclerViewAdapter(Context context, List<Integer> colors, List<String> text) 
{
this.mInflater = LayoutInflater.from(context);
this.colorList = colors;
this.textList = text;
}

// inflates the row layout from xml when needed
@Override
@NonNull
public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) 
{
View view = mInflater.inflate(R.layout.recyclerview_item, parent, false);
return new ViewHolder(view);
}

// binds the data to the view and textview in each row
@Override
public void onBindViewHolder(@NonNull ViewHolder holder, int position) 
{
String animal = textList.get(position);
//holder.myView.setBackgroundColor(color);
holder.circleImageView.setImageResource(colorList.get(position));
//holder.imageView.setImageResource(colorList.get(position));
holder.myTextView.setText(animal);
}

// total number of rows
@Override
public int getItemCount() 
{
return textList.size();
}

// stores and recycles views as they are scrolled off screen
public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener 
{
CircleImageView circleImageView;
//ImageView imageView;
TextView myTextView;

ViewHolder(View itemView) 
{
super(itemView);
circleImageView = itemView.findViewById(R.id.circle_color_view);
//imageView = itemView.findViewById(R.id.image_view);
myTextView = itemView.findViewById(R.id.tvAnimalName);
itemView.setOnClickListener(this);
}

@Override
public void onClick(View view) 
{
if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
}
}

// convenience method for getting data at click position
public String getItem(int id) 
{
return textList.get(id);
}

// allows clicks events to be caught
public void setClickListener(ItemClickListener itemClickListener) 
{
this.mClickListener = itemClickListener;
}

// parent activity will implement this method to respond to click events
public interface ItemClickListener 
{
void onItemClick(View view, int position);
}
}
