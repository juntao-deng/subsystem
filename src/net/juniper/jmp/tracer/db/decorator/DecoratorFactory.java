package net.juniper.jmp.tracer.db.decorator;

import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
/**
 * From p6spy
 */
public interface DecoratorFactory {
    
    public Connection getConnection(Connection conn) throws SQLException;
    public PreparedStatement getPreparedStatement(PreparedStatement real, DConnection conn, String p0) throws SQLException;
    public Statement getStatement(Statement real, DConnection conn) throws SQLException;
    public CallableStatement getCallableStatement(CallableStatement real, DConnection conn, String p0) throws SQLException;
    public DatabaseMetaData getDatabaseMetaData(DatabaseMetaData real, DConnection conn) throws SQLException;
    public ResultSet getResultSet(ResultSet real, DStatement statement) throws SQLException;
    public Array getArray(Array real, DStatement statement) throws SQLException;
    public ResultSetMetaData getResultSetMetaData(ResultSetMetaData real) throws SQLException;
    
}
