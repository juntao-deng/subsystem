package net.juniper.jmp.tracer.dumper.info;

import net.juniper.jmp.tracer.common.MonitorConstants;
/**
 * 
 * @author juntaod
 *
 */
public class SqlInfoDump extends AbstractDumpObject implements Cloneable{
    private static final long serialVersionUID = 4947740328426908979L;
    private String connId;
    private String sql;
    private long duration;
    private String callId;
    private int resultCount;
    private String stagePath;
    private String stageMethod;
    private String stageName;
    private String startTs;
    private String endTs;
    private Integer id;
    
    public SqlInfoDump(String callId, String connId, String sql) {
        this.connId = connId;
        this.sql = sql;
        this.callId = callId;
    }

    private SqlInfoDump() {
        
    }
    
    public String getConnId() {
        return connId;
    }

    public void setConnId(String connId) {
        this.connId = connId;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public Object clone() {
        try {
            return super.clone();
        } 
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String getCallId() {
        return callId;
    }

    public void setCallId(String callId) {
        this.callId = callId;
    }

    public long getDuration() {
        return duration;
    }
    
    public void setDuration(long duration){
        this.duration = duration;
    }

    public void setResultCount(int resultCount) {
        this.resultCount = resultCount;
    }

    public int getResultCount() {
        return resultCount;
    }

    public static SqlInfoDump fromString(String source) {
        if(source == null || source.equals(""))
            return null;
        SqlInfoDump sqlInfo = new SqlInfoDump();
        String[] segs = source.split(MonitorConstants.LOG_SEGMENT);
        for(int i = 0; i < segs.length; i ++){
            String seg = segs[i];
            String[] pair = getPair(seg);
            if(pair == null)
                continue;
            if(pair[0].equals(MonitorConstants.CALL_ID_PRE))
                sqlInfo.callId = pair[1];
            else if(pair[0].equals("sql"))
                sqlInfo.sql = pair[1];
            else if(pair[0].equals("connId"))
                sqlInfo.connId = pair[1];
            else if(pair[0].equals("duringTime"))
                sqlInfo.duration = Integer.parseInt(pair[1]);
            else if(pair[0].equals("resultCount"))
                sqlInfo.resultCount = Integer.parseInt(pair[1]);
            else if(pair[0].equals("startts"))
                sqlInfo.startTs = pair[1];
            else if(pair[0].equals("endts"))
                sqlInfo.endTs = pair[1];
        }
        return sqlInfo;
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

    public String getStagePath() {
        return stagePath;
    }

    public void setStagePath(String stagePath) {
        this.stagePath = stagePath;
    }

    public String getStageMethod() {
        return stageMethod;
    }

    public void setStageMethod(String stageMethod) {
        this.stageMethod = stageMethod;
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    public String getStartTs() {
        return startTs;
    }

    public void setStartTs(String startTs) {
        this.startTs = startTs;
    }

    public String getEndTs() {
        return endTs;
    }

    public void setEndTs(String endTs) {
        this.endTs = endTs;
    }

    @Override
    public SqlInfoDump detach() {
        SqlInfoDump sql = (SqlInfoDump) this.clone();
        sql.userObject = null;
        return sql;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
