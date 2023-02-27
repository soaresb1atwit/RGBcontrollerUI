package com.example.rgbcontrollerui.network;

import java.io.IOException;

public class MCUServerException extends Exception {
    private final Type type;
    private final IOException exception;
    private final String response;

    MCUServerException(Type type, IOException exception, String response)
    {
        this.type = type;
        this.exception = exception;
        this.response = response;
    }

    public MCUServerException(Type type)
    {
        this(type, null, "");
    }

    public MCUServerException(Type type, IOException exception)
    {
        this(type, exception, "");
    }

    public MCUServerException(Type type, String response)
    {
        this(type, null, response);
    }

    public boolean hasException()
    {
        return this.exception != null;
    }

    public boolean hasResponse()
    {
        return !this.response.equals("");
    }

    public IOException getException() {
        return exception;
    }

    public Type getType() {
        return type;
    }

    public String getResponse() {
        return response;
    }

    public enum Type
    {
        UNRESPONSIVE("Server unresponsive"),
        BAD_RESPONSE("Bad response"),
        CLOSED("Socket closed"),
        NO_SOCKET("No socket"),
        ALREADY_CONNECTED("Socket is already connected"),
        CONNECT_FAIL("Failed to connect to server"),
        RECEIVE_FAIL("Failed to receive from server"),
        SEND_FAIL("Failed to send to server"),
        CLOSE_FAIL("Failed to close socket");

        private final String message;

        Type(String message)
        {
           this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
