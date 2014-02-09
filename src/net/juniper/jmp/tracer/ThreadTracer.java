package net.juniper.jmp.tracer;

import net.juniper.jmp.tracer.handler.impl.NullTracerHandler;

import org.apache.log4j.Logger;

/**
 * Performance Monitor Tracer
 * @author juntaod
 *
 */
public class ThreadTracer {
	private static final String TRACER_IMPL_CLASSNAME = "net.juniper.jmp.tracer.handler.impl.TracerHandlerImpl";
	private static Logger logger = Logger.getLogger(ThreadTracer.class);
	private static boolean enabled = false;
	private static ITracerHandler currentInstance;
	private static ITracerHandler nullInstance;
	private static ITracerHandler instance;
	public static ITracerHandler getInstance(){
		if(currentInstance == null){
			synchronized(ThreadTracer.class){
				if(currentInstance == null){
					createInstances();
					currentInstance = enabled ? instance : nullInstance;
					((IMonitorHandler)currentInstance).ready();
				}
			}
		}
		return currentInstance;
	}
	
	public static void switchTracer(boolean enable) {
		if(instance == null)
			createInstances();
		if(enabled != enable){
		    logger.info("thread tracer is change to :" + enable);
			enabled = enable;
			currentInstance = enabled ? instance : nullInstance;
			((IMonitorHandler)currentInstance).ready();
			if(enabled){
				((IMonitorHandler)nullInstance).suspend();
			}
			else{
				((IMonitorHandler)instance).suspend();
			}
		}
	}
	
	private static void createInstances() {
		if(instance == null){
			synchronized(ThreadTracer.class){
				if(instance == null){
					instance = createInstance();
					nullInstance = createNullInstance();
				}
			}
		}
	}
	
	private static ITracerHandler createNullInstance(){
		return new NullTracerHandler();
	}
	
	private static ITracerHandler createInstance() {
		try {
			Class<?> handlerClazz = Class.forName(TRACER_IMPL_CLASSNAME);
			return (ITracerHandler) handlerClazz.newInstance();
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new NullTracerHandler();
		}
	}
	
	public static boolean enabled(){
		return enabled;
	}
}
