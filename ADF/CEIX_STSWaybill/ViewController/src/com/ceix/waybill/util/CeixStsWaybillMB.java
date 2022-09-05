package com.ceix.waybill.util;

import com.ceix.waybill.model.services.CeixStsWaybillAMImpl;

import com.ceix.waybill.model.services.webserviceclient.OrderDetailsResponseBean;

import com.ceix.waybill.model.services.webserviceclient.OrderLineBean;

import com.ceix.waybill.model.services.webserviceclient.PoReceiptBean;

import com.ceix.waybill.model.views.CeixStsWaybillLinesVOImpl;
import com.ceix.waybill.model.views.CeixStsWaybillLinesVORowImpl;

import com.ceix.waybill.model.views.CeixStsWaybillSearchVOImpl;

import com.oracle.xmlns.adf.svc.types.AmountType;
import com.oracle.xmlns.adf.svc.types.MeasureType;
import com.oracle.xmlns.apps.scm.doo.processorder.flex.headercategories.JHeaderEffDooHeadersAddInfoprivate;
import com.oracle.xmlns.apps.scm.doo.processorder.flex.headercontextsb.AcceptableWeights;
import com.oracle.xmlns.apps.scm.doo.processorder.flex.headercontextsb.ContractInformation;
import com.oracle.xmlns.apps.scm.doo.processorder.flex.headercontextsb.CustomerDestination;
import com.oracle.xmlns.apps.scm.doo.processorder.flex.headercontextsb.CustomerPoNumber;
import com.oracle.xmlns.apps.scm.doo.processorder.flex.headercontextsb.InternalOrders;
import com.oracle.xmlns.apps.scm.doo.processorder.flex.headercontextsb.MineLocation;
import com.oracle.xmlns.apps.scm.doo.processorder.flex.headercontextsb.Quality;
import com.oracle.xmlns.apps.scm.doo.processorder.flex.headercontextsb.ShippingInformation;
import com.oracle.xmlns.apps.scm.doo.processorder.model.HeaderEffCategories;
import com.oracle.xmlns.apps.scm.fom.importorders.orderimportservice.Charge;
import com.oracle.xmlns.apps.scm.fom.importorders.orderimportservice.ChargeComponent;
import com.oracle.xmlns.apps.scm.fom.importorders.orderimportservice.Order;
import com.oracle.xmlns.apps.scm.fom.importorders.orderimportservice.OrderImportRequest;
import com.oracle.xmlns.apps.scm.fom.importorders.orderimportservice.OrderImportResponse;
import com.oracle.xmlns.apps.scm.fom.importorders.orderimportservice.OrderLine;
import com.oracle.xmlns.apps.scm.fom.importorders.orderimportservice.OrderProcessingPreferences;
import com.oracle.xmlns.apps.scm.inventory.materialtransactions.pendingtransactions.stagedinventorytransactionservicev2.StagedInventoryTransaction;
import com.oracle.xmlns.apps.scm.inventory.materialtransactions.pendingtransactions.stagedinventorytransactionservicev2.ObjectFactory;
import com.oracle.xmlns.apps.scm.receiving.receiptsinterface.transactions.processorservicev2.StagedReceivingHeader;

import com.oracle.xmlns.apps.scm.receiving.receiptsinterface.transactions.processorservicev2.StagedReceivingTransaction;

import com.oracle.xmlns.apps.scm.shipping.shipconfirm.deliveries.shipmentlineservice.Message;
import com.oracle.xmlns.apps.scm.shipping.shipconfirm.deliveries.shipmentlineservice.ShipmentLineInformation;

import com.oracle.xmlns.apps.scm.shipping.shipconfirm.deliveries.shipmentlineservice.UpdateShipmentLinesResponse;

import com.portal.framework.utilities.Model;

import java.io.PrintWriter;
import java.io.StringWriter;

import java.math.BigDecimal;

import java.math.RoundingMode;

import java.sql.CallableStatement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import java.util.TimeZone;
import java.util.logging.Level;

import javax.el.ELContext;
import javax.el.ExpressionFactory;

import javax.el.MethodExpression;

import javax.el.ValueExpression;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import javax.xml.bind.JAXBElement;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCDataControl;

import oracle.adf.share.ADFContext;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.nav.RichCommandButton;
import oracle.adf.view.rich.component.rich.output.RichPanelCollection;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.adf.view.rich.event.DialogEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.Transaction;
import oracle.jbo.ViewObject;

import oracle.jbo.uicli.binding.JUCtrlHierNodeBinding;

import org.apache.myfaces.trinidad.event.SelectionEvent;

public class CeixStsWaybillMB {
    private RichPanelCollection detailPC;
    private RichSelectBooleanCheckbox selAllCheckBox;
    private RichCommandButton processBT;
    //private RichCommandButton onCalculateFreightBT;
    static final ADFLogger mylog = ADFLogger.createADFLogger(CeixStsWaybillMB.class);
    private RichInputText headerSalesOrder;
    private RichInputText headerNewTrainNumber;
    private RichInputText headerComments;
    private RichCommandButton createSalesOrderVal;
    private RichTable headerTable;
    private RichTable detailTable;
    Model model = new Model();
    private RichCommandButton stragglerBT;
    private RichCommandButton removeStragglerBT;

    public CeixStsWaybillMB() {
    }

    public void onSearch(ActionEvent actionEvent) {
        onSearchCall();
    }

    public void onSearchCall() {
        BindingContext bindingctx = BindingContext.getCurrent();
        DCDataControl dc = bindingctx.dataControlFrame().findDataControl("CeixStsWaybillAMDataControl");
        BindingContainer bindings = bindingctx.getCurrentBindingsEntry();
        OperationBinding operationBinding = (OperationBinding) bindings.getOperationBinding("ExecuteWithParams");
        Object result = operationBinding.execute();
        CeixStsWaybillAMImpl appM = (CeixStsWaybillAMImpl) dc.getDataProvider();
        ViewObject vo = appM.getCeixStsWaybillSearchVO1();
        long rowCount;
        if (vo.getCurrentRow() != null) {
            //System.out.println("Current search row:"+vo.getCurrentRow().getAttribute("TrainNumber"));
            vo.getCurrentRow().setAttribute("EditFlag", "Y");
            //System.out.println("The flag is:"+vo.getCurrentRow().getAttribute("EditFlag"));
            ViewObject vol = appM.getCeixStsWaybillLinesVO1();
            vol.reset();
            vol.clearCache();
            vol.setWhereClause(null);
            String wc = "";
            if (vo.getCurrentRow().getAttribute("TrainNumber") != null) {
                String TrainNum = vo.getCurrentRow()
                                    .getAttribute("TrainNumber")
                                    .toString();
                wc = "train_number = '" + TrainNum + "'";
                //System.out.println("Lines Train:"+TrainNum);
            } else {
                wc = "train_number is null ";
            }
            if (vo.getCurrentRow().getAttribute("OrderNumber") != null) {
                String SalesNum = vo.getCurrentRow()
                                    .getAttribute("OrderNumber")
                                    .toString();
                wc = wc + " and sales_order_number = '" + SalesNum + "'";
                //System.out.println("Lines sales:"+SalesNum);
                this.getCreateSalesOrderVal().setDisabled(true);
            } else {
                this.getCreateSalesOrderVal().setDisabled(false);
                wc = wc + " and sales_order_number is null ";
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(getCreateSalesOrderVal());
            if (vo.getCurrentRow().getAttribute("ShipmentNumber") != null) {
                String ShipNum = vo.getCurrentRow()
                                   .getAttribute("ShipmentNumber")
                                   .toString();
                wc = wc + " and shipment_number = '" + ShipNum + "'";
                //System.out.println("Lines ship:"+ShipNum);
            } else {
                wc = wc + " and shipment_number is null ";
            }
            if (vo.getCurrentRow().getAttribute("DumpDate") != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date();
                try {
                    date = sdf.parse(vo.getCurrentRow()
                                       .getAttribute("DumpDate")
                                       .toString());
                } catch (ParseException e) {
                    //System.out.println("Error: Invalid Date Parser Exception.");
                    mylog.info("Error: Invalid Date Parser Exception.");
                    e.printStackTrace();
                } catch (Exception e) {
                    //System.out.println("Error: Exception.");
                    mylog.info("Error: Exception.");
                    e.printStackTrace();
                }
                wc = wc + " and dump_date = TO_DATE('" + sdf.format(date) + "','yyyy-MM-dd')";
            } else {
                wc = wc + " and dump_date is null ";
            }

            if (vo.getCurrentRow().getAttribute("SoNumber") != null) {
                String OrderNum = vo.getCurrentRow()
                                    .getAttribute("SoNumber")
                                    .toString();
                wc = wc + " and Attribute1 = '" + OrderNum + "'";

            } else {
                wc = wc + " and Attribute1 is null ";
            }
            if (vo.getCurrentRow().getAttribute("Straggler") != null) {
                String straggler = vo.getCurrentRow()
                                     .getAttribute("Straggler")
                                     .toString();
                wc = wc + " and nvl(straggler,'N') = '" + straggler + "'"; // This is from ceix_sts_delivery_lines table

            } else {
                wc = wc + " and nvl(straggler,'N') ='N' ";
            }

            vol.setWhereClause(wc);
            vol.executeQuery();
            rowCount = vol.getEstimatedRowCount();
            AdfFacesContext.getCurrentInstance().addPartialTarget(getDetailPC());
            reset_BT_CB();
        } else {
            ViewObject vol = appM.getCeixStsWaybillLinesVO1();
            vol.reset();
            vol.clearCache();
            vol.setWhereClause("1=2");
            vol.executeQuery();
            rowCount = vol.getEstimatedRowCount();
            AdfFacesContext.getCurrentInstance().addPartialTarget(getDetailPC());
            reset_BT_CB();
        }

        //disable/enable buttons
        boolean disabled = rowCount == 0 ? true : false;
        //enable search assign sales order button
        disableAssignSalesOrdBtn(disabled);
    }

    public void disableAssignSalesOrdBtn(boolean disabled) {

        if (ADFContext.getCurrent()
                      .getSecurityContext()
                      .isUserInRole("CEIX_WaybillUsersRO"))
            disabled = true;

        createSalesOrderVal.setDisabled(disabled);
        AdfFacesContext.getCurrentInstance().addPartialTarget(createSalesOrderVal);
    }

    public void setDetailPC(RichPanelCollection detailPC) {
        this.detailPC = detailPC;
    }

    public RichPanelCollection getDetailPC() {
        return detailPC;
    }

    public void reset_BT_CB() {
        this.selAllCheckBox.setSelected(false);
        //this.assignSalesOrder.setDisabled(true);
        this.processBT.setDisabled(true);
        this.stragglerBT.setDisabled(true);
        this.removeStragglerBT.setDisabled(true);
        AdfFacesContext.getCurrentInstance().addPartialTarget(getDetailPC());
        //this.kleinshcmidtBT.setDisabled(true);
    }

    public void select_BT_CB() {
        //this.assignSalesOrder.setDisabled(false);
        if (ADFContext.getCurrent()
                      .getSecurityContext()
                      .isAuthorizationEnabled()) {
            //System.out.println("Security is enabledKK");
            if (ADFContext.getCurrent()
                          .getSecurityContext()
                          .isAuthenticated()) {
                //System.out.println("Security is Authenticated");
                String user = ADFContext.getCurrent()
                                        .getSecurityContext()
                                        .getUserName();
                //System.out.println("User is :"+user);
                if (ADFContext.getCurrent()
                              .getSecurityContext()
                              .isUserInRole("CEIX_WaybillUsersRO")) {
                    this.processBT.setDisabled(true);
                    this.stragglerBT.setDisabled(true);
                    this.removeStragglerBT.setDisabled(true);
                    //System.out.println("User Exists");
                } else {
                    this.processBT.setDisabled(false);
                    this.stragglerBT.setDisabled(false);
                    this.removeStragglerBT.setDisabled(false);
                    //System.out.println("User doesnot Exists");
                }
            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(getDetailPC());
        //this.kleinshcmidtBT.setDisabled(true);
    }

    public static Object invokeEL(String el, Class[] paramTypes, Object[] params) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELContext elContext = facesContext.getELContext();
        ExpressionFactory expressionFactory = facesContext.getApplication().getExpressionFactory();
        MethodExpression exp = expressionFactory.createMethodExpression(elContext, el, Object.class, paramTypes);

        return exp.invoke(elContext, params);
    }

    public static Object evaluateEL(String el) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELContext elContext = facesContext.getELContext();
        ExpressionFactory expressionFactory = facesContext.getApplication().getExpressionFactory();
        ValueExpression exp = expressionFactory.createValueExpression(elContext, el, Object.class);

        return exp.getValue(elContext);
    }

    public void onTableSelList(SelectionEvent selectionEvent) {

        //enable assign sales order button
        disableAssignSalesOrdBtn(false);

        //System.out.println("row is selected:"+selectionEvent.getSource());
        RichTable tb = (RichTable) selectionEvent.getSource();
        //System.out.println("Table is:"+tb.getSelectedRowData());
        Object rd = tb.getSelectedRowData();
        JUCtrlHierNodeBinding node = (JUCtrlHierNodeBinding) rd;
        Key rk = node.getRowKey();
        //System.out.println("Key is:"+rk);
        BindingContext bindingctx = BindingContext.getCurrent();
        DCDataControl dc = bindingctx.dataControlFrame().findDataControl("CeixStsWaybillAMDataControl");
        CeixStsWaybillAMImpl appM = (CeixStsWaybillAMImpl) dc.getDataProvider();
        ViewObject vo = appM.getCeixStsWaybillSearchVO1();
        invokeEL("#{bindings.CeixStsWaybillSearchVO1.collectionModel.makeCurrent}",
                 new Class[] { SelectionEvent.class }, new Object[] { selectionEvent });
        Row selectedRow = (Row) evaluateEL("#{bindings.CeixStsWaybillSearchVO1Iterator.currentRow}");
        //System.out.println("The name is :"+selectedRow.getAttribute("TrainNumber"));
        vo.setCurrentRow(selectedRow);
        //System.out.println("Second Current row:"+vo.getCurrentRow().getAttribute("TrainNumber"));
        vo.getCurrentRow().setAttribute("EditFlag", "Y");
        //System.out.println("The flagselect is:"+vo.getCurrentRow().getAttribute("EditFlag"));
        ViewObject vol = appM.getCeixStsWaybillLinesVO1();
        vol.reset();
        vol.clearCache();
        vol.setWhereClause(null);
        //String st = "N";
        String wc = "";
        if (selectedRow.getAttribute("TrainNumber") != null) {
            String TrainNum = selectedRow.getAttribute("TrainNumber").toString();
            wc = "train_number = '" + TrainNum + "'";
            //System.out.println("Lines Train:"+TrainNum);
        } else {
            wc = "train_number is null ";
        }
        if (selectedRow.getAttribute("OrderNumber") != null) {
            String SalesNum = selectedRow.getAttribute("OrderNumber").toString();

            wc = wc + " and sales_order_number = '" + SalesNum + "'";
            //System.out.println("Lines sales:"+SalesNum);
        } else {
            wc = wc + " and sales_order_number is null ";
        }
        if (selectedRow.getAttribute("ShipmentNumber") != null) {
            String shipnum = selectedRow.getAttribute("ShipmentNumber").toString();
            wc = wc + " and shipment_number = '" + shipnum + "'";
            //System.out.println("Lines shipment:"+shipnum);
        } else {
            wc = wc + " and shipment_number is null ";
        }
        if (vo.getCurrentRow().getAttribute("DumpDate") != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            try {
                date = sdf.parse(vo.getCurrentRow()
                                   .getAttribute("DumpDate")
                                   .toString());
            } catch (ParseException e) {
                //System.out.println("Error: Invalid Date Parser Exception.");
                mylog.info("Error: Invalid Date Parser Exception.");
                e.printStackTrace();
            } catch (Exception e) {
                //System.out.println("Error: Exception.");
                mylog.info("Error: Exception.");
                e.printStackTrace();
            }
            wc = wc + " and dump_date = TO_DATE('" + sdf.format(date) + "','yyyy-MM-dd')";
        } else {
            wc = wc + " and dump_date is null ";
        }
        if (vo.getCurrentRow().getAttribute("SoNumber") != null) {
            String OrderNum = vo.getCurrentRow()
                                .getAttribute("SoNumber")
                                .toString();
            wc = wc + " and Attribute1 = '" + OrderNum + "'";

        } else {
            wc = wc + " and Attribute1 is null ";
        }
        if (vo.getCurrentRow().getAttribute("Straggler") != null) {
            String straggler = vo.getCurrentRow()
                                 .getAttribute("Straggler")
                                 .toString();
            wc = wc + " and nvl(straggler,'N') = '" + straggler + "'"; // This is from ceix_sts_delivery_lines table

        } else {
            wc = wc + " and nvl(straggler,'N') ='N' ";
        }
        vol.setWhereClause(wc);
        vol.executeQuery();
        reset_BT_CB();
        AdfFacesContext.getCurrentInstance().addPartialTarget(getDetailPC());
    }

    public void onSelectAllNone(ValueChangeEvent valueChangeEvent) {
        //System.out.println("Value onSelAllNone changed:");
        Boolean check = (Boolean) valueChangeEvent.getNewValue();
        //System.out.println("Value  onSelAllNone is:"+check);
        BindingContext bindingctx = BindingContext.getCurrent();
        DCDataControl dc = bindingctx.dataControlFrame().findDataControl("CeixStsWaybillAMDataControl");
        CeixStsWaybillAMImpl appM = (CeixStsWaybillAMImpl) dc.getDataProvider();
        ViewObject vo = appM.getCeixStsWaybillSearchVO1();
        if (vo.getCurrentRow() != null) {
            if (vo.getCurrentRow().getAttribute("ShipmentNumber") == null) {
                appM.handleSelectCheckBox(check);
            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(getDetailPC());
        if (check == Boolean.TRUE && vo.getCurrentRow().getAttribute("ShipmentNumber") == null) {
            select_BT_CB();
        } else {
            reset_BT_CB();
        }
    }

    public void setSelAllCheckBox(RichSelectBooleanCheckbox selAllCheckBox) {
        this.selAllCheckBox = selAllCheckBox;
    }

    public RichSelectBooleanCheckbox getSelAllCheckBox() {
        return selAllCheckBox;
    }

    public void setProcessBT(RichCommandButton processBT) {
        this.processBT = processBT;
    }

    public RichCommandButton getProcessBT() {
        return processBT;
    }

    public void onLineSelCB(ValueChangeEvent valueChangeEvent) {
        int i = 0;
        //System.out.println("Line Select Value changed:");
        Boolean check = (Boolean) valueChangeEvent.getNewValue();
        //System.out.println("Line Select Value is:"+check);
        BindingContext bindingctx = BindingContext.getCurrent();
        DCDataControl dc = bindingctx.dataControlFrame().findDataControl("CeixStsWaybillAMDataControl");
        CeixStsWaybillAMImpl appM = (CeixStsWaybillAMImpl) dc.getDataProvider();
        ViewObject vo = appM.findViewObject("CeixStsWaybillLinesVO1");
        //System.out.println("Curretn Row: "+vo.getCurrentRow().getAttribute("SequenceNum"));
        vo.getCurrentRow().setAttribute("SelectFlag", check);
        Boolean selflag = false;
        if (vo.getRowCount() > 0) {
            Row curRow = vo.first();
            //System.out.println("The sequence is :"+curRow.getAttribute("SequenceNum"));
            for (i = 0; i < vo.getRowCount(); i++) {
                if (curRow.getAttribute("SelectFlag") != null) {
                    //System.out.println("The value is :"+curRow.getAttribute("SelectFlag").toString());
                    if (curRow.getAttribute("SelectFlag")
                              .toString()
                              .compareTo("true") == 0) {
                        //System.out.println("The value after is :"+curRow.getAttribute("SelectFlag").toString());
                        selflag = Boolean.TRUE;
                        break;
                    }
                }
                curRow = vo.next();
            }
        }
        //System.out.println("Flag is"+selflag);
        if (selflag.equals(true)) {
            select_BT_CB();
        } else {
            reset_BT_CB();
        }
    }

    public String throwableToString(Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);
        pw.close();
        return sw.toString();
    }

    public void onProcessBT(ActionEvent actionEvent) {
        int i = 0;
        FacesContext context = FacesContext.getCurrentInstance();
        StringBuilder logMessage = new StringBuilder("<html><body>");
        try {
            String src_system = "OPS";
            String shipFromOrganization = "1072";
            String orderType = null; //"SALES_ORDER";
            String releaseRule = "Bailey Pick Rule";
            String shipConfirmRule = "Consol Ship Confirm Rule";
            String costName = "Freight Charges";
            String legalEntityName = "CONSOL Energy Inc. BU";
            //String releaseRule = "Bailey";
            String releaseMode = "ONLINE";
            BindingContext bindingctx = BindingContext.getCurrent();
            DCDataControl dc = bindingctx.dataControlFrame().findDataControl("CeixStsWaybillAMDataControl");
            CeixStsWaybillAMImpl appM = (CeixStsWaybillAMImpl) dc.getDataProvider();
            ViewObject vo = appM.findViewObject("CeixStsWaybillLinesVO1");
            ViewObject vohead = appM.findViewObject("CeixStsWaybillSearchVO1");
            //System.out.println("The order number is "+vohead.getCurrentRow().getAttribute("OrderNumber").toString());
            mylog.info("The order getting processed is: " + vohead.getCurrentRow().getAttribute("OrderNumber"));
            String orderNumber = vohead.getCurrentRow()
                                       .getAttribute("OrderNumber")
                                       .toString();
            System.out.println("orderNumber: " + orderNumber);
            String trainNum = vohead.getCurrentRow()
                                    .getAttribute("TrainNumber")
                                    .toString();
            System.out.println("trainNum: " + trainNum);
            String dumpDate = vohead.getCurrentRow()
                                    .getAttribute("DumpDate")
                                    .toString();
            System.out.println("dumpDate: " + dumpDate);
            String headAgreementName = null;
            if (vohead.getCurrentRow().getAttribute("SalesAgreementName") != null) {
                headAgreementName = vohead.getCurrentRow()
                                          .getAttribute("SalesAgreementName")
                                          .toString();
            }
            String headItemNumber = null;
            if (vohead.getCurrentRow().getAttribute("ItemNumber") != null) {
                headItemNumber = vohead.getCurrentRow()
                                       .getAttribute("ItemNumber")
                                       .toString();
            }
            String headOrderType = null;

            String headSONumber = null;
            if (vohead.getCurrentRow().getAttribute("SoNumber") != null) {

                headSONumber = vohead.getCurrentRow()
                                     .getAttribute("SoNumber")
                                     .toString();
            }
            String headLoadOrigin = null;
            if (vohead.getCurrentRow().getAttribute("LoadOrigin") != null) {

                headLoadOrigin = vohead.getCurrentRow()
                                       .getAttribute("LoadOrigin")
                                       .toString();
            }
            String headDestination = null;
            if (vohead.getCurrentRow().getAttribute("Destination") != null) {

                headDestination = vohead.getCurrentRow()
                                        .getAttribute("Destination")
                                        .toString();
            }
            String headStraggler = "N";
            if (vohead.getCurrentRow().getAttribute("StragglerFlag") != null) {
                if (vohead.getCurrentRow()
                          .getAttribute("StragglerFlag")
                          .toString()
                          .compareTo("true") == 0) {
                    headStraggler = "Y";
                }
            }
            String headFreight = null;
            if (vohead.getCurrentRow().getAttribute("Freight") != null) {

                headFreight = vohead.getCurrentRow()
                                    .getAttribute("Freight")
                                    .toString();
            }

            CallableStatement st1 = null;
            CallableStatement st2 = null;
            //String customerNum = null;
            String shipPartyNum = null;
            String fob = null;
            String acceptWeight = null;
            String processShipFlag = null;
            //String processCostFlag = null;
            //String ordType = null;
            String poNum = null;
            String transactionOrgID = null;
            String contractId = null;
            String bUId = null;
            String lEId = null;
            String partyID = null;
            String partyNumber = null;
            String partyName = null;
            String currencyCode = null;
            String soNumber = null;
            String retStatus = null;
            String sourceOrdNum = null;
            String billtoPartySiteId = null;
            String accountNumber = null;
            String custAccountId = null;
            String acccountName = null;
            String billtoSiteUseId = null;
            String billAddress1 = null;
            String billAddress2 = null;
            String billAddress3 = null;
            String billAddress4 = null;
            String billCity = null;
            String billPostalCode = null;
            String billState = null;
            String billProvince = null;
            String billCounty = null;
            String billCountry = null;
            String shiptoPartySiteId = null;
            String shipToSiteUseId = null;
            String shipAddress1 = null;
            String shipAddress2 = null;
            String shipAddress3 = null;
            String shipAddress4 = null;
            String shipCity = null;
            String shipPostalCode = null;
            String shipState = null;
            String shipProvince = null;
            String shipCounty = null;
            String shipCountry = null;
            String paymentTermCode = null;
            String coalType = null;
            String prevSample = null;
            String prevAnalysis = null;
            String trainNumberEff = null;
            //String routingInfoEff = null;
            //String qualityInfoEff = null;
            //String shipperIdNumEff = null;
            String classFlexEff = null;
            Date conStartDate = null;
            Date conEndDate = null;
            String custPO = null;
            Date reqLDate = null;
            Date loadStDate = null;
            Date loadEnDate = null;
            String destCountry = null;
            String domExport = null;
            String carrier = null;
            Date shipLDate= null;
            Date dumpLDate= null;
            if (headItemNumber == null || headDestination == null) {
                processShipFlag = "CF";
                logMessage.append("<p><b>Item Number and Destination are Manadatory to process the Waybill.</b></p>");
                logMessage.append("</body></html>");
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, logMessage.toString(), null);
                context.addMessage("Error", message);
            }
            if (!"CF".equals(processShipFlag)) {
                CallableStatement csOrderType =
                    appM.getDBTransaction()
                    .createCallableStatement(" BEGIN" + " CEIX_GET_ORDER_TYPE (:1,:2,:3,:4);" + " END;", 0);
                csOrderType.setString(1, headItemNumber);
                csOrderType.setString(2, headDestination);
                csOrderType.setString(3, headLoadOrigin);
                csOrderType.registerOutParameter(4, Types.VARCHAR);
                boolean result = csOrderType.execute();

                headOrderType = csOrderType.getString(4);

                if ("INVALID".equals(headOrderType)) {
                    processShipFlag = "CF";
                    logMessage.append("<p><b>Unable to Identify Valid Order type with comination of Item, Load Origin and Destination</b></p>");
                    logMessage.append("</body></html>");
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, logMessage.toString(), null);
                    context.addMessage("Error", message);
                } else {
                    processShipFlag = "Y";
                }

                if ("DirectShip SalesOrder".equals(headOrderType)) {
                    orderType = "SALES_ORDER";
                }
                if ("DropShip SalesOrder".equals(headOrderType)) {
                    orderType = "DROP_SHIP";
                }
                if ("Direct PO Receipt".equals(headOrderType)) {
                    orderType = "PURCHASE_ORDER";
                }
                if ("Material Transfer".equals(headOrderType)) {
                    orderType = "TRANSFER_ORDER";
                }
                if ("Ship Bailey and Purchased Coal".equals(headOrderType)) {
                    orderType = "MISC_ISSUE";
                }
                if ("Misc Receipt DirectShip".equals(headOrderType)) {
                    orderType = "MISC_RECEIPT_DIRECT_SHIP";
                }
            }
            if ("Y".equals(processShipFlag)) {
                try {
                    if ("SALES_ORDER".equals(orderType) || "DROP_SHIP".equals(orderType) ||
                        "MISC_RECEIPT_DIRECT_SHIP".equals(orderType)) {
                        st1 =
                            appM.getDBTransaction()
                            .createCallableStatement("BEGIN" +
                                                     " SELECT ccs.ship_party_site_number,cch.id,cch.org_id,cch.legal_entity_id,cch.party_id, " +
                                                     " cch.party_name, cch.party_number,cch.currency_code, " +
                                                     " ccs.bill_to_party_site_id, ccs.account_number, ccs.cust_account_id, ccs.account_name , ccs.bill_to_site_use_id, " +
                                                     " ccs.bill_address1, ccs.bill_address2,ccs.bill_address3, ccs.bill_address4, ccs.bill_city,ccs.bill_postal_code,   " +
                                                     " ccs.bill_state, ccs.bill_province, ccs.bill_county, ccs.bill_country, ccs.ship_to_party_site_id, ccs.ship_to_site_use_id, " +
                                                     " ccs.ship_address1, ccs.ship_address2, ccs.ship_address3, ccs.ship_address4,ccs.ship_city, ccs.ship_postal_code,ccs.ship_state, " +
                                                     " ccs.ship_province, ccs.ship_county,ccs.ship_country, nvl(cch.payment_terms,ccs.PAYMENT_TERMS), cch.acceptable_weight, cch.coal_type,   " +
                                                     " cch.prev_sample, cch.prev_analysis,cch.start_date,cch.end_date, cch.customer_po,cch.fob, cch.DEST_COUNTRY,cch.DOMESTIC_EXPORT  " +
                                                     " INTO :1, :2, :3, :4, :5 , :6, :7, :8, :9, :10, :11, :12, :13, :14, :15, :16, :17, :18, :19, :20, :21, :22, :23, :24, :25, :26, :27, :28, " +
                                                     " :29, :30, :31, :32, :33, :34, :35, :36, :37, :38, :39, :40, :41, :42, :43, :44, :45, :46 " +
                                                     " FROM ceix_contract_headers cch,CEIX_CUSTOMER_SITE_USES  ccs    WHERE cch.contract_number = :47 and " +
                                                     " nvl(ccs.BILL_TO_SITE_STATUS,'A')='A' and nvl(ccs.BILL_TO_SITE_USE_STATUS,'A')='A' and nvl(ccs.SHIP_TO_SITE_STATUS,'A')='A' and " +
                                                     " nvl(ccs.SHIP_TO_SITE_USE_STATUS,'A')='A' AND " +
                                                     " ccs.party_id=cch.party_id and ccs.CUST_ACCOUNT_ID= cch.CUST_ACCOUNT_ID and upper(ccs.SHIP_PARTY_SITE_NAME) = :48; " +
                                                     " END;", 0);
                        st1.registerOutParameter(1, Types.VARCHAR);
                        st1.registerOutParameter(2, Types.VARCHAR);
                        st1.registerOutParameter(3, Types.VARCHAR);
                        st1.registerOutParameter(4, Types.VARCHAR);
                        st1.registerOutParameter(5, Types.VARCHAR);
                        st1.registerOutParameter(6, Types.VARCHAR);
                        st1.registerOutParameter(7, Types.VARCHAR);
                        st1.registerOutParameter(8, Types.VARCHAR);
                        st1.registerOutParameter(9, Types.VARCHAR);
                        st1.registerOutParameter(10, Types.VARCHAR);
                        st1.registerOutParameter(11, Types.VARCHAR);
                        st1.registerOutParameter(12, Types.VARCHAR);
                        st1.registerOutParameter(13, Types.VARCHAR);
                        st1.registerOutParameter(14, Types.VARCHAR);
                        st1.registerOutParameter(15, Types.VARCHAR);
                        st1.registerOutParameter(16, Types.VARCHAR);
                        st1.registerOutParameter(17, Types.VARCHAR);
                        st1.registerOutParameter(18, Types.VARCHAR);
                        st1.registerOutParameter(19, Types.VARCHAR);
                        st1.registerOutParameter(20, Types.VARCHAR);
                        st1.registerOutParameter(21, Types.VARCHAR);
                        st1.registerOutParameter(22, Types.VARCHAR);
                        st1.registerOutParameter(23, Types.VARCHAR);
                        st1.registerOutParameter(24, Types.VARCHAR);
                        st1.registerOutParameter(25, Types.VARCHAR);
                        st1.registerOutParameter(26, Types.VARCHAR);
                        st1.registerOutParameter(27, Types.VARCHAR);
                        st1.registerOutParameter(28, Types.VARCHAR);
                        st1.registerOutParameter(29, Types.VARCHAR);
                        st1.registerOutParameter(30, Types.VARCHAR);
                        st1.registerOutParameter(31, Types.VARCHAR);
                        st1.registerOutParameter(32, Types.VARCHAR);
                        st1.registerOutParameter(33, Types.VARCHAR);
                        st1.registerOutParameter(34, Types.VARCHAR);
                        st1.registerOutParameter(35, Types.VARCHAR);
                        st1.registerOutParameter(36, Types.VARCHAR);
                        st1.registerOutParameter(37, Types.VARCHAR);
                        st1.registerOutParameter(38, Types.VARCHAR);
                        st1.registerOutParameter(39, Types.VARCHAR);
                        st1.registerOutParameter(40, Types.VARCHAR);
                        st1.registerOutParameter(41, Types.DATE);
                        st1.registerOutParameter(42, Types.DATE);
                        st1.registerOutParameter(43, Types.VARCHAR);
                        st1.registerOutParameter(44, Types.VARCHAR);
                        st1.registerOutParameter(45, Types.VARCHAR);
                        st1.registerOutParameter(46, Types.VARCHAR);
                        st1.setString(47, orderNumber);
                        st1.setString(48, headDestination);
                        st1.execute();
                        shipPartyNum = st1.getString(1);
                        contractId = st1.getString(2);
                        bUId = st1.getString(3);
                        lEId = st1.getString(4);
                        partyID = st1.getString(5);
                        partyName = st1.getString(6);
                        partyNumber = st1.getString(7);
                        currencyCode = st1.getString(8);
                        billtoPartySiteId = st1.getString(9);
                        accountNumber = st1.getString(10);
                        custAccountId = st1.getString(11);
                        acccountName = st1.getString(12);
                        billtoSiteUseId = st1.getString(13);
                        billAddress1 = st1.getString(14);
                        billAddress2 = st1.getString(15);
                        billAddress3 = st1.getString(16);
                        billAddress4 = st1.getString(17);
                        billCity = st1.getString(18);
                        billPostalCode = st1.getString(19);
                        billState = st1.getString(20);
                        billProvince = st1.getString(21);
                        billCounty = st1.getString(22);
                        billCountry = st1.getString(23);
                        shiptoPartySiteId = st1.getString(24);
                        shipToSiteUseId = st1.getString(25);
                        shipAddress1 = st1.getString(26);
                        shipAddress2 = st1.getString(27);
                        shipAddress3 = st1.getString(28);
                        shipAddress4 = st1.getString(29);
                        shipCity = st1.getString(30);
                        shipPostalCode = st1.getString(31);
                        shipState = st1.getString(32);
                        shipProvince = st1.getString(33);
                        shipCounty = st1.getString(34);
                        shipCountry = st1.getString(35);
                        paymentTermCode = st1.getString(36);
                        acceptWeight = st1.getString(37);
                        coalType = st1.getString(38);
                        prevSample = st1.getString(39);
                        prevAnalysis = st1.getString(40);
                        conStartDate = st1.getDate(41);
                        conEndDate = st1.getDate(42);
                        custPO = st1.getString(43);
                        fob = st1.getString(44);
                        destCountry = st1.getString(45);
                        domExport = st1.getString(46);
                        //poNum = st1.getString(6);
                    } else {
                        st1 =
                            appM.getDBTransaction()
                            .createCallableStatement("BEGIN" +
                                                     " SELECT cch.id,cch.org_id,cch.legal_entity_id,cch.party_id, " +
                                                     " cch.party_name, cch.party_number,cch.currency_code, " +
                                                     " cch.acceptable_weight, cch.coal_type,   " +
                                                     " cch.prev_sample, cch.prev_analysis,cch.start_date,cch.end_date, cch.customer_po,cch.fob, cch.DEST_COUNTRY,cch.DOMESTIC_EXPORT  " +
                                                     " INTO :1, :2, :3, :4, :5 , :6, :7, :8, :9, :10, :11, :12, :13, :14, :15, :16, :17 " +
                                                     " FROM ceix_contract_headers cch WHERE cch.contract_number = :18; " +
                                                     " END;", 0);
                        st1.registerOutParameter(1, Types.VARCHAR);
                        st1.registerOutParameter(2, Types.VARCHAR);
                        st1.registerOutParameter(3, Types.VARCHAR);
                        st1.registerOutParameter(4, Types.VARCHAR);
                        st1.registerOutParameter(5, Types.VARCHAR);
                        st1.registerOutParameter(6, Types.VARCHAR);
                        st1.registerOutParameter(7, Types.VARCHAR);
                        st1.registerOutParameter(8, Types.VARCHAR);
                        st1.registerOutParameter(9, Types.VARCHAR);
                        st1.registerOutParameter(10, Types.VARCHAR);
                        st1.registerOutParameter(11, Types.VARCHAR);
                        st1.registerOutParameter(12, Types.DATE);
                        st1.registerOutParameter(13, Types.DATE);
                        st1.registerOutParameter(14, Types.VARCHAR);
                        st1.registerOutParameter(15, Types.VARCHAR);
                        st1.registerOutParameter(16, Types.VARCHAR);
                        st1.registerOutParameter(17, Types.VARCHAR);
                        st1.setString(18, orderNumber);
                        st1.execute();
                        contractId = st1.getString(1);
                        bUId = st1.getString(2);
                        lEId = st1.getString(3);
                        partyID = st1.getString(4);
                        partyName = st1.getString(5);
                        partyNumber = st1.getString(6);
                        currencyCode = st1.getString(7);
                        acceptWeight = st1.getString(8);
                        coalType = st1.getString(9);
                        prevSample = st1.getString(10);
                        prevAnalysis = st1.getString(11);
                        conStartDate = st1.getDate(12);
                        conEndDate = st1.getDate(13);
                        custPO = st1.getString(14);
                        fob = st1.getString(15);
                        destCountry = st1.getString(16);
                        domExport = st1.getString(17);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        st1.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                try {
                    st2 =
                        appM.getDBTransaction()
                        .createCallableStatement("BEGIN" +
                                                 " select ORGANIZATION_CODE INTO :1 from CEIX_INV_ORGANIZATION_DEFINITIONS " +
                                                 " where upper(ORGANIZATION_NAME) =:2; " + " END;", 0);
                    st2.registerOutParameter(1, Types.VARCHAR);
                    st2.setString(2, headLoadOrigin);
                    st2.execute();
                    shipFromOrganization = st2.getString(1);
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        st2.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                if (shipFromOrganization == null) {
                    processShipFlag = "CF";
                    orderType = "KK";
                    logMessage.append("<p><b>Unable to fetch the Inventory Organization</b></p>");
                    logMessage.append("</body></html>");
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, logMessage.toString(), null);
                    context.addMessage("Error", message);
                }

                //mylog.info("The values: " + fob + ";" + acceptWeight + ";" +
                //           ordType + ";" + shipFromOrganization);


                if (contractId == null) {
                    processShipFlag = "CF";
                    orderType = "KK";
                    logMessage.append("<p><b>Unable to fetch the customer details from Contracts</b></p>");
                    logMessage.append("</body></html>");
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, logMessage.toString(), null);
                    context.addMessage("Error", message);
                }

                if (shipFromOrganization == null) {
                    shipFromOrganization = "1072";
                    releaseRule = "Bailey Pick Rule";
                    shipConfirmRule = "Consol Ship Confirm Rule";
                    //   shipFromOrganization = "1401";
                } else if ("1665".equals(shipFromOrganization)) {
                    shipConfirmRule = "Baltimore Ship Confirm Rule";
                    releaseRule = "Baltimore Pick Rule";
                } else if ("1401".equals(shipFromOrganization)) {
                    //     =
                    shipConfirmRule = "Consol Ship Confirm Rule"; //Added as a part of ORACLE-5969 as an New Organization enhancement
                    releaseRule = "Itmann5 Coal Release Rule";
                }

                if (!("DESTINATION".equals(fob))) {
                    fob = "ORIGIN";
                }
                /*if ("ORIGIN".equals(fob)) {
                if ("Waybill Weights Prevail".equals(acceptWeight)) {
                    processShipFlag = "Y";
                    processCostFlag = "N";
                } else {
                    processShipFlag = "N";
                    processCostFlag = "N";
                }
            } else {
                if ("Waybill Weights Prevail".equals(acceptWeight)) {
                    processShipFlag = "Y";
                    processCostFlag = "Y";
                } else {
                    processShipFlag = "N";
                    processCostFlag = "N";
                }
            }*/
                if ("Waybill Weights Prevail".equals(acceptWeight)) {
                    processShipFlag = "Y";
                } else {
                    processShipFlag = "N";
                }
                if ("Y".equals(processShipFlag)) {
                    String selAll = "Y";
                    String newtrainnum = null;
                    String prevnewtrainnum = null;
                    if (vo.getRowCount() > 0) {
                        Row curRow = vo.first();
                        for (i = 0; i < vo.getRowCount(); i++) {
                            if (curRow.getAttribute("SelectFlag") != null) {
                                if (curRow.getAttribute("SelectFlag")
                                          .toString()
                                          .compareTo("true") == 0) {
                                    //System.out.println("The row is selected with sequencenum :"+curRow.getAttribute("DumpSequence")+" and car: "+ curRow.getAttribute("CarNumber"));
                                    //System.out.println("The row is selected with sequencenum :"+prevnewtrainnum+";"+newtrainnum+";"+i+";"+curRow.getAttribute("NewTrainNumber"));
                                    if ((curRow.getAttribute("NewTrainNumber") != null) || (prevnewtrainnum != null)) {
                                        if (curRow.getAttribute("NewTrainNumber") != null) {
                                            newtrainnum = curRow.getAttribute("NewTrainNumber").toString();
                                            trainNum = newtrainnum;
                                        } else {
                                            newtrainnum = "NULL";
                                        }

                                        if (!(newtrainnum.equals(prevnewtrainnum)) && (i != 0)) {
                                            processShipFlag = "CF";
                                            orderType = "KK";
                                            logMessage.append("<p><b>New Train Number should be unique for all cars selected.</b></p>");
                                            logMessage.append("<p><b>Please process separate shipments for different New Train numbers.</b></p>");
                                            logMessage.append("</body></html>");
                                            FacesMessage message =
                                                new FacesMessage(FacesMessage.SEVERITY_ERROR, logMessage.toString(),
                                                                 null);
                                            context.addMessage("Error", message);
                                            break;
                                        }
                                        prevnewtrainnum = newtrainnum;
                                    }
                                } else {
                                    selAll = "N";
                                }
                            } else {
                                selAll = "N";
                            }
                            curRow = vo.next();
                        }
                    }
                    // Validation for reprocess of existing Sales Order. For reporcess all lines should be selected.
                    if (headSONumber != null && "N".equals(selAll)) {
                        processShipFlag = "CF";
                        //processCostFlag = "CF";
                        orderType = "KK";
                        logMessage.append("<p><b>For Already created order please select all lines and process</b></p>");
                        logMessage.append("<p><b>Please Cancel existing order and reprocess in case of partial shipments.</b></p>");
                        logMessage.append("</body></html>");
                        FacesMessage message =
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, logMessage.toString(), null);
                        context.addMessage("Error", message);
                    }
                    if ("Y".equals(processShipFlag)) 
					{
                        CallableStatement st12 = null;
                        int cnt = 0;
                        int selrows = 0;
                        BigDecimal totalweight = new BigDecimal(0);
                        if (vo.getRowCount() > 0) 
						{
                            Row curRow = vo.first();
                            for (i = 0; i < vo.getRowCount(); i++) 
							{
                                if (curRow.getAttribute("SelectFlag") != null)
									{
                                    if (curRow.getAttribute("SelectFlag")
                                              .toString()
                                              .compareTo("true") ==
                                        0) {
                                        // System.out.println("The row is selected with sequencenum :"+curRow.getAttribute("SequenceNum")+" and car: "+ curRow.getAttribute("CarId"));
                                        //System.out.println("curRow.getAttribute :" +
                                        //                   curRow.getAttribute("WeightInTons").toString());
                                      /*  BigDecimal weightInTons =
           new BigDecimal(curRow.getAttribute("WeightInTons").toString());
                                        weightInTons = weightInTons.setScale(5, RoundingMode.HALF_EVEN);*/
                                      System.out.println("curRow.getAttribute(\"DumpWeight\").toString()"+curRow.getAttribute("DumpWeight"));
                             //           System.out.println("curRow.getAttribute(\"DumpWeight\").toString()"+curRow.getAttribute("DumpWeight").toString());
                             System.out.println("Testing");
                                   //     BigDecimal weightInTons =curRow.getAttribute("DumpWeight").toString()!=null?
                                        BigDecimal weightInTons =curRow.getAttribute("DumpWeight")!=null?
                                            new BigDecimal(curRow.getAttribute("DumpWeight").toString()):
                                        new BigDecimal(curRow.getAttribute("WeightInTons").toString());
                                      weightInTons = weightInTons.setScale(4, RoundingMode.HALF_EVEN);
                                       totalweight = totalweight.add(weightInTons);
                                     //   totalweight = totalweight.setScale(4, RoundingMode.HALF_EVEN).add(weightInTons);
                                        
                                        //If the quantity of Car is 0 then we are not considering it to add it in Shipment Split
                                        if (!(weightInTons.compareTo(BigDecimal.ZERO) == 0)) {
                                            selrows++;
                                            //totalweight.add(new BigDecimal(String.valueOf(curRow.getAttribute("WeightInTons"))));
                                            // totalweight =totalweight.add(new BigDecimal(curRow.getAttribute("WeightInTons").toString()));
                                            if (selrows == 1) {
                                                //QUALITY DFF Information
                                                try {
                                                    st2 =
                                                        appM.getDBTransaction()
                                                        .createCallableStatement("BEGIN" +
                                                                                 " SELECT DISTINCT (select nvl(new_train_number,dh.TRAIN_NUMBER) from ceix_sts_waybill_btmore_lines where delivery_header_id=dh.delivery_header_id and ROWNUM=1), " +
                                                                                 " csr.REQUESTED_LOAD_DATE, csr.LOAD_START_DATE, csr.LOAD_END_DATE, TO_CHAR(dh.dumped_date,'YYMMDD'), " +
                                                                                 " trim(upper(nvl(csr.carrier, (select dl.rail_road from ceix_sts_waybill_btmore_lines dl where dl.delivery_header_id = dh.delivery_header_id and dl.rail_road is not null and rownum=1)))) ," +
                                                                                 "dh.ship_date,dh.dumped_date"+
                                                                                 " INTO :1, :2, :3, :4, :5, :6 , :7, :8" +
                                                                                 " FROM CEIX_STS_WAYBILL_BTMORE_HDR dh, CEIX_STS_RAIL_SCHEDULES csr " +
                                                                                 " WHERE dh.delivery_header_id = :9 " +
                                                                                 " AND TO_DATE(TO_CHAR(dh.dumped_date, 'DD-MON-YYYY'),'DD-MON-YY') BETWEEN (csr.LOAD_DATE(+)-365) " +
                                                                                 " AND (csr.LOAD_DATE(+)+365) " +
                                                                                 " AND nvl(csr.hold_flag,'x') NOT IN ('D','Y') " +
                                                                                 " AND csr.TRAIN_ID(+) = dh.TRAIN_NUMBER; " +
                                                                                 " END;", 0);
                                                    st2.registerOutParameter(1, Types.VARCHAR);
                                                    st2.registerOutParameter(2, Types.DATE);
                                                    st2.registerOutParameter(3, Types.DATE);
                                                    st2.registerOutParameter(4, Types.DATE);
                                                    st2.registerOutParameter(5, Types.VARCHAR);
                                                    st2.registerOutParameter(6, Types.VARCHAR);
                                                    st2.registerOutParameter(7, Types.DATE);
                                                    st2.registerOutParameter(8, Types.DATE);
                                                    st2.setString(9, vo.getCurrentRow()
                                                                       .getAttribute("DeliveryHeaderId")
                                                                       .toString());
                                                    st2.execute();
                                                    trainNumberEff = st2.getString(1);
                                                    //routingInfoEff = st2.getString(2);
                                                    //qualityInfoEff = st2.getString(3);
                                                    //shipperIdNumEff = st2.getString(4);
                                                    //classFlexEff = st2.getString(5);
                                                    reqLDate = st2.getDate(2);
                                                    loadStDate = st2.getDate(3);
                                                    loadEnDate = st2.getDate(4);
                                                    classFlexEff = st2.getString(5);
                                                    carrier = st2.getString(6);
                                                    shipLDate=st2.getDate(7);
                                                    dumpLDate=st2.getDate(8);
                                                } catch (SQLException e) {
                                                    e.printStackTrace();
                                                } finally {
                                                    try {
                                                        st2.close();
                                                    } catch (SQLException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                curRow = vo.next();
                            }
							totalweight = totalweight.setScale(2, RoundingMode.HALF_EVEN);
                        }

                        if ("Y".equals(processShipFlag) && "MISC_RECEIPT_DIRECT_SHIP".equals(orderType))
							{
                            //If there is already Sales order Created then Directly go to Picking instead of Misc Receipt and SO Creation
                            if (headSONumber != null) {
                                orderType = "SALES_ORDER";
                            } else
								{
                                ObjectFactory objectFactory = new ObjectFactory();
                                List<StagedInventoryTransaction> stagedInventoryTransactionList = new ArrayList();
                                DateFormat timeStamp = new SimpleDateFormat("MMddHHmm");
                                timeStamp.setTimeZone(TimeZone.getTimeZone("America/New_York"));
                                String first10CharsofTrain=trainNum.length()>=10?
                                                                            trainNum.substring(0,10):trainNum;
                                String waybillNum = first10CharsofTrain + "-" + timeStamp.format(new Date());
                                CallableStatement statement = null;
                                int transInterfaceId = 0;
                                String ItemId = null;
                                CallableStatement st17 = null;
                                CallableStatement st18 = null;
                                try 
								{
                                    st17 =
                                        appM.getDBTransaction()
                                        .createCallableStatement("BEGIN " +
                                                                 " SELECT ceix_sts_waybill_trans_ord_seq.NEXTVAL seq_num, " +
                                                                 " ccl.object1_id1 " + " INTO :1,:2 " +
                                                                 " FROM ceix_contract_lines ccl, ceix_contract_headers cch " +
                                                                 " WHERE cch.contract_number ='" + orderNumber + "' " +
                                                                 " AND cch.id = ccl.chr_id " +
                                                                 " AND ccl.ITEM_NUMBER = NVL ('" + headItemNumber +
                                                                 "',ccl.ITEM_NUMBER) " +
                                                                 " AND ccl.item_type <> 'FRT' " +
                                                                 " AND ccl.sts_code in ('ACTIVE','EXPIRED') " +
                                                                 " AND TO_DATE('" + dumpDate +
                                                                 "','yyyy-MM-dd') BETWEEN NVL (ccl.start_date, TO_DATE('" +
                                                                 dumpDate + "','yyyy-MM-dd') - 1) " +
                                                                 " AND NVL (ccl.end_date, TO_DATE('" + dumpDate +
                                                                 "','yyyy-MM-dd') +1); " + " END;", 0);

                                    st17.registerOutParameter(1, Types.INTEGER);
                                    st17.registerOutParameter(2, Types.VARCHAR);
                                    st17.execute();
                                    transInterfaceId = st17.getInt(1);
                                    ItemId = st17.getString(2);
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                } finally {
                                    try {
                                        st17.close();
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                }

                                try {
                                    st18 =
                                        appM.getDBTransaction()
                                        .createCallableStatement("BEGIN" +
                                                                 " select ORGANIZATION_ID INTO :1 from CEIX_INV_ORGANIZATION_DEFINITIONS " +
                                                                 " where upper(ORGANIZATION_NAME) =:2; " + " END;", 0);
                                    st18.registerOutParameter(1, Types.VARCHAR);
                                    st18.setString(2, headLoadOrigin);
                                    st18.execute();
                                    transactionOrgID = st18.getString(1);
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                } finally {
                                    try {
                                        st18.close();
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                }

                                //System.out.println ("The transInterfaceId value is-->" + transInterfaceId+";"+ItemId+";"+OrgId);
                                JAXBElement<Long> transactionHeaderId =
                                    objectFactory.createStagedInventoryTransactionTransactionHeaderId(new Long("1000"));
                                JAXBElement<Long> inventoryItemId =
                                    objectFactory.createStagedInventoryTransactionInventoryItemId(new Long(ItemId));
                                JAXBElement<Long> organizationId =
                                    objectFactory.createStagedInventoryTransactionOrganizationId(new Long(transactionOrgID));
                                MeasureType mt = new MeasureType();
                                mt.setUnitCode("TON");
                                mt.setValue(totalweight);
                                JAXBElement<String> uom =
                                    objectFactory.createStagedInventoryTransactionTransactionUOM("TON");
                                //System.out.println("The date is "+dumpDate);
                                GregorianCalendar cal = new GregorianCalendar();
                                GregorianCalendar trcal = new GregorianCalendar();
                                XMLGregorianCalendar transDate = null;

                                try {
                                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                                    df.setTimeZone(TimeZone.getTimeZone("America/New_York"));
                                    Date date = df.parse(dumpDate);
                                    cal.setTime(date);
                                    //cal.add(GregorianCalendar.HOUR, 6);
                                    DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
                                    transDate = datatypeFactory.newXMLGregorianCalendar(cal);
                                    //System.out.println("The date is :"+transDate);
                                } catch (ParseException e) {
                                    //System.out.println("Error: Invalid Date Parser Exception.");
                                    mylog.info("Error: Invalid Date Parser Exception.");
                                    e.printStackTrace();
                                } catch (Exception e) {
                                    //System.out.println("Error: Exception.");
                                    mylog.info("Error: Exception.");
                                    e.printStackTrace();
                                }
                                JAXBElement<String> subinv =
                                    objectFactory.createStagedInventoryTransactionSubinventoryCode("STAGING");
                                //JAXBElement<String> actionId = objectFactory.createStagedInventoryTransactionTransactionActionId("3");
                                JAXBElement<Long> typeId =
                                    objectFactory.createStagedInventoryTransactionTransactionTypeId(new Long("42"));
                                JAXBElement<String> tranRef =
                                    objectFactory.createStagedInventoryTransactionTransactionReference(trainNum);
                                //BigDecimal fCost = new BigDecimal(curRow.getAttribute("WeightInTons").toString()).multiply(new BigDecimal(freCost));
                                //JAXBElement<BigDecimal> freightCost = objectFactory.createStagedInventoryTransactionTransportationCost(fCost);
                                JAXBElement<String> shipNum =
                                    objectFactory.createStagedInventoryTransactionShipmentNumber(waybillNum);
                                JAXBElement<String> curCost =
                                    objectFactory.createStagedInventoryTransactionUseCurrentCost("Y");
                                JAXBElement<String> dstSegment1 = objectFactory.createStagedInventoryTransactionDstSegment1("1680");
                                JAXBElement<String> dstSegment2 = objectFactory.createStagedInventoryTransactionDstSegment2("17514");
                                JAXBElement<String> dstSegment3 = objectFactory.createStagedInventoryTransactionDstSegment3("050");
                                JAXBElement<String> dstSegment4 = objectFactory.createStagedInventoryTransactionDstSegment4("105040");
                                JAXBElement<String> dstSegment5 = objectFactory.createStagedInventoryTransactionDstSegment5("0000000");
                                JAXBElement<String> dstSegment6 = objectFactory.createStagedInventoryTransactionDstSegment6("0000");
                                JAXBElement<String> dstSegment7 = objectFactory.createStagedInventoryTransactionDstSegment7("0000");
                                JAXBElement<String> dstSegment8 = objectFactory.createStagedInventoryTransactionDstSegment8("0000");

                                StagedInventoryTransaction stagedInventoryTransaction =
                                    new StagedInventoryTransaction();
                                stagedInventoryTransaction.setTransactionInterfaceId(new Long(transInterfaceId));
                                stagedInventoryTransaction.setTransactionHeaderId(transactionHeaderId);
                                stagedInventoryTransaction.setSourceCode(trainNum);
                                stagedInventoryTransaction.setSourceLineId(new Long("1"));
                                stagedInventoryTransaction.setSourceHeaderId(new Long(transInterfaceId));
                                stagedInventoryTransaction.setProcessCode("1");
                                stagedInventoryTransaction.setTransactionMode("3");
                                stagedInventoryTransaction.setInventoryItemId(inventoryItemId);
                                stagedInventoryTransaction.setOrganizationId(organizationId);
                                stagedInventoryTransaction.setTransactionQuantity(mt);
                                stagedInventoryTransaction.setTransactionUOM(uom);
                                stagedInventoryTransaction.setTransactionDate(transDate);
                                stagedInventoryTransaction.setSubinventoryCode(subinv);
                                //stagedInventoryTransaction.setTransactionActionId(actionId);
                                stagedInventoryTransaction.setTransactionTypeId(typeId);
                                stagedInventoryTransaction.setTransactionReference(tranRef);
                                //stagedInventoryTransaction.setTransferSubinventory(trsubinv);
                                //stagedInventoryTransaction.setTransferOrganization(transOrgId);
                                //stagedInventoryTransaction.setTransportationCost(freightCost);
                                stagedInventoryTransaction.setShipmentNumber(shipNum);
                                stagedInventoryTransaction.setUseCurrentCost(curCost);
                                stagedInventoryTransaction.setDstSegment1(dstSegment1);
                                stagedInventoryTransaction.setDstSegment2(dstSegment2);
                                stagedInventoryTransaction.setDstSegment3(dstSegment3);
                                stagedInventoryTransaction.setDstSegment4(dstSegment4);
                                stagedInventoryTransaction.setDstSegment5(dstSegment5);
                                stagedInventoryTransaction.setDstSegment6(dstSegment6);
                                stagedInventoryTransaction.setDstSegment7(dstSegment7);
                                stagedInventoryTransaction.setDstSegment8(dstSegment8);

                                stagedInventoryTransactionList.add(stagedInventoryTransaction);


                                String status = null;
                                status = appM.callinsertAndProcessInterfaceRowsImpl(stagedInventoryTransactionList);
                                //System.out.println("Resp from callProcessorImpl status:"+status);
                                if (!(status.equals("0"))) {
                                    logMessage.append("<p><b>Error occurred while inserting Inventory Transaction : " +
                                                      status + "</b></p>");
                                    logMessage.append("</body></html>");
                                    FacesMessage message =
                                        new FacesMessage(FacesMessage.SEVERITY_ERROR, logMessage.toString(), null);
                                    context.addMessage("Error", message);
                                } else {
                                    logMessage.append("<p><b>Misc Receipt Transaction processed. </b></p>");
                                    logMessage.append("<p><b>Misc Receipt Waybill Number: " + waybillNum + "</b></p>");
                                    logMessage.append("</body></html>");
                                    FacesMessage message =
                                        new FacesMessage(FacesMessage.SEVERITY_INFO, logMessage.toString(), null);
                                    context.addMessage("Confirmation", message);
                                    //Changing Order Type to Sales Order So that it will create Sales order and Shipment after Misc Receipt
                                    orderType = "SALES_ORDER";
                                }
                                //String status = appM.callProcessorImpl(stagedReceivingHeader);
                                /*if ("0".equals(status)) {
                            if (vo.getRowCount() > 0) {
                                Row curRow = vo.first();
                                for (i = 0; i < vo.getRowCount(); i++) {
                                    if (curRow.getAttribute("SelectFlag") != null) {
                                        if (curRow.getAttribute("SelectFlag")
                                                  .toString()
                                                  .compareTo("true") == 0) {
                                            curRow.setAttribute("ShipmentNumber", waybillNum);
                                            curRow.setAttribute("ProcessedFlag", "Y");
                                        }
                                    }
                                    curRow = vo.next();
                                }
                            }
                        }*/
                            }
                        }
                        if ("Y".equals(processShipFlag) &&
                            ("DROP_SHIP".equals(orderType) || "SALES_ORDER".equals(orderType)))
							{
                            //Get Sales ORder number from Sequence
                            OrderImportResponse orderImportResponse = new OrderImportResponse();
                            String price = null;
                            if (headSONumber != null) {
                                sourceOrdNum = headSONumber;
                                soNumber = headSONumber;
                                retStatus = "SUCCESS";
                            } else {
                                try {
                                    st12 =
                                        appM.getDBTransaction()
                                        .createCallableStatement("BEGIN " + " SELECT CEIX_ORDER_NUMBER_SEQ.NEXTVAL " +
                                                                 " INTO :1 " + " FROM DUAL; " + " END;", 0);
                                    st12.registerOutParameter(1, Types.VARCHAR);
                                    st12.execute();
                                    sourceOrdNum = st12.getString(1);
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                } finally {
                                    try {
                                        st12.close();
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                }
                                //Added Code Starts -*-
                                OrderImportRequest OrderImportDtls = null;
                                OrderImportDtls = new OrderImportRequest();
                                com.oracle.xmlns.apps.scm.fom.importorders.orderimportservice.ObjectFactory OrdObjectFactory =
                                    new com.oracle.xmlns.apps.scm.fom.importorders.orderimportservice.ObjectFactory();
                                Order order = new Order();
                                //Order Header Information
                                GregorianCalendar cal = new GregorianCalendar();
                                XMLGregorianCalendar transDate = null;
                                //transactionOn
                                try {
                                    //SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                                    cal.setTimeZone(TimeZone.getTimeZone("America/New_York"));
                                    cal.setTime(cal.getTime());
                                    DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
                                    transDate = datatypeFactory.newXMLGregorianCalendar(cal);
                                    //System.out.println("The date is :"+transDate);
                                } catch (Exception e) {
                                    //System.out.println("Error: Exception.");
                                    e.printStackTrace();
                                }
                                JAXBElement<String> sourceTransactionIdentifier =
                                    OrdObjectFactory.createOrderSourceTransactionIdentifier(sourceOrdNum);
                                JAXBElement<String> sourceTransactionSystem =
                                    OrdObjectFactory.createOrderSourceTransactionSystem(src_system);
                                JAXBElement<String> sourceTransactionNumber =
                                    OrdObjectFactory.createOrderSourceTransactionNumber(sourceOrdNum);
                                JAXBElement<Long> buyingPartyId =
                                    OrdObjectFactory.createOrderBuyingPartyId(Long.valueOf(partyID));
                                JAXBElement<String> buyingPartyName =
                                    OrdObjectFactory.createOrderBuyingPartyName(partyName);
                                JAXBElement<String> transactionalCurrencyCode =
                                    OrdObjectFactory.createOrderTransactionalCurrencyCode(currencyCode);
                                JAXBElement<XMLGregorianCalendar> transactionOn =
                                    OrdObjectFactory.createOrderTransactionOn(transDate);
                                JAXBElement<Long> requestingBusinessUnitIdentifier =
                                    OrdObjectFactory.createOrderRequestingBusinessUnitIdentifier(Long.valueOf(bUId));
                                JAXBElement<String> transactionTypeCode = null;
                                if ("1401".equals(shipFromOrganization)){
                                  transactionTypeCode = OrdObjectFactory.createOrderTransactionTypeCode("ITMANN");
                                }
                                JAXBElement<Long> requestingLegalUnitIdentifier =
                                    OrdObjectFactory.createOrderRequestingLegalUnitIdentifier(Long.valueOf(lEId));
                                JAXBElement<Boolean> partialShipAllowedFlag =
                                    OrdObjectFactory.createOrderLinePartialShipAllowedFlag(true);
                                JAXBElement<Boolean> freezePriceFlag =
                                    OrdObjectFactory.createOrderFreezePriceFlag(false);
                                JAXBElement<Long> shipToPartyIdentifier =
                                    OrdObjectFactory.createOrderLineShipToPartyIdentifier(Long.valueOf(partyID));
                                JAXBElement<String> shipToPartyName =
                                    OrdObjectFactory.createOrderLineShipToPartyName(partyName);
                                JAXBElement<String> shipToPartyNumber =
                                    OrdObjectFactory.createOrderLineShipToPartyNumber(partyNumber);
                                JAXBElement<Long> shipToPartySiteIdentifier =
                                    OrdObjectFactory.createOrderLineShipToPartySiteIdentifier(Long.valueOf(shiptoPartySiteId));
                                JAXBElement<String> shipToAddress1 =
                                    OrdObjectFactory.createOrderLineShipToAddress1(shipAddress1);
                                JAXBElement<String> shipToAddress2 =
                                    OrdObjectFactory.createOrderLineShipToAddress1(shipAddress2);
                                JAXBElement<String> shipToAddress3 =
                                    OrdObjectFactory.createOrderLineShipToAddress1(shipAddress3);
                                JAXBElement<String> shipToAddress4 =
                                    OrdObjectFactory.createOrderLineShipToAddress1(shipAddress4);
                                JAXBElement<String> shipToCity = OrdObjectFactory.createOrderLineShipToCity(shipCity);
                                JAXBElement<String> shipToPostalCode =
                                    OrdObjectFactory.createOrderLineShipToPostalCode(shipPostalCode);
                                JAXBElement<String> shipToState =
                                    OrdObjectFactory.createOrderLineShipToState(shipState);
                                JAXBElement<String> shipToProvince =
                                    OrdObjectFactory.createOrderLineShipToProvince(shipProvince);
                                JAXBElement<String> shipToCounty =
                                    OrdObjectFactory.createOrderLineShipToCounty(shipCounty);
                                JAXBElement<String> shipToCountry =
                                    OrdObjectFactory.createOrderLineShipToCountry(shipCountry);
                                JAXBElement<String> billToCustomerName =
                                    OrdObjectFactory.createOrderBillToCustomerName(acccountName);
                                JAXBElement<String> billToCustomerNumber =
                                    OrdObjectFactory.createOrderBillToCustomerNumber(accountNumber);
                                JAXBElement<Long> billToAccountSiteUseIdentifier =
                                    OrdObjectFactory.createOrderBillToAccountSiteUseIdentifier(Long.valueOf(billtoSiteUseId));
                                JAXBElement<String> billToAddress1 =
                                    OrdObjectFactory.createOrderBillToAddress1(billAddress1);
                                JAXBElement<String> billToAddress2 =
                                    OrdObjectFactory.createOrderBillToAddress1(billAddress2);
                                JAXBElement<String> billToAddress3 =
                                    OrdObjectFactory.createOrderBillToAddress1(billAddress3);
                                JAXBElement<String> billToAddress4 =
                                    OrdObjectFactory.createOrderBillToAddress1(billAddress4);
                                JAXBElement<String> billToCity = OrdObjectFactory.createOrderLineShipToCity(billCity);
                                JAXBElement<String> billToPostalCode =
                                    OrdObjectFactory.createOrderLineShipToPostalCode(billPostalCode);
                                JAXBElement<String> billToState =
                                    OrdObjectFactory.createOrderLineShipToState(billState);
                                JAXBElement<String> billToProvince =
                                    OrdObjectFactory.createOrderLineShipToProvince(billProvince);
                                JAXBElement<String> billToCounty =
                                    OrdObjectFactory.createOrderLineShipToCounty(billCounty);
                                JAXBElement<String> billToCountry =
                                    OrdObjectFactory.createOrderLineShipToCountry(billCountry);
                                //JAXBElement<String> salesAgreementNumber =
                                //    OrdObjectFactory.createOrderSalesAgreementNumber(orderNumber);
                                JAXBElement<Long> salesAgreementHeaderIdentifier =
                                    OrdObjectFactory.createOrderLineSalesAgreementHeaderIdentifier(Long.valueOf(contractId));
                                JAXBElement<String> customerPONumber =
                                    OrdObjectFactory.createOrderCustomerPONumber(custPO);
                                order.setSourceTransactionIdentifier(sourceTransactionIdentifier);
                                order.setSourceTransactionSystem(sourceTransactionSystem);
                                order.setSourceTransactionNumber(sourceTransactionNumber);
                                order.setBuyingPartyId(buyingPartyId);
                                order.setBuyingPartyName(buyingPartyName);
                                if (!transactionalCurrencyCode.isNil()) {
                                    order.setTransactionalCurrencyCode(transactionalCurrencyCode);
                                }
                                order.setTransactionOn(transactionOn);
                                order.setRequestingBusinessUnitIdentifier(requestingBusinessUnitIdentifier);
                                if ("1401".equals(shipFromOrganization)) {
                                    order.setTransactionTypeCode(transactionTypeCode);
                                }
                                order.setRequestingLegalUnitIdentifier(requestingLegalUnitIdentifier);
                                order.setPartialShipAllowedFlag(partialShipAllowedFlag);
                                //order.setFreezePriceFlag(freezePriceFlag);
                                order.setShipToPartyIdentifier(shipToPartyIdentifier);
                                order.setShipToPartyName(shipToPartyName);
                                order.setShipToPartyNumber(shipToPartyNumber);
                                order.setShipToPartySiteIdentifier(shipToPartySiteIdentifier);
                                if (!shipToAddress1.isNil()) {
                                    order.setShipToAddress1(shipToAddress1);
                                }
                                if (!shipToAddress2.isNil()) {
                                    order.setShipToAddress2(shipToAddress2);
                                }
                                if (!shipToAddress3.isNil()) {
                                    order.setShipToAddress3(shipToAddress3);
                                }
                                if (!shipToAddress4.isNil()) {
                                    order.setShipToAddress4(shipToAddress4);
                                }
                                if (!shipToCity.isNil()) {
                                    order.setShipToCity(shipToCity);
                                }
                                if (!shipToPostalCode.isNil()) {
                                    order.setShipToPostalCode(shipToPostalCode);
                                }
                                if (!shipToState.isNil()) {
                                    order.setShipToState(shipToState);
                                }
                                if (!shipToProvince.isNil()) {
                                    order.setShipToProvince(shipToProvince);
                                }
                                if (!shipToCounty.isNil()) {
                                    order.setShipToCounty(shipToCounty);
                                }
                                if (!shipToCountry.isNil()) {
                                    order.setShipToCountry(shipToCountry);
                                }
                                order.setBillToCustomerName(billToCustomerName);
                                order.setBillToCustomerNumber(billToCustomerNumber);
                                order.setBillToAccountSiteUseIdentifier(billToAccountSiteUseIdentifier);
                                if (!billToAddress1.isNil()) {
                                    order.setBillToAddress1(billToAddress1);
                                }
                                if (!billToAddress2.isNil()) {
                                    order.setBillToAddress2(billToAddress2);
                                }
                                if (!billToAddress3.isNil()) {
                                    order.setBillToAddress3(billToAddress3);
                                }
                                if (!billToAddress4.isNil()) {
                                    order.setBillToAddress4(billToAddress4);
                                }
                                if (!billToCity.isNil()) {
                                    order.setBillToCity(billToCity);
                                }
                                if (!billToPostalCode.isNil()) {
                                    order.setBillToPostalCode(billToPostalCode);
                                }
                                if (!billToState.isNil()) {
                                    order.setBillToState(billToState);
                                }
                                if (!billToProvince.isNil()) {
                                    order.setBillToProvince(billToProvince);
                                }
                                if (!billToCounty.isNil()) {
                                    order.setBillToCounty(billToCounty);
                                }
                                if (!billToCountry.isNil()) {
                                    order.setBillToCountry(billToCountry);
                                }
                                order.setCustomerPONumber(customerPONumber);
                                //order.setSalesAgreementNumber(salesAgreementNumber);
                                //order.setSalesAgreementHeaderIdentifier(salesAgreementHeaderIdentifier);

                                //Order Line Information
                                List<OrderLine> lineList = new ArrayList<OrderLine>();
                                Statement st13 = null;
                                String itemNumber = null;
                                String itemId = null;
                                String uomCode = null;
                                String lineCurrCode = null;
                                String itemType = null;
                                String internalPO = null;
                                //int selRows = 0;
                                int lineNum = 0;
                                //if (vo.getRowCount() > 0) {
                                //    Row curRow = vo.first();
                                //    for (i = 0; i < vo.getRowCount(); i++) {
                                //        if (curRow.getAttribute("SelectFlag") != null) {
                                //            if (curRow.getAttribute("SelectFlag")
                                //                      .toString()
                                //                      .compareTo("true") == 0) {
                                //itemNumber = null;
                                //itemId = null;
                                //uomCode = null;
                                //lineCurrCode = null;
                                //                 selRows++;
                                //                 if (selRows == 1) {
                                CallableStatement csPriceOverride =
                                    appM.getDBTransaction()
                                    .createCallableStatement(" BEGIN" + " CEIX_GET_PRICE_OVERRIDE (:1,:2,:3,:4);" +
                                                             " END;", 0);
                                csPriceOverride.setString(1, contractId);
                                csPriceOverride.setString(2, headItemNumber);
                                csPriceOverride.setString(3, dumpDate);
                                csPriceOverride.registerOutParameter(4, Types.VARCHAR);
                                boolean result = csPriceOverride.execute();

                                String priceOverride = csPriceOverride.getString(4);
                                ResultSet rs = null;
                                try {
                                    String sql =
                                        " select item_number,item_id,uom_code,currency_code,item_type,internal_order,price " +
                                        "      from ( " +
                                        " select ccl.item_number,ccl.object1_id1 item_id,ccl.uom_code,ccl.currency_code,ccl.item_type,ccl.internal_order,ccl.price " +
                                        " from ceix_contract_lines ccl WHERE ccl.chr_id =" + contractId +
                                        " and ccl.ITEM_NUMBER = NVL('" + headItemNumber + "' ,ccl.ITEM_NUMBER) " +
                                        " and ccl.item_type<>'FRT' " +
                                        " and ccl.sts_code in ('ACTIVE','EXPIRED') and TO_DATE('" + dumpDate +
                                        "','yyyy-MM-dd') between nvl(ccl.start_date,TO_DATE('" + dumpDate +
                                        "','yyyy-MM-dd')-1) and nvl(ccl.end_date,TO_DATE('" + dumpDate +
                                        "','yyyy-MM-dd')+1) " + " union " +
                                        " select ccl.item_number,ccl.object1_id1 item_id,ccl.uom_code,ccl.currency_code,ccl.item_type,ccl.internal_order,ccl.price " +
                                        " from ceix_contract_lines ccl WHERE ccl.chr_id = " + contractId +
                                        " and ccl.item_type='FRT' and ccl.sts_code in ('ACTIVE','EXPIRED') and TO_DATE('" +
                                        dumpDate + "','yyyy-MM-dd') between nvl(ccl.start_date,TO_DATE('" + dumpDate +
                                        "','yyyy-MM-dd')-1) and nvl(ccl.end_date,TO_DATE('" + dumpDate +
                                        "','yyyy-MM-dd')+1))";

                                    st13 = appM.getDBTransaction().createStatement(50);
                                    rs = st13.executeQuery(sql);

                                    while (rs.next()) {
                                        itemNumber = rs.getString(1);
                                        itemId = rs.getString(2);
                                        uomCode = rs.getString(3);
                                        lineCurrCode = rs.getString(4);
                                        itemType = rs.getString(5);
                                        if (!("FRT").equals(itemType)) {
                                            internalPO = rs.getString(6);
                                        }

                                        //if (("FRT").equals(itemType) &&
                                        //    ("Freight Revenue-Outside").equals(itemNumber)) {
                                        price = rs.getString(7);
                                        //}

                                        //}

                                        // For Drop Ship Order do not insert Drock Revenue Items
                                        if ("SALES_ORDER".equals(orderType) ||
                                            (!"Dock Revenue-General".equals(itemNumber) &&
                                             !"Dock Revenue".equals(itemNumber))) 
											 {
                                            //}
                                            //Ordered  Quantity
                                            MeasureType measureType = new MeasureType();
                                            measureType.setUnitCode(uomCode);
                                            //measureType.setValue(new BigDecimal(curRow.getAttribute("WeightInTons").toString()));
                                            measureType.setValue(totalweight);
                                            GregorianCalendar cal1 = new GregorianCalendar();
                                            XMLGregorianCalendar reqDate = null;
                                            //requestedShipDate
                                            try {
                                                //cal.setTime(cal.getTime());
                                                //DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
                                                //transDate = datatypeFactory.newXMLGregorianCalendar(cal);

                                                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                                                df.setTimeZone(TimeZone.getTimeZone("America/New_York"));
                                                Date date1 = df.parse(dumpDate);
                                                cal1.setTime(date1);
                                                DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
                                                reqDate = datatypeFactory.newXMLGregorianCalendar(cal1);
                                                //System.out.println("The date is :"+reqDate);
                                            } catch (ParseException e) {
                                                //System.out.println("Error: Invalid Date Parser Exception.");
                                                e.printStackTrace();
                                            } catch (Exception e) {
                                                //System.out.println("Error: Exception.");
                                                e.printStackTrace();
                                            }

                                            lineNum = lineNum + 1;
                                            JAXBElement<String> sourceTransactionLineIdentifier =
                                                OrdObjectFactory.createOrderLineSourceTransactionLineIdentifier(String.valueOf(lineNum));
                                            JAXBElement<String> sourceTransactionScheduleIdentifier =
                                                OrdObjectFactory.createOrderLineSourceTransactionScheduleIdentifier("1");
                                            JAXBElement<String> sourceTransactionLineNumber =
                                                OrdObjectFactory.createOrderLineSourceTransactionLineNumber(String.valueOf(lineNum));
                                            JAXBElement<String> sourceTransactionScheduleNumber =
                                                OrdObjectFactory.createOrderLineSourceTransactionScheduleNumber("1");
                                            JAXBElement<Long> productIdentifier =
                                                OrdObjectFactory.createOrderLineProductIdentifier(Long.valueOf(itemId));
                                            JAXBElement<String> productNumber =
                                                OrdObjectFactory.createOrderLineProductNumber(itemNumber);
                                            JAXBElement<MeasureType> orderedQuantity =
                                                OrdObjectFactory.createOrderLineOrderedQuantity(measureType);
                                            JAXBElement<String> orderedUOMCode =
                                                OrdObjectFactory.createOrderLineOrderedUOMCode(uomCode);
                                            // JAXBElement<Long> requestedFulfillmentOrganizationIdentifier =
                                            //    OrdObjectFactory.createOrderLineRequestedFulfillmentOrganizationIdentifier(Long.valueOf("300000006777510"));
                                            JAXBElement<String> requestedFulfillmentOrganizationCode =
                                                OrdObjectFactory.createOrderLineRequestedFulfillmentOrganizationCode(shipFromOrganization);
                                            JAXBElement<XMLGregorianCalendar> requestedShipDate =
                                                OrdObjectFactory.createOrderLineRequestedShipDate(reqDate);
                                            JAXBElement<String> paymentTermsCode =
                                                OrdObjectFactory.createOrderLinePaymentTermsCode(paymentTermCode);
                                            JAXBElement<String> transactionCategoryCode =
                                                OrdObjectFactory.createOrderLineTransactionCategoryCode("ORDER");
                                            JAXBElement<Boolean> line_partialShipAllowedFlag =
                                                OrdObjectFactory.createOrderLinePartialShipAllowedFlag(true);
                                            JAXBElement<String> taxExempt =
                                                OrdObjectFactory.createOrderLineTaxExempt("S");
                                            JAXBElement<String> fobPointCode =
                                                OrdObjectFactory.createOrderLineFOBPointCode(fob);
                                            //JAXBElement<String> lineType =
                                            //    OrdObjectFactory.createOrderLineTransactionLineType("Freight and Revenue");

                                            OrderLine line = new OrderLine();
                                            line.setSourceTransactionLineIdentifier(sourceTransactionLineIdentifier);
                                            line.setSourceTransactionScheduleIdentifier(sourceTransactionScheduleIdentifier);
                                            line.setSourceTransactionLineNumber(sourceTransactionLineNumber);
                                            line.setSourceTransactionScheduleNumber(sourceTransactionScheduleNumber);
                                            line.setProductIdentifier(productIdentifier);
                                            line.setProductNumber(productNumber);
                                            line.setOrderedQuantity(orderedQuantity);
                                            if (!orderedUOMCode.isNil()) {
                                                line.setOrderedUOMCode(orderedUOMCode);
                                            }
                                            //Overriding Frieght Price from Shipments screen
                                            /*if (("FRT").equals(itemType) &&
                                                ("Freight Revenue-Outside").equals(itemNumber)) {
                                                AmountType amtType = new AmountType();
                                                amtType.setCurrencyCode("USD");
                                                amtType.setValue(new BigDecimal(headFreight));
                                                JAXBElement<AmountType> amount =
                                                    OrdObjectFactory.createOrderLineUnitSellingPrice(amtType);
                                                line.setUnitSellingPrice(amount);
                                            }*/
                                            if (!("FRT").equals(itemType) && !("DROP_SHIP").equals(orderType)) {
                                                line.setRequestedFulfillmentOrganizationCode(requestedFulfillmentOrganizationCode);
                                            }
                                            //if (("FRT").equals(itemType)) {
                                            //    line.setTransactionLineType(lineType);
                                            //}
                                            line.setRequestedShipDate(requestedShipDate);
                                            if (!paymentTermsCode.isNil()) {
                                                line.setPaymentTermsCode(paymentTermsCode);
                                            }
                                            line.setTransactionCategoryCode(transactionCategoryCode);
                                            line.setPartialShipAllowedFlag(line_partialShipAllowedFlag);
                                            line.setTaxExempt(taxExempt);
                                            line.setShipToPartyIdentifier(shipToPartyIdentifier);
                                            line.setShipToPartyName(shipToPartyName);
                                            line.setShipToPartyNumber(shipToPartyNumber);
                                            line.setShipToPartySiteIdentifier(shipToPartySiteIdentifier);
                                            if (!shipToAddress1.isNil()) {
                                                line.setShipToAddress1(shipToAddress1);
                                            }
                                            if (!shipToAddress2.isNil()) {
                                                line.setShipToAddress2(shipToAddress2);
                                            }
                                            if (!shipToAddress3.isNil()) {
                                                line.setShipToAddress3(shipToAddress3);
                                            }
                                            if (!shipToAddress4.isNil()) {
                                                line.setShipToAddress4(shipToAddress4);
                                            }
                                            if (!shipToCity.isNil()) {
                                                line.setShipToCity(shipToCity);
                                            }
                                            if (!shipToPostalCode.isNil()) {
                                                line.setShipToPostalCode(shipToPostalCode);
                                            }
                                            if (!shipToState.isNil()) {
                                                line.setShipToState(shipToState);
                                            }
                                            if (!shipToProvince.isNil()) {
                                                line.setShipToProvince(shipToProvince);
                                            }
                                            if (!shipToCounty.isNil()) {
                                                line.setShipToCounty(shipToCounty);
                                            }
                                            if (!shipToCountry.isNil()) {
                                                line.setShipToCountry(shipToCountry);
                                            }
                                            line.setBillToCustomerName(billToCustomerName);
                                            line.setBillToCustomerNumber(billToCustomerNumber);
                                            line.setBillToAccountSiteUseIdentifier(billToAccountSiteUseIdentifier);
                                            if (!billToAddress1.isNil()) {
                                                line.setBillToAddress1(billToAddress1);
                                            }
                                            if (!billToAddress2.isNil()) {
                                                line.setBillToAddress2(billToAddress2);
                                            }
                                            if (!billToAddress3.isNil()) {
                                                line.setBillToAddress3(billToAddress3);
                                            }
                                            if (!billToAddress4.isNil()) {
                                                line.setBillToAddress4(billToAddress4);
                                            }
                                            if (!billToCity.isNil()) {
                                                line.setBillToCity(billToCity);
                                            }
                                            if (!billToPostalCode.isNil()) {
                                                line.setBillToPostalCode(billToPostalCode);
                                            }
                                            if (!billToState.isNil()) {
                                                line.setBillToState(billToState);
                                            }
                                            if (!billToProvince.isNil()) {
                                                line.setBillToProvince(billToProvince);
                                            }
                                            if (!billToCounty.isNil()) {
                                                line.setBillToCounty(billToCounty);
                                            }
                                            if (!billToCountry.isNil()) {
                                                line.setBillToCountry(billToCountry);
                                            }
                                            //line.setSalesAgreementNumber(salesAgreementNumber);

                                            line.setFOBPointCode(fobPointCode);
                                            line.setCustomerPONumber(customerPONumber);

                                            if (!"Y".equals(priceOverride)) {
                                                line.setSalesAgreementHeaderIdentifier(salesAgreementHeaderIdentifier);
                                            }

                                            //If  there is difference in price in sales agreement from load date to sysdate then override the price
                                            if ("Y".equals(priceOverride)) {
                                                JAXBElement<String> chargeDefCode =
                                                    OrdObjectFactory.createChargeChargeDefinitionCode("QP_SALE_PRICE");
                                                JAXBElement<String> chargeSubTypeCode =
                                                    OrdObjectFactory.createChargeChargeSubtypeCode("ORA_PRICE");
                                                JAXBElement<String> priceTypeCode =
                                                    OrdObjectFactory.createChargePriceTypeCode("ONE_TIME");
                                                JAXBElement<String> applyTo =
                                                    OrdObjectFactory.createChargeApplyTo("PRICE");
                                                JAXBElement<Boolean> primaryFlag =
                                                    OrdObjectFactory.createChargePrimaryFlag(true);
                                                JAXBElement<Boolean> rollupFlag =
                                                    OrdObjectFactory.createChargeRollupFlag(false);
                                                JAXBElement<String> srcChargeIdentifier =
                                                    OrdObjectFactory.createChargeSourceChargeIdentifier("SC1");
                                                JAXBElement<String> chargeTypeCode =
                                                    OrdObjectFactory.createChargeChargeTypeCode("ORA_SALE");
                                                JAXBElement<String> ChargeCurrencyCode =
                                                    OrdObjectFactory.createChargeChargeCurrencyCode("USD");
                                                JAXBElement<Integer> sequenceNum =
                                                    OrdObjectFactory.createChargeSequenceNumber(1);

                                                Charge ordChargeUpdt = new Charge();
                                                ordChargeUpdt.setChargeDefinitionCode(chargeDefCode);
                                                ordChargeUpdt.setChargeSubtypeCode(chargeSubTypeCode);
                                                ordChargeUpdt.setPriceTypeCode(priceTypeCode);
                                                ordChargeUpdt.setPricedQuantity(orderedQuantity);
                                                ordChargeUpdt.setPricedQuantityUOMCode(orderedUOMCode);
                                                ordChargeUpdt.setPrimaryFlag(primaryFlag);
                                                ordChargeUpdt.setApplyTo(applyTo);
                                                ordChargeUpdt.setRollupFlag(rollupFlag);
                                                ordChargeUpdt.setSourceChargeIdentifier(srcChargeIdentifier);
                                                ordChargeUpdt.setChargeTypeCode(chargeTypeCode);
                                                ordChargeUpdt.setChargeCurrencyCode(ChargeCurrencyCode);
                                                ordChargeUpdt.setSequenceNumber(sequenceNum);

                                                JAXBElement<String> compChargeCurrCode =
                                                    OrdObjectFactory.createChargeComponentChargeCurrencyCode("USD");
                                                JAXBElement<String> compHdrCurrencyCode =
                                                    OrdObjectFactory.createChargeComponentHeaderCurrencyCode("USD");
                                                AmountType amtType = new AmountType();
                                                amtType.setValue(new BigDecimal("0"));
                                                JAXBElement<AmountType> listhdrExtAmount =
                                                    OrdObjectFactory.createChargeComponentHeaderCurrencyExtendedAmount(amtType);
                                                JAXBElement<String> listPriceElement =
                                                    OrdObjectFactory.createChargeComponentPriceElementCode("QP_LIST_PRICE");
                                                JAXBElement<Integer> listSeqNum =
                                                    OrdObjectFactory.createChargeComponentSequenceNumber(1);
                                                JAXBElement<String> listPriceUsage =
                                                    OrdObjectFactory.createChargeComponentPriceElementUsageCode("LIST_PRICE");
                                                JAXBElement<String> listSrcCompIdentifier =
                                                    OrdObjectFactory.createChargeComponentSourceChargeComponentIdentifier("SCC1");
                                                ChargeComponent compUpdate = new ChargeComponent();
                                                compUpdate.setChargeCurrencyCode(compChargeCurrCode);
                                                compUpdate.setHeaderCurrencyCode(compHdrCurrencyCode);
                                                compUpdate.setHeaderCurrencyExtendedAmount(listhdrExtAmount);
                                                compUpdate.setPriceElementCode(listPriceElement);
                                                compUpdate.setSequenceNumber(listSeqNum);
                                                compUpdate.setPriceElementUsageCode(listPriceUsage);
                                                compUpdate.setChargeCurrencyUnitPrice(listhdrExtAmount);
                                                compUpdate.setHeaderCurrencyUnitPrice(listhdrExtAmount);
                                                compUpdate.setRollupFlag(rollupFlag);
                                                compUpdate.setSourceChargeIdentifier(srcChargeIdentifier);
                                                compUpdate.setSourceChargeComponentIdentifier(listSrcCompIdentifier);


                                                BigDecimal extAmt =
                                                    new BigDecimal(totalweight.toString())
                                                    .multiply(new BigDecimal(price));

                                                AmountType amtType2 = new AmountType();
                                                amtType2.setValue(extAmt);

                                                AmountType pricetype = new AmountType();
                                                pricetype.setValue(new BigDecimal(price));

                                                JAXBElement<AmountType> adjhdrExtAmount =
                                                    OrdObjectFactory.createChargeComponentHeaderCurrencyExtendedAmount(amtType2);
                                                JAXBElement<String> adjPriceElement =
                                                    OrdObjectFactory.createChargeComponentPriceElementCode("QP_CUSTOM_ADJUSTMENT");
                                                JAXBElement<Integer> adjSeqNum =
                                                    OrdObjectFactory.createChargeComponentSequenceNumber(2);
                                                JAXBElement<String> adjPriceUsage =
                                                    OrdObjectFactory.createChargeComponentPriceElementUsageCode("PRICE_ADJUSTMENT");
                                                JAXBElement<String> adjSrcCompIdentifier =
                                                    OrdObjectFactory.createChargeComponentSourceChargeComponentIdentifier("SCC2");
                                                JAXBElement<AmountType> adjUnitPrice =
                                                    OrdObjectFactory.createChargeComponentChargeCurrencyUnitPrice(pricetype);

                                                ChargeComponent compUpdate2 = new ChargeComponent();
                                                compUpdate2.setChargeCurrencyCode(compChargeCurrCode);
                                                compUpdate2.setHeaderCurrencyCode(compHdrCurrencyCode);
                                                compUpdate2.setHeaderCurrencyExtendedAmount(adjhdrExtAmount);
                                                compUpdate2.setPriceElementCode(adjPriceElement);
                                                compUpdate2.setSequenceNumber(adjSeqNum);
                                                compUpdate2.setPriceElementUsageCode(adjPriceUsage);
                                                compUpdate2.setChargeCurrencyUnitPrice(adjUnitPrice);
                                                compUpdate2.setHeaderCurrencyUnitPrice(adjUnitPrice);
                                                compUpdate2.setRollupFlag(rollupFlag);
                                                compUpdate2.setSourceChargeIdentifier(srcChargeIdentifier);
                                                compUpdate2.setSourceChargeComponentIdentifier(adjSrcCompIdentifier);

                                                JAXBElement<String> netPriceElement =
                                                    OrdObjectFactory.createChargeComponentPriceElementCode("QP_NET_PRICE");
                                                JAXBElement<Integer> netSeqNum =
                                                    OrdObjectFactory.createChargeComponentSequenceNumber(3);
                                                JAXBElement<String> netPriceUsage =
                                                    OrdObjectFactory.createChargeComponentPriceElementUsageCode("NET_PRICE");
                                                JAXBElement<String> netSrcCompIdentifier =
                                                    OrdObjectFactory.createChargeComponentSourceChargeComponentIdentifier("SCC3");

                                                ChargeComponent compUpdate3 = new ChargeComponent();
                                                compUpdate3.setChargeCurrencyCode(compChargeCurrCode);
                                                compUpdate3.setHeaderCurrencyCode(compHdrCurrencyCode);
                                                compUpdate3.setHeaderCurrencyExtendedAmount(adjhdrExtAmount);
                                                compUpdate3.setPriceElementCode(netPriceElement);
                                                compUpdate3.setSequenceNumber(netSeqNum);
                                                compUpdate3.setPriceElementUsageCode(netPriceUsage);
                                                compUpdate3.setChargeCurrencyUnitPrice(adjUnitPrice);
                                                compUpdate3.setHeaderCurrencyUnitPrice(adjUnitPrice);
                                                compUpdate3.setRollupFlag(rollupFlag);
                                                compUpdate3.setSourceChargeIdentifier(srcChargeIdentifier);
                                                compUpdate3.setSourceChargeComponentIdentifier(netSrcCompIdentifier);
                                                ordChargeUpdt.getOrderChargeComponent().add(compUpdate);
                                                ordChargeUpdt.getOrderChargeComponent().add(compUpdate2);
                                                ordChargeUpdt.getOrderChargeComponent().add(compUpdate3);
                                                line.getOrderCharge().add(ordChargeUpdt);
                                            }

                                            lineList.add(line);
                                        }
                                    }
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                } finally {
                                    try {
                                        rs.close();
                                        st13.close();
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                }
                                //}
                                //}
                                //curRow = vo.next();
                                //}
                                //}

                                order.getLine().addAll(lineList);

                                //Order Preference
                                JAXBElement<Boolean> submitFlag =
                                    OrdObjectFactory.createOrderProcessingPreferencesSubmitFlag(true);
                                JAXBElement<String> creationMode =
                                    OrdObjectFactory.createOrderProcessingPreferencesCreationMode("I");
                                OrderProcessingPreferences orderPreference = new OrderProcessingPreferences();
                                orderPreference.setSubmitFlag(submitFlag);
                                orderPreference.setCreationMode(creationMode);
                                order.setOrderPreferences(orderPreference);

                                if (!"Y".equals(priceOverride)) {
                                    order.setFreezePriceFlag(freezePriceFlag);
                                }

                                //Header DFF Information
                                com.oracle.xmlns.apps.scm.doo.processorder.flex.headercontextsb.ObjectFactory OrdEFFCtxObjectFactory =
                                    new com.oracle.xmlns.apps.scm.doo.processorder.flex.headercontextsb.ObjectFactory();
                                JAXBElement<String> createContractInformationContractNumber =
                                    OrdEFFCtxObjectFactory.createContractInformationContractNumber(orderNumber);
                                JAXBElement<String> createContractInformationContractDesc = null;
                                if (headAgreementName != null) {
                                    createContractInformationContractDesc =
                                        OrdEFFCtxObjectFactory.createContractInformationContractDescription(headAgreementName.substring(0,
                                                                                                                                        Math.min(headAgreementName.length(),
                                                                                                                                                 150)));
                                }
                                JAXBElement<String> createMineLocationCoalType =
                                    OrdEFFCtxObjectFactory.createMineLocationCoalType(coalType);
                                XMLGregorianCalendar contStartDate = null;
                                if (conStartDate != null) {
                                    GregorianCalendar cal2 = new GregorianCalendar();
                                    try {
                                        cal2.setTime(conStartDate);
                                        DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
                                        contStartDate = datatypeFactory.newXMLGregorianCalendar(cal2);
                                    } catch (Exception e) {
                                        //System.out.println("Error: Exception.");
                                        e.printStackTrace();
                                    }
                                }
                                XMLGregorianCalendar contEndDate = null;
                                if (conEndDate != null) {
                                    GregorianCalendar cal3 = new GregorianCalendar();
                                    try {
                                        cal3.setTime(conEndDate);
                                        DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
                                        contEndDate = datatypeFactory.newXMLGregorianCalendar(cal3);
                                    } catch (Exception e) {
                                        //System.out.println("Error: Exception.");
                                        e.printStackTrace();
                                    }
                                }

                                InternalOrders internalOrder = new InternalOrders();
                                JAXBElement<String> internalOrderNumber =
                                    OrdEFFCtxObjectFactory.createInternalOrdersInternalOrderNumber(internalPO);
                                JAXBElement<String> internalOrderType =
                                    OrdEFFCtxObjectFactory.createInternalOrdersInternalOrderType("Purchase Order");
                                internalOrder.setContextCode("Internal Orders");
                                internalOrder.setInternalOrderNumber(internalOrderNumber);
                                internalOrder.setInternalOrderType(internalOrderType);

                                AcceptableWeights actweight = new AcceptableWeights();
                                JAXBElement<String> acctWeight =
                                    OrdEFFCtxObjectFactory.createAcceptableWeightsWeights(acceptWeight);
                                actweight.setContextCode("Acceptable Weights");
                                actweight.setWeights(acctWeight);
                                ContractInformation con = new ContractInformation();
                                JAXBElement<XMLGregorianCalendar> contractStartDate =
                                    OrdEFFCtxObjectFactory.createContractInformationContractStartDate(contStartDate);
                                JAXBElement<XMLGregorianCalendar> contractEndDate =
                                    OrdEFFCtxObjectFactory.createContractInformationContractEndDate(contEndDate);
                                con.setContextCode("Contract Information");
                                con.setContractNumber(createContractInformationContractNumber);
                                con.setContractDescription(createContractInformationContractDesc);
                                con.setContractStartDate(contractStartDate);
                                con.setContractEndDate(contractEndDate);
                                CustomerDestination custDest = new CustomerDestination();
                                JAXBElement<String> destinationCountry =
                                    OrdEFFCtxObjectFactory.createCustomerDestinationDestinationCountry(destCountry);
                                JAXBElement<String> domesticExport =
                                    OrdEFFCtxObjectFactory.createCustomerDestinationDomesticOrExport(domExport);
                                custDest.setContextCode("Customer Destination");
                                custDest.setDestinationCountry(destinationCountry);
                                custDest.setDomesticOrExport(domesticExport);
                                MineLocation mine = new MineLocation();
                                mine.setContextCode("Mine Location");
                                if (!createMineLocationCoalType.isNil()) {
                                    mine.setCoalType(createMineLocationCoalType);
                                }
                                CustomerPoNumber poNumber = new CustomerPoNumber();
                                poNumber.setContextCode("Customer PO Number");
                                if (custPO != null) {
                                    poNumber.setPoNumber(custPO);
                                }
                                Quality qual = new Quality();
                                qual.setContextCode("Quality");
                                JAXBElement<String> prev_Sample =
                                    OrdEFFCtxObjectFactory.createQualityPrevailingSample(prevSample);
                                JAXBElement<String> prev_Analysis =
                                    OrdEFFCtxObjectFactory.createQualityPrevailingAnalysis(prevAnalysis);
                                if (!prev_Sample.isNil()) {
                                    qual.setPrevailingSample(prev_Sample);
                                }
                                if (!prev_Analysis.isNil()) {
                                    qual.setPrevailingAnalysis(prev_Analysis);
                                }
                                ShippingInformation shipInfo = new ShippingInformation();
                                shipInfo.setContextCode("Shipping Information");
                                XMLGregorianCalendar reqLdDate = null;
                                if (reqLDate != null) {
                                    GregorianCalendar cal4 = new GregorianCalendar();
                                    try {
                                        cal4.setTime(reqLDate);
                                        DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
                                        reqLdDate = datatypeFactory.newXMLGregorianCalendar(cal4);
                                    } catch (Exception e) {
                                        //System.out.println("Error: Exception.");
                                        e.printStackTrace();
                                    }
                                }
                                XMLGregorianCalendar loadStartDt = null;
                                if (loadStDate != null) {
                                    GregorianCalendar cal5 = new GregorianCalendar();
                                    try {
                                        cal5.setTime(loadStDate);
                                        DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
                                        loadStartDt = datatypeFactory.newXMLGregorianCalendar(cal5);
                                    } catch (Exception e) {
                                        //System.out.println("Error: Exception.");
                                        e.printStackTrace();
                                    }
                                }
                                XMLGregorianCalendar loadEndDt = null;
                                if (loadEnDate != null) {
                                    GregorianCalendar cal6 = new GregorianCalendar();
                                    try {
                                        cal6.setTime(loadEnDate);
                                        DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
                                        loadEndDt = datatypeFactory.newXMLGregorianCalendar(cal6);
                                    } catch (Exception e) {
                                        //System.out.println("Error: Exception.");
                                        e.printStackTrace();
                                    }
                                }
                                XMLGregorianCalendar shipDt = null;
                                if (shipLDate != null) {
                                    GregorianCalendar cal7 = new GregorianCalendar();
                                    try {
                                        cal7.setTime(shipLDate);
                                        DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
                                        shipDt = datatypeFactory.newXMLGregorianCalendar(cal7);
                                    } catch (Exception e) {
                                        //System.out.println("Error: Exception.");
                                        e.printStackTrace();
                                    }
                                }
                                XMLGregorianCalendar dumpDt = null;
                                if (dumpLDate != null) {
                                    GregorianCalendar cal8 = new GregorianCalendar();
                                    try {
                                        cal8.setTime(dumpLDate);
                                        DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
                                        dumpDt = datatypeFactory.newXMLGregorianCalendar(cal8);
                                    } catch (Exception e) {
                                        //System.out.println("Error: Exception.");
                                        e.printStackTrace();
                                    }
                                }
                                JAXBElement<String> trainNumberEffs =
                                    OrdEFFCtxObjectFactory.createShippingInformationTrainNumber(trainNumberEff);
                                //JAXBElement<String> routingInfoEffs =
                                //    OrdEFFCtxObjectFactory.createShippingInformationRoutingInformation(routingInfoEff);
                                //JAXBElement<String> qualityInfoEffs =
                                //    OrdEFFCtxObjectFactory.createShippingInformationQualityInformation(qualityInfoEff);
                                //JAXBElement<String> shipperIdNumEffs =
                                //    OrdEFFCtxObjectFactory.createShippingInformationShipperIdNumber(shipperIdNumEff);
                                JAXBElement<String> classFlexEffs =
                                    OrdEFFCtxObjectFactory.createShippingInformationClassField(classFlexEff);
                                JAXBElement<BigDecimal> totalWeightEffs =
                                    OrdEFFCtxObjectFactory.createShippingInformationPaasShipQuantity(totalweight);
                                JAXBElement<XMLGregorianCalendar> requestedLoadDate =
                                    OrdEFFCtxObjectFactory.createShippingInformationRequestedLoadDate(reqLdDate);
                                JAXBElement<XMLGregorianCalendar> loadStartDate =
                                    OrdEFFCtxObjectFactory.createShippingInformationLoadingBegan(loadStartDt);
                                JAXBElement<XMLGregorianCalendar> loadEndDate =
                                    OrdEFFCtxObjectFactory.createShippingInformationLoadingCompleted(loadEndDt);
                                JAXBElement<XMLGregorianCalendar> shipDate =
                                    OrdEFFCtxObjectFactory.createShippingInformationLoadingCompleted(shipDt);
                                JAXBElement<XMLGregorianCalendar> dumpedDate =
                                    OrdEFFCtxObjectFactory.createShippingInformationLoadingCompleted(dumpDt);
                                JAXBElement<String> straggler =
                                    OrdEFFCtxObjectFactory.createShippingInformationStragglers(headStraggler);
                                if (!trainNumberEffs.isNil()) {
                                    shipInfo.setTrainNumber(trainNumberEffs);
                                }
                                //if (!routingInfoEffs.isNil()) {
                                //    shipInfo.setRoutingInformation(routingInfoEffs);
                                //}
                                //if (!qualityInfoEffs.isNil()) {
                                //    shipInfo.setQualityInformation(qualityInfoEffs);
                                //}
                                //if (!shipperIdNumEffs.isNil()) {
                                //     shipInfo.setShipperIdNumber(shipperIdNumEffs);
                                // }
                                if (!classFlexEffs.isNil()) {
                                    shipInfo.setClassField(classFlexEffs);
                                }
                                if (!totalWeightEffs.isNil()) {
                                    shipInfo.setPaasShipQuantity(totalWeightEffs);
                                }
                                shipInfo.setRequestedLoadDate(requestedLoadDate);
                                shipInfo.setLoadingBegan(loadStartDate);
                                shipInfo.setLoadingCompleted(loadEndDate);
                       //         shipInfo.setShipDate(shipDate);
                     //           shipInfo.setDumpDate(dumpedDate);
                                shipInfo.setStragglers(straggler);


                                HeaderEffCategories headerEff = new HeaderEffCategories();
                                com.oracle.xmlns.apps.scm.doo.processorder.model.ObjectFactory OrdEFFObjectFactory =
                                    new com.oracle.xmlns.apps.scm.doo.processorder.model.ObjectFactory();
                                JAXBElement<String> category =
                                    OrdEFFObjectFactory.createHeaderEffCategoriesCategory("DOO_HEADERS_ADD_INFO");
                                headerEff.setCategory(category);

                                JHeaderEffDooHeadersAddInfoprivate jf = new JHeaderEffDooHeadersAddInfoprivate();
                                jf.setHeaderEffBContractInformationprivateVO(con);
                                jf.setHeaderEffBCustomerDestinationprivateVO(custDest);
                                jf.setHeaderEffBMineLocationprivateVO(mine);
                                jf.getHeaderEffBCustomerPONumberprivateVO().add(poNumber);
                                jf.setCategory(category);
                                jf.setHeaderEffBQualityprivateVO(qual);
                                jf.setHeaderEffBShippingInformationprivateVO(shipInfo);
                                jf.setHeaderEffBAcceptableWeightsprivateVO(actweight);
                                jf.setHeaderEffBInternalOrdersprivateVO(internalOrder);

                                order.getAdditionalHeaderInformationCategories().add(jf);

                                List<Order> OrderList = new ArrayList<Order>();
                                OrderList.add(order);
                                OrderImportDtls.getOrder().addAll(OrderList);
                                orderImportResponse = appM.callorderImportImpl(OrderImportDtls);
                                if (orderImportResponse.getOrderNumber() != null) {
                                    soNumber = orderImportResponse.getOrderNumber().getValue();
                                } else {
                                    soNumber = null;
                                }
                                if (orderImportResponse.getReturnStatus() != null) {
                                    retStatus = orderImportResponse.getReturnStatus().getValue();
                                } else {
                                    retStatus = "ERROR";
                                }
                            }
                            if (soNumber == null || !("SUCCESS".equals(retStatus))) {
                                logMessage.append("<p><b>Error occurred while creating Sales Order: " + soNumber + " " +
                                                  orderImportResponse.getMessageText().getValue() + "</b></p>");
                                logMessage.append("</body></html>");
                                FacesMessage message =
                                    new FacesMessage(FacesMessage.SEVERITY_ERROR, logMessage.toString(), null);
                                context.addMessage("Error", message);
                                processShipFlag = "CF";
                            } else {
                                //if (soNumber != null) {
                                if (vo.getRowCount() > 0) {
                                    Row curRow1 = vo.first();
                                    for (i = 0; i < vo.getRowCount(); i++) {
                                        if (curRow1.getAttribute("SelectFlag") != null) {
                                            if (curRow1.getAttribute("SelectFlag")
                                                       .toString()
                                                       .compareTo("true") == 0) {
                                                curRow1.setAttribute("SONUMBER", soNumber);
                                                curRow1.setAttribute("ItemNumber", headItemNumber);
                                                curRow1.setAttribute("Destination", headDestination);
                                                curRow1.setAttribute("LoadOrigin", headLoadOrigin);
                                                curRow1.setAttribute("Straggler", headStraggler);
                                                curRow1.setAttribute("Freight", headFreight);
                                            }
                                        }
                                        curRow1 = vo.next();
                                    }
                                }
                            }

                        }
                        if ("Y".equals(processShipFlag) && "SALES_ORDER".equals(orderType)) {
                            try {
                                logMessage.append("<p><b>Sales Order Created: " + soNumber + "</b></p>");
                                String overallProcess = "Y";
                                int loop = 0;
                                String pick = "N";
                                String createPickWave = null;
                                //Get Order Information and check if all the lines are in Awaiting ship status
                                Object obj = new Object();
                                try {
                                    synchronized (obj) {
                                        while ("N".equals(pick)) {
                                            if (loop < 24) {
                                                loop++;
                                                obj.wait(5000);
                                                try {
                                                    createPickWave =
                                                        appM.callPickWaveServiceImpl(soNumber, src_system,
                                                                                     shipFromOrganization, orderType,
                                                                                     releaseRule);

                                                    //System.out.println("createPickWave: " + createPickWave);
                                                    if (createPickWave == null ||
                                                        "E".equals(createPickWave.substring(0, 1))) {
                                                        pick = "N";
                                                    } else {
                                                        pick = "Y";
                                                    }
                                                } catch (Exception e) {
                                                    pick = "N";
                                                    e.printStackTrace();
                                                    break;
                                                }
                                            } else {
                                                pick = "N";
                                                break;
                                            }
                                        }
                                    }
                                } catch (InterruptedException e) {
                                    pick = "N";
                                    e.printStackTrace();
                                }
                                if ("Y".equals(pick)) {
                                    if (createPickWave == null || "E".equals(createPickWave.substring(0, 1))) {
                                        logMessage.append("<p><b>Error occurred while creating Pick Batch " +
                                                          createPickWave + "</b></p>");
                                        logMessage.append("</body></html>");
                                        FacesMessage message =
                                            new FacesMessage(FacesMessage.SEVERITY_ERROR, logMessage.toString(), null);
                                        context.addMessage("Error", message);
                                        overallProcess = "N";
                                    } else {
                                        logMessage.append("<p><b>CreatePickWave:" + createPickWave + "</b></p>");
                                        //System.out.println("Resp from createPickWave PickBatchName:"+createPickWave);
                                        String pickRelease =
                                            appM.callProcessReleasePickBatchImpl(createPickWave, releaseMode);
                                        //System.out.println("pickRelease: " + pickRelease);
                                        //System.out.println("Resp from pickRelease Status:"+pickRelease);
                                        logMessage.append("<p><b>PickRelease:" + pickRelease + "</b></p>");
                                        //System.out.println("Before generateShip:"+createPickWave+";"+orderNumber +";"+ totalweight);
                                        Long shipmentLineNum = null;
                                        //String shipmentLinetext = null;
                                        //System.out.println("soNumber on Run Report: " + soNumber);
                                        //shipmentLinetext = appM.callRunReportImpl(soNumber);
                                        try {
                                            shipmentLineNum = Long.parseLong(appM.callRunReportImpl(soNumber));
                                        } catch (Exception e) {
                                            mylog.info("Error: Exception." + e);
                                            e.printStackTrace();
                                        }
                                        //There is a deplay sometimes so adding 2 loops to runreport if it fails to fetch first time
                                        /*if (!shipmentLinetext.isEmpty()) {
                                        shipmentLineNum = Long.parseLong(shipmentLinetext);
                                    } else {
                                        Object obj2 = new Object();
                                        try {
                                            synchronized (obj2) {
                                                obj2.wait(10000);
                                            }
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                        shipmentLinetext = appM.callRunReportImpl(soNumber);
                                        shipmentLineNum = Long.parseLong(shipmentLinetext);
                                    }*/
                                        //System.out.println("Before Calling Createshipment shipmentLineNum:"+shipmentLineNum);
                                        DateFormat timeStamp = new SimpleDateFormat("MMddHHmm");
                                        timeStamp.setTimeZone(TimeZone.getTimeZone("America/New_York"));
                                        //System.out.println("The date:"+timeStamp);
                                        String first10CharsofTrain=trainNum.length()>=10?
                                            trainNum.substring(0,10):trainNum;
                                        String shipmentName = first10CharsofTrain + "-" + timeStamp.format(new Date());
                               //         totalweight
                     //   totalweight=totalweight.setScale(2, RoundingMode.HALF_EVEN);
                                        String genship_status =
                                            appM.callCreateShipmentRequestServiceImpl(shipmentName,
                                                                                      shipFromOrganization, partyNumber,
                                                                                      shipPartyNum, dumpDate,
                                                                                      totalweight,carrier);
                                        //    div);
                                        //System.out.println("Resp from Createshipment shipmentLineNum:"+shipmentLineNum);


                                        if (!("SUCCESS".equals(genship_status))) {
                                            logMessage.append("<p><b>Error occurred while creating Shipment:" +
                                                              genship_status + "</b></p>");
                                            logMessage.append("</body></html>");
                                            FacesMessage message =
                                                new FacesMessage(FacesMessage.SEVERITY_ERROR, logMessage.toString(),
                                                                 null);
                                            context.addMessage("Error", message);
                                            overallProcess = "N";
                                        } else {
                                            //logMessage.append("<p><b>CreateShipment:" + genship_status + "</b></p>");
                                            String man_status =
                                                appM.callManageShipmentAssignmentsImpl(shipmentName, shipmentLineNum);
                                            if (!("SUCCESS".equals(man_status))) {
                                                logMessage.append("<p><b>Error occurred while Assigning Shipment:" +
                                                                  man_status + "</b></p>");
                                                logMessage.append("</body></html>");
                                                FacesMessage message =
                                                    new FacesMessage(FacesMessage.SEVERITY_ERROR, logMessage.toString(),
                                                                     null);
                                                context.addMessage("Error", message);
                                                overallProcess = "N";
                                            } else {
                                                //logMessage.append("<p><b>AssignShipment:" + man_status + "</b></p>");
                                                String ship_subinv = "STAGING";
                                                List<ShipmentLineInformation> shipmentLineInformationList =
                                                    new ArrayList<ShipmentLineInformation>();
                                                com.oracle.xmlns.apps.scm.shipping.shipconfirm.deliveries.shipmentlineservice.ObjectFactory shipObjectFactory =
                                                    new com.oracle.xmlns.apps.scm.shipping.shipconfirm.deliveries.shipmentlineservice.ObjectFactory();
                                                int rowCount = 0;
                                                if (vo.getRowCount() > 0) {
                                                    Row curRow = vo.first();
                                                    for (i = 0; i < vo.getRowCount(); i++) {
                                                        if (selrows == 1) {
                                                            if (curRow.getAttribute("SelectFlag") != null) {
                                                                if (curRow.getAttribute("SelectFlag")
                                                                          .toString()
                                                                          .compareTo("true") == 0) {
                                                                    //System.out.println("The row is selected with sequencenum :"+curRow.getAttribute("DumpSequence")+" and car: "+ curRow.getAttribute("CarNumber"));
                                                                    //System.out.println("BeforeSplit:"+shipmentLineNum+";"+curRow.getAttribute("WeightInTons"));
                                                                    Long shipmentLineFromSplitLine1 = shipmentLineNum;
                                                                    //logMessage.append("<p><b>SplitLine:" +
                                                                    //                  shipmentLineFromSplitLine1 + "</b></p>");
                                                                    //System.out.println("Resp from splitShipment shipmentLineFromSplitLine:"+shipmentLineFromSplitLine);
                                                                    //System.out.println("BeforeUpdateShipment:"+shipmentLineFromSplitLine+";"+curRow.getAttribute("WeightInTons"));
                                                                    String tracking_num = null;
                                                                    if (curRow.getAttribute("NewCarNumber") != null) {
                                                                        if (curRow.getAttribute("NewCarPrefix") !=
                                                                            null) {
                                                                            tracking_num =
                                                                                curRow.getAttribute("NewCarPrefix")
                                                                                .toString() + "-" +
                                                                                curRow.getAttribute("NewCarNumber")
                                                                                .toString();

                                                                        } else {
                                                                            tracking_num =
                                                                                curRow.getAttribute("NewCarNumber")
                                                                                .toString();
                                                                        }
                                                                    } else {
                                                                        if (curRow.getAttribute("CarPrefix") != null) {
                                                                            tracking_num =
                                                                                curRow.getAttribute("CarPrefix")
                                                                                .toString() + "-" +
                                                                                curRow.getAttribute("CarNumber")
                                                                                .toString();
                                                                        } else {
                                                                            tracking_num =
                                                                                curRow.getAttribute("CarNumber")
                                                                                .toString();
                                                                        }
                                                                    }
                                                                    String shipQuant = "0";
                                                                    if (curRow.getAttribute("ShipQuant") != null) {
                                                                        shipQuant =
                                                                            curRow.getAttribute("ShipQuant").toString();
                                                                    }

                                                                   /* BigDecimal lineWeight =
                                                                        new BigDecimal(curRow.getAttribute("WeightInTons")
                                                                                       .toString());
                                                                    lineWeight =
                                                                        lineWeight.setScale(5, RoundingMode.HALF_EVEN);*/

                                                                   BigDecimal lineWeight =
                                                                 //   curRow.getAttribute("DumpWeight").toString()!=null?
                                                                    curRow.getAttribute("DumpWeight")!=null?
                                                                                                                new BigDecimal(curRow.getAttribute("DumpWeight").toString()):
                                                                                                            new BigDecimal(curRow.getAttribute("WeightInTons").toString());
                                                                                                                                //      lineWeight =
                                                                                                                                  //        lineWeight.setScale(4, RoundingMode.HALF_EVEN);

                                                                    ShipmentLineInformation shipmentLineInformation =
                                                                        new ShipmentLineInformation();
                                                                    MeasureType measureType = new MeasureType();
                                                                    measureType.setUnitCode("Each");
                                                                    measureType.setValue(lineWeight);
                                                                    JAXBElement<Long> shipmentLine =
                                                                        shipObjectFactory.createShipmentLineInformationShipmentLine(shipmentLineFromSplitLine1);
                                                                    JAXBElement<String> trackingNumber =
                                                                        shipObjectFactory.createShipmentLineInformationTrackingNumber(tracking_num);
                                                                    JAXBElement<String> shipWeight =
                                                                        shipObjectFactory.createShipmentLineInformationSealNumber(shipQuant);
                                                                    JAXBElement<Long> loadSequence =
                                                                        shipObjectFactory.createShipmentLineInformationLoadingSequence(Long.valueOf(curRow.getAttribute("DumpSequence")
                                                                                                                                                    .toString()));
                                                                    JAXBElement<String> subinventory =
                                                                        shipObjectFactory.createShipmentLineInformationSubinventory(ship_subinv);
                                                                    JAXBElement<MeasureType> shippedQuantity =
                                                                        shipObjectFactory.createShipmentLineInformationShippedQuantity(measureType);
                                                                    shipmentLineInformation.setShipmentLine(shipmentLine);
                                                                    shipmentLineInformation.setShippedQuantity(shippedQuantity);
                                                                    shipmentLineInformation.setTrackingNumber(trackingNumber);
                                                                    shipmentLineInformation.setSealNumber(shipWeight);
                                                                    shipmentLineInformation.setLoadingSequence(loadSequence);
                                                                    shipmentLineInformation.setSubinventory(subinventory);
                                                                    shipmentLineInformationList.add(shipmentLineInformation);
                                                                }
                                                            }
                                                        } else {
                                                            if (curRow.getAttribute("SelectFlag") != null) {
                                                                if (curRow.getAttribute("SelectFlag")
                                                                          .toString()
                                                                          .compareTo("true") == 0) {
                                                                 /*   BigDecimal lineWeight =
                                                                        new BigDecimal(curRow.getAttribute("WeightInTons")
                                                                                       .toString());
                                                                    lineWeight =
                                                                        lineWeight.setScale(5, RoundingMode.HALF_EVEN);*/
                                                                    
                                                                    
                                                                    BigDecimal lineWeight =
                                                               //     curRow.getAttribute("DumpWeight").toString()!=null?
                                                                    curRow.getAttribute("DumpWeight")!=null?
                                                                                                                new BigDecimal(curRow.getAttribute("DumpWeight").toString()):
                                                                                                            new BigDecimal(curRow.getAttribute("WeightInTons").toString());
                                                          //         lineWeight =
                                                               //        lineWeight.setScale(4, RoundingMode.HALF_EVEN);
                                                                    //System.out.println("The row is selected with sequencenum :"+curRow.getAttribute("DumpSequence")+" and car: "+ curRow.getAttribute("CarNumber"));
                                                                    //System.out.println("BeforeSplit:"+shipmentLineNum+";"+curRow.getAttribute("WeightInTons"));
                                                                    //If the quantity of Car is 0 then we are not considering it to add it in Shipment Split
                                                                    if (!(lineWeight.compareTo(BigDecimal.ZERO) == 0)) {
                                                                        rowCount++;
                                                                        Long shipmentLineFromSplitLine = null;
                                                                        if (selrows == rowCount) {
                                                                            shipmentLineFromSplitLine = shipmentLineNum;
                                                                        } else {
                                                                            shipmentLineFromSplitLine =
                                                                                appM.callSplitShipmentLineImpl(shipmentLineNum,
                                                                                                               lineWeight);
                                                                        }
                                                                        if (shipmentLineFromSplitLine == null ||
                                                                            "0".equals(shipmentLineNum.toString())) {
                                                                            logMessage.append("<p><b>Error occurred while splitting line ShipmentLinenum: " +
                                                                                              shipmentLineNum +
                                                                                              "</b></p>");
                                                                            logMessage.append("</body></html>");
                                                                            FacesMessage message =
                                                                                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                                                 logMessage.toString(),
                                                                                                 null);
                                                                            context.addMessage("Error", message);
                                                                            overallProcess = "N";
                                                                        } else {
                                                                            //logMessage.append("<p><b>SplitLine:" +
                                                                            //                  shipmentLineFromSplitLine + "</b></p>");
                                                                            //System.out.println("Resp from splitShipment shipmentLineFromSplitLine:"+shipmentLineFromSplitLine);
                                                                            //System.out.println("BeforeUpdateShipment:"+shipmentLineFromSplitLine+";"+curRow.getAttribute("WeightInTons"));
                                                                            String tracking_num = null;
                                                                            if (curRow.getAttribute("NewCarNumber") !=
                                                                                null) {
                                                                                if (curRow.getAttribute("NewCarPrefix") !=
                                                                                    null) {
                                                                                    tracking_num =
                                                                                        curRow.getAttribute("NewCarPrefix")
                                                                                        .toString() + "-" +
                                                                                        curRow.getAttribute("NewCarNumber")
                                                                                        .toString();
                                                                                    //logMessage.append("<p><b>tracking_num:" +
                                                                                    //                  tracking_num + "</b></p>");
                                                                                } else {
                                                                                    tracking_num =
                                                                                        curRow.getAttribute("NewCarNumber")
                                                                                        .toString();
                                                                                    //logMessage.append("<p><b>tracking_num:" +
                                                                                    //                  tracking_num + "</b></p>");
                                                                                }
                                                                            } else {
                                                                                if (curRow.getAttribute("CarPrefix") !=
                                                                                    null) {
                                                                                    tracking_num =
                                                                                        curRow.getAttribute("CarPrefix")
                                                                                        .toString() + "-" +
                                                                                        curRow.getAttribute("CarNumber")
                                                                                        .toString();
                                                                                    //logMessage.append("<p><b>tracking_num:" +
                                                                                    //                  tracking_num + "</b></p>");
                                                                                } else {
                                                                                    tracking_num =
                                                                                        curRow.getAttribute("CarNumber")
                                                                                        .toString();
                                                                                    //logMessage.append("<p><b>tracking_num:" +
                                                                                    //                  tracking_num + "</b></p>");
                                                                                }
                                                                            }
                                                                            String shipQuant = "0";
                                                                            if (curRow.getAttribute("ShipQuant") !=
                                                                                null) {
                                                                                shipQuant =
                                                                                    curRow.getAttribute("ShipQuant")
                                                                                    .toString();
                                                                                //logMessage.append("<p><b>shipQuant:" + shipQuant +
                                                                                //                  "</b></p>");
                                                                            }
                                                                            ShipmentLineInformation shipmentLineInformation =
                                                                                new ShipmentLineInformation();
                                                                            MeasureType measureType = new MeasureType();
                                                                            measureType.setUnitCode("Each");
                                                                            measureType.setValue(lineWeight);
                                                                            JAXBElement<Long> shipmentLine =
                                                                                shipObjectFactory.createShipmentLineInformationShipmentLine(shipmentLineFromSplitLine);
                                                                            JAXBElement<String> trackingNumber =
                                                                                shipObjectFactory.createShipmentLineInformationTrackingNumber(tracking_num);
                                                                            JAXBElement<String> shipWeight =
                                                                                shipObjectFactory.createShipmentLineInformationSealNumber(shipQuant);
                                                                            JAXBElement<Long> loadSequence =
                                                                                shipObjectFactory.createShipmentLineInformationLoadingSequence(Long.valueOf(curRow.getAttribute("DumpSequence")
                                                                                                                                                            .toString()));
                                                                            JAXBElement<String> subinventory =
                                                                                shipObjectFactory.createShipmentLineInformationSubinventory(ship_subinv);
                                                                            JAXBElement<MeasureType> shippedQuantity =
                                                                                shipObjectFactory.createShipmentLineInformationShippedQuantity(measureType);
                                                                            shipmentLineInformation.setShipmentLine(shipmentLine);
                                                                            shipmentLineInformation.setShippedQuantity(shippedQuantity);
                                                                            shipmentLineInformation.setTrackingNumber(trackingNumber);
                                                                            shipmentLineInformation.setSealNumber(shipWeight);
                                                                            shipmentLineInformation.setLoadingSequence(loadSequence);
                                                                            shipmentLineInformation.setSubinventory(subinventory);
                                                                            shipmentLineInformationList.add(shipmentLineInformation);
                                                                        }
                                                                    }
                                                                }
                                                            }

                                                        }
                                                        curRow = vo.next();
                                                    }
                                                    UpdateShipmentLinesResponse updateShipmentLinesResponse =
                                                        appM.callUpdateShipmentLineImpl(shipmentLineInformationList);
                                                    if (!(updateShipmentLinesResponse.getReturnStatus()
                                                                                     .getValue()
                                                                                     .equals("SUCCESS"))) {
                                                        int msgCount = updateShipmentLinesResponse.getMessageCount()
                                                                                                  .getValue()
                                                                                                  .intValue();
                                                        for (i = 0; i < msgCount; i++) {
                                                            Message msg = new Message();
                                                            if (msg.getShipmentLine() != null) {
                                                                logMessage.append("<p><b>Error occurred while updating ShipmentLine: " +
                                                                                  msg.getShipmentLine().getValue() +
                                                                                  "</b></p>");
                                                            }
                                                            if (msg.getMessageText() != null) {
                                                                logMessage.append("<p><b>" +
                                                                                  msg.getMessageText().getValue() +
                                                                                  "</b></p>");
                                                            }

                                                            //logMessage =
                                                            //    logMessage +
                                                            //    " Error occurred while updating One line ShipmentSplitLinenum: " +
                                                            //    shipmentLineFromSplitLine1;
                                                            overallProcess = "N";
                                                        }
                                                        logMessage.append("</body></html>");
                                                        FacesMessage message =
                                                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                             logMessage.toString(), null);
                                                        context.addMessage("Error", message);
                                                    }
                                                }
                                                if ("Y".equals(overallProcess)) {
                                                    if (vo.getRowCount() > 0) {
                                                        Row curRow1 = vo.first();
                                                        for (i = 0; i < vo.getRowCount(); i++) {
                                                            if (curRow1.getAttribute("SelectFlag") != null) {
                                                                if (curRow1.getAttribute("SelectFlag")
                                                                           .toString()
                                                                           .compareTo("true") == 0) {
                                                                    curRow1.setAttribute("ShipmentNumber",
                                                                                         shipmentName);
                                                                    //curRow1.setAttribute("ProcessedFlag", "Y");
                                                                }
                                                            }
                                                            curRow1 = vo.next();
                                                        }
                                                    }
                                                    logMessage.append("<p><b>Shipment " + shipmentName +
                                                                      " is Created.</b></p>");
                                                    logMessage.append("</body></html>");
                                                    FacesMessage message =
                                                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                                                         logMessage.toString(), null);
                                                    context.addMessage("Confirmation", message);
                                                }
                                            }
                                        }

                                    }
                                } else {
                                    logMessage.append("<p><b>Sales Order: " + soNumber +
                                                      " not in Awaiting shippinng status Validate the Order and Re Process:</b></p>");
                                    logMessage.append("</body></html>");
                                    FacesMessage message =
                                        new FacesMessage(FacesMessage.SEVERITY_ERROR, logMessage.toString(), null);
                                    context.addMessage("Error", message);
                                }
                            } catch (Exception e) {
                                mylog.info("Error: Exception." + e);
                                e.printStackTrace();
                                logMessage.append("<p><b>Error Occurred:" + throwableToString(e) + "</b></p>");
                                logMessage.append("</body></html>");
                                FacesMessage message =
                                    new FacesMessage(FacesMessage.SEVERITY_ERROR, logMessage.toString(), null);
                                context.addMessage("Error", message);
                            }
                        }

                        else if ("Y".equals(processShipFlag) && "DROP_SHIP".equals(orderType)) {
                            logMessage.append("<p><b>Sales Order Created: " + soNumber + "</b></p>");
                            String overallProcess = "Y";
                            int loop = 0;
                            String pick = "N";
                            String dsBuId = null;
                            String dsSupplierName = null;
                            String dsSupplierNumber = null;
                            String dsReqInvOrgId = null;
                            String dsBuyerId = null;
                            String dsInvItemId = null;
                            String dsDocUserKey = null;
                            String dsDocLineUserKey = null;

                            //-*-
                            //Get Order Information and check if all the lines are in Awaiting ship status
                            Object obj = new Object();
                            try {
                                synchronized (obj) {
                                    while ("N".equals(pick)) {
                                        if (loop < 24) {
                                            loop++;
                                            obj.wait(5000);
                                            try {
                                                String orderInfo = null;
                                                orderInfo = appM.getLineStatusImpl(soNumber, headItemNumber);
                                                String[] orderInfoList = orderInfo.split(",");
                                                if (!("AWAIT_SHIP").equals(orderInfoList[0])) {
                                                    pick = "N";
                                                } else {
                                                    dsBuId = orderInfoList[1];
                                                    dsSupplierName =
                                                        orderInfoList[2].substring(1, orderInfoList[2].length() - 1);
                                                    dsSupplierNumber = orderInfoList[3];
                                                    dsReqInvOrgId = orderInfoList[4];
                                                    dsBuyerId = orderInfoList[5];
                                                    dsInvItemId = orderInfoList[6];
                                                    dsDocUserKey = orderInfoList[7];
                                                    dsDocLineUserKey = orderInfoList[8];
                                                    pick = "Y";
                                                    obj.wait(120000);

                                                }
                                            } catch (Exception e) {
                                                pick = "N";
                                                e.printStackTrace();
                                                break;
                                            }
                                        } else {
                                            pick = "N";
                                            break;
                                        }
                                    }
                                }
                            } catch (InterruptedException e) {
                                pick = "N";
                                e.printStackTrace();
                            }
                            if ("Y".equals(pick)) {
                                //OrderDetailsResponseBean orderDetailsResponseBean =
                                //    appM.callGetOrderDetailsImpl(src_system, soNumber, headItemNumber);

                                DateFormat timeStamp = new SimpleDateFormat("MMddHHmm");
                                timeStamp.setTimeZone(TimeZone.getTimeZone("America/New_York"));
                                String first10CharsofTrain=trainNum.length()>=10?
                                    trainNum.substring(0,10):trainNum;
                                String shipmentName = first10CharsofTrain + "-" + timeStamp.format(new Date());
                                //List<OrderLineBean> orderLineList = orderDetailsResponseBean.getOrderLineList();
                                //Iterator<OrderLineBean> orderLineListIter = orderLineList.iterator();
                                //OrderLineBean orderLineBean = null;
                                //if (orderLineListIter.hasNext()) {
                                //    orderLineBean = orderLineListIter.next();
                                //}
                                StagedReceivingHeader stagedReceivingHeader = new StagedReceivingHeader();
                                com.oracle.xmlns.apps.scm.receiving.receiptsinterface.transactions.processorservicev2.ObjectFactory objectFactory =
                                    new com.oracle.xmlns.apps.scm.receiving.receiptsinterface.transactions.processorservicev2.ObjectFactory();
                                JAXBElement<String> asnType = objectFactory.createStagedReceivingHeaderASNType("ASN");
                                JAXBElement<Long> buId =
                                    objectFactory.createStagedReceivingHeaderBUId(Long.parseLong(dsBuId));
                                JAXBElement<Long> employeeId =
                                    objectFactory.createStagedReceivingHeaderEmployeeId(Long.parseLong(dsBuyerId));
                                JAXBElement<String> receiptSourceCode =
                                    objectFactory.createStagedReceivingHeaderReceiptSourceCode("VENDOR");
                                JAXBElement<Long> shipToOrganizationId =
                                    objectFactory.createStagedReceivingHeaderShipToOrganizationId(Long.parseLong(dsReqInvOrgId));
                                JAXBElement<String> shipmentNumber =
                                    objectFactory.createStagedReceivingHeaderShipmentNumber(shipmentName);
                                //System.out.println("The date is "+dumpDate);
                                GregorianCalendar cal = new GregorianCalendar();
                                GregorianCalendar trcal = new GregorianCalendar();
                                JAXBElement<XMLGregorianCalendar> shippedDate = null;
                                XMLGregorianCalendar transDate = null;

                                try {
                                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                                    df.setTimeZone(TimeZone.getTimeZone("America/New_York"));
                                    Date date = df.parse(dumpDate);
                                    cal.setTime(date);
                                    //cal.add(GregorianCalendar.HOUR, 6);
                                    DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
                                    XMLGregorianCalendar shipDate = datatypeFactory.newXMLGregorianCalendar(cal);
                                    shippedDate = objectFactory.createStagedReceivingHeaderShippedDate(shipDate);
                                    DatatypeFactory trFactory = DatatypeFactory.newInstance();
                                    XMLGregorianCalendar trDate = trFactory.newXMLGregorianCalendar(trcal);
                                    transDate = shipDate;
                                    //System.out.println("The date is :"+shipDate+transDate);
                                } catch (ParseException e) {
                                    //System.out.println("Error: Invalid Date Parser Exception.");
                                    mylog.info("Error: Invalid Date Parser Exception.");
                                    e.printStackTrace();
                                } catch (Exception e) {
                                    //System.out.println("Error: Exception.");
                                    mylog.info("Error: Exception.");
                                    e.printStackTrace();
                                }
                                JAXBElement<String> transactionType =
                                    objectFactory.createStagedReceivingHeaderTransactionType("NEW");
                                JAXBElement<String> vendorName =
                                    objectFactory.createStagedReceivingHeaderVendorName(dsSupplierName);
                                JAXBElement<String> vendorNumber =
                                    objectFactory.createStagedReceivingHeaderVendorNumber(dsSupplierNumber);

                                stagedReceivingHeader.setASNType(asnType);
                                stagedReceivingHeader.setBUId(buId);
                                stagedReceivingHeader.setEmployeeId(employeeId);
                                stagedReceivingHeader.setReceiptSourceCode(receiptSourceCode);
                                stagedReceivingHeader.setShipToOrganizationId(shipToOrganizationId);
                                stagedReceivingHeader.setShipmentNumber(shipmentNumber);
                                stagedReceivingHeader.setShippedDate(shippedDate);
                                stagedReceivingHeader.setTransactionType(transactionType);
                                stagedReceivingHeader.setVendorName(vendorName);
                                stagedReceivingHeader.setVendorNumber(vendorNumber);
                                BigDecimal totweight = new BigDecimal(0);
                                if (vo.getRowCount() > 0) {
                                    Row curRow = vo.first();
                                    for (i = 0; i < vo.getRowCount(); i++) {
                                        if (curRow.getAttribute("SelectFlag") != null) {
                                            if (curRow.getAttribute("SelectFlag")
                                                      .toString()
                                                      .compareTo("true") == 0) {
                                              /*  BigDecimal lineWeight =
                                                    new BigDecimal(curRow.getAttribute("WeightInTons").toString());
                                                lineWeight = lineWeight.setScale(5, RoundingMode.HALF_EVEN);*/
                                                
                                             //   BigDecimal lineWeight =curRow.getAttribute("DumpWeight").toString()!=null?
                                                BigDecimal lineWeight =curRow.getAttribute("DumpWeight")!=null?
                                            new BigDecimal(curRow.getAttribute("DumpWeight").toString()):
                                        new BigDecimal(curRow.getAttribute("WeightInTons").toString());
                                          //     lineWeight = lineWeight.setScale(4, RoundingMode.HALF_EVEN);
                                                totweight = totweight.add(lineWeight);
                                            }
                                        }
                                        curRow = vo.next();
                                    }
                      //              totweight=totweight.setScale(4, RoundingMode.HALF_EVEN);
                                }
                                JAXBElement<String> autoTransactCode =
                                    objectFactory.createStagedReceivingTransactionAutoTransactCode("DELIVER");
                                JAXBElement<Long> buId1 =
                                    objectFactory.createStagedReceivingTransactionBUId(Long.parseLong(dsBuId));
                                JAXBElement<String> destinationTypeCode =
                                    objectFactory.createStagedReceivingTransactionDestinationTypeCode("DROP SHIP");
                                //System.out.println("The Line is:"+orderLineBean.getDocumentLineUserKey());
                                JAXBElement<Long> documentLineNumber =
                                    objectFactory.createStagedReceivingTransactionDocumentLineNumber(Long.parseLong(dsDocLineUserKey));
                                JAXBElement<String> documentNumber =
                                    objectFactory.createStagedReceivingTransactionDocumentNumber(dsDocUserKey);
                                JAXBElement<Long> itemId =
                                    objectFactory.createStagedReceivingTransactionItemId(Long.parseLong(dsInvItemId));
                                JAXBElement<String> carNumber =
                                    objectFactory.createStagedReceivingTransactionPackingSlip(trainNum);
                                MeasureType mt = new MeasureType();
                                mt.setUnitCode("");
                                mt.setValue(totweight);
                                JAXBElement<MeasureType> quantity =
                                    objectFactory.createStagedReceivingTransactionQuantity(mt);
                                JAXBElement<String> receiptSourceCode1 =
                                    objectFactory.createStagedReceivingTransactionReceiptSourceCode("VENDOR");
                                JAXBElement<Long> toOrganizationId =
                                    objectFactory.createStagedReceivingTransactionToOrganizationId(Long.parseLong(dsReqInvOrgId));
                         //       String transactionType1 = "RECEIVE";
                                String transactionType1 = "SHIP";
                            // As a part of 20D patch , we have updated this attribute
                                StagedReceivingTransaction stagedReceivingTransaction =
                                    new StagedReceivingTransaction();
                                stagedReceivingTransaction.setAutoTransactCode(autoTransactCode);
                                stagedReceivingTransaction.setBUId(buId1);
                                stagedReceivingTransaction.setDestinationTypeCode(destinationTypeCode);
                                stagedReceivingTransaction.setDocumentLineNumber(documentLineNumber);
                                stagedReceivingTransaction.setDocumentNumber(documentNumber);
                                stagedReceivingTransaction.setItemId(itemId);
                                stagedReceivingTransaction.setPackingSlip(carNumber);
                                stagedReceivingTransaction.setQuantity(quantity);
                                stagedReceivingTransaction.setReceiptSourceCode(receiptSourceCode1);
                                stagedReceivingTransaction.setToOrganizationId(toOrganizationId);
                                stagedReceivingTransaction.setTransactionDate(transDate);
                                stagedReceivingTransaction.setTransactionType(transactionType1);

                                stagedReceivingHeader.getStagedReceivingTransaction().add(stagedReceivingTransaction);
                                String status = appM.callProcessorImpl(stagedReceivingHeader);
                                //String status = "dlkjfgd";
                                //System.out.println("Resp from callProcessorImpl status:"+status);

                                if (!(status.equals("SUCCESS"))) {
                                    logMessage.append("<p><b>Error occurred while calling Processor : " + status +
                                                      "</b></p>");
                                    logMessage.append("</body></html>");
                                    FacesMessage message =
                                        new FacesMessage(FacesMessage.SEVERITY_ERROR, logMessage.toString(), null);
                                    context.addMessage("Error", message);
                                } else {
                                    logMessage.append("<p><b>ASN created :" + shipmentName + "</b></p>");
                                    logMessage.append("</body></html>");
                                    FacesMessage message =
                                        new FacesMessage(FacesMessage.SEVERITY_INFO, logMessage.toString(), null);
                                    context.addMessage("Confirmation", message);
                                }
                                //String status = appM.callProcessorImpl(stagedReceivingHeader);
                                if ("SUCCESS".equals(status)) {
                                    if (vo.getRowCount() > 0) {
                                        Row curRow = vo.first();
                                        for (i = 0; i < vo.getRowCount(); i++) {
                                            if (curRow.getAttribute("SelectFlag") != null) {
                                                if (curRow.getAttribute("SelectFlag")
                                                          .toString()
                                                          .compareTo("true") == 0) {
                                                    curRow.setAttribute("ShipmentNumber", shipmentName);
                                                    curRow.setAttribute("ProcessedFlag", "Y");
                                                }
                                            }
                                            curRow = vo.next();
                                        }
                                    }
                                }
                            } else {
                                logMessage.append("<p><b>Sales Order: " + soNumber +
                                                  " not in Awaiting shippinng status Validate the Order and Re Process:</b></p>");
                                logMessage.append("</body></html>");
                                FacesMessage message =
                                    new FacesMessage(FacesMessage.SEVERITY_ERROR, logMessage.toString(), null);
                                context.addMessage("Error", message);
                            }
                        } else if ("Y".equals(processShipFlag) && "PURCHASE_ORDER".equals(orderType)) {
                            CallableStatement st16 = null;
                            try {
                                st16 =
                                    appM.getDBTransaction()
                                    .createCallableStatement("BEGIN " + " SELECT ccl.internal_order " + " INTO :1 " +
                                                             " FROM ceix_contract_lines ccl, ceix_contract_headers cch " +
                                                             " WHERE cch.contract_number ='" + orderNumber + "' " +
                                                             " AND cch.id = ccl.chr_id " +
                                                             " AND ccl.ITEM_NUMBER = NVL ('" + headItemNumber +
                                                             "',ccl.ITEM_NUMBER) " + " AND ccl.item_type <> 'FRT' " +
                                                             " AND ccl.sts_code in ('ACTIVE','EXPIRED') " +
                                                             " AND TO_DATE('" + dumpDate +
                                                             "','yyyy-MM-dd') BETWEEN NVL (ccl.start_date, TO_DATE('" +
                                                             dumpDate + "','yyyy-MM-dd') - 1) " +
                                                             " AND NVL (ccl.end_date, TO_DATE('" + dumpDate +
                                                             "','yyyy-MM-dd')+1); " + " END;", 0);

                                st16.registerOutParameter(1, Types.VARCHAR);
                                st16.execute();
                                poNum = st16.getString(1);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            } finally {
                                try {
                                    st16.close();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (poNum == null) {
                                logMessage.append("<p><b>Unable to fetch Internal Order Number from Sales Agreement </b></p>");
                                logMessage.append("</body></html>");
                                FacesMessage message =
                                    new FacesMessage(FacesMessage.SEVERITY_ERROR, logMessage.toString(), null);
                                context.addMessage("Error", message);
                            } else {
                                PoReceiptBean poReceiptBean =
                                    appM.callGetPurchaseOrderDetailsImpl(poNum, legalEntityName);

                                if (poReceiptBean.getDocumentNumber() != null) {
                                    if (headDestination.equals(poReceiptBean.getShipToOrganizationName()
                                                               .toUpperCase())) {
                                        DateFormat timeStamp = new SimpleDateFormat("MMddHHmm");
                                        timeStamp.setTimeZone(TimeZone.getTimeZone("America/New_York"));
                                        String first10CharsofTrain=trainNum.length()>=10?
                                            trainNum.substring(0,10):trainNum;
                                        String receiptName = first10CharsofTrain + "-" + timeStamp.format(new Date());

                                        StagedReceivingHeader stagedReceivingHeader = new StagedReceivingHeader();
                                        com.oracle.xmlns.apps.scm.receiving.receiptsinterface.transactions.processorservicev2.ObjectFactory objectFactory =
                                            new com.oracle.xmlns.apps.scm.receiving.receiptsinterface.transactions.processorservicev2.ObjectFactory();
                                        JAXBElement<Long> buId =
                                            objectFactory.createStagedReceivingHeaderBUId(poReceiptBean.getBuId());
                                        JAXBElement<Long> employeeId =
                                            objectFactory.createStagedReceivingHeaderEmployeeId(poReceiptBean.getEmployeeId());
                                        JAXBElement<String> receiptSourceCode =
                                            objectFactory.createStagedReceivingHeaderReceiptSourceCode("VENDOR");
                                        JAXBElement<Long> shipToOrganizationId =
                                            objectFactory.createStagedReceivingHeaderShipToOrganizationId(poReceiptBean.getShipToOrganizationId());
                                        JAXBElement<String> receiptNum =
                                            objectFactory.createStagedReceivingHeaderReceiptNumber(receiptName);
                                        //System.out.println("The date is "+dumpDate);
                                        GregorianCalendar cal = new GregorianCalendar();
                                        GregorianCalendar trcal = new GregorianCalendar();
                                        JAXBElement<XMLGregorianCalendar> shippedDate = null;
                                        XMLGregorianCalendar transDate = null;

                                        try {
                                            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                                            df.setTimeZone(TimeZone.getTimeZone("America/New_York"));
                                            Date date = df.parse(dumpDate);
                                            cal.setTime(date);
                                            DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
                                            XMLGregorianCalendar shipDate =
                                                datatypeFactory.newXMLGregorianCalendar(cal);
                                            shippedDate =
                                                objectFactory.createStagedReceivingHeaderShippedDate(shipDate);
                                            DatatypeFactory trFactory = DatatypeFactory.newInstance();
                                            XMLGregorianCalendar trDate = trFactory.newXMLGregorianCalendar(trcal);
                                            transDate = shipDate;
                                            //System.out.println("The date is :"+shipDate+transDate);
                                        } catch (ParseException e) {
                                            //System.out.println("Error: Invalid Date Parser Exception.");
                                            mylog.info("Error: Invalid Date Parser Exception.");
                                            e.printStackTrace();
                                        } catch (Exception e) {
                                            //System.out.println("Error: Exception.");
                                            mylog.info("Error: Exception.");
                                            e.printStackTrace();
                                        }
                                        JAXBElement<String> transactionType =
                                            objectFactory.createStagedReceivingHeaderTransactionType("NEW");
                                        JAXBElement<String> vendorName =
                                            objectFactory.createStagedReceivingHeaderVendorName(poReceiptBean.getVendorname());

                                        stagedReceivingHeader.setBUId(buId);
                                        stagedReceivingHeader.setEmployeeId(employeeId);
                                        stagedReceivingHeader.setReceiptSourceCode(receiptSourceCode);
                                        stagedReceivingHeader.setShipToOrganizationId(shipToOrganizationId);
                                        stagedReceivingHeader.setReceiptNumber(receiptNum);
                                        stagedReceivingHeader.setTransactionType(transactionType);
                                        stagedReceivingHeader.setVendorName(vendorName);

                                        String currsubinv = null;
                                        String prevsubinv = null;
                                        cnt = 0;
                                        if (vo.getRowCount() > 0) {
                                            Row curRow = vo.first();
                                            for (i = 0; i < vo.getRowCount(); i++) {
                                                if (curRow.getAttribute("SelectFlag") != null) {
                                                    if (curRow.getAttribute("SelectFlag")
                                                              .toString()
                                                              .compareTo("true") == 0) {
                                                        cnt = cnt + 1;
                                                    }
                                                }
                                                curRow = vo.next();
                                            }
                                        }
                                        //System.out.println("The total count is:"+cnt);
                                        int j = 0;
                                        String ins = null;
                                        BigDecimal totweight = new BigDecimal(0);

                                        if (vo.getRowCount() > 0) {
                                            Row curRow = vo.first();
                                            int count = 0;
                                            for (i = 0; i < vo.getRowCount(); i++) {
                                                Boolean insertlast = Boolean.TRUE;
                                                if (curRow.getAttribute("SelectFlag") != null) {
                                                    if (curRow.getAttribute("SelectFlag")
                                                              .toString()
                                                              .compareTo("true") == 0) {
                                                        count = count + 1;
                                                        //System.out.println("The row is selected with sequencenum :"+curRow.getAttribute("DumpSequence")+" and car: "+ curRow.getAttribute("CarNumber"));
                                                        //System.out.println("BeforeCalling:"+curRow.getAttribute("WeightInTons"));
                                                        if (curRow.getAttribute("SubInventory") == null) {
                                                            currsubinv = "NULL";
                                                        } else {
                                                            currsubinv = curRow.getAttribute("SubInventory").toString();
                                                        }
                                                     
                                                        //System.out.println("The counter is:"+j);
                                                        //System.out.println("The values are"+currsubinv+";"+prevsubinv);
                                                        if (currsubinv.equals(prevsubinv) || prevsubinv == null) {
                                                          /*  totweight =
                                                                totweight.add(new BigDecimal(curRow.getAttribute("WeightInTons")
                                                                                             .toString()));*/
                                                            
                                                           // totweight = curRow.getAttribute("DumpWeight").toString()!=null?
                                                            totweight = curRow.getAttribute("DumpWeight")!=null?
                                                            totweight.add(new BigDecimal(curRow.getAttribute("DumpWeight")
                                                                                               .toString())):
                                                                totweight.add(new BigDecimal(curRow.getAttribute("WeightInTons")
                                                                                             .toString()));
                                                        } else {
                                                            ins = "Yes";
                                                        }
                                                        if ("Yes".equals(ins) || count == cnt) {
                                                            while (insertlast) {
                                                                if (count == cnt && cnt == 1) {
                                                                    prevsubinv = currsubinv;
                                                                }
                                                                //System.out.println("The total weight for "+prevsubinv+" is:"+totweight+";"+count+";"+cnt);
                                                                JAXBElement<String> autoTransactCode =
                                                                    objectFactory.createStagedReceivingTransactionAutoTransactCode("DELIVER");
                                                                JAXBElement<Long> buId1 =
                                                                    objectFactory.createStagedReceivingTransactionBUId(poReceiptBean.getBuId());
                                                                JAXBElement<Long> documentLineNumber =
                                                                    objectFactory.createStagedReceivingTransactionDocumentLineNumber(Long.parseLong(poReceiptBean.getDocumentLineNumber()));
                                                                JAXBElement<String> documentNumber =
                                                                    objectFactory.createStagedReceivingTransactionDocumentNumber(poReceiptBean.getDocumentNumber());
                                                                JAXBElement<Long> itemId =
                                                                    objectFactory.createStagedReceivingTransactionItemId(poReceiptBean.getItemId());
                                                                JAXBElement<String> trNumber =
                                                                    objectFactory.createStagedReceivingTransactionPackingSlip(trainNum);
                                                                MeasureType mt = new MeasureType();
                                                                mt.setUnitCode("");
                                                                mt.setValue(totweight);
                                                                JAXBElement<MeasureType> quantity =
                                                                    objectFactory.createStagedReceivingTransactionQuantity(mt);
                                                                JAXBElement<String> receiptSourceCode1 =
                                                                    objectFactory.createStagedReceivingTransactionReceiptSourceCode("VENDOR");
                                                                JAXBElement<Long> toOrganizationId =
                                                                    objectFactory.createStagedReceivingTransactionToOrganizationId(poReceiptBean.getToOrganizationId());
                                                                String transferSubInv = "STAGING";
                                                                if ((!("NULL".equals(prevsubinv))) &&
                                                                    prevsubinv != null) {
                                                                    transferSubInv = prevsubinv;
                                                                }
                                                                JAXBElement<String> subinventory =
                                                                    objectFactory.createStagedReceivingTransactionSubinventory(transferSubInv);
                                                                String transactionType1 = "RECEIVE";
                                                                StagedReceivingTransaction stagedReceivingTransaction =
                                                                    new StagedReceivingTransaction();
                                                                stagedReceivingTransaction.setAutoTransactCode(autoTransactCode);
                                                                stagedReceivingTransaction.setBUId(buId1);
                                                                stagedReceivingTransaction.setDocumentLineNumber(documentLineNumber);
                                                                stagedReceivingTransaction.setDocumentNumber(documentNumber);
                                                                stagedReceivingTransaction.setItemId(itemId);
                                                                stagedReceivingTransaction.setPackingSlip(trNumber);
                                                                stagedReceivingTransaction.setQuantity(quantity);
                                                                stagedReceivingTransaction.setReceiptSourceCode(receiptSourceCode1);
                                                                stagedReceivingTransaction.setSubinventory(subinventory);
                                                                stagedReceivingTransaction.setToOrganizationId(toOrganizationId);
                                                                stagedReceivingTransaction.setTransactionDate(transDate);
                                                                stagedReceivingTransaction.setTransactionType(transactionType1);

                                                                stagedReceivingHeader.getStagedReceivingTransaction()
                                                                    .add(stagedReceivingTransaction);
                                                                if ("Yes".equals(ins) && count == cnt) {
                                                                    prevsubinv = currsubinv;
                                                                    insertlast = Boolean.TRUE;
                                                                } else {
                                                                    insertlast = Boolean.FALSE;
                                                                }
                                                                ins = null;
                                                            }
                                                        }
                                                        prevsubinv = currsubinv;
                                                    }
                                                }
                                                curRow = vo.next();
                                            }
                                        }
                                        String status = appM.callProcessorImpl(stagedReceivingHeader);
                                        //System.out.println("Resp from callProcessorImpl status:"+status);
                                        if (!(status.equals("SUCCESS"))) {
                                            logMessage.append("<p><b>Error occurred while calling Processor : " +
                                                              status + "</b></p>");
                                            logMessage.append("</body></html>");
                                            FacesMessage message =
                                                new FacesMessage(FacesMessage.SEVERITY_ERROR, logMessage.toString(),
                                                                 null);
                                            context.addMessage("Error", message);
                                        } else {
                                            logMessage.append("<p><b>RECEIPT created : " + receiptName + "</b></p>");
                                            logMessage.append("</body></html>");
                                            FacesMessage message =
                                                new FacesMessage(FacesMessage.SEVERITY_INFO, logMessage.toString(),
                                                                 null);
                                            context.addMessage("Confirmation", message);
                                        }
                                        //String status = appM.callProcessorImpl(stagedReceivingHeader);
                                        if ("SUCCESS".equals(status)) {
                                            if (vo.getRowCount() > 0) {
                                                Row curRow = vo.first();
                                                for (i = 0; i < vo.getRowCount(); i++) {
                                                    if (curRow.getAttribute("SelectFlag") != null) {
                                                        if (curRow.getAttribute("SelectFlag")
                                                                  .toString()
                                                                  .compareTo("true") == 0) {
                                                            curRow.setAttribute("ShipmentNumber", receiptName);
                                                            curRow.setAttribute("ProcessedFlag", "Y");
                                                            curRow.setAttribute("ItemNumber", headItemNumber);
                                                            curRow.setAttribute("Destination", headDestination);
                                                            curRow.setAttribute("LoadOrigin", headLoadOrigin);
                                                            curRow.setAttribute("Straggler", headStraggler);
                                                            curRow.setAttribute("Freight", headFreight);
                                                        }
                                                    }
                                                    curRow = vo.next();
                                                }
                                            }
                                        }
                                    } else {
                                        logMessage.append("<p><b>Destination must match the ship-to organization on the purchase order: " +
                                                          poNum + "</b></p>");
                                        logMessage.append("</body></html>");
                                        FacesMessage message =
                                            new FacesMessage(FacesMessage.SEVERITY_ERROR, logMessage.toString(), null);
                                        context.addMessage("Error", message);
                                    }
                                } else {
                                    logMessage.append("<p><b>Invalid purchase order: " + poNum + "</b></p>");
                                    logMessage.append("</body></html>");
                                    FacesMessage message =
                                        new FacesMessage(FacesMessage.SEVERITY_ERROR, logMessage.toString(), null);
                                    context.addMessage("Error", message);
                                }
                            }
                        } else if ("Y".equals(processShipFlag) && "TRANSFER_ORDER".equals(orderType)) {
                            ObjectFactory objectFactory = new ObjectFactory();
                            List<StagedInventoryTransaction> stagedInventoryTransactionList = new ArrayList();
                            DateFormat timeStamp = new SimpleDateFormat("MMddHHmm");
                            timeStamp.setTimeZone(TimeZone.getTimeZone("America/New_York"));
                            String first10CharsofTrain=trainNum.length()>=10?
                                trainNum.substring(0,10):trainNum;
                            String waybillNum = first10CharsofTrain + "-" + timeStamp.format(new Date());
                            String currsubinv = null;
                            String prevsubinv = null;
                            cnt = 0;
                            if (vo.getRowCount() > 0) {
                                Row curRow = vo.first();
                                for (i = 0; i < vo.getRowCount(); i++) {
                                    if (curRow.getAttribute("SelectFlag") != null) {
                                        if (curRow.getAttribute("SelectFlag")
                                                  .toString()
                                                  .compareTo("true") == 0) {
                                            cnt = cnt + 1;
                                        }
                                    }
                                    curRow = vo.next();
                                }
                            }
                            //System.out.println("The total count is:"+cnt);
                            int j = 0;
                            String ins = null;
                            String status = null;
                            BigDecimal totweight = new BigDecimal(0);
                         //   BigDecimal shippedTonsInv=new BigDecimal(0);
                            BigDecimal shippedTonsInv=new BigDecimal(0);
                            BigDecimal dumpedTonsInv= new BigDecimal(0);
                            if (vo.getRowCount() > 0) {
                                Row curRow = vo.first();
                                //System.out.println("The rowcount is:"+vo.getRowCount());
                                int count = 0;

                                for (j = 0; j < vo.getRowCount(); j++) 
								{
                                    Boolean insertlast = Boolean.TRUE;
                                    if (curRow.getAttribute("SelectFlag") != null) {
                                        if (curRow.getAttribute("SelectFlag")
                                                  .toString()
                                                  .compareTo("true") == 0) {
                                            count = count + 1;
                                            //curRow.setAttribute("ShipmentNumber", waybillNum);
                                            //curRow.setAttribute("ProcessedFlag", "Y");
                                            if (curRow.getAttribute("SubInventory") == null) {
                                                currsubinv = "NULL";
                                            } else {
                                                currsubinv = curRow.getAttribute("SubInventory").toString();
                                            }
                                            if( curRow.getAttribute("DumpWeight")!=null)
                                                                        {
                                           dumpedTonsInv=dumpedTonsInv.add(new BigDecimal(curRow.getAttribute("DumpWeight")
                                                                           .toString()));
                             //    dumpedTonsInv=  new BigDecimal(curRow.getAttribute("DumpWeight").toString());
                               //                 dumpedTonsInv = dumpedTonsInv.setScale(4, RoundingMode.HALF_EVEN);
                                 //                  dumpedTonsInv = dumpedTonsInv.add(dumpedTonsInv);
                                                
                                            }
                                            {        
                                           shippedTonsInv=shippedTonsInv.add(new BigDecimal(curRow.getAttribute("WeightInTons")
                                                                       .toString()));
                                              //          shippedTonsInv=  new BigDecimal(curRow.getAttribute("WeightInTons").toString());
                                                //                       shippedTonsInv = shippedTonsInv.setScale(4, RoundingMode.HALF_EVEN);
                                                  //                        shippedTonsInv = shippedTonsInv.add(shippedTonsInv);
                                            }
                                            //System.out.println("The counter is:"+j);
                                            //System.out.println("The values are"+currsubinv+";"+prevsubinv);
                                            if (currsubinv.equals(prevsubinv) || prevsubinv == null) 
                                            {
                                               /* totweight =
                                                    totweight.add(new BigDecimal(curRow.getAttribute("WeightInTons")
                                                                                 .toString()));*/
                                                
                                              //  totweight =curRow.getAttribute("DumpWeight").toString()!=null?
                                                
                                              BigDecimal weightInTons =curRow.getAttribute("DumpWeight")!=null?
                                                                                         new BigDecimal(curRow.getAttribute("DumpWeight").toString()):
                                                                                     new BigDecimal(curRow.getAttribute("WeightInTons").toString());
                                                                                 weightInTons = weightInTons.setScale(4, RoundingMode.HALF_EVEN);
                                                                                    totweight = totweight.add(weightInTons);
                                                                                    
                                                                    
                                                                                    
                                        /*        totweight =curRow.getAttribute("DumpWeight")!=null?
                                                totweight.add(new BigDecimal(curRow.getAttribute("DumpWeight")
                                                                                   .toString())):
                                                    totweight.add(new BigDecimal(curRow.getAttribute("WeightInTons")
                                                                                 .toString()));*/
																		
                                 //     totweight = totweight.setScale(2, RoundingMode.HALF_EVEN);
                                    //   totweight = totweight.add(weightInTons);
																		
                                             //   totweight = totweight.setScale(3, RoundingMode.HALF_EVEN);
                                               // totweight = totweight.setScale(2, RoundingMode.HALF_EVEN);
											//	totweight = totweight.setScale(4, RoundingMode.HALF_EVEN);
                                            } else {
                                                ins = "Yes";
                                            }
                                            if ("Yes".equals(ins) || count == cnt) {
                                                while (insertlast) {
                                                    if (count == cnt && cnt == 1) {
                                                        prevsubinv = currsubinv;
                                                    }
                                                    //System.out.println("The total weight for "+prevsubinv+" is:"+totweight+";"+count+";"+cnt);

                                                    CallableStatement statement = null;
                                                    int transInterfaceId = 0;
                                                    String OrgId = null;
                                                    String transferOrgId = null;
                                                    //String freCost = null;
                                                    String itemid = null;
                                                    try {
                                                        statement =
                                                            appM.getDBTransaction()
                                                            .createCallableStatement("BEGIN " +
                                                                                     " SELECT ceix_sts_waybill_trans_ord_seq.NEXTVAL seq_num, " +
                                                                                     " (select ORGANIZATION_ID from CEIX_INV_ORGANIZATION_DEFINITIONS where upper(ORGANIZATION_NAME) = '" +
                                                                                     headDestination +
                                                                                     "' ) TRANSFER_ORG_ID, " +
                                                                                     " (select ORGANIZATION_ID from CEIX_INV_ORGANIZATION_DEFINITIONS where upper(ORGANIZATION_NAME) = '" +
                                                                                     headLoadOrigin + "' ) ORG_ID, " +
                                                                                     " ccl.object1_id1 item_id " +
                                                                                     " INTO :1, :2, :3, :4 " +
                                                                                     " FROM ceix_contract_lines ccl, ceix_contract_headers cch " +
                                                                                     " WHERE cch.contract_number = '" +
                                                                                     orderNumber +
                                                                                     "' and cch.id= ccl.chr_id " +
                                                                                     " AND ccl.ITEM_NUMBER = NVL ( '" +
                                                                                     headItemNumber +
                                                                                     "' , ccl.ITEM_NUMBER) " +
                                                                                     " AND ccl.sts_code in ('ACTIVE','EXPIRED') " +
                                                                                     " AND TO_DATE('" + dumpDate +
                                                                                     "','yyyy-MM-dd') BETWEEN NVL (ccl.start_date, TO_DATE('" +
                                                                                     dumpDate +
                                                                                     "','yyyy-MM-dd') - 1) " +
                                                                                     " AND NVL (ccl.end_date, TO_DATE('" +
                                                                                     dumpDate +
                                                                                     "','yyyy-MM-dd') +1); " + " END;",
                                                                                     0);


                                                        /* " SELECT  ceix_sts_waybill_trans_ord_seq.NEXTVAL seq_num, " +
                                                                                 " (SELECT meaning FROM ceix_lookup_values WHERE lookup_type = 'TRANSFER_ORDER_PARAM' " +
                                                                                 " AND   lookup_code = 'TRANSFER_ORG_ID') trans_org, " +
                                                                                 " (SELECT meaning FROM ceix_lookup_values WHERE lookup_type = 'TRANSFER_ORDER_PARAM' " +
                                                                                 " AND   lookup_code = 'ORG_ID') org, " +
                                                                                 " (SELECT meaning FROM ceix_lookup_values WHERE lookup_type = 'MISC_ISSUE_INV' " +
                                                                                 " AND   lookup_code = '107201') org, " +
                                                                                 " (select cohd.attribute_char5 from ceix_order_headers_dff cohd, ceix_order_headers coh" +
                                                                                 " where cohd.header_id = coh.header_id and coh.order_number = '" +
                                                                                 orderNumber + "' " +
                                                                                 " and context_code = 'Contract Information') freight_rate " +
                                                                                 " INTO :1, :2, :3, :4, :5 " +
                                                                                 " from dual; " + " END;", 0);*/
                                                        statement.registerOutParameter(1, Types.INTEGER);
                                                        statement.registerOutParameter(2, Types.VARCHAR);
                                                        statement.registerOutParameter(3, Types.VARCHAR);
                                                        statement.registerOutParameter(4, Types.VARCHAR);
                                                        //statement.registerOutParameter(5, Types.VARCHAR);
                                                        statement.execute();
                                                        transInterfaceId = statement.getInt(1);
                                                        transferOrgId = statement.getString(2);
                                                        OrgId = statement.getString(3);
                                                        itemid = statement.getString(4);
                                                        //freCost = statement.getString(5);
                                                        //System.out.println ("The transInterfaceId inside value is-->" + transInterfaceId);
                                                    } catch (SQLException e) {
                                                        e.printStackTrace();
                                                    } finally {
                                                        try {
                                                            statement.close();
                                                        } catch (SQLException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                    
                                                    totweight = totweight.setScale(4, RoundingMode.HALF_EVEN);
                                                  
                                                    if (dumpedTonsInv !=null)
                                                    {
                                                    totweight = dumpedTonsInv.setScale(2, RoundingMode.HALF_EVEN);}
                                                    else{
                                                            totweight = shippedTonsInv.setScale(2, RoundingMode.HALF_EVEN);
                                                        }
                                                    shippedTonsInv = shippedTonsInv.setScale(2, RoundingMode.HALF_EVEN);
                                                    dumpedTonsInv = dumpedTonsInv.setScale(2, RoundingMode.HALF_EVEN);
                                                    //System.out.println ("The transInterfaceId value is-->" + transInterfaceId+";"+transferOrgId+";"+OrgId);
                                                    JAXBElement<Long> transactionHeaderId =
                                                        objectFactory.createStagedInventoryTransactionTransactionHeaderId(new Long("1000"));
                                                    JAXBElement<Long> inventoryItemId =
                                                        objectFactory.createStagedInventoryTransactionInventoryItemId(Long.parseLong(itemid));
                                                    JAXBElement<Long> organizationId =
                                                        objectFactory.createStagedInventoryTransactionOrganizationId(new Long(OrgId));
                                                    MeasureType mt = new MeasureType();
                                                    mt.setUnitCode("TON");
                                                    mt.setValue(totweight);
                                                    JAXBElement<String> uom =
                                                        objectFactory.createStagedInventoryTransactionTransactionUOM("TON");
                                                    //System.out.println("The date is "+dumpDate);
                                                    GregorianCalendar cal = new GregorianCalendar();
                                                    GregorianCalendar trcal = new GregorianCalendar();
                                                    XMLGregorianCalendar transDate = null;

                                                    try {
                                                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                                                        df.setTimeZone(TimeZone.getTimeZone("America/New_York"));
                                                        Date date = df.parse(dumpDate);
                                                        cal.setTime(date);
                                                        //cal.add(GregorianCalendar.HOUR, 6);
                                                        DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
                                                        transDate = datatypeFactory.newXMLGregorianCalendar(cal);
                                                        //System.out.println("The date is :"+transDate);
                                                    } catch (ParseException e) {
                                                        //System.out.println("Error: Invalid Date Parser Exception.");
                                                        mylog.info("Error: Invalid Date Parser Exception.");
                                                        e.printStackTrace();
                                                    } catch (Exception e) {
                                                        //System.out.println("Error: Exception.");
                                                        mylog.info("Error: Exception.");
                                                        e.printStackTrace();
                                                    }
                                                    JAXBElement<String> subinv =
                                                        objectFactory.createStagedInventoryTransactionSubinventoryCode("STAGING");
                                                    JAXBElement<String> actionId =
                                                        objectFactory.createStagedInventoryTransactionTransactionActionId("3");
                                                    JAXBElement<Long> typeId =
                                                        objectFactory.createStagedInventoryTransactionTransactionTypeId(new Long("3"));
                                                    JAXBElement<String> tranRef =
                                                        objectFactory.createStagedInventoryTransactionTransactionReference(trainNum);
                                                    JAXBElement<Long> transOrgId =
                                                        objectFactory.createStagedInventoryTransactionTransferOrganization(new Long(transferOrgId));
                                                    //BigDecimal fCost = totweight.multiply(new BigDecimal(freCost));
                                                    //JAXBElement<BigDecimal> freightCost =
                                                    //    objectFactory.createStagedInventoryTransactionTransportationCost(fCost);
                                                    String transferSubInv = "STAGING";

                                                    if ((!("NULL".equals(prevsubinv))) && prevsubinv != null) {
                                                        transferSubInv = prevsubinv;
                                                    }
                                                    String dumpDt_waybill="";
                                                       
                                                       if (dumpLDate != null) {
                                                           try{
                                                        //       DateFormat dateFormat = new SimpleDateFormat("dd-M-yyyy");  
                                                               DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");  
                                                                dumpDt_waybill = dateFormat.format(dumpLDate);  
                                                           }
                                                       catch (Exception e) {
                                                                                                                   //System.out.println("Error: Exception.");
                                                                                                                   e.printStackTrace();
                                                                                                               }
                                                       }
                                                       
                                                       String shipDt_waybill="";
                                                          
                                                          if (shipLDate != null) {
                                                              try{
                                                             //     DateFormat dateFormat = new SimpleDateFormat("dd-M-yyyy");  
                                                                  DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");  
                                                                   shipDt_waybill = dateFormat.format(shipLDate);  
                                                              }
                                                          catch (Exception e) {
                                                                                                                      //System.out.println("Error: Exception.");
                                                                                                                      e.printStackTrace();
                                                                                                                  }
                                                          }
                                                    Date date = Calendar.getInstance().getTime();  
                                                    DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
                                                    String strDate = dateFormat.format(date); 
                                                    JAXBElement<String> trsubinv =
                                                        objectFactory.createStagedInventoryTransactionTransferSubinventory(transferSubInv);
                                                    JAXBElement<String> shipNum =
                                                        objectFactory.createStagedInventoryTransactionShipmentNumber(waybillNum);
                                                    JAXBElement<String> salesAgreementNum =
                                                        objectFactory.createStagedInventoryTransactionShipmentNumber(orderNumber);
                                                    JAXBElement<String> mine =
                                                        objectFactory.createStagedInventoryTransactionShipmentNumber(headLoadOrigin);
                                                    JAXBElement<String> carrierRail =
                                                        objectFactory.createStagedInventoryTransactionShipmentNumber(carrier);
                                                    JAXBElement<String> dumpDt =
                                                    objectFactory.createStagedInventoryTransactionShipmentNumber(dumpDt_waybill);           
                                                    JAXBElement<String> shipDt =
                                                    objectFactory.createStagedInventoryTransactionShipmentNumber(shipDt_waybill);
                                                    BigDecimal freight=new BigDecimal(headFreight);
                                                    Integer sTonsInt=shippedTonsInv.intValue();
                                                      String sTonsStr=String.valueOf(sTonsInt);
                                                 String s = shippedTonsInv.toString(); //to be checked
                                               //     JAXBElement<String> shippedTons =
                                                 //   objectFactory.createStagedInventoryTransactionAttribute8(sTonsStr);
                                                   JAXBElement<String> shippedTons =
                                                   objectFactory.createStagedInventoryTransactionAttribute8(s);//to be checked
                                                    Integer dTonsInt=dumpedTonsInv.intValue();
                                                      String dTonsStr=String.valueOf(dTonsInt);
                                                  String d = dumpedTonsInv.toString();//to be checked
                                                    JAXBElement<String> dumpTons =
                                                     objectFactory.createStagedInventoryTransactionAttribute9(d);// to be checked
                                                    JAXBElement<String> railContract=
                                                                                                            objectFactory.createStagedInventoryTransactionAttribute5(custPO);
                                                    JAXBElement<String> customerName =
                                                        objectFactory.createStagedInventoryTransactionShipmentNumber(partyName);
                                                    JAXBElement<BigDecimal> freightRate =
                                                        objectFactory.createStagedInventoryTransactionAttributeNumber2(freight) ;                                                            
                                                    JAXBElement<String> curCost =
                                                        objectFactory.createStagedInventoryTransactionUseCurrentCost("Y");

                                                    StagedInventoryTransaction stagedInventoryTransaction =
                                                        new StagedInventoryTransaction();
                                                    stagedInventoryTransaction.setTransactionInterfaceId(new Long(transInterfaceId));
                                                    stagedInventoryTransaction.setTransactionHeaderId(transactionHeaderId);
                                                    stagedInventoryTransaction.setSourceCode(trainNum);
                                                    stagedInventoryTransaction.setSourceLineId(new Long("1"));
                                                    stagedInventoryTransaction.setSourceHeaderId(new Long(transInterfaceId));
                                                    stagedInventoryTransaction.setProcessCode("1");
                                                    stagedInventoryTransaction.setTransactionMode("3");
                                                    stagedInventoryTransaction.setInventoryItemId(inventoryItemId);
                                                    stagedInventoryTransaction.setOrganizationId(organizationId);
                                                    stagedInventoryTransaction.setTransactionQuantity(mt);
                                                    stagedInventoryTransaction.setTransactionUOM(uom);
                                                    stagedInventoryTransaction.setTransactionDate(transDate);
                                                    stagedInventoryTransaction.setSubinventoryCode(subinv);
                                                    stagedInventoryTransaction.setTransactionActionId(actionId);
                                                    stagedInventoryTransaction.setTransactionTypeId(typeId);
                                                    stagedInventoryTransaction.setTransactionReference(tranRef);
                                                    stagedInventoryTransaction.setTransferSubinventory(trsubinv);
                                                    stagedInventoryTransaction.setTransferOrganization(transOrgId);
                                                    //stagedInventoryTransaction.setTransportationCost(freightCost);
                                                    stagedInventoryTransaction.setShipmentNumber(shipNum);
                                                    stagedInventoryTransaction.setUseCurrentCost(curCost);
                                               //     stagedInventoryTransaction.setAttributeNumber1("124");
                                          //    stagedInventoryTransaction.setAttribute1(subinv);
                                          stagedInventoryTransaction.setAttribute1(salesAgreementNum);
                                                stagedInventoryTransaction.setAttribute2(mine);
                                                stagedInventoryTransaction.setAttribute3(customerName);
                                               stagedInventoryTransaction.setAttribute4(carrierRail);
                                                stagedInventoryTransaction.setAttribute5(railContract);  
                                         //     stagedInventoryTransaction.setAttribute8(shippedTons); 
                                      stagedInventoryTransaction.setAttribute6(shippedTons);  //to be checked
                                               stagedInventoryTransaction.setAttribute9(dumpTons);   //to be checked
                                             stagedInventoryTransaction.setAttribute7(shipDt);                                                            
                                              stagedInventoryTransaction.setAttribute8(dumpDt);
                                                stagedInventoryTransaction.setAttributeNumber2(freightRate);   

                                                    stagedInventoryTransactionList.add(stagedInventoryTransaction);


                                                    //String status = appM.callProcessorImpl(stagedReceivingHeader);
                                                  /*  totweight =
                                                        new BigDecimal(curRow.getAttribute("WeightInTons").toString());*/
                                                    
                                                   // totweight =curRow.getAttribute("DumpWeight").toString()!=null?
                                           /*         totweight =curRow.getAttribute("DumpWeight")!=null?
                                                        new BigDecimal(curRow.getAttribute("DumpWeight").toString()):
                                                        new BigDecimal(curRow.getAttribute("WeightInTons").toString());
                                                    totweight = totweight.setScale(2, RoundingMode.HALF_EVEN);*/
                                                    if ("Yes".equals(ins) && count == cnt) {
                                                        prevsubinv = currsubinv;
                                                        insertlast = Boolean.TRUE;
                                                    } else {
                                                        insertlast = Boolean.FALSE;
                                                    }
                                                    ins = null;
                                                }
                                            }
                                            prevsubinv = currsubinv;
                                            //System.out.println("The weight for "+currsubinv+" is:"+totweight);
                                        }
                                    }
                                    curRow = vo.next();
                                }
                            }
                            status = appM.callinsertAndProcessInterfaceRowsImpl(stagedInventoryTransactionList);
                            //System.out.println("Resp from callProcessorImpl status:"+status);
                            if (!(status.equals("0"))) {
                                logMessage.append("<p><b>Error occurred while inserting Inventory Transaction : " +
                                                  status + "</b></p>");
                                logMessage.append("</body></html>");
                                FacesMessage message =
                                    new FacesMessage(FacesMessage.SEVERITY_ERROR, logMessage.toString(), null);
                                context.addMessage("Error", message);
                            } else {
                                logMessage.append("<p><b>Inventory Transaction processed. </b></p>");
                                logMessage.append("<p><b>Waybill Number is:" + waybillNum + "</b></p>");
                                logMessage.append("</body></html>");
                                FacesMessage message =
                                    new FacesMessage(FacesMessage.SEVERITY_INFO, logMessage.toString(), null);
                                context.addMessage("Confirmation", message);

                            }
                            if ("0".equals(status)) {
                                if (vo.getRowCount() > 0) {
                                    Row curRow = vo.first();
                                    for (i = 0; i < vo.getRowCount(); i++) {
                                        if (curRow.getAttribute("SelectFlag") != null) {
                                            if (curRow.getAttribute("SelectFlag")
                                                      .toString()
                                                      .compareTo("true") == 0) {
                                                curRow.setAttribute("ShipmentNumber", waybillNum);
                                                curRow.setAttribute("ProcessedFlag", "Y");
                                                curRow.setAttribute("ItemNumber", headItemNumber);
                                                curRow.setAttribute("Destination", headDestination);
                                                curRow.setAttribute("LoadOrigin", headLoadOrigin);
                                                curRow.setAttribute("Straggler", headStraggler);
                                                curRow.setAttribute("Freight", headFreight);
                                            }
                                        }
                                        curRow = vo.next();
                                    }
                                }
                            }
                        } else if ("Y".equals(processShipFlag) && "MISC_ISSUE".equals(orderType)) {
                            ObjectFactory objectFactory = new ObjectFactory();
                            List<StagedInventoryTransaction> stagedInventoryTransactionList = new ArrayList();
                            DateFormat timeStamp = new SimpleDateFormat("MMddHHmm");
                            timeStamp.setTimeZone(TimeZone.getTimeZone("America/New_York"));
                            String first10CharsofTrain=trainNum.length()>=10?
                                trainNum.substring(0,10):trainNum;
                            String waybillNum = first10CharsofTrain + "-" + timeStamp.format(new Date());
                            if (vo.getRowCount() > 0) {
                                Row curRow = vo.first();
                                for (i = 0; i < vo.getRowCount(); i++) {
                                    if (curRow.getAttribute("SelectFlag") != null) {
                                        if (curRow.getAttribute("SelectFlag")
                                                  .toString()
                                                  .compareTo("true") == 0) {
                                            //System.out.println("The row is selected with sequencenum :"+curRow.getAttribute("DumpSequence")+" and car: "+ curRow.getAttribute("CarNumber"));
                                            //System.out.println("BeforeCalling:"+curRow.getAttribute("WeightInTons"));
                                            String item_num = null;
                                            if (curRow.getAttribute("NewCarNumber") != null) {
                                                item_num = curRow.getAttribute("NewCarNumber").toString();
                                            } else {
                                                item_num = curRow.getAttribute("CarNumber").toString();
                                            }
                                            CallableStatement statement = null;
                                            int transInterfaceId = 0;
                                            String OrgId = null;
                                            String ItemId = null;
                                            String freCost = null;
                                            try {
                                                statement =
                                                    appM.getDBTransaction()
                                                    .createCallableStatement("BEGIN" +
                                                                             " SELECT  ceix_sts_waybill_trans_ord_seq.NEXTVAL seq_num, " +
                                                                             " (SELECT meaning FROM ceix_lookup_values WHERE lookup_type = 'MISC_ISSUE_ORG' " +
                                                                             " AND   lookup_code = '1665') org, " +
                                                                             " (SELECT meaning FROM ceix_lookup_values WHERE lookup_type = 'MISC_ISSUE_INV' " +
                                                                             " AND   lookup_code = '" + item_num +
                                                                             "') item_id, " +
                                                                             " (select cohd.attribute_char5 from ceix_order_headers_dff cohd, ceix_order_headers coh" +
                                                                             " where cohd.header_id = coh.header_id and coh.order_number = '" +
                                                                             orderNumber + "' " +
                                                                             " and context_code = 'Contract Information') freight_rate " +
                                                                             " INTO :1, :2, :3, :4 " + " from dual; " +
                                                                             " END;", 0);
                                                statement.registerOutParameter(1, Types.INTEGER);
                                                statement.registerOutParameter(2, Types.VARCHAR);
                                                statement.registerOutParameter(3, Types.VARCHAR);
                                                statement.registerOutParameter(4, Types.VARCHAR);
                                                statement.execute();
                                                transInterfaceId = statement.getInt(1);
                                                OrgId = statement.getString(2);
                                                ItemId = statement.getString(3);
                                                freCost = statement.getString(4);
                                                //System.out.println ("The transInterfaceId inside value is-->" + transInterfaceId);
                                            } catch (SQLException e) {
                                                e.printStackTrace();
                                            } finally {
                                                try {
                                                    st1.close();
                                                } catch (SQLException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            //System.out.println ("The transInterfaceId value is-->" + transInterfaceId+";"+ItemId+";"+OrgId);
                                            JAXBElement<Long> transactionHeaderId =
                                                objectFactory.createStagedInventoryTransactionTransactionHeaderId(new Long("1000"));
                                            JAXBElement<Long> inventoryItemId =
                                                objectFactory.createStagedInventoryTransactionInventoryItemId(new Long(ItemId));
                                            JAXBElement<Long> organizationId =
                                                objectFactory.createStagedInventoryTransactionOrganizationId(new Long(OrgId));
                                            MeasureType mt = new MeasureType();
                                            mt.setUnitCode("TON");
                                        //    if(curRow.getAttribute("DumpWeight").toString()!=null)
                                                if(curRow.getAttribute("DumpWeight")!=null)
                                                mt.setValue(new BigDecimal("-" +
                                                                           curRow.getAttribute("DumpWeight").toString()));
                                            else
                                            mt.setValue(new BigDecimal("-" +
                                                                       curRow.getAttribute("WeightInTons").toString()));
                                            JAXBElement<String> uom =
                                                objectFactory.createStagedInventoryTransactionTransactionUOM("TON");
                                            //System.out.println("The date is "+dumpDate);
                                            GregorianCalendar cal = new GregorianCalendar();
                                            GregorianCalendar trcal = new GregorianCalendar();
                                            XMLGregorianCalendar transDate = null;

                                            try {
                                                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                                                df.setTimeZone(TimeZone.getTimeZone("America/New_York"));
                                                Date date = df.parse(dumpDate);
                                                cal.setTime(date);
                                                //cal.add(GregorianCalendar.HOUR, 6);
                                                DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
                                                transDate = datatypeFactory.newXMLGregorianCalendar(cal);
                                                //System.out.println("The date is :"+transDate);
                                            } catch (ParseException e) {
                                                //System.out.println("Error: Invalid Date Parser Exception.");
                                                mylog.info("Error: Invalid Date Parser Exception.");
                                                e.printStackTrace();
                                            } catch (Exception e) {
                                                //System.out.println("Error: Exception.");
                                                mylog.info("Error: Exception.");
                                                e.printStackTrace();
                                            }
                                            JAXBElement<String> subinv =
                                                objectFactory.createStagedInventoryTransactionSubinventoryCode("STAGING");
                                            //JAXBElement<String> actionId = objectFactory.createStagedInventoryTransactionTransactionActionId("3");
                                            JAXBElement<Long> typeId =
                                                objectFactory.createStagedInventoryTransactionTransactionTypeId(new Long("32"));
                                            JAXBElement<String> tranRef =
                                                objectFactory.createStagedInventoryTransactionTransactionReference(item_num);
                                            //BigDecimal fCost = new BigDecimal(curRow.getAttribute("WeightInTons").toString()).multiply(new BigDecimal(freCost));
                                            //JAXBElement<BigDecimal> freightCost = objectFactory.createStagedInventoryTransactionTransportationCost(fCost);
                                            JAXBElement<String> shipNum =
                                                objectFactory.createStagedInventoryTransactionShipmentNumber(waybillNum);
                                            JAXBElement<String> curCost =
                                                objectFactory.createStagedInventoryTransactionUseCurrentCost("Y");

                                            StagedInventoryTransaction stagedInventoryTransaction =
                                                new StagedInventoryTransaction();
                                            stagedInventoryTransaction.setTransactionInterfaceId(new Long(transInterfaceId));
                                            stagedInventoryTransaction.setTransactionHeaderId(transactionHeaderId);
                                            stagedInventoryTransaction.setSourceCode(trainNum);
                                            stagedInventoryTransaction.setSourceLineId(new Long("1"));
                                            stagedInventoryTransaction.setSourceHeaderId(new Long(transInterfaceId));
                                            stagedInventoryTransaction.setProcessCode("1");
                                            stagedInventoryTransaction.setTransactionMode("3");
                                            stagedInventoryTransaction.setInventoryItemId(inventoryItemId);
                                            stagedInventoryTransaction.setOrganizationId(organizationId);
                                            stagedInventoryTransaction.setTransactionQuantity(mt);
                                            stagedInventoryTransaction.setTransactionUOM(uom);
                                            stagedInventoryTransaction.setTransactionDate(transDate);
                                            stagedInventoryTransaction.setSubinventoryCode(subinv);
                                            //stagedInventoryTransaction.setTransactionActionId(actionId);
                                            stagedInventoryTransaction.setTransactionTypeId(typeId);
                                            stagedInventoryTransaction.setTransactionReference(tranRef);
                                            //stagedInventoryTransaction.setTransferSubinventory(trsubinv);
                                            //stagedInventoryTransaction.setTransferOrganization(transOrgId);
                                            //stagedInventoryTransaction.setTransportationCost(freightCost);
                                            stagedInventoryTransaction.setShipmentNumber(shipNum);
                                            stagedInventoryTransaction.setUseCurrentCost(curCost);

                                            stagedInventoryTransactionList.add(stagedInventoryTransaction);

                                        }
                                    }
                                    curRow = vo.next();
                                }
                            }
                            String status = null;
                            status = appM.callinsertAndProcessInterfaceRowsImpl(stagedInventoryTransactionList);
                            //System.out.println("Resp from callProcessorImpl status:"+status);
                            if (!(status.equals("0"))) {
                                logMessage.append("<p><b>Error occurred while inserting Inventory Transaction : " +
                                                  status + "</b></p>");
                                logMessage.append("</body></html>");
                                FacesMessage message =
                                    new FacesMessage(FacesMessage.SEVERITY_ERROR, logMessage.toString(), null);
                                context.addMessage("Error", message);
                            } else {
                                logMessage.append("<p><b>Inventory Transaction processed. </b></p>");
                                logMessage.append("<p><b>Waybill Number: " + waybillNum + "</b></p>");
                                logMessage.append("</body></html>");
                                FacesMessage message =
                                    new FacesMessage(FacesMessage.SEVERITY_INFO, logMessage.toString(), null);
                                context.addMessage("Confirmation", message);
                            }
                            //String status = appM.callProcessorImpl(stagedReceivingHeader);
                            if ("0".equals(status)) {
                                if (vo.getRowCount() > 0) {
                                    Row curRow = vo.first();
                                    for (i = 0; i < vo.getRowCount(); i++) {
                                        if (curRow.getAttribute("SelectFlag") != null) {
                                            if (curRow.getAttribute("SelectFlag")
                                                      .toString()
                                                      .compareTo("true") == 0) {
                                                curRow.setAttribute("ShipmentNumber", waybillNum);
                                                curRow.setAttribute("ProcessedFlag", "Y");
                                                curRow.setAttribute("Freight", headFreight);
                                            }
                                        }
                                        curRow = vo.next();
                                    }
                                }
                            }
                        }
                        Transaction transaction = appM.getTransaction();
                        transaction.commit();
                        vo.executeQuery();
                    }
                } else if ("N".equals(processShipFlag)) {
                    logMessage.append("<p><b>Weight Prevail : " + acceptWeight + "</b></p>");
                    logMessage.append("<p><b>Cannot process this in Waybill Form." + "</b></p>");
                    logMessage.append("</body></html>");
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, logMessage.toString(), null);
                    context.addMessage("Error", message);
                }
            }
        } catch (Exception e) {
            //System.out.println("Error: Exception.");
            mylog.info("Error: Exception." + e);
            e.printStackTrace();
            logMessage.append("<p><b>Error Occurred:" + throwableToString(e) + "</b></p>");
            logMessage.append("</body></html>");
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, logMessage.toString(), null);
            context.addMessage("Error", message);
        } finally {
            this.onSearchCall();
            AdfFacesContext.getCurrentInstance().addPartialTarget(getHeaderTable());
        }

    }

    public String onCreate() {
        BindingContext bindingctx = BindingContext.getCurrent();
        DCDataControl dc = bindingctx.dataControlFrame().findDataControl("CeixStsWaybillAMDataControl");
        CeixStsWaybillAMImpl appM = (CeixStsWaybillAMImpl) dc.getDataProvider();
        ViewObject vo = appM.getCeixStsWaybillSearchVO1();
        //System.out.println("The header is :"+vo.getCurrentRow().getAttribute("TrainNumber"));
        //System.out.println("The header1 is :"+vo.getCurrentRow().getAttribute("DumpDate"));
        ViewObject vol = appM.getCeixStsWaybillLinesVO1();
        //System.out.println("The header is :"+vol.getCurrentRow().getAttribute("DeliveryHeaderId"));
        //System.out.println("The header1 is :"+vol.getCurrentRow().getAttribute("DeliveryLineId"));

        ViewObject vo3 = appM.getCeixStsWaybillHdrVO2();
        vo3.reset();
        vo3.clearCache();
        vo3.setWhereClause(null);
        vo3.setWhereClause(" delivery_header_id = " + vol.getCurrentRow().getAttribute("DeliveryHeaderId"));
        vo3.executeQuery();
        vo3.setCurrentRow(vo3.first());
        //System.out.println("The new is :"+vo3.getCurrentRow().getAttribute("UnitTrainNumber"));
        //System.out.println("The new1 is :"+vo3.getCurrentRow().getAttribute("LoadDate1"));
        ViewObject vo2 = appM.getCeixStsWaybillLinesVO2();
        Row newrow = vo2.createRow();
        vo2.insertRow(newrow);
        vo2.setCurrentRow(newrow);
        vo2.getCurrentRow().setAttribute("TrainNumber", vo3.getCurrentRow().getAttribute("TrainNumber"));
        return "Create";
    }

    public void onSave(ActionEvent actionEvent) {
        BindingContext bindingctx = BindingContext.getCurrent();
        DCDataControl dc = bindingctx.dataControlFrame().findDataControl("CeixStsWaybillAMDataControl");
        CeixStsWaybillAMImpl appM = (CeixStsWaybillAMImpl) dc.getDataProvider();
        ViewObject vo = appM.getCeixStsWaybillLinesVO2();
        //System.out.println("The header is :"+vo.getCurrentRow().getAttribute("DeliveryLineId"));
        Key curKey = vo.getCurrentRow().getKey();
        Transaction transaction = appM.getTransaction();
        transaction.commit();
        vo.executeQuery();
        Key k = new Key(curKey.getAttributeValues());
        Row[] find = appM.getCeixStsWaybillLinesVO2().findByKey(k, 1);
        Row r = appM.getCeixStsWaybillLinesVO2().getRow(k);
        appM.getCeixStsWaybillLinesVO2().setCurrentRow(r);
    }

    public String onBacktoWaybill() {
        BindingContext bindingctx = BindingContext.getCurrent();
        DCDataControl dc = bindingctx.dataControlFrame().findDataControl("CeixStsWaybillAMDataControl");
        CeixStsWaybillAMImpl appM = (CeixStsWaybillAMImpl) dc.getDataProvider();
        Transaction transaction = appM.getTransaction();
        transaction.rollback();
        ViewObject vo = appM.getCeixStsWaybillSearchVO1();
        ViewObject volines = appM.getCeixStsWaybillLinesVO1();
        volines.executeQuery();
        vo.executeQuery();
        if (vo.getCurrentRow() != null) {
            //System.out.println("Current search row:"+vo.getCurrentRow().getAttribute("TrainNumber"));
            vo.getCurrentRow().setAttribute("EditFlag", "Y");
            //System.out.println("The flag is:"+vo.getCurrentRow().getAttribute("EditFlag"));
            ViewObject vol = appM.getCeixStsWaybillLinesVO1();
            vol.reset();
            vol.clearCache();
            vol.setWhereClause(null);
            String wc = "";
            if (vo.getCurrentRow().getAttribute("TrainNumber") != null) {
                String TrainNum = vo.getCurrentRow()
                                    .getAttribute("TrainNumber")
                                    .toString();
                wc = "train_number = '" + TrainNum + "'";
                //System.out.println("Lines Train:"+TrainNum);
            } else {
                wc = "train_number is null ";
            }
            if (vo.getCurrentRow().getAttribute("OrderNumber") != null) {
                String SalesNum = vo.getCurrentRow()
                                    .getAttribute("OrderNumber")
                                    .toString();
                wc = wc + " and sales_order_number = '" + SalesNum + "'";
                //System.out.println("Lines sales:"+SalesNum);
            } else {
                wc = wc + " and sales_order_number is null ";
            }
            if (vo.getCurrentRow().getAttribute("ShipmentNumber") != null) {
                String ShipNum = vo.getCurrentRow()
                                   .getAttribute("ShipmentNumber")
                                   .toString();
                wc = wc + " and shipment_number = '" + ShipNum + "'";
                //System.out.println("Lines ship:"+ShipNum);
            } else {
                wc = wc + " and shipment_number is null ";
            }
            if (vo.getCurrentRow().getAttribute("DumpDate") != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date();
                try {
                    date = sdf.parse(vo.getCurrentRow()
                                       .getAttribute("DumpDate")
                                       .toString());
                } catch (ParseException e) {
                    //System.out.println("Error: Invalid Date Parser Exception.");
                    mylog.info("Error: Invalid Date Parser Exception.");
                    e.printStackTrace();
                } catch (Exception e) {
                    //System.out.println("Error: Exception.");
                    mylog.info("Error: Exception.");
                    e.printStackTrace();
                }
                wc = wc + " and dump_date = TO_DATE('" + sdf.format(date) + "','yyyy-MM-dd')";
            } else {
                wc = wc + " and dump_date is null ";
            }
            if (vo.getCurrentRow().getAttribute("SoNumber") != null) {
                String OrderNum = vo.getCurrentRow()
                                    .getAttribute("SoNumber")
                                    .toString();
                wc = wc + " and Attribute1 = '" + OrderNum + "'";

            } else {
                wc = wc + " and Attribute1 is null ";
            }
            if (vo.getCurrentRow().getAttribute("Straggler") != null) {
                String straggler = vo.getCurrentRow()
                                     .getAttribute("Straggler")
                                     .toString();
                wc = wc + " and nvl(straggler,'N') = '" + straggler + "'"; // This is from ceix_sts_delivery_lines table

            } else {
                wc = wc + " and nvl(straggler,'N') ='N' ";
            }
            vol.setWhereClause(wc);
            vol.executeQuery();
            AdfFacesContext.getCurrentInstance().addPartialTarget(getDetailPC());
            reset_BT_CB();
        } else {
            ViewObject vol = appM.getCeixStsWaybillLinesVO1();
            vol.reset();
            vol.clearCache();
            vol.setWhereClause("1=2");
            vol.executeQuery();
            AdfFacesContext.getCurrentInstance().addPartialTarget(getDetailPC());
            reset_BT_CB();
        }
        return "back";
    }

    public void onLineSave(ActionEvent actionEvent) {
        BindingContext bindingctx = BindingContext.getCurrent();
        DCDataControl dc = bindingctx.dataControlFrame().findDataControl("CeixStsWaybillAMDataControl");
        CeixStsWaybillAMImpl appM = (CeixStsWaybillAMImpl) dc.getDataProvider();
        ViewObject vo = appM.getCeixStsWaybillLinesVO1();
        ViewObject vosearch = appM.getCeixStsWaybillSearchVO1();
        Transaction transaction = appM.getTransaction();
        transaction.commit();
        vo.executeQuery();
        vosearch.getCurrentRow().setAttribute("EditFlag", "Y");
        //System.out.println("The flagonLineSave is:"+vosearch.getCurrentRow().getAttribute("EditFlag"));
        reset_BT_CB();
    }

    public void onLineEdit(ActionEvent actionEvent) {
        BindingContext bindingctx = BindingContext.getCurrent();
        DCDataControl dc = bindingctx.dataControlFrame().findDataControl("CeixStsWaybillAMDataControl");
        CeixStsWaybillAMImpl appM = (CeixStsWaybillAMImpl) dc.getDataProvider();
        ViewObject vosearch = appM.getCeixStsWaybillSearchVO1();
        vosearch.getCurrentRow().setAttribute("EditFlag", "N");
        //System.out.println("The flagonLineEdit is:"+vosearch.getCurrentRow().getAttribute("EditFlag"));
        reset_BT_CB();
    }

    public void onLineCancel(ActionEvent actionEvent) {
        BindingContext bindingctx = BindingContext.getCurrent();
        DCDataControl dc = bindingctx.dataControlFrame().findDataControl("CeixStsWaybillAMDataControl");
        CeixStsWaybillAMImpl appM = (CeixStsWaybillAMImpl) dc.getDataProvider();
        ViewObject volines = appM.getCeixStsWaybillLinesVO1();
        ViewObject vo = appM.getCeixStsWaybillSearchVO1();
        vo.getCurrentRow().setAttribute("EditFlag", "Y");
        //System.out.println("The flagonLineCancelbefore is:"+vo.getCurrentRow().getAttribute("EditFlag"));
        volines.clearCache();
        Transaction transaction = appM.getTransaction();
        transaction.rollback();
        vo.executeQuery();
        vo.executeQuery();
        if (vo.getCurrentRow() != null) {
            //System.out.println("Current search row:"+vo.getCurrentRow().getAttribute("TrainNumber"));
            ViewObject vol = appM.getCeixStsWaybillLinesVO1();
            vol.reset();
            vol.clearCache();
            vol.setWhereClause(null);
            String wc = "";
            if (vo.getCurrentRow().getAttribute("TrainNumber") != null) {
                String TrainNum = vo.getCurrentRow()
                                    .getAttribute("TrainNumber")
                                    .toString();
                wc = "train_number = '" + TrainNum + "'";
                //System.out.println("Lines Train:"+TrainNum);
            } else {
                wc = "train_number is null ";
            }
            if (vo.getCurrentRow().getAttribute("OrderNumber") != null) {
                String SalesNum = vo.getCurrentRow()
                                    .getAttribute("OrderNumber")
                                    .toString();
                wc = wc + " and sales_order_number = '" + SalesNum + "'";
                //System.out.println("Lines sales:"+SalesNum);
            } else {
                wc = wc + " and sales_order_number is null ";
            }
            if (vo.getCurrentRow().getAttribute("ShipmentNumber") != null) {
                String ShipNum = vo.getCurrentRow()
                                   .getAttribute("ShipmentNumber")
                                   .toString();
                wc = wc + " and shipment_number = '" + ShipNum + "'";
                //System.out.println("Lines ship:"+ShipNum);
            } else {
                wc = wc + " and shipment_number is null ";
            }
            if (vo.getCurrentRow().getAttribute("DumpDate") != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date();
                try {
                    date = sdf.parse(vo.getCurrentRow()
                                       .getAttribute("DumpDate")
                                       .toString());
                } catch (ParseException e) {
                    //System.out.println("Error: Invalid Date Parser Exception.");
                    mylog.info("Error: Invalid Date Parser Exception.");
                    e.printStackTrace();
                } catch (Exception e) {
                    //System.out.println("Error: Exception.");
                    mylog.info("Error: Exception.");
                    e.printStackTrace();
                }
                wc = wc + " and dump_date = TO_DATE('" + sdf.format(date) + "','yyyy-MM-dd')";
            } else {
                wc = wc + " and dump_date is null ";
            }
            if (vo.getCurrentRow().getAttribute("SoNumber") != null) {
                String OrderNum = vo.getCurrentRow()
                                    .getAttribute("SoNumber")
                                    .toString();
                wc = wc + " and Attribute1 = '" + OrderNum + "'";

            } else {
                wc = wc + " and Attribute1 is null ";
            }
            if (vo.getCurrentRow().getAttribute("Straggler") != null) {
                String straggler = vo.getCurrentRow()
                                     .getAttribute("Straggler")
                                     .toString();
                wc = wc + " and nvl(straggler,'N') = '" + straggler + "'"; // This is from ceix_sts_delivery_lines table

            } else {
                wc = wc + " and nvl(straggler,'N') ='N' ";
            }
            vol.setWhereClause(wc);
            vol.executeQuery();
            AdfFacesContext.getCurrentInstance().addPartialTarget(getDetailPC());
            reset_BT_CB();
        } else {
            ViewObject vol = appM.getCeixStsWaybillLinesVO1();
            vol.reset();
            vol.clearCache();
            vol.setWhereClause("1=2");
            vol.executeQuery();
            AdfFacesContext.getCurrentInstance().addPartialTarget(getDetailPC());
            reset_BT_CB();
        }
        vo.getCurrentRow().setAttribute("EditFlag", "Y");
        //System.out.println("The flag is:"+vo.getCurrentRow().getAttribute("EditFlag"));
    }

    /*public void setOnCalculateFreightBT(RichCommandButton onCalculateFreightBT) {
        this.onCalculateFreightBT = onCalculateFreightBT;
    }

    public RichCommandButton getOnCalculateFreightBT() {
        return onCalculateFreightBT;
    }*/

    /*public void onCalculateFreight(ActionEvent actionEvent) {
        FacesContext context = FacesContext.getCurrentInstance();
        StringBuilder logMessage = new StringBuilder("<html><body>");
        try {
            String orderType = "SALES_ORDER";
            BindingContext bindingctx = BindingContext.getCurrent();
            DCDataControl dc =
                bindingctx.dataControlFrame().findDataControl("CeixStsWaybillAMDataControl");
            CeixStsWaybillAMImpl appM =
                (CeixStsWaybillAMImpl)dc.getDataProvider();
            ViewObject vo = appM.findViewObject("CeixStsWaybillLinesVO1");
            ViewObject vohead = appM.findViewObject("CeixStsWaybillSearchVO1");
            //System.out.println("The order number is "+vohead.getCurrentRow().getAttribute("OrderNumber").toString());
            String orderNumber =
                vohead.getCurrentRow().getAttribute("OrderNumber").toString();
            String trainNum =
                vohead.getCurrentRow().getAttribute("TrainNumber").toString();
            String dumpDate =
                vohead.getCurrentRow().getAttribute("DumpDate").toString();
            CallableStatement st1 = null;
            String customerNum = null;
            String shipPartyNum = null;
            String fob = null;
            String acceptWeight = null;
            String processShipFlag = null;
            String processCostFlag = null;
            String ordType = null;
            String freightRate = null;
            try {
                st1 =
appM.getDBTransaction().createCallableStatement("BEGIN" + "  SELECT coh.party_number, coh.ship_party_site_number, coh.fob_point_code, coh.order_type, " +
                                                " (select cohd.attribute_char1 from ceix_order_headers_dff cohd where cohd.header_id = coh.header_id " +
                                                " and context_code = 'Acceptable Weights') acceptable_weights, " +
                                                " (select cohd.attribute_char5 from ceix_order_headers_dff cohd where cohd.header_id = coh.header_id " +
                                                " and context_code = 'Contract Information') freight_rate " +
                                                "    INTO :1, :2, :3, :4, :5, :6 " +
                                                " FROM ceix_order_headers coh " +
                                                " WHERE order_number = :7; " +
                                                "END;", 0);
                st1.registerOutParameter(1, Types.VARCHAR);
                st1.registerOutParameter(2, Types.VARCHAR);
                st1.registerOutParameter(3, Types.VARCHAR);
                st1.registerOutParameter(4, Types.VARCHAR);
                st1.registerOutParameter(5, Types.VARCHAR);
                st1.registerOutParameter(6, Types.VARCHAR);
                st1.setString(7, orderNumber);
                st1.execute();
                customerNum = st1.getString(1);
                shipPartyNum = st1.getString(2);
                fob = st1.getString(3);
                ordType = st1.getString(4);
                acceptWeight = st1.getString(5);
                freightRate = st1.getString(6);
                //System.out.println ("The SQL value is-->" + customerNum+";"+shipPartyNum+";"+ordType);
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    st1.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            //System.out.println ("The values are:"+fob+";"+acceptWeight+";"+ordType);
            if ("Purchased Coal Direct Ship".equals(ordType)) {
                orderType = "DROP_SHIP";
            }
            if ("Purchased Inventory Coal".equals(ordType)) {
                orderType = "PURCHASE_ORDER";
            }
            if ("CONSOL Inventory Coal".equals(ordType)) {
                orderType = "TRANSFER_ORDER";
            }

            if (!("DESTINATION".equals(fob))) {
                fob = "ORIGIN";
            }
            if ("ORIGIN".equals(fob)) {
                if ("Waybill Weights Prevail".equals(acceptWeight)) {
                    processShipFlag = "Y";
                    processCostFlag = "N";
                } else {
                    processShipFlag = "N";
                    processCostFlag = "N";
                }
            } else {
                if ("Waybill Weights Prevail".equals(acceptWeight)) {
                    processShipFlag = "Y";
                    processCostFlag = "Y";
                } else {
                    processShipFlag = "N";
                    processCostFlag = "N";
                }
            }
            if ("Y".equals(processCostFlag)) {
                int i = 0;
                if (freightRate == null) {
                    logMessage.append("<p><b>Freight rate is :" + freightRate +
                                      "</b></p>");
                    logMessage.append("<p><b> Pleasee specify Freight Rate</b></p>");
                    logMessage.append("</body></html>");
                    FacesMessage message =
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                         logMessage.toString(), null);
                    context.addMessage("Error", message);
                } else {
                    if (vo.getRowCount() > 0) {
                        Row curRow = vo.first();
                        for (i = 0; i < vo.getRowCount(); i++) {
                            //System.out.println("The row is selected with sequencenum :"+curRow.getAttribute("DumpSequence")+" and car: "+ curRow.getAttribute("CarNumber"));
                            //System.out.println("The tons:"+curRow.getAttribute("WeightInTons")+";");
                            if (curRow.getAttribute("FreightCost") == null) {
                                BigDecimal weight =
                                    new BigDecimal(curRow.getAttribute("WeightInTons").toString());
                                BigDecimal frcost =
                                    weight.multiply(new BigDecimal(freightRate));
                                curRow.setAttribute("FreightCost",
                                                    frcost.setScale(2,
                                                                    RoundingMode.HALF_DOWN));

                            }
                            curRow = vo.next();
                        }
                    }
                    logMessage.append("<p><b>Calculation of Freight completed: SUCCESS.</b></p>");
                    logMessage.append("</body></html>");
                    FacesMessage message =
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                         logMessage.toString(), null);
                    context.addMessage("Confirmation", message);
                }
            } else {
                logMessage.append("<p><b>Freight Calculation: Not Needed.</b></p>");
                logMessage.append("<p><b> FOB:" + fob + "</b></p>");
                logMessage.append("<p><b> Weights Prevail:" + acceptWeight +
                                  "</b></p>");
                logMessage.append("</body></html>");
                FacesMessage message =
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                     logMessage.toString(), null);
                context.addMessage("Error", message);
            }
            Transaction transaction = appM.getTransaction();
            transaction.commit();
            vo.executeQuery();
            reset_BT_CB();
        } catch (Exception e) {
            //System.out.println("Error: Exception.");
            mylog.info("Error: Exception." + e);
            e.printStackTrace();
            logMessage.append("<p><b>Error Occurred:" + throwableToString(e) +
                              "</b></p>");
            logMessage.append("</body></html>");
            FacesMessage message =
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                 logMessage.toString(), null);
            context.addMessage("Error", message);
        }
    }*/

    public void setCreateSalesOrderVal(RichCommandButton createSalesOrderVal) {
        this.createSalesOrderVal = createSalesOrderVal;
    }

    public RichCommandButton getCreateSalesOrderVal() {
        return createSalesOrderVal;
    }

    public void setHeaderSalesOrder(RichInputText headerSalesOrder) {
        this.headerSalesOrder = headerSalesOrder;
    }

    public RichInputText getHeaderSalesOrder() {
        return headerSalesOrder;
    }

    public void setHeaderComments(RichInputText headerComments) {
        this.headerComments = headerComments;
    }

    public RichInputText getHeaderComments() {
        return headerComments;
    }

    public void assignSalesOrderDialogListener(DialogEvent event) {
        if (event.getOutcome()
                 .name()
                 .equals("Cancel")) {
            resetAssignSalesOrderNewTrainFields();
        } else {
            boolean requiredFieldsNotEntered = assignSalesOrderNumRequiredFieldsMissing();
            if (requiredFieldsNotEntered) {
                showAlert("Assign Sales Agreement/New Train Number",
                          "<p>Please provide either Sales Agreement Number or New Train Number.</p>",
                          FacesMessage.SEVERITY_ERROR);
                return;
            }
            //handle assign sales order/new train number
            handleAssignSalesOrderOrUpdateNewTrain();

        }

        //disable the 'copy to waybill' button
        disableAssignSalesOrdBtn(true);

    }

    private void handleAssignSalesOrderOrUpdateNewTrain() {

        String newHeaderNo = null;
        //if sales order number provided, validate it.
        if (headerSalesOrder.getValue() != null && !headerSalesOrder.getValue().equals("")) {
            //vallid sales order
            boolean salesOrderIsValid = orderNumberIsValid(headerSalesOrder.getValue());
            if (!salesOrderIsValid) {
                //show Error and exit
                showInvalidSalesOrderNumberError();
                return;
            }
        }

        BindingContext bindingctx = BindingContext.getCurrent();
        DCDataControl dc = bindingctx.dataControlFrame().findDataControl("CeixStsWaybillAMDataControl");
        CeixStsWaybillAMImpl appM = (CeixStsWaybillAMImpl) dc.getDataProvider();
        ViewObject vo = appM.getCeixStsWaybillLinesVO1();
        //System.out.println( this.getHeaderSalesOrder().getValue()+" "+vo.getCurrentRow().getAttribute("DeliveryHeaderId"))                                                                                        ;
        newHeaderNo = this.getHeaderSalesOrder()
                          .getValue()
                          .toString();
        String unitTrainNum = (String) vo.getCurrentRow().getAttribute("TrainNumber");
        String isUpdated = "N";
        if (vo.getRowCount() > 0) {
            vo.first();
            RowSetIterator rowItr = vo.createRowSetIterator(null);
            while (rowItr.hasNext()) {
                CeixStsWaybillLinesVORowImpl tsRow = (CeixStsWaybillLinesVORowImpl) rowItr.next();
                if ((this.getHeaderSalesOrder().getValue() != null && !this.getHeaderSalesOrder()
                                                                           .getValue()
                                                                           .equals(""))) {
                    tsRow.setAttribute("SalesOrderNumber", this.getHeaderSalesOrder().getValue());
                    isUpdated = "Y";
                }
                if ((this.getHeaderNewTrainNumber().getValue() != null && !this.getHeaderNewTrainNumber()
                                                                               .getValue()
                                                                               .equals(""))) {
                    tsRow.setAttribute("NewTrainNumber", this.getHeaderNewTrainNumber().getValue());
                    isUpdated = "Y";
                }
                if (this.getHeaderComments().getValue() != null && this.getHeaderComments().getValue() != "") {
                    String comments = tsRow.getComments();
                    if (comments == null)
                        comments = "";
                    else
                        comments = comments + "|";
                    comments = comments + this.getHeaderComments().getValue();
                    tsRow.setAttribute("Comments", comments);
                }
            }
            Transaction transaction = appM.getTransaction();
            transaction.commit();
            vo.executeQuery();
            resetAssignSalesOrderNewTrainFields();

            rowItr.closeRowSetIterator();
            //Update RailSchedules
            if ("Y".equals(isUpdated)) {
                String str =
                    "update ceix_sts_rail_schedules set sales_order_number=:1 where train_id=:2 and sysdate between load_date-30 and load_date+30";
                CallableStatement st = null;
                try {
                    st = appM.getDBTransaction().createCallableStatement(str, 0);
                    //st.setString(1, (String)this.getHeaderSalesOrder().getValue());
                    st.setString(1, newHeaderNo);
                    st.setString(2, unitTrainNum);
                    st.execute();
                    appM.getDBTransaction().commit();
                } catch (SQLException e) {
                    System.out.println(e.toString());
                }
                String str2 =
                    "update CEIX_ORD_QUAL_LAB_RESLT set order_number=:1 where train=:2 and sysdate between datesamp-30 and datesamp+30";
                CallableStatement st18 = null;
                try {
                    st18 = appM.getDBTransaction().createCallableStatement(str2, 0);
                    //st.setString(1, (String)this.getHeaderSalesOrder().getValue());
                    st18.setString(1, newHeaderNo);
                    st18.setString(2, unitTrainNum);
                    st18.execute();
                    appM.getDBTransaction().commit();
                } catch (SQLException e) {
                    System.out.println(e.toString());
                }
            }
        }
        this.getHeaderSalesOrder().setValue("");
        this.getHeaderComments().setValue("");
        this.onSearchCall();
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getHeaderTable());


    }

    private void resetAssignSalesOrderNewTrainFields() {
        this.getHeaderSalesOrder().setValue(null);
        this.getHeaderSalesOrder().resetValue();
        this.getHeaderNewTrainNumber().setValue(null);
        this.getHeaderNewTrainNumber().resetValue();
        this.getHeaderComments().setValue(null);
        this.getHeaderComments().resetValue();
    }

    /**Make sure at least one of the two fields are present: Order Number or Train Number
     * @return
     */
    private boolean assignSalesOrderNumRequiredFieldsMissing() {

        return ((headerSalesOrder.getValue() == null || headerSalesOrder.getValue().equals("")) &&
                (headerNewTrainNumber.getValue() == null || headerNewTrainNumber.getValue().equals("")));

    }

    private boolean orderNumberIsValid(Object salesOrderNumber) {

        String salesOrdNum = salesOrderNumber.toString();
        //validate against order headers table
        BindingContext bindingctx = BindingContext.getCurrent();
        DCDataControl dc = bindingctx.dataControlFrame().findDataControl("CeixStsWaybillAMDataControl");
        CeixStsWaybillAMImpl appM = (CeixStsWaybillAMImpl) dc.getDataProvider();

        CallableStatement st = null;
        String output = null;
        try {
            /* st =
 appM.getDBTransaction().createCallableStatement("BEGIN" + "  SELECT ORDER_NUMBER" +
                                                 "    INTO :1" +
                                                 "  FROM CEIX_ORDER_HEADERS" +
                                                 " WHERE ORDER_NUMBER = :2;" +
                                                 "END;", 0);*/
            st =
                appM.getDBTransaction()
                .createCallableStatement("BEGIN" + "  SELECT CONTRACT_NUMBER" + "    INTO :1" +
                                         "  FROM CEIX_CONTRACT_HEADERS" + " WHERE CONTRACT_NUMBER = :2;" + " END;", 0);
            st.registerOutParameter(1, Types.VARCHAR);
            st.setString(2, salesOrdNum);
            st.execute();
            output = st.getString(1);
            //System.out.println ("The SQL value is-->" + output);
            if (output != null && !output.equals(""))
                return true;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public void showAlert(String title, String msg, javax.faces.application.FacesMessage.Severity severity) {

        StringBuilder message = new StringBuilder("<html><body>");
        message.append(msg);
        message.append("</body></html>");
        FacesMessage fm = new FacesMessage(message.toString());
        fm.setSeverity(severity);
        FacesContext fctx = FacesContext.getCurrentInstance();
        fctx.addMessage(title, fm);

    }

    private void showInvalidSalesOrderNumberError() {

        showAlert("Assign Sales Agreement",
                  "<p>Sales Agreement not found. Please provide correct Sales Agreement Number to proceed.</p>",
                  FacesMessage.SEVERITY_ERROR);

    }

    public void setHeaderNewTrainNumber(RichInputText headerNewTrainNumber) {
        this.headerNewTrainNumber = headerNewTrainNumber;
    }

    public RichInputText getHeaderNewTrainNumber() {
        return headerNewTrainNumber;
    }

    public void returnShipDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome() == DialogEvent.Outcome.yes) {
            //System.out.println("I am in Yes");
            FacesContext context = FacesContext.getCurrentInstance();
            BindingContext bindingctx = BindingContext.getCurrent();
            DCDataControl dc = bindingctx.dataControlFrame().findDataControl("CeixStsWaybillAMDataControl");
            CeixStsWaybillAMImpl appM = (CeixStsWaybillAMImpl) dc.getDataProvider();
            //System.out.println ("onRestShip: Start");
            ViewObject vo = appM.getCeixStsWaybillSearchVO1();
            if (vo.getCurrentRow() != null) {
                if (vo.getCurrentRow().getAttribute("ShipmentNumber") != null) {
                    //System.out.println("The values are :"+vo.getCurrentRow().getAttribute("OrderNumber")+";"+vo.getCurrentRow().getAttribute("UnitTrainNumber"));
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = new Date();
                    try {
                        date = sdf.parse(vo.getCurrentRow()
                                           .getAttribute("DumpDate")
                                           .toString());
                    } catch (Exception e) {
                        //System.out.println("Error: Exception.");
                        mylog.info("Error: Exception.");
                        e.printStackTrace();
                    }
                    CallableStatement st1 = null;
                    CallableStatement st11 = null;
                    try {
                        DateFormat curDate = new SimpleDateFormat("yyyy-MM-dd");
                        curDate.setTimeZone(TimeZone.getTimeZone("America/New_York"));
                        String currDate = curDate.format(new Date()).toString();
                        String shipnum = vo.getCurrentRow()
                                           .getAttribute("ShipmentNumber")
                                           .toString();
                        st1 =
                            appM.getDBTransaction()
                            .createCallableStatement(" BEGIN " + " UPDATE ceix_sts_waybill_btmore_lines " +
                                                     " SET comments = decode(comments, null,'',comments||';')||'Shipment Reset:" +
                                                     vo.getCurrentRow().getAttribute("ShipmentNumber") + " on " +
                                                     currDate + "'," +
                                                     " shipment_number = null, processed_flag = null, attribute1 = null,freight=null " +
                                                     " WHERE shipment_number ='" + shipnum + "'; " + " COMMIT; " +
                                                     " END; ", 0);
                        //System.out.println("The SQL is"+);
                        st1.execute();
                        FacesMessage message =
                            new FacesMessage(FacesMessage.SEVERITY_INFO,
                                             "Reset Train:" + vo.getCurrentRow().getAttribute("TrainNumber"), null);
                        context.addMessage("Confirmation", message);
                        //System.out.println ("Reset Train :"+vo.getCurrentRow().getAttribute("TrainNumber"));

                    } catch (SQLException e) {
                        e.printStackTrace();
                        FacesMessage message =
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                             "Cannot reset SQL Train:" +
                                             vo.getCurrentRow().getAttribute("TrainNumber") + throwableToString(e),
                                             null);
                        context.addMessage("Error", message);
                    } finally {
                        try {
                            st1.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                            FacesMessage message =
                                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                 "Cannot reset SQL2 Train:" +
                                                 vo.getCurrentRow().getAttribute("TrainNumber") + throwableToString(e),
                                                 null);
                            context.addMessage("Error", message);
                        }
                    }
                } else {
                    //System.out.println("The shipmentnumber is  null");
                    FacesMessage message =
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                         "Cannot RESET this Train which is not Shipped yet:" +
                                         vo.getCurrentRow().getAttribute("TrainNumber"), null);
                    context.addMessage("Error", message);
                }
            }
            this.onSearchCall();
            AdfFacesContext.getCurrentInstance().addPartialTarget(getHeaderTable());
        } else {
            System.out.println("I am in No");
        }
    }

    ////Code added by Meghna Kabnurkar on 14-Oct-2019 for CONSOL2-1063
    ////https://astcorporation.atlassian.net/browse/CONSOL2-1063
    ////End user is unable to delete trains in shipments

    /**
     *
     * @param dialogEvent
     */
    public void dialogDeleteListner(DialogEvent dialogEvent) {
        mylog.log(Level.FINER, ">>dialogDeleteListner");

        try {
            if (dialogEvent != null) {
                if (dialogEvent.getOutcome() != null) {
                    if (dialogEvent.getOutcome() == DialogEvent.Outcome.ok) {
                        CeixStsWaybillAMImpl appM = getAM();
                        if (appM != null) {
                            CeixStsWaybillSearchVOImpl vOSummary =
                                (CeixStsWaybillSearchVOImpl) appM.getCeixStsWaybillSearchVO1();
                            if (vOSummary != null) {
                                Row rowSummary = model.getCurrentRow("CeixStsWaybillSearchVO1Iterator");
                                if (rowSummary != null) {
                                    if (rowSummary.getAttribute("TrainNumber") != null) {
                                        dialogDeleteListnerSqlOperation(appM, rowSummary);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (SQLException sqle) {
            FacesMessage msg =
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                 "SQLException (CeixStsShipmentsMB - dialogDeleteListner): " + throwableToString(sqle),
                                 null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            mylog.log(Level.FINER, ">>SQLException (CeixStsShipmentsMB - dialogDeleteListner): " + sqle.getMessage());
        } catch (Exception e) {
            FacesMessage msg =
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                 "Exception (CeixStsShipmentsMB - dialogDeleteListner): " + throwableToString(e), null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            mylog.log(Level.FINER, ">>Exception (CeixStsShipmentsMB - dialogDeleteListner): " + e.getMessage());
        }
    }

    /**
     *
     * @param appM
     * @param rowSummary
     * @throws SQLException
     */
    private void dialogDeleteListnerSqlOperation(CeixStsWaybillAMImpl appM, Row rowSummary) throws SQLException {
        mylog.log(Level.FINER, ">>trainDeleteOperation");

        String strUnitTrainNumber;
        String strDeliveryHeaderId;
        String strDeleteBy;
        String strStatus;
        int intHdrRows;
        int intLnRows;
        strUnitTrainNumber = rowSummary.getAttribute("TrainNumber")
                                       .toString()
                                       .trim();

        String dumpDate = rowSummary.getAttribute("DumpDate")
                                    .toString()
                                    .trim();

        if (strUnitTrainNumber != null &&
            !strUnitTrainNumber.isEmpty()) {
            //            strDeliveryHeaderId =
            //                    model.executeWithParams("ExecuteWithParams1",
            //                                            "bUnitNumber",
            //                                            strUnitTrainNumber,
            //                                            "CeixStsWaybillBtmoreHdrDeleteTrainVo1Iterator",
            //                                            "DeliveryHeaderId");


            CallableStatement csHdrIDRetrival =
                              appM.getDBTransaction()
                              .createCallableStatement(" BEGIN" + " CEIX_PROC_WAYBILL_DELIVER_HEADER_ID (:1,:2,:3);" +
                                                       " END;", 0);
            csHdrIDRetrival.setString(1, strUnitTrainNumber);
            csHdrIDRetrival.setString(2, dumpDate);
            csHdrIDRetrival.registerOutParameter(3, Types.INTEGER);
            boolean result = csHdrIDRetrival.execute();

            System.out.println(result + " ------- result executed store procedure");
            strDeliveryHeaderId = String.valueOf(csHdrIDRetrival.getInt(3));


            if (strDeliveryHeaderId != null && !strDeliveryHeaderId.isEmpty()) {
                strDeleteBy = ADFContext.getCurrent()
                                        .getSecurityContext()
                                        .getUserName();
                CallableStatement csHdrDel =
                    appM.getDBTransaction()
                    .createCallableStatement(" BEGIN" + " ceix_delete_sts_waybill_trains(:1,:2,:3,:4,:5,:6,:7);" +
                                             " END;", 0);
                System.out.println("Header id for deletion #################  " + strDeliveryHeaderId +
                                   " , dump date--  " + dumpDate + "  unitnumber is  " + strUnitTrainNumber);


                csHdrDel.setString(1, strDeliveryHeaderId);
                csHdrDel.setString(2, strUnitTrainNumber);
                csHdrDel.setString(3, null);
                csHdrDel.setString(4, strDeleteBy);
                csHdrDel.registerOutParameter(5, Types.INTEGER);
                csHdrDel.registerOutParameter(6, Types.INTEGER);
                csHdrDel.registerOutParameter(7, Types.VARCHAR);
                csHdrDel.execute();
                intHdrRows = csHdrDel.getInt(5);
                intLnRows = csHdrDel.getInt(6);
                strStatus = csHdrDel.getString(7);
                csHdrDel.close();
                if (intHdrRows > 0) {
                    rowSummary.remove();
                    appM.getDBTransaction().commit();
                    AdfFacesContext.getCurrentInstance().addPartialTarget(headerTable);
                    onTableSelList();
                    AdfFacesContext.getCurrentInstance().addPartialTarget(detailTable);
                }
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, strStatus, "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } else {
            mylog.log(Level.FINER,
                      ">>trainDeleteOperation-- Record does not exist in ceix_sts_delivery_header for this Unit Train Number");

        }
    }

    ////Code added by Meghna Kabnurkar on 14-Oct-2019 for CONSOL2-1063
    ////https://astcorporation.atlassian.net/browse/CONSOL2-1063
    ////End user is unable to delete trains in shipments

    /**
     *
     * @param dialogEvent
     */
    public void dialogDetailDeleteListner(DialogEvent dialogEvent) {
        mylog.log(Level.FINER, ">>dialogDetailDeleteListner");

        String strDeliveryLineId = null;
        try {
            if (dialogEvent.getOutcome() == DialogEvent.Outcome.ok) {
                CeixStsWaybillAMImpl appM = getAM();
                if (appM != null) {
                    CeixStsWaybillLinesVOImpl vODetail = (CeixStsWaybillLinesVOImpl) appM.getCeixStsWaybillLinesVO1();
                    if (vODetail != null) {
                        Row rowDetail = vODetail.getCurrentRow();
                        if (rowDetail != null) {
                            dialogDetailDeleteListnerSqlOperation(strDeliveryLineId, appM, rowDetail);
                        }
                    }
                }
            }
        } catch (Exception e) {
            FacesMessage msg =
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                 "Exception (CeixStsWaybillMB - dialogDetailDeleteListner): " + throwableToString(e),
                                 null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            mylog.log(Level.FINER, ">>Exception (CeixStsWaybillMB - dialogDetailDeleteListner): " + e.getMessage());
        }
    }

    /**
     *
     * @param strDeliveryLineId
     * @param appM
     * @param rowDetail
     * @throws SQLException
     */
    private void dialogDetailDeleteListnerSqlOperation(String strDeliveryLineId, CeixStsWaybillAMImpl appM,
                                                       Row rowDetail) throws SQLException {

        mylog.log(Level.FINER, ">>dialogDetailDeleteListnerSqlOperation");

        String strDeleteBy;
        String strStatus;
        int intLnRows;
        int intHdrRows;
        if (rowDetail.getAttribute("DeliveryLineId") != null) {
            strDeliveryLineId = rowDetail.getAttribute("DeliveryLineId")
                                         .toString()
                                         .trim();
        }
        if (strDeliveryLineId != null && !strDeliveryLineId.isEmpty()) {
            strDeleteBy = ADFContext.getCurrent()
                                    .getSecurityContext()
                                    .getUserName();
            CallableStatement csLnDel =
                appM.getDBTransaction()
                .createCallableStatement(" BEGIN " + " ceix_delete_sts_waybill_trains(:1,:2,:3,:4,:5,:6,:7); " +
                                         " END; ", 0);
            csLnDel.setString(1, null);
            csLnDel.setString(2, null);
            csLnDel.setString(3, strDeliveryLineId);
            csLnDel.setString(4, strDeleteBy);
            csLnDel.registerOutParameter(5, Types.INTEGER);
            csLnDel.registerOutParameter(6, Types.INTEGER);
            csLnDel.registerOutParameter(7, Types.VARCHAR);
            csLnDel.execute();
            intHdrRows = csLnDel.getInt(5);
            intLnRows = csLnDel.getInt(6);
            strStatus = csLnDel.getString(7);
            csLnDel.close();
            if (intLnRows > 0) {
                AdfFacesContext.getCurrentInstance().addPartialTarget(headerTable);
                onTableSelList();
                AdfFacesContext.getCurrentInstance().addPartialTarget(detailTable);
            }
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, strStatus, "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    private CeixStsWaybillAMImpl getAM() {
        FacesContext context = FacesContext.getCurrentInstance();
        BindingContext bindingContext = BindingContext.getCurrent();
        DCDataControl dc = bindingContext.findDataControl("CeixStsWaybillAMDataControl");
        CeixStsWaybillAMImpl appM = (CeixStsWaybillAMImpl) dc.getDataProvider();
        return appM;
    }

    public void onTableSelList() {
        //enable assign sales order button
        disableAssignSalesOrdBtn(false);
        BindingContext bindingctx = BindingContext.getCurrent();
        DCDataControl dc = bindingctx.dataControlFrame().findDataControl("CeixStsWaybillAMDataControl");
        if (dc != null) {
            CeixStsWaybillAMImpl appM = (CeixStsWaybillAMImpl) dc.getDataProvider();
            if (appM != null) {
                ViewObject vo = appM.getCeixStsWaybillSearchVO1();
                if (vo != null) {
                    Row selectedRow = (Row) evaluateEL("#{bindings.CeixStsWaybillSearchVO1Iterator.currentRow}");
                    //System.out.println("The name is :"+selectedRow.getAttribute("TrainNumber"));
                    if (selectedRow != null) {
                        vo.setCurrentRow(selectedRow);
                        //System.out.println("Second Current row:"+vo.getCurrentRow().getAttribute("TrainNumber"));
                        vo.getCurrentRow().setAttribute("EditFlag", "Y");
                        //System.out.println("The flagselect is:"+vo.getCurrentRow().getAttribute("EditFlag"));
                        ViewObject vol = appM.getCeixStsWaybillLinesVO1();
                        vol.reset();
                        vol.clearCache();
                        vol.setWhereClause(null);
                        //String st = "N";
                        String wc = "";
                        if (selectedRow.getAttribute("TrainNumber") != null) {
                            String TrainNum = selectedRow.getAttribute("TrainNumber").toString();
                            wc = "train_number = '" + TrainNum + "'";
                            //System.out.println("Lines Train:"+TrainNum);
                        } else {
                            wc = "train_number is null ";
                        }
                        if (selectedRow.getAttribute("OrderNumber") != null) {
                            String SalesNum = selectedRow.getAttribute("OrderNumber").toString();

                            wc = wc + " and sales_order_number = '" + SalesNum + "'";
                            //System.out.println("Lines sales:"+SalesNum);
                        } else {
                            wc = wc + " and sales_order_number is null ";
                        }
                        if (selectedRow.getAttribute("ShipmentNumber") != null) {
                            String shipnum = selectedRow.getAttribute("ShipmentNumber").toString();
                            wc = wc + " and shipment_number = '" + shipnum + "'";
                            //System.out.println("Lines shipment:"+shipnum);
                        } else {
                            wc = wc + " and shipment_number is null ";
                        }
                        if (vo.getCurrentRow().getAttribute("DumpDate") != null) {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            Date date = new Date();
                            try {
                                date = sdf.parse(vo.getCurrentRow()
                                                   .getAttribute("DumpDate")
                                                   .toString());
                            } catch (ParseException e) {
                                //System.out.println("Error: Invalid Date Parser Exception.");
                                mylog.info("Error: Invalid Date Parser Exception.");
                                e.printStackTrace();
                            } catch (Exception e) {
                                //System.out.println("Error: Exception.");
                                mylog.info("Error: Exception.");
                                e.printStackTrace();
                            }
                            wc = wc + " and dump_date = TO_DATE('" + sdf.format(date) + "','yyyy-MM-dd')";
                        } else {
                            wc = wc + " and dump_date is null ";
                        }
                        if (vo.getCurrentRow().getAttribute("SoNumber") != null) {
                            String OrderNum = vo.getCurrentRow()
                                                .getAttribute("SoNumber")
                                                .toString();
                            wc = wc + " and Attribute1 = '" + OrderNum + "'";

                        } else {
                            wc = wc + " and Attribute1 is null ";
                        }
                        if (vo.getCurrentRow().getAttribute("Straggler") != null) {
                            String straggler = vo.getCurrentRow()
                                                 .getAttribute("Straggler")
                                                 .toString();
                            wc =
                                wc + " and nvl(straggler,'N') = '" + straggler +
                                "'"; // This is from ceix_sts_delivery_lines table

                        } else {
                            wc = wc + " and nvl(straggler,'N') ='N' ";
                        }
                        vol.setWhereClause(wc);
                        vol.executeQuery();
                        System.out.println("Query (CeixStsWaybillMB - onTableSelList): " + vol.getQuery());
                        reset_BT_CB();
                        AdfFacesContext.getCurrentInstance().addPartialTarget(getDetailPC());
                    }
                }
            }
        }
    }

    public void setHeaderTable(RichTable headerTable) {
        this.headerTable = headerTable;
    }

    public RichTable getHeaderTable() {
        return headerTable;
    }

    public void setDetailTable(RichTable detailTable) {
        this.detailTable = detailTable;
    }

    public RichTable getDetailTable() {
        return detailTable;
    }

    public void onStragglerBT(ActionEvent actionEvent) {
        int i = 0;
        BindingContext bindingctx = BindingContext.getCurrent();
        DCDataControl dc = bindingctx.dataControlFrame().findDataControl("CeixStsWaybillAMDataControl");
        CeixStsWaybillAMImpl appM = (CeixStsWaybillAMImpl) dc.getDataProvider();
        ViewObject vo = appM.findViewObject("CeixStsWaybillLinesVO1");
        if (vo.getRowCount() > 0) {
            Row curRow = vo.first();
            for (i = 0; i < vo.getRowCount(); i++) {
                if (curRow.getAttribute("SelectFlag") != null) {
                    //System.out.println("The value is :"+curRow.getAttribute("SelectFlag").toString());
                    if (curRow.getAttribute("SelectFlag")
                              .toString()
                              .compareTo("true") == 0) {
                        curRow.setAttribute("Straggler", "Y");
                    }
                }
                curRow = vo.next();
            }
        }
        Transaction transaction = appM.getTransaction();
        transaction.commit();
        vo.executeQuery();
        this.onSearchCall();
        AdfFacesContext.getCurrentInstance().addPartialTarget(getHeaderTable());
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message =
            new FacesMessage(FacesMessage.SEVERITY_INFO, "Changed Waybill Lines as Stragglers.", null);
        context.addMessage("Confirmation", message);
    }

    public void setStragglerBT(RichCommandButton stragglerBT) {
        this.stragglerBT = stragglerBT;
    }

    public RichCommandButton getStragglerBT() {
        return stragglerBT;
    }

    public void onRemStrBT(ActionEvent actionEvent) {
        int i = 0;
        BindingContext bindingctx = BindingContext.getCurrent();
        DCDataControl dc = bindingctx.dataControlFrame().findDataControl("CeixStsWaybillAMDataControl");
        CeixStsWaybillAMImpl appM = (CeixStsWaybillAMImpl) dc.getDataProvider();
        ViewObject vo = appM.findViewObject("CeixStsWaybillLinesVO1");
        if (vo.getRowCount() > 0) {
            Row curRow = vo.first();
            for (i = 0; i < vo.getRowCount(); i++) {
                if (curRow.getAttribute("SelectFlag") != null) {
                    //System.out.println("The value is :"+curRow.getAttribute("SelectFlag").toString());
                    if (curRow.getAttribute("SelectFlag")
                              .toString()
                              .compareTo("true") == 0) {
                        curRow.setAttribute("Straggler", "");
                    }
                }
                curRow = vo.next();
            }
        }
        Transaction transaction = appM.getTransaction();
        transaction.commit();
        vo.executeQuery();
        this.onSearchCall();
        AdfFacesContext.getCurrentInstance().addPartialTarget(getHeaderTable());
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message =
            new FacesMessage(FacesMessage.SEVERITY_INFO, "Removed Straggler flag from Selected lines.", null);
        context.addMessage("Confirmation", message);
    }

    public void setRemoveStragglerBT(RichCommandButton removeStragglerBT) {
        this.removeStragglerBT = removeStragglerBT;
    }

    public RichCommandButton getRemoveStragglerBT() {
        return removeStragglerBT;
    }
}
