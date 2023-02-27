package com.example.rgbcontrollerui.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiNetworkSpecifier;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Semaphore;

public class WifiInterface
{
    private final Context context;
    private final WifiManager wifiManager;
    private BroadcastReceiver wifiScanReceiver;
    private static long lastScanTimestamp = 0;

    public WifiInterface(Context context)
    {
        this.context = context;
        this.wifiManager = (WifiManager) this.context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
    }

    public boolean isWifiEnabled()
    {
        return this.wifiManager.isWifiEnabled();
    }

    public boolean enableWifi()
    {
        return this.wifiManager.setWifiEnabled(true);
    }

    public void registerScanReceiver(BroadcastReceiver receiver)
    {
        this.wifiScanReceiver = receiver;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        this.context.registerReceiver(this.wifiScanReceiver, intentFilter);
    }

    public void unregisterScanReceiver()
    {
        if(this.wifiScanReceiver != null)
        {
            this.context.unregisterReceiver(this.wifiScanReceiver);
        }
    }

    public void scan() throws WifiInterfaceException
    {
        // Only scan every 30 seconds (can only do it 4 times every 2 minutes)
        if(System.currentTimeMillis() - lastScanTimestamp < 1000 * 30)
        {
            return;
        }

        lastScanTimestamp = System.currentTimeMillis();
        if (!this.wifiManager.startScan())
        {
            throw new WifiInterfaceException();
        }
    }

    public List<ScanResult> getScanResults()
    {
        return this.wifiManager.getScanResults();
    }

    public void connectWifi(String ssid, String password)
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
        {
            WifiNetworkSpecifier.Builder builder = new WifiNetworkSpecifier.Builder();
            builder.setSsid(ssid);
            builder.setWpa2Passphrase(password);

            WifiNetworkSpecifier wifiNetworkSpecifier = builder.build();
            NetworkRequest.Builder networkRequestBuilder = new NetworkRequest.Builder();
            networkRequestBuilder.addTransportType(NetworkCapabilities.TRANSPORT_WIFI);
            networkRequestBuilder.addCapability(NetworkCapabilities.NET_CAPABILITY_NOT_RESTRICTED);
            networkRequestBuilder.addCapability(NetworkCapabilities.NET_CAPABILITY_TRUSTED);
            networkRequestBuilder.setNetworkSpecifier(wifiNetworkSpecifier);
            NetworkRequest networkRequest = networkRequestBuilder.build();
            ConnectivityManager cm = (ConnectivityManager)this.context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm != null)
            {
                cm.requestNetwork(networkRequest, new ConnectivityManager.NetworkCallback() {});
            }
        }
    }
}
