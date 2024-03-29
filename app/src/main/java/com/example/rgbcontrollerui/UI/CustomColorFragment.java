package com.example.rgbcontrollerui.UI;

import static com.example.rgbcontrollerui.UI.Main.powerBtnState;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.rgbcontrollerui.R;

import yuku.ambilwarna.AmbilWarnaDialog;

/**
 * A simple {@link Fragment} subclass.
 * Use the factory method to
 * create an instance of this fragment.
 */
public class CustomColorFragment extends Fragment {
    private Button setColorButton, pickColorButton;
    private View colorPreview;
    private View mColorMainView;
    private TextView colorPreviewTextView;
    private int mDefaultColor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mColorMainView = inflater.inflate(R.layout.fragment_custom_color, container, false);
        pickColorButton = mColorMainView.findViewById(R.id.pick_color_button);
        setColorButton = mColorMainView.findViewById(R.id.set_color_button);
        colorPreview = mColorMainView.findViewById(R.id.preview_selected_color);
        colorPreviewTextView = mColorMainView.findViewById(R.id.colorPreviewTextView);
        mDefaultColor = 0;

//        ImageButton powerBtn = (ImageButton) toolbar.findViewById(R.id.powerBtn);

//        powerBtn.setOnClickListener(new View.OnClickListener() {
//            @SuppressLint("ResourceAsColor")
//            @Override
//            public void onClick(View view) {
//                new Thread(new Main.ClientThread("POWER ON")).start();
//                if (powerBtnState.equalsIgnoreCase("off")) {
//                    powerBtn.setColorFilter(Color.WHITE); // White Tint
//                    powerBtnState = "on";
//                }
//                else if (powerBtnState.equalsIgnoreCase("ON")) {
//                    powerBtn.setColorFilter(Color.BLACK); // White Tint
//                    powerBtnState = "off";
//                }
//            }
//        });

        pickColorButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openColorPickerDialogue();
                    }
                });
        setColorButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new Thread(new Main.ClientThread("CUSTOM:" + Integer.toHexString(mDefaultColor).substring(2))).start();
                        // as the mDefaultColor is the global
                        // variable its value will be changed as
                        // soon as ok button is clicked from the
                        // color picker dialog.
                        colorPreviewTextView.setTextColor(mDefaultColor);
                    }
                });

        TransitionInflater transitionInflater = TransitionInflater.from(requireContext());
        setEnterTransition(transitionInflater.inflateTransition(R.transition.slide_left));
//        setExitTransition(transitionInflater.inflateTransition(R.transition.fade));

//        setToolbar();

        return mColorMainView;
    }

//    private void setToolbar() {
//        toolbar = mColorMainView.findViewById(R.id.toolbar);
//        TextView title = toolbar.findViewById(R.id.toolbarTitle);
//        toolbar.setVisibility(View.GONE);
//        title.setText("ESP32 RGB Controller");
//    }
    public void openColorPickerDialogue() {
        final AmbilWarnaDialog colorPickerDialogue = new AmbilWarnaDialog(getActivity(), mDefaultColor,
                new AmbilWarnaDialog.OnAmbilWarnaListener() {
                    @Override
                    public void onCancel(AmbilWarnaDialog dialog) {
                    }
                    @Override
                    public void onOk(AmbilWarnaDialog dialog, int color) {
                        mDefaultColor = color;
                        colorPreview.setBackgroundColor(mDefaultColor);
                    }
                });
        colorPickerDialogue.show();
    }
}