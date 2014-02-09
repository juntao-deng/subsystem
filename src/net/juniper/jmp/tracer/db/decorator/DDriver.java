package net.juniper.jmp.tracer.db.decorator;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.util.List;

import net.juniper.jmp.tracer.db.DriverRepository;
/**
 * 
 * @author juntaod
 *
 */
public abstract class DDriver implements Driver {
    protected Driver passthruDriver;
    protected DDriver(){
	}
    
    public void setPassthruDriver(Driver realDriver){
    	this.passthruDriver = realDriver;
    }
    
    public Connection connect(String url, java.util.Properties p1) throws SQLException {
        if (url == null) {
            throw new SQLException("url is null: " + url);
        }

        if (passthruDriver == null) {
            findPassthru(url);
        }
        
        if (passthruDriver == null) {
        	throw new SQLException("Unable to find a driver for url: " + url);
        }

        Connection conn = passthruDriver.connect(url, p1);

        if (conn != null) {
            conn = DriverRepository.wrapConnection(this.getClass(), conn);
        }
        return conn;
    }

    protected void findPassthru(String url) {
    	List<Driver> realDrivers = DriverRepository.getRealDrivers();
        for (Driver driver : realDrivers) {
            try {
                if (driver.acceptsURL(url)) {
                    passthruDriver = driver;
                    break;
                }
            } 
            catch (SQLException e) {
            }
        }
    }

    @Override
    public boolean acceptsURL(String url) throws SQLException {
        boolean accepts = false;
        if (passthruDriver == null) {
            findPassthru(url);
            if (passthruDriver == null) {
                throw new SQLException(" can't find a driver for url:" + url);
            }
            
        }

        if (url != null) {
            accepts = passthruDriver.acceptsURL(url);
        }
        return accepts;
    }

    public DriverPropertyInfo[] getPropertyInfo(String p0, java.util.Properties p1) throws SQLException {
        return (passthruDriver.getPropertyInfo(p0, p1));
    }

    public int getMajorVersion() {
        return (passthruDriver.getMajorVersion());
    }

    public int getMinorVersion() {
        return (passthruDriver.getMinorVersion());
    }

    public boolean jdbcCompliant() {
        return (passthruDriver.jdbcCompliant());
    }

}
