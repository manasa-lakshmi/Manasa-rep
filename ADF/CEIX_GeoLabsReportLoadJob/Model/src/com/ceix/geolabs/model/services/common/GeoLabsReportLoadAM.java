package com.ceix.geolabs.model.services.common;


import java.util.List;

import oracle.jbo.ApplicationModule;
import oracle.jbo.server.ViewRowImpl;

// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Wed May 02 17:27:40 IST 2018
// ---------------------------------------------------------------------
public interface GeoLabsReportLoadAM extends ApplicationModule {


    List<ViewRowImpl> execute();

    String executeManual();
}