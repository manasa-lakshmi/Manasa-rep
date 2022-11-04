package com.ceix.ehs.beans;


import com.ceix.ehs.model.services.EHSIncidentEventsAMImpl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.sql.SQLException;

import java.util.Map;
import java.util.logging.Level;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCDataControl;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.input.RichInputFile;
import oracle.adf.view.rich.component.rich.nav.RichCommandButton;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.jbo.Row;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.BlobDomain;
import oracle.jbo.server.ViewObjectImpl;

import org.apache.commons.io.IOUtils;
import org.apache.myfaces.trinidad.model.UploadedFile;


public class UploadViolationAttachmentMB {

    ADFLogger _logger = ADFLogger.createADFLogger(UploadViolationAttachmentMB.class);
    
    private RichOutputText violation_id;
    private RichCommandButton startUploadClick;
    private RichInputFile inputFile;


    public UploadViolationAttachmentMB() {
    }

  


    public void setStartUploadClick(RichCommandButton startUploadClick) {
        this.startUploadClick = startUploadClick;
    }

    public RichCommandButton getStartUploadClick() {
        return startUploadClick;
    }


    public String uploadFile() {
        
        _logger.log(Level.FINER, ">>fileUploadValueChangeListener");
        
        UploadedFile file = (UploadedFile)inputFile.getValue();
        String v_id = "";
        if (file != null) {

            EHSIncidentEventsAMImpl appM = getAM();
            try {
                BlobDomain c = this.createBlobDomain(file);
                
                ViewObjectImpl vo =
                    appM.getCeixEhsIncidentViolationAttachmentVO1();

                AdfFacesContext adfFacesContext =
                    AdfFacesContext.getCurrentInstance();
                Map pageFlowScope = adfFacesContext.getPageFlowScope();
                v_id = pageFlowScope.get("bviolation_id").toString();
                //System.out.println("v_id: "+v_id);
                Row newRow = vo.createRow();
                newRow.setAttribute("FileContent", c);
                newRow.setAttribute("FileName", file.getFilename());
                newRow.setAttribute("ViolationId",
                                    new oracle.jbo.domain.Number(v_id));
                vo.insertRow(newRow);
                appM.getDBTransaction().commit();
                
                
                inputFile.setValue(null);
                AdfFacesContext.getCurrentInstance().addPartialTarget(inputFile);
                showAlert("Attachment Upload","<p>File Uploaded successfully.</p>"
                          ,FacesMessage.SEVERITY_INFO);

                
            } catch (Exception e) {
                appM.getDBTransaction().rollback();
                _logger.severe("Error while uploading file.", e);
                showAlert("Attachment Upload","<p>Error while uploading the file: "+ e.getMessage()+"</p>"
                      ,FacesMessage.SEVERITY_ERROR);
            }
        }
        else
        {
            showAlert("Attachment Upload","<p>Please select a file.</p>"
                      ,FacesMessage.SEVERITY_INFO);
        }   
        
        
        return null;
    }
    

    private BlobDomain createBlobDomain(UploadedFile file) throws IOException,
                                                                  SQLException {
        InputStream in = null;
        BlobDomain blobDomain = null;
        OutputStream out = null;
            in = file.getInputStream();
            blobDomain = new BlobDomain();
            out = blobDomain.getBinaryOutputStream();
            byte[] buffer = new byte[8192];
            int bytesRead = 0;
            while ((bytesRead = in.read(buffer, 0, 8192)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            in.close();
        return blobDomain;
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


    public void downloadBlobFile(FacesContext facesContext,
                                 OutputStream outputStream) {
        
        EHSIncidentEventsAMImpl appM = getAM();
        DCBindingContainer dcbindings =
            (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding iterator =
            dcbindings.findIteratorBinding("CeixEhsIncidentViolationAttachmentVO1Iterator");
        ViewObject vo = iterator.getViewObject();
        
        oracle.jbo.domain.Number ViolationAttchmId = (oracle.jbo.domain.Number)
        vo.getCurrentRow().getAttribute("ViolationAttchmId");
        
        BlobDomain blob =null;
        _logger.log(Level.FINER, "ViolationAttchmId:"+ViolationAttchmId);
       
        try { // copy the data from the blobDomain to the output stream
            blob = (BlobDomain)
             vo.getCurrentRow().getAttribute("FileContent");
            IOUtils.copy(blob.getInputStream(), outputStream);
            //blob.closeInputStream();
            outputStream.flush();
            
        } catch (Exception e) {
            // handle errors
            _logger.severe("Error while downloading Citation Attachment. ", e);
        }
        finally {
            if(blob!=null)
            blob.closeInputStream();
        }
    }

    public void showAlert(String title, String msg, javax.faces.application.FacesMessage.Severity severity) {

           StringBuilder message = new StringBuilder("<html><body>");
           message.append(msg);
           message.append("</body></html>");
           FacesMessage fm = new FacesMessage(message.toString());
           fm.setSeverity(severity);
           FacesContext fctx = FacesContext.getCurrentInstance();
           fctx.addMessage(title, fm);        
           
       }
    
    public void setViolation_id(RichOutputText violation_id) {
        this.violation_id = violation_id;
    }

    public RichOutputText getViolation_id() {
        return violation_id;
    }


    public void loadInitialPage() {
        // Add event code here...
    }


    public void setInputFile(RichInputFile inputFile) {
        this.inputFile = inputFile;
    }

    public RichInputFile getInputFile() {
        return inputFile;
    }



   
}
