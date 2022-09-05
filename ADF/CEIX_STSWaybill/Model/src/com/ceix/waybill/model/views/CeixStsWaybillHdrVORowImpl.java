package com.ceix.waybill.model.views;

import com.ceix.waybill.model.entities.CeixStsWaybillHdrEOImpl;

import oracle.jbo.RowIterator;
import oracle.jbo.domain.Date;
import oracle.jbo.domain.Number;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Thu May 31 19:42:37 CDT 2018
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class CeixStsWaybillHdrVORowImpl extends ViewRowImpl {
    public static final int ENTITY_CEIXSTSWAYBILLHDREO = 0;

    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        DeliveryHeaderId {
            protected Object get(CeixStsWaybillHdrVORowImpl obj) {
                return obj.getDeliveryHeaderId();
            }

            protected void put(CeixStsWaybillHdrVORowImpl obj, Object value) {
                obj.setDeliveryHeaderId((Number) value);
            }
        }
        ,
        TrainNumber {
            protected Object get(CeixStsWaybillHdrVORowImpl obj) {
                return obj.getTrainNumber();
            }

            protected void put(CeixStsWaybillHdrVORowImpl obj, Object value) {
                obj.setTrainNumber((String) value);
            }
        }
        ,
        DumpedDate {
            protected Object get(CeixStsWaybillHdrVORowImpl obj) {
                return obj.getDumpedDate();
            }

            protected void put(CeixStsWaybillHdrVORowImpl obj, Object value) {
                obj.setDumpedDate((Date) value);
            }
        }
        ,
        DumpLocation {
            protected Object get(CeixStsWaybillHdrVORowImpl obj) {
                return obj.getDumpLocation();
            }

            protected void put(CeixStsWaybillHdrVORowImpl obj, Object value) {
                obj.setDumpLocation((String) value);
            }
        }
        ,
        FreightCost {
            protected Object get(CeixStsWaybillHdrVORowImpl obj) {
                return obj.getFreightCost();
            }

            protected void put(CeixStsWaybillHdrVORowImpl obj, Object value) {
                obj.setFreightCost((Number) value);
            }
        }
        ,
        CreationDate {
            protected Object get(CeixStsWaybillHdrVORowImpl obj) {
                return obj.getCreationDate();
            }

            protected void put(CeixStsWaybillHdrVORowImpl obj, Object value) {
                obj.setCreationDate((Date) value);
            }
        }
        ,
        CreatedBy {
            protected Object get(CeixStsWaybillHdrVORowImpl obj) {
                return obj.getCreatedBy();
            }

            protected void put(CeixStsWaybillHdrVORowImpl obj, Object value) {
                obj.setCreatedBy((String) value);
            }
        }
        ,
        LastUpdateDate {
            protected Object get(CeixStsWaybillHdrVORowImpl obj) {
                return obj.getLastUpdateDate();
            }

            protected void put(CeixStsWaybillHdrVORowImpl obj, Object value) {
                obj.setLastUpdateDate((Date) value);
            }
        }
        ,
        LastUpdatedBy {
            protected Object get(CeixStsWaybillHdrVORowImpl obj) {
                return obj.getLastUpdatedBy();
            }

            protected void put(CeixStsWaybillHdrVORowImpl obj, Object value) {
                obj.setLastUpdatedBy((String) value);
            }
        }
        ,
        ShipDate {
            protected Object get(CeixStsWaybillHdrVORowImpl obj) {
                return obj.getShipDate();
            }

            protected void put(CeixStsWaybillHdrVORowImpl obj, Object value) {
                obj.setShipDate((Date) value);
            }
        }
        ,
        CeixStsWaybillLinesVO {
            protected Object get(CeixStsWaybillHdrVORowImpl obj) {
                return obj.getCeixStsWaybillLinesVO();
            }

            protected void put(CeixStsWaybillHdrVORowImpl obj, Object value) {
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

        protected abstract Object get(CeixStsWaybillHdrVORowImpl object);

        protected abstract void put(CeixStsWaybillHdrVORowImpl object, Object value);
    }

    public static final int DELIVERYHEADERID = AttributesEnum.DeliveryHeaderId.index();
    public static final int TRAINNUMBER = AttributesEnum.TrainNumber.index();
    public static final int DUMPEDDATE = AttributesEnum.DumpedDate.index();
    public static final int DUMPLOCATION = AttributesEnum.DumpLocation.index();
    public static final int FREIGHTCOST = AttributesEnum.FreightCost.index();
    public static final int CREATIONDATE = AttributesEnum.CreationDate.index();
    public static final int CREATEDBY = AttributesEnum.CreatedBy.index();
    public static final int LASTUPDATEDATE = AttributesEnum.LastUpdateDate.index();
    public static final int LASTUPDATEDBY = AttributesEnum.LastUpdatedBy.index();
    public static final int SHIPDATE = AttributesEnum.ShipDate.index();
    public static final int CEIXSTSWAYBILLLINESVO = AttributesEnum.CeixStsWaybillLinesVO.index();

    /**
     * This is the default constructor (do not remove).
     */
    public CeixStsWaybillHdrVORowImpl() {
    }

    /**
     * Gets CeixStsWaybillHdrEO entity object.
     * @return the CeixStsWaybillHdrEO
     */
    public CeixStsWaybillHdrEOImpl getCeixStsWaybillHdrEO() {
        return (CeixStsWaybillHdrEOImpl)getEntity(0);
    }

    /**
     * Gets the attribute value for DELIVERY_HEADER_ID using the alias name DeliveryHeaderId.
     * @return the DELIVERY_HEADER_ID
     */
    public Number getDeliveryHeaderId() {
        return (Number) getAttributeInternal(DELIVERYHEADERID);
    }

    /**
     * Sets <code>value</code> as attribute value for DELIVERY_HEADER_ID using the alias name DeliveryHeaderId.
     * @param value value to set the DELIVERY_HEADER_ID
     */
    public void setDeliveryHeaderId(Number value) {
        setAttributeInternal(DELIVERYHEADERID, value);
    }

    /**
     * Gets the attribute value for TRAIN_NUMBER using the alias name TrainNumber.
     * @return the TRAIN_NUMBER
     */
    public String getTrainNumber() {
        return (String) getAttributeInternal(TRAINNUMBER);
    }

    /**
     * Sets <code>value</code> as attribute value for TRAIN_NUMBER using the alias name TrainNumber.
     * @param value value to set the TRAIN_NUMBER
     */
    public void setTrainNumber(String value) {
        setAttributeInternal(TRAINNUMBER, value);
    }

    /**
     * Gets the attribute value for DUMPED_DATE using the alias name DumpedDate.
     * @return the DUMPED_DATE
     */
    public Date getDumpedDate() {
        return (Date) getAttributeInternal(DUMPEDDATE);
    }

    /**
     * Sets <code>value</code> as attribute value for DUMPED_DATE using the alias name DumpedDate.
     * @param value value to set the DUMPED_DATE
     */
    public void setDumpedDate(Date value) {
        setAttributeInternal(DUMPEDDATE, value);
    }

    /**
     * Gets the attribute value for DUMP_LOCATION using the alias name DumpLocation.
     * @return the DUMP_LOCATION
     */
    public String getDumpLocation() {
        return (String) getAttributeInternal(DUMPLOCATION);
    }

    /**
     * Sets <code>value</code> as attribute value for DUMP_LOCATION using the alias name DumpLocation.
     * @param value value to set the DUMP_LOCATION
     */
    public void setDumpLocation(String value) {
        setAttributeInternal(DUMPLOCATION, value);
    }

    /**
     * Gets the attribute value for FREIGHT_COST using the alias name FreightCost.
     * @return the FREIGHT_COST
     */
    public Number getFreightCost() {
        return (Number) getAttributeInternal(FREIGHTCOST);
    }

    /**
     * Sets <code>value</code> as attribute value for FREIGHT_COST using the alias name FreightCost.
     * @param value value to set the FREIGHT_COST
     */
    public void setFreightCost(Number value) {
        setAttributeInternal(FREIGHTCOST, value);
    }

    /**
     * Gets the attribute value for CREATION_DATE using the alias name CreationDate.
     * @return the CREATION_DATE
     */
    public Date getCreationDate() {
        return (Date) getAttributeInternal(CREATIONDATE);
    }

    /**
     * Sets <code>value</code> as attribute value for CREATION_DATE using the alias name CreationDate.
     * @param value value to set the CREATION_DATE
     */
    public void setCreationDate(Date value) {
        setAttributeInternal(CREATIONDATE, value);
    }

    /**
     * Gets the attribute value for CREATED_BY using the alias name CreatedBy.
     * @return the CREATED_BY
     */
    public String getCreatedBy() {
        return (String) getAttributeInternal(CREATEDBY);
    }

    /**
     * Sets <code>value</code> as attribute value for CREATED_BY using the alias name CreatedBy.
     * @param value value to set the CREATED_BY
     */
    public void setCreatedBy(String value) {
        setAttributeInternal(CREATEDBY, value);
    }

    /**
     * Gets the attribute value for LAST_UPDATE_DATE using the alias name LastUpdateDate.
     * @return the LAST_UPDATE_DATE
     */
    public Date getLastUpdateDate() {
        return (Date) getAttributeInternal(LASTUPDATEDATE);
    }

    /**
     * Sets <code>value</code> as attribute value for LAST_UPDATE_DATE using the alias name LastUpdateDate.
     * @param value value to set the LAST_UPDATE_DATE
     */
    public void setLastUpdateDate(Date value) {
        setAttributeInternal(LASTUPDATEDATE, value);
    }

    /**
     * Gets the attribute value for LAST_UPDATED_BY using the alias name LastUpdatedBy.
     * @return the LAST_UPDATED_BY
     */
    public String getLastUpdatedBy() {
        return (String) getAttributeInternal(LASTUPDATEDBY);
    }

    /**
     * Sets <code>value</code> as attribute value for LAST_UPDATED_BY using the alias name LastUpdatedBy.
     * @param value value to set the LAST_UPDATED_BY
     */
    public void setLastUpdatedBy(String value) {
        setAttributeInternal(LASTUPDATEDBY, value);
    }

    /**
     * Gets the attribute value for SHIP_DATE using the alias name ShipDate.
     * @return the SHIP_DATE
     */
    public Date getShipDate() {
        return (Date) getAttributeInternal(SHIPDATE);
    }

    /**
     * Sets <code>value</code> as attribute value for SHIP_DATE using the alias name ShipDate.
     * @param value value to set the SHIP_DATE
     */
    public void setShipDate(Date value) {
        setAttributeInternal(SHIPDATE, value);
    }

    /**
     * Gets the associated <code>RowIterator</code> using master-detail link CeixStsWaybillLinesVO.
     */
    public RowIterator getCeixStsWaybillLinesVO() {
        return (RowIterator)getAttributeInternal(CEIXSTSWAYBILLLINESVO);
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
