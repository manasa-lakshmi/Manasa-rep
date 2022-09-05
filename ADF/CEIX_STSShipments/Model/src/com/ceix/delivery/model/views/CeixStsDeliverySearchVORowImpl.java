package com.ceix.delivery.model.views;

import java.math.BigDecimal;

import oracle.jbo.RowSet;
import oracle.jbo.domain.BFileDomain;
import oracle.jbo.domain.Date;
import oracle.jbo.domain.Number;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Mon Jul 22 19:41:01 IST 2019
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class CeixStsDeliverySearchVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        OrderNumber {
            protected Object get(CeixStsDeliverySearchVORowImpl obj) {
                return obj.getOrderNumber();
            }

            protected void put(CeixStsDeliverySearchVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        SalesAgreementName {
            protected Object get(CeixStsDeliverySearchVORowImpl obj) {
                return obj.getSalesAgreementName();
            }

            protected void put(CeixStsDeliverySearchVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        UnitTrainNumber {
            protected Object get(CeixStsDeliverySearchVORowImpl obj) {
                return obj.getUnitTrainNumber();
            }

            protected void put(CeixStsDeliverySearchVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        NewTrainNumber {
            protected Object get(CeixStsDeliverySearchVORowImpl obj) {
                return obj.getNewTrainNumber();
            }

            protected void put(CeixStsDeliverySearchVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LoadDate1 {
            protected Object get(CeixStsDeliverySearchVORowImpl obj) {
                return obj.getLoadDate1();
            }

            protected void put(CeixStsDeliverySearchVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        ShipmentNumber {
            protected Object get(CeixStsDeliverySearchVORowImpl obj) {
                return obj.getShipmentNumber();
            }

            protected void put(CeixStsDeliverySearchVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        TotalWeight {
            protected Object get(CeixStsDeliverySearchVORowImpl obj) {
                return obj.getTotalWeight();
            }

            protected void put(CeixStsDeliverySearchVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        ShipConfirmedFlag {
            protected Object get(CeixStsDeliverySearchVORowImpl obj) {
                return obj.getShipConfirmedFlag();
            }

            protected void put(CeixStsDeliverySearchVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        SoNumber {
            protected Object get(CeixStsDeliverySearchVORowImpl obj) {
                return obj.getSoNumber();
            }

            protected void put(CeixStsDeliverySearchVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        ItemNumber {
            protected Object get(CeixStsDeliverySearchVORowImpl obj) {
                return obj.getItemNumber();
            }

            protected void put(CeixStsDeliverySearchVORowImpl obj, Object value) {
                obj.setItemNumber((String) value);
            }
        }
        ,
        Straggler {
            protected Object get(CeixStsDeliverySearchVORowImpl obj) {
                return obj.getStraggler();
            }

            protected void put(CeixStsDeliverySearchVORowImpl obj, Object value) {
                obj.setStraggler((String) value);
            }
        }
        ,
        Destination {
            protected Object get(CeixStsDeliverySearchVORowImpl obj) {
                return obj.getDestination();
            }

            protected void put(CeixStsDeliverySearchVORowImpl obj, Object value) {
                obj.setDestination((String) value);
            }
        }
        ,
        LoadOrigin {
            protected Object get(CeixStsDeliverySearchVORowImpl obj) {
                return obj.getLoadOrigin();
            }

            protected void put(CeixStsDeliverySearchVORowImpl obj, Object value) {
                obj.setLoadOrigin((String) value);
            }
        }
        ,
        Freight {
            protected Object get(CeixStsDeliverySearchVORowImpl obj) {
                return obj.getFreight();
            }

            protected void put(CeixStsDeliverySearchVORowImpl obj, Object value) {
                obj.setFreight((String) value);
            }
        }
        ,
        EditFlag {
            protected Object get(CeixStsDeliverySearchVORowImpl obj) {
                return obj.getEditFlag();
            }

            protected void put(CeixStsDeliverySearchVORowImpl obj, Object value) {
                obj.setEditFlag((String) value);
            }
        }
        ,
        StragglerFlag {
            protected Object get(CeixStsDeliverySearchVORowImpl obj) {
                return obj.getStragglerFlag();
            }

            protected void put(CeixStsDeliverySearchVORowImpl obj, Object value) {
                obj.setStragglerFlag((Boolean) value);
            }
        }
        ,
        CeixStsDeliveryAM_CeixStsContractItemsVO1_1 {
            protected Object get(CeixStsDeliverySearchVORowImpl obj) {
                return obj.getCeixStsDeliveryAM_CeixStsContractItemsVO1_1();
            }

            protected void put(CeixStsDeliverySearchVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        CeixStsDeliveryAM_DestinationVO1_1 {
            protected Object get(CeixStsDeliverySearchVORowImpl obj) {
                return obj.getCeixStsDeliveryAM_DestinationVO1_1();
            }

            protected void put(CeixStsDeliverySearchVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        CeixStsDeliveryAM_LoadOriginVO1_1 {
            protected Object get(CeixStsDeliverySearchVORowImpl obj) {
                return obj.getCeixStsDeliveryAM_LoadOriginVO1_1();
            }

            protected void put(CeixStsDeliverySearchVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;


        public int index() {
            return AttributesEnum.firstIndex() + ordinal();
        }

        public static final int firstIndex() {
            return firstIndex;
        }

        public static int count() {
            return AttributesEnum.firstIndex() + AttributesEnum.staticValues().length;
        }

        public static final AttributesEnum[] staticValues() {
            if (vals == null) {
                vals = AttributesEnum.values();
            }
            return vals;
        }


        protected abstract Object get(CeixStsDeliverySearchVORowImpl object);

        protected abstract void put(CeixStsDeliverySearchVORowImpl object, Object value);
    }


    public static final int ORDERNUMBER = AttributesEnum.OrderNumber.index();
    public static final int SALESAGREEMENTNAME = AttributesEnum.SalesAgreementName.index();
    public static final int UNITTRAINNUMBER = AttributesEnum.UnitTrainNumber.index();
    public static final int NEWTRAINNUMBER = AttributesEnum.NewTrainNumber.index();
    public static final int LOADDATE1 = AttributesEnum.LoadDate1.index();
    public static final int SHIPMENTNUMBER = AttributesEnum.ShipmentNumber.index();
    public static final int TOTALWEIGHT = AttributesEnum.TotalWeight.index();
    public static final int SHIPCONFIRMEDFLAG = AttributesEnum.ShipConfirmedFlag.index();
    public static final int SONUMBER = AttributesEnum.SoNumber.index();
    public static final int ITEMNUMBER = AttributesEnum.ItemNumber.index();
    public static final int STRAGGLER = AttributesEnum.Straggler.index();
    public static final int DESTINATION = AttributesEnum.Destination.index();
    public static final int LOADORIGIN = AttributesEnum.LoadOrigin.index();
    public static final int FREIGHT = AttributesEnum.Freight.index();
    public static final int EDITFLAG = AttributesEnum.EditFlag.index();
    public static final int STRAGGLERFLAG = AttributesEnum.StragglerFlag.index();
    public static final int CEIXSTSDELIVERYAM_CEIXSTSCONTRACTITEMSVO1_1 =
        AttributesEnum.CeixStsDeliveryAM_CeixStsContractItemsVO1_1.index();
    public static final int CEIXSTSDELIVERYAM_DESTINATIONVO1_1 =
        AttributesEnum.CeixStsDeliveryAM_DestinationVO1_1.index();
    public static final int CEIXSTSDELIVERYAM_LOADORIGINVO1_1 =
        AttributesEnum.CeixStsDeliveryAM_LoadOriginVO1_1.index();

    /**
     * This is the default constructor (do not remove).
     */
    public CeixStsDeliverySearchVORowImpl() {
    }


    /**
     * Gets the attribute value for the calculated attribute OrderNumber.
     * @return the OrderNumber
     */
    public String getOrderNumber() {
        return (String) getAttributeInternal(ORDERNUMBER);
    }


    /**
     * Gets the attribute value for the calculated attribute SalesAgreementName.
     * @return the SalesAgreementName
     */
    public String getSalesAgreementName() {
        return (String) getAttributeInternal(SALESAGREEMENTNAME);
    }

    /**
     * Gets the attribute value for the calculated attribute UnitTrainNumber.
     * @return the UnitTrainNumber
     */
    public String getUnitTrainNumber() {
        return (String) getAttributeInternal(UNITTRAINNUMBER);
    }


    /**
     * Gets the attribute value for the calculated attribute NewTrainNumber.
     * @return the NewTrainNumber
     */
    public String getNewTrainNumber() {
        return (String) getAttributeInternal(NEWTRAINNUMBER);
    }


    /**
     * Gets the attribute value for the calculated attribute LoadDate1.
     * @return the LoadDate1
     */
    public Date getLoadDate1() {
        return (Date) getAttributeInternal(LOADDATE1);
    }


    /**
     * Gets the attribute value for the calculated attribute ShipmentNumber.
     * @return the ShipmentNumber
     */
    public String getShipmentNumber() {
        return (String) getAttributeInternal(SHIPMENTNUMBER);
    }


    /**
     * Gets the attribute value for the calculated attribute TotalWeight.
     * @return the TotalWeight
     */
    public Number getTotalWeight() {
        return (Number) getAttributeInternal(TOTALWEIGHT);
    }

    /**
     * Gets the attribute value for the calculated attribute ShipConfirmedFlag.
     * @return the ShipConfirmedFlag
     */
    public String getShipConfirmedFlag() {
        return (String) getAttributeInternal(SHIPCONFIRMEDFLAG);
    }


    /**
     * Gets the attribute value for the calculated attribute SoNumber.
     * @return the SoNumber
     */
    public String getSoNumber() {
        return (String) getAttributeInternal(SONUMBER);
    }


    /**
     * Gets the attribute value for the calculated attribute ItemNumber.
     * @return the ItemNumber
     */
    public String getItemNumber() {
        return (String) getAttributeInternal(ITEMNUMBER);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute ItemNumber.
     * @param value value to set the  ItemNumber
     */
    public void setItemNumber(String value) {
        setAttributeInternal(ITEMNUMBER, value);
    }

    /**
     * Gets the attribute value for the calculated attribute Straggler.
     * @return the Straggler
     */
    public String getStraggler() {
        return (String) getAttributeInternal(STRAGGLER);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute Straggler.
     * @param value value to set the  Straggler
     */
    public void setStraggler(String value) {
        setAttributeInternal(STRAGGLER, value);
    }

    /**
     * Gets the attribute value for the calculated attribute Destination.
     * @return the Destination
     */
    public String getDestination() {
        return (String) getAttributeInternal(DESTINATION);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute Destination.
     * @param value value to set the  Destination
     */
    public void setDestination(String value) {
        setAttributeInternal(DESTINATION, value);
    }

    /**
     * Gets the attribute value for the calculated attribute LoadOrigin.
     * @return the LoadOrigin
     */
    public String getLoadOrigin() {
        return (String) getAttributeInternal(LOADORIGIN);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute LoadOrigin.
     * @param value value to set the  LoadOrigin
     */
    public void setLoadOrigin(String value) {
        setAttributeInternal(LOADORIGIN, value);
    }

    /**
     * Gets the attribute value for the calculated attribute Freight.
     * @return the Freight
     */
    public String getFreight() {
        return (String) getAttributeInternal(FREIGHT);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute Freight.
     * @param value value to set the  Freight
     */
    public void setFreight(String value) {
        setAttributeInternal(FREIGHT, value);
    }

    /**
     * Gets the attribute value for the calculated attribute EditFlag.
     * @return the EditFlag
     */
    public String getEditFlag() {
        return (String) getAttributeInternal(EDITFLAG);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute EditFlag.
     * @param value value to set the  EditFlag
     */
    public void setEditFlag(String value) {
        setAttributeInternal(EDITFLAG, value);
    }


    /**
     * Gets the attribute value for the calculated attribute StragglerFlag.
     * @return the StragglerFlag
     */
    public Boolean getStragglerFlag() {
        //return (Boolean) getAttributeInternal(STRAGGLERFLAG);
        if(this.getStraggler()!=null && this.getStraggler().equalsIgnoreCase("Y")){
            return true;
            }
        return false;
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute StragglerFlag.
     * @param value value to set the  StragglerFlag
     */
    public void setStragglerFlag(Boolean value) {
        //setAttributeInternal(STRAGGLERFLAG, value);
        if(value){
            this.setStraggler("Y");
            }
        else{
            this.setStraggler("N");
        }
    }


    /**
     * Gets the view accessor <code>RowSet</code> CeixStsDeliveryAM_CeixStsContractItemsVO1_1.
     */
    public RowSet getCeixStsDeliveryAM_CeixStsContractItemsVO1_1() {
        return (RowSet) getAttributeInternal(CEIXSTSDELIVERYAM_CEIXSTSCONTRACTITEMSVO1_1);
    }

    /**
     * Gets the view accessor <code>RowSet</code> CeixStsDeliveryAM_DestinationVO1_1.
     */
    public RowSet getCeixStsDeliveryAM_DestinationVO1_1() {
        return (RowSet) getAttributeInternal(CEIXSTSDELIVERYAM_DESTINATIONVO1_1);
    }

    /**
     * Gets the view accessor <code>RowSet</code> CeixStsDeliveryAM_LoadOriginVO1_1.
     */
    public RowSet getCeixStsDeliveryAM_LoadOriginVO1_1() {
        return (RowSet) getAttributeInternal(CEIXSTSDELIVERYAM_LOADORIGINVO1_1);
    }

    /**
     * getAttrInvokeAccessor: generated method. Do not modify.
     * @param index the index identifying the attribute
     * @param attrDef the attribute

     * @return the attribute value
     * @throws Exception
     */
    protected Object getAttrInvokeAccessor(int index,
                                           AttributeDefImpl attrDef) throws Exception {
        if ((index >= AttributesEnum.firstIndex()) && (index < AttributesEnum.count())) {
            return AttributesEnum.staticValues()[index - AttributesEnum.firstIndex()].get(this);
        }
        return super.getAttrInvokeAccessor(index, attrDef);
    }

    /**
     * setAttrInvokeAccessor: generated method. Do not modify.
     * @param index the index identifying the attribute
     * @param value the value to assign to the attribute
     * @param attrDef the attribute

     * @throws Exception
     */
    protected void setAttrInvokeAccessor(int index, Object value,
                                         AttributeDefImpl attrDef) throws Exception {
        if ((index >= AttributesEnum.firstIndex()) && (index < AttributesEnum.count())) {
            AttributesEnum.staticValues()[index - AttributesEnum.firstIndex()].put(this, value);
            return;
        }
        super.setAttrInvokeAccessor(index, value, attrDef);
    }
}