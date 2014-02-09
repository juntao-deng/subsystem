package net.juniper.jmp.tracer.db.driver;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.juniper.jmp.tracer.db.DriverRepository;
import net.juniper.jmp.tracer.db.decorator.DDriver;
import net.juniper.jmp.tracer.db.mysql.MysqlTracerFactory;
/**
 * The implementation of the tracer driver for mysql.
 * @author juntaod
 *
 */
public class MysqlTracerDriver extends DDriver {
	private static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";

	static{
		init();
	}
	public MysqlTracerDriver() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		super();
	}
	
	private static void init() {
		DriverRepository.initDrivers(MysqlTracerDriver.class, MYSQL_DRIVER);
		
		List<String> factories = new ArrayList<String>();
		factories.add(MysqlTracerFactory.class.getName());
		DriverRepository.initFactorys(MysqlTracerDriver.class, factories);
	}

	@Override
	public int getMajorVersion() {
		return 5;
	}

	@Override
	public int getMinorVersion() {
		return 1;
	}

	@Override
	public boolean jdbcCompliant() {
		return true;
	}

}
