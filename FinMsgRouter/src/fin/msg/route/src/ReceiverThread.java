package fin.msg.route.src;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.com.app.core.jpos.PackagerFactory;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOPackager;

import fin.msg.route.rsc.QueueProcessor;

public class ReceiverThread implements Runnable {
	private static ISOPackager p = PackagerFactory.getPackager();
	private int port;
	public ReceiverThread(int port){
		this.port = port;
	}
	@Override
	public void run() {
		 ServerSocket ss;
		 QueueProcessor queueProcessor = new QueueProcessor();
		try {
			ss = new ServerSocket(port);
			while (true) {
				Socket s = ss.accept();
				byte[] data = readData(s);
				ISOMsg isoMsg = new ISOMsg();
				isoMsg.setPackager(p);
				isoMsg.unpack(data);
				queueProcessor.put(isoMsg);;
			}
         } catch (IOException e) {
             e.printStackTrace();
         } catch (ISOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private byte[] readData(Socket socket){
		try{
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			byte[] buffer = new byte[4];
			dis.read(buffer);
			int len = Integer.parseInt(new String(buffer));
			byte[] data = new byte[len];
			if(len>0){
				dis.readFully(data);
				return data;
			}
			return null;
		}
		catch(IOException e){
			e.printStackTrace();
			return null;
		}
	}
}
