package net.juniper.jmp.tracer.db.decorator;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;

import javax.sql.ConnectionEventListener;
import javax.sql.PooledConnection;
import javax.sql.StatementEventListener;

import net.juniper.jmp.tracer.db.DriverRepository;

/**
 * From p6spy
 */
public abstract class DPooledConnection implements PooledConnection {
    protected PooledConnection passthru;
    private Class<? extends Driver> driverClass;
    public DPooledConnection(Class<? extends Driver> driver, PooledConnection connection) {
      passthru = connection;
      this.driverClass = driver;
    }

    public Connection getConnection() throws SQLException {
      return DriverRepository.wrapConnection(driverClass, passthru.getConnection());
    }

    public void close() throws SQLException  {
      passthru.close();
    }

    public void addConnectionEventListener(ConnectionEventListener eventTarget) {
      passthru.addConnectionEventListener(eventTarget);
    }


    public void removeConnectionEventListener(ConnectionEventListener eventTarget) {
      passthru.removeConnectionEventListener(eventTarget);
    }

    /**
     * @param listener
     * @see javax.sql.PooledConnection#addStatementEventListener(javax.sql.StatementEventListener)
     */
    public void addStatementEventListener(StatementEventListener listener) {
        passthru.addStatementEventListener(listener);
    }


    /**
     * @param listener
     * @see javax.sql.PooledConnection#removeStatementEventListener(javax.sql.StatementEventListener)
     */
    public void removeStatementEventListener(StatementEventListener listener) {
        passthru.removeStatementEventListener(listener);
    }

}