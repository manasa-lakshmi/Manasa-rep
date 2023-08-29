package com.ceix.waybill_purch_coal_import.loader;

import com.opencsv.bean.CsvBindByName;

import java.io.Serializable;

/**
 * Bean to represent a Waybill file and its fields.
 * Used for waybill file import CSV reader
 * job.
 * @author Romesh Soni
 *
 */

public class WaybillFileBean implements Serializable{
    private static final long serialVersionUID = 1L;
    
    public WaybillFileBean() {
        super();
    }
    
    @CsvBindByName
    private String Unit_Train_number;
    @CsvBindByName
    private String Dumped_date;
    @CsvBindByName
    private String Dumped_Location;
    @CsvBindByName
    private String Freight_Cost;
    @CsvBindByName
    private String Type;
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


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public void setUnit_Train_number(String Unit_Train_number) {
        this.Unit_Train_number = Unit_Train_number;
    }

    public String getUnit_Train_number() {
        return Unit_Train_number;
    }

    public void setDumped_date(String Dumped_date) {
        this.Dumped_date = Dumped_date;
    }

    public String getDumped_date() {
        return Dumped_date;
    }

    public void setDumped_Location(String Dumped_Location) {
        this.Dumped_Location = Dumped_Location;
    }

    public String getDumped_Location() {
        return Dumped_Location;
    }

    public void setFreight_Cost(String Freight_Cost) {
        this.Freight_Cost = Freight_Cost;
    }

    public String getFreight_Cost() {
        return Freight_Cost;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public String getType() {
        return Type;
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
}
