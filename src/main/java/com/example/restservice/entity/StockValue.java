package com.example.restservice.entity;

public class StockValue {
    private String unit;
    private double quantity;

    public StockValue() {}

    public StockValue(String unit, double quantity) {
        this.unit = unit;
        this.quantity = quantity;
    }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    public double getValue() { return quantity; }
    public void setValue(double value) { this.quantity = quantity; }
}
