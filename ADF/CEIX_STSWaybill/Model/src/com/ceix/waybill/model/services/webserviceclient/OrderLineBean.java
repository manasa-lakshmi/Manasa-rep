package com.ceix.waybill.model.services.webserviceclient;

public class OrderLineBean {
    public String requestedSupplierNumber;
    public String requestedSupplierName ;       
    public Long requisitionInventoryOrganizationIdentifier;
    public Long buyerIdentifier;
    public Long productIdentifier;
    public String documentUserKey;
    public String documentLineUserKey;
    public OrderLineBean() {
        super();
    }
    public void setRequestedSupplierNumber(String requestedSupplierNumber) {
        this.requestedSupplierNumber = requestedSupplierNumber;
    }

    public String getRequestedSupplierNumber() {
        return requestedSupplierNumber;
    }

    public void setRequestedSupplierName(String requestedSupplierName) {
        this.requestedSupplierName = requestedSupplierName;
    }

    public String getRequestedSupplierName() {
        return requestedSupplierName;
    }

    public void setRequisitionInventoryOrganizationIdentifier(Long requisitionInventoryOrganizationIdentifier) {
        this.requisitionInventoryOrganizationIdentifier = requisitionInventoryOrganizationIdentifier;
    }

    public Long getRequisitionInventoryOrganizationIdentifier() {
        return requisitionInventoryOrganizationIdentifier;
    }

    public void setBuyerIdentifier(Long buyerIdentifier) {
        this.buyerIdentifier = buyerIdentifier;
    }

    public Long getBuyerIdentifier() {
        return buyerIdentifier;
    }

    public void setProductIdentifier(Long productIdentifier) {
        this.productIdentifier = productIdentifier;
    }

    public Long getProductIdentifier() {
        return productIdentifier;
    }

    public void setDocumentUserKey(String documentUserKey) {
        this.documentUserKey = documentUserKey;
    }

    public String getDocumentUserKey() {
        return documentUserKey;
    }
    
    public void setDocumentLineUserKey(String documentLineUserKey) {
        this.documentLineUserKey = documentLineUserKey;
    }

    public String getDocumentLineUserKey() {
        return documentLineUserKey;
    }
}
