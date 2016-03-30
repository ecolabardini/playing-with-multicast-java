package com.github.ecolabardini.example;

public class Main {

	public static void main(String[] args) {
		final ReceiverThread receiverThread = new ReceiverThread();
		final SenderThread senderThread = new SenderThread();
		
		receiverThread.start();
		senderThread.start();
		
		Runtime.getRuntime().addShutdownHook(new Thread() {
	        public void run() {
	        	System.out.println("Stoppping...");
	        	senderThread.interrupt();
            	receiverThread.interrupt();
	        }
	    });
	}
}
