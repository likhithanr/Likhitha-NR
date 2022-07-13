package com.core;

import java.io.IOException;
import com.report.Log;
import com.report.ReportListener;
import com.report.TestListener;
import com.utils.ConfigManager;

import org.testng.annotations.Listeners;

@Listeners({ TestListener.class, ReportListener.class })
public abstract class BaseTest {

  // constructor to intinalize logger
  public BaseTest() {
    Log.startLogger();
    try {
      ConfigManager.loadProperties();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public abstract void scriptStart();

}
