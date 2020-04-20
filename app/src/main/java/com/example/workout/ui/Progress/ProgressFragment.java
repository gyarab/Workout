package com.example.workout.ui.Progress;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.workout.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class ProgressFragment extends Fragment {

    BarChart barChart;
    ArrayList<BarEntry> barEntryArrayList;
    ArrayList<String> labelsNames;
    ArrayList<ProgressData> progressDataArrayList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
       /* galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);*/
        View root = inflater.inflate(R.layout.fragment_graph, container, false);

        Spinner spinner = root.findViewById(R.id.spinner);
        barChart = root.findViewById(R.id.barChart);
        progressDataArrayList.add(new ProgressData("1", "Bench press", 100));
        progressDataArrayList.add(new ProgressData("2", "Bench press", 130));
        barEntryArrayList = new ArrayList<>();
        labelsNames = new ArrayList<>();
        for (int i = 0; i < progressDataArrayList.size(); i++) {
            String date = progressDataArrayList.get(i).getDay();
            Float weight = progressDataArrayList.get(i).getWeight();
            barEntryArrayList.add(new BarEntry(i, weight));
            labelsNames.add(date);
        }
        // chart initialization
        BarDataSet barDataSet = new BarDataSet(barEntryArrayList, "Weight lifted");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labelsNames));
        xAxis.setPosition(XAxis.XAxisPosition.TOP);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(labelsNames.size());
        xAxis.setLabelRotationAngle(270);
        barChart.animateXY(2000,2000, Easing.EaseInSine);
        barChart.setDrawValueAboveBar(true);

        barChart.setDrawBorders(false);
        barChart.invalidate();

        return root;
    }
}

