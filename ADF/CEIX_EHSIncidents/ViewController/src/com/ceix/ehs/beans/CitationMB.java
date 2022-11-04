package com.ceix.ehs.beans;


import com.ceix.ehs.model.services.EHSIncidentEventsAMImpl;
import com.ceix.ehs.model.views.CeixEhsClinicalLogVOImpl;
import com.ceix.ehs.model.views.CeixEhsViolationNoticeVOImpl;
import com.ceix.ehs.util.ADFUtil;
import com.ceix.ehs.util.ADFUtils;

import com.ceix.ehs.util.EHSConstants;

import java.util.logging.Level;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCDataControl;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.nav.RichCommandButton;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.QueryEvent;

import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;

import oracle.jbo.Row;
import oracle.jbo.ViewObject;

import org.apache.myfaces.trinidad.event.SelectionEvent;


public class CitationMB {
    ADFLogger _logger= ADFLogger.createADFLogger(CitationMB.class);
    
    private RichSelectOneChoice violationState;
    private RichCommandButton editButton;
    private RichCommandButton addButton;
    private RichOutputText citationNumber;
    private String heading;
    private RichSelectBooleanCheckbox dispSticVal;
    private RichOutputText violation_id;
    private RichSelectBooleanCheckbox dispOutbyVal;
    private boolean isAdd;

    public CitationMB() {
    }

    //Method to create AM

    private EHSIncidentEventsAMImpl getAM() {
        FacesContext context = FacesContext.getCurrentInstance();
        BindingContext bindingContext = BindingContext.getCurrent();
        DCDataControl dc =
            bindingContext.findDataControl("EHSIncidentEventsAMDataControl");
        EHSIncidentEventsAMImpl appM =
            (EHSIncidentEventsAMImpl)dc.getDataProvider();
        return appM;
    }

    //Method to Add Citation

    public void addCitation() {
        // Add event code here...
        this.heading = "Add Citation Notice";
        EHSIncidentEventsAMImpl appM = getAM();
        ViewObject vo = appM.getCeixEhsViolationNotice1();
        //ViewObject vo = appM.findViewObject("GetCustomerAddressVO1");
        Row firstRow = vo.first();
        int firstRowIndex = vo.getRangeIndexOf(firstRow);
        Row newRow = vo.createRow();
        vo.insertRow(newRow);
        vo.setCurrentRow(newRow);
        isAdd=true;
        _logger.log(Level.FINER, "created");

    }

    //Method to edit Citation

    public void editCitation() {
        this.heading = "Edit Citation Notice";
        BindingContainer bindings =
            BindingContext.getCurrent().getCurrentBindingsEntry();
        Row currentRow = getCitationRow();
        if (currentRow != null) {
            Object sticVal = currentRow.getAttribute("Stic");
            if(sticVal==null)
                sticVal="2-YesOrNo";
            currentRow.setAttribute("DisSticVal", sticVal.equals("1-YesOrNo")?Boolean.TRUE:Boolean.FALSE);
            
            Object outbyVal= currentRow.getAttribute("Outby");
            if(outbyVal==null)
                outbyVal="2-YesOrNo";
            currentRow.setAttribute("DisOutbyVal", outbyVal.equals("1-YesOrNo")?Boolean.TRUE:Boolean.FALSE);
            
            isAdd=false;
        }
    }
    
    private Row getCitationRow() {
        EHSIncidentEventsAMImpl appM = getAM();
        CeixEhsViolationNoticeVOImpl vo = appM.getCeixEhsViolationNotice1();
        Row row = vo.getCurrentRow();

        return row;
    }


    //Method to Save Citation

    public void saveCitation(ActionEvent actionEvent) {
        // Add event code here...
//        BindingContainer bindings =
//            BindingContext.getCurrent().getCurrentBindingsEntry();
//
//        OperationBinding operationBindings =
//            bindings.getOperationBinding("Commit2");
//        Object result = operationBindings.execute();
        //if (operationBindings.getErrors().isEmpty()) {
        if (this.dispSticVal!=null ){
            BindingContainer bindings =
                BindingContext.getCurrent().getCurrentBindingsEntry();
            AttributeBinding stic =
                         (AttributeBinding)bindings.getControlBinding("Stic");
            if (this.dispSticVal.getValue().equals(Boolean.FALSE)){
                stic.setInputValue("2-YesOrNo");
            }else{
                stic.setInputValue("1-YesOrNo");

            }
            //_logger.log(Level.FINER, this.dispSticVal.getValue());
        }
        if (this.dispOutbyVal!=null ){
            BindingContainer bindings =
                BindingContext.getCurrent().getCurrentBindingsEntry();
            AttributeBinding outby =
                         (AttributeBinding)bindings.getControlBinding("Outby");
            Object val = this.dispOutbyVal.getValue();
            
            if (this.dispOutbyVal.getValue()==null||this.dispOutbyVal.getValue().equals(Boolean.FALSE)){
                outby.setInputValue("2-YesOrNo");
            }else{
                outby.setInputValue("1-YesOrNo");

            }
            //_logger.log(Level.FINER, this.dispOutbyVal.getValue());
        }
        
            EHSIncidentEventsAMImpl appM = getAM();
            appM.save();
            isAdd=false;
            String messageText = "Record Saved Successfully";
            System.out.println(messageText);
            FacesMessage fm = new FacesMessage(messageText);

            fm.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, fm);
       // }
    }

    public void setViolationState(RichSelectOneChoice violationState) {
        this.violationState = violationState;
    }

    public RichSelectOneChoice getViolationState() {
        return violationState;
    }

    //    public String clearEmptyRows() {
    //        // Add event code here...
    //
    //        FacesContext fctx = FacesContext.getCurrentInstance();
    //        DCBindingContainer bindings =
    //            (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
    //         DCIteratorBinding iter =
    //            bindings.findIteratorBinding("CeixEhsViolationNotice1Iterator");
    //        long rowCount = iter.getEstimatedRowCount();
    //
    //        if (rowCount == 0) {
    //           // disableAddNewButtons(true);
    //            System.out.println("Delete");
    //            iter.removeCurrentRow();
    //            iter.refreshIfNeeded();
    //        }
    ////        Row rw = iter.getCurrentRow();
    ////        iter.removeCurrentRow();
    ////        iter.refreshIfNeeded();
    ////        EHSIncidentEventsAMImpl appM = getAM();
    ////        ViewObject vo = appM.getCeixEhsViolationNotice1();
    ////        vo.executeEmptyRowSet();
    ////        vo.clearCache();
    //        return "back";
    //    }

    public void citationSelectionListener(SelectionEvent selectionEvent) {
        // Add event code here...

        ADFUtil.invokeEL("#{bindings.CeixEhsViolationNotice11.collectionModel.makeCurrent}",
                         new Class[] { SelectionEvent.class },
                         new Object[] { selectionEvent });

        System.out.println("Listener");
        if (editButton.isDisabled()) {
            this.editButton.setDisabled(false);
        }
        //        if(!addButton.isDisabled())
        //        {
        //        this.addButton.setDisabled(true);
        //        }
        //AdfFacesContext.getCurrentInstance().addPartialTarget(addButton);
        AdfFacesContext.getCurrentInstance().addPartialTarget(editButton);

    }

    public void setEditButton(RichCommandButton editButton) {
        this.editButton = editButton;
    }

    public RichCommandButton getEditButton() {
        return editButton;
    }

    public void setAddButton(RichCommandButton addButton) {
        this.addButton = addButton;
    }

    public RichCommandButton getAddButton() {
        return addButton;
    }


    public void setCitationNumber(RichOutputText citationNumber) {
        this.citationNumber = citationNumber;
    }

    public RichOutputText getCitationNumber() {
        return citationNumber;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getHeading() {
        return heading;
    }


    public void citationQueryListener(QueryEvent queryEvent) {
        // Add event code here...

        ADFUtil.invokeEL("#{bindings.FindByMineIdandViolationJurisdicionQuery2.processQuery}",
                         new Class[] { QueryEvent.class },
                         new Object[] { queryEvent });

        DCIteratorBinding iterator =
            ADFUtils.findIterator("CeixEhsViolationNotice1Iterator");

        long rowCount = iterator.getEstimatedRowCount();

        if (rowCount == 0) {
            System.out.println("query Listener");
            // this.addButton.setDisabled(false);
            this.editButton.setDisabled(true);
            //AdfFacesContext.getCurrentInstance().addPartialTarget(addButton);
            AdfFacesContext.getCurrentInstance().addPartialTarget(editButton);

        } else {
            //this.addButton.setDisabled(true);
            //AdfFacesContext.getCurrentInstance().addPartialTarget(addButton);
            this.editButton.setDisabled(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(editButton);
        }
    }

    public void clearFedSectionVals(ValueChangeEvent valueChangeEvent) {
        
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        
        // Add event code here...
//        FacesContext context = FacesContext.getCurrentInstance();
//        BindingContext bc = BindingContext.getCurrent();
//        JUCtrlListBinding listBindings = (JUCtrlListBinding)bc.get("CeixEhsIncidentParentLookupsVO1");
//        if( listBindings!=null){
//            listBindings.clearSelectedIndices();
//        } 
        
        
    }

    public void setDispSticVal(RichSelectBooleanCheckbox dispSticVal) {
        this.dispSticVal = dispSticVal;
    }

    public RichSelectBooleanCheckbox getDispSticVal() {

        return dispSticVal;
    }

    public void setViolation_id(RichOutputText violation_id) {
        this.violation_id = violation_id;
    }

    public RichOutputText getViolation_id() {
        return violation_id;
    }

    public void setDispOutbyVal(RichSelectBooleanCheckbox dispOutbyVal) {
        this.dispOutbyVal = dispOutbyVal;
    }

    public RichSelectBooleanCheckbox getDispOutbyVal() {
        return dispOutbyVal;
    }


    public void setIsAdd(boolean isAdd) {
        this.isAdd = isAdd;
    }

    public boolean isIsAdd() {
        return isAdd;
    }
}
