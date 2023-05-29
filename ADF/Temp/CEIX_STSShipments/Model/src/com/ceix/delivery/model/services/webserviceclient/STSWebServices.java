package com.ceix.delivery.model.services.webserviceclient;

import com.ceix.delivery.model.services.CeixStsDeliveryAMImpl;

import com.oracle.xmlns.adf.svc.errors.ServiceMessage;
import com.oracle.xmlns.adf.svc.types.AmountType;
import com.oracle.xmlns.adf.svc.types.FindControl;
import com.oracle.xmlns.adf.svc.types.FindCriteria;
import com.oracle.xmlns.adf.svc.types.MeasureType;
//import com.oracle.xmlns.apps.scm.doo.decomposition.orderdetailservices.orderinformationservice.DocumentReference;
//import com.oracle.xmlns.apps.scm.doo.decomposition.orderdetailservices.orderinformationservice.Line;
//import com.oracle.xmlns.apps.scm.doo.decomposition.orderdetailservices.orderinformationservice.Order;
//import com.oracle.xmlns.apps.scm.doo.decomposition.orderdetailservices.orderinformationservice.OrderInformationService;
//import com.oracle.xmlns.apps.scm.doo.decomposition.orderdetailservices.orderinformationservice.OrderInformationService_Service;
//import com.oracle.xmlns.apps.scm.doo.decomposition.orderdetailservices.orderinformationservice.OrderRequest;
import com.oracle.xmlns.adf.svc.types.ViewCriteria;
import com.oracle.xmlns.adf.svc.types.ViewCriteriaItem;
import com.oracle.xmlns.adf.svc.types.ViewCriteriaRow;
import com.oracle.xmlns.apps.prc.po.editdocument.purchaseorderservicev2.PurchaseOrder;
import com.oracle.xmlns.apps.prc.po.editdocument.purchaseorderservicev2.PurchaseOrderLine;
import com.oracle.xmlns.apps.prc.po.editdocument.purchaseorderservicev2.PurchaseOrderResult;
import com.oracle.xmlns.apps.prc.po.editdocument.purchaseorderservicev2.PurchaseOrderSchedule;
import com.oracle.xmlns.apps.prc.po.editdocument.purchaseorderservicev2.PurchaseOrderService;
import com.oracle.xmlns.apps.prc.po.editdocument.purchaseorderservicev2.PurchaseOrderService_Service;
import com.oracle.xmlns.apps.scm.shipping.pickrelease.pickreleaseservice.CreateBatchReturn;
import com.oracle.xmlns.apps.scm.shipping.pickrelease.pickreleaseservice.ObjectFactory;
import com.oracle.xmlns.apps.scm.shipping.pickrelease.pickreleaseservice.PickWaveService;
import com.oracle.xmlns.apps.scm.shipping.pickrelease.pickreleaseservice.PickWaveService_Service;

import com.oracle.xmlns.apps.scm.shipping.pickrelease.pickreleaseservice.PickingBatch;
import com.oracle.xmlns.apps.scm.shipping.pickrelease.pickreleaseservice.ReleaseBatchReturn;
import com.oracle.xmlns.apps.scm.shipping.shipconfirm.deliveries.shipmentlineservice.ManageShipmentAssignmentResponse;
import com.oracle.xmlns.apps.scm.shipping.shipconfirm.deliveries.shipmentlineservice.Message;
import com.oracle.xmlns.apps.scm.shipping.shipconfirm.deliveries.shipmentlineservice.SerialInformation;
import com.oracle.xmlns.apps.scm.shipping.shipconfirm.deliveries.shipmentlineservice.ShipmentLineInformation;
import com.oracle.xmlns.apps.scm.shipping.shipconfirm.deliveries.shipmentlineservice.ShipmentLineService;
import com.oracle.xmlns.apps.scm.shipping.shipconfirm.deliveries.shipmentlineservice.ShipmentLineService_Service;

import com.oracle.xmlns.apps.scm.shipping.shipconfirm.deliveries.shipmentlineservice.ShippingCost;
import com.oracle.xmlns.apps.scm.shipping.shipconfirm.deliveries.shipmentlineservice.SplitShipmentLineResponse;
import com.oracle.xmlns.apps.scm.shipping.shipconfirm.deliveries.shipmentlineservice.UpdateShipmentLinesResponse;
import com.oracle.xmlns.apps.scm.shipping.shipconfirm.deliveries.shipmentservice.ErrorMessage;
import com.oracle.xmlns.apps.scm.shipping.shipconfirm.deliveries.shipmentservice.ShipmentInformation;
import com.oracle.xmlns.apps.scm.shipping.shipconfirm.deliveries.shipmentservice.ShipmentResponse;
import com.oracle.xmlns.apps.scm.shipping.shipconfirm.deliveries.shipmentservice.ShipmentService;
import com.oracle.xmlns.apps.scm.shipping.shipconfirm.deliveries.shipmentservice.ShipmentService_Service;

import com.oracle.xmlns.apps.scm.fom.importorders.orderimportservice.OrderImportService_Service;
import com.oracle.xmlns.apps.scm.fom.importorders.orderimportservice.OrderImportRequest;
import com.oracle.xmlns.apps.scm.fom.importorders.orderimportservice.OrderImportResponse;
import com.oracle.xmlns.apps.scm.fom.importorders.orderimportservice.OrderImportService;


import com.oracle.xmlns.apps.scm.inventory.materialtransactions.pendingtransactions.stagedinventorytransactionservicev2.StagedInventoryTransaction;
import com.oracle.xmlns.apps.scm.inventory.materialtransactions.pendingtransactions.transactionmanagerservicev2.TransactionManagerService;
import com.oracle.xmlns.apps.scm.inventory.materialtransactions.pendingtransactions.transactionmanagerservicev2.TransactionManagerService_Service;
import com.oracle.xmlns.apps.scm.inventory.materialtransactions.pendingtransactions.transactionmanagerservicev2.types.InsertAndProcessInterfaceRowsResponse;
import com.oracle.xmlns.apps.scm.receiving.receiptsinterface.transactions.processorservicev2.ProcessorResponse;
import com.oracle.xmlns.apps.scm.receiving.receiptsinterface.transactions.processorservicev2.ProcessorResponseResult;
import com.oracle.xmlns.apps.scm.receiving.receiptsinterface.transactions.processorservicev2.ProcessorService;
import com.oracle.xmlns.apps.scm.receiving.receiptsinterface.transactions.processorservicev2.ProcessorService_Service;
import com.oracle.xmlns.apps.scm.receiving.receiptsinterface.transactions.processorservicev2.ReceivingInterfaceError;
import com.oracle.xmlns.apps.scm.receiving.receiptsinterface.transactions.processorservicev2.StagedReceivingHeader;
import com.oracle.xmlns.oxp.service.publicreportservice.ArrayOfParamNameValue;
import com.oracle.xmlns.oxp.service.publicreportservice.ArrayOfString;
import com.oracle.xmlns.oxp.service.publicreportservice.ExternalReportWSSService;

import com.oracle.xmlns.oxp.service.publicreportservice.ExternalReportWSSService_Service;

import com.oracle.xmlns.oxp.service.publicreportservice.ParamNameValue;
import com.oracle.xmlns.oxp.service.publicreportservice.ReportRequest;
import com.oracle.xmlns.oxp.service.publicreportservice.ReportResponse;

import com.oracle.xmlns.scheduler.ESSWebService;
import com.oracle.xmlns.scheduler.ESSWebService_Service;

import com.oracle.xmlns.scheduler.SubmitRequest;
import com.oracle.xmlns.scheduler.SubmitRequestResponse;

import com.oracle.xmlns.scheduler.types.MetadataObjectId;
import com.oracle.xmlns.scheduler.types.MetadataObjectType;
import com.oracle.xmlns.scheduler.types.DataType;

import com.oracle.xmlns.scheduler.types.RequestParameter;

import com.oracle.xmlns.scheduler.types.RequestParameters;

import com.sun.xml.ws.api.addressing.AddressingVersion;

import com.sun.xml.ws.developer.WSBindingProvider;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.math.BigDecimal;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import java.util.Properties;

import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.WebServiceRef;

import oracle.adf.share.logging.ADFLogger;

import weblogic.wsee.jws.jaxws.owsm.SecurityPolicyFeature;

import java.util.Base64;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;

import javax.net.ssl.X509TrustManager;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class STSWebServices {

    @WebServiceRef
    private static PickWaveService_Service pickWaveService_Service;

    @WebServiceRef
    private static ShipmentLineService_Service shipmentLineService_Service;

    @WebServiceRef
    private static ShipmentService_Service shipmentService_Service;
    
    @WebServiceRef
    private static ProcessorService_Service processorService_Service;
    
    @WebServiceRef
    private static PurchaseOrderService_Service purchaseOrderService_Service;
    
    @WebServiceRef
    private static TransactionManagerService_Service transactionManagerService_Service;
 //private static String PROPERTY_FILE = "com/ceix/delivery/model/WebService.properties"; //give full path of the file
   private static String EXTERNAL_PROPERTY_FILE = "WebService.properties";
    Properties mModelProperties = null;
    SecurityPolicyFeature[] m_securityFeature = {
        new SecurityPolicyFeature(getResourceFromProperties("SecurityPolicyName")) };

    private static final AddressingVersion WS_ADDR_VER = AddressingVersion.W3C;
    String serviceUsername = getResourceFromProperties("ServiceUsername");
    String servicePassword = getResourceFromProperties("ServicePassword");

    //private static final String serviceUsername = "Prondla";
    //private static final String servicePassword = "Welcome1";

    private static ADFLogger _logger = ADFLogger.createADFLogger(STSWebServices.class);


    public STSWebServices() {
        super();
    }
    public void overrideSSL() throws NoSuchAlgorithmException, KeyManagementException{
        // Create a trust manager that does not validate certificate chains
                      TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
                              public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                                  return null;
                              }
                              public void checkClientTrusted(X509Certificate[] certs, String authType) {
                              }
                              public void checkServerTrusted(X509Certificate[] certs, String authType) {
                              }
                          }
                      };
              // Install the all-trusting trust manager
                     SSLContext sc = SSLContext.getInstance("SSL");
                     sc.init(null, trustAllCerts, new java.security.SecureRandom());
                     HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
             
                     // Create all-trusting host name verifier
                     HostnameVerifier allHostsValid = new HostnameVerifier() {
                         public boolean verify(String hostname, SSLSession session) {
                             return true;
                         }
                     };
                     
              // Install the all-trusting host verifier
                      HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
    }
     
    

    public String callPickWaveService(String p_orderNo, String p_sourceSystemName, String p_shipFromOrganization,
                                      String p_orderType, String p_releaseRule) {
        String pickingBatchNum = "";

        try {
            pickWaveService_Service = new PickWaveService_Service();
            //SecurityPolicyFeature[] m_securityFeature = new SecurityPolicyFeature[] { new SecurityPolicyFeature("oracle/wss_username_token_over_ssl_client_policy")};
            PickWaveService pickWaveService = pickWaveService_Service.getPickWaveServiceSoapHttpPort(m_securityFeature);
            // Get the request context to set the outgoing addressing properties
            WSBindingProvider wsbp = (WSBindingProvider) pickWaveService;

            // Add your code to call the desired methods.
            String serviceEndpoint = getResourceFromProperties("PickWaveEndPointURL");
            Map<String, Object> requestContext = wsbp.getRequestContext();
            requestContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, serviceEndpoint);
            requestContext.put(WSBindingProvider.USERNAME_PROPERTY, serviceUsername);
            requestContext.put(WSBindingProvider.PASSWORD_PROPERTY, servicePassword);

            //Create Request
            PickingBatch pickingBatchDtls = new PickingBatch();
            ObjectFactory objectFactory = new ObjectFactory();
            JAXBElement<String> sourceSystemName = objectFactory.createPickingBatchSourceSystemName(p_sourceSystemName);
            JAXBElement<String> orderNum = objectFactory.createPickingBatchOrder(p_orderNo);
            //JAXBElement<String> releaseStatus = objectFactory.createPickingBatchReleaseStatus("E");
            JAXBElement<String> createShipments = objectFactory.createPickingBatchCreateShipments("N");
            JAXBElement<String> shipFromOrganization =
                objectFactory.createPickingBatchShipFromOrganization(p_shipFromOrganization);
            JAXBElement<String> orderType = objectFactory.createPickingBatchOrderType(p_orderType);
            JAXBElement<String> autoConfirmPicks = objectFactory.createPickingBatchAutoConfirmPicks("Y");

            pickingBatchDtls.setSourceSystemName(sourceSystemName);
            pickingBatchDtls.setOrder(orderNum);
            //pickingBatchDtls.setReleaseStatus(releaseStatus);
            pickingBatchDtls.setCreateShipments(createShipments);
            pickingBatchDtls.setAutoConfirmPicks(autoConfirmPicks);
            pickingBatchDtls.setShipFromOrganization(shipFromOrganization);
            pickingBatchDtls.setOrderType(orderType);

            BigDecimal apiVersionNumber = new BigDecimal(1.0);

            CreateBatchReturn createBatchReturn = null;
            createBatchReturn =
                pickWaveService.processCreatePickBatch(apiVersionNumber, null, p_releaseRule, null, pickingBatchDtls);
            pickingBatchNum = createBatchReturn.getPickingBatchName().getValue();
            //System.out.println(" #1. processCreatePickBatch PickingBatchName : ======>>>>> " +
            //                 pickingBatchNum);
            String status = createBatchReturn.getReturnStatus().getValue();
            String message = null;
            if ("E".equals(status) && pickingBatchNum == null) {
                message = "Error:" + createBatchReturn.getMsgData().getValue();
                pickingBatchNum = message;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return pickingBatchNum;
    }


    public String callProcessReleasePickBatch(String pickBatch, String p_releaseMode) {
        String pickingBatchReleaseStatus = "";
        try {

            pickWaveService_Service = new PickWaveService_Service();
            PickWaveService pickWaveService = pickWaveService_Service.getPickWaveServiceSoapHttpPort(m_securityFeature);
            // Get the request context to set the outgoing addressing properties
            WSBindingProvider wsbp = (WSBindingProvider) pickWaveService;

            // Add your code to call the desired methods.
            String serviceEndpoint = getResourceFromProperties("PickWaveEndPointURL");
            Map<String, Object> requestContext = wsbp.getRequestContext();
            requestContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, serviceEndpoint);
            requestContext.put(WSBindingProvider.USERNAME_PROPERTY, serviceUsername);
            requestContext.put(WSBindingProvider.PASSWORD_PROPERTY, servicePassword);
            /// ### Calling processReleasePickBatch input is output of processCreatePickBatch
            BigDecimal apiVersionNumber = new BigDecimal(1.0);
            ReleaseBatchReturn releaseBatchReturn = null;
            releaseBatchReturn =
                pickWaveService.processReleasePickBatch(apiVersionNumber, "", pickBatch, p_releaseMode);

            //System.out.println(" ReleasePickBatchResponse ========>>>> "+releaseBatchReturn.getJobRequestId().getValue());
            //System.out.println(" #2. processReleasePickBatch Status : ========>>>> " +
            //                 releaseBatchReturn.getReturnStatus().getValue());
            _logger.info(" #2. processReleasePickBatch Status : ========>>>> " +
                         releaseBatchReturn.getReturnStatus().getValue());
            //System.out.println(" #2. processReleasePickBatch Message : ========>>>> " +
            //                 releaseBatchReturn.getMsgData().getValue());
            //System.out.println(" #2. processReleasePickBatch Count : ========>>>> " +
            //                 releaseBatchReturn.getMsgCount().getValue());
            pickingBatchReleaseStatus =
                releaseBatchReturn.getReturnStatus().getValue() + "-" + releaseBatchReturn.getMsgData().getValue();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return pickingBatchReleaseStatus;
    }

    public String callProcessCreateUpdateShipmentService(String ShipmentName, String p_shipFromOrganization,
                                                         String p_customerNumber, String p_shipToPartySiteNumber,
                                                         String p_loadDate, BigDecimal p_weightTons, String carrier) {
        String status = null;
        try {
            //System.out.println("params for CreateUpdateShipment are:"+ShipmentName+";"+ p_shipFromOrganization
            //                 +";"+p_customerNumber+";"+p_shipToPartySiteNumber +";"+ p_weightTons);
            ShipmentService shipmentService = this.getShipmentWebService();

            _logger.info("Info for sipment service call from method callProcessCreateUpdateShipmentService.  ShipmentName- " +
                         ShipmentName + " ,p_shipFromOrganization- " + p_shipFromOrganization + " ,p_customerNumber" +
                         p_customerNumber + " ,p_shipToPartySiteNumber- " + p_shipToPartySiteNumber + " ,p_loadDate- " +
                         p_loadDate + " ,p_weightTons- " + p_weightTons + " ,carrier- " +
                         carrier );

            System.out.println("Info for sipment service call from method callProcessCreateUpdateShipmentService.  ShipmentName- " +
                               ShipmentName + " ,p_shipFromOrganization- " + p_shipFromOrganization +
                               " ,p_customerNumber" + p_customerNumber + " ,p_shipToPartySiteNumber- " +
                               p_shipToPartySiteNumber + " ,p_loadDate- " + p_loadDate + " ,p_weightTons- " +
                               p_weightTons+ " ,carrier- " + carrier);

            //Create Request
            //processCreateUpdateShipment
            BigDecimal apiVersionNumber = new BigDecimal(1.0);
            
            //Added as part of JIRA ORACLE-6301 to default Shipping Method
            String partyNumber = null;
            String serviceLvl = null;
            String modeOfTrans = null;
            
            if ("CSX".equals(carrier)){
                    partyNumber = "3133";
                    serviceLvl = "RAIL";
                    modeOfTrans = "RAIL";
                }
            else if ("NS".equals(carrier)){
                    partyNumber = "3132";
                    serviceLvl = "RAIL";
                    modeOfTrans = "RAIL";
                }
            else if ("BARGE".equals(carrier)){
                    partyNumber = "14125";
                    serviceLvl = "CES";
                    modeOfTrans = "BARGE";
                }
            else if ("TRUCK".equals(carrier)){
                    partyNumber = "14125";
                    serviceLvl = "TRUCK";
                    modeOfTrans = "TRUCK";
                }
            else if ("VESSEL".equals(carrier) || "VESSL".equals(carrier) ){
                    partyNumber = "14125";
                    serviceLvl = "BOAT";
                    modeOfTrans = "VESSEL";
                }

            ShipmentInformation shipmentInformation = new ShipmentInformation();

            com.oracle.xmlns.apps.scm.shipping.shipconfirm.deliveries.shipmentservice.ObjectFactory objectFactory =
                new com.oracle.xmlns.apps.scm.shipping.shipconfirm.deliveries.shipmentservice.ObjectFactory();
            JAXBElement<String> shipment = objectFactory.createShipmentInformationShipment(ShipmentName);
            JAXBElement<String> shipFromOrganization =
                objectFactory.createShipmentInformationShipFromOrganization(p_shipFromOrganization);
            JAXBElement<String> customerNumber =
                objectFactory.createShipmentInformationCustomerNumber(p_customerNumber);
            JAXBElement<String> shipToPartySiteNumber =
                objectFactory.createShipmentInformationShipToPartySiteNumber(p_shipToPartySiteNumber);
            GregorianCalendar cal = new GregorianCalendar();
            JAXBElement<XMLGregorianCalendar> shippedDate = null;
            JAXBElement<XMLGregorianCalendar> planDate = null;
            try {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                Date date = df.parse(p_loadDate);
                cal.setTime(date);
                DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
                XMLGregorianCalendar shipDate = datatypeFactory.newXMLGregorianCalendar(cal);
                shippedDate = objectFactory.createShipmentInformationInitialShipDate(shipDate);
                planDate = objectFactory.createShipmentInformationPlannedDeliveryDate(shipDate);
                //System.out.println("The date is :"+shipDate);
            } catch (ParseException e) {
                //System.out.println("Error: Invalid Date Parser Exception.");
                e.printStackTrace();
            } catch (Exception e) {
                //System.out.println("Error: Exception.");
                e.printStackTrace();
            }


            JAXBElement<BigDecimal> grossWeight = objectFactory.createShipmentInformationGrossWeight(p_weightTons);
            JAXBElement<BigDecimal> netWeight = objectFactory.createShipmentInformationNetWeight(p_weightTons);
            JAXBElement<String> weightUOM = objectFactory.createShipmentInformationWeightUOM("TON");
            JAXBElement<String> carrierPartyNumber = objectFactory.createShipmentInformationCarrierPartyNumber(partyNumber);
            JAXBElement<String> servieLevel = objectFactory.createShipmentInformationServiceLevel(serviceLvl);
            JAXBElement<String> modeOfTransport = objectFactory.createShipmentInformationModeOfTransport(modeOfTrans);

            _logger.info("shipment service call formated parameter values is :grossWeight- " + grossWeight +
                         " ,netWeight- " + netWeight + " ,weightUOM- " + weightUOM);

            shipmentInformation.setShipment(shipment);
            shipmentInformation.setShipFromOrganization(shipFromOrganization);
            shipmentInformation.setCustomerNumber(customerNumber);
            shipmentInformation.setShipToPartySiteNumber(shipToPartySiteNumber);
            shipmentInformation.setInitialShipDate(shippedDate);
            shipmentInformation.setPlannedDeliveryDate(planDate);
            shipmentInformation.setGrossWeight(grossWeight);
            shipmentInformation.setNetWeight(netWeight);
            shipmentInformation.setWeightUOM(weightUOM);
            
            //Added as part of JIRA ORACLE-6301 to default Shipping Method
            if (!carrierPartyNumber.isNil()){
            shipmentInformation.setCarrierPartyNumber(carrierPartyNumber);
            shipmentInformation.setServiceLevel(servieLevel);
            shipmentInformation.setModeOfTransport(modeOfTransport);
            }

            _logger.info("final processCreateUpdateShipment call with requestbody parameter for apiversion: " +
                         apiVersionNumber + " ,method: CREATE" + " ,shipment: " + shipment +
                         " ,shipFromOrganization: " + shipFromOrganization + " ,customerNumber: " + customerNumber +
                         " ,shipToPartySiteNumber: " + shipToPartySiteNumber + " ,shippedDate: " + shippedDate +
                         " ,planDate: " + planDate + " ,grossWeight: " + grossWeight);
            ShipmentResponse shipmentResponse =
                shipmentService.processCreateUpdateShipment(apiVersionNumber, "", "CREATE", shipmentInformation);
            //System.out.println(" #4. processCreateUpdateShipment : ReturnStatus =====>>>>> " +
            //                 shipmentResponse.getReturnStatus().getValue());
            status = shipmentResponse.getReturnStatus().getValue();
            //System.out.println(" #4. processCreateUpdateShipment : Shipment =====>>>>> " +
            //                 shipmentResponse.getShipment().getValue());
            //System.out.println(" #4. processCreateUpdateShipment : Shipment =====>>>>> " +
            //                 shipmentResponse.getShipment().getValue());
            if (shipmentResponse.getReturnStatus()
                                .getValue()
                                .equals("ERROR")) {
                List<ErrorMessage> messageList = shipmentResponse.getMessage();
                ErrorMessage message = new ErrorMessage();
                if (null != messageList.get(0))
                    message = messageList.get(0);
                //System.out.println(" #4.1 manageShipmentAssignments RESPONSE MSG ========>>>> " +
                //                 message.getMessageText().getValue());
                status = message.getMessageText().getValue();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return status;
    }

    public String callManageShipmentAssignments(String shipmentName, Long shipmentLineNum) {
        String status = null;
        try {
            ShipmentLineService shipmentLineService = this.getShipmentLineWebService();
            //Create Request
            List<Long> shipmentLineList = new ArrayList<Long>();

            // call ManageShipmentAssignments
            /* *
                 * @param1: apiVersionNumber
                 * @param2: ShipmentLineList (Shipment line number from generateShipmentRequest)
                 * @param3: ActionCode
                 * @param4: Shipment (Shipment Name same name passed in processCreateUpdateShipment)
                 */
            shipmentLineList.add(shipmentLineNum);
            //System.out.println("The shipment name is:"+shipmentName+";"+shipmentLineList.get(0));
            ManageShipmentAssignmentResponse manageShipmentAssignmentResponse =
                shipmentLineService.manageShipmentAssignments(new BigDecimal(1.0), null, shipmentLineList, "ASSIGN",
                                                              shipmentName);
            //System.out.println(" #5. manageShipmentAssignments RESPONSE ========>>>> " +
            //                 manageShipmentAssignmentResponse.getReturnStatus().getValue());
            status = manageShipmentAssignmentResponse.getReturnStatus().getValue();
            if (manageShipmentAssignmentResponse.getReturnStatus()
                                                .getValue()
                                                .equals("ERROR")) {
                List<Message> messageList = manageShipmentAssignmentResponse.getMessage();
                Message message = new Message();
                if (null != messageList.get(0))
                    message = messageList.get(0);
                //System.out.println(" #5.1 manageShipmentAssignments RESPONSE MSG ========>>>> " +
                //                 message.getMessageText().getValue());
                status = message.getMessageText().getValue();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return status;
    }

    public Long callSplitShipmentLine(Long shipmentLineNum, BigDecimal weightTons) {
        /**
         * @param1: apiVersionNumber
         * @param2: InitMsgList (leave it blank)
         * @param3: ShipmentLine (Shipment line number from generateShipmentRequest) // check if we call multiple
         * @param4: SplitQuantity (Quantity that will come from line item fron DB)
         * @param5: SecondarySplitQuantity  (leave it blank)
         */
        String status = null;
        Long shipmentLineFromSplitLine = 0L;
        try {
            ShipmentLineService shipmentLineService = this.getShipmentLineWebService();
            //_logger.info(" #6.  splitShipmentLine Status call with requestbody parameter: ========>>>> " + shipmentLineNum);
            //System.out.println(" #6.  splitShipmentLine Status call with requestbody parameter: ========>>>> " + shipmentLineNum);
            SplitShipmentLineResponse splitShipmentLineResponse =
                shipmentLineService.splitShipmentLine(new BigDecimal(1.0), "", shipmentLineNum, weightTons, null);


            if (null != splitShipmentLineResponse.getShipmentLine().getValue())
                shipmentLineFromSplitLine = Long.valueOf(splitShipmentLineResponse.getShipmentLine().getValue());
            status = splitShipmentLineResponse.getReturnStatus()
                                              .getValue()
                                              .toString();
            //System.out.println(" #6. splitShipmentLine RESPONSE ========>>>> " +
            //                 splitShipmentLineResponse.getReturnStatus().getValue().toString());
            if (splitShipmentLineResponse.getReturnStatus()
                                         .getValue()
                                         .equals("ERROR")) {
                List<Message> splitMessageList = splitShipmentLineResponse.getMessage();
                Message splitMessage = splitMessageList.get(0);
                //System.out.println(" #6. splitShipmentLine RESPONSE ========>>>> "+splitMessage.getMessageText().getValue());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return shipmentLineFromSplitLine;
    }

    public UpdateShipmentLinesResponse callUpdateShipmentLine( List<ShipmentLineInformation> p_shipmentLineInformationList) {
        //call UpdateShipmentLine
        /**
         * @param1: apiVersionNumber
         * @param2: InitMsgList (leave it blank)
         * @param3: ShipmentLine (Shipment line number from splitShipmentLine)
         * @param4: ShippedQuantity (same as above(splitShipmentLine))
         */

        //String status = null;
        UpdateShipmentLinesResponse updateShipmentLinesResponse = new UpdateShipmentLinesResponse();
        try {
            ShipmentLineService shipmentLineService = this.getShipmentLineWebService();
            List<SerialInformation> serialInformationList = new ArrayList<SerialInformation>();
            //then update with new line quantity
            //_logger.info(" #7.  updateShipmentLines Start: ========>>>> ");
            //System.out.println(" #7.  updateShipmentLines Start: ========>>>> ");
            updateShipmentLinesResponse =
                shipmentLineService.updateShipmentLines(new BigDecimal(1.0), "", p_shipmentLineInformationList,
                                                        serialInformationList);
            //_logger.info(" #8.  updateShipmentLines Complete: ========>>>> ");
            //System.out.println(" #8.  updateShipmentLines Complete: ========>>>> ");
            //status = updateShipmentLinesResponse.getReturnStatus().getValue();
            //System.out.println(" #7. updateShipmentLines RESPONSE ========>>>> " +
            //                 updateShipmentLinesResponse.getReturnStatus().getValue());
            //System.out.println(" #7. updateShipmentLines RESPONSE ========>>>> "+updateShipmentLinesResponse.getMessageCount().getValue());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return updateShipmentLinesResponse;
    }

    public String callprocessShippingCost(String p_cost, BigDecimal p_Amount, Long p_shipmentLineFromSplitLine) {
        String status = null;
        try {
            ShipmentLineService shipmentLineService = this.getShipmentLineWebService();
            com.oracle.xmlns.apps.scm.shipping.shipconfirm.deliveries.shipmentlineservice.ObjectFactory objectFactory =
                new com.oracle.xmlns.apps.scm.shipping.shipconfirm.deliveries.shipmentlineservice.ObjectFactory();
            //for New shipment Line
            ShippingCost shippingCost = new ShippingCost();
            AmountType amountType = new AmountType();
            String currencyCode = "USD";
            amountType.setValue(p_Amount);
            amountType.setCurrencyCode(currencyCode);
            JAXBElement<String> action = objectFactory.createShippingCostAction("CREATE");
            JAXBElement<String> cost = objectFactory.createShippingCostCost(p_cost);
            JAXBElement<Long> shipmentLine = objectFactory.createShippingCostShipmentLine(p_shipmentLineFromSplitLine);

            shippingCost.setAction(action);
            shippingCost.setCost(cost);
            shippingCost.setAmount(amountType);
            shippingCost.setShipmentLine(shipmentLine);

            UpdateShipmentLinesResponse ShippingCostResponse = shipmentLineService.processShippingCost(shippingCost);
            //System.out.println(" #8. processShippingCost RESPONSE ========>>>> "+ShippingCostResponse.getReturnStatus().getValue());
            status = ShippingCostResponse.getReturnStatus().getValue();
            if (ShippingCostResponse.getReturnStatus()
                                    .getValue()
                                    .equals("ERROR")) {
                List<Message> splitMessageList = ShippingCostResponse.getMessage();
                Message splitMessage = splitMessageList.get(0);
                //System.out.println(" #8. processShippingCost Message ========>>>> "+splitMessage.getMessageText().getValue());
                status = splitMessage.getMessageText().getValue();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return status;
    }

    public ShipmentLineService getShipmentLineWebService() {
        shipmentLineService_Service = new ShipmentLineService_Service();
        ShipmentLineService shipmentLineService =
            shipmentLineService_Service.getShipmentLineServiceSoapHttpPort(m_securityFeature);
        // Get the request context to set the outgoing addressing properties
        WSBindingProvider wsbp = (WSBindingProvider) shipmentLineService;
        // Add your code to call the desired methods.
        String serviceEndpoint = getResourceFromProperties("ShipmentLineEndPointURL");
        Map<String, Object> requestContext = wsbp.getRequestContext();
        requestContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, serviceEndpoint);
        requestContext.put(WSBindingProvider.USERNAME_PROPERTY, serviceUsername);
        requestContext.put(WSBindingProvider.PASSWORD_PROPERTY, servicePassword);
        return shipmentLineService;
    }

    public ShipmentService getShipmentWebService() {
        shipmentService_Service = new ShipmentService_Service();
        ShipmentService shipmentService;
        shipmentService = shipmentService_Service.getShipmentServiceSoapHttpPort(m_securityFeature);
        // Get the request context to set the outgoing addressing properties
        WSBindingProvider wsbp = (WSBindingProvider) shipmentService;
        // Add your code to call the desired methods.
        String serviceEndpoint = getResourceFromProperties("ShipmentEndpointURL");
        Map<String, Object> requestContext = wsbp.getRequestContext();
        requestContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, serviceEndpoint);
        requestContext.put(WSBindingProvider.USERNAME_PROPERTY, serviceUsername);
        requestContext.put(WSBindingProvider.PASSWORD_PROPERTY, servicePassword);
        return shipmentService;
    }

    public String callProcessShipmentAction(String shipmentName, String shipConfirmRule) {
        BigDecimal apiVersionNumber = new BigDecimal(1.0);
        String status = null;
        try {
            ShipmentService shipmentService = this.getShipmentWebService();
            ShipmentResponse shipmentResponse =
                shipmentService.processShipmentAction(apiVersionNumber, null, "CONFIRM", shipmentName, null, null, null,
                                                      null, null, null, shipConfirmRule, null, null, null, null);
            List<ErrorMessage> messagelist = shipmentResponse.getMessage();
            ErrorMessage message = messagelist.get(0);
            String messageText = message.getMessageText().getValue();
            //System.out.println("#8. ProcessShipmentAction : =====>>>>>"+messageText);
            //System.out.println("");
            status = shipmentResponse.getReturnStatus().getValue();
            if (!("SUCCESS".equals(status))) {
                status = messageText;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return status;
    }

    public String getResourceFromProperties(String key) {
        // read property file only once
        if (mModelProperties == null) {
            initProperties();
        }

        return mModelProperties.getProperty(key);
    }


    /*public String getModelResource(String key) {
       if (mModelProperties == null) {
           initProperties();
       }
       return mModelProperties.getProperty(key);
   }


  
    /**
     * load the properties from the resource file
  */   
    
    private void initProperties() {
         //InputStream inStream = this.getClass().getClassLoader().getResourceAsStream(EXTERNAL_PROPERTY_FILE);
         try {
         InputStream inStream = new FileInputStream(EXTERNAL_PROPERTY_FILE);
         if (inStream == null) {
             System.out.println(" ===> Could not load property file: ==> '" + EXTERNAL_PROPERTY_FILE + "'");
             mModelProperties = new Properties();
             return;
         }

         mModelProperties = new Properties();

             mModelProperties.load(inStream);
         } catch (IOException e) {
             e.printStackTrace();
             System.out.println(" ===>> Can't load properties: " + e.getMessage());
         }
         //System.out.println("  ===>> Model properties loaded!");
     }

/*
   private void initProperties() {
        InputStream inStream = this.getClass()
                                   .getClassLoader()
                                   .getResourceAsStream(PROPERTY_FILE);
        try {
          overrideSSL();
        } catch (KeyManagementException | NoSuchAlgorithmException e) {
        }
        if (inStream == null) {
            //System.out.println("Could not load property file: '" + PROPERTY_FILE + "'");
            mModelProperties = new Properties();
            return;
        }

        mModelProperties = new Properties();
        try {
            mModelProperties.load(inStream);
        } catch (IOException e) {
            e.printStackTrace();
            //System.out.println(" ===>>Can't load properties: " + e.getMessage());
        }
        //System.out.println("  ===>> Model properties loaded!");
    }*/

    //Added Method -*-
    public OrderImportService getOrderImportWebService() {
        OrderImportService_Service orderImportService_Service = new OrderImportService_Service();
        OrderImportService orderImportService;
        orderImportService = orderImportService_Service.getOrderImportServiceSoapHttpPort(m_securityFeature);
        // Get the request context to set the outgoing addressing properties
        WSBindingProvider wsbp = (WSBindingProvider) orderImportService;
        // Add your code to call the desired methods.
        String serviceEndpoint = getResourceFromProperties("OrderImportEndpointURL");
        Map<String, Object> requestContext = wsbp.getRequestContext();
        requestContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, serviceEndpoint);
        requestContext.put(WSBindingProvider.USERNAME_PROPERTY, serviceUsername);
        requestContext.put(WSBindingProvider.PASSWORD_PROPERTY, servicePassword);
        return orderImportService;
    }

    //Added Method -*-
    public OrderImportResponse callorderImport(OrderImportRequest OrderImportDtls) {
        OrderImportResponse orderImportResponse = new OrderImportResponse();
        try {
            // Create a trust manager that does not validate certificate chains
      /*      TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }
            };
            // Install the all-trusting trust manager
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            // Create all-trusting host name verifier
            HostnameVerifier allHostsValid = new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };

            // Install the all-trusting host verifier
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);*/

            //OrderImportService orderImportService = this.getOrderImportWebService();
            OrderImportService_Service orderImportService_Service = new OrderImportService_Service();
            OrderImportService orderImportService;
            orderImportService = orderImportService_Service.getOrderImportServiceSoapHttpPort(m_securityFeature);
            // Get the request context to set the outgoing addressing properties
            WSBindingProvider wsbp = (WSBindingProvider) orderImportService;
            // Add your code to call the desired methods.
            String serviceEndpoint = getResourceFromProperties("OrderImportEndpointURL");
            Map<String, Object> requestContext = wsbp.getRequestContext();
            requestContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, serviceEndpoint);
            requestContext.put(WSBindingProvider.USERNAME_PROPERTY, serviceUsername);
            requestContext.put(WSBindingProvider.PASSWORD_PROPERTY, servicePassword);
            List<OrderImportResponse> orderImportResponseList = null;
            orderImportResponseList = orderImportService.createOrders(OrderImportDtls);
            orderImportResponse = orderImportResponseList.get(0);
            System.out.println("Order Number: " + orderImportResponse.getOrderNumber().getValue());
            System.out.println("Return Status: " + orderImportResponse.getReturnStatus().getValue());
            System.out.println("Message Text: " + orderImportResponse.getMessageText().getValue());
        } catch (Exception ex) {
            _logger.severe(" call orderImport Error ========>>>> "+ ex);
            ex.printStackTrace();
        }
        return orderImportResponse;
    }

    public String callRunReport(String salesOrderNumbr) {
        //List<DeliveryDetails> delList = new ArrayList<DeliveryDetails>();
        String deliveryDetaiilId = null;
        try {
            ExternalReportWSSService_Service externalReportWSSService_Service = new ExternalReportWSSService_Service();
            ExternalReportWSSService externalReportWSSService =
                externalReportWSSService_Service.getExternalReportWSSService(m_securityFeature);
            // Get the request context to set the outgoing addressing properties
            WSBindingProvider wsbp = (WSBindingProvider) externalReportWSSService;

            // Add your code to call the desired methods.
            String serviceEndpoint = getResourceFromProperties("ExternalWSSEndPointURL");
            Map<String, Object> requestContext = wsbp.getRequestContext();
            requestContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, serviceEndpoint);
            requestContext.put(WSBindingProvider.USERNAME_PROPERTY, serviceUsername);
            requestContext.put(WSBindingProvider.PASSWORD_PROPERTY, servicePassword);
            /// ### Calling runReport
            ReportRequest reportRequest = new ReportRequest();
            reportRequest.setAttributeFormat("text");
            reportRequest.setFlattenXML(false);
            reportRequest.setByPassCache(true);
            reportRequest.setReportAbsolutePath("/Custom/PAAS Integration/Outbound/SCM/OM/Report/CEIX PAAS DELIVERY DETAILS Report.xdo");
            reportRequest.setSizeOfDataChunkDownload(-1);
            ArrayOfParamNameValue arrayOfParamNameValue = new ArrayOfParamNameValue();
            ParamNameValue paramNameValue = new ParamNameValue();
            ArrayOfString arrayOfString = new ArrayOfString();
            paramNameValue.setName("p_order_number");
            arrayOfString.getItem().add(salesOrderNumbr);
            paramNameValue.setValues(arrayOfString);
            arrayOfParamNameValue.getItem().add(paramNameValue);
            reportRequest.setParameterNameValues(arrayOfParamNameValue);
            ReportResponse reportResponse = new ReportResponse();
            //_logger.info(" #4.  call runReport Start: ========>>>> ");
            //System.out.println(" #4.  call runReport Start: ========>>>> ");
            reportResponse = externalReportWSSService.runReport(reportRequest, "");
            Base64.Decoder decoder = Base64.getMimeDecoder();
            byte[] decodedBytes = decoder.decode(Base64.getEncoder().encode(reportResponse.getReportBytes()));
            deliveryDetaiilId = new String(decodedBytes);
            //_logger.info(" #5.  call runReport End: ========>>>> "+ deliveryDetaiilId);
            //System.out.println(" #5.  call runReport End: ========>>>> "+ deliveryDetaiilId);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return deliveryDetaiilId.trim();
    }
    
    public String callinsertAndProcessInterfaceRows(List<StagedInventoryTransaction> stagedInventoryTransactionList){
        transactionManagerService_Service = new TransactionManagerService_Service();
        InsertAndProcessInterfaceRowsResponse processinterfaceresponse = new InsertAndProcessInterfaceRowsResponse();
        TransactionManagerService transationManService = transactionManagerService_Service.getTransactionManagerServiceSoapHttpPort(m_securityFeature);
        // Get the request context to set the outgoing addressing properties
        WSBindingProvider wsbp = (WSBindingProvider)transationManService;
        String status = null;
        // Add your code to call the desired methods.
        try {
            String serviceEndpoint = getResourceFromProperties("TransManagerEndpointURL");
            
            Map<String, Object> requestContext = wsbp.getRequestContext();
            requestContext.put (BindingProvider.ENDPOINT_ADDRESS_PROPERTY,serviceEndpoint );
            requestContext.put (WSBindingProvider.USERNAME_PROPERTY,serviceUsername);    
            requestContext.put (WSBindingProvider.PASSWORD_PROPERTY,servicePassword);
            
            Long id = 1L;
            Long headId = 1000L;
            
            String insertResult = transationManService.insertAndProcessInterfaceRows(stagedInventoryTransactionList, 
                                                                                    null, null, headId, id, id);
            
            //System.out.println("TransactionManagerServ response ===>"+insertResult);         
            status = insertResult;
        }catch(Exception ex) {
            ex.printStackTrace();
        }
        return status;
    }
    
    public String getLineStatus(String salesOrderNumbr, String itemNumber) {
        //List<DeliveryDetails> delList = new ArrayList<DeliveryDetails>();
        String orderInfo = null;
        try {
            ExternalReportWSSService_Service externalReportWSSService_Service = new ExternalReportWSSService_Service();
            ExternalReportWSSService externalReportWSSService =
                externalReportWSSService_Service.getExternalReportWSSService(m_securityFeature);
            // Get the request context to set the outgoing addressing properties
            WSBindingProvider wsbp = (WSBindingProvider) externalReportWSSService;

            // Add your code to call the desired methods.
            String serviceEndpoint = getResourceFromProperties("ExternalWSSEndPointURL");
            Map<String, Object> requestContext = wsbp.getRequestContext();
            requestContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, serviceEndpoint);
            requestContext.put(WSBindingProvider.USERNAME_PROPERTY, serviceUsername);
            requestContext.put(WSBindingProvider.PASSWORD_PROPERTY, servicePassword);
            /// ### Calling runReport
            ReportRequest reportRequest = new ReportRequest();
            reportRequest.setAttributeFormat("text");
            reportRequest.setFlattenXML(false);
            reportRequest.setByPassCache(true);
            reportRequest.setReportAbsolutePath("/Custom/PAAS Integration/Outbound/SCM/OM/Report/CEIX PAAS GET ORDER INFO Report.xdo");
            reportRequest.setSizeOfDataChunkDownload(-1);
            ArrayOfParamNameValue arrayOfParamNameValue = new ArrayOfParamNameValue();
            ParamNameValue paramNameValue = new ParamNameValue();
            ArrayOfString arrayOfString = new ArrayOfString();
            paramNameValue.setName("p_order_num");
            arrayOfString.getItem().add(salesOrderNumbr);
            paramNameValue.setValues(arrayOfString);
            arrayOfParamNameValue.getItem().add(paramNameValue);
            
            ParamNameValue paramNameValue2 = new ParamNameValue();
            ArrayOfString arrayOfString2 = new ArrayOfString();
            paramNameValue2.setName("p_item_num");
            arrayOfString2.getItem().add(itemNumber);
            paramNameValue2.setValues(arrayOfString2);
            arrayOfParamNameValue.getItem().add(paramNameValue2);
            
            reportRequest.setParameterNameValues(arrayOfParamNameValue);
            ReportResponse reportResponse = new ReportResponse();
            reportResponse = externalReportWSSService.runReport(reportRequest, "");
            Base64.Decoder decoder = Base64.getMimeDecoder();
            byte[] decodedBytes = decoder.decode(Base64.getEncoder().encode(reportResponse.getReportBytes()));
            orderInfo = new String(decodedBytes);
            _logger.info(orderInfo);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return orderInfo.trim();
    }
    
    public String callProcessor(StagedReceivingHeader stagedReceivingHeader){
        processorService_Service = new ProcessorService_Service();
        OrderDetailsResponseBean orderDetailsResponseBean = new OrderDetailsResponseBean ();
        ProcessorService processorService = processorService_Service.getProcessorServiceSoapHttpPort(m_securityFeature);
        // Get the request context to set the outgoing addressing properties
        WSBindingProvider wsbp = (WSBindingProvider)processorService;
        String status = null;
        // Add your code to call the desired methods.
        try {
            String serviceEndpoint = getResourceFromProperties("ProcessorEndpointURL");
            
            Map<String, Object> requestContext = wsbp.getRequestContext();
            requestContext.put (BindingProvider.ENDPOINT_ADDRESS_PROPERTY,serviceEndpoint );
            requestContext.put (WSBindingProvider.USERNAME_PROPERTY,serviceUsername);    
            requestContext.put (WSBindingProvider.PASSWORD_PROPERTY,servicePassword);
            
            List<StagedReceivingHeader> stagedReceivingHeaderList = new ArrayList();
            stagedReceivingHeaderList.add(stagedReceivingHeader);
            
            ProcessorResponseResult processorResponseResult = processorService.processor(stagedReceivingHeaderList);
            
            List<ProcessorResponse> valuelist = processorResponseResult.getValue();
            ProcessorResponse processorResponse = valuelist.get(0);
            status = processorResponse.getStatus().getValue();
            //System.out.println("processor ProcessorResponse 1 =======>>>>"+processorResponse.getStatus().getValue());
            //System.out.println("processor ProcessorResponse 2 =======>>>>"+processorResponse.getGroupId().getValue());
            List<ReceivingInterfaceError>  receivingInterfaceErrorList = processorResponse.getReceivingInterfaceError();
            if (receivingInterfaceErrorList.size() > 0){
                ReceivingInterfaceError receivingInterfaceError = receivingInterfaceErrorList.get(0);
                status = status+" : "+receivingInterfaceError.getErrorMessage().getValue();
                //System.out.println("processor ProcessorResponse Message =======>>>>"+receivingInterfaceError.getErrorMessage().getValue());
                //System.out.println("processor ProcessorResponse error Type =======>>>>"+receivingInterfaceError.getErrorType().getValue());
                //System.out.println("processor ProcessorResponse Table =======>>>>"+receivingInterfaceError.getTableName().getValue());
                //System.out.println("processor ProcessorResponse Column =======>>>>"+receivingInterfaceError.getColumnName().getValue());
            }
            List<ServiceMessage> messageList = processorResponseResult.getMessage();
            if(messageList.size() > 0){
                ServiceMessage msg = messageList.get(0);
                //System.out.println("10# processor ServiceReturn Messsage code ====>>>> "+msg.getCode());
                //System.out.println("10# processor ServiceReturn Messsage message ====>>>> "+msg.getMessage());
            }             
            
        }catch(Exception ex) {
            ex.printStackTrace();
        }
        return status;
    }
    
    public PoReceiptBean callGetPurchaseOrderDetails(String poNum, String legalEntityName) {
        PoReceiptBean poReceiptBean = new PoReceiptBean();
        try {
            
            purchaseOrderService_Service = new PurchaseOrderService_Service();
            PurchaseOrderService purchaseOrderService = purchaseOrderService_Service.getPurchaseOrderServiceSoapHttpPort(m_securityFeature);
            // Get the request context to set the outgoing addressing properties
            WSBindingProvider wsbp = (WSBindingProvider)purchaseOrderService;           
            String serviceEndpoint = getResourceFromProperties("PurchaseEndpointURL");
            // Add your code to call the desired methods.
           
            Map<String, Object> requestContext = wsbp.getRequestContext();
            requestContext.put (BindingProvider.ENDPOINT_ADDRESS_PROPERTY,serviceEndpoint );
            requestContext.put (WSBindingProvider.USERNAME_PROPERTY,serviceUsername);    
            requestContext.put (WSBindingProvider.PASSWORD_PROPERTY,servicePassword);
            
            FindCriteria findCriteria = new FindCriteria();
        
            int fetchStart = 0;
            int fetchSize = -1;
            ViewCriteria viewCriteria = new ViewCriteria();        
            ViewCriteriaRow viewCriteriaRow = new ViewCriteriaRow();
            ViewCriteriaItem viewCriteriaItem = new ViewCriteriaItem();
            String attribute = "OrderNumber";           
            String operator = "=";
            viewCriteriaItem.setAttribute(attribute);
            viewCriteriaItem.setOperator(operator);
            viewCriteriaItem.getValue().add(poNum);// "CONSOL100333"
            viewCriteriaRow.getItem().add(viewCriteriaItem);
            viewCriteria.getGroup().add(viewCriteriaRow);
            
            findCriteria.setFetchStart(fetchStart);
            findCriteria.setFetchSize(fetchSize);
            findCriteria.setFilter(viewCriteria);
            
            FindControl findControl = new FindControl();
            boolean retrieveAllTranslations = false;
            findControl.setRetrieveAllTranslations(retrieveAllTranslations);
            
            PurchaseOrderResult purchaseOrderResult = purchaseOrderService.findPurchaseOrderByOrderNumber(findCriteria, "",0L, legalEntityName, findControl);
            
            List<ServiceMessage> message = purchaseOrderResult.getMessage();
            if(message.size()>0) {
                ServiceMessage serviceMessage = message.get(0);
                //System.out.println(" code ===>>>"+serviceMessage.getCode());
                //System.out.println(" message ===>>>"+serviceMessage.getMessage());
                //System.out.println(" severity ===>>>"+serviceMessage.getSeverity());            
            }
            
            List<PurchaseOrder> ordervalueList = purchaseOrderResult.getValue();
            Iterator<PurchaseOrder> ordervalueListIter = ordervalueList.iterator();
            if(ordervalueListIter.hasNext()){
                PurchaseOrder purchaseOrder = ordervalueListIter.next();
                //System.out.println(" ProcurementBUId  ===>>>"+purchaseOrder.getProcurementBUId());  
                //System.out.println(" BuyerId ===>>>"+purchaseOrder.getBuyerId()); 
                poReceiptBean.setBuId(purchaseOrder.getProcurementBUId());
                poReceiptBean.setEmployeeId(purchaseOrder.getBuyerId());
                poReceiptBean.setDocumentNumber(purchaseOrder.getOrderNumber());
                
                poReceiptBean.setVendorname(purchaseOrder.getSupplier().getValue());
                poReceiptBean.setVendorNumber(purchaseOrder.getSupplierId().getValue());
                List<PurchaseOrderLine> purchaseOrderLineList = purchaseOrder.getPurchaseOrderLine();
                Iterator<PurchaseOrderLine> purchaseOrderLineListIter = purchaseOrderLineList.iterator();
                if(purchaseOrderLineListIter.hasNext()){
                    PurchaseOrderLine purchaseOrderLine = purchaseOrderLineListIter.next();                        
                    //System.out.println("purchaseOrderLine Item ID ===>>>"+purchaseOrderLine.getItemId().getValue());
                    //System.out.println("purchaseOrderLine Item ID ===>>>"+purchaseOrderLine.getLineNumber());
                    poReceiptBean.setItemId(purchaseOrderLine.getItemId().getValue());
                    poReceiptBean.setDocumentLineNumber(purchaseOrderLine.getLineNumber().toString());
                    List<PurchaseOrderSchedule> purchaseOrderScheduleList = purchaseOrderLine.getPurchaseOrderSchedule();
                    Iterator<PurchaseOrderSchedule> purchaseOrderScheduleListIter = purchaseOrderScheduleList.iterator();
                    if(purchaseOrderScheduleListIter.hasNext()) {
                        PurchaseOrderSchedule purchaseOrderSchedule = purchaseOrderScheduleListIter.next();
                        //System.out.println("purchaseOrderLine --> purchaseOrderSchedule ShipToOrganizationId ===>>>"+purchaseOrderSchedule.getShipToOrganizationId().getValue());
                        poReceiptBean.setShipToOrganizationId(purchaseOrderSchedule.getShipToOrganizationId().getValue());
                        poReceiptBean.setToOrganizationId(purchaseOrderSchedule.getShipToOrganizationId().getValue());
                        poReceiptBean.setShipToOrganizationName(purchaseOrderSchedule.getShipToOrganizationName());
                    }                    
                }
            }            
        }catch(Exception ex) {
           ex.printStackTrace(); 
        }
        return poReceiptBean;
    }
    
    /*public long callESSJob(String salesOrderNumbr) {
        long requestId = 0;
        try {
            ESSWebService_Service essWebService_Service = new ESSWebService_Service();
            ESSWebService essWebService = essWebService_Service.getSchedulerServiceImplPort(m_securityFeature);
            // Get the request context to set the outgoing addressing properties
            WSBindingProvider wsbp = (WSBindingProvider) essWebService;

            // Add your code to call the desired methods.
            String serviceEndpoint = getResourceFromProperties("ESSWebEndPointURL");
            Map<String, Object> requestContext = wsbp.getRequestContext();
            requestContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, serviceEndpoint);
            requestContext.put(WSBindingProvider.USERNAME_PROPERTY, serviceUsername);
            requestContext.put(WSBindingProvider.PASSWORD_PROPERTY, servicePassword);
            /// ### Calling runReport
            
            MetadataObjectId  metadataObjectId = new MetadataObjectId();
            metadataObjectId.setName("CEIXORDERCONFIRMNOTIFICTN");
            metadataObjectId.setPackageName("/oracle/apps/ess/custom/scm/");
            metadataObjectId.setType(MetadataObjectType.JOB_DEFINITION);
            RequestParameter requestParameter = new RequestParameter();
            requestParameter.setName("submit.argument1");
            requestParameter.setDataType(DataType.STRING);
            requestParameter.setValue(salesOrderNumbr);
            RequestParameters requestParameters = new RequestParameters();
            requestParameters.getParameter().add(requestParameter);
           
            SubmitRequest submitRequest = new SubmitRequest();
            submitRequest.setDescription("CEIX Order Confirmation Notification Report");
            submitRequest.setJobDefinitionId(metadataObjectId);
            submitRequest.setApplication("FscmEss");
            submitRequest.setRequestParameters(requestParameters);
            
            SubmitRequestResponse requestResponse = new SubmitRequestResponse();
            requestResponse = essWebService.submitRequest(submitRequest);
            requestId = requestResponse.getRequestId();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return requestId;
    }*/
    
    /*public Order callGetOrderDetails(String sourceTransSystem, String sourceTransNumber) {
        Order orderresult = new Order();
        try {
            OrderInformationService_Service orderInformationService_Service = new OrderInformationService_Service();              
            //OrderInformationService orderInformationService = orderInformationService_Service.getOrderInformationServiceSoapHttpPort();
            OrderInformationService orderInformationService = orderInformationService_Service.getOrderInformationServiceSoapHttpPort(m_securityFeature);
            // Get the request context to set the outgoing addressing properties
            WSBindingProvider wsbp = (WSBindingProvider) orderInformationService;            
            String serviceEndpoint = getResourceFromProperties("OrderInfoEndpointURL");
             // Add your code to call the desired methods.
             Map<String, Object> requestContext = wsbp.getRequestContext();  
             requestContext.put (BindingProvider.ENDPOINT_ADDRESS_PROPERTY,serviceEndpoint );
             requestContext.put (WSBindingProvider.USERNAME_PROPERTY,serviceUsername);    
             requestContext.put (WSBindingProvider.PASSWORD_PROPERTY,servicePassword);         
                 com.oracle.xmlns.apps.scm.doo.decomposition.orderdetailservices.orderinformationservice.ObjectFactory objectFactory = new com.oracle.xmlns.apps.scm.doo.decomposition.orderdetailservices.orderinformationservice.ObjectFactory();
                 
                 OrderRequest orderRequest = new OrderRequest();
                 //JAXBElement<String> sourceTransactionIdentifier = objectFactory.createOrderRequestSourceTransactionIdentifier("");
                 JAXBElement<String> sourceTransactionSystem = objectFactory.createOrderRequestSourceTransactionSystem(sourceTransSystem);      
                 JAXBElement<String> sourceTransactionNumber = objectFactory.createOrderRequestSourceTransactionNumber(sourceTransNumber);
                 orderRequest.setSourceTransactionSystem(sourceTransactionSystem);
                 orderRequest.setSourceTransactionNumber(sourceTransactionNumber);
                 List<OrderRequest> orderRequestlist = new ArrayList();
                 orderRequestlist.add(orderRequest);
                 
                 List<Order> orderresponse = orderInformationService.getOrderDetails(orderRequestlist);
                 
                orderresult = orderresponse.get(0);
            
        }catch(Exception ex) {
           ex.printStackTrace(); 
        }
        return orderresult;
    }*/


}
