package net.juniper.jmp.tracer.executor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import net.juniper.jmp.tracer.IMonitorHandler;
import net.juniper.jmp.tracer.ThreadTracer;
import net.juniper.jmp.tracer.common.MonitorConstants;
import net.juniper.jmp.tracer.dumper.info.ThreadInfoDump;
import net.juniper.jmp.tracer.lock.LockHelper;

import org.apache.log4j.Logger;
/**
 * return thread information to share channel
 * @author juntaod
 *
 */
public class ThreadInfoActionExecutor implements IActionExecutor {
	private Thread currThread;
	@Override
	public void execute(Integer action, Integer actionSeq, String actionParam, FileChannel raChannel, MappedByteBuffer buf) throws Exception {
		if(currThread != null && currThread.isAlive())
			currThread.interrupt();
		currThread = new Thread(new GetThreadInfoThread(action, actionSeq, raChannel, buf));
		currThread.start();
	}


	@Override
	public boolean handle(Integer action) {
		return action == MonitorConstants.ACTION_THREADINFO;
	}

	@Override
	public boolean instant() {
		return false;
	}

}


class GetThreadInfoThread implements Runnable{
	private static Logger logger = Logger.getLogger(GetThreadInfoThread.class);
	private FileChannel raChannel;
	private MappedByteBuffer buf;
	private Integer action;
	private Integer actionSeq;
	public GetThreadInfoThread(Integer action, Integer actionSeq, FileChannel raChannel, MappedByteBuffer buf){
		this.raChannel = raChannel;
		this.buf = buf;
		this.action = action;
		this.actionSeq = actionSeq;
	}
	@Override
	public void run() {
		byte[] threadBytes = getThreadInfos();
//		FileLock lock = null;
		boolean lock = false;
		int times = 0;
		while(times < 100){
			if(Thread.currentThread().isInterrupted())
				break;
	        times ++;
			try{
	            //lock = raChannel.tryLock(0 , MonitorConstants.SHARE_CONTENT_SIZE, false);
				lock = LockHelper.getLock(buf, LockHelper.LOCK_WRITE);
				int length = 0;
	            if(threadBytes != null && threadBytes.length != 0){
	            	length = threadBytes.length;
	            }
	            buf.clear();
	            buf.putInt(LockHelper.LOCK_WRITE);
	            buf.putInt(action);
	            buf.putInt(actionSeq);
	            buf.putInt(length);
	            if(length != 0)
	            	buf.put(threadBytes);
	            return;
			}
	        catch(Exception e){
	        	logger.error(e.getMessage(), e);
	        }
	        finally{
	        	if(lock)
	        		LockHelper.releaseLock(buf);
//	        	if(lock != null){
//	        		try {
//						lock.release();
//					} 
//	        		catch (IOException e) {
//	        			logger.error(e.getMessage(), e);
//					}
//	        	}
	        }
			
			try {
				Thread.sleep(10);
			} 
			catch (InterruptedException e) {
				break;
			}
		}
	}
	
	private byte[] getThreadInfos() {
		ThreadInfoDump[] infoDumps = ((IMonitorHandler)ThreadTracer.getInstance()).getThreadInfoDumps();
		if(infoDumps != null && infoDumps.length != 0){
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			ObjectOutputStream oout = null;;
			try {
				oout = new ObjectOutputStream(bout);
				oout.writeObject(infoDumps);
				byte[] bytes = bout.toByteArray();
				return bytes;
			} 
			catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
			finally{
				if(oout != null){
					try {
						oout.close();
					} 
					catch (IOException e) {
						logger.error(e.getMessage(), e);
					}
				}
			}
		}
		return null;
	}
}