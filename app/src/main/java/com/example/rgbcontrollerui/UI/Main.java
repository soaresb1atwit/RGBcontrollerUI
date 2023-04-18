package com.example.rgbcontrollerui.UI;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
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
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import kotlin.text.Charsets;

public class Main extends AppCompatActivity {
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 6;
    final int PERMISSION_REQUEST_CODE = 112;
    static Toolbar toolbar;
    static ImageButton powerBtn;
    static PopupMenu dropDownMenu;
    static Menu menu;
    private TextView toolbarTitle;
    public static String powerBtnState = "off";
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
//                        toolbar.setVisibility(View.GONE);
                        return true;
                }
                return false;
            }
        });

        ImageButton imageButton = (ImageButton) toolbar.findViewById(R.id.settingsBtn);
        dropDownMenu = new PopupMenu(this, imageButton);
        menu = dropDownMenu.getMenu();
        dropDownMenu.getMenuInflater().inflate(R.menu.settings_menu, menu);

        dropDownMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.reconnect:
                            promptInAppConnection();
                        System.out.println("Reconnect is pressed");
                        return true;
                    case R.id.debug:
                        System.out.println("Debug is pressed");
                        Intent launchNewIntent = new Intent(Main.this,TcpTemp.class);
                        startActivityForResult(launchNewIntent, 0);
                        return true;
                }
                return false;
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dropDownMenu.show();
            }
        });

        powerBtn = (ImageButton) toolbar.findViewById(R.id.powerBtn);

        powerBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                if (powerBtnState.equalsIgnoreCase("off")) {
                    powerBtn.setColorFilter(Color.WHITE); // White Tint
                    powerBtnState = "on";
                }
                else if (powerBtnState.equalsIgnoreCase("ON")) {
                    powerBtn.setColorFilter(Color.BLACK); // White Tint
                    powerBtnState = "off";
                }
                new Thread(new ClientThread("TOGGLEPOWER: " + powerBtnState)).start();
                if (!powerBtnState.equalsIgnoreCase("off")) {
                    Toast.makeText(getApplicationContext(),"Connection Successful!",Toast.LENGTH_SHORT).show();
                }
            }
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
            Toast.makeText(getApplicationContext(),"Power on the lights using the power button above.",Toast.LENGTH_LONG).show();
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

    static class ClientThread implements Runnable {
        private final String outMessage;
        String inMessage;
        Socket socket = null;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        private final String ip = "10.220.40.75";
        private final int portNum = 12345;

        public ClientThread(String outMessage) {
            this.outMessage = outMessage;
        }

        private String convertStreamToString(InputStream is) throws IOException
        {
            String response = "";
            byte[] packet = new byte[128];
            int read = is.read(packet);
            if(read < 0)
            {
                return response;
            }
            response += new String(packet, 0, read, Charsets.US_ASCII);
            return response;
        }

        @Override
        public void run() {
            try {
                socket = new Socket(ip, portNum);
                oos = new ObjectOutputStream(socket.getOutputStream());
                System.out.println("Sending request to Socket Server");

                oos.writeObject(outMessage);

                Log.d("STATUS","MESSAGE SENT: " + outMessage);

                String dataString = "";

                try
                {
                    InputStream inputStream = socket.getInputStream();

                    dataString = convertStreamToString(inputStream);

                    System.out.println("MESSAGE RECEIVED: " + dataString);
                    Log.d("MESSAGE RECEIVED: ", dataString);
                    inMessage = dataString;
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                oos.close();
            }
            catch (IOException e) {
                Log.d("Status 1","First runtime exception.");
                e.printStackTrace();
            }

            System.out.println("Server started. Listening to the port: " + portNum);

            // updating the UI
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    if (textField.getText().toString().equalsIgnoreCase("exit")) {
//                        finish();
//                    }
//                    textField.setText("");
//                }
//            });
//            finish();
        }
    }
}