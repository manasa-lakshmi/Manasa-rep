package com.ceix.geolabs.model.view;

import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Fri Apr 27 13:59:22 IST 2018
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class WSResultPVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        Status {
            public Object get(WSResultPVORowImpl obj) {
                return obj.getStatus();
            }

            public void put(WSResultPVORowImpl obj, Object value) {
                obj.setStatus((String)value);
            }
        }
        ,
        Message {
            public Object get(WSResultPVORowImpl obj) {
                return obj.getMessage();
            }

            public void put(WSResultPVORowImpl obj, Object value) {
                obj.setMessage((String)value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        public abstract Object get(WSResultPVORowImpl object);

        public abstract void put(WSResultPVORowImpl object, Object value);

        public int index() {
            return WSResultPVORowImpl.AttributesEnum.firstIndex() + ordinal();
        }

        public static final int firstIndex() {
            return firstIndex;
        }

        public static int count() {
            return WSResultPVORowImpl.AttributesEnum.firstIndex() + WSResultPVORowImpl.AttributesEnum.staticValues().length;
        }

        public static final AttributesEnum[] staticValues() {
            if (vals == null) {
                vals = WSResultPVORowImpl.AttributesEnum.values();
            }
            return vals;
        }
    }


    public static final int STATUS = AttributesEnum.Status.index();
    public static final int MESSAGE = AttributesEnum.Message.index();

    /**
     * This is the default constructor (do not remove).
     */
    public WSResultPVORowImpl() {
    }

    /**
     * Gets the attribute value for the calculated attribute Status.
     * @return the Status
     */
    public String getStatus() {
        return (String) getAttributeInternal(STATUS);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute Status.
     * @param value value to set the  Status
     */
    public void setStatus(String value) {
        setAttributeInternal(STATUS, value);
    }

    /**
     * Gets the attribute value for the calculated attribute Message.
     * @return the Message
     */
    public String getMessage() {
        return (String) getAttributeInternal(MESSAGE);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute Message.
     * @param value value to set the  Message
     */
    public void setMessage(String value) {
        setAttributeInternal(MESSAGE, value);
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
