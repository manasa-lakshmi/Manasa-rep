package com.ceix.geolabs.model.view.common;

import org.eclipse.persistence.sdo.SDODataObject;

@SuppressWarnings("serial")
public class CEIXOrdQualLabResltSDOImpl extends SDODataObject implements CEIXOrdQualLabResltSDO {

   public static final int START_PROPERTY_INDEX = 0;

   public static final int END_PROPERTY_INDEX = START_PROPERTY_INDEX + 44;

   public CEIXOrdQualLabResltSDOImpl() {}

   public java.lang.String getOrderNumber() {
      return getString(START_PROPERTY_INDEX + 0);
   }

   public void setOrderNumber(java.lang.String value) {
      set(START_PROPERTY_INDEX + 0 , value);
   }

   public java.lang.String getTrain() {
      return getString(START_PROPERTY_INDEX + 1);
   }

   public void setTrain(java.lang.String value) {
      set(START_PROPERTY_INDEX + 1 , value);
   }

   public java.lang.String getYymm() {
      return getString(START_PROPERTY_INDEX + 2);
   }

   public void setYymm(java.lang.String value) {
      set(START_PROPERTY_INDEX + 2 , value);
   }

   public java.lang.Integer getCars() {
      return getInt(START_PROPERTY_INDEX + 3);
   }

   public void setCars(java.lang.Integer value) {
      set(START_PROPERTY_INDEX + 3 , value);
   }

   public java.sql.Timestamp getDatesamp() {
      return (java.sql.Timestamp)get(START_PROPERTY_INDEX + 4);
   }

   public void setDatesamp(java.sql.Timestamp value) {
      set(START_PROPERTY_INDEX + 4 , value);
   }

   public java.lang.String getRptsampno() {
      return getString(START_PROPERTY_INDEX + 5);
   }

   public void setRptsampno(java.lang.String value) {
      set(START_PROPERTY_INDEX + 5 , value);
   }

   public java.lang.String getSampmethod() {
      return getString(START_PROPERTY_INDEX + 6);
   }

   public void setSampmethod(java.lang.String value) {
      set(START_PROPERTY_INDEX + 6 , value);
   }

   public java.math.BigDecimal getFsi() {
      return getBigDecimal(START_PROPERTY_INDEX + 7);
   }

   public void setFsi(java.math.BigDecimal value) {
      set(START_PROPERTY_INDEX + 7 , value);
   }

   public java.math.BigDecimal getTm() {
      return getBigDecimal(START_PROPERTY_INDEX + 8);
   }

   public void setTm(java.math.BigDecimal value) {
      set(START_PROPERTY_INDEX + 8 , value);
   }

   public java.math.BigDecimal getAshAsr() {
      return getBigDecimal(START_PROPERTY_INDEX + 9);
   }

   public void setAshAsr(java.math.BigDecimal value) {
      set(START_PROPERTY_INDEX + 9 , value);
   }

   public java.math.BigDecimal getAshDry() {
      return getBigDecimal(START_PROPERTY_INDEX + 10);
   }

   public void setAshDry(java.math.BigDecimal value) {
      set(START_PROPERTY_INDEX + 10 , value);
   }

   public java.math.BigDecimal getVolAsr() {
      return getBigDecimal(START_PROPERTY_INDEX + 11);
   }

   public void setVolAsr(java.math.BigDecimal value) {
      set(START_PROPERTY_INDEX + 11 , value);
   }

   public java.math.BigDecimal getVolDry() {
      return getBigDecimal(START_PROPERTY_INDEX + 12);
   }

   public void setVolDry(java.math.BigDecimal value) {
      set(START_PROPERTY_INDEX + 12 , value);
   }

   public java.math.BigDecimal getVolDaf() {
      return getBigDecimal(START_PROPERTY_INDEX + 13);
   }

   public void setVolDaf(java.math.BigDecimal value) {
      set(START_PROPERTY_INDEX + 13 , value);
   }

   public java.math.BigDecimal getSulAsr() {
      return getBigDecimal(START_PROPERTY_INDEX + 14);
   }

   public void setSulAsr(java.math.BigDecimal value) {
      set(START_PROPERTY_INDEX + 14 , value);
   }

   public java.math.BigDecimal getSulDry() {
      return getBigDecimal(START_PROPERTY_INDEX + 15);
   }

   public void setSulDry(java.math.BigDecimal value) {
      set(START_PROPERTY_INDEX + 15 , value);
   }

   public java.math.BigDecimal getSulDaf() {
      return getBigDecimal(START_PROPERTY_INDEX + 16);
   }

   public void setSulDaf(java.math.BigDecimal value) {
      set(START_PROPERTY_INDEX + 16 , value);
   }

   public java.math.BigDecimal getFcAsr() {
      return getBigDecimal(START_PROPERTY_INDEX + 17);
   }

   public void setFcAsr(java.math.BigDecimal value) {
      set(START_PROPERTY_INDEX + 17 , value);
   }

   public java.math.BigDecimal getFcDry() {
      return getBigDecimal(START_PROPERTY_INDEX + 18);
   }

   public void setFcDry(java.math.BigDecimal value) {
      set(START_PROPERTY_INDEX + 18 , value);
   }

   public java.math.BigDecimal getFcDaf() {
      return getBigDecimal(START_PROPERTY_INDEX + 19);
   }

   public void setFcDaf(java.math.BigDecimal value) {
      set(START_PROPERTY_INDEX + 19 , value);
   }

   public java.lang.Integer getBtuAsr() {
      return getInt(START_PROPERTY_INDEX + 20);
   }

   public void setBtuAsr(java.lang.Integer value) {
      set(START_PROPERTY_INDEX + 20 , value);
   }

   public java.lang.Integer getBtuDry() {
      return getInt(START_PROPERTY_INDEX + 21);
   }

   public void setBtuDry(java.lang.Integer value) {
      set(START_PROPERTY_INDEX + 21 , value);
   }

   public java.lang.Integer getBtuDaf() {
      return getInt(START_PROPERTY_INDEX + 22);
   }

   public void setBtuDaf(java.lang.Integer value) {
      set(START_PROPERTY_INDEX + 22 , value);
   }

   public java.sql.Timestamp getCreationDate() {
      return (java.sql.Timestamp)get(START_PROPERTY_INDEX + 23);
   }

   public void setCreationDate(java.sql.Timestamp value) {
      set(START_PROPERTY_INDEX + 23 , value);
   }

   public java.sql.Timestamp getLastUpdateDate() {
      return (java.sql.Timestamp)get(START_PROPERTY_INDEX + 24);
   }

   public void setLastUpdateDate(java.sql.Timestamp value) {
      set(START_PROPERTY_INDEX + 24 , value);
   }

   public java.lang.String getCreatedBy() {
      return getString(START_PROPERTY_INDEX + 25);
   }

   public void setCreatedBy(java.lang.String value) {
      set(START_PROPERTY_INDEX + 25 , value);
   }

   public java.lang.String getLastUpdatedBy() {
      return getString(START_PROPERTY_INDEX + 26);
   }

   public void setLastUpdatedBy(java.lang.String value) {
      set(START_PROPERTY_INDEX + 26 , value);
   }

   public java.lang.Integer getAftiRed() {
      return getInt(START_PROPERTY_INDEX + 27);
   }

   public void setAftiRed(java.lang.Integer value) {
      set(START_PROPERTY_INDEX + 27 , value);
   }

   public java.lang.Integer getAftsRed() {
      return getInt(START_PROPERTY_INDEX + 28);
   }

   public void setAftsRed(java.lang.Integer value) {
      set(START_PROPERTY_INDEX + 28 , value);
   }

   public java.lang.Integer getAfthRed() {
      return getInt(START_PROPERTY_INDEX + 29);
   }

   public void setAfthRed(java.lang.Integer value) {
      set(START_PROPERTY_INDEX + 29 , value);
   }

   public java.lang.Integer getAftfRed() {
      return getInt(START_PROPERTY_INDEX + 30);
   }

   public void setAftfRed(java.lang.Integer value) {
      set(START_PROPERTY_INDEX + 30 , value);
   }

   public java.lang.Integer getChlorine() {
      return getInt(START_PROPERTY_INDEX + 31);
   }

   public void setChlorine(java.lang.Integer value) {
      set(START_PROPERTY_INDEX + 31 , value);
   }

   public java.math.BigDecimal getMercury() {
      return getBigDecimal(START_PROPERTY_INDEX + 32);
   }

   public void setMercury(java.math.BigDecimal value) {
      set(START_PROPERTY_INDEX + 32 , value);
   }

   public java.math.BigDecimal getLbss() {
      return getBigDecimal(START_PROPERTY_INDEX + 33);
   }

   public void setLbss(java.math.BigDecimal value) {
      set(START_PROPERTY_INDEX + 33 , value);
   }

   public java.math.BigDecimal getLbsso2() {
      return getBigDecimal(START_PROPERTY_INDEX + 34);
   }

   public void setLbsso2(java.math.BigDecimal value) {
      set(START_PROPERTY_INDEX + 34 , value);
   }

   public java.lang.String getSampleWt() {
      return getString(START_PROPERTY_INDEX + 35);
   }

   public void setSampleWt(java.lang.String value) {
      set(START_PROPERTY_INDEX + 35 , value);
   }

   public java.lang.String getTonnage() {
      return getString(START_PROPERTY_INDEX + 36);
   }

   public void setTonnage(java.lang.String value) {
      set(START_PROPERTY_INDEX + 36 , value);
   }

   public java.lang.String getTruck() {
      return getString(START_PROPERTY_INDEX + 37);
   }

   public void setTruck(java.lang.String value) {
      set(START_PROPERTY_INDEX + 37 , value);
   }

   public java.math.BigDecimal getRommoisture() {
      return getBigDecimal(START_PROPERTY_INDEX + 38);
   }

   public void setRommoisture(java.math.BigDecimal value) {
      set(START_PROPERTY_INDEX + 38 , value);
   }

   public java.math.BigDecimal getRawash() {
      return getBigDecimal(START_PROPERTY_INDEX + 39);
   }

   public void setRawash(java.math.BigDecimal value) {
      set(START_PROPERTY_INDEX + 39 , value);
   }

   public java.math.BigDecimal getPerrecovery() {
      return getBigDecimal(START_PROPERTY_INDEX + 40);
   }

   public void setPerrecovery(java.math.BigDecimal value) {
      set(START_PROPERTY_INDEX + 40 , value);
   }

   public java.lang.String getMinelocation() {
      return getString(START_PROPERTY_INDEX + 41);
   }

   public void setMinelocation(java.lang.String value) {
      set(START_PROPERTY_INDEX + 41 , value);
   }

   public java.math.BigDecimal getCleanTons() {
      return getBigDecimal(START_PROPERTY_INDEX + 42);
   }

   public void setCleanTons(java.math.BigDecimal value) {
      set(START_PROPERTY_INDEX + 42 , value);
   }

   public java.math.BigDecimal getOxidation() {
      return getBigDecimal(START_PROPERTY_INDEX + 43);
   }

   public void setOxidation(java.math.BigDecimal value) {
      set(START_PROPERTY_INDEX + 43 , value);
   }

   public java.math.BigDecimal getRawTons() {
      return getBigDecimal(START_PROPERTY_INDEX + 44);
   }

   public void setRawTons(java.math.BigDecimal value) {
      set(START_PROPERTY_INDEX + 44 , value);
   }


}

