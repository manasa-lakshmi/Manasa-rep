package com.ceix.delivery.model.services.webserviceclient;

import java.util.List;

public class OrderDetailsResponseBean {
    public Long requestingBusinessUnitIdentifier;

    public List<OrderLineBean> orderLineList;

    public OrderDetailsResponseBean() {
        super();
    }

    public void setRequestingBusinessUnitIdentifier(Long requestingBusinessUnitIdentifier) {
        this.requestingBusinessUnitIdentifier = requestingBusinessUnitIdentifier;
    }

    public Long getRequestingBusinessUnitIdentifier() {
        return requestingBusinessUnitIdentifier;
    }

    public void setOrderLineList(List<OrderLineBean> orderLineList) {
        this.orderLineList = orderLineList;
    }

    public List<OrderLineBean> getOrderLineList() {
        return orderLineList;
    }
}
