package com.ceix.ehs.beans;

import com.ceix.ehs.model.loader.BIFileProcessor;
import com.ceix.ehs.model.loader.Employee;
import com.ceix.ehs.model.services.EHSIncidentEventsAMImpl;
import com.ceix.ehs.model.views.CeixEhsIncidentHeaderVOImpl;

import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCDataControl;
import oracle.adf.share.logging.ADFLogger;

import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.render.OutputMode;

import oracle.jbo.Row;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class ReportMB {
    
    
    private static ADFLogger _logger =
        ADFLogger.createADFLogger(ReportMB.class);
    private Employee emp;
    private RichOutputText rtwVal;
    private RichSelectOneChoice rtwSelectVal;
    public ReportMB() {
    }

    public void setReportParams(ActionEvent actionEvent) {
        _logger.info("Retrieving the attributes for Report");

        EHSIncidentEventsAMImpl appM = getAM();
        BIFileProcessor processFile = new BIFileProcessor();

        CeixEhsIncidentHeaderVOImpl ehs = appM.getCeixEhsIncidentHeader1();
        Row currentRow = ehs.getCurrentRow();

        if (currentRow != null) {
            _logger.info("Retrieving the attributes for PersonNumber: ");

            System.out.println("Emp" +
                               currentRow.getAttribute("PersonNumber"));
            String pNum =
                currentRow.getAttribute("PersonNumber") != null ? currentRow.getAttribute("PersonNumber").toString() :
                null;
            _logger.info("Retrieving the attributes for PersonNumber: "+pNum);

            if (pNum == null) {

                String messageText =
                    "Employee PersonalNumber is empty.Unable to retrieve Employee Details.";
                FacesMessage fm = new FacesMessage(messageText);

                fm.setSeverity(FacesMessage.SEVERITY_WARN);
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, fm);
                emp = new Employee();
                return;
            }
            try {

                emp = processFile.process(pNum);

            } catch (Exception e) {
                e.printStackTrace();
                

                String messageText =e.getMessage()!=null?e.getMessage() :"Unable to retrieve Employee Details.";
                _logger.warning("Exception in getting Employee Details:" +messageText);
                FacesMessage fm = new FacesMessage(messageText);

                fm.setSeverity(FacesMessage.SEVERITY_WARN);
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, fm);
            }

        }

    }
    private EHSIncidentEventsAMImpl getAM() {
        FacesContext context = FacesContext.getCurrentInstance();
        BindingContext bindingContext = BindingContext.getCurrent();
        DCDataControl dc =
            bindingContext.findDataControl("EHSIncidentEventsAMDataControl");
        EHSIncidentEventsAMImpl appM =
            (EHSIncidentEventsAMImpl)dc.getDataProvider();
        return appM;
    }

    public void beforePhaseMethod(PhaseEvent phaseEvent) {
        if (phaseEvent.getPhaseId() == PhaseId.RENDER_RESPONSE) {
            FacesContext fctx = FacesContext.getCurrentInstance();
            AdfFacesContext adfFacesContext =
                AdfFacesContext.getCurrentInstance();
     
            if (adfFacesContext.getOutputMode() == OutputMode.PRINTABLE) {
                ExtendedRenderKitService erks = null;
                erks =
    Service.getRenderKitService(fctx, ExtendedRenderKitService.class);
                erks.addScript(fctx, "window.print();");
            }
        }
    }

    public void setEmp(Employee emp) {
        this.emp = emp;
    }

    public Employee getEmp() {
        return emp;
    }
    
    public void setRtwVal(RichOutputText rtwVal) {
        if (this.getRtwSelectVal() != null) {
            if (this.getRtwSelectVal().getValue().toString().contains("1"))
                rtwVal.setValue("Yes");
            else
                rtwVal.setValue("No");

        }
        this.rtwVal = rtwVal;
    }

    public RichOutputText getRtwVal() {
       
        return rtwVal;
    }

    public void setRtwSelectVal(RichSelectOneChoice rtwSelectVal) {
        this.rtwSelectVal = rtwSelectVal;
    }

    public RichSelectOneChoice getRtwSelectVal() {
        return rtwSelectVal;
    }

}
