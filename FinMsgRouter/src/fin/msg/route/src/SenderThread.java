package fin.msg.route.src;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;
import org.com.app.core.jpos.PackagerFactory;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOPackager;

import fin.msg.route.rsc.QueueProcessor;

public class SenderThread implements Runnable {
	private Logger logger = Logger.getLogger(getClass());
	private static ISOPackager p = PackagerFactory.getPackager();
	private int port;
	private String host;
	public SenderThread(String host, int port){
		this.host = host;
		this.port = port;
	}
	@Override
	public void run() {
		Socket s = null;
		DataOutputStream out = null;
		QueueProcessor queueProcessor = new QueueProcessor();
		try {
			s = new Socket(host, port);
			out = new DataOutputStream(
					s.getOutputStream());
            while (true) {
               if(queueProcessor.isExist()){
            	   ISOMsg isoMsg = queueProcessor.fetch();
            	   isoMsg.setPackager(p);
            	   byte[] data = isoMsg.pack();
            	   out.write(data);
               }
            }
        } catch (UnknownHostException e) {
        	logger.debug("Specified Host is down", e);
        } catch (IOException e) {
        	logger.debug("Socket is not open or could not access to file", e);
        } catch (InterruptedException e) {
			logger.debug("Signal interrupted from other thread", e);
		} catch (ISOException e) {
			logger.debug("Could not parse ISO Message", e);
		}
		finally{
			try {
				out.close();
				s.close();
			} catch (IOException e) {
				logger.debug("IO Exception throwed while try to close socket",e);
			}
		}
	}

}
