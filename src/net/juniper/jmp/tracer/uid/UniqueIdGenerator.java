package net.juniper.jmp.tracer.uid;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

public final class UniqueIdGenerator {

    public static final String OID_BASE_INITIAL_VAL = "10000000000000";

    private Logger logger = Logger.getLogger(UniqueIdGenerator.class);
    
    private String oidPrefix;
    
    private Lock lock;

    private static UniqueIdGenerator instance = new UniqueIdGenerator();
    
    
    private UniqueIdGenerator() {
        lock = new ReentrantLock();
        initOidBase();
    }


    public static UniqueIdGenerator getInstance() {
        return instance;
    }

    public String nextOid() {
        try {
            lock.lock();
            String nextOid = OidBaseAlgorithm.getInstance().nextOidBase();
            return getOidPrefix() + nextOid;
        } 
        finally {
            lock.unlock();
        }
    }


    private String getOidPrefix() {
        if(oidPrefix == null){
            try{
                Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
                while (allNetInterfaces.hasMoreElements()){
                    NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                    Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                    while (addresses.hasMoreElements()){
                        InetAddress ip = (InetAddress) addresses.nextElement();
                        if (ip != null && ip instanceof Inet4Address){
                            String ipStr = ip.getHostAddress();
                            oidPrefix = formatPrefix(ipStr);
                            break;
                        }
                    }
                    if(oidPrefix != null)
                        break;
                }
            }
            catch(Exception e){
                logger.error(e.getMessage(), e);
                oidPrefix = mockOidPrefix();
            }
        }
        return oidPrefix;
    }


    private String formatPrefix(String ipStr) {
        String[] strs = ipStr.split("\\.");
//      String str1 = strs[2];
        String str2 = strs[3];
        String ts = System.currentTimeMillis() + "";
        
        return fill3Chars(str2) + ts.substring(ts.length() - 3);
    }


    private String fill3Chars(String str) {
        int length = str.length();
        if(length == 3)
            return str;
        for(int i = 0; i < (3 - length); i ++){
            str = "0" + str;
        }
        return str;
    }


    private String mockOidPrefix() {
        String ts = System.currentTimeMillis() + "";
        return ts.substring(ts.length() - 6, ts.length());
    }


    private void initOidBase() {
        OidBaseAlgorithm.getInstance().setOidBaseCodes(OID_BASE_INITIAL_VAL.getBytes());
    }
    
    public static void main(String[] args){
        for(int i = 0; i < 100; i ++){
            System.out.println(UniqueIdGenerator.getInstance().nextOid());
        }
    }

}