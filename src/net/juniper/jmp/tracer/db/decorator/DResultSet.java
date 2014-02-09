package net.juniper.jmp.tracer.db.decorator;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.NClob;
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
import java.util.Calendar;
import java.util.Map;

/**
 * From p6spy
 */
public class DResultSet extends DBase implements ResultSet {

    protected ResultSet passthru;
    protected DStatement statement;
//    protected String query;
//    protected String preparedQuery;

    public DResultSet(DecoratorFactory factory, ResultSet resultSet, DStatement statement) {
        super(factory);
        this.passthru = resultSet;
        this.statement = statement;
//        this.query = query;
//        this.preparedQuery = preparedQuery;
    }

    /**
     * This gets overloaded in the P6LogResultSet, but may need to do what that class does
     */
    public boolean next() throws SQLException {
    	return passthru.next();
    }

    public int getRow() throws SQLException {
        return passthru.getRow();
    }

    public byte[] getBytes(String p0) throws SQLException {
        return passthru.getBytes(p0);
    }

    public byte[] getBytes(int p0) throws SQLException {
		return passthru.getBytes(p0);
    }

    public boolean getBoolean(int p0) throws SQLException {
		boolean result = passthru.getBoolean(p0);
		return result;
    }

    public boolean getBoolean(String p0) throws SQLException {
		boolean result = passthru.getBoolean(p0);
		return result;
    }

    public int getType() throws SQLException {
        return passthru.getType();
    }

    public long getLong(int p0) throws SQLException {
		long result = passthru.getLong(p0);
		return result;
    }

    public long getLong(String p0) throws SQLException {
		long result = passthru.getLong(p0);
		return result;
    }

    public boolean previous() throws SQLException {
        return passthru.previous();
    }

    public void close() throws SQLException {
        passthru.close();
    }

    public Object getObject(String p0, Map<String,Class<?>> p1) throws SQLException {
        return passthru.getObject(p0,p1);
    }

    public Object getObject(int p0) throws SQLException {
        return getObject(passthru.getMetaData().getColumnName(p0));
    }

    public Object getObject(String p0) throws SQLException {
        return passthru.getObject(p0);
    }

    public Object getObject(int p0, Map<String,Class<?>> p1) throws SQLException {
        return passthru.getObject(p0,p1);
    }

    public Ref getRef(String p0) throws SQLException {
        return passthru.getRef(p0);
    }

    public Ref getRef(int p0) throws SQLException {
        return getRef(passthru.getMetaData().getColumnName(p0));
    }

    public Time getTime(int p0, Calendar p1) throws SQLException {
        return passthru.getTime(p0,p1);
    }

    public Time getTime(String p0, Calendar p1) throws SQLException {
        return passthru.getTime(p0,p1);
    }

    public Time getTime(String p0) throws SQLException {
        return passthru.getTime(p0);
    }

    public Time getTime(int p0) throws SQLException {
        return getTime(passthru.getMetaData().getColumnName(p0));
    }

    public java.sql.Date getDate(int p0) throws SQLException {
        return getDate(passthru.getMetaData().getColumnName(p0));
    }

    public java.sql.Date getDate(String p0, Calendar p1) throws SQLException {
        return passthru.getDate(p0, p1);
    }

    public java.sql.Date getDate(String p0) throws SQLException {
        return passthru.getDate(p0);
    }

    public java.sql.Date getDate(int p0, Calendar p1) throws SQLException {
        return passthru.getDate(p0,p1);
    }

    public boolean wasNull() throws SQLException {
        return passthru.wasNull();
    }

    public String getString(String p0) throws SQLException {
        String result = passthru.getString(p0);
        return result;
    }

    public String getString(int p0) throws SQLException {
		String result = passthru.getString(p0);
		return result;
    }

    public byte getByte(String p0) throws SQLException {
        return passthru.getByte(p0);
    }

    public byte getByte(int p0) throws SQLException {
        return getByte(passthru.getMetaData().getColumnName(p0));
    }

    public short getShort(String p0) throws SQLException {
        short result = passthru.getShort(p0);
        return result;
    }

    public short getShort(int p0) throws SQLException {
        return getShort(passthru.getMetaData().getColumnName(p0));
    }

    public int getInt(int p0) throws SQLException {
        return getInt(passthru.getMetaData().getColumnName(p0));
    }

    public int getInt(String p0) throws SQLException {
        int result = passthru.getInt(p0);
        return result;
    }

    public float getFloat(String p0) throws SQLException {
        return passthru.getFloat(p0);
    }

    public float getFloat(int p0) throws SQLException {
        return getFloat(passthru.getMetaData().getColumnName(p0));
    }

    public double getDouble(int p0) throws SQLException {
        return getDouble(passthru.getMetaData().getColumnName(p0));
    }

    public double getDouble(String p0) throws SQLException {
        return passthru.getDouble(p0);
    }

    public BigDecimal getBigDecimal(String p0) throws SQLException {
        return passthru.getBigDecimal(p0);
    }

    public BigDecimal getBigDecimal(int p0) throws SQLException {
        return getBigDecimal(passthru.getMetaData().getColumnName(p0));
    }

    @SuppressWarnings("deprecation")
    public BigDecimal getBigDecimal(int p0, int p1) throws SQLException {
        return passthru.getBigDecimal(p0,p1);
    }

    @SuppressWarnings("deprecation")
    public BigDecimal getBigDecimal(String p0, int p1) throws SQLException {
        return passthru.getBigDecimal(p0,p1);
    }

    public Timestamp getTimestamp(String p0) throws SQLException {
        return passthru.getTimestamp(p0);
    }

    public Timestamp getTimestamp(String p0, Calendar p1) throws SQLException {
        return passthru.getTimestamp(p0,p1);
    }

    public Timestamp getTimestamp(int p0) throws SQLException {
        return getTimestamp(passthru.getMetaData().getColumnName(p0));
    }

    public Timestamp getTimestamp(int p0, Calendar p1) throws SQLException {
        return passthru.getTimestamp(p0,p1);
    }

    public InputStream getAsciiStream(String p0) throws SQLException {
        return passthru.getAsciiStream(p0);
    }

    public InputStream getAsciiStream(int p0) throws SQLException {
        return getAsciiStream(passthru.getMetaData().getColumnName(p0));
    }

    @SuppressWarnings("deprecation")
    public InputStream getUnicodeStream(int p0) throws SQLException {
        return passthru.getUnicodeStream(p0);
    }

    @SuppressWarnings("deprecation")
    public InputStream getUnicodeStream(String p0) throws SQLException {
        return passthru.getUnicodeStream(p0);
    }

    public InputStream getBinaryStream(int p0) throws SQLException {
		return passthru.getBinaryStream(p0);
    }

    public InputStream getBinaryStream(String p0) throws SQLException {
        return passthru.getBinaryStream(p0);
    }

    public SQLWarning getWarnings() throws SQLException {
        return passthru.getWarnings();
    }

    public void clearWarnings() throws SQLException {
        passthru.clearWarnings();
    }

    public String getCursorName() throws SQLException {
        return passthru.getCursorName();
    }

    public ResultSetMetaData getMetaData() throws SQLException {
        return getDecoratorFactory().getResultSetMetaData(passthru.getMetaData());
    }

    public int findColumn(String p0) throws SQLException {
        return passthru.findColumn(p0);
    }

    public Reader getCharacterStream(String p0) throws SQLException {
        return passthru.getCharacterStream(p0);
    }

    public Reader getCharacterStream(int p0) throws SQLException {
		return passthru.getCharacterStream(p0);
    }

    public boolean isBeforeFirst() throws SQLException {
        return passthru.isBeforeFirst();
    }

    public boolean isAfterLast() throws SQLException {
        return passthru.isAfterLast();
    }

    public boolean isFirst() throws SQLException {
        return passthru.isFirst();
    }

    public boolean isLast() throws SQLException {
        return passthru.isLast();
    }

    public void beforeFirst() throws SQLException {
        passthru.beforeFirst();
    }

    public void afterLast() throws SQLException {
        passthru.afterLast();
    }

    public boolean first() throws SQLException {
        return passthru.first();
    }

    public boolean last() throws SQLException {
        return passthru.last();
    }

    public boolean absolute(int p0) throws SQLException {
        return passthru.absolute(p0);
    }

    public boolean relative(int p0) throws SQLException {
        return passthru.relative(p0);
    }

    public void setFetchDirection(int p0) throws SQLException {
        passthru.setFetchDirection(p0);
    }

    public int getFetchDirection() throws SQLException {
        return passthru.getFetchDirection();
    }

    public void setFetchSize(int p0) throws SQLException {
        passthru.setFetchSize(p0);
    }

    public int getFetchSize() throws SQLException {
        return passthru.getFetchSize();
    }

    public int getConcurrency() throws SQLException {
        return passthru.getConcurrency();
    }

    public boolean rowUpdated() throws SQLException {
        return passthru.rowUpdated();
    }

    public boolean rowInserted() throws SQLException {
        return passthru.rowInserted();
    }

    public boolean rowDeleted() throws SQLException {
        return passthru.rowDeleted();
    }

    public void updateNull(int p0) throws SQLException {
        passthru.updateNull(p0);
    }

    public void updateNull(String p0) throws SQLException {
        passthru.updateNull(p0);
    }

    public void updateBoolean(int p0, boolean p1) throws SQLException {
        passthru.updateBoolean(p0, p1);
    }

    public void updateBoolean(String p0, boolean p1) throws SQLException {
        passthru.updateBoolean(p0, p1);
    }

    public void updateByte(String p0, byte p1) throws SQLException {
        passthru.updateByte(p0, p1);
    }

    public void updateByte(int p0, byte p1) throws SQLException {
        passthru.updateByte(p0, p1);
    }

    public void updateShort(int p0, short p1) throws SQLException {
        passthru.updateShort(p0, p1);
    }

    public void updateShort(String p0, short p1) throws SQLException {
        passthru.updateShort(p0, p1);
    }

    public void updateInt(int p0, int p1) throws SQLException {
        passthru.updateInt(p0, p1);
    }

    public void updateInt(String p0, int p1) throws SQLException {
        passthru.updateInt(p0, p1);
    }

    public void updateLong(int p0, long p1) throws SQLException {
        passthru.updateLong(p0, p1);
    }

    public void updateLong(String p0, long p1) throws SQLException {
        passthru.updateLong(p0, p1);
    }

    public void updateFloat(String p0, float p1) throws SQLException {
        passthru.updateFloat(p0, p1);
    }

    public void updateFloat(int p0, float p1) throws SQLException {
        passthru.updateFloat(p0, p1);
    }

    public void updateDouble(int p0, double p1) throws SQLException {
        passthru.updateDouble(p0, p1);
    }

    public void updateDouble(String p0, double p1) throws SQLException {
        passthru.updateDouble(p0, p1);
    }

    public void updateBigDecimal(String p0, BigDecimal p1) throws SQLException {
        passthru.updateBigDecimal(p0, p1);
    }

    public void updateBigDecimal(int p0, BigDecimal p1) throws SQLException {
        passthru.updateBigDecimal(p0, p1);
    }

    public void updateString(String p0, String p1) throws SQLException {
        passthru.updateString(p0, p1);
    }

    public void updateString(int p0, String p1) throws SQLException {
        passthru.updateString(p0, p1);
    }

    public void updateBytes(int p0, byte[] p1) throws SQLException {
        passthru.updateBytes(p0, p1);
    }

    public void updateBytes(String p0, byte[] p1) throws SQLException {
        passthru.updateBytes(p0, p1);
    }

    public void updateDate(int p0, java.sql.Date p1) throws SQLException {
        passthru.updateDate(p0, p1);
    }

    public void updateDate(String p0, java.sql.Date p1) throws SQLException {
        passthru.updateDate(p0, p1);
    }

    public void updateTime(String p0, Time p1) throws SQLException {
        passthru.updateTime(p0, p1);
    }

    public void updateTime(int p0, Time p1) throws SQLException {
        passthru.updateTime(p0, p1);
    }

    public void updateTimestamp(int p0, Timestamp p1) throws SQLException {
        passthru.updateTimestamp(p0, p1);
    }

    public void updateTimestamp(String p0, Timestamp p1) throws SQLException {
        passthru.updateTimestamp(p0, p1);
    }

    public void updateAsciiStream(int p0, InputStream p1, int p2)
            throws SQLException {
        passthru.updateAsciiStream(p0, p1, p2);
    }

    public void updateAsciiStream(String p0, InputStream p1, int p2)
            throws SQLException {
        passthru.updateAsciiStream(p0, p1, p2);
    }

    public void updateBinaryStream(int p0, InputStream p1, int p2)
            throws SQLException {
        passthru.updateBinaryStream(p0, p1, p2);
    }

    public void updateBinaryStream(String p0, InputStream p1, int p2)
            throws SQLException {
        passthru.updateBinaryStream(p0, p1, p2);
    }

    public void updateCharacterStream(int p0, Reader p1, int p2)
            throws SQLException {
        passthru.updateCharacterStream(p0, p1, p2);
    }

    public void updateCharacterStream(String p0, Reader p1, int p2)
            throws SQLException {
        passthru.updateCharacterStream(p0, p1, p2);
    }

    public void updateObject(int p0, Object p1) throws SQLException {
        passthru.updateObject(p0, p1);
    }

    public void updateObject(int p0, Object p1, int p2) throws SQLException {
        passthru.updateObject(p0, p1, p2);
    }

    public void updateObject(String p0, Object p1) throws SQLException {
        passthru.updateObject(p0, p1);
    }

    public void updateObject(String p0, Object p1, int p2) throws SQLException {
        passthru.updateObject(p0, p1, p2);
    }

    public void insertRow() throws SQLException {
        passthru.insertRow();
    }

    public void updateRow() throws SQLException {
        passthru.updateRow();
    }

    public void deleteRow() throws SQLException {
        passthru.deleteRow();
    }

    public void refreshRow() throws SQLException {
        passthru.refreshRow();
    }

    public void cancelRowUpdates() throws SQLException {
        passthru.cancelRowUpdates();
    }

    public void moveToInsertRow() throws SQLException {
        passthru.moveToInsertRow();
    }

    public void moveToCurrentRow() throws SQLException {
        passthru.moveToCurrentRow();
    }

    public Statement getStatement() throws SQLException {
        return this.statement;
    }

    public Blob getBlob(int p0) throws SQLException {
		return passthru.getBlob(p0);
    }

    public Blob getBlob(String p0) throws SQLException {
        return passthru.getBlob(p0);
    }

    public Clob getClob(String p0) throws SQLException {
        return passthru.getClob(p0);
    }

    public Clob getClob(int p0) throws SQLException {
		return passthru.getClob(p0);
    }

    public Array getArray(int p0) throws SQLException {
        return getDecoratorFactory().getArray(passthru.getArray(p0), statement);
    }

    public Array getArray(String p0) throws SQLException {
        return getDecoratorFactory().getArray(passthru.getArray(p0), statement);
    }

    // Since JDK 1.4
    public java.net.URL getURL(int p0) throws SQLException {
        return passthru.getURL(p0);
    }

    // Since JDK 1.4
    public java.net.URL getURL(String p0) throws SQLException {
        return passthru.getURL(p0);
    }

    // Since JDK 1.4
    public void updateRef(int p0, Ref p1) throws SQLException {
        passthru.updateRef(p0, p1);
    }

    // Since JDK 1.4
    public void updateRef(String p0, Ref p1) throws SQLException {
        passthru.updateRef(p0, p1);
    }

    // Since JDK 1.4
    public void updateBlob(int p0, Blob p1) throws SQLException {
        passthru.updateBlob(p0, p1);
    }

    // Since JDK 1.4
    public void updateBlob(String p0, Blob p1) throws SQLException {
        passthru.updateBlob(p0, p1);
    }

    // Since JDK 1.4
    public void updateClob(int p0, Clob p1) throws SQLException {
        passthru.updateClob(p0, p1);
    }

    // Since JDK 1.4
    public void updateClob(String p0, Clob p1) throws SQLException {
        passthru.updateClob(p0, p1);
    }

    // Since JDK 1.4
    public void updateArray(int p0, Array p1) throws SQLException {
        passthru.updateArray(p0, p1);
    }

    // Since JDK 1.4
    public void updateArray(String p0, Array p1) throws SQLException {
        passthru.updateArray(p0, p1);
    }

    /**
     * Returns the underlying JDBC object (in this case, a java.sql.ResultSet)
     *
     * @return the wrapped JDBC object
     */
    public ResultSet getJDBC() {
        ResultSet wrapped = (passthru instanceof DResultSet) ? ((DResultSet) passthru)
                .getJDBC()
                : passthru;

        return wrapped;
    }

    /**
     * @see java.sql.ResultSet#getHoldability()
     */
    public int getHoldability() throws SQLException {
        return passthru.getHoldability();
    }

    /**
     * @see java.sql.ResultSet#getNCharacterStream(int)
     */
    public Reader getNCharacterStream(int columnIndex) throws SQLException {
        return passthru.getNCharacterStream(columnIndex);
    }

    /**
     * @see java.sql.ResultSet#getNCharacterStream(java.lang.String)
     */
    public Reader getNCharacterStream(String columnLabel) throws SQLException {
        return passthru.getNCharacterStream(columnLabel);
    }

    /**
     * @see java.sql.ResultSet#getNClob(int)
     */
    public NClob getNClob(int columnIndex) throws SQLException {
        return passthru.getNClob(columnIndex);
    }

    /**
     * @see java.sql.ResultSet#getNClob(java.lang.String)
     */
    public NClob getNClob(String columnLabel) throws SQLException {
        return passthru.getNClob(columnLabel);
    }

    /**
     * @see java.sql.ResultSet#getNString(int)
     */
    public String getNString(int columnIndex) throws SQLException {
        return passthru.getNString(columnIndex);
    }

    /**
     * @see java.sql.ResultSet#getNString(java.lang.String)
     */
    public String getNString(String columnLabel) throws SQLException {
        return passthru.getNString(columnLabel);
    }

    /**
     * @see java.sql.ResultSet#getRowId(int)
     */
    public RowId getRowId(int columnIndex) throws SQLException {
        return passthru.getRowId(columnIndex);
    }

    /**
     * @see java.sql.ResultSet#getRowId(java.lang.String)
     */
    public RowId getRowId(String columnLabel) throws SQLException {
        return passthru.getRowId(columnLabel);
    }

    /**
     * @see java.sql.ResultSet#getSQLXML(int)
     */
    public SQLXML getSQLXML(int columnIndex) throws SQLException {
        return passthru.getSQLXML(columnIndex);
    }

    /**
     * @see java.sql.ResultSet#getSQLXML(java.lang.String)
     */
    public SQLXML getSQLXML(String columnLabel) throws SQLException {
        return passthru.getSQLXML(columnLabel);
    }

    /**
     * @see java.sql.ResultSet#isClosed()
     */
    public boolean isClosed() throws SQLException {
        return passthru.isClosed();
    }

    /**
     * @see java.sql.Wrapper#isWrapperFor(java.lang.Class)
     */
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return passthru.isWrapperFor(iface);
    }

    /**
     * @see java.sql.Wrapper#unwrap(java.lang.Class)
     */
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return passthru.unwrap(iface);
    }

    /**
     * @see java.sql.ResultSet#updateAsciiStream(int, java.io.InputStream, long)
     */
    public void updateAsciiStream(int columnIndex, InputStream x, long length)
            throws SQLException {
        passthru.updateAsciiStream(columnIndex, x, length);
    }

    /**
     * @see java.sql.ResultSet#updateAsciiStream(int, java.io.InputStream)
     */
    public void updateAsciiStream(int columnIndex, InputStream x)
            throws SQLException {
        passthru.updateAsciiStream(columnIndex, x);
    }

    /**
     * @see java.sql.ResultSet#updateAsciiStream(java.lang.String,
     *      java.io.InputStream, long)
     */
    public void updateAsciiStream(String columnLabel, InputStream x, long length)
            throws SQLException {
        passthru.updateAsciiStream(columnLabel, x, length);
    }

    /**
     * @see java.sql.ResultSet#updateAsciiStream(java.lang.String,
     *      java.io.InputStream)
     */
    public void updateAsciiStream(String columnLabel, InputStream x)
            throws SQLException {
        passthru.updateAsciiStream(columnLabel, x);
    }

    /**
     * @see java.sql.ResultSet#updateBinaryStream(int, java.io.InputStream,
     *      long)
     */
    public void updateBinaryStream(int columnIndex, InputStream x, long length)
            throws SQLException {
        passthru.updateBinaryStream(columnIndex, x, length);
    }

    /**
     * @see java.sql.ResultSet#updateBinaryStream(int, java.io.InputStream)
     */
    public void updateBinaryStream(int columnIndex, InputStream x)
            throws SQLException {
        passthru.updateBinaryStream(columnIndex, x);
    }

    /**
     * @see java.sql.ResultSet#updateBinaryStream(java.lang.String,
     *      java.io.InputStream, long)
     */
    public void updateBinaryStream(String columnLabel, InputStream x, long length)
            throws SQLException {
        passthru.updateBinaryStream(columnLabel, x, length);
    }

    /**
     * @see java.sql.ResultSet#updateBinaryStream(java.lang.String,
     *      java.io.InputStream)
     */
    public void updateBinaryStream(String columnLabel, InputStream x)
            throws SQLException {
        passthru.updateBinaryStream(columnLabel, x);
    }

    /**
     * @see java.sql.ResultSet#updateBlob(int, java.io.InputStream, long)
     */
    public void updateBlob(int columnIndex, InputStream inputStream, long length)
            throws SQLException {
        passthru.updateBlob(columnIndex, inputStream, length);
    }

    /**
     * @param columnIndex
     * @param inputStream
     * @throws SQLException
     * @see java.sql.ResultSet#updateBlob(int, java.io.InputStream)
     */
    public void updateBlob(int columnIndex, InputStream inputStream)
            throws SQLException {
        passthru.updateBlob(columnIndex, inputStream);
    }

    /**
     * @see java.sql.ResultSet#updateBlob(java.lang.String, java.io.InputStream,
     *      long)
     */
    public void updateBlob(String columnLabel, InputStream inputStream, long length)
            throws SQLException {
        passthru.updateBlob(columnLabel, inputStream, length);
    }

    /**
     * @see java.sql.ResultSet#updateBlob(java.lang.String, java.io.InputStream)
     */
    public void updateBlob(String columnLabel, InputStream inputStream)
            throws SQLException {
        passthru.updateBlob(columnLabel, inputStream);
    }

    /**
     * @see java.sql.ResultSet#updateCharacterStream(int, java.io.Reader, long)
     */
    public void updateCharacterStream(int columnIndex, Reader x, long length)
            throws SQLException {
        passthru.updateCharacterStream(columnIndex, x, length);
    }

    /**
     * @see java.sql.ResultSet#updateCharacterStream(int, java.io.Reader)
     */
    public void updateCharacterStream(int columnIndex, Reader x)
            throws SQLException {
        passthru.updateCharacterStream(columnIndex, x);
    }

    /**
     * @see java.sql.ResultSet#updateCharacterStream(java.lang.String,
     *      java.io.Reader, long)
     */
    public void updateCharacterStream(String columnLabel, Reader reader, long length)
            throws SQLException {
        passthru.updateCharacterStream(columnLabel, reader, length);
    }

    /**
     * @see java.sql.ResultSet#updateCharacterStream(java.lang.String,
     *      java.io.Reader)
     */
    public void updateCharacterStream(String columnLabel, Reader reader)
            throws SQLException {
        passthru.updateCharacterStream(columnLabel, reader);
    }

    /**
     * @see java.sql.ResultSet#updateClob(int, java.io.Reader, long)
     */
    public void updateClob(int columnIndex, Reader reader, long length)
            throws SQLException {
        passthru.updateClob(columnIndex, reader, length);
    }

    /**
     * @see java.sql.ResultSet#updateClob(int, java.io.Reader)
     */
    public void updateClob(int columnIndex, Reader reader) throws SQLException {
        passthru.updateClob(columnIndex, reader);
    }

    /**
     * @see java.sql.ResultSet#updateClob(java.lang.String, java.io.Reader,
     *      long)
     */
    public void updateClob(String columnLabel, Reader reader, long length)
            throws SQLException {
        passthru.updateClob(columnLabel, reader, length);
    }

    /**
     * @see java.sql.ResultSet#updateClob(java.lang.String, java.io.Reader)
     */
    public void updateClob(String columnLabel, Reader reader)
            throws SQLException {
        passthru.updateClob(columnLabel, reader);
    }

    /**
     * @see java.sql.ResultSet#updateNCharacterStream(int, java.io.Reader, long)
     */
    public void updateNCharacterStream(int columnIndex, Reader x, long length)
            throws SQLException {
        passthru.updateNCharacterStream(columnIndex, x, length);
    }

    /**
     * @see java.sql.ResultSet#updateNCharacterStream(int, java.io.Reader)
     */
    public void updateNCharacterStream(int columnIndex, Reader x)
            throws SQLException {
        passthru.updateNCharacterStream(columnIndex, x);
    }

    /**
     * @see java.sql.ResultSet#updateNCharacterStream(java.lang.String,
     *      java.io.Reader, long)
     */
    public void updateNCharacterStream(String columnLabel, Reader reader, long length)
            throws SQLException {
        passthru.updateNCharacterStream(columnLabel, reader, length);
    }

    /**
     * @see java.sql.ResultSet#updateNCharacterStream(java.lang.String,
     *      java.io.Reader)
     */
    public void updateNCharacterStream(String columnLabel, Reader reader)
            throws SQLException {
        passthru.updateNCharacterStream(columnLabel, reader);
    }

    /**
     * @see java.sql.ResultSet#updateNClob(int, java.sql.NClob)
     */
    public void updateNClob(int columnIndex, NClob clob) throws SQLException {
        passthru.updateNClob(columnIndex, clob);
    }

    /**
     * @see java.sql.ResultSet#updateNClob(int, java.io.Reader, long)
     */
    public void updateNClob(int columnIndex, Reader reader, long length)
            throws SQLException {
        passthru.updateNClob(columnIndex, reader, length);
    }

    /**
     * @see java.sql.ResultSet#updateNClob(int, java.io.Reader)
     */
    public void updateNClob(int columnIndex, Reader reader) throws SQLException {
        passthru.updateNClob(columnIndex, reader);
    }

    /**
     * @see java.sql.ResultSet#updateNClob(java.lang.String, java.sql.NClob)
     */
    public void updateNClob(String columnLabel, NClob clob) throws SQLException {
        passthru.updateNClob(columnLabel, clob);
    }

    /**
     * @see java.sql.ResultSet#updateNClob(java.lang.String, java.io.Reader,
     *      long)
     */
    public void updateNClob(String columnLabel, Reader reader, long length)
            throws SQLException {
        passthru.updateNClob(columnLabel, reader, length);
    }

    /**
     * @see java.sql.ResultSet#updateNClob(java.lang.String, java.io.Reader)
     */
    public void updateNClob(String columnLabel, Reader reader)
            throws SQLException {
        passthru.updateNClob(columnLabel, reader);
    }

    /**
     * @see java.sql.ResultSet#updateNString(int, java.lang.String)
     */
    public void updateNString(int columnIndex, String string)
            throws SQLException {
        passthru.updateNString(columnIndex, string);
    }

    /**
     * @see java.sql.ResultSet#updateNString(java.lang.String, java.lang.String)
     */
    public void updateNString(String columnLabel, String string)
            throws SQLException {
        passthru.updateNString(columnLabel, string);
    }

    /**
     * @see java.sql.ResultSet#updateRowId(int, java.sql.RowId)
     */
    public void updateRowId(int columnIndex, RowId x) throws SQLException {
        passthru.updateRowId(columnIndex, x);
    }

    /**
     * @see java.sql.ResultSet#updateRowId(java.lang.String, java.sql.RowId)
     */
    public void updateRowId(String columnLabel, RowId x) throws SQLException {
        passthru.updateRowId(columnLabel, x);
    }

    /**
     * @see java.sql.ResultSet#updateSQLXML(int, java.sql.SQLXML)
     */
    public void updateSQLXML(int columnIndex, SQLXML xmlObject)
            throws SQLException {
        passthru.updateSQLXML(columnIndex, xmlObject);
    }

    /**
     * @see java.sql.ResultSet#updateSQLXML(java.lang.String, java.sql.SQLXML)
     */
    public void updateSQLXML(String columnLabel, SQLXML xmlObject)
            throws SQLException {
        passthru.updateSQLXML(columnLabel, xmlObject);
    }
}
