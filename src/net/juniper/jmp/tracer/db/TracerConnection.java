package net.juniper.jmp.tracer.db;

import java.sql.Connection;
import java.sql.SQLException;

import net.juniper.jmp.tracer.db.decorator.DConnection;
import net.juniper.jmp.tracer.db.decorator.DecoratorFactory;
/**
 * 
 * @author juntaod
 *
 */
public class TracerConnection extends DConnection {
	public TracerConnection(DecoratorFactory factory, Connection conn)
			throws SQLException {
		super(factory, conn);
	}
}
