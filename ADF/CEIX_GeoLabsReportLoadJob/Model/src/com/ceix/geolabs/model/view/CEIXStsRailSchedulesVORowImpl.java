package com.ceix.geolabs.model.view;

import oracle.jbo.domain.Date;
import oracle.jbo.domain.Number;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Fri May 11 18:10:12 IST 2018
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class CEIXStsRailSchedulesVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        RailScheduleId {
            public Object get(CEIXStsRailSchedulesVORowImpl obj) {
                return obj.getRailScheduleId();
            }

            public void put(CEIXStsRailSchedulesVORowImpl obj, Object value) {
                obj.setRailScheduleId((Number)value);
            }
        }
        ,
        SalesOrderNumber {
            public Object get(CEIXStsRailSchedulesVORowImpl obj) {
                return obj.getSalesOrderNumber();
            }

            public void put(CEIXStsRailSchedulesVORowImpl obj, Object value) {
                obj.setSalesOrderNumber((String)value);
            }
        }
        ,
        TrainId {
            public Object get(CEIXStsRailSchedulesVORowImpl obj) {
                return obj.getTrainId();
            }

            public void put(CEIXStsRailSchedulesVORowImpl obj, Object value) {
                obj.setTrainId((String)value);
            }
        }
        ,
        LoadDate {
            public Object get(CEIXStsRailSchedulesVORowImpl obj) {
                return obj.getLoadDate();
            }

            public void put(CEIXStsRailSchedulesVORowImpl obj, Object value) {
                obj.setLoadDate((Date)value);
            }
        }
        ,
        LoadDtShort {
            public Object get(CEIXStsRailSchedulesVORowImpl obj) {
                return obj.getLoadDtShort();
            }

            public void put(CEIXStsRailSchedulesVORowImpl obj, Object value) {
                obj.setLoadDtShort((Date)value);
            }
        }
        ,
        Loaddtfrom {
            public Object get(CEIXStsRailSchedulesVORowImpl obj) {
                return obj.getLoaddtfrom();
            }

            public void put(CEIXStsRailSchedulesVORowImpl obj, Object value) {
                obj.setLoaddtfrom((Date)value);
            }
        }
        ,
        Loaddtto {
            public Object get(CEIXStsRailSchedulesVORowImpl obj) {
                return obj.getLoaddtto();
            }

            public void put(CEIXStsRailSchedulesVORowImpl obj, Object value) {
                obj.setLoaddtto((Date)value);
            }
        }
        ,
        LoadDateShort {
            public Object get(CEIXStsRailSchedulesVORowImpl obj) {
                return obj.getLoadDateShort();
            }

            public void put(CEIXStsRailSchedulesVORowImpl obj, Object value) {
                obj.setLoadDateShort((String)value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        public abstract Object get(CEIXStsRailSchedulesVORowImpl object);

        public abstract void put(CEIXStsRailSchedulesVORowImpl object,
                                 Object value);

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
    }


    public static final int RAILSCHEDULEID = AttributesEnum.RailScheduleId.index();
    public static final int SALESORDERNUMBER = AttributesEnum.SalesOrderNumber.index();
    public static final int TRAINID = AttributesEnum.TrainId.index();
    public static final int LOADDATE = AttributesEnum.LoadDate.index();
    public static final int LOADDTSHORT = AttributesEnum.LoadDtShort.index();
    public static final int LOADDTFROM = AttributesEnum.Loaddtfrom.index();
    public static final int LOADDTTO = AttributesEnum.Loaddtto.index();
    public static final int LOADDATESHORT = AttributesEnum.LoadDateShort.index();

    /**
     * This is the default constructor (do not remove).
     */
    public CEIXStsRailSchedulesVORowImpl() {
    }

    /**
     * Gets CEIXStsRailSchedulesEO entity object.
     * @return the CEIXStsRailSchedulesEO
     */
    public EntityImpl getCEIXStsRailSchedulesEO() {
        return (EntityImpl)getEntity(0);
    }

    /**
     * Gets the attribute value for RAIL_SCHEDULE_ID using the alias name RailScheduleId.
     * @return the RAIL_SCHEDULE_ID
     */
    public Number getRailScheduleId() {
        return (Number) getAttributeInternal(RAILSCHEDULEID);
    }

    /**
     * Sets <code>value</code> as attribute value for RAIL_SCHEDULE_ID using the alias name RailScheduleId.
     * @param value value to set the RAIL_SCHEDULE_ID
     */
    public void setRailScheduleId(Number value) {
        setAttributeInternal(RAILSCHEDULEID, value);
    }

    /**
     * Gets the attribute value for SALES_ORDER_NUMBER using the alias name SalesOrderNumber.
     * @return the SALES_ORDER_NUMBER
     */
    public String getSalesOrderNumber() {
        return (String) getAttributeInternal(SALESORDERNUMBER);
    }

    /**
     * Sets <code>value</code> as attribute value for SALES_ORDER_NUMBER using the alias name SalesOrderNumber.
     * @param value value to set the SALES_ORDER_NUMBER
     */
    public void setSalesOrderNumber(String value) {
        setAttributeInternal(SALESORDERNUMBER, value);
    }

    /**
     * Gets the attribute value for TRAIN_ID using the alias name TrainId.
     * @return the TRAIN_ID
     */
    public String getTrainId() {
        return (String) getAttributeInternal(TRAINID);
    }

    /**
     * Sets <code>value</code> as attribute value for TRAIN_ID using the alias name TrainId.
     * @param value value to set the TRAIN_ID
     */
    public void setTrainId(String value) {
        setAttributeInternal(TRAINID, value);
    }

    /**
     * Gets the attribute value for LOAD_DATE using the alias name LoadDate.
     * @return the LOAD_DATE
     */
    public Date getLoadDate() {
        return (Date) getAttributeInternal(LOADDATE);
    }

    /**
     * Sets <code>value</code> as attribute value for LOAD_DATE using the alias name LoadDate.
     * @param value value to set the LOAD_DATE
     */
    public void setLoadDate(Date value) {
        setAttributeInternal(LOADDATE, value);
    }


    /**
     * Gets the attribute value for LOADDATESHORT using the alias name LoadDateShort.
     * @return the LOADDATESHORT
     */
    public String getLoadDateShort() {
        return (String) getAttributeInternal(LOADDATESHORT);
    }

    /**
     * Sets <code>value</code> as attribute value for LOADDATESHORT using the alias name LoadDateShort.
     * @param value value to set the LOADDATESHORT
     */
    public void setLoadDateShort(String value) {
        setAttributeInternal(LOADDATESHORT, value);
    }

    /**
     * Gets the attribute value for the calculated attribute LoadDtShort.
     * @return the LoadDtShort
     */
    public Date getLoadDtShort() {
        return (Date) getAttributeInternal(LOADDTSHORT);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute LoadDtShort.
     * @param value value to set the  LoadDtShort
     */
    public void setLoadDtShort(Date value) {
        setAttributeInternal(LOADDTSHORT, value);
    }

    /**
     * Gets the attribute value for the calculated attribute Loaddtfrom.
     * @return the Loaddtfrom
     */
    public Date getLoaddtfrom() {
        return (Date) getAttributeInternal(LOADDTFROM);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute Loaddtfrom.
     * @param value value to set the  Loaddtfrom
     */
    public void setLoaddtfrom(Date value) {
        setAttributeInternal(LOADDTFROM, value);
    }

    /**
     * Gets the attribute value for the calculated attribute Loaddtto.
     * @return the Loaddtto
     */
    public Date getLoaddtto() {
        return (Date) getAttributeInternal(LOADDTTO);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute Loaddtto.
     * @param value value to set the  Loaddtto
     */
    public void setLoaddtto(Date value) {
        setAttributeInternal(LOADDTTO, value);
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