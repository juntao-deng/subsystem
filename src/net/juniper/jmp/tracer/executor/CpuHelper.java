package net.juniper.jmp.tracer.executor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
/**
 * This code is from internet
 * @author juntaod
 *
 */
public class CpuHelper {
	private static String linuxVersion = "2.6";
	public static float getCpuRateForLinux(){   
        InputStream is = null;   
        InputStreamReader isr = null;   
        BufferedReader brStat = null;   
        StringTokenizer tokenStat = null;   
        try{   
            Process process = Runtime.getRuntime().exec("top -b -n 1");   
            is = process.getInputStream();                     
            isr = new InputStreamReader(is);   
            brStat = new BufferedReader(isr);   
             
            if(linuxVersion.equals("2.4")){   
                brStat.readLine();   
                brStat.readLine();   
                brStat.readLine();   
                brStat.readLine();   
                 
                tokenStat = new StringTokenizer(brStat.readLine());   
                tokenStat.nextToken();   
                tokenStat.nextToken();   
                String user = tokenStat.nextToken();   
                tokenStat.nextToken();   
                String system = tokenStat.nextToken();   
                tokenStat.nextToken();   
                String nice = tokenStat.nextToken();   
                 
                System.out.println(user+" , "+system+" , "+nice);   
                 
                user = user.substring(0,user.indexOf("%"));   
                system = system.substring(0,system.indexOf("%"));   
                nice = nice.substring(0,nice.indexOf("%"));   
                 
                float userUsage = new Float(user).floatValue();   
                float systemUsage = new Float(system).floatValue();   
                float niceUsage = new Float(nice).floatValue();   
                 
                return (userUsage+systemUsage+niceUsage)/100;   
            }
            else{   
                brStat.readLine();   
                brStat.readLine();   
                
                String line = brStat.readLine();
                tokenStat = new StringTokenizer(line, ",");
                String nextToken = tokenStat.nextToken();
                while(!nextToken.contains("id") && tokenStat.hasMoreTokens()){
                  nextToken = tokenStat.nextToken();
                }
//                tokenStat.nextToken();   
//                tokenStat.nextToken();   
//                tokenStat.nextToken();   
//                tokenStat.nextToken();   
//                tokenStat.nextToken();   
//                tokenStat.nextToken();   
//                tokenStat.nextToken();   
//                String cpuUsage = tokenStat.nextToken();   
//                System.out.println("CPU idle : " + cpuUsage);
                int index = nextToken.indexOf("%");
                if(index != -1){
                  nextToken = nextToken.substring(0, index);
                }
                else{
                	index = nextToken.indexOf("id");
                	if(index != -1){
                        nextToken = nextToken.substring(0, index);
                    }                
                }
                
                Float usage = Float.parseFloat(nextToken.trim());
                return 100 - usage;
            }   
        } 
        catch(Exception ioe){   
            ioe.printStackTrace();  
            freeResource(is, isr, brStat);   
            return 0;   
        } 
        finally{   
            freeResource(is, isr, brStat);   
        }   
  
    }   

	 private static void freeResource(InputStream is, InputStreamReader isr, BufferedReader br){   
		 try{   
			 if(is != null)   
				 is.close();
		 }
		 catch(IOException ioe){
			 ioe.printStackTrace();
		 }
		 try{
			 if(isr != null)
				 isr.close();
		 }
		 catch(IOException ioe){
			 ioe.printStackTrace();
		 }
		 try{
			 if(br != null)
				 br.close();
		 }
		 catch(IOException ioe){
			 ioe.printStackTrace();
		 }
	}
	 
	public static void main(String[] args){
		System.out.println(CpuHelper.getCpuRateForLinux());
	}
}
