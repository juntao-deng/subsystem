package net.juniper.jmp.persist.datasource;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import net.juniper.jmp.persist.exp.JmpDbRuntimeException;


public class JNDIDatasourceProvider implements DataSourceProvider {
	private Logger logger = Logger.getLogger(JNDIDatasourceProvider.class);
	private Context context;
	private static final String DS_PRE = "java:jboss/jdbc/";
	@Override
	public DataSource getDataSource(String name) {
		try{
			return (DataSource) getContext().lookup(DS_PRE + name);
		}
		catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new JmpDbRuntimeException("error getting ds:" + name);
		}
	}

	private Context getContext() {
		try{
			if(context == null)
				context = new InitialContext();
			return context;
		}
		catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new JmpDbRuntimeException("error getting context");
		}
	}
}
