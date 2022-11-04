package com.ceix.ehs.model.loader;

import com.ceix.ehs.model.ws.proxy.AccessDeniedException;
import com.ceix.ehs.model.ws.proxy.InvalidParametersException;
import com.ceix.ehs.model.ws.proxy.OperationFailedException;

import oracle.adf.share.logging.ADFLogger;

import com.ceix.ehs.model.ws.proxy.AccessDeniedException;
import com.ceix.ehs.model.ws.proxy.InvalidParametersException;
import com.ceix.ehs.model.ws.proxy.OperationFailedException;


import com.ceix.ehs.model.ws.proxy.ReportService;
import com.ceix.ehs.model.ws.proxy.ReportService_Service;
import com.ceix.ehs.model.ws.proxy.types.ArrayOfParamNameValue;
import com.ceix.ehs.model.ws.proxy.types.ArrayOfString;
import com.ceix.ehs.model.ws.proxy.types.ObjectFactory;

import com.ceix.ehs.model.ws.proxy.types.ParamNameValue;
import com.ceix.ehs.model.ws.proxy.types.ParamNameValues;
import com.ceix.ehs.model.ws.proxy.types.ReportRequest;

import com.ceix.ehs.model.ws.proxy.types.ReportResponse;


import com.opencsv.bean.CsvToBean;

import com.opencsv.bean.CsvToBeanBuilder;

import javax.xml.ws.BindingProvider;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;

import java.io.FileOutputStream;

import java.io.InputStream;

import java.io.InputStreamReader;

import java.util.Iterator;

import weblogic.wsee.jws.jaxws.owsm.SecurityPoliciesFeature;


public class BIFileProcessor {
    private static ADFLogger _logger = ADFLogger.createADFLogger(BIFileProcessor.class);
    private String BI_RPT_SRVC_ENDPOINT;
    private static final String BI_RPT_SRVC_ENDPOINT_SUFFIX
        ="/xmlpserver/services/v2/ReportService";
    private static final String BI_RPT_PATH_FOR_SCHEDULES_RPT =
    "/Custom/PAAS Integration/Inbound/HCM/EHS/Reports/CEIX EHS Employee Report.xdo";
    private String SAAS_USER;
    private String SAAS_USER_PWD;

    public BIFileProcessor() {
        ReadProperties propUtil = new ReadProperties();
        String saasUrl =propUtil.getResourceFromProperties("SAAS_HOST");
        this.BI_RPT_SRVC_ENDPOINT = saasUrl+BI_RPT_SRVC_ENDPOINT_SUFFIX;
        

        this.SAAS_USER =propUtil.getResourceFromProperties("ServiceUsername");

                this.SAAS_USER_PWD=propUtil.getResourceFromProperties("ServicePassword");
    }
    
    public  Employee process(String empId) throws AccessDeniedException,
                                 OperationFailedException,
                                 InvalidParametersException, Exception {
       File f=null;
          
        ReportService_Service reportService_Service;
        reportService_Service = new ReportService_Service();
        
        
        ReportService reportService = reportService_Service.getReportService();
        System.out.println("***    "+reportService);
        
        BindingProvider wsbp = (BindingProvider) reportService;
        
        wsbp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
                                     BI_RPT_SRVC_ENDPOINT);


        ObjectFactory of = new ObjectFactory();
        ReportRequest reportRequest = of.createReportRequest();
        reportRequest.setAttributeFormat("csv");
        reportRequest.setAttributeLocale("en-US");
        reportRequest.setByPassCache(false);
        reportRequest.setFlattenXML(false);
        reportRequest.setReportAbsolutePath(BI_RPT_PATH_FOR_SCHEDULES_RPT);
        reportRequest.setSizeOfDataChunkDownload(-1);
        ParamNameValues paramNameValues = of.createParamNameValues();
               ArrayOfParamNameValue listOfParamNameValues = of.createArrayOfParamNameValue();
               putParamNameValues(listOfParamNameValues, "P_PERSONNUM", empId);
               paramNameValues.setListOfParamNameValues(listOfParamNameValues);
               reportRequest.setParameterNameValues(paramNameValues);
        ReportResponse reportResponse=null;
        try {
              reportResponse =
                reportService.runReport(reportRequest, SAAS_USER,
                                        SAAS_USER_PWD);
        } catch (  Exception e) {
            _logger.warning(e.getMessage());
          throw new Exception(e.getMessage());
        }  
        byte[] reportBytes=reportResponse.getReportBytes();
        Employee emp=null;
        if (reportBytes.length > 0) {
            String data = new String(reportBytes);
                  
                       emp = processEmpRptData(data);
            

        } else {
            _logger.log("No data returned by BI report service.");
            throw new Exception("No data returned by BI report service.");

        }

        
        
       return emp;
        
    }




    

    private Employee processEmpRptData(String data) {
        ByteArrayInputStream bin = null;
               if (data != null) {
                   try {
                       bin = new ByteArrayInputStream(data.getBytes());
                       Iterator<Employee> iter = createBISchRptBeanIterator(bin);

                       if (iter.hasNext()) {
                           return iter.next();
                       }

                       return null;
                   } catch (Exception e) {
                       //TODO: ROMESH
                       e.printStackTrace();
                       throw e;
                   } finally {
                       try {
                           if (bin != null)
                               bin.close();
                           bin = null;
                       } catch (Exception e) {
                           e.printStackTrace();
                       }
                   }

               }
               {
                   //no results for given order
                   return null;
               }
    }
    
    private Iterator<Employee> createBISchRptBeanIterator(InputStream inputStream) {

          CsvToBean csv = new CsvToBean();
          CsvToBean<Employee> csvToBean =
              new CsvToBeanBuilder(new InputStreamReader(inputStream)).withType(Employee.class).withIgnoreLeadingWhiteSpace(true).build();

          Iterator<Employee> itr = csvToBean.iterator();

          return itr;

      }
    
    private void putParamNameValues(ArrayOfParamNameValue listOfParamNameValues, String paramName, String paramVal) {

           ObjectFactory of = new ObjectFactory();
           ParamNameValue paramNameValueSeg1 = of.createParamNameValue();
           ArrayOfString arrayOfString1 = of.createArrayOfString();
           paramNameValueSeg1.setName(paramName);
           paramNameValueSeg1.setValues(arrayOfString1);
           paramNameValueSeg1.getValues().getItem().add(paramVal);
           listOfParamNameValues.getItem().add(paramNameValueSeg1);

       }
}
