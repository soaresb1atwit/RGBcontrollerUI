package com.example.rgbcontrollerui.UI;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rgbcontrollerui.R;
import com.google.android.material.slider.RangeSlider;
import com.google.android.material.slider.Slider;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class CycleColorFragment extends Fragment {
    RangeSlider cycleSpeedRangeSlider;
    RangeSlider cycleRedSlider;
    RangeSlider cycleGreenSlider;
    RangeSlider cycleBlueSlider;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View cycleColorInflatedView = inflater.inflate(R.layout.fragment_cycle_color, container, false);
        cycleSpeedRangeSlider = cycleColorInflatedView.findViewById(R.id.cycleSpeedRangeSlider);
        cycleRedSlider = cycleColorInflatedView.findViewById(R.id.cycleRedBrightnessRangeSlider);
        cycleGreenSlider = cycleColorInflatedView.findViewById(R.id.cycleGreenBrightnessRangeSlider);
        cycleBlueSlider = cycleColorInflatedView.findViewById(R.id.cycleBlueBrightnessRangeSlider);
        cycleRedSlider.setValues(255.0f);
        cycleGreenSlider.setValues(255.0f);
        cycleBlueSlider.setValues(255.0f);

        new Thread(new Main.ClientThread("CYCLE:" + cycleSpeedRangeSlider.getValues() + " (" + cycleRedSlider.getValues()
                + ", " + cycleGreenSlider.getValues() + ", " + cycleBlueSlider.getValues() + ")")).start();

        cycleSpeedRangeSlider.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                // Use the value
                cycleSpeedRangeSlider.setValues(value);
                new Thread(new Main.ClientThread("CYCLE:" + cycleSpeedRangeSlider.getValues() + " (" + cycleRedSlider.getValues()
                        + ", " + cycleGreenSlider.getValues() + ", " + cycleBlueSlider.getValues() + ")")).start();
            }
        });
        cycleRedSlider.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                // Use the value
                cycleRedSlider.setValues(value);
                new Thread(new Main.ClientThread("CYCLE:" + cycleSpeedRangeSlider.getValues() + " (" + cycleRedSlider.getValues()
                        + ", " + cycleGreenSlider.getValues() + ", " + cycleBlueSlider.getValues() + ")")).start();
            }
        });
        cycleGreenSlider.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                // Use the value
                cycleGreenSlider.setValues(value);
                new Thread(new Main.ClientThread("CYCLE:" + cycleSpeedRangeSlider.getValues() + " (" + cycleRedSlider.getValues()
                        + ", " + cycleGreenSlider.getValues() + ", " + cycleBlueSlider.getValues() + ")")).start();
            }
        });
        cycleBlueSlider.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                // Use the value
                cycleBlueSlider.setValues(value);
                new Thread(new Main.ClientThread("CYCLE:" + cycleSpeedRangeSlider.getValues() + " (" + cycleRedSlider.getValues()
                        + ", " + cycleGreenSlider.getValues() + ", " + cycleBlueSlider.getValues() + ")")).start();
            }
        });

        TransitionInflater transitionInflater = TransitionInflater.from(requireContext());
        setEnterTransition(transitionInflater.inflateTransition(R.transition.fade));
        // Inflate the layout for this fragment
        return cycleColorInflatedView;
    }
}