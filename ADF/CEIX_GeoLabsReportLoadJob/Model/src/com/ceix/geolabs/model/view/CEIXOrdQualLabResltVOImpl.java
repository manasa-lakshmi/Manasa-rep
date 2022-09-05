package com.ceix.geolabs.model.view;

import java.sql.ResultSet;

import oracle.jbo.Row;
import oracle.jbo.domain.Date;
import oracle.jbo.server.ViewObjectImpl;
import oracle.jbo.server.ViewRowImpl;
import oracle.jbo.server.ViewRowSetImpl;

// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Mon Apr 30 15:58:13 IST 2018
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class CEIXOrdQualLabResltVOImpl extends ViewObjectImpl {
    /**
     * This is the default constructor (do not remove).
     */
    public CEIXOrdQualLabResltVOImpl() {
    }

    /**
     * executeQueryForCollection - overridden for custom java data source support.
     */
    protected void executeQueryForCollection(Object qc, Object[] params,
                                             int noUserParams) {
        super.executeQueryForCollection(qc, params, noUserParams);
    }

    /**
     * hasNextForCollection - overridden for custom java data source support.
     */
    protected boolean hasNextForCollection(Object qc) {
        boolean bRet = super.hasNextForCollection(qc);
        return bRet;
    }

    /**
     * createRowFromResultSet - overridden for custom java data source support.
     */
    protected ViewRowImpl createRowFromResultSet(Object qc,
                                                 ResultSet resultSet) {
        ViewRowImpl value = super.createRowFromResultSet(qc, resultSet);
        return value;
    }

    /**
     * getQueryHitCount - overridden for custom java data source support.
     */
    public long getQueryHitCount(ViewRowSetImpl viewRowSet) {
        long value = super.getQueryHitCount(viewRowSet);
        return value;
    }

    /**
     * Returns the variable value for trainNo.
     * @return variable value for trainNo
     */
    public String gettrainNo() {
        return (String)ensureVariableManager().getVariableValue("trainNo");
    }

    /**
     * Sets <code>value</code> for variable trainNo.
     * @param value value to bind as trainNo
     */
    public void settrainNo(String value) {
        ensureVariableManager().setVariableValue("trainNo", value);
    }

    /**
     * Returns the variable value for rptSampleDt.
     * @return variable value for rptSampleDt
     */
    public Date getrptSampleDt() {
        return (Date)ensureVariableManager().getVariableValue("rptSampleDt");
    }

    /**
     * Sets <code>value</code> for variable rptSampleDt.
     * @param value value to bind as rptSampleDt
     */
    public void setrptSampleDt(Date value) {
        ensureVariableManager().setVariableValue("rptSampleDt", value);
    }

    /**
     * Returns the variable value for labNumber.
     * @return variable value for labNumber
     */
    public String getlabNumber() {
        return (String)ensureVariableManager().getVariableValue("labNumber");
    }

    /**
     * Sets <code>value</code> for variable labNumber.
     * @param value value to bind as labNumber
     */
    public void setlabNumber(String value) {
        ensureVariableManager().setVariableValue("labNumber", value);
    }

    /**
     * getCappedQueryHitCount - for custom java data source support.
     */
    @Override
    public long getCappedQueryHitCount(ViewRowSetImpl viewRowSet, Row[] masterRows, long oldCap, long cap) {
        long value = super.getCappedQueryHitCount(viewRowSet, masterRows, oldCap, cap);
        return value;
    }
}