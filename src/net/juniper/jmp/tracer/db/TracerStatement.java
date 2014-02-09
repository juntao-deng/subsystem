package net.juniper.jmp.tracer.db;

import java.sql.ResultSet;
import java.sql.Statement;

import net.juniper.jmp.tracer.ThreadTracer;
import net.juniper.jmp.tracer.db.decorator.DConnection;
import net.juniper.jmp.tracer.db.decorator.DStatement;
import net.juniper.jmp.tracer.db.decorator.DecoratorFactory;
/**
 * 
 * @author juntaod
 *
 */
public class TracerStatement extends DStatement {

    public TracerStatement(DecoratorFactory factory, Statement statement, DConnection conn) {
        super(factory, statement, conn);
    }

    @Override
    public boolean execute(String sql) throws java.sql.SQLException {
        try {
        	ThreadTracer.getInstance().startSql(connId);
            ThreadTracer.getInstance().appendSql(sql);
            return passthru.execute(sql);
        }
        finally {
        	ThreadTracer.getInstance().endSql();
        }
    }

    @Override
    public boolean execute(String sql, int p1) throws java.sql.SQLException {
        try {
        	ThreadTracer.getInstance().startSql(connId);
            ThreadTracer.getInstance().appendSql(sql);
            return passthru.execute(sql, p1);
        }
        finally {
        	ThreadTracer.getInstance().endSql();
        }
    }

    @Override
    public boolean execute(String sql, int p1[]) throws java.sql.SQLException {
        try {
        	ThreadTracer.getInstance().startSql(connId);
            ThreadTracer.getInstance().appendSql(sql);
            return passthru.execute(sql, p1);
        }
        finally {
        	ThreadTracer.getInstance().endSql();
        }
    }

    @Override
    public boolean execute(String sql, String p1[]) throws java.sql.SQLException {
        try {
        	ThreadTracer.getInstance().startSql(connId);
            ThreadTracer.getInstance().appendSql(sql);
            return passthru.execute(sql, p1);
        }
        finally {
        	ThreadTracer.getInstance().endSql();
        }
    }

    @Override
    public ResultSet executeQuery(String sql) throws java.sql.SQLException {
        try {
        	ThreadTracer.getInstance().startSql(connId);
        	ThreadTracer.getInstance().appendSql(sql);
            return getDecoratorFactory().getResultSet(passthru.executeQuery(sql), this);
        }
        finally {
        	ThreadTracer.getInstance().endSql();
        }
    }

    @Override
    public int executeUpdate(String sql) throws java.sql.SQLException {
        try {
        	ThreadTracer.getInstance().startSql(connId);
            ThreadTracer.getInstance().appendSql(sql);
            return(passthru.executeUpdate(sql));
        }
        finally {
        	ThreadTracer.getInstance().endSql();
        }
    }

    @Override
    public int executeUpdate(String sql, int p1) throws java.sql.SQLException {
        try {
        	ThreadTracer.getInstance().startSql(connId);
            ThreadTracer.getInstance().appendSql(sql);
            return(passthru.executeUpdate(sql, p1));
        }
        finally {
        	ThreadTracer.getInstance().endSql();
        }
    }

    @Override
    public int executeUpdate(String sql, int p1[]) throws java.sql.SQLException {
        try {
        	ThreadTracer.getInstance().startSql(connId);
            ThreadTracer.getInstance().appendSql(sql);
            return(passthru.executeUpdate(sql, p1));
        }
        finally {
        	ThreadTracer.getInstance().endSql();
        }
    }

    @Override
    public int executeUpdate(String sql, String p1[]) throws java.sql.SQLException {
        try {
        	ThreadTracer.getInstance().startSql(connId);
            ThreadTracer.getInstance().appendSql(sql);
            return(passthru.executeUpdate(sql, p1));
        }
        finally {
        	ThreadTracer.getInstance().endSql();
        }
    }

    @Override
    public void addBatch(String sql) throws java.sql.SQLException {
        try {
        	ThreadTracer.getInstance().startSql(connId);
            ThreadTracer.getInstance().appendSql(sql);
            passthru.addBatch(sql);
        }
        finally {
        	ThreadTracer.getInstance().endSql();
        }
    }

    @Override
    public int[] executeBatch() throws java.sql.SQLException {
        try {
        	ThreadTracer.getInstance().startSql(connId);
            ThreadTracer.getInstance().appendSql(sql);
            return(passthru.executeBatch());
        }
        finally {
        	ThreadTracer.getInstance().endSql();
        }
    }
}