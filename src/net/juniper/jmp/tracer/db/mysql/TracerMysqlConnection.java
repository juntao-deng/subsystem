package net.juniper.jmp.tracer.db.mysql;

import java.sql.Connection;
import java.sql.SQLException;

import net.juniper.jmp.tracer.db.decorator.DecoratorFactory;
/**
 * 
 * @author juntaod
 *
 */
public class TracerMysqlConnection extends MysqlConnection {

	public TracerMysqlConnection(DecoratorFactory factory, Connection conn)
			throws SQLException {
		super(factory, conn);
	}
}
