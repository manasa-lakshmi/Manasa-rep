package com.ceix.delivery.model.services.webserviceclient;

public class PoReceiptBean {
    Long buId;
    String vendorname;
    Long vendorNumber;
    Long shipToOrganizationId;
    Long employeeId;
    String documentNumber;
    String documentLineNumber;
    Long itemId;
    Long toOrganizationId;
    String ShipToOrganizationName;
    public PoReceiptBean() {
        super();
    }
    
    public void setBuId(Long buId) {
        this.buId = buId;
    }

    public Long getBuId() {
        return buId;
    }

    public void setVendorname(String vendorname) {
        this.vendorname = vendorname;
    }

    public String getVendorname() {
        return vendorname;
    }

    public void setShipToOrganizationId(Long shipToOrganizationId) {
        this.shipToOrganizationId = shipToOrganizationId;
    }

    public Long getShipToOrganizationId() {
        return shipToOrganizationId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }
    
    public void setDocumentLineNumber(String documentLineNumber) {
        this.documentLineNumber = documentLineNumber;
    }

    public String getDocumentLineNumber() {
        return documentLineNumber;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setToOrganizationId(Long toOrganizationId) {
        this.toOrganizationId = toOrganizationId;
    }

    public Long getToOrganizationId() {
        return toOrganizationId;
    }

    public void setVendorNumber(Long vendorNumber) {
        this.vendorNumber = vendorNumber;
    }

    public Long getVendorNumber() {
        return vendorNumber;
    }

    public void setShipToOrganizationName(String ShipToOrganizationName) {
        this.ShipToOrganizationName = ShipToOrganizationName;
    }

    public String getShipToOrganizationName() {
        return ShipToOrganizationName;
    }
}
