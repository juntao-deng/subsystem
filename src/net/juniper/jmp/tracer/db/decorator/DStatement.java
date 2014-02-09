package net.juniper.jmp.tracer.db.decorator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/**
 * From p6spy
 */
public class DStatement extends DBase implements Statement {

    protected Statement passthru;

    protected DConnection connection;

    protected String sql;
    
    protected String connId;

    public DStatement(DecoratorFactory factory, Statement statement, DConnection conn) {
        super(factory);
        this.connId = conn.getId() + "";
        this.passthru = statement;
        this.connection = conn;
        this.sql = "";
    }

    public void close() throws SQLException {
        passthru.close();
    }

    public boolean execute(String p0) throws SQLException {
        return passthru.execute(p0);
    }

    // Bug 161:  this method, unlike getResultSet(), should  never return null
    public ResultSet executeQuery(String p0) throws SQLException {
        return (getDecoratorFactory().getResultSet(passthru.executeQuery(p0), this));
    }

    public int executeUpdate(String p0) throws SQLException {
        return (passthru.executeUpdate(p0));
    }

    public int getMaxFieldSize() throws SQLException {
        return (passthru.getMaxFieldSize());
    }

    public void setMaxFieldSize(int p0) throws SQLException {
        passthru.setMaxFieldSize(p0);
    }

    public int getMaxRows() throws SQLException {
        return (passthru.getMaxRows());
    }

    public void setMaxRows(int p0) throws SQLException {
        passthru.setMaxRows(p0);
    }

    public void setEscapeProcessing(boolean p0) throws SQLException {
        passthru.setEscapeProcessing(p0);
    }

    public int getQueryTimeout() throws SQLException {
        return (passthru.getQueryTimeout());
    }

    public void setQueryTimeout(int p0) throws SQLException {
        passthru.setQueryTimeout(p0);
    }

    public void cancel() throws SQLException {
        passthru.cancel();
    }

    public java.sql.SQLWarning getWarnings() throws SQLException {
        return (passthru.getWarnings());
    }

    public void clearWarnings() throws SQLException {
        passthru.clearWarnings();
    }

    public void setCursorName(String p0) throws SQLException {
        passthru.setCursorName(p0);
    }

    public java.sql.ResultSet getResultSet() throws SQLException {
        ResultSet rs = passthru.getResultSet();
        return (rs == null) ? null : getDecoratorFactory().getResultSet(rs, this);
    }

    public int getUpdateCount() throws SQLException {
        return (passthru.getUpdateCount());
    }

    public boolean getMoreResults() throws SQLException {
        return (passthru.getMoreResults());
    }

    public void setFetchDirection(int p0) throws SQLException {
        passthru.setFetchDirection(p0);
    }

    public int getFetchDirection() throws SQLException {
        return (passthru.getFetchDirection());
    }

    public void setFetchSize(int p0) throws SQLException {
        passthru.setFetchSize(p0);
    }

    public int getFetchSize() throws SQLException {
        return (passthru.getFetchSize());
    }

    public int getResultSetConcurrency() throws SQLException {
        return (passthru.getResultSetConcurrency());
    }

    public int getResultSetType() throws SQLException {
        return (passthru.getResultSetType());
    }

    public void addBatch(String p0) throws SQLException {
        passthru.addBatch(p0);
    }

    public void clearBatch() throws SQLException {
        passthru.clearBatch();
    }

    public int[] executeBatch() throws SQLException {
        return (passthru.executeBatch());
    }

    // returns the p6connection
    public java.sql.Connection getConnection() throws SQLException {
        return connection;
    }

    // Since JDK 1.4
    public boolean getMoreResults(int p0) throws SQLException {
        return (passthru.getMoreResults(p0));
    }

    // Since JDK 1.4
    public java.sql.ResultSet getGeneratedKeys() throws SQLException {
        return (passthru.getGeneratedKeys());
    }

    // Since JDK 1.4
    public int executeUpdate(String p0, int p1) throws SQLException {
        return (passthru.executeUpdate(p0, p1));
    }

    // Since JDK 1.4
    public int executeUpdate(String p0, int p1[]) throws SQLException {
        return (passthru.executeUpdate(p0, p1));
    }

    // Since JDK 1.4
    public int executeUpdate(String p0, String p1[]) throws SQLException {
        return (passthru.executeUpdate(p0, p1));
    }

    // Since JDK 1.4
    public boolean execute(String p0, int p1) throws SQLException {
        return (passthru.execute(p0, p1));
    }

    // Since JDK 1.4
    public boolean execute(String p0, int p1[]) throws SQLException {
        return (passthru.execute(p0, p1));
    }

    // Since JDK 1.4
    public boolean execute(String p0, String p1[]) throws SQLException {
        return (passthru.execute(p0, p1));
    }

    // Since JDK 1.4
    public int getResultSetHoldability() throws SQLException {
        return (passthru.getResultSetHoldability());
    }

    /**
     * Returns the underlying JDBC object (in this case, a java.sql.Statement)
     *
     * @return the wrapped JDBC object
     */
    public Statement getJDBC() {
        Statement wrapped = (passthru instanceof DStatement) ? ((DStatement) passthru).getJDBC() : passthru;

        return wrapped;
    }

    public boolean isClosed() throws SQLException {
        return passthru.isClosed();
    }

    public boolean isPoolable() throws SQLException {
        return passthru.isPoolable();
    }

    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return passthru.isWrapperFor(iface);
    }

    public void setPoolable(boolean poolable) throws SQLException {
        passthru.setPoolable(poolable);
    }

    public <T> T unwrap(Class<T> iface) throws SQLException {
        return passthru.unwrap(iface);
    }
}
