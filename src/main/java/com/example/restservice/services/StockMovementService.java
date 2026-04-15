package com.example.restservice.services;

import com.example.restservice.entity.Enum.MovementType;
import com.example.restservice.entity.Enum.StockUnit;
import com.example.restservice.entity.StockMovement;

import java.time.Instant;

public class StockMovementService {
    private Long id;
    private Instant createdAt;
    private StockUnit unit;
    private double value;
    private MovementType type;

    public StockMovementService(StockMovement sm) {
        this.id = sm.getId();
        this.createdAt = sm.getCreatedAt();
        this.unit = sm.getUnit();
        this.value = sm.getValue();
        this.type = sm.getType();
    }

    public Long getId() { return id; }
    public Instant getCreatedAt() { return createdAt; }
    public StockUnit getUnit() { return unit; }
    public double getValue() { return value; }
    public MovementType getType() { return type; }
}