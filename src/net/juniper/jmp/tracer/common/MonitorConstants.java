package net.juniper.jmp.tracer.common;
/**
 * 
 * @author juntaod
 *
 */
public class MonitorConstants {
	public static final String SHARE_BASE_DIR = "/var/tmp/monitor/";
	public static final String PMC_LOG_DIR = SHARE_BASE_DIR + "pmclog";
	public static final String PMC_METHOD_LOG = "pmc_method.log";
	public static final String PMC_STAGE_LOG = "pmc_stage.log";
	public static final String PMC_SQL_LOG = "pmc_sql.log";
	public static final String PMC_THREAD_LOG = "pmc_thread.log";
	
	public static final String SHARE_SIGN_NAME = "SHARE_SIGN_NAME";
	public static final String SHARE_CONTENT_NAME = "SHARE_CONTENT_NAME";
	public static final int SHARE_CONTENT_SIZE = 80 * 1024 * 1024;
	public static final int SHARE_SIGN_SIZE = 4 * 1024;
	public static final String CALL_ID_PRE = "###callId";
	
	public static final Integer ACTION_THREADINFO = 4;
//	public static final Integer ACTION_PRERIOD_THREADINFO = 1;
	public static final Integer ACTION_CPUINFO = 2;
	public static final Integer ACTION_MEMINFO = 3;
	public static final Integer ACTION_STATE = 5;
	public static final Integer ACTION_RECORD = 6;
	public static final Integer ACTION_ENDRECORD = 7;
	//Processing sign
	public static final Integer ACTION_PROCESSING = 100;
	
	public static final Integer[] ACTION_ARRAY = new Integer[]{ACTION_THREADINFO, ACTION_CPUINFO, ACTION_MEMINFO, ACTION_STATE, ACTION_RECORD, ACTION_ENDRECORD};
	
	public static final String LOG_PREFIX = "###";
	public static final String LOG_SEP = ":";
	public static final String LOG_SEGMENT = "#,#";
}
