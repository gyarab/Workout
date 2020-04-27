package com.example.Workout.ui.Customization;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.Workout.DBHandler;
import com.example.Workout.R;


public class CustomizationFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_customization, container, false);
        Button check_button = root.findViewById(R.id.check_button);

        check_button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ShowToast")
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Check button clicked", Toast.LENGTH_LONG);
                EditText program_name = root.findViewById(R.id.customization_name);
                EditText program_weeks = root.findViewById(R.id.customization_weeks);
                EditText program_days = root.findViewById(R.id.customization_days);
                RadioGroup radioGroup = root.findViewById(R.id.radioGroup);

                RadioButton radioButton = root.findViewById(radioGroup.getCheckedRadioButtonId());
                String name = program_name.getText().toString();
                String weeks = program_weeks.getText().toString();
                String days = program_days.getText().toString();

                DBHandler dbHandler = new DBHandler(getContext());
                if (radioGroup.getCheckedRadioButtonId() == -1 | name.equals("") | days.equals("") | weeks.equals("")) {
                    Toast.makeText(getContext(), "Please fill parameters correctly", Toast.LENGTH_LONG);
                } else {
                    String type = radioButton.getText().toString();
                    Bundle bundle = new Bundle();
                    bundle.putString("name", name); // Put anything what you want
                    bundle.putString("weeks", weeks);
                    bundle.putString("days",days);
                    bundle.putString("type",type);
                    dbHandler.updateDate(1,1);
                    CustomizationFragment_2 fragment2 = new CustomizationFragment_2();
                    fragment2.setArguments(bundle);
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.nav_host_fragment, fragment2, "tag").addToBackStack(null)
                            .commit();
                }
            }
        });
        Button x_button = root.findViewById(R.id.x_button);
        x_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return root;
    }
}
