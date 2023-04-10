package com.example.rgbcontrollerui.UI;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.example.rgbcontrollerui.R;
import com.example.rgbcontrollerui.testTCP.TcpTemp;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main extends AppCompatActivity {
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 6;
    final int PERMISSION_REQUEST_CODE = 112;
    private Toolbar toolbar;
    private TextView toolbarTitle;
    public static BottomNavigationView bottomNavigationView;
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
        toolbarTitle.setVisibility(View.GONE);

        //Initial launch fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.container, customColorFragment).commit();
        toolbarTitle.setText("Custom Color");

        bottomNavigationView.findViewById(R.id.custom).performClick();
        MenuItem item = bottomNavigationView.getMenu().findItem(R.id.custom);
        item.setChecked(true);

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

        ImageButton imageButton = (ImageButton) toolbar.findViewById(R.id.settingsBtn);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptInAppConnection();
            }
        });

        ImageButton powerBtn = (ImageButton) toolbar.findViewById(R.id.powerBtn);
        powerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), TcpTemp.class);
                view.getContext().startActivity(intent);}
        });
    }

    private void setToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbarTitle = toolbar.findViewById(R.id.toolbarTitle);
        toolbarTitle.setVisibility(View.GONE);
    }

    private void promptConnection() {
        Button cancel;
        Button confirm;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        View view = LayoutInflater.from(this).inflate(R.layout.connection_on_launch_dialog, null);
        builder.setView(view);

        cancel = view.findViewById(R.id.cancelConnectionBtn);
        confirm = view.findViewById(R.id.confirmConnectionBtn);

        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        cancel.setOnClickListener(v -> {
            startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
        });

        confirm.setOnClickListener(v -> {
            dialog.dismiss();
        });
    }

    private void promptInAppConnection() {
        Button cancel;
        Button confirm;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        View view = LayoutInflater.from(this).inflate(R.layout.connection_dialog, null);
        builder.setView(view);

        cancel = view.findViewById(R.id.cancelConnectionBtn);
        confirm = view.findViewById(R.id.confirmConnectionBtn);

        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        cancel.setOnClickListener(v -> {
            dialog.dismiss();
        });

        confirm.setOnClickListener(v -> {
            startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
            dialog.dismiss();
        });
    }

    private  boolean checkAndRequestPermissions() {
        int coarseLocationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        int fineLocationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int wiFiStatePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_WIFI_STATE);
        int changeWiFiStatePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CHANGE_WIFI_STATE);
        int externalStoragePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int readMediaPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_AUDIO);

        List<String> listPermissionsNeeded = new ArrayList<>();

        if (coarseLocationPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (fineLocationPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (externalStoragePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (readMediaPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_MEDIA_AUDIO);
        }
        if (wiFiStatePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_WIFI_STATE);
        }
        if (changeWiFiStatePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CHANGE_WIFI_STATE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode) {
//            case REQUEST_ID_MULTIPLE_PERMISSIONS: {
//
//                Map<String, Integer> perms = new HashMap<>();
//                // Initialize the map with both permissions
//                perms.put(Manifest.permission.ACCESS_COARSE_LOCATION, PackageManager.PERMISSION_GRANTED);
//                perms.put(Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);
//                perms.put(Manifest.permission.READ_MEDIA_AUDIO, PackageManager.PERMISSION_GRANTED);
//                perms.put(Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
//                perms.put(Manifest.permission.ACCESS_WIFI_STATE, PackageManager.PERMISSION_GRANTED);
//                perms.put(Manifest.permission.CHANGE_WIFI_STATE, PackageManager.PERMISSION_GRANTED);
//                // Fill with actual results from user
//                if (grantResults.length > 0) {
//                    for (int i = 0; i < permissions.length; i++)
//                        perms.put(permissions[i], grantResults[i]);
//                    // Check for both permissions
//                    if (perms.get(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
//                            && perms.get(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
//                            && perms.get(Manifest.permission.READ_MEDIA_AUDIO) == PackageManager.PERMISSION_GRANTED
//                            && perms.get(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
//                            && perms.get(Manifest.permission.ACCESS_WIFI_STATE) == PackageManager.PERMISSION_GRANTED
//                            && perms.get(Manifest.permission.CHANGE_WIFI_STATE) == PackageManager.PERMISSION_GRANTED) {
//                        // process the normal flow
//                        //else any one or both the permissions are not granted
//                    } else {
//                        //permission is denied (this is the first time, when "never ask again" is not checked) so ask again explaining the usage of permission
////                        // shouldShowRequestPermissionRationale will return true
//                        //show the dialog or snackbar saying its necessary and try again otherwise proceed with setup.
//                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS) || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
//                            showDialogOK("Location, WiFi, and Read Services Permission required for this app",
//                                    new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            switch (which) {
//                                                case DialogInterface.BUTTON_POSITIVE:
//                                                    checkAndRequestPermissions();
//                                                    break;
//                                                case DialogInterface.BUTTON_NEGATIVE:
//                                                    // proceed with logic by disabling the related features or quit the app.
//                                                    break;
//                                            }
//                                        }
//                                    });
//                        }
//                        //permission is denied (and never ask again is  checked)
//                        //shouldShowRequestPermissionRationale will return false
//                        else {
//                            Toast.makeText(this, "Go to settings and enable permissions", Toast.LENGTH_LONG)
//                                    .show();
//                            //                            //proceed with logic by disabling the related features or quit the app.
//                        }
//                    }
//                }
//            }
//        }
//    }

    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show();
    }

    public void getNotificationPermission(){
        try {
            if (Build.VERSION.SDK_INT > 32) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS, Manifest.permission.CAMERA, Manifest.permission.ACCESS_WIFI_STATE},
                        PERMISSION_REQUEST_CODE);
            }
        }catch (Exception e) {

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // allow

                }  else {
                    //deny
                }
                return;
        }
    }


}