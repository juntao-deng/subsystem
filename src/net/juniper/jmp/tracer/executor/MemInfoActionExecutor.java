package net.juniper.jmp.tracer.executor;

import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import net.juniper.jmp.tracer.common.MonitorConstants;
/**
 * return memory usage information to share channel
 * @author juntaod
 *
 */
public class MemInfoActionExecutor implements IActionExecutor {
//    private Integer currentSeq = -1;
    private int mb = 1024 * 1024;
    @Override
    public void execute(Integer action, Integer actionSeq, String actionParam, FileChannel raChannel, MappedByteBuffer buf)
            throws Exception {
        int freeMem = (int) (Runtime.getRuntime().freeMemory() / mb);
        int totalMem = (int) (Runtime.getRuntime().totalMemory() / mb);
        int maxMem = (int) (Runtime.getRuntime().maxMemory() / mb );
        
        buf.putInt(freeMem);
        buf.putInt(totalMem);
        buf.putInt(maxMem);
    }

    @Override
    public boolean handle(Integer action) {
        return action == MonitorConstants.ACTION_MEMINFO;
    }

    @Override
    public boolean instant() {
        return true;
    }
}
