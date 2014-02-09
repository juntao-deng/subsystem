package net.juniper.jmp.tracer.handler.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;

import net.juniper.jmp.tracer.IMonitorHandler;
import net.juniper.jmp.tracer.ITracerHandler;
import net.juniper.jmp.tracer.dumper.IThreadInfoDumper;
import net.juniper.jmp.tracer.dumper.impl.LoggerProvider;
import net.juniper.jmp.tracer.dumper.impl.ThreadInfoDumper;
import net.juniper.jmp.tracer.dumper.info.ThreadInfoDump;
import net.juniper.jmp.tracer.info.ThreadInfo;
import net.juniper.jmp.tracer.uid.UniqueIdGenerator;

import org.apache.log4j.Logger;
/**
 * 
 * @author juntaod
 *
 */
public class TracerHandlerImpl implements ITracerHandler, IMonitorHandler, IActionRecord{
    private static Logger logger = Logger.getLogger(TracerHandlerImpl.class);
    private ThreadLocal<ThreadInfo> threadLocal = new ThreadLocal<ThreadInfo>();
    
    /**
     * this map record all current alive thread infos 
     */
    private Map<String, ThreadInfo> threadInfoMap = new ConcurrentHashMap<String, ThreadInfo>();
    
    /**
     * The logger dumper
     */
    private IThreadInfoDumper dumper = new ThreadInfoDumper();
    
    /**
     * the id for action record, it's used to generate record files and extract informations from record files
     */
    private String recordId;
    
    private Stack<ThreadInfo> asyncThreadInfoStack = new Stack<ThreadInfo>();
    
    public void startTracer(){
        if(threadLocal.get() != null){
            throw new RuntimeException("Shouldn't call here 'startTracer', start/end must appear not in pair elsewhere");
        }
        
        //generate the unique 20 ID
        String callId = UniqueIdGenerator.getInstance().nextOid();
        ThreadInfo info = new ThreadInfo(callId);
        threadInfoMap.put(callId, info);
        threadLocal.set(info);
        info.start();
    }
    
    public void endTracer(){
        ThreadInfo info = threadLocal.get();
        if(info != null){
            threadLocal.remove();
            info.end();
            if(info.getAsyncCallId() != null){
                stackThread(info);
            }
            dumpInfo(info);
            threadInfoMap.remove(info.getCallId());
        }
        else{
            throw new RuntimeException("Shouldn't call here 'endTracer', start/end must appear not in pair elsewhere");
        }
    }
    
    private void stackThread(ThreadInfo info) {
        if(asyncThreadInfoStack.size() > 0)
            asyncThreadInfoStack.pop();
        asyncThreadInfoStack.add(info);
    }

    private void dumpInfo(ThreadInfo info) {
        dumper.dump(info);
    }

    @Override
    public void setUserId(String userId) {
        ThreadInfo info = threadLocal.get();
        if(info != null)
            info.setUserId(userId);
    }

    @Override
    public void setClientIp(String clientIp) {
        ThreadInfo info = threadLocal.get();
        if(info != null)
            info.setClientIp(clientIp);
    }

    @Override
    public void setCallPath(String path) {
        ThreadInfo info = threadLocal.get();
        if(info != null)
            info.setStagePath(path);
    }

    @Override
    public void setCallMethod(String method) {
        ThreadInfo info = threadLocal.get();
        if(info != null){
            info.setStageMethod(method);
        }
    }

    @Override
    public void setRequestBytes(int num) {
        ThreadInfo info = threadLocal.get();
        if(info != null)
            info.setRequestBytes(num);
    }

    @Override
    public void setReponseBytes(int num) {
        ThreadInfo info = threadLocal.get();
        if(info != null)
            info.setResponseBytes(num);
    }

    @Override
    public void startStage(String stageName) {
        ThreadInfo info = threadLocal.get();
        if(info != null)
            info.newStage(stageName);
    }

    @Override
    public void endStage() {
        ThreadInfo info = threadLocal.get();
        if(info != null)
            info.endStage();
    }

    @Override
    public void startSql(String connId) {
        ThreadInfo info = threadLocal.get();
        if(info != null)
            info.addSql(connId, "");
    }

    @Override
    public void endSql(int resultCount) {
        ThreadInfo info = threadLocal.get();
        if(info != null)
            info.endSql(resultCount);
    }

    @Override
    public void endSql() {
        ThreadInfo info = threadLocal.get();
        if(info != null)
            info.endSql(0);
    }

    @Override
    public ThreadInfoDump[] getThreadInfoDumps() {
        if(threadInfoMap == null || threadInfoMap.size() == 0)
            return null;
        List<ThreadInfoDump> currList = new ArrayList<ThreadInfoDump>();
        Iterator<Entry<String, ThreadInfo>> entryIt = threadInfoMap.entrySet().iterator();
        while(entryIt.hasNext()){
            Entry<String, ThreadInfo> entry = entryIt.next();
            currList.add((ThreadInfoDump) entry.getValue().dump());
        }
        
        ThreadInfo[] asyncTds = asyncThreadInfoStack.toArray(new ThreadInfo[0]);
        for(int i = 0; i < asyncTds.length; i ++){
            ThreadInfoDump asyncTd = (ThreadInfoDump) asyncTds[i].dump();
            asyncTd.setAlreadyEnded(true);
            currList.add(asyncTd);
        }
        return currList.toArray(new ThreadInfoDump[0]);
    }

    @Override
    public void ready() {
        RequestManager.startListening();
        logger.debug(this.getClass().getName() + " is ready");
    }

    @Override
    public void suspend() {
        threadInfoMap.clear();
        threadLocal.remove();
        RequestManager.endListening();
        logger.debug(this.getClass().getName() + " is suspended");
    }

    @Override
    public void setName(String name) {
        ThreadInfo info = threadLocal.get();
        if(info != null)
          info.setStageName(name);
    }

    @Override
    public boolean traced() {
        return threadLocal.get() != null;
    }

    @Override
    public boolean tryStartTracer(String name) {
        boolean traced = traced();
        if(traced)
            startStage(name);
        else{
            startTracer();
            setName(name);
        }
        return traced;
    }

    @Override
    public void tryEndTracer(boolean traced) {
        if(traced)
            this.endStage();
        else
            this.endTracer();
    }

    @Override
    public void appendSql(String sql) {
        ThreadInfo info = threadLocal.get();
        if(info != null)
            info.appendSql(sql);
    }

    @Override
    public void plusSqlResultCount() {
        ThreadInfo info = threadLocal.get();
        if(info != null)
            info.plusSqlResultCount();
    }

    @Override
    public void record(String recordId) {
        this.recordId = recordId;
        new Thread(new RecordEnsureCloseThread(recordId)).start();
    }

    @Override
    public void endRecord(String recordId) {
        if(recordId.equals(this.recordId)){
            LoggerProvider.endRecordLogger(recordId);
            this.recordId = null;
        }
    }

    @Override
    public String getRecordId() {
        return recordId;
    }

    @Override
    public void attachToAsyncId(String id) {
        ThreadInfo info = threadLocal.get();
        if(info != null)
            info.attachToAsyncId(id);
    }

    @Override
    public void setAsyncId(String asyncId) {
        ThreadInfo info = threadLocal.get();
        if(info != null)
            info.setAsyncId(asyncId);
        //if came to this path, we should find what thread is not traced
        else{
            try{
                this.startTracer();
                this.setName(ITracerHandler.TYPE_NOTIFICATION_SENDER);
                this.setAsyncId(asyncId);
            }
            finally{
                this.endTracer();
            }
        }
    }

    @Override
    public String getCallId() {
        ThreadInfo info = threadLocal.get();
        return info == null ? null : info.getCallId();
    }

    @Override
    public String getAsyncId() {
        ThreadInfo info = threadLocal.get();
        if(info != null)
            return info.getAsyncId();
        return null;
    }
}
