package net.juniper.jmp.tracer.info;
/**
 * 
 * @author juntaod
 *
 */
public interface ILogable {
	public static final String LOG_PREFIX = "###";
	public static final String LOG_SEP = ":";
	public static final String LOG_SEGMENT = ",";
	public static final String LOG_SEGMENT_SQL = "#$#";
	public String toLog();
	public Object dump();
}
