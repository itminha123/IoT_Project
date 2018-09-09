package kr.hkit.iot_project.socket;

import android.os.AsyncTask;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class SocketClient extends AsyncTask<Void, Void, Void> {

    private static final int BUFF_SIZE = 1024;

    private String ip;
    private int port;
    private String sendMessage;
    private String receiveMessage;
    private SocketClientListener socketClientListener;

    public SocketClient(String ip, int port, String sendMessage, SocketClientListener socketClientListener) {
        this.ip = ip;
        this.port = port;
        this.sendMessage = sendMessage;
        this.socketClientListener = socketClientListener;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            Socket socket = new Socket(ip, port);

            OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream());
            writer.write(sendMessage);
            writer.flush();

            InputStreamReader reader = new InputStreamReader(socket.getInputStream());
            char[] cbuf = new char[BUFF_SIZE];
            StringBuilder sb = new StringBuilder();
            while (reader.read(cbuf) != -1) {
                sb.append(cbuf);
            }
            receiveMessage = sb.toString();

            writer.close();
            reader.close();
            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
            receiveMessage = e.getMessage();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        if (socketClientListener != null) {
            socketClientListener.onReceiveMessage(receiveMessage);
        }
    }

}
