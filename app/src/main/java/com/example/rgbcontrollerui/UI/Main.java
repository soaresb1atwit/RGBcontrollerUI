package com.example.rgbcontrollerui.UI;

import android.Manifest;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//import com.espressif.provisioning.ESPProvisionManager;
import com.example.rgbcontrollerui.Adapters.SongAdapter;
import com.example.rgbcontrollerui.R;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarItemView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.slider.RangeSlider;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Main extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView toolbarTitle;
    BottomNavigationView bottomNavigationView;
    CustomColorFragment customColorFragment = new CustomColorFragment();
    FadeColorFragment fadeColorFragment = new FadeColorFragment();
    CycleColorFragment cycleColorFragment = new CycleColorFragment();
    MusicSyncFragment musicSyncFragment = new MusicSyncFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setToolbar();
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        promptConnection();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, customColorFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.custom:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, customColorFragment).commit();
                        toolbarTitle.setText("Custom Color");
                        return true;
                    case R.id.fade:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, fadeColorFragment).commit();
                        toolbarTitle.setText("Fade Color");
                        return true;
                    case R.id.cycle:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, cycleColorFragment).commit();
                        toolbarTitle.setText("Cycle Color");
                        return true;
                    case R.id.music:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, musicSyncFragment).commit();
                        toolbar.setVisibility(View.GONE);
                        return true;
                }
                return false;
            }
        });
    }

    private void setToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbarTitle = toolbar.findViewById(R.id.toolbarTitle);
    }

    private void promptConnection() {
        Button cancel;
        Button confirm;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        View view = LayoutInflater.from(this).inflate(R.layout.connection_dialog, null);
        builder.setView(view);

        cancel = view.findViewById(R.id.cancelConnectionBtn);
        confirm = view.findViewById(R.id.confirmConnectionBtn);

        AlertDialog dialog = builder.create();
        dialog.show();

        cancel.setOnClickListener(v -> {
            startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
        });

        confirm.setOnClickListener(v -> {
            dialog.dismiss();
        });
    }
}