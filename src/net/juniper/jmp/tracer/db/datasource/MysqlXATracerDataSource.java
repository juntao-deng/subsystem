package net.juniper.jmp.tracer.db.datasource;

import java.sql.SQLException;

import javax.sql.DataSource;
import javax.sql.XAConnection;
import javax.sql.XADataSource;

import net.juniper.jmp.tracer.db.decorator.DXAConnection;
import net.juniper.jmp.tracer.db.driver.MysqlTracerDriver;

import org.apache.log4j.Logger;
/**
 * 
 * @author juntaod
 *
 */
public class MysqlXATracerDataSource extends MysqlTracerDataSource implements XADataSource {
	private Logger logger = Logger.getLogger(MysqlXATracerDataSource.class);
    private static final String MYSQL_XA_DATA_SOURCE = "com.mysql.jdbc.jdbc2.optional.MysqlXADataSource";

	public MysqlXATracerDataSource() {
        super();
    }
    
    @Override
    protected DataSource createRealDataSource() {
		try {
			return (DataSource) Class.forName(MYSQL_XA_DATA_SOURCE).newInstance();
		} 
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException("Didn't create mysql datasource");
		}
	}
    
    public XAConnection getXAConnection() throws SQLException {
        XAConnection xac = ((XADataSource) passthru).getXAConnection();
        DXAConnection p6xac = new DXAConnection(MysqlTracerDriver.class, xac);
        return p6xac;
    }

    public XAConnection getXAConnection(String s, String s1) throws SQLException {
        XAConnection xac = ((XADataSource) passthru).getXAConnection(s, s1);
        DXAConnection p6xac = new DXAConnection(MysqlTracerDriver.class, xac);
        return p6xac;
    }
}