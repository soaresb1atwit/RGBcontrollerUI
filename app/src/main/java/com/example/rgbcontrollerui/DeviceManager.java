package com.example.rgbcontrollerui;

import android.content.Context;

import com.example.rgbcontrollerui.Device;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class DeviceManager
{
    private ArrayList<Device> devices = new ArrayList<>();
    private final Context context;
    private final static Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

    public DeviceManager(Context context)
    {
        this.context = context;
    }

    private void saveDevices() throws IOException
    {
        FileOutputStream fOut = this.context.openFileOutput("devices.json", Context.MODE_PRIVATE);
        OutputStreamWriter osw = new OutputStreamWriter(fOut);

        Type type = new TypeToken<ArrayList<Device>>() {}.getType();
        osw.write(gson.toJson(this.devices, type));
        osw.flush();
        osw.close();
    }

    public void loadDevices() throws IOException
    {
        File devicesFile = new File(this.context.getFilesDir(), "devices.json");

        // If file doesn't exist, fill it with our current devices
        if(!devicesFile.exists())
        {
            saveDevices();
        }
        // If file does exist, read its contents into devices list
        else
        {
            FileInputStream fIn = this.context.openFileInput("devices.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(fIn));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null)
            {
                sb.append(line).append("\n");
            }
            reader.close();

            Type type = new TypeToken<ArrayList<Device>>() {}.getType();
            this.devices = gson.fromJson(sb.toString(), type);
        }
    }

    public void addDevice(Device device) throws IOException
    {
        this.devices.add(device);
        saveDevices();
    }

    public void removeDevice(Device device) throws IOException
    {
        this.devices.remove(device);
        saveDevices();
    }

    public ArrayList<Device> getDevices()
    {
        return this.devices;
    }
}
