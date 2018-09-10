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

    private static final String IP = "192.168.0.4";
    private static final int PORT = 80;
    private static final String NOTI_TYPE = "android";

    private static final int STATE_DONE = 0;
    private static final int STATE_RUNNING = 1;

    private DatagramSocket socket;
    private int state;

    private byte[] buf = new byte[1024];
    private String command;

    NotificationListener notificationListener;

    Notification(NotificationListener notificationListener) {
        this.notificationListener = notificationListener;

        byte[] sendBuf = NOTI_TYPE.getBytes(Charset.forName("UTF-8"));

        try {
            socket = new DatagramSocket();
            InetAddress address = InetAddress.getByName(IP);
            DatagramPacket packet = new DatagramPacket(sendBuf, sendBuf.length, address, PORT);
            socket.send(packet);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        super.run();


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
