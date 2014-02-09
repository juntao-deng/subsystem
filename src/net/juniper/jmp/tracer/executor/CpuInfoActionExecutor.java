package net.juniper.jmp.tracer.executor;

import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import net.juniper.jmp.tracer.common.MonitorConstants;
import net.juniper.jmp.tracer.lock.LockHelper;

import org.apache.log4j.Logger;
/**
 * 
 * @author juntaod
 *
 */
public class CpuInfoActionExecutor implements IActionExecutor {
    
//    private Integer currentSeq = -1;
    private Thread currThread;
    @Override
    public void execute(Integer action, Integer actionSeq, String actionParam, FileChannel raChannel, MappedByteBuffer buf)
            throws Exception {
//        currentSeq ++;
        if(currThread != null && currThread.isAlive())
            currThread.interrupt();
        currThread = new Thread(new GetCpuInfoThread(action, actionSeq, raChannel, buf));
        currThread.start();
    }

    @Override
    public boolean handle(Integer action) {
        return action == MonitorConstants.ACTION_CPUINFO;
    }

    @Override
    public boolean instant() {
        return false;
    }
}

class GetCpuInfoThread implements Runnable{
    private static Logger logger = Logger.getLogger(GetCpuInfoThread.class);
    private FileChannel raChannel;
    private MappedByteBuffer buf;
    private Integer action;
    private Integer actionSeq;
    public GetCpuInfoThread(Integer action, Integer actionSeq, FileChannel raChannel, MappedByteBuffer buf){
        this.raChannel = raChannel;
        this.buf = buf;
        this.action = action;
        this.actionSeq = actionSeq;
    }
    @Override
    public void run() {
        float usage = CpuHelper.getCpuRateForLinux();
        System.out.println("usage:" + usage);
//      FileLock lock = null;
        boolean lock = false;
        int times = 0;
        while(times < 100){
            if(Thread.currentThread().isInterrupted())
                break;
            times ++;
            try{
                lock = LockHelper.getLock(buf, LockHelper.LOCK_WRITE);
                //lock = raChannel.tryLock(0 , MonitorConstants.SHARE_CONTENT_SIZE, false);
                buf.clear();
                buf.putInt(LockHelper.LOCK_WRITE);
                buf.putInt(action);
                buf.putInt(actionSeq);
                buf.putFloat(usage);
                return;
            }
            catch(Exception e){
                logger.error(e.getMessage(), e);
            }
            finally{
                if(lock)
                    LockHelper.releaseLock(buf);
//              if(lock != null){
//                  try {
//                      lock.release();
//                  } 
//                  catch (IOException e) {
//                      logger.error(e.getMessage(), e);
//                  }
//              }
            }
            
            try {
                Thread.sleep(10);
            } 
            catch (InterruptedException e) {
                break;
            }
        }
    }
    
}