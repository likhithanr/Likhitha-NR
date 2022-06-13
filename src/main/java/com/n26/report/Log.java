package com.n26.report;

import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.testng.ITestResult;


public class Log {

     // private static final Logger log = Logger.getLogger(Log.class);
     static Logger  log = Logger.getLogger(Log.class);
      static Map<Integer, Logger> loggerTestMap = new HashMap<Integer, Logger>();
    private static ThreadLocal<Logger> threadtest = new ThreadLocal<Logger>();
   
    public static synchronized Logger getLogger() {
        return loggerTestMap.get((int) Thread.currentThread().getId());
    }

    public static synchronized Logger startLogger() {
       Logger  test = Logger.getLogger(Log.class);
        threadtest.set(test);
        loggerTestMap.put((int) Thread.currentThread().getId(), test);
        return test;
    }
 


     public static String getCallInfo()
    {
        String callInfo;
        String className=Thread.currentThread().getStackTrace()[3].getClassName();
        String methodName=Thread.currentThread().getStackTrace()[3].getMethodName();
       // int lineNumber=Thread.currentThread().getStackTrace()[3].getLineNumber();
        callInfo= className+" : "+methodName+" ";

        return callInfo;


    }

    //Info Level Logs
      public static void log (Object message) {
        getLogger().info(message);
      }
        //Info Level Logs
        public static void info (Object message) {
          getLogger().info( message);
        }
          //Info Level Logs
          public static void scriptInfo(Object message) {
            getLogger().info( message);
            TestInfo.info(message.toString());
          }
         //Info Level Logs
         public static void testInfo(Object message) {
          TestInfo.info(message.toString());
        }
       //Info Level Logs
       public static void info (Object message,Throwable t) {
        getLogger().info( message,t);
    }
      //Warn Level Logs
      public static void warn (Object message) {
        getLogger().warn(message);
      }
         //Warn Level Logs
         public static void warn (Object message,Throwable t) {
          getLogger().warn(message,t);
          }
  
       //Error Level Logs
       public static void error (ITestResult result,Throwable t) {
        getLogger().error(result.getMethod().getMethodName()+" : FAILED : ",t);
      }
        //Error Level Logs
        public static void error (Throwable t) {
          getLogger().error("Failed due to "+t);
        }
      //Fatal Level Logs
      public static void fatal (Object message) {
        getLogger().fatal(message);
      }
      //Fatal Level Logs
      public static void fatal (Object message,Throwable t) {
        getLogger().fatal(getCallInfo() + message,t);
      }
      //Debug Level Logs
      public static void debug (Object message) {
        getLogger().debug(getCallInfo() + message);
      }
       //Debug Level Logs
       public static void debug (Object message,Throwable t) {
        getLogger().debug(getCallInfo() + message,t);
      
    }


    public static void endTestCase()
    {
      
      log("***********************************Test execution ended************************************\n");
    }
}
  

