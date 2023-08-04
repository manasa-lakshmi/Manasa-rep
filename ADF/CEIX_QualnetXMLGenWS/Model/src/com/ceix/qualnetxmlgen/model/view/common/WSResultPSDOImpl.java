package com.ceix.qualnetxmlgen.model.view.common;

import org.eclipse.persistence.sdo.SDODataObject;

public class WSResultPSDOImpl extends SDODataObject implements WSResultPSDO {

   public static final int START_PROPERTY_INDEX = 0;

   public static final int END_PROPERTY_INDEX = START_PROPERTY_INDEX + 1;

   public WSResultPSDOImpl() {}

   public java.lang.String getStatus() {
      return getString(START_PROPERTY_INDEX + 0);
   }

   public void setStatus(java.lang.String value) {
      set(START_PROPERTY_INDEX + 0 , value);
   }

   public java.lang.String getMessage() {
      return getString(START_PROPERTY_INDEX + 1);
   }

   public void setMessage(java.lang.String value) {
      set(START_PROPERTY_INDEX + 1 , value);
   }


}

