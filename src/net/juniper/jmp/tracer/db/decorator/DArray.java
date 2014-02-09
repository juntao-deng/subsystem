package net.juniper.jmp.tracer.db.decorator;

import java.sql.Array;
import java.sql.SQLException;
import java.util.Map;
/**
 * From p6spy
 */
public class DArray extends DBase implements java.sql.Array {

    protected Array passthru;

    protected DStatement statement;

    public DArray(DecoratorFactory factory, Array array, DStatement statement) {
        super(factory);
        this.passthru = array;
        this.statement = statement;
    }

    public Object getArray() throws java.sql.SQLException {
        return passthru.getArray();
    }

    public Object getArray(Map map) throws java.sql.SQLException {
        return passthru.getArray(map);
    }

    public Object getArray(long p0, int p1) throws java.sql.SQLException {
        return passthru.getArray(p0, p1);
    }

    public Object getArray(long p0, int p1, Map map) throws java.sql.SQLException {
        return passthru.getArray(p0, p1, map);
    }

    public int getBaseType() throws java.sql.SQLException {
        return passthru.getBaseType();
    }

    public String getBaseTypeName() throws java.sql.SQLException {
        return passthru.getBaseTypeName();
    }

    public java.sql.ResultSet getResultSet() throws java.sql.SQLException {
        return getDecoratorFactory().getResultSet(passthru.getResultSet(), statement);
    }

    public java.sql.ResultSet getResultSet(Map p0) throws java.sql.SQLException {
        return getDecoratorFactory().getResultSet(passthru.getResultSet(p0), statement);
    }

    public java.sql.ResultSet getResultSet(long p0, int p1) throws java.sql.SQLException {
        return getDecoratorFactory().getResultSet(passthru.getResultSet(p0, p1), statement);
    }

    public java.sql.ResultSet getResultSet(long p0, int p1, Map p2) throws java.sql.SQLException {
        return getDecoratorFactory().getResultSet(passthru.getResultSet(p0, p1, p2), statement);
    }

    public void free() throws SQLException {
        passthru.free();
    }

    public Array getJDBC() {
        return (passthru instanceof DArray) ? ((DArray) passthru).getJDBC() : passthru;
    }
}
