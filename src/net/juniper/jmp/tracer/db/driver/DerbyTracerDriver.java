package net.juniper.jmp.tracer.db.driver;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.juniper.jmp.tracer.db.DriverRepository;
import net.juniper.jmp.tracer.db.decorator.DDriver;
import net.juniper.jmp.tracer.db.derby.DerbyTracerFactory;
/**
 * 
 * @author juntaod
 *
 */
public class DerbyTracerDriver extends DDriver {
	private static final String DERBY_EMBEDDED_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";

	static{
		init();
	}
	public DerbyTracerDriver() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		super();
	}
	
	private static void init() {
		DriverRepository.initDrivers(DerbyTracerDriver.class, DERBY_EMBEDDED_DRIVER);
		
		List<String> factories = new ArrayList<String>();
		factories.add(DerbyTracerFactory.class.getName());
		DriverRepository.initFactorys(DerbyTracerDriver.class, factories);
	}

}
