package net.juniper.jmp.tracer.sharemem;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

import net.juniper.jmp.tracer.common.MonitorConstants;

import org.apache.log4j.Logger;
/**
 * Monitoring the action request
 * @author juntaod
 *
 */
public class RequestMonitor extends Thread{
    private static Logger logger = Logger.getLogger(RequestMonitor.class);
    private RandomAccessFile raFile;
    private FileChannel raChannel;
    private MappedByteBuffer mappedBuffer;
    private Integer action;
//    private Integer lastActionSeq = -1;
    private Producer producer;
    
    public RequestMonitor(Integer action){
        try{
            this.action = action;
            this.producer = new Producer(action);
            String dir = MonitorConstants.SHARE_BASE_DIR;
            String fileName = dir + MonitorConstants.SHARE_SIGN_NAME + action;
            raFile = new RandomAccessFile(fileName, "rw");
            raChannel = raFile.getChannel();
            mappedBuffer = raChannel.map(FileChannel.MapMode.READ_WRITE, 0, MonitorConstants.SHARE_SIGN_SIZE).load();
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
    }

    public void run(){
        while(true){
            if(this.isInterrupted()){
                releaseResource();
                return;
            }
            FileLock lock = null;
            try{
                Thread.sleep(10);
                lock = raChannel.tryLock(0 , MonitorConstants.SHARE_SIGN_SIZE, false);
                //Another thread is writing, wait
                if(lock == null){
                    continue;
                }
                mappedBuffer.rewind();
                Integer action = mappedBuffer.getInt();
                if(action <= 0){
                    continue;
                }
                if(this.action != action){
                    throw new IllegalArgumentException("wrong chanel for action:" + action);
                }
                Integer actionSeq = mappedBuffer.getInt();
                Integer actionLength = mappedBuffer.getInt();
                String actionParam = null;
                if(actionLength > 0){
                    byte[] bytes = new byte[actionLength];
                    mappedBuffer.get(bytes);
                    actionParam = new String(bytes);
                }
                mappedBuffer.clear();
                mappedBuffer.putInt(-1);
                mappedBuffer.putInt(-1);
                if(actionSeq >= 0)
//                if(actionSeq > lastActionSeq){
                    System.out.println("Got signal, " + action + " and sequence:" + actionSeq);
//                  lastActionSeq = actionSeq;
                    producer.execute(actionSeq, actionParam);
//                }
            }
            catch(InterruptedException e){
                releaseResource();
                return;
            }
            catch(Exception e){
                releaseResource();
               logger.error(e.getMessage(), e);
               return;
            }
            finally{
                if(lock != null)
                    try {
                        lock.release();
                    } 
                    catch (IOException e) {
                        logger.error(e.getMessage(), e);
                    }
            }
        }

    }
    
    private void releaseResource() {
        if(raFile != null){
            try {
                raFile.close();
            } 
            catch (IOException e) {
                 logger.error(e.getMessage(), e);
            }
        }
        if(producer != null){
            producer.releaseResource();
        }
    }
} 