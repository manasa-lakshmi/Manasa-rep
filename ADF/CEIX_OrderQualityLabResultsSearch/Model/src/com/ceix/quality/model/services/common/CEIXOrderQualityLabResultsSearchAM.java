package com.ceix.quality.model.services.common;

import oracle.jbo.ApplicationModule;
import oracle.jbo.Row;
import oracle.jbo.domain.Date;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Wed Apr 18 14:53:18 IST 2018
// ---------------------------------------------------------------------
public interface CEIXOrderQualityLabResultsSearchAM extends ApplicationModule {
    void getContractQualityMetricsByOrderNumber(String orderNum);

    Row creategetCEIXOrdQualLabReslt1Row();

    void cancelCurrentRowCEIXOrdQualLabReslt1Row();

    boolean orderQualityLabResultsExists(String trainNumber, Date sampleDate,
                                         String rptSampleNo);
}
