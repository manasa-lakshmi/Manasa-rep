package com.ceix.waybill_purch_coal_import.model.views;

import java.sql.Date;

import oracle.jbo.domain.Number;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Thu Jun 28 13:57:57 IST 2018
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class CEIXStsDeliveryLinesVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        DeliveryLineId {
            public Object get(CEIXStsDeliveryLinesVORowImpl obj) {
                return obj.getDeliveryLineId();
            }

            public void put(CEIXStsDeliveryLinesVORowImpl obj, Object value) {
                obj.setDeliveryLineId((Number)value);
            }
        }
        ,
        CreationDate {
            public Object get(CEIXStsDeliveryLinesVORowImpl obj) {
                return obj.getCreationDate();
            }

            public void put(CEIXStsDeliveryLinesVORowImpl obj, Object value) {
                obj.setCreationDate((Date)value);
            }
        }
        ,
        CreatedBy {
            public Object get(CEIXStsDeliveryLinesVORowImpl obj) {
                return obj.getCreatedBy();
            }

            public void put(CEIXStsDeliveryLinesVORowImpl obj, Object value) {
                obj.setCreatedBy((String)value);
            }
        }
        ,
        LastUpdateDate {
            public Object get(CEIXStsDeliveryLinesVORowImpl obj) {
                return obj.getLastUpdateDate();
            }

            public void put(CEIXStsDeliveryLinesVORowImpl obj, Object value) {
                obj.setLastUpdateDate((Date)value);
            }
        }
        ,
        LastUpdatedBy {
            public Object get(CEIXStsDeliveryLinesVORowImpl obj) {
                return obj.getLastUpdatedBy();
            }

            public void put(CEIXStsDeliveryLinesVORowImpl obj, Object value) {
                obj.setLastUpdatedBy((String)value);
            }
        }
        ,
        DeliveryHeaderId {
            public Object get(CEIXStsDeliveryLinesVORowImpl obj) {
                return obj.getDeliveryHeaderId();
            }

            public void put(CEIXStsDeliveryLinesVORowImpl obj, Object value) {
                obj.setDeliveryHeaderId((Number)value);
            }
        }
        ,
        SalesOrderNumber {
            public Object get(CEIXStsDeliveryLinesVORowImpl obj) {
                return obj.getSalesOrderNumber();
            }

            public void put(CEIXStsDeliveryLinesVORowImpl obj, Object value) {
                obj.setSalesOrderNumber((String)value);
            }
        }
        ,
        UnitTrainNumber {
            public Object get(CEIXStsDeliveryLinesVORowImpl obj) {
                return obj.getUnitTrainNumber();
            }

            public void put(CEIXStsDeliveryLinesVORowImpl obj, Object value) {
                obj.setUnitTrainNumber((String)value);
            }
        }
        ,
        NewTrainNumber {
            public Object get(CEIXStsDeliveryLinesVORowImpl obj) {
                return obj.getNewTrainNumber();
            }

            public void put(CEIXStsDeliveryLinesVORowImpl obj, Object value) {
                obj.setNewTrainNumber((String)value);
            }
        }
        ,
        ScheduleDate {
            public Object get(CEIXStsDeliveryLinesVORowImpl obj) {
                return obj.getScheduleDate();
            }

            public void put(CEIXStsDeliveryLinesVORowImpl obj, Object value) {
                obj.setScheduleDate((Date)value);
            }
        }
        ,
        SequenceNum {
            public Object get(CEIXStsDeliveryLinesVORowImpl obj) {
                return obj.getSequenceNum();
            }

            public void put(CEIXStsDeliveryLinesVORowImpl obj, Object value) {
                obj.setSequenceNum((String)value);
            }
        }
        ,
        CarId {
            public Object get(CEIXStsDeliveryLinesVORowImpl obj) {
                return obj.getCarId();
            }

            public void put(CEIXStsDeliveryLinesVORowImpl obj, Object value) {
                obj.setCarId((String)value);
            }
        }
        ,
        NewCarId {
            public Object get(CEIXStsDeliveryLinesVORowImpl obj) {
                return obj.getNewCarId();
            }

            public void put(CEIXStsDeliveryLinesVORowImpl obj, Object value) {
                obj.setNewCarId((String)value);
            }
        }
        ,
        GrossAllow {
            public Object get(CEIXStsDeliveryLinesVORowImpl obj) {
                return obj.getGrossAllow();
            }

            public void put(CEIXStsDeliveryLinesVORowImpl obj, Object value) {
                obj.setGrossAllow((String)value);
            }
        }
        ,
        CarTare {
            public Object get(CEIXStsDeliveryLinesVORowImpl obj) {
                return obj.getCarTare();
            }

            public void put(CEIXStsDeliveryLinesVORowImpl obj, Object value) {
                obj.setCarTare((String)value);
            }
        }
        ,
        Ownership {
            public Object get(CEIXStsDeliveryLinesVORowImpl obj) {
                return obj.getOwnership();
            }

            public void put(CEIXStsDeliveryLinesVORowImpl obj, Object value) {
                obj.setOwnership((String)value);
            }
        }
        ,
        Weight {
            public Object get(CEIXStsDeliveryLinesVORowImpl obj) {
                return obj.getWeight();
            }

            public void put(CEIXStsDeliveryLinesVORowImpl obj, Object value) {
                obj.setWeight((String)value);
            }
        }
        ,
        WeightInTons {
            public Object get(CEIXStsDeliveryLinesVORowImpl obj) {
                return obj.getWeightInTons();
            }

            public void put(CEIXStsDeliveryLinesVORowImpl obj, Object value) {
                obj.setWeightInTons((Number)value);
            }
        }
        ,
        LoadTime {
            public Object get(CEIXStsDeliveryLinesVORowImpl obj) {
                return obj.getLoadTime();
            }

            public void put(CEIXStsDeliveryLinesVORowImpl obj, Object value) {
                obj.setLoadTime((String)value);
            }
        }
        ,
        BinGross {
            public Object get(CEIXStsDeliveryLinesVORowImpl obj) {
                return obj.getBinGross();
            }

            public void put(CEIXStsDeliveryLinesVORowImpl obj, Object value) {
                obj.setBinGross((String)value);
            }
        }
        ,
        BinTare {
            public Object get(CEIXStsDeliveryLinesVORowImpl obj) {
                return obj.getBinTare();
            }

            public void put(CEIXStsDeliveryLinesVORowImpl obj, Object value) {
                obj.setBinTare((String)value);
            }
        }
        ,
        LoadedNett {
            public Object get(CEIXStsDeliveryLinesVORowImpl obj) {
                return obj.getLoadedNett();
            }

            public void put(CEIXStsDeliveryLinesVORowImpl obj, Object value) {
                obj.setLoadedNett((String)value);
            }
        }
        ,
        LineChild11 {
            public Object get(CEIXStsDeliveryLinesVORowImpl obj) {
                return obj.getLineChild11();
            }

            public void put(CEIXStsDeliveryLinesVORowImpl obj, Object value) {
                obj.setLineChild11((String)value);
            }
        }
        ,
        ShipmentNumber {
            public Object get(CEIXStsDeliveryLinesVORowImpl obj) {
                return obj.getShipmentNumber();
            }

            public void put(CEIXStsDeliveryLinesVORowImpl obj, Object value) {
                obj.setShipmentNumber((String)value);
            }
        }
        ,
        FreightCost {
            public Object get(CEIXStsDeliveryLinesVORowImpl obj) {
                return obj.getFreightCost();
            }

            public void put(CEIXStsDeliveryLinesVORowImpl obj, Object value) {
                obj.setFreightCost((String)value);
            }
        }
        ,
        Comments {
            public Object get(CEIXStsDeliveryLinesVORowImpl obj) {
                return obj.getComments();
            }

            public void put(CEIXStsDeliveryLinesVORowImpl obj, Object value) {
                obj.setComments((String)value);
            }
        }
        ,
        ProcessedFlag {
            public Object get(CEIXStsDeliveryLinesVORowImpl obj) {
                return obj.getProcessedFlag();
            }

            public void put(CEIXStsDeliveryLinesVORowImpl obj, Object value) {
                obj.setProcessedFlag((String)value);
            }
        }
        ,
        TransferredFlag {
            public Object get(CEIXStsDeliveryLinesVORowImpl obj) {
                return obj.getTransferredFlag();
            }

            public void put(CEIXStsDeliveryLinesVORowImpl obj, Object value) {
                obj.setTransferredFlag((String)value);
            }
        }
        ,
        CarPrefix {
            public Object get(CEIXStsDeliveryLinesVORowImpl obj) {
                return obj.getCarPrefix();
            }

            public void put(CEIXStsDeliveryLinesVORowImpl obj, Object value) {
                obj.setCarPrefix((String)value);
            }
        }
        ,
        Displaycarid {
            public Object get(CEIXStsDeliveryLinesVORowImpl obj) {
                return obj.getDisplaycarid();
            }

            public void put(CEIXStsDeliveryLinesVORowImpl obj, Object value) {
                obj.setDisplaycarid((String)value);
            }
        }
        ,
        NewCarPrefix {
            public Object get(CEIXStsDeliveryLinesVORowImpl obj) {
                return obj.getNewCarPrefix();
            }

            public void put(CEIXStsDeliveryLinesVORowImpl obj, Object value) {
                obj.setNewCarPrefix((String)value);
            }
        }
        ,
        RailRoad {
            public Object get(CEIXStsDeliveryLinesVORowImpl obj) {
                return obj.getRailRoad();
            }

            public void put(CEIXStsDeliveryLinesVORowImpl obj, Object value) {
                obj.setRailRoad((String)value);
            }
        }
        ,
        ItemNumber {
            public Object get(CEIXStsDeliveryLinesVORowImpl obj) {
                return obj.getItemNumber();
            }

            public void put(CEIXStsDeliveryLinesVORowImpl obj, Object value) {
                obj.setItemNumber((String) value);
            }
        }
        ,
        Destination {
            public Object get(CEIXStsDeliveryLinesVORowImpl obj) {
                return obj.getDestination();
            }

            public void put(CEIXStsDeliveryLinesVORowImpl obj, Object value) {
                obj.setDestination((String) value);
            }
        }
        ,
        LoadOrigin {
            public Object get(CEIXStsDeliveryLinesVORowImpl obj) {
                return obj.getLoadOrigin();
            }

            public void put(CEIXStsDeliveryLinesVORowImpl obj, Object value) {
                obj.setLoadOrigin((String) value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        public abstract Object get(CEIXStsDeliveryLinesVORowImpl object);

        public abstract void put(CEIXStsDeliveryLinesVORowImpl object,
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
    public static final int DELIVERYLINEID = AttributesEnum.DeliveryLineId.index();
    public static final int CREATIONDATE = AttributesEnum.CreationDate.index();
    public static final int CREATEDBY = AttributesEnum.CreatedBy.index();
    public static final int LASTUPDATEDATE = AttributesEnum.LastUpdateDate.index();
    public static final int LASTUPDATEDBY = AttributesEnum.LastUpdatedBy.index();
    public static final int DELIVERYHEADERID = AttributesEnum.DeliveryHeaderId.index();
    public static final int SALESORDERNUMBER = AttributesEnum.SalesOrderNumber.index();
    public static final int UNITTRAINNUMBER = AttributesEnum.UnitTrainNumber.index();
    public static final int NEWTRAINNUMBER = AttributesEnum.NewTrainNumber.index();
    public static final int SCHEDULEDATE = AttributesEnum.ScheduleDate.index();
    public static final int SEQUENCENUM = AttributesEnum.SequenceNum.index();
    public static final int CARID = AttributesEnum.CarId.index();
    public static final int NEWCARID = AttributesEnum.NewCarId.index();
    public static final int GROSSALLOW = AttributesEnum.GrossAllow.index();
    public static final int CARTARE = AttributesEnum.CarTare.index();
    public static final int OWNERSHIP = AttributesEnum.Ownership.index();
    public static final int WEIGHT = AttributesEnum.Weight.index();
    public static final int WEIGHTINTONS = AttributesEnum.WeightInTons.index();
    public static final int LOADTIME = AttributesEnum.LoadTime.index();
    public static final int BINGROSS = AttributesEnum.BinGross.index();
    public static final int BINTARE = AttributesEnum.BinTare.index();
    public static final int LOADEDNETT = AttributesEnum.LoadedNett.index();
    public static final int LINECHILD11 = AttributesEnum.LineChild11.index();
    public static final int SHIPMENTNUMBER = AttributesEnum.ShipmentNumber.index();
    public static final int FREIGHTCOST = AttributesEnum.FreightCost.index();
    public static final int COMMENTS = AttributesEnum.Comments.index();
    public static final int PROCESSEDFLAG = AttributesEnum.ProcessedFlag.index();
    public static final int TRANSFERREDFLAG = AttributesEnum.TransferredFlag.index();
    public static final int CARPREFIX = AttributesEnum.CarPrefix.index();
    public static final int DISPLAYCARID = AttributesEnum.Displaycarid.index();
    public static final int NEWCARPREFIX = AttributesEnum.NewCarPrefix.index();
    public static final int RAILROAD = AttributesEnum.RailRoad.index();
    public static final int ITEMNUMBER = AttributesEnum.ItemNumber.index();
    public static final int DESTINATION = AttributesEnum.Destination.index();
    public static final int LOADORIGIN = AttributesEnum.LoadOrigin.index();

    /**
     * This is the default constructor (do not remove).
     */
    public CEIXStsDeliveryLinesVORowImpl() {
    }

    /**
     * Gets CEIXStsDeliveryLinesEO entity object.
     * @return the CEIXStsDeliveryLinesEO
     */
    public EntityImpl getCEIXStsDeliveryLinesEO() {
        return (EntityImpl)getEntity(0);
    }

    /**
     * Gets the attribute value for DELIVERY_LINE_ID using the alias name DeliveryLineId.
     * @return the DELIVERY_LINE_ID
     */
    public Number getDeliveryLineId() {
        return (Number)getAttributeInternal(DELIVERYLINEID);
    }

    /**
     * Sets <code>value</code> as attribute value for DELIVERY_LINE_ID using the alias name DeliveryLineId.
     * @param value value to set the DELIVERY_LINE_ID
     */
    public void setDeliveryLineId(Number value) {
        setAttributeInternal(DELIVERYLINEID, value);
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
     * Gets the attribute value for DELIVERY_HEADER_ID using the alias name DeliveryHeaderId.
     * @return the DELIVERY_HEADER_ID
     */
    public Number getDeliveryHeaderId() {
        return (Number)getAttributeInternal(DELIVERYHEADERID);
    }

    /**
     * Sets <code>value</code> as attribute value for DELIVERY_HEADER_ID using the alias name DeliveryHeaderId.
     * @param value value to set the DELIVERY_HEADER_ID
     */
    public void setDeliveryHeaderId(Number value) {
        setAttributeInternal(DELIVERYHEADERID, value);
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
     * Gets the attribute value for UNIT_TRAIN_NUMBER using the alias name UnitTrainNumber.
     * @return the UNIT_TRAIN_NUMBER
     */
    public String getUnitTrainNumber() {
        return (String) getAttributeInternal(UNITTRAINNUMBER);
    }

    /**
     * Sets <code>value</code> as attribute value for UNIT_TRAIN_NUMBER using the alias name UnitTrainNumber.
     * @param value value to set the UNIT_TRAIN_NUMBER
     */
    public void setUnitTrainNumber(String value) {
        setAttributeInternal(UNITTRAINNUMBER, value);
    }

    /**
     * Gets the attribute value for NEW_TRAIN_NUMBER using the alias name NewTrainNumber.
     * @return the NEW_TRAIN_NUMBER
     */
    public String getNewTrainNumber() {
        return (String) getAttributeInternal(NEWTRAINNUMBER);
    }

    /**
     * Sets <code>value</code> as attribute value for NEW_TRAIN_NUMBER using the alias name NewTrainNumber.
     * @param value value to set the NEW_TRAIN_NUMBER
     */
    public void setNewTrainNumber(String value) {
        setAttributeInternal(NEWTRAINNUMBER, value);
    }

    /**
     * Gets the attribute value for SCHEDULE_DATE using the alias name ScheduleDate.
     * @return the SCHEDULE_DATE
     */
    public Date getScheduleDate() {
        return (Date) getAttributeInternal(SCHEDULEDATE);
    }

    /**
     * Sets <code>value</code> as attribute value for SCHEDULE_DATE using the alias name ScheduleDate.
     * @param value value to set the SCHEDULE_DATE
     */
    public void setScheduleDate(Date value) {
        setAttributeInternal(SCHEDULEDATE, value);
    }

    /**
     * Gets the attribute value for SEQUENCE_NUM using the alias name SequenceNum.
     * @return the SEQUENCE_NUM
     */
    public String getSequenceNum() {
        return (String) getAttributeInternal(SEQUENCENUM);
    }

    /**
     * Sets <code>value</code> as attribute value for SEQUENCE_NUM using the alias name SequenceNum.
     * @param value value to set the SEQUENCE_NUM
     */
    public void setSequenceNum(String value) {
        setAttributeInternal(SEQUENCENUM, value);
    }

    /**
     * Gets the attribute value for CAR_ID using the alias name CarId.
     * @return the CAR_ID
     */
    public String getCarId() {
        return (String) getAttributeInternal(CARID);
    }

    /**
     * Sets <code>value</code> as attribute value for CAR_ID using the alias name CarId.
     * @param value value to set the CAR_ID
     */
    public void setCarId(String value) {
        setAttributeInternal(CARID, value);
    }

    /**
     * Gets the attribute value for NEW_CAR_ID using the alias name NewCarId.
     * @return the NEW_CAR_ID
     */
    public String getNewCarId() {
        return (String) getAttributeInternal(NEWCARID);
    }

    /**
     * Sets <code>value</code> as attribute value for NEW_CAR_ID using the alias name NewCarId.
     * @param value value to set the NEW_CAR_ID
     */
    public void setNewCarId(String value) {
        setAttributeInternal(NEWCARID, value);
    }

    /**
     * Gets the attribute value for GROSS_ALLOW using the alias name GrossAllow.
     * @return the GROSS_ALLOW
     */
    public String getGrossAllow() {
        return (String) getAttributeInternal(GROSSALLOW);
    }

    /**
     * Sets <code>value</code> as attribute value for GROSS_ALLOW using the alias name GrossAllow.
     * @param value value to set the GROSS_ALLOW
     */
    public void setGrossAllow(String value) {
        setAttributeInternal(GROSSALLOW, value);
    }

    /**
     * Gets the attribute value for CAR_TARE using the alias name CarTare.
     * @return the CAR_TARE
     */
    public String getCarTare() {
        return (String) getAttributeInternal(CARTARE);
    }

    /**
     * Sets <code>value</code> as attribute value for CAR_TARE using the alias name CarTare.
     * @param value value to set the CAR_TARE
     */
    public void setCarTare(String value) {
        setAttributeInternal(CARTARE, value);
    }

    /**
     * Gets the attribute value for OWNERSHIP using the alias name Ownership.
     * @return the OWNERSHIP
     */
    public String getOwnership() {
        return (String) getAttributeInternal(OWNERSHIP);
    }

    /**
     * Sets <code>value</code> as attribute value for OWNERSHIP using the alias name Ownership.
     * @param value value to set the OWNERSHIP
     */
    public void setOwnership(String value) {
        setAttributeInternal(OWNERSHIP, value);
    }

    /**
     * Gets the attribute value for WEIGHT using the alias name Weight.
     * @return the WEIGHT
     */
    public String getWeight() {
        return (String) getAttributeInternal(WEIGHT);
    }

    /**
     * Sets <code>value</code> as attribute value for WEIGHT using the alias name Weight.
     * @param value value to set the WEIGHT
     */
    public void setWeight(String value) {
        setAttributeInternal(WEIGHT, value);
    }

    /**
     * Gets the attribute value for WEIGHT_IN_TONS using the alias name WeightInTons.
     * @return the WEIGHT_IN_TONS
     */
    public Number getWeightInTons() {
        return (Number)getAttributeInternal(WEIGHTINTONS);
    }

    /**
     * Sets <code>value</code> as attribute value for WEIGHT_IN_TONS using the alias name WeightInTons.
     * @param value value to set the WEIGHT_IN_TONS
     */
    public void setWeightInTons(Number value) {
        setAttributeInternal(WEIGHTINTONS, value);
    }

    /**
     * Gets the attribute value for LOAD_TIME using the alias name LoadTime.
     * @return the LOAD_TIME
     */
    public String getLoadTime() {
        return (String) getAttributeInternal(LOADTIME);
    }

    /**
     * Sets <code>value</code> as attribute value for LOAD_TIME using the alias name LoadTime.
     * @param value value to set the LOAD_TIME
     */
    public void setLoadTime(String value) {
        setAttributeInternal(LOADTIME, value);
    }

    /**
     * Gets the attribute value for BIN_GROSS using the alias name BinGross.
     * @return the BIN_GROSS
     */
    public String getBinGross() {
        return (String) getAttributeInternal(BINGROSS);
    }

    /**
     * Sets <code>value</code> as attribute value for BIN_GROSS using the alias name BinGross.
     * @param value value to set the BIN_GROSS
     */
    public void setBinGross(String value) {
        setAttributeInternal(BINGROSS, value);
    }

    /**
     * Gets the attribute value for BIN_TARE using the alias name BinTare.
     * @return the BIN_TARE
     */
    public String getBinTare() {
        return (String) getAttributeInternal(BINTARE);
    }

    /**
     * Sets <code>value</code> as attribute value for BIN_TARE using the alias name BinTare.
     * @param value value to set the BIN_TARE
     */
    public void setBinTare(String value) {
        setAttributeInternal(BINTARE, value);
    }

    /**
     * Gets the attribute value for LOADED_NETT using the alias name LoadedNett.
     * @return the LOADED_NETT
     */
    public String getLoadedNett() {
        return (String) getAttributeInternal(LOADEDNETT);
    }

    /**
     * Sets <code>value</code> as attribute value for LOADED_NETT using the alias name LoadedNett.
     * @param value value to set the LOADED_NETT
     */
    public void setLoadedNett(String value) {
        setAttributeInternal(LOADEDNETT, value);
    }

    /**
     * Gets the attribute value for LINE_CHILD_11 using the alias name LineChild11.
     * @return the LINE_CHILD_11
     */
    public String getLineChild11() {
        return (String) getAttributeInternal(LINECHILD11);
    }

    /**
     * Sets <code>value</code> as attribute value for LINE_CHILD_11 using the alias name LineChild11.
     * @param value value to set the LINE_CHILD_11
     */
    public void setLineChild11(String value) {
        setAttributeInternal(LINECHILD11, value);
    }

    /**
     * Gets the attribute value for SHIPMENT_NUMBER using the alias name ShipmentNumber.
     * @return the SHIPMENT_NUMBER
     */
    public String getShipmentNumber() {
        return (String) getAttributeInternal(SHIPMENTNUMBER);
    }

    /**
     * Sets <code>value</code> as attribute value for SHIPMENT_NUMBER using the alias name ShipmentNumber.
     * @param value value to set the SHIPMENT_NUMBER
     */
    public void setShipmentNumber(String value) {
        setAttributeInternal(SHIPMENTNUMBER, value);
    }

    /**
     * Gets the attribute value for FREIGHT_COST using the alias name FreightCost.
     * @return the FREIGHT_COST
     */
    public String getFreightCost() {
        return (String) getAttributeInternal(FREIGHTCOST);
    }

    /**
     * Sets <code>value</code> as attribute value for FREIGHT_COST using the alias name FreightCost.
     * @param value value to set the FREIGHT_COST
     */
    public void setFreightCost(String value) {
        setAttributeInternal(FREIGHTCOST, value);
    }

    /**
     * Gets the attribute value for COMMENTS using the alias name Comments.
     * @return the COMMENTS
     */
    public String getComments() {
        return (String) getAttributeInternal(COMMENTS);
    }

    /**
     * Sets <code>value</code> as attribute value for COMMENTS using the alias name Comments.
     * @param value value to set the COMMENTS
     */
    public void setComments(String value) {
        setAttributeInternal(COMMENTS, value);
    }

    /**
     * Gets the attribute value for PROCESSED_FLAG using the alias name ProcessedFlag.
     * @return the PROCESSED_FLAG
     */
    public String getProcessedFlag() {
        return (String) getAttributeInternal(PROCESSEDFLAG);
    }

    /**
     * Sets <code>value</code> as attribute value for PROCESSED_FLAG using the alias name ProcessedFlag.
     * @param value value to set the PROCESSED_FLAG
     */
    public void setProcessedFlag(String value) {
        setAttributeInternal(PROCESSEDFLAG, value);
    }

    /**
     * Gets the attribute value for TRANSFERRED_FLAG using the alias name TransferredFlag.
     * @return the TRANSFERRED_FLAG
     */
    public String getTransferredFlag() {
        return (String) getAttributeInternal(TRANSFERREDFLAG);
    }

    /**
     * Sets <code>value</code> as attribute value for TRANSFERRED_FLAG using the alias name TransferredFlag.
     * @param value value to set the TRANSFERRED_FLAG
     */
    public void setTransferredFlag(String value) {
        setAttributeInternal(TRANSFERREDFLAG, value);
    }

    /**
     * Gets the attribute value for CAR_PREFIX using the alias name CarPrefix.
     * @return the CAR_PREFIX
     */
    public String getCarPrefix() {
        return (String) getAttributeInternal(CARPREFIX);
    }

    /**
     * Sets <code>value</code> as attribute value for CAR_PREFIX using the alias name CarPrefix.
     * @param value value to set the CAR_PREFIX
     */
    public void setCarPrefix(String value) {
        setAttributeInternal(CARPREFIX, value);
    }

    /**
     * Gets the attribute value for DISPLAYCARID using the alias name Displaycarid.
     * @return the DISPLAYCARID
     */
    public String getDisplaycarid() {
        return (String) getAttributeInternal(DISPLAYCARID);
    }

    /**
     * Sets <code>value</code> as attribute value for DISPLAYCARID using the alias name Displaycarid.
     * @param value value to set the DISPLAYCARID
     */
    public void setDisplaycarid(String value) {
        setAttributeInternal(DISPLAYCARID, value);
    }

    /**
     * Gets the attribute value for NEW_CAR_PREFIX using the alias name NewCarPrefix.
     * @return the NEW_CAR_PREFIX
     */
    public String getNewCarPrefix() {
        return (String) getAttributeInternal(NEWCARPREFIX);
    }

    /**
     * Sets <code>value</code> as attribute value for NEW_CAR_PREFIX using the alias name NewCarPrefix.
     * @param value value to set the NEW_CAR_PREFIX
     */
    public void setNewCarPrefix(String value) {
        setAttributeInternal(NEWCARPREFIX, value);
    }

    /**
     * Gets the attribute value for RAIL_ROAD using the alias name RailRoad.
     * @return the RAIL_ROAD
     */
    public String getRailRoad() {
        return (String) getAttributeInternal(RAILROAD);
    }

    /**
     * Sets <code>value</code> as attribute value for RAIL_ROAD using the alias name RailRoad.
     * @param value value to set the RAIL_ROAD
     */
    public void setRailRoad(String value) {
        setAttributeInternal(RAILROAD, value);
    }

    /**
     * Gets the attribute value for ATTRIBUTE3 using the alias name ItemNumber.
     * @return the ATTRIBUTE3
     */
    public String getItemNumber() {
        return (String) getAttributeInternal(ITEMNUMBER);
    }

    /**
     * Sets <code>value</code> as attribute value for ATTRIBUTE3 using the alias name ItemNumber.
     * @param value value to set the ATTRIBUTE3
     */
    public void setItemNumber(String value) {
        setAttributeInternal(ITEMNUMBER, value);
    }

    /**
     * Gets the attribute value for DESTINATION using the alias name Destination.
     * @return the DESTINATION
     */
    public String getDestination() {
        return (String) getAttributeInternal(DESTINATION);
    }

    /**
     * Sets <code>value</code> as attribute value for DESTINATION using the alias name Destination.
     * @param value value to set the DESTINATION
     */
    public void setDestination(String value) {
        setAttributeInternal(DESTINATION, value);
    }

    /**
     * Gets the attribute value for LOAD_ORIGIN using the alias name LoadOrigin.
     * @return the LOAD_ORIGIN
     */
    public String getLoadOrigin() {
        return (String) getAttributeInternal(LOADORIGIN);
    }

    /**
     * Sets <code>value</code> as attribute value for LOAD_ORIGIN using the alias name LoadOrigin.
     * @param value value to set the LOAD_ORIGIN
     */
    public void setLoadOrigin(String value) {
        setAttributeInternal(LOADORIGIN, value);
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
