package net.juniper.jmp.tracer.db;

import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import net.juniper.jmp.tracer.db.decorator.DArray;
import net.juniper.jmp.tracer.db.decorator.DConnection;
import net.juniper.jmp.tracer.db.decorator.DDatabaseMetaData;
import net.juniper.jmp.tracer.db.decorator.DResultSetMetaData;
import net.juniper.jmp.tracer.db.decorator.DStatement;
import net.juniper.jmp.tracer.db.decorator.DecoratorFactory;
/**
 * 
 * @author juntaod
 *
 */
public class TracerFactory implements DecoratorFactory {

    public TracerFactory() {
    }

    @Override
    public Connection getConnection(Connection conn) throws SQLException {
        return new TracerConnection(this, conn);
    }

    @Override
    public PreparedStatement getPreparedStatement(PreparedStatement real, DConnection conn, String p0) throws SQLException {
        return new TracerPreparedStatement(this, real, conn, p0);
    }

    @Override
    public Statement getStatement(Statement statement, DConnection conn) throws SQLException {
        return new TracerStatement(this, statement, conn);
    }

    @Override
    public CallableStatement getCallableStatement(CallableStatement real, DConnection conn, String p0) throws SQLException {
        return new TracerCallableStatement(this, real, conn, p0);
    }

    @Override
    public ResultSet getResultSet(ResultSet real, DStatement statement) throws SQLException {
        return new TracerResultSet(this, real, statement);
    }

	@Override
	public DatabaseMetaData getDatabaseMetaData(DatabaseMetaData real,
			DConnection conn) throws SQLException {
		return new DDatabaseMetaData(this, real, conn);
	}

	@Override
	public Array getArray(Array real, DStatement statement) throws SQLException {
		return new DArray(this, real, statement);
	}

	@Override
	public ResultSetMetaData getResultSetMetaData(ResultSetMetaData real)
			throws SQLException {
		return new DResultSetMetaData(this, real);
	}

}
