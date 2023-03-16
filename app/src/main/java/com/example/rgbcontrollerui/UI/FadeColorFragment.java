package com.example.rgbcontrollerui.UI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
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
public class FadeColorFragment extends Fragment {
    RangeSlider fadeRedSlider;
    RangeSlider fadeGreenSlider;
    RangeSlider fadeBlueSlider;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View fadeColorInflatedView = inflater.inflate(R.layout.fragment_fade_color, container, false);
        fadeRedSlider = fadeColorInflatedView.findViewById(R.id.fadeRedBrightnessRangeSlider);
        fadeGreenSlider = fadeColorInflatedView.findViewById(R.id.fadeGreenBrightnessRangeSlider);
        fadeBlueSlider = fadeColorInflatedView.findViewById(R.id.fadeBlueBrightnessRangeSlider);
        fadeRedSlider.setValues(255.0f);
        fadeGreenSlider.setValues(255.0f);
        fadeBlueSlider.setValues(255.0f);
        // Inflate the layout for this fragment
        return fadeColorInflatedView;
    }
}