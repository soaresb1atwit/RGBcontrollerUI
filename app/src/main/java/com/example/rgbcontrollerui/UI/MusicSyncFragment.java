package com.example.rgbcontrollerui.UI;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
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

import com.example.rgbcontrollerui.Adapters.SongAdapter;
import com.example.rgbcontrollerui.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class MusicSyncFragment extends Fragment {

    ImageView albumCover;
    private Toolbar toolbar;
    private View musicSyncInflatedView;
    SeekBar seekBar;
    FloatingActionButton fab;
    String url = "https://dns4.vippendu.com/download/128k-dmmok/Rabb-Karke.mp3";
    int position;
    Thread updateSeekBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        musicSyncInflatedView = inflater.inflate(R.layout.fragment_music_sync, container, false);
        albumCover = musicSyncInflatedView.findViewById(R.id.albumCoverImgView);
        seekBar = musicSyncInflatedView.findViewById(R.id.trackSeekBar);
        fab = musicSyncInflatedView.findViewById(R.id.musicLibraryFab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MusicActivity.class);
                startActivity(intent);
            }
        });
        return musicSyncInflatedView;
    }

}