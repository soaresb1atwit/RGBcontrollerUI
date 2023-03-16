package com.example.rgbcontrollerui.UI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rgbcontrollerui.R;
import com.google.android.material.slider.RangeSlider;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class CycleColorFragment extends Fragment {
    RangeSlider cycleRedSlider;
    RangeSlider cycleGreenSlider;
    RangeSlider cycleBlueSlider;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View cycleColorInflatedView = inflater.inflate(R.layout.fragment_cycle_color, container, false);
        cycleRedSlider = cycleColorInflatedView.findViewById(R.id.cycleRedBrightnessRangeSlider);
        cycleGreenSlider = cycleColorInflatedView.findViewById(R.id.cycleGreenBrightnessRangeSlider);
        cycleBlueSlider = cycleColorInflatedView.findViewById(R.id.cycleBlueBrightnessRangeSlider);
        cycleRedSlider.setValues(255.0f);
        cycleGreenSlider.setValues(255.0f);
        cycleBlueSlider.setValues(255.0f);
        // Inflate the layout for this fragment
        return cycleColorInflatedView;
    }
}