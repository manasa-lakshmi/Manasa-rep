package com.ceix.ehs.beans;

import com.ceix.ehs.model.services.EHSIncidentEventsAMImpl;
import com.ceix.ehs.model.views.CeixEhsClinicalLogVOImpl;
import com.ceix.ehs.model.views.CeixEhsClinicalLogVORowImpl;
import com.ceix.ehs.model.views.CeixEhsIncidentHeaderVOImpl;
import com.ceix.ehs.model.views.CeixEhsIncidentHeaderVORowImpl;

import com.ceix.ehs.util.ADFUtil;

import com.ceix.ehs.util.ADFUtils;

import com.ceix.ehs.util.EHSConstants;

import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.component.rich.RichDialog;
import oracle.adf.view.rich.component.rich.RichPopup;

import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.QueryOperationEvent;

import oracle.binding.BindingContainer;

import oracle.adf.model.BindingContext;

import oracle.binding.OperationBinding;

import oracle.adf.model.binding.DCDataControl;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.RichForm;
import oracle.adf.view.rich.component.rich.nav.RichCommandButton;


import oracle.adf.view.rich.component.rich.nav.RichResetButton;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.QueryEvent;

import oracle.jbo.Row;

import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewObject;

import oracle.jbo.server.ViewObjectImpl;

import org.apache.myfaces.trinidad.event.SelectionEvent;

public class SearchIncidentsMB {

    private static ADFLogger _logger =
        ADFLogger.createADFLogger(SearchIncidentsMB.class);

    private RichCommandButton addNewClinLogBtn;
    private RichTable searchTable;
    private RichCommandButton viewReportBtn;
    private RichDialog deleteConfirmation;


    public SearchIncidentsMB() {
    }

    public void incidentSelectionListener(SelectionEvent selectionEvent) {
        _logger.info("Selecting an incident from the table");
        //First make them disabled
        addNewClinLogBtn.setDisabled(false);
 
        
        
        ADFUtil.invokeEL("#{bindings.CeixEhsIncidentHeader1.collectionModel.makeCurrent}",
                         new Class[] { SelectionEvent.class },
                         new Object[] { selectionEvent });

        //This code is for browser back button

        DCIteratorBinding itorBinding =
            ADFUtils.findIterator("CeixEhsClinicalLog2Iterator");
        Row currentRow = null;
        if (itorBinding != null) {
            currentRow = itorBinding.getCurrentRow();
            if (currentRow != null &&
                currentRow.getAttribute("IncidentHeaderId") != null &&
                currentRow.getAttribute("LastUpdatedBy") == null) {
                //                itorBinding.removeCurrentRow();
                currentRow.refresh(Row.REFRESH_UNDO_CHANGES |
                                   Row.REFRESH_WITH_DB_FORGET_CHANGES);
                itorBinding.refreshIfNeeded();

            }


        }
         
         boolean rowExists = getClinicalDetails();//itorBinding.getEstimatedRowCount();
        _logger.info("Found ClinicalLog Rows Count:" + rowExists);
        if (rowExists ) {
           this.getViewReportBtn().setDisabled(false);
            this.getAddNewClinLogBtn().setText("Edit Clinical Log");
        } else {
            this.getAddNewClinLogBtn().setText("Add Clinical Log");
            this.getViewReportBtn().setDisabled(true);
        }
        System.out.println("count" + rowExists);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getViewReportBtn());

        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getAddNewClinLogBtn());
    }

    private void disableAddNewButtons(boolean enabled) {

        addNewClinLogBtn.setDisabled(enabled);

        this.getViewReportBtn().setDisabled(enabled);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getViewReportBtn());

        AdfFacesContext.getCurrentInstance().addPartialTarget(addNewClinLogBtn);

    }

    public void searchIncidentsListener(QueryEvent queryEvent) {
        _logger.info("Searchig an incident, this is removing prev selected rows from the table");
        // as this is retaining the prev current row selection
        if (searchTable.getSelectedRowKeys() != null) {
            searchTable.getSelectedRowKeys().clear();
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(searchTable);


        ADFUtil.invokeEL("#{bindings.SearchIncidentsQuery.processQuery}",
                         new Class[] { QueryEvent.class },
                         new Object[] { queryEvent });


        disableAddNewButtons(true);


    }


    public void setAddNewClinLogBtn(RichCommandButton addNewClinLogBtn) {
        this.addNewClinLogBtn = addNewClinLogBtn;
    }

    public RichCommandButton getAddNewClinLogBtn() {
        return addNewClinLogBtn;
    }


    public void initCriticalLog() {
        _logger.info("initCriticalLog Row");

        System.out.println(">>initCriticalLog");
        if (searchTable.getSelectedRowKeys() != null) {
            searchTable.getSelectedRowKeys().clear();
        }
        EHSIncidentEventsAMImpl appM = getAM();
        CeixEhsIncidentHeaderVOImpl ehs = appM.getCeixEhsIncidentHeader1();
        Row currentRow = ehs.getCurrentRow();


        CeixEhsClinicalLogVOImpl vo = appM.getCeixEhsClinicalLog2();

        vo.setNamedWhereClauseParam("incidentHdrId",
                                    currentRow.getAttribute("IncidentHeaderId"));

        vo.setApplyViewCriteriaName("ClinicalLogsWithHeaderId");
        vo.executeQuery();
        Row row1 = vo.next();

        if (row1 == null) {
            _logger.info("Creating a new Row");

            Row row = vo.createRow();
            row.setAttribute("Row_Status", EHSConstants.STATUS_NEW);
            row.setAttribute("IncidentHeaderId",
                             currentRow.getAttribute("IncidentHeaderId"));
            row.setAttribute("InjuryType",
                             currentRow.getAttribute("InjuryType") != null ?
                             currentRow.getAttribute("InjuryType").toString() :
                             "");
            row.setAttribute("BodyPart",
                             currentRow.getAttribute("BodyPart") != null ?
                             currentRow.getAttribute("BodyPart").toString() :
                             "");

            System.out.println("inside method" +
                               row.getAttribute("ClinicalId"));
            vo.insertRow(row);
                        vo.setCurrentRow(row);

        } else {

            row1.setAttribute("Row_Status", EHSConstants.STATUS_EDIT);
            _logger.info("Editing the existing row");
                         vo.setCurrentRow(row1);

        }
    }


    private EHSIncidentEventsAMImpl getAM() {
        FacesContext context = FacesContext.getCurrentInstance();
        BindingContext bindingContext = BindingContext.getCurrent();
        DCDataControl dc =
            bindingContext.findDataControl("EHSIncidentEventsAMDataControl");
        EHSIncidentEventsAMImpl appM =
            (EHSIncidentEventsAMImpl)dc.getDataProvider();
        return appM;
    }


    public void setCurrentIncidentRow(ActionEvent actionEvent) {
   
    }

    private boolean getClinicalDetails() {
        EHSIncidentEventsAMImpl appM = getAM();
        CeixEhsIncidentHeaderVOImpl ehs = appM.getCeixEhsIncidentHeader1();
        Row currentRow = ehs.getCurrentRow();


        CeixEhsClinicalLogVOImpl vo = appM.getCeixEhsClinicalLog2();

        vo.setNamedWhereClauseParam("incidentHdrId",
                                    currentRow.getAttribute("IncidentHeaderId"));

        vo.setApplyViewCriteriaName("ClinicalLogsWithHeaderId");
        vo.executeQuery(); //executeVO with Criteria
       long count= vo.getEstimatedRowCount();
         return count>0 ?true:false;
    }


    //Method to Add Violation

    public void addViolationNotice() {

        // Add event code here...
        EHSIncidentEventsAMImpl appM = getAM();
        ViewObject vo = appM.getCeixEhsViolationNotice1();
        //ViewObject vo = appM.findViewObject("GetCustomerAddressVO1");
        Row firstRow = vo.first();
        int firstRowIndex = vo.getRangeIndexOf(firstRow);
        Row newRow = vo.createRow();
        vo.insertRow(newRow);
        vo.setCurrentRow(newRow);
        System.out.println("created");

    }


    public void queryOperationListener(QueryOperationEvent queryOperationEvent) {
        _logger.info("Reset the search criteria");

    //This is for reset
        ADFUtil.invokeEL("#{bindings.SearchIncidentsQuery2.processQueryOperation}",
                         Object.class, QueryOperationEvent.class,
                         queryOperationEvent);

        System.out.println("onquery" +
                           queryOperationEvent.getOperation().name());
        if (searchTable.getSelectedRowKeys() != null) {
            searchTable.getSelectedRowKeys().clear();
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(searchTable);
        this.disableAddNewButtons(true);

    }

    public void setSearchTable(RichTable searchTable) {
        this.searchTable = searchTable;
    }

    public RichTable getSearchTable() {
        return searchTable;
    }

    public void setViewReportBtn(RichCommandButton viewReportBtn) {
        this.viewReportBtn = viewReportBtn;
    }

    public RichCommandButton getViewReportBtn() {
        return viewReportBtn;
    }


    public void setReportParams(ActionEvent actionEvent) {
        _logger.info("Setting the params to view Report");

        // Add event code here...
        if (searchTable.getSelectedRowKeys() != null) {
            searchTable.getSelectedRowKeys().clear();
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(searchTable);

        ADFUtil.invokeEL("#{pageFlowScope.ReportMB.setReportParams}",
                         Object.class, ActionEvent.class,
                         actionEvent);
    }
    
    public String deleteIncidentLog() {

         _logger.info("Invoking the Delete logs action.");

         EHSIncidentEventsAMImpl appM = getAM();
         CeixEhsIncidentHeaderVOImpl ehs = appM.getCeixEhsIncidentHeader1();
         Row ehs_currentRow = ehs.getCurrentRow();
         
         CeixEhsClinicalLogVOImpl clinicalview = appM.getCeixEhsClinicalLog2();


         clinicalview.setNamedWhereClauseParam("incidentHdrId",
                                               ehs_currentRow.getAttribute("IncidentHeaderId"));

         //clinicalview.setApplyViewCriteriaName("ClinicalLogsWithHeaderId");
         clinicalview.executeQuery(); //executeVO with Criteria
         RowSetIterator iter = clinicalview.createRowSetIterator(null);
         while (iter.hasNext()) {
             Row row = iter.next();
             row.remove();
         }

         ehs_currentRow.remove();
         appM.getDBTransaction().commit(); // Code to Commit.
         //showMessage("The Selected Record and associated Child entities are deleted.");
         
         FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Record Deleted Successfully.", "");  
         FacesContext.getCurrentInstance().addMessage(null, msg);
         
         return null;
     }
    
    public String showMessage(String message) {
        // String messageText=message;
        FacesMessage fm = new FacesMessage(message);
        fm.setSeverity(FacesMessage.SEVERITY_INFO);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, fm);
        return null;


    }

    public void setDeleteConfirmation(RichDialog deleteConfirmation) {
        this.deleteConfirmation = deleteConfirmation;
    }

    public RichDialog getDeleteConfirmation() {
        return deleteConfirmation;
    }

    public void dialogLogoutListener(DialogEvent dialogEvent)
    {
      if (dialogEvent.getOutcome() == DialogEvent.Outcome.ok)
      {
        deleteIncidentLog();
      }
        AdfFacesContext.getCurrentInstance().addPartialTarget(
         this.getSearchTable());

    }

    public void dialogDeleteList(DialogEvent dialogEvent) {
        // Add event code here...
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    
}
