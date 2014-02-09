package net.juniper.jmp.tracer.info;

import java.io.Serializable;

public class NodeStateInfo implements Serializable {
	private static final long serialVersionUID = 3650831084399361921L;
	private boolean alive = false;
	public boolean isAlive() {
		return alive;
	}
	public void setAlive(boolean alive) {
		this.alive = alive;
	}
}
