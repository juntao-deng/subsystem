package net.juniper.jmp.tracer.executor;

import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import net.juniper.jmp.tracer.common.MonitorConstants;
/**
 * 
 * @author juntaod
 *
 */
public class StateActionExecutor implements IActionExecutor {

//    private Integer currentSeq = -1;
    @Override
    public void execute(Integer action, Integer actionSeq, String actionParam, FileChannel raChannel, MappedByteBuffer buf)
            throws Exception {
        buf.putInt(1);
    }

    @Override
    public boolean handle(Integer action) {
        return action == MonitorConstants.ACTION_STATE;
    }

    @Override
    public boolean instant() {
        return true;
    }

}
