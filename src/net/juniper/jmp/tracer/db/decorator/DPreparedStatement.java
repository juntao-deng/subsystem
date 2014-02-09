package net.juniper.jmp.tracer.db.decorator;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import net.juniper.jmp.tracer.ThreadTracer;
/**
 * From p6spy
 */
public class DPreparedStatement extends DStatement implements PreparedStatement {

    public final static int P6_MAX_FIELDS = 32;

    public static int P6_GROW_MAX = 32;


    protected PreparedStatement prepStmtPassthru;

    protected String preparedQuery;

    protected Object values[];

    protected boolean isString[];
    
    protected String connId;

    private static final char[] HEX_CHARS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

    public DPreparedStatement(DecoratorFactory factory, PreparedStatement statement, DConnection conn, String query) {
        super(factory, statement, conn);
        connId = "" + conn.getId();
        prepStmtPassthru = statement;
        this.preparedQuery = query;
        initValues();
    }

    protected void initValues() {
        values = new Object[P6_MAX_FIELDS + 1];
        isString = new boolean[P6_MAX_FIELDS + 1];
    }

    public void addBatch() throws SQLException {
        prepStmtPassthru.addBatch();
    }

    public void clearParameters() throws SQLException {
        prepStmtPassthru.clearParameters();
    }

    public boolean execute() throws SQLException {
        return prepStmtPassthru.execute();
    }

    public ResultSet executeQuery() throws SQLException {
        ResultSet resultSet = prepStmtPassthru.executeQuery();
        return (getDecoratorFactory().getResultSet(resultSet, this));
    }

    public int executeUpdate() throws SQLException {
        return prepStmtPassthru.executeUpdate();
    }

    public ResultSetMetaData getMetaData() throws SQLException {
        return prepStmtPassthru.getMetaData();
    }

    public void setArray(int p0, Array p1) throws SQLException {
        setObjectAsString(p0, p1);
        // we need to make sure we get the real object in this case
        if (p1 instanceof DArray) {
            prepStmtPassthru.setArray(p0, ((DArray) p1).passthru);
        } else {
            prepStmtPassthru.setArray(p0, p1);
        }
    }

    public void setAsciiStream(int p0, InputStream p1, int p2) throws SQLException {
        setObjectAsString(p0, p1);
        prepStmtPassthru.setAsciiStream(p0, p1, p2);
    }

    public void setBigDecimal(int p0, BigDecimal p1) throws SQLException {
        setObjectAsString(p0, p1);
        prepStmtPassthru.setBigDecimal(p0, p1);
    }

    public void setBinaryStream(int p0, InputStream p1, int p2) throws SQLException {
        setObjectAsString(p0, p1);
        prepStmtPassthru.setBinaryStream(p0, p1, p2);
    }

    public void setBlob(int p0, Blob p1) throws SQLException {
        setObjectAsString(p0, p1);
        prepStmtPassthru.setBlob(p0, p1);
    }

    public void setBoolean(int p0, boolean p1) throws SQLException {
        setObjectAsString(p0, new Boolean(p1));
        prepStmtPassthru.setBoolean(p0, p1);
    }

    public void setByte(int p0, byte p1) throws SQLException {
        setObjectAsString(p0, new Byte(p1));
        prepStmtPassthru.setByte(p0, p1);
    }

    public void setBytes(int p0, byte[] p1) throws SQLException {
        setObjectAsString(p0, p1);
        prepStmtPassthru.setBytes(p0, p1);
    }

    public void setCharacterStream(int p0, Reader p1, int p2) throws SQLException {
        setObjectAsString(p0, p1);
        prepStmtPassthru.setCharacterStream(p0, p1, p2);
    }

    public void setClob(int p0, Clob p1) throws SQLException {
        setObjectAsString(p0, p1);
        prepStmtPassthru.setClob(p0, p1);
    }

    public void setDate(int p0, Date p1) throws SQLException {
        setObjectAsString(p0, p1);
        prepStmtPassthru.setDate(p0, p1);
    }

    public void setDate(int p0, Date p1, java.util.Calendar p2) throws SQLException {
        setObjectAsString(p0, p1);
        prepStmtPassthru.setDate(p0, p1, p2);
    }

    public void setDouble(int p0, double p1) throws SQLException {
        setObjectAsInt(p0, new Double(p1));
        prepStmtPassthru.setDouble(p0, p1);
    }

    public void setFloat(int p0, float p1) throws SQLException {
        setObjectAsInt(p0, new Float(p1));
        prepStmtPassthru.setFloat(p0, p1);
    }

    public void setInt(int p0, int p1) throws SQLException {
        setObjectAsInt(p0, new Integer(p1));
        prepStmtPassthru.setInt(p0, p1);
    }

    public void setLong(int p0, long p1) throws SQLException {
        setObjectAsInt(p0, new Long(p1));
        prepStmtPassthru.setLong(p0, p1);
    }

    public void setNull(int p0, int p1, String p2) throws SQLException {
        setObjectAsString(p0, null);
        prepStmtPassthru.setNull(p0, p1, p2);
    }

    public void setNull(int p0, int p1) throws SQLException {
        setObjectAsString(p0, null);
        prepStmtPassthru.setNull(p0, p1);
    }

    public void setObject(int p0, Object p1, int p2, int p3) throws SQLException {
        setObjectAsString(p0, p1);
        prepStmtPassthru.setObject(p0, p1, p2, p3);
    }

    public void setObject(int p0, Object p1, int p2) throws SQLException {
        setObjectAsString(p0, p1);
        prepStmtPassthru.setObject(p0, p1, p2);
    }

    public void setObject(int p0, Object p1) throws SQLException {
        setObjectAsString(p0, p1);
        prepStmtPassthru.setObject(p0, p1);
    }

    public void setRef(int p0, Ref p1) throws SQLException {
        setObjectAsString(p0, p1);
        prepStmtPassthru.setRef(p0, p1);
    }

    public void setShort(int p0, short p1) throws SQLException {
        setObjectAsString(p0, new Short(p1));
        prepStmtPassthru.setShort(p0, p1);
    }

    public void setString(int p0, String p1) throws SQLException {
        setObjectAsString(p0, p1);
        prepStmtPassthru.setString(p0, p1);
    }

    public void setTime(int p0, Time p1, java.util.Calendar p2) throws SQLException {
        setObjectAsString(p0, p1);
        prepStmtPassthru.setTime(p0, p1, p2);
    }

    public void setTime(int p0, Time p1) throws SQLException {
        setObjectAsString(p0, p1);
        prepStmtPassthru.setTime(p0, p1);
    }

    public void setTimestamp(int p0, Timestamp p1, java.util.Calendar p2) throws SQLException {
        setObjectAsString(p0, p1);
        prepStmtPassthru.setTimestamp(p0, p1, p2);
    }

    public void setTimestamp(int p0, Timestamp p1) throws SQLException {
        setObjectAsString(p0, p1);
        prepStmtPassthru.setTimestamp(p0, p1);
    }

    @Deprecated
    public void setUnicodeStream(int p0, InputStream p1, int p2) throws SQLException {
        setObjectAsString(p0, p1);
        prepStmtPassthru.setUnicodeStream(p0, p1, p2);
    }

    public ResultSet getResultSet() throws java.sql.SQLException {
        ResultSet rs = passthru.getResultSet();
        return (rs == null) ? null : getDecoratorFactory().getResultSet(rs, this);
    }

    protected final String getSqlFromStatement() {
    	if(!ThreadTracer.enabled())
    		return null;
        int len = preparedQuery.length();
        StringBuffer t = new StringBuffer(len * 2);

        if (values != null) {
            int i = 1, limit = 0, base = 0;

            while ((limit = preparedQuery.indexOf('?', limit)) != -1) {
                if (isString[i]) {
                    t.append(preparedQuery.substring(base, limit));
                    t.append("'");
                    t.append(values[i]);
                    t.append("'");
                } else {
                    t.append(preparedQuery.substring(base, limit));
                    t.append(values[i]);
                }
                i++;
                limit++;
                base = limit;
            }
            if (base < len) {
                t.append(preparedQuery.substring(base));
            }
        }

        return t.toString();
    }

    protected void growValues(int newMax) {
        int size = values.length;
        Object[] values_tmp = new Object[newMax + P6_GROW_MAX];
        boolean[] isString_tmp = new boolean[newMax + P6_GROW_MAX];
        System.arraycopy(values, 0, values_tmp, 0, size);
        values = values_tmp;
        System.arraycopy(isString, 0, isString_tmp, 0, size);
        isString = isString_tmp;
    }

    protected void setObjectAsString(int i, Object o) {
        if (values != null) {
            if (i >= 0) {
                if (i >= values.length) {
                    growValues(i);
                }
                if (o instanceof java.util.Date) {
                    values[i] = new SimpleDateFormat().format(o);
                }
                else if ( o instanceof byte[] ) {
                    values[i] = toHexString( (byte[])o );
                }
                else {
                    values[i] = (o == null) ? "" : o.toString();
                }
                isString[i] = true;
            }
        }
    }

    private static final String toHexString( byte[] bytes ) {
        String value = "";
        for ( byte b : bytes ) {
            int temp = (int)b & 0xFF;
            value += HEX_CHARS[ temp/16 ];
            value += HEX_CHARS[ temp%16 ];
        }
        return value;
    }

    protected void setObjectAsInt(int i, Object o) {
        if (values != null) {
            if (i >= 0) {
                if (i >= values.length) {
                    growValues(i);
                }
                values[i] = (o == null) ? "" : o.toString();
                isString[i] = false;
            }
        }
    }

    // Since JDK 1.4
    public void setURL(int p0, java.net.URL p1) throws java.sql.SQLException {
        setObjectAsString(p0, p1);
        prepStmtPassthru.setURL(p0, p1);
    }

    // Since JDK 1.4
    public java.sql.ParameterMetaData getParameterMetaData() throws java.sql.SQLException {
        return (prepStmtPassthru.getParameterMetaData());
    }

    /**
     * Returns the underlying JDBC object (in this case, a java.sql.PreparedStatement).
     * <p>
     * The returned object is a java.sql.Statement due to inheritance reasons, so you'll need to
     * cast appropriately.
     *
     * @return the wrapped JDBC object
     */
    @Override
    public Statement getJDBC() {
        Statement wrapped = (prepStmtPassthru instanceof DStatement) ? ((DStatement) prepStmtPassthru).getJDBC() : prepStmtPassthru;

        return wrapped;
    }

    public int getValuesLength() {
        return values.length;
    }

    /**
     * @param parameterIndex
     * @param x
     * @throws SQLException
     * @see java.sql.PreparedStatement#setAsciiStream(int, java.io.InputStream)
     */
    public void setAsciiStream(int parameterIndex, InputStream x) throws SQLException {
        prepStmtPassthru.setAsciiStream(parameterIndex, x);
    }

    /**
     * @param parameterIndex
     * @param x
     * @param length
     * @throws SQLException
     * @see java.sql.PreparedStatement#setAsciiStream(int, java.io.InputStream, long)
     */
    public void setAsciiStream(int parameterIndex, InputStream x, long length) throws SQLException {
        prepStmtPassthru.setAsciiStream(parameterIndex, x, length);
    }

    /**
     * @param parameterIndex
     * @param x
     * @throws SQLException
     * @see java.sql.PreparedStatement#setBinaryStream(int, java.io.InputStream)
     */
    public void setBinaryStream(int parameterIndex, InputStream x) throws SQLException {
        prepStmtPassthru.setBinaryStream(parameterIndex, x);
    }

    /**
     * @param parameterIndex
     * @param x
     * @param length
     * @throws SQLException
     * @see java.sql.PreparedStatement#setBinaryStream(int, java.io.InputStream, long)
     */
    public void setBinaryStream(int parameterIndex, InputStream x, long length) throws SQLException {
        prepStmtPassthru.setBinaryStream(parameterIndex, x, length);
    }

    /**
     * @param parameterIndex
     * @param inputStream
     * @throws SQLException
     * @see java.sql.PreparedStatement#setBlob(int, java.io.InputStream)
     */
    public void setBlob(int parameterIndex, InputStream inputStream) throws SQLException {
        prepStmtPassthru.setBlob(parameterIndex, inputStream);
    }

    /**
     * @param parameterIndex
     * @param inputStream
     * @param length
     * @throws SQLException
     * @see java.sql.PreparedStatement#setBlob(int, java.io.InputStream, long)
     */
    public void setBlob(int parameterIndex, InputStream inputStream, long length) throws SQLException {
        prepStmtPassthru.setBlob(parameterIndex, inputStream, length);
    }

    /**
     * @param parameterIndex
     * @param reader
     * @throws SQLException
     * @see java.sql.PreparedStatement#setCharacterStream(int, java.io.Reader)
     */
    public void setCharacterStream(int parameterIndex, Reader reader) throws SQLException {
        prepStmtPassthru.setCharacterStream(parameterIndex, reader);
    }

    /**
     * @param parameterIndex
     * @param reader
     * @param length
     * @throws SQLException
     * @see java.sql.PreparedStatement#setCharacterStream(int, java.io.Reader, long)
     */
    public void setCharacterStream(int parameterIndex, Reader reader, long length) throws SQLException {
        prepStmtPassthru.setCharacterStream(parameterIndex, reader, length);
    }

    /**
     * @param parameterIndex
     * @param reader
     * @throws SQLException
     * @see java.sql.PreparedStatement#setClob(int, java.io.Reader)
     */
    public void setClob(int parameterIndex, Reader reader) throws SQLException {
        prepStmtPassthru.setClob(parameterIndex, reader);
    }

    /**
     * @param parameterIndex
     * @param reader
     * @param length
     * @throws SQLException
     * @see java.sql.PreparedStatement#setClob(int, java.io.Reader, long)
     */
    public void setClob(int parameterIndex, Reader reader, long length) throws SQLException {
        prepStmtPassthru.setClob(parameterIndex, reader, length);
    }

    /**
     * @param parameterIndex
     * @param value
     * @throws SQLException
     * @see java.sql.PreparedStatement#setNCharacterStream(int, java.io.Reader)
     */
    public void setNCharacterStream(int parameterIndex, Reader value) throws SQLException {
        prepStmtPassthru.setNCharacterStream(parameterIndex, value);
    }

    /**
     * @param parameterIndex
     * @param value
     * @param length
     * @throws SQLException
     * @see java.sql.PreparedStatement#setNCharacterStream(int, java.io.Reader, long)
     */
    public void setNCharacterStream(int parameterIndex, Reader value, long length) throws SQLException {
        prepStmtPassthru.setNCharacterStream(parameterIndex, value, length);
    }

    /**
     * @param parameterIndex
     * @param value
     * @throws SQLException
     * @see java.sql.PreparedStatement#setNClob(int, java.sql.NClob)
     */
    public void setNClob(int parameterIndex, NClob value) throws SQLException {
        prepStmtPassthru.setNClob(parameterIndex, value);
    }

    /**
     * @param parameterIndex
     * @param reader
     * @throws SQLException
     * @see java.sql.PreparedStatement#setNClob(int, java.io.Reader)
     */
    public void setNClob(int parameterIndex, Reader reader) throws SQLException {
        prepStmtPassthru.setNClob(parameterIndex, reader);
    }

    /**
     * @param parameterIndex
     * @param reader
     * @param length
     * @throws SQLException
     * @see java.sql.PreparedStatement#setNClob(int, java.io.Reader, long)
     */
    public void setNClob(int parameterIndex, Reader reader, long length) throws SQLException {
        prepStmtPassthru.setNClob(parameterIndex, reader, length);
    }

    /**
     * @param parameterIndex
     * @param value
     * @throws SQLException
     * @see java.sql.PreparedStatement#setNString(int, java.lang.String)
     */
    public void setNString(int parameterIndex, String value) throws SQLException {
        prepStmtPassthru.setNString(parameterIndex, value);
    }

    /**
     * @param parameterIndex
     * @param x
     * @throws SQLException
     * @see java.sql.PreparedStatement#setRowId(int, java.sql.RowId)
     */
    public void setRowId(int parameterIndex, RowId x) throws SQLException {
        prepStmtPassthru.setRowId(parameterIndex, x);
    }

    /**
     * @param parameterIndex
     * @param xmlObject
     * @throws SQLException
     * @see java.sql.PreparedStatement#setSQLXML(int, java.sql.SQLXML)
     */
    public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException {
        prepStmtPassthru.setSQLXML(parameterIndex, xmlObject);
    }

    /**
     * @param sql
     * @throws SQLException
     * @see java.sql.Statement#addBatch(java.lang.String)
     */
    @Override
    public void addBatch(String sql) throws SQLException {
        prepStmtPassthru.addBatch(sql);
    }

    /**
     * @throws SQLException
     * @see java.sql.Statement#cancel()
     */
    @Override
    public void cancel() throws SQLException {
        prepStmtPassthru.cancel();
    }

    /**
     * @throws SQLException
     * @see java.sql.Statement#clearBatch()
     */
    @Override
    public void clearBatch() throws SQLException {
        prepStmtPassthru.clearBatch();
    }

    /**
     * @throws SQLException
     * @see java.sql.Statement#clearWarnings()
     */
    @Override
    public void clearWarnings() throws SQLException {
        prepStmtPassthru.clearWarnings();
    }

    /**
     * @throws SQLException
     * @see java.sql.Statement#close()
     */
    @Override
    public void close() throws SQLException {
        prepStmtPassthru.close();
    }

    /**
     * @param sql
     * @return
     * @throws SQLException
     * @see java.sql.Statement#execute(java.lang.String)
     */
    @Override
    public boolean execute(String sql) throws SQLException {
        return prepStmtPassthru.execute(sql);
    }

    /**
     * @param sql
     * @param autoGeneratedKeys
     * @return
     * @throws SQLException
     * @see java.sql.Statement#execute(java.lang.String, int)
     */
    @Override
    public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
        return prepStmtPassthru.execute(sql, autoGeneratedKeys);
    }

    /**
     * @param sql
     * @param columnIndexes
     * @return
     * @throws SQLException
     * @see java.sql.Statement#execute(java.lang.String, int[])
     */
    @Override
    public boolean execute(String sql, int[] columnIndexes) throws SQLException {
        return prepStmtPassthru.execute(sql, columnIndexes);
    }

    /**
     * @param sql
     * @param columnNames
     * @return
     * @throws SQLException
     * @see java.sql.Statement#execute(java.lang.String, java.lang.String[])
     */
    @Override
    public boolean execute(String sql, String[] columnNames) throws SQLException {
        return prepStmtPassthru.execute(sql, columnNames);
    }

    /**
     * @return
     * @throws SQLException
     * @see java.sql.Statement#executeBatch()
     */
    @Override
    public int[] executeBatch() throws SQLException {
        return prepStmtPassthru.executeBatch();
    }

    /**
     * @param sql
     * @return
     * @throws SQLException
     * @see java.sql.Statement#executeQuery(java.lang.String)
     */
    @Override
    public ResultSet executeQuery(String sql) throws SQLException {
        return prepStmtPassthru.executeQuery(sql);
    }

    /**
     * @param sql
     * @return
     * @throws SQLException
     * @see java.sql.Statement#executeUpdate(java.lang.String)
     */
    @Override
    public int executeUpdate(String sql) throws SQLException {
        return prepStmtPassthru.executeUpdate(sql);
    }

    /**
     * @param sql
     * @param autoGeneratedKeys
     * @return
     * @throws SQLException
     * @see java.sql.Statement#executeUpdate(java.lang.String, int)
     */
    @Override
    public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
        return prepStmtPassthru.executeUpdate(sql, autoGeneratedKeys);
    }

    /**
     * @param sql
     * @param columnIndexes
     * @return
     * @throws SQLException
     * @see java.sql.Statement#executeUpdate(java.lang.String, int[])
     */
    @Override
    public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
        return prepStmtPassthru.executeUpdate(sql, columnIndexes);
    }

    /**
     * @param sql
     * @param columnNames
     * @return
     * @throws SQLException
     * @see java.sql.Statement#executeUpdate(java.lang.String, java.lang.String[])
     */
    @Override
    public int executeUpdate(String sql, String[] columnNames) throws SQLException {
        return prepStmtPassthru.executeUpdate(sql, columnNames);
    }

    /**
     * @return
     * @throws SQLException
     * @see java.sql.Statement#getConnection()
     */
    @Override
    public Connection getConnection() throws SQLException {
        return prepStmtPassthru.getConnection();
    }

    /**
     * @return
     * @throws SQLException
     * @see java.sql.Statement#getFetchDirection()
     */
    @Override
    public int getFetchDirection() throws SQLException {
        return prepStmtPassthru.getFetchDirection();
    }

    /**
     * @return
     * @throws SQLException
     * @see java.sql.Statement#getFetchSize()
     */
    @Override
    public int getFetchSize() throws SQLException {
        return prepStmtPassthru.getFetchSize();
    }

    /**
     * @return
     * @throws SQLException
     * @see java.sql.Statement#getGeneratedKeys()
     */
    @Override
    public ResultSet getGeneratedKeys() throws SQLException {
        return prepStmtPassthru.getGeneratedKeys();
    }

    /**
     * @return
     * @throws SQLException
     * @see java.sql.Statement#getMaxFieldSize()
     */
    @Override
    public int getMaxFieldSize() throws SQLException {
        return prepStmtPassthru.getMaxFieldSize();
    }

    /**
     * @return
     * @throws SQLException
     * @see java.sql.Statement#getMaxRows()
     */
    @Override
    public int getMaxRows() throws SQLException {
        return prepStmtPassthru.getMaxRows();
    }

    /**
     * @return
     * @throws SQLException
     * @see java.sql.Statement#getMoreResults()
     */
    @Override
    public boolean getMoreResults() throws SQLException {
        return prepStmtPassthru.getMoreResults();
    }

    /**
     * @param current
     * @return
     * @throws SQLException
     * @see java.sql.Statement#getMoreResults(int)
     */
    @Override
    public boolean getMoreResults(int current) throws SQLException {
        return prepStmtPassthru.getMoreResults(current);
    }

    /**
     * @return
     * @throws SQLException
     * @see java.sql.Statement#getQueryTimeout()
     */
    @Override
    public int getQueryTimeout() throws SQLException {
        return prepStmtPassthru.getQueryTimeout();
    }

    /**
     * @return
     * @throws SQLException
     * @see java.sql.Statement#getResultSetConcurrency()
     */
    @Override
    public int getResultSetConcurrency() throws SQLException {
        return prepStmtPassthru.getResultSetConcurrency();
    }

    /**
     * @return
     * @throws SQLException
     * @see java.sql.Statement#getResultSetHoldability()
     */
    @Override
    public int getResultSetHoldability() throws SQLException {
        return prepStmtPassthru.getResultSetHoldability();
    }

    /**
     * @return
     * @throws SQLException
     * @see java.sql.Statement#getResultSetType()
     */
    @Override
    public int getResultSetType() throws SQLException {
        return prepStmtPassthru.getResultSetType();
    }

    /**
     * @return
     * @throws SQLException
     * @see java.sql.Statement#getUpdateCount()
     */
    @Override
    public int getUpdateCount() throws SQLException {
        return prepStmtPassthru.getUpdateCount();
    }

    /**
     * @return
     * @throws SQLException
     * @see java.sql.Statement#getWarnings()
     */
    @Override
    public SQLWarning getWarnings() throws SQLException {
        return prepStmtPassthru.getWarnings();
    }

    /**
     * @return
     * @throws SQLException
     * @see java.sql.Statement#isClosed()
     */
    @Override
    public boolean isClosed() throws SQLException {
        return prepStmtPassthru.isClosed();
    }

    /**
     * @return
     * @throws SQLException
     * @see java.sql.Statement#isPoolable()
     */
    @Override
    public boolean isPoolable() throws SQLException {
        return prepStmtPassthru.isPoolable();
    }

    /**
     * @param name
     * @throws SQLException
     * @see java.sql.Statement#setCursorName(java.lang.String)
     */
    @Override
    public void setCursorName(String name) throws SQLException {
        prepStmtPassthru.setCursorName(name);
    }

    /**
     * @param enable
     * @throws SQLException
     * @see java.sql.Statement#setEscapeProcessing(boolean)
     */
    @Override
    public void setEscapeProcessing(boolean enable) throws SQLException {
        prepStmtPassthru.setEscapeProcessing(enable);
    }

    /**
     * @param direction
     * @throws SQLException
     * @see java.sql.Statement#setFetchDirection(int)
     */
    @Override
    public void setFetchDirection(int direction) throws SQLException {
        prepStmtPassthru.setFetchDirection(direction);
    }

    /**
     * @param rows
     * @throws SQLException
     * @see java.sql.Statement#setFetchSize(int)
     */
    @Override
    public void setFetchSize(int rows) throws SQLException {
        prepStmtPassthru.setFetchSize(rows);
    }

    /**
     * @param max
     * @throws SQLException
     * @see java.sql.Statement#setMaxFieldSize(int)
     */
    @Override
    public void setMaxFieldSize(int max) throws SQLException {
        prepStmtPassthru.setMaxFieldSize(max);
    }

    /**
     * @param max
     * @throws SQLException
     * @see java.sql.Statement#setMaxRows(int)
     */
    @Override
    public void setMaxRows(int max) throws SQLException {
        prepStmtPassthru.setMaxRows(max);
    }

    /**
     * @param poolable
     * @throws SQLException
     * @see java.sql.Statement#setPoolable(boolean)
     */
    @Override
    public void setPoolable(boolean poolable) throws SQLException {
        prepStmtPassthru.setPoolable(poolable);
    }

    /**
     * @param seconds
     * @throws SQLException
     * @see java.sql.Statement#setQueryTimeout(int)
     */
    @Override
    public void setQueryTimeout(int seconds) throws SQLException {
        prepStmtPassthru.setQueryTimeout(seconds);
    }

    /**
     * @param iface
     * @return
     * @throws SQLException
     * @see java.sql.Wrapper#isWrapperFor(java.lang.Class)
     */
    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return prepStmtPassthru.isWrapperFor(iface);
    }

    /**
     * @param <T>
     * @param iface
     * @return
     * @throws SQLException
     * @see java.sql.Wrapper#unwrap(java.lang.Class)
     */
    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return prepStmtPassthru.unwrap(iface);
    }
}
