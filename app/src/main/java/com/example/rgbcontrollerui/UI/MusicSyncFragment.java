package com.example.rgbcontrollerui.UI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rgbcontrollerui.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MusicSyncFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MusicSyncFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_music_sync, container, false);
    }
}