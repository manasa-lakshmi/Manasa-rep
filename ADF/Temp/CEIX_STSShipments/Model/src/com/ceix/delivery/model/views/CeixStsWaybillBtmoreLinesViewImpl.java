package com.ceix.delivery.model.views;

import oracle.jbo.domain.Number;
import oracle.jbo.server.ViewObjectImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Tue Aug 21 21:16:29 IST 2018
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class CeixStsWaybillBtmoreLinesViewImpl extends ViewObjectImpl {
    /**
     * This is the default constructor (do not remove).
     */
    public CeixStsWaybillBtmoreLinesViewImpl() {
    }

    /**
     * Returns the variable value for deliveryHeaderId.
     * @return variable value for deliveryHeaderId
     */
    public Number getdeliveryHeaderId() {
        return (Number)ensureVariableManager().getVariableValue("deliveryHeaderId");
    }

    /**
     * Sets <code>value</code> for variable deliveryHeaderId.
     * @param value value to bind as deliveryHeaderId
     */
    public void setdeliveryHeaderId(Number value) {
        ensureVariableManager().setVariableValue("deliveryHeaderId", value);
    }

    /**
     * Returns the variable value for carNumber.
     * @return variable value for carNumber
     */
    public String getcarNumber() {
        return (String)ensureVariableManager().getVariableValue("carNumber");
    }

    /**
     * Sets <code>value</code> for variable carNumber.
     * @param value value to bind as carNumber
     */
    public void setcarNumber(String value) {
        ensureVariableManager().setVariableValue("carNumber", value);
    }
}