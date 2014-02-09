package net.juniper.jmp.tracer.sharemem;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import net.juniper.jmp.tracer.common.MonitorConstants;
import net.juniper.jmp.tracer.executor.ActionExecutorFactory;
import net.juniper.jmp.tracer.lock.LockHelper;

import org.apache.log4j.Logger;
/**
 * Content producer, outputting to shared channel 
 * @author juntaod
 *
 */
public class Producer implements IProducer{
    private static Logger logger = Logger.getLogger(Producer.class);
    private RandomAccessFile raFile;
    private FileChannel raChannel;
    private MappedByteBuffer mappedBuffer;
    private Integer action;
    public Producer(Integer action){
        try{
            this.action = action;
            String dir = MonitorConstants.SHARE_BASE_DIR;
            String fileName = dir + MonitorConstants.SHARE_CONTENT_NAME + action;
            raFile = new RandomAccessFile(fileName, "rw");
            raChannel = raFile.getChannel();
            mappedBuffer = raChannel.map(FileChannel.MapMode.READ_WRITE, 0, MonitorConstants.SHARE_CONTENT_SIZE).load();
        }
        catch(IOException e){
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void execute(Integer actionSeq, String actionParam) {
        int times = 0;
        while(times < 100){
//          FileLock lock = null;
            boolean lock = false;
            try{
                if(times != 0)
                    Thread.sleep(10);
                times ++;
                lock = LockHelper.getLock(mappedBuffer, LockHelper.LOCK_WRITE);
                //lock = raChannel.tryLock(0 , MonitorConstants.SHARE_CONTENT_SIZE, false);
                if(!lock){
                    continue;
                }
                ActionExecutorFactory.handle(action, actionSeq, actionParam, raChannel, mappedBuffer);
                return;
            }
            catch(Exception e){
                 logger.error(e.getMessage(), e);
            }
            finally{
                if(lock)
                    LockHelper.releaseLock(mappedBuffer);
            }
        }
    }

    public void releaseResource() {
        if(raFile != null){
            try {
                raFile.close();
            } 
            catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }
} 