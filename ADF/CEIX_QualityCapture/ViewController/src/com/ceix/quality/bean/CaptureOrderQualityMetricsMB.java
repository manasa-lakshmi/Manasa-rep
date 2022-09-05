package com.ceix.quality.bean;

import com.ceix.util.ADFUtil;

import com.ceix.util.JSFUtils;

import java.util.logging.Level;

import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;

import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.QueryEvent;

import org.apache.myfaces.trinidad.event.SelectionEvent;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.binding.OperationBinding;

import oracle.jbo.Key;
import oracle.jbo.Row;

import oracle.jbo.uicli.binding.JUCtrlHierBinding;
import oracle.jbo.uicli.binding.JUCtrlHierNodeBinding;

import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.RowKeySet;

public class CaptureOrderQualityMetricsMB {
    
    private static ADFLogger _logger = 
               ADFLogger.createADFLogger(CaptureOrderQualityMetricsMB.class); 
    
    private RichPanelGroupLayout _qualityMetricsFormContainr;
    private RichTable _resultsTable;
    private RichPopup _notApplicableConfirmationPopup;
    private RichSelectBooleanCheckbox _notApplicableTypicalCheckbox;
    private String currentPopupCheckBoxId;
    private final int NUMBER_OF_MATRICS_INPUTBOXES=23;
    private RichSelectBooleanCheckbox _notApplicableSuspensionCheckbox;
    private RichSelectBooleanCheckbox _notApplicableRejectCheckbox;
    private final String TYPICAL_CHECKBOX_KEY="typical";
    private final String SUSPENSION_CHECKBOX_KEY="suspension";
    private final String REJECT_CHECKBOX_KEY="reject";
    
   /* QueryEvent queryEvent;
    String currentRowKey;
    Key _rowKey ;*/
    
    public void queryListener(QueryEvent queryEvent) {
        _logger.log(Level.FINE,"queryListener");
        //#{bindings.CEIXSalesOrdersVOCriteriaQuery.processQuery}
        ADFUtil.invokeEL("#{bindings.CEIXSalesOrdersVOCriteriaQuery.processQuery}", new Class[] { QueryEvent.class },
                             new Object[] { queryEvent });

        DCBindingContainer bindings=(DCBindingContainer)ADFUtil.getBindings();
        DCIteratorBinding dcIter=bindings.findIteratorBinding("CEIXSalesOrders1Iterator");       
        
        
        long rowCount = dcIter.getEstimatedRowCount();
        
        _resultsTable.getSelectedRowKeys().removeAll();
        
        if(rowCount==0) 
             {
                 _qualityMetricsFormContainr.setVisible(false);
                 AdfFacesContext.getCurrentInstance().addPartialTarget(_qualityMetricsFormContainr);
                 
             }
       
        //this.queryEvent=queryEvent;
    }
    
    public void rowSelectionListener(SelectionEvent selectionEvent) {
        // +#{bindings.CEIXSalesOrders1.collectionModel.makeCurrent}
        ADFUtil.invokeEL("#{bindings.CEIXSalesOrders1.collectionModel.makeCurrent}", new Class[] { SelectionEvent.class },
                             new Object[] { selectionEvent });
        
        //make sure all input boxes are enabled.
        enableAllInputBoxes();
        
        DCBindingContainer bindings=(DCBindingContainer)ADFUtil.getBindings();
        DCIteratorBinding dcIter2=bindings.findIteratorBinding("CEIXOrdQualMetrics2Iterator");       
        long rowCount2 = dcIter2.getEstimatedRowCount();
        if(rowCount2==0) {
            OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert");
            Object result = operationBinding.execute();
        }

        _qualityMetricsFormContainr.setVisible(true);
        AdfFacesContext.getCurrentInstance().addPartialTarget(_qualityMetricsFormContainr);
        
        //preserveCurrentRow();
        
        //printDebugData("rowSelectionListener");
    }
    
    public void notApplicableCheckboxClickHandler_Typical(ValueChangeEvent valueChangeEvent) {
      
        currentPopupCheckBoxId=TYPICAL_CHECKBOX_KEY;
        notApplicableCheckboxClickHandler(valueChangeEvent);
    }
    public void notApplicableCheckboxClickHandler_Suspension(ValueChangeEvent valueChangeEvent) {
      
        currentPopupCheckBoxId=SUSPENSION_CHECKBOX_KEY;
        notApplicableCheckboxClickHandler(valueChangeEvent);
    }
    public void notApplicableCheckboxClickHandler_Reject(ValueChangeEvent valueChangeEvent) {
      
        currentPopupCheckBoxId=REJECT_CHECKBOX_KEY;
        notApplicableCheckboxClickHandler(valueChangeEvent);
    }
    
    public void notApplicableCheckboxClickHandler(ValueChangeEvent valueChangeEvent) {
      
        UIComponent com = (UIComponent)valueChangeEvent.getSource();
        
        Boolean newVal = (Boolean)valueChangeEvent.getNewValue();

        if(newVal) {
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            _notApplicableConfirmationPopup.show(hints);
        }
        else {
            
            disableEnableAllInputBoxes(false);
        }
    }
    
    public void notApplicableConfirmationDialogListener(DialogEvent dialogEvent) {
        
        Object source = dialogEvent.getSource();
        
        if (dialogEvent.getOutcome() == DialogEvent.Outcome.yes)
                  {
                      boolean disabled=true;
                      disableEnableAllInputBoxes(disabled);
                  }
        else if (dialogEvent.getOutcome() == DialogEvent.Outcome.no) {
            if(currentPopupCheckBoxId.equals(TYPICAL_CHECKBOX_KEY)) {
                _notApplicableTypicalCheckbox.setValue(0);    
                AdfFacesContext.getCurrentInstance().addPartialTarget(_notApplicableTypicalCheckbox);
            }
            
            else if(currentPopupCheckBoxId.equals(SUSPENSION_CHECKBOX_KEY)) {
                _notApplicableSuspensionCheckbox.setValue(0);    
                AdfFacesContext.getCurrentInstance().addPartialTarget(_notApplicableSuspensionCheckbox);
            }
            
            else if(currentPopupCheckBoxId.equals(REJECT_CHECKBOX_KEY)) {
                _notApplicableRejectCheckbox.setValue(0);    
                AdfFacesContext.getCurrentInstance().addPartialTarget(_notApplicableRejectCheckbox);
            }
            
            
            
        }
    }

    private void disableEnableAllInputBoxes(boolean disabled) {
        
        //oracle.jbo.domain.Number zeroValue = new oracle.jbo.domain.Number(0);
        for(int i=1;i<=NUMBER_OF_MATRICS_INPUTBOXES;i++) 
        {
            RichInputText input =  (RichInputText)JSFUtils.findComponentInRoot(currentPopupCheckBoxId+"_"+i);
            if(disabled){
                input.setValue("");    
            }
            //input.setValue(zeroValue);
            
            input.setReadOnly(disabled);
            AdfFacesContext.getCurrentInstance().addPartialTarget(input);
        }
                
    }
    private void enableAllInputBoxes() {
        
        Object val = _notApplicableTypicalCheckbox.getValue();
        boolean selected = _notApplicableTypicalCheckbox.isSelected();
        
        enableAll(TYPICAL_CHECKBOX_KEY,_notApplicableTypicalCheckbox.isSelected());
        enableAll(SUSPENSION_CHECKBOX_KEY,_notApplicableSuspensionCheckbox.isSelected());
        enableAll(REJECT_CHECKBOX_KEY,_notApplicableRejectCheckbox.isSelected());
                
    }
    
  
    private void enableAll(String checkboxId,boolean enable) {
        
        for(int i=1;i<=NUMBER_OF_MATRICS_INPUTBOXES;i++) 
        {
            RichInputText input =  (RichInputText)JSFUtils.findComponentInRoot(checkboxId+"_"+i);
            input.setReadOnly(enable);
            //AdfFacesContext.getCurrentInstance().addPartialTarget(input);
        }
    }

    
    public CaptureOrderQualityMetricsMB() {
    }

    


    public void set_qualityMetricsFormContainr(RichPanelGroupLayout _qualityMetricsFormContainr) {
        this._qualityMetricsFormContainr = _qualityMetricsFormContainr;
    }

    public RichPanelGroupLayout get_qualityMetricsFormContainr() {
        return _qualityMetricsFormContainr;
    }


    public void set_resultsTable(RichTable _resultsTable) {
        this._resultsTable = _resultsTable;
    }

    public RichTable get_resultsTable() {
        return _resultsTable;
    }


    public void set_notApplicableConfirmationPopup(RichPopup _notApplicableConfirmationPopup) {
        this._notApplicableConfirmationPopup = _notApplicableConfirmationPopup;
    }

    public RichPopup get_notApplicableConfirmationPopup() {
        return _notApplicableConfirmationPopup;
    }


    public void set_notApplicableTypicalCheckbox(RichSelectBooleanCheckbox _notApplicableTypicalCheckbox) {
        this._notApplicableTypicalCheckbox = _notApplicableTypicalCheckbox;
    }

    public RichSelectBooleanCheckbox get_notApplicableTypicalCheckbox() {
        return _notApplicableTypicalCheckbox;
    }


    public void set_notApplicableSuspensionCheckbox(RichSelectBooleanCheckbox _notApplicableSuspensionCheckbox) {
        this._notApplicableSuspensionCheckbox = _notApplicableSuspensionCheckbox;
    }

    public RichSelectBooleanCheckbox get_notApplicableSuspensionCheckbox() {
        return _notApplicableSuspensionCheckbox;
    }

    public void set_notApplicableRejectCheckbox(RichSelectBooleanCheckbox _notApplicableRejectCheckbox) {
        this._notApplicableRejectCheckbox = _notApplicableRejectCheckbox;
    }

    public RichSelectBooleanCheckbox get_notApplicableRejectCheckbox() {
        return _notApplicableRejectCheckbox;
    }


    public String save() {
        
        _logger.log(Level.INFO, "Inside Save.");
        // actionListener="#{bindings.Commit.execute}"
        
        
        //Save the record first
        DCBindingContainer bindings=(DCBindingContainer)ADFUtil.getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Commit");
        operationBinding.execute();
        //printDebugData("save1");
        
        //rerunQuery();
        //makeCurrentRow();
        
        //printDebugData("save2");
        
        
        //Get current selected row
     /*   RowKeySet selectedRowKey=_resultsTable.getSelectedRowKeys();

        //Fire the search again 
        ADFUtil.invokeEL("#{bindings.CEIXSalesOrdersVOCriteriaQuery.processQuery}", new Class[] { QueryEvent.class },
                             new Object[] { queryEvent });
        
        


         //select the rows in refreshed table   
        _resultsTable.setSelectedRowKeys(selectedRowKey);*/
            
        return null;
    }

    /*private void printDebugData(String callerName) {
        
        System.out.println(callerName);
        Row selectedRow =
            (Row)ADFUtil.evaluateEL("#{bindings.CEIXSalesOrders1Iterator.currentRow}");
        Object orderNumber = selectedRow.getAttribute("OrderNumber");
        System.out.println(orderNumber.toString());
        RowKeySet selectedRowKey=_resultsTable.getSelectedRowKeys();
        System.out.println("CEIXSalesOrders1Iterator: orderNum:"+selectedRowKey);
        
        Row selectedRowCEIXOrdQualMetricsIterator =
            (Row)ADFUtil.evaluateEL("#{bindings.CEIXOrdQualMetricsIterator.currentRow}");
        if(selectedRowCEIXOrdQualMetricsIterator!=null) {
            Object orderNumber2 = selectedRowCEIXOrdQualMetricsIterator.getAttribute("OrderNumber");
            System.out.println("CEIXOrdQualMetricsIterator: orderNum: "+orderNumber2.toString());
        }
        else
        System.out.println("CEIXOrdQualMetricsIterator is null ");


    }*/

//    private void makeCurrentRow() {
//        
//        /*DCBindingContainer bindings=(DCBindingContainer)ADFUtil.getBindings();
//        DCIteratorBinding dcIter=bindings.findIteratorBinding("CEIXSalesOrders1Iterator");       
//        dcIter.setCurrentRowWithKey(currentRowKey);*/
//        
//        
//        CollectionModel _tableModle = (CollectionModel)_resultsTable.getValue();
//        JUCtrlHierBinding _tableBinding = (JUCtrlHierBinding)_tableModle.getWrappedData();
//         DCIteratorBinding _tableIteratorBinding = _tableBinding.getDCIteratorBinding();
//         _tableIteratorBinding.setCurrentRowWithKey(_rowKey.toStringFormat(true));
//           
//
//    }

//    private void preserveCurrentRow() {
//        
//        DCBindingContainer bindings=(DCBindingContainer)ADFUtil.getBindings();
//        DCIteratorBinding dcIter=bindings.findIteratorBinding("CEIXSalesOrders1Iterator");       
//
//        currentRowKey=dcIter.getCurrentRowKeyString();
//        System.out.println("currentRowKey: "+currentRowKey);
//        
//        Object _selectedRowData = _resultsTable.getSelectedRowData();
//        JUCtrlHierNodeBinding _nodeBinding = (JUCtrlHierNodeBinding)_selectedRowData;
//        _rowKey = _nodeBinding.getRowKey(); 
//        System.out.println("_rowKey:"+_rowKey);
//        
//    }

//    private void rerunQuery() {
//        
//        System.out.println("rerunQuery");
//       /* ADFUtil.invokeEL("#{bindings.CEIXSalesOrdersVOCriteriaQuery.processQuery}", new Class[] { QueryEvent.class },
//                             new Object[] { queryEvent });*/
//       DCBindingContainer bindings=(DCBindingContainer)ADFUtil.getBindings();
//       DCIteratorBinding dcIter=bindings.findIteratorBinding("CEIXSalesOrders1Iterator");       
//       dcIter.executeQuery();        
//        
//    }


  
}
