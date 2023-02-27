package com.example.rgbcontrollerui;

import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.format.DateFormat;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import java.util.Date;

public class Logger
{
    private final TextView textView;
    private final Handler mainHandler;

    public Logger(TextView textView)
    {
        this.textView = textView;
        this.mainHandler = new Handler(Looper.getMainLooper());
    }

    private void logMessage(String message, int color)
    {
        Spannable text = new SpannableString(message);
        text.setSpan(new ForegroundColorSpan(color), 0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        String date = DateFormat.format("hh:mm:ss", new Date()).toString();

        this.mainHandler.post(() ->
        {
            this.textView.append("[" + date + "] ");
            this.textView.append(text);
            this.textView.append("\n");
        });
    }

    public void logError(String message)
    {
        logMessage(message, Color.RED);
    }

    public void logPending(String message)
    {
        logMessage(message, Color.YELLOW);
    }

    public void logSuccess(String message)
    {
        logMessage(message, Color.GREEN);
    }

    public void logInfo(String message)
    {
        logMessage(message, Color.WHITE);
    }
}
