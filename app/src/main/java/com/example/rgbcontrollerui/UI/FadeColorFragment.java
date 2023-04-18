package com.example.rgbcontrollerui.UI;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.transition.TransitionInflater;
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
    RangeSlider fadeSpeedRangeSlider;
    RangeSlider fadeRedSlider;
    RangeSlider fadeGreenSlider;
    RangeSlider fadeBlueSlider;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View fadeColorInflatedView = inflater.inflate(R.layout.fragment_fade_color, container, false);
        fadeSpeedRangeSlider = fadeColorInflatedView.findViewById(R.id.fadeSpeedRangeSlider);
        fadeRedSlider = fadeColorInflatedView.findViewById(R.id.fadeRedBrightnessRangeSlider);
        fadeGreenSlider = fadeColorInflatedView.findViewById(R.id.fadeGreenBrightnessRangeSlider);
        fadeBlueSlider = fadeColorInflatedView.findViewById(R.id.fadeBlueBrightnessRangeSlider);
        fadeRedSlider.setValues(255.0f);
        fadeGreenSlider.setValues(255.0f);
        fadeBlueSlider.setValues(255.0f);

        new Thread(new Main.ClientThread("FADE:" + fadeSpeedRangeSlider.getValues() + " (" + fadeRedSlider.getValues()
                + ", " + fadeGreenSlider.getValues() + ", " + fadeBlueSlider.getValues() + ")")).start();

        fadeSpeedRangeSlider.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                // Use the value
                fadeSpeedRangeSlider.setValues(value);
                new Thread(new Main.ClientThread("FADE:" + fadeSpeedRangeSlider.getValues() + " (" + fadeRedSlider.getValues()
                        + ", " + fadeGreenSlider.getValues() + ", " + fadeBlueSlider.getValues() + ")")).start();
            }
        });
        fadeRedSlider.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                // Use the value
                fadeRedSlider.setValues(value);
                new Thread(new Main.ClientThread("FADE:" + fadeSpeedRangeSlider.getValues() + " (" + fadeRedSlider.getValues()
                        + ", " + fadeGreenSlider.getValues() + ", " + fadeBlueSlider.getValues() + ")")).start();
            }
        });
        fadeGreenSlider.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                // Use the value
                fadeGreenSlider.setValues(value);
                new Thread(new Main.ClientThread("FADE:" + fadeSpeedRangeSlider.getValues() + " (" + fadeRedSlider.getValues()
                        + ", " + fadeGreenSlider.getValues() + ", " + fadeBlueSlider.getValues() + ")")).start();
            }
        });
        fadeBlueSlider.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                // Use the value
                fadeBlueSlider.setValues(value);
                new Thread(new Main.ClientThread("FADE:" + fadeSpeedRangeSlider.getValues() + " (" + fadeRedSlider.getValues()
                        + ", " + fadeGreenSlider.getValues() + ", " + fadeBlueSlider.getValues() + ")")).start();
            }
        });

        TransitionInflater transitionInflater = TransitionInflater.from(requireContext());
        setEnterTransition(transitionInflater.inflateTransition(R.transition.fade));
        // Inflate the layout for this fragment
        return fadeColorInflatedView;
    }
}