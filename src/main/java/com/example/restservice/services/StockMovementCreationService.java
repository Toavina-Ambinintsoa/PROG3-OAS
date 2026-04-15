package com.example.restservice.services;

import com.example.restservice.entity.Enum.MovementType;
import com.example.restservice.entity.Enum.StockUnit;

public class StockMovementCreationService {
    private StockUnit unit;
    private double value;
    private MovementType type;

    public StockUnit getUnit() { return unit; }
    public void setUnit(StockUnit unit) { this.unit = unit; }

    public double getValue() { return value; }
    public void setValue(double value) { this.value = value; }

    public MovementType getType() { return type; }
    public void setType(MovementType type) { this.type = type; }
}