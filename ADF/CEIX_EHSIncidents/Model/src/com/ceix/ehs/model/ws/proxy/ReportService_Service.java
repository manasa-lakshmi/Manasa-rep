package com.ceix.ehs.model.ws.proxy;

import java.io.File;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
// !DO NOT EDIT THIS FILE!
// This source file is generated by Oracle tools
// Contents may be subject to change
// For reporting problems, use the following
// Version = Oracle WebServices (11.1.1.0.0, build 150302.2135.55683)

@WebServiceClient(wsdlLocation="https://eguc-dev2.fa.us2.oraclecloud.com/xmlpserver/services/v2/ReportService?wsdl",
  targetNamespace="http://xmlns.oracle.com/oxp/service/v2", name="ReportService")
public class ReportService_Service
  extends Service
{
  private static URL wsdlLocationURL;

  private static Logger logger;
  static
  {
    try
    {
      logger = Logger.getLogger("com.ceix.ehs.model.ws.proxy.ReportService_Service");
      URL baseUrl = ReportService_Service.class.getResource(".");
      if (baseUrl == null)
      {
        wsdlLocationURL =
            ReportService_Service.class.getResource("https://eguc-dev2.fa.us2.oraclecloud.com/xmlpserver/services/v2/ReportService?wsdl");
        if (wsdlLocationURL == null)
        {
          baseUrl = new File(".").toURL();
          wsdlLocationURL =
              new URL(baseUrl, "https://eguc-dev2.fa.us2.oraclecloud.com/xmlpserver/services/v2/ReportService?wsdl");
        }
      }
      else
      {
                if (!baseUrl.getPath().endsWith("/")) {
         baseUrl = new URL(baseUrl, baseUrl.getPath() + "/");
}
                wsdlLocationURL =
            new URL(baseUrl, "https://eguc-dev2.fa.us2.oraclecloud.com/xmlpserver/services/v2/ReportService?wsdl");
      }
    }
    catch (MalformedURLException e)
    {
      logger.log(Level.ALL,
          "Failed to create wsdlLocationURL using https://eguc-dev2.fa.us2.oraclecloud.com/xmlpserver/services/v2/ReportService?wsdl",
          e);
    }
  }

  public ReportService_Service()
  {
    super(wsdlLocationURL,
          new QName("http://xmlns.oracle.com/oxp/service/v2",
                    "ReportService"));
  }

  public ReportService_Service(URL wsdlLocation, QName serviceName)
  {
    super(wsdlLocation, serviceName);
  }

  @WebEndpoint(name="ReportService")
  public com.ceix.ehs.model.ws.proxy.ReportService getReportService()
  {
    return (com.ceix.ehs.model.ws.proxy.ReportService) super.getPort(new QName("http://xmlns.oracle.com/oxp/service/v2",
                                                                               "ReportService"),
                                                                     com.ceix.ehs.model.ws.proxy.ReportService.class);
  }

  @WebEndpoint(name="ReportService")
  public com.ceix.ehs.model.ws.proxy.ReportService getReportService(WebServiceFeature... features)
  {
    return (com.ceix.ehs.model.ws.proxy.ReportService) super.getPort(new QName("http://xmlns.oracle.com/oxp/service/v2",
                                                                               "ReportService"),
                                                                     com.ceix.ehs.model.ws.proxy.ReportService.class,
                                                                     features);
  }
}
