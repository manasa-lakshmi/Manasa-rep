package com.ceix.quality.test;

import oracle.adf.share.ADFContext;

import oracle.jbo.*;
import oracle.jbo.client.Configuration;
import oracle.jbo.domain.*;
import oracle.jbo.domain.Number;

public class TestClient {
  
  public static void main(String []s) {
      ADFContext oldContext =
            ADFContext.initADFContext(null, null, null, null);
        try {
            String amDef = "test.TestModule";
            String config = "TestModuleLocal";
            ApplicationModule am =
                Configuration.createRootApplicationModule(amDef, config);
            ViewObject vo = am.findViewObject("TestView");
            // Work with your appmodule and view object here
            Configuration.releaseRootApplicationModule(am, true);
        } finally {
            ADFContext.resetADFContext(oldContext);
        }
  }
    
}
