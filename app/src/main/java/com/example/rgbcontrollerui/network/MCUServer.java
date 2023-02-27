package com.example.rgbcontrollerui.network;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import kotlin.text.Charsets;

public abstract class MCUServer
{
    protected Socket socket;
    protected InetSocketAddress socketAddress;

    public MCUServer(InetSocketAddress socketAddress)
    {
        this.socketAddress = socketAddress;
    }

    public void connect(Socket socket, int timeout) throws MCUServerException
    {
        this.socket = socket;

        // If it's already closed
        if(this.socket.isClosed())
        {
            throw new MCUServerException(MCUServerException.Type.CLOSED);
        }

        // If it's already in a connection
        if(this.socket.isConnected())
        {
            throw new MCUServerException(MCUServerException.Type.ALREADY_CONNECTED);
        }

        // Connect
        try
        {
            System.out.println(this.socketAddress);
            this.socket.connect(this.socketAddress, timeout);
        }
        catch (IOException e)
        {
            throw new MCUServerException(MCUServerException.Type.CONNECT_FAIL, e);
        }
    }

    protected String read() throws MCUServerException
    {
        try
        {
            return convertStreamToString(this.socket.getInputStream());
        }
        catch (IOException e)
        {
            throw new MCUServerException(MCUServerException.Type.RECEIVE_FAIL, e);
        }
    }

    protected void sendSetterCommand(String label, Object... args) throws MCUServerException
    {
        StringBuilder request = new StringBuilder(label + "[");
        for(Object object : args)
        {
            request.append(object.toString()).append(",");
        }
        request = request.deleteCharAt(request.lastIndexOf(",")).append("]");
        sendAndValidateCommand(request.toString(), label + "[OK]");
    }

    protected String sendGetterCommand(String request) throws MCUServerException
    {
        String recvString = sendCommandRecvResponse(request);
        try
        {
            return recvString.substring(recvString.indexOf("[") + 1, recvString.indexOf("]"));
        }
        catch (StringIndexOutOfBoundsException e)
        {
            throw new MCUServerException(MCUServerException.Type.BAD_RESPONSE);
        }
    }

    /**
     * Sends the given request and if the expected response isn't matched, an exception is thrown
     * @param request the request to send
     * @param expectedResponse the expected response to the request
     * @throws MCUServerException same as sendCommandRecvResponse() and additionally if the expected response doesn't
     * match
     */
    protected void sendAndValidateCommand(String request, String expectedResponse) throws MCUServerException
    {
        String recvString = sendCommandRecvResponse(request);
        if(!recvString.equals(expectedResponse))
        {
            throw new MCUServerException(MCUServerException.Type.BAD_RESPONSE, recvString);
        }
    }

    /**
     * Sends the given request and returns the response
     * @param request the request to send
     * @return the response as a String
     * @throws MCUServerException same as sendCommand() and additionally if failed to receive data from socket
     */
    protected String sendCommandRecvResponse(String request) throws MCUServerException
    {
        sendCommand(request);

        try
        {
            return convertStreamToString(this.socket.getInputStream());
        }
        catch (IOException e)
        {
            throw new MCUServerException(MCUServerException.Type.RECEIVE_FAIL, e);
        }
    }



    /**
     * Sends the given request via the socket
     * @param request the request to send
     * @throws MCUServerException when socket is null or it fails to write to the socket
     */
    protected void sendCommand(String request) throws MCUServerException
    {
        System.out.println("Sending command: " + request);
        if(this.socket == null)
        {
            throw new MCUServerException(MCUServerException.Type.NO_SOCKET);
        }

        try
        {
            this.socket.getOutputStream().write(request.getBytes());
        }
        catch (IOException e)
        {
            throw new MCUServerException(MCUServerException.Type.SEND_FAIL, e);
        }
    }

    private static String convertStreamToString(InputStream is) throws IOException
    {
        String response = "";
        byte[] packet = new byte[128];
        int read = is.read(packet);
        if(read < 0)
        {
            return response;
        }
        response += new String(packet, 0, read, Charsets.US_ASCII);
        System.out.println("Received response: " + response);
        return response;
    }

    public void setAddress(String address, int port) throws UnknownHostException
    {
        this.socketAddress = new InetSocketAddress(InetAddress.getByName(address), port);
    }

    public Socket getSocket()
    {
        return socket;
    }

    public InetSocketAddress getSocketAddress()
    {
        return socketAddress;
    }
}
