package net.juniper.jmp.tracer.db.decorator;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
/**
 * From p6spy
 */
public class DResultSetMetaData extends DBase implements ResultSetMetaData{


    protected ResultSetMetaData passthru;

    public DResultSetMetaData(DecoratorFactory factory, ResultSetMetaData resultSetMetaData) {
        super(factory);
        this.passthru = resultSetMetaData;
    }

    public String getCatalogName(int param) throws java.sql.SQLException {
        return passthru.getCatalogName(param);
    }

    public String getColumnClassName(int param) throws java.sql.SQLException {
        return passthru.getColumnClassName(param);
    }

    public int getColumnCount() throws java.sql.SQLException {
        return passthru.getColumnCount();
    }

    public int getColumnDisplaySize(int param) throws java.sql.SQLException {
        return passthru.getColumnDisplaySize(param);
    }

    public String getColumnLabel(int param) throws java.sql.SQLException {
        return passthru.getColumnLabel(param);
    }

    public String getColumnName(int param) throws java.sql.SQLException {
        return passthru.getColumnName(param);
    }

    public int getColumnType(int param) throws java.sql.SQLException {
        return passthru.getColumnType(param);
    }

    public String getColumnTypeName(int param) throws java.sql.SQLException {
        return passthru.getColumnTypeName(param);
    }

    public int getPrecision(int param) throws java.sql.SQLException {
        return passthru.getPrecision(param);
    }

    public int getScale(int param) throws java.sql.SQLException {
        return passthru.getScale(param);
    }

    public String getSchemaName(int param) throws java.sql.SQLException {
        return passthru.getSchemaName(param);
    }

    public String getTableName(int param) throws java.sql.SQLException {
        return passthru.getTableName(param);
    }

    public boolean isAutoIncrement(int param) throws java.sql.SQLException {
        return passthru.isAutoIncrement(param);
    }

    public boolean isCaseSensitive(int param) throws java.sql.SQLException {
        return passthru.isCaseSensitive(param);
    }

    public boolean isCurrency(int param) throws java.sql.SQLException {
        return passthru.isCurrency(param);
    }

    public boolean isDefinitelyWritable(int param) throws java.sql.SQLException {
        return passthru.isDefinitelyWritable(param);
    }

    public int isNullable(int param) throws java.sql.SQLException {
        return passthru.isNullable(param);
    }

    public boolean isReadOnly(int param) throws java.sql.SQLException {
        return passthru.isReadOnly(param);
    }

    public boolean isSearchable(int param) throws java.sql.SQLException {
        return passthru.isSearchable(param);
    }

    public boolean isSigned(int param) throws java.sql.SQLException {
        return passthru.isSigned(param);
    }

    public boolean isWritable(int param) throws java.sql.SQLException {
        return passthru.isWritable(param);
    }

    /**
     * Returns the underlying JDBC object (in this case, a
     * java.sql.ResultSetMetaData)
     * @return the wrapped JDBC object
     */
    public ResultSetMetaData getJDBC() {
	ResultSetMetaData wrapped = (passthru instanceof DResultSetMetaData) ?
	    ((DResultSetMetaData) passthru).getJDBC() :
	    passthru;

	return wrapped;
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
     * @param <T>
     * @param iface
     * @return
     * @throws SQLException
     * @see java.sql.Wrapper#unwrap(java.lang.Class)
     */
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return passthru.unwrap(iface);
    }

}
