package com.mycompany.myapp81;

import android.app.*;
import android.graphics.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import com.github.mikephil.charting.charts.*;
import com.github.mikephil.charting.components.*;
import com.github.mikephil.charting.data.*;
import java.util.*;

public class FragmentLineChart extends Fragment 
{
ArrayList<Float> lineChartArrayList;
ArrayList<Entry> entries;

public FragmentLineChart(ArrayList<Float> inputArrayList)
{
lineChartArrayList = inputArrayList;
}

@Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
{
View view2= inflater.inflate(R.layout.fragment_line_chart, container, false);
Button Button1 = (Button) view2.findViewById(R.id.chart_btn0);
Button angryButton = (Button) view2.findViewById(R.id.chart_btn1);
Button angryButton2 = (Button) view2.findViewById(R.id.chart_btn2);
Button angryButton3 = (Button) view2.findViewById(R.id.chart_btn3);
Button angryButton4 = (Button) view2.findViewById(R.id.chart_btn4);

Button daraButton0 = (Button) view2.findViewById(R.id.data_btn0);
Button dataButton1 = (Button) view2.findViewById(R.id.data_btn1);
//Button dataButton2 = (Button) view2.findViewById(R.id.data_btn2);
//Button dataButton3 = (Button) view2.findViewById(R.id.data_btn3);
//Button dataButton4 = (Button) view2.findViewById(R.id.data_btn4);

// in this example, a LineChart is initialized from xml
LineChart lineChart = (LineChart) view2.findViewById(R.id.chart);

XAxis xAxis = lineChart.getXAxis();
xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
xAxis.setTextSize(20f);
//xAxis.setLabelsToSkip(1);//skip labels periodically
xAxis.setTextColor(Color.GRAY);
xAxis.setDrawAxisLine(true);
xAxis.setDrawGridLines(true);

YAxis yAxis = lineChart.getAxisRight();
yAxis.setTextSize(20f);
yAxis.setTextColor(Color.GRAY);
yAxis.setDrawAxisLine(true);
yAxis.setDrawGridLines(false);

lineChart.getAxisLeft().setEnabled(false); // no left axis

/*ArrayList<Entry> entries = new ArrayList<>();
entries.add(new Entry(4f, 0));
entries.add(new Entry(8f, 1));
entries.add(new Entry(6f, 2));
entries.add(new Entry(2f, 3));
entries.add(new Entry(9f, 4));
entries.add(new Entry(10f,5));
entries.add(new Entry(3f, 6));
entries.add(new Entry(7f, 7));
entries.add(new Entry(12f, 8));
entries.add(new Entry(3f, 9));
entries.add(new Entry(4f, 10));
entries.add(new Entry(10f,11));
entries.add(new Entry(8f, 12));
entries.add(new Entry(1f, 13));
entries.add(new Entry(3f, 14));
entries.add(new Entry(12f,15));
entries.add(new Entry(5f, 16));
entries.add(new Entry(11f,17));
entries.add(new Entry(9f, 18));
entries.add(new Entry(8f, 19));
entries.add(new Entry(2f, 20));
entries.add(new Entry(4f, 21));
entries.add(new Entry(6f, 22));
entries.add(new Entry(10f, 23));*/

entries = new ArrayList<>();
for (int i=0; i < lineChartArrayList.size(); i++)
{
entries.add(new Entry(lineChartArrayList.get(i), i));
}

LineDataSet lineDataSet0 = new LineDataSet(entries, "Days in a month");

lineDataSet0.setLineWidth(2);
lineDataSet0.setDrawValues(false);//values on line
lineDataSet0.setDrawCircles(false);//circles on line
lineDataSet0.setColor(getResources().getColor(R.color.chart_color));

ArrayList<String> labels = new ArrayList<String>();
for (int i=0;i<lineChartArrayList.size();i++)
{
labels.add(""+i);
}

LineData lineData = new LineData(labels);
lineData.addDataSet(lineDataSet0);
lineChart.setData(lineData);
lineChart.setDrawGridBackground(false);
lineChart.setDrawBorders(true);
lineChart.setBorderColor(Color.GRAY);
lineChart.setDescription(" ");
lineChart.animateX(800);
//lineChart.invalidate();
Legend legend = lineChart.getLegend();
legend.setEnabled(false);//need legend?

return view2;
}
}
