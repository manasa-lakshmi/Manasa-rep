package com.ceix.qualnetxmlgen.model.services.client;

import com.ceix.qualnetxmlgen.model.services.common.CEIXQualnetXMLGenAM;

import java.util.List;

import oracle.jbo.client.remote.ApplicationModuleImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Tue May 15 16:35:42 IST 2018
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class CEIXQualnetXMLGenAMClient extends ApplicationModuleImpl implements CEIXQualnetXMLGenAM {
    /**
     * This is the default constructor (do not remove).
     */
    public CEIXQualnetXMLGenAMClient() {
    }

    public List<ViewRowImpl> generateQualnetReports() {
        Object _ret =
            this.riInvokeExportedMethod(this,"generateQualnetReports",null,null);
        return (List<ViewRowImpl>)_ret;
    }
}