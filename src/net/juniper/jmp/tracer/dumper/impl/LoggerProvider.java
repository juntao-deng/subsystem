package net.juniper.jmp.tracer.dumper.impl;

import net.juniper.jmp.tracer.common.MonitorConstants;

import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

/**
 * 
 * @author juntaod
 *
 */
public final class LoggerProvider {
//  private static final String LOG_SIZE = "10240"; //10M
    private static Logger threadInfoLogger;
    private static Logger sqlInfoLogger;
    private static Logger stageLogger;
    private static Logger methodLogger;
    private static Logger logger = Logger.getLogger(LoggerProvider.class);
    private static RecordLoggerObject recordLoggerObject;
    public static Logger getThreadInfoLogger(){
        if(threadInfoLogger == null){
            synchronized(LoggerProvider.class){
                if(threadInfoLogger == null){
                    threadInfoLogger = initializeLogger(MonitorConstants.PMC_THREAD_LOG);
                }
            }
        }
        return threadInfoLogger;
    }
    
    public static Logger getSqlInfoLogger(){
        if(sqlInfoLogger == null){
            synchronized(LoggerProvider.class){
                if(sqlInfoLogger == null)
                    sqlInfoLogger = initializeLogger(MonitorConstants.PMC_SQL_LOG);
            }
        }
        return sqlInfoLogger;
    }
    
    public static Logger getStageLogger(){
        if(stageLogger == null){
            synchronized(LoggerProvider.class){
                if(stageLogger == null)
                    stageLogger = initializeLogger(MonitorConstants.PMC_STAGE_LOG);
            }
        }
        return stageLogger;
    }
    
    public static Logger getMethodLogger() {
        if(methodLogger == null){
            synchronized(LoggerProvider.class){
                if(methodLogger == null)
                    methodLogger = initializeLogger(MonitorConstants.PMC_METHOD_LOG);
            }
        }
        return methodLogger;
    }
    
    private static Logger initializeLogger(String logName){
        try{
            Logger logger = Logger.getLogger(logName);
            PatternLayout layout = new PatternLayout();
            layout.setConversionPattern("%m%n");
            DailyRollingFileAppender appender = new DailyRollingFileAppender(layout, MonitorConstants.PMC_LOG_DIR + "/" + logName, "'.'yyyy-MM-dd-HH-mm");
            appender.setName("FILE");
            appender.setAppend(true);
            logger.setLevel(Level.INFO);
            logger.addAppender(appender);
            logger.setAdditivity(false);
            return logger;
        }
        catch(Exception e){
            logger.error("error creating logger:" + logName, e);
        }
        return null;
    }

    public static void endRecordLogger(String recordId){
        if(recordLoggerObject != null && recordLoggerObject.recordId.equals(recordId)){
            if(recordLoggerObject.threadLogger != null){
                recordLoggerObject.threadLogger.removeAllAppenders();
            }
            if(recordLoggerObject.stageLogger != null)
                recordLoggerObject.stageLogger.removeAllAppenders();
            if(recordLoggerObject.sqlLogger != null)
                recordLoggerObject.sqlLogger.removeAllAppenders();
            if(recordLoggerObject.methodLogger != null)
                recordLoggerObject.methodLogger.removeAllAppenders();
        }
    }
    
    public static Logger getThreadInfoRecordLogger(String recordId) {
        if(recordLoggerObject == null || !recordId.equals(recordLoggerObject.recordId)){
            synchronized(LoggerProvider.class){
                if(recordLoggerObject == null || !recordId.equals(recordLoggerObject.recordId)){
                    recordLoggerObject = new RecordLoggerObject(recordId);
                    recordLoggerObject.threadLogger = initializeRecordLogger(MonitorConstants.PMC_THREAD_LOG + "." + recordId);
                    recordLoggerObject.stageLogger = initializeRecordLogger(MonitorConstants.PMC_STAGE_LOG + "." + recordId);
                    recordLoggerObject.sqlLogger = initializeRecordLogger(MonitorConstants.PMC_SQL_LOG + "." + recordId);
                    recordLoggerObject.methodLogger = initializeRecordLogger(MonitorConstants.PMC_METHOD_LOG + "." + recordId);
                }
            }
        }
        return recordLoggerObject.threadLogger;
    }

    public static Logger getSqlInfoRecordLogger(String recordId) {
        if(recordLoggerObject != null && recordLoggerObject.recordId.equals(recordId))
            return recordLoggerObject.sqlLogger;
        return null;
    }

    public static Logger getStageRecordLogger(String recordId) {
        if(recordLoggerObject != null && recordLoggerObject.recordId.equals(recordId))
            return recordLoggerObject.stageLogger;
        return null;
    }

    public static Logger getMethodRecordLogger(String recordId) {
        if(recordLoggerObject != null && recordLoggerObject.recordId.equals(recordId))
            return recordLoggerObject.methodLogger;
        return null;
    }
    
    private static Logger initializeRecordLogger(String logName){
        try{
            Logger logger = Logger.getLogger("action_" + logName);
            logger.removeAllAppenders();
            PatternLayout layout = new PatternLayout();
            layout.setConversionPattern("%m%n");
            FileAppender appender = new FileAppender(layout, MonitorConstants.PMC_LOG_DIR + "/action/" + logName);
            appender.setName("ACTION_FILE");
            appender.setAppend(false);
            appender.setBufferedIO(false);
            appender.setImmediateFlush(true);
            logger.setLevel(Level.INFO);
            logger.addAppender(appender);
            logger.setAdditivity(false);
            return logger;
        }
        catch(Exception e){
            logger.error("error creating logger:" + logName, e);
        }
        return null;
    }
}

class RecordLoggerObject{
    String recordId;
    Logger sqlLogger;
    Logger threadLogger;
    Logger stageLogger;
    Logger methodLogger;
    protected RecordLoggerObject(String recordId){
        this.recordId = recordId;
    }
}