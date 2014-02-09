package net.juniper.jmp.persist.exp;

import net.juniper.jmp.exception.JMPRuntimeException;

public class JmpDbRuntimeException extends JMPRuntimeException {
	private static final long serialVersionUID = -5875066590858218583L;

	public JmpDbRuntimeException(String message) {
		super(message);
	}

	public JmpDbRuntimeException(String message, Throwable e) {
		super(message, e);
	}

	public JmpDbRuntimeException(JmpDbException e) {
		super(e);
	}
}
