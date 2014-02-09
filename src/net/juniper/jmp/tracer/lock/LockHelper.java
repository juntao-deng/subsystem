package net.juniper.jmp.tracer.lock;

import java.nio.MappedByteBuffer;

public class LockHelper {
	public static final int LOCK_READ = -1;
	public static final int LOCK_WRITE = 1;
	public static final int LOCK_RELEASED = 0;
	public static boolean getLock(MappedByteBuffer buf, int state) {
		buf.rewind();
		int lockState = buf.getInt();
		if(state == LOCK_READ){
			if(lockState != LOCK_WRITE){
				buf.rewind();
				buf.putInt(state);
				return true;
			}
		}
		else if(state == LOCK_WRITE){
			if(lockState != LOCK_READ){
				buf.rewind();
				buf.putInt(state);
				return true;
			}
		}
		return false;
	}
	
	public static void releaseLock(MappedByteBuffer buf){
		buf.rewind();
		buf.putInt(LOCK_RELEASED);
	}
}
