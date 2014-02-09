package net.juniper.jmp.tracer.handler.impl;

public interface IActionRecord {
	public void record(String recordId);
	public void endRecord(String recordId);
	public String getRecordId();
}
