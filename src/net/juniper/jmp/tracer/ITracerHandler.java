package net.juniper.jmp.tracer;


/**
 * Tracer Handler
 * @author juntaod
 *
 */
public interface ITracerHandler {
    public static final String TYPE_APP_INIT_NAME = "App Init";
    public static final String TYPE_EJB_NAME = "Ejb Call";
    public static final String TYPE_TIMEOUT_NAME = "Timeout Schedule";
    public static final String TYPE_THREAD_NAME = "Seperate Thread";
    public static final String TYPE_REQUEST_NAME = "Http Request";
    public static final String TYPE_JOB_NAME = "Job Schedule";
    public static final String TYPE_MESSAGE_NAME = "Message";
    public static final String TYPE_NOTIFICATION_RECEIVER = "Notification Receiver";
    public static final String TYPE_NOTIFICATION_SENDER = "Notification Sender";
    public static final String ASYNC_ID_KEY = "tracer_async_id_key";
    
    /**
     * Start a tracer in a separate thread, it must be used with endTracer in pair.
     * eg,
     *      ThreadTracer.getInstance().startTracer();
     *      try{ 
     *          //some codes
     *      }
     *      finally{
     *          ThreadTracer.getInstance().endTracer();
     *      }
     */
    public void startTracer();
    
    
    /**
     * End the started tracer in current thread
     * @see startTracer();
     */
    public void endTracer();
    
    /**
     * Like the startTracer, it is used when you are not sure if the tracer is already started elsewhere.
     * if not started, it's just the same as startTracer(), else, it will call startStage instead.
     * eg,
     *      boolean started = ThreadTracer.getInstance().tryStartTracer(ITracerHandler.TYPE_EJB_NAME);
     *      try{ 
     *          //some codes
     *      }
     *      finally{
     *          ThreadTracer.getInstance().tryEndTracer(started);
     *      }
     * @param name
     * @return
     */
    public boolean tryStartTracer(String name);
    
    /**
     * try to end the tracer in current thread, if the tracer is not started here, it will call endStage instead.
     * @see tryStartTracer(name)
     * @param traced
     */
    public void tryEndTracer(boolean traced);
    
    /**
     * Start a stage in the thread. eg, an ejb calls another ejb
     * @param stageName
     */
    public void startStage(String stageName);
    /**
     * End the stage on the top of the stack. It must be used in pair with startStage.
     */
    public void endStage();
    
    /**
     * Start a sql in current thread or stage
     * It's usually used in jdbc tracer. @See MysqlTracerFactory
     * @param connId
     */
    public void startSql(String connId);
    /**
     * append a sql string in current thread or stage
     * @param sql
     */
    public void appendSql(String sql);
    /**
     * end current sql monitor
     * @param resultCount
     */
    public void endSql(int resultCount);
    
    /**
     * end current sql monitor
     * @param resultCount
     */
    public void endSql();
    
    /**
     * increase the result statistics of the sql.
     */
    public void plusSqlResultCount();
    
    
    /**
     * Set the name of the current thread or stage.It depends on which is on the top of the stack.
     * @param name
     */
    public void setName(String name);
    
    /**
     * Set the user's id of the current thread. It's only available for thread. if not set, a default user "#SYS#" will be set.
     * @param name
     */
    public void setUserId(String userId);
    
    /**
     * Set the client id of the current thread. It's only available for thread. if it's not a call from http client, eg, EJB timer or job scheduler, it's better to set a "SERVER" string instead.
     * @param name
     */
    public void setClientIp(String clientIp);
    
    /**
     * Set the call path of the current thread or stage.It depends on which is on the top of the stack.
     * It's used as a string for monitor client. So it's better to make sense.
     * @param name
     */
    public void setCallPath(String path);
    
    /**
     * Set the call method of the current thread or stage.It depends on which is on the top of the stack.
     * It's used as a string for monitor client. So it's better to make sense.
     * @param name
     */
    public void setCallMethod(String method);
    
    /**
     * Set the request bytes from client
     * @param num
     */
    public void setRequestBytes(int num);
    
    /**
     * Set the response bytes to the client.
     * @param num
     */
    public void setReponseBytes(int num);
   
    public void attachToAsyncId(String asyncId);
    
    public void setAsyncId(String asyncId);
    
    public String getAsyncId();

    public String getCallId();
    
}
