package kr.hkit.iot_project;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;

public class Notification extends Thread {

    public interface NotificationListener {
        public void onNotification(String command);
    }

    private static final String NOTI_TYPE = "android";

    private static final int STATE_DONE = 0;
    private static final int STATE_RUNNING = 1;

    private DatagramSocket socket;
    private int state;

    private byte[] buf = new byte[1024];
    private String command;
    private String ip;
    private int port;

    private NotificationListener notificationListener;

    Notification(String ip, int port, NotificationListener notificationListener) {
        this.notificationListener = notificationListener;
        this.ip = ip;
        this.port = port;



        try {
            socket = new DatagramSocket();
        }  catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        super.run();

        byte[] sendBuf = NOTI_TYPE.getBytes(Charset.forName("UTF-8"));

        try {
            InetAddress address = InetAddress.getByName(this.ip);
            DatagramPacket packet = new DatagramPacket(sendBuf, sendBuf.length, address, this.port);
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }

        state = STATE_RUNNING;

        try {
            while(state == STATE_RUNNING) {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                command = new String(packet.getData(), 0, packet.getLength());
                Log.i("receiveData : ", command);
                handler.sendEmptyMessage(0);
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void done() {
        state = STATE_DONE;
        socket.close();
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(notificationListener != null) {
                notificationListener.onNotification(command);
            }
        }
    };
}
