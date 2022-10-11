package com.ceix.stsrail;


import com.ceix.stsrail.model.services.CeixStsRailSchedulesAMImpl;
import com.ceix.stsrail.model.views.CeixStsRailSchedulesHOLDVOImpl;
import com.ceix.stsrail.model.views.CeixStsRailSchedulesVOImpl;
import com.ceix.stsrail.model.views.CeixStsRailSchedulesVORowImpl;
import com.ceix.util.ADFUtil;

import com.ceix.util.ADFUtils;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

import java.util.logging.Level;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCDataControl;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.RichQuery;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.CalendarActivityEvent;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupFetchEvent;
import oracle.adf.view.rich.model.CalendarActivity;
import oracle.adf.view.rich.model.QueryDescriptor;
import oracle.adf.view.rich.model.QueryModel;


import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.Transaction;
import oracle.jbo.ViewObject;
import oracle.jbo.server.ViewObjectImpl;


public class CeixStsRailScheduleMB {
    private RichPopup updatePopup;
    private RichPopup createPopup;
    private RichPopup holdUpdatePopup;
    private RichInputText ptrainInp;
    private RichSelectOneChoice pcustNumInp;
    private RichSelectOneChoice porderNumInp;


    private RichTable _searchDialogTable1;
    private RichQuery _searchDialogQueryComponent1;

    private RichPopup _searchCustomerPopup_mainScreen;
    private RichPopup _searchCustomerPopup_createScreen;
    private RichPopup _searchCustomerPopup_updateScreen;
    private RichInputText pcontrNumInp;
    private RichSelectOneChoice porderNumInp_createScreen;
    private RichSelectOneChoice porderNumInp_updateScreen;
    private RichInputText pdestination;
    private RichPopup _popupMassUpdateOrderNumber;
    private RichInputText _popup_OrderNumber;
    private RichQuery _orderNumberUpdatePopupQuery;
    private RichSelectOneChoice porderNumInp_CopyScreen;
    private RichPopup _searchCustomerPopup_copyScreen;
    private RichTable thold;
    private RichTable rails;
    private RichPopup copyAndCreatePopup;
    private RichSelectOneChoice pitemNumInp_updateScreen;
    private RichSelectOneChoice pitemNumInp_createScreen;
    private RichSelectOneChoice pitemNumInp_CopyScreen;
    private RichSelectOneChoice pordNumInp_HoldScreen;
    private RichSelectOneChoice pitemNumInp_HoldScreen;
    private RichSelectOneChoice pdestInp_HoldScreen;
    private RichSelectOneChoice pdestInp_CopyScreen;
    private RichSelectOneChoice pdestInp_createScreen;
    private RichSelectOneChoice pdestInp_updateScreen;
    private RichSelectOneChoice pmineLocInp;
    private RichInputDate ploadFromInp;
    private RichInputDate ploadToInp;

    public CeixStsRailScheduleMB() {
    }

    public void setUpdatePopup(RichPopup updatePopup) {
        this.updatePopup = updatePopup;
    }

    public RichPopup getUpdatePopup() {
        return updatePopup;
    }

    public void setCreatePopup(RichPopup createPopup) {
        this.createPopup = createPopup;
    }

    public RichPopup getCreatePopup() {
        return createPopup;
    }

    public void onCalActList(CalendarActivityEvent calendarActivityEvent) {
        CalendarActivity activity =
            calendarActivityEvent.getCalendarActivity();
        //System.out.println("In activityList ");
        if (activity != null) {
            //System.out.println("In activityList :"+activity);
            DCBindingContainer dcbindings =
                (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
            DCIteratorBinding iterator =
                dcbindings.findIteratorBinding("CeixStsRailSchedulesVO1Iterator");
            //System.out.println("In activityList 1 :"+activity.getId());
            Key key = new Key(new Object[] { activity.getId() });
            //System.out.println("In activityList 22 :"+key);
            RowSetIterator rsi = iterator.getRowSetIterator();
            Row row = rsi.findByKey(key, 1)[0];
            rsi.setCurrentRow(row);
            //System.out.println("In activityList END");
            BindingContext bindingctx = BindingContext.getCurrent();
            DCDataControl dc =
                bindingctx.dataControlFrame().findDataControl("CeixStsRailSchedulesAMDataControl");
            CeixStsRailSchedulesAMImpl appM =
                (CeixStsRailSchedulesAMImpl)dc.getDataProvider();
            ViewObject vo = appM.getCeixStsRailSchedulesVO1();
            //System.out.println("Current ROw is:"+vo.getCurrentRow().getAttribute("RailScheduleId"));
            //System.out.println("Current ROw is:"+vo.getCurrentRow().getAttribute("SalesOrderNumber"));
            //System.out.println("Current ROw is:"+vo.getCurrentRow().getAttribute("CustomerNumber"));
            ViewObject abc = appM.getSalesOrderVO1();
            abc.reset();
            abc.clearCache();
            abc.getWhereClauseParams();
            abc.setNamedWhereClauseParam("p_acct_number",
                                         vo.getCurrentRow().getAttribute("CustomerNumber"));
            abc.executeQuery();
            
            String orderNumber;
            if (row != null) {
                if (row.getAttribute("SalesOrderNumber") != null) {
                    orderNumber = row.getAttribute("SalesOrderNumber").toString();
                    ViewObject itemVO = appM.getContractItemsVO1();
                    itemVO.reset();
                    itemVO.clearCache();
                    itemVO.getWhereClauseParams();
                    itemVO.setNamedWhereClauseParam("pContractNumber", orderNumber);
                    itemVO.executeQuery();

                    ViewObject destVO = appM.getDestinationVO1();
                    destVO.reset();
                    destVO.clearCache();
                    destVO.getWhereClauseParams();
                    destVO.setNamedWhereClauseParam("pcontractNum", orderNumber);
                    destVO.executeQuery();
                }
            }
            
        }
    }

    public void onCustomerSel(ValueChangeEvent valueChangeEvent) {
        //System.out.println("Cust Value got changed:"+valueChangeEvent.getNewValue());
        BindingContext bindingctx = BindingContext.getCurrent();
        DCDataControl dc =
            bindingctx.dataControlFrame().findDataControl("CeixStsRailSchedulesAMDataControl");
        CeixStsRailSchedulesAMImpl appM =
            (CeixStsRailSchedulesAMImpl)dc.getDataProvider();
        ViewObject abc = appM.getSalesOrderVO1();
        abc.reset();
        abc.clearCache();
        abc.getWhereClauseParams();
        abc.setNamedWhereClauseParam("p_acct_number",
                                     valueChangeEvent.getNewValue());
        abc.executeQuery();
    }

    public void onSave(ActionEvent actionEvent) {
        BindingContext bindingctx = BindingContext.getCurrent();
        DCDataControl dc =
            bindingctx.dataControlFrame().findDataControl("CeixStsRailSchedulesAMDataControl");
        CeixStsRailSchedulesAMImpl appM =
            (CeixStsRailSchedulesAMImpl)dc.getDataProvider();
        ViewObject abc = appM.getCeixStsRailSchedulesVO1();
        //System.out.println("The rail id:"+abc.getCurrentRow().getAttribute("RailScheduleId"));
        Transaction transaction = appM.getTransaction();
        transaction.commit();
        if (actionEvent.getComponent().getId().equals("cb1")) {
//            RichPopup popup = getUpdatePopup(); //Commented by Manasa Yalamarthy on 26th Sept,22 - Oracle 8013
//            popup.hide();
        } else if (actionEvent.getComponent().getId().equals("cb6")) {
            RichPopup popup = getHoldUpdatePopup();
            popup.hide();
        }
        abc.reset();
        abc.clearCache();
        abc.executeQuery();
        ViewObject ab = appM.getCeixStsRailSchedulesHOLDVO1();
        ab.reset();
        ab.clearCache();
        ab.executeQuery();
    }

    ////Code added by Meghna Kabnurkar on 19-Sep-2019 for CONSOL2-1250
    ////https://astcorporation.atlassian.net/browse/CONSOL2-1250
    ////Need to change the business logic in the rail schedule integrations

    public void onCopyAndCreate(ActionEvent actionEvent) {
        String strMessage = null;

        try {
            strMessage = callceix_insert_ns_csx_rs_adf();
            if (strMessage != null && !strMessage.isEmpty()) {
                strMessage = strMessage.trim();
                if (strMessage.toLowerCase().contains("success")) {
                    BindingContext bindingctx = BindingContext.getCurrent();
                    DCDataControl dc =
                        bindingctx.dataControlFrame().findDataControl("CeixStsRailSchedulesAMDataControl");
                    if (dc != null) {
                        CeixStsRailSchedulesAMImpl appM =
                            (CeixStsRailSchedulesAMImpl)dc.getDataProvider();
                        if (appM != null) {
                            Transaction transaction = appM.getTransaction();
                            transaction.commit();
                            showAlert("Status", strMessage,
                                      FacesMessage.SEVERITY_INFO);
                            RichPopup popup = getCopyAndCreatePopup();
                            popup.hide();
                            ViewObject vO = appM.getCeixStsRailSchedulesVO1();
                            if (vO != null) {
                                vO.reset();
                                vO.clearCache();
                                vO.executeQuery();
                            }
                            ViewObject vOHold =
                                appM.getCeixStsRailSchedulesHOLDVO1();
                            if (vOHold != null) {
                                vOHold.reset();
                                vOHold.clearCache();
                                vOHold.executeQuery();
                            }
                        }
                    }
                } else {
                    showAlert("Status", strMessage,
                              FacesMessage.SEVERITY_ERROR);
                    rollBackCeixStsRailSchedule();
                }
            } else {
                showAlert("Status", "Please contact Database Administrator",
                          FacesMessage.SEVERITY_ERROR);
                rollBackCeixStsRailSchedule();
            }
        } catch (Exception e) {
            showAlert("Exception",
                      "Please contact Database Administrator. Exception (CeixStsRailScheduleMB - onCopyAndCreate): " +
                      e.getMessage(), FacesMessage.SEVERITY_ERROR);
            rollBackCeixStsRailSchedule();
        }
    }

    ////Code added by Meghna Kabnurkar on 19-Sep-2019 for CONSOL2-1250
    ////https://astcorporation.atlassian.net/browse/CONSOL2-1250
    ////Need to change the business logic in the rail schedule integrations

    public void onCreate(ActionEvent actionEvent) {
        String strMessage = null;

        try {
            strMessage = callceix_insert_ns_csx_rs_adf();
            if (strMessage != null && !strMessage.isEmpty()) {
                strMessage = strMessage.trim();
                if (strMessage.toLowerCase().contains("success")) {
                    BindingContext bindingctx = BindingContext.getCurrent();
                    DCDataControl dc =
                        bindingctx.dataControlFrame().findDataControl("CeixStsRailSchedulesAMDataControl");
                    if (dc != null) {
                        CeixStsRailSchedulesAMImpl appM =
                            (CeixStsRailSchedulesAMImpl)dc.getDataProvider();
                        if (appM != null) {
                            Transaction transaction = appM.getTransaction();
                            transaction.commit();
                            showAlert("Status", strMessage,
                                      FacesMessage.SEVERITY_INFO);
                            RichPopup popup = getCreatePopup();
                            popup.hide();
                            ViewObject vO = appM.getCeixStsRailSchedulesVO1();
                            if (vO != null) {
                                vO.reset();
                                vO.clearCache();
                                vO.executeQuery();
                            }
                            ViewObject vOHold =
                                appM.getCeixStsRailSchedulesHOLDVO1();
                            if (vOHold != null) {
                                vOHold.reset();
                                vOHold.clearCache();
                                vOHold.executeQuery();
                            }
                        }
                    }
                } else {
                    showAlert("Status", strMessage,
                              FacesMessage.SEVERITY_ERROR);
                    rollBackCeixStsRailSchedule();
                }
            } else {
                showAlert("Status", "Please contact Database Administrator",
                          FacesMessage.SEVERITY_ERROR);
                rollBackCeixStsRailSchedule();
            }
        } catch (Exception e) {
            showAlert("Exception",
                      "Please contact Database Administrator. Exception (CeixStsRailScheduleMB - onCreate): " +
                      e.getMessage(), FacesMessage.SEVERITY_ERROR);
            rollBackCeixStsRailSchedule();
        }
    }

    private void rollBackCeixStsRailSchedule() {
        BindingContext bindingctx = BindingContext.getCurrent();
        DCDataControl dc =
            bindingctx.dataControlFrame().findDataControl("CeixStsRailSchedulesAMDataControl");
        if (dc != null) {
            CeixStsRailSchedulesAMImpl appM =
                (CeixStsRailSchedulesAMImpl)dc.getDataProvider();
            if (appM != null) {
                Transaction transaction = appM.getTransaction();
                transaction.rollback();
                ViewObject vO = appM.getCeixStsRailSchedulesVO1();
                if (vO != null) {
                    vO.reset();
                    vO.clearCache();
                    vO.executeQuery();
                }
                ViewObject vOHold = appM.getCeixStsRailSchedulesHOLDVO1();
                if (vOHold != null) {
                    vOHold.reset();
                    vOHold.clearCache();
                    vOHold.executeQuery();
                }
            }
        }
    }

    ////Code added by Meghna Kabnurkar on 19-Sep-2019 for CONSOL2-1250
    ////https://astcorporation.atlassian.net/browse/CONSOL2-1250
    ////Need to change the business logic in the rail schedule integrations

    //    private String callceix_insert_ns_csx_rs_adf() throws SQLException {
    //        String strSalesOrderNumber = null;
    //        String strTrainId = null;
    //        String strLoadOrder = null;
    //        String strLoadDate = null;
    //        String strDestination = null;
    //        String strDestinationEta = null;
    //        String strConsignee = null;
    //        String strLoadOrigin = null;
    //        String strOriginEta = null;
    //        String strReservationStatus = null;
    //        String strRequest = null;
    //        String strOrderPlaceDate = null;
    //        String strRequestedLoadDate = null;
    //        String strCars = null;
    //        String strTons = null;
    //        String strCoalClass = null;
    //        String strWeigh = null;
    //        String strLoads = null;
    //        String strMty = null;
    //        String strWaybillDate = null;
    //        String strWaybill = null;
    //        String strLastTrain = null;
    //        String strCity = null;
    //        String strLastEventTime = null;
    //        String strCurrentSet = null;
    //        String strCurrentStatus = null;
    //        String strHoldFlag = null;
    //        String strCarrier = null;
    //        String strProblem = null;
    //        String strComments = null;
    //        String strCustomerContract = null;
    //        String strRr = null;
    //        String strSource = null;
    //        String strMessage = null;
    //
    //        BindingContext bindingctx = BindingContext.getCurrent();
    //        DCDataControl dc =
    //            bindingctx.dataControlFrame().findDataControl("CeixStsRailSchedulesAMDataControl");
    //        if (dc != null) {
    //            CeixStsRailSchedulesAMImpl appM =
    //                (CeixStsRailSchedulesAMImpl)dc.getDataProvider();
    //            if (appM != null) {
    //                CeixStsRailSchedulesVOImpl vO =
    //                    appM.getCeixStsRailSchedulesVO1();
    //                if (vO != null) {
    //                    CeixStsRailSchedulesVORowImpl row =
    //                        (CeixStsRailSchedulesVORowImpl)vO.getCurrentRow();
    //                    if (row != null) {
    //                        if (row.getSalesOrderNumber() != null) {
    //                            strSalesOrderNumber =
    //                                    row.getSalesOrderNumber().toString().trim();
    //                        }
    //                        if (row.getTrainId() != null) {
    //                            strTrainId = row.getTrainId().toString().trim();
    //                        }
    //                        if (row.getLoadOrder() != null) {
    //                            strLoadOrder =
    //                                    row.getLoadOrder().toString().trim();
    //                        }
    //                        if (row.getLoadDate() != null) {
    //                            strLoadDate = row.getLoadDate().toString().trim();
    //                        }
    //                        if (row.getDestination() != null) {
    //                            strDestination =
    //                                    row.getDestination().toString().trim();
    //                        }
    //                        if (row.getDestinationEta() != null) {
    //                            strDestinationEta =
    //                                    row.getDestinationEta().toString().trim();
    //                        }
    //                        if (row.getConsignee() != null) {
    //                            strConsignee =
    //                                    row.getConsignee().toString().trim();
    //                        }
    //                        if (row.getLoadOrigin() != null) {
    //                            strLoadOrigin =
    //                                    row.getLoadOrigin().toString().trim();
    //                        }
    //                        if (row.getOriginEta() != null) {
    //                            strOriginEta =
    //                                    row.getOriginEta().toString().trim();
    //                        }
    //                        if (row.getReservationStatus() != null) {
    //                            strReservationStatus =
    //                                    row.getReservationStatus().toString().trim();
    //                        }
    //                        if (row.getRequest() != null) {
    //                            strRequest = row.getRequest().toString().trim();
    //                        }
    //                        if (row.getOrderPlaceDate() != null) {
    //                            strOrderPlaceDate =
    //                                    row.getOrderPlaceDate().toString().trim();
    //                        }
    //                        if (row.getRequestedLoadDate() != null) {
    //                            strRequestedLoadDate =
    //                                    row.getRequestedLoadDate().toString().trim();
    //                        }
    //                        if (row.getCars() != null) {
    //                            strCars = row.getCars().toString().trim();
    //                        }
    //                        if (row.getTons() != null) {
    //                            strTons = row.getTons().toString().trim();
    //                        }
    //                        if (row.getCoalClass() != null) {
    //                            strCoalClass =
    //                                    row.getCoalClass().toString().trim();
    //                        }
    //                        if (row.getWeigh() != null) {
    //                            strWeigh = row.getWeigh().toString().trim();
    //                        }
    //                        if (row.getLoads() != null) {
    //                            strLoads = row.getLoads().toString().trim();
    //                        }
    //                        if (row.getMty() != null) {
    //                            strMty = row.getMty().toString().trim();
    //                        }
    //                        if (row.getWaybillDate() != null) {
    //                            strWaybillDate =
    //                                    row.getWaybillDate().toString().trim();
    //                        }
    //                        if (row.getWaybill() != null) {
    //                            strWaybill = row.getWaybill().toString().trim();
    //                        }
    //                        if (row.getLastTrain() != null) {
    //                            strLastTrain =
    //                                    row.getLastTrain().toString().trim();
    //                        }
    //                        if (row.getCity() != null) {
    //                            strCity = row.getCity().toString().trim();
    //                        }
    //                        if (row.getLastEventTime() != null) {
    //                            strLastEventTime =
    //                                    row.getLastEventTime().toString().trim();
    //                        }
    //                        if (row.getCurrentSet() != null) {
    //                            strCurrentSet =
    //                                    row.getCurrentSet().toString().trim();
    //                        }
    //                        if (row.getCurrentStatus() != null) {
    //                            strCurrentStatus =
    //                                    row.getCurrentStatus().toString().trim();
    //                        }
    //                        if (row.getHoldFlag() != null) {
    //                            strHoldFlag = row.getHoldFlag().toString().trim();
    //                        }
    //                        if (row.getCarrier() != null) {
    //                            strCarrier = row.getCarrier().toString().trim();
    //                        }
    //                        if (row.getProblem() != null) {
    //                            strProblem = row.getProblem().toString().trim();
    //                        }
    //                        if (row.getComments() != null) {
    //                            strComments = row.getComments().toString().trim();
    //                        }
    //                        if (row.getCustomerContract() != null) {
    //                            strCustomerContract =
    //                                    row.getCustomerContract().toString().trim();
    //                        }
    //                        if (row.getRr() != null) {
    //                            strRr = row.getRr().toString().trim();
    //                        }
    //                        if (row.getSource() != null) {
    //                            strSource = row.getSource().toString().trim();
    //                        }
    //                        CallableStatement cs =
    //                            appM.getDBTransaction().createCallableStatement(" BEGIN " +
    //                                                                            " ceix_insert_ns_csx_rs_adf(:1,:2,:3,:4,:5,:6,:7,:8,:9,:10,:11,:12,:13,:14,:15,:16,:17,:18,:19,:20,:21,:22,:23,:24,:25,:26,:27,:28,:29,:30,:31,:32,:33,:34); " +
    //                                                                            " END; ",
    //                                                                            0);
    //                        cs.setString(1, strSalesOrderNumber);
    //                        cs.setString(2, strTrainId);
    //                        cs.setString(3, strLoadOrder);
    //                        cs.setString(4, strLoadDate);
    //                        cs.setString(5, strDestination);
    //                        cs.setString(6, strDestinationEta);
    //                        cs.setString(7, strConsignee);
    //                        cs.setString(8, strLoadOrigin);
    //                        cs.setString(9, strOriginEta);
    //                        cs.setString(10, strReservationStatus);
    //                        cs.setString(11, strRequest);
    //                        cs.setString(12, strOrderPlaceDate);
    //                        cs.setString(13, strRequestedLoadDate);
    //                        cs.setString(14, strCars);
    //                        cs.setString(15, strTons);
    //                        cs.setString(16, strCoalClass);
    //                        cs.setString(17, strWeigh);
    //                        cs.setString(18, strLoads);
    //                        cs.setString(19, strMty);
    //                        cs.setString(20, strWaybillDate);
    //                        cs.setString(21, strWaybill);
    //                        cs.setString(22, strLastTrain);
    //                        cs.setString(23, strCity);
    //                        cs.setString(24, strLastEventTime);
    //                        cs.setString(25, strCurrentSet);
    //                        cs.setString(26, strCurrentStatus);
    //                        cs.setString(27, strHoldFlag);
    //                        cs.setString(28, strCarrier);
    //                        cs.setString(29, strProblem);
    //                        cs.setString(30, strComments);
    //                        cs.setString(31, strCustomerContract);
    //                        cs.setString(32, strRr);
    //                        cs.setString(33, strSource);
    //                        cs.registerOutParameter(34, Types.VARCHAR);
    //                        cs.execute();
    //                        strMessage = cs.getString(34);
    //                        cs.close();
    //                    }
    //                }
    //            }
    //        }
    //
    //        return strMessage;
    //    }

    private String callceix_insert_ns_csx_rs_adf() throws SQLException {
        String strTrainId = null;
        String strRequestedLoadDate = null;
        String strMessage = null;

        BindingContext bindingctx = BindingContext.getCurrent();
        DCDataControl dc =
            bindingctx.dataControlFrame().findDataControl("CeixStsRailSchedulesAMDataControl");
        if (dc != null) {
            CeixStsRailSchedulesAMImpl appM =
                (CeixStsRailSchedulesAMImpl)dc.getDataProvider();
            if (appM != null) {
                CeixStsRailSchedulesVOImpl vO =
                    appM.getCeixStsRailSchedulesVO1();
                if (vO != null) {
                    CeixStsRailSchedulesVORowImpl row =
                        (CeixStsRailSchedulesVORowImpl)vO.getCurrentRow();
                    if (row != null) {
                        if (row.getTrainId() != null) {
                            strTrainId = row.getTrainId().toString().trim();
                        }
                        if (row.getRequestedLoadDate() != null) {
                            strRequestedLoadDate =
                                    row.getRequestedLoadDate().toString().trim();
                        }
                        CallableStatement cs =
                            appM.getDBTransaction().createCallableStatement(" BEGIN " +
                                                                            " ceix_insert_ns_csx_rs_adf(:1,:2,:3); " +
                                                                            " END; ",
                                                                            0);
                        cs.setString(1, strTrainId);
                        cs.setString(2, strRequestedLoadDate);
                        cs.registerOutParameter(3, Types.VARCHAR);
                        cs.execute();
                        strMessage = cs.getString(3);
                        cs.close();
                    }
                }
            }
        }

        return strMessage;
    }

    public void onCancel(ActionEvent actionEvent) {
        BindingContext bindingctx = BindingContext.getCurrent();
        DCDataControl dc =
            bindingctx.dataControlFrame().findDataControl("CeixStsRailSchedulesAMDataControl");
        CeixStsRailSchedulesAMImpl appM =
            (CeixStsRailSchedulesAMImpl)dc.getDataProvider();
        Transaction transaction = appM.getTransaction();
        transaction.rollback();
        if (actionEvent.getComponent().getId().equals("cb2")) {
            RichPopup popup = getUpdatePopup();
            oracle.adf.view.rich.util.ResetUtils.reset(actionEvent.getComponent());
            popup.hide();
        } else if (actionEvent.getComponent().getId().equals("cb7")) {
            RichPopup popup = getHoldUpdatePopup();
            oracle.adf.view.rich.util.ResetUtils.reset(actionEvent.getComponent());
            popup.hide();
        } else {
            RichPopup popup = getCreatePopup();
            oracle.adf.view.rich.util.ResetUtils.reset(actionEvent.getComponent());
            popup.hide();
        }
        //AdfFacesContext.getCurrentInstance().addPartialTarget(getTbpanelCollection());
        ViewObject abc = appM.getCeixStsRailSchedulesVO1();
        abc.reset();
        abc.clearCache();
        abc.executeQuery();
        ViewObject ab = appM.getCeixStsRailSchedulesHOLDVO1();
        ab.reset();
        ab.clearCache();
        ab.executeQuery();
    }

    ////Code added by Meghna Kabnurkar on 19-Sep-2019 for CONSOL2-1250
    ////https://astcorporation.atlassian.net/browse/CONSOL2-1250
    ////Need to change the business logic in the rail schedule integrations

    public void onCreatePopupFetch(PopupFetchEvent popupFetchEvent) {
        if (ADFContext.getCurrent().getSecurityContext().isUserInRole("CEIX_STSRailSchedulesUsers")) {
            //System.out.println("I am in CreateIF");
            BindingContext bindingctx = BindingContext.getCurrent();
            DCDataControl dc =
                bindingctx.dataControlFrame().findDataControl("CeixStsRailSchedulesAMDataControl");
            CeixStsRailSchedulesAMImpl appM =
                (CeixStsRailSchedulesAMImpl)dc.getDataProvider();
            ViewObject abc = appM.getCeixStsRailSchedulesVO1();
            abc.executeEmptyRowSet();
            BindingContainer bindings = bindingctx.getCurrentBindingsEntry();
            OperationBinding operationBinding =
                bindings.getOperationBinding("CreateInsert");
            operationBinding.execute();
        } else {
            //System.out.println("I am in CreateELSE");
            BindingContext bindingctx = BindingContext.getCurrent();
            DCDataControl dc =
                bindingctx.dataControlFrame().findDataControl("CeixStsRailSchedulesAMDataControl");
            CeixStsRailSchedulesAMImpl appM =
                (CeixStsRailSchedulesAMImpl)dc.getDataProvider();
            ViewObject abc = appM.getCeixStsRailSchedulesVO1();
            abc.executeEmptyRowSet();
        }
        //System.out.println("I am in Create");
    }

    public void setHoldUpdatePopup(RichPopup holdUpdatePopup) {
        this.holdUpdatePopup = holdUpdatePopup;
    }

    public RichPopup getHoldUpdatePopup() {
        return holdUpdatePopup;
    }

    ////Code added by Meghna Kabnurkar on 19-Sep-2019 for CONSOL2-1250
    ////https://astcorporation.atlassian.net/browse/CONSOL2-1250
    ////Need to change the business logic in the rail schedule integrations

    public void deleteDialogListner(DialogEvent dialogEvent) {
        try {
            if (dialogEvent != null) {
                if (dialogEvent.getOutcome() == DialogEvent.Outcome.ok) {
                    BindingContext bindingContext =
                        BindingContext.getCurrent();
                    DCDataControl dc =
                        bindingContext.findDataControl("CeixStsRailSchedulesAMDataControl");
                    if (dc != null) {
                        CeixStsRailSchedulesAMImpl appM =
                            (CeixStsRailSchedulesAMImpl)dc.getDataProvider();
                        if (appM != null) {
                            CeixStsRailSchedulesHOLDVOImpl vO =
                                (CeixStsRailSchedulesHOLDVOImpl)appM.getCeixStsRailSchedulesHOLDVO1();
                            if (vO != null) {
                                Row row = vO.getCurrentRow();
                                if (row != null) {
                                    row.remove();
                                    appM.getDBTransaction().commit();
                                    FacesMessage msg =
                                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                                         "Record deleted successfully.",
                                                         "");
                                    FacesContext.getCurrentInstance().addMessage(null,
                                                                                 msg);
                                    AdfFacesContext.getCurrentInstance().addPartialTarget(thold);
                                } else {
                                    FacesMessage msg =
                                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                         "Unable to delete record.",
                                                         "");
                                    FacesContext.getCurrentInstance().addMessage(null,
                                                                                 msg);
                                }
                            } else {
                                FacesMessage msg =
                                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                     "Unable to delete record.",
                                                     "");
                                FacesContext.getCurrentInstance().addMessage(null,
                                                                             msg);
                            }
                        } else {
                            FacesMessage msg =
                                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                 "Unable to delete record.",
                                                 "");
                            FacesContext.getCurrentInstance().addMessage(null,
                                                                         msg);
                        }
                    } else {
                        FacesMessage msg =
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                             "Unable to delete record.", "");
                        FacesContext.getCurrentInstance().addMessage(null,
                                                                     msg);
                    }
                }
            }
        } catch (Exception e) {
            showAlert("Exception (CeixStsRailScheduleMB - deleteDialogListner): ",
                      e.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void onCustomerSelHOLD(ValueChangeEvent valueChangeEvent) {
        //System.out.println("Cust Value got changed:"+valueChangeEvent.getNewValue());
        BindingContext bindingctx = BindingContext.getCurrent();
        DCDataControl dc =
            bindingctx.dataControlFrame().findDataControl("CeixStsRailSchedulesAMDataControl");
        CeixStsRailSchedulesAMImpl appM =
            (CeixStsRailSchedulesAMImpl)dc.getDataProvider();
        ViewObject abc = appM.getSalesOrderVO1();
        abc.reset();
        abc.clearCache();
        abc.getWhereClauseParams();
        abc.setNamedWhereClauseParam("p_acct_number",
                                     valueChangeEvent.getNewValue());
        abc.executeQuery();
        
        //added
        String orderNumber = null;
        if (abc.getCurrentRow().getAttribute("OrderNumber") != null) {
            orderNumber = abc.getCurrentRow()
                             .getAttribute("OrderNumber")
                             .toString();
        }
        
        ViewObject itemVO = appM.getContractItemsVO1();
        itemVO.reset();
        itemVO.clearCache();
        itemVO.getWhereClauseParams();
        itemVO.setNamedWhereClauseParam("pContractNumber", orderNumber);
        itemVO.executeQuery();
        pitemNumInp_HoldScreen.setValue(null);
        AdfFacesContext.getCurrentInstance().addPartialTarget(pitemNumInp_HoldScreen);
        
        ViewObject destVO = appM.getDestinationVO1();
        destVO.reset();
        destVO.clearCache();
        destVO.getWhereClauseParams();
        destVO.setNamedWhereClauseParam("pcontractNum", orderNumber);
        destVO.executeQuery();
        AdfFacesContext.getCurrentInstance().addPartialTarget(pdestInp_HoldScreen);
    }

    public void onGoBT(ActionEvent actionEvent) {
        BindingContext bindingctx = BindingContext.getCurrent();
        DCDataControl dc =
            bindingctx.dataControlFrame().findDataControl("CeixStsRailSchedulesAMDataControl");
        CeixStsRailSchedulesAMImpl appM =
            (CeixStsRailSchedulesAMImpl)dc.getDataProvider();
        ViewObject railvo = appM.getCeixStsRailSchedulesVO1();
        //System.out.println("The value is:"+ this.getPtrainInp().getValue());
        //System.out.println("The custvalue is:"+ this.getPcustNumInp().getValue());
        //System.out.println("The ordervalue is:"+ this.getPorderNumInp().getValue());
        //System.out.println("The contrvalue is:"+ this.getPcontrNumInp().getValue());
        railvo.reset();
        railvo.clearCache();
        railvo.setNamedWhereClauseParam("pTrainId",
                                        this.getPtrainInp().getValue());
        /*if( !("[Select Customer]".equals(this.getPcustNumInp().getValue()))){
            railvo.setNamedWhereClauseParam("pCustNum", this.getPcustNumInp().getValue());
            System.out.println("The custvalue2 is:"+ this.getPcustNumInp().getValue());
        }
        else
            railvo.setNamedWhereClauseParam("pCustNum", null);*/
        if (!("[Select Agreement]".equals(this.getPorderNumInp().getValue()))) {
            railvo.setNamedWhereClauseParam("pOrderNum",
                                            this.getPorderNumInp().getValue());
            //System.out.println("The ordervalue2 is:"+ this.getPorderNumInp().getValue());
        } else
            railvo.setNamedWhereClauseParam("pOrderNum", null);
        railvo.setNamedWhereClauseParam("pContrNum",
                                        this.getPcontrNumInp().getValue());
        railvo.setNamedWhereClauseParam("pDest",
                                        this.getPdestination().getValue());
//Added by Manasa Yalamarthy on 26th Sept,22 - Oracle 8013
        railvo.setNamedWhereClauseParam("pLoadFromDate",
                                     this.getPloadFromInp().getValue());
        railvo.setNamedWhereClauseParam("pLoadToDate",
                                        this.getPloadToInp().getValue());
        if (!("[Select Location]".equals(this.getPmineLocInp().getValue()))) { // Added by Manasa Yalamarthy on 9th August,2022 - 7972
            railvo.setNamedWhereClauseParam("pmineloc",
                                            this.getPmineLocInp().getValue());
        } else
            railvo.setNamedWhereClauseParam("pmineloc", null);

        railvo.executeQuery();
    }

    public void setPtrainInp(RichInputText ptrainInp) {
        this.ptrainInp = ptrainInp;
    }

    public RichInputText getPtrainInp() {
        return ptrainInp;
    }

    public void setPcustNumInp(RichSelectOneChoice pcustNumInp) {
        this.pcustNumInp = pcustNumInp;
    }

    public RichSelectOneChoice getPcustNumInp() {
        return pcustNumInp;
    }

    public void setPorderNumInp(RichSelectOneChoice porderNumInp) {
        this.porderNumInp = porderNumInp;
    }

    public RichSelectOneChoice getPorderNumInp() {
        return porderNumInp;
    }

    public void setPcontrNumInp(RichInputText pcontrNumInp) {
        this.pcontrNumInp = pcontrNumInp;
    }

    public RichInputText getPcontrNumInp() {
        return pcontrNumInp;
    }

    public void onCustomerParamSel(ValueChangeEvent valueChangeEvent) {
        //System.out.println("Cust Value got changed:"+valueChangeEvent.getNewValue());
        BindingContext bindingctx = BindingContext.getCurrent();
        DCDataControl dc =
            bindingctx.dataControlFrame().findDataControl("CeixStsRailSchedulesAMDataControl");
        CeixStsRailSchedulesAMImpl appM =
            (CeixStsRailSchedulesAMImpl)dc.getDataProvider();
        ViewObject abc = appM.getSalesOrderVO1();
        abc.reset();
        abc.clearCache();
        abc.getWhereClauseParams();
        abc.setNamedWhereClauseParam("p_acct_number",
                                     valueChangeEvent.getNewValue());
        abc.executeQuery();
    }

    public void onCopyPopupFetch(PopupFetchEvent popupFetchEvent) {
        BindingContext bindingctx = BindingContext.getCurrent();
        DCDataControl dc =
            bindingctx.dataControlFrame().findDataControl("CeixStsRailSchedulesAMDataControl");
        CeixStsRailSchedulesAMImpl appM =
            (CeixStsRailSchedulesAMImpl)dc.getDataProvider();
        ViewObject railvo = appM.getCeixStsRailSchedulesVO1();
        System.out.println("The copy value is:" +
                           railvo.getCurrentRow().getAttribute("RailScheduleId"));
        Row currentRow = railvo.getCurrentRow();
        System.out.println("I am here");
        Row newRow = railvo.createRow();
        System.out.println("I am here1");
        newRow.setAttribute("SalesOrderNumber",
                            currentRow.getAttribute("SalesOrderNumber"));
        newRow.setAttribute("TrainId", currentRow.getAttribute("TrainId"));
        newRow.setAttribute("LoadOrder", currentRow.getAttribute("LoadOrder"));
        newRow.setAttribute("LoadDate", currentRow.getAttribute("LoadDate"));
        newRow.setAttribute("Type", currentRow.getAttribute("Type"));
        newRow.setAttribute("Destination",
                            currentRow.getAttribute("Destination"));
        newRow.setAttribute("DestinationEta",
                            currentRow.getAttribute("DestinationEta"));
        newRow.setAttribute("Consignee", currentRow.getAttribute("Consignee"));
        newRow.setAttribute("LoadOrigin",
                            currentRow.getAttribute("LoadOrigin"));
        newRow.setAttribute("OriginEta", currentRow.getAttribute("OriginEta"));
        newRow.setAttribute("ReservationStatus",
                            currentRow.getAttribute("ReservationStatus"));
        newRow.setAttribute("Request", currentRow.getAttribute("Request"));
        newRow.setAttribute("OrderPlaceDate",
                            currentRow.getAttribute("OrderPlaceDate"));
        newRow.setAttribute("RequestedLoadDate",
                            currentRow.getAttribute("RequestedLoadDate"));
        newRow.setAttribute("Cars", currentRow.getAttribute("Cars"));
        newRow.setAttribute("Tons", currentRow.getAttribute("Tons"));
        newRow.setAttribute("CoalClass", currentRow.getAttribute("CoalClass"));
        newRow.setAttribute("Weigh", currentRow.getAttribute("Weigh"));
        newRow.setAttribute("Loads", currentRow.getAttribute("Loads"));
        newRow.setAttribute("Mty", currentRow.getAttribute("Mty"));
        newRow.setAttribute("WaybillDate",
                            currentRow.getAttribute("WaybillDate"));
        newRow.setAttribute("Waybill", currentRow.getAttribute("Waybill"));
        newRow.setAttribute("LastTrain", currentRow.getAttribute("LastTrain"));
        newRow.setAttribute("City", currentRow.getAttribute("City"));
        newRow.setAttribute("LastEventTime",
                            currentRow.getAttribute("LastEventTime"));
        newRow.setAttribute("CurrentSet",
                            currentRow.getAttribute("CurrentSet"));
        newRow.setAttribute("CurrentStatus",
                            currentRow.getAttribute("CurrentStatus"));
        newRow.setAttribute("HoldFlag", currentRow.getAttribute("HoldFlag"));
        newRow.setAttribute("Carrier", currentRow.getAttribute("Carrier"));
        newRow.setAttribute("Problem", currentRow.getAttribute("Problem"));
        newRow.setAttribute("Comments", currentRow.getAttribute("Comments"));
        newRow.setAttribute("CustomerContract",
                            currentRow.getAttribute("CustomerContract"));
        newRow.setAttribute("Rr", currentRow.getAttribute("Rr"));
        newRow.setAttribute("Source", currentRow.getAttribute("Source"));
        newRow.setAttribute("ItemNumber", currentRow.getAttribute("ItemNumber"));
        newRow.setAttribute("MineLocation", currentRow.getAttribute("MineLocation"));//Added by Manasa Yalamarthy on 26th Sept,22 - Oracle 8013
        railvo.insertRow(newRow);
        railvo.setCurrentRow(newRow);
        System.out.println("Rail Id is:" +
                           railvo.getCurrentRow().getAttribute("RailScheduleId"));
    }

    public void searchCustomerDialog1Listener(DialogEvent dialogEvent) {

        handleCustomerSearchDialogEvent(dialogEvent, porderNumInp, null, null);


    }

    public void searchCustomerDialog_CopyScreenListener(DialogEvent dialogEvent) {
        handleCustomerSearchDialogEvent(dialogEvent, porderNumInp_CopyScreen,pitemNumInp_CopyScreen,pdestInp_CopyScreen);
    }

    public void searchCustomerDialog_CreateScreenListener(DialogEvent dialogEvent) {

        handleCustomerSearchDialogEvent(dialogEvent,
                                        porderNumInp_createScreen,pitemNumInp_createScreen,pdestInp_createScreen);

    }

    public void searchCustomerDialog_UpdateScreenListener(DialogEvent dialogEvent) {

        handleCustomerSearchDialogEvent(dialogEvent,
                                        porderNumInp_updateScreen,pitemNumInp_updateScreen,pdestInp_updateScreen );

    }

    private void handleCustomerSearchDialogEvent(DialogEvent dialogEvent,
                                                 RichSelectOneChoice orderListToRefresh,RichSelectOneChoice itemListToRefresh,RichSelectOneChoice destListToRefresh) {

        if (dialogEvent.getOutcome() == DialogEvent.Outcome.ok) {

            Row selectedRow =
                (Row)ADFUtil.evaluateEL("#{bindings.CeixStsCustDetailsView1Iterator.currentRow}");

            if (selectedRow != null) {
                Object orderNumber = selectedRow.getAttribute("OrderNumber");
                Object accountNumber =
                    selectedRow.getAttribute("AccountNumber");
                refreshOrderNumbetList(orderListToRefresh,
                                       accountNumber.toString(),
                                       orderNumber.toString(),itemListToRefresh, destListToRefresh);
            }
        }

    }

    private void refreshOrderNumbetList(RichSelectOneChoice orderListToRefresh,
                                        String accountNumber,
                                        String orderNumber, RichSelectOneChoice itemListToRefresh,RichSelectOneChoice destListToRefresh) {


        BindingContext bindingctx = BindingContext.getCurrent();
        DCDataControl dc =
            bindingctx.dataControlFrame().findDataControl("CeixStsRailSchedulesAMDataControl");
        CeixStsRailSchedulesAMImpl appM =
            (CeixStsRailSchedulesAMImpl)dc.getDataProvider();
        ViewObject abc = appM.getSalesOrderVO1();
        abc.reset();
        abc.clearCache();
        abc.getWhereClauseParams();
        abc.setNamedWhereClauseParam("p_acct_number", accountNumber);
        abc.executeQuery();

        orderListToRefresh.setValue(orderNumber);
        AdfFacesContext.getCurrentInstance().addPartialTarget(orderListToRefresh);
        if (itemListToRefresh != null) {
            ViewObject itemVO = appM.getContractItemsVO1();
            itemVO.reset();
            itemVO.clearCache();
            itemVO.getWhereClauseParams();
            itemVO.setNamedWhereClauseParam("pContractNumber", orderNumber);
            itemVO.executeQuery();
            itemListToRefresh.setValue(null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemListToRefresh);
        }
        
        if (destListToRefresh != null) {
            ViewObject destVO = appM.getDestinationVO1();
                    destVO.reset();
                    destVO.clearCache();
                    destVO.getWhereClauseParams();
                    destVO.setNamedWhereClauseParam("pcontractNum", orderNumber);
                    destVO.executeQuery();
            AdfFacesContext.getCurrentInstance().addPartialTarget(destListToRefresh);
        }

    }

    public void orderNumberUpdateDialogListener(ActionEvent actionEvent) {

        UIComponent source = (UIComponent)actionEvent.getSource();
        String orderNumber = (String)_popup_OrderNumber.getValue();

        BindingContext bindingctx = BindingContext.getCurrent();
        DCDataControl dc =
            bindingctx.dataControlFrame().findDataControl("CeixStsRailSchedulesAMDataControl");
        CeixStsRailSchedulesAMImpl appM =
            (CeixStsRailSchedulesAMImpl)dc.getDataProvider();

        ViewObject vol = appM.getCeixStsRailSchedulesOrdNumMassUpdateVO1();
        if (source.getId().equals("update")) {
            //validate order number is correct _popup_OrderNumber
            boolean salesOrderIsValid = orderNumberIsValid(orderNumber);
            if (!salesOrderIsValid) {
                //show Error and exit
                showInvalidSalesOrderNumberError();
                return;
            }

            //make sure user has selected one or more records
            RowSetIterator rsIterator = vol.createRowSetIterator(null);
            rsIterator.reset();
            boolean atLeastOneRowChecked = false;
            while (rsIterator.hasNext()) {
                Row row = rsIterator.next();
                Boolean isChecked = (Boolean)row.getAttribute("IsSelected");

                if (isChecked != null && isChecked) {
                    atLeastOneRowChecked = true;
                    //now save order number in this row
                    row.setAttribute("SalesOrderNumber", orderNumber);
                }


                System.out.println("isChecked: " + isChecked);
                System.out.println("RailScheduleId: " +
                                   row.getAttribute("RailScheduleId"));

            }
            rsIterator.closeRowSetIterator();
            if (!atLeastOneRowChecked) {
                //Show alert
                showNoLinesSelectedError();
                return;
            } else {
                //commit reccords
                appM.getDBTransaction().commit();
                //show success msg
                showOrderNumberUpdatedSuccessfullyMsg();
                //just hide the popup
                closeUpdateOrdNumberPopup(actionEvent, vol);
            }
        } else if (source.getId().equals("cancel")) {
            //just hide the popup
            closeUpdateOrdNumberPopup(actionEvent, vol);
        }
    }

    private void closeUpdateOrdNumberPopup(ActionEvent actionEvent,
                                           ViewObject vol) {

        clearPopupFields(actionEvent, vol);
        _popupMassUpdateOrderNumber.hide();
    }

    private void clearPopupFields(ActionEvent actionEvent, ViewObject vol) {

        //reset query fields.
        QueryModel queryModel = _orderNumberUpdatePopupQuery.getModel();
        QueryDescriptor queryDescriptor =
            _orderNumberUpdatePopupQuery.getValue();
        queryModel.reset(queryDescriptor);
        _orderNumberUpdatePopupQuery.refresh(FacesContext.getCurrentInstance());
        //clear order number
        _popup_OrderNumber.resetValue();
        _popup_OrderNumber.setValue(null);
        AdfFacesContext.getCurrentInstance().addPartialTarget(_popup_OrderNumber);
        //clear the table
        vol.executeEmptyRowSet();


    }

    private void showOrderNumberUpdatedSuccessfullyMsg() {

        showAlert("Update Sales Agreement Number",
                  "<p>Sales Agreement Numbe updated successfully.</p>",
                  FacesMessage.SEVERITY_INFO);

    }

    private void showInvalidSalesOrderNumberError() {

        showAlert("Update Sales Agreement Number",
                  "<p>Sales Agreement Number not found. Please provide correct Sales Agreement Number to proceed.</p>",
                  FacesMessage.SEVERITY_ERROR);

    }

    private void showNoLinesSelectedError() {

        showAlert("Update Sales Agreement Number",
                  "<p>Please select at least one record to proceed.</p>",
                  FacesMessage.SEVERITY_ERROR);

    }

    public void showAlert(String title, String msg,
                          javax.faces.application.FacesMessage.Severity severity) {

        StringBuilder message = new StringBuilder("<html><body>");
        message.append(msg);
        message.append("</body></html>");
        FacesMessage fm = new FacesMessage(message.toString());
        fm.setSeverity(severity);
        FacesContext fctx = FacesContext.getCurrentInstance();
        fctx.addMessage(title, fm);

    }

    private boolean orderNumberIsValid(Object salesOrderNumber) {

        String salesOrdNum = salesOrderNumber.toString();
        //validate against order headers table
        BindingContext bindingctx = BindingContext.getCurrent();
        DCDataControl dc =
            bindingctx.dataControlFrame().findDataControl("CeixStsRailSchedulesAMDataControl");
        CeixStsRailSchedulesAMImpl appM =
            (CeixStsRailSchedulesAMImpl)dc.getDataProvider();

        CallableStatement st = null;
        String output = null;
        try {
            st =
 appM.getDBTransaction().createCallableStatement("BEGIN" + "  SELECT CONTRACT_NUMBER" +
                                                 "    INTO :1" +
                                                 "  FROM CEIX_CONTRACT_HEADERS" +
                                                 " WHERE CONTRACT_NUMBER = :2;" +
                                                 "END;", 0);
            st.registerOutParameter(1, Types.VARCHAR);
            st.setString(2, salesOrdNum);
            st.execute();
            output = st.getString(1);
            System.out.println("The SQL value is-->" + output);
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

    //    public void lovSearchListener(ReturnPopupEvent returnPopupEvent) {
    //        //an item from drop down is selected
    //
    //        System.out.println(">>lovSearchListener");
    //        UIXInputPopup lovComponent =
    //            (UIXInputPopup) returnPopupEvent.getSource();
    //
    //          //Looking for the table component within
    //          //seacrhContent facet
    //          //RichTable table = findTable(lovComponent);
    //
    //          RowKeySet keySet = _searchDialogTable1.getSelectedRowKeys();
    //
    //
    //          if (keySet != null && keySet.size() > 0)
    //          {
    //
    //            ListOfValuesModel model = lovComponent.getModel();
    //            Object newVal = model.getValueFromSelection(keySet);
    //
    //            Object oldVal = lovComponent.getValue();
    //            //Have we selected anything new?
    //            //If yes, set it as a value of the LOV and update the model
    //            if (!ObjectUtils.equal(oldVal, newVal))
    //            {
    //              lovComponent.setValue(newVal);
    //              lovComponent.processUpdates(FacesContext.getCurrentInstance());
    //
    //              System.out.println("newVal:"+newVal);
    //              refreshOrderNumbetList(newVal);
    //
    //            }
    //
    //              //DCBindingContainer bindings=(DCBindingContainer)ADFUtil.getBindings();
    //              //DCIteratorBinding dcIter=bindings.findIteratorBinding("CeixStsCustDetailsView1Iterator");
    //
    //
    //
    //              System.out.println("clearing keys");
    //              _searchDialogTable1.getSelectedRowKeys().removeAll();
    //              _searchDialogTable1.setSelectedRowKeys(null);
    //              System.out.println("cleared keys");
    //              keySet = _searchDialogTable1.getSelectedRowKeys();
    //              System.out.println(keySet);
    //
    //
    //          }
    //          else {
    //              Object oldVal = lovComponent.getValue();
    //              refreshOrderNumbetList(oldVal);
    //
    //          }
    //
    //
    //    }
    //
    //    public void lovPopupListener(LaunchPopupEvent launchPopupEvent)
    //    {
    //      //before the drop down is rendered
    //        //or when search link is clicked to open the dialog
    //
    //      UIXInputPopup lovComponent =
    //        (UIXInputPopup) launchPopupEvent.getSource();
    //      ListOfValuesModel model = lovComponent.getModel();
    //
    //      if (model != null)
    //      {
    //        //Resetting the query component.
    //        //So each time whenever the dialog is rendered the query component
    //                      //is in its initial state
    //        QueryModel queryModel = model.getQueryModel();
    //        QueryDescriptor queryDesc = model.getQueryDescriptor();
    //        if ((queryModel != null) && (queryDesc != null))
    //        {
    //          queryModel.reset(queryDesc);
    //          //Looking for the query component within
    //          //seacrhContent facet
    //          //RichQuery query = findQuery(lovComponent);
    //          if (_searchDialogQueryComponent1 != null)
    //            _searchDialogQueryComponent1.refresh(FacesContext.getCurrentInstance());
    //        }
    //
    //
    //        //If the LOV component has the searchFacet, then the framework fires this
    //        //event even in case of leaving the LOV component by TAB pressing.
    //        //So, we have to check whether the dialog is really need to be launched.
    //        //And in case of exact match, we don't need any search dialog.
    //        Object oldVal = lovComponent.getValue();
    //        if (!ObjectUtils.equal(String.valueOf(oldVal),
    //                               launchPopupEvent.getSubmittedValue()))
    //        {
    //          List<Object> autoComplete =
    //            model.autoCompleteValue(launchPopupEvent.getSubmittedValue());
    //
    //          //Do we have an exact match?
    //          if (autoComplete != null && autoComplete.size() == 1)
    //          {
    //            Object autoCompletedValue = autoComplete.get(0);
    //
    //            Object newVal = model.getValueFromSelection(autoCompletedValue);
    //            lovComponent.setValue(newVal);
    //            lovComponent.processUpdates(FacesContext.getCurrentInstance());
    //
    //            //We don't need to launch the dialog anymore
    //            launchPopupEvent.setLaunchPopup(false);
    //          }
    //
    //        }
    //
    //      }
    //    }
    //
    //    private void refreshOrderNumbetList(Object listValue) {
    //
    //
    //        String accountNumber= null;
    //        String orderNumber =null;
    //        if(listValue!=null&&!listValue.toString().equals("")) {
    //            String listVal=listValue.toString();
    //             orderNumber =listVal.substring(0,listVal.indexOf("-"));
    //             accountNumber= listVal.substring(listVal.lastIndexOf("-")+1
    //                                                    ,listVal.length());
    //
    //        }
    //
    //        BindingContext bindingctx   = BindingContext.getCurrent();
    //        DCDataControl dc            = bindingctx.dataControlFrame().findDataControl("CeixStsRailSchedulesAMDataControl");
    //        CeixStsRailSchedulesAMImpl appM = (CeixStsRailSchedulesAMImpl)dc.getDataProvider();
    //        ViewObject abc = appM.getSalesOrderVO1();
    //        abc.reset();
    //        abc.clearCache();
    //        abc.getWhereClauseParams();
    //        abc.setNamedWhereClauseParam("p_acct_number", accountNumber);
    //        abc.executeQuery();
    //
    //        porderNumInp.setValue(orderNumber);
    //        AdfFacesContext.getCurrentInstance().addPartialTarget(porderNumInp);
    //
    //    }
    //
    //    public void onCustomerParamSel1(ValueChangeEvent valueChangeEvent) {
    //
    //        System.out.println("Cust Value got changed:"+valueChangeEvent.getNewValue());
    //
    //        Object listValue = valueChangeEvent.getNewValue();
    //        refreshOrderNumbetList(listValue);
    //
    //        //_searchDialogTable1.setSelectedRowKeys(arg0);
    //
    //    }

    public void set_searchDialogTable1(RichTable _searchDialogTable1) {
        this._searchDialogTable1 = _searchDialogTable1;
    }

    public RichTable get_searchDialogTable1() {
        return _searchDialogTable1;
    }

    public void set_searchDialogQueryComponent1(RichQuery _searchDialogQueryComponent1) {
        this._searchDialogQueryComponent1 = _searchDialogQueryComponent1;
    }

    public RichQuery get_searchDialogQueryComponent1() {
        return _searchDialogQueryComponent1;
    }

    public void set_searchCustomerPopup_mainScreen(RichPopup _searchCustomerPopup_mainScreen) {
        this._searchCustomerPopup_mainScreen = _searchCustomerPopup_mainScreen;
    }

    public RichPopup get_searchCustomerPopup_mainScreen() {
        return _searchCustomerPopup_mainScreen;
    }

    public void set_searchCustomerPopup_createScreen(RichPopup _searchCustomerPopup_createScreen) {
        this._searchCustomerPopup_createScreen =
                _searchCustomerPopup_createScreen;
    }

    public RichPopup get_searchCustomerPopup_createScreen() {
        return _searchCustomerPopup_createScreen;
    }


    public void setPorderNumInp_createScreen(RichSelectOneChoice porderNumInp_createScreen) {
        this.porderNumInp_createScreen = porderNumInp_createScreen;
    }

    public RichSelectOneChoice getPorderNumInp_createScreen() {
        return porderNumInp_createScreen;
    }

    public void set_searchCustomerPopup_updateScreen(RichPopup _searchCustomerPopup_updateScreen) {
        this._searchCustomerPopup_updateScreen =
                _searchCustomerPopup_updateScreen;
    }

    public RichPopup get_searchCustomerPopup_updateScreen() {
        return _searchCustomerPopup_updateScreen;
    }


    public void setPorderNumInp_updateScreen(RichSelectOneChoice porderNumInp_updateScreen) {
        this.porderNumInp_updateScreen = porderNumInp_updateScreen;
    }

    public RichSelectOneChoice getPorderNumInp_updateScreen() {
        return porderNumInp_updateScreen;
    }

    public void setPdestination(RichInputText pdestination) {
        this.pdestination = pdestination;
    }

    public RichInputText getPdestination() {
        return pdestination;
    }

    public void set_popupMassUpdateOrderNumber(RichPopup _popupMassUpdateOrderNumber) {
        this._popupMassUpdateOrderNumber = _popupMassUpdateOrderNumber;
    }

    public RichPopup get_popupMassUpdateOrderNumber() {
        return _popupMassUpdateOrderNumber;
    }


    public void set_popup_OrderNumber(RichInputText _popup_OrderNumber) {
        this._popup_OrderNumber = _popup_OrderNumber;
    }

    public RichInputText get_popup_OrderNumber() {
        return _popup_OrderNumber;
    }

    public void set_orderNumberUpdatePopupQuery(RichQuery _orderNumberUpdatePopupQuery) {
        this._orderNumberUpdatePopupQuery = _orderNumberUpdatePopupQuery;
    }

    public RichQuery get_orderNumberUpdatePopupQuery() {
        return _orderNumberUpdatePopupQuery;
    }

    public void setPorderNumInp_CopyScreen(RichSelectOneChoice porderNumInp_CopyScreen) {
        this.porderNumInp_CopyScreen = porderNumInp_CopyScreen;
    }

    public RichSelectOneChoice getPorderNumInp_CopyScreen() {
        return porderNumInp_CopyScreen;
    }

    public void set_searchCustomerPopup_copyScreen(RichPopup _searchCustomerPopup_copyScreen) {
        this._searchCustomerPopup_copyScreen = _searchCustomerPopup_copyScreen;
    }

    public RichPopup get_searchCustomerPopup_copyScreen() {
        return _searchCustomerPopup_copyScreen;
    }

    public void setThold(RichTable thold) {
        this.thold = thold;
    }

    public RichTable getThold() {
        return thold;
    }

    public void setRails(RichTable rails) {
        this.rails = rails;
    }

    public RichTable getRails() {
        return rails;
    }

    public void setCopyAndCreatePopup(RichPopup copyAndCreatePopup) {
        this.copyAndCreatePopup = copyAndCreatePopup;
    }

    public RichPopup getCopyAndCreatePopup() {
        return copyAndCreatePopup;
    }

    public void setPitemNumInp_updateScreen(RichSelectOneChoice pitemNumInp_updateScreen) {
        this.pitemNumInp_updateScreen = pitemNumInp_updateScreen;
    }

    public RichSelectOneChoice getPitemNumInp_updateScreen() {
        return pitemNumInp_updateScreen;
    }

    public void setPitemNumInp_createScreen(RichSelectOneChoice pitemNumInp_createScreen) {
        this.pitemNumInp_createScreen = pitemNumInp_createScreen;
    }

    public RichSelectOneChoice getPitemNumInp_createScreen() {
        return pitemNumInp_createScreen;
    }

    public void setPitemNumInp_CopyScreen(RichSelectOneChoice pitemNumInp_CopyScreen) {
        this.pitemNumInp_CopyScreen = pitemNumInp_CopyScreen;
    }

    public RichSelectOneChoice getPitemNumInp_CopyScreen() {
        return pitemNumInp_CopyScreen;
    }

    public void setPordNumInp_HoldScreen(RichSelectOneChoice pordNumInp_HoldScreen) {
        this.pordNumInp_HoldScreen = pordNumInp_HoldScreen;
    }

    public RichSelectOneChoice getPordNumInp_HoldScreen() {
        return pordNumInp_HoldScreen;
    }

    public void setPitemNumInp_HoldScreen(RichSelectOneChoice pitemNumInp_HoldScreen) {
        this.pitemNumInp_HoldScreen = pitemNumInp_HoldScreen;
    }

    public RichSelectOneChoice getPitemNumInp_HoldScreen() {
        return pitemNumInp_HoldScreen;
    }
    
    public void handleSalesOrderNumberChangeEvent(ValueChangeEvent valueChangeEvent, RichSelectOneChoice itemNumberInp, RichSelectOneChoice destNumberInp) {
        BindingContext bindingctx = BindingContext.getCurrent();
        DCDataControl dc = bindingctx.dataControlFrame().findDataControl("CeixStsRailSchedulesAMDataControl");
        CeixStsRailSchedulesAMImpl appM = (CeixStsRailSchedulesAMImpl) dc.getDataProvider();
        ViewObject abc = appM.getContractItemsVO1();
        abc.reset();
        abc.clearCache();
        abc.getWhereClauseParams();
        abc.setNamedWhereClauseParam("pContractNumber", valueChangeEvent.getNewValue().toString());
        abc.executeQuery();
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemNumberInp);
        
        ViewObject destVO = appM.getDestinationVO1();
                destVO.reset();
                destVO.clearCache();
                destVO.getWhereClauseParams();
                destVO.setNamedWhereClauseParam("pcontractNum", valueChangeEvent.getNewValue().toString());
                destVO.executeQuery();
                AdfFacesContext.getCurrentInstance().addPartialTarget(destNumberInp);
    }

    public void onSalesOrderNumberChangeCreate(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        handleSalesOrderNumberChangeEvent(valueChangeEvent, pitemNumInp_createScreen, pdestInp_createScreen);
    }

    public void onSalesOrderNumberChangeUpdate(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        handleSalesOrderNumberChangeEvent(valueChangeEvent, pitemNumInp_updateScreen, pdestInp_updateScreen);
    }

    public void onSalesOrderNumberChangeCopy(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        handleSalesOrderNumberChangeEvent(valueChangeEvent, pitemNumInp_CopyScreen,pdestInp_CopyScreen);
    }

    public void onSalesOrderNumberChangeHold(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        handleSalesOrderNumberChangeEvent(valueChangeEvent, pitemNumInp_HoldScreen,pdestInp_HoldScreen);
    }

    /*public void onUpdatePopupFetch(PopupFetchEvent popupFetchEvent) {
        // Add event code here...
        Row selectedRow = (Row) ADFUtil.evaluateEL("#{bindings.CeixStsRailSchedulesV01Iterator.currentRow}");
        if (selectedRow != null) {
                    if (selectedRow.getAttribute("SalesOrderNumber") != null) {
                        showAlert("Status", selectedRow.getAttribute("SalesOrderNumber").toString(),
                                  FacesMessage.SEVERITY_INFO);
                    }
        if (selectedRow.getAttribute("TrainId") != null) {
        showAlert("Status", selectedRow.getAttribute("SalesOrderNumber").toString(),
                  FacesMessage.SEVERITY_INFO);
        }
        }
    }*/
    public void setPdestInp_HoldScreen(RichSelectOneChoice pdestInp_HoldScreen) {
        this.pdestInp_HoldScreen = pdestInp_HoldScreen;
    }

    public RichSelectOneChoice getPdestInp_HoldScreen() {
        return pdestInp_HoldScreen;
    }

    public void setPdestInp_CopyScreen(RichSelectOneChoice pdestInp_CopyScreen) {
        this.pdestInp_CopyScreen = pdestInp_CopyScreen;
    }

    public RichSelectOneChoice getPdestInp_CopyScreen() {
        return pdestInp_CopyScreen;
    }

    public void setPdestInp_createScreen(RichSelectOneChoice pdestInp_createScreen) {
        this.pdestInp_createScreen = pdestInp_createScreen;
    }

    public RichSelectOneChoice getPdestInp_createScreen() {
        return pdestInp_createScreen;
    }

    public void setPdestInp_updateScreen(RichSelectOneChoice pdestInp_updateScreen) {
        this.pdestInp_updateScreen = pdestInp_updateScreen;
    }

    public RichSelectOneChoice getPdestInp_updateScreen() {
        return pdestInp_updateScreen;
    }

    public void setPmineLocInp(RichSelectOneChoice pmineLocInp) {
        this.pmineLocInp = pmineLocInp;
    }

    public RichSelectOneChoice getPmineLocInp() {
        return pmineLocInp;
    }


    public void onLineCancel(ActionEvent actionEvent) {
        // Add event code here...
    }


    public void setPloadFromInp(RichInputDate ploadFromInp) {
        this.ploadFromInp = ploadFromInp;
    }

    public RichInputDate getPloadFromInp() {
        return ploadFromInp;
    }

    public void setPloadToInp(RichInputDate ploadToInp) {
        this.ploadToInp = ploadToInp;
    }

    public RichInputDate getPloadToInp() {
        return ploadToInp;
    }

    //Added by Manasa Yalamarthy on 26th Sept,22 - Oracle 8013
    public void salesOrderTableVCL(ValueChangeEvent valueChangeEvent) {
        
        System.out.println("**Inside salesOrderTableVCL**");
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("CeixStsRailSchedulesVO1Iterator");
        ViewObjectImpl railVo = (ViewObjectImpl) dciter.getViewObject();
        CeixStsRailSchedulesVORowImpl currRow = (CeixStsRailSchedulesVORowImpl) railVo.getCurrentRow();

        if (currRow != null) {
            System.out.println("Inside CurrRow");
            if (currRow.getItemNumber() != null) {
                currRow.setItemNumber(null);
            }
            if (currRow.getDestination() != null) {
                currRow.setDestination(null);
            }

            AdfFacesContext.getCurrentInstance().addPartialTarget(getRails());
        }

    }
}
