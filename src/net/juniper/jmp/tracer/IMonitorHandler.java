package net.juniper.jmp.tracer;

import net.juniper.jmp.tracer.dumper.info.ThreadInfoDump;
/**
 * It's for monitor tools.
 * @author juntaod
 *
 */
public interface IMonitorHandler {
	 public ThreadInfoDump[] getThreadInfoDumps();
	    public boolean traced();
	    /**
	     * prepare services
	     */
	    public void ready();
	    /**
	     * stop services
	     */
	    public void suspend();
}
