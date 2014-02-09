package net.juniper.jmp.tracer.db;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import net.juniper.jmp.tracer.db.decorator.DDriver;
import net.juniper.jmp.tracer.db.decorator.DecoratorFactory;

import org.apache.log4j.Logger;
/**
 * 
 * @author juntaod
 *
 */
public class DriverRepository {
	private static Logger logger = Logger.getLogger(DriverRepository.class);
    private static Map<Class<? extends Driver>, List<DecoratorFactory>> factoriesMap = new HashMap<Class<? extends Driver>, List<DecoratorFactory>>();
    private static List<Driver> realDrivers = new CopyOnWriteArrayList<Driver>();

	public static List<Driver> getRealDrivers() {
		return realDrivers;
	}

	public static void initFactorys(Class<? extends Driver> c, List<String> facListStr) {
		if(facListStr != null){
			List<DecoratorFactory> facList = new ArrayList<DecoratorFactory>();
			Iterator<String> fit = facListStr.iterator();
			String fc = fit.next();
			try{
				DecoratorFactory f = (DecoratorFactory) Class.forName(fc).newInstance();
				facList.add(f);
				factoriesMap.put(c, facList);
			}
			catch(Exception e){
				logger.error(e.getMessage(), e);
			}
		}
	}


	public static void initDrivers(Class<? extends Driver> driver, String driverName){
		if(driverName != null){
			try{
				Driver realDriver = (Driver) Class.forName(driverName).newInstance();
			    DDriver tracerDriver = (DDriver) driver.newInstance();
			    DriverManager.registerDriver(tracerDriver);
	
			    deregister(realDriver.getClass());
		        // just in case you had to deregister
		        DriverManager.registerDriver(realDriver);
	
			    // now wrap your realDriver in the spy
			    tracerDriver.setPassthruDriver(realDriver);
			    realDrivers.add(realDriver);
	
			    logger.info("Registered driver: " + driverName);
			}
			catch(Exception e){
				logger.error(e.getMessage(), e);
			}
		}
	}
	
	static void deregister(Class<? extends Driver> c) throws SQLException {
        List<Driver> dereg = new ArrayList<Driver>();
        List<Driver> reorderDriver = new ArrayList<Driver>();
        boolean wrapped = false;
        for (Enumeration<Driver> e = DriverManager.getDrivers(); e.hasMoreElements();) {
            Driver driver = e.nextElement();
            if (driver.getClass().equals(c)) {
                dereg.add(driver);
                wrapped = false;
            }
            else if(DDriver.class.isAssignableFrom(driver.getClass())){
            	wrapped = true;
            }
            else{
            	if(!wrapped){
	            	//system drivers disturb. Reorder them.
	            	reorderDriver.add(driver);
            	}
            	wrapped = false;
            }
        }

        int size = dereg.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                Driver driver = dereg.get(i);
                logger.info("deregistering driver " + driver.getClass().getName());
                DriverManager.deregisterDriver(driver);
            }
        }
        
        size = reorderDriver.size();
        if(size > 0){
        	for(int i = 0; i < size; i++){
        		Driver driver = reorderDriver.get(i);
        		logger.info("reorder driver " + driver.getClass().getName());
        		DriverManager.deregisterDriver(driver);
        		DriverManager.registerDriver(driver);
        	}
        }

    }


    // these methods are the secret sauce here
    public static Connection wrapConnection(Class<? extends Driver> c, Connection realConnection) throws SQLException {
    	List<DecoratorFactory> facList = factoriesMap.get(c);
        Connection con = realConnection;
        if (facList != null) {
            for (DecoratorFactory factory : facList) {
                con = factory.getConnection(con);
            }
        }
        return con;
    }
}
