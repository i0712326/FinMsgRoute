package fin.msg.route.rsc;

import java.util.concurrent.BlockingQueue;

import org.jpos.iso.ISOMsg;
import org.springframework.stereotype.Service;

@Service("queueProcessor")
public class QueueProcessor {
	public synchronized ISOMsg fetch() throws InterruptedException {
		BlockingQueue<ISOMsg> queue = ApplicationQueue.getQueue();
		return queue.take();
	}

	public synchronized void put(ISOMsg data) throws InterruptedException {
		BlockingQueue<ISOMsg> queue = ApplicationQueue.getQueue();
		queue.put(data);
	}

	public synchronized boolean isExist() {
		BlockingQueue<ISOMsg> queue = ApplicationQueue.getQueue();
		return !queue.isEmpty();
	}
	
	
}
