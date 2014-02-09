package net.juniper.jmp.tracer.info;

import java.util.Stack;

import net.juniper.jmp.tracer.common.MonitorConstants;
import net.juniper.jmp.tracer.dumper.info.StageInfoBaseDump;
import net.juniper.jmp.tracer.dumper.info.ThreadInfoDump;

/**
 * 
 * @author juntaod
 *
 */
public class ThreadInfo extends StageInfoBase {
    private static final long serialVersionUID = -7369758295222978235L;
    private static final String SYS = "#SYS#";
//  private static final String SERVER = "SERVER";
    private String userId;
    private String clientIp;
    private long threadId;
    private int requestBytes;
    private int responseBytes;
    private String asyncId;
    private String attachToAsyncId;
    private String asyncCallId;
    //The stages are currently working
    private transient Stack<StageInfoBase> stageStack;
    public ThreadInfo(String sequenceId) {
        super(sequenceId, Thread.currentThread().getName());
        this.threadId = Thread.currentThread().getId();
    }
    
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public long getThreadId() {
        return threadId;
    }
    public void setThreadId(long threadId) {
        this.threadId = threadId;
    }
    public int getRequestBytes() {
        return requestBytes;
    }
    public void setRequestBytes(int requestBytes) {
        this.requestBytes = requestBytes;
    }
    public int getResponseBytes() {
        return responseBytes;
    }
    public void setResponseBytes(int responseBytes) {
        this.responseBytes = responseBytes;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public void setStagePath(String stagePath) {
        StageInfoBase top = getTopStage();
        if(top != null)
            top.setStagePath(stagePath);
        else
            super.setStagePath(stagePath);
    }
    
    public void setStageMethod(String stageMethod) {
        StageInfoBase top = getTopStage();
        if(top != null)
            top.setStageMethod(stageMethod);
        else
            super.setStageMethod(stageMethod);
    }
    
    public void newStage(String name){
        StageInfoBase top = getTopStage();
        if(top != null)
            top.newStage(name);
        else
            super.newStage(name);
    }
    
    public void endStage(){
        StageInfoBase top = getTopStage();
        if(top != null){
            StageInfoBase parent = top.getParent();
            if(parent instanceof ThreadInfo)
                ((ThreadInfo)parent).doEndStage();
            else
                parent.endStage();
        }
        else
            doEndStage();
    }
    
    private void doEndStage() {
        super.endStage();
    }
    
    public void addSql(String connId, String sql) {
        StageInfoBase top = getTopStage();
        if(top != null)
            top.addSql(connId, sql);
        else
            super.addSql(connId, sql);
    }
    
    public void endSql(int resultCount) {
        StageInfoBase top = getTopStage();
        if(top != null)
            top.endSql(resultCount);
        else
            super.endSql(resultCount);
    }
    
    private StageInfoBase getTopStage() {
        return (stageStack == null || stageStack.size() == 0 )? null : stageStack.peek();
    }

    public void addStageToStack(StageInfoBase top) {
        if(stageStack == null)
            stageStack = new Stack<StageInfoBase>();
        stageStack.add(top);
    }
    
    public StageInfoBase popStageFromStack() {
        if(stageStack == null)
            throw new IllegalArgumentException("wrong operation, may be not call addStage and endStage in pair elsewhere");
        return stageStack.pop(); 
    }

    @Override
    protected String processImportantInfo() {
        String user = this.userId;
        if(user == null || user.equals(""))
            user = SYS;
        StringBuffer buf = new StringBuffer();
        buf.append("userId").append(MonitorConstants.LOG_SEP).append(user).append(MonitorConstants.LOG_SEGMENT).
        append("clientIp").append(MonitorConstants.LOG_SEP).append(this.clientIp).append(MonitorConstants.LOG_SEGMENT).
        append("threadId").append(MonitorConstants.LOG_SEP).append(this.getThreadId()).append(MonitorConstants.LOG_SEGMENT).
        append("requestBytes").append(MonitorConstants.LOG_SEP).append(this.requestBytes).append(MonitorConstants.LOG_SEGMENT).
        append("responseBytes").append(MonitorConstants.LOG_SEP).append(this.responseBytes).append(MonitorConstants.LOG_SEGMENT).
        append("asyncId").append(MonitorConstants.LOG_SEP).append(asyncId).append(MonitorConstants.LOG_SEGMENT).
        append("attachId").append(MonitorConstants.LOG_SEP).append(attachToAsyncId).append(MonitorConstants.LOG_SEGMENT).
        append("asyncCallId").append(MonitorConstants.LOG_SEP).append(asyncCallId).append(MonitorConstants.LOG_SEGMENT);
        return buf.toString();
    }

    @Override
    protected StageInfoBaseDump createStageInfoDump() {
        ThreadInfoDump threadInfo = new ThreadInfoDump(this.getCallId(), this.getStageName(), this.getThreadId());
        threadInfo.setUserId(this.getUserId());
        threadInfo.setClientIp(this.getClientIp());
        threadInfo.setRequestBytes(this.getRequestBytes());
        threadInfo.setResponseBytes(this.getResponseBytes());
        threadInfo.setAsyncCallId(this.asyncCallId);
        threadInfo.setAsyncId(this.asyncId);
        threadInfo.setAttachToAsyncId(this.attachToAsyncId);
        return threadInfo;
    }

    public void plusSqlResultCount() {
        StageInfoBase top = getTopStage();
        if(top != null)
            top.plusSqlResultCount();
        else
            super.plusSqlResultCount();
    }

    public void appendSql(String sql) {
        StageInfoBase top = getTopStage();
        if(top != null)
            top.appendSql(sql);
        else
            super.appendSql(sql);
    }

    
    public void attachToAsyncId(String asyncId) {
        this.attachToAsyncId = asyncId;
    }

//    public void setAsyncId(String callId, String asyncId) {
//        this.asyncCallId = callId;
//        this.asyncId = asyncId;
//        StageInfoBase top = getTopStage();
//        if(top != null)
//            top.setAsync(true);
//        else
//            super.setAsync(true);
//    }

    public String getAsyncId() {
        return asyncId;
    }

    public void setAsyncId(String asyncId) {
        StageInfoBase top = getTopStage();
        if(top != null){
            this.asyncCallId = top.getCallId();
            top.setAsync(true);
        }
        else{
            this.asyncCallId = this.getCallId();
            super.setAsync(true);
        }
        this.asyncId = asyncId;
    }

    public String getAsyncCallId() {
        return asyncCallId;
    }

    public void setAsyncCallId(String asyncCallId) {
        this.asyncCallId = asyncCallId;
    }
}
 
