package com.ceix.stsrail.model.views;

import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Mon Aug 08 17:56:53 IST 2022
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class MineLocationLOVRowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    protected enum AttributesEnum {
        Name {
            protected Object get(MineLocationLOVRowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(MineLocationLOVRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        Value {
            protected Object get(MineLocationLOVRowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(MineLocationLOVRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        protected abstract Object get(MineLocationLOVRowImpl object);

        protected abstract void put(MineLocationLOVRowImpl object, Object value);

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
    public static final int NAME = AttributesEnum.Name.index();
    public static final int VALUE = AttributesEnum.Value.index();

    /**
     * This is the default constructor (do not remove).
     */
    public MineLocationLOVRowImpl() {
    }
}

