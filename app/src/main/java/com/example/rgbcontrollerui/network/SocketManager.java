package com.example.rgbcontrollerui.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SocketManager
{
    private final List<Socket> sockets;
    private final int port = 1234;
    private final Socket socket ;

   /* public void serverSocket(int port) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while(true) {
                InputStream input = serverSocket.accept().getInputStream();
                InputStreamReader reader = new InputStreamReader(input);
                int digit = reader.read();  // reads a single character

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }*/
 void run() {
     try {
         int serverPort = 1111 ;
         InetAddress host = InetAddress.getByName("localhost") ;
         System.out.println("Connecting to server on port " + serverPort) ;
         System.out.println("Just connected to " + this.socket.getRemoteSocketAddress());
         PrintWriter toServer =
                 new PrintWriter(socket.getOutputStream(),true);
         BufferedReader fromServer =
                 new BufferedReader(
                         new InputStreamReader(socket.getInputStream()));
         toServer.println("Hello from " + socket.getLocalSocketAddress());
         String line = fromServer.readLine();
         System.out.println("Client received: " + line + " from Server");
     } catch (IOException e) {
         throw new RuntimeException(e);
     }
 }


    public SocketManager(Socket socket)
    {
        this.socket = socket;
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
