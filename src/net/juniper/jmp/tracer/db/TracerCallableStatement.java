package net.juniper.jmp.tracer.db;

import java.sql.CallableStatement;

import net.juniper.jmp.tracer.db.decorator.DCallableStatement;
import net.juniper.jmp.tracer.db.decorator.DConnection;
import net.juniper.jmp.tracer.db.decorator.DecoratorFactory;
/**
 * 
 * @author juntaod
 *
 */
public class TracerCallableStatement extends DCallableStatement {

	public TracerCallableStatement(DecoratorFactory factory,
			CallableStatement statement, DConnection conn, String query) {
		super(factory, statement, conn, query);
	}

}
