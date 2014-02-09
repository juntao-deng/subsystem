package net.juniper.jmp.tracer.dumper.info;

import java.io.Serializable;
/**
 * 
 * @author juntaod
 *
 */
public abstract class AbstractDumpObject implements Serializable, IDetachable, IDumpObject{
	private static final long serialVersionUID = -4955931558840018635L;
	protected transient Object userObject;
	public Object getUserObject() {
		return userObject;
	}

	public void setUserObject(Object userObject) {
		this.userObject = userObject;
	}
}
