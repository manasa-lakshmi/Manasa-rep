package com.ceix.ehs.model.ws.proxy;

import javax.xml.ws.WebFault;

@WebFault(faultBean="com.ceix.ehs.model.ws.proxy.types.OperationFailedException",
  targetNamespace="http://xmlns.oracle.com/oxp/service/v2", name="fault1")
public class OperationFailedException
  extends Exception
{
  private com.ceix.ehs.model.ws.proxy.types.OperationFailedException faultInfo;

  public OperationFailedException(String message,
                                  com.ceix.ehs.model.ws.proxy.types.OperationFailedException faultInfo)
  {
    super(message);
    this.faultInfo = faultInfo;
  }

  public OperationFailedException(String message,
                                  com.ceix.ehs.model.ws.proxy.types.OperationFailedException faultInfo,
                                  Throwable t)
  {
    super(message,t);
    this.faultInfo = faultInfo;
  }

  public com.ceix.ehs.model.ws.proxy.types.OperationFailedException getFaultInfo()
  {
    return faultInfo;
  }

  public void setFaultInfo(com.ceix.ehs.model.ws.proxy.types.OperationFailedException faultInfo)
  {
    this.faultInfo = faultInfo;
  }
}
// !DO NOT EDIT THIS FILE!
// This source file is generated by Oracle tools
// Contents may be subject to change
// For reporting problems, use the following
// Version = Oracle WebServices (11.1.1.0.0, build 150302.2135.55683)