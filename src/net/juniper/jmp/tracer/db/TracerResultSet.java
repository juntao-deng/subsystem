package net.juniper.jmp.tracer.db;

import java.sql.ResultSet;
import java.sql.SQLException;

import net.juniper.jmp.tracer.ThreadTracer;
import net.juniper.jmp.tracer.db.decorator.DResultSet;
import net.juniper.jmp.tracer.db.decorator.DStatement;
import net.juniper.jmp.tracer.db.decorator.DecoratorFactory;
/**
 * 
 * @author juntaod
 *
 */
public class TracerResultSet extends DResultSet {

	public TracerResultSet(DecoratorFactory factory, ResultSet resultSet, DStatement statement) {
		super(factory, resultSet, statement);
	}

	@Override
	public boolean next() throws SQLException {
		ThreadTracer.getInstance().plusSqlResultCount();
		return super.next();
	}

	
}
