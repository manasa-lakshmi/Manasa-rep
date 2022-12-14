package com.ceix.waybill.model.views;

import oracle.jbo.domain.Date;
import oracle.jbo.server.ViewObjectImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Tue Oct 15 17:55:31 IST 2019
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class CeixStsWaybillSearchVOImpl extends ViewObjectImpl {
    /**
     * This is the default constructor (do not remove).
     */
    public CeixStsWaybillSearchVOImpl() {
    }

    /**
     * Returns the bind variable value for pOrderNum.
     * @return bind variable value for pOrderNum
     */
    public String getpOrderNum() {
        return (String)getNamedWhereClauseParam("pOrderNum");
    }

    /**
     * Sets <code>value</code> for bind variable pOrderNum.
     * @param value value to bind as pOrderNum
     */
    public void setpOrderNum(String value) {
        setNamedWhereClauseParam("pOrderNum", value);
    }

    /**
     * Returns the bind variable value for pTrainNum.
     * @return bind variable value for pTrainNum
     */
    public String getpTrainNum() {
        return (String)getNamedWhereClauseParam("pTrainNum");
    }

    /**
     * Sets <code>value</code> for bind variable pTrainNum.
     * @param value value to bind as pTrainNum
     */
    public void setpTrainNum(String value) {
        setNamedWhereClauseParam("pTrainNum", value);
    }

    /**
     * Returns the bind variable value for pShipNum.
     * @return bind variable value for pShipNum
     */
    public String getpShipNum() {
        return (String)getNamedWhereClauseParam("pShipNum");
    }

    /**
     * Sets <code>value</code> for bind variable pShipNum.
     * @param value value to bind as pShipNum
     */
    public void setpShipNum(String value) {
        setNamedWhereClauseParam("pShipNum", value);
    }

    /**
     * Returns the bind variable value for pShipFlag.
     * @return bind variable value for pShipFlag
     */
    public String getpShipFlag() {
        return (String)getNamedWhereClauseParam("pShipFlag");
    }

    /**
     * Sets <code>value</code> for bind variable pShipFlag.
     * @param value value to bind as pShipFlag
     */
    public void setpShipFlag(String value) {
        setNamedWhereClauseParam("pShipFlag", value);
    }

    /**
     * Returns the bind variable value for pLoadFromDate.
     * @return bind variable value for pLoadFromDate
     */
    public Date getpLoadFromDate() {
        return (Date)getNamedWhereClauseParam("pLoadFromDate");
    }

    /**
     * Sets <code>value</code> for bind variable pLoadFromDate.
     * @param value value to bind as pLoadFromDate
     */
    public void setpLoadFromDate(Date value) {
        setNamedWhereClauseParam("pLoadFromDate", value);
    }

    /**
     * Returns the bind variable value for pLoadToDate.
     * @return bind variable value for pLoadToDate
     */
    public Date getpLoadToDate() {
        return (Date)getNamedWhereClauseParam("pLoadToDate");
    }

    /**
     * Sets <code>value</code> for bind variable pLoadToDate.
     * @param value value to bind as pLoadToDate
     */
    public void setpLoadToDate(Date value) {
        setNamedWhereClauseParam("pLoadToDate", value);
    }
}
