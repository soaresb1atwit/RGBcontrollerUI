package com.example.rgbcontrollerui.UI;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.example.rgbcontrollerui.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.slider.RangeSlider;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class MusicSyncFragment extends Fragment {
    RangeSlider sensitivitiySlider;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View musicReactInflatedView = inflater.inflate(R.layout.fragment_music_sync, container, false);
        sensitivitiySlider = musicReactInflatedView.findViewById(R.id.sensitivityRangeSlider);
        sensitivitiySlider.setValues(5.0f);
        // Inflate the layout for this fragment

        TransitionInflater transitionInflater = TransitionInflater.from(requireContext());
        setEnterTransition(transitionInflater.inflateTransition(R.transition.slide_right));
//        setExitTransition(transitionInflater.inflateTransition(R.transition.fade));

        return musicReactInflatedView;
    }

}