package net.juniper.jmp.tracer.executor;

import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import net.juniper.jmp.tracer.ThreadTracer;
import net.juniper.jmp.tracer.common.MonitorConstants;
import net.juniper.jmp.tracer.handler.impl.IActionRecord;
/**
 * 
 * @author juntaod
 *
 */
public class EndRecordExecutor implements IActionExecutor {
	@Override
	public void execute(Integer action, Integer actionSeq, String actionParam, FileChannel raChannel, MappedByteBuffer buf) throws Exception {
		String recordId = actionParam;
		((IActionRecord)ThreadTracer.getInstance()).endRecord(recordId);
	}

	@Override
	public boolean handle(Integer action) {
		return action == MonitorConstants.ACTION_ENDRECORD;
	}

	@Override
	public boolean instant() {
		return true;
	}
}

