package com.ceix.waybill_purch_coal_import.loader;
import com.opencsv.bean.CsvBindByName;

import java.io.Serializable;

/**
 * Bean to represent a Shipment Coal Car details file and its fields.
 * Used for Shipment coal car file import CSV reader
 * job.
 * @author Romesh Soni
 *
 */
public class ShipmentCoalCarBean  implements Serializable{
    private static final long serialVersionUID = 1L;
        
    public ShipmentCoalCarBean() {
        super();
    }
    
    @CsvBindByName
    private String Unit_Train_number;
    @CsvBindByName
    private String Shipped_date;
    @CsvBindByName
    private String Freight_Cost;
    @CsvBindByName
    private String Load_Sequence;
    @CsvBindByName
    private String Car_Initial;
    @CsvBindByName
    private String Car_number;
    @CsvBindByName
    private String Weight;
    @CsvBindByName
    private String UOM;
    @CsvBindByName
    private String Sales_order_number;
    @CsvBindByName
    private String Rail_road;
    @CsvBindByName
    private String Class_Name;


    public void setUnit_Train_number(String Unit_Train_number) {
        this.Unit_Train_number = Unit_Train_number;
    }

    public String getUnit_Train_number() {
        return Unit_Train_number;
    }

    public void setShipped_date(String Shipped_date) {
        this.Shipped_date = Shipped_date;
    }

    public String getShipped_date() {
        return Shipped_date;
    }

    public void setFreight_Cost(String Freight_Cost) {
        this.Freight_Cost = Freight_Cost;
    }

    public String getFreight_Cost() {
        return Freight_Cost;
    }

    public void setLoad_Sequence(String Load_Sequence) {
        this.Load_Sequence = Load_Sequence;
    }

    public String getLoad_Sequence() {
        return Load_Sequence;
    }

    public void setCar_Initial(String Car_Initial) {
        this.Car_Initial = Car_Initial;
    }

    public String getCar_Initial() {
        return Car_Initial;
    }

    public void setCar_number(String Car_number) {
        this.Car_number = Car_number;
    }

    public String getCar_number() {
        return Car_number;
    }

    public void setWeight(String Weight) {
        this.Weight = Weight;
    }

    public String getWeight() {
        return Weight;
    }

    public void setUOM(String UOM) {
        this.UOM = UOM;
    }

    public String getUOM() {
        return UOM;
    }

    public void setSales_order_number(String Sales_order_number) {
        this.Sales_order_number = Sales_order_number;
    }

    public String getSales_order_number() {
        return Sales_order_number;
    }

    public void setRail_road(String Rail_road) {
        this.Rail_road = Rail_road;
    }

    public String getRail_road() {
        return Rail_road;
    }

    public void setClass_Name(String Class_Name) {
        this.Class_Name = Class_Name;
    }

    public String getClass_Name() {
        return Class_Name;
    }
}
