package fin.msg.route.main;

import java.io.IOException;

import fin.msg.route.src.KeepAliveThread;
import fin.msg.route.src.ReceiverThread;
import fin.msg.route.src.SenderThread;

public class Main {
	private static final String host = "203.185.82.3";
	private static final int port = 2204;
	public static void main(String[] args) throws IOException, InterruptedException {
		SenderThread senderThread = new SenderThread(host,port);
		ReceiverThread receiverThread = new ReceiverThread(port);
		KeepAliveThread keepAliveThread = new KeepAliveThread();
		Thread t1 = new Thread(receiverThread);
		Thread t2 = new Thread(senderThread);
		Thread t3 = new Thread(keepAliveThread);
		t1.start();
		t2.start();
		t3.start();
		
		t1.join();
		t2.join();
		t3.join();
    }
}
