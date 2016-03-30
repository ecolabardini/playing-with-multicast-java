package com.github.ecolabardini.example;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class ReceiverThread extends Thread {
    private Map<String, Long> hosts = new HashMap<String, Long>();

    @Override
    public void run() {
        try {
            MulticastSocket socket = new MulticastSocket(Config.port);
            socket.joinGroup(InetAddress.getByName(Config.group));
            
            while (!isInterrupted()) {
                byte buf[] = new byte[1024];
                DatagramPacket pack = new DatagramPacket(buf, buf.length);
                socket.receive(pack);
                
                String message = new String(pack.getData(), 0, pack.getLength());
                if (message.equals("hi")) {
                    hosts.put(pack.getAddress().getHostAddress(), System.currentTimeMillis());
                }
                System.out.println(getAvailableHosts());
            }
            
            socket.leaveGroup(InetAddress.getByName(Config.group));
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Set<String> getAvailableHosts() {
        List<String> hostsToRemove = new ArrayList<String>();
        for (Entry<String, Long> entry : hosts.entrySet()) {
            long entryAge = System.currentTimeMillis() - entry.getValue();
            if (entryAge > 10000) {
                hostsToRemove.add(entry.getKey());
            }
        }
        
        for (String host : hostsToRemove) hosts.remove(host);
        return hosts.keySet();
    }
}
