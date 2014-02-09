package net.juniper.jmp.tracer.dumper.impl;

import java.util.Iterator;
import java.util.List;

import net.juniper.jmp.tracer.ThreadTracer;
import net.juniper.jmp.tracer.dumper.IThreadInfoDumper;
import net.juniper.jmp.tracer.handler.impl.IActionRecord;
import net.juniper.jmp.tracer.info.MethodInfo;
import net.juniper.jmp.tracer.info.SqlInfo;
import net.juniper.jmp.tracer.info.StageInfoBase;
import net.juniper.jmp.tracer.info.ThreadInfo;

import org.apache.log4j.Logger;
/**
 * 
 * @author juntaod
 *
 */
public class ThreadInfoDumper implements IThreadInfoDumper {

	@Override
	public void dump(ThreadInfo info) {
		String recordId = ((IActionRecord)ThreadTracer.getInstance()).getRecordId();
		String threadLog = info.toLog();
		if(recordId != null){
			LoggerProvider.getThreadInfoRecordLogger(recordId).info(threadLog);
		}
		LoggerProvider.getThreadInfoLogger().info(threadLog);
		List<StageInfoBase> stageList = info.getChildrenStageList();
		if(stageList != null){
			Iterator<StageInfoBase> it = stageList.iterator();
			while(it.hasNext())
				dumpStage(it.next(), recordId);
		}
		dumpSql(info, recordId);
		dumpMethod(info, recordId);
	}

	private void dumpSql(StageInfoBase stage, String recordId) {
		List<SqlInfo> sqlList = stage.getSqlInfoList();
		if(sqlList != null){
			Logger sqlLogger = LoggerProvider.getSqlInfoLogger();
			Iterator<SqlInfo> it = sqlList.iterator();
			Logger sqlRecordLogger = null;
			if(recordId != null)
				sqlRecordLogger = LoggerProvider.getSqlInfoRecordLogger(recordId);
			while(it.hasNext()){
				String sqlLog = it.next().toLog();
				if(sqlRecordLogger != null)
					sqlRecordLogger.info(sqlLog);
				sqlLogger.info(sqlLog);
			}
		}
	}

	private void dumpStage(StageInfoBase stage, String recordId) {
		String stageLog = stage.toLog();
		if(recordId != null){
			Logger stageRecordLogger = LoggerProvider.getStageRecordLogger(recordId);
			if(stageRecordLogger != null)
				stageRecordLogger.info(stageLog);
		}
		LoggerProvider.getStageLogger().info(stageLog);
		List<StageInfoBase> stageList = stage.getChildrenStageList();
		if(stageList != null){
			Iterator<StageInfoBase> it = stageList.iterator();
			while(it.hasNext())
				dumpStage(it.next(), recordId);
		}
		dumpSql(stage, recordId);
		dumpMethod(stage, recordId);
	}

	private void dumpMethod(StageInfoBase stage, String recordId) {
		MethodInfo methodStack = stage.getMethodStack();
		if(methodStack != null){
			String methodLog = methodStack.toLog();
			if(recordId != null){
				Logger methodRecordLogger = LoggerProvider.getMethodRecordLogger(recordId);
				if(methodRecordLogger != null)
					methodRecordLogger.info(methodLog);
			}
			LoggerProvider.getMethodLogger().info(methodLog);
		}
	}

}
