package com.github.ecolabardini.example;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

public class SenderThread extends Thread {

    @Override
    public void run() {
        while (!isInterrupted()) {
            send("hi");
        }
    }

    private void send(String message) {
        try {
            MulticastSocket socket = new MulticastSocket();
            socket.setTimeToLive(1);
            byte[] buf = message.getBytes();
            DatagramPacket pack = new DatagramPacket(buf, buf.length,
                    InetAddress.getByName(Config.MULTICAST_GROUP), Config.MULTICAST_PORT);
            socket.send(pack);
            socket.close();
            sleep(Config.SLEEP_TIME);
        } catch (InterruptedException e) {
            interrupt(); /* restore the interrupted status */
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
