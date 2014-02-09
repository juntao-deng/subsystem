package net.juniper.jmp.tracer.dumper.info;

import net.juniper.jmp.tracer.common.MonitorConstants;


/**
 * 
 * @author juntaod
 *
 */
public class ThreadInfoDump extends StageInfoBaseDump{
    private static final long serialVersionUID = -6676919119955470929L;
    private static final String SYS = "#SYS#";
    private static final String SERVER = "SERVER";
    private String userId;
    private String clientIp;
    private long threadId;
    private int requestBytes;
    private int responseBytes;
    private String asyncId;
    private String attachToAsyncId;
    private String asyncCallId;
    /**
     * sign if the thread is already ended.it only for async message joint.
     */
    private boolean alreadyEnded = false;
    public ThreadInfoDump(String callId, String stageName, long threadId) {
        super(null, callId, stageName);
        this.threadId = threadId;
    }
    
    private ThreadInfoDump() {
        super();
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
    
    public ThreadInfoDump detach() {
      ThreadInfoDump t = (ThreadInfoDump) super.detach();
      if(t.getUserId() == null)
        t.setUserId(SYS);
      if(t.getClientIp() == null)
        t.setClientIp(SERVER);
      return t;
    }
    
    public static ThreadInfoDump fromString(String source){
        if(source == null || source.equals(""))
            return null;
        ThreadInfoDump stageInfo = new ThreadInfoDump();
        String[] segs = source.split(MonitorConstants.LOG_SEGMENT);
        for(int i = 0; i < segs.length; i ++){
            String seg = segs[i];
            String[] pair = getPair(seg);
            if(pair == null)
                continue;
            if(pair[0].equals("userId"))
                stageInfo.userId = pair[1];
            else if(pair[0].equals("clientIp"))
                stageInfo.clientIp = pair[1]; 
            else if(pair[0].equals("threadId"))
                stageInfo.threadId = getInteger(pair[1]);
            else if(pair[0].equals("requestBytes"))
                stageInfo.requestBytes = getInteger(pair[1]);
            else if(pair[0].equals("responseBytes"))
                stageInfo.responseBytes = getInteger(pair[1]);
            else if(pair[0].equals("asyncId")){
                String asyncId = pair[1];
                if(asyncId.equals("") || asyncId.equals("null"))
                    asyncId = null;
                stageInfo.asyncId = asyncId;
            }
            else if(pair[0].equals("attachId")){
                String attachId = pair[1];
                if(attachId.equals("") || attachId.equals("null"))
                    attachId = null;
                stageInfo.attachToAsyncId = attachId;
            }
            else if(pair[0].equals("asyncCallId")){
                String asyncCallId = pair[1];
                if(asyncCallId.equals("") || asyncCallId.equals("null"))
                    asyncCallId = null;
                stageInfo.asyncCallId = asyncCallId;
            }
        }
        StageInfoBaseDump.doFromString(source, stageInfo);
        return stageInfo;
    }

    private static String[] getPair(String s){
        int index = s.indexOf(MonitorConstants.LOG_SEP);
        if(index != -1){
            String[] pair = new String[2];
            pair[0] = s.substring(0, index);
            pair[1] = s.substring(index + MonitorConstants.LOG_SEP.length());
            return pair;
        }
        return null;
    }
    
    private static Integer getInteger(String s) {
        try{
            return Integer.parseInt(s);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    public String getAsyncId() {
        return asyncId;
    }

    public void setAsyncId(String asyncId) {
        this.asyncId = asyncId;
    }

    public String getAttachToAsyncId() {
        return attachToAsyncId;
    }

    public void setAttachToAsyncId(String attachToAsyncId) {
        this.attachToAsyncId = attachToAsyncId;
    }

    public String getAsyncCallId() {
        return asyncCallId;
    }

    public void setAsyncCallId(String asyncCallId) {
        this.asyncCallId = asyncCallId;
    }

    public boolean isAlreadyEnded() {
        return alreadyEnded;
    }

    public void setAlreadyEnded(boolean alreadyEnded) {
        this.alreadyEnded = alreadyEnded;
    }
}
 
