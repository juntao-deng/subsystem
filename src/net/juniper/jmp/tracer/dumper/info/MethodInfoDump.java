package net.juniper.jmp.tracer.dumper.info;

import java.io.Serializable;

import net.juniper.jmp.tracer.common.MonitorConstants;
/**
 * 
 * @author juntaod
 *
 */
public class MethodInfoDump extends AbstractDumpObject implements Serializable, Cloneable{
    private static final long serialVersionUID = 5055337996199589834L;
    private String method;
    private String callId;
    public MethodInfoDump(String callId, String method) {
        this.method = method;
        this.callId = callId;
    }
    
    private MethodInfoDump() {
        
    }
    
    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getCallId() {
        return callId;
    }

    public void setCallId(String callId) {
        this.callId = callId;
    }
    
    public Object clone() {
        try {
            return super.clone();
        } 
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static MethodInfoDump fromString(String source) {
        if(source == null || source.equals(""))
            return null;
        MethodInfoDump methodInfo = new MethodInfoDump();
        int index = source.indexOf(MonitorConstants.LOG_SEGMENT);
        if(index != -1){
            String callIdPart = source.substring(0, index);
            String methodPart = source.substring(index + MonitorConstants.LOG_SEGMENT.length());
            String[] callPair = callIdPart.split(MonitorConstants.LOG_SEP);
            if(callPair.length == 2){
                methodInfo.setCallId(callPair[1]);
                if(methodPart != null && !methodPart.equals("")){
                    int methodIndex = methodPart.indexOf(MonitorConstants.LOG_SEP);
                    if(methodIndex != -1)
                        methodInfo.setMethod(methodPart.substring(methodIndex + MonitorConstants.LOG_SEP.length()));
                }
            }
        }
        return methodInfo;
    }

    @Override
    public IDumpObject detach() {
        return null;
    }
}