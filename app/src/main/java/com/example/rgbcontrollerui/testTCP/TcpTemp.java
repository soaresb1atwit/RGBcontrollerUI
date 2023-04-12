package com.example.rgbcontrollerui.testTCP;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rgbcontrollerui.R;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class TcpTemp extends AppCompatActivity {
    // declaring required variables
    private EditText textField;
    private Button button;
    private String outMessage;
    public static String inMessage;
    private final String ip = "192.168.1.249";
    private final int portNum = 12345;

    @Override    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tcp_temp);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearlayout);

        // reference to the text field
        textField = (EditText) findViewById(R.id.editText1);

        // reference to the send button
        button = (Button) findViewById(R.id.button1);

        // Button press event listener
        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // get the text message on the text field
                outMessage = textField.getText().toString();

                // start the Thread to connect to server
                new Thread(new ClientThread(outMessage)).start();

                TextView sentTextView = new TextView(getApplicationContext());
                sentTextView.setText("Client: " + outMessage);
                sentTextView.setTextColor(ContextCompat.getColor(TcpTemp.this, R.color.cancelBtn));
                sentTextView.setTextSize(18);
                linearLayout.addView(sentTextView);

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                TextView receivedtextView = new TextView(getApplicationContext());
                receivedtextView.setText("Server: " + inMessage);
                receivedtextView.setTextColor(ContextCompat.getColor(TcpTemp.this, R.color.cancelBtn));
                receivedtextView.setTextSize(18);
                linearLayout.addView(receivedtextView);
            }
        });
    }

    class ClientThread implements Runnable {
        private final String outMessage;
        Socket socket = null;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;

        ClientThread(String outMessage) {
            this.outMessage = outMessage;
        }

        @Override
        public void run() {
            try {
                socket = new Socket(ip, portNum);
                oos = new ObjectOutputStream(socket.getOutputStream());
                System.out.println("Sending request to Socket Server");

                oos.writeObject(outMessage);

                Log.d("STATUS","MESSAGE SENT: " + outMessage);

                String dataString = "";

                try
                {
                    InputStream inputStream = socket.getInputStream();
                    DataInputStream in = new DataInputStream(inputStream);

                    dataString = in.readUTF();
                    System.out.println("MESSAGE RECEIVED: " + dataString);
                    inMessage = dataString;
                    in.close();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }





//                ois = new ObjectInputStream(socket.getInputStream());
//                String message = (String) ois.readObject();

//                System.out.println("Message: " + message);
//                Log.d("STATUS","MESSAGE RECEIVED: " + message);

//                ois.close();
                oos.close();
            }
            catch (IOException e) {
                Log.d("Status 1","First runtime exception.");
                e.printStackTrace();
            }
//            catch (ClassNotFoundException e) {
//                Log.d("Status 2","Second runtime exception.");
//                throw new RuntimeException(e);
//            }

            // System.out.println("Server started. Listening to the port ####");
            System.out.println("Server started. Listening to the port: " + portNum);

            // updating the UI
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (textField.getText().toString().equalsIgnoreCase("exit")) {
                        finish();
                    }
                    textField.setText("");
                }
            });
        }
    }
}