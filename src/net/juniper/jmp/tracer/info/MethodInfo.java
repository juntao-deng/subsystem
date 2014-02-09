package net.juniper.jmp.tracer.info;

import java.io.Serializable;

import net.juniper.jmp.tracer.common.MonitorConstants;
import net.juniper.jmp.tracer.dumper.info.MethodInfoDump;
/**
 * 
 * @author juntaod
 *
 */
public class MethodInfo implements ILogable, Serializable , Cloneable{

    private static final long serialVersionUID = 6662572124336818256L;
    private transient StageInfoBase parent;
    private String method;
    private String callId;
    public MethodInfo(StageInfoBase parent, String method) {
        this.parent = parent;
        this.method = method;
        this.callId = this.parent.getCallId();
    }
    
    public MethodInfo() {
    }

    @Override
    public String toLog() {
        StringBuffer buf = new StringBuffer();
        buf.append(MonitorConstants.LOG_PREFIX).
        append("callId").append(MonitorConstants.LOG_SEP).append(callId).append(MonitorConstants.LOG_SEGMENT).
        append("method:").append(MonitorConstants.LOG_SEP).append(method);
        return buf.toString();
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
    
    @Override
    public Object dump() {
        MethodInfoDump dump = new MethodInfoDump(this.callId, this.method);
        return dump;
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

    public static Object fromString(String source) {
        if(source == null || source.equals(""))
            return null;
        String[] segs = source.split(",");
        MethodInfo mi = new MethodInfo();
        mi.setCallId(segs[0].split(":")[0]);
        mi.setMethod(segs[1].split(":")[0]);
        return mi;
    }

}
