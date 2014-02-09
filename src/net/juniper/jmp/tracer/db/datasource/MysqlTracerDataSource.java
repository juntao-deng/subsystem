package net.juniper.jmp.tracer.db.datasource;

import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import net.juniper.jmp.tracer.db.DriverRepository;
import net.juniper.jmp.tracer.db.driver.MysqlTracerDriver;

import org.apache.log4j.Logger;
/**
 * It's a light wrapper for Mysql DataSource, which will enable tracer function.
 * @author juntaod
 *
 */
public class MysqlTracerDataSource implements DataSource{
    private static final String MYSQL_DATA_SOURCE = "com.mysql.jdbc.jdbc2.optional.MysqlDataSource";
    private Logger logger = Logger.getLogger(MysqlTracerDataSource.class);
    protected DataSource passthru;
    
    //invoke to pre initialize
    static{
        try {
            Class.forName(MysqlTracerDriver.class.getName());
        } 
        catch (ClassNotFoundException e) {
        }
    }
    
    public MysqlTracerDataSource() {
        passthru = createRealDataSource();
    }
    
    protected DataSource createRealDataSource() {
        try {
            return (DataSource) Class.forName(MYSQL_DATA_SOURCE).newInstance();
        } 
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("Didn't create mysql datasource");
        }
    }

    public void setDatabaseName(String dbName){
        try {
            Method m = passthru.getClass().getMethod("setDatabaseName", new Class[]{String.class});
            m.invoke(passthru, dbName);
        } 
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("setting database name error");
        }
    }
    
    public void setUser(String userName){
        try {
            Method m = passthru.getClass().getMethod("setUser", new Class[]{String.class});
            m.invoke(passthru, userName);
        } 
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("setting user name error");
        }
    }
    
    public void setURL(String url){
      try {
          Method m = passthru.getClass().getMethod("url", new Class[]{String.class});
          m.invoke(passthru, url);
      } 
      catch (Exception e) {
          logger.error(e.getMessage(), e);
          throw new RuntimeException("setting url error");
      }
    }
    
    public void setServerName(String serverName){
        try {
            Method m = passthru.getClass().getMethod("setServerName", new Class[]{String.class});
            m.invoke(passthru, serverName);
        } 
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("setting server name error");
        }
    }
    
    public void setPassword(String pass){
        try {
            Method m = passthru.getClass().getMethod("setPassword", new Class[]{String.class});
            m.invoke(passthru, pass);
        } 
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("setting password error");
        }
    }
    
    public void setAutoReconnect(boolean auto){
        try {
            Method m = passthru.getClass().getMethod("setAutoReconnect", new Class[]{boolean.class});
            m.invoke(passthru, auto);
        } 
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("setting setAutoReconnect error");
        }
    }
    
    public void setAutoReconnectForPools(boolean auto){
        try {
            Method m = passthru.getClass().getMethod("setAutoReconnectForPools", new Class[]{boolean.class});
            m.invoke(passthru, auto);
        } 
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("setting autoReconnectForPools error");
        }
    }
    
    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return passthru.getLogWriter();
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        passthru.setLogWriter(out);
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        passthru.setLoginTimeout(seconds);
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return passthru.getLoginTimeout();
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return passthru.unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return passthru.isWrapperFor(iface);
    }

    @Override
    public Connection getConnection() throws SQLException {
        return DriverRepository.wrapConnection(MysqlTracerDriver.class, passthru.getConnection());
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return DriverRepository.wrapConnection(MysqlTracerDriver.class, passthru.getConnection(username, password));
    }
    

}