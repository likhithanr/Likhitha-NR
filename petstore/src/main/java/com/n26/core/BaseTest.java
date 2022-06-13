package com.n26.core;

import java.io.IOException;
import com.n26.report.Log;
import com.n26.report.ReportListener;
import com.n26.report.TestListener;
import com.n26.utils.ConfigManager;

import org.testng.annotations.Listeners;


@Listeners({TestListener.class,ReportListener.class})
public abstract class BaseTest {
    
  //constructor to intinalize logger
   public BaseTest()
   {
        Log.startLogger();
        try {
          ConfigManager.loadProperties();
      } catch (IOException e) {
          e.printStackTrace();
      }
   }

   public abstract void scriptStart();
    
}

