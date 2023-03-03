package com.ceix.ehs.model.views;

import oracle.jbo.server.ViewObjectImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Thu Dec 20 21:24:59 IST 2018
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class CeixEhsClinicalLogVOImpl extends ViewObjectImpl {
    /**
     * This is the default constructor (do not remove).
     */
    public CeixEhsClinicalLogVOImpl() {
    }

    /**
     * Returns the variable value for incidentHdrId.
     * @return variable value for incidentHdrId
     */
    public String getincidentHdrId() {
        return (String)ensureVariableManager().getVariableValue("incidentHdrId");
    }

    /**
     * Sets <code>value</code> for variable incidentHdrId.
     * @param value value to bind as incidentHdrId
     */
    public void setincidentHdrId(String value) {
        ensureVariableManager().setVariableValue("incidentHdrId", value);
    }

    /**
     * Returns the variable value for clinicalId.
     * @return variable value for clinicalId
     */
    public String getclinicalId() {
        return (String)ensureVariableManager().getVariableValue("clinicalId");
    }

    /**
     * Sets <code>value</code> for variable clinicalId.
     * @param value value to bind as clinicalId
     */
    public void setclinicalId(String value) {
        ensureVariableManager().setVariableValue("clinicalId", value);
    }
}