package net.juniper.jmp.tracer.info;

import java.io.Serializable;

public class CpuInfo implements Serializable{
	private static final long serialVersionUID = 7280014701090138934L;
	private float usage;

	public float getUsage() {
		return usage;
	}

	public void setUsage(float usage) {
		this.usage = usage;
	}
}
