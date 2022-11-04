package com.ceix.ehs.beans;

import com.ceix.ehs.model.loader.BIFileProcessor;

import com.ceix.ehs.model.loader.Employee;


import javax.faces.event.ValueChangeEvent;

import oracle.adf.view.rich.component.rich.RichDialog;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.nav.RichCommandButton;

import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.event.DialogEvent;

import oracle.jbo.uicli.binding.JUCtrlHierBinding;

import oracle.jbo.uicli.binding.JUCtrlHierNodeBinding;

import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.CollectionModel;

import com.ceix.ehs.model.services.EHSIncidentEventsAMImpl;
import com.ceix.ehs.model.views.CeixEhsClinicalLogVOImpl;

import com.ceix.ehs.model.views.CeixEhsClinicalLogVORowImpl;
import com.ceix.ehs.model.views.CeixEhsIncidentHeaderVOImpl;

import org.apache.myfaces.trinidad.util.Service;

import com.ceix.ehs.model.ws.proxy.AccessDeniedException;
import com.ceix.ehs.model.ws.proxy.InvalidParametersException;
import com.ceix.ehs.model.ws.proxy.OperationFailedException;
import com.ceix.ehs.util.ADFUtil;
import com.ceix.ehs.util.ADFUtils;

import com.ceix.ehs.util.EHSConstants;

import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.PhaseEvent;

import javax.faces.event.PhaseId;

import oracle.adf.model.AttributeBinding;
import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCDataControl;

import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.nav.RichResetButton;

import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.QueryEvent;


import oracle.adf.view.rich.render.OutputMode;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;
import oracle.jbo.server.ViewObjectImpl;
import oracle.jbo.server.ViewRowImpl;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;

public class ClinicalLogMB {
    private static ADFLogger _logger =
        ADFLogger.createADFLogger(ClinicalLogMB.class);

    private RichResetButton resetButton;


    private RichInputText incHdrId;
    private RichInputText rtwDesc;
    private RichInputDate rtwDate;



    public ClinicalLogMB() {
    }


    public void setResetButton(RichResetButton resetButton) {
        this.resetButton = resetButton;
    }

    public RichResetButton getResetButton() {
        return resetButton;
    }

    public void insertClinicalLog(ActionEvent action) {
        _logger.info("Inserting the Record into Clinical Log table.");

        // Add event code here...
        BindingContainer bindings =
            BindingContext.getCurrent().getCurrentBindingsEntry();
        Row currentRow = getClinicalLogRow();
        if (currentRow != null) {
            currentRow.setAttribute("Row_Status", EHSConstants.STATUS_EDIT);
            currentRow.setAttribute("LastUpdatedBy", "EHS");
        }
        OperationBinding operationBindings =
            bindings.getOperationBinding("Commit");
        _logger.info("Commited");

        Object result = operationBindings.execute();
        String messageText = "Record Saved Successfully";
        FacesMessage fm = new FacesMessage(messageText);

        if (operationBindings.getErrors().isEmpty()) {
      
            _logger.info("Successful in inserting the record");

            fm.setSeverity(FacesMessage.SEVERITY_INFO);
        }else{
            messageText=operationBindings.getErrors().get(0)!=null?operationBindings.getErrors().get(0).toString():"Errors received in inserting the record";
            operationBindings =
                        bindings.getOperationBinding("Rollback");
   
            _logger.warning("Errors received in inserting the record");
            fm.setSeverity(FacesMessage.SEVERITY_ERROR);
        }
        


        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, fm);
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


    public void onTableRowSelection(SelectionEvent selectionEvent) {
   
        ADFUtil.invokeEL("#{bindings.CeixEhsClinicalLog21.collectionModel.makeCurrent}",
                         new Class[] { SelectionEvent.class },
                         new Object[] { selectionEvent });
    }


  

    public void onSelEmpNameTable(SelectionEvent selectionEvent) {
        ADFUtil.invokeEL("#{bindings.CeixEhsEmployee21.collectionModel.makeCurrent}",
                         new Class[] { SelectionEvent.class },
                         new Object[] { selectionEvent });
    }


    public void setIncHdrId(RichInputText incHdrId) {
        this.incHdrId = incHdrId;
    }

    public RichInputText getIncHdrId() {
        return incHdrId;
    }


 


    public void setRtwDesc(RichInputText rtwDesc) {
        this.rtwDesc = rtwDesc;
    }

    public RichInputText getRtwDesc() {
        return rtwDesc;
    }

    public void setRtwDate(RichInputDate rtwDate) {
        this.rtwDate = rtwDate;
    }

    public RichInputDate getRtwDate() {
        return rtwDate;
    }

    public void onRtwValueChange(ValueChangeEvent vcc) {
        System.out.println("testing value change" +
                           vcc.getNewValue().toString());
      
        if (vcc.getNewValue() != null){
            if (((String)vcc.getNewValue()).contains("1")) {
                this.getRtwDate().setDisabled(false);
                this.getRtwDesc().setDisabled(true);


            } else {
                this.getRtwDate().setDisabled(true);
                this.getRtwDesc().setDisabled(false);


            }
        }else{
            this.getRtwDate().setDisabled(true);
            this.getRtwDesc().setDisabled(true);
        }
        this.getRtwDate().setValue("");
        this.getRtwDesc().resetValue();
        AdfFacesContext.getCurrentInstance().addPartialTarget(getRtwDate());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getRtwDesc());
    }


    public boolean isNewRow() {

        //This is getting emp details
        Row currentRow = getClinicalLogRow();

        if (currentRow != null) {
                   _logger.info("Row Status" +
                               currentRow.getAttribute("Row_Status"));

            return currentRow.getAttribute("LastUpdatedBy") != null ? true :
                   false;
            //return   !currentRow.getAttribute("Row_Status").toString().equals(EHSConstants.STATUS_NEW);
        }


        return false;

    }

    private Row getClinicalLogRow() {
        EHSIncidentEventsAMImpl appM = getAM();
        CeixEhsClinicalLogVOImpl vo = appM.getCeixEhsClinicalLog2();
        Row row = vo.getCurrentRow();
        if (row != null)
                   _logger.info("Incident Header Id for this clinical record" +
                               row.getAttribute("IncidentHeaderId"));
        return row;
    }


   
}
