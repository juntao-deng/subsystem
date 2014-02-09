package net.juniper.jmp.tracer.db.mysql;

import java.sql.Connection;
import java.sql.SQLException;

import net.juniper.jmp.tracer.db.TracerFactory;
/**
 * 
 * @author juntaod
 *
 */
public class MysqlTracerFactory extends TracerFactory {
    @Override
    public Connection getConnection(Connection conn) throws SQLException {
        return new TracerMysqlConnection(this, conn);
    }
}
