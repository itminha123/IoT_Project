package kr.hkit.iot_project.socket;

public interface SocketClientListener {
    void onReceiveMessage(String receiveMessage);
}
