package net.juniper.jmp.tracer.handler.impl;

import net.juniper.jmp.tracer.ThreadTracer;

import org.apache.log4j.Logger;
/**
 * Make sure that the action record will be closed if it not closed
 * @author juntaod
 *
 */
public class RecordEnsureCloseThread implements Runnable {
	private Logger logger = Logger.getLogger(RecordEnsureCloseThread.class);
	private String recordId;
	public RecordEnsureCloseThread(String record){
		this.recordId = record;
	}
	@Override
	public void run() {
		try {
			Thread.sleep(1000 * 2 * 60);
			((IActionRecord)ThreadTracer.getInstance()).endRecord(recordId);
		} 
		catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
		}
	}
}
