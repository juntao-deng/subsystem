package net.juniper.jmp.tracer.executor;

import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
/**
 * Action executor, put information to share channel
 * @author juntaod
 *
 */
public interface IActionExecutor {
    public void execute(Integer action, Integer actionSeq, String actionParam, FileChannel raChannel, MappedByteBuffer buf) throws Exception;
    public boolean handle(Integer action);
    public boolean instant();
}
