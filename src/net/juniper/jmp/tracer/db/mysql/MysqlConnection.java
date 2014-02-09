package net.juniper.jmp.tracer.db.mysql;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;

import net.juniper.jmp.tracer.db.decorator.DConnection;
import net.juniper.jmp.tracer.db.decorator.DecoratorFactory;
/**
 * 
 * @author juntaod
 *
 */
public class MysqlConnection extends DConnection{

	public MysqlConnection(DecoratorFactory factory, Connection conn)
			throws SQLException {
		super(factory, conn);
	}
	
	/**
	 * make jboss checker pass.
	 * @throws Exception
	 */
	public void ping() throws Exception{
		Method m = passthru.getClass().getMethod("ping", null);
		m.invoke(passthru, null);
	}
}
