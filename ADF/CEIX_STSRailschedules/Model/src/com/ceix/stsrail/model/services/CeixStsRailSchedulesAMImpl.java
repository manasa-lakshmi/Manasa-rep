package com.ceix.stsrail.model.services;

import com.ceix.stsrail.model.views.CeixStsRailSchedulesHOLDVOImpl;
import com.ceix.stsrail.model.views.CeixStsRailSchedulesOrdNumMassUpdateVOImpl;
import com.ceix.stsrail.model.views.CeixStsRailSchedulesVOImpl;
import com.ceix.stsrail.model.views.ContractItemsVOImpl;
import com.ceix.stsrail.model.views.CustomerVOImpl;

import com.ceix.stsrail.model.views.DestinationVOImpl;
import com.ceix.stsrail.model.views.LoadOriginVOImpl;
import com.ceix.stsrail.model.views.MineLocationLOVImpl;
import com.ceix.stsrail.model.views.SalesOrderVOImpl;

import oracle.jbo.server.ApplicationModuleImpl;
import oracle.jbo.server.ViewObjectImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Mon May 14 14:43:31 CDT 2018
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class CeixStsRailSchedulesAMImpl extends ApplicationModuleImpl {
    /**
     * This is the default constructor (do not remove).
     */
    public CeixStsRailSchedulesAMImpl() {
    }

    /**
     * Container's getter for CeixStsRailSchedulesVO1.
     * @return CeixStsRailSchedulesVO1
     */
    public CeixStsRailSchedulesVOImpl getCeixStsRailSchedulesVO1() {
        return (CeixStsRailSchedulesVOImpl)findViewObject("CeixStsRailSchedulesVO1");
    }

    /**
     * Container's getter for CustomerVO1.
     * @return CustomerVO1
     */
    public CustomerVOImpl getCustomerVO1() {
        return (CustomerVOImpl)findViewObject("CustomerVO1");
    }

    /**
     * Container's getter for SalesOrderVO1.
     * @return SalesOrderVO1
     */
    public SalesOrderVOImpl getSalesOrderVO1() {
        return (SalesOrderVOImpl)findViewObject("SalesOrderVO1");
    }

    /**
     * Container's getter for CeixStsRailSchedulesHOLDVO1.
     * @return CeixStsRailSchedulesHOLDVO1
     */
    public ViewObjectImpl getCeixStsRailSchedulesHOLDVO1() {
        return (ViewObjectImpl)findViewObject("CeixStsRailSchedulesHOLDVO1");
    }


    /**
     * Container's getter for CeixStsCustDetailsView1.
     * @return CeixStsCustDetailsView1
     */
    public ViewObjectImpl getCeixStsCustDetailsView1() {
        return (ViewObjectImpl)findViewObject("CeixStsCustDetailsView1");
    }

    /**
     * Container's getter for CeixStsRailSchedulesOrdNumMassUpdateVO1.
     * @return CeixStsRailSchedulesOrdNumMassUpdateVO1
     */
    public CeixStsRailSchedulesOrdNumMassUpdateVOImpl getCeixStsRailSchedulesOrdNumMassUpdateVO1() {
        return (CeixStsRailSchedulesOrdNumMassUpdateVOImpl)findViewObject("CeixStsRailSchedulesOrdNumMassUpdateVO1");
    }

    /**
     * Container's getter for ContractItemsVO1.
     * @return ContractItemsVO1
     */
    public ContractItemsVOImpl getContractItemsVO1() {
        return (ContractItemsVOImpl) findViewObject("ContractItemsVO1");
    }

    /**
     * Container's getter for LoadOriginVO1.
     * @return LoadOriginVO1
     */
    public LoadOriginVOImpl getLoadOriginVO1() {
        return (LoadOriginVOImpl) findViewObject("LoadOriginVO1");
    }

    /**
     * Container's getter for DestinationVO1.
     * @return DestinationVO1
     */
    public DestinationVOImpl getDestinationVO1() {
        return (DestinationVOImpl) findViewObject("DestinationVO1");
    }

    /**
     * Container's getter for MineLocationLOV1.
     * @return MineLocationLOV1
     */
    public MineLocationLOVImpl getMineLocationLOV1() {
        return (MineLocationLOVImpl) findViewObject("MineLocationLOV1");
    }
}
