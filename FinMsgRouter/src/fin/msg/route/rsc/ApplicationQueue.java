package fin.msg.route.rsc;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.jpos.iso.ISOMsg;

public class ApplicationQueue {
	private static BlockingQueue<ISOMsg> queue = new ArrayBlockingQueue<ISOMsg>(500);
	
	public static BlockingQueue<ISOMsg> getQueue(){
		return queue;
	}
}
