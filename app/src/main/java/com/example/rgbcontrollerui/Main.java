package com.example.rgbcontrollerui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import yuku.ambilwarna.AmbilWarnaDialog;

public class Main extends AppCompatActivity {
    private Toolbar toolbar;
    private Button mSetColorButton, mPickColorButton;
    private View mColorPreview;
    private TextView textView;
    private int mDefaultColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setToolbar();
        promptConnection();
        mPickColorButton = findViewById(R.id.pick_color_button);
        mSetColorButton = findViewById(R.id.set_color_button);
        mColorPreview = findViewById(R.id.preview_selected_color);
        textView = findViewById(R.id.textView);
        mDefaultColor = 0;

        mPickColorButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // to make code look cleaner the color
                        // picker dialog functionality are
                        // handled in openColorPickerDialogue()
                        // function
                        openColorPickerDialogue();
                    }
                });

        // button to set the color GFG text
        mSetColorButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // as the mDefaultColor is the global
                        // variable its value will be changed as
                        // soon as ok button is clicked from the
                        // color picker dialog.
                        textView.setTextColor(mDefaultColor);
                    }
                });
    }
    private void setToolbar() {
        toolbar = findViewById(R.id.toolbar);
        TextView title = toolbar.findViewById(R.id.toolbarTitle);
        title.setText("ESP32 RGB Controller");
    }
    public void openColorPickerDialogue() {

        // the AmbilWarnaDialog callback needs 3 parameters
        // one is the context, second is default color,
        final AmbilWarnaDialog colorPickerDialogue = new AmbilWarnaDialog(this, mDefaultColor,
                new AmbilWarnaDialog.OnAmbilWarnaListener() {
                    @Override
                    public void onCancel(AmbilWarnaDialog dialog) {
                        // leave this function body as
                        // blank, as the dialog
                        // automatically closes when
                        // clicked on cancel button
                    }

                    @Override
                    public void onOk(AmbilWarnaDialog dialog, int color) {
                        // change the mDefaultColor to
                        // change the GFG text color as
                        // it is returned when the OK
                        // button is clicked from the
                        // color picker dialog
                        mDefaultColor = color;

                        // now change the picked color
                        // preview box to mDefaultColor
                        mColorPreview.setBackgroundColor(mDefaultColor);
                    }
                });
        colorPickerDialogue.show();
    }
    @SuppressLint("MissingInflatedId")
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