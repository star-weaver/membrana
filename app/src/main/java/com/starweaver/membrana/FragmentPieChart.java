package com.starweaver.membrana;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import com.github.mikephil.charting.charts.*;
import com.github.mikephil.charting.data.*;
import com.github.mikephil.charting.utils.*;
import java.util.*;

//PieChart:
//https://javapapers.com/android/android-chart-example-app-using-mpandroidchart/
//https://www.ssaurel.com/blog/learn-to-create-a-pie-chart-in-android-with-mpandroidchart/
//https://www.android-examples.com/pie-chart-graph-android-app-using-mpandroidchart/
//https://android-pratap.blogspot.com/2016/04/create-piechart-using-mpandroidchart.html?m=1

public class FragmentPieChart extends Fragment 
{
PieChart pieChart;
ArrayList<Entry> entries;
ArrayList<String> pieEntryLabels;
PieDataSet pieDataSet;
PieData pieData;

@Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
{
View viewPieChart= inflater.inflate(R.layout.fragment_pie_chart, container, false);
Button Button1 = (Button) viewPieChart.findViewById(R.id.chart_btn0);
Button angryButton = (Button) viewPieChart.findViewById(R.id.chart_btn1);
Button angryButton2 = (Button) viewPieChart.findViewById(R.id.chart_btn2);
Button angryButton3 = (Button) viewPieChart.findViewById(R.id.chart_btn3);
Button angryButton4 = (Button) viewPieChart.findViewById(R.id.chart_btn4);

pieChart = (PieChart) viewPieChart.findViewById(R.id.pie_chart);
entries = new ArrayList<>();
pieEntryLabels = new ArrayList<String>();
addValuesToPie();
addLabelsToPie();
pieDataSet = new PieDataSet(entries, "");
pieData = new PieData(pieEntryLabels, pieDataSet);
pieDataSet.setColors(ColorTemplate.LIBERTY_COLORS);
pieChart.setData(pieData);
pieDataSet.setValueTextSize(10f);
pieChart.animateY(1000);
/*
// configure pie chart
pieChart.setUsePercentValues(true);
pieChart.setDescription("Smartphones Market Share");
// enable hole and configure
pieChart.setDrawHoleEnabled(true);
pieChart.setHoleColorTransparent(true);
pieChart.setHoleRadius(7);
pieChart.setTransparentCircleRadius(10);
// enable rotation of the chart by touch
pieChart.setRotationAngle(0);
pieChart.setRotationEnabled(true);
*/
return viewPieChart;
}

public void addValuesToPie()
{
entries.add(new BarEntry(2f, 0));
entries.add(new BarEntry(4f, 1));
entries.add(new BarEntry(6f, 2));
entries.add(new BarEntry(8f, 3));
entries.add(new BarEntry(7f, 4));
entries.add(new BarEntry(3f, 5));
}

public void addLabelsToPie()
{
pieEntryLabels.add("January");
pieEntryLabels.add("February");
pieEntryLabels.add("March");
pieEntryLabels.add("April");
pieEntryLabels.add("May");
pieEntryLabels.add("June");
}

}
