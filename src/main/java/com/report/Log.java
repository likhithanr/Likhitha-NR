package com.report;

import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.testng.ITestResult;

/**
 * @author Likhitha NR
 */

public class Log {

  static Logger log = Logger.getLogger(Log.class);
  static Map<Integer, Logger> loggerTestMap = new HashMap<Integer, Logger>();
  private static ThreadLocal<Logger> threadtest = new ThreadLocal<Logger>();

  /**
   * @return Logger
   */
  public static synchronized Logger getLogger() {
    return loggerTestMap.get((int) Thread.currentThread().getId());
  }

  /**
   * @return Logger
   */
  public static synchronized Logger startLogger() {
    Logger test = Logger.getLogger(Log.class);
    threadtest.set(test);
    loggerTestMap.put((int) Thread.currentThread().getId(), test);
    return test;
  }

  /**
   * @return String
   */
  public static String getCallInfo() {
    String callInfo;
    String className = Thread.currentThread().getStackTrace()[3].getClassName();
    String methodName = Thread.currentThread().getStackTrace()[3].getMethodName();
    callInfo = className + " : " + methodName + " ";

    return callInfo;
  }

  /**
   * @param message
   */
  // Info Level Logs
  public static void log(Object message) {
    getLogger().info(message);
  }

  /**
   * @param message
   */
  // Info Level Logs
  public static void info(Object message) {
    getLogger().info(message);
  }

  /**
   * @param message
   */
  // Info Level Logs
  public static void scriptInfo(Object message) {
    getLogger().info(message);
    TestInfo.info(message.toString());
  }

  /**
   * @param message
   */
  // Info Level Logs
  public static void testInfo(Object message) {
    TestInfo.info(message.toString());
  }

  /**
   * @param message
   * @param t
   */
  // Info Level Logs
  public static void info(Object message, Throwable t) {
    getLogger().info(message, t);
  }

  /**
   * @param message
   */
  // Warn Level Logs
  public static void warn(Object message) {
    getLogger().warn(message);
  }

  /**
   * @param message
   * @param t
   */
  // Warn Level Logs
  public static void warn(Object message, Throwable t) {
    getLogger().warn(message, t);
  }

  /**
   * @param result
   * @param t
   */
  // Error Level Logs
  public static void error(ITestResult result, Throwable t) {
    getLogger().error(result.getMethod().getMethodName() + " : FAILED : ", t);
  }

  /**
   * @param t
   */
  // Error Level Logs
  public static void error(Throwable t) {
    getLogger().error("Failed due to " + t);
  }

  /**
   * @param message
   */
  // Fatal Level Logs
  public static void fatal(Object message) {
    getLogger().fatal(message);
  }

  /**
   * @param message
   * @param error
   */
  // Fatal Level Logs
  public static void fatal(Object message, Throwable error) {
    getLogger().fatal(getCallInfo() + message, error);
  }

  /**
   * @param message
   */
  // Debug Level Logs
  public static void debug(Object message) {
    getLogger().debug(getCallInfo() + message);
  }

  /**
   * @param message
   * @param error
   */
  // Debug Level Logs
  public static void debug(Object message, Throwable error) {
    getLogger().debug(getCallInfo() + message, error);

  }

  public static void endTestCase() {
    log("***********************************Test execution ended************************************\n");
  }
}
