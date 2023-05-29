package com.ceix.delivery.model.views;

import oracle.jbo.domain.Number;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Mon Jul 29 15:46:00 IST 2019
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class CeixStsDeliveryHeaderDeleteTrainVoRowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        DeliveryHeaderId {
            public Object get(CeixStsDeliveryHeaderDeleteTrainVoRowImpl obj) {
                return obj.getDeliveryHeaderId();
            }

            public void put(CeixStsDeliveryHeaderDeleteTrainVoRowImpl obj,
                            Object value) {
                obj.setDeliveryHeaderId((Number)value);
            }
        }
        ,
        UnitTrainNumber {
            public Object get(CeixStsDeliveryHeaderDeleteTrainVoRowImpl obj) {
                return obj.getUnitTrainNumber();
            }

            public void put(CeixStsDeliveryHeaderDeleteTrainVoRowImpl obj,
                            Object value) {
                obj.setUnitTrainNumber((String)value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        public abstract Object get(CeixStsDeliveryHeaderDeleteTrainVoRowImpl object);

        public abstract void put(CeixStsDeliveryHeaderDeleteTrainVoRowImpl object,
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


    public static final int DELIVERYHEADERID = AttributesEnum.DeliveryHeaderId.index();
    public static final int UNITTRAINNUMBER = AttributesEnum.UnitTrainNumber.index();

    /**
     * This is the default constructor (do not remove).
     */
    public CeixStsDeliveryHeaderDeleteTrainVoRowImpl() {
    }

    /**
     * Gets the attribute value for the calculated attribute DeliveryHeaderId.
     * @return the DeliveryHeaderId
     */
    public Number getDeliveryHeaderId() {
        return (Number) getAttributeInternal(DELIVERYHEADERID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute DeliveryHeaderId.
     * @param value value to set the  DeliveryHeaderId
     */
    public void setDeliveryHeaderId(Number value) {
        setAttributeInternal(DELIVERYHEADERID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute UnitTrainNumber.
     * @return the UnitTrainNumber
     */
    public String getUnitTrainNumber() {
        return (String) getAttributeInternal(UNITTRAINNUMBER);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute UnitTrainNumber.
     * @param value value to set the  UnitTrainNumber
     */
    public void setUnitTrainNumber(String value) {
        setAttributeInternal(UNITTRAINNUMBER, value);
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
