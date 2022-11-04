package com.ceix.ehs.beans;

import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.context.AdfFacesContext;

public class DisableFiledsMB {
    
    private boolean disabled = false;
    private RichPanelFormLayout mainForm;
    private RichSelectOneChoice mineID;

    public DisableFiledsMB() {
    }

    public void disableMainFileds(ActionEvent actionEvent) {
        // Add event code here...
        this.setDisabled(!isDisabled());
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public String disableFields() {
        // Add event code here..
        if (isDisabled()) {
            System.out.println("if Disable");
            this.setDisabled(isDisabled());
        }
        else {
            System.out.println("else Enable");
            this.setDisabled(!isDisabled());
        }
        return "edit";
    }

    public String enableFields() {
        // Add event code here.
        if(isDisabled())
        {
        System.out.println("if Enable");
        this.setDisabled(!isDisabled());
        }
        else{
            System.out.println("else Enable");
            this.setDisabled(isDisabled());
        }
        return "initViolationNotc";  
    }

    public void setMainForm(RichPanelFormLayout mainForm) {
        this.mainForm = mainForm;
    }

    public RichPanelFormLayout getMainForm() {
        return mainForm;
    }

    protected void refreshPage() {
        FacesContext fc = FacesContext.getCurrentInstance();
        String refreshpage = fc.getViewRoot().getViewId();
        ViewHandler ViewH = fc.getApplication().getViewHandler();
        UIViewRoot UIV = ViewH.createView(fc, refreshpage);
        UIV.setViewId(refreshpage);
        fc.setViewRoot(UIV);
    }

    public void setMineID(RichSelectOneChoice mineID) {
        this.mineID = mineID;
    }

    public RichSelectOneChoice getMineID() {
        return mineID;
    }
}
