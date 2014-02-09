package net.juniper.jmp.tracer.info;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import net.juniper.jmp.tracer.common.MonitorConstants;
import net.juniper.jmp.tracer.dumper.info.StageInfoBaseDump;
/**
 * Info value object, it will be transfered to clients or logged to files.
 * The object's lifecycle starts from start() method and ends with dump(). 
 * @author juntaod
 *
 */
public class StageInfoBase implements Serializable, Cloneable, ILifecycle, ILogable{
    private static final long serialVersionUID = 1592600708234846351L;
    private transient StageInfoBase parent;
    private transient ThreadInfo threadInfo;
    private transient Thread currentThread;
    private transient StackTraceElement topTraceElement;
    private String callId;
    private int sumSqlCount = 0;
    private int sumStageCount = 0;
    private long startTime;
    private long endTime;
    private List<StageInfoBase> childrenStageList;
    private HashSet<String> connIds;
    private List<SqlInfo> sqlInfoList;
    private MethodInfo methodStack;
    private String stageName;
    private String stagePath;
    private String stageMethod;
    private boolean async;
//    private String serverIp;
    //detachInfo
//    private String detachedSql;
//    private int sqls;
//    private int conns;
//    private int stages;
    public StageInfoBase(ThreadInfo threadInfo, StageInfoBase parent, String name){
        this.parent = parent;
        this.callId = generateId();
        this.stageName = name;
        this.threadInfo = threadInfo;
    }
    
    public StageInfoBase(String id, String name){
        this.callId = id;
        this.stageName = name;
    }
    
    private String generateId() {
        List<StageInfoBase> list = this.parent.getChildrenStageList();
        if(list == null){
            return this.parent.getCallId() + "_s0";
        }
        return this.parent.getCallId() + "_s" + list.size();
    }

    public void start() {
        startTime = System.currentTimeMillis();
        this.currentThread = Thread.currentThread();
        StackTraceElement topEle = this.parent == null ? null : this.parent.topTraceElement;
        this.topTraceElement = MonitorHelper.getTopTraceElement(topEle);
//        this.serverIp = IpAddressHelper.getIpAddress();
    }
    
    public MethodInfo getMethodStack() {
        if(methodStack == null){
            StackTraceElement topEle = this.parent == null ? null : this.parent.topTraceElement;
            String method = MonitorHelper.dumpMethodStack(topEle, this.currentThread);
            methodStack = new MethodInfo(this, method);
        }
        return methodStack;
    }

    public void setMethodStack(MethodInfo methodStack) {
        this.methodStack = methodStack;
    }

    public void end() {
        endTime = System.currentTimeMillis();
        getMethodStack();
    }
    public List<SqlInfo> getSqlInfoList() {
        return sqlInfoList;
    }
    
    public void setSqlInfoList(List<SqlInfo> sqlInfo) {
        this.sqlInfoList = sqlInfo;
    }
    
    public StageInfoBase getParent() {
        return parent;
    }
    public void setParent(StageInfoBase parent) {
        this.parent = parent;
    }
    public int getSumSqlCount() {
        return sumSqlCount;
    }
    public void setSumSqlCount(int sumSqlCount) {
        this.sumSqlCount = sumSqlCount;
    }
    public int getSumStageCount() {
        return sumStageCount;
    }
    public void setSumStageCount(int sumStageCount) {
        this.sumStageCount = sumStageCount;
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

    public List<StageInfoBase> getChildrenStageList() {
        return childrenStageList;
    }
    
    public void setChildrenStageList(List<StageInfoBase> list) {
        this.childrenStageList = list;
    }

    public String getCallId() {
        return callId;
    }

    public void setCallId(String callId) {
        this.callId = callId;
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    public String getStagePath() {
        return stagePath;
    }

    public void setStagePath(String stagePath) {
        this.stagePath = stagePath;
    }
    
    public void setStageMethod(String stageMethod) {
        if(this.stageMethod != null)
            this.stageMethod += "," + stageMethod;
        else
            this.stageMethod = stageMethod;
    }
    
    public String getStageMethod() {
        return this.stageMethod;
    }

    public void newStage(String name){
        ThreadInfo ti = getThreadInfo();
        StageInfoBase top = new StageInfoBase(ti, this, name);
        ti.addStageToStack(top);
        if(childrenStageList == null){
            childrenStageList = Collections.synchronizedList(new ArrayList<StageInfoBase>());
        }
        childrenStageList.add(top);
        top.start();
        
        //populate infos to parent
        increaseStageInfo(top);
    }
    
    public void endStage(){
        ThreadInfo ti = getThreadInfo();
        StageInfoBase stageFromStack = ti.popStageFromStack();
        StageInfoBase stageFromList = childrenStageList.get(childrenStageList.size() - 1);
        if(stageFromStack != stageFromList)
            throw new IllegalArgumentException("wrong operation, may be not call addStage and endStage in pair elsewhere");
        stageFromList.end();
    }
    

    public void addSql(String connId, String sql) {
        SqlInfo sqlInfo = new SqlInfo(this, connId, sql);
        if(sqlInfoList == null){
            sqlInfoList = new ArrayList<SqlInfo>();
        }
        int size = sqlInfoList.size();
        if(size > 0){
            SqlInfo curr = sqlInfoList.get(size - 1);
            if(!curr.isEnded())
                return;
        }
        sqlInfoList.add(sqlInfo);
        sqlInfo.start();
        increaseSqlInfo(sqlInfo);
    }
    
    public void endSql(int resultCount) {
        if(sqlInfoList == null)
            throw new IllegalArgumentException("wrong operation, may be not call addSql and endSql in pair elsewhere");
        SqlInfo sql = sqlInfoList.get(sqlInfoList.size() - 1);
        sql.setResultCount(resultCount);
        sql.end();
    }
    
    public void plusSqlResultCount() {
         if(sqlInfoList == null)
             throw new IllegalArgumentException("wrong operation, may be not call addSql and endSql in pair elsewhere");
         SqlInfo sql = sqlInfoList.get(sqlInfoList.size() - 1);
         sql.setResultCount(sql.getResultCount() + 1);
    }
    
    public void appendSql(String sqlstr) {
        if(sqlInfoList == null)
            throw new IllegalArgumentException("wrong operation, may be not call addSql and endSql in pair elsewhere");
        SqlInfo sql = sqlInfoList.get(sqlInfoList.size() - 1);
        String ori = sql.getSql();
        if(ori == null || ori.equals(""))
            ori = sqlstr;
        else{
            ori = ori + "," + sqlstr;
        }
        sql.setSql(ori);
    }
    
    /**
     * sum all sqlinfo to parent
     * @param sqlInfo
     */
    protected void increaseSqlInfo(SqlInfo sqlInfo){
        this.sumSqlCount ++;
        if(this.connIds == null)
            this.connIds = new HashSet<String>();
        connIds.add(sqlInfo.getConnId());
        if(this.parent != null)
            this.parent.increaseSqlInfo(sqlInfo);
    }

    protected void increaseStageInfo(StageInfoBase stage){
        this.sumStageCount ++;
        if(this.parent != null)
            this.parent.increaseStageInfo(stage);
    }
    
    public long getDuration() {
        return endTime - startTime;
    }
    
//    public int getStages() {
//        return this.stages;
//    }
//    
//    public int getSqls() {
//        return sqls;
//    }
//    
//    public int getConns() {
//        return this.conns;
//    }

    @Override
    public String toLog() {
        StringBuffer buf = new StringBuffer();
        buf.append(MonitorConstants.LOG_PREFIX).
        append("callId").append(MonitorConstants.LOG_SEP).append(this.callId).append(MonitorConstants.LOG_SEGMENT).
        append("name").append(MonitorConstants.LOG_SEP).append(this.stageName).append(MonitorConstants.LOG_SEGMENT).
        append("path").append(MonitorConstants.LOG_SEP).append(this.stagePath).append(MonitorConstants.LOG_SEGMENT).
        append("method").append(MonitorConstants.LOG_SEP).append(this.stageMethod).append(MonitorConstants.LOG_SEGMENT).
        append(processImportantInfo());
        long startTime = getStartTime();
        Date d = new Date(startTime);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        long endTime = getEndTime();
        Date endD = new Date(endTime);
        buf.append("startts").append(MonitorConstants.LOG_SEP).append(sdf.format(d)).append(MonitorConstants.LOG_SEGMENT).
        append("endts").append(MonitorConstants.LOG_SEP).append(sdf.format(endD)).append(MonitorConstants.LOG_SEGMENT).
        append("stages").append(MonitorConstants.LOG_SEP).append(childrenStageList == null ? 0 : childrenStageList.size()).append(MonitorConstants.LOG_SEGMENT).
        append("sqls").append(MonitorConstants.LOG_SEP).append(sqlInfoList == null ? 0 : sqlInfoList.size()).append(MonitorConstants.LOG_SEGMENT).
        append("dbconnections").append(MonitorConstants.LOG_SEP).append(connIds == null ? 0 : connIds.size()).append(MonitorConstants.LOG_SEGMENT).
        append("sumsqls").append(MonitorConstants.LOG_SEP).append(sumSqlCount).append(MonitorConstants.LOG_SEGMENT).
        append("sumstages").append(MonitorConstants.LOG_SEP).append(sumStageCount).append(MonitorConstants.LOG_SEGMENT).
        append("duringTime").append(MonitorConstants.LOG_SEP).append(getDuration()).append(MonitorConstants.LOG_SEGMENT).
        append("async").append(MonitorConstants.LOG_SEP).append(async);//.append(MonitorConstants.LOG_SEGMENT);
//        append("serverIp").append(MonitorConstants.LOG_SEP).append(serverIp).append(MonitorConstants.LOG_SEGMENT);
        return buf.toString();
    }
    
    @Override
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append(toLog());
        buf.append("\n");
        buf.append("===============stages==============\n");
        if(this.childrenStageList != null){
            Iterator<StageInfoBase> it = this.childrenStageList.iterator();
            while(it.hasNext()){
                buf.append(it.next().toLog());
                buf.append("\n");
            }
        }
        buf.append("===============sqls===============\n");
        if(this.sqlInfoList != null){
            Iterator<SqlInfo> it = this.sqlInfoList.iterator();
            while(it.hasNext()){
                buf.append(it.next().toLog());
                buf.append("\n");
            }
        }
        buf.append("===============method stack===============\n");
        if(this.methodStack != null)
            buf.append(this.methodStack.toLog());
        return buf.toString();
    }

    protected String processImportantInfo() {
        return "";
    }
    
    protected ThreadInfo getThreadInfo() {
        if(this.threadInfo != null)
            return this.threadInfo;
        return (ThreadInfo) this;
    }

//    public String getServerIp() {
//      return serverIp;
//  }
//
//  public void setServerIp(String serverIp) {
//      this.serverIp = serverIp;
//  }
    
//    public String getDetachedSql() {
//        return this.detachedSql;
//    }
    
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
        StageInfoBaseDump stage = createStageInfoDump();
        //doesn't end
        if(endTime == 0){
            StackTraceElement topEle = this.parent == null ? null : this.parent.topTraceElement;
            String method = MonitorHelper.dumpMethodStack(topEle, this.currentThread);
//            MethodInfo methodStack = new MethodInfo(this, method);
            stage.setDetachedMethod(method);
        }
        else
            stage.setDetachedMethod(this.getMethodStack().getMethod());
        stage.setStagePath(this.getStagePath());
        stage.setStageMethod(this.getStageMethod());
        stage.setSumSqlCount(this.getSumSqlCount());
        stage.setSumStageCount(this.getSumStageCount());
        stage.setSqls(this.getSqlInfoList() == null ? 0 : this.getSqlInfoList().size());
        stage.setStages(this.getChildrenStageList() == null ? 0 : this.getChildrenStageList().size());
        stage.setAsync(async);
//        stage.setServerIp(this.getServerIp());
        if(this.getEndTime() == 0)
            stage.setDuration(System.currentTimeMillis() - this.getStartTime());
        else
            stage.setDuration(this.getDuration());
        
        if(this.childrenStageList != null){
            StageInfoBase[] infos = this.childrenStageList.toArray(new StageInfoBase[0]);
            if(infos != null){
                List<StageInfoBaseDump> infoDumpList = new ArrayList<StageInfoBaseDump>();
                for(int i = 0; i < infos.length; i ++)
                    infoDumpList.add((StageInfoBaseDump) infos[i].dump());
                stage.setChildrenStages(infoDumpList);
            }
        }
        if(this.connIds != null){
            String[] strs = this.connIds.toArray(new String[0]);
            if(strs != null)
                stage.setConns(strs.length);
        }
        
        stage.setDetachedSql(this.detachSql());
//        if(this.sqlInfoList != null){
//            SqlInfo[] infos = this.sqlInfoList.toArray(new SqlInfo[0]);
//            if(infos != null){
//                List<SqlInfoDump> sqlDumpList = new ArrayList<SqlInfoDump>();
//                for(int i = 0; i < infos.length; i ++)
//                    sqlDumpList.add((SqlInfoDump) infos[i].dump());
//                stage.setSqlInfos(sqlDumpList);
//            }
//        }
        return stage;
    }
    
    protected StageInfoBaseDump createStageInfoDump(){
        String parentId = this.parent == null ? null : this.parent.getCallId();
        return new StageInfoBaseDump(parentId, this.getCallId(), this.getStageName());
    }
    
    protected String detachSql() {
        StringBuffer buf = new StringBuffer();
        List<SqlInfo> sqlsList = this.getSqlInfoList();
        if(sqlsList != null){
            SqlInfo[] sqls = sqlsList.toArray(new SqlInfo[0]);
            for(int i = 0; i < sqls.length; i ++){
                SqlInfo sql = sqls[i];
                buf.append("dbconn:" + sql.getConnId() + ",sql:" + sql.getSql() + ",duration:" + sql.getDuration() + ",resultCount:" + sql.getResultCount());
                buf.append("\n");
            }
        }
//        List<StageInfoBase> cstageList = this.getChildrenStageList();
//        if(cstageList != null){
//            Iterator<StageInfoBase> cit = cstageList.iterator();
//            while(cit.hasNext()){
//              StageInfoBase stage = cit.next();
//              String detachSql = stage.getDetachedSql();
//              if(detachSql != null && !detachSql.equals(""))
//                  buf.append(StageInfoBaseDump.$REPLACE_BY + stage.getCallId());
//            }
//        }
        return buf.toString();
    }

    public boolean isAsync() {
        return async;
    }

    public void setAsync(boolean async) {
        this.async = async;
        if(this.parent != null)
            this.parent.setAsync(true);
    }

//    public StageInfoBase detach() {
//        StageInfoBase t = (StageInfoBase) this.clone();
////      t.setMethodStack(null);
//        t.sqls = this.sqlInfoList == null ? 0 : this.sqlInfoList.size();
//        t.stages = this.childrenStageList == null ? 0 : this.childrenStageList.size();
//        t.conns = this.connIds == null ? 0 : this.connIds.size();
//        t.detachedSql = detachSql();
//        t.setChildrenStageList(null);
//        t.setSqlInfoList(null);
//        return t;
//    }
}
