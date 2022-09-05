package com.ceix.geolabs.model.services;


import com.ceix.geolabs.loader.GeoLabsFileLoader;
import com.ceix.geolabs.model.services.common.GeoLabsReportLoadAM;
import com.ceix.geolabs.model.view.CEIXOrdQualLabResltVOImpl;
import com.ceix.geolabs.model.view.CEIXStsDeliveryLinesVOImpl;
import com.ceix.geolabs.model.view.CEIXStsRailSchedulesVOImpl;
import com.ceix.geolabs.model.view.WSResultPVOImpl;
import com.ceix.geolabs.model.view.WSResultPVORowImpl;

import java.util.List;

import oracle.jbo.server.ApplicationModuleImpl;
import oracle.jbo.server.ViewObjectImpl;
import oracle.jbo.server.ViewRowImpl;


// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Fri Apr 27 13:34:33 IST 2018
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class GeoLabsReportLoadAMImpl extends ApplicationModuleImpl implements GeoLabsReportLoadAM {
    /**
     * This is the default constructor (do not remove).
     */
    public GeoLabsReportLoadAMImpl() {
    }

   
    
    public List<ViewRowImpl> execute() {
        
        WSResultPVOImpl resultVo = this.getWSResultP1();
        
        GeoLabsFileLoader loader = new GeoLabsFileLoader();
        
        CEIXOrdQualLabResltVOImpl vo = (CEIXOrdQualLabResltVOImpl
             )this.getCEIXOrdQualLabReslt1();
        
       CEIXStsRailSchedulesVOImpl stsVO =  
           (CEIXStsRailSchedulesVOImpl)this.getCEIXStsRailSchedules1();
       
       
       
        

       /* CEIXOrdQualLabResltVORowImpl ceixOrdQualLabResltVORowImpl
            = (CEIXOrdQualLabResltVORowImpl)vo.createRow();
        ceixOrdQualLabResltVORowImpl.setTrain("hhffdd");*/

        List<ViewRowImpl> lstResult = loader.load(resultVo,vo,stsVO);
      /*  RowSetIterator iterator = vo.createRowSetIterator(null);
        iterator.reset();
        while(iterator.hasNext()) {
            CEIXOrdQualLabResltVORowImpl row = (CEIXOrdQualLabResltVORowImpl)vo.next();
            System.out.println("Rows found in iterator."+row.getTrain());

        }*/


       /* try {
            ceixOrdQualLabResltVORowImpl.setDatesamp(ConversionUtil.stringToDate(
                "01/01/2018", "MM/dd/yyyy")  );
        } catch (ParseException e) {
        }
        System.out.println("doing commit");
        vo.insertRow(ceixOrdQualLabResltVORowImpl);
        getDBTransaction().commit(); */
        
        
        return lstResult;
        //return lstResult;
    }
    
    public List<ViewRowImpl> executeItmann() {
        
        WSResultPVOImpl resultVo = this.getWSResultP1();
        
        GeoLabsFileLoader loader = new GeoLabsFileLoader();
        
        CEIXOrdQualLabResltVOImpl vo = (CEIXOrdQualLabResltVOImpl
             )this.getCEIXOrdQualLabReslt1();
        
    //   CEIXStsRailSchedulesVOImpl stsVO =  
        //   (CEIXStsRailSchedulesVOImpl)this.getCEIXStsRailSchedules1();
       
        CEIXStsDeliveryLinesVOImpl stsDelVO=
            (CEIXStsDeliveryLinesVOImpl)this.getCEIXStsDeliveryLines1();
        

       /* CEIXOrdQualLabResltVORowImpl ceixOrdQualLabResltVORowImpl
            = (CEIXOrdQualLabResltVORowImpl)vo.createRow();
        ceixOrdQualLabResltVORowImpl.setTrain("hhffdd");*/

      //  List<ViewRowImpl> lstResult = loader.load(resultVo,vo,stsVO);
        List<ViewRowImpl> lstResult = loader.loadItmann(resultVo,vo,stsDelVO);
      /*  RowSetIterator iterator = vo.createRowSetIterator(null);
        iterator.reset();
        while(iterator.hasNext()) {
            CEIXOrdQualLabResltVORowImpl row = (CEIXOrdQualLabResltVORowImpl)vo.next();
            System.out.println("Rows found in iterator."+row.getTrain());

        }*/


       /* try {
            ceixOrdQualLabResltVORowImpl.setDatesamp(ConversionUtil.stringToDate(
                "01/01/2018", "MM/dd/yyyy")  );
        } catch (ParseException e) {
        }
        System.out.println("doing commit");
        vo.insertRow(ceixOrdQualLabResltVORowImpl);
        getDBTransaction().commit(); */
        
        
        return lstResult;
        //return lstResult;
    }
    
    public String executeManual() {
        
        WSResultPVOImpl resultVo = this.getWSResultP1();
        
        GeoLabsFileLoader loader = new GeoLabsFileLoader();
        
        CEIXOrdQualLabResltVOImpl vo = (CEIXOrdQualLabResltVOImpl
             )this.getCEIXOrdQualLabReslt1();
        CEIXStsRailSchedulesVOImpl stsVO =  
            (CEIXStsRailSchedulesVOImpl)this.getCEIXStsRailSchedules1();
        
        WSResultPVORowImpl row=null;
        String status="Unexpected error.";
        String msg ="";
        List<ViewRowImpl> lstResult = loader.load(resultVo,vo,stsVO);
        if(lstResult!=null&&lstResult.size()>0) {
            row = (WSResultPVORowImpl)lstResult.get(0);
            status = row.getStatus();
            msg = row.getMessage();
            
        }
        return status+", "+msg;

    }
    
    
    /*public CEIXOrdQualLabResltVORowImpl labResultRecordExistsAlready(String trainNo,Date reportDate) {
    
        boolean exists=false;
        ViewObjectImpl vo = this.getCEIXOrdQualLabReslt1();
        CEIXOrdQualLabResltVORowImpl row =null;
        vo.setNamedWhereClauseParam("trainNo", trainNo); 

        vo.setNamedWhereClauseParam("rptSampleDt", reportDate);
        
        vo.setApplyViewCriteriaName("FindByTrainAndSamplDtVOCriteria");

        vo.executeQuery(); //executeVO with Criteria
        long rowCount = vo.getEstimatedRowCount();
        if(rowCount>0) {
            row = (CEIXOrdQualLabResltVORowImpl)vo.next();
            exists=true;
        }
            
        
        //remove the criteria
        vo.removeViewCriteria("FindByTrainAndSamplDtVOCriteria");
        vo.executeQuery();    
        
        return row;
    }*/
    
  
    
  
    
    /**
     * Container's getter for CEIXOrdQualLabReslt1.
     * @return CEIXOrdQualLabReslt1
     */
    public CEIXOrdQualLabResltVOImpl getCEIXOrdQualLabReslt1() {
        return (CEIXOrdQualLabResltVOImpl) findViewObject("CEIXOrdQualLabReslt1");
    }


    /**
     * Container's getter for WSResultP1.
     * @return WSResultP1
     */
    public WSResultPVOImpl getWSResultP1() {
        return (WSResultPVOImpl)findViewObject("WSResultP1");
    }

    /**
     * Container's getter for CEIXStsRailSchedules1.
     * @return CEIXStsRailSchedules1
     */
    public ViewObjectImpl getCEIXStsRailSchedules1() {
        return (ViewObjectImpl)findViewObject("CEIXStsRailSchedules1");
    }

   
    /**
     * Container's getter for CEIXStsDeliveryLines1.
     * @return CEIXStsDeliveryLines1
     */
    public CEIXStsDeliveryLinesVOImpl getCEIXStsDeliveryLines1() {
        return (CEIXStsDeliveryLinesVOImpl) findViewObject("CEIXStsDeliveryLines1");
    }
}