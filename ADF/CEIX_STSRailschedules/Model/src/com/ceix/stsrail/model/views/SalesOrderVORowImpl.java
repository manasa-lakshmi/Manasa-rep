package com.ceix.stsrail.model.views;

import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Tue May 15 15:14:03 CDT 2018
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class SalesOrderVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        OrderNumber {
            protected Object get(SalesOrderVORowImpl obj) {
                return obj.getOrderNumber();
            }

            protected void put(SalesOrderVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        OrderType {
            protected Object get(SalesOrderVORowImpl obj) {
                return obj.getOrderType();
            }

            protected void put(SalesOrderVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        AccountNumber {
            protected Object get(SalesOrderVORowImpl obj) {
                return obj.getAccountNumber();
            }

            protected void put(SalesOrderVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        AccountName {
            protected Object get(SalesOrderVORowImpl obj) {
                return obj.getAccountName();
            }

            protected void put(SalesOrderVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        ShipToAddress {
            protected Object get(SalesOrderVORowImpl obj) {
                return obj.getShipToAddress();
            }

            protected void put(SalesOrderVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        ContractNumber {
            protected Object get(SalesOrderVORowImpl obj) {
                return obj.getContractNumber();
            }

            protected void put(SalesOrderVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        ContractDescription {
            protected Object get(SalesOrderVORowImpl obj) {
                return obj.getContractDescription();
            }

            protected void put(SalesOrderVORowImpl obj, Object value) {
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

        protected abstract Object get(SalesOrderVORowImpl object);

        protected abstract void put(SalesOrderVORowImpl object, Object value);
    }


    public static final int ORDERNUMBER = AttributesEnum.OrderNumber.index();
    public static final int ORDERTYPE = AttributesEnum.OrderType.index();
    public static final int ACCOUNTNUMBER = AttributesEnum.AccountNumber.index();
    public static final int ACCOUNTNAME = AttributesEnum.AccountName.index();
    public static final int SHIPTOADDRESS = AttributesEnum.ShipToAddress.index();
    public static final int CONTRACTNUMBER = AttributesEnum.ContractNumber.index();
    public static final int CONTRACTDESCRIPTION = AttributesEnum.ContractDescription.index();

    /**
     * This is the default constructor (do not remove).
     */
    public SalesOrderVORowImpl() {
    }

    /**
     * Gets the attribute value for the calculated attribute OrderNumber.
     * @return the OrderNumber
     */
    public String getOrderNumber() {
        return (String) getAttributeInternal(ORDERNUMBER);
    }


    /**
     * Gets the attribute value for the calculated attribute OrderType.
     * @return the OrderType
     */
    public String getOrderType() {
        return (String) getAttributeInternal(ORDERTYPE);
    }


    /**
     * Gets the attribute value for the calculated attribute AccountNumber.
     * @return the AccountNumber
     */
    public String getAccountNumber() {
        return (String) getAttributeInternal(ACCOUNTNUMBER);
    }


    /**
     * Gets the attribute value for the calculated attribute AccountName.
     * @return the AccountName
     */
    public String getAccountName() {
        return (String) getAttributeInternal(ACCOUNTNAME);
    }


    /**
     * Gets the attribute value for the calculated attribute ShipToAddress.
     * @return the ShipToAddress
     */
    public String getShipToAddress() {
        return (String) getAttributeInternal(SHIPTOADDRESS);
    }


    /**
     * Gets the attribute value for the calculated attribute ContractNumber.
     * @return the ContractNumber
     */
    public String getContractNumber() {
        return (String) getAttributeInternal(CONTRACTNUMBER);
    }


    /**
     * Gets the attribute value for the calculated attribute ContractDescription.
     * @return the ContractDescription
     */
    public String getContractDescription() {
        return (String) getAttributeInternal(CONTRACTDESCRIPTION);
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
