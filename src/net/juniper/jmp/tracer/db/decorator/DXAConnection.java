package net.juniper.jmp.tracer.db.decorator;

import java.sql.Driver;
import java.sql.SQLException;

import javax.sql.XAConnection;
import javax.transaction.xa.XAResource;
/**
 * From p6spy
 */
public class DXAConnection extends DPooledConnection implements XAConnection {

    public DXAConnection(Class<? extends Driver> driver, XAConnection connection) {
        super(driver, connection);
    }

    public XAResource getXAResource() throws SQLException {
        return ((XAConnection) passthru).getXAResource();
    }

}