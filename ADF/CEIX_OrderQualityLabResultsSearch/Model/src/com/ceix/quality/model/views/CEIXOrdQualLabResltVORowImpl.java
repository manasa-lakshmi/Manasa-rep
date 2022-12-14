package com.ceix.quality.model.views;

import oracle.jbo.domain.Date;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Fri Mar 01 10:47:19 IST 2019
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class CEIXOrdQualLabResltVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        OrderNumber {
            public Object get(CEIXOrdQualLabResltVORowImpl obj) {
                return obj.getOrderNumber();
            }

            public void put(CEIXOrdQualLabResltVORowImpl obj, Object value) {
                obj.setOrderNumber((String)value);
            }
        }
        ,
        Train {
            public Object get(CEIXOrdQualLabResltVORowImpl obj) {
                return obj.getTrain();
            }

            public void put(CEIXOrdQualLabResltVORowImpl obj, Object value) {
                obj.setTrain((String)value);
            }
        }
        ,
        Yymm {
            public Object get(CEIXOrdQualLabResltVORowImpl obj) {
                return obj.getYymm();
            }

            public void put(CEIXOrdQualLabResltVORowImpl obj, Object value) {
                obj.setYymm((String)value);
            }
        }
        ,
        Cars {
            public Object get(CEIXOrdQualLabResltVORowImpl obj) {
                return obj.getCars();
            }

            public void put(CEIXOrdQualLabResltVORowImpl obj, Object value) {
                obj.setCars((Number)value);
            }
        }
        ,
        Datesamp {
            public Object get(CEIXOrdQualLabResltVORowImpl obj) {
                return obj.getDatesamp();
            }

            public void put(CEIXOrdQualLabResltVORowImpl obj, Object value) {
                obj.setDatesamp((Date)value);
            }
        }
        ,
        Rptsampno {
            public Object get(CEIXOrdQualLabResltVORowImpl obj) {
                return obj.getRptsampno();
            }

            public void put(CEIXOrdQualLabResltVORowImpl obj, Object value) {
                obj.setRptsampno((String)value);
            }
        }
        ,
        Sampmethod {
            public Object get(CEIXOrdQualLabResltVORowImpl obj) {
                return obj.getSampmethod();
            }

            public void put(CEIXOrdQualLabResltVORowImpl obj, Object value) {
                obj.setSampmethod((String)value);
            }
        }
        ,
        Fsi {
            public Object get(CEIXOrdQualLabResltVORowImpl obj) {
                return obj.getFsi();
            }

            public void put(CEIXOrdQualLabResltVORowImpl obj, Object value) {
                obj.setFsi((Number)value);
            }
        }
        ,
        Tm {
            public Object get(CEIXOrdQualLabResltVORowImpl obj) {
                return obj.getTm();
            }

            public void put(CEIXOrdQualLabResltVORowImpl obj, Object value) {
                obj.setTm((Number)value);
            }
        }
        ,
        AshAsr {
            public Object get(CEIXOrdQualLabResltVORowImpl obj) {
                return obj.getAshAsr();
            }

            public void put(CEIXOrdQualLabResltVORowImpl obj, Object value) {
                obj.setAshAsr((Number)value);
            }
        }
        ,
        AshDry {
            public Object get(CEIXOrdQualLabResltVORowImpl obj) {
                return obj.getAshDry();
            }

            public void put(CEIXOrdQualLabResltVORowImpl obj, Object value) {
                obj.setAshDry((Number)value);
            }
        }
        ,
        VolAsr {
            public Object get(CEIXOrdQualLabResltVORowImpl obj) {
                return obj.getVolAsr();
            }

            public void put(CEIXOrdQualLabResltVORowImpl obj, Object value) {
                obj.setVolAsr((Number)value);
            }
        }
        ,
        VolDry {
            public Object get(CEIXOrdQualLabResltVORowImpl obj) {
                return obj.getVolDry();
            }

            public void put(CEIXOrdQualLabResltVORowImpl obj, Object value) {
                obj.setVolDry((Number)value);
            }
        }
        ,
        VolDaf {
            public Object get(CEIXOrdQualLabResltVORowImpl obj) {
                return obj.getVolDaf();
            }

            public void put(CEIXOrdQualLabResltVORowImpl obj, Object value) {
                obj.setVolDaf((Number)value);
            }
        }
        ,
        SulAsr {
            public Object get(CEIXOrdQualLabResltVORowImpl obj) {
                return obj.getSulAsr();
            }

            public void put(CEIXOrdQualLabResltVORowImpl obj, Object value) {
                obj.setSulAsr((Number)value);
            }
        }
        ,
        SulDry {
            public Object get(CEIXOrdQualLabResltVORowImpl obj) {
                return obj.getSulDry();
            }

            public void put(CEIXOrdQualLabResltVORowImpl obj, Object value) {
                obj.setSulDry((Number)value);
            }
        }
        ,
        SulDaf {
            public Object get(CEIXOrdQualLabResltVORowImpl obj) {
                return obj.getSulDaf();
            }

            public void put(CEIXOrdQualLabResltVORowImpl obj, Object value) {
                obj.setSulDaf((Number)value);
            }
        }
        ,
        FcAsr {
            public Object get(CEIXOrdQualLabResltVORowImpl obj) {
                return obj.getFcAsr();
            }

            public void put(CEIXOrdQualLabResltVORowImpl obj, Object value) {
                obj.setFcAsr((Number)value);
            }
        }
        ,
        FcDry {
            public Object get(CEIXOrdQualLabResltVORowImpl obj) {
                return obj.getFcDry();
            }

            public void put(CEIXOrdQualLabResltVORowImpl obj, Object value) {
                obj.setFcDry((Number)value);
            }
        }
        ,
        FcDaf {
            public Object get(CEIXOrdQualLabResltVORowImpl obj) {
                return obj.getFcDaf();
            }

            public void put(CEIXOrdQualLabResltVORowImpl obj, Object value) {
                obj.setFcDaf((Number)value);
            }
        }
        ,
        BtuAsr {
            public Object get(CEIXOrdQualLabResltVORowImpl obj) {
                return obj.getBtuAsr();
            }

            public void put(CEIXOrdQualLabResltVORowImpl obj, Object value) {
                obj.setBtuAsr((Number)value);
            }
        }
        ,
        BtuDry {
            public Object get(CEIXOrdQualLabResltVORowImpl obj) {
                return obj.getBtuDry();
            }

            public void put(CEIXOrdQualLabResltVORowImpl obj, Object value) {
                obj.setBtuDry((Number)value);
            }
        }
        ,
        BtuDaf {
            public Object get(CEIXOrdQualLabResltVORowImpl obj) {
                return obj.getBtuDaf();
            }

            public void put(CEIXOrdQualLabResltVORowImpl obj, Object value) {
                obj.setBtuDaf((Number)value);
            }
        }
        ,
        CreationDate {
            public Object get(CEIXOrdQualLabResltVORowImpl obj) {
                return obj.getCreationDate();
            }

            public void put(CEIXOrdQualLabResltVORowImpl obj, Object value) {
                obj.setCreationDate((Timestamp)value);
            }
        }
        ,
        LastUpdateDate {
            public Object get(CEIXOrdQualLabResltVORowImpl obj) {
                return obj.getLastUpdateDate();
            }

            public void put(CEIXOrdQualLabResltVORowImpl obj, Object value) {
                obj.setLastUpdateDate((Timestamp)value);
            }
        }
        ,
        CreatedBy {
            public Object get(CEIXOrdQualLabResltVORowImpl obj) {
                return obj.getCreatedBy();
            }

            public void put(CEIXOrdQualLabResltVORowImpl obj, Object value) {
                obj.setCreatedBy((String)value);
            }
        }
        ,
        LastUpdatedBy {
            public Object get(CEIXOrdQualLabResltVORowImpl obj) {
                return obj.getLastUpdatedBy();
            }

            public void put(CEIXOrdQualLabResltVORowImpl obj, Object value) {
                obj.setLastUpdatedBy((String)value);
            }
        }
        ,
        AftfRed {
            public Object get(CEIXOrdQualLabResltVORowImpl obj) {
                return obj.getAftfRed();
            }

            public void put(CEIXOrdQualLabResltVORowImpl obj, Object value) {
                obj.setAftfRed((Number)value);
            }
        }
        ,
        AfthRed {
            public Object get(CEIXOrdQualLabResltVORowImpl obj) {
                return obj.getAfthRed();
            }

            public void put(CEIXOrdQualLabResltVORowImpl obj, Object value) {
                obj.setAfthRed((Number)value);
            }
        }
        ,
        AftiRed {
            public Object get(CEIXOrdQualLabResltVORowImpl obj) {
                return obj.getAftiRed();
            }

            public void put(CEIXOrdQualLabResltVORowImpl obj, Object value) {
                obj.setAftiRed((Number)value);
            }
        }
        ,
        AftsRed {
            public Object get(CEIXOrdQualLabResltVORowImpl obj) {
                return obj.getAftsRed();
            }

            public void put(CEIXOrdQualLabResltVORowImpl obj, Object value) {
                obj.setAftsRed((Number)value);
            }
        }
        ,
        Chlorine {
            public Object get(CEIXOrdQualLabResltVORowImpl obj) {
                return obj.getChlorine();
            }

            public void put(CEIXOrdQualLabResltVORowImpl obj, Object value) {
                obj.setChlorine((Number)value);
            }
        }
        ,
        Mercury {
            public Object get(CEIXOrdQualLabResltVORowImpl obj) {
                return obj.getMercury();
            }

            public void put(CEIXOrdQualLabResltVORowImpl obj, Object value) {
                obj.setMercury((Number)value);
            }
        }
        ,
        Lbss {
            public Object get(CEIXOrdQualLabResltVORowImpl obj) {
                return obj.getLbss();
            }

            public void put(CEIXOrdQualLabResltVORowImpl obj, Object value) {
                obj.setLbss((Number)value);
            }
        }
        ,
        Lbsso2 {
            public Object get(CEIXOrdQualLabResltVORowImpl obj) {
                return obj.getLbsso2();
            }

            public void put(CEIXOrdQualLabResltVORowImpl obj, Object value) {
                obj.setLbsso2((Number)value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        public abstract Object get(CEIXOrdQualLabResltVORowImpl object);

        public abstract void put(CEIXOrdQualLabResltVORowImpl object,
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
    public static final int ORDERNUMBER = AttributesEnum.OrderNumber.index();
    public static final int TRAIN = AttributesEnum.Train.index();
    public static final int YYMM = AttributesEnum.Yymm.index();
    public static final int CARS = AttributesEnum.Cars.index();
    public static final int DATESAMP = AttributesEnum.Datesamp.index();
    public static final int RPTSAMPNO = AttributesEnum.Rptsampno.index();
    public static final int SAMPMETHOD = AttributesEnum.Sampmethod.index();
    public static final int FSI = AttributesEnum.Fsi.index();
    public static final int TM = AttributesEnum.Tm.index();
    public static final int ASHASR = AttributesEnum.AshAsr.index();
    public static final int ASHDRY = AttributesEnum.AshDry.index();
    public static final int VOLASR = AttributesEnum.VolAsr.index();
    public static final int VOLDRY = AttributesEnum.VolDry.index();
    public static final int VOLDAF = AttributesEnum.VolDaf.index();
    public static final int SULASR = AttributesEnum.SulAsr.index();
    public static final int SULDRY = AttributesEnum.SulDry.index();
    public static final int SULDAF = AttributesEnum.SulDaf.index();
    public static final int FCASR = AttributesEnum.FcAsr.index();
    public static final int FCDRY = AttributesEnum.FcDry.index();
    public static final int FCDAF = AttributesEnum.FcDaf.index();
    public static final int BTUASR = AttributesEnum.BtuAsr.index();
    public static final int BTUDRY = AttributesEnum.BtuDry.index();
    public static final int BTUDAF = AttributesEnum.BtuDaf.index();
    public static final int CREATIONDATE = AttributesEnum.CreationDate.index();
    public static final int LASTUPDATEDATE = AttributesEnum.LastUpdateDate.index();
    public static final int CREATEDBY = AttributesEnum.CreatedBy.index();
    public static final int LASTUPDATEDBY = AttributesEnum.LastUpdatedBy.index();
    public static final int AFTFRED = AttributesEnum.AftfRed.index();
    public static final int AFTHRED = AttributesEnum.AfthRed.index();
    public static final int AFTIRED = AttributesEnum.AftiRed.index();
    public static final int AFTSRED = AttributesEnum.AftsRed.index();
    public static final int CHLORINE = AttributesEnum.Chlorine.index();
    public static final int MERCURY = AttributesEnum.Mercury.index();
    public static final int LBSS = AttributesEnum.Lbss.index();
    public static final int LBSSO2 = AttributesEnum.Lbsso2.index();

    /**
     * This is the default constructor (do not remove).
     */
    public CEIXOrdQualLabResltVORowImpl() {
    }

    /**
     * Gets CEIXOrdQualLabResltEO entity object.
     * @return the CEIXOrdQualLabResltEO
     */
    public EntityImpl getCEIXOrdQualLabResltEO() {
        return (EntityImpl)getEntity(0);
    }

    /**
     * Gets the attribute value for ORDER_NUMBER using the alias name OrderNumber.
     * @return the ORDER_NUMBER
     */
    public String getOrderNumber() {
        return (String) getAttributeInternal(ORDERNUMBER);
    }

    /**
     * Sets <code>value</code> as attribute value for ORDER_NUMBER using the alias name OrderNumber.
     * @param value value to set the ORDER_NUMBER
     */
    public void setOrderNumber(String value) {
        setAttributeInternal(ORDERNUMBER, value);
    }

    /**
     * Gets the attribute value for TRAIN using the alias name Train.
     * @return the TRAIN
     */
    public String getTrain() {
        return (String) getAttributeInternal(TRAIN);
    }

    /**
     * Sets <code>value</code> as attribute value for TRAIN using the alias name Train.
     * @param value value to set the TRAIN
     */
    public void setTrain(String value) {
        setAttributeInternal(TRAIN, value);
    }

    /**
     * Gets the attribute value for YYMM using the alias name Yymm.
     * @return the YYMM
     */
    public String getYymm() {
        return (String) getAttributeInternal(YYMM);
    }

    /**
     * Sets <code>value</code> as attribute value for YYMM using the alias name Yymm.
     * @param value value to set the YYMM
     */
    public void setYymm(String value) {
        setAttributeInternal(YYMM, value);
    }

    /**
     * Gets the attribute value for CARS using the alias name Cars.
     * @return the CARS
     */
    public Number getCars() {
        return (Number)getAttributeInternal(CARS);
    }

    /**
     * Sets <code>value</code> as attribute value for CARS using the alias name Cars.
     * @param value value to set the CARS
     */
    public void setCars(Number value) {
        setAttributeInternal(CARS, value);
    }

    /**
     * Gets the attribute value for DATESAMP using the alias name Datesamp.
     * @return the DATESAMP
     */
    public Date getDatesamp() {
        return (Date)getAttributeInternal(DATESAMP);
    }

    /**
     * Sets <code>value</code> as attribute value for DATESAMP using the alias name Datesamp.
     * @param value value to set the DATESAMP
     */
    public void setDatesamp(Date value) {
        setAttributeInternal(DATESAMP, value);
    }

    /**
     * Gets the attribute value for RPTSAMPNO using the alias name Rptsampno.
     * @return the RPTSAMPNO
     */
    public String getRptsampno() {
        return (String) getAttributeInternal(RPTSAMPNO);
    }

    /**
     * Sets <code>value</code> as attribute value for RPTSAMPNO using the alias name Rptsampno.
     * @param value value to set the RPTSAMPNO
     */
    public void setRptsampno(String value) {
        setAttributeInternal(RPTSAMPNO, value);
    }

    /**
     * Gets the attribute value for SAMPMETHOD using the alias name Sampmethod.
     * @return the SAMPMETHOD
     */
    public String getSampmethod() {
        return (String) getAttributeInternal(SAMPMETHOD);
    }

    /**
     * Sets <code>value</code> as attribute value for SAMPMETHOD using the alias name Sampmethod.
     * @param value value to set the SAMPMETHOD
     */
    public void setSampmethod(String value) {
        setAttributeInternal(SAMPMETHOD, value);
    }

    /**
     * Gets the attribute value for FSI using the alias name Fsi.
     * @return the FSI
     */
    public Number getFsi() {
        return (Number)getAttributeInternal(FSI);
    }

    /**
     * Sets <code>value</code> as attribute value for FSI using the alias name Fsi.
     * @param value value to set the FSI
     */
    public void setFsi(Number value) {
        setAttributeInternal(FSI, value);
    }

    /**
     * Gets the attribute value for TM using the alias name Tm.
     * @return the TM
     */
    public Number getTm() {
        return (Number)getAttributeInternal(TM);
    }

    /**
     * Sets <code>value</code> as attribute value for TM using the alias name Tm.
     * @param value value to set the TM
     */
    public void setTm(Number value) {
        setAttributeInternal(TM, value);
    }

    /**
     * Gets the attribute value for ASH_ASR using the alias name AshAsr.
     * @return the ASH_ASR
     */
    public Number getAshAsr() {
        return (Number)getAttributeInternal(ASHASR);
    }

    /**
     * Sets <code>value</code> as attribute value for ASH_ASR using the alias name AshAsr.
     * @param value value to set the ASH_ASR
     */
    public void setAshAsr(Number value) {
        setAttributeInternal(ASHASR, value);
    }

    /**
     * Gets the attribute value for ASH_DRY using the alias name AshDry.
     * @return the ASH_DRY
     */
    public Number getAshDry() {
        return (Number)getAttributeInternal(ASHDRY);
    }

    /**
     * Sets <code>value</code> as attribute value for ASH_DRY using the alias name AshDry.
     * @param value value to set the ASH_DRY
     */
    public void setAshDry(Number value) {
        setAttributeInternal(ASHDRY, value);
    }

    /**
     * Gets the attribute value for VOL_ASR using the alias name VolAsr.
     * @return the VOL_ASR
     */
    public Number getVolAsr() {
        return (Number)getAttributeInternal(VOLASR);
    }

    /**
     * Sets <code>value</code> as attribute value for VOL_ASR using the alias name VolAsr.
     * @param value value to set the VOL_ASR
     */
    public void setVolAsr(Number value) {
        setAttributeInternal(VOLASR, value);
    }

    /**
     * Gets the attribute value for VOL_DRY using the alias name VolDry.
     * @return the VOL_DRY
     */
    public Number getVolDry() {
        return (Number)getAttributeInternal(VOLDRY);
    }

    /**
     * Sets <code>value</code> as attribute value for VOL_DRY using the alias name VolDry.
     * @param value value to set the VOL_DRY
     */
    public void setVolDry(Number value) {
        setAttributeInternal(VOLDRY, value);
    }

    /**
     * Gets the attribute value for VOL_DAF using the alias name VolDaf.
     * @return the VOL_DAF
     */
    public Number getVolDaf() {
        return (Number)getAttributeInternal(VOLDAF);
    }

    /**
     * Sets <code>value</code> as attribute value for VOL_DAF using the alias name VolDaf.
     * @param value value to set the VOL_DAF
     */
    public void setVolDaf(Number value) {
        setAttributeInternal(VOLDAF, value);
    }

    /**
     * Gets the attribute value for SUL_ASR using the alias name SulAsr.
     * @return the SUL_ASR
     */
    public Number getSulAsr() {
        return (Number)getAttributeInternal(SULASR);
    }

    /**
     * Sets <code>value</code> as attribute value for SUL_ASR using the alias name SulAsr.
     * @param value value to set the SUL_ASR
     */
    public void setSulAsr(Number value) {
        setAttributeInternal(SULASR, value);
    }

    /**
     * Gets the attribute value for SUL_DRY using the alias name SulDry.
     * @return the SUL_DRY
     */
    public Number getSulDry() {
        return (Number)getAttributeInternal(SULDRY);
    }

    /**
     * Sets <code>value</code> as attribute value for SUL_DRY using the alias name SulDry.
     * @param value value to set the SUL_DRY
     */
    public void setSulDry(Number value) {
        setAttributeInternal(SULDRY, value);
    }

    /**
     * Gets the attribute value for SUL_DAF using the alias name SulDaf.
     * @return the SUL_DAF
     */
    public Number getSulDaf() {
        return (Number)getAttributeInternal(SULDAF);
    }

    /**
     * Sets <code>value</code> as attribute value for SUL_DAF using the alias name SulDaf.
     * @param value value to set the SUL_DAF
     */
    public void setSulDaf(Number value) {
        setAttributeInternal(SULDAF, value);
    }

    /**
     * Gets the attribute value for FC_ASR using the alias name FcAsr.
     * @return the FC_ASR
     */
    public Number getFcAsr() {
        return (Number)getAttributeInternal(FCASR);
    }

    /**
     * Sets <code>value</code> as attribute value for FC_ASR using the alias name FcAsr.
     * @param value value to set the FC_ASR
     */
    public void setFcAsr(Number value) {
        setAttributeInternal(FCASR, value);
    }

    /**
     * Gets the attribute value for FC_DRY using the alias name FcDry.
     * @return the FC_DRY
     */
    public Number getFcDry() {
        return (Number)getAttributeInternal(FCDRY);
    }

    /**
     * Sets <code>value</code> as attribute value for FC_DRY using the alias name FcDry.
     * @param value value to set the FC_DRY
     */
    public void setFcDry(Number value) {
        setAttributeInternal(FCDRY, value);
    }

    /**
     * Gets the attribute value for FC_DAF using the alias name FcDaf.
     * @return the FC_DAF
     */
    public Number getFcDaf() {
        return (Number)getAttributeInternal(FCDAF);
    }

    /**
     * Sets <code>value</code> as attribute value for FC_DAF using the alias name FcDaf.
     * @param value value to set the FC_DAF
     */
    public void setFcDaf(Number value) {
        setAttributeInternal(FCDAF, value);
    }

    /**
     * Gets the attribute value for BTU_ASR using the alias name BtuAsr.
     * @return the BTU_ASR
     */
    public Number getBtuAsr() {
        return (Number)getAttributeInternal(BTUASR);
    }

    /**
     * Sets <code>value</code> as attribute value for BTU_ASR using the alias name BtuAsr.
     * @param value value to set the BTU_ASR
     */
    public void setBtuAsr(Number value) {
        setAttributeInternal(BTUASR, value);
    }

    /**
     * Gets the attribute value for BTU_DRY using the alias name BtuDry.
     * @return the BTU_DRY
     */
    public Number getBtuDry() {
        return (Number)getAttributeInternal(BTUDRY);
    }

    /**
     * Sets <code>value</code> as attribute value for BTU_DRY using the alias name BtuDry.
     * @param value value to set the BTU_DRY
     */
    public void setBtuDry(Number value) {
        setAttributeInternal(BTUDRY, value);
    }

    /**
     * Gets the attribute value for BTU_DAF using the alias name BtuDaf.
     * @return the BTU_DAF
     */
    public Number getBtuDaf() {
        return (Number)getAttributeInternal(BTUDAF);
    }

    /**
     * Sets <code>value</code> as attribute value for BTU_DAF using the alias name BtuDaf.
     * @param value value to set the BTU_DAF
     */
    public void setBtuDaf(Number value) {
        setAttributeInternal(BTUDAF, value);
    }

    /**
     * Gets the attribute value for CREATION_DATE using the alias name CreationDate.
     * @return the CREATION_DATE
     */
    public Timestamp getCreationDate() {
        return (Timestamp)getAttributeInternal(CREATIONDATE);
    }

    /**
     * Sets <code>value</code> as attribute value for CREATION_DATE using the alias name CreationDate.
     * @param value value to set the CREATION_DATE
     */
    public void setCreationDate(Timestamp value) {
        setAttributeInternal(CREATIONDATE, value);
    }

    /**
     * Gets the attribute value for LAST_UPDATE_DATE using the alias name LastUpdateDate.
     * @return the LAST_UPDATE_DATE
     */
    public Timestamp getLastUpdateDate() {
        return (Timestamp)getAttributeInternal(LASTUPDATEDATE);
    }

    /**
     * Sets <code>value</code> as attribute value for LAST_UPDATE_DATE using the alias name LastUpdateDate.
     * @param value value to set the LAST_UPDATE_DATE
     */
    public void setLastUpdateDate(Timestamp value) {
        setAttributeInternal(LASTUPDATEDATE, value);
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
     * Gets the attribute value for AFTF_RED using the alias name AftfRed.
     * @return the AFTF_RED
     */
    public Number getAftfRed() {
        return (Number)getAttributeInternal(AFTFRED);
    }

    /**
     * Sets <code>value</code> as attribute value for AFTF_RED using the alias name AftfRed.
     * @param value value to set the AFTF_RED
     */
    public void setAftfRed(Number value) {
        setAttributeInternal(AFTFRED, value);
    }

    /**
     * Gets the attribute value for AFTH_RED using the alias name AfthRed.
     * @return the AFTH_RED
     */
    public Number getAfthRed() {
        return (Number)getAttributeInternal(AFTHRED);
    }

    /**
     * Sets <code>value</code> as attribute value for AFTH_RED using the alias name AfthRed.
     * @param value value to set the AFTH_RED
     */
    public void setAfthRed(Number value) {
        setAttributeInternal(AFTHRED, value);
    }

    /**
     * Gets the attribute value for AFTI_RED using the alias name AftiRed.
     * @return the AFTI_RED
     */
    public Number getAftiRed() {
        return (Number)getAttributeInternal(AFTIRED);
    }

    /**
     * Sets <code>value</code> as attribute value for AFTI_RED using the alias name AftiRed.
     * @param value value to set the AFTI_RED
     */
    public void setAftiRed(Number value) {
        setAttributeInternal(AFTIRED, value);
    }

    /**
     * Gets the attribute value for AFTS_RED using the alias name AftsRed.
     * @return the AFTS_RED
     */
    public Number getAftsRed() {
        return (Number)getAttributeInternal(AFTSRED);
    }

    /**
     * Sets <code>value</code> as attribute value for AFTS_RED using the alias name AftsRed.
     * @param value value to set the AFTS_RED
     */
    public void setAftsRed(Number value) {
        setAttributeInternal(AFTSRED, value);
    }

    /**
     * Gets the attribute value for CHLORINE using the alias name Chlorine.
     * @return the CHLORINE
     */
    public Number getChlorine() {
        return (Number)getAttributeInternal(CHLORINE);
    }

    /**
     * Sets <code>value</code> as attribute value for CHLORINE using the alias name Chlorine.
     * @param value value to set the CHLORINE
     */
    public void setChlorine(Number value) {
        setAttributeInternal(CHLORINE, value);
    }

    /**
     * Gets the attribute value for MERCURY using the alias name Mercury.
     * @return the MERCURY
     */
    public Number getMercury() {
        return (Number)getAttributeInternal(MERCURY);
    }

    /**
     * Sets <code>value</code> as attribute value for MERCURY using the alias name Mercury.
     * @param value value to set the MERCURY
     */
    public void setMercury(Number value) {
        setAttributeInternal(MERCURY, value);
    }

    /**
     * Gets the attribute value for LBSS using the alias name Lbss.
     * @return the LBSS
     */
    public Number getLbss() {
        return (Number)getAttributeInternal(LBSS);
    }

    /**
     * Sets <code>value</code> as attribute value for LBSS using the alias name Lbss.
     * @param value value to set the LBSS
     */
    public void setLbss(Number value) {
        setAttributeInternal(LBSS, value);
    }

    /**
     * Gets the attribute value for LBSSO2 using the alias name Lbsso2.
     * @return the LBSSO2
     */
    public Number getLbsso2() {
        return (Number)getAttributeInternal(LBSSO2);
    }

    /**
     * Sets <code>value</code> as attribute value for LBSSO2 using the alias name Lbsso2.
     * @param value value to set the LBSSO2
     */
    public void setLbsso2(Number value) {
        setAttributeInternal(LBSSO2, value);
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
