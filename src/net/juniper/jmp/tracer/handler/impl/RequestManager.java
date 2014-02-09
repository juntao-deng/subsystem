package net.juniper.jmp.tracer.handler.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.juniper.jmp.tracer.common.MonitorConstants;
import net.juniper.jmp.tracer.sharemem.RequestMonitor;

public class RequestManager {
	private static List<RequestMonitor> monitorList = new ArrayList<RequestMonitor>();
	
	private static void initRequestMonitor(Integer action){
		RequestMonitor requestMonitor = new RequestMonitor(action);
		requestMonitor.start();
		monitorList.add(requestMonitor);
	}

	public synchronized static void startListening() {
		if(monitorList.size() > 0)
			return;
		for(int i = 0; i < MonitorConstants.ACTION_ARRAY.length; i ++){
			initRequestMonitor(MonitorConstants.ACTION_ARRAY[i]);
		}
	}
	
	public synchronized static void endListening() {
		Iterator<RequestMonitor> it = monitorList.iterator();
		while(it.hasNext()){
			RequestMonitor monitor = it.next();
			monitor.interrupt();
			it.remove();
		}
	}
}
