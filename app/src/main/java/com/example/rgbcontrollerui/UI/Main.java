package com.example.rgbcontrollerui.UI;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

//import com.espressif.provisioning.ESPProvisionManager;
import com.example.rgbcontrollerui.R;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarItemView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.slider.RangeSlider;

public class Main extends AppCompatActivity {
    private Toolbar toolbar;
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
                        return true;
                    case R.id.fade:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, fadeColorFragment).commit();
                        return true;
                    case R.id.cycle:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, cycleColorFragment).commit();
                        return true;
                    case R.id.music:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, musicSyncFragment).commit();
                        return true;
                }
                return false;
            }
        });
    }

//    private void setSliderValues() {
//        fadeRedSlider.setValueTo(255);
//        fadeGreenSlider.setValueTo(255);
//        fadeBlueSlider.setValueTo(255);
//        cycleRedSlider.setValueTo(255);
//        cycleGreenSlider.setValueTo(255);
//        cycleBlueSlider.setValueTo(255);
//    }

    private void setToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setVisibility(View.GONE);
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