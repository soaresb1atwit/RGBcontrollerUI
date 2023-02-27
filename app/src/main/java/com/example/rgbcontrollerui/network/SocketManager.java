package com.example.rgbcontrollerui.network;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SocketManager
{
    private final List<Socket> sockets;

    public SocketManager()
    {
        this.sockets = new ArrayList<>();
    }

    public Socket createSocket()
    {
        Socket socket = new Socket();
        this.sockets.add(socket);
        return socket;
    }

    public void cleanAllSockets()
    {
        for(Socket socket : this.sockets)
        {
            cleanSocket(socket);
        }
    }

    public void cleanSocket(Socket socket)
    {
        if(socket != null && !socket.isClosed())
        {
            try
            {
                socket.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
