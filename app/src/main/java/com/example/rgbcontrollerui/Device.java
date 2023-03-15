package com.example.rgbcontrollerui;

import android.graphics.Color;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.annotations.Expose;

import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Objects;

import com.example.rgbcontrollerui.network.MCUAlertServer;
import com.example.rgbcontrollerui.network.MCUServerException;

public class Device
{
    @Expose
    private final String name;

    @Expose
    private final String address;

    @Expose
    private final String ssid;

    private MCUAlertServer alertServer;
    private boolean configured = false;

    public Device(String name, String ssid, String address)
    {
        this.name = name;
        this.ssid = ssid;
        this.address = address;
    }

    public Device(String name, String ssid)
    {
        this(name, ssid, null);
    }

    public void createAlertServer()
    {
        if(this.address != null)
        {
            this.configured = true;

            try
            {
                this.alertServer = new MCUAlertServer(this.address);
            }
            catch (UnknownHostException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void connectToAlertServer(Socket socket) throws MCUServerException
    {
        this.alertServer.connect(socket, 5000);
        this.alertServer.helloCmd();
    }

    public Status getStatus()
    {
        if(!this.isConfigured())
        {
            return Status.UNCONFIGURED;
        }

        try
        {
            return this.alertServer.getStatus();
        }
        catch (MCUServerException e)
        {
            e.printStackTrace();
            return Device.Status.DISCONNECTED;
        }
    }

    public String getAddress()
    {
        return address;
    }

    public String getName()
    {
        return name;
    }

    public String getSsid()
    {
        return ssid;
    }

    public boolean isConfigured()
    {
        return this.configured;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Device device = (Device) o;
        return name.equals(device.name) &&
                address.equals(device.address) &&
                ssid.equals(device.ssid);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode()
    {
        return Objects.hash(name, address, ssid);
    }

    public enum Status
    {
        STILL(Color.GREEN),
        MOVING(Color.YELLOW),
        DISCONNECTED(Color.RED),
        UNCONFIGURED(Color.CYAN);

        private final int color;

        Status(int color)
        {
            this.color = color;
        }

        public int getColor()
        {
            return color;
        }
    }
}