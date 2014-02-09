package net.juniper.jmp.tracer.info;


/**
 * 
 * @author juntaod
 *
 */
public class MonitorHelper {
    public static String dumpMethodStack(StackTraceElement topTraceElement, Thread thread) {
        StringBuffer buf = new StringBuffer();
        StackTraceElement[] stackElements = thread.getStackTrace();
        if(stackElements != null){
            for(int i = 0; i < stackElements.length && i < 50; i++){
                StackTraceElement ele = stackElements[i];
                if(ele == topTraceElement)
                    break;
                String str = ele.toString();
                if(isTracerMethod(str))
                    continue;
                buf.append(str);
                if(i != stackElements.length - 1)
                    buf.append("\n"); 
            }
        }
        return buf.toString();
    }

    private static boolean isTracerMethod(String str) {
        return str.startsWith("net.juniper.jmp.tracer") || str.startsWith("net.juniper.jmp.monitor") || str.startsWith("java.lang.Thread.getStackTrace");
    }
    
    public static StackTraceElement getTopTraceElement(StackTraceElement topTraceElement) {
        StackTraceElement[] stackElements = Thread.currentThread().getStackTrace();
        if(stackElements != null){
            for(int i = 0; i < stackElements.length && i < 10; i++){
                StackTraceElement ele = stackElements[i];
                if(ele == topTraceElement)
                    break;
                String str = ele.toString();
                if(isTracerMethod(str))
                    continue;
                return ele;
            }
        }
        return null;
    }
}
