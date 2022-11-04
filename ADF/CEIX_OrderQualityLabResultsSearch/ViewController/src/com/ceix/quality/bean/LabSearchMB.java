package com.ceix.quality.bean;

import com.ceix.quality.model.services.CEIXOrderQualityLabResultsSearchAMImpl;
import com.ceix.quality.model.views.CEIXOrdQualLabResltVOImpl;
import com.ceix.util.ADFUtil;

import com.ceix.util.ADFUtils;

import java.sql.SQLException;

import java.util.Map;

import java.util.logging.Level;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCDataControl;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;

import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.output.RichOutputLabel;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupCanceledEvent;
import oracle.adf.view.rich.event.QueryEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;

import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewCriteria;
import oracle.jbo.server.ViewObjectImpl;

import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.event.SortEvent;

public class LabSearchMB {
    
    ADFLogger _logger = ADFLogger.createADFLogger(
                            LabSearchMB.class);
    
    private RichTable _resultsTable;
    private RichPanelGroupLayout _contractMetricsFormContainer;
    private RichOutputLabel _labelTmDifference;
    private String _tmDifference;
    private String _ashAsrDifference;
    private String _ashDryDifference;
    private String _volAsrDifference;
    private String _volDryDifference;
    private String _volDafDifference;
    private String _sulAsrDifference;
    private String _sulDryDifference;
    private String _sulDafDifference;
    private String _fcAsrDifference;

    private String _lbsSulDifference;
    private String _lbsSo2Difference;


    private String _fcDryDifference;
    private String _fcDafDifference;
    private String _btuAsrDifference;
    private String _btuDryDifference;
    private String _btuDafDifference;
    private String _AftiRedDifference;
    private String _AftsRedDifference;
    private String _AfthRedDifference;
    private String _AftfRedDifference;
    private String _ChlorineDifference;
    private String _MercuryDifference;

    private final String NOT_IN_RANGE_MSG = "Rejectable";
    private RichInputText _orderNumberInputField;
    private RichOutputText _orderNumberLabel;

    private RichPopup _contractMetricsNotFoundDialog;

    //temp variable to store order number selected in popup window
    private String selectedOrderNumber;
    private RichPopup _selectOrderNumberPopup;
    private RichPopup _addNewLabResultsPopup;
    private RichInputText _addNewLabResult_TrainNumber;
    private RichInputDate _addNewLabResult_DateSampled;
    private RichInputText _rptSampleNumber;


    public LabSearchMB() {
    }


    public void handleSearch(QueryEvent queryEvent) {
        _resultsTable.getSelectedRowKeys().clear();
        ADFUtil.invokeEL("#{bindings.CEIXOrdQualLabResltCustomVOCriteriaQuery.processQuery}",
                         new Class[] { QueryEvent.class },
                         new Object[] { queryEvent });
        _contractMetricsFormContainer.setVisible(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(_contractMetricsFormContainer);

    }

    public void rowSelectionListener(SelectionEvent selectionEvent) {
        // #{bindings.CEIXOrdQualLabReslt1.collectionModel.makeCurrent}
        ADFUtil.invokeEL("#{bindings.CEIXOrdQualLabReslt1.collectionModel.makeCurrent}",
                         new Class[] { SelectionEvent.class },
                         new Object[] { selectionEvent });

        Row selectedRow =
            (Row)ADFUtil.evaluateEL("#{bindings.CEIXOrdQualLabReslt1Iterator.currentRow}");

        Object orderNumber = selectedRow.getAttribute("OrderNumber");
        DCIteratorBinding iterator =
            ADFUtils.findIterator("CEIXOrdQualMetrics1Iterator");
        DCBindingContainer bindings = ADFUtils.getDCBindingContainer();

        _orderNumberLabel.setValue(orderNumber);
        _orderNumberInputField.setValue(orderNumber);

        if (orderNumber != null && !orderNumber.toString().trim().equals("")) {

            long rowCount = refreshContractQualityMetricsIterator(orderNumber);
            //lab number record already contains Order Number
            //load details of the order and lab result record


            //iterator.refresh(DCIteratorBinding.RANGESIZE_UNLIMITED);

            /*  BindingContainer container= BindingContext.getCurrent().getCurrentBindingsEntry();

            iterator.clearForRecreate();

            OperationBinding operationBinding= container.getOperationBinding("executeFindOrdQualMetricsViewByOrderNumber");
            operationBinding.getParamsMap().put("orderNumber", orderNumber);
            operationBinding.execute();


            iterator.refresh(DCIteratorBinding.RANGESIZE_UNLIMITED); */


            //currentRow=iterator.getCurrentRow();
            //ordNm = (String)currentRow.getAttribute("OrderNumber");

            //AdfFacesContext.getCurrentInstance().addPartialTarget(_contractQualityMetricsForm.getParent());
        } else {
            //ask user to associate an Order to lab results record.
            iterator = ADFUtils.findIterator("CEIXOrdQualMetrics1Iterator");
            OperationBinding operationBinding =
                bindings.getOperationBinding("CreateInsert");
            Object result = operationBinding.execute();
        }

        //refresh the UI
        _contractMetricsFormContainer.setVisible(true);
        AdfFacesContext.getCurrentInstance().addPartialTarget(_contractMetricsFormContainer);


    }

    private long refreshContractQualityMetricsIterator(Object orderNumber) {

        //getContractQualityMetricsByOrderNumber

        BindingContainer bindings = ADFUtils.getBindingContainer();

        OperationBinding ob =
            bindings.getOperationBinding("getContractQualityMetricsByOrderNumber");

        Map params = ob.getParamsMap();

        params.put("orderNum", orderNumber.toString());

        ob.execute();

        DCIteratorBinding iterator =
            ADFUtils.findIterator("CEIXOrdQualMetrics1Iterator");
        long rowCount = iterator.getEstimatedRowCount();
        return rowCount;
        /*
        Row currentRow;
        currentRow=iterator.getCurrentRow();
        String ordNm = (String)currentRow.getAttribute("OrderNumber");*/
        /*DCIteratorBinding iterator = ADFUtils.findIterator("CEIXOrdQualMetrics1Iterator");
        ViewObjectImpl vo =(ViewObjectImpl)iterator.getViewObject();
        vo.setApplyViewCriteriaName("FindByOrderNumberVOCriteriaQuery");
        vo.setNamedWhereClauseParam("orNum", orderNumber);
        vo.executeQuery();*/

        /*ViewCriteria vc =vo.getViewCriteria("FindByOrderNumberVOCriteriaQuery");
        vo.applyViewCriteria(vc);
        vo.setNamedWhereClauseParam("orNum",orderNumber);
        vo.executeQuery();*/

        //iterator = ADFUtils.findIterator("CEIXOrdQualMetrics1Iterator");
        // long rowCount = iterator.getEstimatedRowCount();
        //return rowCount;
        //return 0;
    }

    public void searchDialogListener(DialogEvent dialogEvent) {

        if (dialogEvent.getOutcome() == DialogEvent.Outcome.ok) {
            Row selectedRow =
                (Row)ADFUtil.evaluateEL("#{bindings.CEIXSalesOrders1Iterator.currentRow}");
            if (selectedRow != null) {
                Object orderNumber = selectedRow.getAttribute("OrderNumber");
                selectedOrderNumber = orderNumber.toString();

                //check if there is contract metrics (from capture screen) associated for this order
                long rowCount =
                    refreshContractQualityMetricsIterator(orderNumber);

                //if no rows found for quality metrics show error msg
                if (rowCount == 0)
                    showHideNoContractQualityMetricsFoundError();
                else {
                    //if quality metrics already associated, go ahead and add it
                    //with given lab metrics record
                    setOrderNumberInParentForm();

                    //refresh the form
                    _contractMetricsFormContainer.setVisible(true);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(_contractMetricsFormContainer);

                }

            }


        }
    }

    private void setOrderNumberInParentForm() {

        _orderNumberInputField.setValue(selectedOrderNumber);
        _orderNumberLabel.setValue(selectedOrderNumber);
        AdfFacesContext.getCurrentInstance().addPartialTarget(_orderNumberInputField);
        AdfFacesContext.getCurrentInstance().addPartialTarget(_orderNumberLabel);
        selectedOrderNumber = null;

    }

    private void showHideNoContractQualityMetricsFoundError() {

        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        _contractMetricsNotFoundDialog.show(hints);

    }


    public void initializeAddNewLabResult() {
        BindingContext bc = BindingContext.getCurrent();
        DCBindingContainer dcb =
            (DCBindingContainer)bc.getCurrentBindingsEntry();
        OperationBinding op = dcb.getOperationBinding("CreateInsert");
        Row row = (Row)op.execute();
    }

    //    public String showAddNewLlabResultsPopup() {
    //
    //
    //       //bindings.creategetCEIXOrdQualLabReslt1Row.execute
    //
    //        System.out.println(">>showAddNewLlabResultsPopup");
    //       /* DCBindingContainer bindings=ADFUtils.getDCBindingContainer();
    //        DCIteratorBinding iterator = ADFUtils.findIterator("CEIXOrdQualLabReslt1Iterator");
    //        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert1");
    //
    //        Object result = operationBinding.execute();
    //
    //   */
    //       BindingContext bc = BindingContext.getCurrent();
    //       DCBindingContainer dcb =
    //       (DCBindingContainer)bc.getCurrentBindingsEntry();
    //       OperationBinding op = dcb.getOperationBinding
    //       ("creategetCEIXOrdQualLabReslt1Row");
    //       Row row = (Row)op.execute();
    //       //row.setAttribute("Train", "4343");
    //       if (op.getErrors().size() == 0) {
    //       //On success, keep the new row as active
    //       /*ArrayList lst = new ArrayList(1);
    //       lst.add(row.getKey());
    //       getDataTable().setActiveRowKey(lst);*/
    //       }
    //        RichPopup.PopupHints hints = new RichPopup.PopupHints();
    //        _addNewLabResultsPopup.show(hints);
    //
    //        return null;
    //    }

    public String saveNewLabResultsMetrics() {

        //        if(rowAlreadyExists()) {
        //            System.out.println("record already exists.");
        //              return "";
        //        }
        //        else
        //            System.out.println("Row doesnt exist already.");


        BindingContext bc = BindingContext.getCurrent();
        DCBindingContainer dcb =
            (DCBindingContainer)bc.getCurrentBindingsEntry();
        OperationBinding op = dcb.getOperationBinding("Commit");
        op.execute();
        //empty the vo


        return "labSearchPage";
    }

    private boolean rowAlreadyExists() {

        BindingContainer bindings = ADFUtils.getBindingContainer();

        OperationBinding ob =
            bindings.getOperationBinding("orderQualityLabResultsExists");

        Map params = ob.getParamsMap();

        params.put("trainNumber", _addNewLabResult_TrainNumber.getValue());
        params.put("sampleDate", _addNewLabResult_DateSampled.getValue());
        params.put("rptSampleNo", _rptSampleNumber.getValue());
        //System.out.println(ob);

        Boolean result = (Boolean)ob.execute();
        return result;

    }

    public String cancelClickedOnAddNewLabResults() {


        performRollback();

        return "labSearchPage";
    }

    public void performRollback() {
        BindingContext bc = BindingContext.getCurrent();
        DCBindingContainer dcb =
            (DCBindingContainer)bc.getCurrentBindingsEntry();
        OperationBinding op = dcb.getOperationBinding("Rollback");
        op.execute();
    }
    /*   public void saveNewLabResultsPopupCancelledListener(PopupCanceledEvent popupCanceledEvent) {

        //cancelCurrentRowCEIXOrdQualLabReslt1Row

        BindingContext bc = BindingContext.getCurrent();
        DCBindingContainer dcb =
        (DCBindingContainer)bc.getCurrentBindingsEntry();
        OperationBinding op = dcb.getOperationBinding
        ("cancelCurrentRowCEIXOrdQualLabReslt1Row");
        op.execute();
    } */

    public void contractMetricsNotFoundDialogListener(DialogEvent dialogEvent) {

        Object source = dialogEvent.getSource();

        if (dialogEvent.getOutcome() == DialogEvent.Outcome.yes) {
            boolean disabled = true;
            AdfFacesContext.getCurrentInstance().addPartialTarget(_contractMetricsFormContainer);
            setOrderNumberInParentForm();

            //System.out.println("yes");
        } else if (dialogEvent.getOutcome() == DialogEvent.Outcome.no) {
            selectedOrderNumber = null;
            //let the user select some other order number
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            _selectOrderNumberPopup.show(hints);
        }

    }

    public void dialogDeleteListner(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome() == DialogEvent.Outcome.ok) {

            _logger.log(Level.FINER, ">>dialogDeleteListner");
//            BindingContainer bindings = getBindings();
//            OperationBinding operationBinding =
//                bindings.getOperationBinding("Delete");
//            Object result = operationBinding.execute();
//            
            
            
            DCBindingContainer bindings=ADFUtils.getDCBindingContainer();
            DCIteratorBinding iterator = ADFUtils.findIterator("CEIXOrdQualLabReslt1Iterator");
            //OperationBinding operationBinding = bindings.getOperationBinding("Delete");
    
            //Object result = operationBinding.execute();
            
            
            CEIXOrderQualityLabResultsSearchAMImpl appM = getAM();
            CEIXOrdQualLabResltVOImpl ehs = (CEIXOrdQualLabResltVOImpl)appM.getCEIXOrdQualLabReslt1();
            Row ehs_currentRow = ehs.getCurrentRow();
            ehs_currentRow.remove();
            appM.getDBTransaction().commit();
            
            
            
            FacesMessage msg =
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Record Deleted Successfully.",
                                 "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

        AdfFacesContext.getCurrentInstance().addPartialTarget(_resultsTable);

    }
    
    private CEIXOrderQualityLabResultsSearchAMImpl getAM() {
        FacesContext context = FacesContext.getCurrentInstance();
        BindingContext bindingContext = BindingContext.getCurrent();
        DCDataControl dc =
            bindingContext.findDataControl("CEIXOrderQualityLabResultsSearchAMDataControl");
        CEIXOrderQualityLabResultsSearchAMImpl appM =
            (CEIXOrderQualityLabResultsSearchAMImpl)dc.getDataProvider();
        return appM;
    }


    public void set_resultsTable(RichTable _resultsTable) {
        this._resultsTable = _resultsTable;
    }

    public RichTable get_resultsTable() {
        return _resultsTable;
    }

    public void set_contractMetricsFormContainer(RichPanelGroupLayout _contractMetricsFormContainer) {
        this._contractMetricsFormContainer = _contractMetricsFormContainer;
    }

    public RichPanelGroupLayout get_contractMetricsFormContainer() {
        return _contractMetricsFormContainer;
    }

    public void set_labelTmDifference(RichOutputLabel _labelTmDifference) {
        this._labelTmDifference = _labelTmDifference;
    }

    public RichOutputLabel get_labelTmDifference() {
        return _labelTmDifference;
    }


    public String calculateDifference(String tagName) {


        //reject value
        String rejectValueExp = "#{bindings." + tagName + "Reject.inputValue}";
        Object rejectVal = ADFUtil.evaluateEL(rejectValueExp);

        //lab value
        String actualValueExp = "#{bindings." + tagName + ".inputValue}";
        Object actualVal = ADFUtil.evaluateEL(actualValueExp);

        //warning code
        String warningExp = "#{bindings." + tagName + "WarngCode.inputValue}";
        Object warningExpVal = ADFUtil.evaluateEL(warningExp);

        String out = "";

        if (rejectVal != null && !rejectVal.toString().trim().equals("") &&
            !rejectVal.toString().trim().equals("0") &&
            warningExpVal != null &&
            !warningExpVal.toString().trim().equals("") && actualVal != null &&
            !actualVal.toString().trim().equals("")) {

            oracle.jbo.domain.Number rejectValue =
                (oracle.jbo.domain.Number)rejectVal;
            oracle.jbo.domain.Number actualValue =
                (oracle.jbo.domain.Number)actualVal;
            String warningExpValue = (String)warningExpVal;

            int compareResult = actualValue.compareTo(rejectValue);

            //different if/else cases just to make things clear
            //=
            if (warningExpValue.equals("=") && compareResult == 0) {
                out = NOT_IN_RANGE_MSG;
            }
            //>
            else if (warningExpValue.equals(">") && compareResult > 0) {
                out = NOT_IN_RANGE_MSG;
            }
            //<
            else if (warningExpValue.equals("<") && compareResult < 0) {
                out = NOT_IN_RANGE_MSG;
            }
            //>=
            else if (warningExpValue.equals(">=") &&
                     (compareResult > 0 || compareResult == 0)) {
                out = NOT_IN_RANGE_MSG;
            }

            //<=
            else if (warningExpValue.equals("<=") &&
                     (compareResult < 0 || compareResult == 0)) {
                out = NOT_IN_RANGE_MSG;
            }

        }
        return out;
    }

    public void set_tmDifference(String _tmDifference) {
        this._tmDifference = _tmDifference;
    }

    public String get_tmDifference() {

        //!IMPORTRANT: CUSTOM CODE HERE
        return calculateDifference("Tm");

    }

    public String get_tmDifference1() {
        return _tmDifference;
    }

    public void set_ashAsrDifference(String _ashAsrDifference) {
        this._ashAsrDifference = _ashAsrDifference;
    }

    public String get_ashAsrDifference() {
        //!IMPORTRANT: CUSTOM CODE HERE
        return calculateDifference("AshAsr");

    }

    public void set_ashDryDifference(String _ashDryDifference) {
        this._ashDryDifference = _ashDryDifference;
    }

    public String get_ashDryDifference() {
        //!IMPORTRANT: CUSTOM CODE HERE
        return calculateDifference("AshDry");
    }

    public void set_volAsrDifference(String _volAsrDifference) {
        this._volAsrDifference = _volAsrDifference;
    }

    public String get_volAsrDifference() {
        //!IMPORTRANT: CUSTOM CODE HERE
        return calculateDifference("VolAsr");
    }

    public void set_volDryDifference(String _volDryDifference) {
        this._volDryDifference = _volDryDifference;
    }

    public String get_volDryDifference() {
        //!IMPORTRANT: CUSTOM CODE HERE
        return calculateDifference("VolDry");
    }

    public void set_volDafDifference(String _volDafDifference) {
        this._volDafDifference = _volDafDifference;
    }

    public String get_volDafDifference() {
        //!IMPORTRANT: CUSTOM CODE HERE
        return calculateDifference("VolDaf");
    }

    public void set_sulAsrDifference(String _sulAsrDifference) {
        this._sulAsrDifference = _sulAsrDifference;
    }

    public String get_sulAsrDifference() {
        //!IMPORTRANT: CUSTOM CODE HERE
        return calculateDifference("SulAsr");
    }

    public void set_sulDryDifference(String _sulDryDifference) {
        this._sulDryDifference = _sulDryDifference;
    }

    public String get_sulDryDifference() {
        //!IMPORTRANT: CUSTOM CODE HERE
        return calculateDifference("SulDry");
    }

    public void set_sulDafDifference(String _sulDafDifference) {
        this._sulDafDifference = _sulDafDifference;
    }

    public String get_sulDafDifference() {
        //!IMPORTRANT: CUSTOM CODE HERE
        return calculateDifference("SulDaf");
    }

    public void set_fcAsrDifference(String _fcAsrDifference) {
        this._fcAsrDifference = _fcAsrDifference;
    }

    public String get_fcAsrDifference() {
        //!IMPORTRANT: CUSTOM CODE HERE
        return calculateDifference("FcAsr");
    }

    public void set_lbsSulDifference(String _lbsSulDifference) {
        this._lbsSulDifference = _lbsSulDifference;
    }

    public String get_lbsSulDifference() {
        //!IMPORTRANT: CUSTOM CODE HERE
        return calculateDifference("Lbss");
    }

    public void set_lbsSo2Difference(String _lbsSo2Difference) {
        this._lbsSo2Difference = _lbsSo2Difference;
    }

    public String get_lbsSo2Difference() {
        //!IMPORTRANT: CUSTOM CODE HERE
        return calculateDifference("Lbsso2");
    }

    public void set_fcDryDifference(String _fcDryDifference) {
        this._fcDryDifference = _fcDryDifference;
    }

    public String get_fcDryDifference() {
        //!IMPORTRANT: CUSTOM CODE HERE
        return calculateDifference("FcDry");
    }

    public void set_fcDafDifference(String _fcDafDifference) {
        this._fcDafDifference = _fcDafDifference;
    }

    public String get_fcDafDifference() {
        //!IMPORTRANT: CUSTOM CODE HERE
        return calculateDifference("FcDaf");
    }

    public void set_btuAsrDifference(String _btuAsrDifference) {
        this._btuAsrDifference = _btuAsrDifference;
    }

    public String get_btuAsrDifference() {
        //!IMPORTRANT: CUSTOM CODE HERE
        return calculateDifference("BtuAsr");
    }

    public void set_btuDryDifference(String _btuDryDifference) {
        this._btuDryDifference = _btuDryDifference;
    }

    public String get_btuDryDifference() {
        //!IMPORTRANT: CUSTOM CODE HERE
        return calculateDifference("BtuDry");
    }

    public void set_btuDafDifference(String _btuDafDifference) {
        this._btuDafDifference = _btuDafDifference;
    }

    public String get_btuDafDifference() {
        //!IMPORTRANT: CUSTOM CODE HERE
        return calculateDifference("BtuDaf");
    }

    public void set__AftiRedDifference(String _AftiRedDifference) {
        this._AftiRedDifference = _AftiRedDifference;
    }

    public String get_AftiRedDifference() {
        //!IMPORTRANT: CUSTOM CODE HERE
        return calculateDifference("AftiRed");

    }

    public void set_AftsRedDifference(String _AftsRedDifference) {
        this._AftsRedDifference = _AftsRedDifference;
    }

    public String get_AftsRedDifference() {
        //!IMPORTRANT: CUSTOM CODE HERE
        return calculateDifference("AftsRed");

    }

    public void set_AfthRedDifference(String _AfthRedDifference) {
        this._AfthRedDifference = _AfthRedDifference;
    }

    public String get_AfthRedDifference() {
        //!IMPORTRANT: CUSTOM CODE HERE
        return calculateDifference("AfthRed");

    }

    public void set_AftfRedDifference(String _AftfRedDifference) {
        this._AftfRedDifference = _AftfRedDifference;
    }

    public String get_AftfRedDifference() {
        //!IMPORTRANT: CUSTOM CODE HERE
        return calculateDifference("AftfRed");
    }

    public void set_ChlorineDifference(String _ChlorineDifference) {
        this._ChlorineDifference = _ChlorineDifference;
    }

    public String get_ChlorineDifference() {
        //!IMPORTRANT: CUSTOM CODE HERE
        return calculateDifference("Chlorine");
    }

    public void set_MercuryDifference(String _MercuryDifference) {
        this._MercuryDifference = _MercuryDifference;
    }

    public String get_MercuryDifference() {
        //!IMPORTRANT: CUSTOM CODE HERE
        return calculateDifference("Mercury");
    }


    public void set_orderNumberInputField(RichInputText _orderNumberInputField) {
        this._orderNumberInputField = _orderNumberInputField;
    }

    public RichInputText get_orderNumberInputField() {
        return _orderNumberInputField;
    }

    public void set_orderNumberLabel(RichOutputText _orderNumberLabel) {
        this._orderNumberLabel = _orderNumberLabel;
    }

    public RichOutputText get_orderNumberLabel() {
        return _orderNumberLabel;
    }


    public void set_contractMetricsNotFoundDialog(RichPopup _contractMetricsNotFoundDialog) {
        this._contractMetricsNotFoundDialog = _contractMetricsNotFoundDialog;
    }

    public RichPopup get_contractMetricsNotFoundDialog() {
        return _contractMetricsNotFoundDialog;
    }


    public void set_selectOrderNumberPopup(RichPopup _selectOrderNumberPopup) {
        this._selectOrderNumberPopup = _selectOrderNumberPopup;
    }

    public RichPopup get_selectOrderNumberPopup() {
        return _selectOrderNumberPopup;
    }


    public void manualSavelLabResults(DialogEvent dialogEvent) {
        // Add event code here...
    }


    public void set_addNewLabResultsPopup(RichPopup _addNewLabResultsPopup) {
        this._addNewLabResultsPopup = _addNewLabResultsPopup;
    }

    public RichPopup get_addNewLabResultsPopup() {
        return _addNewLabResultsPopup;
    }


    public void set_addNewLabResult_TrainNumber(RichInputText _addNewLabResult_TrainNumber) {
        this._addNewLabResult_TrainNumber = _addNewLabResult_TrainNumber;
    }

    public RichInputText get_addNewLabResult_TrainNumber() {
        return _addNewLabResult_TrainNumber;
    }

    public void set_addNewLabResult_DateSampled(RichInputDate _addNewLabResult_DateSampled) {
        this._addNewLabResult_DateSampled = _addNewLabResult_DateSampled;
    }

    public RichInputDate get_addNewLabResult_DateSampled() {
        return _addNewLabResult_DateSampled;
    }

    public void set_rptSampleNumber(RichInputText _rptSampleNumber) {
        this._rptSampleNumber = _rptSampleNumber;
    }

    public RichInputText get_rptSampleNumber() {
        return _rptSampleNumber;
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }


}
