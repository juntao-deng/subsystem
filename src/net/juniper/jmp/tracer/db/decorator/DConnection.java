package net.juniper.jmp.tracer.db.decorator;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Properties;
/**
 * From p6spy
 */
public class DConnection extends DBase implements java.sql.Connection {


    protected static int counter = 0;
    protected int id = counter ++;
    protected Connection passthru;

    public DConnection(DecoratorFactory factory, Connection conn) throws SQLException {
        super(factory);
        this.passthru = conn;
    }

    public void setReadOnly(boolean p0) throws SQLException {
        passthru.setReadOnly(p0);
    }

    public void close() throws SQLException {
        passthru.close();
    }

    public int getId() {
        return this.id;
    }

    public boolean isClosed() throws SQLException {
        return(passthru.isClosed());
    }

    public boolean isReadOnly() throws SQLException {
        return(passthru.isReadOnly());
    }

    public Statement createStatement() throws SQLException {
        Statement statement = getDecoratorFactory().getStatement(passthru.createStatement(), this);
        return(statement);
    }

    public Statement createStatement(int p0, int p1) throws SQLException {
        Statement statement = getDecoratorFactory().getStatement(passthru.createStatement(p0,p1), this);
        return(statement);
    }

    public PreparedStatement prepareStatement(String p0) throws SQLException {
        return (getDecoratorFactory().getPreparedStatement(passthru.prepareStatement(p0), this, p0));
    }

    public PreparedStatement prepareStatement(String p0, int p1, int p2) throws SQLException {
        return (getDecoratorFactory().getPreparedStatement(passthru.prepareStatement(p0,p1,p2), this, p0));
    }

    public CallableStatement prepareCall(String p0) throws SQLException {
        return (getDecoratorFactory().getCallableStatement(passthru.prepareCall(p0), this, p0));
    }

    public CallableStatement prepareCall(String p0, int p1, int p2) throws SQLException {
        return (getDecoratorFactory().getCallableStatement(passthru.prepareCall(p0,p1,p2), this, p0));
    }

    public String nativeSQL(String p0) throws SQLException {
        return(passthru.nativeSQL(p0));
    }

    public void setAutoCommit(boolean p0) throws SQLException {
        passthru.setAutoCommit(p0);
    }

    public boolean getAutoCommit() throws SQLException {
        return(passthru.getAutoCommit());
    }

    public void commit() throws SQLException {
        passthru.commit();
    }

    public void rollback() throws SQLException {
        passthru.rollback();
    }

    public DatabaseMetaData getMetaData() throws SQLException {
        return getDecoratorFactory().getDatabaseMetaData(passthru.getMetaData(), this);
    }

    public void setCatalog(String p0) throws SQLException {
        passthru.setCatalog(p0);
    }

    public String getCatalog() throws SQLException {
        return(passthru.getCatalog());
    }

    public void setTransactionIsolation(int p0) throws SQLException {
        passthru.setTransactionIsolation(p0);
    }

    public int getTransactionIsolation() throws SQLException {
        return(passthru.getTransactionIsolation());
    }

    public SQLWarning getWarnings() throws SQLException {
        return(passthru.getWarnings());
    }

    public void clearWarnings() throws SQLException {
        passthru.clearWarnings();
    }

    public java.util.Map getTypeMap() throws SQLException {
        return(passthru.getTypeMap());
    }

    public void setTypeMap(java.util.Map p0) throws SQLException {
        passthru.setTypeMap(p0);
    }

    // Since JDK 1.4
    public void setHoldability(int p0) throws SQLException {
        passthru.setHoldability(p0);
    }

    // Since JDK 1.4
    public int getHoldability() throws SQLException {
        return(passthru.getHoldability());
    }

    // Since JDK 1.4
    public Savepoint setSavepoint() throws SQLException {
        return(passthru.setSavepoint());
    }

    // Since JDK 1.4
    public Savepoint setSavepoint(String p0) throws SQLException {
        return(passthru.setSavepoint(p0));
    }

    // Since JDK 1.4
    public void rollback(Savepoint p0) throws SQLException {
        passthru.rollback(p0);
    }

    // Since JDK 1.4
    public void releaseSavepoint(Savepoint p0) throws SQLException {
        passthru.releaseSavepoint(p0);
    }

    // Since JDK 1.4
    public Statement createStatement(int p0, int p1, int p2) throws SQLException {
        return getDecoratorFactory().getStatement(passthru.createStatement(p0, p1, p2), this);
    }

    // Since JDK 1.4
    public PreparedStatement prepareStatement(String p0, int p1, int p2, int p3) throws SQLException {
        return (getDecoratorFactory().getPreparedStatement(passthru.prepareStatement(p0, p1, p2, p3), this, p0));
    }

    // Since JDK 1.4
    public CallableStatement prepareCall(String p0, int p1, int p2, int p3) throws SQLException {
        return (getDecoratorFactory().getCallableStatement(passthru.prepareCall(p0, p1, p2, p3), this, p0));
    }

    // Since JDK 1.4
    public PreparedStatement prepareStatement(String p0, int p1) throws SQLException {
        return (getDecoratorFactory().getPreparedStatement(passthru.prepareStatement(p0, p1), this, p0));
    }

    // Since JDK 1.4
    public PreparedStatement prepareStatement(String p0, int p1[]) throws SQLException {
        return (getDecoratorFactory().getPreparedStatement(passthru.prepareStatement(p0, p1), this, p0));
    }

    // Since JDK 1.4
    public PreparedStatement prepareStatement(String p0, String p1[]) throws SQLException {
        return (getDecoratorFactory().getPreparedStatement(passthru.prepareStatement(p0, p1), this, p0));
    }

    /**
     * @param typeName
     * @param elements
     * @return
     * @throws SQLException
     * @see java.sql.Connection#createArrayOf(java.lang.String, java.lang.Object[])
     */
    public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
        return passthru.createArrayOf(typeName, elements);
    }

    /**
     * @return
     * @throws SQLException
     * @see java.sql.Connection#createBlob()
     */
    public Blob createBlob() throws SQLException {
        return passthru.createBlob();
    }

    /**
     * @return
     * @throws SQLException
     * @see java.sql.Connection#createClob()
     */
    public Clob createClob() throws SQLException {
        return passthru.createClob();
    }

    /**
     * @return
     * @throws SQLException
     * @see java.sql.Connection#createNClob()
     */
    public NClob createNClob() throws SQLException {
        return passthru.createNClob();
    }

    /**
     * @return
     * @throws SQLException
     * @see java.sql.Connection#createSQLXML()
     */
    public SQLXML createSQLXML() throws SQLException {
        return passthru.createSQLXML();
    }

    /**
     * @param typeName
     * @param attributes
     * @return
     * @throws SQLException
     * @see java.sql.Connection#createStruct(java.lang.String, java.lang.Object[])
     */
    public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
        return passthru.createStruct(typeName, attributes);
    }

    /**
     * @return
     * @throws SQLException
     * @see java.sql.Connection#getClientInfo()
     */
    public Properties getClientInfo() throws SQLException {
        return passthru.getClientInfo();
    }

    /**
     * @param name
     * @return
     * @throws SQLException
     * @see java.sql.Connection#getClientInfo(java.lang.String)
     */
    public String getClientInfo(String name) throws SQLException {
        return passthru.getClientInfo(name);
    }

    /**
     * @param timeout
     * @return
     * @throws SQLException
     * @see java.sql.Connection#isValid(int)
     */
    public boolean isValid(int timeout) throws SQLException {
        return passthru.isValid(timeout);
    }

    /**
     * @param iface
     * @return
     * @throws SQLException
     * @see java.sql.Wrapper#isWrapperFor(java.lang.Class)
     */
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return passthru.isWrapperFor(iface);
    }

    /**
     * @param properties
     * @throws SQLClientInfoException
     * @see java.sql.Connection#setClientInfo(java.util.Properties)
     */
    public void setClientInfo(Properties properties) throws SQLClientInfoException {
        passthru.setClientInfo(properties);
    }

    /**
     * @param name
     * @param value
     * @throws SQLClientInfoException
     * @see java.sql.Connection#setClientInfo(java.lang.String, java.lang.String)
     */
    public void setClientInfo(String name, String value) throws SQLClientInfoException {
        passthru.setClientInfo(name, value);
    }

    /**
     * @param <T>
     * @param iface
     * @return
     * @throws SQLException
     * @see java.sql.Wrapper#unwrap(java.lang.Class)
     */
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return passthru.unwrap(iface);
    }

    /**
     * Returns the underlying JDBC object (in this case, a
     * java.sql.Connection)
     * @return the wrapped JDBC object
     */
    public Connection getJDBC() {
		Connection wrapped = (passthru instanceof DConnection) ?
		    ((DConnection) passthru).getJDBC() :
		    passthru;
	
		return wrapped;
    }

}
