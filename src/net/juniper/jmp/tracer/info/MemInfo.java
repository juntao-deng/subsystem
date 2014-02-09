package net.juniper.jmp.tracer.info;

import java.io.Serializable;

public class MemInfo implements Serializable {
	private static final long serialVersionUID = 5898070898719851081L;
	private int total;
	private int max;
	private int free;
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getMax() {
		return max;
	}
	public void setMax(int max) {
		this.max = max;
	}
	public int getFree() {
		return free;
	}
	public void setFree(int free) {
		this.free = free;
	}
	
	public String toString() {
		return "max:" + max + ",free:" + free + ",total:" + total;
	}
}
