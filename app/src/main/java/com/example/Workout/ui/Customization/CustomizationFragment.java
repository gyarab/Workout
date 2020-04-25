package com.example.Workout.ui.Customization;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.Workout.R;


public class CustomizationFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_customization, container, false);
        Button check_button = root.findViewById(R.id.check_button);
        check_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Check button clicked",Toast.LENGTH_SHORT);
            }
        });
        Button x_button = root.findViewById(R.id.x_button);
        x_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"X button clicked", Toast.LENGTH_SHORT);
            }
        });
        return root;
    }
}
