package com.github.ecolabardini.example;

public class Main {

	public static void main(String[] args) {
		final ReceiverThread receiverThread = new ReceiverThread();
		final SenderThread senderThread = new SenderThread();
		
		receiverThread.start();
		senderThread.start();
		
		Runtime.getRuntime().addShutdownHook(new Thread() {
	        public void run() {

                /* so they can finish their work gracefully */
            	receiverThread.interrupt();
                senderThread.interrupt();

                try {
                    /* waits at most millis MAX_WAIT milliseconds for this threads to die */
                    receiverThread.join(Config.MAX_WAIT);
                    senderThread.join(Config.MAX_WAIT);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("Exiting...");
	        }
	    });
	}
}
