package com.ceix.qualnetxmlgen.model.view;

import java.sql.Date;

import oracle.jbo.domain.Number;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Wed May 16 14:32:45 IST 2018
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class QualityMetricsReportVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        OrderNumber {
            public Object get(QualityMetricsReportVORowImpl obj) {
                return obj.getOrderNumber();
            }

            public void put(QualityMetricsReportVORowImpl obj, Object value) {
                obj.setOrderNumber((String)value);
            }
        }
        ,
        ContractSoldTo {
            public Object get(QualityMetricsReportVORowImpl obj) {
                return obj.getContractSoldTo();
            }

            public void put(QualityMetricsReportVORowImpl obj, Object value) {
                obj.setContractSoldTo((String)value);
            }
        }
        ,
        ContractShipTo {
            public Object get(QualityMetricsReportVORowImpl obj) {
                return obj.getContractShipTo();
            }

            public void put(QualityMetricsReportVORowImpl obj, Object value) {
                obj.setContractShipTo((String)value);
            }
        }
        ,
        ContractShipToNumber {
            public Object get(QualityMetricsReportVORowImpl obj) {
                return obj.getContractShipToNumber();
            }

            public void put(QualityMetricsReportVORowImpl obj, Object value) {
                obj.setContractShipToNumber((String)value);
            }
        }
        ,
        OrderShipToName {
            public Object get(QualityMetricsReportVORowImpl obj) {
                return obj.getOrderShipToName();
            }

            public void put(QualityMetricsReportVORowImpl obj, Object value) {
                obj.setOrderShipToName((String)value);
            }
        }
        ,
        OrderStartDate {
            public Object get(QualityMetricsReportVORowImpl obj) {
                return obj.getOrderStartDate();
            }

            public void put(QualityMetricsReportVORowImpl obj, Object value) {
                obj.setOrderStartDate((Date)value);
            }
        }
        ,
        SulAsrTypical {
            public Object get(QualityMetricsReportVORowImpl obj) {
                return obj.getSulAsrTypical();
            }

            public void put(QualityMetricsReportVORowImpl obj, Object value) {
                obj.setSulAsrTypical((Number)value);
            }
        }
        ,
        SulAsrSuspension {
            public Object get(QualityMetricsReportVORowImpl obj) {
                return obj.getSulAsrSuspension();
            }

            public void put(QualityMetricsReportVORowImpl obj, Object value) {
                obj.setSulAsrSuspension((Number)value);
            }
        }
        ,
        SulAsrReject {
            public Object get(QualityMetricsReportVORowImpl obj) {
                return obj.getSulAsrReject();
            }

            public void put(QualityMetricsReportVORowImpl obj, Object value) {
                obj.setSulAsrReject((Number)value);
            }
        }
        ,
        SulAsrWarngCode {
            public Object get(QualityMetricsReportVORowImpl obj) {
                return obj.getSulAsrWarngCode();
            }

            public void put(QualityMetricsReportVORowImpl obj, Object value) {
                obj.setSulAsrWarngCode((String)value);
            }
        }
        ,
        ShipToAddress {
            public Object get(QualityMetricsReportVORowImpl obj) {
                return obj.getShipToAddress();
            }

            public void put(QualityMetricsReportVORowImpl obj, Object value) {
                obj.setShipToAddress((String)value);
            }
        }
        ,
        ContractNumber {
            public Object get(QualityMetricsReportVORowImpl obj) {
                return obj.getContractNumber();
            }

            public void put(QualityMetricsReportVORowImpl obj, Object value) {
                obj.setContractNumber((String)value);
            }
        }
        ,
        ContractDescription {
            public Object get(QualityMetricsReportVORowImpl obj) {
                return obj.getContractDescription();
            }

            public void put(QualityMetricsReportVORowImpl obj, Object value) {
                obj.setContractDescription((String)value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        public abstract Object get(QualityMetricsReportVORowImpl object);

        public abstract void put(QualityMetricsReportVORowImpl object,
                                 Object value);

        public int index() {
            return QualityMetricsReportVORowImpl.AttributesEnum.firstIndex() + ordinal();
        }

        public static final int firstIndex() {
            return firstIndex;
        }

        public static int count() {
            return QualityMetricsReportVORowImpl.AttributesEnum.firstIndex() + QualityMetricsReportVORowImpl.AttributesEnum.staticValues().length;
        }

        public static final AttributesEnum[] staticValues() {
            if (vals == null) {
                vals = QualityMetricsReportVORowImpl.AttributesEnum.values();
            }
            return vals;
        }
    }


    public static final int ORDERNUMBER = AttributesEnum.OrderNumber.index();
    public static final int CONTRACTSOLDTO = AttributesEnum.ContractSoldTo.index();
    public static final int CONTRACTSHIPTO = AttributesEnum.ContractShipTo.index();
    public static final int CONTRACTSHIPTONUMBER = AttributesEnum.ContractShipToNumber.index();
    public static final int ORDERSHIPTONAME = AttributesEnum.OrderShipToName.index();
    public static final int ORDERSTARTDATE = AttributesEnum.OrderStartDate.index();
    public static final int SULASRTYPICAL = AttributesEnum.SulAsrTypical.index();
    public static final int SULASRSUSPENSION = AttributesEnum.SulAsrSuspension.index();
    public static final int SULASRREJECT = AttributesEnum.SulAsrReject.index();
    public static final int SULASRWARNGCODE = AttributesEnum.SulAsrWarngCode.index();
    public static final int SHIPTOADDRESS = AttributesEnum.ShipToAddress.index();
    public static final int CONTRACTNUMBER = AttributesEnum.ContractNumber.index();
    public static final int CONTRACTDESCRIPTION = AttributesEnum.ContractDescription.index();

    /**
     * This is the default constructor (do not remove).
     */
    public QualityMetricsReportVORowImpl() {
    }

    /**
     * Gets the attribute value for the calculated attribute OrderNumber.
     * @return the OrderNumber
     */
    public String getOrderNumber() {
        return (String) getAttributeInternal(ORDERNUMBER);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute OrderNumber.
     * @param value value to set the  OrderNumber
     */
    public void setOrderNumber(String value) {
        setAttributeInternal(ORDERNUMBER, value);
    }

    /**
     * Gets the attribute value for the calculated attribute ContractSoldTo.
     * @return the ContractSoldTo
     */
    public String getContractSoldTo() {
        return (String) getAttributeInternal(CONTRACTSOLDTO);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute ContractSoldTo.
     * @param value value to set the  ContractSoldTo
     */
    public void setContractSoldTo(String value) {
        setAttributeInternal(CONTRACTSOLDTO, value);
    }

    /**
     * Gets the attribute value for the calculated attribute ContractShipTo.
     * @return the ContractShipTo
     */
    public String getContractShipTo() {
        return (String) getAttributeInternal(CONTRACTSHIPTO);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute ContractShipTo.
     * @param value value to set the  ContractShipTo
     */
    public void setContractShipTo(String value) {
        setAttributeInternal(CONTRACTSHIPTO, value);
    }

    /**
     * Gets the attribute value for the calculated attribute ContractShipToNumber.
     * @return the ContractShipToNumber
     */
    public String getContractShipToNumber() {
        return (String) getAttributeInternal(CONTRACTSHIPTONUMBER);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute ContractShipToNumber.
     * @param value value to set the  ContractShipToNumber
     */
    public void setContractShipToNumber(String value) {
        setAttributeInternal(CONTRACTSHIPTONUMBER, value);
    }

    /**
     * Gets the attribute value for the calculated attribute OrderShipToName.
     * @return the OrderShipToName
     */
    public String getOrderShipToName() {
        return (String) getAttributeInternal(ORDERSHIPTONAME);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute OrderShipToName.
     * @param value value to set the  OrderShipToName
     */
    public void setOrderShipToName(String value) {
        setAttributeInternal(ORDERSHIPTONAME, value);
    }

    /**
     * Gets the attribute value for the calculated attribute OrderStartDate.
     * @return the OrderStartDate
     */
    public Date getOrderStartDate() {
        return (Date) getAttributeInternal(ORDERSTARTDATE);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute OrderStartDate.
     * @param value value to set the  OrderStartDate
     */
    public void setOrderStartDate(Date value) {
        setAttributeInternal(ORDERSTARTDATE, value);
    }


    /**
     * Gets the attribute value for the calculated attribute SulAsrTypical.
     * @return the SulAsrTypical
     */
    public Number getSulAsrTypical() {
        return (Number) getAttributeInternal(SULASRTYPICAL);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute SulAsrTypical.
     * @param value value to set the  SulAsrTypical
     */
    public void setSulAsrTypical(Number value) {
        setAttributeInternal(SULASRTYPICAL, value);
    }

    /**
     * Gets the attribute value for the calculated attribute SulAsrSuspension.
     * @return the SulAsrSuspension
     */
    public Number getSulAsrSuspension() {
        return (Number) getAttributeInternal(SULASRSUSPENSION);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute SulAsrSuspension.
     * @param value value to set the  SulAsrSuspension
     */
    public void setSulAsrSuspension(Number value) {
        setAttributeInternal(SULASRSUSPENSION, value);
    }

    /**
     * Gets the attribute value for the calculated attribute SulAsrReject.
     * @return the SulAsrReject
     */
    public Number getSulAsrReject() {
        return (Number) getAttributeInternal(SULASRREJECT);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute SulAsrReject.
     * @param value value to set the  SulAsrReject
     */
    public void setSulAsrReject(Number value) {
        setAttributeInternal(SULASRREJECT, value);
    }

    /**
     * Gets the attribute value for the calculated attribute SulAsrWarngCode.
     * @return the SulAsrWarngCode
     */
    public String getSulAsrWarngCode() {
        return (String) getAttributeInternal(SULASRWARNGCODE);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute SulAsrWarngCode.
     * @param value value to set the  SulAsrWarngCode
     */
    public void setSulAsrWarngCode(String value) {
        setAttributeInternal(SULASRWARNGCODE, value);
    }


    /**
     * Gets the attribute value for the calculated attribute ShipToAddress.
     * @return the ShipToAddress
     */
    public String getShipToAddress() {
        return (String) getAttributeInternal(SHIPTOADDRESS);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute ShipToAddress.
     * @param value value to set the  ShipToAddress
     */
    public void setShipToAddress(String value) {
        setAttributeInternal(SHIPTOADDRESS, value);
    }

    /**
     * Gets the attribute value for the calculated attribute ContractNumber.
     * @return the ContractNumber
     */
    public String getContractNumber() {
        return (String) getAttributeInternal(CONTRACTNUMBER);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute ContractNumber.
     * @param value value to set the  ContractNumber
     */
    public void setContractNumber(String value) {
        setAttributeInternal(CONTRACTNUMBER, value);
    }

    /**
     * Gets the attribute value for the calculated attribute ContractDescription.
     * @return the ContractDescription
     */
    public String getContractDescription() {
        return (String) getAttributeInternal(CONTRACTDESCRIPTION);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute ContractDescription.
     * @param value value to set the  ContractDescription
     */
    public void setContractDescription(String value) {
        setAttributeInternal(CONTRACTDESCRIPTION, value);
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