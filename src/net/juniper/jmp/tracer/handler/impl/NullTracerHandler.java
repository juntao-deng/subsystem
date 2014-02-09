package net.juniper.jmp.tracer.handler.impl;

import net.juniper.jmp.tracer.IMonitorHandler;
import net.juniper.jmp.tracer.ITracerHandler;
import net.juniper.jmp.tracer.dumper.info.ThreadInfoDump;

import org.apache.log4j.Logger;
/**
 * Empty implementations of ITracerHandler, it does nothing. Just for convenient
 * @author juntaod
 *
 */
public class NullTracerHandler implements ITracerHandler, IMonitorHandler, IActionRecord{
    private static Logger logger = Logger.getLogger(NullTracerHandler.class);
    @Override
    public void startTracer() {
    }

    @Override
    public void endTracer() {
        
    }

    @Override
    public void startStage(String stageName) {
        
    }

    @Override
    public void endStage() {
        
    }

    @Override
    public void startSql(String connId) {
        
    }

    @Override
    public void endSql(int resultCount) {
        
    }

    @Override
    public void endSql() {
        
    }

    @Override
    public void setRequestBytes(int num) {
        
    }

    @Override
    public void setReponseBytes(int num) {
        
    }

    @Override
    public void setCallPath(String path) {
    }

    @Override
    public void setCallMethod(String method) {
    }

    @Override
    public void setUserId(String userId) {
    }

    @Override
    public void setClientIp(String clientIp) {
    }

    @Override
    public void ready() {
        logger.debug(this.getClass().getName() + " is ready");
    }

    @Override
    public void suspend() {
        logger.debug(this.getClass().getName() + " is suspended");
    }

    @Override
    public void setName(String name) {
    }

    @Override
    public ThreadInfoDump[] getThreadInfoDumps() {
        return null;
    }

    @Override
    public boolean traced() {
      return true;
    }

    @Override
    public boolean tryStartTracer(String name) {
      return false;
    }

    @Override
    public void tryEndTracer(boolean traced) {
    }

    @Override
    public void appendSql(String sql) {
        
    }

    @Override
    public void plusSqlResultCount() {
        
    }

    @Override
    public void record(String recordId) {
    }

    @Override
    public void endRecord(String record) {
    }

    @Override
    public String getRecordId() {
        return null;
    }

    @Override
    public void attachToAsyncId(String id) {
    }

    @Override
    public void setAsyncId(String asyncId) {
    }

    @Override
    public String getCallId() {
        return null;
    }

    @Override
    public String getAsyncId() {
        return null;
    }
}
