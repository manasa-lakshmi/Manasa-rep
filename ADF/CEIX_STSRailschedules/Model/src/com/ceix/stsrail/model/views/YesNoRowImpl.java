package com.ceix.stsrail.model.views;

import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Fri Sep 16 21:53:15 IST 2022
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class YesNoRowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    protected enum AttributesEnum {
        YesNo {
            protected Object get(YesNoRowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(YesNoRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        Value {
            protected Object get(YesNoRowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(YesNoRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        protected abstract Object get(YesNoRowImpl object);

        protected abstract void put(YesNoRowImpl object, Object value);

        protected int index() {
            return AttributesEnum.firstIndex() + ordinal();
        }

        protected static final int firstIndex() {
            return firstIndex;
        }

        protected static int count() {
            return AttributesEnum.firstIndex() + AttributesEnum.staticValues().length;
        }

        protected static final AttributesEnum[] staticValues() {
            if (vals == null) {
                vals = AttributesEnum.values();
            }
            return vals;
        }
    }
    public static final int YESNO = AttributesEnum.YesNo.index();
    public static final int VALUE = AttributesEnum.Value.index();

    /**
     * This is the default constructor (do not remove).
     */
    public YesNoRowImpl() {
    }
}

