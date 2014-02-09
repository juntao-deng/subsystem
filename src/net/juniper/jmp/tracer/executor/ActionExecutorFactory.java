package net.juniper.jmp.tracer.executor;

import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.juniper.jmp.tracer.common.MonitorConstants;
import net.juniper.jmp.tracer.lock.LockHelper;

import org.apache.log4j.Logger;
/**
 * 
 * @author juntaod
 *
 */
public class ActionExecutorFactory {
	private static Logger logger = Logger.getLogger(ActionExecutorFactory.class);
	private static List<IActionExecutor> actionList = new ArrayList<IActionExecutor>();
	static{
		actionList.add(new ThreadInfoActionExecutor());
		actionList.add(new CpuInfoActionExecutor());
		actionList.add(new MemInfoActionExecutor());
//		actionList.add(new PeriodThreadInfoExecutor());
		actionList.add(new StateActionExecutor());
		actionList.add(new RecordExecutor());
		actionList.add(new EndRecordExecutor());
	}
	public static void handle(int action, int actionSeq, String actionParam, FileChannel raChannel, MappedByteBuffer buf){
		Iterator<IActionExecutor> it = actionList.iterator();
		while(it.hasNext()){
			IActionExecutor executor = it.next();
			if(executor.handle(action)){
				try {
					if(!executor.instant()){
						buf.clear();
						//write back processing sign
						buf.putInt(LockHelper.LOCK_WRITE);
						buf.putInt(MonitorConstants.ACTION_PROCESSING);
						buf.putInt(actionSeq);
						executor.execute(action, actionSeq, actionParam, raChannel, buf);
					}
					else{
						buf.clear();
						buf.putInt(LockHelper.LOCK_WRITE);
						buf.putInt(action);
						buf.putInt(actionSeq);
						executor.execute(action, actionSeq, actionParam, raChannel, buf);
					}
				} 
				catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
				break;
			}
		}
	}
}