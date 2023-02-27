package com.example.rgbcontrollerui.network;

import com.example.rgbcontrollerui.Device;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

import com.example.rgbcontrollerui.Device;

public class MCUAlertServer extends MCUServer
{
    public MCUAlertServer(String address) throws UnknownHostException
    {
        super(new InetSocketAddress(InetAddress.getByName(address), 4444));
    }

    public void helloCmd() throws MCUServerException
    {
        sendAndValidateCommand("HELLO", "HELLO[OK]");
    }

    public Device.Status getStatus() throws MCUServerException
    {
        String response = sendGetterCommand("GET_STATUS");
        return Device.Status.valueOf(response);
    }
}
