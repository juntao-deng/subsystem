package net.juniper.jmp.tracer.info;

import java.io.Serializable;

import net.juniper.jmp.tracer.common.MonitorConstants;
import net.juniper.jmp.tracer.dumper.info.SqlInfoDump;
/**
 * 
 * @author juntaod
 *
 */
public class SqlInfo implements Serializable, Cloneable, ILogable, ILifecycle {
    private static final long serialVersionUID = 4205057275187923983L;
    private String connId;
    private String sql;
    private transient StageInfoBase parent;
    private long startTime;
    private long endTime;
    private boolean ended = false;
    private String callId;
    private int resultCount = 0;
    private int duringTime;
    public SqlInfo(StageInfoBase parent, String connId, String sql) {
        this.connId = connId;
        this.sql = sql;
        this.parent = parent;
        this.callId = this.parent.getCallId();
    }
    
    public SqlInfo() {
        
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
    
    public String toLog(){
        StringBuffer buf = new StringBuffer();
        buf.append(MonitorConstants.LOG_PREFIX).
        append("callId").append(MonitorConstants.LOG_SEP).append(callId).append(MonitorConstants.LOG_SEGMENT).
        append("sql").append(MonitorConstants.LOG_SEP).append(sql).append(MonitorConstants.LOG_SEGMENT).
        append("connId").append(MonitorConstants.LOG_SEP).append(connId).append(MonitorConstants.LOG_SEGMENT).
        append("duringTime").append(MonitorConstants.LOG_SEP).append((endTime - startTime)).append(MonitorConstants.LOG_SEGMENT).
        append("resultCount").append(MonitorConstants.LOG_SEP).append(resultCount);
        return buf.toString();
    }

    @Override
    public void start() {
        this.startTime = System.currentTimeMillis();
    }

    @Override
    public void end() {
        if(ended){
            return;
            //throw new IllegalArgumentException("wrong operation, may be not call addSql and endSql in pair elsewhere");
        }
        ended = true;
        this.endTime = System.currentTimeMillis();
    }
    
    public boolean isEnded() {
        return ended;
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

    @Override
    public Object dump() {
        SqlInfoDump sql = new SqlInfoDump(this.callId, this.connId, this.sql);
        if(this.getEndTime() == 0)
            sql.setDuration(System.currentTimeMillis() - this.getStartTime());
        else
            sql.setDuration(this.getDuration());
        sql.setResultCount(this.getResultCount());
        return sql;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getCallId() {
        return callId;
    }

    public void setCallId(String callId) {
        this.callId = callId;
    }

    public long getDuration() {
        if(duringTime != 0)
            return duringTime;
        return endTime - startTime;
    }

    public void setResultCount(int resultCount) {
        this.resultCount = resultCount;
    }

    public int getResultCount() {
        return resultCount;
    }
}
