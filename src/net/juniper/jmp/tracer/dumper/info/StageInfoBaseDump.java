package net.juniper.jmp.tracer.dumper.info;

import java.util.List;

import net.juniper.jmp.tracer.common.MonitorConstants;
/**
 * 
 * @author juntaod
 *
 */
public class StageInfoBaseDump extends AbstractDumpObject implements Cloneable{
    private static final long serialVersionUID = 7027579901888697640L;
    public static final String $REPLACE_BY = "$REPLACE_BY_";
    private String callId;
    private int sumSqlCount = 0;
    private int sumStageCount = 0;
    private String startTs;
    private String endTs;
    private long duration;
    private List<StageInfoBaseDump> childrenStages;
//  private List<SqlInfoDump> sqlInfos;
//  private MethodInfoDump methodStack;
    private String stageName;
    private String stagePath;
    private String stageMethod;
    //detachInfo
    private String detachedSql;
    private String detachedMethod;
    private int sqls;
    private int conns;
    private int stages;
    private String parentId;
    private boolean async;
    private boolean updated;
    
    public StageInfoBaseDump(String parentId, String id, String name){
        this.callId = id;
        this.parentId = parentId;
        this.stageName = name;
    }
    
    protected StageInfoBaseDump() {
        
    }
    public String getParentId() {
        return parentId;
    }
    
//  public MethodInfoDump getMethodStack() {
//      return methodStack;
//  }
//
//  public void setMethodStack(MethodInfoDump methodStack) {
//      this.methodStack = methodStack;
//  }

//  public List<SqlInfoDump> getSqlInfos() {
//      return sqlInfos;
//  }
//  
//  public void setSqlInfos(List<SqlInfoDump> sqlInfos) {
//      this.sqlInfos = sqlInfos;
//  }
//  
//  public void addSqlInfo(SqlInfoDump sql){
//      if(this.sqlInfos == null)
//          this.sqlInfos = new ArrayList<SqlInfoDump>();
//      this.sqlInfos.add(sql);
//  }
    
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

    public List<StageInfoBaseDump> getChildrenStages() {
        return childrenStages;
    }
    
    public void setChildrenStages(List<StageInfoBaseDump> stages) {
        this.childrenStages = stages;
    }
    
//  public void addChildStage(StageInfoBaseDump stage){
//      if(this.childrenStages == null)
//          this.childrenStages = new ArrayList<StageInfoBaseDump>();
//      this.childrenStages.add(stage);
//      this.sumStageCount += (stage.getSumStageCount() + 1);
//      this.sumSqlCount += stage.getSumSqlCount();
//      this.sqls += stage.getSqls();
//      this.stages += 1;
//  }

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
        this.stageMethod = stageMethod;
    }
    
    public String getStageMethod() {
        return this.stageMethod;
    }
    
    public long getDuration() {
        return duration;
    }
    
    public void setDuration(long duration){
        this.duration = duration;
    }
    
    public int getStages() {
        return this.stages;
    }
    
    public void setDetachedSql(String detachedSql) {
        this.detachedSql = detachedSql;
    }

    public String getDetachedMethod() {
        return detachedMethod;
    }

    public void setDetachedMethod(String detachedMethod) {
        this.detachedMethod = detachedMethod;
    }

    public void setSqls(int sqls) {
        this.sqls = sqls;
    }

    public void setStages(int stages) {
        this.stages = stages;
    }

    public int getSqls() {
        return sqls;
    }
    
    public int getConns() {
        return this.conns;
    }
    
    public void setConns(int conns){
        this.conns = conns;
    }

    protected String processImportantInfo() {
        return "";
    }

    public String getDetachedSql() {
//      if(this.detachedSql == null)
//          this.detachedSql = detachSql();
        return this.detachedSql;
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
    
//  protected String detachSql() {
//      StringBuffer buf = new StringBuffer();
//      List<SqlInfoDump> sqls = this.getSqlInfos();
//      if(sqls != null){
//          int size = sqls.size();
//          for(int i = 0; i < size; i ++){
//              SqlInfoDump sql = sqls.get(i);
//              buf.append("dbconn:" + sql.getConnId() + ",sql:" + sql.getSql() + ",duration:" + sql.getDuration() + ",resultCount:" + sql.getResultCount());
//              buf.append("\n");
//          }
//      }
//      List<StageInfoBaseDump> cstages = this.getChildrenStages();
//      if(cstages != null){
//          int size = cstages.size();
//          for(int i = 0; i < size; i ++){
//              buf.append(cstages.get(i).detachSql());
//          }
//      }
//      return buf.toString();
//  }
//  
//  /**
//   * minimum the memory usage
//   */
//  public void fillSqls() {
//      doFillsqls(null);
//  }
    
//  protected void doFillsqls(Map<String, String> psqlMap) {
//      if(this.childrenStages != null){
//          Map<String, String> sqlMap = new HashMap<String, String>();
//          Iterator<StageInfoBaseDump> it = this.childrenStages.iterator();
//          while(it.hasNext()){
//              StageInfoBaseDump stage = it.next();
//              String sql = stage.getDetachedSql();
//              if(sql != null && !sql.equals(""))
//                  sqlMap.put(stage.getCallId(), sql);
//              stage.doFillsqls(sqlMap);
//          }
//          Iterator<Entry<String, String>> sqlEntryIt = sqlMap.entrySet().iterator();
//          while(sqlEntryIt.hasNext()){
//              Entry<String, String> entry = sqlEntryIt.next();
//              String key = $REPLACE_BY + entry.getKey();
//              this.detachedSql.replace(key, entry.getValue());
//          }
//          if(psqlMap != null)
//              psqlMap.putAll(sqlMap);
//      }
//  }
    
    public StageInfoBaseDump detach() {
        StageInfoBaseDump t = (StageInfoBaseDump) this.clone();
        t.setChildrenStages(null);
        t.userObject = null;
        return t;
    }
    public static StageInfoBaseDump fromString(String source) {
        if(source == null || source.equals(""))
            return null;
        StageInfoBaseDump stageInfo = new StageInfoBaseDump();
        doFromString(source, stageInfo);
        return stageInfo;
    }
    
    protected static void doFromString(String source, StageInfoBaseDump stageInfo) {
        String[] segs = source.split(MonitorConstants.LOG_SEGMENT);
        for(int i = 0; i < segs.length; i ++){
            String seg = segs[i];
            String[] pair = getPair(seg);
            if(pair == null)
                continue;
            if(pair[0].equals(MonitorConstants.CALL_ID_PRE)){
                String callId = pair[1];
                stageInfo.callId = callId;
                if(callId.indexOf("_") != -1){
                    stageInfo.parentId = callId.substring(0, callId.lastIndexOf("_"));
                }
            }
            else if(pair[0].equals("name"))
                stageInfo.stageName = pair[1]; 
            else if(pair[0].equals("path"))
                stageInfo.stagePath = pair[1];
            else if(pair[0].equals("method"))
                stageInfo.stageMethod = pair[1];
            else if(pair[0].equals("stages"))
                stageInfo.stages = getInteger(pair[1]);
            else if(pair[0].equals("sqls"))
                stageInfo.sqls = getInteger(pair[1]);
            else if(pair[0].equals("dbconnections"))
                stageInfo.conns = getInteger(pair[1]);
            else if(pair[0].equals("duringTime"))
                stageInfo.duration = getInteger(pair[1]);
            else if(pair[0].equals("sumstages"))
                stageInfo.sumStageCount = getInteger(pair[1]);
            else if(pair[0].equals("sumsqls"))
                stageInfo.sumSqlCount = getInteger(pair[1]);
            else if(pair[0].equals("startts"))
                stageInfo.startTs = pair[1];
            else if(pair[0].equals("endts"))
                stageInfo.endTs = pair[1];
            else if(pair[0].equals("async"))
                stageInfo.async = Boolean.valueOf(pair[1]);
//          else if(pair[0].equals("serverIp"))
//              stageInfo.serverIp = pair[1];
        }
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

    public boolean isAsync() {
        return async;
    }

    public void setAsync(boolean async) {
        this.async = async;
    }

    public boolean isUpdated() {
        return updated;
    }

    public void setUpdated(boolean updated) {
        this.updated = updated;
    }
    
//  public String getServerIp() {
//      return serverIp;
//  }
//
//  public void setServerIp(String serverIp) {
//      this.serverIp = serverIp;
//  }
}
