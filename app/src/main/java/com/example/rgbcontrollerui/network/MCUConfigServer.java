package com.example.rgbcontrollerui.network;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

public class MCUConfigServer extends MCUServer
{

    public MCUConfigServer() throws UnknownHostException
    {
        super(new InetSocketAddress(InetAddress.getByName("192.168.33.33"), 3333));
    }

    public void helloCmd() throws MCUServerException
    {
        String request = "HELLO";
        String expectedResponse = "HELLO[OK]";
        sendAndValidateCommand(request, expectedResponse);
    }

    public void setWifiCmd(String ssid, String password) throws MCUServerException
    {
        sendSetterCommand("SET_WIFI", ssid, password);
    }

    public void setThreshold(float value) throws MCUServerException
    {
        sendSetterCommand("SET_THRESHOLD", value);
    }

    public void setStillMs(int value) throws MCUServerException
    {
        sendSetterCommand("SET_STILL_MS", value);
    }

    public void setMoveMs(int value) throws MCUServerException
    {
        sendSetterCommand("SET_MOVE_MS", value);
    }

    public void setPeriodMs(int value) throws MCUServerException
    {
        sendSetterCommand("SET_PERIOD_MS", value);
    }

    public void connectCmd() throws MCUServerException
    {
        String request = "CONNECT";
        String expectedResponse = "CONNECT[OK]";
        sendAndValidateCommand(request, expectedResponse);
    }

    public void alertServerStartCmd() throws MCUServerException
    {
        String request = "ALERT_SERVER_START";
        String expectedResponse = "ALERT_SERVER_START[OK]";
        sendAndValidateCommand(request, expectedResponse);
    }

    public void configServerStopCmd() throws MCUServerException
    {
        String request = "CONFIG_SERVER_STOP";
        String expectedResponse = "CONFIG_SERVER_STOP[OK]";
        sendAndValidateCommand(request, expectedResponse);
    }

    public void configServerRehostAP() throws MCUServerException
    {
        String request = "CONFIG_SERVER_REHOST_AP";
        sendCommand(request);
    }

    public void configServerRehostSTA() throws MCUServerException
    {
        String request = "CONFIG_SERVER_REHOST_STA";
        sendCommand(request);
    }

    public String getAddressCmd() throws MCUServerException
    {
        String request = "GET_ADDRESS";
        return sendGetterCommand(request);
    }
}
