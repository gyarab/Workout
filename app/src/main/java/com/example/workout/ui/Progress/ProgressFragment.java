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
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class ProgressFragment extends Fragment {

    LineChart lineChart;
    ArrayList<Entry> lineEntryArrayList;
    ArrayList<String> labelsNames;
    ArrayList<ProgressData> progressDataArrayList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
       /* galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);*/
        View root = inflater.inflate(R.layout.fragment_graph, container, false);

        Spinner spinner = root.findViewById(R.id.spinner);
        lineChart = root.findViewById(R.id.barChart);
        progressDataArrayList.add(new ProgressData("1", "Bench press", 100));
        progressDataArrayList.add(new ProgressData("2", "Bench press", 130));
        progressDataArrayList.add(new ProgressData("3", "Bench press", (float) 132.5));
        progressDataArrayList.add(new ProgressData("4", "Bench press", 135));
        progressDataArrayList.add(new ProgressData("5", "Bench press", 140));
        progressDataArrayList.add(new ProgressData("6", "Bench press", 145));

        lineEntryArrayList = new ArrayList<>();
        labelsNames = new ArrayList<>();
        for (int i = 0; i < progressDataArrayList.size(); i++) {
            String date = progressDataArrayList.get(i).getDay();
            Float weight = progressDataArrayList.get(i).getWeight();
            lineEntryArrayList.add(new BarEntry(i, weight));
            labelsNames.add(date);
        }
        // chart initialization
        LineDataSet lineDataSet = new LineDataSet(lineEntryArrayList, "Weight lifted");
        lineDataSet.setDrawFilled(true);
        lineDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        LineData lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labelsNames));
        xAxis.setPosition(XAxis.XAxisPosition.TOP);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setGranularity(3f);
        xAxis.setLabelCount(labelsNames.size());
        xAxis.setLabelRotationAngle(270);
        lineChart.animateXY(2000,2000, Easing.EaseInSine);

        lineChart.setDrawBorders(false);
        lineChart.invalidate();

        return root;
    }
}

