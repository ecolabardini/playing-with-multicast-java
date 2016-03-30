package com.github.ecolabardini.example;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class SenderThread extends Thread {
    @Override
    public void run() {
        while (!isInterrupted())
            send("hi");
    }

    private void send(String message) {
        try {
            MulticastSocket socket = new MulticastSocket();
            socket.setTimeToLive(1);
            byte[] buf = message.getBytes();
            DatagramPacket pack = new DatagramPacket(buf, buf.length, InetAddress.getByName(Config.group), Config.port);
            socket.send(pack);
            socket.close();
            sleep(5000);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            // we don't care about sleep() being interrupted
        }
    }
}
